# Tic-Tac-Toe Calculator

### Description
This program was made for calculating the result of a specific state of a Tic-Tac-Toe game.

## Input
The input (game state) is read from a specified file, placed in the `resources` folder.
The structure of the file is the following:
- Count of rows
- Count of columns
- Count of neighbouring symbols considered a win
- State of the game, where the symbols are
    - Player A: "X"
    - Player B: "O"
    - Empty cell: " "

An example input can be found in the resources folder
```text
5
6
3
O O
OX
XXX
```

### Running the program
To run the program run `gradlew run` or to run the unit tests run `gradlew test`