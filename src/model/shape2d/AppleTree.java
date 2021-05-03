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
public class AppleTree extends Shape2D{
     public AppleTree(boolean[][] markedChangeOfBoard, Color[][] changedColorOfBoard, String[][] changedCoordOfBoard, Color filledColor) {
        super(markedChangeOfBoard, changedColorOfBoard, changedCoordOfBoard, filledColor);
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
