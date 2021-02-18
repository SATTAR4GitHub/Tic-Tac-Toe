package tictactoe;

// version 201935.000

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TicTacToeNode {

    static int nodes = 0;
    char[][] board;
    ArrayList<TicTacToeNode> children;
    int xWins = 0;
    int oWins = 0;
    int minimaxScore = 0;

    public TicTacToeNode() {
        nodes++;
        board = new char[3][3];
        children = new ArrayList<>();
    }

    @Override
    public String toString() {
        String boardStr;

        boardStr = "---\n";
        boardStr += Arrays.toString(board[0]) + "\n";
        boardStr += Arrays.toString(board[1]) + "\n";
        boardStr += Arrays.toString(board[2]) + "\n";
        boardStr += "---\n";

        return boardStr;
    }
private final CellState[][] boardState = new CellState[][]{
        {CellState.BLANK, CellState.BLANK, CellState.BLANK},
        {CellState.BLANK, CellState.BLANK, CellState.BLANK},
        {CellState.BLANK, CellState.BLANK, CellState.BLANK}
    };
    public boolean isGameWon() {
        CellState winner = checkWhoWon();
        return winner != null;
    }

    public CellState checkWhoWon() {
        CellState checkingPlayer;
        //check columns
        for (int i = 0; i < boardState.length; i++) {
            checkingPlayer = boardState[0][i];
            if (checkingPlayer == CellState.BLANK) {
                continue;
            }
            for (int j = 0; j < boardState[i].length; j++) {
                if (boardState[j][i] != checkingPlayer) {
                    checkingPlayer = null;
                    break;
                }
            }
            if (checkingPlayer != null) {
                return checkingPlayer;
            }
        }
        //check rows
        for (CellState[] cellStates : boardState) {
            checkingPlayer = cellStates[0];
            if (checkingPlayer == CellState.BLANK) {
                continue;
            }
            for (CellState cellState : cellStates) {
                if (cellState != checkingPlayer) {
                    checkingPlayer = null;
                    break;
                }
            }
            if (checkingPlayer != null) {
                return checkingPlayer;
            }
        }
        //check diagonals
        checkingPlayer = boardState[0][0];
        if (boardState[1][1] == checkingPlayer && boardState[2][2] == checkingPlayer && checkingPlayer != CellState.BLANK) {
            return checkingPlayer;
        }

        checkingPlayer = boardState[2][0];
        if (boardState[1][1] == checkingPlayer && boardState[0][2] == checkingPlayer && checkingPlayer != CellState.BLANK) {
            return checkingPlayer;
        }
        //check tie;
        for (CellState[] cellStates : boardState) {
            for (CellState cellState : cellStates) {
                if (cellState == CellState.BLANK) {
                    return null;
                }
            }
        }

        return CellState.BLANK;
    }

    public boolean isFull() {
        boolean full = true;

        for (char[] row : board) {
            for (char pos : row) {
                full &= (pos != Character.MIN_VALUE);
            }
        }
        return full;
    }

    public char[][] copyBoard() {
        // Java's .clone() does not handle 2D arrays
        // by copying values, rather we get a clone of refs
        char[][] boardCopy = new char[3][3];

        for ( int i = 0; i < 3; i ++ )
            System.arraycopy(board[i], 0, boardCopy[i], 0, 3);
        
        return boardCopy;
    }
    
    public void displayBoard() {
        for (int i = 0; i < boardState.length; i++) {
            System.out.print(2 - i + " ");
            for (int j = 0; j < boardState[i].length; j++) {
                if (boardState[i][j] != CellState.BLANK) {
                    System.out.print(boardState[i][j] + (j + 1 == boardState[i].length ? "" : "|"));
                } else {
                    System.out.print(" " + (j + 1 == boardState[i].length ? "" : "|"));
                }
            }
            System.out.print(i + 1 == boardState.length ? "" : "\n  -----\n");
        }
        System.out.println("\n  0 1 2");
        System.out.println();
    }

    public CellState[][] getBoard() {
        return boardState;
    }

    public List<Integer[]> getPositions() {
        ArrayList<Integer[]> availablePositions = new ArrayList<>();
        for (int i = 0; i < boardState.length; i++) {
            for (int j = 0; j < boardState[i].length; j++) {
                if (boardState[i][j] == TicTacToeNode.CellState.BLANK) {
                    availablePositions.add(new Integer[]{i, j});
                }
            }
        }
        return availablePositions;
    }

    public void Place(Integer[] location, CellState player) {
        boardState[location[0]][location[1]] = player;
    }

    public enum CellState {
        X, O, BLANK
    }

}
