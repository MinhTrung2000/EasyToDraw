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

        pointB.setCoord(startPoint.coordX, startPoint.coordY + 3 * dy);

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
    public void saveCoordinates() {
        pointA.saveCoord(changedCoordOfBoard);
        pointB.saveCoord(changedCoordOfBoard);
        pointC.saveCoord(changedCoordOfBoard);
        pointD.saveCoord(changedCoordOfBoard);
        pointE.saveCoord(changedCoordOfBoard);
        pointF.saveCoord(changedCoordOfBoard);
        pointG.saveCoord(changedCoordOfBoard);
    }
}
