package model.shape2d;

import control.myawt.SKPoint2D;
import java.awt.Color;

public class Diamond extends Shape2D {

    private SKPoint2D startPoint;
    private SKPoint2D endPoint;

    private SKPoint2D leftPoint;
    private SKPoint2D rightPoint;
    private SKPoint2D topPoint;
    private SKPoint2D bottomPoint;

    public enum Modal {
        COMMON_DIAMOND,
        SQUARE_DIAMOND,
    }
    public Diamond(boolean[][] markedChangeOfBoard, Color[][] changedColorOfBoard, String[][] changedCoordOfBoard, Color filledColor) {
        super(markedChangeOfBoard, changedColorOfBoard, changedCoordOfBoard, filledColor);

        startPoint = new SKPoint2D();
        endPoint = new SKPoint2D();
        leftPoint = new SKPoint2D();
        rightPoint = new SKPoint2D();
        topPoint = new SKPoint2D();
        bottomPoint = new SKPoint2D();
    }

    public void setProperty(SKPoint2D startPoint, SKPoint2D endPoint,Modal modal) {
        int width = (int) (endPoint.getCoordX() - startPoint.getCoordX());
        int height = (int) (endPoint.getCoordY() - startPoint.getCoordY());
        
        this.startPoint = startPoint;
        if(modal == Modal.COMMON_DIAMOND){
            this.endPoint = endPoint;
        } else {
            int widthDirection = this.getWidthDirection(width);
            int heightDirection = this.getHeightDirection(height);
            int preferedLength = this.getPreferredLength(width, height);
            
            
            this.endPoint.setLocation(this.startPoint.getCoordX()+widthDirection*preferedLength,this.startPoint.getCoordY()+heightDirection*preferedLength);
        }
        
        
        this.centerPoint2D = SKPoint2D.midPoint(this.startPoint, this.endPoint);

        this.leftPoint.setLocation(this.startPoint.getCoordX(), this.centerPoint2D.getCoordY());
        this.rightPoint.setLocation(this.endPoint.getCoordX(), this.centerPoint2D.getCoordY());
        this.topPoint.setLocation(this.centerPoint2D.getCoordX(), this.startPoint.getCoordY());
        this.bottomPoint.setLocation(this.centerPoint2D.getCoordX(), this.endPoint.getCoordY());
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
        SKPoint2D tempTopPoint = topPoint.createRotationPoint(centerPoint2D, rotatedAngle);
        SKPoint2D tempBottomPoint = bottomPoint.createRotationPoint(centerPoint2D, rotatedAngle);
        SKPoint2D tempLeftPoint = leftPoint.createRotationPoint(centerPoint2D, rotatedAngle);
        SKPoint2D tempRightPoint = rightPoint.createRotationPoint(centerPoint2D, rotatedAngle);

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
}
