package com.kokusz19.model;

public enum InputFiles {
    SIMPLE_GAME("input1.txt"),
    COMPLEX_GAME("input2.txt"),
    NO_WINNER("input3.txt"),
    ILLEGAL_ARGUMENTS("illegalArguments.txt"),
    NUMBER_FORMAT_EXCEPTION("numberFormatException.txt"),
    WRONG_CONNECTION_TO_WIN_COUNT("wrongConnectionToWinCount.txt"),
    NON_EXISTING("nonExistingFile.txt");

    private String path;

    InputFiles(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
