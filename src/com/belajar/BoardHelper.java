package com.belajar;

import java.util.ArrayList;
import java.util.Collections;

public class BoardHelper {

    public static ArrayList<Coordinate> getAllValidMoves(int[][] currBoard,int turn) {

        ArrayList<Coordinate> validMoves = new ArrayList<>();

        for (int i = 0; i < currBoard.length; i++) {
            for (int j = 0; j < currBoard.length; j++) {

                if (currBoard[i][j] == turn) {
                    ArrayList<Coordinate> horizontalValidMoves = getHorizontalValidMoves(currBoard,i, j, turn);
                    ArrayList<Coordinate> verticalValidMoves = getVerticalValidMoves(currBoard,i, j, turn);
                    ArrayList<Coordinate> diagonalValidMoves = getDiagonalValidMoves(currBoard,i, j, turn);
                    validMoves.addAll(horizontalValidMoves);
                    validMoves.addAll(verticalValidMoves);
                    validMoves.addAll(diagonalValidMoves);
                }

            }
        }
        validMoves.removeAll(Collections.singleton(null));
        return validMoves;
    }

    // Checking Horizontal

    private static ArrayList<Coordinate> getHorizontalValidMoves(int[][] currBoard,int row, int col, int turn){

        ArrayList<Coordinate> validMoves = new ArrayList<>();
        Coordinate left = checkLeft(currBoard,row, col, turn);
        validMoves.add(left);
        Coordinate right = checkRight(currBoard,row, col, turn);
        validMoves.add(right);
        return validMoves;

    }
    private static Coordinate checkLeft(int[][] currBoard,int row,int col, int turn){

        Coordinate valid = new Coordinate();

        if (col > 0) {
            if (currBoard[row][col - 1] == turn || currBoard[row][col - 1] == Player.NONE) {
                return null;
            } else {
                boolean opponentExist = true;
                while (col > 0 && opponentExist) {
                    col--;
                    if (currBoard[row][col] == turn) {
                        opponentExist = false;
                    }
                    if (currBoard[row][col] == Player.NONE) {
                        valid.setRow(row);
                        valid.setCol(col);
                        return valid;
                    }
                }
            }
        }
        return null;
    }
    private static Coordinate checkRight(int[][] currBoard,int row, int col, int turn) {

        Coordinate valid = new Coordinate();

        if (col < currBoard.length - 1) {
            if (currBoard[row][col + 1] == turn || currBoard[row][col + 1] == Player.NONE) {
                return null;
            } else {
                boolean opponentExist = true;
                while (col < currBoard.length - 1 && opponentExist) {
                    col++;
                    if (currBoard[row][col] == turn) {
                        opponentExist = false;
                    }
                    if (currBoard[row][col] == Player.NONE) {
                        valid.setRow(row);
                        valid.setCol(col);
                        return valid;
                    }
                }
            }
        }
        return null;
    }

    //Checking Vertical

    private static ArrayList<Coordinate> getVerticalValidMoves(int[][] currBoard,int row, int col, int turn) {
        ArrayList<Coordinate> validMoves = new ArrayList<>();
        Coordinate up = checkUp(currBoard,row, col, turn);
        validMoves.add(up);
        Coordinate down = checkDown(currBoard,row, col, turn);
        validMoves.add(down);
        return validMoves;
    }
    private static Coordinate checkUp(int[][] currBoard,int row, int col, int turn) {
        Coordinate valid = new Coordinate();

        if (row > 0) {
            if (currBoard[row - 1][col] == turn || currBoard[row - 1][col] == Player.NONE) {
                return null;
            } else {
                boolean opponentExist = true;
                while (row > 0 && opponentExist) {
                    row--;
                    if (currBoard[row][col] == turn) {
                        opponentExist = false;
                    } else if (currBoard[row][col] == Player.NONE) {
                        valid.setRow(row);
                        valid.setCol(col);
                        return valid;
                    }
                }
            }
        }
        return null;
    }
    private static Coordinate checkDown(int[][] currBoard,int row, int col, int turn) {

        Coordinate valid = new Coordinate();

        if (row < currBoard.length - 1) {
            if (currBoard[row + 1][col] == turn || currBoard[row + 1][col] == Player.NONE) {
                return null;
            } else {
                boolean opponentExist = true;
                while (row < currBoard.length - 1 && opponentExist) {
                    row++;
                    if (currBoard[row][col] == turn) {
                        opponentExist = false;
                    } else if (currBoard[row][col] == Player.NONE) {
                        valid.setRow(row);
                        valid.setCol(col);
                        return valid;
                    }
                }
            }
        }
        return null;
    }

    //Checking Diagonal

    private static ArrayList<Coordinate> getDiagonalValidMoves(int[][] currBoard,int row, int col, int turn) {

        ArrayList<Coordinate> validMoves = new ArrayList<>();
        Coordinate rightUp = checkRightUp(currBoard,row, col, turn);
        Coordinate rightDown = checkRightDown(currBoard,row, col, turn);
        Coordinate leftDown = checkLeftDown(currBoard,row, col, turn);
        Coordinate leftUp = checkLeftUp(currBoard,row, col, turn);

        validMoves.add(rightUp);
        validMoves.add(rightDown);
        validMoves.add(leftDown);
        validMoves.add(leftUp);

        return validMoves;
    }
    private static Coordinate checkRightUp(int[][] currBoard,int row, int col, int turn) {
        Coordinate valid = new Coordinate();

        if (!outOfBound(currBoard,row - 1, col + 1)) {
            if (currBoard[row - 1][col + 1] == Player.NONE || currBoard[row - 1][col + 1] == turn) {
                return null;
            } else {
                boolean opponentExist = true;
                while (!outOfBound(currBoard,row - 1, col + 1)) {
                    row--;
                    col++;
                    if (currBoard[row][col] == turn) {
                        opponentExist = false;
                    } else if (currBoard[row][col] == Player.NONE && opponentExist) {
                        valid.setRow(row);
                        valid.setCol(col);
                        return valid;
                    }
                }
            }
        }
        return null;
    }
    private static Coordinate checkLeftUp(int[][] currBoard,int row, int col, int turn) {

        Coordinate valid = new Coordinate();

        if (!outOfBound(currBoard,row - 1, col - 1)) {
            if (currBoard[row - 1][col - 1] == Player.NONE || currBoard[row - 1][col - 1] == turn) {
                return null;
            } else {
                boolean opponentExist = true;
                while (!outOfBound(currBoard,row - 1, col - 1)) {
                    row--;
                    col--;
                    if (currBoard[row][col] == turn) {
                        opponentExist = false;
                    } else if (currBoard[row][col] == Player.NONE && opponentExist) {
                        valid.setRow(row);
                        valid.setCol(col);
                        return valid;
                    }
                }
            }
        }
        return null;
    }
    private static Coordinate checkRightDown(int[][] currBoard,int row, int col, int turn) {
        Coordinate valid = new Coordinate();

        if (!outOfBound(currBoard,row + 1, col + 1)) {
            if (currBoard[row + 1][col + 1] == Player.NONE || currBoard[row + 1][col + 1] == turn) {
                return null;
            } else {
                boolean opponentExist = true;
                while (!outOfBound(currBoard,row + 1, col + 1) && opponentExist) {
                    row++;
                    col++;
                    if (currBoard[row][col] == turn) {
                        opponentExist = false;
                    } else if (currBoard[row][col] == Player.NONE) {
                        valid.setRow(row);
                        valid.setCol(col);
                        return valid;
                    }
                }
            }
        }
        return null;
    }
    private static Coordinate checkLeftDown(int[][] currBoard,int row, int col, int turn) {
        Coordinate valid = new Coordinate();
        if (!outOfBound(currBoard,row + 1, col - 1)) {
            if (currBoard[row + 1][col - 1] == Player.NONE || currBoard[row + 1][col - 1] == turn) {
                return null;
            } else {
                boolean opponentExist = true;
                while (!outOfBound(currBoard,row + 1, col - 1)) {
                    row++;
                    col--;
                    if (currBoard[row][col] == turn) {
                        opponentExist = false;
                    } else if (currBoard[row][col] == Player.NONE && opponentExist) {
                        valid.setRow(row);
                        valid.setCol(col);
                        return valid;
                    }
                }
            }
        }
        return null;
    }

    // Out of bound
    private static boolean outOfBound(int[][] currBoard, int i, int j) {
        return (i > currBoard.length - 1 || i < 0 || j > currBoard.length - 1 || j < 0);
    }


    // Making move and WE BE FLIPPIN BOIIII

    public static void makeMove(int[][] currBoard,int turn, int row, int col) {

        currBoard[row][col] = turn;
        //Horizontal dan Vertikal
        tryFlipUp(currBoard,row, col, turn);
        tryFlipDown(currBoard,row, col, turn);
        tryFlipLeft(currBoard,row, col, turn);
        tryFlipRight(currBoard,row, col, turn);
        // Diagonal
        tryFlipLeftUp(currBoard,row, col, turn);
        tryFlipRightUp(currBoard,row, col, turn);
        tryFlipRightDown(currBoard,row, col, turn);
        tryFlipLeftDown(currBoard,row, col, turn);
    }

    //Vertical
    private static void tryFlipUp(int[][] currBoard,int row, int col, int turn) {
        int currRow = row - 1;
        int value = 0;
        boolean checkNext = true;
        // Find nearest same Colour
        while (currRow >= 0 && checkNext) {
            value = currBoard[currRow][col];
            if (value == turn || value == Player.NONE) {
                checkNext = false;
            } else {
                currRow--;
            }
        }
        if (checkNext == false && value == turn) {
            while (currRow < row) {
                currBoard[currRow][col] = turn;
                currRow++;
            }
        }
    }
    private static void tryFlipDown(int[][] currBoard,int row, int col, int turn) {
        int currRow = row + 1;
        int value = 0;
        boolean checkNext = true;
        // Find nearest same Colour
        while (currRow <= currBoard.length - 1 && checkNext) {
            value = currBoard[currRow][col];
            if (value == turn || value == Player.NONE) {
                checkNext = false;
            } else {
                currRow++;
            }
        }
        if (checkNext == false && value == turn) {
            while (currRow > row) {
                currBoard[currRow][col] = turn;
                currRow--;
            }
        }
    }
    //Horizontal
    private static void tryFlipLeft(int[][] currBoard,int row, int col, int turn) {
        int currCol = col - 1;
        int value = 0;
        boolean checkNext = true;
        // Find nearest same Colour
        while (currCol >= 0 && checkNext) {
            value = currBoard[row][currCol];
            if (value == turn || value == Player.NONE) {
                checkNext = false;
            } else {
                currCol--;
            }
        }
        if (checkNext == false && value == turn) {
            while (currCol < col) {
                currBoard[row][currCol] = turn;
                currCol++;
            }
        }
    }
    private static void tryFlipRight(int[][] currBoard,int row, int col, int turn) {
        int currCol = col + 1;
        int value = 0;
        boolean checkNext = true;
        // Find nearest same Colour
        while (currCol <= currBoard.length - 1 && checkNext) {
            value = currBoard[row][currCol];
            if (value == turn || value == Player.NONE) {
                checkNext = false;
            } else {
                currCol++;
            }
        }
        if (checkNext == false && value == turn) {
            while (currCol > col) {
                currBoard[row][currCol] = turn;
                currCol--;
            }
        }
    }

    //Diagonal
    private static void tryFlipLeftUp(int[][] currBoard,int row, int col, int turn) {

        int currRow = row - 1;
        int currCol = col - 1;
        int value = 0;
        boolean checkNext = true;
        while ((currRow >= 0 && currCol >= 0) && checkNext) {
            value = currBoard[currRow][currCol];
            if (value == turn || value == Player.NONE) {
                checkNext = false;
            } else {
                currRow--;
                currCol--;
            }
        }
        if (checkNext == false && value == turn) {
            while (currRow < row && currCol < col) {
                currBoard[currRow][currCol] = turn;
                currRow++;
                currCol++;
            }
        }
    }
    private static void tryFlipRightUp(int[][] currBoard,int row, int col, int turn) {

        int currRow = row - 1;
        int currCol = col + 1;
        int value = 0;
        boolean checkNext = true;
        while ((currRow >= 0 && currCol <= currBoard.length - 1) && checkNext) {
            value = currBoard[currRow][currCol];
            if (value == turn || value == Player.NONE) {
                checkNext = false;
                break;
            } else {
                currRow--;
                currCol++;
            }
        }
        if (checkNext == false && value == turn) {
            while (currRow < row && currCol > col) {
                currBoard[currRow][currCol] = turn;
                currRow++;
                currCol--;
            }
        }
    }
    private static void tryFlipRightDown(int[][] currBoard,int row, int col, int turn) {

        int currRow = row + 1;
        int currCol = col + 1;
        int value = 0;
        boolean checkNext = true;
        while ((currRow <= currBoard.length - 1 && currCol <= currBoard.length - 1) && checkNext) {
            value = currBoard[currRow][currCol];
            if (value == turn || value == Player.NONE) {
                checkNext = false;
            } else {
                currRow++;
                currCol++;
            }
        }
        if (checkNext == false && value == turn) {
            while (currRow > row && currCol > col) {
                currBoard[currRow][currCol] = turn;
                currRow--;
                currCol--;
            }
        }
    }
    private static void tryFlipLeftDown(int[][] currBoard,int row, int col, int turn) {

        int currRow = row + 1;
        int currCol = col - 1;
        int value = 0;
        boolean checkNext = true;
        while ((currRow <= currBoard.length - 1 && currCol >= 0) && checkNext) {
            value = currBoard[currRow][currCol];
            if (value == turn || value == Player.NONE) {
                checkNext = false;
            } else {
                currRow++;
                currCol--;
            }
        }
        if (checkNext == false && value == turn) {
            while (currRow > row && currCol < col) {
                currBoard[currRow][currCol] = turn;
                currRow--;
                currCol++;
            }
        }
    }

    public static int[][] makeMoveForAI(int[][]currBoard,int player ,int row,int col){

        int[][] newBoard = new int[Othello.boardSize][Othello.boardSize];

        for(int i = 0; i < Othello.boardSize;i++){
            for(int j = 0; j < Othello.boardSize;j++){
                newBoard[i][j] = currBoard[i][j];
            }
        }

        makeMove(newBoard,player,row,col);

        return newBoard;
    }

    public static boolean gameEnd(int[][] currBoard) {

        int availableBlack = BoardHelper.getAllValidMoves(currBoard,Player.BLACK).size();
        int availableWhite = BoardHelper.getAllValidMoves(currBoard,Player.WHITE).size();

        if(availableBlack == 0 && availableWhite == 0){
            return true;
        }else{
            return false;
        }
    }



}