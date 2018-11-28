package com.homer.ahmed.memorygame;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.homer.ahmed.memorygame.board.BoardActivity;
import com.homer.ahmed.memorygame.data.GridOption;
import com.homer.ahmed.memorygame.util.OptionsAdapter;

import java.util.ArrayList;

public class LobbyActivity extends AppCompatActivity {

    private static final String TAG = LobbyActivity.class.getSimpleName();

    private ListView optionsListView;
    private ArrayList<GridOption> gridOptions = new ArrayList<>();
    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);

        title = findViewById(R.id.title);
        Typeface font = ResourcesCompat.getFont(getApplicationContext(), R.font.sushi_omelette);
        title.setTypeface(font);

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
