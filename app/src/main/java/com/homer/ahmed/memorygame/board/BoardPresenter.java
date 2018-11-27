package com.homer.ahmed.memorygame.board;

import com.homer.ahmed.memorygame.data.Card;
import com.homer.ahmed.memorygame.data.GridOption;

import java.util.ArrayList;
import java.util.List;

public class BoardPresenter implements BoardContract.Actions {

    private BoardContract.View view;
    private GridOption gridOption;
    private List<Card> cards = new ArrayList<>();

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
        int numberOfCards = gridOption.getLength() * gridOption.getWidth();
        for (int i = 0; i < numberOfCards; i++) {
            Card.Type type = Card.Type.randomLetter();
            Card card = new Card(type);
            cards.add(card);
        }

        view.populateCardGrid(cards, gridOption.getWidth());
    }

    @Override
    public void onCardClicked(int position) {
        Card card = cards.get(position);
        card.setFlipped(!card.isFlipped());

        view.populateCardGrid(cards, gridOption.getWidth());
    }
}
