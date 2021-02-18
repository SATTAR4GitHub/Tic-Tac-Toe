package tictactoe;

// version 201935.000

import java.util.Scanner;
import static tictactoe.MiniMaxAI.*;

public class PlayTicTacToe {

    static boolean DEBUG = false; // not final to allow local debug toggles, ie use debug in 1 method only

    public static int[] buildBoards(TicTacToeNode root, char symbol) {
        // A straight forward way to generate at most 9 children per level.
        // If the spot is already filled we skip that child, generating the
        // expected tree.  Note we create the child after the if check to
        // avoid throwing our count of nodes created off.
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                char[][] newBoard = root.copyBoard();
                if (newBoard[i][j] == Character.MIN_VALUE) {
                    newBoard[i][j] = symbol;
                    TicTacToeNode child = new TicTacToeNode();
                    child.board = newBoard;
                    root.children.add(child);
                    if (DEBUG) {
                        System.out.println(child);
                    }
                }
            }
        }

        for (TicTacToeNode child : root.children) {
            if (child.isGameWon()) {
                // stop creating branches, we are at a leaf node
                // do not return from here so that the other siblings
                // will be correctly generated
                if (child.isGameWon()) {
                    root.xWins++;
                    child.xWins++;
                }
                //if ( child.isGameWon('O') ) {root.oWins++; child.oWins++;}
            } else if (!child.isFull()) {
                // if the board is full there's nothing to do, simply iterate
                // to the next sibling.  If the board is not a win, nor a tie
                // then there are more children to create, thus the recursive
                // step appears here
                int[] scores;
                scores = buildBoards(child, symbol == 'O' ? 'X' : 'O');
                root.xWins += scores[0];
                root.oWins += scores[1];
            }
        }

        // Once all children are processed we can return the results of the parent
        // node.  To reach this return statement we will have already have created
        // all sub-branches and counted their scores.
        int[] scores = {root.xWins, root.oWins};
        return scores;
    }

    public static void main(String[] args) {

        TicTacToeNode tttTree = new TicTacToeNode();

        Scanner in = new Scanner(System.in);
        System.out.println("Unbeatable bot! Take a challenge to beat this AI.");
        tttTree.displayBoard();
        buildBoards(tttTree, 'X');
        System.out.println(TicTacToeNode.nodes + " nodes created.");

        while (tttTree.checkWhoWon() == null) {
            Integer[] pos;
            boolean inputValid;
            do {
                System.out.print("Your Turn, Enter Either 0,0 OR 0,1, OR 0,2 OR 1,0, OR 1,1 OR 1,2 OR 2,0, OR 2,1 OR 2,2:   ");
                inputValid = true;
                String[] position = in.nextLine().split(",");
                pos = new Integer[2];
                if (position.length < 2) {
                    inputValid = false;
                    continue;
                }
                try {
                    pos[0] = 2 - Integer.parseInt(position[1].trim());
                    pos[1] = Integer.parseInt(position[0].trim());
                } catch (NumberFormatException e) {
                    inputValid = false;
                }
                if (tttTree.getBoard()[pos[0]][pos[1]] != TicTacToeNode.CellState.BLANK) {
                    inputValid = false;
                }
            } while (!inputValid);

            tttTree.Place(pos, TicTacToeNode.CellState.O);

            tttTree.displayBoard();
            System.out.println();
            if (tttTree.checkWhoWon() != null) {
                break;
            }
            System.out.println("Bot's Turn");
            tttTree.Place(getBestMove(tttTree), TicTacToeNode.CellState.X);
            tttTree.displayBoard();
        }

        System.out.println("**************************");
        System.out.println("GAME OVER!");
        tttTree.displayBoard();
        if (tttTree.checkWhoWon() == bot) {
            System.out.println("RESULT: BOT WON!");
        } else if (tttTree.checkWhoWon() == person) {
            System.out.println("RESULT: YOU WON!");
        } else {
            System.out.println("RESULT: GAME TIE");
        }
    }
}
