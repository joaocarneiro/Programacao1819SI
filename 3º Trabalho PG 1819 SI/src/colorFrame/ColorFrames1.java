package colorFrame;

import isel.leic.pg.Console;
import org.jetbrains.annotations.Contract;
import panel.Panel1;

import static java.awt.event.KeyEvent.*;

public class ColorFrames1 {
    public static final int MAX_COLORS = 5;  // [1..9] Color used to generate random piece
    public static final int BOARD_DIM = 3;  // [2..4] Dimension (lines and columns) of board
    public static final int FRAMES_DIM = 3; // [1..4] Number of frames in each place of of board
    private static final int NO_FRAME = -1;  // Special color to mark frame absence
    private static final int BOARD_PLACES = BOARD_DIM * BOARD_DIM;

    /**
     * Random generated piece.
     * Each position has the color of each frame [0 .. MAX_COLORS-1] or NO_FRAME indicates that frame does not exist.
     * Each index corresponds to one dimension of the frame.
     */
    private static int[] piece = new int[FRAMES_DIM];
    private static int[][][] ultimateBoard = new int[BOARD_DIM][BOARD_DIM][FRAMES_DIM];
    private static int[] lineColors;
    private static int[] columnColors;
    private static int[] gridNumsByLine;
    private static int[] gridNumsByColumn;

    /**
     * Flag to finish the game
     */
    private static boolean terminate = false;
    private static int score = 0;

    public static void main(String[] args) {
        Panel1.init();
        Panel1.printMessage("Welcome");
        playGame();
        Panel1.printMessageAndWait("BYE");
        Panel1.end();
    }

    private static void playGame() {
        int key;
        for (int i = 0; i < ultimateBoard.length; ++i)
            for (int j = 0; j < ultimateBoard[i].length; ++j)
                for (int k = 0; k < ultimateBoard[i][j].length; ++k)
                    ultimateBoard[i][j][k] = NO_FRAME;

        generatePiece();
        printPiece();
        do {
            key = Console.waitKeyPressed(5000);
            if (key > 0) {
                processKey(key);
                Console.waitKeyReleased(key);
            } else Panel1.printMessage("");  // Clear last message
        } while (!terminate);
    }

    private static void processKey(int key) {
        if (key == VK_ESCAPE) terminate = true;
        int gridNum = 0;
        if (key >= VK_1 && key <= VK_9)
            gridNum = key - VK_1 + 1;
        else if (key >= VK_NUMPAD1 && key <= VK_NUMPAD9)
            gridNum = key - VK_NUMPAD1 + 1;
        else if (key >= VK_A && key <= VK_Z)
            gridNum = key - VK_A + 10;
        if (gridNum >= 1 && gridNum <= BOARD_PLACES) {
            if (validateBoardPlace(gridNum)) {
                putPieceInBoard(gridNum);
                Panel1.printMessage("Ok");
                updateSecondaryBoardMatrix(gridNum);
                checkPositionsForVictory(gridNum);
                generatePiece();
                printPiece();
                if (checkIfBoardIsFull())
                    terminate = true;
            } else
                Panel1.printMessage("Invalid;Place");
        }
    }

    private static boolean validateBoardPlace(int gridNum) {
        int line = checkLine(gridNum);
        int col = checkCol(line, gridNum);
        for (int k = 0; k < ultimateBoard[line][col].length; ++k)
            if (piece[k] != NO_FRAME)
                if (ultimateBoard[line][col][k] != NO_FRAME)
                    return false;
        return true;
    }

    @Contract(pure = true)
    private static int checkLine(int gridNum) {
        if (gridNum > 0 && gridNum <= BOARD_DIM)
            return 0;
        if (gridNum > BOARD_DIM && gridNum <= BOARD_DIM * 2)
            return 1;
        if (gridNum > BOARD_DIM * 2 && gridNum <= BOARD_DIM * 3)
            return 2;
        return 3;
    }

    @Contract(pure = true)
    private static int checkCol(int line, int gridNum) {
        return gridNum - BOARD_DIM * line - 1;
    }

    private static void updateSecondaryBoardMatrix(int gridNum) {
        int line = checkLine(gridNum);
        int col = checkCol(line, gridNum);
        for (int k = 0; k < ultimateBoard[line][col].length; ++k)
            if (piece[k] != NO_FRAME)
                ultimateBoard[line][col][k] = piece[k];
    }

    private static void checkPositionsForVictory(int gridNum) {
        boolean cellToRemove = checkCell(gridNum);
        boolean lineToRemove = checkLines(gridNum);
        boolean columnToRemove = checkColumns(gridNum);
        clearFrames(gridNum, cellToRemove, lineToRemove, columnToRemove);
//        if(line == 0 || line == BOARD_DIM-1 && col == 0 || col == BOARD_DIM-1) {
//          diagonalToRemove(gridNum);
//        }
    }

    public static int[] populateGridNumsByColumn(int col) {
        int[] gridNumColumn = new int[BOARD_DIM];
        for (int i = 0; i < gridNumColumn.length; ++i)
            gridNumColumn[i] = BOARD_DIM * (i + 1) - (BOARD_DIM - col - 1);
        return gridNumColumn;
    }

    private static boolean checkColumns(int gridNum) {
        int line = checkLine(gridNum);
        int col = checkCol(line, gridNum);
        gridNumsByColumn = populateGridNumsByColumn(col);
        columnColors = new int[FRAMES_DIM];
        boolean colorToRemove = false;
        for (int f = 0; f < columnColors.length; ++f)
            columnColors[f] = NO_FRAME;
        int color = NO_FRAME;
        for (int frame = 0; frame < FRAMES_DIM; ++frame) {
            for (int nextLine = 1; nextLine < BOARD_DIM; ++nextLine) {
                for (int nextLineFrame = 0; nextLineFrame < FRAMES_DIM; ++nextLineFrame) {
                    if (ultimateBoard[0][col][frame] == ultimateBoard[nextLine][col][nextLineFrame] && ultimateBoard[0][col][frame] != NO_FRAME) {
                        if (nextLine == BOARD_DIM - 1 && color == ultimateBoard[nextLine][col][nextLineFrame])
                            colorToRemove = true;
                        if (nextLine < BOARD_DIM - 1)
                            color = ultimateBoard[0][col][frame];
                        break;
                    }
                }
                if (color == NO_FRAME)
                    break;
            }
            int f = 0;
            if (colorToRemove) {
                while (color != NO_FRAME) {
                    if (columnColors[f] == color) {
                        color = NO_FRAME;
                        break;
                    }
                    if (columnColors[f] == NO_FRAME) {
                        columnColors[f] = color;
                        color = NO_FRAME;
                    } else
                        ++f;
                }
            }
            colorToRemove = false;
        }
        for (int f = 0; f < FRAMES_DIM; ++f)
            if (columnColors[f] != NO_FRAME)
                return true;
        return false;
    }

    private static void clearFrames(int gridNum, boolean cellToRemove, boolean lineToRemove, boolean columnToRemove) {
        int line = checkLine(gridNum);
        int col = checkCol(line, gridNum);
        if (cellToRemove) {
            Panel1.clearFrame(0, gridNum);
            Panel1.clearFrame(1, gridNum);
            Panel1.clearFrame(2, gridNum);
        }
//        if(lineToRemove){
//            for(int colI = 0; colI < BOARD_DIM; ++colI) {
//                for (int frame = 0; frame < FRAMES_DIM; ++frame)
//                    for (int color = 0; color < lineColors.length; ++color)
//                        if (ultimateBoard[line][colI][frame] == lineColors[color]) {
//                            ultimateBoard[line][colI][frame] = NO_FRAME;
//                            Panel1.clearFrame(frame, gridNumsByLine[colI]);
//                        }
//            }
//        }
        if (columnToRemove) {
            for (int lineI = 0; lineI < BOARD_DIM; ++lineI) {
                for (int frame = 0; frame < FRAMES_DIM; ++frame)
                    for (int color = 0; color < columnColors.length; ++color)
                        if (ultimateBoard[lineI][col][frame] == columnColors[color]) {
                            ultimateBoard[lineI][col][frame] = NO_FRAME;
                            Panel1.clearFrame(frame, gridNumsByColumn[lineI]);
                        }
            }
        }
    }

    private static boolean checkCell(int gridNum) {
        int line = checkLine(gridNum);
        int col = checkCol(line, gridNum);
        for (int k = 0, j = 1; j < ultimateBoard[line][col].length; ++k, ++j) {
            if (ultimateBoard[line][col][k] != ultimateBoard[line][col][j])
                return false;
        }
        for (int k = 0; k < ultimateBoard[line][col].length; ++k)
            ultimateBoard[line][col][k] = NO_FRAME;
        score += FRAMES_DIM;
        Panel1.printScore(score);
        return true;
//
    }

    public static int[] populateGridNumsByLine(int line) {
        int[] gridNumLine = new int[BOARD_DIM];
        for (int i = 0; i < gridNumLine.length; ++i)
            gridNumLine[i] = BOARD_DIM * line + i + 1;
        return gridNumLine;
    }

    public static boolean checkLines(int gridNum) {
        int line = checkLine(gridNum);
        gridNumsByLine = populateGridNumsByLine(line);
        lineColors = new int[FRAMES_DIM];
        boolean colorToRemove = false;
        for (int f = 0; f < lineColors.length; ++f)
            lineColors[f] = NO_FRAME;
        int color = NO_FRAME;
        for (int frame = 0; frame < FRAMES_DIM; ++frame) {
            for (int nextCol = 1; nextCol < BOARD_DIM; ++nextCol) {
                for (int nextColFrame = 0; nextColFrame < FRAMES_DIM; ++nextColFrame) {
                    if (ultimateBoard[line][0][frame] == ultimateBoard[line][nextCol][nextColFrame] && ultimateBoard[line][0][frame] != NO_FRAME) {
                        if (nextCol == BOARD_DIM - 1 && color == ultimateBoard[line][nextCol][nextColFrame])
                            colorToRemove = true;
                        if (nextCol < BOARD_DIM - 1)
                            color = ultimateBoard[line][0][frame];
                        break;
                    }
                }
                if (color == NO_FRAME)
                    break;
            }
            int f = 0;
            if (colorToRemove) {
                while (color != NO_FRAME) {
                    if (lineColors[f] == color) {
                        color = NO_FRAME;
                        break;
                    }
                    if (lineColors[f] == NO_FRAME) {
                        lineColors[f] = color;
                        color = NO_FRAME;
                    } else
                        ++f;
                }
            }
            colorToRemove = false;
        }
        for (int f = 0; f < FRAMES_DIM; ++f)
            if (lineColors[f] != NO_FRAME)
                return true;
        return false;
    }

    public static boolean checkIfBoardIsFull() {
        for (int i = 0; i < ultimateBoard.length; ++i)
            for (int j = 0; j < ultimateBoard[i].length; ++j)
                for (int k = 0; k < ultimateBoard[i][j].length; ++k)
                    if (ultimateBoard[i][j][k] == NO_FRAME)
                        return false;
        return true;
    }

    private static void putPieceInBoard(int gridNum) {
        for (int f = 0; f < FRAMES_DIM; ++f) { // For each frame dimension
            int color = piece[f];
            if (color != NO_FRAME) {
                Panel1.printFrame(f, color, gridNum); // Displays the frame with (f) dimension and (color)
                Panel1.clearFrame(f, 0);             // Clean the frame of piece.
            }
        }
    }

    private static void generatePiece() {
        int kNumber = 1 + validateBoardCells();
        int numOfFrames = 1 + (int) (Math.random() * (kNumber - 1)); // Frames to generate
        do {
            for (int f = 0; f < FRAMES_DIM; ++f)
                piece[f] = NO_FRAME;
            for (int i = 0; i < numOfFrames; ++i) {
                int frameSize;
                do frameSize = (int) (Math.random() * FRAMES_DIM); // Selects a free random dimension
                while (piece[frameSize] != NO_FRAME);
                piece[frameSize] = (int) (Math.random() * MAX_COLORS); // Generate random color
            }
            if (validateBoardCells() <= 0) break;
        } while (!validatePieceCombinations(piece));
    }

    @Contract(pure = true)
    public static int validateBoardCells() {
        int framesToGenerate = 0;
        int larger = 0;
        for (int i = 0; i < ultimateBoard.length; ++i)
            for (int j = 0; j < ultimateBoard[i].length; ++j) {
                for (int k = 0; k < ultimateBoard[i][j].length; ++k)
                    if (ultimateBoard[i][j][k] == NO_FRAME) ++framesToGenerate;
                if (framesToGenerate > larger)
                    larger = framesToGenerate;
                framesToGenerate = 0;
            }
        return larger;
    }

    @Contract(pure = true)
    public static boolean validatePieceCombinations(int[] piece) {
        int k;
        for (int i = 0; i < ultimateBoard.length; ++i)
            for (int j = 0; j < ultimateBoard[i].length; ++j) {
                for (k = 0; k < ultimateBoard[i][j].length; ++k) {
                    if (piece[k] != NO_FRAME && ultimateBoard[i][j][k] == NO_FRAME || piece[k] == NO_FRAME) {}
                    else
                        break;
                }
                if(k>=ultimateBoard[i][j].length) return true;
            }
        return false;
    }

    private static void printPiece() {
        for (int f = 0; f < FRAMES_DIM; ++f) {  // For each frame dimension
            int color = piece[f];
            if (color == NO_FRAME) Panel1.clearFrame(f, 0);  // Clean if does not exist with (f) dimension
            else Panel1.printFrame(f, color, 0);      // or Displays the frame with (f) dimension and (color)
        }
    }
}
