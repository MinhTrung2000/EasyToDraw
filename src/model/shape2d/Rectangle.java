package model.shape2d;

import control.myawt.SKPoint2D;
import java.awt.Color;

public class Rectangle extends Shape2D {

    public enum Modal {
        RECTANGLE,
        SQUARE
    }

    private SKPoint2D leftTopPoint;
    private SKPoint2D rightTopPoint;
    private SKPoint2D leftBottomPoint;
    private SKPoint2D rightBottomPoint;

    public Rectangle(boolean[][] markedChangeOfBoard, Color[][] changedColorOfBoard, String[][] changedCoordOfBoard, Color filledColor) {
        super(markedChangeOfBoard, changedColorOfBoard, changedCoordOfBoard, filledColor);

        leftTopPoint = new SKPoint2D(-1, -1);
        rightTopPoint = new SKPoint2D(-1, -1);
        leftBottomPoint = new SKPoint2D(-1, -1);
        rightBottomPoint = new SKPoint2D(-1, -1);
    }

    public void setProperty(SKPoint2D startPoint, SKPoint2D endPoint, Modal modal) {
        centerPoint = SKPoint2D.midPoint(startPoint, endPoint);

        if (modal == Modal.RECTANGLE) {
            leftTopPoint.setLocation(startPoint);
            rightBottomPoint.setLocation(endPoint);

            leftBottomPoint.setLocation(leftTopPoint.getCoordX(), rightBottomPoint.getCoordY());
            rightTopPoint.setLocation(rightBottomPoint.getCoordX(), leftTopPoint.getCoordY());

        } else {
            int width = (int) (endPoint.getCoordX() - startPoint.getCoordX());
            int height = (int) (endPoint.getCoordY() - startPoint.getCoordY());
            int widthDirection = this.getWidthDirection(width);
            int heightDirection = this.getHeightDirection(height);
            int preferedLength = this.getPreferredLength(width, height);
            if (preferedLength > 0) {
                leftTopPoint.setLocation(startPoint);
                leftBottomPoint.setLocation(startPoint.getCoordX(), startPoint.getCoordY() + heightDirection * preferedLength);
                rightTopPoint.setLocation(startPoint.getCoordX() + widthDirection * preferedLength, startPoint.getCoordY());
                rightBottomPoint.setLocation(startPoint.getCoordX() + widthDirection * preferedLength, startPoint.getCoordY() + heightDirection * preferedLength);

            }
        }
    }

    @Override
    public void setProperty(SKPoint2D startPoint, SKPoint2D endPoint) {
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
        SKPoint2D tempLeftTopPoint = leftTopPoint.createRotationPoint(centerPoint, this.rotatedAngle);
        SKPoint2D tempRightTopPoint = rightTopPoint.createRotationPoint(centerPoint, this.rotatedAngle);
        SKPoint2D tempLeftBottomPoint = leftBottomPoint.createRotationPoint(centerPoint, this.rotatedAngle);
        SKPoint2D tempRightBottomPoint = rightBottomPoint.createRotationPoint(centerPoint, this.rotatedAngle);

        drawSegment(tempLeftTopPoint, tempRightTopPoint, lineStyle);
        drawSegment(tempRightTopPoint, tempRightBottomPoint, lineStyle);
        drawSegment(tempRightBottomPoint, tempLeftBottomPoint, lineStyle);
        drawSegment(tempLeftBottomPoint, tempLeftTopPoint, lineStyle);
    }

}
