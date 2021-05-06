/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.shape2d.animation;

import java.awt.Color;
import control.myawt.SKPoint2D;
import model.shape2d.Shape2D;
import model.shape2d.Vector2D;

/**
 *
 * @author Minh Tu
 */
public class Cloud extends Shape2D {

    public Cloud(boolean[][] markedChangeOfBoard, Color[][] changedColorOfBoard, String[][] changedCoordOfBoard, Color filledColor) {
        super(markedChangeOfBoard, changedColorOfBoard, changedCoordOfBoard, filledColor);
    }
    int width = 18;

    public void drawCloud(SKPoint2D startP) {
        this.drawSegment(startP, new SKPoint2D(startP, width, 0));
        this.drawSegment(new SKPoint2D(startP, -1, -1), new SKPoint2D(startP, -1, -1 - 3));
        this.drawSegment(new SKPoint2D(startP, width + 1, -1), new SKPoint2D(startP, width + 1, -1 - 3));

        this.drawOutlineCircle(6, new SKPoint2D(startP, 5, -4), true, false, false, false, false, false, true, true);
        this.drawOutlineCircle(3, new SKPoint2D(startP, width - 2, -4), true, true, false, false, false, false, false, true);
        this.drawOutlineCircle(3, new SKPoint2D(startP, width - 6, -7), true, true, false, false, false, false, true, true);
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
