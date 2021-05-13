package model.shape2d;

import control.myawt.SKPoint2D;
import java.awt.Color;

public class Star extends Shape2D {

    // Five top points
    private SKPoint2D pointA = new SKPoint2D();
    private SKPoint2D pointB = new SKPoint2D();
    private SKPoint2D pointC = new SKPoint2D();
    private SKPoint2D pointD = new SKPoint2D();
    private SKPoint2D pointE = new SKPoint2D();

    // Five oppsite points of above
    private SKPoint2D opPointA = new SKPoint2D();
    private SKPoint2D opPointB = new SKPoint2D();
    private SKPoint2D opPointC = new SKPoint2D();
    private SKPoint2D opPointD = new SKPoint2D();
    private SKPoint2D opPointE = new SKPoint2D();

    public Star(boolean[][] markedChangeOfBoard, Color[][] changedColorOfBoard, String[][] changedCoordOfBoard, Color filledColor) {
        super(markedChangeOfBoard, changedColorOfBoard, changedCoordOfBoard, filledColor);
    }

    public void setProperty(SKPoint2D startPoint, SKPoint2D endPoint) {
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

        this.startPoint2D.setLocation(startPoint);

        this.endPoint2D.setLocation(
                startPoint.getCoordX() + widthDirection * preferedLength,
                startPoint.getCoordY() + heightDirection * preferedLength
        );

        centerPoint2D.setMidLocation(this.startPoint2D, this.endPoint2D);

        if ((widthDirection == 1 && heightDirection == 1)
                || (widthDirection == -1 && heightDirection == 1)) {
            pointA.setLocation(centerPoint2D.getCoordX(),
                    this.startPoint2D.getCoordY());
        } else {
            pointA.setLocation(centerPoint2D.getCoordX(),
                    this.endPoint2D.getCoordY());
        }

        opPointA.setLocation(pointA.getCoordX(), pointA.getCoordY()
                + (int) ((centerPoint2D.getCoordY() - pointA.getCoordY()) * 3 / 2));

        pointB = Transform2D.getTransformPoint(pointA, Transform2D.getRotateFromPointMat(centerPoint2D, Math.toRadians(72)));
        opPointB = Transform2D.getTransformPoint(opPointA, Transform2D.getRotateFromPointMat(centerPoint2D, Math.toRadians(72)));

        pointC = Transform2D.getTransformPoint(pointB, Transform2D.getRotateFromPointMat(centerPoint2D, Math.toRadians(72)));
        opPointC = Transform2D.getTransformPoint(opPointB, Transform2D.getRotateFromPointMat(centerPoint2D, Math.toRadians(72)));

        pointD = Transform2D.getTransformPoint(pointC, Transform2D.getRotateFromPointMat(centerPoint2D, Math.toRadians(72)));
        opPointD = Transform2D.getTransformPoint(opPointC, Transform2D.getRotateFromPointMat(centerPoint2D, Math.toRadians(72)));

        pointE = Transform2D.getTransformPoint(pointD, Transform2D.getRotateFromPointMat(centerPoint2D, Math.toRadians(72)));
        opPointE = Transform2D.getTransformPoint(opPointD, Transform2D.getRotateFromPointMat(centerPoint2D, Math.toRadians(72)));
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

        centerPoint2D.move(vector);
    }

    @Override
    public void saveCoordinates() {
        pointA.saveCoord(changedCoordOfBoard);
        pointB.saveCoord(changedCoordOfBoard);
        pointC.saveCoord(changedCoordOfBoard);
        pointD.saveCoord(changedCoordOfBoard);
        pointE.saveCoord(changedCoordOfBoard);
    }

    @Override
    public void createRotate(SKPoint2D centerPoint, double angle) {
        if (pointSet2D.isEmpty()) {
            return;
        }

        double totalAngle = rotatedAngle + angle;

        SKPoint2D newPointA = pointA.createRotate(centerPoint, totalAngle);
        SKPoint2D newPointB = pointB.createRotate(centerPoint, totalAngle);
        SKPoint2D newPointC = pointC.createRotate(centerPoint, totalAngle);
        SKPoint2D newPointD = pointD.createRotate(centerPoint, totalAngle);
        SKPoint2D newPointE = pointE.createRotate(centerPoint, totalAngle);

        SKPoint2D newOpPointA = opPointA.createRotate(centerPoint, totalAngle);
        SKPoint2D newOpPointB = opPointB.createRotate(centerPoint, totalAngle);
        SKPoint2D newOpPointC = opPointC.createRotate(centerPoint, totalAngle);
        SKPoint2D newOpPointD = opPointD.createRotate(centerPoint, totalAngle);
        SKPoint2D newOpPointE = opPointE.createRotate(centerPoint, totalAngle);

        drawSegmentUnSave(newPointA, newOpPointD);
        drawSegmentUnSave(newOpPointD, newPointB);
        drawSegmentUnSave(newPointB, newOpPointE);
        drawSegmentUnSave(newOpPointE, newPointC);
        drawSegmentUnSave(newPointC, newOpPointA);
        drawSegmentUnSave(newOpPointA, newPointD);
        drawSegmentUnSave(newPointD, newOpPointB);
        drawSegmentUnSave(newOpPointB, newPointE);
        drawSegmentUnSave(newPointE, newOpPointC);
        drawSegmentUnSave(newOpPointC, newPointA);

        newPointA.saveCoord(changedCoordOfBoard);
        newPointB.saveCoord(changedCoordOfBoard);
        newPointC.saveCoord(changedCoordOfBoard);
        newPointD.saveCoord(changedCoordOfBoard);
        newPointE.saveCoord(changedCoordOfBoard);
    }

    @Override
    public void createSymOCenter() {
        super.createSymOCenter();

        SKPoint2D newPointA = pointA.createOCenterSym();
        SKPoint2D newPointB = pointB.createOCenterSym();
        SKPoint2D newPointC = pointC.createOCenterSym();
        SKPoint2D newPointD = pointD.createOCenterSym();
        SKPoint2D newPointE = pointE.createOCenterSym();

        newPointA.saveCoord(changedCoordOfBoard);
        newPointB.saveCoord(changedCoordOfBoard);
        newPointC.saveCoord(changedCoordOfBoard);
        newPointD.saveCoord(changedCoordOfBoard);
        newPointE.saveCoord(changedCoordOfBoard);
    }

    @Override
    public void createSymOX() {
        super.createSymOX();

        SKPoint2D newPointA = pointA.createOXSym();
        SKPoint2D newPointB = pointB.createOXSym();
        SKPoint2D newPointC = pointC.createOXSym();
        SKPoint2D newPointD = pointD.createOXSym();
        SKPoint2D newPointE = pointE.createOXSym();

        newPointA.saveCoord(changedCoordOfBoard);
        newPointB.saveCoord(changedCoordOfBoard);
        newPointC.saveCoord(changedCoordOfBoard);
        newPointD.saveCoord(changedCoordOfBoard);
        newPointE.saveCoord(changedCoordOfBoard);
    }

    @Override
    public void createSymOY() {
        super.createSymOY();

        SKPoint2D newPointA = pointA.createOYSym();
        SKPoint2D newPointB = pointB.createOYSym();
        SKPoint2D newPointC = pointC.createOYSym();
        SKPoint2D newPointD = pointD.createOYSym();
        SKPoint2D newPointE = pointE.createOYSym();

        newPointA.saveCoord(changedCoordOfBoard);
        newPointB.saveCoord(changedCoordOfBoard);
        newPointC.saveCoord(changedCoordOfBoard);
        newPointD.saveCoord(changedCoordOfBoard);
        newPointE.saveCoord(changedCoordOfBoard);
    }

    @Override
    public void createSymPoint(SKPoint2D basePoint) {
        super.createSymPoint(basePoint);

        SKPoint2D newPointA = pointA.createPointSym(basePoint);
        SKPoint2D newPointB = pointB.createPointSym(basePoint);
        SKPoint2D newPointC = pointC.createPointSym(basePoint);
        SKPoint2D newPointD = pointD.createPointSym(basePoint);
        SKPoint2D newPointE = pointE.createPointSym(basePoint);

        newPointA.saveCoord(changedCoordOfBoard);
        newPointB.saveCoord(changedCoordOfBoard);
        newPointC.saveCoord(changedCoordOfBoard);
        newPointD.saveCoord(changedCoordOfBoard);
        newPointE.saveCoord(changedCoordOfBoard);
    }

    @Override
    public void createSymLine(double a, double b, double c) {
        super.createSymLine(a, b, c);

        SKPoint2D newPointA = pointA.createLineSym(a, b, c);
        SKPoint2D newPointB = pointB.createLineSym(a, b, c);
        SKPoint2D newPointC = pointC.createLineSym(a, b, c);
        SKPoint2D newPointD = pointD.createLineSym(a, b, c);
        SKPoint2D newPointE = pointE.createLineSym(a, b, c);

        newPointA.saveCoord(changedCoordOfBoard);
        newPointB.saveCoord(changedCoordOfBoard);
        newPointC.saveCoord(changedCoordOfBoard);
        newPointD.saveCoord(changedCoordOfBoard);
        newPointE.saveCoord(changedCoordOfBoard);
    }

}
