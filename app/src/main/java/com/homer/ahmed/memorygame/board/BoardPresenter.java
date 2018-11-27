package com.homer.ahmed.memorygame.board;

import com.homer.ahmed.memorygame.data.GridOption;

public class BoardPresenter implements BoardContract.Actions {

    BoardContract.View view;
    GridOption gridOption;

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
        view.updateOption(gridOption.getName());
    }
}
