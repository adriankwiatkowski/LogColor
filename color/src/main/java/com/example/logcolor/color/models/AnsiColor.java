package com.example.logcolor.color.models;

public enum AnsiColor {
    ANSI_RESET("\u001B[0m", "", ""),
    ANSI_BLACK("\u001B[30m", "Black", "#000000"),
    ANSI_RED("\u001B[31m", "Red", "#FF0000"),
    ANSI_GREEN("\u001B[32m", "Green", "#008000"),
    ANSI_YELLOW("\u001B[33m", "Yellow", "#FFFF00"),
    ANSI_BLUE("\u001B[34m", "Blue", "#0000FF"),
    ANSI_PURPLE("\u001B[35m", "Purple", "#800080"),
    ANSI_CYAN("\u001B[36m", "Cyan", "#00FFFF"),
    ANSI_WHITE("\u001B[37m", "White", "#FFFFFF"),
    ANSI_BRIGHT_BLACK("\u001B[90m", "DarkGrey", "#A9A9A9"),
    ANSI_BRIGHT_RED("\u001B[91m", "OrangeRed", "#FF4500"),
    ANSI_BRIGHT_GREEN("\u001B[92m", "GreenYellow", "#ADFF2F"),
    ANSI_BRIGHT_YELLOW("\u001B[93m", "Gold", "#FFD700"),
    ANSI_BRIGHT_BLUE("\u001B[94m", "LightSkyBlue", "#87CEFA"),
    ANSI_BRIGHT_PURPLE("\u001B[95m", "MediumPurple", "#9370DB"),
    ANSI_BRIGHT_CYAN("\u001B[96m", "LightCyan", "#E0FFFF"),
    ANSI_BRIGHT_WHITE("\u001B[97m", "LightGrey", "#D3D3D3"),
    ANSI_BG_BLACK("\u001B[40m", "Black", "#000000"),
    ANSI_BG_RED("\u001B[41m", "Red", "#FF0000"),
    ANSI_BG_GREEN("\u001B[42m", "Green", "#008000"),
    ANSI_BG_YELLOW("\u001B[43m", "Yellow", "#FFFF00"),
    ANSI_BG_BLUE("\u001B[44m", "Blue", "#0000FF"),
    ANSI_BG_PURPLE("\u001B[45m", "Purple", "#800080"),
    ANSI_BG_CYAN("\u001B[46m", "Cyan", "#00FFFF"),
    ANSI_BG_WHITE("\u001B[47m", "White", "#FFFFFF"),
    ANSI_BRIGHT_BG_BLACK("\u001B[100m", "DarkGrey", "#A9A9A9"),
    ANSI_BRIGHT_BG_RED("\u001B[101m", "OrangeRed", "#FF4500"),
    ANSI_BRIGHT_BG_GREEN("\u001B[102m", "GreenYellow", "#ADFF2F"),
    ANSI_BRIGHT_BG_YELLOW("\u001B[103m", "Gold", "#FFD700"),
    ANSI_BRIGHT_BG_BLUE("\u001B[104m", "LightSkyBlue", "#87CEFA"),
    ANSI_BRIGHT_BG_PURPLE("\u001B[105m", "MediumPurple", "#9370DB"),
    ANSI_BRIGHT_BG_CYAN("\u001B[106m", "LightCyan", "#E0FFFF"),
    ANSI_BRIGHT_BG_WHITE("\u001B[107m", "LightGrey", "#D3D3D3");

    public static final AnsiColor[] FOREGROUNDS = {
            ANSI_BLACK, ANSI_RED, ANSI_GREEN, ANSI_YELLOW,
            ANSI_BLUE, ANSI_PURPLE, ANSI_CYAN, ANSI_WHITE,
            ANSI_BRIGHT_BLACK, ANSI_BRIGHT_RED, ANSI_BRIGHT_GREEN, ANSI_BRIGHT_YELLOW,
            ANSI_BRIGHT_BLUE, ANSI_BRIGHT_PURPLE, ANSI_BRIGHT_CYAN, ANSI_BRIGHT_WHITE
    };

    public static final AnsiColor[] BACKGROUNDS = {
            ANSI_BG_BLACK, ANSI_BG_RED, ANSI_BG_GREEN, ANSI_BG_YELLOW,
            ANSI_BG_BLUE, ANSI_BG_PURPLE, ANSI_BG_CYAN, ANSI_BG_WHITE,
            ANSI_BRIGHT_BG_BLACK, ANSI_BRIGHT_BG_RED, ANSI_BRIGHT_BG_GREEN, ANSI_BRIGHT_BG_YELLOW,
            ANSI_BRIGHT_BG_BLUE, ANSI_BRIGHT_BG_PURPLE, ANSI_BRIGHT_BG_CYAN, ANSI_BRIGHT_BG_WHITE
    };

    private final String ansi;

    private final String html;

    private final String hex;

    public String getAnsi() {
        return ansi;
    }

    public String getHtml() {
        return html;
    }

    public String getHex() {
        return hex;
    }

    AnsiColor(String ansi, String html, String hex) {
        this.ansi = ansi;
        this.html = html;
        this.hex = hex;
    }
}
