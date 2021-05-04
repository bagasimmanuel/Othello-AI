package com.belajar;

import com.sun.org.apache.bcel.internal.generic.RETURN;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Othello {

    public static MinimaxAI ai = new MinimaxAI();
    public static int boardSize = 0;
    public static int[][] board;

    public Othello(int i) {
        this.board = new int[i][i];
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board.length; col++) {
                board[row][col] = Player.NONE;
            }
        }
        int mid = (i / 2) - 1;
        board[mid][mid] = Player.WHITE;
        board[mid][mid + 1] = Player.BLACK;
        board[mid + 1][mid] = Player.BLACK;
        board[mid + 1][mid + 1] = Player.WHITE;


        //Harus dibuat test tpi idk how

        //othello.printBoard(Othello.BLACK);
        //Test LeftUp
        // board[mid+2][mid+2] = BLACK;
        //Test Right Down
        // board[mid-1][mid-1] = BLACK;

        //othello.printBoard(Othello.WHITE);
        //Test RightUp
        //board[mid+2][mid-1] = WHITE;
        // Test LeftDown
//        board[mid-1][mid+2] = WHITE;
    }

    public void printBoard(int[][] currBoard, ArrayList<Coordinate> validMovesForCurrPlayer) {
        int numBlacks = 0;
        int numWhites = 0;
        System.out.println();
        System.out.printf("   ");
        for (int i = 0; i < currBoard.length; i++) {
            System.out.printf(" " + i + "  ");
        }
        System.out.printf("\n  ");
        for (int i = 0; i < currBoard.length; i++) {
            System.out.printf("----");
        }
        System.out.println();
        for (int i = 0; i < currBoard.length; i++) {
            System.out.printf(i + " |");
            for (int j = 0; j < currBoard.length; j++) {
                if (currBoard[i][j] == Player.WHITE) {
                    System.out.printf(" O |");
                    numWhites++;
                } else if (currBoard[i][j] == Player.BLACK) {
                    System.out.printf(" X |");
                    numBlacks++;
                } else if (checkIfExistsInAvailableMoves(validMovesForCurrPlayer, i, j)) {
                    System.out.printf(" * |");
                } else {
                    System.out.printf("   |");
                }
            }
            System.out.println();
            System.out.printf("  ");
            for (int j = 0; j < currBoard.length; j++) {
                System.out.printf("----");
            }
            System.out.println();

        }
        System.out.println("Black: " + numBlacks + " - " + "White: " + numWhites);
        System.out.println();
    }
    public void play() {

        int turn = Player.WHITE;
        String player;
        Scanner sc = new Scanner(System.in);
        String aiInput;
        boolean withAI = false;
        int chosenRow;
        int chosenCol;

        System.out.println("Play with AI? ");
        aiInput = sc.nextLine();
        if(aiInput.equals("YES")  || aiInput.equals("yes")  || aiInput.equals("Y")  || aiInput.equals("y")){
            withAI = true;
        }else {
            withAI = false;
        }

        while (!BoardHelper.gameEnd(board,turn)) {
            player = getCurrentPlayer(turn);
            ArrayList<Coordinate> availableMoves = BoardHelper.getAllValidMoves(this.board,turn);
            printBoard(this.board,availableMoves);

            if(withAI == true && turn == Player.BLACK){
                Coordinate bestMove = ai.solve(this.board,5,Player.BLACK);
                Coordinate bestMoveMMAB = ai.solveMMAB(this.board,5,Player.BLACK);
                if(bestMove != null){
                    BoardHelper.makeMove(this.board,turn,bestMove.getRow(), bestMove.getCol());
                }
            }else{
                if(availableMoves.size() > 0){
                    System.out.println(player + " TURN \n");
                    System.out.println("Pilih Row : ");
                    chosenRow = sc.nextInt();
                    System.out.println("Pilih Column : ");
                    chosenCol = sc.nextInt();
                    while (!(checkIfExistsInAvailableMoves(availableMoves, chosenRow, chosenCol))) {
                        System.out.println("Wrong Coordinate, please try again\n");
                        System.out.println("Pilih Row : ");
                        chosenRow = sc.nextInt();
                        System.out.println("Pilih Column : ");
                        chosenCol = sc.nextInt();
                    }
                    BoardHelper.makeMove(this.board,turn, chosenRow, chosenCol);
                }
            }

            if (turn == Player.WHITE) {
                turn = Player.BLACK;
            } else {
                turn = Player.WHITE;
            }
        }

        // Game Ends
        int numWhite = 0;
        int numBlack = 0;
        String winner;
        for(int i = 0; i < board.length;i++){
            for(int j = 0; j < board.length;j++){
                if(board[i][j] == Player.WHITE){
                    numWhite++;
                }else if(board[i][j] == Player.BLACK){
                    numBlack++;
                }
            }
        }
        if(numWhite > numBlack){
            winner = "White";
        }else if(numBlack > numWhite){
            winner = "Black";
        }else{
            winner = "NO ONE :)\n ROUND TIE!";
        }
        System.out.println("Game Ended\nCurrent Score is\n");
        System.out.println("White : " + numWhite + " Black : " + numBlack + "\n");
        System.out.println("THE WINNER IS " + winner);

    }

    // Utils
    String getCurrentPlayer(int turn) {
        String player;
        if (turn == Player.WHITE) {
            player = "White";
        } else {
            player = "Black";
        }
        return player;
    }
    // State Functions


    // ArrayList.Contains(new Coordinate(i,j) doesnt work so i make this utility function to check whether Current
    // position is a valid move that has been inserted in validMovesForCurrPlayer arrayList
    public boolean checkIfExistsInAvailableMoves(ArrayList<Coordinate> validMovesForCurrPlayer, int i, int j) {

        for (Coordinate coor : validMovesForCurrPlayer) {
            if (coor.getCol() == j && coor.getRow() == i) {
                return true;
            }
        }
        return false;
    }



}




