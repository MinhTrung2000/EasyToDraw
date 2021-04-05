package main;

import java.awt.Color;
import static java.lang.Math.abs;

public class Circle extends Shape {
    
    public Circle(boolean[][] markedPointsOfShape, Color[][] colorPointsOfShape, Color color)  {
        super(markedPointsOfShape, colorPointsOfShape, color);
    }
    
    /**
     * Drawing a circle having a center at sourcePoint and radius equals the
 distance between sourcePoint and destinationPoint.
     *
     * @param startPoint
     * @param endPoint
     * @param lineStyle
     */
    @Override
    public void draw() {
        if (sourcePoint.coordX != -1 && sourcePoint.coordY != -1) {
            int radius = abs(sourcePoint.coordX - sourcePoint.coordY);

            int coordX = 0;
            int coordY = radius;

            while (coordX <= coordY) {
                if (Ultility.checkPixelPut(coordX, lineStyle)) {
                    putEightSymmetricPoints(coordX, coordY, sourcePoint.coordX, sourcePoint.coordY);
                }
                coordX++;
                coordY = (int) Math.round(Math.sqrt(Math.pow(radius, 2) - Math.pow(coordX, 2)));
            }
        }
    }
}
