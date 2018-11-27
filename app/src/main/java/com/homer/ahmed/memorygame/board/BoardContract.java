package com.homer.ahmed.memorygame.board;

import com.homer.ahmed.memorygame.data.GridOption;

public interface BoardContract {

    interface Actions {

        void setView(BoardContract.View view);
        void setGridOption(GridOption gridOption);
        void populateView();
    }

    interface View {

        void updateOption(String name);
    }
}
