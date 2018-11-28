package com.homer.ahmed.memorygame.board;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.GridView;

import com.homer.ahmed.memorygame.R;
import com.homer.ahmed.memorygame.data.Card;
import com.homer.ahmed.memorygame.data.GridOption;
import com.homer.ahmed.memorygame.util.ImageAdapter;

import java.util.List;

public class BoardActivity extends AppCompatActivity implements BoardContract.View {

    private static final String TAG = BoardActivity.class.getSimpleName();
    private static final String OPTION = "gridOption";

    private BoardContract.Actions presenter;

    private GridView cardsGrid;
    private ImageAdapter adapter;

    public static Intent createIntent(Context context, GridOption gridOption) {
        Intent intent = new Intent(context, BoardActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(OPTION, gridOption);
        intent.putExtra(OPTION, bundle);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);

        initializeToolbar();
        initializePresenter();
        initializeViews();
        loadGridOption();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.populateView();
    }

    @Override
    public void populateCardGrid(List<Card> cards, int width) {
        adapter.setCards(cards);
        cardsGrid.setNumColumns(width);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void updateAfterDelay(List<Card> cards) {
        adapter.setCards(cards);
        // Adapter must be refreshed using a Handler due to being called from a separate thread.
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            adapter.notifyDataSetChanged();
        }, 0);
    }

    //
    // Helper Methods
    //

    /**
     * Initialize custom toolbar with provided back image.
     * Enable image to act as the Android "Up" function
     *
     * Possible features/menu options:
     *      - A "pause" state if a timer was running, with additional options (e.g. reset, back to home screen, sound options, etc)
     *      - Reset option
     */
    private void initializeToolbar() {
        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_action_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    /**
     * Create a presenter to house business logic. Set the view to interact with it.
     */
    private void initializePresenter() {
        presenter = new BoardPresenter();
        presenter.setView(this);
    }

    /**
     * Set up grid of cards and event listener for each item.
     *
     * Possible feature: add card transition animations.
     */
    private void initializeViews() {
        cardsGrid = findViewById(R.id.cards_grid);
        cardsGrid.setOnItemClickListener((parent, view, position, id) -> presenter.onCardClicked(position));

        adapter = new ImageAdapter(getBaseContext());
        cardsGrid.setAdapter(adapter);
    }

    /**
     * Confirm chosen grid option has been passed from previous screen.
     */
    private void loadGridOption() {
        // Check for necessary extras
        if (!getIntent().hasExtra(OPTION)) {
            Log.e(TAG, "OPTION does not exist.");
            finishActivity(-1);
            return;
        }

        GridOption gridOption = (GridOption) getIntent().getBundleExtra(OPTION).get(OPTION);
        presenter.setGridOption(gridOption);
    }
}
