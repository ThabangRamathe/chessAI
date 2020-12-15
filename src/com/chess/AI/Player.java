package com.chess.AI;

import com.chess.*;

public abstract class Player {
    protected Color color;

    public Player(Color color){
        this.color=color;
    }

    public Color getColor(){return color;}

    public void setColor(Color c){color=c;}

    public abstract Move NextMove(Board board);
}