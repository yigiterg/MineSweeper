package minesweeper;

import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("How many mines do you want on the field? ");
        int n = scanner.nextInt();
        Field field = new Field(n);
        field.displayField();
       field.gamePlay();
    }
}
