package com.homer.ahmed.memorygame.data;

public class Option {

    private String name;
    private int length;
    private int width;

    public Option(String name) {
        this.name = name;
        this.length = Integer.parseInt(name.substring(0,1));
        this.width = Integer.parseInt(name.substring(2,3));
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "name: " + name + ", "
                + "length: " + length + ", "
                + "width: " + width + "\n";
    }
}
