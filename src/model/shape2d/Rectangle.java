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
            int widthDirection = this.getWidthDirection(width);
            int heightDirection = this.getHeightDirection(height);
            int preferedLength = this.getPreferredLength(width, height);
            if (preferedLength > 0) {
                leftTopPoint.setCoord(startPoint);
                leftBottomPoint.setCoord(startPoint.getCoordX(), startPoint.getCoordY() + heightDirection * preferedLength);
                rightTopPoint.setCoord(startPoint.getCoordX() + widthDirection * preferedLength, startPoint.getCoordY());
                rightBottomPoint.setCoord(startPoint.getCoordX() + widthDirection * preferedLength, startPoint.getCoordY() + heightDirection * preferedLength);

            }
        }
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
    public void applyMove(Vector2D vector) {
        leftTopPoint.rotate(rotatedAngle).move(vector);
        rightTopPoint.rotate(rotatedAngle).move(vector);
        leftBottomPoint.rotate(rotatedAngle).move(vector);
        rightBottomPoint.rotate(rotatedAngle).move(vector);

        centerPoint.move(vector);
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
