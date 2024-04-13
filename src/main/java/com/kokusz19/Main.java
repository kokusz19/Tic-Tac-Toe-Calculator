package com.kokusz19;

import com.kokusz19.model.Direction;
import com.kokusz19.model.Winner;

import java.io.*;
import java.util.*;

public class Main {

    private static String PATH_TO_INPUT = "input1.txt";
    private static Map<Integer, Map<Integer, String>> STATE;
    private static int CONNECTIONS_TO_WIN;
    private static int MAX_ROW;
    private static int MAX_COL;

    public static void main(String[] args) {
        StateLoader.loadState();
        StateHandler.printState();
        WinnerHandler.printWinner(WinnerHandler.findWinner());
    }

    public static class WinnerHandler {
        public static Winner findWinner() {
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
                winner = checkLine(StateHandler.getColumnValues(col), Direction.COLUMN);
                if (winner != Winner.NO_WINNER) {
                    return winner;
                }
            }

            // TODO
            // Check diagonals

            // No winner found
            return Winner.NO_WINNER;
        }

        public static void printWinner(Winner winner) {
            System.out.println("The winner is " + winner);
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

    public static class StateHandler {
        public static void printState() {
            for (int row = 0; row < MAX_ROW; row++) {
                Map<Integer, String> rowMap = STATE.getOrDefault(row, Collections.emptyMap());
                for (int col = 0; col < MAX_COL; col++) {
                    String symbol = rowMap.getOrDefault(col, "-");
                    System.out.print(symbol + " ");
                }
                System.out.println();
            }
        }

        public static Map<Integer, String> getColumnValues(int column) {
            Map<Integer, String> columnValues = new HashMap<>();

            for (Map.Entry<Integer, Map<Integer, String>> row : STATE.entrySet()) {
                if(row.getValue().get(column) != null) {
                    columnValues.put(row.getKey(), row.getValue().get(column));
                }
            }

            return columnValues;
        }
    }

    public static class StateLoader {
        public static void loadState() {
            try (InputStream inputStream = Main.class.getClassLoader().getResourceAsStream(PATH_TO_INPUT);
                 BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

                String line = reader.readLine();
                MAX_ROW = Integer.parseInt(line);

                line = reader.readLine();
                MAX_COL = Integer.parseInt(line);

                line = reader.readLine();
                CONNECTIONS_TO_WIN = Integer.parseInt(line);

                STATE = new HashMap<>();
                int rowNum = 0;
                while ((line = reader.readLine()) != null) {
                    Map<Integer, String> row = new HashMap<>();
                    for (int colNum = 0; colNum < line.length(); colNum++) {
                        String character = line.substring(colNum, colNum+1);
                        if(character.equals("X") || character.equals("O")) {
                            row.put(colNum, character);
                        } else if(!character.equals(" ")) {
                            throw new IllegalArgumentException("The given state contains illegal values. Only possible values are [\"X\", \"O\" or \" \"].");
                        }
                    }
                    STATE.put(rowNum, row);
                    rowNum++;
                }
            } catch (IOException ioException) {
                ioException.printStackTrace();
            } catch (NullPointerException nullPointerException) {
                throw new IllegalArgumentException("File could not be opened!");
            } catch (NumberFormatException numberFormatException) {
                throw new IllegalArgumentException("Number format exception occurred.");
            }
        }
    }
}