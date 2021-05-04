/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.shape2d.animation;

import java.awt.Color;
import model.shape2d.Point2D;
import model.shape2d.Shape2D;
import model.shape2d.Vector2D;

/**
 *
 * @author Minh Tu
 */
public class Cloud extends Shape2D{
    public Cloud(boolean[][] markedChangeOfBoard, Color[][] changedColorOfBoard, String[][] changedCoordOfBoard, Color filledColor) {
        super(markedChangeOfBoard, changedColorOfBoard, changedCoordOfBoard, filledColor);
    }
    int width = 18;
    public void drawCloud(Point2D startP){
        this.drawSegment(startP, new Point2D (startP,width,0));
        this.drawSegment(new Point2D (startP,-1,-1), new Point2D (startP,-1,-1 - 3));
        this.drawSegment(new Point2D (startP,width+1,-1), new Point2D (startP,width+1,-1 - 3));
        
        this.drawOutlineCircle(6, new Point2D (startP, 5, -4), true, false, false, false, false, false, true, true);
        this.drawOutlineCircle(3, new Point2D (startP, width -2, -4), true, true, false, false, false, false, false, true);
        this.drawOutlineCircle(3, new Point2D (startP, width -6, -7), true, true, false, false, false, false, true, true);
    }
      @Override
    public void applyMove(Vector2D vector) {

    }

    @Override
    public void saveCoordinates() {

    }

    @Override
    public void drawOutline() {

    }

    @Override
    public void setProperty(Point2D startPoint, Point2D endPoint) {

    }
}
