package model.shape2d;

import java.awt.Color;

/*
    Circle equation: (x - a)^2 + (y - b)^2 = R^2
    => y = b + sqrt(R^2 - (x - a)^2)

    Ellipse equation: x^2 / a^2 + y^2 / b^2 = 1
    => y = b * sqrt(1 - (x^2 / a^2))

    Note that:
    + Center point is a start point.
 */
public class Ellipse extends Shape2D {

    public enum Modal {
        ELLIPSE,
        CIRLCE,
    }
    
    
    Point2D startPoint;
    Point2D endPoint;
    private double a;
    private double b;

    private Modal modal;

    public Ellipse(boolean[][] markedChangeOfBoard, Color[][] changedColorOfBoard,
            String[][] changedCoordOfBoard, Color filledColor) {
        super(markedChangeOfBoard, changedColorOfBoard, changedCoordOfBoard, filledColor);
        a = b = 1.0;
        modal = Modal.ELLIPSE;
        startPoint = new Point2D();
        endPoint = new Point2D();
    }

    public void setProperty(Point2D startPoint, Point2D endPoint, Modal modal) {
        int width = endPoint.coordX - startPoint.coordX;
        int height = endPoint.coordY - startPoint.coordY;
        double half_x = Math.abs(width) / 2.0;
        double half_y = Math.abs(height) / 2.0;
        
        
        if (modal == Modal.ELLIPSE) {
            this.startPoint=startPoint;
            this.endPoint=endPoint;
            a = half_x;
            b = half_y;

        } else {
            int heightValue = Math.abs(height);
            int widthValue = Math.abs(width);
            int preferedLength;
            if(heightValue >= widthValue){
                preferedLength= widthValue;
            }else{
                preferedLength=heightValue;
            }
            int widthDirection;
            if(width<0){
                widthDirection=-1;
            }else{
                widthDirection=1;
            }
            
            int heightDirection;
            if(height<0){
                heightDirection=-1;
            }else{
                heightDirection=1;
            }
            this.startPoint = startPoint;
            this.endPoint.setCoord(startPoint.getCoordX()+widthDirection*preferedLength,startPoint.getCoordY()+heightDirection*preferedLength);
            a=preferedLength/2;
            b=a;
//            System.out.println("start X: "+ this.startPoint.getCoordX()+ " start Y: "+ startPoint.getCoordY());
//            System.out.println("end X: "+ this.endPoint.getCoordY()+" end Y: "+endPoint.getCoordY());
        }

        centerPoint.setCoord(
                (int) Math.round(this.startPoint.coordX + this.endPoint.coordX) / 2, 
                (int) Math.round(this.startPoint.coordY + this.endPoint.coordY) / 2
        );
//        System.out.println("center X: "+ centerPoint.getCoordY()+" center Y: "+centerPoint.getCoordY());

        this.modal = modal;
    }

    @Override
    public void savePointCoordinate(int coordX, int coordY) {
        centerPoint.saveCoord(changedCoordOfBoard);
    }

    public void drawOutlineEllipse() {
        // Save center point coordination
        savePointWithLineStyleCheck(centerPoint.coordX, centerPoint.coordY, 1, lineStyle);
        
        double x = 0.0;
        double y = b;

        double fx = 0;
        double fy = 2 * a * a * y;

        pixelCounter = 1;
        putFourSymmetricPoints((int) x, (int) y, centerPoint.coordX, centerPoint.coordY);

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
            putFourSymmetricPoints((int) x, (int) y, centerPoint.coordX, centerPoint.coordY);
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
            putFourSymmetricPoints((int) x, (int) y, centerPoint.coordX, centerPoint.coordY);
        }
    }

    public void drawOutlineCircle() {
        // Save center point coordination
        savePointWithLineStyleCheck(centerPoint.coordX, centerPoint.coordY, 1, lineStyle);
        
        double x = 0;
        double y = a;

        pixelCounter = 1;
        putEightSymmetricPoints((int) x, (int) y, centerPoint.coordX, centerPoint.coordY);
        
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
            putEightSymmetricPoints((int) x, (int) y, centerPoint.coordX, centerPoint.coordY);
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
