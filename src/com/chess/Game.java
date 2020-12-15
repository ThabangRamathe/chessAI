package com.chess;

import java.util.*;

import com.chess.AI.*;

public class Game {

    public static void main(String[] args) {
        Scanner scan=new Scanner(System.in);
        Board board=new Board();
        // boolean gameOver=false;
        Player NPC=new MinMaxPlayer(Color.WHITE, 1);
        Player NPC2=new MinMaxPlayer(Color.BLACK, 3);
        // System.out.println(NPC2.getColor());
        // Player NPC=new WeaklingPlayer(Color.WHITE);
        board.printBoard();

        int res= Play1(NPC, NPC2, board);
        System.out.println(res);

        scan.close();
    }

    public static int Play1(Player p1, Player p2, Board board){
        Move move;
        int turn=0;

        while(true){
            turn++;
            if(turn>100) return 0;

            move=p1.NextMove(board);
            if(move==null && board.isCheck(p1.getColor())) return 1;
            if(move==null) return 0;

            board.makeMove(move);
            System.out.println(turn);
            board.printBoard();

            move=p2.NextMove(board);
            if(move==null && board.isCheck(p2.getColor())) return 2;
            if(move==null) return 0;

            board.makeMove(move);
            // System.out.println(p2.getColor());
            board.printBoard();
        }
    }

    public static int play(Player NPC, Board board){
        Scanner scan=new Scanner(System.in);
        Move move;
        int turn=0;
        boolean gameOver=false;

        while(!gameOver){
            if(turn++>200) return 0;

            System.out.println("NPC thinks!");

            move= NPC.NextMove(board);
            if(move==null && board.isCheck(NPC.getColor())) return 1;
            if(move==null) return 0;

            System.out.println("NPC moves!");

            board.makeMove(move);
            board.printBoard();

            System.out.println("Select a tile to select a piece to move(e.g A1): ");
            String input=scan.nextLine();
            if(input.equals("q")){break;}
            Vector pieceToMove=board.getTile(input);
            ArrayList<Move> moves= board.getMoves(pieceToMove);
            while(pieceToMove==null){
                System.out.println("Selection was invalid/Piece can't be moved");
                System.out.println("Select a tile to select a piece to move(e.g A1): ");
                input=scan.nextLine();
                if(input.equalsIgnoreCase("q")){
                    gameOver=true;
                    break;
                }
                pieceToMove=board.getTile(input);
                moves=board.getMoves(pieceToMove);
            }

            System.out.println("Select a tile to move the piece to(e.g A1): ");
            input=scan.nextLine();
            if(input.equals("q")){break;}
            Vector destMove=board.getTile(input);
            while(destMove==null){
                System.out.println("Selection was invalid/Piece can't be moved");
                System.out.println("Select a tile to move the piece to(e.g A1): ");
                input=scan.nextLine();
                if(input.equalsIgnoreCase("q")){
                    gameOver=true;
                    break;
                }
                destMove=board.getTile(input);
            }
            move=new Move(pieceToMove.x,pieceToMove.y,destMove.x,destMove.y);

            for (Move move2 : moves) {
                if(move2.equals1(move)){
                    move=move2;
                    break;
                }
            }

            if(move==null && board.isCheck(Color.BLACK)) return -1;
            if(move==null) return 0;

            board.makeMove(move);
            board.printBoard();
        }
        
        scan.close();
        return 0;
    }
}