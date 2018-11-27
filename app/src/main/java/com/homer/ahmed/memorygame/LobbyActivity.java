package com.homer.ahmed.memorygame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.homer.ahmed.memorygame.data.Option;
import com.homer.ahmed.memorygame.util.OptionsAdapter;

import java.util.ArrayList;

public class LobbyActivity extends AppCompatActivity {

    private static final String TAG = LobbyActivity.class.getSimpleName();

    private ListView optionsListView;
    private ArrayList<Option> options = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);

        options.add(new Option("3x4"));
        options.add(new Option("5x2"));
        options.add(new Option("4x4"));
        options.add(new Option("4x5"));

        optionsListView = findViewById(R.id.options_list);
        OptionsAdapter adapter = new OptionsAdapter(this, R.layout.item_option, options);
        optionsListView.setAdapter(adapter);

        Log.d(TAG, "onCreate: options: " + options);
    }
}
