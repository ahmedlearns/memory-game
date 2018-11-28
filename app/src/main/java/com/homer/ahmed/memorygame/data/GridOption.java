package com.homer.ahmed.memorygame.data;

import android.os.Parcel;
import android.os.Parcelable;

public class GridOption implements Parcelable {

    private String name;
    private int length;
    private int width;

    public GridOption(String name) {
        this.name = name;
        this.length = Integer.parseInt(name.split("x")[1]);
        this.width = Integer.parseInt(name.split("x")[0]);
    }

    public String getName() {
        return name;
    }

    public int getLength() {
        return length;
    }

    public int getWidth() {
        return width;
    }

    @Override
    public String toString() {
        return "name: " + name + ", "
                + "length: " + length + ", "
                + "width: " + width + "\n";
    }

    protected GridOption(Parcel in) {
        name = in.readString();
        length = in.readInt();
        width = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(length);
        dest.writeInt(width);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<GridOption> CREATOR = new Creator<GridOption>() {
        @Override
        public GridOption createFromParcel(Parcel in) {
            return new GridOption(in);
        }

        @Override
        public GridOption[] newArray(int size) {
            return new GridOption[size];
        }
    };

}
