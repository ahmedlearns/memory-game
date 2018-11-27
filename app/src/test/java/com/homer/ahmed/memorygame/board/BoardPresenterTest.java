package com.homer.ahmed.memorygame.board;

import com.homer.ahmed.memorygame.data.Card;
import com.homer.ahmed.memorygame.data.GridOption;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class BoardPresenterTest {

    BoardPresenter presenter;
    GridOption gridOption = new GridOption("3x4");
    BoardContract.View view = new BoardContract.View() {
        @Override
        public void populateCardGrid(List<Card> cards, int width) {
        }

        @Override
        public void updateAfterDelay(List<Card> cards) {
        }
    };

    @Before
    public void setUp() {
        presenter = new BoardPresenter();
        presenter.setView(view);
        presenter.setGridOption(gridOption);
    }

    @Test
    public void testPopulateViewAddsCardsToView() {

    }

    @Test
    public void testPopulateViewDoesNotAddNewCardsIfAlreadyPopulated() {

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