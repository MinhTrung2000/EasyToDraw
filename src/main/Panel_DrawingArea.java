package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Event;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Stack;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import static main.Settings.COORD_X_O;
import static main.Settings.COORD_Y_O;
import static main.Settings.SIZE;
import static main.Settings.SPACE;

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
    private static Stack<String[][]> undoCoordOfBoardStack;
    private static Stack<Color[][]> undoColorOfBoardStack;
    private static Stack<String[][]> redoCoordOfBoardStack;
    private static Stack<Color[][]> redoColorOfBoardStack;

    // Visual option
    private boolean showGridFlag;
    private boolean showCoordinateFlag;

//    private Point2D mousePoint;
    private Point2D startDrawingPoint;
    private Point2D endDrawingPoint;

    // Coordinated system.
    private Settings.CoordinateMode coordinateMode;

    // Color of board, includes grid color and background color and coordinate color
    private Color gridColor;
    private Color backgroundColor;
    private Color coordinateColor;

    /**
     * Selected option by user.
     */
    private Settings.DrawingToolMode selectedToolMode;
    private Color selectedColor;
    private Settings.LineStyle selectedLineStyle;
    private Integer selectedLineSize;

    private Shape genericShape;

    public Panel_DrawingArea() {
        widthOfScreen = Settings.WIDTH_DRAW_AREA;
        heightOfScreen = Settings.HEIGHT_DRAW_AREA;

        this.colorOfBoard = new Color[heightBoard][widthBoard];
        this.coordOfBoard = new String[heightBoard][widthBoard];

        this.changedColorOfBoard = new Color[heightBoard][widthBoard];
        this.changedCoordOfBoard = new String[heightBoard][widthBoard];

        this.markedChangeOfBoard = new boolean[heightBoard][widthBoard];

        undoCoordOfBoardStack = new Stack<>();
        undoColorOfBoardStack = new Stack<>();
        redoCoordOfBoardStack = new Stack<>();
        redoColorOfBoardStack = new Stack<>();

        showGridFlag = Settings.DEFAULT_VISUAL_SHOW_GRID;
        showCoordinateFlag = Settings.DEFAULT_VISUAL_SHOW_COORDINATE;

        coordinateMode = Settings.CoordinateMode.MODE_2D;

        backgroundColor = Settings.DEFAULT_DRAWING_BACKGROUND_COLOR;
        gridColor = Settings.DEFAULT_DRAWING_GRID_COLOR;
        coordinateColor = Settings.DEFAULT_DRAWING_COORDINATE_COLOR;

//        mousePoint = Settings.DEFAULT_UNUSED_POINT;
        startDrawingPoint = new Point2D(Settings.DEFAULT_UNUSED_POINT);
        endDrawingPoint = new Point2D(Settings.DEFAULT_UNUSED_POINT);

        selectedToolMode = Settings.DrawingToolMode.DRAWING_LINE_SEGMENT;
        selectedColor = Settings.DEFAULT_FILL_COLOR;
        selectedLineStyle = Settings.LineStyle.DEFAULT;
        selectedLineSize = Settings.DEFAULT_LINE_SIZE;

        genericShape = new Shape(markedChangeOfBoard, changedColorOfBoard, changedCoordOfBoard);

        resetChangedPropertyArray();
        resetSavedPropertyArray();

        this.addMouseMotionListener(new CustomMouseMotionHandling());
        this.addMouseListener(new CustomMouseClickHandling());
    }

    public void setStartDrawingPoint(int coordX, int coordY) {
        this.startDrawingPoint.setCoord(coordX, coordY);
    }

    public void setEndDrawingPoint(int coordX, int coordY) {
        this.endDrawingPoint.setCoord(coordX, coordY);
    }

    public boolean ableUndo() {
        return !undoColorOfBoardStack.empty();
    }

    public boolean ableRedo() {
        return !redoColorOfBoardStack.empty();
    }

    public void setSelectedButtonMode(Settings.DrawingToolMode selectedToolMode) {
        this.selectedToolMode = selectedToolMode;
    }

    public Settings.DrawingToolMode getSelectedToolMode() {
        return this.selectedToolMode;
    }

    public void setSelectedLineSize(int lineSize) {
        selectedLineSize = lineSize;
    }

    public void setSelectedLineStyle(Settings.LineStyle lineStyle) {
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
    public void setCoordinateMode(Settings.CoordinateMode mode) {
        // Clear old coordinate system before changing coordinate mode flag
        this.coordinateMode = mode;

        resetSavedPropertyArray();
        disposeStack();

        showGridFlag = Settings.DEFAULT_VISUAL_SHOW_GRID;
        showCoordinateFlag = Settings.DEFAULT_VISUAL_SHOW_COORDINATE;

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
    }

    /**
     * Clear all drawn object in board by setting its default color and
     * coordinate value. <br>
     * This method doesn't release memory in stack. You can use disposeStack
     * method for this purpose.
     */
    public void resetSavedPropertyArray() {
        for (int i = 0; i < this.heightBoard; i++) {
            for (int j = 0; j < this.widthBoard; j++) {
                colorOfBoard[i][j] = this.gridColor;
                coordOfBoard[i][j] = null;
            }
        }
    }

    /**
     * Clear all buffered drawn object in board. <br>
     */
    public void resetChangedPropertyArray() {
        for (int i = 0; i < this.heightBoard; i++) {
            for (int j = 0; j < this.widthBoard; j++) {
                markedChangeOfBoard[i][j] = false;
                changedColorOfBoard[i][j] = this.gridColor;
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
        undoColorOfBoardStack.push(tempBoard);
    }

    /**
     * Save the current coordinates of drawing board to undo stack.
     */
    private void saveCurrentCoordBoardToUndoStack() {
        String[][] tempBoard = new String[heightBoard][widthBoard];
        copyCoordValue(coordOfBoard, tempBoard);
        undoCoordOfBoardStack.push(tempBoard);
    }

    /**
     * Save the current color of drawing board to redo stack.
     */
    private void saveCurrentColorBoardToRedoStack() {
        Color[][] tempBoard = new Color[heightBoard][widthBoard];
        copyColorValue(colorOfBoard, tempBoard);
        redoColorOfBoardStack.push(tempBoard);
    }

    /**
     * Save the current coordinates of drawing board to redo stack.
     */
    private void saveCurrentCoordBoardToRedoStack() {
        String[][] tempBoard = new String[heightBoard][widthBoard];
        copyCoordValue(coordOfBoard, tempBoard);
        redoCoordOfBoardStack.push(tempBoard);
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
        if (!undoColorOfBoardStack.empty()) {
            saveCurrentColorBoardToRedoStack();
            copyColorValue(undoColorOfBoardStack.pop(), colorOfBoard);
        }
        if (!undoCoordOfBoardStack.empty()) {
            saveCurrentCoordBoardToRedoStack();
            copyCoordValue(undoCoordOfBoardStack.pop(), coordOfBoard);
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
            copyColorValue(redoColorOfBoardStack.pop(), colorOfBoard);
        }
        if (!redoCoordOfBoardStack.empty()) {
            saveCurrentCoordBoardToUndoStack();
            copyCoordValue(redoCoordOfBoardStack.pop(), coordOfBoard);
        }
    }

    /**
     * Save the last user's drawing action to saved board.
     */
    public void apply() {
        // Clear redo stacks.
//        redoColorOfBoardStack.clear();
//        redoCoordOfBoardStack.clear();

        // Save current state to undo stack.
//        saveCurrentColorBoardToUndoStack();
//        saveCurrentCoordBoardToUndoStack();

        // Merge of changed color to saved state of board
        // NOTE: Why not mergeColorValue coordinate??
        mergeColorValue();

        // Save the changed coordinate into board.
//        copyCoordValue(coordOfBoard, changedCoordOfBoard);

        // Reset marked change array.
        resetChangedPropertyArray();
    }

    private boolean isNotSelected() {
        return (startDrawingPoint.equal(Settings.DEFAULT_UNUSED_POINT)
                && endDrawingPoint.equal(Settings.DEFAULT_UNUSED_POINT));
    }

    public void setSelected(Point2D startPoint, Point2D endPoint) {
        startDrawingPoint.setCoord(startPoint);
        endDrawingPoint.setCoord(endPoint);
    }

    /**
     * Create grid-line in board by fill each pixels.
     *
     * @param graphic
     */
    public void showGridLines(Graphics graphic) {
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

    /**
     * Show coordinate of the board.
     *
     * @param graphic
     */
    public void showCoordinate(Graphics graphic) {
        graphic.setColor(this.coordinateColor);

        if (coordinateMode == Settings.CoordinateMode.MODE_2D) {
            // Show coordinate in 2D
            // Ox axis 
            graphic.drawLine(1, Settings.COORD_Y_O, this.widthBoard, Settings.COORD_Y_O);
            // Oy axis
            graphic.drawLine(Settings.COORD_X_O, 1, Settings.COORD_X_O, this.heightBoard);

            // Coordinate of points
            for (int i = 0; i < this.heightBoard; i++) {
                for (int j = 0; j < this.widthBoard; j++) {
                    String coordinateProperty = coordOfBoard[i][j];
                    if (coordinateProperty != null) {
                        if (coordinateProperty.equals(this.gridColor)) {
                            graphic.setColor(Settings.DEFAULT_DRAWING_COLOR);
                        } else {
                            graphic.setColor(colorOfBoard[i][j]);
                        }
                        int rectSize = Settings.SPACE + Settings.SIZE;

                        // 5 at end??
                        int posX = i * rectSize + 5;
                        int posY = j * rectSize - 3;

                        // Why?
                        if (posX + coordinateProperty.length() * 6 > (this.widthBoard + rectSize)) {
                            posX = (this.widthBoard + rectSize) - coordinateProperty.length() * 6;
                        }

                        if (posY < 15) {
                            posY = 15;
                        }

                        graphic.drawString(coordinateProperty, posX, posY);

//                        System.out.println(posX + "   " + posY);
                    }
                }
            }
            // ??
        } else {
            // Show coordinate in 3D
            // Ox
            graphic.drawLine(Settings.COORD_X_O, Settings.COORD_Y_O, this.widthBoard, Settings.COORD_Y_O);
            // Oy
            graphic.drawLine(Settings.COORD_X_O, 1, Settings.COORD_X_O, Settings.COORD_Y_O);
            // Oz
            graphic.drawLine(Settings.COORD_X_O, Settings.COORD_Y_O, 1, Settings.COORD_X_O + Settings.COORD_Y_O);
        }
    }

    public boolean getShowGridFlag() {
        return showGridFlag;
    }

    public boolean getShowCoordinateFlag() {
        return showCoordinateFlag;
    }

    /**
     * Return the current coordinate system.
     *
     * @return Settings.CoordinateMode
     */
    public Settings.CoordinateMode getCoordinateMode() {
        return this.coordinateMode;
    }

    private void processCoordShowingOption(Graphics graphic) {
        graphic.setColor(Color.WHITE);
        graphic.fillRect(0, 0, this.widthBoard, this.heightBoard);

        if (showGridFlag == true) {
            if (showCoordinateFlag == true) {
                showGridLines(graphic);
                showCoordinate(graphic);
            } else {
                showGridLines(graphic);
            }
        } else {
            if (showCoordinateFlag == true) {
                showCoordinate(graphic);
            }
        }
    }

    /**
     * Show color property of drawn object.
     *
     * @param graphic
     */
    private void showObjectColor(Graphics graphic) {
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
    }

    /**
     * Show eraser by small rectangle.
     *
     * @param graphic
     */
    public void showEraserTool(Graphics graphic) {
    }

    /**
     * Show selection tool.
     *
     * @param graphic
     */
    public void showSelectTool(Graphics graphic) {

    }

    @Override
    public void paintComponent(Graphics graphic) {
        super.paintComponent(graphic);
        processCoordShowingOption(graphic);
        showObjectColor(graphic);
    }

    private void selectionTool(Graphics graphic) {
//        if (!isNotSelected()) {
//            graphic.setColor(Color.BLACK);
//            //System.out.println("ccccccccc");
//            Graphics2D g2d = (Graphics2D) graphic;
//            g2d.setColor(Color.pink);
//
//            float[] dashingPattern1 = {2f, 2f};
//            Stroke stroke1 = new BasicStroke(2f, BasicStroke.CAP_BUTT,
//                    BasicStroke.JOIN_MITER, 1.0f, dashingPattern1, 3.0f);
//
//            g2d.setStroke(stroke1);
//            g2d.drawRect(recStart.X * rectSize, recStart.Y * rectSize,
//                    Math.abs(recStart.X - recEnd.X) * rectSize, Math.abs(recStart.Y - recEnd.Y) * rectSize);
//            recStart.set(-1, -1);
//            recEnd.set(-1, -1);
//
//        }        
    }

    /**
     * Merge color of this board with another in pixel having position marked in
     * permittedPoint array.
     *
     * @param permittedPoint
     * @param otherBoard
     */
    private void mergeColorValue() {
        for (int i = 0; i < this.heightBoard; i++) {
            for (int j = 0; j < this.widthBoard; j++) {
                if (markedChangeOfBoard[i][j] == true) {
                    System.out.println("colorOfBoard[i][j]: " + colorOfBoard[i][j]);
                    colorOfBoard[i][j] = new Color(changedColorOfBoard[i][j].getRGB());
                }
            }
        }
    }

    public void symOx() {
        if (genericShape instanceof Segment2D) {
            ((Segment2D) genericShape).drawOXSymmetry();
        } else if (genericShape instanceof Rectangle) {
            ((Rectangle) genericShape).drawOXSymmetry();
        } else if (genericShape instanceof Circle) {
            ((Circle) genericShape).drawOXSymmetry();
        } else if (genericShape instanceof Triangle) {
            ((Triangle) genericShape).drawOXSymmetry();
        } else if (genericShape instanceof Arrow2D) {
            ((Arrow2D) genericShape).drawOXSymmetry();
        }
    }

    public void symOy() {
        if (genericShape instanceof Segment2D) {
            ((Segment2D) genericShape).drawOYSymmetry();
        } else if (genericShape instanceof Rectangle) {
            ((Rectangle) genericShape).drawOYSymmetry();
        } else if (genericShape instanceof Circle) {
            ((Circle) genericShape).drawOYSymmetry();
        } else if (genericShape instanceof Triangle) {
            ((Triangle) genericShape).drawOYSymmetry();
        } else if (genericShape instanceof Arrow2D) {
            ((Arrow2D) genericShape).drawOYSymmetry();
        }
    }

    public void symPoint(Point2D basePoint) {
        if (genericShape instanceof Segment2D) {
            ((Segment2D) genericShape).drawPointSymmetry(basePoint);
        } else if (genericShape instanceof Rectangle) {
            ((Rectangle) genericShape).drawPointSymmetry(basePoint);
        } else if (genericShape instanceof Circle) {
            ((Circle) genericShape).drawPointSymmetry(basePoint);
        } else if (genericShape instanceof Triangle) {
            ((Triangle) genericShape).drawPointSymmetry(basePoint);
        } else if (genericShape instanceof Arrow2D) {
            ((Arrow2D) genericShape).drawPointSymmetry(basePoint);
        }
    }

    public boolean checkStartingPointAvailable() {
        return (startDrawingPoint.coordX != -1 && startDrawingPoint.coordY != -1);
    }

    public class CustomMouseClickHandling implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent event) {
//            System.out.println("main.Panel_DrawingArea.CustomMouseClickHandling.mouseClicked()");

            if (!SwingUtilities.isLeftMouseButton(event)) {
                return;
            }

            resetChangedPropertyArray();

            Settings.DrawingToolMode selectedTool = getSelectedToolMode();

            switch (selectedTool) {
                case DRAWING_TRANSFORM_ROTATION: {

                }
                case DRAWING_TRANSFORM_SYMMETRY: {

                }
            }
        }

        @Override
        public void mousePressed(MouseEvent event) {
//            System.out.println("main.Panel_DrawingArea.CustomMouseClickHandling.mousePressed()");
            if (!SwingUtilities.isLeftMouseButton(event)) {
                return;
            }

            resetChangedPropertyArray();
//
            setStartDrawingPoint(event.getX() / (Settings.SIZE + Settings.SPACE),
                    event.getY() / (Settings.SIZE + Settings.SPACE));

        }

        @Override
        public void mouseReleased(MouseEvent event) {
//            System.out.println("main.Panel_DrawingArea.CustomMouseClickHandling.mouseReleased()");
            
            if (!SwingUtilities.isLeftMouseButton(event)) {
                return;
            }
            
            apply();
        }

        @Override
        public void mouseEntered(MouseEvent e) {
//            if (selectedButton == button_ColorPicker) {
//                panel_DrawingArea.setCursor(toolkit.createCustomCursor(
//                        toolkit.getImage("/img/pencil.png"),
//                        new Point(0, 0),
//                        "img")
//                );
//            }
        }

        @Override
        public void mouseExited(MouseEvent e) {
//            setCursor(Cursor.getDefaultCursor());
        }

    }

    public class CustomMouseMotionHandling implements MouseMotionListener {

        @Override
        public void mouseDragged(MouseEvent event) {
//            System.out.println("main.Panel_DrawingArea.CustomMouseMotionHandling.mouseDragged()");

            if (!SwingUtilities.isLeftMouseButton(event)) {
                return;
            }

            Settings.DrawingToolMode selectedTool = getSelectedToolMode();

//            System.out.println("Selected tools:" + selectedTool);
            
            switch (selectedTool) {
                case DRAWING_LINE_SEGMENT: {
                    resetChangedPropertyArray();

                    setEndDrawingPoint(event.getX() / (Settings.SIZE + Settings.SPACE), event.getY() / (Settings.SIZE + Settings.SPACE));

                    if (checkStartingPointAvailable()) {
                        System.out.println("Start point: " + startDrawingPoint.coordX + " , " + startDrawingPoint.coordY);
                        Segment2D segment = new Segment2D(markedChangeOfBoard, changedColorOfBoard, changedCoordOfBoard);
                        segment.setProperty(startDrawingPoint, endDrawingPoint);
                        segment.setLineStyle(selectedLineStyle);
                        segment.draw();
                        segment.saveCoordinates();
                    }
                    repaint();
                    break;
                }
                case DRAWING_POLYGON_RECTANGLE: {
                    resetChangedPropertyArray();
                    
                    setEndDrawingPoint(event.getX() / (Settings.SIZE + Settings.SPACE), event.getY() / (Settings.SIZE + Settings.SPACE));
                    if (checkStartingPointAvailable()) {
                        Rectangle rectangle = new Rectangle(markedChangeOfBoard, changedColorOfBoard, changedCoordOfBoard);
                        rectangle.setProperty(startDrawingPoint, endDrawingPoint);
                        rectangle.draw();
                        rectangle.saveCoordinate();
                    }
                    repaint();
                    break;
                }
            }
        }

        @Override
        public void mouseMoved(MouseEvent e) {
        }

    }

}
