package colorFrame;

import isel.leic.pg.Console;
import org.jetbrains.annotations.Contract;
import panel.Panel;
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
                Panel.printMessage("Ok");
                updateSecondaryBoardMatrix(gridNum);
                generatePiece();
                printPiece();
                checkPositionsForVictory(gridNum);
                if (checkIfBoardIsFull())
                    terminate = true;
            } else
                Panel.printMessage("Invalid;Place");
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
        return 2;
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
        checkCell(gridNum);
//        checkLines(gridNum);
//        checkColumns(gridNum);
//        checkDiagonals(gridNum);
    }

    private static void checkCell(int gridNum) {
        int line = checkLine(gridNum);
        int col = checkCol(line, gridNum);
        int count = 0;
        for (int k = 0, j = 1; j < ultimateBoard[line][col].length; ++k, ++j) {
            if (ultimateBoard[line][col][k] == ultimateBoard[line][col][j])
                count++;
            else
                break;
        }
        if (count == FRAMES_DIM - 1) {
            for (int k = 0; k < ultimateBoard[line][col].length; ++k)
                ultimateBoard[line][col][k] = NO_FRAME;
            Panel.clearFrame(0, gridNum);
            Panel.clearFrame(1, gridNum);
            Panel.clearFrame(2, gridNum);
            score += FRAMES_DIM;
            Panel.printScore(score);
        }
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
        for (int f = 0; f < FRAMES_DIM; ++f)
            piece[f] = NO_FRAME;
        int kNumber = 1 + validateBoardCells();
        boolean s = validatePieceOnBoardBySize(0); //returns true if there is space for small pieces
        boolean m = validatePieceOnBoardBySize(1); //returns true if there is space for medium pieces
        boolean l = validatePieceOnBoardBySize(2); //returns true if there is space for large pieces
        int numOfFrames = 1 + (int) (Math.random() * (kNumber - 1)); // Frames to generate
        do {
            for (int i = 0; i < numOfFrames; ++i) {
                int frameSize;
                do frameSize = (int) (Math.random() * FRAMES_DIM); // Selects a free random dimension
                while (piece[frameSize] != NO_FRAME);
                piece[frameSize] = (int) (Math.random() * MAX_COLORS); // Generate random color
            }
            if (!s && !m && !l) break;
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
    public static boolean validatePieceOnBoardBySize(int size) {
        for (int i = 0; i < ultimateBoard.length; ++i)
            for (int j = 0; j < ultimateBoard[i].length; ++j)
                if (ultimateBoard[i][j][size] == NO_FRAME)
                    return true;
        return false;
    }

    @Contract(pure = true)
    public static boolean validatePieceCombinations(int[] piece) {
        for (int i = 0; i < ultimateBoard.length; ++i)
            for (int j = 0; j < ultimateBoard[i].length; ++j) {
                for (int k = 0; k < ultimateBoard[i][j].length; ++k) {
                    if (piece[k] != NO_FRAME && ultimateBoard[i][j][k] == NO_FRAME || piece[k] == NO_FRAME) {
                    } else
                        break;
                }

            }

        int i = 0, j, k, f;
        for (i = 0; i < BOARD_PLACES * 3; i += 3) {
            for (j = i, k = i + 2, f = 0; j <= k; ++j, ++f) {
                if (j <= k) {
                    if (auxPiece[f] != NO_FRAME && boardMatrix[j] == NO_FRAME || auxPiece[f] == NO_FRAME) {
                    } else break;
                }
            }
            f = 0;
            if (j > k) return true;
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
