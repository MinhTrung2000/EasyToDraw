package model.shape2d;

import control.myawt.SKPoint2D;
import java.awt.Color;

public class Star extends Shape2D {

    // Five top points
    private SKPoint2D pointA;
    private SKPoint2D pointB;
    private SKPoint2D pointC;
    private SKPoint2D pointD;
    private SKPoint2D pointE;

    // File oppsite points of above
    private SKPoint2D opPointA;
    private SKPoint2D opPointB;
    private SKPoint2D opPointC;
    private SKPoint2D opPointD;
    private SKPoint2D opPointE;

    public Star(boolean[][] markedChangeOfBoard, Color[][] changedColorOfBoard, String[][] changedCoordOfBoard, Color filledColor) {
        super(markedChangeOfBoard, changedColorOfBoard, changedCoordOfBoard, filledColor);

        pointA = new SKPoint2D();
        pointB = new SKPoint2D();
        pointC = new SKPoint2D();
        pointD = new SKPoint2D();
        pointE = new SKPoint2D();

        opPointA = new SKPoint2D();
        opPointB = new SKPoint2D();
        opPointC = new SKPoint2D();
        opPointD = new SKPoint2D();
        opPointE = new SKPoint2D();
    }

    public void setProperty(SKPoint2D startPoint, SKPoint2D endPoint) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        
        int width = (int) (endPoint.getCoordX() - startPoint.getCoordX());
        int height = (int) (endPoint.getCoordY() - startPoint.getCoordY());
        
         int heightValue = Math.abs(height);
            int widthValue = Math.abs(width);
            int preferedLength;
            if (heightValue >= widthValue) {
                preferedLength = widthValue;
            } else {
                preferedLength = heightValue;
            }
        int widthDirection;
            if (width < 0) {
                widthDirection = -1;
            } else {
                widthDirection = 1;
            }

            int heightDirection;
            if (height < 0) {
                heightDirection = -1;
            } else {
                heightDirection = 1;
            }
            
            
        this.startPoint=startPoint;
        this.endPoint.setLocation(startPoint.getCoordX() + widthDirection * preferedLength, startPoint.getCoordY() + heightDirection * preferedLength);
        
        centerPoint = SKPoint2D.midPoint(this.startPoint, this.endPoint);
        if(widthDirection == 1 && heightDirection == 1 || widthDirection == -1 && heightDirection ==1){
            pointA.setLocation(centerPoint.getCoordX(), this.startPoint.getCoordY());
        }else{
            pointA.setLocation(centerPoint.getCoordX(), this.endPoint.getCoordY());
        }
        
        opPointA.setLocation(pointA.getCoordX(), pointA.getCoordY() + (int) ((centerPoint.getCoordY() - pointA.getCoordY()) * 3 / 2));

        pointB = pointA.createRotationPoint(centerPoint, Math.toRadians(72));
        opPointB = opPointA.createRotationPoint(centerPoint, Math.toRadians(72));

        pointC = pointB.createRotationPoint(centerPoint, Math.toRadians(72));
        opPointC = opPointB.createRotationPoint(centerPoint, Math.toRadians(72));

        pointD = pointC.createRotationPoint(centerPoint, Math.toRadians(72));
        opPointD = opPointC.createRotationPoint(centerPoint, Math.toRadians(72));

        pointE = pointD.createRotationPoint(centerPoint, Math.toRadians(72));
        opPointE = opPointD.createRotationPoint(centerPoint, Math.toRadians(72));
    }

    @Override
    public void drawOutline() {
        drawSegment(pointA, opPointD);
        drawSegment(opPointD, pointB);
        drawSegment(pointB, opPointE);
        drawSegment(opPointE, pointC);
        drawSegment(pointC, opPointA);
        drawSegment(opPointA, pointD);
        drawSegment(pointD, opPointB);
        drawSegment(opPointB, pointE);
        drawSegment(pointE, opPointC);
        drawSegment(opPointC, pointA);
    }

    @Override
    public void applyMove(Vector2D vector) {
        pointA.rotate(rotatedAngle).move(vector);
        pointB.rotate(rotatedAngle).move(vector);
        pointC.rotate(rotatedAngle).move(vector);
        pointD.rotate(rotatedAngle).move(vector);
        pointE.rotate(rotatedAngle).move(vector);

        opPointA.rotate(rotatedAngle).move(vector);
        opPointB.rotate(rotatedAngle).move(vector);
        opPointC.rotate(rotatedAngle).move(vector);
        opPointD.rotate(rotatedAngle).move(vector);
        opPointE.rotate(rotatedAngle).move(vector);

        centerPoint.move(vector);
    }

    @Override
    public void saveCoordinates() {
        pointA.saveCoord(changedCoordOfBoard);
        pointB.saveCoord(changedCoordOfBoard);
        pointC.saveCoord(changedCoordOfBoard);
        pointD.saveCoord(changedCoordOfBoard);
        pointE.saveCoord(changedCoordOfBoard);
    }
}
