package com.homer.ahmed.memorygame.data;

import android.os.Parcel;
import android.os.Parcelable;

public class Card implements Parcelable {

    public enum Type {
        COW, HEN, HORSE, PIG, BAT, CAT, GHOST_DOG, SPIDER;
    }

    private boolean matched = false;
    private boolean flipped = false;
    private Type type;

    public Card(Type type) {
        this.type = type;
    }

    public boolean isMatched() {
        return matched;
    }

    public void setMatched(boolean matched) {
        this.matched = matched;
    }

    public boolean isFlipped() {
        return flipped;
    }

    public void setFlipped(boolean flipped) {
        this.flipped = flipped;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    /**
     * Check if two flipped cards match based on type.
     *
     * Can potentially be abstracted away from this class into presenter.
     * Not using the equals() method since that is used to determine if two cards are the same card.
     *
     * @param card Card to compare to
     * @return True if two cards match
     */
    public boolean matches(Card card) {
        return type.equals(card.getType()) && flipped == card.isFlipped();
    }

    //
    // Parcelable methods
    //

    protected Card(Parcel in) {
        matched = in.readByte() != 0;
        flipped = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (matched ? 1 : 0));
        dest.writeByte((byte) (flipped ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Card> CREATOR = new Creator<Card>() {
        @Override
        public Card createFromParcel(Parcel in) {
            return new Card(in);
        }

        @Override
        public Card[] newArray(int size) {
            return new Card[size];
        }
    };
}
