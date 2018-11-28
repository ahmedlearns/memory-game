package com.homer.ahmed.memorygame.board;

import android.util.Log;

import com.homer.ahmed.memorygame.data.Card;
import com.homer.ahmed.memorygame.data.GridOption;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class BoardPresenter implements BoardContract.Actions {

    private static final String TAG = BoardPresenter.class.getSimpleName();
    private static final int DELAY = 1000;

    private BoardContract.View view;
    private GridOption gridOption;
    private List<Card> cards = new ArrayList<>();
    private Card flippedCard;

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
        // Randomly populate board with cards
        if (!cards.isEmpty()) {
            Log.d(TAG, "populateView: cards is already populated");
            return;
        }

        int numberOfCards = gridOption.getLength() * gridOption.getWidth();
        for (int i = 0; i < numberOfCards; i++) {
            Card.Type type = Card.Type.randomLetter();
            cards.add(new Card(type));
            cards.add(new Card(type));
            i++;
        }
        Collections.shuffle(cards);

        view.populateCardGrid(cards, gridOption.getWidth());
    }

    @Override
    public void onCardClicked(int position) {
        Card card = cards.get(position);

        if (card.isFlipped()) {
            Log.d(TAG, "onCardClicked: Cards are not actionable while flipped");
            return;
        }

        long numberOfFlippedCardInPlay = cards.stream().filter(c -> c.isFlipped() && !c.isMatched()).count();
        if (numberOfFlippedCardInPlay == 2) {
            Log.d(TAG, "onCardClicked: Wait until cards are flipped back to continue.");
            return;
        }

        card.setFlipped(true);

        if (card.equals(flippedCard) || card.isMatched()) {
            Log.d(TAG, "onCardClicked: Pick another card to continue.");
            return;
        }

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
        } else {
            // If they do not match, wait 1 second before updating.
            view.populateCardGrid(cards, gridOption.getWidth());

            new Timer().schedule(new TimerTask() {
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

    //
    // Methods for unit tests
    //

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }
}
