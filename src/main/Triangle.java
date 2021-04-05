package main;

import java.awt.Color;

public class Triangle extends Shape {
    
    private Point2D pointA;
    private Point2D pointB;
    private Point2D pointC;

    public Triangle(boolean[][] markedPointsOfShape, Color[][] colorPointsOfShape, Color color) {
        super(markedPointsOfShape, colorPointsOfShape, color);
        
        pointA = new Point2D();
        pointB = new Point2D();
        pointC = new Point2D();
    }

    @Override
    public void setProperty(Point2D sourcePoint, Point2D destinationPoint, Settings.LineStyle lineStyle) {
        super.setProperty(sourcePoint, destinationPoint, lineStyle);
        
        pointA.setCoord(sourcePoint);
    }
    
    

}
