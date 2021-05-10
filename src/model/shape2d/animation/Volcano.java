/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.shape2d.animation;

import control.SettingConstants;
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
public class Volcano extends Shape2D {

    //  int [] roughNumberArray110 = {2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 1, 2, 1, 2, 2, 2, 1, 2, 1, 2, 2, 1, 1, 1, 1, 2, 2, 2, 2, 1, 2, 2, 1, 2, 2, 2, 2, 2, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 1, 1, 2, 2, 1, 1, 1, 1, 2, 1, 2, 2, 1, 1, 1, 2, 1, 2, 1, 2, 2, 2, 1, 1, 1, 2, 2, 2, 2, 2, 2, 1, 1, 1, 1, 2, 2, 2, 1, 1, 2, 2, 2, 1, 2, 2, 1, 2, 2, 2, 1};
    int[] roughNumberArray110 = {2, 1, 1, 2, 1, 2, 1, 2, 1, 2, 2, 1, 1, 2, 1, 2, 2, 2, 2, 1, 2, 1, 1, 1, 2, 2, 1, 1, 2, 2, 2, 2, 2, 1, 1, 1, 2, 2, 1, 2, 2, 1, 2, 1, 1, 1, 2, 2, 1, 1, 2, 2, 1, 1, 2, 2, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 2, 1, 1, 2, 1, 2, 1, 1, 2, 1, 1, 2, 2, 1, 2, 1, 2, 1, 1, 1, 1, 2, 2, 1, 1, 2, 1, 1, 1, 2, 1, 2, 2, 2, 1, 1, 1, 2, 1, 1, 2, 1, 1, 2};
    int[] roughNumberArray110_2 = {1, 2, 2, 2, 2, 2, 2, 1, 1, 1, 2, 2, 2, 2, 2, 1, 2, 1, 1, 1, 2, 1, 2, 1, 1, 2, 1, 1, 2, 1, 1, 2, 1, 2, 1, 2, 2, 2, 2, 2, 1, 2, 2, 2, 1, 2, 1, 2, 2, 2, 1, 1, 2, 2, 1, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 1, 2, 2, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 2, 1, 2, 2, 2, 1, 1, 1, 1, 1, 2, 1, 2, 2, 1, 2, 2, 1, 1, 2, 1, 1, 2, 2, 2, 2, 2, 2, 2};

    public Volcano(boolean[][] markedChangeOfBoard, Color[][] changedColorOfBoard, String[][] changedCoordOfBoard, Color filledColor) {
        super(markedChangeOfBoard, changedColorOfBoard, changedCoordOfBoard, filledColor);
    }

    public void drawVolcano(SKPoint2D startPoint, SKPoint2D endPoint) {
        this.filledColor = new Color(0, 0, 0);
        //cạnh còn lại của núi lửa
        SKPoint2D startPoint2 = new SKPoint2D(startPoint, 30, 0);
        SKPoint2D endPoint2 = new SKPoint2D(startPoint2, 70, 60);
        //vẽ đường thẳng + gây nhiễu
        this.drawSegmentS(startPoint, endPoint, roughNumberArray110, roughNumberArray110_2);
        this.drawSegmentS(startPoint2, endPoint2, roughNumberArray110, roughNumberArray110_2);
        this.drawSegment(endPoint, endPoint2);
        //tâm ellipse miệng núi lửa
        SKPoint2D volcano_CenterP = new SKPoint2D((startPoint.getCoordX() + startPoint2.getCoordX()) / 2, startPoint.getCoordY());
        this.drawOutlineEllipse((int) (startPoint2.getCoordX() - startPoint.getCoordX()) / 2, 4, volcano_CenterP, false, false, true, true);

        //vẽ dung nham
        this.filledColor = new Color(255, 100, 50);

        //nhánh lớn 1
        ArrayList<SKPoint2D> pointList1 = new ArrayList<>();
        pointList1.add(new SKPoint2D(volcano_CenterP, -3, 5));
        pointList1.add(new SKPoint2D(startPoint, 5, 13));
        pointList1.add(new SKPoint2D(pointList1.get(1), 8, 15));
        pointList1.add(new SKPoint2D(pointList1.get(2), -14, 10));
        pointList1.add(new SKPoint2D(pointList1.get(3), 16, 20));
        this.drawZigZag(pointList1);
        //nhánh 1.2
        this.drawSegmentS(new SKPoint2D(pointList1.get(2)), new SKPoint2D(pointList1.get(2), 9, 12), roughNumberArray110, roughNumberArray110_2);
        this.drawSegmentS(new SKPoint2D(pointList1.get(2), 9, 12), new SKPoint2D(pointList1.get(2), 12, 25), roughNumberArray110, roughNumberArray110_2);

        //nhánh 2
        this.drawSegmentS(new SKPoint2D(startPoint2, -4, 4), new SKPoint2D(startPoint2, 3, 50), roughNumberArray110, roughNumberArray110_2);
        // nhánh 2.2
        this.drawSegmentS(new SKPoint2D(startPoint2, -2, 14), new SKPoint2D(startPoint2, -8, 30), roughNumberArray110, roughNumberArray110_2);
        this.drawSegmentS(new SKPoint2D(new SKPoint2D(startPoint2, -8, 30)), new SKPoint2D(startPoint2, -8, 35), roughNumberArray110, roughNumberArray110_2);
        //nhánh 2.3
        this.drawSegmentS(new SKPoint2D(startPoint2, 0, 22), new SKPoint2D(startPoint2, 10, 30), roughNumberArray110, roughNumberArray110_2);
        this.drawSegmentS(new SKPoint2D(startPoint2, 10, 30), new SKPoint2D(startPoint2, 14, 60), roughNumberArray110, roughNumberArray110_2);
        // nhánh 3
        this.drawSegmentS(new SKPoint2D(startPoint, 0, 2), new SKPoint2D(startPoint, 3, 25), roughNumberArray110, roughNumberArray110_2);
        //nhánh 3.2
        this.drawSegmentS(new SKPoint2D(startPoint, 1, 10), new SKPoint2D(startPoint, -10, 40), roughNumberArray110, roughNumberArray110_2);

        //dung nham ở miệng núi lửa
        this.drawOutlineEllipse((int) (startPoint2.getCoordX() - startPoint.getCoordX()) / 2, 4, new SKPoint2D(volcano_CenterP, 0, 1), false, false, true, true);
        this.drawOutlineEllipse((int) (startPoint2.getCoordX() - startPoint.getCoordX()) / 2, 4, new SKPoint2D(volcano_CenterP, 0, 2), false, false, true, true);
        this.drawSegment(new SKPoint2D(pointList1.get(0), 0, 0), new SKPoint2D(pointList1.get(0), 5, 0));
    }

    public void paintVolcano(SKPoint2D startPoint) {
        this.filledColor = new Color(132, 127, 111);
        Ultility.paint(changedColorOfBoard, markedChangeOfBoard, new SKPoint2D(startPoint, 0, 7), new Color(123, 99, 99), false);
        Ultility.paint(changedColorOfBoard, markedChangeOfBoard, new SKPoint2D(startPoint, 20, 10), new Color(123, 99, 99), false);

        Ultility.paint(changedColorOfBoard, markedChangeOfBoard, new SKPoint2D(startPoint, 45, 30), new Color(123, 99, 99), false);

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
