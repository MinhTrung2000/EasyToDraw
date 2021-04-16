package model.shape2d;

import java.awt.Color;

/*
    Circle equation: (x - a)^2 + (y - b)^2 = R^2
    => y = b + sqrt(R^2 - (x - a)^2)

    Ellipse equation: x^2 / a^2 + y^2 / b^2 = 1
    => y = b * sqrt(1 - (x^2 / a^2))
 */
public class Ellipse extends Shape2D {
    
    public enum Modal {
        ELLIPSE,
        CIRLCE,
    }
    
    private double a;
    private double b;
    private double c;
    
    private Modal modal;
    
    public Ellipse(boolean[][] markedChangeOfBoard, Color[][] changedColorOfBoard, 
            String[][] changedCoordOfBoard, Color filledColor) {
        super(markedChangeOfBoard, changedColorOfBoard, changedCoordOfBoard, filledColor);
        a = b = c = 1.0;
        modal = Modal.ELLIPSE;
    }

    public void setProperty(Point2D startPoint, Point2D endPoint, Modal modal) {
        if (modal == Modal.ELLIPSE) {
            a = Math.abs(startPoint.coordX - endPoint.coordX) / 2.0;
            b = Math.abs(startPoint.coordY - endPoint.coordY) / 2.0;
        
            double max = Math.max(a, b);
            if (a != max) {
                b = a;
                a = max;
    }
//
//            c = Math.sqrt(a * a - b * b);
        } else {
            a = b = Math.abs(startPoint.coordX - endPoint.coordX) / 2.0;
        }
    
        centerPoint.setCoord((int) (startPoint.coordX + endPoint.coordX) / 2, (int) (startPoint.coordY + endPoint.coordY) / 2);

        this.modal = modal;
    }

    @Override
    public void savePointCoordinate(int coordX, int coordY) {
        centerPoint.saveCoord(changedCoordOfBoard);
    }
    
    private void put(double x, double y) {
        pixelCounter++;
        savePointWithLineStyleCheck((int) (centerPoint.coordX + x), (int) (centerPoint.coordY - y), pixelCounter, lineStyle);
        savePointWithLineStyleCheck((int) (centerPoint.coordX + x), (int) (centerPoint.coordY + y), pixelCounter, lineStyle);
        savePointWithLineStyleCheck((int) (centerPoint.coordX - x), (int) (centerPoint.coordY + y), pixelCounter, lineStyle);
        savePointWithLineStyleCheck((int) (centerPoint.coordX - x), (int) (centerPoint.coordY - y), pixelCounter, lineStyle);
    }
    
    public void drawOutlineEllipse() {
        savePointWithLineStyleCheck(centerPoint.coordX, centerPoint.coordY, pixelCounter, lineStyle);
        
//        System.out.println("=================================================");
        double x = 0.0;
        double y = b;
        
        put(x, y);
            
        double p = b * b - a * a * b + a * a * 0.25;

        while (x <= b) {
            put(x, y);
            if (p < 0) {
//                System.out.println("x <= b: p < 0");
                p += b * b * (2 * x + 3);
            } else {
//                System.out.println("x <= b: p >= 0");
                p += b * b * (2 * x + 3) + a * a * (-2 * y + 2);
                y--;
            }
            x++;
        }

        p = b * b * (x + 0.5) * (x + 0.5) + a * a * (y - 1.0) * (y - 1.0) - a * a * b * b;

        while (y >= 0) {
            put(x, y);
            if (p < 0) {
//                System.out.println("y >= 0: p < 0");
                p += b * b * (2 * x + 2) + a * a * (-2 * y + 3);
                x++;
            } else {
//                System.out.println("y >= 0: p >= 0");
                p += a * a * (3 - 2 * y);
    }
            y--;
}
    }

    public void drawOutlineCircle() {
        savePointWithLineStyleCheck(centerPoint.coordX, centerPoint.coordY, pixelCounter, lineStyle);

        pixelCounter++;
//        savePointWithLineStyleCheck((int) (centerPoint.coordX + a), (int) (centerPoint.coordY + a), pixelCounter, lineStyle);
//        savePointWithLineStyleCheck((int) (centerPoint.coordX + a), (int) (centerPoint.coordY - a), pixelCounter, lineStyle);
//        savePointWithLineStyleCheck((int) (centerPoint.coordX - a), (int) (centerPoint.coordY + a), pixelCounter, lineStyle);
//        savePointWithLineStyleCheck((int) (centerPoint.coordX - a), (int) (centerPoint.coordY - a), pixelCounter, lineStyle);

        int x = 0;
        int y = (int) a;

        put(x, y);

        double p = 5.0 / 4 - a;

        while (x <= a) {
            if (p < 0) {
                p += 2 * x + 3;
            } else {
                p += 2 * (x - y) + 5;
                y--;
            }
            x++;
            pixelCounter++;
//            putEightSymmetricPoints(x, y, centerPoint.coordX, centerPoint.coordY);
//            put(x, y);
            savePointWithLineStyleCheck((int) (centerPoint.coordX + x), (int) (centerPoint.coordY - y), pixelCounter, lineStyle);
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
        centerPoint.saveCoord(changedCoordOfBoard);
    }
}
