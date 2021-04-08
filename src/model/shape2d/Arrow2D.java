package model.shape2d;

import java.awt.Color;
import java.awt.Point;

/*      
   
  O +--------> Ox
    |
    |
    |
 Oy v
               
               (top)
                **-------+ <---------- end point
              *    *     |
            *        *   |
          *  C      D  * |
(left)  *****        *****     (right)
            *    *   *
            *  center*
            *        *
            *        *
            *        *
          A ********** B
            ^
            |
        start point

distance(left, C) = distance(right, D) = AB / 2
C.y - top.y = 2/3 * (C.y - A.y)

A           = start
B           = [(end.x - start.x) / 3 * 2, A.y]
C           = [A.x, end.y + (start.y - end.y) / 3]
D           = [B.x, C.y]
left        = [C.x - (end.x - D.x), C.y]
right       = [D.x + (end.x - D.x), D.y]
top         = [(A.x + B.x) / 2, end.y]
center      = [top.x, top.y + (A.y - top.y) / 2]
*/


public class Arrow2D extends Shape2D {

    private Point2D pointA;
    private Point2D pointB;
    private Point2D pointC;
    private Point2D pointD;
    private Point2D pointLeft;
    private Point2D pointRight;
    private Point2D pointTop;
//    private Point2D startPoint;
//    private Point2D endPoint;

    public Arrow2D(boolean[][] markedChangeOfBoard, Color[][] changedColorOfBoard, String[][] changedCoordOfBoard) {
        super(markedChangeOfBoard, changedColorOfBoard, changedCoordOfBoard);
        
        pointA = new Point2D(0, 0);
        pointB = new Point2D(0, 0);
        pointC = new Point2D(0, 0);
        pointLeft = new Point2D(0, 0);
        pointRight = new Point2D(0, 0);
        pointTop = new Point2D(0, 0);
//        startPoint = new Point2D(0, 0);
//        endPoint = new Point2D(0, 0);
    }

    public void setProperty(Point2D startPoint, Point2D endPoint) {
        pointA = startPoint;
        pointB.setCoord((endPoint.coordX - startPoint.coordX) / 3 * 2, pointA.coordY);
        pointC.setCoord(pointA.coordX, endPoint.coordY + (startPoint.coordY - endPoint.coordY) / 3);
        pointD.setCoord(pointB.coordX, pointC.coordY);
        pointLeft.setCoord(pointC.coordX - (endPoint.coordX - pointD.coordX), pointC.coordY);
        pointRight.setCoord(pointD.coordX + (endPoint.coordX - pointD.coordX), pointD.coordY);
        pointTop.setCoord((pointA.coordX + pointB.coordX) / 2, endPoint.coordY);
        centerPoint.setCoord(pointTop.coordX, pointTop.coordY + (pointA.coordY - pointTop.coordY) / 2);
    }

    @Override
    public void drawOutline() {
        drawSegment(pointA, pointB);
        drawSegment(pointA, pointC);
        drawSegment(pointB, pointD);
        drawSegment(pointLeft, pointC);
        drawSegment(pointD, pointRight);
        drawSegment(pointLeft, pointTop);
        drawSegment(pointRight, pointTop);
    }

    @Override
    public void applyMove(Vector2D vector) {
        pointA.rotate(rotatedAngle).move(vector);
        pointB.rotate(rotatedAngle).move(vector);
        pointC.rotate(rotatedAngle).move(vector);
        pointD.rotate(rotatedAngle).move(vector);
        pointLeft.rotate(rotatedAngle).move(vector);
        pointRight.rotate(rotatedAngle).move(vector);
        pointTop.rotate(rotatedAngle).move(vector);
        centerPoint.move(vector);
    }

    @Override
    public void drawOXSymmetry() {
        Point2D tempPointA = pointA.createRotationPoint(centerPoint, rotatedAngle).symOx();
        Point2D tempPointB = pointB.createRotationPoint(centerPoint, rotatedAngle).symOx();
        Point2D tempPointC = pointC.createRotationPoint(centerPoint, rotatedAngle).symOx();
        Point2D tempPointD = pointD.createRotationPoint(centerPoint, rotatedAngle).symOx();
        Point2D tempPointLeft = pointLeft.createRotationPoint(centerPoint, rotatedAngle).symOx();
        Point2D tempPointRight = pointRight.createRotationPoint(centerPoint, rotatedAngle).symOx();
        Point2D tempPointTop = pointTop.createRotationPoint(centerPoint, rotatedAngle).symOx();
        
        drawSegment(tempPointA, tempPointB);
        drawSegment(tempPointA, tempPointC);
        drawSegment(tempPointB, tempPointD);
        drawSegment(tempPointLeft, tempPointC);
        drawSegment(tempPointD, tempPointRight);
        drawSegment(tempPointLeft, tempPointTop);
        drawSegment(tempPointRight, tempPointTop);
    }

    @Override
    public void drawOYSymmetry() {
        Point2D tempPointA = pointA.createRotationPoint(centerPoint, rotatedAngle).symOy();
        Point2D tempPointB = pointB.createRotationPoint(centerPoint, rotatedAngle).symOy();
        Point2D tempPointC = pointC.createRotationPoint(centerPoint, rotatedAngle).symOy();
        Point2D tempPointD = pointD.createRotationPoint(centerPoint, rotatedAngle).symOy();
        Point2D tempPointLeft = pointLeft.createRotationPoint(centerPoint, rotatedAngle).symOy();
        Point2D tempPointRight = pointRight.createRotationPoint(centerPoint, rotatedAngle).symOy();
        Point2D tempPointTop = pointTop.createRotationPoint(centerPoint, rotatedAngle).symOy();
        
        drawSegment(tempPointA, tempPointB);
        drawSegment(tempPointA, tempPointC);
        drawSegment(tempPointB, tempPointD);
        drawSegment(tempPointLeft, tempPointC);
        drawSegment(tempPointD, tempPointRight);
        drawSegment(tempPointLeft, tempPointTop);
        drawSegment(tempPointRight, tempPointTop);        
    }

    @Override
    public void drawPointSymmetry(Point2D basePoint) {
        Point2D tempPointA = pointA.createRotationPoint(centerPoint, rotatedAngle).symPoint(basePoint);
        Point2D tempPointB = pointB.createRotationPoint(centerPoint, rotatedAngle).symPoint(basePoint);
        Point2D tempPointC = pointC.createRotationPoint(centerPoint, rotatedAngle).symPoint(basePoint);
        Point2D tempPointD = pointD.createRotationPoint(centerPoint, rotatedAngle).symPoint(basePoint);
        Point2D tempPointLeft = pointLeft.createRotationPoint(centerPoint, rotatedAngle).symPoint(basePoint);
        Point2D tempPointRight = pointRight.createRotationPoint(centerPoint, rotatedAngle).symPoint(basePoint);
        Point2D tempPointTop = pointTop.createRotationPoint(centerPoint, rotatedAngle).symPoint(basePoint);
        
        drawSegment(tempPointA, tempPointB);
        drawSegment(tempPointA, tempPointC);
        drawSegment(tempPointB, tempPointD);
        drawSegment(tempPointLeft, tempPointC);
        drawSegment(tempPointD, tempPointRight);
        drawSegment(tempPointLeft, tempPointTop);
        drawSegment(tempPointRight, tempPointTop);          
    }

    @Override
    public void drawVirtualMove(Vector2D vector) {
        Point2D tempPointA = pointA.createRotationPoint(centerPoint, rotatedAngle).move(vector);
        Point2D tempPointB = pointB.createRotationPoint(centerPoint, rotatedAngle).move(vector);
        Point2D tempPointC = pointC.createRotationPoint(centerPoint, rotatedAngle).move(vector);
        Point2D tempPointD = pointD.createRotationPoint(centerPoint, rotatedAngle).move(vector);
        Point2D tempPointLeft = pointLeft.createRotationPoint(centerPoint, rotatedAngle).move(vector);
        Point2D tempPointRight = pointRight.createRotationPoint(centerPoint, rotatedAngle).move(vector);
        Point2D tempPointTop = pointTop.createRotationPoint(centerPoint, rotatedAngle).move(vector);
        
        drawSegment(tempPointA, tempPointB);
        drawSegment(tempPointA, tempPointC);
        drawSegment(tempPointB, tempPointD);
        drawSegment(tempPointLeft, tempPointC);
        drawSegment(tempPointD, tempPointRight);
        drawSegment(tempPointLeft, tempPointTop);
        drawSegment(tempPointRight, tempPointTop);            
    }

    @Override
    public void drawVirtualRotation(double angle) {
        double totalAngle = this.rotatedAngle + angle;
        
        Point2D tempPointA = pointA.createRotationPoint(centerPoint, rotatedAngle).rotate(centerPoint, totalAngle);
        Point2D tempPointB = pointB.createRotationPoint(centerPoint, rotatedAngle).rotate(centerPoint, totalAngle);
        Point2D tempPointC = pointC.createRotationPoint(centerPoint, rotatedAngle).rotate(centerPoint, totalAngle);
        Point2D tempPointD = pointD.createRotationPoint(centerPoint, rotatedAngle).rotate(centerPoint, totalAngle);
        Point2D tempPointLeft = pointLeft.createRotationPoint(centerPoint, rotatedAngle).rotate(centerPoint, totalAngle);
        Point2D tempPointRight = pointRight.createRotationPoint(centerPoint, rotatedAngle).rotate(centerPoint, totalAngle);
        Point2D tempPointTop = pointTop.createRotationPoint(centerPoint, rotatedAngle).rotate(centerPoint, totalAngle);
        
        drawSegment(tempPointA, tempPointB);
        drawSegment(tempPointA, tempPointC);
        drawSegment(tempPointB, tempPointD);
        drawSegment(tempPointLeft, tempPointC);
        drawSegment(tempPointD, tempPointRight);
        drawSegment(tempPointLeft, tempPointTop);
        drawSegment(tempPointRight, tempPointTop);             
    }

    @Override
    public void savePointCoordinate(int coordX, int coordY) {
        pointA.saveCoord(changedCoordOfBoard);
        pointB.saveCoord(changedCoordOfBoard);
        pointC.saveCoord(changedCoordOfBoard);
        pointD.saveCoord(changedCoordOfBoard);
        pointLeft.saveCoord(changedCoordOfBoard);
        pointRight.saveCoord(changedCoordOfBoard);
        pointTop.saveCoord(changedCoordOfBoard);
    }
}
