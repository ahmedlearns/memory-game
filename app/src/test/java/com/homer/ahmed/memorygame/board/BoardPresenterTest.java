package com.homer.ahmed.memorygame.board;

import com.homer.ahmed.memorygame.data.Card;
import com.homer.ahmed.memorygame.data.GridOption;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class BoardPresenterTest {

    private BoardPresenter presenter;
    private GridOption gridOption = new GridOption("3x4");
    private BoardContract.View view;

    @Before
    public void setUp() {
        view = mock(BoardContract.View.class);
        presenter = new BoardPresenter();
        presenter.setView(view);
        presenter.setGridOption(gridOption);
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

        verify(view, times(0)).populateCardGrid(anyList(), anyInt());
    }

    @Test
    public void testCardsHasTwoOfEveryTypeOfCardUsed() {

    }

    @Test
    public void testCardsIsShuffled() {

    }

    @Test
    public void testOneCardAppearsUntilAnotherCardIsClicked() {

    }

    @Test
    public void testTwoMatchingCardsContinueToShow() {

    }

    @Test
    public void testTwoNonMatchingCardsContinueToShowForOneSecondBeforeBeingFlipped() {

    }

    @Test
    public void testCardsShouldNotBeActionableWhileFlipped() {

    }
}