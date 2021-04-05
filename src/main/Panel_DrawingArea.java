package main;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Stack;
import javax.swing.JPanel;

public class Panel_DrawingArea extends JPanel {

    // The actual size of board
    // The visual drawing part have these sizes in fixed version.
    public final int widthBoard = Settings.WIDTH_DRAW_AREA;
    public final int heightBoard = Settings.HEIGHT_DRAW_AREA;

    // The size of drawing panel can be resizable later.
    // These variables are used to handle it.
    private int widthOfScreen;
    private int heightOfScreen;

    // Color and coord property of all points when any drawing action is applied
    private Color[][] colorOfBoard;
    private String[][] coordOfBoard;

    // The color property of all points when a new drawing action is happened.
    private Color[][] changedColorOfBoard;
    private String[][] changedCoordOfBoard;
    // This array is used to mark the point is changed in an action.
    private boolean[][] markedChangeOfBoard;

    // Undo, Redo container
    // Each includes color and coordinate stack
    private static Stack<String[][]> stack_UndoCoordOfBoard;
    private static Stack<Color[][]> stack_UndoColorOfBoard;
    private static Stack<String[][]> stack_RedoCoordOfBoard;
    private static Stack<Color[][]> stack_RedoColorOfBoard;

    // Variable to save the recently drawn shape.
    private Shape recentlyDrawnShape;

    // Visual option
    private boolean showGrid_Flag;
    private boolean showCoordinate_Flag;

    private Point2D startDrawingPoint;
    private Point2D endDrawingPoint;
    public static final Point2D DEFAULT_UNUSED_POINT = new Point2D(-1, -1);

    // Set 2D as default coordinated system.
    private Settings.CoordinateMode coordinateMode;

    // Color of board, includes grid color and background color and coordinate color
    private Color gridColor;
    private Color backgroundColor;
    private Color coordinateColor;
    public static final Color DEFAULT_GRID_COLOR = Color.WHITE;
    public static final Color DEFAULT_BACKGROUND_COLOR = new Color(235, 235, 235);
    public static final Color DEFAULT_COORDINATE_COLOR = new Color(128, 128, 128);

    public Panel_DrawingArea() {
        widthOfScreen = Settings.WIDTH_DRAW_AREA;
        heightOfScreen = Settings.HEIGHT_DRAW_AREA;

        this.colorOfBoard = new Color[heightBoard][widthBoard];
        this.coordOfBoard = new String[heightBoard][widthBoard];

        this.changedColorOfBoard = new Color[heightBoard][widthBoard];
        this.changedCoordOfBoard = new String[heightBoard][widthBoard];

        this.markedChangeOfBoard = new boolean[heightBoard][widthBoard];

        stack_UndoCoordOfBoard = new Stack<>();
        stack_UndoColorOfBoard = new Stack<>();
        stack_RedoCoordOfBoard = new Stack<>();
        stack_RedoColorOfBoard = new Stack<>();

        showGrid_Flag = Settings.DEFAULT_VISUAL_SHOW_GRID;
        showCoordinate_Flag = Settings.DEFAULT_VISUAL_SHOW_COORDINATE;

        coordinateMode = Settings.CoordinateMode.MODE_2D;

        backgroundColor = DEFAULT_BACKGROUND_COLOR;
        gridColor = DEFAULT_GRID_COLOR;
        coordinateColor = DEFAULT_COORDINATE_COLOR;

        startDrawingPoint = DEFAULT_UNUSED_POINT;
        endDrawingPoint = DEFAULT_UNUSED_POINT;
        
        resetChangedColorArray();
        resetChangedColorArray();
        resetChangedCoordArray();
        
        clearBoard();
    }

    public void setCoordinateMode(Settings.CoordinateMode mode) {
        if (this.coordinateMode == mode) return;
        
        // Clear old coordinate system before changing coordinate mode flag
        
        
        this.coordinateMode = mode;
    }
    
    /**
     * Release all resource in undo, redo stack.
     */
    private void disposeStack() {
        stack_RedoColorOfBoard.clear();
        stack_RedoCoordOfBoard.clear();
        stack_UndoColorOfBoard.clear();
        stack_UndoCoordOfBoard.clear();
    }
    
    /**
     * Clear all drawn object in board.
     */
    public void clearBoard() {
        for (int i = 0; i < this.heightBoard; i++) {
            for (int j = 0; j < this.widthBoard; j++) {
                colorOfBoard[i][j] = this.gridColor;
                coordOfBoard[i][j] = null;
            }
        }
    }
    
    public void resetMarkedChangeArray() {
        for (int i = 0; i < this.heightBoard; i++) {
            for (int j = 0; j < this.widthBoard; j++) {
                markedChangeOfBoard[i][j] = false;
            }
        }
    }

    public void resetChangedColorArray() {
        for (int i = 0; i < this.heightBoard; i++) {
            for (int j = 0; j < this.widthBoard; j++) {
                changedColorOfBoard[i][j] = this.gridColor;
            }
        }
    }

    public void resetChangedCoordArray() {
        for (int i = 0; i < this.heightBoard; i++) {
            for (int j = 0; j < this.widthBoard; j++) {
                changedCoordOfBoard[i][j] = null;
            }
        }
    }

    public void setShowGridLinesFlag(boolean flag) {
        showGrid_Flag = flag;
        this.repaint();
    }

    public void setShowCoordinateFlag(boolean flag) {
        showCoordinate_Flag = flag;
        this.repaint();
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
        Color[][] tempBoard = new Color[heightBoard][widthBoard];
        copyColorValue(colorOfBoard, tempBoard);
        stack_UndoColorOfBoard.push(tempBoard);
    }

    /**
     * Save the current coordinates of drawing board to undo stack.
     */
    private void saveCurrentCoordBoardToUndoStack() {
        String[][] tempBoard = new String[heightBoard][widthBoard];
        copyCoordValue(coordOfBoard, tempBoard);
        stack_UndoCoordOfBoard.push(tempBoard);
    }

    /**
     * Save the current color of drawing board to redo stack.
     */
    private void saveCurrentColorBoardToRedoStack() {
        Color[][] tempBoard = new Color[heightBoard][widthBoard];
        copyColorValue(colorOfBoard, tempBoard);
        stack_RedoColorOfBoard.push(tempBoard);
    }

    /**
     * Save the current coordinates of drawing board to redo stack.
     */
    private void saveCurrentCoordBoardToRedoStack() {
        String[][] tempBoard = new String[heightBoard][widthBoard];
        copyCoordValue(coordOfBoard, tempBoard);
        stack_RedoCoordOfBoard.push(tempBoard);
    }

    /**
     * Get the previous status of board. <br>
     * This reverts back to the saved state at the top of undo stack. If user
     * just changes any color but not coordinate of objects, we only get the
     * color from undo stack of color, not stack of coordinates. The same works
     * for changing coordinate but not color. Simultaneously, the redo stack
     * will push the current state to its.
     */
    public void undo() {
        if (!stack_UndoColorOfBoard.empty()) {
            saveCurrentColorBoardToRedoStack();
            copyColorValue(stack_UndoColorOfBoard.pop(), colorOfBoard);
        }
        if (!stack_UndoCoordOfBoard.empty()) {
            saveCurrentCoordBoardToRedoStack();
            copyCoordValue(stack_UndoCoordOfBoard.pop(), coordOfBoard);
        }
    }

    /**
     * Get the previous status of board after undo action. <br>
     * This reverts back to the saved state at the top of redo stack. If user
     * just changes any color but not coordinate of objects, we only get the
     * color from redo stack of color, not stack of coordinates. The same works
     * for changing coordinate but not color. Simultaneously, the undo stack
     * will push the current state to its.
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
     * Save the last user's drawing action to saved board.
     */
    public void apply() {
        // Clear redo stacks.
        stack_RedoColorOfBoard.clear();
        stack_RedoCoordOfBoard.clear();

        // Save current state to undo stack.
        saveCurrentColorBoardToUndoStack();
        saveCurrentCoordBoardToUndoStack();

        // Merge of changed color to saved state of board
        // NOTE: Why not mergeColorValue coordinate??
        mergeColorValue(markedChangeOfBoard, changedColorOfBoard);

        // Save the changed coordinate into board.
        copyCoordValue(coordOfBoard, changedCoordOfBoard);

        // Reset marked change array.
        resetMarkedChangeArray();
        resetChangedColorArray();
        resetChangedCoordArray();
    }

    private boolean isNotSelected() {
        return (startDrawingPoint.equal(DEFAULT_UNUSED_POINT)
                && endDrawingPoint.equal(DEFAULT_UNUSED_POINT));
    }

    public void setSelected(Point2D startPoint, Point2D endPoint) {
        startDrawingPoint.setCoor(startPoint);
        endDrawingPoint.setCoor(endPoint);
    }

    public void showGridLines(Graphics graphic) {
        graphic.setColor(Color.WHITE);
        graphic.fillRect(0, 0, this.widthBoard, this.heightBoard);

        if (showGrid_Flag) {
            graphic.setColor(this.backgroundColor);
            graphic.fillRect(0, 0, this.widthBoard, this.heightBoard);
            graphic.setColor(this.gridColor);
            for (int i = 0; i < this.heightBoard; i++) {
                for (int j = 0; j < this.widthBoard; j++) {
                    graphic.fillRect(
                            i * (Settings.SPACE + Settings.SIZE) + 1,
                            j * (Settings.SPACE + Settings.SIZE) + 1,
                            Settings.SIZE,
                            Settings.SIZE
                    );
                }
            }
        }
    }

    public void showCoordinate(Graphics graphic) {
        if (showCoordinate_Flag) {
            graphic.setColor(this.coordinateColor);
        } else {
            graphic.setColor(DEFAULT_GRID_COLOR);
        }
        if (coordinateMode == Settings.CoordinateMode.MODE_2D) {
            // Show coordinate in 2D
            // Ox axis 
            graphic.drawLine(1, Settings.COORD_Y_O, this.widthBoard, Settings.COORD_Y_O);
            // Oy axis
            graphic.drawLine(Settings.COORD_X_O, 1, Settings.COORD_X_O, this.heightBoard);

            // Coordinate of points
            // ??
        } else {
            // Show coordinate in 3D
            // Ox
            graphic.drawLine(Settings.COORD_X_O, Settings.COORD_Y_O, this.widthBoard, Settings.COORD_Y_O);
            // Oy
            graphic.drawLine(Settings.COORD_X_O, 1, Settings.COORD_X_O, Settings.COORD_Y_O);
            // Oz
            graphic.drawLine(
                    Settings.COORD_X_O, 
                    Settings.COORD_Y_O, 
                    Settings.COORD_X_O, 
                    1
            );
        }
    }

    @Override
    public void paintComponent(Graphics graphic) {
//        System.out.println("main.Panel_DrawingArea.paintComponent()");

        super.paintComponent(graphic);
        showGridLines(graphic);

        for (int i = 0; i < this.heightBoard; i++) {
            for (int j = 0; j < this.widthBoard; j++) {
                if (markedChangeOfBoard[i][j] == true) {
                    graphic.setColor(changedColorOfBoard[i][j]);
                } else {
                    graphic.setColor(colorOfBoard[i][j]);
                }
                graphic.fillRect(
                        i * (Settings.SPACE + Settings.SIZE) + 1,
                        j * (Settings.SPACE + Settings.SIZE) + 1,
                        Settings.SIZE,
                        Settings.SIZE
                );
            }
        }

        showCoordinate(graphic);
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
//    public static void putPixel(Graphics graphic, int x, int y) {
//        graphic.fillRect(getNewX(x), getNewY(y), this.size, this.size);
//    }
    /**
     * Merge color of this board with another in pixel having position marked in
     * permittedPoint array.
     *
     * @param permittedPoint
     * @param otherBoard
     */
    public void mergeColorValue(boolean[][] permittedPoint, Color[][] otherBoard) {
        for (int i = 0; i < this.heightBoard; i++) {
            for (int j = 0; j < this.widthBoard; j++) {
                if (permittedPoint[i][j] == true) {
                    this.colorOfBoard[i][j] = new Color(otherBoard[i][j].getRGB());
                }
            }
        }
    }
}
