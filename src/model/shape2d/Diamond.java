package model.shape2d;

import java.awt.Color;

public class Diamond extends Shape2D {

    private Point2D startPoint;
    private Point2D endPoint;

    private Point2D leftPoint;
    private Point2D rightPoint;
    private Point2D topPoint;
    private Point2D bottomPoint;

    public Diamond(boolean[][] markedChangeOfBoard, Color[][] changedColorOfBoard, String[][] changedCoordOfBoard, Color filledColor) {
        super(markedChangeOfBoard, changedColorOfBoard, changedCoordOfBoard, filledColor);

        startPoint = new Point2D();
        endPoint = new Point2D();
        leftPoint = new Point2D();
        rightPoint = new Point2D();
        topPoint = new Point2D();
        bottomPoint = new Point2D();
    }

    public void setProperty(Point2D startPoint, Point2D endPoint) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;

        this.centerPoint = Point2D.midPoint(startPoint, endPoint);

        this.leftPoint.setCoord(this.startPoint.coordX, this.centerPoint.coordY);
        this.rightPoint.setCoord(this.endPoint.coordX, this.centerPoint.coordY);
        this.topPoint.setCoord(this.centerPoint.coordX, this.startPoint.coordY);
        this.bottomPoint.setCoord(this.centerPoint.coordX, this.endPoint.coordY);
    }

    @Override
    public void saveCoordinates() {
        this.leftPoint.saveCoord(changedCoordOfBoard);
        this.rightPoint.saveCoord(changedCoordOfBoard);
        this.topPoint.saveCoord(changedCoordOfBoard);
        this.bottomPoint.saveCoord(changedCoordOfBoard);
    }

    @Override
    public void drawOutline() {
        Point2D tempTopPoint = topPoint.createRotationPoint(centerPoint, rotatedAngle);
        Point2D tempBottomPoint = bottomPoint.createRotationPoint(centerPoint, rotatedAngle);
        Point2D tempLeftPoint = leftPoint.createRotationPoint(centerPoint, rotatedAngle);
        Point2D tempRightPoint = rightPoint.createRotationPoint(centerPoint, rotatedAngle);

        drawSegment(tempTopPoint, tempRightPoint);
        drawSegment(tempRightPoint, tempBottomPoint);
        drawSegment(tempBottomPoint, tempLeftPoint);
        drawSegment(tempLeftPoint, tempTopPoint);
    }

    @Override
    public void drawVirtualMove(Vector2D vector) {
        Point2D tempTopPoint = topPoint.createRotationPoint(centerPoint, rotatedAngle).move(vector);
        Point2D tempBottomPoint = bottomPoint.createRotationPoint(centerPoint, rotatedAngle).move(vector);
        Point2D tempLeftPoint = leftPoint.createRotationPoint(centerPoint, rotatedAngle).move(vector);
        Point2D tempRightPoint = rightPoint.createRotationPoint(centerPoint, rotatedAngle).move(vector);

        drawSegment(tempTopPoint, tempRightPoint);
        drawSegment(tempRightPoint, tempBottomPoint);
        drawSegment(tempBottomPoint, tempLeftPoint);
        drawSegment(tempLeftPoint, tempTopPoint);
    }

    @Override
    public void drawOXSymmetry() {
        Point2D tempTopPoint = topPoint.createRotationPoint(centerPoint, rotatedAngle).symOx();
        Point2D tempBottomPoint = bottomPoint.createRotationPoint(centerPoint, rotatedAngle).symOx();
        Point2D tempLeftPoint = leftPoint.createRotationPoint(centerPoint, rotatedAngle).symOx();
        Point2D tempRightPoint = rightPoint.createRotationPoint(centerPoint, rotatedAngle).symOx();

        drawSegment(tempTopPoint, tempRightPoint);
        drawSegment(tempRightPoint, tempBottomPoint);
        drawSegment(tempBottomPoint, tempLeftPoint);
        drawSegment(tempLeftPoint, tempTopPoint);
    }

    @Override
    public void drawOYSymmetry() {
        Point2D tempTopPoint = topPoint.createRotationPoint(centerPoint, rotatedAngle).symOy();
        Point2D tempBottomPoint = bottomPoint.createRotationPoint(centerPoint, rotatedAngle).symOy();
        Point2D tempLeftPoint = leftPoint.createRotationPoint(centerPoint, rotatedAngle).symOy();
        Point2D tempRightPoint = rightPoint.createRotationPoint(centerPoint, rotatedAngle).symOy();

        drawSegment(tempTopPoint, tempRightPoint);
        drawSegment(tempRightPoint, tempBottomPoint);
        drawSegment(tempBottomPoint, tempLeftPoint);
        drawSegment(tempLeftPoint, tempTopPoint);
    }

    @Override
    public void drawPointSymmetry(Point2D basePoint) {
        Point2D tempTopPoint = topPoint.createRotationPoint(centerPoint, rotatedAngle).symPoint(basePoint);
        Point2D tempBottomPoint = bottomPoint.createRotationPoint(centerPoint, rotatedAngle).symPoint(basePoint);
        Point2D tempLeftPoint = leftPoint.createRotationPoint(centerPoint, rotatedAngle).symPoint(basePoint);
        Point2D tempRightPoint = rightPoint.createRotationPoint(centerPoint, rotatedAngle).symPoint(basePoint);

        drawSegment(tempTopPoint, tempRightPoint);
        drawSegment(tempRightPoint, tempBottomPoint);
        drawSegment(tempBottomPoint, tempLeftPoint);
        drawSegment(tempLeftPoint, tempTopPoint);
    }

    @Override
    public void drawLineSymmetry(double a, double b, double c) {
        Point2D tempTopPoint = topPoint.createRotationPoint(centerPoint, rotatedAngle).symLine(a, b, c);
        Point2D tempBottomPoint = bottomPoint.createRotationPoint(centerPoint, rotatedAngle).symLine(a, b, c);
        Point2D tempLeftPoint = leftPoint.createRotationPoint(centerPoint, rotatedAngle).symLine(a, b, c);
        Point2D tempRightPoint = rightPoint.createRotationPoint(centerPoint, rotatedAngle).symLine(a, b, c);

        drawSegment(tempTopPoint, tempRightPoint);
        drawSegment(tempRightPoint, tempBottomPoint);
        drawSegment(tempBottomPoint, tempLeftPoint);
        drawSegment(tempLeftPoint, tempTopPoint);
    }

    @Override
    public void applyMove(Vector2D vector) {
        topPoint.rotate(rotatedAngle).move(vector);
        bottomPoint.rotate(rotatedAngle).move(vector);
        leftPoint.rotate(rotatedAngle).move(vector);
        rightPoint.rotate(rotatedAngle).move(vector);
        centerPoint.move(vector);
    }
}
