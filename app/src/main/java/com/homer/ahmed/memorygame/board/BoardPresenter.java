package com.homer.ahmed.memorygame.board;

import android.util.Log;

import com.homer.ahmed.memorygame.data.Card;
import com.homer.ahmed.memorygame.data.GridOption;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class BoardPresenter implements BoardContract.Actions {

    private static final String TAG = BoardPresenter.class.getSimpleName();
    public static final long DELAY = 1000;

    private BoardContract.View view;
    private GridOption gridOption;
    private List<Card> cards = new ArrayList<>();
    private Card flippedCard;
    private Timer timer = new Timer();

    @Override
    public void setView(BoardContract.View view) {
        this.view = view;
    }

    @Override
    public void setGridOption(GridOption gridOption) {
        this.gridOption = gridOption;
    }

    @Override
    public void populateView() {
        if (!cards.isEmpty()) {
            Log.d(TAG, "populateView: cards is already populated, not re-generating new cards");
            view.populateCardGrid(cards, gridOption.getWidth());
            return;
        }

        generateCards();
        view.populateCardGrid(cards, gridOption.getWidth());
    }

    @Override
    public void onCardClicked(int position) {
        Card card = cards.get(position);

        if (card.isFlipped() || card.isMatched()) {
            Log.d(TAG, "onCardClicked: Cards are not actionable while flipped or matched");
            return;
        }

        long numberOfFlippedCardsInPlay = cards.stream().filter(c -> c.isFlipped() && !c.isMatched()).count();
        if (numberOfFlippedCardsInPlay >= 2) {
            Log.d(TAG, "onCardClicked: Wait until cards are flipped back to continue.");
            return;
        }

        card.setFlipped(true);

        // If there is one card flipped, set their flipped state, return
        if (null == flippedCard) {
            flippedCard = card;
            view.populateCardGrid(cards, gridOption.getWidth());
            return;
        }

        // If there are two cards flipped, check if they match
        if (card.matches(flippedCard)) {
            card.setMatched(true);
            flippedCard.setMatched(true);
            view.populateCardGrid(cards, gridOption.getWidth());
            flippedCard = null;

            // check for win scenario
            boolean containsUnmatchedCards = cards.stream().anyMatch(c -> !c.isMatched());
            if (!containsUnmatchedCards) {
                view.cardsAreMatched();
            }
        } else {
            // If they do not match, wait 1 second before updating.
            view.populateCardGrid(cards, gridOption.getWidth());

            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    card.setFlipped(false);
                    flippedCard.setFlipped(false);
                    view.updateAfterDelay(cards);
                    flippedCard = null;
                }
            }, DELAY);
        }
    }

    @Override
    public List<Card> saveCardsState() {
        return cards;
    }

    @Override
    public void restoreCardsState(List<Card> cards) {
        this.cards = cards;
        // Restore flippedCard state
        if (null == flippedCard) {
            Optional<Card> optionalCard = this.cards
                    .stream()
                    .filter(card -> card.isFlipped() && !card.isMatched())
                    .findFirst();
            optionalCard.ifPresent(card -> flippedCard = card);
        }
    }

    //
    // Helper Methods
    //

    /**
     * Generate as even a distribution of card types as possible
     * by getting a random card of each type until there are no types left,
     * then repeat.
     */
    public void generateCards() {
        List<Card.Type> typesLeft = new ArrayList<>(EnumSet.allOf(Card.Type.class));
        int numberOfCards = gridOption.getLength() * gridOption.getWidth();
        Random random = new Random();
        for (int i = 0; i < numberOfCards; i++) {
            if (typesLeft.isEmpty()) {
                typesLeft = new ArrayList<>(EnumSet.allOf(Card.Type.class));
            }
            Card.Type randomType = typesLeft.get(random.nextInt(typesLeft.size()));
            cards.add(new Card(randomType));
            cards.add(new Card(randomType));
            typesLeft.remove(randomType);
            i++;
        }
        Collections.shuffle(cards);
    }

    //
    // Methods for unit tests
    //

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public Card getFlippedCard() {
        return flippedCard;
    }

    public void setFlippedCard(Card flippedCard) {
        this.flippedCard = flippedCard;
    }

    public void setTimer(Timer timer) {
        this.timer = timer;
    }
}
