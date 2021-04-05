package main;

import java.awt.Color;
import java.awt.Graphics;
import static java.lang.Math.abs;
import java.util.ArrayList;

public class Shape {

    public static final double DEFAULT_ANGLE = 0.0;
    public static final Settings.LineStyle DEFAULT_LINE_STYLE = Settings.LineStyle.DEFAULT;

    /**
     * This array is used to mark the points are in this shape.
     */
    protected boolean[][] markedPointsOfShape;

    /**
     * This array is used to save color of points in this shape.
     */
    protected Color[][] colorPointsOfShape;

    /**
     * The list of coordinate of points in this shape.
     */
    protected ArrayList<Point2D> listOfPoints;

    /**
     * The list of color of points in this shape.
     */
    protected ArrayList<Color> listOfCoordinates;

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

    /**
     * The starting point of shape.
     */
    protected Point2D sourcePoint;

    /**
     * The ending point of shape.
     */
    protected Point2D destinationPoint;

    public Shape(boolean[][] markedPointsOfShape, Color[][] colorPointsOfShape, Color color) {
        this.markedPointsOfShape = markedPointsOfShape;
        this.colorPointsOfShape = colorPointsOfShape;
        this.filledColor = color;

        listOfPoints = new ArrayList<>();
        listOfCoordinates = new ArrayList<>();

        rotatedAngle = DEFAULT_ANGLE;
        lineStyle = DEFAULT_LINE_STYLE;

        sourcePoint = new Point2D();
        destinationPoint = new Point2D();
        centerPoint = new Point2D();
    }

    public void setProperty(Point2D sourcePoint, Point2D destinationPoint, Settings.LineStyle lineStyle) {
        this.sourcePoint = sourcePoint;
        this.destinationPoint = destinationPoint;
        this.lineStyle = lineStyle;
    }
    
    /**
     * Draw a segment from startPoint to endPoint by using Bresenham algorithm.
     * 
     * @param startPoint
     * @param endPoint
     * @param lineStyle 
     */
    public void drawSegment(Point2D startPoint, Point2D endPoint, Settings.LineStyle lineStyle) {
        savePoint(startPoint.coordX, startPoint.coordY);
        
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
                int extraLength = 5;
                Point2D headArrowPoint = vector.getKTimesUnitPoint(endPoint, extraLength);
                double angleSegmentWithOx = vector.getAngleWithOx();
                
                Point2D upPoint = new Point2D(headArrowPoint.coordX, headArrowPoint.coordY - extraLength);
                Point2D downPoint = new Point2D(headArrowPoint.coordX, headArrowPoint.coordY + extraLength);
                
                this.drawSegment(endPoint, upPoint.rotate(headArrowPoint, angleSegmentWithOx), lineStyle);
                this.drawSegment(endPoint, downPoint.rotate(headArrowPoint, angleSegmentWithOx), lineStyle);
            }
        }
    }

    public void draw() {
    }

    public void drawZigZag(ArrayList<Point2D> pointList) {
        int pointNumber = pointList.size();
        
        for (int i = 0; i < pointNumber - 1; i++) {
            drawSegment(pointList.get(i), pointList.get(i + 1), this.lineStyle);
        }
    }
    
    /**
     * Mark the point in marked array and color array.
     *
     * @param coordX
     * @param coordY
     */
    public void savePoint(int coordX, int coordY) {
        if (Ultility.checkValidPoint(colorPointsOfShape, coordX, coordY)) {
            markedPointsOfShape[coordX][coordY] = true;
            colorPointsOfShape[coordX][coordY] = filledColor;
        }
    }

    /**
     * Mark the point in marked array and color array if it can put in the board
     * when using a specific line style.
     *
     * @param coordX
     * @param coordY
     */
    public void savePointWithLineStyleCheck(int coordX, int coordY, Settings.LineStyle lineStyle) {
        if (Ultility.checkValidPoint(colorPointsOfShape, coordX, coordY)
                && Ultility.checkPixelPut(coordX, lineStyle)) {
            markedPointsOfShape[coordX][coordY] = true;
            colorPointsOfShape[coordX][coordY] = filledColor;
        }
    }
    
    /**
     * Save the coordinate information of point.
     * @param coordOfBoard 
     */
    public void saveCoordinateOfPoint(String[][] coordOfBoard) {
        
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
        savePoint(x + xd, y + yd);
        savePoint(-x + xd, y + yd);
        savePoint(x + xd, -y + yd);
        savePoint(-x + xd, -y + yd);
        savePoint(y + xd, x + yd);
        savePoint(-y + xd, x + yd);
        savePoint(y + xd, -x + yd);
        savePoint(-y + xd, -x + yd);
    }

    /**
     * Rotate this shape by angle.
     *
     * @param basePoint
     * @param angle
     */
    public void rotate(Point2D basePoint, double angle) {

    }
    
    /**
     * Rotation in place of its center point.
     * @param angle 
     */
    public void rotate(double angle) {
        
    }
    
    public void applyRotation(double angle) {
        this.rotatedAngle += angle;
    }
    
    /**
     * Move this shape follow the vector.
     * @param vector 
     */
    public void move(Vector2D vector) {
        
    }
    
}
