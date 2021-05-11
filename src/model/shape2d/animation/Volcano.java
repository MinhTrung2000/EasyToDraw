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

    public static final int[] ROUGH_NUMBER_ARRAY_1 = {2, 1, 1, 2, 1, 2, 1, 2, 1, 2, 2, 1, 1, 2, 1, 2, 2, 2, 2, 1, 2, 1, 1, 1, 2, 2, 1, 1, 2, 2, 2, 2, 2, 1, 1, 1, 2, 2, 1, 2, 2, 1, 2, 1, 1, 1, 2, 2, 1, 1, 2, 2, 1, 1, 2, 2, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 2, 1, 1, 2, 1, 2, 1, 1, 2, 1, 1, 2, 2, 1, 2, 1, 2, 1, 1, 1, 1, 2, 2, 1, 1, 2, 1, 1, 1, 2, 1, 2, 2, 2, 1, 1, 1, 2, 1, 1, 2, 1, 1, 2};
    public static final int[] ROUGH_NUMBER_ARRAY_2 = {1, 2, 2, 2, 2, 2, 2, 1, 1, 1, 2, 2, 2, 2, 2, 1, 2, 1, 1, 1, 2, 1, 2, 1, 1, 2, 1, 1, 2, 1, 1, 2, 1, 2, 1, 2, 2, 2, 2, 2, 1, 2, 2, 2, 1, 2, 1, 2, 2, 2, 1, 1, 2, 2, 1, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 1, 2, 2, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 2, 1, 2, 2, 2, 1, 1, 1, 1, 1, 2, 1, 2, 2, 1, 2, 2, 1, 1, 2, 1, 1, 2, 2, 2, 2, 2, 2, 2};

    public static final Color COLOR_1 = new Color(255, 100, 50);
    public static final Color COLOR_2 = new Color(132, 127, 111);
    public static final Color COLOR_3 = new Color(123, 99, 99);

    public Volcano(boolean[][] markedChangeOfBoard, Color[][] changedColorOfBoard, String[][] changedCoordOfBoard, Color filledColor) {
        super(markedChangeOfBoard, changedColorOfBoard, changedCoordOfBoard, filledColor);
    }

    public void drawVolcano(SKPoint2D startPoint, SKPoint2D endPoint) {
        setFilledColor(Color.BLACK);
        
        //cạnh còn lại của núi lửa
        SKPoint2D startPoint2 = new SKPoint2D(startPoint, 30, 0);
        SKPoint2D endPoint2 = new SKPoint2D(startPoint2, 70, 60);

        //vẽ đường thẳng + gây nhiễu
        drawSegmentS(startPoint, endPoint, ROUGH_NUMBER_ARRAY_1, ROUGH_NUMBER_ARRAY_2);
        drawSegmentS(startPoint2, endPoint2, ROUGH_NUMBER_ARRAY_1, ROUGH_NUMBER_ARRAY_2);
        drawSegment(endPoint, endPoint2);

        //tâm ellipse miệng núi lửa
        SKPoint2D volcano_CenterP = new SKPoint2D((startPoint.getCoordX() + startPoint2.getCoordX()) / 2, startPoint.getCoordY());
        drawOutlineEllipse((int) (startPoint2.getCoordX() - startPoint.getCoordX()) / 2, 4, volcano_CenterP, false, false, true, true);

        //vẽ dung nham
        setFilledColor(COLOR_1);

        //nhánh lớn 1
        ArrayList<SKPoint2D> pointList1 = new ArrayList<>();

        pointList1.add(new SKPoint2D(volcano_CenterP, -3, 5));
        pointList1.add(new SKPoint2D(startPoint, 5, 13));
        pointList1.add(new SKPoint2D(pointList1.get(1), 8, 15));
        pointList1.add(new SKPoint2D(pointList1.get(2), -14, 10));
        pointList1.add(new SKPoint2D(pointList1.get(3), 16, 20));

        drawZigZag(pointList1);

        //nhánh 1.2
        drawSegmentS(new SKPoint2D(pointList1.get(2)), new SKPoint2D(pointList1.get(2), 9, 12), ROUGH_NUMBER_ARRAY_1, ROUGH_NUMBER_ARRAY_2);
        drawSegmentS(new SKPoint2D(pointList1.get(2), 9, 12), new SKPoint2D(pointList1.get(2), 12, 25), ROUGH_NUMBER_ARRAY_1, ROUGH_NUMBER_ARRAY_2);

        //nhánh 2
        drawSegmentS(new SKPoint2D(startPoint2, -4, 4), new SKPoint2D(startPoint2, 3, 50), ROUGH_NUMBER_ARRAY_1, ROUGH_NUMBER_ARRAY_2);

        // nhánh 2.2
        drawSegmentS(new SKPoint2D(startPoint2, -2, 14), new SKPoint2D(startPoint2, -8, 30), ROUGH_NUMBER_ARRAY_1, ROUGH_NUMBER_ARRAY_2);
        drawSegmentS(new SKPoint2D(new SKPoint2D(startPoint2, -8, 30)), new SKPoint2D(startPoint2, -8, 35), ROUGH_NUMBER_ARRAY_1, ROUGH_NUMBER_ARRAY_2);

        //nhánh 2.3
        drawSegmentS(new SKPoint2D(startPoint2, 0, 22), new SKPoint2D(startPoint2, 10, 30), ROUGH_NUMBER_ARRAY_1, ROUGH_NUMBER_ARRAY_2);
        drawSegmentS(new SKPoint2D(startPoint2, 10, 30), new SKPoint2D(startPoint2, 14, 60), ROUGH_NUMBER_ARRAY_1, ROUGH_NUMBER_ARRAY_2);

        // nhánh 3
        drawSegmentS(new SKPoint2D(startPoint, 0, 2), new SKPoint2D(startPoint, 3, 25), ROUGH_NUMBER_ARRAY_1, ROUGH_NUMBER_ARRAY_2);

        //nhánh 3.2
        drawSegmentS(new SKPoint2D(startPoint, 1, 10), new SKPoint2D(startPoint, -10, 40), ROUGH_NUMBER_ARRAY_1, ROUGH_NUMBER_ARRAY_2);

        //dung nham ở miệng núi lửa
        drawOutlineEllipse((int) (startPoint2.getCoordX() - startPoint.getCoordX()) / 2, 4, new SKPoint2D(volcano_CenterP, 0, 1), false, false, true, true);
        drawOutlineEllipse((int) (startPoint2.getCoordX() - startPoint.getCoordX()) / 2, 4, new SKPoint2D(volcano_CenterP, 0, 2), false, false, true, true);

        drawSegment(new SKPoint2D(pointList1.get(0), 0, 0), new SKPoint2D(pointList1.get(0), 5, 0));
    }

    public void paintVolcano(SKPoint2D startPoint) {
        setFilledColor(COLOR_2);
        Ultility.paint(changedColorOfBoard, markedChangeOfBoard, new SKPoint2D(startPoint, 0, 7), COLOR_3, false);
        Ultility.paint(changedColorOfBoard, markedChangeOfBoard, new SKPoint2D(startPoint, 20, 10), COLOR_3, false);
        Ultility.paint(changedColorOfBoard, markedChangeOfBoard, new SKPoint2D(startPoint, 45, 30), COLOR_3, false);
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
