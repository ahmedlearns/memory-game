package com.homer.ahmed.memorygame.board;

import com.homer.ahmed.memorygame.data.Card;
import com.homer.ahmed.memorygame.data.GridOption;

import java.util.List;

public interface BoardContract {

    interface Actions {
        /**
         * Method to give the presenter a reference to its view
         * @param view View to be given to presenter
         */
        void setView(BoardContract.View view);

        void setGridOption(GridOption gridOption);

        void populateView();

        void onCardClicked(int position);
    }

    interface View {

        void populateCardGrid(List<Card> cards, int width);
    }
}
