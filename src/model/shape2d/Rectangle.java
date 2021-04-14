package model.shape2d;

import java.awt.Color;

public class Rectangle extends Shape2D {

    private Point2D leftTopPoint;
    private Point2D rightTopPoint;
    private Point2D leftBottomPoint;
    private Point2D rightBottomPoint;

    public Rectangle(boolean[][] markedChangeOfBoard, Color[][] changedColorOfBoard, String[][] changedCoordOfBoard, Color filledColor) {
        super(markedChangeOfBoard, changedColorOfBoard, changedCoordOfBoard, filledColor);
        
        leftTopPoint = new Point2D(0, 0);
        rightTopPoint = new Point2D(0, 0);
        leftBottomPoint = new Point2D(0, 0);
        rightBottomPoint = new Point2D(0, 0);
    }


    public void saveCoordinate() {
        leftTopPoint.createRotationPoint(centerPoint, rotatedAngle).saveCoord(this.changedCoordOfBoard);
        rightTopPoint.createRotationPoint(centerPoint, rotatedAngle).saveCoord(this.changedCoordOfBoard);
        leftBottomPoint.createRotationPoint(centerPoint, rotatedAngle).saveCoord(this.changedCoordOfBoard);
        rightBottomPoint.createRotationPoint(centerPoint, rotatedAngle).saveCoord(this.changedCoordOfBoard);
    }

    public void setProperty(Point2D startPoint, Point2D endPoint) {
        leftTopPoint.setCoord(startPoint);
        rightBottomPoint.setCoord(endPoint);

        leftBottomPoint.setCoord(leftTopPoint.coordX, rightBottomPoint.coordY);
        rightTopPoint.setCoord(rightBottomPoint.coordX, leftTopPoint.coordY);

        centerPoint.setCoord(
                (int) (leftTopPoint.coordX + rightTopPoint.coordX) / 2,
                (int) (leftTopPoint.coordY + leftBottomPoint.coordY) / 2
        );
    }

    @Override
    public void drawVirtualRotation(double angle) {
        double totalAngle = this.rotatedAngle + angle;

        Point2D tempLeftTopPoint = leftTopPoint.createRotationPoint(centerPoint, totalAngle);
        Point2D tempRightTopPoint = rightTopPoint.createRotationPoint(centerPoint, totalAngle);
        Point2D tempLeftBottomPoint = leftBottomPoint.createRotationPoint(centerPoint, totalAngle);
        Point2D tempRightBottomPoint = rightBottomPoint.createRotationPoint(centerPoint, totalAngle);

        drawSegment(tempLeftTopPoint, tempRightTopPoint, lineStyle);
        drawSegment(tempRightTopPoint, tempLeftBottomPoint, lineStyle);
        drawSegment(tempLeftBottomPoint, tempRightBottomPoint, lineStyle);
        drawSegment(tempRightBottomPoint, tempLeftTopPoint, lineStyle);
    }

    @Override
    public void drawVirtualMove(Vector2D vector) {
        Point2D tempLeftTopPoint = leftTopPoint.createRotationPoint(centerPoint, rotatedAngle).move(vector);
        Point2D tempRightTopPoint = rightTopPoint.createRotationPoint(centerPoint, rotatedAngle).move(vector);
        Point2D tempLeftBottomPoint = leftBottomPoint.createRotationPoint(centerPoint, rotatedAngle).move(vector);
        Point2D tempRightBottomPoint = rightBottomPoint.createRotationPoint(centerPoint, rotatedAngle).move(vector);

        drawSegment(tempLeftTopPoint, tempRightTopPoint, lineStyle);
        drawSegment(tempRightTopPoint, tempLeftBottomPoint, lineStyle);
        drawSegment(tempLeftBottomPoint, tempRightBottomPoint, lineStyle);
        drawSegment(tempRightBottomPoint, tempLeftTopPoint, lineStyle);
    }
    
    @Override
    public void applyMove(Vector2D vector) {
        leftTopPoint.move(vector);
        rightTopPoint.move(vector);
        leftBottomPoint.move(vector);
        rightBottomPoint.move(vector);
        
        centerPoint.move(vector);
    }
    
    @Override
    public void drawOXSymmetry() {
        Point2D tempLeftTopPoint = leftTopPoint.createOXSymmetryPoint();
        Point2D tempRightTopPoint = rightTopPoint.createOXSymmetryPoint();
        Point2D tempLeftBottomPoint = leftBottomPoint.createOXSymmetryPoint();
        Point2D tempRightBottomPoint = rightBottomPoint.createOXSymmetryPoint();
        
        drawSegment(tempLeftTopPoint, tempRightTopPoint, lineStyle);
        drawSegment(tempRightTopPoint, tempLeftBottomPoint, lineStyle);
        drawSegment(tempLeftBottomPoint, tempRightBottomPoint, lineStyle);
        drawSegment(tempRightBottomPoint, tempLeftTopPoint, lineStyle);
    }
    
    @Override
    public void drawOYSymmetry() {
        Point2D tempLeftTopPoint = leftTopPoint.createOYSymmetryPoint();
        Point2D tempRightTopPoint = rightTopPoint.createOYSymmetryPoint();
        Point2D tempLeftBottomPoint = leftBottomPoint.createOYSymmetryPoint();
        Point2D tempRightBottomPoint = rightBottomPoint.createOYSymmetryPoint();
        
        drawSegment(tempLeftTopPoint, tempRightTopPoint, lineStyle);
        drawSegment(tempRightTopPoint, tempLeftBottomPoint, lineStyle);
        drawSegment(tempLeftBottomPoint, tempRightBottomPoint, lineStyle);
        drawSegment(tempRightBottomPoint, tempLeftTopPoint, lineStyle);
    }
    
    @Override
    public void drawPointSymmetry(Point2D point) {
        Point2D tempLeftTopPoint = leftTopPoint.createSymmetryPoint(point);
        Point2D tempRightTopPoint = rightTopPoint.createSymmetryPoint(point);
        Point2D tempLeftBottomPoint = leftBottomPoint.createSymmetryPoint(point);
        Point2D tempRightBottomPoint = rightBottomPoint.createSymmetryPoint(point);
        
        drawSegment(tempLeftTopPoint, tempRightTopPoint, lineStyle);
        drawSegment(tempRightTopPoint, tempLeftBottomPoint, lineStyle);
        drawSegment(tempLeftBottomPoint, tempRightBottomPoint, lineStyle);
        drawSegment(tempRightBottomPoint, tempLeftTopPoint, lineStyle);        
    }

    @Override
    public void drawOutline() {
        Point2D tempLeftTopPoint = leftTopPoint.createRotationPoint(centerPoint, this.rotatedAngle);
        Point2D tempRightTopPoint = rightTopPoint.createRotationPoint(centerPoint, this.rotatedAngle);
        Point2D tempLeftBottomPoint = leftBottomPoint.createRotationPoint(centerPoint, this.rotatedAngle);
        Point2D tempRightBottomPoint = rightBottomPoint.createRotationPoint(centerPoint, this.rotatedAngle);
        
        drawSegment(tempLeftTopPoint, tempRightTopPoint, lineStyle);
        drawSegment(tempRightTopPoint, tempRightBottomPoint, lineStyle);
        drawSegment(tempRightBottomPoint, tempLeftBottomPoint, lineStyle);
        drawSegment(tempLeftBottomPoint, tempLeftTopPoint, lineStyle);            
    }

}
