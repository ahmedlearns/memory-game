package com.homer.ahmed.memorygame.board;

import com.homer.ahmed.memorygame.data.Card;
import com.homer.ahmed.memorygame.data.GridOption;

import java.util.List;

public interface BoardContract {

    interface Actions {
        /**
         * Give the presenter a reference to its view
         * @param view View to be given to presenter
         */
        void setView(BoardContract.View view);

        /**
         * Give the presenter the user chosen grid option.
         *
         * @param gridOption Grid configuration chosen by the user
         */
        void setGridOption(GridOption gridOption);

        /**
         * Use view characteristics and user-chosen parameters to construct the data model and
         * inflate UI views.
         */
        void populateView();

        /**
         * Handle an event when a card is clicked.
         *
         * This method will handle all scenarios of clicking a card. This can be the first or second card of
         * a pair to be matched, an already matched card, or a card that is already showing its image.
         *
         * @param position Index of card the user has interacted with
         */
        void onCardClicked(int position);
    }

    interface View {
        /**
         * Generate views based on the current list of cards in the game.
         *
         * @param cards List of cards with corresponding "flipped" and "matched" states
         * @param width Number of cards to display in a row
         */
        void populateCardGrid(List<Card> cards, int width);

        /**
         * Update card views after a simulated delay in a separate thread (besides the UI/main thread)
         *
         * @param cards List of cards with corresponding "flipped" and "matched" states
         */
        void updateAfterDelay(List<Card> cards);
    }
}
