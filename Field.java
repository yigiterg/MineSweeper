package minesweeper;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Field {
    private final Scanner scanner = new Scanner(System.in);
    private final Random random = new Random();
    private int mineNumber;
    private final Cell[][] field = new Cell[9][9];
    private int mineController = 0;
    private int wrongChoice = 0;
    private int mineSize = 0;
    char mine = 'X';
    char notMine = '.';

    public Field(int n) {
        this.mineSize = n;
        this.mineNumber = n;
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                field[i][j] = new Cell();
            }
        }
        setMines();
        setNeighbours();
    }

    public void setMines() {
        while (mineNumber > 0) {
            int row = random.nextInt(9);
            int column = random.nextInt(9);
            if (!field[row][column].getMine()) {
                field[row][column].setMine(true);
                mineNumber--;
            }
        }
    }

    public void displayMines() {
        System.out.print("\n │123456789│\n—│—————————│\n");
        int i = 1;
        for (Cell[] booleans : field) {
            System.out.printf("%d│", i);
            for (Cell aBoolean : booleans) {
                if (aBoolean.getMine()) {
                    System.out.print("X");
                } else if (aBoolean.getFree() && aBoolean.getMineSize() == 0) {
                    System.out.print("/");
                } else if (aBoolean.getMineSize() != 0 && aBoolean.getFree()) {
                    System.out.print(aBoolean.getMineSize());
                } else {
                    System.out.print(".");
                }
            }
            i++;
            System.out.print("│\n");
        }
        System.out.println("—│—————————│");
    }

    public void displayField() {
        System.out.print("\n │123456789│\n—│—————————│\n");
        int i = 1;
        for (Cell[] booleans : field) {
            System.out.printf("%d│",i);
            for (Cell aBoolean : booleans) {
                if(!aBoolean.getMark() && !aBoolean.getFree()) {
                    System.out.print(".");
                } else if (aBoolean.getMark()) {
                    System.out.print("*");
                } else if (aBoolean.getMineSize() == 0 && aBoolean.getFree() && !aBoolean.getMark()) {
                    System.out.print("/");
                } else if (aBoolean.getMineSize() != 0 && aBoolean.getFree() && !aBoolean.getMark()) {
                    System.out.print(aBoolean.getMineSize());
                }
            }
            i++;
            System.out.print("│\n");
        }
        System.out.println("—│—————————│");
    }

    public void gamePlay() {
        int x,y;
        while(true) {
            String xCoordinate,yCoordinate;
            System.out.print("Set/unset mines marks or " +
                    "claim a cell as free: ");
            xCoordinate = scanner.next();
            yCoordinate = scanner.next();
            String string = scanner.next();
            x = Integer.parseInt(xCoordinate);
            y = Integer.parseInt(yCoordinate);
            if(string.equals("free")) {
                if(!markFree(x,y)) {
                    displayMines();
                    System.out.println("You stepped on a mine and failed!");
                    break;
                }
            } else if(string.equals("mine")) {
                markMine(x,y);
            }
            if(this.mineController == mineSize && wrongChoice == 0) {
                System.out.print("Congratulations! You found all mines!\n");
                break;
            } else {
                displayField();
            }
        }

    }
    public boolean markFree(int y, int x) {
        if(field[x-1][y-1].getMine()) {
            return false;
        }
        field[x-1][y-1].setFree();
        return true;
    }

    public void markMine(int y, int x) {
        if(field[x-1][y-1].getMine() && !field[x-1][y-1].getMark()) {
            field[x-1][y-1].setMark();
            mineController++;
        } else if (!field[x-1][y-1].getMine() && !field[x-1][y-1].getMark()) {
            field[x-1][y-1].setMark();
            wrongChoice++;
        } else if (field[x-1][y-1].getMine() && field[x-1][y-1].getMark()) {
            field[x-1][y-1].deleteMark();
            mineController--;
        } else if (!field[x-1][y-1].getMine() && field[x-1][y-1].getMark() ) {
            field[x-1][y-1].deleteMark();
            wrongChoice--;
        } else {
            System.out.println("There is a number here!");
        }
    }

    public void setNeighbours() {
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                setNorth(i, j);
                setSouth(i, j);
                setWest(i, j);
                setEast(i, j);
                setNorthWest(i, j);
                setNorthEast(i, j);
                setSouthWest(i, j);
                setSouthEast(i, j);
            }
        }
    }

    public void setNorth(int i, int j) {
        if (i != 0) {
            field[i][j].addNeighbour(field[i - 1][j]);
        }
    }

    public void setSouth(int i, int j) {
        if (i != field.length - 1) {
            field[i][j].addNeighbour(field[i + 1][j]);
        }
    }

    public void setWest(int i, int j) {
        if (j != 0) {
            field[i][j].addNeighbour(field[i][j - 1]);
        }
    }

    public void setEast(int i, int j) {
        if (j != field.length - 1) {
            field[i][j].addNeighbour(field[i][j + 1]);
        }
    }

    public void setNorthWest(int i, int j) {
        if (j != 0 && i != 0) {
            field[i][j].addNeighbour(field[i - 1][j - 1]);
        }
    }

    public void setNorthEast(int i, int j) {
        if (j != field.length - 1 && i != 0) {
            field[i][j].addNeighbour(field[i - 1][j + 1]);
        }
    }

    public void setSouthWest(int i, int j) {
        if (j != 0 && i != field.length - 1) {
            field[i][j].addNeighbour(field[i + 1][j - 1]);
        }
    }

    public void setSouthEast(int i, int j) {
        if (j != field.length - 1 && i != field.length - 1) {
            field[i][j].addNeighbour(field[i + 1][j + 1]);
        }
    }

    public void printNeighbourNumber() {
        for (Cell[] cellArr : field) {
            for (Cell cell : cellArr) {
                if (cell.getMine()) {
                    System.out.print(cell.getMineSize());
                }
                System.out.println();
            }
        }
    }
}
