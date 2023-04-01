package com.abx.fiveInARow;

import java.util.Scanner;

/**
 * @author bxa
 * 在原有程序落子的判断与胜利的判断基础上新增策略下棋的实现。下棋策略的实现是通过计算每个位置的分数，选择分数最高的位置进行落子。
 * 后期可以让策略在实现上进行优化，比如可以加入一些AI学习机制等。
 * 五子棋游戏V3
 */
public class FiveInARow03 {
    public static void main(String[] args) {
        FiveInARow03 game = new FiveInARow03();
        game.start();
    }

    private static final int BOARD_SIZE = 15;
    private static final char EMPTY = ' ';
    private static final char BLACK = 'X';
    private static final char WHITE = 'O';

    private char[][] board = new char[BOARD_SIZE][BOARD_SIZE];

    private boolean blackTurn = true;

    public void start() {
        // 初始化棋盘
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = EMPTY;
            }
        }

        // 游戏循环
        while (true) {
            printBoard();

            if (checkWin()) {
                System.out.println(blackTurn ? "黑方胜利！" : "白方胜利！");
                break;
            }

            // 提示当前轮到哪个玩家
            if (blackTurn) {
                // 根据分数落子
                int[] scoreMove = scoreStrategy(board, BLACK);
                int x = scoreMove[0];
                int y = scoreMove[1];
                if (!isValidMove(x, y)) {
                    // 根据分数落子
                    scoreMove = scoreStrategy(board, BLACK);
                    x = scoreMove[0];
                    y = scoreMove[1];
                }
                board[x][y] = BLACK;
            } else {
                System.out.println("白方落子：");
                Scanner scanner = new Scanner(System.in);
                int x = scanner.nextInt();
                int y = scanner.nextInt();
                if (!isValidMove(x, y)) {
                    System.out.println("该位置已有落子，请重新选择！");
                    continue;
                }
                board[x][y] = WHITE;
            }

            // 切换玩家
            blackTurn = !blackTurn;
        }
    }

    /**
     * 打印当前棋盘状态
     */
    private void printBoard() {
        System.out.print(" ");
        for (int i = 0; i < BOARD_SIZE; i++) {
            System.out.print(" " + i);
        }
        System.out.println();

        for (int i = 0; i < BOARD_SIZE; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < BOARD_SIZE; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    /**
     * 检查是否胜利
     *
     * @return 是否胜利
     */
    private boolean checkWin() {
        // 检查每一行
        for (int i = 0; i < BOARD_SIZE; i++) {
            int count = 0;
            for (int j = 0; j < BOARD_SIZE; j++) {
                count = board[i][j] == (blackTurn ? BLACK : WHITE) ? count + 1 : 0;
                if (count >= 5) {
                    return true;
                }
            }
        }

        // 检查每一列
        for (int i = 0; i < BOARD_SIZE; i++) {
            int count = 0;
            for (int j = 0; j < BOARD_SIZE; j++) {
                count = board[j][i] == (blackTurn ? BLACK : WHITE) ? count + 1 : 0;
                if (count >= 5) {
                    return true;
                }
            }
        }

        // 检查主对角线
        for (int i = 0; i <= BOARD_SIZE - 5; i++) {
            for (int j = 0; j <= BOARD_SIZE - 5; j++) {
                boolean win = true;
                for (int k = 0; k < 5; k++) {
                    if (board[i + k][j + k] != (blackTurn ? BLACK : WHITE)) {
                        win = false;
                        break;
                    }
                }
                if (win) {
                    return true;
                }
            }
        }

        // 检查副对角线
        for (int i = 0; i <= BOARD_SIZE - 5; i++) {
            for (int j = BOARD_SIZE - 1; j >= 4; j--) {
                boolean win = true;
                for (int k = 0; k < 5; k++) {
                    if (board[i + k][j - k] != (blackTurn ? BLACK : WHITE)) {
                        win = false;
                        break;
                    }
                }
                if (win) {
                    return true;
                }
            }
        }
        // 没有胜利状态
        return false;
    }

    /**
     * 判断落子是否合法
     *
     * @param x 横坐标
     * @param y 纵坐标
     * @return 落子是否合法
     */
    private boolean isValidMove(int x, int y) {
        if (x < 0 || x >= BOARD_SIZE || y < 0 || y >= BOARD_SIZE) {
            return false;
        }

        return board[x][y] == EMPTY;
    }

    /**
     * 根据分数落子
     *
     * @param board 棋盘
     * @param player 玩家
     * @return 落子位置
     */
    private int[] scoreStrategy(char[][] board, char player) {
        int[] move = new int[2];
        int maxScore = -1;
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j] == EMPTY) {
                    int score = calculateScore(board, i, j, player);
                    if (score > maxScore) {
                        maxScore = score;
                        move[0] = i;
                        move[1] = j;
                    }
                }
            }
        }
        return move;
    }

    /**
     * 计算落子位置的分数
     *
     * @param board 棋盘
     * @param x 横坐标
     * @param y 纵坐标
     * @param player 玩家
     * @return 落子位置的分数
     */
    private int calculateScore(char[][] board, int x, int y, char player) {
        int score = 0;
        // 计算横向分数
        for (int i = Math.max(0, x - 4); i <= Math.min(BOARD_SIZE - 1, x + 4); i++) {
            if (board[i][y] == player) {
                score++;
            }
        }
        // 计算纵向分数
        for (int i = Math.max(0, y - 4); i <= Math.min(BOARD_SIZE - 1, y + 4); i++) {
            if (board[x][i] == player) {
                score++;
            }
        }
        // 计算主对角线分数
        for (int i = Math.max(x - 4, 0), j = Math.max(y - 4, 0); i <= Math.min(x + 4, BOARD_SIZE - 1) && j <= Math.min(y + 4, BOARD_SIZE - 1); i++, j++) {
            if (board[i][j] == player) {
                score++;
            }
        }
        // 计算副对角线分数
        for (int i = Math.min(x + 4, BOARD_SIZE - 1), j = Math.max(y - 4, 0); i >= Math.max(x - 4, 0) && j <= Math.min(y + 4, BOARD_SIZE - 1); i--, j++) {
            if (board[i][j] == player) {
                score++;
            }
        }
        if (player == BLACK) {
            score += x * 0.1;
        } else {
            score += (BOARD_SIZE - x) * 0.1;
        }
        return score;
    }
}