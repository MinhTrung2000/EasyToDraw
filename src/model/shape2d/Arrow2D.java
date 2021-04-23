package model.shape2d;

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

    private Point2D pointA;
    private Point2D pointB;
    private Point2D pointC;
    private Point2D pointD;
    private Point2D pointE;
    private Point2D pointF;
    private Point2D pointG;

    public Arrow2D(boolean[][] markedChangeOfBoard, Color[][] changedColorOfBoard, String[][] changedCoordOfBoard, Color filledColor) {
        super(markedChangeOfBoard, changedColorOfBoard, changedCoordOfBoard, filledColor);
        
        pointA = new Point2D();
        pointB = new Point2D();
        pointC = new Point2D();
        pointD = new Point2D();
        pointE = new Point2D();
        pointF = new Point2D();
        pointG = new Point2D();
    }

    public void setProperty(Point2D startPoint, Point2D endPoint) {
        centerPoint = Point2D.midPoint(startPoint, endPoint);
        
        int dx = (int) (endPoint.coordX - startPoint.coordX) / 5;
        int dy = (int) (endPoint.coordY - startPoint.coordY) / 4;
        
        pointA.setCoord(startPoint.coordX, startPoint.coordY + dy);
        
        pointB.setCoord(startPoint.coordX,startPoint.coordY + 3 * dy);
        
        pointC.setCoord(endPoint.coordX - dx, pointB.coordY);
        
        pointD.setCoord(pointC.coordX, pointA.coordY);
        
        pointE.setCoord(endPoint.coordX, startPoint.coordY + 2 * dy);
        
        pointF.setCoord(pointD.coordX, startPoint.coordY);
        
        pointG.setCoord(pointC.coordX, endPoint.coordY);
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
        centerPoint.move(vector);
    }

    @Override
    public void drawOXSymmetry() {
        Point2D tempPointA = pointA.createRotationPoint(centerPoint, rotatedAngle).symOx();
        Point2D tempPointB = pointB.createRotationPoint(centerPoint, rotatedAngle).symOx();
        Point2D tempPointC = pointC.createRotationPoint(centerPoint, rotatedAngle).symOx();
        Point2D tempPointD = pointD.createRotationPoint(centerPoint, rotatedAngle).symOx();
        Point2D tempPointE = pointE.createRotationPoint(centerPoint, rotatedAngle).symOx();
        Point2D tempPointF = pointF.createRotationPoint(centerPoint, rotatedAngle).symOx();
        Point2D tempPointG = pointG.createRotationPoint(centerPoint, rotatedAngle).symOx();
        
        drawSegment(tempPointA, tempPointB);
        drawSegment(tempPointB, tempPointC);
        drawSegment(tempPointC, tempPointG);
        drawSegment(tempPointG, tempPointE);
        drawSegment(tempPointE, tempPointF);
        drawSegment(tempPointF, tempPointD);
        drawSegment(tempPointD, tempPointA);
    }

    @Override
    public void drawOYSymmetry() {
        Point2D tempPointA = pointA.createRotationPoint(centerPoint, rotatedAngle).symOy();
        Point2D tempPointB = pointB.createRotationPoint(centerPoint, rotatedAngle).symOy();
        Point2D tempPointC = pointC.createRotationPoint(centerPoint, rotatedAngle).symOy();
        Point2D tempPointD = pointD.createRotationPoint(centerPoint, rotatedAngle).symOy();
        Point2D tempPointE = pointE.createRotationPoint(centerPoint, rotatedAngle).symOy();
        Point2D tempPointF = pointF.createRotationPoint(centerPoint, rotatedAngle).symOy();
        Point2D tempPointG = pointG.createRotationPoint(centerPoint, rotatedAngle).symOy();
        
        drawSegment(tempPointA, tempPointB);
        drawSegment(tempPointB, tempPointC);
        drawSegment(tempPointC, tempPointG);
        drawSegment(tempPointG, tempPointE);
        drawSegment(tempPointE, tempPointF);
        drawSegment(tempPointF, tempPointD);
        drawSegment(tempPointD, tempPointA);    
    }

    @Override
    public void drawPointSymmetry(Point2D basePoint) {
        Point2D tempPointA = pointA.createRotationPoint(centerPoint, rotatedAngle).symPoint(basePoint);
        Point2D tempPointB = pointB.createRotationPoint(centerPoint, rotatedAngle).symPoint(basePoint);
        Point2D tempPointC = pointC.createRotationPoint(centerPoint, rotatedAngle).symPoint(basePoint);
        Point2D tempPointD = pointD.createRotationPoint(centerPoint, rotatedAngle).symPoint(basePoint);
        Point2D tempPointE = pointE.createRotationPoint(centerPoint, rotatedAngle).symPoint(basePoint);
        Point2D tempPointF = pointF.createRotationPoint(centerPoint, rotatedAngle).symPoint(basePoint);
        Point2D tempPointG = pointG.createRotationPoint(centerPoint, rotatedAngle).symPoint(basePoint);
        
        drawSegment(tempPointA, tempPointB);
        drawSegment(tempPointB, tempPointC);
        drawSegment(tempPointC, tempPointG);
        drawSegment(tempPointG, tempPointE);
        drawSegment(tempPointE, tempPointF);
        drawSegment(tempPointF, tempPointD);
        drawSegment(tempPointD, tempPointA);        
    }

    @Override
    public void drawVirtualMove(Vector2D vector) {
        Point2D tempPointA = pointA.createRotationPoint(centerPoint, rotatedAngle).move(vector);
        Point2D tempPointB = pointB.createRotationPoint(centerPoint, rotatedAngle).move(vector);
        Point2D tempPointC = pointC.createRotationPoint(centerPoint, rotatedAngle).move(vector);
        Point2D tempPointD = pointD.createRotationPoint(centerPoint, rotatedAngle).move(vector);
        Point2D tempPointE = pointE.createRotationPoint(centerPoint, rotatedAngle).move(vector);
        Point2D tempPointF = pointF.createRotationPoint(centerPoint, rotatedAngle).move(vector);
        Point2D tempPointG = pointG.createRotationPoint(centerPoint, rotatedAngle).move(vector);
        
        drawSegment(tempPointA, tempPointB);
        drawSegment(tempPointB, tempPointC);
        drawSegment(tempPointC, tempPointG);
        drawSegment(tempPointG, tempPointE);
        drawSegment(tempPointE, tempPointF);
        drawSegment(tempPointF, tempPointD);
        drawSegment(tempPointD, tempPointA);               
    }

    @Override
    public void drawVirtualRotation(double angle) {
        double totalAngle = this.rotatedAngle + angle;
        
        Point2D tempPointA = pointA.createRotationPoint(centerPoint, rotatedAngle).rotate(centerPoint, totalAngle);
        Point2D tempPointB = pointB.createRotationPoint(centerPoint, rotatedAngle).rotate(centerPoint, totalAngle);
        Point2D tempPointC = pointC.createRotationPoint(centerPoint, rotatedAngle).rotate(centerPoint, totalAngle);
        Point2D tempPointD = pointD.createRotationPoint(centerPoint, rotatedAngle).rotate(centerPoint, totalAngle);
        Point2D tempPointE = pointE.createRotationPoint(centerPoint, rotatedAngle).rotate(centerPoint, totalAngle);
        Point2D tempPointF = pointF.createRotationPoint(centerPoint, rotatedAngle).rotate(centerPoint, totalAngle);
        Point2D tempPointG = pointG.createRotationPoint(centerPoint, rotatedAngle).rotate(centerPoint, totalAngle);
        
        drawSegment(tempPointA, tempPointB);
        drawSegment(tempPointB, tempPointC);
        drawSegment(tempPointC, tempPointG);
        drawSegment(tempPointG, tempPointE);
        drawSegment(tempPointE, tempPointF);
        drawSegment(tempPointF, tempPointD);
        drawSegment(tempPointD, tempPointA);         
    }

    @Override
    public void savePointCoordinate(int coordX, int coordY) {
        pointA.saveCoord(changedCoordOfBoard);
        pointB.saveCoord(changedCoordOfBoard);
        pointC.saveCoord(changedCoordOfBoard);
        pointD.saveCoord(changedCoordOfBoard);
        pointE.saveCoord(changedCoordOfBoard);
        pointF.saveCoord(changedCoordOfBoard);
        pointG.saveCoord(changedCoordOfBoard);
    }
}
