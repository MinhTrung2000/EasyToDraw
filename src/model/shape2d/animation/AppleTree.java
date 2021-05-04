/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.shape2d.animation;

import java.awt.Color;
import java.util.ArrayList;
import model.shape2d.Point2D;
import model.shape2d.Shape2D;
import model.shape2d.Vector2D;

/**
 *
 * @author Minh Tu
 */
public class AppleTree extends Shape2D{
    public AppleTree (boolean[][] markedChangeOfBoard, Color[][] changedColorOfBoard, String[][] changedCoordOfBoard, Color filledColor) {
        super(markedChangeOfBoard, changedColorOfBoard, changedCoordOfBoard, filledColor);
    }
    
    public void drawAppleTree(Point2D startPoint){
        this.startPoint=startPoint;
        this.filledColor = new Color (255,0,0);
        savePoint(this.startPoint.getCoordX(), this.startPoint.getCoordY());
        this.filledColor = new Color (0,0,0);
        
        ArrayList<Point2D> pointList = new ArrayList<>();
        pointList.add(new Point2D(this.startPoint,-10,-4));
        this.drawOutlineCircle(20, pointList.get(0), false, false, false, false, false, false, true, true);
        
        pointList.add(new Point2D(pointList.get(0),10,-6));
        this.drawOutlineCircle(20, pointList.get(1), true, false, false, false, false, false, false, true);
        
        pointList.add(new Point2D(pointList.get(1),10,3));
        this.drawOutlineCircle(20, pointList.get(2), true, true, false, false, false, false, false, false);
        
        pointList.add(new Point2D(pointList.get(2),5,6));
        this.drawOutlineCircle(15, pointList.get(3), false, true, true, true, false, false, false, false);
        
        //về trái
        pointList.add(new Point2D(pointList.get(0),-9,6));
        this.drawOutlineCircle(12, pointList.get(4), false, false, false, true, true, true, true, false);
        
        // vẽ thân
        this.drawOutlineEllipse(5, 17,  new Point2D ( pointList.get(4),7,19), false, true, false, true);
        this.drawOutlineEllipse(5, 17,  new Point2D ( pointList.get(4),38,19), true, false, true, false);
        //vẽ vân
        
        
        this.drawSegment( new Point2D ( pointList.get(4),9,1),  new Point2D ( pointList.get(4),36,1));
        this.drawSegment( new Point2D ( pointList.get(4),9,36),  new Point2D ( pointList.get(4),36,36));
        
    }
    @Override
    public void applyMove(Vector2D vector) {
        
    }
    
    @Override
    public void saveCoordinates(){
        
    }
    
    @Override
    public void drawOutline() {
        
    }
    
    @Override
    public void setProperty(Point2D startPoint, Point2D endPoint) {
        
    }
}
