package model.shape2d;

import java.awt.Color;

public class Rectangle extends Shape2D {

    public enum Modal {
        RECTANGLE,
        SQUARE
    }

    private Point2D leftTopPoint;
    private Point2D rightTopPoint;
    private Point2D leftBottomPoint;
    private Point2D rightBottomPoint;

    public Rectangle(boolean[][] markedChangeOfBoard, Color[][] changedColorOfBoard, String[][] changedCoordOfBoard, Color filledColor) {
        super(markedChangeOfBoard, changedColorOfBoard, changedCoordOfBoard, filledColor);

        leftTopPoint = new Point2D(-1, -1);
        rightTopPoint = new Point2D(-1, -1);
        leftBottomPoint = new Point2D(-1, -1);
        rightBottomPoint = new Point2D(-1, -1);
    }

    public void setProperty(Point2D startPoint, Point2D endPoint, Modal modal) {
        centerPoint = Point2D.midPoint(startPoint, endPoint);

        if (modal == Modal.RECTANGLE) {
            leftTopPoint.setCoord(startPoint);
            rightBottomPoint.setCoord(endPoint);

            leftBottomPoint.setCoord(leftTopPoint.coordX, rightBottomPoint.coordY);
            rightTopPoint.setCoord(rightBottomPoint.coordX, leftTopPoint.coordY);

        } else {
            int width = endPoint.getCoordX() - startPoint.getCoordX();
            int height = endPoint.getCoordY() - startPoint.getCoordY();
            int widthDirection;
            int heightDirection;
            if (width <= 0) {
                widthDirection = -1;
            } else {
                widthDirection = 1;
            }

            if (height <= 0) {
                heightDirection = -1;
            } else {
                heightDirection = 1;
            }
            int widthValue = Math.abs(width);
            int heightValue = Math.abs(height);

            int preferedLength;
            if (widthValue >= heightValue) {
                preferedLength = heightValue;
            } else {
                preferedLength = widthValue;
            }
            if (preferedLength > 0) {
                leftTopPoint.setCoord(startPoint);
                leftBottomPoint.setCoord(startPoint.getCoordX(), startPoint.getCoordY() + heightDirection * preferedLength);
                rightTopPoint.setCoord(startPoint.getCoordX() + widthDirection * preferedLength, startPoint.getCoordY());
                rightBottomPoint.setCoord(startPoint.getCoordX() + widthDirection * preferedLength, startPoint.getCoordY() + heightDirection * preferedLength);

            }
        }
    }

    @Override
    public void drawVirtualRotation(Point2D centerPoint, double angle) {
        double totalAngle = this.rotatedAngle + angle;

        Point2D tempLeftTopPoint = leftTopPoint.createRotationPoint(centerPoint, totalAngle);
        Point2D tempRightTopPoint = rightTopPoint.createRotationPoint(centerPoint, totalAngle);
        Point2D tempLeftBottomPoint = leftBottomPoint.createRotationPoint(centerPoint, totalAngle);
        Point2D tempRightBottomPoint = rightBottomPoint.createRotationPoint(centerPoint, totalAngle);

        drawSegment(tempLeftTopPoint, tempRightTopPoint, lineStyle);
        drawSegment(tempRightTopPoint, tempLeftBottomPoint, lineStyle);
        drawSegment(tempLeftBottomPoint, tempRightBottomPoint, lineStyle);
        drawSegment(tempRightBottomPoint, tempLeftTopPoint, lineStyle);
    }

    @Override
    public void setProperty(Point2D startPoint, Point2D endPoint) {
        setProperty(startPoint, endPoint, Modal.RECTANGLE);
    }

    @Override
    public void saveCoordinates() {
        leftTopPoint.createRotationPoint(centerPoint, rotatedAngle).saveCoord(this.changedCoordOfBoard);
        rightTopPoint.createRotationPoint(centerPoint, rotatedAngle).saveCoord(this.changedCoordOfBoard);
        leftBottomPoint.createRotationPoint(centerPoint, rotatedAngle).saveCoord(this.changedCoordOfBoard);
        rightBottomPoint.createRotationPoint(centerPoint, rotatedAngle).saveCoord(this.changedCoordOfBoard);
    }

    @Override
    public void drawVirtualRotation(double angle) {
        double totalAngle = this.rotatedAngle + angle;

        Point2D tempLeftTopPoint = leftTopPoint.createRotationPoint(centerPoint, totalAngle);
        Point2D tempRightTopPoint = rightTopPoint.createRotationPoint(centerPoint, totalAngle);
        Point2D tempLeftBottomPoint = leftBottomPoint.createRotationPoint(centerPoint, totalAngle);
        Point2D tempRightBottomPoint = rightBottomPoint.createRotationPoint(centerPoint, totalAngle);

        drawSegment(tempLeftTopPoint, tempRightTopPoint, lineStyle);
        drawSegment(tempRightTopPoint, tempLeftBottomPoint, lineStyle);
        drawSegment(tempLeftBottomPoint, tempRightBottomPoint, lineStyle);
        drawSegment(tempRightBottomPoint, tempLeftTopPoint, lineStyle);
    }

    @Override
    public void drawVirtualMove(Vector2D vector) {
        Point2D tempLeftTopPoint = leftTopPoint.createRotationPoint(centerPoint, rotatedAngle).move(vector);
        Point2D tempRightTopPoint = rightTopPoint.createRotationPoint(centerPoint, rotatedAngle).move(vector);
        Point2D tempLeftBottomPoint = leftBottomPoint.createRotationPoint(centerPoint, rotatedAngle).move(vector);
        Point2D tempRightBottomPoint = rightBottomPoint.createRotationPoint(centerPoint, rotatedAngle).move(vector);

        drawSegment(tempLeftTopPoint, tempRightTopPoint, lineStyle);
        drawSegment(tempRightTopPoint, tempLeftBottomPoint, lineStyle);
        drawSegment(tempLeftBottomPoint, tempRightBottomPoint, lineStyle);
        drawSegment(tempRightBottomPoint, tempLeftTopPoint, lineStyle);
    }

    @Override
    public void applyMove(Vector2D vector) {
        leftTopPoint.move(vector);
        rightTopPoint.move(vector);
        leftBottomPoint.move(vector);
        rightBottomPoint.move(vector);

        centerPoint.move(vector);
    }

    @Override
    public void drawOXSymmetry() {
        Point2D tempLeftTopPoint = leftTopPoint.createRotationPoint(centerPoint, rotatedAngle).symOx();
        Point2D tempRightTopPoint = rightTopPoint.createRotationPoint(centerPoint, rotatedAngle).symOx();
        Point2D tempLeftBottomPoint = leftBottomPoint.createRotationPoint(centerPoint, rotatedAngle).symOx();
        Point2D tempRightBottomPoint = rightBottomPoint.createRotationPoint(centerPoint, rotatedAngle).symOx();

        drawSegment(tempLeftTopPoint, tempRightTopPoint, lineStyle);
        drawSegment(tempRightTopPoint, tempLeftBottomPoint, lineStyle);
        drawSegment(tempLeftBottomPoint, tempRightBottomPoint, lineStyle);
        drawSegment(tempRightBottomPoint, tempLeftTopPoint, lineStyle);
    }

    @Override
    public void drawOYSymmetry() {
        Point2D tempLeftTopPoint = leftTopPoint.createRotationPoint(centerPoint, rotatedAngle).symOy();
        Point2D tempRightTopPoint = rightTopPoint.createRotationPoint(centerPoint, rotatedAngle).symOy();
        Point2D tempLeftBottomPoint = leftBottomPoint.createRotationPoint(centerPoint, rotatedAngle).symOy();
        Point2D tempRightBottomPoint = rightBottomPoint.createRotationPoint(centerPoint, rotatedAngle).symOy();

        drawSegment(tempLeftTopPoint, tempRightTopPoint, lineStyle);
        drawSegment(tempRightTopPoint, tempLeftBottomPoint, lineStyle);
        drawSegment(tempLeftBottomPoint, tempRightBottomPoint, lineStyle);
        drawSegment(tempRightBottomPoint, tempLeftTopPoint, lineStyle);
    }

    @Override
    public void drawPointSymmetry(Point2D point) {
        Point2D tempLeftTopPoint = leftTopPoint.createRotationPoint(centerPoint, rotatedAngle).symPoint(point);
        Point2D tempRightTopPoint = rightTopPoint.createRotationPoint(centerPoint, rotatedAngle).symPoint(point);
        Point2D tempLeftBottomPoint = leftBottomPoint.createRotationPoint(centerPoint, rotatedAngle).symPoint(point);
        Point2D tempRightBottomPoint = rightBottomPoint.createRotationPoint(centerPoint, rotatedAngle).symPoint(point);

        drawSegment(tempLeftTopPoint, tempRightTopPoint, lineStyle);
        drawSegment(tempRightTopPoint, tempLeftBottomPoint, lineStyle);
        drawSegment(tempLeftBottomPoint, tempRightBottomPoint, lineStyle);
        drawSegment(tempRightBottomPoint, tempLeftTopPoint, lineStyle);
    }

    @Override
    public void drawLineSymmetry(double a, double b, double c) {
        Point2D tempLeftTopPoint = leftTopPoint.createRotationPoint(centerPoint, rotatedAngle).symLine(a, b, c);
        Point2D tempRightTopPoint = rightTopPoint.createRotationPoint(centerPoint, rotatedAngle).symLine(a, b, c);
        Point2D tempLeftBottomPoint = leftBottomPoint.createRotationPoint(centerPoint, rotatedAngle).symLine(a, b, c);
        Point2D tempRightBottomPoint = rightBottomPoint.createRotationPoint(centerPoint, rotatedAngle).symLine(a, b, c);

        drawSegment(tempLeftTopPoint, tempRightTopPoint, lineStyle);
        drawSegment(tempRightTopPoint, tempLeftBottomPoint, lineStyle);
        drawSegment(tempLeftBottomPoint, tempRightBottomPoint, lineStyle);
        drawSegment(tempRightBottomPoint, tempLeftTopPoint, lineStyle);
    }

    @Override
    public void drawOutline() {
        Point2D tempLeftTopPoint = leftTopPoint.createRotationPoint(centerPoint, this.rotatedAngle);
        Point2D tempRightTopPoint = rightTopPoint.createRotationPoint(centerPoint, this.rotatedAngle);
        Point2D tempLeftBottomPoint = leftBottomPoint.createRotationPoint(centerPoint, this.rotatedAngle);
        Point2D tempRightBottomPoint = rightBottomPoint.createRotationPoint(centerPoint, this.rotatedAngle);

        drawSegment(tempLeftTopPoint, tempRightTopPoint, lineStyle);
        drawSegment(tempRightTopPoint, tempRightBottomPoint, lineStyle);
        drawSegment(tempRightBottomPoint, tempLeftBottomPoint, lineStyle);
        drawSegment(tempLeftBottomPoint, tempLeftTopPoint, lineStyle);
    }

}
