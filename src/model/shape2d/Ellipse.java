package model.shape2d;

import java.awt.Color;

public class Ellipse extends Shape2D {
    
    private Point2D topPoint;
    private Point2D bottomPoint;
    private Point2D leftPoint;
    private Point2D rightPoint;
    
    private double majorRadius;
    private double minorRadius;
    
    private boolean isCircle;
    
    public Ellipse(boolean[][] markedChangeOfBoard, Color[][] changedColorOfBoard, 
            String[][] changedCoordOfBoard, Color filledColor) {
        super(markedChangeOfBoard, changedColorOfBoard, changedCoordOfBoard, filledColor);
        topPoint = new Point2D();
        bottomPoint = new Point2D();
        leftPoint = new Point2D();
        rightPoint = new Point2D();
        
        isCircle = false;
    }

    public void setProperty(Point2D startPoint, Point2D endPoint) {
        
    }
    
    @Override
    public void savePointCoordinate(int coordX, int coordY) {
        centerPoint.saveCoord(changedCoordOfBoard);
        
    }
    
    public void rotate(double angle) {
        int coordX_C = 0;
        
        while (coordX_C < majorRadius) {
            double coordY_C = 0.0;
            
            if (isCircle) {
                coordY_C = Math.sqrt(majorRadius * majorRadius - coordX_C * coordX_C);
            } else {
                coordY_C = Math.sqrt(majorRadius * majorRadius * minorRadius * minorRadius
                        - minorRadius * minorRadius * coordX_C * coordX_C
                );
            }
        }
    }
}
