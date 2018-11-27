package com.homer.ahmed.memorygame.data;

import java.util.Random;

public class Card {

    public enum Type {
        COW, HEN, HORSE, PIG, BAT, CAT, GHOST_DOG, SPIDER;

        private static final Random RANDOM = new Random();

        public static Type randomLetter()  {
            Type[] values = values();
            return values[RANDOM.nextInt(values.length)];
        }
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

    public boolean matches(Card card) {
        return type.equals(card.getType()) && flipped == card.isFlipped();
    }
}
