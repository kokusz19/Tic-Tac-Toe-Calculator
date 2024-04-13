package com.kokusz19;

import com.kokusz19.model.Winner;

import java.io.*;
import java.util.*;

public class Main {

    private static String PATH_TO_INPUT = "input2.txt";
    private static Map<Integer, Map<Integer, String>> STATE;
    private static int CONNECTIONS_TO_WIN;
    private static int MAX_ROW;
    private static int MAX_COL;

    public static void main(String[] args) {
        StateLoader.loadState(PATH_TO_INPUT);
        StateHandler.printState();
        WinnerHandler.printWinner(WinnerHandler.findWinner());
    }

    public static class WinnerHandler {
        public static Winner findWinner() {
            Winner winner;

            int checkedColumns = 0;
            for (int row = 0; row < MAX_ROW; row++) {
                for (int col = 0; col < MAX_COL; col++) {

                    // Check columns
                    // - enough to check them only once
                    if(checkedColumns < MAX_COL) {
                        checkedColumns++;
                        winner = checkRow(StateHandler.getColumnValuesAsRow(col));
                        if (winner != Winner.NO_WINNER) {
                            return winner;
                        }
                    }

                    // Check diagonals
                    // - it's enough to check each diag once, hence the diagonals starting from the border cells are enough
                    // - the last rows can be skipped, as there won't be any space left to get the necessary connections to win
                    if ((row == 0 || col == 0 || col == MAX_COL-1) && row+CONNECTIONS_TO_WIN <= MAX_ROW) { /** KERET minusz utolsó sor*/
                        // Check diag (lower right \)
                        // - the last columns can be skipped, just as the last rows previously
                        if (col+CONNECTIONS_TO_WIN <= MAX_COL) {
                            winner = checkRow(StateHandler.getDiagonalValuesAsRow(row, col, false));
                            if (winner != Winner.NO_WINNER) {
                                return winner;
                            }
                        }

                        // Check diag (lower left /)
                        // - the first columns can be skipped, just as the last rows previously
                        if (col-CONNECTIONS_TO_WIN+1 >= 0) {
                            winner = checkRow(StateHandler.getDiagonalValuesAsRow(row, col, true));
                            if (winner != Winner.NO_WINNER) {
                                return winner;
                            }
                        }
                    }
                }

                // Check rows
                // - enough to check them only once
                winner = checkRow(STATE.getOrDefault(row, Collections.emptyMap()));
                if (winner != Winner.NO_WINNER) {
                    return winner;
                }
            }

            // No winner found
            return Winner.NO_WINNER;
        }

        public static void printWinner(Winner winner) {
            System.out.println("The winner is " + winner);
        }

        /**
         * Checks if there are any winning positions in the given row
         * @param row
         * @return <code>Winner.PLAYER_A</code> if "X" wins, <code>Winner.PLAYER_B</code> if "O" wins, <code>Winner.NO_WINNER</code> if there are no winning positions
         */
        private static Winner checkRow(Map<Integer, String> row) {
            if (row.isEmpty()) {
                return Winner.NO_WINNER;
            }

            for (Map.Entry<Integer, String> element : row.entrySet()) {
                if(isWinnerPosition(row, element.getKey(), element.getValue())) {
                    return Winner.getWinner(element.getValue());
                }
            }

            return Winner.NO_WINNER;
        }

        /**
         * @param line
         * @param lineElement
         * @param character
         * @return <code>true</code> if the given line contains a winner position or not starting from the given character, else <code>false</code>
         */
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

        public static Map<Integer, String> getColumnValuesAsRow(int column) {
            Map<Integer, String> columnValues = new HashMap<>();

            for (Map.Entry<Integer, Map<Integer, String>> row : STATE.entrySet()) {
                if (row.getValue().get(column) != null) {
                    columnValues.put(row.getKey(), row.getValue().get(column));
                }
            }

            return columnValues;
        }

        /**
         * @param row
         * @param column
         * @param isLowerLeftDiagonal <code>true</code> if lower left diagonal, <code>false</code> if lower right diagonal
         * @return the values in the given diagonal as a row
         */
        public static Map<Integer, String> getDiagonalValuesAsRow(int row, int column, boolean isLowerLeftDiagonal) {
            Map<Integer, String> columnValues = new HashMap<>();

            for (int i = 0; isLowerLeftDiagonal ? row+i < MAX_ROW && column-i >= 0 : row+i < MAX_ROW && column+i < MAX_COL; i++) {
                int finalI = i;
                String nextCharacter = Optional.ofNullable(STATE.get(row + finalI)).map(currentRow -> currentRow.get(isLowerLeftDiagonal ? column - finalI : column + finalI)).orElse(null);
                if(nextCharacter != null) {
                    columnValues.put(i, nextCharacter);
                }
            }

            return columnValues;
        }
    }

    public static class StateLoader {
        /**
         * Loads the content of the file specified by <code>PATH_TO_INPUT</code>
         */
        public static void loadState(String pathToInput) {
            try (InputStream inputStream = Main.class.getClassLoader().getResourceAsStream(pathToInput);
                 BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

                String line = reader.readLine();
                MAX_ROW = Integer.parseInt(line);

                line = reader.readLine();
                MAX_COL = Integer.parseInt(line);

                line = reader.readLine();
                CONNECTIONS_TO_WIN = Integer.parseInt(line);

                if (CONNECTIONS_TO_WIN > MAX_COL && CONNECTIONS_TO_WIN > MAX_ROW) {
                    throw new IllegalArgumentException("Can't have a higher connection to win count, than available spaces on the board!");
                }

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