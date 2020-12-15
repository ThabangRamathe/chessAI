package com.chess;

public class Move {
    private int x1, y1, x2, y2, cx1,cy1;
    private boolean castling, promoting, firstMove, capture;
    private Color color;

    public Move(int x1, int y1, int x2, int y2){
        this.x1=x1;
        this.x2=x2;
        this.y1=y1;
        this.y2=y2;
        castling=false;
        promoting=false;
        capture=false;
    }

    public Move(int x1, int y1, int x2, int y2, boolean promoting, boolean capture){
        this.x1=x1;
        this.x2=x2;
        this.y1=y1;
        this.y2=y2;
        this.castling=false;
        this.promoting=promoting;
        this.capture=capture;
    }

    public Move(int x1, int y1, int x2, int y2, int cx, int cy){
        this.x1=x1;
        this.x2=x2;
        this.y1=y1;
        this.y2=y2;
        this.cx1=cx;
        this.cy1=cy;
        castling=true;
        promoting=false;
        capture=false;
    }

    public int getX1(){return x1;}

    public int getX2(){return x2;}

    public int getY1(){return y1;}

    public int getY2(){return y2;}

    public int getCX1(){return cx1;}

    public int getCY1(){return cy1;}

    public boolean isCastling(){return castling;}

    public boolean isPromoting(){return promoting;}

    public void setFirstMove(boolean fm){firstMove=fm;}

    public boolean isFirstMove(){return firstMove;}

    public void setCapture(boolean c){ capture=c;}

    public boolean isCapture(){return capture;}

    public Color getColor() {
        return color;
    }

    public String getString(){return x1+":"+y1+" - "+x2+":"+y2;}

    public boolean equals1(Move m){
        if(x1==m.getX1() && x2==m.getX2() && y1==m.getY1() && y2==m.getY2()) return true;

        return false;
    }
}