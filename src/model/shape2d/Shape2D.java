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
    protected static boolean[][] markedChangeOfBoard;

    /**
     * This references to the color value of points of drawing board.
     */
    protected static Color[][] changedColorOfBoard;

    /**
     * This references to the coordinate property of points of drawing board.
     */
    protected static String[][] changedCoordOfBoard;

    /**
     * The total angle of this shape after rotation.
     */
    protected double rotatedAngle = DEFAULT_ANGLE;

    /**
     * The line style of shape.
     */
    protected SettingConstants.LineStyle lineStyle = DEFAULT_LINE_STYLE;

    /**
     * Color filled in shape.
     */
    protected Color filledColor;

    /**
     * Center point of shape.
     */
    protected SKPoint2D centerPoint2D = new SKPoint2D();

    /**
     * The total of pixel number.
     */
//    protected int pixelCounter = 0;
    protected SKPoint2D startPoint2D = new SKPoint2D();
    protected SKPoint2D endPoint2D = new SKPoint2D();

    protected ArrayList<SKPoint2D> pointSet2D = new ArrayList<>();

    public Shape2D(boolean[][] markedChangeOfBoard, Color[][] changedColorOfBoard,
            String[][] changedCoordOfBoard, Color filledColor) {
        this.markedChangeOfBoard = markedChangeOfBoard;
        this.changedColorOfBoard = changedColorOfBoard;
        this.changedCoordOfBoard = changedCoordOfBoard;

        this.filledColor = filledColor;
    }

    public SKPoint2D getStartPoint() {
        return this.startPoint2D;
    }

    public SKPoint2D getEndPoint() {
        return this.endPoint2D;
    }

    public ArrayList<SKPoint2D> getPointSet() {
        return pointSet2D;
    }

    public void setLineStyle(SettingConstants.LineStyle lineStyle) {
        this.lineStyle = lineStyle;
    }

    public void setFilledColor(Color filledColor) {
        this.filledColor = filledColor;
    }

    public void setProperty(SKPoint2D startPoint, SKPoint2D endPoint) {
    }

    public int getDirectionWidth(int width) {
        if (width < 0) {
            return -1;
        } else {
            return 1;
        }
    }

    public int getDirectionHeight(int height) {
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
    public void drawSegment(SKPoint2D startPoint, SKPoint2D endPoint,
            SettingConstants.LineStyle lineStyle) {
        int pixelCounter = 1;

        savePointWithLineStyleCheck(startPoint.getCoordX(),
                startPoint.getCoordY(), pixelCounter, lineStyle);
        pointSet2D.add(startPoint);

        int dx = 0, dy = 0;
        int incx = 0, incy = 0;
        int balance = 0;

        if (endPoint.getCoordX() >= startPoint.getCoordX()) {
            dx = endPoint.getCoordX() - startPoint.getCoordX();
            incx = 1;
        } else {
            dx = startPoint.getCoordX() - endPoint.getCoordX();
            incx = -1;
        }

        if (endPoint.getCoordY() >= startPoint.getCoordY()) {
            dy = endPoint.getCoordY() - startPoint.getCoordY();
            incy = 1;
        } else {
            dy = startPoint.getCoordY() - endPoint.getCoordY();
            incy = -1;
        }

        int x = startPoint.getCoordX();
        int y = startPoint.getCoordY();

        if (dx >= dy) {
            dy <<= 1;
            balance = dy - dx;
            dx <<= 1;

            while (x != endPoint.getCoordX()) {

                pixelCounter += 1;
                savePointWithLineStyleCheck(x, y, pixelCounter, lineStyle);
                pointSet2D.add(new SKPoint2D(x, y));

                if (balance >= 0) {
                    y += incy;
                    balance -= dx;
                }

                balance += dy;
                x += incx;
            }

            pixelCounter += 1;
            savePointWithLineStyleCheck(x, y, pixelCounter, lineStyle);
            pointSet2D.add(new SKPoint2D(x, y));
        } else {
            dx <<= 1;
            balance = dx - dy;
            dy <<= 1;

            while (y != endPoint.getCoordY()) {
                pixelCounter += 1;
                savePointWithLineStyleCheck(x, y, pixelCounter, lineStyle);
                pointSet2D.add(new SKPoint2D(x, y));

                if (balance >= 0) {
                    x += incx;
                    balance -= dy;
                }

                balance += dx;
                y += incy;
            }

            pixelCounter += 1;
            savePointWithLineStyleCheck(x, y, pixelCounter, lineStyle);
            pointSet2D.add(new SKPoint2D(x, y));
        }

        if (lineStyle == SettingConstants.LineStyle.ARROW) {
            Vector2D vector = new Vector2D(startPoint, endPoint);

            if (vector.getLength() != 0) {

                // An extra length is used for drawing the head of arrow.
                int extraLength = 3;

                double angleSegmentWithOx = vector.getAngleWithOx();

                SKPoint2D upPoint = new SKPoint2D(endPoint.getCoordX() - extraLength,
                        endPoint.getCoordY() - extraLength);
                SKPoint2D downPoint = new SKPoint2D(endPoint.getCoordX() - extraLength,
                        endPoint.getCoordY() + extraLength);

                Transform2D.transform(upPoint, Transform2D.getRotateFromPointMat(endPoint, angleSegmentWithOx));
                Transform2D.transform(downPoint, Transform2D.getRotateFromPointMat(endPoint, angleSegmentWithOx));

                drawSegment(endPoint, upPoint, SettingConstants.LineStyle.DEFAULT);
                drawSegment(endPoint, downPoint, SettingConstants.LineStyle.DEFAULT);
            }
        }
    }

    /**
     * Draw segment without saving point to current point set.
     *
     * @param startPoint
     * @param endPoint
     * @param lineStyle
     */
    public void drawSegmentUnSave(SKPoint2D startPoint, SKPoint2D endPoint,
            SettingConstants.LineStyle lineStyle) {
        int pixelCounter = 1;

        savePointWithLineStyleCheck(startPoint.getCoordX(),
                startPoint.getCoordY(), pixelCounter, lineStyle);

        int dx = 0, dy = 0;
        int incx = 0, incy = 0;
        int balance = 0;

        if (endPoint.getCoordX() >= startPoint.getCoordX()) {
            dx = endPoint.getCoordX() - startPoint.getCoordX();
            incx = 1;
        } else {
            dx = startPoint.getCoordX() - endPoint.getCoordX();
            incx = -1;
        }

        if (endPoint.getCoordY() >= startPoint.getCoordY()) {
            dy = endPoint.getCoordY() - startPoint.getCoordY();
            incy = 1;
        } else {
            dy = startPoint.getCoordY() - endPoint.getCoordY();
            incy = -1;
        }

        int x = startPoint.getCoordX();
        int y = startPoint.getCoordY();

        if (dx >= dy) {
            dy <<= 1;
            balance = dy - dx;
            dx <<= 1;

            while (x != endPoint.getCoordX()) {

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

            while (y != endPoint.getCoordY()) {
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

                SKPoint2D upPoint = new SKPoint2D(endPoint.getCoordX() - extraLength,
                        endPoint.getCoordY() - extraLength);
                SKPoint2D downPoint = new SKPoint2D(endPoint.getCoordX() - extraLength,
                        endPoint.getCoordY() + extraLength);

                upPoint.rotate(endPoint, angleSegmentWithOx);
                downPoint.rotate(endPoint, angleSegmentWithOx);

                drawSegmentUnSave(endPoint, upPoint, SettingConstants.LineStyle.DEFAULT);
                drawSegmentUnSave(endPoint, downPoint, SettingConstants.LineStyle.DEFAULT);
            }
        }
    }

    public void drawSegment(SKPoint3D startPoint, SKPoint3D endPoint,
            SettingConstants.LineStyle lineStyle) {
        SKPoint2D from = startPoint.get2DRelativePosition();
        SKPoint2D to = endPoint.get2DRelativePosition();

        drawSegment(from, to, lineStyle);
    }

    public void drawZigZagS(ArrayList<SKPoint2D> pointList,
            int[] roughNumberArray110, int[] roughNumberArray110_2) {
        int pointNumber = pointList.size();

        for (int i = 0; i < pointNumber - 1; i++) {
            drawSegmentS(pointList.get(i), pointList.get(i + 1), roughNumberArray110, roughNumberArray110_2);
        }
    }

    public void drawSegmentS(SKPoint2D startPoint, SKPoint2D endPoint,
            int[] roughNumberArray110, int[] roughNumberArray110_2) {
        int pixelCounter = 1;

        savePointWithLineStyleCheck(startPoint.getCoordX(), startPoint.getCoordY(), pixelCounter, lineStyle);
        pointSet2D.add(startPoint);

        int dx = 0, dy = 0;
        int incx = 0, incy = 0;
        int balance = 0;

        if (endPoint.getCoordX() >= startPoint.getCoordX()) {
            dx = endPoint.getCoordX() - startPoint.getCoordX();
            incx = 1;
        } else {
            dx = startPoint.getCoordX() - endPoint.getCoordX();
            incx = -1;
        }

        if (endPoint.getCoordY() >= startPoint.getCoordY()) {
            dy = endPoint.getCoordY() - startPoint.getCoordY();
            incy = 1;
        } else {
            dy = startPoint.getCoordY() - endPoint.getCoordY();
            incy = -1;
        }

        int x = startPoint.getCoordX();
        int y = startPoint.getCoordY();

        if (dx >= dy) {
            dy <<= 1;
            balance = dy - dx;
            dx <<= 1;

            while (x != endPoint.getCoordX()) {
                pixelCounter += 1;
                savePointWithLineStyleCheck(x, y, pixelCounter, lineStyle);
                pointSet2D.add(new SKPoint2D(x, y));

                for (int i = 1; i <= roughNumberArray110[pixelCounter] - 1; i++) {
                    savePointWithLineStyleCheck(x, y - i, pixelCounter, lineStyle);
                    pointSet2D.add(new SKPoint2D(x, y - i));
                }

                for (int i = 1; i <= roughNumberArray110_2[pixelCounter] - 1; i++) {
                    savePointWithLineStyleCheck(x, y + i, pixelCounter, lineStyle);
                    pointSet2D.add(new SKPoint2D(x, y + i));
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
            pointSet2D.add(new SKPoint2D(x, y));

            for (int i = 1; i <= roughNumberArray110[pixelCounter] - 1; i++) {
                savePointWithLineStyleCheck(x, y - i, pixelCounter, lineStyle);
                pointSet2D.add(new SKPoint2D(x, y - i));
            }

            for (int i = 1; i <= roughNumberArray110_2[pixelCounter] - 1; i++) {
                savePointWithLineStyleCheck(x, y + i, pixelCounter, lineStyle);
                pointSet2D.add(new SKPoint2D(x, y + i));
            }
        } else {
            dx <<= 1;
            balance = dx - dy;
            dy <<= 1;

            while (y != endPoint.getCoordY()) {
                pixelCounter += 1;
                savePointWithLineStyleCheck(x, y, pixelCounter, lineStyle);

                for (int i = 1; i <= roughNumberArray110[pixelCounter] - 1; i++) {
                    savePointWithLineStyleCheck(x, y - i, pixelCounter, lineStyle);
                    pointSet2D.add(new SKPoint2D(x, y - i));
                }

                for (int i = 1; i <= roughNumberArray110_2[pixelCounter] - 1; i++) {
                    savePointWithLineStyleCheck(x, y + i, pixelCounter, lineStyle);
                    pointSet2D.add(new SKPoint2D(x, y + i));
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
                pointSet2D.add(new SKPoint2D(x, y - i));
            }

            for (int i = 1; i <= roughNumberArray110_2[pixelCounter] - 1; i++) {
                savePointWithLineStyleCheck(x, y + i, pixelCounter, lineStyle);
                pointSet2D.add(new SKPoint2D(x, y + i));
            }
        }
    }

    public void drawSegment(SKPoint2D startPoint, SKPoint2D endPoint) {
        drawSegment(startPoint, endPoint, this.lineStyle);
    }

    public void drawSegmentUnSave(SKPoint2D startPoint, SKPoint2D endPoint) {
        drawSegmentUnSave(startPoint, endPoint, this.lineStyle);
    }

    public void drawOutlineCircle(SKPoint2D centerPoint, double radius, boolean octant1,
            boolean octant2, boolean octant3, boolean octant4, boolean octant5,
            boolean octant6, boolean octant7, boolean octant8) {
        int x = 0;
        int y = (int) radius;

        int pixelCounter = 0;

        pixelCounter++;
        putEightSymPoints(pixelCounter, x, y, centerPoint.getCoordX(),
                centerPoint.getCoordY(), octant1, octant2, octant3, octant4,
                octant5, octant6, octant7, octant8);

        double p = 5 / 4.0 - radius;

        while (x < y) {
            if (p < 0) {
                p += 2 * x + 3;
            } else {
                p += 2 * (x - y) + 5;
                y--;
            }
            x++;

            pixelCounter++;
            putEightSymPoints(pixelCounter, x, y, centerPoint.getCoordX(),
                    centerPoint.getCoordY(), octant1, octant2, octant3, octant4,
                    octant5, octant6, octant7, octant8);
        }
    }
    
    public void drawOutlineCircleUnSave(SKPoint2D centerPoint, double radius, boolean octant1,
            boolean octant2, boolean octant3, boolean octant4, boolean octant5,
            boolean octant6, boolean octant7, boolean octant8) {
        int x = 0;
        int y = (int) radius;

        int pixelCounter = 0;

        pixelCounter++;
        putEightSymPointsUnSave(pixelCounter, x, y, centerPoint.getCoordX(),
                centerPoint.getCoordY(), octant1, octant2, octant3, octant4,
                octant5, octant6, octant7, octant8);

        double p = 5 / 4.0 - radius;

        while (x < y) {
            if (p < 0) {
                p += 2 * x + 3;
            } else {
                p += 2 * (x - y) + 5;
                y--;
            }
            x++;

            pixelCounter++;
            putEightSymPointsUnSave(pixelCounter, x, y, centerPoint.getCoordX(),
                    centerPoint.getCoordY(), octant1, octant2, octant3, octant4,
                    octant5, octant6, octant7, octant8);
        }
    }

    /**
     * Merge point set of circle without drawing.
     *
     * @param array
     * @param centerPoint
     * @param radius
     * @param octant1
     * @param octant2
     * @param octant3
     * @param octant4
     * @param octant5
     * @param octant6
     * @param octant7
     * @param octant8
     */
    public static void mergePointSetCircle(ArrayList<SKPoint2D> array,
            SKPoint2D centerPoint, double radius, boolean octant1, boolean octant2,
            boolean octant3, boolean octant4, boolean octant5, boolean octant6,
            boolean octant7, boolean octant8) {
        int x = 0;
        int y = (int) radius;

        addEightSymPoints(array, x, y, centerPoint.getCoordX(), centerPoint.getCoordY(), octant1, octant2, octant3, octant4, octant5, octant6, octant7, octant8);

        double p = 5 / 4.0 - radius;

        while (x < y) {
            if (p < 0) {
                p += 2 * x + 3;
            } else {
                p += 2 * (x - y) + 5;
                y--;
            }
            x++;

            addEightSymPoints(array, x, y, centerPoint.getCoordX(), centerPoint.getCoordY(), octant1, octant2, octant3, octant4, octant5, octant6, octant7, octant8);
        }
    }

    /**
     * Merge point set of ellipse without drawing.
     *
     * @param array
     * @param centerPoint
     * @param radius
     * @param octant1
     * @param octant2
     * @param octant3
     * @param octant4
     */
    public static void mergePointSetEllipse(ArrayList<SKPoint2D> array,
            SKPoint2D centerPoint, double majorRadius, double minorRadius,
            boolean quadrant1, boolean quadrant2, boolean quadrant3,
            boolean quadrant4) {
        double x = 0.0;
        double y = minorRadius;

        double fx = 0;
        double fy = 2 * majorRadius * majorRadius * y;

        addFourSymPoints(array, x, y, centerPoint.getCoordX(), centerPoint.getCoordY(),
                quadrant1, quadrant2, quadrant3, quadrant4);

        double p = minorRadius * minorRadius - majorRadius * majorRadius * minorRadius
                + majorRadius * majorRadius * 0.25;

        while (fx < fy) {
            x++;
            fx += 2 * minorRadius * minorRadius;

            if (p < 0) {
                p += minorRadius * minorRadius * (2 * x + 3);
            } else {
                p += minorRadius * minorRadius * (2 * x + 3)
                        + majorRadius * majorRadius * (-2 * y + 2);
                y--;
                fy -= 2 * majorRadius * majorRadius;
            }

            addFourSymPoints(array, x, y, centerPoint.getCoordX(),
                    centerPoint.getCoordY(), quadrant1, quadrant2, quadrant3,
                    quadrant4);
        }

        p = minorRadius * minorRadius * (x + 0.5) * (x + 0.5)
                + majorRadius * majorRadius * (y - 1.0) * (y - 1.0)
                - majorRadius * majorRadius * minorRadius * minorRadius;

        while (y >= 0) {
            y--;

            if (p < 0) {
                p += minorRadius * minorRadius * (2 * x + 2)
                        + majorRadius * majorRadius * (-2 * y + 3);
                x++;
            } else {
                p += majorRadius * majorRadius * (3 - 2 * y);
            }

            addFourSymPoints(array, x, y, centerPoint.getCoordX(),
                    centerPoint.getCoordY(), quadrant1, quadrant2, quadrant3,
                    quadrant4);
        }
    }

    /**
     * Put eight symmetric point from a center point. Octant 1..8 are clockwise,
     * starting at the top right corner.
     *
     * @param pixelCounter
     * @param x
     * @param y
     * @param center_x
     * @param center_y
     * @param octant1
     * @param octant2
     * @param octant3
     * @param octant4
     * @param octant5
     * @param octant6
     * @param octant7
     * @param octant8
     */
    public void putEightSymPoints(int pixelCounter, double x, double y,
            double center_x, double center_y, boolean octant1, boolean octant2,
            boolean octant3, boolean octant4, boolean octant5, boolean octant6,
            boolean octant7, boolean octant8) {
        if (octant4) {
            savePointWithLineStyleCheck(x + center_x, y + center_y, pixelCounter,
                    lineStyle);
            pointSet2D.add(new SKPoint2D(x + center_x, y + center_y));
        }
        if (octant3) {
            savePointWithLineStyleCheck(y + center_x, x + center_y, pixelCounter,
                    lineStyle);
            pointSet2D.add(new SKPoint2D(y + center_x, x + center_y));
        }
        if (octant2) {
            savePointWithLineStyleCheck(y + center_x, -x + center_y, pixelCounter,
                    lineStyle);
            pointSet2D.add(new SKPoint2D(y + center_x, -x + center_y));
        }
        if (octant1) {
            savePointWithLineStyleCheck(x + center_x, -y + center_y, pixelCounter,
                    lineStyle);
            pointSet2D.add(new SKPoint2D(x + center_x, -y + center_y));
        }
        if (octant8) {
            savePointWithLineStyleCheck(-x + center_x, -y + center_y, pixelCounter,
                    lineStyle);
            pointSet2D.add(new SKPoint2D(-x + center_x, -y + center_y));
        }
        if (octant7) {
            savePointWithLineStyleCheck(-y + center_x, -x + center_y, pixelCounter,
                    lineStyle);
            pointSet2D.add(new SKPoint2D(-y + center_x, -x + center_y));
        }
        if (octant6) {
            savePointWithLineStyleCheck(-y + center_x, x + center_y, pixelCounter,
                    lineStyle);
            pointSet2D.add(new SKPoint2D(-y + center_x, x + center_y));
        }
        if (octant5) {
            savePointWithLineStyleCheck(-x + center_x, y + center_y, pixelCounter,
                    lineStyle);
            pointSet2D.add(new SKPoint2D(-x + center_x, y + center_y));
        }
    }
    public void putEightSymPointsUnSave(int pixelCounter, double x, double y,
            double center_x, double center_y, boolean octant1, boolean octant2,
            boolean octant3, boolean octant4, boolean octant5, boolean octant6,
            boolean octant7, boolean octant8) {
        if (octant4) {
            savePointWithLineStyleCheck(x + center_x, y + center_y, pixelCounter,
                    lineStyle);
        }
        if (octant3) {
            savePointWithLineStyleCheck(y + center_x, x + center_y, pixelCounter,
                    lineStyle);
        }
        if (octant2) {
            savePointWithLineStyleCheck(y + center_x, -x + center_y, pixelCounter,
                    lineStyle);
        }
        if (octant1) {
            savePointWithLineStyleCheck(x + center_x, -y + center_y, pixelCounter,
                    lineStyle);
        }
        if (octant8) {
            savePointWithLineStyleCheck(-x + center_x, -y + center_y, pixelCounter,
                    lineStyle);
        }
        if (octant7) {
            savePointWithLineStyleCheck(-y + center_x, -x + center_y, pixelCounter,
                    lineStyle);
        }
        if (octant6) {
            savePointWithLineStyleCheck(-y + center_x, x + center_y, pixelCounter,
                    lineStyle);
        }
        if (octant5) {
            savePointWithLineStyleCheck(-x + center_x, y + center_y, pixelCounter,
                    lineStyle);
        }
    }

    public void drawOutlineEllipse(SKPoint2D centerPoint, double majorRadius,
            double minorRadius, boolean quadrant1, boolean quadrant2, boolean quadrant3, boolean quadrant4) {
        double x = 0.0;
        double y = minorRadius;

        double fx = 0;
        double fy = 2 * majorRadius * majorRadius * y;

        int pixelCounter = 0;

        pixelCounter++;
        putFourSymPoints(pixelCounter, x, y, centerPoint.getCoordX(),
                centerPoint.getCoordY(), quadrant1, quadrant2, quadrant3, quadrant4);

        double p = minorRadius * minorRadius - majorRadius * majorRadius
                * minorRadius + majorRadius * majorRadius * 0.25;

        while (fx < fy) {
            x++;
            fx += 2 * minorRadius * minorRadius;

            if (p < 0) {
                p += minorRadius * minorRadius * (2 * x + 3);
            } else {
                p += minorRadius * minorRadius * (2 * x + 3) + majorRadius
                        * majorRadius * (-2 * y + 2);
                y--;
                fy -= 2 * majorRadius * majorRadius;
            }

            pixelCounter++;
            putFourSymPoints(pixelCounter, x, y, centerPoint.getCoordX(),
                    centerPoint.getCoordY(), quadrant1, quadrant2, quadrant3, quadrant4);
        }

        p = minorRadius * minorRadius * (x + 0.5) * (x + 0.5) + majorRadius
                * majorRadius * (y - 1.0) * (y - 1.0) - majorRadius * majorRadius
                * minorRadius * minorRadius;

        while (y >= 0) {
            y--;
            if (p < 0) {
                p += minorRadius * minorRadius * (2 * x + 2) + majorRadius
                        * majorRadius * (-2 * y + 3);
                x++;
            } else {
                p += majorRadius * majorRadius * (3 - 2 * y);
            }

            pixelCounter++;
            putFourSymPoints(pixelCounter, x, y, centerPoint.getCoordX(),
                    centerPoint.getCoordY(), quadrant1, quadrant2, quadrant3, quadrant4);
        }
    }
    
    public void drawOutlineEllipseUnSave(SKPoint2D centerPoint, double majorRadius,
            double minorRadius, boolean quadrant1, boolean quadrant2, boolean quadrant3, boolean quadrant4) {
        double x = 0.0;
        double y = minorRadius;

        double fx = 0;
        double fy = 2 * majorRadius * majorRadius * y;

        int pixelCounter = 0;

        pixelCounter++;
        putFourSymPointsUnSave(pixelCounter, x, y, centerPoint.getCoordX(),
                centerPoint.getCoordY(), quadrant1, quadrant2, quadrant3, quadrant4);

        double p = minorRadius * minorRadius - majorRadius * majorRadius
                * minorRadius + majorRadius * majorRadius * 0.25;

        while (fx < fy) {
            x++;
            fx += 2 * minorRadius * minorRadius;

            if (p < 0) {
                p += minorRadius * minorRadius * (2 * x + 3);
            } else {
                p += minorRadius * minorRadius * (2 * x + 3) + majorRadius
                        * majorRadius * (-2 * y + 2);
                y--;
                fy -= 2 * majorRadius * majorRadius;
            }

            pixelCounter++;
            putFourSymPointsUnSave(pixelCounter, x, y, centerPoint.getCoordX(),
                    centerPoint.getCoordY(), quadrant1, quadrant2, quadrant3, quadrant4);
        }

        p = minorRadius * minorRadius * (x + 0.5) * (x + 0.5) + majorRadius
                * majorRadius * (y - 1.0) * (y - 1.0) - majorRadius * majorRadius
                * minorRadius * minorRadius;

        while (y >= 0) {
            y--;
            if (p < 0) {
                p += minorRadius * minorRadius * (2 * x + 2) + majorRadius
                        * majorRadius * (-2 * y + 3);
                x++;
            } else {
                p += majorRadius * majorRadius * (3 - 2 * y);
            }

            pixelCounter++;
            putFourSymPointsUnSave(pixelCounter, x, y, centerPoint.getCoordX(),
                    centerPoint.getCoordY(), quadrant1, quadrant2, quadrant3, quadrant4);
        }
    }

    public void putFourSymPoints(int pixelCounter, double x, double y,
            double center_x, double center_y, boolean quadrant1,
            boolean quadrant2, boolean quadrant3, boolean quadrant4) {
        if (quadrant2) {
            savePointWithLineStyleCheck(center_x + x, center_y + y, pixelCounter,
                    lineStyle);
            pointSet2D.add(new SKPoint2D(center_x + x, center_y + y));
        }
        if (quadrant1) {
            savePointWithLineStyleCheck(center_x + x, center_y - y, pixelCounter,
                    lineStyle);
            pointSet2D.add(new SKPoint2D(center_x + x, center_y - y));
        }
        if (quadrant4) {
            savePointWithLineStyleCheck(center_x - x, center_y - y, pixelCounter,
                    lineStyle);
            pointSet2D.add(new SKPoint2D(center_x - x, center_y - y));
        }
        if (quadrant3) {
            savePointWithLineStyleCheck(center_x - x, center_y + y, pixelCounter,
                    lineStyle);
            pointSet2D.add(new SKPoint2D(center_x - x, center_y + y));
        }
    }
    
    public void putFourSymPointsUnSave(int pixelCounter, double x, double y,
            double center_x, double center_y, boolean quadrant1,
            boolean quadrant2, boolean quadrant3, boolean quadrant4) {
        if (quadrant2) {
            savePointWithLineStyleCheck(center_x + x, center_y + y, pixelCounter,
                    lineStyle);
        }
        if (quadrant1) {
            savePointWithLineStyleCheck(center_x + x, center_y - y, pixelCounter,
                    lineStyle);
        }
        if (quadrant4) {
            savePointWithLineStyleCheck(center_x - x, center_y - y, pixelCounter,
                    lineStyle);
        }
        if (quadrant3) {
            savePointWithLineStyleCheck(center_x - x, center_y + y, pixelCounter,
                    lineStyle);
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

    public void drawOutline() {
        int pixelCounter = 0;

        for (int i = 0; i < pointSet2D.size(); i++) {
            SKPoint2D p = pointSet2D.get(i);
            pixelCounter++;
            savePointWithLineStyleCheck(p.getCoordX(), p.getCoordY(), pixelCounter, lineStyle);
        }

        saveCoordinates();
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

    protected boolean savePoint(double coordX, double coordY) {
        return savePoint((int) coordX, (int) coordY);
    }

    protected boolean savePoint(SKPoint2D point) {
        return savePoint(point.getCoordX(), point.getCoordY());
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
    public boolean savePointWithLineStyleCheck(int coordX, int coordY,
            int pixelCounter, SettingConstants.LineStyle lineStyle) {
        if (Ultility.checkValidPoint(changedColorOfBoard, coordX, coordY)
                && Ultility.checkPixelPut(pixelCounter, lineStyle)) {
            markedChangeOfBoard[coordY][coordX] = true;
            changedColorOfBoard[coordY][coordX] = this.filledColor;
            return true;
        }
        return false;
    }

    public boolean savePointWithLineStyleCheck(double coordX, double coordY,
            int pixelCounter, SettingConstants.LineStyle lineStyle) {
        return savePointWithLineStyleCheck((int) coordX, (int) coordY, pixelCounter, lineStyle);
    }

    /**
     * Add four symmetric point from a center point. Quadrant1 is top right and
     * following clock wise. Coordinate is in system mode.
     *
     * @param arr
     * @param x
     * @param y
     * @param center_x
     * @param center_y
     * @param quadrant1
     * @param quadrant2
     * @param quadrant3
     * @param quadrant4
     */
    public static void addFourSymPoints(ArrayList<SKPoint2D> arr, double x,
            double y, double center_x, double center_y, boolean quadrant1,
            boolean quadrant2, boolean quadrant3, boolean quadrant4) {
        if (quadrant2) {
            arr.add(new SKPoint2D(center_x + x, center_y + y));
        }
        if (quadrant1) {
            arr.add(new SKPoint2D(center_x + x, center_y - y));
        }
        if (quadrant4) {
            arr.add(new SKPoint2D(center_x - x, center_y - y));
        }
        if (quadrant3) {
            arr.add(new SKPoint2D(center_x - x, center_y + y));
        }
    }

    /**
     * octant 1..8 follows counter-clockwise (in system coordinate)
     *
     * @param arr
     * @param x
     * @param y
     * @param center_x
     * @param center_y
     * @param octant1
     * @param octant2
     * @param octant3
     * @param octant4
     * @param octant5
     * @param octant6
     * @param octant7
     * @param octant8
     */
    public static void addEightSymPoints(ArrayList<SKPoint2D> arr, double x,
            double y, double center_x, double center_y, boolean octant1,
            boolean octant2, boolean octant3, boolean octant4, boolean octant5,
            boolean octant6, boolean octant7, boolean octant8) {
        if (octant4) {
            arr.add(new SKPoint2D(x + center_x, y + center_y));
        }
        if (octant3) {
            arr.add(new SKPoint2D(y + center_x, x + center_y));
        }
        if (octant2) {
            arr.add(new SKPoint2D(y + center_x, -x + center_y));
        }
        if (octant1) {
            arr.add(new SKPoint2D(x + center_x, -y + center_y));
        }
        if (octant8) {
            arr.add(new SKPoint2D(-x + center_x, -y + center_y));
        }
        if (octant7) {
            arr.add(new SKPoint2D(-y + center_x, -x + center_y));
        }
        if (octant6) {
            arr.add(new SKPoint2D(-y + center_x, x + center_y));
        }
        if (octant5) {
            arr.add(new SKPoint2D(-x + center_x, y + center_y));
        }
    }

    public void createRotate(SKPoint2D centerPoint, double angle) {
        if (pointSet2D.isEmpty()) {
            return;
        }

        double totalAngle = this.rotatedAngle + angle;

        for (int i = 0; i < pointSet2D.size(); i++) {
            SKPoint2D pt = pointSet2D.get(i).createRotate(centerPoint, totalAngle);
            savePoint(pt);
        }
    }
    
    public void rotate(SKPoint2D centerPoint, double angle) {
        if (pointSet2D.isEmpty()) {
            return;
        }

        double totalAngle = this.rotatedAngle + angle;

        for (int i = 0; i < pointSet2D.size(); i++) {
            pointSet2D.get(i).rotate(centerPoint, totalAngle);
        }
    }

    public void createRotate(double angle) {
        createRotate(this.centerPoint2D, angle);
    }

    public void rotate(double angle) {
        rotate(this.centerPoint2D, angle);
    }

    public void createScale(double sx, double sy) {
        if (pointSet2D.isEmpty()) {
            return;
        }

        for (int i = 0; i < pointSet2D.size(); i++) {
            SKPoint2D pt = pointSet2D.get(i).createScale(sx, sy);
            savePoint(pt);
        }
    }
    
    public void scale(double sx, double sy) {
        if (pointSet2D.isEmpty()) {
            return;
        }

        for (int i = 0; i < pointSet2D.size(); i++) {
            pointSet2D.get(i).scale(sx, sy);
        }
    }

    public void createMove(Vector2D vector) {
        if (pointSet2D.isEmpty()) {
            return;
        }

        for (int i = 0; i < pointSet2D.size(); i++) {
            SKPoint2D point = pointSet2D.get(i).createMove(vector);
            savePoint(point.getCoordX(), point.getCoordY());
        }
    }

    public void move(Vector2D vector) {
        if (pointSet2D.isEmpty()) {
            return;
        }

        for (int i = 0; i < pointSet2D.size(); i++) {
            pointSet2D.get(i).move(vector);
        }
    }

    public void applyRotation(double angle) {
        this.rotatedAngle += angle;
    }

    public abstract void applyMove(Vector2D vector);

    public void createSymOCenter() {
        if (pointSet2D.isEmpty()) {
            return;
        }

        for (int i = 0; i < pointSet2D.size(); i++) {
            SKPoint2D point = pointSet2D.get(i).createOCenterSym();
            savePoint(point);
        }
    }

    public void createSymOX() {
        if (pointSet2D.isEmpty()) {
            return;
        }
        for (int i = 0; i < pointSet2D.size(); i++) {
            SKPoint2D point = pointSet2D.get(i).createOXSym();
            savePoint(point);
        }
    }

    public void createSymOY() {
        if (pointSet2D.isEmpty()) {
            return;
        }

        for (int i = 0; i < pointSet2D.size(); i++) {
            SKPoint2D point = pointSet2D.get(i).createOYSym();
            savePoint(point);
        }
    }

    public void createSymVertical(int x) {
        if (pointSet2D.isEmpty()) {
            return;
        }

        for (int i = 0; i < pointSet2D.size(); i++) {
            SKPoint2D point = pointSet2D.get(i).createVerticalSym(x);
            savePoint(point);
        }
    }
    
    public void symVertical(int x) {
        if (pointSet2D.isEmpty()) {
            return;
        }

        for (int i = 0; i < pointSet2D.size(); i++) {
            pointSet2D.get(i).symVertical(x);
        }
    }

    public void createSymHorizontal(int y) {
        if (pointSet2D.isEmpty()) {
            return;
        }

        for (int i = 0; i < pointSet2D.size(); i++) {
            SKPoint2D point = pointSet2D.get(i).createHorizontalSym(y);
            savePoint(point);
        }
    }
    
    public void symHorizontal(int y) {
        if (pointSet2D.isEmpty()) {
            return;
        }

        for (int i = 0; i < pointSet2D.size(); i++) {
            pointSet2D.get(i).symHorizontal(y);
        }
    }

    public void createSymPoint(SKPoint2D basePoint) {
        if (pointSet2D.isEmpty()) {
            return;
        }

        for (int i = 0; i < pointSet2D.size(); i++) {
            SKPoint2D point = pointSet2D.get(i).createPointSym(basePoint);
            savePoint(point.getCoordX(), point.getCoordY());
        }
    }

    public void createSymLine(double a, double b, double c) {
        if (pointSet2D.isEmpty()) {
            return;
        }

        for (int i = 0; i < pointSet2D.size(); i++) {
            SKPoint2D point = pointSet2D.get(i).createLineSym(a, b, c);
            savePoint(point.getCoordX(), point.getCoordY());
        }
    }

    public void paint() {
        Ultility.paint(changedColorOfBoard, markedChangeOfBoard, this.centerPoint2D, this.filledColor, false);
    }

    public void drawArc(ArrayList<SKPoint2D> pointSet, SettingConstants.LineStyle lineStyle) {
        int pixelCounter = 0;

        for (int i = 0; i < pointSet.size(); i++) {
            SKPoint2D p = pointSet.get(i);
            pixelCounter++;
            savePointWithLineStyleCheck(p.getCoordX(), p.getCoordY(), pixelCounter, lineStyle);
        }
    }

    /**
     * Get left top point follows system coordinate.
     *
     * @return
     */
    public SKPoint2D getLeftTopPoint() {
        if (pointSet2D.isEmpty()) {
            return null;
        }

        SKPoint2D ret = pointSet2D.get(0);

        for (int i = 1; i < pointSet2D.size(); i++) {
            SKPoint2D p = pointSet2D.get(i);

            if ((p.getCoordX() < ret.getCoordX())
                    || (p.getCoordX() == ret.getCoordX() && p.getCoordY() < ret.getCoordY())) {
                ret = p;
            }
        }

        return ret;
    }

    /**
     * Get right top point follows system coordinate.
     *
     * @return
     */
    public SKPoint2D getRightTopPoint() {
        if (pointSet2D.isEmpty()) {
            return null;
        }

        SKPoint2D ret = pointSet2D.get(0);

        for (int i = 1; i < pointSet2D.size(); i++) {
            SKPoint2D p = pointSet2D.get(i);

            if ((p.getCoordX() > ret.getCoordX())
                    || (p.getCoordX() == ret.getCoordX() && p.getCoordY() < ret.getCoordY())) {
                ret = p;
            }
        }

        return ret;
    }

    /**
     * Get left bottom point follows system coordinate.
     *
     * @return
     */
    public SKPoint2D getLeftBottomPoint() {
        if (pointSet2D.isEmpty()) {
            return null;
        }

        SKPoint2D ret = pointSet2D.get(0);

        for (int i = 1; i < pointSet2D.size(); i++) {
            SKPoint2D p = pointSet2D.get(i);

            if ((p.getCoordX() < ret.getCoordX())
                    || (p.getCoordX() == ret.getCoordX() && p.getCoordY() > ret.getCoordY())) {
                ret = p;
            }
        }

        return ret;
    }

    /**
     * Get right bottom point follows system coordinate.
     *
     * @return
     */
    public SKPoint2D getRightBottomPoint() {
        if (pointSet2D.isEmpty()) {
            return null;
        }

        SKPoint2D ret = pointSet2D.get(0);

        for (int i = 1; i < pointSet2D.size(); i++) {
            SKPoint2D p = pointSet2D.get(i);

            if ((p.getCoordX() > ret.getCoordX())
                    || (p.getCoordX() == ret.getCoordX() && p.getCoordY() > ret.getCoordY())) {
                ret = p;
            }
        }

        return ret;
    }
}
