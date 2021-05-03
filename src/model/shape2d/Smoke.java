/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.shape2d;

import java.awt.Color;

/**
 *
 * @author Minh Tu
 */
public class Smoke extends Shape2D{
    public Smoke (boolean[][] markedChangeOfBoard, Color[][] changedColorOfBoard, String[][] changedCoordOfBoard, Color filledColor) {
        super(markedChangeOfBoard, changedColorOfBoard, changedCoordOfBoard, filledColor);
    }
    
    public void drawSmoke(Point2D startPoint){
        this.startPoint=startPoint; //startPoint là tâm của khói
        this.filledColor = new Color (255,0,0);
        savePoint(this.startPoint.coordX, this.startPoint.coordY);
        this.filledColor = new Color (0,0,0);
        
        this.drawOutlineCircle(2, new Point2D (this.startPoint,-6,2), false, false, false, false, false, true, true, true);  
        this.drawOutlineCircle(2, new Point2D (this.startPoint,-6 -2,2 + 3), false, false, false, false, false, false, true, true); 
        
        this.drawOutlineCircle(4, new Point2D (this.startPoint,7,8), true, true, true, false, false, false, false, false);
        //vòng tròn liên tiếp
        this.drawOutlineCircle(4, new Point2D (this.startPoint,4,-4), true, true, false, false, false, false, false, true);   
        this.drawOutlineCircle(2, new Point2D (this.startPoint,4 +6,-4), true, true, true, false, false, false, false, true);
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
