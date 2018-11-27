package com.homer.ahmed.memorygame.board;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;
import android.widget.Toast;

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

        cardsGrid = findViewById(R.id.cards_grid);
        cardsGrid.setOnItemClickListener((parent, view, position, id) ->
                Toast.makeText(getBaseContext(), "Clicking on item " + position, Toast.LENGTH_LONG).show());

        // Set up presenter
        presenter = new BoardPresenter();
        presenter.setView(this);

        // Check for necessary extras
        if (!getIntent().hasExtra(OPTION)) {
            Log.e(TAG, "EXTRA_EPISODE_ID does not exist.");
            finishActivity(-1);
            return;
        }

        GridOption gridOption = (GridOption) getIntent().getBundleExtra(OPTION).get(OPTION);
        presenter.setGridOption(gridOption);
    }

    @Override
    protected void onResume() {
        super.onResume();

        presenter.populateView();
    }

    @Override
    public void populateCardGrid(List<Card> cards, int width) {
        adapter = new ImageAdapter(getBaseContext(), cards);
        cardsGrid.setNumColumns(width);
        cardsGrid.setAdapter(adapter);
    }
}