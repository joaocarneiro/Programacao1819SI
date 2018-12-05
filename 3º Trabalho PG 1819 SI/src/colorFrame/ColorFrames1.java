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
    private static int[] diagonalColors;
    private static int[] gridNumsByLine;
    private static int[] gridNumsByColumn;
    private static int[] gridNumsByDiagonal;

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

    private static void generatePiece() {
        int kNumber = 1 + validateBoardCells();
        int numOfFrames = 1;// + (int) (Math.random() * (kNumber - 1)); // Frames to generate
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
                updateBoard();
                generatePiece();
                printPiece();
                if (checkIfBoardIsFull())
                    terminate = true;
            } else
                Panel1.printMessage("Invalid;Place");
        }
    }

    private static boolean validateBoardPlace(int gridNum) {
        int line = getLine(gridNum);
        int col = getCol(line, gridNum);
        for (int k = 0; k < ultimateBoard[line][col].length; ++k)
            if (piece[k] != NO_FRAME)
                if (ultimateBoard[line][col][k] != NO_FRAME)
                    return false;
        return true;
    }

    private static int getLine(int gridNum) {
        if (gridNum > 0 && gridNum <= BOARD_DIM)
            return 0;
        if (gridNum > BOARD_DIM && gridNum <= BOARD_DIM * 2)
            return 1;
        if (gridNum > BOARD_DIM * 2 && gridNum <= BOARD_DIM * 3)
            return 2;
        return 3;
    }

    private static int getCol(int line, int gridNum) {
        return gridNum - BOARD_DIM * line - 1;
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

    private static void updateSecondaryBoardMatrix(int gridNum) {
        int line = getLine(gridNum);
        int col = getCol(line, gridNum);
        for (int k = 0; k < ultimateBoard[line][col].length; ++k)
            if (piece[k] != NO_FRAME)
                ultimateBoard[line][col][k] = piece[k];
    }

    private static void checkPositionsForVictory(int gridNum) {
        boolean removeCell = checkCell(gridNum);
        boolean removeLine = checkLines(gridNum);
        boolean removeColumn = checkColumns(gridNum);
        boolean removeMainDiagonal = checkMainDiagonal(gridNum);
        boolean removeSecondaryDiagonal = checkSecondaryDiagonal(gridNum);
        clearFrames(gridNum, removeCell, removeLine, removeColumn, removeMainDiagonal, removeSecondaryDiagonal);
    }

    private static boolean checkCell(int gridNum) {
        int line = getLine(gridNum);
        int col = getCol(line, gridNum);
        for (int k = 0, j = 1; j < ultimateBoard[line][col].length; ++k, ++j)
            if (ultimateBoard[line][col][k] != ultimateBoard[line][col][j])
                return false;
        return true;
    }

    public static boolean checkIfPlaceInLineIsEmpty(int a) {
        int frameNextCol;
        int b;
        for (b = 1; b < BOARD_DIM; ++b) {
            int sum = 0;
            for (frameNextCol = 0; frameNextCol < FRAMES_DIM; ++frameNextCol)
                sum += ultimateBoard[a][b][frameNextCol];
            if (sum == FRAMES_DIM * NO_FRAME)
                return false;
        }
        return true;
    }

    public static boolean checkIfPlaceInColumnIsEmpty(int b) {
        int frameNextCol;
        int a;
        for (a = 1; a < BOARD_DIM; ++a) {
            int sum = 0;
            for (frameNextCol = 0; frameNextCol < FRAMES_DIM; ++frameNextCol)
                sum += ultimateBoard[a][b][frameNextCol];
            if (sum == FRAMES_DIM * NO_FRAME)
                return false;
        }
        return true;
    }

    public static boolean checkLines(int gridNum) {
        lineColors = new int[FRAMES_DIM];
        for (int f = 0; f < lineColors.length; ++f)
            lineColors[f] = NO_FRAME;
        int line = getLine(gridNum);
        int col = 1;
        int frameNextCol;
        int color;
        boolean[] checkIfAllPlacesContainFrameColor = new boolean[BOARD_DIM];
        if (!checkIfPlaceInLineIsEmpty(line))
            return false;
        for (int frame = 0; frame < FRAMES_DIM; ++frame) {
            if (ultimateBoard[line][0][frame] == NO_FRAME) continue;
            color = ultimateBoard[line][0][frame];
            checkIfAllPlacesContainFrameColor[0] = true;
            for (col = 1; col < BOARD_DIM; ++col) {
                for (frameNextCol = 0; frameNextCol < FRAMES_DIM; ++frameNextCol) {
                    if (color == ultimateBoard[line][col][frameNextCol])
                        break;
                }
                if (frameNextCol >= FRAMES_DIM)
                    break;
                else
                    checkIfAllPlacesContainFrameColor[col] = true;
            }
            int i = 0;
            for (; i < checkIfAllPlacesContainFrameColor.length; ++i)
                if (checkIfAllPlacesContainFrameColor[i] == false)
                    break;
            if (i >= checkIfAllPlacesContainFrameColor.length) {
                lineColors[frame] = color;
                checkIfAllPlacesContainFrameColor = new boolean[BOARD_DIM];
            }
        }
        for (int i = 0; i < lineColors.length; ++i)
            if (lineColors[i] != NO_FRAME)
                return true;
        return false;
    }

    private static boolean checkColumns(int gridNum) {
        columnColors = new int[FRAMES_DIM];
        for (int f = 0; f < columnColors.length; ++f)
            columnColors[f] = NO_FRAME;
        int line = getLine(gridNum);
        int col = getCol(line, gridNum);
        int frameNextLine;
        int color;
        boolean[] checkIfAllPlacesContainFrameColor = new boolean[BOARD_DIM];
        if (!checkIfPlaceInColumnIsEmpty(col))
            return false;
        for (int frame = 0; frame < FRAMES_DIM; ++frame) {
            if (ultimateBoard[0][col][frame] == NO_FRAME) continue;
            color = ultimateBoard[0][col][frame];
            checkIfAllPlacesContainFrameColor[0] = true;
            for (line = 1; line < BOARD_DIM; ++line) {
                for (frameNextLine = 0; frameNextLine < FRAMES_DIM; ++frameNextLine) {
                    if (color == ultimateBoard[line][col][frameNextLine])
                        break;
                }
                if (frameNextLine >= FRAMES_DIM)
                    break;
                else
                    checkIfAllPlacesContainFrameColor[line] = true;
            }
            int i = 0;
            for (; i < checkIfAllPlacesContainFrameColor.length; ++i)
                if (checkIfAllPlacesContainFrameColor[i] == false)
                    break;
            if (i >= checkIfAllPlacesContainFrameColor.length) {
                columnColors[frame] = color;
                checkIfAllPlacesContainFrameColor = new boolean[BOARD_DIM];
            }
        }
        for (int i = 0; i < columnColors.length; ++i)
            if (columnColors[i] != NO_FRAME)
                return true;
        return false;
    }

    public static boolean checkMainDiagonal (int gridNum){
        int line = getLine(gridNum);
        int col = line;
        diagonalColors = new int[FRAMES_DIM];
        boolean colorToRemove = false;
        for (int f = 0; f < diagonalColors.length; ++f)
            diagonalColors[f] = NO_FRAME;
        int color = NO_FRAME;
        for (int frame = 0; frame < FRAMES_DIM; ++frame) {
            for (int nextLine = 1; nextLine < BOARD_DIM; ++nextLine) {
                for (int nextLineFrame = 0; nextLineFrame < FRAMES_DIM; ++nextLineFrame) {
                    if (ultimateBoard[line][col][frame] == ultimateBoard[nextLine][nextLine][nextLineFrame] && ultimateBoard[line][col][frame] != NO_FRAME) {
                        if (nextLine == BOARD_DIM - 1 && color == ultimateBoard[nextLine][nextLine][nextLineFrame])
                            colorToRemove = true;
                        if (nextLine < BOARD_DIM - 1)
                            color = ultimateBoard[line][col][frame];
                        break;
                    }
                }
                if (color == NO_FRAME)
                    break;
            }
            int f = 0;
            if (colorToRemove) {
                while (color != NO_FRAME) {
                    if (diagonalColors[f] == color) {
                        color = NO_FRAME;
                        break;
                    }
                    if (diagonalColors[f] == NO_FRAME) {
                        diagonalColors[f] = color;
                        color = NO_FRAME;
                    } else
                        ++f;
                }
            }
            colorToRemove = false;
        }
        for (int f = 0; f < FRAMES_DIM; ++f)
            if (diagonalColors[f] != NO_FRAME)
                return true;
        return false;
    }

    public static boolean checkSecondaryDiagonal(int gridNum){
        return false;
    }

    public static void updateBoard(){
        int gridNum=1;
        while(gridNum<=BOARD_PLACES) {
            for (int line = 0; line < ultimateBoard.length; ++line) {
                for (int col = 0; col < ultimateBoard[line].length; ++col) {
                    for (int frame = 0; frame < ultimateBoard[line][col].length; ++frame) {
                        if (ultimateBoard[line][col][frame] == NO_FRAME)
                            Panel1.clearFrame(frame, gridNum);
                    }
                    gridNum++;
                }
            }
        }
    }

    private static void clearFrames(int gridNum, boolean removeCell, boolean removeLine, boolean removeColumn, boolean removeMainDiagonal, boolean removeSecondaryDiagonal) {
        int line = getLine(gridNum);
        int col = getCol(line, gridNum);

        if (removeCell) {
            for (int k = 0; k < ultimateBoard[line][col].length; ++k)
                ultimateBoard[line][col][k] = NO_FRAME;
        }
        if(removeLine){
            for(int colI = 0; colI < BOARD_DIM; ++colI)
                for (int frame = 0; frame < FRAMES_DIM; ++frame)
                    for (int color = 0; color < lineColors.length; ++color)
                        if (ultimateBoard[line][colI][frame] == lineColors[color])
                            ultimateBoard[line][colI][frame] = NO_FRAME;
        }
        if (removeColumn) {
            for (int lineI = 0; lineI < BOARD_DIM; ++lineI)
                for (int frame = 0; frame < FRAMES_DIM; ++frame)
                    for (int color = 0; color < columnColors.length; ++color)
                        if (ultimateBoard[lineI][col][frame] == columnColors[color])
                            ultimateBoard[lineI][col][frame] = NO_FRAME;
        }
        if(removeMainDiagonal){
            for (int lineI = 0; lineI < BOARD_DIM; ++lineI)
                for (int frame = 0; frame < FRAMES_DIM; ++frame)
                    for (int color = 0; color < columnColors.length; ++color)
                        if (ultimateBoard[lineI][col][frame] == columnColors[color])
                            ultimateBoard[lineI][col][frame] = NO_FRAME;
        }
        if(removeSecondaryDiagonal){
            for (int lineI = 0; lineI < BOARD_DIM; ++lineI)
                for (int frame = 0; frame < FRAMES_DIM; ++frame)
                    for (int color = 0; color < columnColors.length; ++color)
                        if (ultimateBoard[lineI][col][frame] == columnColors[color])
                            ultimateBoard[lineI][col][frame] = NO_FRAME;
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
}
