package com.kokusz19.model;

import java.util.Arrays;

public enum Winner {
    PLAYER_A("X"),
    PLAYER_B("O"),
    NO_WINNER;

    private boolean winner;
    private String mark;

    Winner(){
        this.mark = null;
    }
    Winner(String mark) {
        this.mark = mark;
        this.winner = true;
    }

    public String getMark() {
        return mark;
    }

    public static Winner getWinner(String mark) {
        return Arrays.stream(Winner.values())
                .filter(winner -> winner.mark.equals(mark))
                .findAny()
                .orElse(NO_WINNER);
    }

    @Override
    public String toString() {
        return this.winner
                ? String.format("%s (mark=%s)", this.name(), this.mark)
                : this.name();
    }
}
