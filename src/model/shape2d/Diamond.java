package model.shape2d;

import control.myawt.SKPoint2D;
import java.awt.Color;

public class Diamond extends Shape2D {

    private SKPoint2D leftPoint = new SKPoint2D();
    private SKPoint2D rightPoint = new SKPoint2D();
    private SKPoint2D topPoint = new SKPoint2D();
    private SKPoint2D bottomPoint = new SKPoint2D();

    public enum Modal {
        COMMON_DIAMOND,
        SQUARE_DIAMOND,
    }

    public Diamond(boolean[][] markedChangeOfBoard, Color[][] changedColorOfBoard, String[][] changedCoordOfBoard, Color filledColor) {
        super(markedChangeOfBoard, changedColorOfBoard, changedCoordOfBoard, filledColor);
    }

    public void setProperty(SKPoint2D startPoint, SKPoint2D endPoint, Modal modal) {
        int width = (int) (endPoint.getCoordX() - startPoint.getCoordX());
        int height = (int) (endPoint.getCoordY() - startPoint.getCoordY());

        this.startPoint2D.setLocation(startPoint);

        if (modal == Modal.COMMON_DIAMOND) {
            this.endPoint2D = endPoint;
        } else {
            int widthDirection = this.getWidthDirection(width);
            int heightDirection = this.getHeightDirection(height);
            int preferedLength = this.getPreferredLength(width, height);

            this.endPoint2D.setLocation(this.startPoint2D.getCoordX() + widthDirection * preferedLength, this.startPoint2D.getCoordY() + heightDirection * preferedLength);
        }

        this.centerPoint2D = SKPoint2D.midPoint(this.startPoint2D, this.endPoint2D);

        this.leftPoint.setLocation(this.startPoint2D.getCoordX(), this.centerPoint2D.getCoordY());
        this.rightPoint.setLocation(this.endPoint2D.getCoordX(), this.centerPoint2D.getCoordY());
        this.topPoint.setLocation(this.centerPoint2D.getCoordX(), this.startPoint2D.getCoordY());
        this.bottomPoint.setLocation(this.centerPoint2D.getCoordX(), this.endPoint2D.getCoordY());
    }

    @Override
    public void setProperty(SKPoint2D startPoint, SKPoint2D endPoint) {
        setProperty(startPoint, endPoint, Modal.COMMON_DIAMOND);
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
        SKPoint2D tempTopPoint = topPoint.createRotate(centerPoint2D, rotatedAngle);
        SKPoint2D tempBottomPoint = bottomPoint.createRotate(centerPoint2D, rotatedAngle);
        SKPoint2D tempLeftPoint = leftPoint.createRotate(centerPoint2D, rotatedAngle);
        SKPoint2D tempRightPoint = rightPoint.createRotate(centerPoint2D, rotatedAngle);

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
        centerPoint2D.move(vector);
    }

    @Override
    public void createRotateInstance(SKPoint2D centerPoint, double angle) {
        if (pointSet.isEmpty()) {
            return;
        }

        double totalAngle = rotatedAngle + angle;

        SKPoint2D newTopPoint = topPoint.createRotate(centerPoint, totalAngle);
        SKPoint2D newBottomPoint = bottomPoint.createRotate(centerPoint, totalAngle);
        SKPoint2D newLeftPoint = leftPoint.createRotate(centerPoint, totalAngle);
        SKPoint2D newRightPoint = rightPoint.createRotate(centerPoint, totalAngle);

        drawSegment(newTopPoint, newRightPoint);
        drawSegment(newRightPoint, newBottomPoint);
        drawSegment(newBottomPoint, newLeftPoint);
        drawSegment(newLeftPoint, newTopPoint);
        
        newTopPoint.saveCoord(changedCoordOfBoard);
        newBottomPoint.saveCoord(changedCoordOfBoard);
        newLeftPoint.saveCoord(changedCoordOfBoard);
        newRightPoint.saveCoord(changedCoordOfBoard);
    }
}
