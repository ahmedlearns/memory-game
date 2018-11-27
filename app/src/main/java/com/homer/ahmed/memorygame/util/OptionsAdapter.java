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
import com.homer.ahmed.memorygame.data.GridOption;

import java.util.List;

public class OptionsAdapter extends ArrayAdapter<GridOption> {
    public OptionsAdapter(@NonNull Context context, int resource, @NonNull List<GridOption> gridOptions) {
        super(context, resource, gridOptions);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Get the data item for this position
        final GridOption gridOption = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_option, parent, false);
        }

        TextView title = convertView.findViewById(R.id.option_title);
        title.setText(gridOption.getName());

        // pass events to the parent
        title.setOnClickListener(view -> this.gridOptionEventListener.onGridOptionClicked(gridOption));

        return convertView;
    }

    //
    // Event listener for communicating events to parent.
    //
    private GridOptionEventListener gridOptionEventListener;

    public interface GridOptionEventListener {
        void onGridOptionClicked(GridOption option);
    }

    public void setGridOptionEventListener(GridOptionEventListener listener) {
        this.gridOptionEventListener = listener;
    }
}
