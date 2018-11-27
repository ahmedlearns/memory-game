package com.homer.ahmed.memorygame.util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.homer.ahmed.memorygame.R;
import com.homer.ahmed.memorygame.data.Option;

import java.util.List;

public class OptionsAdapter extends ArrayAdapter<Option> {
    public OptionsAdapter(@NonNull Context context, int resource, @NonNull List<Option> options) {
        super(context, resource, options);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Get the data item for this position
        Option option = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_option, parent, false);
        }

        TextView title = convertView.findViewById(R.id.option_title);
        title.setText(option.getName());

        return convertView;
    }
}
