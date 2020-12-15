package com.chess.Pieces;

import java.util.ArrayList;
import com.chess.*;

public abstract class Piece{

    public int rank, file, gridVal, value;
    public Color color;
    public boolean taken, moved;
    public ArrayList<Move> availMoves, availCaptures, availCheck;

    public Piece(int r, int f, Color c ){
        rank=r;
        file=f;
        color=c;

        taken=false;
        moved=false;
        availMoves=new ArrayList<>();
        availCaptures=new ArrayList<>();
        availCheck=new ArrayList<>();
    }

    public void move(int r, int f){
        rank=r;
        file=f;
        if(!moved) moved=true;
    }

    public void undo(int r, int f, boolean fm){
        rank=r;
        file=f;
        if(fm) moved=false;
    }

    public abstract void possibleMoves(Board board);

    public abstract int[][] getGrid();

    public void printGrid(int[][] grid){
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                System.out.printf("%4s",grid[i][j]+"|");
            }
            System.out.println();
        }
    }

    public int[][] reverseGrid(int[][] grid){
        int[][] res=new int[grid.length][grid[0].length];
        int r=0, f=0;
        for (int i = grid.length-1; i > -1; i--) {
            for (int j = grid[0].length-1; j > -1; j--) {
                res[r][f]=grid[i][j];
                f++;
            }
            r++;
            f=0;
        }
        return res;
    }

    public boolean valid(int c, int r){
        if(c<0 || c>7 || r<0 || r>7) return false;

        return true;
    }

    public int getValue(){return value;}

    public Color getColor(){ return color;}

    public ArrayList<Move> getCheck(){return availCheck;}

    public ArrayList<Move> getCaptures(){return availCaptures;}

    public ArrayList<Move> getMoves(){
        ArrayList<Move> res=new ArrayList<>();
        res.addAll(availCheck);
        res.addAll(availCaptures);
        res.addAll(availMoves);
        return res;
    }

    public boolean hasMoved(){return moved;}

    public void setMoved(boolean m){moved=m;}

    public abstract String getString();
}
