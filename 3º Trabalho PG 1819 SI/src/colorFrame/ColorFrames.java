package colorFrame;

import isel.leic.pg.Console;
import static java.awt.event.KeyEvent.*;
import panel.*;

import java.awt.event.MouseEvent;

public class ColorFrames {
    public static final int MAX_COLORS = 5;  // [1..9] Color used to generate random piece
    public static final int BOARD_DIM = 3;  // [2..4] Dimension (lines and columns) of board
    public static final int FRAMES_DIM = 3; // [1..4] Number of frames in each place of of board
    private static final int NO_FRAME = -1;  // Special color to mark frame absence
    private static final int BOARD_PLACES = BOARD_DIM * BOARD_DIM;
    private static int [] boardMatrix = new int [BOARD_PLACES*FRAMES_DIM];
    private static int [] board = new int [BOARD_PLACES];
    private static int score = 0;

    /**
     * Random generated piece.
     * Each position has the color of each frame [0 .. MAX_COLORS-1] or NO_FRAME indicates that frame does not exist.
     * Each index corresponds to one dimension of the frame.
     */
    private static int[] piece = new int[FRAMES_DIM];

    /**
     * Flag to finish the game
     */
    private static boolean terminate = false;

    public static void main(String[] args) {
        Panel.init();
        Panel.printMessage("Welcome");
        playGame();
        Panel.printMessageAndWait("BYE");
        Panel.end();
    }

    private static void playGame() {
        int key;
        for (int f = 0; f < boardMatrix.length; ++f)
            boardMatrix[f]=NO_FRAME;
        generatePiece();
        printPiece();
        do {
            key = Console.waitKeyPressed(5000);
            if (key>0) {
                processKey(key);
                Console.waitKeyReleased(key);
            } else Panel.printMessage("");  // Clear last message
        } while( !terminate );
    }

    private static void processKey(int key) {

        if (key == VK_ESCAPE) terminate = true;
        int gridNum = 0;
        if (key >= VK_1 && key <= VK_9)
            gridNum = key-VK_1+1;
        else if (key >= VK_NUMPAD1 && key <= VK_NUMPAD9 )
            gridNum = key-VK_NUMPAD1+1;
        else if (key >= VK_A && key <= VK_Z)
            gridNum = key-VK_A+10;
        if (gridNum>=1 && gridNum<=BOARD_PLACES) {
            if (validateBoardPlace(gridNum)) {
                putPieceInBoard(gridNum);
                Panel.printMessage("Ok");
                updateSecondaryBoardMatrix(gridNum);
                generatePiece();
                printPiece();
                checkPositionsForVictory(gridNum);
                if(checkIfBoardIsFull())
                    terminate = true;
            }
            else
                Panel.printMessage("Invalid;Place");
        }
    }

    private static void updateSecondaryBoardMatrix(int gridNum){
        int iniF = gridNum*2+(gridNum-3);
        int endF = gridNum*2+(gridNum-1);
        for (int f = iniF, i=0; f <= endF; ++f, ++i){
            if(piece[i]!=NO_FRAME)
                boardMatrix[f]=piece[i];
        }
    }

    private static boolean validateBoardPlace(int gridNum) {
        int iniF = gridNum*2+(gridNum-3);
        int endF = gridNum*2+(gridNum-1);
        for (int f = iniF, i=0; f <= endF; ++f, ++i){
            if(piece[i]!=NO_FRAME)
                if(boardMatrix[f]!=NO_FRAME)
                    return false;
        }
        return true;
    }

    private static void putPieceInBoard(int gridNum) {
        for (int f = 0; f < FRAMES_DIM; ++f) { // For each frame dimension
            int color = piece[f];
            if (color!=NO_FRAME) {
                Panel.printFrame(f,color,gridNum); // Displays the frame with (f) dimension and (color)
                Panel.clearFrame(f,0);             // Clean the frame of piece.
            }
        }
    }

    public static boolean checkIfBoardIsFull(){
        for (int i=0; i<boardMatrix.length; ++i) {
            if(boardMatrix[i]==NO_FRAME) return false;
        }
        return true;
    }

    public static void checkPositionsForVictory(int gridNum) {
        checkCell(gridNum);
        checkLines(gridNum);
        checkColumns(gridNum);
        checkDiagonals(gridNum);
    }

    public static void checkLines(int gridNum){

    }

    public static void checkColumns(int gridNum){
        int iCol = gridNum * 2 + (gridNum - 3);
        int iLine = iCol;
        int count = 0;
        while(iCol>=9)
            iCol-=9;
        int jLine = iCol+BOARD_DIM-1;
        int jCol = iLine+(BOARD_PLACES*2);
        for (;iLine<=jLine;++iLine) {
            for(;iCol<=jCol;iCol+=9) {
                if (boardMatrix[iCol] == boardMatrix[iCol + (BOARD_PLACES)] && boardMatrix[iCol]!=NO_FRAME) count++;
                else break;
            }

        }
        System.out.println(count);
//        colunas => i=iniF i/=9 até i<=9
    }

    public static void checkDiagonals(int gridNum){

    }

    public static void checkCell(int gridNum){
        int iniF = gridNum * 2 + (gridNum - 3);
        int endF = gridNum * 2 + (gridNum - 1);
        int count = 0;
        for (; iniF < endF; ++iniF) {
            if (boardMatrix[iniF] == boardMatrix[iniF + 1]) count++;
            else break;
        }
        if (count == FRAMES_DIM-1) {
            iniF = gridNum * 2 + (gridNum - 3);
            for (; iniF <= endF; ++iniF) {
                boardMatrix[iniF] = NO_FRAME;
            }
            Panel.clearFrame(0, gridNum);
            Panel.clearFrame(1, gridNum);
            Panel.clearFrame(2, gridNum);
            score+=FRAMES_DIM;
            Panel.printScore(score);
        }
    }

    public static boolean validatePieceOnBoardBySize(int i) {
        for(;i<boardMatrix.length;i+=3)
            if(boardMatrix[i]==NO_FRAME)
                return true;
        return false;
    }

    public static int validateBoardCells() {
        int framesToGenerate = 0;
        int larger = 0;
        for (int i = 0; i < BOARD_PLACES * 3; i += 3) {
            for (int j = i, k = i + 2; j <= k; ++j) {
                if (boardMatrix[j] == NO_FRAME) ++framesToGenerate;
            }
            if (framesToGenerate > larger) larger = framesToGenerate;
            framesToGenerate = 0;
        }
        return larger;
    }

    public static boolean validatePieceCombinations(int[] auxPiece) {
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

    private static void generatePiece() {
        int[] auxPiece = new int[FRAMES_DIM];
        for (int f = 0; f < FRAMES_DIM; ++f)
            piece[f] = NO_FRAME;
        int kNumber = 1 + validateBoardCells();
        boolean s = validatePieceOnBoardBySize(0); //returns true if there is space for small pieces
        boolean m = validatePieceOnBoardBySize(1); //returns true if there is space for medium pieces
        boolean l = validatePieceOnBoardBySize(2); //returns true if there is space for large pieces
        int numOfFrames = 1 + (int) (Math.random() * (kNumber - 1)); // Frames to generate
        do {
            for (int f = 0; f < FRAMES_DIM; ++f)
                auxPiece[f] = NO_FRAME;
            for (int i = 0; i < numOfFrames; ++i) {
                int frameSize;
                do frameSize = (int) (Math.random() * FRAMES_DIM); // Selects a free random dimension
                while (auxPiece[frameSize] != NO_FRAME);
                auxPiece[frameSize] = (int) (Math.random() * MAX_COLORS); // Generate random color
            }
            if(!s&&!m&&!l) break;
        } while (!validatePieceCombinations(auxPiece));
        for (int f = 0; f < auxPiece.length; ++f)  // Removes all frames
            piece[f] = auxPiece[f];
    }

    private static int validateIfFrameExists(boolean frame) {
        if (frame) return 1;
        return 0;
    }

    private static int generateFrameSize(boolean s, boolean m, boolean l) {
        return validateIfFrameExists(s) + validateIfFrameExists(m) + validateIfFrameExists(l);
    }

    private static void printPiece() {
        for (int f = 0; f < FRAMES_DIM; ++f) {  // For each frame dimension
            int color = piece[f];
            if (color==NO_FRAME) Panel.clearFrame(f,0);  // Clean if does not exist with (f) dimension
            else Panel.printFrame(f,color,0);      // or Displays the frame with (f) dimension and (color)
        }
    }
}
