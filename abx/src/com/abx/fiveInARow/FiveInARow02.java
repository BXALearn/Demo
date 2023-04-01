package com.abx.fiveInARow;

import java.util.Random;
import java.util.Scanner;

/**
 * @author bxa
 * 程序初始化一个15x15的棋盘，第一个在横向纵向或对角线上连成五个子的玩家获胜。
 * 在上一个基础上新增白棋随机落子支持一个玩家也能参与游戏的机会。
 * 五子棋游戏V2
 */
public class FiveInARow02 {
    public static void main(String[] args) {
        FiveInARow02 game = new FiveInARow02();
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
                System.out.println("黑方落子：");
                Scanner scanner = new Scanner(System.in);
                int x = scanner.nextInt();
                int y = scanner.nextInt();
                if (!isValidMove(x, y)) {
                    System.out.println("该位置已有落子，请重新选择！");
                    continue;
                }
                board[x][y] = BLACK;
            } else {
                System.out.println("白方落子：");
                Random random = new Random();
                int x = random.nextInt(BOARD_SIZE);
                int y = random.nextInt(BOARD_SIZE);
                if (!isValidMove(x, y)) {
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



}