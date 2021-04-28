package model.shape2d;

import java.awt.Color;
import java.util.ArrayList;
import control.SettingConstants;
import control.util.Ultility;

public abstract class Shape2D {

    /**
     * Default initial angle of shape.
     */
    public static final double DEFAULT_ANGLE = 0.0;

    /**
     * Default line style for drawing.
     */
    public static final SettingConstants.LineStyle DEFAULT_LINE_STYLE = SettingConstants.LineStyle.DEFAULT;

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
    protected SettingConstants.LineStyle lineStyle;

    /**
     * Color filled in shape.
     */
    protected Color filledColor;

    /**
     * Center point of shape.
     */
    protected Point2D centerPoint;

    /**
     * The total of pixel number.
     */
    protected int pixelCounter;

    protected Point2D startPoint;
    protected Point2D endPoint;

    protected ArrayList<Point2D> pointSet = new ArrayList<>();

    public Shape2D(boolean[][] markedChangeOfBoard, Color[][] changedColorOfBoard,
            String[][] changedCoordOfBoard, Color filledColor) {
        this.markedChangeOfBoard = markedChangeOfBoard;
        this.changedColorOfBoard = changedColorOfBoard;
        this.changedCoordOfBoard = changedCoordOfBoard;

        this.filledColor = filledColor;

        rotatedAngle = DEFAULT_ANGLE;
        lineStyle = DEFAULT_LINE_STYLE;

        centerPoint = new Point2D(0, 0);

        pixelCounter = 0;

        startPoint = new Point2D();
        endPoint = new Point2D();
    }

    protected void normalizeStartEndPoint() {
        int minX = Math.min(this.startPoint.coordX, this.endPoint.coordX);
        int maxX = Math.max(this.startPoint.coordX, this.endPoint.coordX);
        int minY = Math.min(this.startPoint.coordY, this.endPoint.coordY);
        int maxY = Math.max(this.startPoint.coordY, this.endPoint.coordY);
        this.startPoint.setCoord(minX, maxY);
        this.endPoint.setCoord(maxX, minY);
    }

    public Point2D getStartPoint() {
        return this.startPoint;
    }

    public Point2D getEndPoint() {
        return this.endPoint;
    }

    public void setLineStyle(SettingConstants.LineStyle lineStyle) {
        this.lineStyle = lineStyle;
    }

    public void setFilledColor(Color filledColor) {
        this.filledColor = filledColor;
    }

    public abstract void setProperty(Point2D startPoint, Point2D endPoint);

    /**
     * Draw a segment from startPoint to endPoint by using Bresenham algorithm.
     *
     * @param startPoint
     * @param endPoint
     * @param lineStyle
     */
    public void drawSegment(Point2D startPoint, Point2D endPoint, SettingConstants.LineStyle lineStyle) {
        pixelCounter = 1;

        savePointWithLineStyleCheck(startPoint.getCoordX(), startPoint.getCoordY(), pixelCounter, lineStyle);

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
                pixelCounter += 1;
                savePointWithLineStyleCheck(x, y, pixelCounter, lineStyle);
                if (balance >= 0) {
                    y += incy;
                    balance -= dx;
                }
                balance += dy;
                x += incx;
            }
            pixelCounter += 1;
            savePointWithLineStyleCheck(x, y, pixelCounter, lineStyle);
        } else {
            dx <<= 1;
            balance = dx - dy;
            dy <<= 1;

            while (y != endPoint.coordY) {
                pixelCounter += 1;
                savePointWithLineStyleCheck(x, y, pixelCounter, lineStyle);
                if (balance >= 0) {
                    x += incx;
                    balance -= dy;
                }
                balance += dx;
                y += incy;
            }
            pixelCounter += 1;
            savePointWithLineStyleCheck(x, y, pixelCounter, lineStyle);
        }

        if (lineStyle == SettingConstants.LineStyle.ARROW) {
            Vector2D vector = new Vector2D(startPoint, endPoint);

            if (vector.getLength() != 0) {

                // An extra length is used for drawing the head of arrow.
                int extraLength = 3;

                double angleSegmentWithOx = vector.getAngleWithOx();

                Point2D upPoint = new Point2D(endPoint.coordX - extraLength, endPoint.coordY - extraLength);
                Point2D downPoint = new Point2D(endPoint.coordX - extraLength, endPoint.coordY + extraLength);

                upPoint.rotate(endPoint, angleSegmentWithOx);
                downPoint.rotate(endPoint, angleSegmentWithOx);

                this.drawSegment(endPoint, upPoint, SettingConstants.LineStyle.DEFAULT);
                this.drawSegment(endPoint, downPoint, SettingConstants.LineStyle.DEFAULT);
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

    public abstract void drawOutline();

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
    protected boolean savePoint(int coordX, int coordY) {
        if (Ultility.checkValidPoint(changedColorOfBoard, coordX, coordY)) {
            markedChangeOfBoard[coordY][coordX] = true;
            changedColorOfBoard[coordY][coordX] = filledColor;
            return true;
        }
        return false;
    }

    public abstract void saveCoordinates();

    /**
     * Mark the point in array and it's color if it can be put at the board with
     * defined line style.
     *
     * @param coordX
     * @param coordY
     * @param lineStyle
     */
    public boolean savePointWithLineStyleCheck(int coordX, int coordY, int pixelCounter, SettingConstants.LineStyle lineStyle) {
        if (Ultility.checkValidPoint(changedColorOfBoard, coordX, coordY)
                && Ultility.checkPixelPut(pixelCounter, lineStyle)) {
            markedChangeOfBoard[coordY][coordX] = true;
            changedColorOfBoard[coordY][coordX] = filledColor;
            pointSet.add(new Point2D(coordX, coordY));
            return true;
        }
        return false;
    }

    public void putFourSymmetricPoints(int x, int y, int center_x, int center_y) {
        savePointWithLineStyleCheck(center_x + x, center_y - y, pixelCounter, lineStyle);
        savePointWithLineStyleCheck(center_x + x, center_y + y, pixelCounter, lineStyle);
        savePointWithLineStyleCheck(center_x - x, center_y + y, pixelCounter, lineStyle);
        savePointWithLineStyleCheck(center_x - x, center_y - y, pixelCounter, lineStyle);
    }

    /**
     * Put eight points symmetrically through center point having
     * <code>center_x</code> and <code>center_y</code> coordination.
     *
     * @param x
     * @param y
     * @param center_x
     * @param center_y
     */
    public void putEightSymmetricPoints(int x, int y, int center_x, int center_y) {
        savePointWithLineStyleCheck(x + center_x, y + center_y, pixelCounter, lineStyle);
        savePointWithLineStyleCheck(y + center_x, x + center_y, pixelCounter, lineStyle);
        savePointWithLineStyleCheck(y + center_x, -x + center_y, pixelCounter, lineStyle);
        savePointWithLineStyleCheck(x + center_x, -y + center_y, pixelCounter, lineStyle);
        savePointWithLineStyleCheck(-x + center_x, -y + center_y, pixelCounter, lineStyle);
        savePointWithLineStyleCheck(-y + center_x, -x + center_y, pixelCounter, lineStyle);
        savePointWithLineStyleCheck(-y + center_x, x + center_y, pixelCounter, lineStyle);
        savePointWithLineStyleCheck(-x + center_x, y + center_y, pixelCounter, lineStyle);
    }

    public void drawVirtualRotation(Point2D centerPoint, double angle) {
        if (pointSet.size() == 0) {
            return;
        }

        double totalAngle = this.rotatedAngle + angle;

        for (int i = 0; i < pointSet.size(); i++) {
            Point2D pt = pointSet.get(i).createRotationPoint(centerPoint, totalAngle);
            savePoint(pt.getCoordX(), pt.getCoordY());
        }
    }

    public void drawVirtualRotation(double angle) {
        drawVirtualRotation(this.centerPoint, angle);
    }

    public abstract void drawVirtualMove(Vector2D vector);

    public void applyRotation(double angle) {
        this.rotatedAngle += angle;
    }

    public abstract void applyMove(Vector2D vector);

    public abstract void drawOXSymmetry();

    public abstract void drawOYSymmetry();

    public abstract void drawPointSymmetry(Point2D basePoint);

    public abstract void drawLineSymmetry(double a, double b, double c);
}
