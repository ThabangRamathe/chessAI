package com.chess.Pieces;

import com.chess.*;
import java.util.ArrayList;

public class Pawn extends Piece{
    public int[][] pieceEval={
        {0,0,0,0,0,0,0,0},
        {50,50,50,50,50,50,50,50},
        {10,10,20,30,30,20,10,10},
        {5,5,10,25,25,10,5,5},
        {0,0,0,20,30,0,0,0},
        {5,-5,-10,-5,-5,-10,-5,5},
        {5,5,10,-20,-20,10,5,5},
        {0,0,0,0,0,0,0,0},
    };

    public Pawn(int r, int f, Color c) {
        super(r, f, c);
        
        value=10;
        if(c==Color.WHITE){pieceEval= reverseGrid(pieceEval);}
    }

    @Override
    public void possibleMoves(Board board) {
        availCaptures=new ArrayList<>();
        availMoves=new ArrayList<>();
        availCheck=new ArrayList<>();
        //if piece pinned to king
        // if(board.isPinned(rank, file, color)){
        //     ArrayList<Square> threats=board.getThreats(rank, file, color);
        //     if(threats.size()>1) return;

        //     int i=threats.get(0).getPiece().rank, j=threats.get(0).getPiece().file;
        //     if(i==rank+1 && (j==file+1 || j==file-1)) availCaptures.add(new Move(rank, file,i,j));
        //     return;
        // }
        
        if(color==Color.WHITE){
            if(!hasMoved()){
                if(board.getSquare(rank+1, file).getPiece()==null){
                    availMoves.add(new Move(rank, file, rank+1, file));
                    if(board.getSquare(rank+2, file).getPiece()==null){
                        availMoves.add(new Move(rank, file, rank+2, file));
                    }
                }
            }
            else if(valid(rank+1,file) && board.getSquare(rank+1, file).getPiece()==null){
                if(rank+1==7) availMoves.add(new Move(rank, file, rank+1, file, true, false));
                else availMoves.add(new Move(rank, file, rank+1, file));
            }
            
            if(valid(rank+1,file+1) && board.getSquare(rank+1, file+1).getPiece()!=null && board.getSquare(rank+1, file+1).getPiece().getColor()!=color){
                if(board.getSquare(rank+1, file+1).getPiece().getValue()==200) availCheck.add(new Move(rank, file, rank+1, file+1));
                else if(rank+1==7) availCaptures.add(new Move(rank, file, rank+1, file+1, true, true));
                else availCaptures.add(new Move(rank, file, rank+1, file+1,false,true));
            }
            if(valid(rank+1,file-1) && board.getSquare(rank+1, file-1).getPiece()!=null && board.getSquare(rank+1, file-1).getPiece().getColor()!=color){
                if(board.getSquare(rank+1, file-1).getPiece().getValue()==200) availCheck.add(new Move(rank, file, rank+1, file+1));
                else if(rank+1==7) availCaptures.add(new Move(rank, file, rank+1, file-1, true,true));
                else availCaptures.add(new Move(rank, file, rank+1, file-1,false,true));
            }
        }
        else if(color==Color.BLACK){
            if(!hasMoved()){
                availMoves.add(new Move(rank, file, rank-1, file));
                availMoves.add(new Move(rank, file, rank-2, file));
            }
            else if(valid(rank-1,file) && board.getSquare(rank-1, file).getPiece()==null){
                if(rank-1==0) availMoves.add(new Move(rank, file, rank-1, file, true,false));
                else availMoves.add(new Move(rank, file, rank-1, file));
            }
            
            if(valid(rank-1,file+1) && board.getSquare(rank-1, file+1).getPiece()!=null && board.getSquare(rank-1, file+1).getPiece().getColor()!=color){
                if(board.getSquare(rank-1, file+1).getPiece().getValue()==200) availCheck.add(new Move(rank, file, rank+1, file+1));
                else if(rank-1==0) availCaptures.add(new Move(rank, file, rank-1, file+1, true, true));
                else availCaptures.add(new Move(rank, file, rank-1, file+1,false,true));
            }
            if(valid(rank-1,file-1) && board.getSquare(rank-1, file-1).getPiece()!=null && board.getSquare(rank-1, file-1).getPiece().getColor()!=color){
                if(board.getSquare(rank-1, file-1).getPiece().getValue()==200) availCheck.add(new Move(rank, file, rank+1, file+1));
                else if(rank-1==0) availCaptures.add(new Move(rank, file, rank-1, file-1, true, true));
                else availCaptures.add(new Move(rank, file, rank-1, file-1,false,true));
            }
        }
    }

    public String getString(){
        if(color==Color.BLACK) return "p";
        return "P";
    }

    public int[][] getGrid(){return pieceEval;}
}