package model.shape2d;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import static view.SketchPointConstants.*;

public class Segment2D extends Shape2D {

    protected Point2D startPoint;
    protected Point2D endPoint;
    public enum Modal {
        LINE_45_90_DEGREE,
        STRAIGHT_LINE
    }
    public enum UnchangedPoint{
        HEAD_POINT,
        FEET_POINT
    }
    public Segment2D(boolean[][] markedChangeOfBoard, Color[][] changedColorOfBoard, String[][] changedCoordOfBoard, Color filledColor) {
        super(markedChangeOfBoard, changedColorOfBoard, changedCoordOfBoard, filledColor);

        startPoint = new Point2D(0, 0);
        endPoint = new Point2D(0, 0);
    }

    public Point2D getStartPoint() {
        return this.startPoint;
    }
    
    public Point2D getEndPoint() {
        return this.endPoint;
    }
    
    public void setProperty (Point2D startPoint, Point2D endPoint,Modal modal) {
        if(modal==Modal.STRAIGHT_LINE){
            this.startPoint = startPoint;
            this.endPoint = endPoint;
        }else {
            Point2D feetPoint = new Point2D();
            Point2D headPoint = new Point2D();
            int width = endPoint.getCoordX()-startPoint.getCoordX();
            int height = endPoint.getCoordY()-startPoint.getCoordY();
            int widthValue = Math.abs(width);
            int heightValue = Math.abs(height);
            

            int heightDirection;
            int widthDirection;
            
            if(width<=0) widthDirection=-1;
            else widthDirection=1;
            
            if(height<=0) heightDirection=-1;
            else heightDirection=1;
            
            int preferedLength;
            if (widthValue >= heightValue){
                preferedLength = widthValue;
            }else {
                preferedLength = heightValue;
            }
            
            this.startPoint = startPoint;
            
            double ratio = (double)widthValue/(double)heightValue;
            if (ratio <=0.3){
                this.endPoint.setCoord(this.startPoint.getCoordX(),this.startPoint.getCoordY()+heightDirection*preferedLength);
            }else if (ratio >0.3 && ratio<=1.5){
                this.endPoint.setCoord(this.startPoint.getCoordX()+widthDirection*preferedLength,this.startPoint.getCoordY()+heightDirection*preferedLength);
            }else {
                this.endPoint.setCoord(this.startPoint.getCoordX()+widthDirection*preferedLength,this.startPoint.getCoordY());
            }
                
                
            
 
        }
        
        this.centerPoint.setCoord(
                (startPoint.coordX + endPoint.coordX) / 2,
                (startPoint.coordY + endPoint.coordY) / 2
        );
    }

    /**
     * The line drawing method implemented by Bresenham algorithm.
     *
     * @param startPoint
     * @param endPoint
     * @param lineStyle
     */
    @Override
    public void drawOutline() {
        drawSegment(startPoint, endPoint, lineStyle);
    }

    @Override
    public void drawOXSymmetry() {
        Point2D tempStartPoint = startPoint.createOXSymmetryPoint();
        Point2D tempEndPoint = endPoint.createOXSymmetryPoint();

        drawSegment(tempStartPoint, tempEndPoint);
    }

    @Override
    public void drawOYSymmetry() {
        Point2D tempStartPoint = startPoint.createOYSymmetryPoint();
        Point2D tempEndPoint = endPoint.createOYSymmetryPoint();

        drawSegment(tempStartPoint, tempEndPoint);
    }

    @Override
    public void drawPointSymmetry(Point2D basePoint) {
        Point2D tempStartPoint = startPoint.createSymmetryPoint(basePoint);
        Point2D tempEndPoint = endPoint.createSymmetryPoint(basePoint);

        drawSegment(tempStartPoint, tempEndPoint);
    }

    @Override
    public void drawVirtualMove(Vector2D vector) {
        Point2D tempStartPoint = startPoint.createMovingPoint(vector);
        Point2D tempEndPoint = endPoint.createMovingPoint(vector);
        
        drawSegment(tempStartPoint, tempEndPoint);
    }

    @Override
    public void drawVirtualRotation(double angle) {
        double totalAngle = angle + this.rotatedAngle;

        Point2D rotatedStartPoint = startPoint.createRotationPoint(centerPoint, totalAngle);
        Point2D rotatedEndPoint = endPoint.createRotationPoint(centerPoint, totalAngle);

        drawSegment(rotatedStartPoint, rotatedEndPoint, this.lineStyle);
    }

    /**
     * Move the line and change in place.
     *
     * @param vector
     */
    @Override
    public void applyMove(Vector2D vector) {
        startPoint = startPoint.createMovingPoint(vector);
        endPoint = endPoint.createMovingPoint(vector);
        centerPoint = centerPoint.createMovingPoint(vector);
    }
    
    @Override
    public void saveCoordinates() {
        this.startPoint.saveCoord(changedCoordOfBoard);
        this.endPoint.saveCoord(changedCoordOfBoard);
    }
    
    public Point2D intersect(Segment2D other) {
        Vector2D vec_a_b = new Vector2D(this.startPoint, this.endPoint);
        Vector2D vec_c_d = new Vector2D(other.startPoint, other.endPoint);
        Vector2D vec_c_a = new Vector2D(other.startPoint, this.endPoint);
        
        Vector2D r = Vector2D.getVectorOfLinearEquationRepr(vec_a_b, vec_c_d, vec_c_a);
        
        // Two segments are not collided if the root is not in range 0..1
        if (Double.compare(r.getCoordX(), 0.0) > 0 && Double.compare(r.getCoordX(), 1.0) < 0 &&
                Double.compare(r.getCoordY(), 0.0) > 0 && Double.compare(r.getCoordY(), 1.0) < 0) {
            return null;
}
        
        Point2D result = new Point2D(this.startPoint);
        result.move(vec_a_b.scale(r.getCoordX()));
        
        return result;
    }
}
