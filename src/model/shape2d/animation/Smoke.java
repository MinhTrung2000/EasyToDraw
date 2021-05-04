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
public class Smoke extends Shape2D {

    public Smoke(boolean[][] markedChangeOfBoard, Color[][] changedColorOfBoard, String[][] changedCoordOfBoard, Color filledColor) {
        super(markedChangeOfBoard, changedColorOfBoard, changedCoordOfBoard, filledColor);
    }

    public void drawSmoke(Point2D startPoint) {
        this.startPoint = startPoint; //startPoint là tâm của khói
        this.filledColor = new Color(255, 0, 0);
        savePoint(this.startPoint.getCoordX(), this.startPoint.getCoordY());
        this.filledColor = new Color(0, 0, 0);
        //khói ở trong
        this.drawOutlineCircle(2, new Point2D(this.startPoint, -6, 2), false, false, false, false, false, true, true, true);
        this.drawOutlineCircle(2, new Point2D(this.startPoint, -6 - 2, 2 + 3), false, false, false, false, false, false, true, true);

        this.drawOutlineCircle(4, new Point2D(this.startPoint, 7, 8), true, true, true, false, false, false, false, false);
        //vòng tròn liên tiếp
        this.drawOutlineCircle(4, new Point2D(this.startPoint, 4, -4), true, true, false, false, false, false, false, true);
        this.drawOutlineCircle(2, new Point2D(this.startPoint, 4 + 6, -4), true, true, true, false, false, false, false, true);

        this.drawOutlineCircle(4, new Point2D(this.startPoint, 0, 6), false, false, false, false, true, true, false, false);

        //viền ngoài, theo chiều kim đồng hồ lồng nhau
        ArrayList<Point2D> pointList_Border = new ArrayList<>();
        pointList_Border.add(new Point2D(this.startPoint, -10, -1));
        this.drawOutlineCircle(7, pointList_Border.get(0), false, false, false, false, false, false, true, true);

        pointList_Border.add(new Point2D(pointList_Border.get(0), 4, -3));
        this.drawOutlineCircle(6, pointList_Border.get(1), false, false, false, false, false, false, true, true);

        pointList_Border.add(new Point2D(pointList_Border.get(1), 3, -2));
        this.drawOutlineCircle(7, pointList_Border.get(2), true, false, false, false, false, false, false, true);

        pointList_Border.add(new Point2D(pointList_Border.get(2), 6, -4));
        this.drawOutlineCircle(5, pointList_Border.get(3), true, false, false, false, false, false, false, true);

        pointList_Border.add(new Point2D(pointList_Border.get(3), 5, 1));
        this.drawOutlineCircle(7, pointList_Border.get(4), true, false, false, false, false, false, false, true);

        pointList_Border.add(new Point2D(pointList_Border.get(4), 5, 0));
        this.drawOutlineCircle(7, pointList_Border.get(5), true, true, true, false, false, false, false, false);

        pointList_Border.add(new Point2D(pointList_Border.get(5), 3, 5));
        this.drawOutlineCircle(5, pointList_Border.get(6), false, true, true, true, false, false, false, false);

        pointList_Border.add(new Point2D(pointList_Border.get(6), 1, 8));
        this.drawOutlineCircle(5, pointList_Border.get(7), true, true, true, false, false, false, false, false);

        //quay về đầu, làm bên trái
        pointList_Border.add(new Point2D(pointList_Border.get(0), -3, 6));
        this.drawOutlineCircle(7, pointList_Border.get(8), false, false, false, false, true, true, true, true);

        pointList_Border.add(new Point2D(pointList_Border.get(8), 4, 4));
        this.drawOutlineCircle(7, pointList_Border.get(9), false, false, false, false, true, true, false, false);

        //quay lại bên phải làm cái cuối cùng trước khi vẽ ellipse
        pointList_Border.add(new Point2D(pointList_Border.get(7), -3, 4));
        this.drawOutlineCircle(6, pointList_Border.get(10), false, true, true, true, false, false, false, false);

        this.drawOutlineEllipse(6, 10, new Point2D(pointList_Border.get(10), 2, 12), true, false, false, false);
        this.drawOutlineEllipse(6, 10, new Point2D(pointList_Border.get(10), -25, 12), false, true, false, false);

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
