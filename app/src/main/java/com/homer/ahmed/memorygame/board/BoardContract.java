package com.homer.ahmed.memorygame.board;

import com.homer.ahmed.memorygame.data.Card;
import com.homer.ahmed.memorygame.data.GridOption;

import java.util.List;

public interface BoardContract {

    interface Actions {

        void setView(BoardContract.View view);
        void setGridOption(GridOption gridOption);
        void populateView();
    }

    interface View {

        void populateCardGrid(List<Card> cards, int width);
    }
}
