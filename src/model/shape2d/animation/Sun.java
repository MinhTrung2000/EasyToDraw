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
import model.shape2d.Segment2D;
import model.shape2d.Shape2D;
import model.shape2d.Vector2D;

/**
 *
 * @author Minh Tu
 */
public class Sun extends Shape2D {

    public static final Color Color0 = new Color(245, 120, 60);
    public static final Color Color1 = new Color(255, 247, 79);
    public static final Color Color2 = new Color(255, 242, 0);

    private ArrayList<SKPoint2D> pointList = new ArrayList<>();
    
    private Segment2D sunLine;

    public Sun(boolean[][] markedChangeOfBoard, Color[][] changedColorOfBoard, String[][] changedCoordOfBoard, Color filledColor) {
        super(markedChangeOfBoard, changedColorOfBoard, changedCoordOfBoard, filledColor);
    }
    
    public void setProperty(SKPoint2D centerPoint) {
        this.centerPoint2D = centerPoint;
        
        pointList.add(new SKPoint2D(this.centerPoint2D, 2, 6));
        pointList.add(new SKPoint2D(this.centerPoint2D, 2, 5));
        pointList.add(new SKPoint2D(this.centerPoint2D, 3, 5));
        pointList.add(new SKPoint2D(this.centerPoint2D, 3, 1));

        pointList.add(new SKPoint2D(this.centerPoint2D, 5, 1));
        pointList.add(new SKPoint2D(this.centerPoint2D, 5, -4));

        pointList.add(new SKPoint2D(this.centerPoint2D, 4, -4));
        pointList.add(new SKPoint2D(this.centerPoint2D, 4, -5));
        pointList.add(new SKPoint2D(this.centerPoint2D, 2, -5));
        pointList.add(new SKPoint2D(this.centerPoint2D, 2, -6));
        pointList.add(new SKPoint2D(this.centerPoint2D, -2, -6));
        pointList.add(new SKPoint2D(this.centerPoint2D, -2, -3));
        pointList.add(new SKPoint2D(this.centerPoint2D, -5, -3));
        pointList.add(new SKPoint2D(this.centerPoint2D, -5, 4));
        pointList.add(new SKPoint2D(this.centerPoint2D, -3, 4));
        pointList.add(new SKPoint2D(this.centerPoint2D, -3, 5));
    }

    public void drawSun() {
        this.filledColor = Color0;
        this.drawOutlineCircle(7, this.centerPoint2D, true, true, true, true, true, true, true, true);
        paintSun();
    }

    public void paintSun() {
        this.filledColor = Color1;

        // tô nền => tạo viền => tô lại màu trong viền
        Ultility.paint(changedColorOfBoard, markedChangeOfBoard, this.centerPoint2D, filledColor);

        this.filledColor = Color2;

        this.drawZigZag(pointList);

        Ultility.paint(changedColorOfBoard, markedChangeOfBoard, this.centerPoint2D, filledColor);
    }

    public void drawSunny() {
        
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
