package com.kokusz19;

import java.util.Collections;
import java.util.Map;

public class Main {

    private static Map<Integer, Map<Integer, Character>> STATE;
    private static int CONNECTIONS_TO_WIN;
    private static int MAX_ROW;
    private static int MAX_COL;

    public static void main(String[] args) {
        initGame();
        printState();
    }

    private static void printState() {
        for (int row = 0; row < MAX_ROW; row++) {
            Map<Integer, Character> rowMap = STATE.getOrDefault(row, Collections.emptyMap());
            for (int col = 0; col < MAX_COL; col++) {
                char symbol = rowMap.getOrDefault(col, '-');
                System.out.print(symbol + " ");
            }
            System.out.println();
        }
    }

    public static void initGame() {
        // TODO: fix after reading
        CONNECTIONS_TO_WIN = 3;
        STATE = Map.of(
                0, Map.of(0, 'O', 2, 'O'),
                1, Map.of(0, 'O', 1, 'X'),
                2, Map.of(0, 'X', 1, 'X', 2, 'X'));
        MAX_ROW = 3;
        MAX_COL = 3;
    }
}