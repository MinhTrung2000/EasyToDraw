/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.shape2d.animation;

import control.util.Ultility;
import java.awt.Color;
import java.util.ArrayList;
import control.myawt.SKPoint2D;
import model.shape2d.Shape2D;
import model.shape2d.Vector2D;

/**
 *
 * @author Minh Tu
 */
public class Sun extends Shape2D{
    public Sun(boolean[][] markedChangeOfBoard, Color[][] changedColorOfBoard, String[][] changedCoordOfBoard, Color filledColor) {
        super(markedChangeOfBoard, changedColorOfBoard, changedCoordOfBoard, filledColor);
    }
    public void drawSun(SKPoint2D startP){
      //  this.filledColor = new Color(236,224,0);
      this.filledColor = new Color (245, 120, 60);
        this.drawOutlineCircle(7, startP, true, true, true, true, true, true, true, true);
      //  this.filledColor = new Color (255,242,0);
       this.filledColor = new Color(0,0,0);
        
        
//        pointList.add(new SKPoint2D(startP,-6,-6));
//        pointList.add(new SKPoint2D(startP,-6,0));
//        pointList.add(new SKPoint2D(startP,-6,6));
//        pointList.add(new SKPoint2D(startP,-5,5));
//        pointList.add(new SKPoint2D(startP,-5,6));
        
        
        
    }
  public void paintSun(SKPoint2D startP){
       this.filledColor = new Color (255,247,79);
       // tô nền => tạo viền => tô lại màu trong viền
       Ultility.paint(changedColorOfBoard, markedChangeOfBoard, startP, filledColor);
        this.filledColor= new Color(255,242,0);
        ArrayList<SKPoint2D> pointList = new ArrayList<>();
        pointList.add(new SKPoint2D(startP,2,6));
        pointList.add(new SKPoint2D(startP,2,5));
        pointList.add(new SKPoint2D(startP,3,5));
        pointList.add(new SKPoint2D(startP,3,1));
        
        pointList.add(new SKPoint2D(startP,5,1));
        pointList.add(new SKPoint2D(startP,5,-4));
        
        pointList.add(new SKPoint2D(startP,4,-4));
        pointList.add(new SKPoint2D(startP,4,-5));
        pointList.add(new SKPoint2D(startP,2,-5));
        pointList.add(new SKPoint2D(startP,2,-6));
        pointList.add(new SKPoint2D(startP,-2,-6));
        pointList.add(new SKPoint2D(startP,-2,-3));
        pointList.add(new SKPoint2D(startP,-5,-3));
        pointList.add(new SKPoint2D(startP,-5,4));
        pointList.add(new SKPoint2D(startP,-3,4));
        pointList.add(new SKPoint2D(startP,-3,5));
        this.drawZigZag(pointList);
        Ultility.paint(changedColorOfBoard, markedChangeOfBoard, startP, filledColor);
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
    public void setProperty(SKPoint2D startPoint, SKPoint2D endPoint) {

    }
}
