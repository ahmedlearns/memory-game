package com.homer.ahmed.memorygame.util;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.homer.ahmed.memorygame.R;
import com.homer.ahmed.memorygame.data.Card;

import java.util.List;

public class ImageAdapter extends BaseAdapter {
    private static final String TAG = ImageAdapter.class.getSimpleName();

    private Context context;
    private List<Card> cards;

    public ImageAdapter(Context context, List<Card> cards) {
        this.context = context;
        this.cards = cards;
    }

    //
    // BaseAdapter methods
    //

    @Override
    public int getCount() {
        if (null == cards) {
            Log.e(TAG, "getCount: cards not set");
            return 0;
        }
        return cards.size();
    }


    // create a new ImageView for each item referenced by the Adapter
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        Card card = cards.get(position);
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(context);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(200, 200));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            int padding = 10;
            imageView.setPadding(padding, padding, padding, padding);
        } else {
            imageView = (ImageView) convertView;
        }

        Integer imageResource = getImageResource(card.getType());
        imageView.setImageResource(imageResource);
        return imageView;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    //
    // Helper Methods
    //

    private Integer getImageResource(Card.Type type) {
        switch (type) {
            case BAT:
                return R.drawable.bat;
            case CAT:
                return R.drawable.cat;
            case COW:
                return R.drawable.cow;
            case GHOST_DOG:
                return R.drawable.ghost_dog;
            case HEN:
                return R.drawable.hen;
            case HORSE:
                return R.drawable.horse;
            case PIG:
                return R.drawable.pig;
            case SPIDER:
                return R.drawable.spider;
        }
        return R.drawable.card_back;
    }
}
