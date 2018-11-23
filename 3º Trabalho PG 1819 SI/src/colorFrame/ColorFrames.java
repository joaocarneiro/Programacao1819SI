package colorFrame;

import isel.leic.pg.Console;
import static java.awt.event.KeyEvent.*;
import panel.*;

public class ColorFrames {
    public static final int MAX_COLORS = 5;  // [1..9] Color used to generate random piece
    public static final int BOARD_DIM = 3;  // [2..4] Dimension (lines and columns) of board
    public static final int FRAMES_DIM = 3; // [1..4] Number of frames in each place of of board
    private static final int NO_FRAME = -1;  // Special color to mark frame absence
    private static final int BOARD_PLACES = BOARD_DIM * BOARD_DIM;
    private static int [] boardMatrix = new int [BOARD_PLACES*3];

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
            if (validBoardPlace(gridNum)){
                putPieceInBoard(gridNum);
                Panel.printMessage("Ok");
                updateSecondaryBoardMatrix(gridNum);
                generatePiece();
                printPiece();
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

    private static boolean validBoardPlace(int gridNum){
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

    private static void generatePiece() {
        for (int f = 0; f < FRAMES_DIM; ++f)  // Removes all frames
            piece[f] = NO_FRAME;
        boolean s = false, m = false, xl = false;
        int i, k = 3;
        for (i = 0; i < boardMatrix.length; i += 3) {
            if (boardMatrix[i] == NO_FRAME) {
                if (k == 3) {
                    s = true;
                    if (i == boardMatrix.length - k) {
                        i = 1;
                        k = 2;
                    }
                }
                if (k == 2) {
                    m = true;
                    if (i == boardMatrix.length - k) {
                        i = 2;
                        k = 1;
                    }
                }
                if (k == 1) xl = true;
            }
        }
        System.out.println(s + " " + m + "" + xl);
        int numOfFrames = 1;// + (int) (Math.random()* (FRAMES_DIM-1)); // Frames to generate
        for (i = 0; i < numOfFrames; ++i) {
            int frameSize;
            do frameSize = 0;// (int) (Math.random()* FRAMES_DIM); // Selects a free random dimension
            while( piece[frameSize] != NO_FRAME );
            piece[frameSize] = (int) (Math.random()*MAX_COLORS); // Generate random color
        }
    }

    private static void printPiece() {
        for (int f = 0; f < FRAMES_DIM; ++f) {  // For each frame dimension
            int color = piece[f];
            if (color==NO_FRAME) Panel.clearFrame(f,0);  // Clean if does not exist with (f) dimension
            else Panel.printFrame(f,color,0);      // or Displays the frame with (f) dimension and (color)
        }
    }
}
