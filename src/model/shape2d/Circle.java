package model.shape2d;

import java.awt.Color;
import main.Ultility;
import static java.lang.Math.abs;

public class Circle extends Shape2D {
    
    private double radius;
    
    public Circle(boolean[][] markedChangeOfBoard, Color[][] changedColorOfBoard, String[][] changedCoordOfBoard, Color filledColor) {
        super(markedChangeOfBoard, changedColorOfBoard, changedCoordOfBoard, filledColor);
        
        this.radius = 0.0;
    }

    public void setProperty(Point2D centerPoint, double radius) {
        this.centerPoint = centerPoint;
        this.radius = radius;
    }
    
    /**
     * Drawing a circle having a center at sourcePoint and radius equals the
     * distance between sourcePoint and destinationPoint.
     *
     * @param startPoint
     * @param endPoint
     * @param lineStyle
     */
    @Override
    public void drawOutline() {
        if (centerPoint.coordX != -1 && centerPoint.coordY != -1) {
            int radius = abs(centerPoint.coordX - centerPoint.coordY);

            int posX = 0;
            int posY = radius;

            while (posX <= posY) {
                if (Ultility.checkPixelPut(posX, lineStyle)) {
                    putEightSymmetricPoints(posX, posY, centerPoint.coordX, centerPoint.coordY);
                }
                posX++;
                posY = (int) Math.round(Math.sqrt(Math.pow(this.radius, 2) - Math.pow(posX, 2)));
            }
        }
    }
    
}
