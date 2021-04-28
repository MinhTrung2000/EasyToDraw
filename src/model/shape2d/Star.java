package model.shape2d;

import java.awt.Color;

public class Star extends Shape2D {

    // Five top points
    private Point2D pointA;
    private Point2D pointB;
    private Point2D pointC;
    private Point2D pointD;
    private Point2D pointE;

    // File oppsite points of above
    private Point2D opPointA;
    private Point2D opPointB;
    private Point2D opPointC;
    private Point2D opPointD;
    private Point2D opPointE;

    public Star(boolean[][] markedChangeOfBoard, Color[][] changedColorOfBoard, String[][] changedCoordOfBoard, Color filledColor) {
        super(markedChangeOfBoard, changedColorOfBoard, changedCoordOfBoard, filledColor);

        pointA = new Point2D();
        pointB = new Point2D();
        pointC = new Point2D();
        pointD = new Point2D();
        pointE = new Point2D();

        opPointA = new Point2D();
        opPointB = new Point2D();
        opPointC = new Point2D();
        opPointD = new Point2D();
        opPointE = new Point2D();
    }

    public void setProperty(Point2D startPoint, Point2D endPoint) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        
        int width = endPoint.coordX - startPoint.coordX;
        int height = endPoint.coordY - startPoint.coordY;
        
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
        this.endPoint.setCoord(startPoint.getCoordX() + widthDirection * preferedLength, startPoint.getCoordY() + heightDirection * preferedLength);
        
        centerPoint = Point2D.midPoint(this.startPoint, this.endPoint);
        if(widthDirection == 1 && heightDirection == 1 || widthDirection == -1 && heightDirection ==1){
            pointA.setCoord(centerPoint.coordX, this.startPoint.coordY);
        }else{
            pointA.setCoord(centerPoint.coordX, this.endPoint.coordY);
        }
        
        opPointA.setCoord(pointA.coordX, pointA.coordY + (int) ((centerPoint.coordY - pointA.coordY) * 3 / 2));

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
    public void drawOXSymmetry() {
        Point2D tempPointA = pointA.createRotationPoint(centerPoint, rotatedAngle).symOx();
        Point2D tempPointB = pointB.createRotationPoint(centerPoint, rotatedAngle).symOx();
        Point2D tempPointC = pointC.createRotationPoint(centerPoint, rotatedAngle).symOx();
        Point2D tempPointD = pointD.createRotationPoint(centerPoint, rotatedAngle).symOx();
        Point2D tempPointE = pointE.createRotationPoint(centerPoint, rotatedAngle).symOx();

        Point2D tempOpPointA = opPointA.createRotationPoint(centerPoint, rotatedAngle).symOx();
        Point2D tempOpPointB = opPointB.createRotationPoint(centerPoint, rotatedAngle).symOx();
        Point2D tempOpPointC = opPointC.createRotationPoint(centerPoint, rotatedAngle).symOx();
        Point2D tempOpPointD = opPointD.createRotationPoint(centerPoint, rotatedAngle).symOx();
        Point2D tempOpPointE = opPointE.createRotationPoint(centerPoint, rotatedAngle).symOx();

        drawSegment(tempPointA, tempOpPointD);
        drawSegment(tempOpPointD, tempPointB);
        drawSegment(tempPointB, tempOpPointE);
        drawSegment(tempOpPointE, tempPointC);
        drawSegment(tempPointC, tempOpPointA);
        drawSegment(tempOpPointA, tempPointD);
        drawSegment(tempPointD, tempOpPointB);
        drawSegment(tempOpPointB, tempPointE);
        drawSegment(tempPointE, tempOpPointC);
        drawSegment(tempOpPointC, tempPointA);
    }

    @Override
    public void drawOYSymmetry() {
        Point2D tempPointA = pointA.createRotationPoint(centerPoint, rotatedAngle).symOy();
        Point2D tempPointB = pointB.createRotationPoint(centerPoint, rotatedAngle).symOy();
        Point2D tempPointC = pointC.createRotationPoint(centerPoint, rotatedAngle).symOy();
        Point2D tempPointD = pointD.createRotationPoint(centerPoint, rotatedAngle).symOy();
        Point2D tempPointE = pointE.createRotationPoint(centerPoint, rotatedAngle).symOy();

        Point2D tempOpPointA = opPointA.createRotationPoint(centerPoint, rotatedAngle).symOy();
        Point2D tempOpPointB = opPointB.createRotationPoint(centerPoint, rotatedAngle).symOy();
        Point2D tempOpPointC = opPointC.createRotationPoint(centerPoint, rotatedAngle).symOy();
        Point2D tempOpPointD = opPointD.createRotationPoint(centerPoint, rotatedAngle).symOy();
        Point2D tempOpPointE = opPointE.createRotationPoint(centerPoint, rotatedAngle).symOy();

        drawSegment(tempPointA, tempOpPointD);
        drawSegment(tempOpPointD, tempPointB);
        drawSegment(tempPointB, tempOpPointE);
        drawSegment(tempOpPointE, tempPointC);
        drawSegment(tempPointC, tempOpPointA);
        drawSegment(tempOpPointA, tempPointD);
        drawSegment(tempPointD, tempOpPointB);
        drawSegment(tempOpPointB, tempPointE);
        drawSegment(tempPointE, tempOpPointC);
        drawSegment(tempOpPointC, tempPointA);
    }

    @Override
    public void drawPointSymmetry(Point2D basePoint) {
        Point2D tempPointA = pointA.createRotationPoint(centerPoint, rotatedAngle).symPoint(basePoint);
        Point2D tempPointB = pointB.createRotationPoint(centerPoint, rotatedAngle).symPoint(basePoint);
        Point2D tempPointC = pointC.createRotationPoint(centerPoint, rotatedAngle).symPoint(basePoint);
        Point2D tempPointD = pointD.createRotationPoint(centerPoint, rotatedAngle).symPoint(basePoint);
        Point2D tempPointE = pointE.createRotationPoint(centerPoint, rotatedAngle).symPoint(basePoint);

        Point2D tempOpPointA = opPointA.createRotationPoint(centerPoint, rotatedAngle).symPoint(basePoint);
        Point2D tempOpPointB = opPointB.createRotationPoint(centerPoint, rotatedAngle).symPoint(basePoint);
        Point2D tempOpPointC = opPointC.createRotationPoint(centerPoint, rotatedAngle).symPoint(basePoint);
        Point2D tempOpPointD = opPointD.createRotationPoint(centerPoint, rotatedAngle).symPoint(basePoint);
        Point2D tempOpPointE = opPointE.createRotationPoint(centerPoint, rotatedAngle).symPoint(basePoint);

        drawSegment(tempPointA, tempOpPointD);
        drawSegment(tempOpPointD, tempPointB);
        drawSegment(tempPointB, tempOpPointE);
        drawSegment(tempOpPointE, tempPointC);
        drawSegment(tempPointC, tempOpPointA);
        drawSegment(tempOpPointA, tempPointD);
        drawSegment(tempPointD, tempOpPointB);
        drawSegment(tempOpPointB, tempPointE);
        drawSegment(tempPointE, tempOpPointC);
        drawSegment(tempOpPointC, tempPointA);
    }

    @Override
    public void drawVirtualMove(Vector2D vector) {
        Point2D tempPointA = pointA.createRotationPoint(centerPoint, rotatedAngle).move(vector);
        Point2D tempPointB = pointB.createRotationPoint(centerPoint, rotatedAngle).move(vector);
        Point2D tempPointC = pointC.createRotationPoint(centerPoint, rotatedAngle).move(vector);
        Point2D tempPointD = pointD.createRotationPoint(centerPoint, rotatedAngle).move(vector);
        Point2D tempPointE = pointE.createRotationPoint(centerPoint, rotatedAngle).move(vector);

        Point2D tempOpPointA = opPointA.createRotationPoint(centerPoint, rotatedAngle).move(vector);
        Point2D tempOpPointB = opPointB.createRotationPoint(centerPoint, rotatedAngle).move(vector);
        Point2D tempOpPointC = opPointC.createRotationPoint(centerPoint, rotatedAngle).move(vector);
        Point2D tempOpPointD = opPointD.createRotationPoint(centerPoint, rotatedAngle).move(vector);
        Point2D tempOpPointE = opPointE.createRotationPoint(centerPoint, rotatedAngle).move(vector);

        drawSegment(tempPointA, tempOpPointD);
        drawSegment(tempOpPointD, tempPointB);
        drawSegment(tempPointB, tempOpPointE);
        drawSegment(tempOpPointE, tempPointC);
        drawSegment(tempPointC, tempOpPointA);
        drawSegment(tempOpPointA, tempPointD);
        drawSegment(tempPointD, tempOpPointB);
        drawSegment(tempOpPointB, tempPointE);
        drawSegment(tempPointE, tempOpPointC);
        drawSegment(tempOpPointC, tempPointA);
    }

    @Override
    public void drawVirtualRotation(double angle) {
        double totalAngle = this.rotatedAngle + angle;

        Point2D tempPointA = pointA.createRotationPoint(centerPoint, totalAngle);
        Point2D tempPointB = pointB.createRotationPoint(centerPoint, totalAngle);
        Point2D tempPointC = pointC.createRotationPoint(centerPoint, totalAngle);
        Point2D tempPointD = pointD.createRotationPoint(centerPoint, totalAngle);
        Point2D tempPointE = pointE.createRotationPoint(centerPoint, totalAngle);

        Point2D tempOpPointA = opPointA.createRotationPoint(centerPoint, totalAngle);
        Point2D tempOpPointB = opPointB.createRotationPoint(centerPoint, totalAngle);
        Point2D tempOpPointC = opPointC.createRotationPoint(centerPoint, totalAngle);
        Point2D tempOpPointD = opPointD.createRotationPoint(centerPoint, totalAngle);
        Point2D tempOpPointE = opPointE.createRotationPoint(centerPoint, totalAngle);

        drawSegment(tempPointA, tempOpPointD);
        drawSegment(tempOpPointD, tempPointB);
        drawSegment(tempPointB, tempOpPointE);
        drawSegment(tempOpPointE, tempPointC);
        drawSegment(tempPointC, tempOpPointA);
        drawSegment(tempOpPointA, tempPointD);
        drawSegment(tempPointD, tempOpPointB);
        drawSegment(tempOpPointB, tempPointE);
        drawSegment(tempPointE, tempOpPointC);
        drawSegment(tempOpPointC, tempPointA);
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
    public void drawLineSymmetry(double a, double b, double c) {
        Point2D tempPointA = pointA.createRotationPoint(centerPoint, rotatedAngle).symLine(a, b, c);
        Point2D tempPointB = pointB.createRotationPoint(centerPoint, rotatedAngle).symLine(a, b, c);
        Point2D tempPointC = pointC.createRotationPoint(centerPoint, rotatedAngle).symLine(a, b, c);
        Point2D tempPointD = pointD.createRotationPoint(centerPoint, rotatedAngle).symLine(a, b, c);
        Point2D tempPointE = pointE.createRotationPoint(centerPoint, rotatedAngle).symLine(a, b, c);

        Point2D tempOpPointA = opPointA.createRotationPoint(centerPoint, rotatedAngle).symLine(a, b, c);
        Point2D tempOpPointB = opPointB.createRotationPoint(centerPoint, rotatedAngle).symLine(a, b, c);
        Point2D tempOpPointC = opPointC.createRotationPoint(centerPoint, rotatedAngle).symLine(a, b, c);
        Point2D tempOpPointD = opPointD.createRotationPoint(centerPoint, rotatedAngle).symLine(a, b, c);
        Point2D tempOpPointE = opPointE.createRotationPoint(centerPoint, rotatedAngle).symLine(a, b, c);

        drawSegment(tempPointA, tempOpPointD);
        drawSegment(tempOpPointD, tempPointB);
        drawSegment(tempPointB, tempOpPointE);
        drawSegment(tempOpPointE, tempPointC);
        drawSegment(tempPointC, tempOpPointA);
        drawSegment(tempOpPointA, tempPointD);
        drawSegment(tempPointD, tempOpPointB);
        drawSegment(tempOpPointB, tempPointE);
        drawSegment(tempPointE, tempOpPointC);
        drawSegment(tempOpPointC, tempPointA);
    }
}
