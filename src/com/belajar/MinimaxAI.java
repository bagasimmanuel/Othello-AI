package com.belajar;

public class MinimaxAI {

    public static int totalNodesExplored = 0;

    public Coordinate solve(int[][] board, int depth, int player) {

        int bestScore = Integer.MAX_VALUE;
        Coordinate bestMove = null;

        for (Coordinate move : BoardHelper.getAllValidMoves(board,player)) {

            int[][] newBoard = BoardHelper.makeMoveForAI(Othello.board,player,move.getRow(),move.getCol());
            int childScore = MM(newBoard,player,depth-1,false);
            if(bestScore > childScore ){
                bestScore = childScore;
                bestMove = move;
            }

        }
        System.out.println(totalNodesExplored);
        return bestMove;
    }


    private int MM(int[][] currBoard,int player,int depth,boolean max){
        totalNodesExplored++;
        if(depth == 0 || BoardHelper.gameEnd(currBoard)){
            int whitePiece = 0;
            for(int i = 0; i < currBoard.length;i++){
                for(int j = 0; j < currBoard.length;j++){
                    if(currBoard[i][j] == Player.WHITE){
                        whitePiece++;
                    }
                }
            }
            return whitePiece;
        }
        int oplayer = (player == 1) ? 2 : 1;

        if(max && BoardHelper.getAllValidMoves(currBoard,player).size() == 0 || (!max && BoardHelper.getAllValidMoves(currBoard
                ,oplayer).size() == 0)){
//            System.out.println("Currently gk bisa jalan woe, skip next player");
            return MM(currBoard,player,depth-1,!max);
        }
        int score;
        int[][] newBoard;
        if(max){
            //maximizing
            score = Integer.MIN_VALUE;
            for(Coordinate move : BoardHelper.getAllValidMoves(currBoard,player)){ //my turn
                //create new node

                newBoard = BoardHelper.makeMoveForAI(currBoard,player,move.getRow(), move.getCol());
                //recursive call
                int childScore = MM(newBoard,player,depth-1,false);
                if(childScore > score) score = childScore;
            }
        }else{
            //minimizing
            score = Integer.MAX_VALUE;
            for(Coordinate move : BoardHelper.getAllValidMoves(currBoard,oplayer)){ //opponent turn
                //create new node
                newBoard = BoardHelper.makeMoveForAI(currBoard,oplayer,move.getRow(), move.getCol());
                //recursive call
                int childScore = MM(newBoard,player,depth-1,true);
                if(childScore < score) score = childScore;
            }
        }
        return score;
    }

}
