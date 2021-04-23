package com.belajar;

import com.sun.org.apache.bcel.internal.generic.RETURN;

import java.util.ArrayList;
import java.util.Collections;

public class Othello {

    public int[][] board;

    public static final int NONE = 0;
    public static final int WHITE = 1;
    public static final int BLACK = 2;

    public Othello(int i) {
        this.board = new int[i][i];
        for(int row= 0; row < board.length;row++){
            for(int col = 0; col < board.length;col++){
                board[row][col] = NONE;
            }
        }
        int mid = (i / 2)- 1;
        board[mid][mid] = WHITE;
        board[mid][mid+1] = BLACK;
        board[mid+1][mid] = BLACK;
        board[mid+1][mid+1] = WHITE;
    }

    public void printBoard(int turn){
        int numBlacks = 0;
        int numWhites = 0;
        ArrayList<Coordinate> validMovesForCurrPlayer = getAllValidMoves(turn);
        System.out.println();
        System.out.printf("   ");
        for (int i = 0; i < this.board.length; i++) {
            System.out.printf(" " + i + "  ");
        }
        System.out.printf("\n  ");
        for (int i = 0; i < this.board.length; i++) {
            System.out.printf("----");
        }
        System.out.println();
        for (int i = 0; i < this.board.length; i++) {
            System.out.printf(i + " |");
            for (int j = 0; j < this.board.length; j++) {
                if (this.board[i][j] == WHITE) {
                    System.out.printf(" O |");
                    numWhites++;
                } else if (this.board[i][j] == BLACK) {
                    System.out.printf(" X |");
                    numBlacks++;
                } else if(checkIfExistsInAvailableMoves(validMovesForCurrPlayer,i,j)){
                    System.out.printf(" * |");
                }
                else {
                    System.out.printf("   |");
                }
            }
            System.out.println();
            System.out.printf("  ");
            for (int j = 0; j < this.board.length; j++) {
                System.out.printf("----");
            }
            System.out.println();

        }
        System.out.println("Black: " + numBlacks + " - " + "White: " + numWhites);
        System.out.println();
    }
    // ArrayList.Contains(new Coordinate(i,j) doesnt work so i make this utility function to check whether Current
    // position is a valid move that has been inserted in validMovesForCurrPlayer arrayList
    public boolean checkIfExistsInAvailableMoves(ArrayList<Coordinate> validMovesForCurrPlayer,int i,int j){

        for (Coordinate coor: validMovesForCurrPlayer) {
            if(coor.getCol() == j && coor.getRow() == i){
              return true;
            }
        }
        return false;
    }


   public ArrayList<Coordinate> getAllValidMoves(int turn){

        ArrayList<Coordinate> validMoves = new ArrayList<>();

        for(int i = 0; i < board.length;i++){
            for(int j = 0; j < board.length;j++){

                if(board[i][j] == turn){
                    ArrayList<Coordinate> horizontalValidMoves =
                            getHorizontalValidMoves(i,j,turn);
//                    ArrayList<Coordinate> verticalValidMoves =
//                    ArrayList<Coordinate> diagonalValidMoves =
                    validMoves.addAll(horizontalValidMoves);
                }

            }
        }
        validMoves.removeAll(Collections.singleton(null));
        return validMoves;
   }

    public ArrayList<Coordinate> getHorizontalValidMoves(int row,int col,int turn){
        ArrayList<Coordinate> validMoves = new ArrayList<>();
        Coordinate left = checkLeft(row,col,turn);
        validMoves.add(left);
        Coordinate right = checkRight(row,col,turn);
        validMoves.add(right);
        return validMoves;
    }
    public Coordinate checkLeft(int row,int col,int turn) {

        Coordinate valid = new Coordinate();

        if(col > 0){
           if(board[row][col-1] == turn || board[row][col-1] == NONE){
               return null;
           }else{
               boolean opponentExist = true;
               while(col > 0 && opponentExist){
                   col--;
                   if(board[row][col] == turn){
                       opponentExist = false;
                   }
                   if(board[row][col] == NONE){
                       valid.setRow(row);
                       valid.setCol(col);
                       return valid;
                   }
               }
           }
        }
         return null;
    }
    public Coordinate checkRight(int row,int col,int turn) {

        Coordinate valid = new Coordinate();

        if(col > 0){
            if(board[row][col+1] == turn || board[row][col+1] == NONE){
                return null;
            }else{
                boolean opponentExist = true;
                while(col < board.length && opponentExist){
                    col++;
                    if(board[row][col] == turn){
                        opponentExist = false;
                    }
                    if(board[row][col] == NONE){
                        valid.setRow(row);
                        valid.setCol(col);
                        return valid;
                    }
                }
            }
        }
        return null;
    }

}




