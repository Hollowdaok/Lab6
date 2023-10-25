package com.denys;

import java.io.PrintStream;
import java.util.function.Predicate;
import java.util.stream.IntStream;

public class Table {
    final int height;
    final int width;
    final Object[][] tableData;
    private PrintStream printStream;
    private Predicate<Object> hightlighPredicate;
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    private static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public Table(Object[][] tableData, int height, int width) {
        this.tableData = tableData;
        this.height = height;
        this.width = width;
        printStream = System.out;
        hightlighPredicate = x -> false;
    }
    public void setHighlightPredicate(Predicate<Object> predicate){
        hightlighPredicate = predicate;
    }
    public void setPrintStream(PrintStream printStream){
        this.printStream = printStream;
    }
    public void print() {
        printHeader();
        IntStream.range(0, height).forEach(this::printRow);
        printHeader();
    }
    private void printHeader(){
        StringBuilder builder = new StringBuilder();
        builder.append("\t");
        for(int i = 0; i < width; i++) {
            builder.append(" ");
            builder.append(i);
            builder.append("\t");
        }
        printStream.println(builder);
    }
    private void printRow(int row){
        StringBuilder builder = new StringBuilder();
        builder.append(" ");
        builder.append(row);
        builder.append(" |");

        for (int i = 0; i < width; i++){
            builder.append(formatCell(tableData[row][i]));
        }

        builder.append(" | ");
        builder.append(row);

        printStream.println(builder);
    }
    private String formatCell(Object data) {
        StringBuilder builder = new StringBuilder();

        if(hightlighPredicate.test(data)) {
            builder.append(ANSI_YELLOW_BACKGROUND);
            builder.append(ANSI_RED);
        }
        else {
            builder.append(ANSI_BLACK_BACKGROUND);
            builder.append(ANSI_GREEN);
        }

        builder.append("  ");
        builder.append(data);
        builder.append("\t");
        builder.append(ANSI_RESET);
        return builder.toString();
    }
}
