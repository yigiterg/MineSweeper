package minesweeper;

import java.util.ArrayList;

public class Cell {

    private boolean isMine = false;
    private ArrayList <Cell> neighbour = new ArrayList<>();
    private int mineSize = 0;
    private boolean isMarked = false;
    private boolean isFree = false;
    public Cell () {

    }

    public void setMine(boolean bool) {

        isMine = bool;
    }
    public boolean getMine() {
        return isMine;
    }

    public boolean getMark() {
        return isMarked;
    }

    public void setMark() {
        this.isMarked = true;
    }

    public void setFree () {
        isFree = true;
        if(this.getMineSize() == 0) {
            this.neighbourSet();
        }
    }
    public boolean getFree() {
        return isFree;
    }

    public void deleteMark() {
        this.isMarked = false;
    }

    public void addNeighbour(Cell cell) {
        neighbour.add(cell);
        if(cell.getMine()) {
            mineSize++;
        }
    }

    public int getMineSize() {
        return mineSize;
    }

    public void neighbourSet() {
        for (Cell cell : neighbour) {
            if(!cell.getFree() && cell.getMineSize() == 0) {
                cell.setFree();
                cell.neighbourSet();
            } else if(!cell.getFree() && !cell.getMine()) {
                cell.setFree();
            }
            if(cell.getMark() && !cell.isMine && this.getMineSize() == 0) {
                cell.deleteMark();
                cell.setFree();
            }
        }
    }

}

