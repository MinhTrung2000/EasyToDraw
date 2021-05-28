package view;

import control.SettingConstants;
import control.util.Ultility;
import control.myawt.SKPoint2D;
import model.shape2d.Rectangle;
import model.shape2d.Segment2D;
import model.shape2d.Triangle;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.Stack;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import model.shape2d.Arrow2D;
import model.shape2d.Diamond;
import model.shape2d.Ellipse;
import model.shape2d.Line2D;
import model.shape2d.Shape2D;
import model.shape2d.Star;
import model.shape3d.Cylinder;
import model.shape3d.Pyramid;
import model.shape3d.Rectangular;
import model.shape3d.Sphere;
import model.tuple.MyPair;

/**
 * Class used for implementing drawing area.
 */
public class DrawingPanel extends JPanel {

    /**
     * The actual width of board.
     */
    public static final int WIDTH_BOARD = SettingConstants.WIDTH_DRAW_AREA / SettingConstants.RECT_SIZE;

    /**
     * The actual height of board.
     */
    public static final int HEIGHT_BOARD = SettingConstants.HEIGHT_DRAW_AREA / SettingConstants.RECT_SIZE;

    // Color and coord property of all points when any drawing action is applied
    /**
     * Color of each pixel after applying drawing action.
     */
    public Color[][] colorOfBoard;

    /**
     * Coordination text of each pixel after applying drawing action.
     */
    private String[][] coordOfBoard;

    // The color property of all points when a new drawing action is happened.
    private Color[][] changedColorOfBoard;
    private String[][] changedCoordOfBoard;

    // This array is used to mark the point is changed in an action.
    private boolean[][] markedChangeOfBoard;

    // Recent drawn shape 2d
    private Shape2D recentShape = null;

    /**
     * Undo stack of coordinate
     */
    private Stack<String[][]> undoCoordOfBoardStack = new Stack<>();
    private Stack<Color[][]> undoColorOfBoardStack = new Stack<>();
    private Stack<String[][]> redoCoordOfBoardStack = new Stack<>();
    private Stack<Color[][]> redoColorOfBoardStack = new Stack<>();

    private Stack<SKPoint2D> undoPreviousPointStack = new Stack<>();
    private Stack<SKPoint2D> redoPreviousPointStack = new Stack<>();
    /**
     * Showing grid lines flag.
     */
    private boolean showGridFlag = SettingConstants.DEFAULT_VISUAL_SHOW_GRID;

    /**
     * Showing coordinate flag.
     */
    private boolean showCoordinateFlag = SettingConstants.DEFAULT_VISUAL_SHOW_COORDINATE;

    /**
     * Starting point of drawn object.
     */
    private SKPoint2D startDrawingPoint = new SKPoint2D();

    /**
     * Ending point of drawn object.
     */
    private SKPoint2D endDrawingPoint = new SKPoint2D();

    /**
     * User selected coordinate system.
     */
    private SettingConstants.CoordinateMode coordinateMode = SettingConstants.CoordinateMode.MODE_2D;

    /**
     * Selected option by user.
     */
    private SettingConstants.DrawingToolMode selectedToolMode = SettingConstants.DrawingToolMode.DRAWING_LINE_SEGMENT;

    /**
     * User selected color.
     */
    private Color selectedColor = SettingConstants.DEFAULT_FILL_COLOR;

    /**
     * User customized line style.
     */
    private SettingConstants.LineStyle selectedLineStyle = SettingConstants.LineStyle.DEFAULT;

    /**
     * User customized line size.
     */
    private SKPoint2D Polygon_previousPoint = null;
    private SKPoint2D Polygon_firstPoint = null;

    private SKPoint2D eraserPos = new SKPoint2D();

    public DrawingPanel() {
        this.colorOfBoard = new Color[HEIGHT_BOARD][WIDTH_BOARD];
        this.coordOfBoard = new String[HEIGHT_BOARD][WIDTH_BOARD];

        this.changedColorOfBoard = new Color[HEIGHT_BOARD][WIDTH_BOARD];
        this.changedCoordOfBoard = new String[HEIGHT_BOARD][WIDTH_BOARD];
        this.markedChangeOfBoard = new boolean[HEIGHT_BOARD][WIDTH_BOARD];

        resetChangedPropertyArray();
        resetSavedPropertyArray();

        this.addMouseMotionListener(new CustomMouseMotionHandling());
        this.addMouseListener(new CustomMouseClickHandling());

//        Volcano vol = new Volcano(markedChangeOfBoard, changedColorOfBoard, changedCoordOfBoard, selectedColor);
//        vol.setProperty(new SKPoint2D(80, 40), new SKPoint2D(30, 100));
//        vol.drawVolcano();
    }

    public Color[][] getColorOfBoard() {
        return this.colorOfBoard;
    }

    public String[][] getCoordOfBoard() {
        return this.coordOfBoard;
    }

    public boolean isEmpty() {
        boolean result = true;
        for (int i = 0; i < HEIGHT_BOARD; i++) {
            for (int j = 0; j < WIDTH_BOARD; j++) {
                if (!colorOfBoard[i][j].equals(SettingConstants.DEFAULT_PIXEL_COLOR)) {
                    result = false;
                    break;
                }
            }
            if (!result) {
                break;
            }
        }
        return result;
    }

    /**
     * Check for undo action.
     *
     * @return
     */
    public boolean ableUndo() {
        return !undoColorOfBoardStack.empty();
    }

    /**
     * Check for redo action.
     *
     * @return
     */
    public boolean ableRedo() {
        return !redoColorOfBoardStack.empty();
    }

    /**
     * Set selected drawing tool button.
     *
     * @param selectedToolMode
     */
    public boolean setSelectedToolMode(SettingConstants.DrawingToolMode selectedToolMode) {
        if ((selectedToolMode == SettingConstants.DrawingToolMode.DRAWING_TRANSFORM_ROTATION)
                || (selectedToolMode == SettingConstants.DrawingToolMode.DRAWING_TRANSFORM_SYMMETRY)) {
            if (recentShape == null) {
                JOptionPane.showMessageDialog(this.getParent(), "Draw your object before using transformation!");
                return false;
            }
        } else if (selectedToolMode == SettingConstants.DrawingToolMode.TOOL_CLEAR_ALL) {
            recentShape = null;
        } else if (selectedToolMode != SettingConstants.DrawingToolMode.DRAWING_POLYGON_FREE) {
            Polygon_firstPoint = null;
            Polygon_previousPoint = null;
        }

        this.selectedToolMode = selectedToolMode;
        return true;
    }

    /**
     * Get current drawing tool button.
     *
     * @return
     */
    public SettingConstants.DrawingToolMode getSelectedToolMode() {
        return this.selectedToolMode;
    }

    /**
     * Set selected line style.
     *
     * @param lineStyle
     */
    public void setSelectedLineStyle(SettingConstants.LineStyle lineStyle) {
        selectedLineStyle = lineStyle;
    }

    public void setSelectedColor(Color selectedColor) {
        this.selectedColor = selectedColor;
    }

    /**
     * Change coordinate system for the board. <br>
     * Release all buffered memory and clear the board.
     *
     * @param mode
     */
    public void setCoordinateMode(SettingConstants.CoordinateMode mode) {
        // Clear old coordinate system before changing coordinate mode flag
        this.coordinateMode = mode;

        resetChangedPropertyArray();
        resetSavedPropertyArray();
        disposeStack();

        showGridFlag = SettingConstants.DEFAULT_VISUAL_SHOW_GRID;
        showCoordinateFlag = SettingConstants.DEFAULT_VISUAL_SHOW_COORDINATE;

        this.repaint();
    }

    /**
     * Release all resource in undo, redo stack.
     */
    private void disposeStack() {
        redoColorOfBoardStack.clear();
        redoCoordOfBoardStack.clear();
        undoColorOfBoardStack.clear();
        undoCoordOfBoardStack.clear();

        undoPreviousPointStack.clear();
        redoPreviousPointStack.clear();
    }

    /**
     * Clear all drawn object in board by setting its default color and
     * coordinate value. <br>
     * This method doesn't release memory in stack. You can use disposeStack
     * method for this purpose.
     */
    public void resetSavedPropertyArray() {
        for (int i = 0; i < this.HEIGHT_BOARD; i++) {
            for (int j = 0; j < this.WIDTH_BOARD; j++) {
                colorOfBoard[i][j] = SettingConstants.DEFAULT_PIXEL_COLOR;
                coordOfBoard[i][j] = null;
            }
        }
    }

    /**
     * Clear all buffered drawn object in board. <br>
     */
    public void resetChangedPropertyArray() {
        for (int i = 0; i < this.HEIGHT_BOARD; i++) {
            for (int j = 0; j < this.WIDTH_BOARD; j++) {
                markedChangeOfBoard[i][j] = false;
                changedColorOfBoard[i][j] = SettingConstants.DEFAULT_PIXEL_COLOR;
                changedCoordOfBoard[i][j] = null;
            }
        }
    }

    public void setShowGridLinesFlag(boolean flag) {
        showGridFlag = flag;
        this.repaint();
    }

    public void setShowCoordinateFlag(boolean flag) {
        showCoordinateFlag = flag;
        this.repaint();
    }

    /**
     * Copy color value of each pixel from <code>color_board_from</code> to
     * <code>color_board_to</code>. <br>
     * Require they have the same size.
     *
     * @param color_board_from
     * @param color_board_to
     */
    public void copyColorValue(Color[][] color_board_from, Color[][] color_board_to, boolean fromColorOBToChangedCOB) {
        int height = color_board_from.length;
        int width = color_board_from[0].length;
        if (!fromColorOBToChangedCOB) {
            for (int row = 0; row < height; row++) {
                for (int col = 0; col < width; col++) {
                    color_board_to[row][col] = new Color(color_board_from[row][col].getRGB());
                }
            }
        } else {
            for (int row = 0; row < height; row++) {
                for (int col = 0; col < width; col++) {
                    markedChangeOfBoard[row][col] = true;
                    color_board_to[row][col] = new Color(color_board_from[row][col].getRGB());
                }
            }
        }

    }

    /**
     * Copy coordinate value of each pixel from <code>coord_board_from</code> to
     * <code>coord_board_to</code>. <br>
     * Require they have the same size.
     *
     * @param coord_board_from
     * @param coord_board_to
     */
    public void mergeCoordValue(String[][] coord_board_from, String[][] coord_board_to) {
        int height = coord_board_from.length;
        int width = coord_board_from[0].length;

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (coord_board_from[row][col] != null) {
                    coord_board_to[row][col] = coord_board_from[row][col];
                }
            }
        }
    }

    public void copyCoordValue(String[][] coord_board_from, String[][] coord_board_to) {
        int height = coord_board_from.length;
        int width = coord_board_from[0].length;

        for (int row = 0; row < height; row++) {
            System.arraycopy(coord_board_from[row], 0, coord_board_to[row], 0, width);
        }
    }

    /**
     * Save the current color of drawing board to undo stack.
     */
    private void saveCurrentColorBoardToUndoStack() {
        Color[][] tempBoard = new Color[HEIGHT_BOARD][WIDTH_BOARD];
        copyColorValue(colorOfBoard, tempBoard, false);
        undoColorOfBoardStack.push(tempBoard);
    }

    /**
     * Save the current coordinates of drawing board to undo stack.
     */
    private void saveCurrentCoordBoardToUndoStack() {
        String[][] tempBoard = new String[HEIGHT_BOARD][WIDTH_BOARD];
        copyCoordValue(coordOfBoard, tempBoard);
        undoCoordOfBoardStack.push(tempBoard);

    }

    /**
     * Save the current color of drawing board to redo stack.
     */
    private void saveCurrentColorBoardToRedoStack() {
        Color[][] tempBoard = new Color[HEIGHT_BOARD][WIDTH_BOARD];
        copyColorValue(colorOfBoard, tempBoard, false);
        redoColorOfBoardStack.push(tempBoard);

        redoPreviousPointStack.push(Polygon_previousPoint);
    }

    /**
     * Save the current coordinates of drawing board to redo stack.
     */
    private void saveCurrentCoordBoardToRedoStack() {
        String[][] tempBoard = new String[HEIGHT_BOARD][WIDTH_BOARD];
        copyCoordValue(coordOfBoard, tempBoard);
        redoCoordOfBoardStack.push(tempBoard);
    }

    private void hidePixels(boolean delete) {
        SKPoint2D hidePixelsPos = new SKPoint2D();
        if (!delete) {
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    hidePixelsPos.setLocation(eraserPos.getCoordX() + i, eraserPos.getCoordY() + j);
                    int x = hidePixelsPos.getCoordX();
                    int y = hidePixelsPos.getCoordY();
                    if (Ultility.checkValidPoint(changedColorOfBoard, hidePixelsPos.getCoordX(), hidePixelsPos.getCoordY())) {
                        changedColorOfBoard[y][x] = SettingConstants.DEFAULT_EMPTY_BACKGROUND_COLOR;
                        markedChangeOfBoard[y][x] = true;
                    }
                }

            }
        } else {
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    hidePixelsPos.setLocation(eraserPos.getCoordX() + i, eraserPos.getCoordY() + j);
                    int x = hidePixelsPos.getCoordX();
                    int y = hidePixelsPos.getCoordY();
                    if (Ultility.checkValidPoint(changedColorOfBoard, hidePixelsPos.getCoordX(), hidePixelsPos.getCoordY())) {
                        changedColorOfBoard[y][x] = SettingConstants.DEFAULT_EMPTY_BACKGROUND_COLOR;
                        markedChangeOfBoard[y][x] = true;
                        if (coordOfBoard[y][x] != null) {
                            coordOfBoard[y][x] = null;
                        }
                    }
                }

            }
        }

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

        resetChangedPropertyArray();
        if (!undoColorOfBoardStack.empty()) {
            saveCurrentColorBoardToRedoStack();
            copyColorValue(undoColorOfBoardStack.pop(), colorOfBoard, false);
        }
        if (!undoCoordOfBoardStack.empty()) {
            saveCurrentCoordBoardToRedoStack();
            copyCoordValue(undoCoordOfBoardStack.pop(), coordOfBoard);
        }
        if (!undoPreviousPointStack.empty()) {
            Polygon_previousPoint = undoPreviousPointStack.pop();
            //   System.out.println("Pop previous: " + Polygon_previousPoint.getCoordX() +" " + Polygon_previousPoint.getCoordY());
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
        if (!redoColorOfBoardStack.empty()) {
            saveCurrentColorBoardToUndoStack();
            copyColorValue(redoColorOfBoardStack.pop(), colorOfBoard, false);
        }
        if (!redoCoordOfBoardStack.empty()) {
            saveCurrentCoordBoardToUndoStack();
            copyCoordValue(redoCoordOfBoardStack.pop(), coordOfBoard);
        }
        if (!redoPreviousPointStack.empty()) {
            Polygon_previousPoint = redoPreviousPointStack.pop();
        }
        repaint();
    }

    /**
     * Save the last user's drawing action to saved board.
     */
    public void apply() {
        // Save current state to undo stack.
        saveCurrentColorBoardToUndoStack();
        saveCurrentCoordBoardToUndoStack();
        // Merge of changed color to saved state of board
        mergeColorValue();

        MainFrame.button_Undo.setEnabled(this.ableUndo());
        // Save the changed coordinate into board.
        mergeCoordValue(changedCoordOfBoard, coordOfBoard);

    }

    public void setSelected(SKPoint2D startPoint, SKPoint2D endPoint) {
        startDrawingPoint.setLocation(startPoint);
        endDrawingPoint.setLocation(endPoint);
    }

    public boolean getShowGridFlag() {
        return this.showGridFlag;
    }

    public boolean getShowCoordinateFlag() {
        return this.showCoordinateFlag;
    }

    /**
     * Return the current coordinate system.
     *
     * @return SettingConstants.CoordinateMode
     */
    public SettingConstants.CoordinateMode getCoordinateMode() {
        return this.coordinateMode;
    }

    /**
     * Fill background board with specific color.
     *
     * @param graphic
     * @param backgroundColor
     */
    private void drawBackgroundBoard(Graphics graphic, Color backgroundColor) {
        graphic.setColor(backgroundColor);
        graphic.fillRect(0, 0, SettingConstants.WIDTH_DRAW_AREA, SettingConstants.HEIGHT_DRAW_AREA);
    }

    /**
     * Show axis and point coordination.
     *
     * @param graphic
     */
    private void showBoardCoordination(Graphics graphic) {
        /*
            Show axis coordination.
         */
        graphic.setColor(SettingConstants.DEFAULT_COORDINATE_AXIS_COLOR);

        if (coordinateMode == SettingConstants.CoordinateMode.MODE_2D) {
            // Ox axis 
            graphic.drawLine(1, SettingConstants.COORD_Y_O, SettingConstants.WIDTH_DRAW_AREA, SettingConstants.COORD_Y_O);
            // Oy axis
            graphic.drawLine(SettingConstants.COORD_X_O, 1, SettingConstants.COORD_X_O, SettingConstants.HEIGHT_DRAW_AREA);

            // Add ), Ox, Oy text string
            graphic.drawString("O", SettingConstants.COORD_X_O - 10, SettingConstants.COORD_Y_O + 10);
            graphic.drawString("Ox", SettingConstants.WIDTH_DRAW_AREA - 20, SettingConstants.COORD_Y_O + 10);
            graphic.drawString("Oy", SettingConstants.COORD_X_O - 20, 10);

        } else {
            // Ox
            graphic.drawLine(SettingConstants.COORD_X_O, SettingConstants.COORD_Y_O, SettingConstants.WIDTH_DRAW_AREA, SettingConstants.COORD_Y_O);
            // Oz
            graphic.drawLine(SettingConstants.COORD_X_O, 1, SettingConstants.COORD_X_O, SettingConstants.COORD_Y_O);
            // Oy
            graphic.drawLine(SettingConstants.COORD_X_O, SettingConstants.COORD_Y_O, 1, SettingConstants.COORD_X_O + SettingConstants.COORD_Y_O);

            // Add O, Ox, Oy, Oz text string
            graphic.drawString("O", SettingConstants.COORD_X_O - 10, SettingConstants.COORD_Y_O + 10);
            graphic.drawString("Ox", SettingConstants.WIDTH_DRAW_AREA - 20, SettingConstants.COORD_Y_O + 10);
            graphic.drawString("Oy", SettingConstants.COORD_X_O - SettingConstants.COORD_Y_O - 10, SettingConstants.HEIGHT_DRAW_AREA - 10);
            graphic.drawString("Oz", SettingConstants.COORD_X_O - 20, 10);
        }

        /*
            Show point coordination.
         */
        graphic.setColor(SettingConstants.DEFAULT_COORDINATE_POINT_COLOR);

        for (int i = 0; i < HEIGHT_BOARD; i++) {
            for (int j = 0; j < WIDTH_BOARD; j++) {
                String coordinateProperty = coordOfBoard[i][j];

                if (coordinateProperty != null) {

                    int posX = (j + 1) * SettingConstants.RECT_SIZE;
                    int posY = i * SettingConstants.RECT_SIZE - 2;

                    if (posY < 0) {
                        posY = 0;
                    }

                    graphic.drawString(coordinateProperty, posX, posY);
                }
            }
        }
    }

    /**
     * Paint the board but not showing coordination.
     *
     * @param graphic
     */
    private void paintBoardColor(Graphics graphic) {
        if (showGridFlag) {
            drawBackgroundBoard(graphic, SettingConstants.DEFAULT_GRID_BACKGROUND_COLOR);
        } else {
            drawBackgroundBoard(graphic, SettingConstants.DEFAULT_EMPTY_BACKGROUND_COLOR);
        }

        for (int i = 0; i < this.HEIGHT_BOARD; i++) {
            for (int j = 0; j < this.WIDTH_BOARD; j++) {
                if (markedChangeOfBoard[i][j]) {
                    graphic.setColor(changedColorOfBoard[i][j]);
                } else {
                    graphic.setColor(colorOfBoard[i][j]);
                }

                graphic.fillRect(j * SettingConstants.RECT_SIZE + 1,
                        i * SettingConstants.RECT_SIZE + 1,
                        SettingConstants.SIZE,
                        SettingConstants.SIZE
                );
            }
        }

        if (selectedToolMode == SettingConstants.DrawingToolMode.TOOL_ERASER) {
            graphic.setColor(Color.BLACK);//-1 để dịch ra ngoài lưới pixel!
            graphic.drawRect((int) (eraserPos.getCoordX() - 1) * SettingConstants.RECT_SIZE, (int) (eraserPos.getCoordY() - 1) * SettingConstants.RECT_SIZE,
                    SettingConstants.RECT_SIZE * 3, SettingConstants.RECT_SIZE * 3);
        }
    }

    @Override
    public void paintComponent(Graphics graphic) {
        super.paintComponent(graphic);

        paintBoardColor(graphic);

        if (showCoordinateFlag) {
            showBoardCoordination(graphic);
        }
    }

    /**
     * Merge color of this board with another in pixel having position marked in
     * permittedPoint array.
     *
     * @param permittedPoint
     * @param otherBoard
     */
    private void mergeColorValue() {
        for (int i = 0; i < this.HEIGHT_BOARD; i++) {
            for (int j = 0; j < this.WIDTH_BOARD; j++) {
                if (markedChangeOfBoard[i][j] == true) {
                    colorOfBoard[i][j] = new Color(changedColorOfBoard[i][j].getRGB());
                }
            }
        }
    }

    public boolean checkStartingPointAvailable() {
        return (startDrawingPoint.getCoordX() != -1 && startDrawingPoint.getCoordY() != -1);
    }

    public void paintRotation(SKPoint2D centerPoint, double angle) {
        if (recentShape == null) {
            return;
        }
        recentShape.createRotate(centerPoint, angle);
        apply();
        repaint();
    }

    public void paintOCenterSymmetry() {
        if (recentShape == null) {
            return;
        }
        recentShape.createSymOCenter();
        apply();
        repaint();
    }

    public void paintOXSymmetry() {
        if (recentShape == null) {
            return;
        }
        recentShape.createSymOX();
        apply();
        repaint();
    }

    public void paintOYSymmetry() {
        if (recentShape == null) {
            return;
        }

        recentShape.createSymOY();
        apply();
        repaint();
    }

    public void paintViaPointSymmetry(SKPoint2D centerPoint) {
        if (recentShape == null) {
            return;
        }

        recentShape.createSymPoint(centerPoint);
        apply();
        repaint();
    }

    public void paintViaLineSymmetry(double a, double b, double c) {
        if (recentShape == null) {
            return;
        }

        recentShape.createSymLine(a, b, c);
        apply();
        repaint();
    }

    public void draw3DShapeRectangular(double center_x, double center_y, double center_z, int width, int height, int high) {
        resetChangedPropertyArray();
        Rectangular rectangular = new Rectangular(markedChangeOfBoard, changedColorOfBoard, changedCoordOfBoard, selectedColor);
        rectangular.setProperty(center_x, center_y, center_z, width, height, high);
        rectangular.drawOutline();
        rectangular.saveCoordinates();
        apply();
        repaint();
        recentShape = null;
    }

    public void draw3DShapeCylinder(double center_x, double center_y, double center_z, int radius, int high) {
        resetChangedPropertyArray();
        Cylinder cylinder = new Cylinder(markedChangeOfBoard, changedColorOfBoard, changedCoordOfBoard, selectedColor);
        cylinder.setProperty(center_x, center_y, center_z, radius, high);
        cylinder.drawOutline();
        cylinder.saveCoordinates();
        apply();
        repaint();
        recentShape = null;
    }

    public void draw3DShapePyramid(double center_x, double center_y, double center_z, int edge, int high) {
        resetChangedPropertyArray();
        Pyramid pyramid = new Pyramid(markedChangeOfBoard, changedColorOfBoard, changedCoordOfBoard, selectedColor);
        pyramid.setProperty(center_x, center_y, center_z, edge, high);
        pyramid.drawOutline();
        pyramid.saveCoordinates();
        apply();
        repaint();
        recentShape = null;
    }

    public void draw3DShapeSphere(double center_x, double center_y, double center_z, int radius) {
        resetChangedPropertyArray();
        Sphere sphere = new Sphere(markedChangeOfBoard, changedColorOfBoard, changedCoordOfBoard, selectedColor);
        sphere.setProperty(center_x, center_y, center_z, radius);
        sphere.drawOutline();
        sphere.saveCoordinates();
        apply();
        repaint();
        recentShape = null;
    }

    public MyPair getXBound() {
        int half_x = (int) (this.WIDTH_BOARD / 2);
        return new MyPair(-half_x, half_x);
    }

    public MyPair getYBound() {
        int half_y = (int) (this.HEIGHT_BOARD / 2);
        return new MyPair(-half_y, half_y);
    }

    private class CustomMouseClickHandling implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent event) {
        }

        @Override
        public void mousePressed(MouseEvent event) {
            if (!SwingUtilities.isLeftMouseButton(event)) {
                return;
            }

            if (coordinateMode == SettingConstants.CoordinateMode.MODE_3D) {
                return;
            }

            startDrawingPoint.setLocation(event.getX() / SettingConstants.RECT_SIZE,
                    event.getY() / SettingConstants.RECT_SIZE);

            resetChangedPropertyArray();

            switch (selectedToolMode) {
                case DRAWING_LINE_FREE: {
                    markedChangeOfBoard[startDrawingPoint.getCoordY()][startDrawingPoint.getCoordX()] = true;
                    changedColorOfBoard[startDrawingPoint.getCoordY()][startDrawingPoint.getCoordX()] = selectedColor;
                    startDrawingPoint.saveCoord(changedCoordOfBoard);
                    repaint();
                    recentShape = null;
                    break;
                }
                case TOOL_COLOR_FILLER: {
                    copyColorValue(colorOfBoard, changedColorOfBoard, true);
                    SKPoint2D currentMousePos = new SKPoint2D();
                    currentMousePos.setLocation(event.getX() / SettingConstants.RECT_SIZE,
                            event.getY() / SettingConstants.RECT_SIZE);
                    Ultility.paint(changedColorOfBoard, markedChangeOfBoard,
                            currentMousePos, selectedColor, false);
                    repaint();
                    recentShape = null;
                    break;
                }
                case DRAWING_POLYGON_FREE: {
                    if (checkStartingPointAvailable()) {

                        //nếu như đây là 1 chu trình polygon mới
                        if (Polygon_previousPoint == null) {
                            Polygon_previousPoint = new SKPoint2D(startDrawingPoint);
                            Polygon_firstPoint = new SKPoint2D(startDrawingPoint);

                            markedChangeOfBoard[Polygon_firstPoint.getCoordY()][Polygon_firstPoint.getCoordX()] = true;
                            changedColorOfBoard[Polygon_firstPoint.getCoordY()][Polygon_firstPoint.getCoordX()] = Color.RED;

                            Polygon_firstPoint.saveCoord(changedCoordOfBoard);

                            repaint();
                            return;
                        }

                        //không phải chu trình mới thì vẽ đoạn thẳng
                        Segment2D segment = new Segment2D(markedChangeOfBoard,
                                changedColorOfBoard, changedCoordOfBoard, selectedColor);
                        segment.setProperty(Polygon_previousPoint, startDrawingPoint,
                                Segment2D.Modal.STRAIGHT_LINE);

                        segment.setLineStyle(selectedLineStyle);
                        segment.drawOutline();

                        //vẽ lại chấm đỏ phòng trường hợp bị đè mất
                        markedChangeOfBoard[Polygon_firstPoint.getCoordY()][Polygon_firstPoint.getCoordX()] = true;
                        changedColorOfBoard[Polygon_firstPoint.getCoordY()][Polygon_firstPoint.getCoordX()] = Color.RED;

                        //kiểm tra xem đây có phải đoạn thẳng kết thúc chu trình
                        boolean end = false;
//                        int D_X[] = {-1, 0, 0, 1, -1, 1, 1, -1};
//                        int D_Y[] = {0, -1, 1, 0, 1, 1, -1, -1};

                        if (startDrawingPoint.equal(Polygon_firstPoint)) {
                            end = true;
                        }

                        //điểm lân cận 1 pixel cũng tính là first point
                        SKPoint2D neibourhoodPoint = new SKPoint2D();
                        for (int i = -1; i <= 1; i++) {
                            for (int j = -1; j <= 1; j++) {
                                neibourhoodPoint.setLocation(
                                        startDrawingPoint.getCoordX() + i,
                                        startDrawingPoint.getCoordY() + j
                                );
                                if (neibourhoodPoint.equal(Polygon_firstPoint)) {
                                    end = true;
                                    break;
                                }
                            }

                        }

                        if (end == false) {
                            segment.saveCoordinates();

                            undoPreviousPointStack.push(Polygon_previousPoint);

                            Polygon_previousPoint = new SKPoint2D(startDrawingPoint);
                        } else {
                            Polygon_previousPoint = null;
                            Polygon_firstPoint = null;
                        }

                    }
                    break;
                }
                case TOOL_ERASER: {
                    //Không resetChangedProperty vì đây là đè, cố ý muốn xóa
                    if (checkStartingPointAvailable()) {

                        eraserPos.setLocation(event.getX() / SettingConstants.RECT_SIZE,
                                event.getY() / SettingConstants.RECT_SIZE);
                        hidePixels(true);
                    }
                    repaint();
                    recentShape = null;
                }
                case TOOL_ANIMATION: {
                    recentShape = null;
                    break;
                }

            }
        }

        @Override
        public void mouseReleased(MouseEvent event) {

            if (!SwingUtilities.isLeftMouseButton(event)) {
                return;
            }

            switch (selectedToolMode) {
                case DRAWING_LINE_FREE: {
                    startDrawingPoint.saveCoord(changedCoordOfBoard);
                    break;
                }
            }
            apply();
            repaint();

        }

        @Override
        public void mouseEntered(MouseEvent e) {
            // trường hợp từ mode khác, bấm vào eraser thì dùng đến vế thứ 2 của if!
            if (selectedToolMode == SettingConstants.DrawingToolMode.TOOL_ERASER__FALSE
                    || selectedToolMode == SettingConstants.DrawingToolMode.TOOL_ERASER) {
                selectedToolMode = SettingConstants.DrawingToolMode.TOOL_ERASER;

                // Transparent 16 x 16 pixel cursor image.
                BufferedImage cursorImg = new BufferedImage(16, 16,
                        BufferedImage.TYPE_INT_ARGB);

                // Create a new blank cursor.
                Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
                        cursorImg, new Point(0, 0), "blank cursor");

                setCursor(blankCursor);
            } else {
                setCursor(Cursor.getDefaultCursor());
            }
        }

        @Override
        public void mouseExited(MouseEvent e) {
            if (selectedToolMode == SettingConstants.DrawingToolMode.TOOL_ERASER) {
                // set về cái này để repaint() lại mất eraser!
                selectedToolMode = SettingConstants.DrawingToolMode.TOOL_ERASER__FALSE;
                repaint();
            }
        }

    }

    private class CustomMouseMotionHandling implements MouseMotionListener {

        @Override
        public void mouseDragged(MouseEvent event) {

            if (!SwingUtilities.isLeftMouseButton(event)) {
                return;
            }

            if (coordinateMode == SettingConstants.CoordinateMode.MODE_3D) {
                return;
            }

            endDrawingPoint.setLocation(event.getX() / SettingConstants.RECT_SIZE,
                    event.getY() / SettingConstants.RECT_SIZE);

            switch (selectedToolMode) {
                case DRAWING_LINE_SEGMENT: {
                    resetChangedPropertyArray();
                    if (checkStartingPointAvailable()) {
                        Segment2D segment = new Segment2D(markedChangeOfBoard,
                                changedColorOfBoard, changedCoordOfBoard, selectedColor);
                        if (event.isShiftDown()) {
                            segment.setProperty(startDrawingPoint, endDrawingPoint,
                                    Segment2D.Modal.LINE_45_90_DEGREE);
                        } else {
                            segment.setProperty(startDrawingPoint, endDrawingPoint,
                                    Segment2D.Modal.STRAIGHT_LINE);
                        }
                        segment.setLineStyle(selectedLineStyle);
                        segment.drawOutline();
                        segment.saveCoordinates();
                        recentShape = segment;
                    }
                    repaint();
                    break;
                }
                case DRAWING_LINE_STRAIGHT: {
                    // Do no thing
                    resetChangedPropertyArray();
                    if (checkStartingPointAvailable()) {
                        Line2D line = new Line2D(markedChangeOfBoard,
                                changedColorOfBoard, changedCoordOfBoard, selectedColor);
                        line.setLineStyle(selectedLineStyle);
                        line.setProperty(startDrawingPoint, endDrawingPoint);
                        line.drawOutline();
                        line.saveCoordinates();
                        recentShape = line;
                    }
                    repaint();
                    break;
                }
                case DRAWING_LINE_FREE: {
                    if (checkStartingPointAvailable()) {
                        Segment2D pixel = new Segment2D(markedChangeOfBoard,
                                changedColorOfBoard, changedCoordOfBoard, selectedColor);
                        pixel.setProperty(startDrawingPoint, endDrawingPoint,
                                Segment2D.Modal.STRAIGHT_LINE);
                        pixel.drawOutline();
                    }
                    startDrawingPoint.setLocation(event.getX() / SettingConstants.RECT_SIZE,
                            event.getY() / SettingConstants.RECT_SIZE);
                    repaint();
                    break;
                }
                case DRAWING_POLYGON_TRIANGLE: {
                    resetChangedPropertyArray();
                    if (checkStartingPointAvailable()) {
                        Triangle triangle = new Triangle(markedChangeOfBoard,
                                changedColorOfBoard, changedCoordOfBoard, selectedColor);
                        if (event.isShiftDown()) {
                            triangle.setProperty(startDrawingPoint, endDrawingPoint,
                                    Triangle.Modal.EQUILATERAL_TRIANGLE);
                        } else {
                            triangle.setProperty(startDrawingPoint, endDrawingPoint,
                                    Triangle.Modal.COMMON_TRIANGLE);
                        }
                        triangle.setLineStyle(selectedLineStyle);
                        triangle.drawOutline();
                        triangle.saveCoordinates();
                        recentShape = triangle;
                    }
                    repaint();
                    break;
                }
                case DRAWING_POLYGON_RECTANGLE: {
                    resetChangedPropertyArray();
                    if (checkStartingPointAvailable()) {
                        Rectangle rectangle = new Rectangle(markedChangeOfBoard,
                                changedColorOfBoard, changedCoordOfBoard, selectedColor);
                        if (event.isShiftDown()) {
                            rectangle.setProperty(startDrawingPoint, endDrawingPoint,
                                    Rectangle.Modal.SQUARE);
                        } else {
                            rectangle.setProperty(startDrawingPoint, endDrawingPoint,
                                    Rectangle.Modal.RECTANGLE);
                        }
                        rectangle.setLineStyle(selectedLineStyle);
                        rectangle.drawOutline();
                        rectangle.saveCoordinates();
                        recentShape = rectangle;
                    }
                    repaint();
                    break;
                }
                case DRAWING_POLYGON_ELLIPSE: {
                    resetChangedPropertyArray();
                    if (checkStartingPointAvailable()) {
                        Ellipse ellipse = new Ellipse(markedChangeOfBoard,
                                changedColorOfBoard, changedCoordOfBoard, selectedColor);

                        if (event.isShiftDown()) {
                            ellipse.setProperty(startDrawingPoint, endDrawingPoint,
                                    Ellipse.Modal.CIRLCE);
                        } else {
                            ellipse.setProperty(startDrawingPoint, endDrawingPoint,
                                    Ellipse.Modal.ELLIPSE);
                        }

                        ellipse.setLineStyle(selectedLineStyle);
                        ellipse.drawOutline();
                        ellipse.saveCoordinates();
                        recentShape = ellipse;
                    }
                    repaint();
                    break;
                }
                case DRAWING_SHAPE_STAR: {
                    resetChangedPropertyArray();
                    if (checkStartingPointAvailable()) {
                        Star star = new Star(markedChangeOfBoard,
                                changedColorOfBoard, changedCoordOfBoard, selectedColor);
                        star.setProperty(startDrawingPoint, endDrawingPoint);
                        star.setLineStyle(selectedLineStyle);
                        star.drawOutline();
                        star.saveCoordinates();
                        recentShape = star;
                    }
                    repaint();
                    break;
                }
                case DRAWING_SHAPE_DIAMOND: {
                    resetChangedPropertyArray();
                    if (checkStartingPointAvailable()) {
                        Diamond diamond = new Diamond(markedChangeOfBoard,
                                changedColorOfBoard, changedCoordOfBoard, selectedColor);
                        if (event.isShiftDown()) {
                            diamond.setProperty(startDrawingPoint, endDrawingPoint,
                                    Diamond.Modal.SQUARE_DIAMOND);
                        } else {
                            diamond.setProperty(startDrawingPoint, endDrawingPoint,
                                    Diamond.Modal.COMMON_DIAMOND);
                        }
                        diamond.setLineStyle(selectedLineStyle);
                        diamond.drawOutline();
                        diamond.saveCoordinates();
                        recentShape = diamond;
                    }
                    repaint();
                    break;
                }
                case DRAWING_SHAPE_ARROW: {
                    resetChangedPropertyArray();
                    if (checkStartingPointAvailable()) {
                        Arrow2D arrow = new Arrow2D(markedChangeOfBoard,
                                changedColorOfBoard, changedCoordOfBoard, selectedColor);
                        arrow.setProperty(startDrawingPoint, endDrawingPoint);
                        arrow.setLineStyle(selectedLineStyle);
                        arrow.drawOutline();
                        arrow.saveCoordinates();
                        recentShape = arrow;
                    }
                    repaint();
                    break;
                }
                case TOOL_ERASER: {
                    //Không resetChangedProperty vì đây là đè, cố ý muốn xóa
                    if (checkStartingPointAvailable()) {
                        eraserPos.setLocation(event.getX() / SettingConstants.RECT_SIZE,
                                event.getY() / SettingConstants.RECT_SIZE);
                        hidePixels(true);
                    }
                    repaint();
                }
            }
        }

        /*
            Use for showing eraser.
         */
        @Override
        public void mouseMoved(MouseEvent e) {
            if (selectedToolMode == SettingConstants.DrawingToolMode.TOOL_ERASER) {
                // để nó hiện lại những chỗ đã đi qua
                resetChangedPropertyArray();
                eraserPos.setLocation(e.getX() / SettingConstants.RECT_SIZE,
                        e.getY() / SettingConstants.RECT_SIZE);
                hidePixels(false);

                repaint();

            }
            return;
        }

    }

}
