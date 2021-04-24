package com.belajar;

import com.sun.org.apache.bcel.internal.generic.RETURN;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Othello {

    public int[][] board;

    public static final int NONE = 0;
    public static final int WHITE = 1;
    public static final int BLACK = 2;

    public Othello(int i) {
        this.board = new int[i][i];
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board.length; col++) {
                board[row][col] = NONE;
            }
        }
        int mid = (i / 2) - 1;
        board[mid][mid] = WHITE;
        board[mid][mid + 1] = BLACK;
        board[mid + 1][mid] = BLACK;
        board[mid + 1][mid + 1] = WHITE;


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

    public void printBoard(ArrayList<Coordinate> validMovesForCurrPlayer) {
        int numBlacks = 0;
        int numWhites = 0;
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
                } else if (checkIfExistsInAvailableMoves(validMovesForCurrPlayer, i, j)) {
                    System.out.printf(" * |");
                } else {
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
    public void play() {
        int turn = WHITE;
        String player;
        if (turn == WHITE) {
            player = "White";
        } else {
            player = "Black";
        }
        Scanner sc = new Scanner(System.in);
        int chosenRow;
        int chosenCol;
        while (!gameEnd()) {
            player = getCurrentPlayer(turn);
            ArrayList<Coordinate> availableMoves = getAllValidMoves(turn);
            printBoard(availableMoves);

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
                makeMove(turn, chosenRow, chosenCol);
            }

            if (turn == WHITE) {
                turn = BLACK;
            } else {
                turn = WHITE;
            }
        }
        // Game Ends
        int numWhite = 0;
        int numBlack = 0;
        String winner;
        for(int i = 0; i < board.length;i++){
            for(int j = 0; j < board.length;j++){
                if(board[i][j] == WHITE){
                    numWhite++;
                }else if(board[i][j] == BLACK){
                    numBlack++;
                }
            }
        }
        if(numWhite > numBlack){
            winner = "White";
        }else if(numBlack < numWhite){
            winner = "Black";
        }else{
            winner = "NO ONE :) \n ROUND TIE!";
        }
        System.out.println("Game Ended \n Current Score is \n");
        System.out.println("White : " + numWhite + " Black : " + numBlack + "\n");
        System.out.println("THE WINNER IS " + winner);

    }

    // Utils
    String getCurrentPlayer(int turn) {
        String player;
        if (turn == WHITE) {
            player = "White";
        } else {
            player = "Black";
        }
        return player;
    }

    // State Functions
    public boolean gameEnd() {

        int availableBlack = getAllValidMoves(BLACK).size();
        int availableWhite = getAllValidMoves(WHITE).size();

        if(availableBlack == 0 && availableWhite == 0){
            return true;
        }else{
            return false;
        }
    }


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


    public ArrayList<Coordinate> getAllValidMoves(int turn) {

        ArrayList<Coordinate> validMoves = new ArrayList<>();

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {

                if (board[i][j] == turn) {
                    ArrayList<Coordinate> horizontalValidMoves = getHorizontalValidMoves(i, j, turn);
                    ArrayList<Coordinate> verticalValidMoves = getVerticalValidMoves(i, j, turn);
                    ArrayList<Coordinate> diagonalValidMoves = getDiagonalValidMoves(i, j, turn);
                    validMoves.addAll(horizontalValidMoves);
                    validMoves.addAll(verticalValidMoves);
                    validMoves.addAll(diagonalValidMoves);
                }

            }
        }
        validMoves.removeAll(Collections.singleton(null));
        return validMoves;
    }

    // Ini checking Horizontal
    public ArrayList<Coordinate> getHorizontalValidMoves(int row, int col, int turn) {
        ArrayList<Coordinate> validMoves = new ArrayList<>();
        Coordinate left = checkLeft(row, col, turn);
        validMoves.add(left);
        Coordinate right = checkRight(row, col, turn);
        validMoves.add(right);
        return validMoves;
    }

    public Coordinate checkLeft(int row, int col, int turn) {

        Coordinate valid = new Coordinate();

        if (col > 0) {
            if (board[row][col - 1] == turn || board[row][col - 1] == NONE) {
                return null;
            } else {
                boolean opponentExist = true;
                while (col > 0 && opponentExist) {
                    col--;
                    if (board[row][col] == turn) {
                        opponentExist = false;
                    }
                    if (board[row][col] == NONE) {
                        valid.setRow(row);
                        valid.setCol(col);
                        return valid;
                    }
                }
            }
        }
        return null;
    }

    public Coordinate checkRight(int row, int col, int turn) {

        Coordinate valid = new Coordinate();

        if (col < board.length - 1) {
            if (board[row][col + 1] == turn || board[row][col + 1] == NONE) {
                return null;
            } else {
                boolean opponentExist = true;
                while (col < board.length - 1 && opponentExist) {
                    col++;
                    if (board[row][col] == turn) {
                        opponentExist = false;
                    }
                    if (board[row][col] == NONE) {
                        valid.setRow(row);
                        valid.setCol(col);
                        return valid;
                    }
                }
            }
        }
        return null;
    }
// Ini Checking Vertical

    public ArrayList<Coordinate> getVerticalValidMoves(int row, int col, int turn) {
        ArrayList<Coordinate> validMoves = new ArrayList<>();
        Coordinate up = checkUp(row, col, turn);
        validMoves.add(up);
        Coordinate down = checkDown(row, col, turn);
        validMoves.add(down);
        return validMoves;
    }

    public Coordinate checkUp(int row, int col, int turn) {
        Coordinate valid = new Coordinate();

        if (row > 0) {
            if (board[row - 1][col] == turn || board[row - 1][col] == NONE) {
                return null;
            } else {
                boolean opponentExist = true;
                while (row > 0 && opponentExist) {
                    row--;
                    if (board[row][col] == turn) {
                        opponentExist = false;
                    } else if (board[row][col] == NONE) {
                        valid.setRow(row);
                        valid.setCol(col);
                        return valid;
                    }
                }
            }
        }
        return null;
    }

    public Coordinate checkDown(int row, int col, int turn) {

        Coordinate valid = new Coordinate();

        if (row < board.length - 1) {
            if (board[row + 1][col] == turn || board[row + 1][col] == NONE) {
                return null;
            } else {
                boolean opponentExist = true;
                while (row < board.length - 1 && opponentExist) {
                    row++;
                    if (board[row][col] == turn) {
                        opponentExist = false;
                    } else if (board[row][col] == NONE) {
                        valid.setRow(row);
                        valid.setCol(col);
                        return valid;
                    }
                }
            }
        }
        return null;
    }

    // Ini Cheking Diagonal

    public ArrayList<Coordinate> getDiagonalValidMoves(int row, int col, int turn) {

        ArrayList<Coordinate> validMoves = new ArrayList<>();
        Coordinate rightUp = checkRightUp(row, col, turn);
        Coordinate rightDown = checkRightDown(row, col, turn);
        Coordinate leftDown = checkLeftDown(row, col, turn);
        Coordinate leftUp = checkLeftUp(row, col, turn);

        validMoves.add(rightUp);
        validMoves.add(rightDown);
        validMoves.add(leftDown);
        validMoves.add(leftUp);

        return validMoves;
    }

    public Coordinate checkRightUp(int row, int col, int turn) {
        Coordinate valid = new Coordinate();

        if (!outOfBound(row - 1, col + 1)) {
            if (board[row - 1][col + 1] == NONE || board[row - 1][col + 1] == turn) {
                return null;
            } else {
                boolean opponentExist = true;
                while (!outOfBound(row - 1, col + 1)) {
                    row--;
                    col++;
                    if (board[row][col] == turn) {
                        opponentExist = false;
                    } else if (board[row][col] == NONE && opponentExist) {
                        valid.setRow(row);
                        valid.setCol(col);
                        return valid;
                    }
                }
            }
        }
        return null;
    }
    public Coordinate checkLeftUp(int row, int col, int turn) {

        Coordinate valid = new Coordinate();

        if (!outOfBound(row - 1, col - 1)) {
            if (board[row - 1][col - 1] == NONE || board[row - 1][col - 1] == turn) {
                return null;
            } else {
                boolean opponentExist = true;
                while (!outOfBound(row - 1, col - 1)) {
                    row--;
                    col--;
                    if (board[row][col] == turn) {
                        opponentExist = false;
                    } else if (board[row][col] == NONE && opponentExist) {
                        valid.setRow(row);
                        valid.setCol(col);
                        return valid;
                    }
                }
            }
        }
        return null;
    }
    public Coordinate checkRightDown(int row, int col, int turn) {
        Coordinate valid = new Coordinate();

        if (!outOfBound(row + 1, col + 1)) {
            if (board[row + 1][col + 1] == NONE || board[row + 1][col + 1] == turn) {
                return null;
            } else {
                boolean opponentExist = true;
                while (!outOfBound(row + 1, col + 1) && opponentExist) {
                    row++;
                    col++;
                    if (board[row][col] == turn) {
                        opponentExist = false;
                    } else if (board[row][col] == NONE) {
                        valid.setRow(row);
                        valid.setCol(col);
                        return valid;
                    }
                }
            }
        }
        return null;
    }
    public Coordinate checkLeftDown(int row, int col, int turn) {
        Coordinate valid = new Coordinate();
        if (!outOfBound(row + 1, col - 1)) {
            if (board[row + 1][col - 1] == NONE || board[row + 1][col - 1] == turn) {
                return null;
            } else {
                boolean opponentExist = true;
                while (!outOfBound(row + 1, col - 1)) {
                    row++;
                    col--;
                    if (board[row][col] == turn) {
                        opponentExist = false;
                    } else if (board[row][col] == NONE && opponentExist) {
                        valid.setRow(row);
                        valid.setCol(col);
                        return valid;
                    }
                }
            }
        }
        return null;
    }

    public boolean outOfBound(int i, int j) {
        return (i > board.length - 1 || i < 0 || j > board.length - 1 || j < 0);
    }

    public void makeMove(int turn, int row, int col) {

        board[row][col] = turn;
        //Horizontal dan Vertikal
        tryFlipUp(row, col, turn);
        tryFlipDown(row, col, turn);
        tryFlipLeft(row, col, turn);
        tryFlipRight(row, col, turn);
        // Diagonal
        tryFlipLeftUp(row, col, turn);
        tryFlipRightUp(row, col, turn);
        tryFlipRightDown(row, col, turn);
        tryFlipLeftDown(row, col, turn);
    }

    //Vertical
    private void tryFlipUp(int row, int col, int turn) {
        int currRow = row - 1;
        int value = 0;
        boolean checkNext = true;
        // Find nearest same Colour
        while (currRow >= 0 && checkNext) {
            value = board[currRow][col];
            if (value == turn || value == NONE) {
                checkNext = false;
            } else {
                currRow--;
            }
        }
        if (checkNext == false && value == turn) {
            while (currRow < row) {
                board[currRow][col] = turn;
                currRow++;
            }
        }
    }
    private void tryFlipDown(int row, int col, int turn) {
        int currRow = row + 1;
        int value = 0;
        boolean checkNext = true;
        // Find nearest same Colour
        while (currRow <= board.length - 1 && checkNext) {
            value = board[currRow][col];
            if (value == turn || value == NONE) {
                checkNext = false;
            } else {
                currRow++;
            }
        }
        if (checkNext == false && value == turn) {
            while (currRow > row) {
                board[currRow][col] = turn;
                currRow--;
            }
        }
    }
    //Horizontal
    private void tryFlipLeft(int row, int col, int turn) {
        int currCol = col - 1;
        int value = 0;
        boolean checkNext = true;
        // Find nearest same Colour
        while (currCol >= 0 && checkNext) {
            value = board[row][currCol];
            if (value == turn || value == NONE) {
                checkNext = false;
            } else {
                currCol--;
            }
        }
        if (checkNext == false && value == turn) {
            while (currCol < col) {
                board[row][currCol] = turn;
                currCol++;
            }
        }
    }
    private void tryFlipRight(int row, int col, int turn) {
        int currCol = col + 1;
        int value = 0;
        boolean checkNext = true;
        // Find nearest same Colour
        while (currCol <= board.length - 1 && checkNext) {
            value = board[row][currCol];
            if (value == turn || value == NONE) {
                checkNext = false;
            } else {
                currCol++;
            }
        }
        if (checkNext == false && value == turn) {
            while (currCol > col) {
                board[row][currCol] = turn;
                currCol--;
            }
        }
    }

    //Diagonal
    private void tryFlipLeftUp(int row, int col, int turn) {

        int currRow = row - 1;
        int currCol = col - 1;
        int value = 0;
        boolean checkNext = true;
        while ((currRow >= 0 && currCol >= 0) && checkNext) {
            value = board[currRow][currCol];
            if (value == turn || value == NONE) {
                checkNext = false;
            } else {
                currRow--;
                currCol--;
            }
        }
        if (checkNext == false && value == turn) {
            while (currRow < row && currCol < col) {
                board[currRow][currCol] = turn;
                currRow++;
                currCol++;
            }
        }
    }
    private void tryFlipRightUp(int row, int col, int turn) {

        int currRow = row - 1;
        int currCol = col + 1;
        int value = 0;
        boolean checkNext = true;
        while ((currRow >= 0 && currCol <= board.length - 1) && checkNext) {
            value = board[currRow][currCol];
            if (value == turn || value == NONE) {
                checkNext = false;
                break;
            } else {
                currRow--;
                currCol++;
            }
        }
        if (checkNext == false && value == turn) {
            while (currRow < row && currCol > col) {
                board[currRow][currCol] = turn;
                currRow++;
                currCol--;
            }
        }
    }
    private void tryFlipRightDown(int row, int col, int turn) {

        int currRow = row + 1;
        int currCol = col + 1;
        int value = 0;
        boolean checkNext = true;
        while ((currRow <= board.length - 1 && currCol <= board.length - 1) && checkNext) {
            value = board[currRow][currCol];
            if (value == turn || value == NONE) {
                checkNext = false;
            } else {
                currRow++;
                currCol++;
            }
        }
        if (checkNext == false && value == turn) {
            while (currRow > row && currCol > col) {
                board[currRow][currCol] = turn;
                currRow--;
                currCol--;
            }
        }
    }
    private void tryFlipLeftDown(int row, int col, int turn) {

        int currRow = row + 1;
        int currCol = col - 1;
        int value = 0;
        boolean checkNext = true;
        while ((currRow <= board.length - 1 && currCol >= 0) && checkNext) {
            value = board[currRow][currCol];
            if (value == turn || value == NONE) {
                checkNext = false;
            } else {
                currRow++;
                currCol--;
            }
        }
        if (checkNext == false && value == turn) {
            while (currRow > row && currCol < col) {
                board[currRow][currCol] = turn;
                currRow--;
                currCol++;
            }
        }
    }

}




