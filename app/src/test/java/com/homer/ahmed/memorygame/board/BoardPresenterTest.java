package com.homer.ahmed.memorygame.board;

import com.homer.ahmed.memorygame.data.Card;
import com.homer.ahmed.memorygame.data.GridOption;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class BoardPresenterTest {

    private BoardPresenter presenter;
    private GridOption gridOption = new GridOption("4x5");
    private BoardContract.View view;
    private int position = 10;

    @Before
    public void setUp() {
        view = mock(BoardContract.View.class);
        presenter = new BoardPresenter();
        presenter.setView(view);
        presenter.setGridOption(gridOption);
    }

    @After
    public void tearDown() {
        presenter = null;
    }

    @Test
    public void testPopulateViewAddsCardsToView() {
        presenter.populateView();

        int expectedSize = gridOption.getLength() * gridOption.getWidth();
        assertEquals(expectedSize, presenter.getCards().size());
        verify(view).populateCardGrid(presenter.getCards(), gridOption.getWidth());
    }

    @Test
    public void testPopulateViewDoesNotAddNewCardsIfAlreadyPopulated() {
        List<Card> cards = new ArrayList<>();
        cards.add(new Card(Card.Type.BAT));
        presenter.setCards(cards);

        presenter.populateView();

        assertEquals(presenter.getCards().size(), cards.size());
        verify(view, times(1)).populateCardGrid(anyList(), anyInt());
    }

    @Test
    public void testCardsHasTwoOfEveryTypeOfCardUsed() {
        // For every card, count number of cards of that type,
        // make sure number is greater than 1 and even
        presenter.generateCards();
        List<Card> cards = presenter.getCards();
        cards.forEach(card -> {
            long numberOfCardsOfType = cards.stream().filter(c -> c.matches(card)).count();
            assertTrue(numberOfCardsOfType > 1 && numberOfCardsOfType % 2 == 0);
        });
    }

    @Test
    public void testCardsHasAtMostOneOfEveryTypeOfAnimalBeforeRepeating() {
        presenter.setGridOption(new GridOption("12x15"));
        presenter.generateCards();
        List<Card> cards = presenter.getCards();

        // Map to keep count of each card type
        Map<Card.Type, Integer> cardTypeMap = new HashMap<>();
        cards.forEach(card -> cardTypeMap.put(card.getType(),
                cardTypeMap.containsKey(card.getType()) ? cardTypeMap.get(card.getType()) + 1 : 1));

        // Formula example: 4x5 grid yields 20 cards;
        //  There are 8 types -> 16 cards with unique matches,
        //  Leaving 4 extra cards -> 2 types can repeat ->
        //      no more than 2 types should have >2 cards
        List<Card.Type> types = Arrays.asList(Card.Type.values());
        int numberOfTypesThatCanRepeat = (cards.size() - (types.size() * 2)) / 2;
        int numberOfRepeatingTypes = 0;

        for(Card.Type type : types) {
            if (cardTypeMap.containsKey(type) && cardTypeMap.get(type) > 2) {
                numberOfRepeatingTypes++;
            }
        }

        if (numberOfTypesThatCanRepeat > 0) {
            assertTrue(numberOfRepeatingTypes <= numberOfTypesThatCanRepeat);
        } else {
            assertEquals(0, numberOfRepeatingTypes);
        }
    }

    @Test
    public void testCardsIsShuffled() {
        // increase number of cards to decrease likelihood of random perfect order
        presenter.setGridOption(new GridOption("12x15"));
        presenter.generateCards();
        List<Card> cards = presenter.getCards();

        Card.Type currentlyCheckingCardType = Card.Type.BAT;
        int numberOfRepeats = 0;
        for (int i = 0; i < cards.size(); i++) {
            if (i % 2 == 0) {
                currentlyCheckingCardType = cards.get(i).getType();
            } else if (cards.get(i).getType().equals(currentlyCheckingCardType)){
                numberOfRepeats++;
            }
        }

        int numberOfRepeatsIfUnevenDistribution = cards.size() / 2;
        assertTrue(numberOfRepeats < numberOfRepeatsIfUnevenDistribution);
    }

    @Test
    public void testOneCardAppearsUntilAnotherCardIsClicked() {
        presenter.generateCards();
        presenter.setFlippedCard(null);

        presenter.onCardClicked(position);

        assertEquals(presenter.getCards().get(position), presenter.getFlippedCard());
        verify(view, times(1)).populateCardGrid(anyList(), anyInt());
    }

    @Test
    public void testThereShouldBeAtMostTwoFlippedCards() {
        presenter.generateCards();
        List<Card> cards = presenter.getCards();
        Random random = new Random();
        int numberOfFlippedCards = 3;
        for (int i = 0; i < numberOfFlippedCards; i++) {
            cards.get(random.nextInt(cards.size())).setFlipped(true);
        }

        presenter.onCardClicked(position);

        verify(view, times(0)).populateCardGrid(anyList(), anyInt());
    }

    @Test
    public void testTwoMatchingCardsContinueToShow() {
        List<Card> cards = Arrays.asList(new Card(Card.Type.BAT), new Card(Card.Type.BAT));
        cards.get(0).setFlipped(true);
        presenter.setFlippedCard(cards.get(0));
        presenter.setCards(cards);

        presenter.onCardClicked(1);

        assertTrue(cards.get(0).isFlipped());
        assertTrue(cards.get(1).isFlipped());
        assertTrue(cards.get(0).isMatched());
        assertTrue(cards.get(1).isMatched());
        verify(view, times(1)).populateCardGrid(anyList(), anyInt());
    }

    @Test
    public void testTwoNonMatchingCardsContinueToShowForOneSecondBeforeBeingFlipped() {
        List<Card> cards = Arrays.asList(new Card(Card.Type.HEN), new Card(Card.Type.BAT));
        cards.get(0).setFlipped(true);
        presenter.setFlippedCard(cards.get(0));
        presenter.setCards(cards);
        Timer timer = mock(Timer.class);
        presenter.setTimer(timer);

        presenter.onCardClicked(1);

        assertTrue(cards.get(0).isFlipped());
        assertTrue(cards.get(1).isFlipped());
        assertFalse(cards.get(0).isMatched());
        assertFalse(cards.get(1).isMatched());
        verify(timer).schedule(any(TimerTask.class), eq(BoardPresenter.DELAY));
    }

    @Test
    public void testCardsShouldNotBeActionableWhileFlipped() {
        presenter.generateCards();
        List<Card> cards = presenter.getCards();
        cards.get(position).setFlipped(true);
        presenter.setCards(cards);

        presenter.onCardClicked(position);

        verify(view, times(0)).populateCardGrid(anyList(), anyInt());
    }

    @Test
    public void testCardsShouldNotBeActionableIfMatched() {
        presenter.generateCards();
        List<Card> cards = presenter.getCards();
        cards.get(position).setMatched(true);
        presenter.setCards(cards);

        presenter.onCardClicked(position);

        verify(view, times(0)).populateCardGrid(anyList(), anyInt());
    }
}