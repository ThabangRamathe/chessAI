package com.chess;

import java.util.ArrayList;
import com.chess.Pieces.*;

public class Board {

    private Square[][] squares;

    private boolean whitePlays;
    private ArrayList<Piece> wCaptured, bCaptured;
    private ArrayList<Move> mHistory;
    private ArrayList<Square[][]> moveHistory;

    private String[] files={"A","B","C","D","E","F","G","H"};

    public Board(){
        wCaptured=new ArrayList<>();
        bCaptured=new ArrayList<>();
        mHistory=new ArrayList<>();
        moveHistory =new ArrayList<>();

        whitePlays=true;

        squares=new Square[8][8];

        squares[0][0]=new Square(new Rook(0, 0, Color.WHITE));
        squares[0][1]=new Square(new Knight(0, 1, Color.WHITE));
        squares[0][2]=new Square(new Bishop(0, 2, Color.WHITE));
        squares[0][3]=new Square(new Queen(0, 3, Color.WHITE));
        squares[0][4]=new Square(new King(0, 4, Color.WHITE));
        squares[0][5]=new Square(new Bishop(0, 5, Color.WHITE));
        squares[0][6]=new Square(new Knight(0, 6, Color.WHITE));
        squares[0][7]=new Square(new Rook(0, 7, Color.WHITE));

        for(int i=0; i<8; i++){
            squares[1][i] = new Square(new Pawn(1,i,Color.WHITE));
        }

        for(int i=2;i<6;i++){
            for(int j=0;j<8; j++){
                squares[i][j]=new Square();
            }
        }

        for(int i=0; i<8; i++){
            squares[6][i] = new Square(new Pawn(6,i,Color.BLACK));
        }

        squares[7][0]=new Square(new Rook(7, 0, Color.BLACK));
        squares[7][1]=new Square(new Knight(7, 1, Color.BLACK));
        squares[7][2]=new Square(new Bishop(7, 2, Color.BLACK));
        squares[7][3]=new Square(new Queen(7, 3, Color.BLACK));
        squares[7][4]=new Square(new King(7, 4, Color.BLACK));
        squares[7][5]=new Square(new Bishop(7, 5, Color.BLACK));
        squares[7][6]=new Square(new Knight(7, 6, Color.BLACK));
        squares[7][7]=new Square(new Rook(7, 7, Color.BLACK));
    }

    public void printBoard(){
        for (int i = -1; i < 9; i++) {
            for (int j = -1; j < 9; j++) {
                if(i==-1){
                    if(j==-1 || j==8){System.out.printf("%4s","");}
                    else{System.out.printf("%-4s",("|"+files[j]));}
                }
                else if(i==8){
                    if(j==-1 || j==8){System.out.printf("%4s","");}
                    else{System.out.printf("%-4s",("|"+files[j]));}
                }
                else if(j==-1 || j==8){System.out.printf("%-4s",("|"+(i+1)));}
                else{
                    if(squares[i][j].getPiece()!=null){
                        System.out.printf("%-4s",("|"+squares[i][j].getPiece().getString()));
                    }
                    else{System.out.printf("%-4s","|");}
                }
            }
            System.out.println();
        }
    }

    public Vector getTile(String tile){
        int rank=Integer.parseInt(tile.substring(1));
        return getTile(tile.substring(0,1), rank);
    }

    public Vector getTile(String f, int r){ 
        int file=fileIndex(f);
        if(file==-1 || r>8 || r<1) return null;
        // return board[r-1][file];
        return new Vector(r-1, file);
    }

    public int fileIndex(String f){
        for (int i=0; i<files.length; i++) {
            if((files[i]).equalsIgnoreCase(f)){return i;}
        }

        return -1;
    }

    public Square getSquare(int x, int y){return squares[x][y];}

    public ArrayList<Move> getMoves(Vector v){
        if(v==null) return null;

        getSquare(v.x,v.y).getPiece().possibleMoves(this);
        ArrayList<Move> m= getSquare(v.x,v.y).getPiece().getCaptures();
        m.addAll(getSquare(v.x, v.y).getPiece().getMoves());
        return m;
    }

    public boolean isCheck(Color color){

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if(squares[i][j].isOccupied() && squares[i][j].getPiece().getColor()!=color){
                    Piece p=squares[i][j].getPiece();
                    p.possibleMoves(this);
                    ArrayList<Move> moves=p.getCheck();

                    if(moves.size()!=0) return true;
                }
            }
        }

        return false;
    }

    public void makeMove(Move move){
        // System.out.print(move.getString());
        Square square=squares[move.getX1()][move.getY1()];
        Square destSquare=squares[move.getX2()][move.getY2()];

        Piece piece=square.getPiece();
        // if(move.isCapture()) System.out.println(" | "+piece.getString()+" | C");
        // else System.out.println(" | "+piece.getString());
        // move.setFirstMove(!piece.hasMoved());
        // if(piece==null) return;
        piece.move(move.getX2(), move.getY2());

        //castling
        if(move.isCastling()){
            moveHistory.add(squares);
            mHistory.add(move);
            squares[move.getX2()][move.getY2()]=square;
            squares[move.getX1()][move.getY1()]=new Square();
            squares[move.getX2()][move.getY2()].setPiece(piece);

            piece=squares[move.getCX1()][move.getCY1()].getPiece();
            if(move.getCY1()==7){
                piece.move(move.getX1(),move.getY1()+1);
                squares[move.getX1()][move.getY1()+1]=squares[move.getCX1()][move.getCY1()];
                squares[move.getCX1()][move.getCY1()]=new Square();
                squares[move.getX1()][move.getY1()+1].setPiece(piece);
            }
            else if(move.getCY1()==0){
                piece.move(move.getX1(), move.getY1()-1);
                squares[move.getX1()][move.getY1()-1]=squares[move.getCX1()][move.getCY1()];
                squares[move.getCX1()][move.getCY1()]=new Square();
                squares[move.getX1()][move.getY1()-1].setPiece(piece);
            }
            whitePlays=!whitePlays;

            return;
        }

        //pawn promotion
        if(square.getPiece().getValue()==10 && square.getPiece().getColor()==Color.WHITE && 
        move.getX2()==7){
            if(move.isCapture()){
                bCaptured.add(destSquare.getPiece());
            }
            moveHistory.add(squares);
            mHistory.add(move);
            squares[move.getX2()][move.getY2()]=new Square(new Queen(move.getX2(), move.getY2(), Color.WHITE));
            squares[move.getX1()][move.getY1()]=new Square();

            whitePlays=!whitePlays;
            return;
        }

        if(square.getPiece().getValue()==10 && square.getPiece().getColor()==Color.BLACK && 
        move.getX2()==0){
            if(move.isCapture()){
                wCaptured.add(destSquare.getPiece());
            }
            moveHistory.add(squares);
            mHistory.add(move);
            squares[move.getX2()][move.getY2()]=new Square(new Queen(move.getX2(), move.getY2(), Color.BLACK));
            squares[move.getX1()][move.getY1()]=new Square();

            whitePlays=!whitePlays;
            return;
        }

        if(square.getPiece().getColor()==Color.WHITE && whitePlays){
            if(move.isCapture()){
                if(squares[move.getX2()][move.getY2()].getPiece().getValue()!=200){
                    mHistory.add(move);
                    moveHistory.add(squares);
                    bCaptured.add(destSquare.getPiece());

                    squares[move.getX2()][move.getY2()]=square;
                    squares[move.getX1()][move.getY1()]=new Square();
                    squares[move.getX2()][move.getY2()].setPiece(piece);
                    whitePlays=!whitePlays;
                }
            }
            else{
                mHistory.add(move);
                moveHistory.add(squares);
                squares[move.getX2()][move.getY2()]=square;
                squares[move.getX1()][move.getY1()]=new Square();
                squares[move.getX2()][move.getY2()].setPiece(piece);
                whitePlays=!whitePlays;
            }
        }
        else if(square.getPiece().getColor()==Color.BLACK && !whitePlays){
            if(move.isCapture()){
                if(squares[move.getX2()][move.getY2()].getPiece().getValue()!=200){
                    moveHistory.add(squares);
                    mHistory.add(move);
                    wCaptured.add(destSquare.getPiece());

                    squares[move.getX2()][move.getY2()]=square;
                    squares[move.getX1()][move.getY1()]=new Square();
                    squares[move.getX2()][move.getY2()].setPiece(piece);
                    whitePlays=!whitePlays;
                }
            }
            else{
                moveHistory.add(squares);
                mHistory.add(move);
                squares[move.getX2()][move.getY2()]=square;
                squares[move.getX1()][move.getY1()]=new Square();
                squares[move.getX2()][move.getY2()].setPiece(piece);
                whitePlays=!whitePlays;
            }
        }
        else return;
        
    }

    public void undo(){
        // if(spare==null) return;

        // squares=spare;
        // whitePlays=!whitePlays;
        if(mHistory.size()==0) return;

        // Square[][] sq=moveHistory.remove(moveHistory.size()-1);
        // squares=sq;
        // whitePlays=!whitePlays;

        Move m=mHistory.remove(mHistory.size()-1);
        moveHistory.remove(moveHistory.size()-1);
        // if(m.getX1()==4 && m.getY1()==5 && m.isCapture()){
        //     System.out.print("Hello! "+mHistory.size()+"$ ");
        //     System.out.println(m.getString());
        // }
        Piece piece=squares[m.getX2()][m.getY2()].getPiece();

        if(m.isCastling()){
            piece=squares[m.getX2()][m.getY2()].getPiece();
            piece.undo(m.getX1(), m.getY1(), true);
            squares[m.getX1()][m.getY1()]=squares[m.getX2()][m.getY2()];
            squares[m.getX2()][m.getY2()]=new Square();
            squares[m.getX1()][m.getY1()].setPiece(piece);

            piece=squares[m.getX1()][m.getY1()+1].getPiece();
            if(m.getCY1()==7){
                piece.undo(m.getCX1(),m.getCY1(), true);
                squares[m.getCX1()][m.getCY1()]=squares[m.getX1()][m.getY1()+1];
                squares[m.getX1()][m.getY1()+1]=new Square();
                squares[m.getCX1()][m.getCY1()].setPiece(piece);
            }
            else if(m.getCY1()==1){
                piece.undo(m.getCX1(),m.getCY1(), true);
                squares[m.getCX1()][m.getCY1()]=squares[m.getX1()][m.getY1()-1];
                squares[m.getX1()][m.getY1()-1]=new Square();
                squares[m.getCX1()][m.getCY1()].setPiece(piece);
            }
            whitePlays=!whitePlays;

            return;
        }

        if(m.isPromoting()){
            if(m.getX2()==7){
                squares[m.getX2()][m.getY2()]=new Square();
                Piece p=new Pawn(m.getX1(),m.getY1(),Color.WHITE);
                p.setMoved(true);
                Square sq=new Square(p);
                squares[m.getX1()][m.getY1()]=sq;

                if(m.isCapture()){
                    piece=bCaptured.remove(bCaptured.size()-1);
                    squares[m.getX2()][m.getY2()].setPiece(piece);
                }
                whitePlays=!whitePlays;
            }
            else if(m.getX2()==0){
                squares[m.getX2()][m.getY2()]=new Square();
                Piece p=new Pawn(m.getX1(),m.getY1(),Color.BLACK);
                p.setMoved(true);
                Square sq=new Square(p);
                squares[m.getX1()][m.getY1()]=sq;
                
                if(m.isCapture()){
                    piece=wCaptured.remove(wCaptured.size()-1);
                    squares[m.getX2()][m.getY2()].setPiece(piece);
                }
                whitePlays=!whitePlays;
            }

            return;
        }

        if(m.isFirstMove()){
            piece=squares[m.getX2()][m.getY2()].getPiece();
            piece.undo(m.getX1(), m.getY1(), true);
            squares[m.getX1()][m.getY1()]=squares[m.getX2()][m.getY2()];
            squares[m.getX2()][m.getY2()]=new Square();
            squares[m.getX1()][m.getY1()].setPiece(piece);
            whitePlays=!whitePlays;
        }
        else{
            piece=squares[m.getX2()][m.getY2()].getPiece();
            piece.undo(m.getX1(), m.getY1(), false);
            squares[m.getX1()][m.getY1()]=squares[m.getX2()][m.getY2()];
            squares[m.getX2()][m.getY2()]=new Square();
            squares[m.getX1()][m.getY1()].setPiece(piece);
            whitePlays=!whitePlays;
        }

        if(m.isCapture()){
            if(squares[m.getX1()][m.getY1()].getPiece().getColor()==Color.WHITE){
                piece=bCaptured.remove(bCaptured.size()-1);
                squares[m.getX2()][m.getY2()]=new Square(piece);
            }
            else{
                piece=wCaptured.remove(wCaptured.size()-1);
                squares[m.getX2()][m.getY2()]=new Square(piece);
            }
        }

        // if(whist){
        //     piece=wCaptured.remove(wCaptured.size()-1);
        //     squares[m.getX2()][m.getY2()].setPiece(piece);
        //     squares[m.getX2()][m.getY2()].Occupy();;
        // }

        // if(bhist){
        //     piece=bCaptured.remove(bCaptured.size()-1);
        //     squares[m.getX2()][m.getY2()].setPiece(piece);
        //     squares[m.getX2()][m.getY2()].Occupy();
        // }
    }

    public boolean isCastlingPossible(Color c, Move m, Move m2){
        int x=m.getX2(), y=m.getY2();
        int x2=m2.getX2(),y2=m2.getY2();
        int x1=-1, y1=-1;

        if(squares[m.getX2()][m.getY2()].getPiece()!=null) return false;

        if(squares[m2.getX2()][m2.getY2()].getPiece()!=null) return false;

        ArrayList<Piece> opPieces=new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if(squares[i][j].getPiece()==null) continue;

                if(squares[i][j].getPiece().getValue()==200){
                    if(squares[i][j].getPiece().getColor()==c){
                        x1=i;
                        x2=j;
                    }
                    else continue;
                }

                if(squares[i][j].isOccupied() && squares[i][j].getPiece().getColor()!=c){
                    opPieces.add(squares[i][j].getPiece());
                }
            }
        }

        for (Piece piece : opPieces) {
            piece.possibleMoves(this);
            for (Move move : piece.getMoves()) {
                if(move.getX2()==x1 && move.getY2()==y1) return false;
                if(move.getX2()==x && move.getY2()==y) return false;
                if(move.getX2()==x2 && move.getY2()==y2) return false;
            }
        }

        return true;
    }

    public ArrayList<Move> getMoves(Color c){
        ArrayList<Move> res=new ArrayList<>();
        // int k=1;

        for(int i=0; i<8; i++){
            for(int j=0;j<8;j++){
                if(squares[i][j].getPiece()==null) continue;

                Piece piece=squares[i][j].getPiece();
                if(piece.getColor()==c){
                    piece.possibleMoves(this);
                    // res.addAll(piece.getCaptures());
                    ArrayList<Move> moves=piece.getMoves();
                    if(moves.size()!=0){
                        res.addAll(moves);
                        // System.out.println(piece.getString()+k);
                        // k++;
                    }
                    // for (Move move : piece.getCaptures()) {
                    //     res.add(move);
                    // }

                    // for(Move move: piece.getMoves()){
                    //     res.add(move);
                    // }
                }
            }
        }

        return res;
    }

    public ArrayList<Move> getValidatedMoves(Color c){
        ArrayList<Move> res=new ArrayList<>();

        for(int i=0; i<8; i++){
            for(int j=0;j<8;j++){
                if(squares[i][j].getPiece()==null) continue;

                Piece piece=squares[i][j].getPiece();
                if(piece.getColor()==c){
                    piece.possibleMoves(this);

                    ArrayList<Move> moves=piece.getMoves();
                    for (Move move : moves) {
                        if(!isCheckAfter(c, move)) res.add(move);
                    }
                }
            }
        }

        return res;
    }

    public boolean isCheckAfter(Color c, Move m){
        makeMove(m);
        boolean res=isCheck(c);
        undo();
        return res;
    }

    public boolean isPinned(int x, int y, Color c){
        squares[x][y].Unoccupy();
        boolean res=isCheck(c);
        squares[x][y].Occupy();
        return res;
    }

    public ArrayList<Square> getThreats(int r, int f, Color c){
        ArrayList<Square> res=new ArrayList<>();
        int x=-1, y=-1;

        // find the position of the king and get opposition's pieces on the board
        ArrayList<Square> opPieces=new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if(squares[i][j].isOccupied() && squares[i][j].getPiece().getValue()==200 && 
                squares[i][j].getPiece().getColor()==c){
                    x=i;
                    y=j;
                }

                if(squares[i][j].isOccupied() && squares[i][j].getPiece().getColor()!=c){
                    opPieces.add(squares[i][j]);
                }
            }
        }

        squares[r][f].Unoccupy();
        for (Square square : opPieces) {
            Square sq=square;
            sq.getPiece().possibleMoves(this);

            for (Move move : sq.getPiece().getCheck()) {
                if(move.getX2()==x && move.getY2()==y) res.add(sq);
            }
        }
        squares[r][f].Occupy();

        return res;
    }

    public Board clone(){
        Board b=new Board();
        b.bCaptured=this.bCaptured;
        b.wCaptured=this.wCaptured;
        b.mHistory=this.mHistory;
        b.moveHistory=this.moveHistory;
        b.whitePlays=this.whitePlays;
        b.squares=this.squares;

        return b;
    }
}