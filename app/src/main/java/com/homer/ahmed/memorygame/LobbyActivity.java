package com.homer.ahmed.memorygame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.homer.ahmed.memorygame.board.BoardActivity;
import com.homer.ahmed.memorygame.data.GridOption;
import com.homer.ahmed.memorygame.util.OptionsAdapter;

import java.util.ArrayList;

public class LobbyActivity extends AppCompatActivity {

    private static final String TAG = LobbyActivity.class.getSimpleName();

    private ListView optionsListView;
    private ArrayList<GridOption> gridOptions = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);

        gridOptions.add(new GridOption("3x4"));
        gridOptions.add(new GridOption("5x2"));
        gridOptions.add(new GridOption("4x4"));
        gridOptions.add(new GridOption("4x5"));

        optionsListView = findViewById(R.id.options_list);
        OptionsAdapter adapter = new OptionsAdapter(this, R.layout.item_option, gridOptions);
        adapter.setGridOptionEventListener(option -> {
            Intent intent = BoardActivity.createIntent(getApplicationContext(), option);
            startActivity(intent);
        });
        optionsListView.setAdapter(adapter);

        Log.d(TAG, "onCreate: gridOptions: " + gridOptions);
    }
}
