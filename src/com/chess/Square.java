package com.chess;

import com.chess.Pieces.*;

public class Square{
    private boolean occupied;
    private Piece piece;

    public Square(){
        occupied=false;
        piece=null;
    }

    public Square(Piece p){
        piece=p;
        occupied=true;
    }

    public Piece getPiece(){return piece;}

    public void setPiece(Piece piece){
        this.occupied=true;
        this.piece=piece;
    }

    public boolean isOccupied(){ return occupied;}

    public void Unoccupy(){occupied=false;}

    public void Occupy(){occupied=true;}
}
