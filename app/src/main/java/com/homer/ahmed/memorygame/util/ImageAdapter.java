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
    private static final Integer CARD_BACK = R.drawable.card_back;
    private static final int CARD_LENGTH = 422;
    private static final int CARD_WIDTH = 294;
    private static final double CARD_SCALE = 0.65;


    private Context context;
    private List<Card> cards;

    public ImageAdapter(Context context) {
        this.context = context;
    }

    public void setCards(List<Card> cards) {
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


    // Create an ImageView for each item referenced by the Adapter
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Cards needs to be set before generating images for each card.
        if (null == cards) {
            Log.e(TAG, "getView: Cards not set");
            return convertView;
        }

        ImageView imageView;
        Card card = cards.get(position);
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(context);
            int width = (int) (CARD_WIDTH * CARD_SCALE);
            int length = (int) (CARD_LENGTH * CARD_SCALE);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(width, length));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            int padding = 6;
            imageView.setPadding(padding, padding, 0, 0);
        } else {
            imageView = (ImageView) convertView;
        }

        Integer imageResource = getImageResource(card.getType());
        if (card.isFlipped()) {
            imageView.setImageResource(imageResource);
        } else {
            imageView.setImageResource(CARD_BACK);
        }
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
