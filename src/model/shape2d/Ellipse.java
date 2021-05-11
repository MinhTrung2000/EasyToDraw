package model.shape2d;

import control.myawt.SKPoint2D;
import java.awt.Color;
import java.util.ArrayList;

public class Ellipse extends Shape2D {

    public enum Modal {
        ELLIPSE,
        CIRLCE,
    }

    private double a = 1.0;
    private double b = 1.0;

    private Modal modal = Modal.ELLIPSE;

    public Ellipse(boolean[][] markedChangeOfBoard, Color[][] changedColorOfBoard,
            String[][] changedCoordOfBoard, Color filledColor) {
        super(markedChangeOfBoard, changedColorOfBoard, changedCoordOfBoard, filledColor);
    }

    public void setModal(Modal modal) {
        this.modal = modal;
    }

    public void setProperty(SKPoint2D startPoint, SKPoint2D endPoint, Modal modal) {
        
        int width = (int) (endPoint.getCoordX() - startPoint.getCoordX());
        int height = (int) (endPoint.getCoordY() - startPoint.getCoordY());

        double half_x = Math.abs(width) / 2.0;
        double half_y = Math.abs(height) / 2.0;

        if (modal == Modal.ELLIPSE) {
            this.startPoint2D = startPoint;
            this.endPoint2D = endPoint;
            a = half_x;
            b = half_y;

        } else {

            int preferedLength = this.getPreferredLength(width, height);
            int widthDirection = this.getWidthDirection(width);
            int heightDirection = this.getHeightDirection(height);

            this.startPoint2D = startPoint;
            this.endPoint2D.setLocation(startPoint.getCoordX() + widthDirection * preferedLength, startPoint.getCoordY() + heightDirection * preferedLength);
            a = preferedLength / 2;
            b = a;
        }

        centerPoint2D.setMidLocation(startPoint2D, endPoint2D);

        this.modal = modal;
    }

    @Override
    public void setProperty(SKPoint2D startPoint, SKPoint2D endPoint) {
        setProperty(startPoint, endPoint, Modal.ELLIPSE);
    }

    /**
     * If mode2 flag is true, we have an ellipse with top half arc is dot style.
     *
     * @param mode2
     */
    public void drawOutlineEllipse() {
        pointSet.clear();

        // Save center point coordination
        centerPoint2D.saveCoord(changedCoordOfBoard);
        markedChangeOfBoard[centerPoint2D.getCoordY()][centerPoint2D.getCoordX()] = true;
        changedColorOfBoard[centerPoint2D.getCoordY()][centerPoint2D.getCoordX()] = filledColor;

        double x = 0.0;
        double y = b;

        double fx = 0;
        double fy = 2 * a * a * y;

        pixelCounter = 1;
        putFourSymmetricPoints((int) x, (int) y, centerPoint2D.getCoordX(), centerPoint2D.getCoordY());

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
            putFourSymmetricPoints((int) x, (int) y, centerPoint2D.getCoordX(), centerPoint2D.getCoordY());
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
            putFourSymmetricPoints((int) x, (int) y, centerPoint2D.getCoordX(), centerPoint2D.getCoordY());
        }
    }

    public void drawOutlineCircle() {
        pointSet.clear();

        // Save center point coordination
        centerPoint2D.saveCoord(changedCoordOfBoard);
        markedChangeOfBoard[(int) centerPoint2D.getCoordY()][(int) centerPoint2D.getCoordX()] = true;
        changedColorOfBoard[(int) centerPoint2D.getCoordY()][(int) centerPoint2D.getCoordX()] = filledColor;

        double x = 0;
        double y = a;

        pixelCounter = 1;
//        pointSet.add(new SKPoint2D((int) x, (int) y));
        putEightSymmetricPoints(x, y, centerPoint2D.getCoordX(), centerPoint2D.getCoordY());

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
            putEightSymmetricPoints((int) x, (int) y, centerPoint2D.getCoordX(), centerPoint2D.getCoordY());
        }
    }

    @Override
    public void drawOutline() {
        if (modal == Modal.ELLIPSE) {
            drawOutlineEllipse();
        } else {
            drawOutlineCircle();
        }
    }

    @Override
    public void saveCoordinates() {
        centerPoint2D.saveCoord(changedCoordOfBoard);
    }

    @Override
    public void applyMove(Vector2D vector) {
        centerPoint2D.move(vector);
    }

    public ArrayList<SKPoint2D> getSetOfAllPoints(boolean topPart, boolean bottomPart) {
        ArrayList<SKPoint2D> ret = new ArrayList<>();

        if (modal == Modal.ELLIPSE) {
            double x = 0.0;
            double y = b;

            double fx = 0;
            double fy = 2 * a * a * y;

            pixelCounter = 0;

            if (topPart) {
                addFourSymmetricPoints(ret, x, y, centerPoint2D.getCoordX(), centerPoint2D.getCoordY(), true, false, false, true);
            }
            if (bottomPart) {
                addFourSymmetricPoints(ret, x, y, centerPoint2D.getCoordX(), centerPoint2D.getCoordY(), false, true, true, false);
            }

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
                if (topPart) {
                    addFourSymmetricPoints(ret, x, y, centerPoint2D.getCoordX(), centerPoint2D.getCoordY(), true, false, false, true);
                }
                if (bottomPart) {
                    addFourSymmetricPoints(ret, x, y, centerPoint2D.getCoordX(), centerPoint2D.getCoordY(), false, true, true, false);
                }
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

                if (topPart) {
                    addFourSymmetricPoints(ret, x, y, centerPoint2D.getCoordX(), centerPoint2D.getCoordY(), true, false, false, true);
                }
                if (bottomPart) {
                    addFourSymmetricPoints(ret, x, y, centerPoint2D.getCoordX(), centerPoint2D.getCoordY(), false, true, true, false);
                }
            }
        } else {
            double x = 0;
            double y = a;

            pixelCounter = 0;
//            addEightSymmetricPoints(ret, x, y, centerPoint2D.getCoordX(), centerPoint2D.getCoordY(), mode2);
            double p = 5 / 4.0 - a;

            while (x < y) {
                if (p < 0) {
                    p += 2 * x + 3;
                } else {
                    p += 2 * (x - y) + 5;
                    y--;
                }
                x++;
                if (topPart) {
                    addEightSymmetricPoints(ret, x, y, centerPoint2D.getCoordX(), centerPoint2D.getCoordY(), false, false, true, true, true, true, false, false);
                }
                if (bottomPart) {
                    addEightSymmetricPoints(ret, x, y, centerPoint2D.getCoordX(), centerPoint2D.getCoordY(), true, true, false, false, false, false, true, true);
                }
            }
        }

        return ret;
    }
}
