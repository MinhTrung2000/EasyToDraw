package main;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import static main.Settings.*;

public class Segment2D extends Shape {

    private Point2D startPoint;
    private Point2D endPoint;

    public Segment2D(boolean[][] markedChangeOfBoard, Color[][] changedColorOfBoard, String[][] changedCoordOfBoard) {
        super(markedChangeOfBoard, changedColorOfBoard, changedCoordOfBoard);

        startPoint = new Point2D(0, 0);
        endPoint = new Point2D(0, 0);
    }

    public void setProperty(Point2D startPoint, Point2D endPoint) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.centerPoint.setCoord(
                (startPoint.coordX + endPoint.coordX) / 2,
                (startPoint.coordY + endPoint.coordY) / 2
        );
    }

    /**
     * The line drawing method implemented by Bresenham algorithm.
     *
     * @param startPoint
     * @param endPoint
     * @param lineStyle
     */
    @Override
    public void drawOutline() {
        drawSegment(startPoint, endPoint, lineStyle);
    }

    @Override
    public void drawOXSymmetry() {
        Point2D tempStartPoint = startPoint.createOXSymmetryPoint();
        Point2D tempEndPoint = endPoint.createOXSymmetryPoint();

        drawSegment(tempStartPoint, tempEndPoint);
    }

    @Override
    public void drawOYSymmetry() {
        Point2D tempStartPoint = startPoint.createOYSymmetryPoint();
        Point2D tempEndPoint = endPoint.createOYSymmetryPoint();

        drawSegment(tempStartPoint, tempEndPoint);
    }

    @Override
    public void drawPointSymmetry(Point2D basePoint) {
        Point2D tempStartPoint = startPoint.createSymmetryPoint(basePoint);
        Point2D tempEndPoint = endPoint.createSymmetryPoint(basePoint);

        drawSegment(tempStartPoint, tempEndPoint);
    }

    @Override
    public void drawVirtualMove(Vector2D vector) {
        Point2D tempStartPoint = startPoint.createMovingPoint(vector);
        Point2D tempEndPoint = endPoint.createMovingPoint(vector);
        
        drawSegment(tempStartPoint, tempEndPoint);
    }

    @Override
    public void drawVirtualRotation(double angle) {
        double totalAngle = angle + this.rotatedAngle;

        Point2D rotatedStartPoint = startPoint.createRotationPoint(centerPoint, totalAngle);
        Point2D rotatedEndPoint = endPoint.createRotationPoint(centerPoint, totalAngle);

        drawSegment(rotatedStartPoint, rotatedEndPoint, this.lineStyle);
    }

    /**
     * Move the line and change in place.
     *
     * @param vector
     */
    @Override
    public void applyMove(Vector2D vector) {
        startPoint = startPoint.createMovingPoint(vector);
        endPoint = endPoint.createMovingPoint(vector);
        centerPoint = centerPoint.createMovingPoint(vector);
    }
    
    @Override
    public void saveCoordinates() {
        this.startPoint.saveCoord(changedCoordOfBoard);
        this.endPoint.saveCoord(changedCoordOfBoard);
    }
}
