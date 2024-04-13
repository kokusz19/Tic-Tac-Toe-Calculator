package com.kokusz19;

import com.kokusz19.model.Direction;
import com.kokusz19.model.Winner;

import java.util.*;

public class Main {

    private static Map<Integer, Map<Integer, String>> STATE;
    private static int CONNECTIONS_TO_WIN;
    private static int MAX_ROW;
    private static int MAX_COL;

    public static void main(String[] args) {
        initGame();
        printState();
        printWinner(findWinner());
    }

    private static void printWinner(Winner winner) {
        System.out.println("\n\nThe winner is " + winner);
    }

    private static void printState() {
        for (int row = 0; row < MAX_ROW; row++) {
            Map<Integer, String> rowMap = STATE.getOrDefault(row, Collections.emptyMap());
            for (int col = 0; col < MAX_COL; col++) {
                String symbol = rowMap.getOrDefault(col, "-");
                System.out.print(symbol + " ");
            }
            System.out.println();
        }
    }

    public static void initGame() {
        // TODO: fix after reading
        CONNECTIONS_TO_WIN = 3;
        STATE = Map.of(
                0, Map.of(0, "O", 2, "O"),
                1, Map.of(0, "O", 1, "X"),
                2, Map.of(0, "X", 1, "X", 2, "X"));
        MAX_ROW = 3;
        MAX_COL = 3;
    }

    private static Winner findWinner() {
        Winner winner;

        // Check rows
        for (int row = 0; row < MAX_ROW; row++) {
            winner = checkLine(STATE.getOrDefault(row, Collections.emptyMap()), Direction.ROW);
            if (winner != Winner.NO_WINNER) {
                return winner;
            }
        }

        // Check columns
        for (int col = 0; col < MAX_COL; col++) {
            winner = checkLine(getColumnValues(col), Direction.COLUMN);
            if (winner != Winner.NO_WINNER) {
                return winner;
            }
        }

        // TODO
        // Check diagonals

        // No winner found
        return Winner.NO_WINNER;
    }

    private static Map<Integer, String> getColumnValues(int column) {
        Map<Integer, String> columnValues = new HashMap<>();

        for (Map.Entry<Integer, Map<Integer, String>> row : STATE.entrySet()) {
            if(row.getValue().get(column) != null) {
                columnValues.put(row.getKey(), row.getValue().get(column));
            }
        }

        return columnValues;
    }

    private static Winner checkLine(Map<Integer, String> line, Direction direction) {
        if (line.isEmpty()) {
            return Winner.NO_WINNER;
        }

        for (Map.Entry<Integer, String> element : line.entrySet()) {
            if(isWinnerPosition(line, element.getKey(), element.getValue())) {
                return Winner.getWinner(element.getValue());
            }
        }

        return Winner.NO_WINNER;
    }

    private static boolean isWinnerPosition(Map<Integer, String> line, int lineElement, String character) {
        return isWinnerPosition(line, lineElement, character, 1);
    }
    private static boolean isWinnerPosition(Map<Integer, String> line, int lineElement, String character, int count) {
        // TODO: check for easier solution
        if (!character.equals(line.get(lineElement+1))) {
            return false;
        }

        count++;
        if (count != CONNECTIONS_TO_WIN) {
            return isWinnerPosition(line, lineElement+1, character, count);
        }

        return true;
    }
}