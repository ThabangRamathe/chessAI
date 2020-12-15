package com.chess.AI;

import com.chess.Board;
import com.chess.Color;
import com.chess.Move;
import com.chess.minimax.*;

public class MinMaxPlayer extends Player {
    private MinMax minMax;

    public MinMaxPlayer(Color color, int depth) {
        super(color);
        minMax = new MinMax(color, depth);
    }

    @Override
    public Move NextMove(Board board) {
        return minMax.Decision(board);
    }
}