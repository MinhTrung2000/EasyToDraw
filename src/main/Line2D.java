package main;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import static main.Settings.*;

public class Line2D extends Shape {

    public Line2D(boolean[][] nextDrawing, Color[][] nextPoint, Color color) {
        super(nextDrawing, nextPoint, color);
    }

    /**
     * The line drawing method implemented by Bresenham algorithm.
     *
     * @param startPoint
     * @param endPoint
     * @param lineStyle
     */
    @Override
    public void draw() {
        drawSegment(sourcePoint, destinationPoint, lineStyle);
    }

    @Override
    public void rotate(Point2D basePoint, double angle) {
        double totalAngle = angle + this.rotatedAngle;

        Point2D rotatedStartPoint = sourcePoint.rotate(basePoint, totalAngle);
        Point2D rotatedEndPoint = destinationPoint.rotate(basePoint, totalAngle);

        drawSegment(rotatedStartPoint, rotatedEndPoint, this.lineStyle);
    }

    /**
     * Line rotation in its center point.
     * @param angle 
     */
    public void rotateInPlace(double angle) {
        double totalAngle = angle + this.rotatedAngle;

        Point2D rotatedStartPoint = sourcePoint.rotate(centerPoint, totalAngle);
        Point2D rotatedEndPoint = destinationPoint.rotate(centerPoint, totalAngle);

        drawSegment(rotatedStartPoint, rotatedEndPoint, this.lineStyle);
    }

    public void setProperty(Point2D sourcePoint, Point2D destinationPoint, LineStyle lineStyle) {
        this.sourcePoint = sourcePoint;
        this.destinationPoint = destinationPoint;
        this.lineStyle = lineStyle;

        this.centerPoint = new Point2D(
                (int) (sourcePoint.coordX + destinationPoint.coordX) / 2,
                (int) (sourcePoint.coordY + destinationPoint.coordY) / 2
        );
    }
    
    /**
     * Update rotation angle after rotating it.
     * @param angle 
     */
    public void applyRotation(double angle) {
        this.rotatedAngle += angle;
    }
    
    /**
     * Move the line and change in place.
     * @param vector 
     */
    @Override
    public void move(Vector2D vector) {
        sourcePoint = sourcePoint.getMovePoint(vector);
        destinationPoint = destinationPoint.getMovePoint(vector);
        draw();
    }
    
    public void drawSymmetricOY() {
        
    }
}
