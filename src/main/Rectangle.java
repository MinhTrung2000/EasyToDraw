package main;

import java.awt.Color;

public class Rectangle extends Shape {

    private Point2D leftTopPoint;
    private Point2D rightTopPoint;
    private Point2D leftBottomPoint;
    private Point2D rightBottomPoint;

    public Rectangle(boolean[][] markedPointsOfShape, Color[][] colorPointsOfShape, Color color) {
        super(markedPointsOfShape, colorPointsOfShape, color);

        leftTopPoint = new Point2D();
        leftBottomPoint = new Point2D();
        rightTopPoint = new Point2D();
        rightBottomPoint = new Point2D();

        sourcePoint = leftTopPoint;
        destinationPoint = rightBottomPoint;
    }

    public void draw(Color filledColor) {
        draw();
        Ultility.paint(colorPointsOfShape, markedPointsOfShape, centerPoint, filledColor);
    }
    
    @Override
    public void saveCoordinateOfPoint(String[][] coordOfBoard) {
        leftTopPoint.rotate(centerPoint, rotatedAngle).saveCoord(coordOfBoard);
        rightTopPoint.rotate(centerPoint, rotatedAngle).saveCoord(coordOfBoard);
        leftBottomPoint.rotate(centerPoint, rotatedAngle).saveCoord(coordOfBoard);
        rightBottomPoint.rotate(centerPoint, rotatedAngle).saveCoord(coordOfBoard);
    }

    @Override
    public void setProperty(Point2D startPoint, Point2D endPoint, Settings.LineStyle lineStyle) {
        super.setProperty(sourcePoint, destinationPoint, lineStyle);
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
    public void rotate(double angle) {
        double totalAngle = this.rotatedAngle + angle;

        Point2D tempLeftTopPoint = leftTopPoint.rotate(centerPoint, totalAngle);
        Point2D tempRightTopPoint = rightTopPoint.rotate(centerPoint, totalAngle);
        Point2D tempLeftBottomPoint = leftBottomPoint.rotate(centerPoint, totalAngle);
        Point2D tempRightBottomPoint = rightBottomPoint.rotate(centerPoint, totalAngle);

        drawSegment(tempLeftTopPoint, tempRightTopPoint, lineStyle);
        drawSegment(tempRightTopPoint, tempLeftBottomPoint, lineStyle);
        drawSegment(tempLeftBottomPoint, tempRightBottomPoint, lineStyle);
        drawSegment(tempRightBottomPoint, tempLeftTopPoint, lineStyle);
    }

    @Override
    public void move(Vector2D vector) {
        Point2D tempLeftTopPoint = leftTopPoint.rotate(centerPoint, rotatedAngle).move(vector);
        Point2D tempRightTopPoint = rightTopPoint.rotate(centerPoint, rotatedAngle).move(vector);
        Point2D tempLeftBottomPoint = leftBottomPoint.rotate(centerPoint, rotatedAngle).move(vector);
        Point2D tempRightBottomPoint = rightBottomPoint.rotate(centerPoint, rotatedAngle).move(vector);

        drawSegment(tempLeftTopPoint, tempRightTopPoint, lineStyle);
        drawSegment(tempRightTopPoint, tempLeftBottomPoint, lineStyle);
        drawSegment(tempLeftBottomPoint, tempRightBottomPoint, lineStyle);
        drawSegment(tempRightBottomPoint, tempLeftTopPoint, lineStyle);
    }
    
    public void applyMove(Vector2D vector) {
        leftTopPoint.move(vector);
        rightTopPoint.move(vector);
        leftBottomPoint.move(vector);
        rightBottomPoint.move(vector);
        
        centerPoint.move(vector);
        draw();
    }
    
    public void getOXSymmetry() {
        Point2D tempLeftTopPoint = leftTopPoint.getOXSymmetry();
        Point2D tempRightTopPoint = rightTopPoint.getOXSymmetry();
        Point2D tempLeftBottomPoint = leftBottomPoint.getOXSymmetry();
        Point2D tempRightBottomPoint = rightBottomPoint.getOXSymmetry();
        
        drawSegment(tempLeftTopPoint, tempRightTopPoint, lineStyle);
        drawSegment(tempRightTopPoint, tempLeftBottomPoint, lineStyle);
        drawSegment(tempLeftBottomPoint, tempRightBottomPoint, lineStyle);
        drawSegment(tempRightBottomPoint, tempLeftTopPoint, lineStyle);
    }
    
    public void getOYSymmetry() {
        Point2D tempLeftTopPoint = leftTopPoint.getOYSymmetry();
        Point2D tempRightTopPoint = rightTopPoint.getOYSymmetry();
        Point2D tempLeftBottomPoint = leftBottomPoint.getOYSymmetry();
        Point2D tempRightBottomPoint = rightBottomPoint.getOYSymmetry();
        
        drawSegment(tempLeftTopPoint, tempRightTopPoint, lineStyle);
        drawSegment(tempRightTopPoint, tempLeftBottomPoint, lineStyle);
        drawSegment(tempLeftBottomPoint, tempRightBottomPoint, lineStyle);
        drawSegment(tempRightBottomPoint, tempLeftTopPoint, lineStyle);
    }
    
    public void getPointSymmetry(Point2D point) {
        Point2D tempLeftTopPoint = leftTopPoint.getPointSymmetry(point);
        Point2D tempRightTopPoint = rightTopPoint.getPointSymmetry(point);
        Point2D tempLeftBottomPoint = leftBottomPoint.getPointSymmetry(point);
        Point2D tempRightBottomPoint = rightBottomPoint.getPointSymmetry(point);
        
        drawSegment(tempLeftTopPoint, tempRightTopPoint, lineStyle);
        drawSegment(tempRightTopPoint, tempLeftBottomPoint, lineStyle);
        drawSegment(tempLeftBottomPoint, tempRightBottomPoint, lineStyle);
        drawSegment(tempRightBottomPoint, tempLeftTopPoint, lineStyle);        
    }
}
