package com.chess.Pieces;

import com.chess.*;
import java.util.ArrayList;

public class Bishop extends Piece{
    public int[][] pieceEval={
        {-20,-10,-10,-10,-10,-10,-10,-20},
        {-10,0,0,0,0,0,0,-10},
        {-10,0,5,10,10,5,0,-10},
        {-10,5,5,10,10,5,5,-10},
        {-10,0,10,10,10,10,0,-10},
        {-10,10,10,10,10,10,10,-10},
        {-10,5,0,0,0,0,5,-10},
        {-20,-10,-10,-10,-10,-10,-10,-20}
        };

    public Bishop(int r, int f, Color c) {
        super(r, f, c);

        gridVal=4;
        value=33;
        if(c==Color.WHITE){
            gridVal=gridVal*-1;
            pieceEval= reverseGrid(pieceEval);
        }
    }

    @Override
    public void possibleMoves(Board board) {

        availCaptures=new ArrayList<>();
        availMoves=new ArrayList<>();
        availCheck=new ArrayList<>();

        int slope=file-rank, slant=rank+file;
        //if piece pinned to king
        // if(board.isPinned(rank, file, color)){
            // ArrayList<Square> threats=board.getThreats(rank, file, color);
            // if(threats.size()>0) return;

        //     int i=threats.get(0).getPiece().rank, j=threats.get(0).getPiece().file;
        //     if(j-i==slope || j+i==slant) availCaptures.add(new Move(rank, file,i,j));
        //     return;
        // }

        // for (int i = 0; i < board.length; i++) {
        //     for (int j = 0; j < board[0].length; j++) {
        //         if(j-i==slope || j+i==slant){availMoves.add(i+""+j);}
        //         if(board[i][j]>0){
        //             if(color.equalsIgnoreCase("white")) pathBlockers.add(i+""+j);
        //             else availCaptures.add(i+""+j);
        //         }
        //         else if(board[i][j]<0){
        //             if(color.equalsIgnoreCase("white")) availCaptures.add(i+""+j);
        //             else pathBlockers.add(i+""+j);
        //         }
        //     }
        // }

        boolean stop=false;
        for (int i = rank; i < 8; i++) {
            for (int j = file; j < 8; j++) {
                if(i==rank)break;

                if(j-i==slope && j!=file){
                    if(board.getSquare(i, j).getPiece()==null) availMoves.add(new Move(rank, file,i,j));

                    else if(board.getSquare(i, j).getPiece().getColor()==color){
                        stop=true;
                        break;
                    }
    
                    else if(valid(i, j) && !board.getSquare(i, j).isOccupied()){
                        if(board.getSquare(i, j).getPiece().getColor()!=color){
                            if(board.getSquare(i, j).getPiece().getValue()==200) availCheck.add(new Move(rank, file,i,j,false,true));
                            else availCaptures.add(new Move(rank, file,i,j,false,true));
                        }
                        stop=true;
                        break;
                    }
                }
            }
            if(stop) break;
        }

        stop=false;
        for (int i = rank; i > -1; i--) {
            for (int j = file; j > -1; j--) {
                if(i==rank)break;

                if(j-i==slope && j!=file){
                    if(valid(i, j) && board.getSquare(i, j).getPiece()==null) availMoves.add(new Move(rank, file,i,j));

                    else if(valid(i, j) && board.getSquare(i, j).getPiece().getColor()==color){
                        stop=true;
                        break;
                    }
    
                    else if(valid(i, j) && board.getSquare(i, j).isOccupied()){
                        if(board.getSquare(i, j).getPiece().getColor()!=color){
                            if(board.getSquare(i, j).getPiece().getValue()==200) availCheck.add(new Move(rank, file,i,j,false,true));
                            else availCaptures.add(new Move(rank, file,i,j,false,true));
                        }
                        stop=true;
                    }

                    break;
                }
            }
            if(stop) break;
        }

        stop=false;
        for (int i = rank; i < 8; i++) {
            for (int j = file; j > -1; j--) {
                if(i==rank) break;

                if (i+j==slant && j!=file) {
                    if(board.getSquare(i, j).getPiece()==null && valid(i, j)) availMoves.add(new Move(rank, file,i,j));

                    else if(valid(i, j) && board.getSquare(i, j).getPiece().getColor()==color){
                        stop=true;
                        break;
                    }
    
                    else if(valid(i, j) && board.getSquare(i, j).isOccupied()){
                        if(board.getSquare(i, j).getPiece().getColor()!=color){
                            if(board.getSquare(i, j).getPiece().getValue()==200) availCheck.add(new Move(rank, file,i,j,false,true));
                            else availCaptures.add(new Move(rank, file,i,j,false,true));
                        }
                        stop=true;
                        break;
                    }

                }
            }
            if(stop) break;
        }

        stop=false;
        for (int i = rank; i > -1; i--) {
            for (int j = file; j < 8; j++) {
                if(i==rank) break;

                if (i+j==slant && j!=file) {
                    if(board.getSquare(i, j).getPiece()==null) availMoves.add(new Move(rank, file,i,j));

                    else if(board.getSquare(i, j).getPiece().getColor()==color){
                        stop=true;
                        break;
                    }
    
                    else if(valid(i, j) && !board.getSquare(i, j).isOccupied()){
                        if(board.getSquare(i, j).getPiece().getColor()!=color){
                            if(board.getSquare(i, j).getPiece().getValue()==200) availCheck.add(new Move(rank, file,i,j,false,true));
                            else availCaptures.add(new Move(rank, file,i,j,false,true));
                        }
                        stop=true;
                        break;
                    }
                }
            }
            if(stop) break;
        }
    }

    public String getString(){
        if(color==Color.BLACK) return "b";
        return "B";
    }

    @Override
    public int[][] getGrid() {
        return pieceEval;
    }

}