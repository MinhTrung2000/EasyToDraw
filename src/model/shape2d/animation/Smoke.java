/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.shape2d.animation;

import java.awt.Color;
import java.util.ArrayList;
import control.myawt.SKPoint2D;
import model.shape2d.Shape2D;
import model.shape2d.Vector2D;

/**
 *
 * @author Minh Tu
 */
public class Smoke extends Shape2D {

    public Smoke(boolean[][] markedChangeOfBoard, Color[][] changedColorOfBoard, String[][] changedCoordOfBoard, Color filledColor) {
        super(markedChangeOfBoard, changedColorOfBoard, changedCoordOfBoard, filledColor);
    }

    public void drawSmoke(SKPoint2D startPoint) {
        this.startPoint2D = startPoint; //startPoint là tâm của khói
//        this.filledColor = new Color(255, 0, 0);
//        savePoint(this.startPoint2D.getCoordX(), this.startPoint2D.getCoordY());
        this.filledColor = new Color(0, 0, 0);
        ArrayList<SKPoint2D> pointList_Inside = new ArrayList<>();
        //khói ở trong
        pointList_Inside.add(new SKPoint2D(this.startPoint2D, -6, 2));
        this.drawOutlineCircle(2, pointList_Inside.get(0), false, false, false, false, false, true, true, true);
        
        pointList_Inside.add(new SKPoint2D(this.startPoint2D, -6 - 2, 2 + 3));
        this.drawOutlineCircle(2, pointList_Inside.get(1), false, false, false, false, false, false, true, true);

        pointList_Inside.add(new SKPoint2D(this.startPoint2D, 7, 8));
        this.drawOutlineCircle(4, pointList_Inside.get(2), true, true, true, false, false, false, false, false);
        //vòng tròn liên tiếp
        
        pointList_Inside.add(new SKPoint2D(this.startPoint2D, 4, -4));
        this.drawOutlineCircle(4, pointList_Inside.get(3), true, true, false, false, false, false, false, true);
        
        pointList_Inside.add(new SKPoint2D(this.startPoint2D, 4 + 6, -4));
        this.drawOutlineCircle(2, pointList_Inside.get(4), true, true, true, false, false, false, false, true);
        
        pointList_Inside.add(new SKPoint2D(this.startPoint2D, 0, 6));
        this.drawOutlineCircle(4, pointList_Inside.get(5), false, false, false, false, true, true, false, false);

        //viền ngoài, theo chiều kim đồng hồ lồng nhau
        ArrayList<SKPoint2D> pointList_Border = new ArrayList<>();
        pointList_Border.add(new SKPoint2D(this.startPoint2D, -10, -1));
        this.drawOutlineCircle(7, pointList_Border.get(0), false, false, false, false, false, false, true, true);

        pointList_Border.add(new SKPoint2D(startPoint2D, -6, -4));
        this.drawOutlineCircle(5, pointList_Border.get(1), false, false, false, false, false, false, true, true);

        pointList_Border.add(new SKPoint2D(startPoint2D, -3, -6));
        this.drawOutlineCircle(7, pointList_Border.get(2), true, false, false, false, false, false, true, true);

        pointList_Border.add(new SKPoint2D(startPoint2D, 3, -10));
        this.drawOutlineCircle(5, pointList_Border.get(3), true, false, false, false, false, false, false, true);

        pointList_Border.add(new SKPoint2D(startPoint2D, 8, -9));
        this.drawOutlineCircle(7, pointList_Border.get(4), true, false, false, false, false, false, false, true);

        pointList_Border.add(new SKPoint2D(startPoint2D, 13, -9));
        this.drawOutlineCircle(7, pointList_Border.get(5), true, true, true, false, false, false, false, false);

        pointList_Border.add(new SKPoint2D(startPoint2D, 16, -4));
        this.drawOutlineCircle(5, pointList_Border.get(6), false, true, true, true, false, false, false, false);

        pointList_Border.add(new SKPoint2D(startPoint2D, 17, 4));
        this.drawOutlineCircle(5, pointList_Border.get(7), true, true, true, false, false, false, false, false);

        //quay về đầu, làm bên trái
        //-6 2
        //-3 6
        pointList_Border.add(new SKPoint2D(startPoint2D, -12, 5));
        this.drawOutlineCircle(7, pointList_Border.get(8), false, false, false, false, true, true, true, true);

        pointList_Border.add(new SKPoint2D(startPoint2D, -8, 9));
        this.drawOutlineCircle(7, pointList_Border.get(9), false, false, false, false, true, true, false, false);

        //quay lại bên phải làm cái cuối cùng trước khi vẽ ellipse
        pointList_Border.add(new SKPoint2D(startPoint2D, 14, 8));
        this.drawOutlineCircle(6, pointList_Border.get(10), false, true, true, true, false, false, false, false);

        this.drawOutlineEllipse(6, 10, new SKPoint2D(pointList_Border.get(10), 2, 12), true, false, false, false);
        this.drawOutlineEllipse(6, 10, new SKPoint2D(pointList_Border.get(10), -25, 12), false, true, false, false);

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
