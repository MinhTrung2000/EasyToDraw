package model.shape2d;

import control.myawt.SKPoint2D;
import java.awt.Color;
import java.util.ArrayList;
import control.SettingConstants;
import control.myawt.SKPoint3D;
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
    protected SKPoint2D centerPoint2D;

    /**
     * The total of pixel number.
     */
    protected int pixelCounter;

    protected SKPoint2D startPoint2D;
    protected SKPoint2D endPoint2D;

    protected ArrayList<SKPoint2D> pointSet = new ArrayList<>();

    public Shape2D(boolean[][] markedChangeOfBoard, Color[][] changedColorOfBoard,
            String[][] changedCoordOfBoard, Color filledColor) {
        this.markedChangeOfBoard = markedChangeOfBoard;
        this.changedColorOfBoard = changedColorOfBoard;
        this.changedCoordOfBoard = changedCoordOfBoard;

        this.filledColor = filledColor;

        rotatedAngle = DEFAULT_ANGLE;
        lineStyle = DEFAULT_LINE_STYLE;

        centerPoint2D = new SKPoint2D(0, 0);

        pixelCounter = 0;

        startPoint2D = new SKPoint2D();
        endPoint2D = new SKPoint2D();
    }

    protected void normalizeStartEndPoint() {
        double minX = Math.min(this.startPoint2D.getCoordX(), this.endPoint2D.getCoordX());
        double maxX = Math.max(this.startPoint2D.getCoordX(), this.endPoint2D.getCoordX());
        double minY = Math.min(this.startPoint2D.getCoordY(), this.endPoint2D.getCoordY());
        double maxY = Math.max(this.startPoint2D.getCoordY(), this.endPoint2D.getCoordY());
        this.startPoint2D.setLocation(minX, maxY);
        this.endPoint2D.setLocation(maxX, minY);
    }

    public SKPoint2D getStartPoint() {
        return this.startPoint2D;
    }

    public SKPoint2D getEndPoint() {
        return this.endPoint2D;
    }

    public ArrayList<SKPoint2D> getPointSet() {
        return pointSet;
    }

    public void setLineStyle(SettingConstants.LineStyle lineStyle) {
        this.lineStyle = lineStyle;
    }

    public void setFilledColor(Color filledColor) {
        this.filledColor = filledColor;
    }

    public abstract void setProperty(SKPoint2D startPoint, SKPoint2D endPoint);

    public int getWidthDirection(int width) {
        if (width < 0) {
            return -1;
        } else {
            return 1;
        }
    }

    public int getHeightDirection(int height) {
        if (height < 0) {
            return -1;
        } else {
            return 1;
        }
    }

    public int getPreferredLength(int width, int height) {
        int widthValue = Math.abs(width);
        int heightValue = Math.abs(height);

        if (widthValue >= heightValue) {
            return heightValue;
        } else {
            return widthValue;
        }
    }

    /**
     * Draw a segment from startPoint2D to endPoint2D by using Bresenham
     * algorithm.
     *
     * @param startPoint
     * @param endPoint
     * @param lineStyle
     */
    public void drawSegment(SKPoint2D startPoint, SKPoint2D endPoint, SettingConstants.LineStyle lineStyle) {
        pixelCounter = 1;

        savePointWithLineStyleCheck(startPoint.getCoordX(), startPoint.getCoordY(), pixelCounter, lineStyle);

        int dx = 0, dy = 0;
        int incx = 0, incy = 0;
        int balance = 0;

        if (endPoint.getCoordX() >= startPoint.getCoordX()) {
            dx = (int) (endPoint.getCoordX() - startPoint.getCoordX());
            incx = 1;
        } else {
            dx = (int) (startPoint.getCoordX() - endPoint.getCoordX());
            incx = -1;
        }

        if (endPoint.getCoordY() >= startPoint.getCoordY()) {
            dy = (int) (endPoint.getCoordY() - startPoint.getCoordY());
            incy = 1;
        } else {
            dy = (int) (startPoint.getCoordY() - endPoint.getCoordY());
            incy = -1;
        }

        int x = (int) startPoint.getCoordX();
        int y = (int) startPoint.getCoordY();

        if (dx >= dy) {
            dy <<= 1;
            balance = dy - dx;
            dx <<= 1;

            while (x != (int) endPoint.getCoordX()) {
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

            while (y != (int) endPoint.getCoordY()) {
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

                SKPoint2D upPoint = new SKPoint2D(endPoint.getCoordX() - extraLength, endPoint.getCoordY() - extraLength);
                SKPoint2D downPoint = new SKPoint2D(endPoint.getCoordX() - extraLength, endPoint.getCoordY() + extraLength);

                upPoint.rotate(endPoint, angleSegmentWithOx);
                downPoint.rotate(endPoint, angleSegmentWithOx);

                this.drawSegment(endPoint, upPoint, SettingConstants.LineStyle.DEFAULT);
                this.drawSegment(endPoint, downPoint, SettingConstants.LineStyle.DEFAULT);
            }
        }
    }

    public void drawSegment(SKPoint3D startPoint, SKPoint3D endPoint, SettingConstants.LineStyle lineStyle) {
        SKPoint2D from = startPoint.get2DRelativePosition().convertViewToMachineCoord();
        SKPoint2D to = endPoint.get2DRelativePosition().convertViewToMachineCoord();
        drawSegment(from, to, lineStyle);
    }

    public void drawZigZagS(ArrayList<SKPoint2D> pointList, int[] roughNumberArray110, int[] roughNumberArray110_2) {
        int pointNumber = pointList.size();

        for (int i = 0; i < pointNumber - 1; i++) {
            drawSegmentS(pointList.get(i), pointList.get(i + 1), roughNumberArray110, roughNumberArray110_2);
        }
    }

    public void drawSegmentS(SKPoint2D startPoint, SKPoint2D endPoint, int[] roughNumberArray110, int[] roughNumberArray110_2) {
        pixelCounter = 1;

        savePointWithLineStyleCheck(startPoint.getCoordX(), startPoint.getCoordY(), pixelCounter, lineStyle);

        int dx = 0, dy = 0;
        int incx = 0, incy = 0;
        int balance = 0;

        if (endPoint.getCoordX() >= startPoint.getCoordX()) {
            dx = (int) (endPoint.getCoordX() - startPoint.getCoordX());
            incx = 1;
        } else {
            dx = (int) (startPoint.getCoordX() - endPoint.getCoordX());
            incx = -1;
        }

        if (endPoint.getCoordY() >= startPoint.getCoordY()) {
            dy = (int) (endPoint.getCoordY() - startPoint.getCoordY());
            incy = 1;
        } else {
            dy = (int) (startPoint.getCoordY() - endPoint.getCoordY());
            incy = -1;
        }

        int x = (int) startPoint.getCoordX();
        int y = (int) startPoint.getCoordY();

        if (dx >= dy) {
            dy <<= 1;
            balance = dy - dx;
            dx <<= 1;

            while (x != (int) endPoint.getCoordX()) {
                pixelCounter += 1;
                savePointWithLineStyleCheck(x, y, pixelCounter, lineStyle);

                for (int i = 1; i <= roughNumberArray110[pixelCounter] - 1; i++) {
                    savePointWithLineStyleCheck(x, y - i, pixelCounter, lineStyle);

                }
                for (int i = 1; i <= roughNumberArray110_2[pixelCounter] - 1; i++) {
                    savePointWithLineStyleCheck(x, y + i, pixelCounter, lineStyle);
                }
                if (balance >= 0) {
                    y += incy;
                    balance -= dx;
                }
                balance += dy;
                x += incx;
            }
            pixelCounter += 1;
            savePointWithLineStyleCheck(x, y, pixelCounter, lineStyle);

            for (int i = 1; i <= roughNumberArray110[pixelCounter] - 1; i++) {
                savePointWithLineStyleCheck(x, y - i, pixelCounter, lineStyle);

            }
            for (int i = 1; i <= roughNumberArray110_2[pixelCounter] - 1; i++) {
                savePointWithLineStyleCheck(x, y + i, pixelCounter, lineStyle);
            }
        } else {
            dx <<= 1;
            balance = dx - dy;
            dy <<= 1;

            while (y != (int) endPoint.getCoordY()) {
                pixelCounter += 1;
                savePointWithLineStyleCheck(x, y, pixelCounter, lineStyle);

                for (int i = 1; i <= roughNumberArray110[pixelCounter] - 1; i++) {
                    savePointWithLineStyleCheck(x, y - i, pixelCounter, lineStyle);

                }
                for (int i = 1; i <= roughNumberArray110_2[pixelCounter] - 1; i++) {
                    savePointWithLineStyleCheck(x, y + i, pixelCounter, lineStyle);
                }
                if (balance >= 0) {
                    x += incx;
                    balance -= dy;
                }
                balance += dx;
                y += incy;
            }
            pixelCounter += 1;
            savePointWithLineStyleCheck(x, y, pixelCounter, lineStyle);

            for (int i = 1; i <= roughNumberArray110[pixelCounter] - 1; i++) {
                savePointWithLineStyleCheck(x, y - i, pixelCounter, lineStyle);

            }
            for (int i = 1; i <= roughNumberArray110_2[pixelCounter] - 1; i++) {
                savePointWithLineStyleCheck(x, y + i, pixelCounter, lineStyle);
            }
        }

    }

    public void drawSegment(SKPoint2D startPoint, SKPoint2D endPoint) {
        drawSegment(startPoint, endPoint, this.lineStyle);
    }

    protected void drawOutlineCircle(int a, SKPoint2D centerPoint, boolean Pos1, boolean Pos2, boolean Pos3,
            boolean Pos4, boolean Pos5, boolean Pos6, boolean Pos7, boolean Pos8) {
        pointSet.clear();

        int x = 0;
        int y = a;

        pixelCounter = 1;
        pointSet.add(new SKPoint2D(x, y));
        putSymmetricPoints_Circle(x, y, centerPoint.getCoordX(), centerPoint.getCoordY(), Pos1, Pos2, Pos3, Pos4, Pos5, Pos6, Pos7, Pos8);

        double p = 5 / 4.0 - a;

        while (x < y) {
            if (p < 0) {
                p += 2 * x + 3;
            } else {
                p += 2 * (x - y) + 5;
                y--;
            }
            x++;
            pixelCounter++;
            putSymmetricPoints_Circle(x, y, centerPoint.getCoordX(), centerPoint.getCoordY(), Pos1, Pos2, Pos3, Pos4, Pos5, Pos6, Pos7, Pos8);
        }
    }

    public void putSymmetricPoints_Circle(double x, double y, double center_x, double center_y, boolean Pos1, boolean Pos2, boolean Pos3, boolean Pos4, boolean Pos5,
            boolean Pos6, boolean Pos7, boolean Pos8) {
        if (Pos4) {
            savePointWithLineStyleCheck(x + center_x, y + center_y, pixelCounter, lineStyle);
        }
        if (Pos3) {
            savePointWithLineStyleCheck(y + center_x, x + center_y, pixelCounter, lineStyle);
        }
        if (Pos2) {
            savePointWithLineStyleCheck(y + center_x, -x + center_y, pixelCounter, lineStyle);
        }
        if (Pos1) {
            savePointWithLineStyleCheck(x + center_x, -y + center_y, pixelCounter, lineStyle);
        }
        if (Pos8) {
            savePointWithLineStyleCheck(-x + center_x, -y + center_y, pixelCounter, lineStyle);
        }
        if (Pos7) {
            savePointWithLineStyleCheck(-y + center_x, -x + center_y, pixelCounter, lineStyle);
        }
        if (Pos6) {
            savePointWithLineStyleCheck(-y + center_x, x + center_y, pixelCounter, lineStyle);
        }
        if (Pos5) {
            savePointWithLineStyleCheck(-x + center_x, y + center_y, pixelCounter, lineStyle);
        }
    }

    public void drawOutlineEllipse(int a, int b, SKPoint2D centerPoint, boolean topLeft, boolean topRight, boolean botLeft, boolean botRight) {
        pointSet.clear();

        // Save center point coordination
        double x = 0.0;
        double y = b;

        double fx = 0;
        double fy = 2 * a * a * y;

        pixelCounter = 1;
        putSymmetricPoints(x, y, centerPoint.getCoordX(), centerPoint.getCoordY(), topLeft, topRight, botLeft, botRight);

        double p = b * b - a * a * b + a * a * 0.25;

        while (fx < fy) {
            x++;
            fx += 2 * b * b;
            if (p < 0) {
                p += b * b * (2 * x + 3);
            } else {
                p += b * b * (2 * x + 3) + a * a * (-2 * y + 2);
                y--;
                fy -= 2 * a * a;
            }
            pixelCounter++;
            putSymmetricPoints((int) x, (int) y, centerPoint.getCoordX(), centerPoint.getCoordY(), topLeft, topRight, botLeft, botRight);
        }

        p = b * b * (x + 0.5) * (x + 0.5) + a * a * (y - 1.0) * (y - 1.0) - a * a * b * b;

        while (y >= 0) {
            y--;
            if (p < 0) {
                p += b * b * (2 * x + 2) + a * a * (-2 * y + 3);
                x++;
            } else {
                p += a * a * (3 - 2 * y);
            }
            pixelCounter++;
            putSymmetricPoints((int) x, (int) y, centerPoint.getCoordX(), centerPoint.getCoordY(), topLeft, topRight, botLeft, botRight);
        }
    }

    public void putSymmetricPoints(double x, double y, double center_x, double center_y, boolean topLeft, boolean topRight, boolean botLeft, boolean botRight) {
        if (topLeft) {
            savePointWithLineStyleCheck(center_x - x, center_y - y, pixelCounter, lineStyle);
        }

        if (topRight) {
            savePointWithLineStyleCheck(center_x + x, center_y - y, pixelCounter, lineStyle);
        }
        if (botRight) {
            savePointWithLineStyleCheck(center_x + x, center_y + y, pixelCounter, lineStyle);
        }

        if (botLeft) {
            savePointWithLineStyleCheck(center_x - x, center_y + y, pixelCounter, lineStyle);
        }

    }

    /**
     * Drawing for a complex shape.
     *
     * @param pointList
     */
    public void drawZigZag(ArrayList<SKPoint2D> pointList) {
        int pointNumber = pointList.size();

        for (int i = 0; i < pointNumber - 1; i++) {
            drawSegment(pointList.get(i), pointList.get(i + 1), this.lineStyle);
        }
    }

    public abstract void drawOutline();

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

    protected boolean savePoint(double coordX, double coordY) {
        return savePoint((int) coordX, (int) coordY);
    }

    public abstract void saveCoordinates();

    /**
     * Mark the point in array and it's color if it can be put at the board with
     * defined line style.
     *
     * @param coordX
     * @param coordY
     * @param pixelCounter
     * @param lineStyle
     * @return
     */
    public boolean savePointWithLineStyleCheck(int coordX, int coordY, int pixelCounter, SettingConstants.LineStyle lineStyle) {
        if (Ultility.checkValidPoint(changedColorOfBoard, coordX, coordY)
                && Ultility.checkPixelPut(pixelCounter, lineStyle)) {
            markedChangeOfBoard[coordY][coordX] = true;
            changedColorOfBoard[coordY][coordX] = filledColor;
            pointSet.add(new SKPoint2D(coordX, coordY));
            return true;
        }
        return false;
    }

    public boolean savePointWithLineStyleCheck(double coordX, double coordY, int pixelCounter, SettingConstants.LineStyle lineStyle) {
        return savePointWithLineStyleCheck((int) coordX, (int) coordY, pixelCounter, lineStyle);
    }

    public void putFourSymmetricPoints(int x, int y, int center_x, int center_y) {
        putFourSymmetricPoints(x, y, center_x, center_y, false);
    }

    public void putFourSymmetricPoints(int x, int y, int center_x, int center_y, boolean mode2) {
        pixelCounter++;
        savePointWithLineStyleCheck(center_x + x, center_y + y, pixelCounter, lineStyle);
        savePointWithLineStyleCheck(center_x - x, center_y + y, pixelCounter, lineStyle);

        if (!mode2 || pixelCounter % 2 == 0) {
            savePointWithLineStyleCheck(center_x + x, center_y - y, pixelCounter, lineStyle);
            savePointWithLineStyleCheck(center_x - x, center_y - y, pixelCounter, lineStyle);
        }
    }

    public void putFourSymmetricPoints(int x, int y, double center_x, double center_y) {
        putFourSymmetricPoints(x, y, (int) center_x, (int) center_y);
    }

    public void addFourSymmetricPoints(ArrayList<SKPoint2D> arr, double x, double y, double center_x, double center_y, boolean mode2) {
        pixelCounter++;
        arr.add(new SKPoint2D(center_x + x, center_y + y));
        arr.add(new SKPoint2D(center_x - x, center_y + y));

        if (mode2 && pixelCounter % 2 == 0) {
            arr.add(new SKPoint2D(center_x + x, center_y - y));
            arr.add(new SKPoint2D(center_x - x, center_y - y));
        }
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
    public void putEightSymmetricPoints(double x, double y, double center_x, double center_y, boolean mode2) {
        pixelCounter++;
        savePointWithLineStyleCheck(y + center_x, -x + center_y, pixelCounter, lineStyle);
        savePointWithLineStyleCheck(x + center_x, -y + center_y, pixelCounter, lineStyle);
        savePointWithLineStyleCheck(-y + center_x, x + center_y, pixelCounter, lineStyle);
        savePointWithLineStyleCheck(-x + center_x, y + center_y, pixelCounter, lineStyle);

        if (!mode2 || pixelCounter % 2 == 0) {
            savePointWithLineStyleCheck(y + center_x, x + center_y, pixelCounter, lineStyle);
            savePointWithLineStyleCheck(x + center_x, y + center_y, pixelCounter, lineStyle);
            savePointWithLineStyleCheck(-x + center_x, -y + center_y, pixelCounter, lineStyle);
            savePointWithLineStyleCheck(-y + center_x, -x + center_y, pixelCounter, lineStyle);
        }
    }

    public void putEightSymmetricPoints(double x, double y, double center_x, double center_y) {
        putEightSymmetricPoints(x, y, center_x, center_y, false);
    }

    public void addEightSymmetricPoints(ArrayList<SKPoint2D> arr, double x, double y, double center_x, double center_y, boolean mode2) {
        pixelCounter++;
        arr.add(new SKPoint2D(y + center_x, -x + center_y));
        arr.add(new SKPoint2D(x + center_x, -y + center_y));
        arr.add(new SKPoint2D(-y + center_x, x + center_y));
        arr.add(new SKPoint2D(-x + center_x, y + center_y));
        
        if (!mode2 || pixelCounter % 2 == 0) {
            arr.add(new SKPoint2D(y + center_x, x + center_y));
            arr.add(new SKPoint2D(x + center_x, y + center_y));
            arr.add(new SKPoint2D(-x + center_x, -y + center_y));
            arr.add(new SKPoint2D(-y + center_x, -x + center_y));
        }
    }

    public void drawVirtualRotation(SKPoint2D centerPoint, double angle) {
        if (pointSet.isEmpty()) {
            return;
        }

        double totalAngle = this.rotatedAngle + angle;

        for (int i = 0; i < pointSet.size(); i++) {
            SKPoint2D pt = pointSet.get(i).createRotationPoint(centerPoint, totalAngle);
            savePoint(pt.getCoordX(), pt.getCoordY());
        }
    }

    public void drawVirtualRotation(double angle) {
        drawVirtualRotation(this.centerPoint2D, angle);
    }

    public void drawVirtualMove(Vector2D vector) {
        if (pointSet.isEmpty()) {
            return;
        }

        for (int i = 0; i < pointSet.size(); i++) {
            SKPoint2D point = pointSet.get(i).createMovingPoint(vector);
            savePoint(point.getCoordX(), point.getCoordY());
        }
    }

    public void applyRotation(double angle) {
        this.rotatedAngle += angle;
    }

    public abstract void applyMove(Vector2D vector);

    public void drawOCenterSymmetry() {
        if (pointSet.isEmpty()) {
            return;
        }

        for (int i = 0; i < pointSet.size(); i++) {
            SKPoint2D point = pointSet.get(i).createCenterOSymmetry();
            savePoint(point.getCoordX(), point.getCoordY());
        }
    }

    public void drawOXSymmetry() {
        if (pointSet.isEmpty()) {
            return;
        }
        for (int i = 0; i < pointSet.size(); i++) {
            SKPoint2D point = pointSet.get(i).createOXSymmetryPoint();
            savePoint(point.getCoordX(), point.getCoordY());
        }
    }

    public void drawOYSymmetry() {
        if (pointSet.isEmpty()) {
            return;
        }

        for (int i = 0; i < pointSet.size(); i++) {
            SKPoint2D point = pointSet.get(i).createOYSymmetryPoint();
            savePoint(point.getCoordX(), point.getCoordY());
        }
    }

    public void drawPointSymmetry(SKPoint2D basePoint) {
        if (pointSet.isEmpty()) {
            return;
        }

        for (int i = 0; i < pointSet.size(); i++) {
            SKPoint2D point = pointSet.get(i).createSymmetryPoint(basePoint);
            savePoint(point.getCoordX(), point.getCoordY());
        }
    }

    public void drawLineSymmetry(double a, double b, double c) {
        if (pointSet.isEmpty()) {
            return;
        }

        for (int i = 0; i < pointSet.size(); i++) {
            SKPoint2D point = pointSet.get(i).createLineSymmetryPoint(a, b, c);
            savePoint(point.getCoordX(), point.getCoordY());
        }
    }

    public void scale(int k) {
        startPoint2D.scale(k);
        endPoint2D.scale(k);
    }
}
