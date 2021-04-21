package view;

import model.shape2d.Point2D;
import model.shape2d.Rectangle;
import model.shape2d.Segment2D;
import model.shape2d.Triangle;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Stack;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import model.shape2d.Ellipse;
import model.shape2d.Shape2D;

/**
 * Class used for implementing drawing area.
 */
public class DrawingPanel extends JPanel {

    /**
     * The actual width of board.
     */
    public final int widthBoard = SketchPointConstants.WIDTH_DRAW_AREA;

    /**
     * The actual height of board.
     */
    public final int heightBoard = SketchPointConstants.HEIGHT_DRAW_AREA;

    // Color and coord property of all points when any drawing action is applied
    /**
     * Color of each pixel after applying drawing action.
     */
    private Color[][] colorOfBoard;

    /**
     * Coordination text of each pixel after applying drawing action.
     */
    private String[][] coordOfBoard;

    // The color property of all points when a new drawing action is happened.
    private Color[][] changedColorOfBoard;
    private String[][] changedCoordOfBoard;

    // This array is used to mark the point is changed in an action.
    private boolean[][] markedChangeOfBoard;

    /**
     * Undo stack of coordinate
     */
    private static Stack<String[][]> undoCoordOfBoardStack;
    private static Stack<Color[][]> undoColorOfBoardStack;
    private static Stack<String[][]> redoCoordOfBoardStack;
    private static Stack<Color[][]> redoColorOfBoardStack;

    /**
     * Showing grid lines flag.
     */
    private boolean showGridFlag;

    /**
     * Showing coordinate flag.
     */
    private boolean showCoordinateFlag;

    /**
     * Starting point of drawn object.
     */
    private Point2D startDrawingPoint;

    /**
     * Ending point of drawn object.
     */
    private Point2D endDrawingPoint;

    /**
     * User selected coordinate system.
     */
    private SketchPointConstants.CoordinateMode coordinateMode;

    /**
     * Selected option by user.
     */
    private SketchPointConstants.DrawingToolMode selectedToolMode;

    /**
     * User selected color.
     */
    private Color selectedColor;

    /**
     * User customized line style.
     */
    private SketchPointConstants.LineStyle selectedLineStyle;

    /**
     * User customized line size.
     */
    private Integer selectedLineSize;

    public DrawingPanel() {
        this.colorOfBoard = new Color[heightBoard][widthBoard];
        this.coordOfBoard = new String[heightBoard][widthBoard];

        this.changedColorOfBoard = new Color[heightBoard][widthBoard];
        this.changedCoordOfBoard = new String[heightBoard][widthBoard];

        this.markedChangeOfBoard = new boolean[heightBoard][widthBoard];

        undoCoordOfBoardStack = new Stack<>();
        undoColorOfBoardStack = new Stack<>();
        redoCoordOfBoardStack = new Stack<>();
        redoColorOfBoardStack = new Stack<>();

        showGridFlag = SketchPointConstants.DEFAULT_VISUAL_SHOW_GRID;
        showCoordinateFlag = SketchPointConstants.DEFAULT_VISUAL_SHOW_COORDINATE;

        coordinateMode = SketchPointConstants.CoordinateMode.MODE_2D;

        startDrawingPoint = new Point2D(SketchPointConstants.DEFAULT_UNUSED_POINT);
        endDrawingPoint = new Point2D(SketchPointConstants.DEFAULT_UNUSED_POINT);

        selectedToolMode = SketchPointConstants.DrawingToolMode.DRAWING_LINE_SEGMENT;
        selectedColor = SketchPointConstants.DEFAULT_FILL_COLOR;
        selectedLineStyle = SketchPointConstants.LineStyle.DEFAULT;
        selectedLineSize = SketchPointConstants.DEFAULT_LINE_SIZE;

        resetChangedPropertyArray();
        resetSavedPropertyArray();

        this.addMouseMotionListener(new CustomMouseMotionHandling());
        this.addMouseListener(new CustomMouseClickHandling());
    }

    public Color[][] getColorOfBoard(){
        return this.colorOfBoard;
    }
    public String[][] getCoordOfBoard(){
        return this.coordOfBoard;
    }
    
    
    public void setStartDrawingPoint(int coordX, int coordY) {
        this.startDrawingPoint.setCoord(coordX, coordY);
    }

    public void setEndDrawingPoint(int coordX, int coordY) {
        this.endDrawingPoint.setCoord(coordX, coordY);
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
    public void setSelectedButtonMode(SketchPointConstants.DrawingToolMode selectedToolMode) {
        this.selectedToolMode = selectedToolMode;
    }

    /**
     * Get current drawing tool button.
     *
     * @return
     */
    public SketchPointConstants.DrawingToolMode getSelectedToolMode() {
        return this.selectedToolMode;
    }

    /**
     * Set selected line size.
     *
     * @param lineSize
     */
    public void setSelectedLineSize(int lineSize) {
        selectedLineSize = lineSize;
    }

    /**
     * Set selected line style.
     *
     * @param lineStyle
     */
    public void setSelectedLineStyle(SketchPointConstants.LineStyle lineStyle) {
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
    public void setCoordinateMode(SketchPointConstants.CoordinateMode mode) {
        // Clear old coordinate system before changing coordinate mode flag
        this.coordinateMode = mode;

        resetSavedPropertyArray();
        disposeStack();

        showGridFlag = SketchPointConstants.DEFAULT_VISUAL_SHOW_GRID;
        showCoordinateFlag = SketchPointConstants.DEFAULT_VISUAL_SHOW_COORDINATE;

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
                colorOfBoard[i][j] = SketchPointConstants.DEFAULT_PIXEL_COLOR;
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
                changedColorOfBoard[i][j] = SketchPointConstants.DEFAULT_PIXEL_COLOR;
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
    public void copyColorValue(Color[][] color_board_from, Color[][] color_board_to,boolean fromColorOBToChangedCOB) {
        int height = color_board_from.length;
        int width = color_board_from[0].length;
        if(!fromColorOBToChangedCOB){
            for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                color_board_to[row][col] = new Color(color_board_from[row][col].getRGB());
            }
        }
        }else{
            for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                markedChangeOfBoard [row][col]=true;
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
    public void copyCoordValue(String[][] coord_board_from, String[][] coord_board_to) {
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

    /**
     * Save the current color of drawing board to undo stack.
     */
    private void saveCurrentColorBoardToUndoStack() {
        Color[][] tempBoard = new Color[heightBoard][widthBoard];
        copyColorValue(colorOfBoard, tempBoard,false);
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
        copyColorValue(colorOfBoard, tempBoard,false);
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
            copyColorValue(undoColorOfBoardStack.pop(), colorOfBoard,false);
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
            copyColorValue(redoColorOfBoardStack.pop(), colorOfBoard,false);
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
        copyCoordValue(changedCoordOfBoard, coordOfBoard);

        // Reset marked change array.
       // resetChangedPropertyArray();
    }

    private boolean isNotSelected() {
        return (startDrawingPoint.equal(SketchPointConstants.DEFAULT_UNUSED_POINT)
                && endDrawingPoint.equal(SketchPointConstants.DEFAULT_UNUSED_POINT));
    }

    public void setSelected(Point2D startPoint, Point2D endPoint) {
        startDrawingPoint.setCoord(startPoint);
        endDrawingPoint.setCoord(endPoint);
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
     * @return SketchPointConstants.CoordinateMode
     */
    public SketchPointConstants.CoordinateMode getCoordinateMode() {
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
        graphic.fillRect(0, 0, this.widthBoard, this.heightBoard);
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
        graphic.setColor(SketchPointConstants.DEFAULT_COORDINATE_AXIS_COLOR);

        if (coordinateMode == SketchPointConstants.CoordinateMode.MODE_2D) {
            // Ox axis 
            graphic.drawLine(1, SketchPointConstants.COORD_Y_O, this.widthBoard, SketchPointConstants.COORD_Y_O);
            // Oy axis
            graphic.drawLine(SketchPointConstants.COORD_X_O, 1, SketchPointConstants.COORD_X_O, this.heightBoard);

        } else {
            // Ox
            graphic.drawLine(SketchPointConstants.COORD_X_O, SketchPointConstants.COORD_Y_O, this.widthBoard, SketchPointConstants.COORD_Y_O);
            // Oy
            graphic.drawLine(SketchPointConstants.COORD_X_O, 1, SketchPointConstants.COORD_X_O, SketchPointConstants.COORD_Y_O);
            // Oz
            graphic.drawLine(SketchPointConstants.COORD_X_O, SketchPointConstants.COORD_Y_O, 1, SketchPointConstants.COORD_X_O + SketchPointConstants.COORD_Y_O);
        }

        /*
            Show point coordination.
         */
        graphic.setColor(SketchPointConstants.DEFAULT_COORDINATE_POINT_COLOR);

        for (int i = 0; i < heightBoard; i++) {
            for (int j = 0; j < widthBoard; j++) {
                String coordinateProperty = coordOfBoard[i][j];

                if (coordinateProperty != null) {

                    int posX = (i + 1) * SketchPointConstants.RECT_SIZE;
                    int posY = j * SketchPointConstants.RECT_SIZE - 2;

                    // Normalize
                    if (posX <= 0) {
                        posX = 1;
                    } else if (posX >= widthBoard) {
                        posX = widthBoard - coordinateProperty.length() * SketchPointConstants.SIZE;
                    }

                    if (posY <= 0) {
                        posY = 1;
                    } else if (posY >= heightBoard) {
                        posY = heightBoard - SketchPointConstants.SIZE;
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
            drawBackgroundBoard(graphic, SketchPointConstants.DEFAULT_GRID_BACKGROUND_COLOR);
        } else {
            drawBackgroundBoard(graphic, SketchPointConstants.DEFAULT_EMPTY_BACKGROUND_COLOR);
        }

        for (int i = 0; i < this.heightBoard / SketchPointConstants.RECT_SIZE; i++) {
            for (int j = 0; j < this.widthBoard / SketchPointConstants.RECT_SIZE; j++) {
                if (markedChangeOfBoard[i][j] == true) {
                    graphic.setColor(changedColorOfBoard[i][j]);
                } else {
                    graphic.setColor(colorOfBoard[i][j]);
                }

                graphic.fillRect(j * SketchPointConstants.RECT_SIZE + 1,
                        i * SketchPointConstants.RECT_SIZE + 1,
                        SketchPointConstants.SIZE,
                        SketchPointConstants.SIZE
                );
            }
        }
    }

//    private void paintRect(Graphics graphic, int topLeftX, int topLeftY, int width, int height) {
//        int bottomRightX = topLeftX + width;
//        int bottomRightY = topLeftY + height;
//
//        for (int i = topLeftY; i < bottomRightY; i++) {
//            for (int j = topLeftX; j < bottomRightX; j++) {
//                if (markedChangeOfBoard[i][j] == true) {
//                    graphic.setColor(changedColorOfBoard[i][j]);
//                } else {
//                    graphic.setColor(colorOfBoard[i][j]);
//                }
//
//                graphic.fillRect(
//                        i * SketchPointConstants.RECT_SIZE + 1,
//                        j * SketchPointConstants.RECT_SIZE + 1,
//                        SketchPointConstants.SIZE,
//                        SketchPointConstants.SIZE
//                );
//            }
//        }
//    }

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
        for (int i = 0; i < this.heightBoard; i++) {
            for (int j = 0; j < this.widthBoard; j++) {
                if (markedChangeOfBoard[i][j] == true) {
                    colorOfBoard[i][j] = new Color(changedColorOfBoard[i][j].getRGB());
                }
            }
        }
    }

    public boolean checkStartingPointAvailable() {
        return (startDrawingPoint.getCoordX() != -1 && startDrawingPoint.getCoordY() != -1);
    }

    private class CustomMouseClickHandling implements MouseListener {

        @Override
       public void mouseClicked(MouseEvent event) {

            if (!SwingUtilities.isLeftMouseButton(event)) {
                return;
            }
            
            

            SketchPointConstants.DrawingToolMode selectedTool = getSelectedToolMode();
            
               
            
            switch (selectedTool) {
                case DRAWING_TRANSFORM_ROTATION: {
                    break;
                }
                case DRAWING_TRANSFORM_SYMMETRY: {
                    break;
                }
            }
        }

        @Override
       public void mousePressed(MouseEvent event) {
            
            if (!SwingUtilities.isLeftMouseButton(event)) {
                return;
            }
            setStartDrawingPoint(event.getX() / SketchPointConstants.RECT_SIZE, event.getY() / SketchPointConstants.RECT_SIZE);

            SketchPointConstants.DrawingToolMode selectedTool = getSelectedToolMode();     
            
            resetChangedPropertyArray();
            switch (selectedTool) {
                case DRAWING_LINE_FREE: {
                 //   setEndDrawingPoint(event.getX() / Settings.RECT_SIZE, event.getY() / Settings.RECT_SIZE);
                //    setStartDrawingPoint(endDrawingPoint.getCoordX(), endDrawingPoint.getCoordY());
                    
                    markedChangeOfBoard[startDrawingPoint.getCoordY()][startDrawingPoint.getCoordX()] = true;
                    changedColorOfBoard[startDrawingPoint.getCoordY()][startDrawingPoint.getCoordX()] = selectedColor;
                    startDrawingPoint.saveCoord(coordOfBoard);
                    repaint();
                    break;
                }
                case DRAWING_LINE_SEGMENT: {
                    // Work later
                    break;
                }
                case TOOL_COLOR_FILLER: {
                    copyColorValue(colorOfBoard, changedColorOfBoard,true);
                    Point2D currentMousePos = new Point2D();
                    currentMousePos.setCoord(event.getX()/ SketchPointConstants.RECT_SIZE, event.getY()/SketchPointConstants.RECT_SIZE);
                    
                    Ultility.paint(changedColorOfBoard, markedChangeOfBoard, currentMousePos, selectedColor);
                    repaint();
                    break;
                }
                
            }
        }
       

        @Override
        public void mouseReleased(MouseEvent event) {
            
            if (!SwingUtilities.isLeftMouseButton(event)) {
                return;
            }
            SketchPointConstants.DrawingToolMode selectedTool = getSelectedToolMode();   
            switch(selectedTool){
                case DRAWING_LINE_FREE: {
                    
                }
                case DRAWING_LINE_SEGMENT: {
                    
                }
                case DRAWING_POLYGON_CIRCLE: {
                    
                }
                case DRAWING_POLYGON_TRIANGLE: {
                    
                }
                case DRAWING_POLYGON_RECTANGLE: {
                    apply();
                    repaint();
                   
                   
                    break;
                }
                case TOOL_COLOR_FILLER: {
                    apply();
                    repaint();
                    break;
                }
            }
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }

    }

    private class CustomMouseMotionHandling implements MouseMotionListener {

        @Override
        public void mouseDragged(MouseEvent event) {

            if (!SwingUtilities.isLeftMouseButton(event)) {
                return;
            }

            SketchPointConstants.DrawingToolMode selectedTool = getSelectedToolMode();
            
//            if(selectedTool!=SketchPointConstants.DrawingToolMode.DRAWING_LINE_FREE){
//                resetChangedPropertyArray();
//            }
            //Đặt resetChangedPropertyArray(); trong từng phần để dễ kiểm soát hơn
            //VD: Tô màu bị lỗi do nó vô tình chạy mouse Dragged khi pressed quá nhanh (thành Dragged) => Gọi dragged=> chạy sai

            setEndDrawingPoint(event.getX() / SketchPointConstants.RECT_SIZE, event.getY() / SketchPointConstants.RECT_SIZE);
            
            switch (selectedTool) {
                case DRAWING_LINE_SEGMENT: {
                    resetChangedPropertyArray();
                    if (checkStartingPointAvailable()) {
                        Segment2D segment = new Segment2D(markedChangeOfBoard, changedColorOfBoard, changedCoordOfBoard, selectedColor);
                        if (event.isShiftDown()){
                            segment.setProperty(startDrawingPoint, endDrawingPoint,Segment2D.Modal.LINE_45_90_DEGREE);
                        }else {
                            segment.setProperty(startDrawingPoint, endDrawingPoint,Segment2D.Modal.STRAIGHT_LINE);
                        }
                        segment.setLineStyle(selectedLineStyle);
                        segment.draw();
                        segment.saveCoordinates();
                    }
                    repaint();
                    break;
                }
                case DRAWING_LINE_FREE: {
                    if (checkStartingPointAvailable()){
                        Segment2D pixel = new Segment2D(markedChangeOfBoard, changedColorOfBoard, changedCoordOfBoard, selectedColor);
//                        System.out.println("start X: "+startDrawingPoint.getCoordX()+" start Y: "+startDrawingPoint.getCoordY());
//                        System.out.println("end X: "+endDrawingPoint.getCoordX()+" end Y: "+endDrawingPoint.getCoordY());
                        pixel.setProperty(startDrawingPoint, endDrawingPoint, Segment2D.Modal.STRAIGHT_LINE);
                        //=========NẾU CÙNG ĐỂ STARTDRAWINGPOINT HOẶC ENDDRAWING POINT THÌ PIXEL SẼ BỊ THƯA (DÙ TỌA CỦA START VÀ END LÀ NHƯ NHAU)======
                        pixel.draw();
//                          markedChangeOfBoard[event.getY()/SketchPointConstants.RECT_SIZE][event.getX()/SketchPointConstants.RECT_SIZE] = true;
//                          changedColorOfBoard[event.getY()/SketchPointConstants.RECT_SIZE][event.getX()/SketchPointConstants.RECT_SIZE] = selectedColor;
//                        ========  CÁCH NÀY CŨNG LÀM PIXEL BỊ THƯA!!!===========
                    }
                    setStartDrawingPoint(event.getX()/SketchPointConstants.RECT_SIZE, event.getY()/SketchPointConstants.RECT_SIZE);
                    
                    repaint();
                    break;
                }
                case DRAWING_POLYGON_RECTANGLE: {
                    resetChangedPropertyArray();
                    if (checkStartingPointAvailable()) {
                        Rectangle rectangle = new Rectangle(markedChangeOfBoard, changedColorOfBoard, changedCoordOfBoard, selectedColor);
                        if (event.isShiftDown()){
                            rectangle.setProperty(startDrawingPoint, endDrawingPoint,Rectangle.Modal.SQUARE);
                        }else { 
                            rectangle.setProperty(startDrawingPoint, endDrawingPoint,Rectangle.Modal.RECTANGLE);
                        }
                        rectangle.setLineStyle(selectedLineStyle);
                        rectangle.draw();
                        rectangle.saveCoordinate();
                    }
                    repaint();
                    break;
                }
                case DRAWING_POLYGON_TRIANGLE: {
                    resetChangedPropertyArray();
                    if (checkStartingPointAvailable()) {
                        Triangle triangle = new Triangle(markedChangeOfBoard, changedColorOfBoard, changedCoordOfBoard, selectedColor);
                        if (event.isShiftDown()) {
                            triangle.setProperty(startDrawingPoint, endDrawingPoint,Triangle.Modal.EQUILATERAL_TRIANGLE);
                        }else {
                            triangle.setProperty(startDrawingPoint, endDrawingPoint,Triangle.Modal.COMMON_TRIANGLE);
                        }
                        triangle.setLineStyle(selectedLineStyle);
                        triangle.draw();
                        triangle.saveCoordinate();
                    }
                    repaint();
                    break;
                }
                case DRAWING_POLYGON_CIRCLE: {
                    resetChangedPropertyArray();
                    if (checkStartingPointAvailable()) {
                        Ellipse ellipse = new Ellipse(markedChangeOfBoard, changedColorOfBoard, changedCoordOfBoard, selectedColor);

                        if (event.isShiftDown()) {
                            ellipse.setProperty(startDrawingPoint, endDrawingPoint, Ellipse.Modal.CIRLCE);
                        } else {
                            ellipse.setProperty(startDrawingPoint, endDrawingPoint, Ellipse.Modal.ELLIPSE);
                        }

                        ellipse.setLineStyle(selectedLineStyle);
                        ellipse.draw();
                        ellipse.saveCoordinates();
                    }
                    repaint();
                    break;
                }
            }
        }

        /*
        Use for showing eraser.
         */
        @Override
        public void mouseMoved(MouseEvent e) {
            return;
        }

    }

}
