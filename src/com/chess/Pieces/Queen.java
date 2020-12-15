package com.chess.Pieces;

import com.chess.*;
import java.util.ArrayList;

public class Queen extends Piece{
    public int[][] pieceEval={
        {-20,-10,-10,-5,-5,-10,-10,-20},
        {-10,0,0,0,0,0,0,-10},
        {-10,0,5,5,5,5,0,-10},
        {-5,0,5,5,5,5,0,-5},
        {0,0,5,5,5,5,0,-5},
        {-10,5,5,5,5,5,0,-10},
        {-10,0,5,0,0,0,0,-10},
        {-20,-10,-10,-5,-5,-10,-10,-20}
    };

    public Queen(int r, int f, Color c) {
        super(r, f, c);

        value=90;
        if(c==Color.WHITE){pieceEval= reverseGrid(pieceEval);}
    }

    @Override
    public void possibleMoves(Board board) {
        availCaptures=new ArrayList<>();
        availMoves=new ArrayList<>();
        availCheck=new ArrayList<>();
        int slope=file-rank, slant=rank+file;
        //if piece pinned to king
        // if(board.isPinned(rank, file, color)){
        //     ArrayList<Square> threats=board.getThreats(rank, file, color);
        //     if(threats.size()>1) return;

        //     int i=threats.get(0).getPiece().rank, j=threats.get(0).getPiece().file;
        //     if(j-i==slope || j+i==slant) availCaptures.add(new Move(rank, file,i,j));
        //     if(i==rank || j==file) availCaptures.add(new Move(rank, file,i,j));
        //     return;
        // }
        
        //Bishop
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

        //Rook
        for (int i = rank; i < 8; i++) {
            if(i!=rank){
                if(valid(i, file) && board.getSquare(i, file).getPiece()==null) availMoves.add(new Move(rank, file, i, file));

                else if(valid(i, file) && board.getSquare(i, file).getPiece().getColor()==color) break;

                else if(valid(i, file) && board.getSquare(i, file).isOccupied()){
                    if(board.getSquare(i, file).getPiece().getColor()!=color){
                        if(board.getSquare(i, file).getPiece().getValue()==200) availCheck.add(new Move(rank, file, i, file, false, true));
                        else availCaptures.add(new Move(rank, file, i, file,false,true));
                    }
                    break;
                }
            }
        }

        for (int i = file; i < 8; i++) {
            if (i!=file) {
                if(valid(rank, i) && board.getSquare(rank, i).getPiece()==null) availMoves.add(new Move(rank, file, rank, i));

                else if(valid(rank, i) && board.getSquare(rank, i).getPiece().getColor()==color) break;

                else if(valid(rank, i) && board.getSquare(rank, i).isOccupied()){
                    if(board.getSquare(rank, i).getPiece().getColor()!=color){
                        if(board.getSquare(rank, i).getPiece().getValue()==200) availCheck.add(new Move(rank, file, rank, i, false, true));
                        else availCaptures.add(new Move(rank, file, rank, i,false,true));
                    }
                    break;
                }
            }
        }

        for (int i = rank; i > 0; i--) {
            if(i!=rank){
                if(valid(i, file) && board.getSquare(i, file).getPiece()==null) availMoves.add(new Move(rank, file, i, file));

                else if(valid(i, file) && board.getSquare(i, file).getPiece().getColor()==color) break;

                else if(valid(i, file) && board.getSquare(i, file).isOccupied()){
                    if(board.getSquare(i, file).getPiece().getColor()!=color){
                        if(board.getSquare(i, file).getPiece().getValue()==200) availCheck.add(new Move(rank, file, i, file, false, true));
                        else availCaptures.add(new Move(rank, file, i, file,false,true));
                    }
                    break;
                }
            }
        }

        for (int i = file; i > 0; i--) {
            if (i!=file) {
                if(valid(rank, i) && board.getSquare(rank, i).getPiece()==null) availMoves.add(new Move(rank, file, rank, i));

                else if(valid(rank, i) && board.getSquare(rank, i).getPiece().getColor()==color) break;

                else if(valid(rank, i) && board.getSquare(rank, i).isOccupied()){
                    if(board.getSquare(rank, i).getPiece().getColor()!=color){
                        if(board.getSquare(rank, i).getPiece().getValue()==200) availCheck.add(new Move(rank, file, rank, i, false, true));
                        else availCaptures.add(new Move(rank, file, rank, i,false,true));
                    }
                    break;
                }
            }
        }
    }

    public String getString(){
        if(color==Color.BLACK) return "q";
        return "Q";
    }

    public int[][] getGrid(){return pieceEval;}
}