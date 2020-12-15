package com.chess.Pieces;

import java.util.ArrayList;

import com.chess.*;

public class King extends Piece{
    public int[][] pieceEval={
        {-30,-40,-40,-50,-50,-40,-40,-30},
        {-30,-40,-40,-50,-50,-40,-40,-30},
        {-30,-40,-40,-50,-50,-40,-40,-30},
        {-30,-40,-40,-50,-50,-40,-40,-30},
        {-20,-30,-30,-40,-40,-30,-30,-20},
        {-10,-20,-20,-20,-20,-20,-20,-10},
        {20,20,-10,-10,-10,-10,20,20},
        {20,30,20,0,0,5,30,20}
    };
    public int[][] endGameEval={
        {-50,-40,-30,-20,-20,-30,-40,-50},
        {-30,-20,-10,0,0,-10,-20,-30},
        {-30,-10,20,30,30,20,-10,-30},
        {-30,-10,30,40,40,30,-10,-30},
        {-30,-10,30,40,40,30,-10,-30},
        {-30,-10,20,30,30,20,-10,-30},
        {-30,-30,0,0,0,0,-30,-30},
        {-50,-30,-30,-30,-30,-30,-30,-50}
    };

    private boolean endgame;

    public King(int r, int f, Color c) {
        super(r, f, c);

        value=200;
        endgame=false;
        if(c==Color.WHITE){
            pieceEval=reverseGrid(pieceEval);
            endGameEval= reverseGrid(endGameEval);
        }
    }

     /**
     * @return the endgame
     */
    public boolean isEndgame() {
        return endgame;
    }

    public void setEndgame(boolean val){endgame=val;}

    @Override
    public void possibleMoves(Board board) {
        availCaptures=new ArrayList<>();
        availMoves=new ArrayList<>();
        availCheck=new ArrayList<>();
        if(isEndgame()) pieceEval=endGameEval;

        //N
        if(valid(rank+1, file)){
            if(board.getSquare(rank+1, file).getPiece()!=null){
                if(board.getSquare(rank+1, file).getPiece().getColor()!=color){
                    if(board.getSquare(rank+1, file).getPiece().getValue()==200) availCheck.add(new Move(rank, file, rank, file+1));
                    else availCaptures.add(new Move(rank, file, rank+1, file,false,true));
                }
            }
            else{ availMoves.add(new Move(rank, file, rank+1, file));}
        }
        //NW
        if(valid(rank+1, file-1)){
            if(board.getSquare(rank+1, file-1).isOccupied()){
                if(board.getSquare(rank+1, file-1).getPiece().getColor()!=color){
                    if(board.getSquare(rank+1, file-1).getPiece().getValue()==200) availCheck.add(new Move(rank, file, rank+1, file-1));
                    else availCaptures.add(new Move(rank, file, rank+1, file-1,false,true));
                }
            }
            else{ availMoves.add(new Move(rank, file, rank+1, file-1));}
        }
        //W
        if(valid(rank, file-1)){
            if(board.getSquare(rank, file-1).isOccupied()){
                if(board.getSquare(rank, file-1).getPiece().getColor()!=color){
                    if(board.getSquare(rank, file-1).getPiece().getValue()==200) availCheck.add(new Move(rank, file, rank, file-1));
                    else availCaptures.add(new Move(rank, file, rank, file-1,false,true));
                }
            }
            else{ availMoves.add(new Move(rank, file, rank, file-1));}
        }
        //SW
        if(valid(rank-1, file-1)){
            if(board.getSquare(rank-1, file-1).isOccupied()){
                if(board.getSquare(rank-1, file-1).getPiece().getColor()!=color){
                    if(board.getSquare(rank-1, file-1).getPiece().getValue()==200) availCheck.add(new Move(rank, file, rank-1, file-1));
                    else availCaptures.add(new Move(rank, file, rank-1, file-1,false,true));
                }
            }
            else{ availMoves.add(new Move(rank, file, rank-1, file-1));}
        }
        //S
        if(valid(rank-1, file)){
            if(board.getSquare(rank-1, file).isOccupied()){
                if(board.getSquare(rank-1, file).getPiece().getColor()!=color){
                    if(board.getSquare(rank-1, file).getPiece().getValue()==200) availCheck.add(new Move(rank, file, rank, file-1));
                    else availCaptures.add(new Move(rank, file, rank-1, file,false,true));
                }
            }
            else{ availMoves.add(new Move(rank, file, rank-1, file));}
        }
        //SE
        if(valid(rank-1, file+1)){
            if(board.getSquare(rank-1, file+1).isOccupied()){
                if(board.getSquare(rank-1, file+1).getPiece().getColor()!=color){
                    if(board.getSquare(rank-1, file+1).getPiece().getValue()==200) availCheck.add(new Move(rank, file, rank-1, file+1));
                    else availCaptures.add(new Move(rank, file, rank-1, file+1,false,true));
                }
            }
            else{ availMoves.add(new Move(rank, file, rank-1, file+1));}
        }
        //E
        if(valid(rank, file+1)){
            if(board.getSquare(rank, file+1).isOccupied()){
                if(board.getSquare(rank, file+1).getPiece().getColor()!=color){
                    if(board.getSquare(rank, file+1).getPiece().getValue()==200) availCheck.add(new Move(rank, file, rank, file+1));
                    else availCaptures.add(new Move(rank, file, rank, file+1,false,true));
                }
            }
            else{ availMoves.add(new Move(rank, file, rank, file+1));}
        }
        //NE
        if(valid(rank+1, file+1)){
            if(board.getSquare(rank+1, file+1).isOccupied()){
                if(board.getSquare(rank+1, file+1).getPiece().getColor()!=color){
                    if(board.getSquare(rank+1, file+1).getPiece().getValue()==200) availCheck.add(new Move(rank, file, rank+1, file+1));
                    else availCaptures.add(new Move(rank, file, rank+1, file+1,false,true));
                }
            }
            else{ availMoves.add(new Move(rank, file, rank+1, file+1));}
        }

        //Castling
        //King side castling
        if(!hasMoved() && ((board.getSquare(rank, 7).getPiece()!=null && !board.getSquare(rank, 7).getPiece().hasMoved()) || 
        board.getSquare(rank, 0).getPiece()!=null && !board.getSquare(rank, 0).getPiece().hasMoved())){
            if(valid(rank,file+2)){
                if(board.isCastlingPossible(color, new Move(rank,file, rank, file+1), new Move(rank,file, rank, file+2))){
                    availMoves.add(new Move(rank, file, rank, file+2,7,7));
                }
            }
            if(valid(rank, file-2)){
                if(board.isCastlingPossible(color, new Move(rank,file, rank, file-1), new Move(rank,file, rank, file-2))){
                    availMoves.add(new Move(rank, file, rank, file-2,0,0));
                }
            }
        }
        //Queen side castling
        if(!hasMoved() && ((board.getSquare(rank, 0).getPiece()!=null && !board.getSquare(rank, 0).getPiece().hasMoved())
        || board.getSquare(rank, 7).getPiece()!=null && !board.getSquare(rank, 7).getPiece().hasMoved())){
            if(valid(rank, file-2)){
                if(board.isCastlingPossible(color, new Move(rank,file, rank, file-1), new Move(rank,file, rank, file-2))){
                    availMoves.add(new Move(rank, file, rank, file-2,0,0));
                }
            }
            if(valid(rank,file+2)){
                if(board.isCastlingPossible(color, new Move(rank,file, rank, file+1), new Move(rank,file, rank, file+2))){
                    availMoves.add(new Move(rank, file, rank, file+2,7,7));
                }
            }
        }
    }

    public String getString(){
        if(color==Color.BLACK) return "k";
        return "K";
    }

    public int[][] getGrid(){return pieceEval;}
}