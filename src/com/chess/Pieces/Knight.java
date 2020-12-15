package com.chess.Pieces;

import com.chess.*;
import java.util.ArrayList;;

public class Knight extends Piece{
    public int[][] pieceEval={
        {-50,-40,-30,-30,-30,-30,-40,-50},
        {-40,-20,0,0,0,0,-20,-40},
        {-30,0,10,15,15,10,0,-30},
        {-30,5,15,20,20,15,5,-30},
        {-30,0,15,20,20,15,0,-30},
        {-30,5,10,15,15,15,10,5,-30},
        {-40,-20,0,5,5,0,-20,-40},
        {-50,-40,-30,-30,-30,-30,-40,-50}
    };

    public Knight(int r, int f, Color c) {
        super(r, f, c);

        value=32;
        if(c==Color.WHITE){pieceEval= reverseGrid(pieceEval);}
    }

    @Override
    public void possibleMoves(Board board) {
        availCaptures=new ArrayList<>();
        availMoves=new ArrayList<>();
        availCheck=new ArrayList<>();

        ArrayList<Square> threats=board.getThreats(rank, file, color);
        if(threats.size()!=0) return;
    
        if(valid(rank+2, file+1) && board.getSquare(rank+2, file+1).getPiece()==null){
            availMoves.add(new Move(rank, file,rank+2,file+1));
        }
        else if(valid(rank+2, file+1) && board.getSquare(rank+2, file+1).getPiece().getColor()!=color){
            if(board.getSquare(rank+2, file+1).getPiece().getValue()==200) availCheck.add(new Move(rank, file, rank+2, file+1));
            else availCaptures.add(new Move(rank, file,rank+2,file+1,false,true));
        }

        if(valid(rank+2, file-1) && board.getSquare(rank+2, file-1).getPiece()==null){
            availMoves.add(new Move(rank, file,rank+2,file-1));
        }
        else if(valid(rank+2, file-1) && board.getSquare(rank+2, file-1).getPiece().getColor()!=color){
            if(board.getSquare(rank+2, file-1).getPiece().getValue()==200) availCheck.add(new Move(rank, file, rank+2, file-1));
            else availCaptures.add(new Move(rank, file,rank+2,file-1,false,true));
        }

        if(valid(rank-2, file+1) && board.getSquare(rank-2, file+1).getPiece()==null){
            availMoves.add(new Move(rank, file,rank-2,file+1));
        }
        else if(valid(rank-2, file+1) && board.getSquare(rank-2, file+1).getPiece().getColor()!=color){
            if(board.getSquare(rank-2, file+1).getPiece().getValue()==200) availCheck.add(new Move(rank, file, rank-2, file+1));
            else availCaptures.add(new Move(rank, file,rank-2,file+1,false,true));
        }

        if(valid(rank-2, file-1) && board.getSquare(rank-2, file-1).getPiece()==null){
            availMoves.add(new Move(rank, file,rank-2,file-1));
        }
        else if(valid(rank-2, file-1) && board.getSquare(rank-2, file-1).getPiece().getColor()!=color){
            if(board.getSquare(rank-2, file-1).getPiece().getValue()==200) availCheck.add(new Move(rank, file, rank-2, file-1));
            else availCaptures.add(new Move(rank, file,rank-2,file-1,false,true));
        }

        if(valid(rank+1, file+2) && board.getSquare(rank+1, file+2).getPiece()==null){
            availMoves.add(new Move(rank, file,rank+1,file+2));
        }
        else if(valid(rank+1, file+2) && board.getSquare(rank+1, file+2).getPiece().getColor()!=color){
            if(board.getSquare(rank+1, file+2).getPiece().getValue()==200) availCheck.add(new Move(rank, file, rank+1, file+2));
            else availCaptures.add(new Move(rank, file,rank+1,file+2,false,true));
        }

        if(valid(rank-1, file+2) && board.getSquare(rank-1, file+2).getPiece()==null){
            availMoves.add(new Move(rank, file,rank-1,file+2));
        }
        else if(valid(rank-1, file+2) && board.getSquare(rank-1, file+2).getPiece().getColor()!=color){
            if(board.getSquare(rank-1, file+2).getPiece().getValue()==200) availCheck.add(new Move(rank, file, rank-1, file+2));
            else availCaptures.add(new Move(rank, file,rank-1,file+2,false,true));
        }

        if(valid(rank-1, file-2) && board.getSquare(rank-1, file-2).getPiece()==null){
            availMoves.add(new Move(rank, file,rank-1,file-2));
        }
        else if(valid(rank-1, file-2) && board.getSquare(rank-1, file-2).getPiece().getColor()!=color){
            if(board.getSquare(rank-1, file-2).getPiece().getValue()==200) availCheck.add(new Move(rank, file, rank-1, file-2));
            else availCaptures.add(new Move(rank, file,rank-1,file-2,false,true));
        }

        if(valid(rank+1, file-2) && board.getSquare(rank+1, file-2).getPiece()==null){
            availMoves.add(new Move(rank, file,rank+1,file-2));
        }
        else if(valid(rank+1, file-2) && board.getSquare(rank+1, file-2).getPiece().getColor()!=color){
            if(board.getSquare(rank+1, file-2).getPiece().getValue()==200) availCheck.add(new Move(rank, file, rank+1, file-2));
            else availCaptures.add(new Move(rank, file,rank+1,file-2,false,true));
        }
    }

    public String getString(){
        if(color==Color.BLACK) return "n";
        return "N";
    }

    public int[][] getGrid(){return pieceEval;}
}