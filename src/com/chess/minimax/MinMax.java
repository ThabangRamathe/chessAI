package com.chess.minimax;

import java.util.ArrayList;
import java.util.Random;

import com.chess.*;

public class MinMax{
    int maxDepth;
    Color color;
    Random rand;
    static int count=0;

    public MinMax(Color color, int depth){
        this.color=color;
        this.maxDepth=depth;
        rand=new Random();
    }

    public double eval(Board board, Color color){

        int whiteEval=0, blackEval=0;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                // if(board.getSquare(i, j).getPiece()==null) continue;

                if(board.getSquare(i, j).isOccupied()){
                    if(board.getSquare(i, j).getPiece().getColor()==Color.WHITE){
                        whiteEval+=board.getSquare(i, j).getPiece().getValue();
                        whiteEval+=board.getSquare(i,j).getPiece().getGrid()[i][j];
                        board.getSquare(i, j).getPiece().possibleMoves(board);
                        int moves=board.getSquare(i, j).getPiece().getCaptures().size();
                        moves+=board.getSquare(i, j).getPiece().getMoves().size();
                        moves*=10;
                        whiteEval+=moves;
                    }
                    else if(board.getSquare(i, j).getPiece().getColor()==Color.BLACK){
                        blackEval+=board.getSquare(i, j).getPiece().getValue();
                        blackEval+=board.getSquare(i, j).getPiece().getGrid()[i][j];
                        board.getSquare(i, j).getPiece().possibleMoves(board);
                        int moves=board.getSquare(i, j).getPiece().getCaptures().size();
                        moves+=board.getSquare(i, j).getPiece().getMoves().size();
                        moves*=10;
                        blackEval+=moves;
                    }
                }
            }
        }

        if(color==Color.WHITE) return whiteEval- blackEval;
        else if(color==Color.BLACK) return blackEval -whiteEval;

        return whiteEval-blackEval;
    }

    public double eval1(Board board, Color color){

        int whiteEval=0, blackEval=0;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if(board.getSquare(i, j).isOccupied()){
                    if(board.getSquare(i, j).getPiece().getColor()==Color.WHITE){
                        whiteEval+=board.getSquare(i, j).getPiece().getValue();
                        whiteEval+=board.getSquare(i,j).getPiece().getGrid()[i][j];
                    }
                    else if(board.getSquare(i, j).getPiece().getColor()==Color.BLACK){
                        blackEval+=board.getSquare(i, j).getPiece().getValue();
                        blackEval+=board.getSquare(i, j).getPiece().getGrid()[i][j];
                    }
                }
            }
        }

        if(color==Color.WHITE) return whiteEval- blackEval;
        else if(color==Color.BLACK) return blackEval -whiteEval;

        return 0;
    }

    public float Max(Board board, float alpha, float beta, int depth){
        
        if(depth==0) return (float) eval(board, color);

        ArrayList<Move> moves=board.getValidatedMoves(color);
        if(moves.size()==0) return Float.NEGATIVE_INFINITY;
        float evalVal=Float.NEGATIVE_INFINITY;

        for(int i=0; i<moves.size(); i++){
            // System.out.println(moves.get(i).getString());
            board.makeMove(moves.get(i));
            evalVal= Math.max(evalVal,Min(board, alpha, beta, depth-1));
            board.undo();
            count++;

            // if(evalVal>alpha) alpha=evalVal;
            evalVal=Math.max(alpha,evalVal);
            if(beta<=alpha) return evalVal;
        }

        return evalVal;
    }

    public float Min(Board board, float alpha, float beta, int depth){

        if(depth==0){
            if(color==Color.WHITE) return (float) eval(board, Color.BLACK);
            else if(color==Color.BLACK) return (float) eval(board, Color.WHITE);
        }

        ArrayList<Move> moves=new ArrayList<>();
        Color c=Color.WHITE;
        if(color==Color.WHITE) c=Color.BLACK;

        moves=board.getValidatedMoves(c);
        if(moves.size()==0) return Float.POSITIVE_INFINITY;

        float evalVal=Float.POSITIVE_INFINITY;

        for(int i=0; i<moves.size(); i++){
            // System.out.println(moves.get(i).getString());
            board.makeMove(moves.get(i));
            evalVal= Math.min(evalVal,Max(board, alpha, beta, depth-1));
            board.undo();
            count++;

            evalVal=Math.min(beta,evalVal);
            if(beta<=alpha) return evalVal;
        }

        return evalVal;
    }

    public Move Decision(Board board){

        ArrayList<Move> moves=board.getValidatedMoves(color);
        if(moves.size()==0) return null;

        float[] costs=new float[moves.size()];

        int index=-1;
        float max=Float.NEGATIVE_INFINITY;
        for (int i = 0; i < moves.size(); i++) {
            // System.out.println(moves.get(i).getString());
            board.makeMove(moves.get(i));
            costs[i]=Min(board, Float.NEGATIVE_INFINITY, Float.POSITIVE_INFINITY, maxDepth);
            // System.out.println("hello");
            board.undo();
            count++;
            // costs[i]=evalVal;

            if(costs[i]>max){
                max=costs[i];
                index=i;
            }
        }

        // int index=-1;
        // float max=Float.NEGATIVE_INFINITY;
        // for (int i=0; i<costs.length; i++) {
        //     if(costs[i]>max){
        //         max=costs[i];
        //         index=i;
        //     }
        // }
        System.out.println(count);

        if(index==-1) return null;
        else return moves.get(index);
    }
}
