package main;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Stack;
import javax.swing.JPanel;

public class Panel_DrawingArea extends JPanel {

    // The actual size of board
    // The visual drawing part have these sizes in fixed version.
    private final int widthBoard = Settings.WIDTH_DRAW_AREA;
    private final int heightBoard = Settings.HEIGHT_DRAW_AREA;

    // The size of drawing panel can be resizable later.
    // These variables are used to handle it.
    private int widthOfScreen = Settings.WIDTH_DRAW_AREA;
    private int heightOfScreen = Settings.HEIGHT_DRAW_AREA;

    // Two arrays are used for saving the state of board, include color
    // and coordinate of objects (point, shapes, ect...)
    private String[][] coordOfBoard = new String[widthBoard][heightBoard];
    private Color[][] colorOfBoard = new Color[widthBoard][heightBoard];

    // Undo, Redo stack
    private static Stack<String[][]> stack_UndoCoordOfBoard = new Stack<>();
    private static Stack<Color[][]> stack_UndoColorOfBoard = new Stack<>();
    private static Stack<String[][]> stack_RedoCoordOfBoard = new Stack<>();
    private static Stack<Color[][]> stack_RedoColorOfBoard = new Stack<>();

    // Variable to save the recently drawn shape.
    private Shape recentlyDrawnShape;
    
    public Panel_DrawingArea() {
    }

    public void updateCurrentSize() {
        widthOfScreen = this.getWidth();
        heightOfScreen = this.getHeight();
    }

    /**
     * Copy color value of each pixel from <code>color_board_from</code> to
     * <code>color_board_to</code>. <br>
     * Require they have the same size.
     *
     * @param color_board_from Color[][]
     * @param color_board_to Color[][]
     */
    public static void copyColorValue(Color[][] color_board_from, Color[][] color_board_to) {
        int height = color_board_from.length;
        int width = color_board_from[0].length;

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                color_board_to[row][col] = new Color(color_board_from[row][col].getRGB());
            }
        }
    }

    /**
     * Copy coordinate value of each pixel from <code>coord_board_from</code> to
     * <code>coord_board_to</code>. <br>
     * Require they have the same size.
     *
     * @param coord_board_from Color[][]
     * @param coord_board_to Color[][]
     */
    public static void copyCoordValue(String[][] coord_board_from, String[][] coord_board_to) {
        int height = coord_board_from.length;
        int width = coord_board_from[0].length;

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                coord_board_to[row][col] = coord_board_from[row][col];
            }
        }
    }

    /**
     * Save the current color of drawing board to undo stack.
     */
    private void saveCurrentColorBoardToUndoStack() {
        Color[][] tempBoard = new Color[widthBoard][heightBoard];
        copyColorValue(colorOfBoard, tempBoard);
        stack_UndoColorOfBoard.push(tempBoard);
    }

    /**
     * Save the current coordinates of drawing board to undo stack.
     */
    private void saveCurrentCoordBoardToUndoStack() {
        String[][] tempBoard = new String[widthBoard][heightBoard];
        copyCoordValue(coordOfBoard, tempBoard);
        stack_UndoCoordOfBoard.push(tempBoard);
    }
    
    /**
     * Save the current color of drawing board to redo stack.
     */
    private void saveCurrentColorBoardToRedoStack() {
        Color[][] tempBoard = new Color[widthBoard][heightBoard];
        copyColorValue(colorOfBoard, tempBoard);
        stack_RedoColorOfBoard.push(tempBoard);
    }

    /**
     * Save the current coordinates of drawing board to redo stack.
     */
    private void saveCurrentCoordBoardToRedoStack() {
        String[][] tempBoard = new String[widthBoard][heightBoard];
        copyCoordValue(coordOfBoard, tempBoard);
        stack_RedoCoordOfBoard.push(tempBoard);
    }

    /**
     * Get the previous status of board. <br>
     * This reverts back to the saved state at the top of undo stack. 
     * If user just changes any color but not coordinate of objects, we only 
     * get the color from undo stack of color, not stack of coordinates. The 
     * same works for changing coordinate but not color. 
     * Simultaneously, the redo stack will push the current state to its.
     */
    public void undo() {
        if (! stack_UndoColorOfBoard.empty()) {
            saveCurrentColorBoardToRedoStack();
            copyColorValue(stack_UndoColorOfBoard.pop(), colorOfBoard);
        }
        if (! stack_UndoCoordOfBoard.empty()) {
            saveCurrentCoordBoardToRedoStack();
            copyCoordValue(stack_UndoCoordOfBoard.pop(), coordOfBoard);
        }
    }

    /**
     * Get the previous status of board after undo action. <br>
     * This reverts back to the saved state at the top of redo stack. 
     * If user just changes any color but not coordinate of objects, we only 
     * get the color from redo stack of color, not stack of coordinates. The 
     * same works for changing coordinate but not color. 
     * Simultaneously, the undo stack will push the current state to its.
     */
    public void redo() {
        if (!stack_RedoColorOfBoard.empty()) {
            saveCurrentColorBoardToUndoStack();
            copyColorValue(stack_RedoColorOfBoard.pop(), colorOfBoard);
        }
        if (!stack_RedoCoordOfBoard.empty()) {
            saveCurrentCoordBoardToUndoStack();
            copyCoordValue(stack_RedoCoordOfBoard.pop(), coordOfBoard);
        }
    }

    /**
     * Save the last user's drawing action to the board.
     */
    public void apply() {
        // Clear redo stack.
        stack_RedoColorOfBoard.clear();
        stack_RedoCoordOfBoard.clear();
        
        // Save current state to undo stack.
        saveCurrentColorBoardToUndoStack();
        saveCurrentCoordBoardToUndoStack();
    }
    
//    public void createGridLayout(Graphics g) {
//        g.setColor(new Color(235, 235, 235));
//        g.fillRect(0, 0, width, height); // vẽ nền, chính là lưới pixel sau khi chấm các điểm pixel màu trắng lên
//        g.setColor(Color.white);
//        for (int i = 0; i < height; i++) {
//            for (int j = 0; j < width; j++) {
//                g.fillRect(i * (space + size) + 1, j * (space + size) + 1, size, size); //chấm các điểm pixel màu trắng (+1 để thụt vào 1 pixel, không bị viền đen che mất)
//            }
//        }
//    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
//        createGridLayout(g);
//        createCoordinateAxis(g);

        //g.setColor(Color.yellow);
        //Point2D point = new Point2D(-5,1);
        //point=point.ConvertToPixelCoord(point, OX, OY);
        //g.fillRect(point.X, point.Y, size, size);
//        createLine(g, 0, 0, 20, 20);
    }

//    private int getNewX(int x) {
//        return (65 + x) * (this.space + this.size) + 1;
//    }
//
//    private int getNewY(int y) {
//        return (51 - y) * (this.space + this.size) + 1;
//    }

//    public void createCoordinateAxis(Graphics g) {
//        //Ve truc Y
//        g.setColor(Color.red);
//        g.drawLine(OX, 1, OX, height);
//        //Ve truc X
//        g.drawLine(1, OY, width, OY);
//    }

//    public void putPixel(Graphics g, int x, int y) {
//        g.fillRect(getNewX(x), getNewY(y), this.size, this.size);
//    }
    
    /*
    public void createLine(Graphics g, int x1, int y1, int x2, int y2) {
        int x, y;
        int dx, dy;
        int incx, incy;
        int balance;

        if (x2 >= x1) {
            dx = x2 - x1;
            incx = 1;
        } else {
            dx = x1 - x2;
            incx = -1;
        }

        if (y2 >= y1) {
            dy = y2 - y1;
            incy = 1;
        } else {
            dy = y1 - y2;
            incy = -1;
        }

        x = x1;
        y = y1;

        if (dx >= dy) {
            dy <<= 1;
            balance = dy - dx;
            dx <<= 1;

            while (x != x2) {
                putPixel(g, x, y);
                if (balance >= 0) {
                    y += incy;
                    balance -= dx;
                }
                balance += dy;
                x += incx;
            }
            putPixel(g, x, y);
        } else {
            dx <<= 1;
            balance = dx - dy;
            dy <<= 1;

            while (y != y2) {
                putPixel(g, x, y);
                if (balance >= 0) {
                    x += incx;
                    balance -= dy;
                }
                balance += dx;
                y += incy;
            }
            putPixel(g, x, y);
        }
        return;
    }
    */
}
