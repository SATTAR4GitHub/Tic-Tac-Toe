package tictactoe;

/**
 *
 * @author sattar-dell
 */
public class MiniMaxAI {

    public static TicTacToeNode.CellState bot = TicTacToeNode.CellState.X;
    public static TicTacToeNode.CellState person = TicTacToeNode.CellState.O;

    public static Integer[] getBestMove(TicTacToeNode ticTacToeNode) {
        Integer[] bestMove = null;
        int bestScore = -10000;
        for (Integer[] position : ticTacToeNode.getPositions()) {
            ticTacToeNode.Place(position, bot);
            int score = minimaxScoring(ticTacToeNode);
            ticTacToeNode.Place(position, TicTacToeNode.CellState.BLANK);
            if (score > bestScore) {
                bestScore = score;
                bestMove = position;
            }
        }
        return bestMove;
    }

    public static int minimaxScoring(TicTacToeNode ticTacToeNode) {
        TicTacToeNode.CellState winner = ticTacToeNode.checkWhoWon();
        int score;
        if (winner != null) {
            if (winner == bot) {
                score = 100;
                return score;
            } else if (winner == TicTacToeNode.CellState.BLANK) {
                score = 0;
                return score;
            } else {
                score = -100;
                return score;
            }
        }

        int botCount = 0;
        int personCount = 0;

        for (TicTacToeNode.CellState[] board : ticTacToeNode.getBoard()) {
            for (TicTacToeNode.CellState board1 : board) {
                if (board1 == bot) {
                    botCount++;
                } else if (board1 == person) {
                    personCount++;
                }
            }
        }

        int bestScore;
        if (personCount > botCount) {
            bestScore = -100;
        } else {
            bestScore = 100;
        }

        for (Integer[] position : ticTacToeNode.getPositions()) {
            ticTacToeNode.Place(position, personCount > botCount ? bot : person);
            int currentScore = minimaxScoring(ticTacToeNode);
            ticTacToeNode.Place(position, TicTacToeNode.CellState.BLANK);
            if (personCount > botCount ? currentScore > bestScore : currentScore < bestScore) {
                bestScore = currentScore;
            }
        }
        return bestScore;
    }
}
