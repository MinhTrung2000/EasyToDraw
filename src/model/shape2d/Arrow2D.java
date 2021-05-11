package model.shape2d;

import control.myawt.SKPoint2D;
import java.awt.Color;
import java.awt.Point;

/* 
   
  O +--------> Ox
    |
    |
    |
 Oy v
    
    start                 F
        +---------------- *
        |               D * *
     A  *******************  *
        |         *           *E
        |       center        *|
     B  *******************  * |
                        C * *  |
                          *----+
                          G   end

 */
public class Arrow2D extends Shape2D {

    private SKPoint2D pointA = new SKPoint2D();
    private SKPoint2D pointB = new SKPoint2D();
    private SKPoint2D pointC = new SKPoint2D();
    private SKPoint2D pointD = new SKPoint2D();
    private SKPoint2D pointE = new SKPoint2D();
    private SKPoint2D pointF = new SKPoint2D();
    private SKPoint2D pointG = new SKPoint2D();

    public Arrow2D(boolean[][] markedChangeOfBoard, Color[][] changedColorOfBoard, String[][] changedCoordOfBoard, Color filledColor) {
        super(markedChangeOfBoard, changedColorOfBoard, changedCoordOfBoard, filledColor);
    }

    public void setProperty(SKPoint2D startPoint, SKPoint2D endPoint) {
        startPoint2D.setLocation(startPoint);
        endPoint2D.setLocation(endPoint);

        centerPoint2D = SKPoint2D.midPoint(startPoint, endPoint);

        int dx = (int) (endPoint.getCoordX() - startPoint.getCoordX()) / 5;
        int dy = (int) (endPoint.getCoordY() - startPoint.getCoordY()) / 4;

        pointA.setLocation(startPoint.getCoordX(), startPoint.getCoordY() + dy);

        pointB.setLocation(startPoint.getCoordX(), startPoint.getCoordY() + 3 * dy);

        pointC.setLocation(endPoint.getCoordX() - dx, pointB.getCoordY());

        pointD.setLocation(pointC.getCoordX(), pointA.getCoordY());

        pointE.setLocation(endPoint.getCoordX(), startPoint.getCoordY() + 2 * dy);

        pointF.setLocation(pointD.getCoordX(), startPoint.getCoordY());

        pointG.setLocation(pointC.getCoordX(), endPoint.getCoordY());
    }

    @Override
    public void drawOutline() {
        drawSegment(pointA, pointB);
        drawSegment(pointB, pointC);
        drawSegment(pointC, pointG);
        drawSegment(pointG, pointE);
        drawSegment(pointE, pointF);
        drawSegment(pointF, pointD);
        drawSegment(pointD, pointA);
    }

    @Override
    public void applyMove(Vector2D vector) {
        pointA.rotate(rotatedAngle).move(vector);
        pointB.rotate(rotatedAngle).move(vector);
        pointC.rotate(rotatedAngle).move(vector);
        pointD.rotate(rotatedAngle).move(vector);
        pointE.rotate(rotatedAngle).move(vector);
        pointF.rotate(rotatedAngle).move(vector);
        pointG.rotate(rotatedAngle).move(vector);
        centerPoint2D.move(vector);
    }

    @Override
    public void saveCoordinates() {
        pointA.saveCoord(changedCoordOfBoard);
        pointB.saveCoord(changedCoordOfBoard);
        pointC.saveCoord(changedCoordOfBoard);
        pointD.saveCoord(changedCoordOfBoard);
        pointE.saveCoord(changedCoordOfBoard);
        pointF.saveCoord(changedCoordOfBoard);
        pointG.saveCoord(changedCoordOfBoard);
    }

    @Override
    public void createRotateInstance(SKPoint2D centerPoint, double angle) {
        if (pointSet.isEmpty()) {
            return;
        }

        double totalAngle = rotatedAngle + angle;

        SKPoint2D newPointA = pointA.getRotationPoint(centerPoint, totalAngle);
        SKPoint2D newPointB = pointB.getRotationPoint(centerPoint, totalAngle);
        SKPoint2D newPointC = pointC.getRotationPoint(centerPoint, totalAngle);
        SKPoint2D newPointD = pointD.getRotationPoint(centerPoint, totalAngle);
        SKPoint2D newPointE = pointE.getRotationPoint(centerPoint, totalAngle);
        SKPoint2D newPointF = pointF.getRotationPoint(centerPoint, totalAngle);
        SKPoint2D newPointG = pointG.getRotationPoint(centerPoint, totalAngle);

        drawSegment(newPointA, newPointB);
        drawSegment(newPointB, newPointC);
        drawSegment(newPointC, newPointG);
        drawSegment(newPointG, newPointE);
        drawSegment(newPointE, newPointF);
        drawSegment(newPointF, newPointD);
        drawSegment(newPointD, newPointA);
        
        newPointA.saveCoord(changedCoordOfBoard);
        newPointB.saveCoord(changedCoordOfBoard);
        newPointC.saveCoord(changedCoordOfBoard);
        newPointD.saveCoord(changedCoordOfBoard);
        newPointE.saveCoord(changedCoordOfBoard);
        newPointF.saveCoord(changedCoordOfBoard);
        newPointG.saveCoord(changedCoordOfBoard);
    }

}
