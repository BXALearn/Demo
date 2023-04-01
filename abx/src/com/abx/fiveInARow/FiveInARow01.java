package com.abx.fiveInARow;

import java.util.Scanner;

/**
 * @author bxa
 * 程序初始化一个15x15的棋盘，第一个在横向纵向或对角线上连成五个子的玩家获胜。
 * 五子棋游戏V1
 */
public class FiveInARow01 {
    // 定义井字棋的棋盘大小为3x3
    private static final int BOARD_SIZE = 3;
    // 定义玩家和电脑的符号
    private static final char PLAYER_SYMBOL = 'X';
    private static final char AI_SYMBOL = 'O';
    // 定义空位的符号
    private static final char EMPTY_SYMBOL = '-';
    // 二维数组表示井字棋的棋盘
    private char[][] board;

    /**
     * 主函数，创建井字棋对象并开始游戏
     */
    public static void main(String[] args) {
        FiveInARow01 ticTacToe = new FiveInARow01();
        ticTacToe.play();
    }

    /**
     * 开始游戏，交替进行玩家和电脑回合
     */
    public void play(){
        Scanner scanner = new Scanner(System.in);
        // 玩家先手
        boolean playerTurn = false;
        boolean gameOver = false;
        while (!gameOver) {
            if (playerTurn) {
                // 玩家回合
                printBoard();
                int[] move = getPlayerMove(scanner, PLAYER_SYMBOL);
                makeMove(move[0], move[1], PLAYER_SYMBOL);
                if (checkGameOver(PLAYER_SYMBOL)) {
                    System.out.println("恭喜你获胜了！");
                    gameOver = true;
                } else if (isDraw()) {
                    System.out.println("平局！");
                    gameOver = true;
                }
                playerTurn = false;
            } else {
                // 电脑回合
                int[] move = getAIMove();
                makeMove(move[0], move[1], AI_SYMBOL);
                System.out.println("电脑落子位置为：" + (move[0] + 1) + " " + (move[1] + 1));
                if (checkGameOver(AI_SYMBOL)) {
                    System.out.println("很遗憾，电脑获胜了！");
                    gameOver = true;
                } else if (isDraw()) {
                    System.out.println("平局！");
                    gameOver = true;
                }
                playerTurn = true;
            }
        }
        printBoard();
        scanner.close();
    }

    /**
     * 构造函数，初始化井字棋棋盘
     */
    public FiveInARow01() {
        board = new char[BOARD_SIZE][BOARD_SIZE];
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = EMPTY_SYMBOL;
            }
        }
    }

    /**
     * 打印当前的井字棋棋盘
     */
    private void printBoard() {
        System.out.println("-------");
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                System.out.print(board[i][j] + "|");
            }
            System.out.println();
            System.out.println("-------");
        }
    }

    /**
     * 玩家回合，获取玩家需要落子的位置
     * @param scanner 输入流对象，用于读取用户输入
     * @param symbol 玩家的符号
     * @return 落子位置的行列数组
     */
    private int[] getPlayerMove(Scanner scanner, char symbol) {
        while (true) {
            System.out.println("请输入落子位置，如 1 1 表示第一行第一列：");
            int row = scanner.nextInt();
            int col = scanner.nextInt();

            // 检查输入的位置是否合法
            if (row < 1 || row > BOARD_SIZE || col < 1 || col > BOARD_SIZE) {
                System.out.println("输入的位置不合法，请重新输入！");
            } else if (board[row - 1][col - 1] != EMPTY_SYMBOL) {
                System.out.println("该位置已经有棋子了，请重新输入！");
            } else {
                return new int[]{row - 1, col - 1};
            }
        }
    }

    /**
     * 在指定位置落子
     * @param row 落子的行数
     * @param col 落子的列数
     * @param symbol 落子的符号
     */
    private void makeMove(int row, int col, char symbol) {
        board[row][col] = symbol;
    }

    /**
     * 检查游戏是否结束
     * @param symbol 最后一个落子的符号
     * @return 是否结束
     */
    private boolean checkGameOver(char symbol) {
        // 检查行
        for (int i = 0; i < BOARD_SIZE; i++) {
            boolean win = true;
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j] != symbol) {
                    win = false;
                    break;
                }
            }
            if (win) {
                return true;
            }
        }

        // 检查列
        for (int j = 0; j < BOARD_SIZE; j++) {
            boolean win = true;
            for (int i = 0; i < BOARD_SIZE; i++) {
                if (board[i][j] != symbol) {
                    win = false;
                    break;
                }
            }
            if (win) {
                return true;
            }
        }
        // 检查正对角线
        boolean win = true;
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (board[i][i] != symbol) {
                win = false;
                break;
            }
        }
        if (win) {
            return true;
        }
        // 检查反对角线
        win = true;
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (board[i][BOARD_SIZE - i - 1] != symbol) {
                win = false;
                break;
            }
        }
        if (win) {
            return true;
        }
        // 没有获胜
        return false;
    }

    /**
     * 检查棋盘是否已满，即是否平局
     * @return 是否平局
     */
    private boolean isDraw() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j] == EMPTY_SYMBOL) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 获取电脑落子的位置
     * @return 落子位置的行列数组
     */
    private int[] getAIMove() {
        // 首先检查是否可以直接获胜
        int[] winningMove = getWinningMove(AI_SYMBOL);
        if (winningMove != null) {
            return winningMove;
        }

        // 其次检查是否需要阻止玩家获胜
        int[] blockingMove = getWinningMove(PLAYER_SYMBOL);
        if (blockingMove != null) {
            return blockingMove;
        }

        // 然后考虑是否占据中心位置
        if (board[1][1] == EMPTY_SYMBOL) {
            return new int[]{1, 1};
        }

        // 再考虑是否占据角落
        if (board[0][0] == EMPTY_SYMBOL) {
            return new int[]{0, 0};
        }
        if (board[0][BOARD_SIZE - 1] == EMPTY_SYMBOL) {
            return new int[]{0, BOARD_SIZE - 1};
        }
        if (board[BOARD_SIZE - 1][0] == EMPTY_SYMBOL) {
            return new int[]{BOARD_SIZE - 1, 0};
        }
        if (board[BOARD_SIZE - 1][BOARD_SIZE - 1] == EMPTY_SYMBOL) {
            return new int[]{BOARD_SIZE - 1, BOARD_SIZE - 1};
        }

        // 最后考虑占据边缘位置
        for (int i = 0; i < BOARD_SIZE; i += 2) {
            if (board[i][0] == EMPTY_SYMBOL) {
                return new int[]{i, 0};
            }
            if (board[i][BOARD_SIZE - 1] == EMPTY_SYMBOL) {
                return new int[]{i, BOARD_SIZE - 1};
            }
            if (board[0][i] == EMPTY_SYMBOL) {
                return new int[]{0, i};
            }
            if (board[BOARD_SIZE - 1][i] == EMPTY_SYMBOL) {
                return new int[]{BOARD_SIZE - 1, i};
            }
        }

        // 棋盘已满，无法落子
        return null;
    }

    /**
     * 获取获胜或需要阻止的位置
     * @param symbol 玩家或电脑的符号
     * @return 落子位置的行列数组，如果不存在，则返回null
     */
    private int[] getWinningMove(char symbol) {
        // 检查行
        for (int i = 0; i < BOARD_SIZE; i++) {
            int emptyCount = 0;
            int rowEmptyIndex = -1;
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j] == symbol) {
                    // 行已经有玩家的符号了，不是获胜或需要阻止的位置
                    break;
                } else if (board[i][j] == EMPTY_SYMBOL) {
                    emptyCount++;
                    rowEmptyIndex = j;
                }
            }
            if (emptyCount == 1) {
                return new int[]{i, rowEmptyIndex};
            }
        }
        // 检查列
        for (int j = 0; j < BOARD_SIZE; j++) {
            int emptyCount = 0;
            int colEmptyIndex = -1;
            for (int i = 0; i < BOARD_SIZE; i++) {
                if (board[i][j] == symbol) {
                    // 列已经有玩家的符号了，不是获胜或需要阻止的位置
                    break;
                } else if (board[i][j] == EMPTY_SYMBOL) {
                    emptyCount++;
                    colEmptyIndex = i;
                }
            }
            if (emptyCount == 1) {
                return new int[]{colEmptyIndex, j};
            }
        }
        // 检查正对角线
        int emptyCount = 0;
        int diagonalEmptyIndex = -1;
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (board[i][i] == symbol) {
                // 对角线已经有玩家的符号了，不是获胜或需要阻止的位置
                break;
            } else if (board[i][i] == EMPTY_SYMBOL) {
                emptyCount++;
                diagonalEmptyIndex = i;
            }
        }
        if (emptyCount == 1) {
            return new int[]{diagonalEmptyIndex, diagonalEmptyIndex};
        }
        // 检查反对角线
        emptyCount = 0;
        diagonalEmptyIndex = -1;
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (board[i][BOARD_SIZE - i - 1] == symbol) {
                // 对角线已经有玩家的符号了，不是获胜或需要阻止的位置
                break;
            } else if (board[i][BOARD_SIZE - i - 1] == EMPTY_SYMBOL) {
                emptyCount++;
                diagonalEmptyIndex = i;
            }
        }
        if (emptyCount == 1) {
            return new int[]{diagonalEmptyIndex, BOARD_SIZE - diagonalEmptyIndex - 1};
        }
        // 没有获胜或需要阻止的位置
        return null;
    }
}