package model.shape2d;

import java.awt.Color;

public class Triangle extends Shape {

    private Point2D pointA;
    private Point2D pointB;
    private Point2D pointC;

    public Triangle(boolean[][] markedChangeOfBoard, Color[][] changedColorOfBoard, String[][] changedCoordOfBoard) {
        super(markedChangeOfBoard, changedColorOfBoard, changedCoordOfBoard);
        pointA = new Point2D();
        pointB = new Point2D();
        pointC = new Point2D();
    }

    /**
     * Set 3 points of triangle.
     *
     * @param pointA
     * @param pointB
     * @param pointC
     */
    public void setProperty(Point2D pointA, Point2D pointB, Point2D pointC) {
        this.pointA = pointA;
        this.pointC = pointC;

        pointB.setCoord(pointA.coordX - (pointC.coordX - pointA.coordX), pointC.coordY);

        centerPoint.setCoord(
                pointA.coordX,
                pointC.coordY + (int) ((2.0 / 3.0) * Math.abs(pointC.coordY - pointA.coordY))
        );
    }

    public void saveCoordinate() {
        pointA.saveCoord(this.changedCoordOfBoard);
        pointB.saveCoord(this.changedCoordOfBoard);
        pointC.saveCoord(this.changedCoordOfBoard);
    }

    /**
     * Rotate a copy of this shape by an angle.
     * @param angle 
     */
    @Override
    public void drawVirtualRotation(double angle) {
        double totalAngle = this.rotatedAngle + angle;

        Point2D tempPointA = pointA.createRotationPoint(centerPoint, totalAngle);
        Point2D tempPointB = pointB.createRotationPoint(centerPoint, totalAngle);
        Point2D tempPointC = pointC.createRotationPoint(centerPoint, totalAngle);

        drawSegment(tempPointA, tempPointB);
        drawSegment(tempPointB, tempPointC);
        drawSegment(tempPointC, tempPointA);
    }

    /**
     * Drawing in board.
     */
    @Override
    public void drawOutline() {
        Point2D tempPointA = pointA.createRotationPoint(centerPoint, this.rotatedAngle);
        Point2D tempPointB = pointB.createRotationPoint(centerPoint, this.rotatedAngle);
        Point2D tempPointC = pointC.createRotationPoint(centerPoint, this.rotatedAngle);

        drawSegment(tempPointA, tempPointB);
        drawSegment(tempPointB, tempPointC);
        drawSegment(tempPointC, tempPointA);
    }

    /**
     * Drawing
     * @param vector 
     */
    @Override
    public void drawVirtualMove(Vector2D vector) {
        Point2D tempPointA = pointA.createRotationPoint(centerPoint, this.rotatedAngle).move(vector);
        Point2D tempPointB = pointB.createRotationPoint(centerPoint, this.rotatedAngle).move(vector);
        Point2D tempPointC = pointC.createRotationPoint(centerPoint, this.rotatedAngle).move(vector);

        drawSegment(tempPointA, tempPointB);
        drawSegment(tempPointB, tempPointC);
        drawSegment(tempPointC, tempPointA);
    }

    @Override
    public void applyMove(Vector2D vector) {
        pointA.rotate(centerPoint, this.rotatedAngle).move(vector);
        pointB = pointB.rotate(centerPoint, this.rotatedAngle).move(vector);
        pointC = pointC.rotate(centerPoint, this.rotatedAngle).move(vector);

        centerPoint.move(vector);
    }

    @Override
    public void drawOYSymmetry() {
        Point2D pointASymmetry = pointA.createRotationPoint(centerPoint, rotatedAngle).createOYSymmetryPoint();
        Point2D pointBSymmetry = pointB.createRotationPoint(centerPoint, rotatedAngle).createOYSymmetryPoint();
        Point2D pointCSymmetry = pointC.createRotationPoint(centerPoint, rotatedAngle).createOYSymmetryPoint();

        drawSegment(pointASymmetry, pointBSymmetry);
        drawSegment(pointBSymmetry, pointCSymmetry);
        drawSegment(pointCSymmetry, pointASymmetry);
    }

    @Override
    public void drawOXSymmetry() {
        Point2D pointASymmetry = pointA.createRotationPoint(centerPoint, rotatedAngle).createOXSymmetryPoint();
        Point2D pointBSymmetry = pointB.createRotationPoint(centerPoint, rotatedAngle).createOXSymmetryPoint();
        Point2D pointCSymmetry = pointC.createRotationPoint(centerPoint, rotatedAngle).createOXSymmetryPoint();

        drawSegment(pointASymmetry, pointBSymmetry);
        drawSegment(pointBSymmetry, pointCSymmetry);
        drawSegment(pointCSymmetry, pointASymmetry);
    }

    @Override
    public void drawPointSymmetry(Point2D basePoint) {
        Point2D pointASymmetry = pointA.createSymmetryPoint(basePoint);
        Point2D pointBSymmetry = pointB.createSymmetryPoint(basePoint);
        Point2D pointCSymmetry = pointC.createSymmetryPoint(basePoint);

        drawSegment(pointASymmetry, pointBSymmetry);
        drawSegment(pointBSymmetry, pointCSymmetry);
        drawSegment(pointCSymmetry, pointASymmetry);
    }

}
