package model.shape2d;

import java.awt.Color;

public class Triangle extends Shape2D {

    private Point2D pointA;
    private Point2D pointB;
    private Point2D pointC;

    public enum Modal {
        COMMON_TRIANGLE,
        EQUILATERAL_TRIANGLE,
    }

    public Triangle(boolean[][] markedChangeOfBoard, Color[][] changedColorOfBoard, String[][] changedCoordOfBoard, Color filledColor) {
        super(markedChangeOfBoard, changedColorOfBoard, changedCoordOfBoard, filledColor);
        pointA = new Point2D(-1, -1);
        pointB = new Point2D(-1, -1);
        pointC = new Point2D(-1, -1);
    }

    /**
     * Set 3 points of triangle by using startPoint and endPoint The type of
     * triangle is either common or equilateral depending on Modal
     *
     * @param startPoint
     * @param endPoint
     */
    public void setProperty(Point2D startPoint, Point2D endPoint, Modal modal) {
        int width = endPoint.getCoordX() - startPoint.getCoordX();
        int height = endPoint.getCoordY() - startPoint.getCoordY();
        
        int adjustingValue;
        int widthDirection = this.getWidthDirection(width);
        int heightDirection = this.getHeightDirection(height);
    
        if (modal == Modal.COMMON_TRIANGLE) {
            this.startPoint=startPoint;
            this.endPoint=endPoint;
            adjustingValue =0;
        }else {
            adjustingValue = -widthDirection; // giá trị để dịch 1 pixel có hướng, căn chỉnh lại cho đều 2 cạnh của tam giác đều

            int preferedLength = this.getPreferredLength(width, height);
            
            
            this.startPoint=startPoint;
            this.endPoint.setCoord(this.startPoint.getCoordX()+widthDirection*preferedLength,this.startPoint.getCoordY()+heightDirection*preferedLength);
            
        }

            int Ay, By, Cy;
            if(this.endPoint.getCoordY()< this.startPoint.getCoordY()){
              Ay=this.endPoint.getCoordY();
              By=this.startPoint.getCoordY();
              Cy=this.startPoint.getCoordY();
            }else {
              Ay=this.startPoint.getCoordY();
              By=this.endPoint.getCoordY();
              Cy=this.endPoint.getCoordY();
            } 
            
            pointA.setCoord(((this.startPoint.coordX + this.endPoint.coordX) / 2), Ay);
            pointB.setCoord(this.startPoint.getCoordX(), By);
            pointC.setCoord(this.endPoint.getCoordX()+adjustingValue,Cy);
            
            centerPoint.setCoord(
                pointA.coordX,
                pointC.coordY + (int) ((2.0 / 3.0) * Math.abs(pointC.coordY - pointA.coordY))
        );
    }

    @Override
    public void setProperty(Point2D startPoint, Point2D endPoint) {
        setProperty(startPoint, endPoint, Modal.COMMON_TRIANGLE);
    }

    @Override
    public void saveCoordinates() {
        pointA.saveCoord(this.changedCoordOfBoard);
        pointB.saveCoord(this.changedCoordOfBoard);
        pointC.saveCoord(this.changedCoordOfBoard);
    }

    /**
     * Drawing in board.
     */
    @Override
    public void drawOutline() {
        Point2D tempPointA = pointA.createRotationPoint(centerPoint, this.rotatedAngle);
        Point2D tempPointB = pointB.createRotationPoint(centerPoint, this.rotatedAngle);
        Point2D tempPointC = pointC.createRotationPoint(centerPoint, this.rotatedAngle);

        drawSegment(tempPointA, tempPointB);
        drawSegment(tempPointB, tempPointC);
        drawSegment(tempPointC, tempPointA);
    }

    @Override
    public void applyMove(Vector2D vector) {
        pointA.rotate(centerPoint, this.rotatedAngle).move(vector);
        pointB.rotate(centerPoint, this.rotatedAngle).move(vector);
        pointC.rotate(centerPoint, this.rotatedAngle).move(vector);

        centerPoint.move(vector);
    }
}
