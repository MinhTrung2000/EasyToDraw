package model.shape2d;

import java.awt.Color;
import java.awt.Graphics;
import static java.lang.Math.abs;
import java.util.ArrayList;
import main.Settings;
import main.Ultility;

public class Shape {
    
    /**
     * Default initial angle of shape.
     */
    public static final double DEFAULT_ANGLE = 0.0;

    /**
     * Default line style for drawing.
     */
    public static final Settings.LineStyle DEFAULT_LINE_STYLE = Settings.LineStyle.DEFAULT;

    /**
     * This references to the marked coordinates of drawing board.
     */
    protected boolean[][] markedChangeOfBoard;

    /**
     * This references to the color value of points of drawing board.
     */
    protected Color[][] changedColorOfBoard;

    /**
     * This references to the coordinate property of points of drawing board.
     */
    protected String[][] changedCoordOfBoard;

    /**
     * The total angle of this shape after rotation.
     */
    protected double rotatedAngle;

    /**
     * The line style of shape.
     */
    protected Settings.LineStyle lineStyle;

    /**
     * Color filled in shape.
     */
    protected Color filledColor;

    /**
     * Center point of shape.
     */
    protected Point2D centerPoint;

    public Shape(boolean[][] markedChangeOfBoard, Color[][] changedColorOfBoard,
            String[][] changedCoordOfBoard) {
        this.markedChangeOfBoard = markedChangeOfBoard;
        this.changedColorOfBoard = changedColorOfBoard;
        this.changedCoordOfBoard = changedCoordOfBoard;

        this.filledColor = Color.BLACK;

        rotatedAngle = DEFAULT_ANGLE;
        lineStyle = DEFAULT_LINE_STYLE;

        centerPoint = new Point2D(0, 0);
    }

    public void setLineStyle(Settings.LineStyle lineStyle) {
        this.lineStyle = lineStyle;
    }

    public void setFilledColor(Color filledColor) {
        this.filledColor = filledColor;
    }

    /**
     * Draw a segment from startPoint to endPoint by using Bresenham algorithm.
     *
     * @param startPoint
     * @param endPoint
     * @param lineStyle
     */
    public void drawSegment(Point2D startPoint, Point2D endPoint, Settings.LineStyle lineStyle) {
        savePointCoordinate(startPoint.coordX, startPoint.coordY);

        int dx = 0, dy = 0;
        int incx = 0, incy = 0;
        int balance = 0;

        if (endPoint.coordX >= startPoint.coordX) {
            dx = endPoint.coordX - startPoint.coordX;
            incx = 1;
        } else {
            dx = startPoint.coordX - endPoint.coordX;
            incx = -1;
        }

        if (endPoint.coordY >= startPoint.coordY) {
            dy = endPoint.coordY - startPoint.coordY;
            incy = 1;
        } else {
            dy = startPoint.coordY - endPoint.coordY;
            incy = -1;
        }

        int x = startPoint.coordX;
        int y = startPoint.coordY;

        if (dx >= dy) {
            dy <<= 1;
            balance = dy - dx;
            dx <<= 1;

            while (x != endPoint.coordX) {
                savePointWithLineStyleCheck(x, y, lineStyle);
                if (balance >= 0) {
                    y += incy;
                    balance -= dx;
                }
                balance += dy;
                x += incx;
            }
            savePointWithLineStyleCheck(x, y, lineStyle);
        } else {
            dx <<= 1;
            balance = dx - dy;
            dy <<= 1;

            while (y != endPoint.coordY) {
                savePointWithLineStyleCheck(x, y, lineStyle);
                if (balance >= 0) {
                    x += incx;
                    balance -= dy;
                }
                balance += dx;
                y += incy;
            }
            savePointWithLineStyleCheck(x, y, lineStyle);
        }

        if (lineStyle == Settings.LineStyle.ARROW) {
            Vector2D vector = new Vector2D(startPoint, endPoint);
            
            if (vector.getLength() != 0) {
                
                // An extra length is used for drawing the head of arrow.
                int extraLength = 3;
                
                double angleSegmentWithOx = vector.getAngleWithOx();
                
                Point2D upPoint = new Point2D(endPoint.coordX - extraLength, endPoint.coordY - extraLength);
                Point2D downPoint = new Point2D(endPoint.coordX - extraLength, endPoint.coordY + extraLength);

                upPoint.rotate(endPoint, angleSegmentWithOx);
                downPoint.rotate(endPoint, angleSegmentWithOx);
                
                this.drawSegment(endPoint, upPoint, Settings.LineStyle.DEFAULT);
                this.drawSegment(endPoint, downPoint, Settings.LineStyle.DEFAULT);
            }
        }
    }

    public void drawSegment(Point2D startPoint, Point2D endPoint) {
        drawSegment(startPoint, endPoint, this.lineStyle);
    }

    /**
     * Drawing for a complex shape.
     *
     * @param pointList
     */
    public void drawZigZag(ArrayList<Point2D> pointList) {
        int pointNumber = pointList.size();

        for (int i = 0; i < pointNumber - 1; i++) {
            drawSegment(pointList.get(i), pointList.get(i + 1), this.lineStyle);
        }
    }

    public void drawOutline() {

    }

    public void draw() {
        drawOutline();
//        Ultility.paint(changedColorOfBoard, markedChangeOfBoard, this.centerPoint, this.filledColor);
    }

    /**
     * Mark the point coordinates in marked array and color array if they are in
     * bound.
     *
     * @param coordX
     * @param coordY
     */
    protected void savePointCoordinate(int coordX, int coordY) {
        if (Ultility.checkValidPoint(changedColorOfBoard, coordX, coordY)) {
//            System.out.println("main.Shape.savePointCoordinate(" + coordX + ", " + coordY +")");
            markedChangeOfBoard[coordX][coordY] = true;
            changedColorOfBoard[coordX][coordY] = filledColor;
        }
    }

    public void saveCoordinates() {

    }

    /**
     * Mark the point in marked array and color array if it can be put in the
     * board when using a specific line style.
     *
     * @param coordX
     * @param coordY
     * @param lineStyle
     */
    public void savePointWithLineStyleCheck(int coordX, int coordY, Settings.LineStyle lineStyle) {
        if (Ultility.checkValidPoint(changedColorOfBoard, coordX, coordY)
                && Ultility.checkPixelPut(coordX, lineStyle)) {
            markedChangeOfBoard[coordX][coordY] = true;
            changedColorOfBoard[coordX][coordY] = filledColor;
        }
    }

    /**
     * Put eight points symmetrically.
     *
     * @param x
     * @param y
     * @param xd
     * @param yd
     */
    public void putEightSymmetricPoints(int x, int y, int xd, int yd) {
        savePointCoordinate(x + xd, y + yd);
        savePointCoordinate(-x + xd, y + yd);
        savePointCoordinate(x + xd, -y + yd);
        savePointCoordinate(-x + xd, -y + yd);
        savePointCoordinate(y + xd, x + yd);
        savePointCoordinate(-y + xd, x + yd);
        savePointCoordinate(y + xd, -x + yd);
        savePointCoordinate(-y + xd, -x + yd);
    }

    public void drawVirtualRotation(double angle) {

    }

    public void drawVirtualMove(Vector2D vector) {

    }

    public void applyRotation(double angle) {
        this.rotatedAngle += angle;
    }

    public void applyMove(Vector2D vector) {

    }

    public void drawOXSymmetry() {

    }

    public void drawOYSymmetry() {

    }

    public void drawPointSymmetry(Point2D basePoint) {

    }
}
