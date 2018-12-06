package colorFrame;

import isel.leic.pg.Console;

import static isel.leic.pg.Console.*;
import static isel.leic.pg.Console.MAX_COLORS;
import static java.awt.event.KeyEvent.*;
import static isel.leic.pg.Location.*;

import isel.leic.pg.Location;
import isel.leic.pg.MouseEvent;
import panel.Panel1;

public class ColorFrames1 {
    public static int MAX_COLORS = 4;  // [1..9] Color used to generate random piece
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
    private static int[] lineColors = new int[FRAMES_DIM];
    private static int[] columnColors = new int[FRAMES_DIM];
    private static int[] mainDiagonalColors = new int[FRAMES_DIM];
    private static int[] secondaryDiagonalColors = new int[FRAMES_DIM];

    /**
     * Flag to finish the game
     */
    private static boolean terminate = false;
    private static int score = 0;
    private static int level = 1;
    private static int startTime = 0;
    private static Location loc = new Location();

    public static void main(String[] args) {
        Panel1.init();
        Panel1.printMessage("Welcome");
        playGame();
        Panel1.printMessageAndWait("BYE");
        Panel1.end();
    }

    private static void restartGame() {
        Panel1.printMessage("Welcome");
        Panel1.printMessage("Restart?;Press Y;Or;Press N");
        int key;
        do {
            key = Console.waitKeyPressed(5000);
            if (key > 0) {
                processKey(key);
                Console.waitKeyReleased(key);
            } else Panel1.printMessage("");  // Clear last message
        } while (!terminate);
    }

    private static void prepareForNewGame(){
        for (int gridNum = 1; gridNum <= BOARD_PLACES; ++gridNum)
            for (int frame = 0; frame < FRAMES_DIM; ++frame)
                Panel1.clearFrame(frame, gridNum);

        for (int line = 0; line < ultimateBoard.length; ++line)
            for (int col = 0; col < ultimateBoard[line].length; ++col)
                for (int frame= 0; frame < ultimateBoard[line][col].length; ++frame)
                    ultimateBoard[line][col][frame] = NO_FRAME;

        for (int i = 0; i < FRAMES_DIM; ++i) {
            lineColors[i]=NO_FRAME;
            columnColors[i]=NO_FRAME;
            mainDiagonalColors[i]=NO_FRAME;
            secondaryDiagonalColors[i]=NO_FRAME;
        }

        MAX_COLORS=4;
        score=0;
        Panel1.printScore(score);
        level = 1;
        terminate=false;
        playGame();
    }

    private static void processClick(Location l) {
        if (l == null) return; // Não é DOWN
        if (l.line>0)
            cursor(l.line, l.col);
        if(l.line>=17&&l.line<=23&&l.col>=1&&l.col<=7)
            processKey(VK_1);
        if(l.line>=17&&l.line<=23&&l.col>=9&&l.col<=15)
            processKey(VK_2);
        if(l.line>=17&&l.line<=23&&l.col>=17&&l.col<=23)
            processKey(VK_3);
        if(l.line>=9&&l.line<=15&&l.col>=1&&l.col<=7)
            processKey(VK_4);
        if(l.line>=9&&l.line<=15&&l.col>=9&&l.col<=15)
            processKey(VK_5);
        if(l.line>=9&&l.line<=15&&l.col>=17&&l.col<=23)
            processKey(VK_6);
        if(l.line>=1&&l.line<=7&&l.col>=1&&l.col<=7)
            processKey(VK_7);
        if(l.line>=1&&l.line<=7&&l.col>=9&&l.col<=15)
            processKey(VK_8);
        if(l.line>=1&&l.line<=7&&l.col>=16&&l.col<=23)
            processKey(VK_9);
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
            if (key == MOUSE_EVENT){
                processClick(getMouseEvent(MouseEvent.CLICK));
            }
            else if (key > 0) {
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
                piece[frameSize] = 1 + (int) (Math.random() * MAX_COLORS); // Generate random color
            }
            if (validateBoardCells() <= 0) break;
        } while (!validatePieceCombinations(piece));
    }

    private static int validateBoardCells() {
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

    private static boolean validatePieceCombinations(int[] piece) {
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
        if (checkIfBoardIsFull()) {
            if (key == VK_N) terminate = true;
            else if (key == VK_Y)
                prepareForNewGame();
        }
        int gridNum = 0;
        if (key >= VK_1 && key <= VK_9)
            gridNum = key - VK_1 + 1;
        else if (key >= VK_NUMPAD1 && key <= VK_NUMPAD9)
            gridNum = key - VK_NUMPAD1 + 1;
        else if (key >= VK_A && key <= VK_Z)
            gridNum = key - VK_A + 10;
        if (gridNum >= 1 && gridNum <= BOARD_PLACES) {
            if (validateBoardPlace(gridNum)) {
                Panel1.printLevel(level);
                putPieceInBoard(gridNum);
                Panel1.printMessage("Ok");
                updateUltimateBoard(gridNum);
                checkPositionsForVictory(gridNum);
                updateBoard();
                updateMaxColors(score);
                generatePiece();
                printPiece();
                if (checkIfBoardIsFull())
                    restartGame();
            } else
                Panel1.printMessage("Invalid;Place");
        }
    }

    private static boolean validateBoardPlace(int gridNum) {
        int line = getLine(gridNum);
        int col = getCol(gridNum);
        for (int k = 0; k < ultimateBoard[line][col].length; ++k)
            if (piece[k] != NO_FRAME)
                if (ultimateBoard[line][col][k] != NO_FRAME)
                    return false;
        return true;
    }

    private static int getLine(int gridNum) {
        return (gridNum - 1) / BOARD_DIM;
    }

    private static int getCol(int gridNum) {
        return (gridNum - 1) % BOARD_DIM;
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

    private static void updateUltimateBoard(int gridNum) {
        int line = getLine(gridNum);
        int col = getCol(gridNum);
        for (int k = 0; k < ultimateBoard[line][col].length; ++k)
            if (piece[k] != NO_FRAME)
                ultimateBoard[line][col][k] = piece[k];
    }

    private static void checkPositionsForVictory(int gridNum) {
        boolean removeCell = checkCell(gridNum);
        boolean removeLine = checkLines(gridNum);
        boolean removeColumn = checkColumns(gridNum);
        boolean removeMainDiagonal = false;
        if (getLine(gridNum) == getCol(gridNum))
            removeMainDiagonal = checkMainDiagonal(gridNum);
        boolean removeSecondaryDiagonal = false;
        if (getLine(gridNum) + getCol(gridNum) == BOARD_DIM - 1)
            removeSecondaryDiagonal = checkSecondaryDiagonal(gridNum);
        clearFrames(gridNum, removeCell, removeLine, removeColumn, removeMainDiagonal, removeSecondaryDiagonal);
    }

    private static boolean checkCell(int gridNum) {
        int line = getLine(gridNum);
        int col = getCol(gridNum);
        for (int k = 0, j = 1; j < ultimateBoard[line][col].length; ++k, ++j)
            if (ultimateBoard[line][col][k] != ultimateBoard[line][col][j])
                return false;
        return true;
    }

    private static boolean checkIfPlaceInLineIsEmpty(int gridNum) {
        int frameNextCol;
        int line = getLine(gridNum);
        int col;
        for (col = 1; col < BOARD_DIM; ++col) {
            int sum = 0;
            for (frameNextCol = 0; frameNextCol < FRAMES_DIM; ++frameNextCol)
                sum += ultimateBoard[line][col][frameNextCol];
            if (sum == FRAMES_DIM * NO_FRAME)
                return false;
        }
        return true;
    }

    private static boolean checkIfPlaceInColumnIsEmpty(int gridNum) {
        int frameNextCol;
        int line;
        int col = getCol(gridNum);
        for (line = 1; line < BOARD_DIM; ++line) {
            int sum = 0;
            for (frameNextCol = 0; frameNextCol < FRAMES_DIM; ++frameNextCol)
                sum += ultimateBoard[line][col][frameNextCol];
            if (sum == FRAMES_DIM * NO_FRAME)
                return false;
        }
        return true;
    }

    private static boolean checkIfPlaceInMainDiagonalIsEmpty(int gridNum) {
        int frameNextCol;
        int line = getLine(gridNum);
        for (; line < BOARD_DIM; ++line) {
            int sum = 0;
            for (frameNextCol = 0; frameNextCol < FRAMES_DIM; ++frameNextCol)
                sum += ultimateBoard[line][line][frameNextCol];
            if (sum == FRAMES_DIM * NO_FRAME)
                return false;
        }
        return true;
    }

    private static boolean checkIfPlaceInSecondaryDiagonalIsEmpty(int gridNum) {
        int frameNextCol;
        int line = getLine(gridNum);
        int col = getCol(gridNum);
        for (; line < BOARD_DIM; ++line, --col) {
            int sum = 0;
            for (frameNextCol = 0; frameNextCol < FRAMES_DIM; ++frameNextCol)
                sum += ultimateBoard[line][col][frameNextCol];
            if (sum == FRAMES_DIM * NO_FRAME)
                return false;
        }
        return true;
    }

    private static boolean checkLines(int gridNum) {
        lineColors = new int[FRAMES_DIM];
        for (int f = 0; f < lineColors.length; ++f)
            lineColors[f] = NO_FRAME;
        int line = getLine(gridNum);
        int col = 1;
        int frameNextCol;
        int color;
        boolean[] checkIfAllPlacesContainFrameColor = new boolean[BOARD_DIM];
        if (!checkIfPlaceInLineIsEmpty(gridNum))
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
        int col = getCol(gridNum);
        int frameNextLine;
        int color;
        boolean[] checkIfAllPlacesContainFrameColor = new boolean[BOARD_DIM];
        if (!checkIfPlaceInColumnIsEmpty(gridNum))
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

    private static boolean checkMainDiagonal(int gridNum) {
        mainDiagonalColors = new int[FRAMES_DIM];
        for (int f = 0; f < mainDiagonalColors.length; ++f)
            mainDiagonalColors[f] = NO_FRAME;
        int line;
        int frameNextLine;
        int color;
        boolean[] checkIfAllPlacesContainFrameColor = new boolean[BOARD_DIM];
        if (!checkIfPlaceInMainDiagonalIsEmpty(gridNum))
            return false;
        for (int frame = 0; frame < FRAMES_DIM; ++frame) {
            if (ultimateBoard[0][0][frame] == NO_FRAME) continue;
            color = ultimateBoard[0][0][frame];
            checkIfAllPlacesContainFrameColor[0] = true;
            for (line = 1; line < BOARD_DIM; ++line) {
                for (frameNextLine = 0; frameNextLine < FRAMES_DIM; ++frameNextLine) {
                    if (color == ultimateBoard[line][line][frameNextLine])
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
                mainDiagonalColors[frame] = color;
                checkIfAllPlacesContainFrameColor = new boolean[BOARD_DIM];
            }
        }
        for (int i = 0; i < mainDiagonalColors.length; ++i)
            if (mainDiagonalColors[i] != NO_FRAME)
                return true;
        return false;
    }

    private static boolean checkSecondaryDiagonal(int gridNum) {
        //getLine(gridNum)+getCol(gridNum)==BOARD_DIM-1
        secondaryDiagonalColors = new int[FRAMES_DIM];
        for (int f = 0; f < secondaryDiagonalColors.length; ++f)
            secondaryDiagonalColors[f] = NO_FRAME;
        int line;
        int col;
        int frameNextLine;
        int color;
        boolean[] checkIfAllPlacesContainFrameColor = new boolean[BOARD_DIM];
        if (!checkIfPlaceInSecondaryDiagonalIsEmpty(gridNum))
            return false;
        for (int frame = 0; frame < FRAMES_DIM; ++frame) {
            if (ultimateBoard[0][BOARD_DIM - 1][frame] == NO_FRAME) continue;
            color = ultimateBoard[0][BOARD_DIM - 1][frame];
            checkIfAllPlacesContainFrameColor[0] = true;
            for (line = 1, col = 1; line < BOARD_DIM; ++line, --col) {
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
                secondaryDiagonalColors[frame] = color;
                checkIfAllPlacesContainFrameColor = new boolean[BOARD_DIM];
            }
        }
        for (int i = 0; i < secondaryDiagonalColors.length; ++i)
            if (secondaryDiagonalColors[i] != NO_FRAME)
                return true;
        return false;
    }

    private static void updateBoard() {
        int gridNum = 1;
        while (gridNum <= BOARD_PLACES) {
            for (int line = 0; line < ultimateBoard.length; ++line) {
                for (int col = 0; col < ultimateBoard[line].length; ++col) {
                    for (int frame = 0; frame < ultimateBoard[line][col].length; ++frame) {
                        if (ultimateBoard[line][col][frame] == NO_FRAME) {
                            sleep(2);
                            Panel1.clearFrame(frame, gridNum);
                        }
                    }
                    gridNum++;
                }
            }
        }
    }

    private static void clearFrames(int gridNum, boolean removeCell, boolean removeLine, boolean removeColumn, boolean removeMainDiagonal, boolean removeSecondaryDiagonal) {
        if (removeCell) {
            int line = getLine(gridNum);
            int col = getCol(gridNum);
            for (int k = 0; k < ultimateBoard[line][col].length; ++k) {
                score += 1;
                ultimateBoard[line][col][k]=NO_FRAME;
                Panel1.clearFrame(k, gridNum);
            }
        }
        if(removeLine){
            int line = getLine(gridNum);
            for (int col = 0; col < BOARD_DIM; ++col)
                for (int frame = 0; frame < FRAMES_DIM; ++frame)
                    for (int color = 0; color < lineColors.length; ++color)
                        if (ultimateBoard[line][col][frame] == lineColors[color] && ultimateBoard[line][col][frame] != NO_FRAME) {
                            ultimateBoard[line][col][frame] = NO_FRAME;
                            score+=1;
                        }
        }
        if (removeColumn) {
            int col = getCol(gridNum);
            for (int line = 0; line < BOARD_DIM; ++line)
                for (int frame = 0; frame < FRAMES_DIM; ++frame)
                    for (int color = 0; color < columnColors.length; ++color)
                        if (ultimateBoard[line][col][frame] == columnColors[color] && ultimateBoard[line][col][frame] != NO_FRAME) {
                            ultimateBoard[line][col][frame] = NO_FRAME;
                            score+=1;
                        }
        }
        if(removeMainDiagonal){
            for (int line = 0; line < BOARD_DIM; ++line)
                for (int frame = 0; frame < FRAMES_DIM; ++frame)
                    for (int color = 0; color < mainDiagonalColors.length; ++color)
                        if (ultimateBoard[line][line][frame] == mainDiagonalColors[color] && ultimateBoard[line][line][frame] != NO_FRAME) {
                            ultimateBoard[line][line][frame] = NO_FRAME;
                            score+=1;
                        }
        }
        if(removeSecondaryDiagonal){
            for (int line = 0, col = 2; line < BOARD_DIM; ++line, --col)
                for (int frame = 0; frame < FRAMES_DIM; ++frame)
                    for (int color = 0; color < secondaryDiagonalColors.length; ++color)
                        if (ultimateBoard[line][col][frame] == secondaryDiagonalColors[color] && ultimateBoard[line][col][frame] != NO_FRAME) {
                            ultimateBoard[line][col][frame] = NO_FRAME;
                            score+=1;
                        }
        }
        Panel1.printScore(score);
    }

    private static void updateMaxColors(int score) {
        if (score > 25 && score <= 50 && level == 1) {
            MAX_COLORS++;
            level++;
            Panel1.printLevel(level);
        }
        if (score > 50 && score <= 100 && level == 2) {
            MAX_COLORS++;
            level++;
            Panel1.printLevel(level);
        }
        if (score > 100 && score <= 200 && level == 3) {
            MAX_COLORS++;
            level++;
            Panel1.printLevel(level);
        }
        if (score > 200 && score <= 400 && level == 4) {
            MAX_COLORS++;
            level++;
            Panel1.printLevel(level);
        }
        if (score > 400 && level == 5) {
            MAX_COLORS++;
            level++;
            Panel1.printLevel(level);
        }
    }

    private static boolean checkIfBoardIsFull() {
        for (int i = 0; i < ultimateBoard.length; ++i)
            for (int j = 0; j < ultimateBoard[i].length; ++j)
                for (int k = 0; k < ultimateBoard[i][j].length; ++k)
                    if (ultimateBoard[i][j][k] == NO_FRAME)
                        return false;
        return true;
    }
}
