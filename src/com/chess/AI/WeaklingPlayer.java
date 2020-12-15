package com.chess.AI;

import java.util.ArrayList;
import java.util.Random;

import com.chess.Board;
import com.chess.Color;
import com.chess.Move;

public class WeaklingPlayer extends Player {
    Random rand;

    public WeaklingPlayer(Color color) {
        super(color);

        rand=new Random();
    }

    @Override
    public Move NextMove(Board board) {
        System.out.println("hey!");
        ArrayList<Move> moves=board.getMoves(color);
        System.out.println("Done!");

        if(moves.size()==0) return null;

        return moves.get(rand.nextInt(moves.size()));
    }

}