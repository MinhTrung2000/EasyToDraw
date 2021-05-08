/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.shape2d.animation;

import java.awt.Color;
import control.myawt.SKPoint2D;
import model.shape2d.Segment2D;
import model.shape2d.Shape2D;
import model.shape2d.Vector2D;

/**
 *
 * @author Minh Tu
 */
public class Cloud extends Shape2D {

    public final int COULD_WIDTH = 18;

    public Cloud(boolean[][] markedChangeOfBoard, Color[][] changedColorOfBoard, String[][] changedCoordOfBoard, Color filledColor) {
        super(markedChangeOfBoard, changedColorOfBoard, changedCoordOfBoard, filledColor);
    }

    public void setProperty(SKPoint2D centerPoint) {
        this.centerPoint2D = centerPoint;
        
        pointSet.clear();

//        this.drawSegment(this.centerPoint2D, new SKPoint2D(this.centerPoint2D, COULD_WIDTH, 0));
//        this.drawSegment(new SKPoint2D(this.centerPoint2D, -1, -1), new SKPoint2D(this.centerPoint2D, -1, -1 - 3));
//        this.drawSegment(new SKPoint2D(this.centerPoint2D, COULD_WIDTH + 1, -1), new SKPoint2D(this.centerPoint2D, COULD_WIDTH + 1, -1 - 3));
        Segment2D.mergePointSet(this.pointSet, this.centerPoint2D, new SKPoint2D(this.centerPoint2D, COULD_WIDTH, 0));
        Segment2D.mergePointSet(this.pointSet, new SKPoint2D(this.centerPoint2D, -1, -1), new SKPoint2D(this.centerPoint2D, -1, -1 - 3));
        Segment2D.mergePointSet(this.pointSet, new SKPoint2D(this.centerPoint2D, COULD_WIDTH + 1, -1), new SKPoint2D(this.centerPoint2D, COULD_WIDTH + 1, -1 - 3));

//        this.drawOutlineCircle(6, new SKPoint2D(this.centerPoint2D, 5, -4), true, false, false, false, false, false, true, true);
//        this.drawOutlineCircle(3, new SKPoint2D(this.centerPoint2D, COULD_WIDTH - 2, -4), true, true, false, false, false, false, false, true);
//        this.drawOutlineCircle(3, new SKPoint2D(this.centerPoint2D, COULD_WIDTH - 6, -7), true, true, false, false, false, false, true, true);

        mergePointSetCircle(this.pointSet, new SKPoint2D(this.centerPoint2D, 5, -4), 6, true, false, false, false, false, false, true, true);
        mergePointSetCircle(this.pointSet, new SKPoint2D(this.centerPoint2D, COULD_WIDTH - 2, -4), 3, true, true, false, false, false, false, false, true);
        mergePointSetCircle(this.pointSet, new SKPoint2D(this.centerPoint2D, COULD_WIDTH - 6, -7), 3, true, true, false, false, false, false, true, true);
    }

    public void drawCloud() {
        for (int i = 0; i < pointSet.size(); i++) {
            SKPoint2D point = pointSet.get(i);
            savePoint(point.getCoordX(), point.getCoordY());
        }
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
