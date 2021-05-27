package model.shape2d.animation;

import control.SettingConstants;
import control.util.Ultility;
import java.awt.Color;
import java.util.ArrayList;
import control.myawt.SKPoint2D;
import model.shape2d.Shape2D;
import model.shape2d.Vector2D;

public class Volcano extends Shape2D {

    public static final int[] ROUGH_NUMBER_ARRAY_1 = {2, 1, 1, 2, 1, 2, 1, 2, 1, 2, 2, 1, 1, 2, 1, 2, 2, 2, 2, 1, 2, 1, 1, 1, 2, 2, 1, 1, 2, 2, 2, 2, 2, 1, 1, 1, 2, 2, 1, 2, 2, 1, 2, 1, 1, 1, 2, 2, 1, 1, 2, 2, 1, 1, 2, 2, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 2, 1, 1, 2, 1, 2, 1, 1, 2, 1, 1, 2, 2, 1, 2, 1, 2, 1, 1, 1, 1, 2, 2, 1, 1, 2, 1, 1, 1, 2, 1, 2, 2, 2, 1, 1, 1, 2, 1, 1, 2, 1, 1, 2};
    public static final int[] ROUGH_NUMBER_ARRAY_2 = {1, 2, 2, 2, 2, 2, 2, 1, 1, 1, 2, 2, 2, 2, 2, 1, 2, 1, 1, 1, 2, 1, 2, 1, 1, 2, 1, 1, 2, 1, 1, 2, 1, 2, 1, 2, 2, 2, 2, 2, 1, 2, 2, 2, 1, 2, 1, 2, 2, 2, 1, 1, 2, 2, 1, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 1, 2, 2, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 2, 1, 2, 2, 2, 1, 1, 1, 1, 1, 2, 1, 2, 2, 1, 2, 2, 1, 1, 2, 1, 1, 2, 2, 2, 2, 2, 2, 2};

    public static final Color COLOR_1 = new Color(255, 100, 50);
    public static final Color COLOR_2 = new Color(132, 127, 111);
    public static final Color COLOR_3 = new Color(123, 99, 99);

    private SKPoint2D startPoint2D_2 = new SKPoint2D();
    private SKPoint2D endPoint2D_2 = new SKPoint2D();

    //tâm ellipse miệng núi lửa
    private SKPoint2D faceCenterPoint = new SKPoint2D();

    //nhánh lớn 1s
    private ArrayList<SKPoint2D> pointList1 = new ArrayList<>();

    public Volcano(boolean[][] markedChangeOfBoard, Color[][] changedColorOfBoard,
            String[][] changedCoordOfBoard, Color filledColor) {
        super(markedChangeOfBoard, changedColorOfBoard, changedCoordOfBoard,
                filledColor);
    }

    public void setProperty(SKPoint2D startPoint, SKPoint2D endPoint) {
        this.startPoint2D.setLocation(startPoint);
        this.endPoint2D.setLocation(endPoint);

        // cạnh còn lại của núi lửa
        this.startPoint2D_2.setLocation(this.startPoint2D, 30, 0);
        this.endPoint2D_2.setLocation(this.startPoint2D_2, 70, 60);

        this.faceCenterPoint.setLocation((this.startPoint2D.getCoordX()
                + startPoint2D_2.getCoordX()) / 2, this.startPoint2D.getCoordY());

        // dung nham
        pointList1.clear();

        pointList1.add(new SKPoint2D(faceCenterPoint, -3, 5));
        pointList1.add(new SKPoint2D(this.startPoint2D, 5, 13));
        pointList1.add(new SKPoint2D(pointList1.get(1), 8, 15));
        pointList1.add(new SKPoint2D(pointList1.get(2), -14, 10));
        pointList1.add(new SKPoint2D(pointList1.get(3), 16, 20));
    }

    public void drawVolcano() {
        setFilledColor(Color.BLACK);

        //vẽ đường thẳng + gây nhiễu
        drawSegmentS(this.startPoint2D, this.endPoint2D, ROUGH_NUMBER_ARRAY_1,
                ROUGH_NUMBER_ARRAY_2);

        drawSegmentS(startPoint2D_2, endPoint2D_2, ROUGH_NUMBER_ARRAY_1,
                ROUGH_NUMBER_ARRAY_2);

        drawSegmentUnSave(this.endPoint2D, endPoint2D_2);

        drawOutlineEllipse(faceCenterPoint, (int) (startPoint2D_2.getCoordX()
                - this.startPoint2D.getCoordX()) / 2, 4, false, true,
                true, false);

        //vẽ dung nham
        setFilledColor(COLOR_1);

        drawZigZagS(pointList1,ROUGH_NUMBER_ARRAY_1, ROUGH_NUMBER_ARRAY_2);

        //nhánh 1.2
        drawSegmentS(new SKPoint2D(pointList1.get(2)),
                new SKPoint2D(pointList1.get(2), 9, 12), ROUGH_NUMBER_ARRAY_1,
                ROUGH_NUMBER_ARRAY_2);

        drawSegmentS(new SKPoint2D(pointList1.get(2), 9, 12),
                new SKPoint2D(pointList1.get(2), 12, 25), ROUGH_NUMBER_ARRAY_1,
                ROUGH_NUMBER_ARRAY_2);

        //nhánh 2
        drawSegmentS(new SKPoint2D(startPoint2D_2, -4, 4),
                new SKPoint2D(startPoint2D_2, 3, 50), ROUGH_NUMBER_ARRAY_1,
                ROUGH_NUMBER_ARRAY_2);

        // nhánh 2.2
        drawSegmentS(new SKPoint2D(startPoint2D_2, -2, 14),
                new SKPoint2D(startPoint2D_2, -8, 30), ROUGH_NUMBER_ARRAY_1,
                ROUGH_NUMBER_ARRAY_2);

        drawSegmentS(new SKPoint2D(new SKPoint2D(startPoint2D_2, -8, 30)),
                new SKPoint2D(startPoint2D_2, -8, 35), ROUGH_NUMBER_ARRAY_1,
                ROUGH_NUMBER_ARRAY_2);

        //nhánh 2.3
        drawSegmentS(new SKPoint2D(startPoint2D_2, 0, 22),
                new SKPoint2D(startPoint2D_2, 10, 30), ROUGH_NUMBER_ARRAY_1,
                ROUGH_NUMBER_ARRAY_2);

        drawSegmentS(new SKPoint2D(startPoint2D_2, 10, 30),
                new SKPoint2D(startPoint2D_2, 14, 60), ROUGH_NUMBER_ARRAY_1,
                ROUGH_NUMBER_ARRAY_2);

        // nhánh 3
        drawSegmentS(new SKPoint2D(this.startPoint2D, 0, 2),
                new SKPoint2D(this.startPoint2D, 3, 25), ROUGH_NUMBER_ARRAY_1,
                ROUGH_NUMBER_ARRAY_2);

        //nhánh 3.2
        drawSegmentS(new SKPoint2D(this.startPoint2D, 1, 10),
                new SKPoint2D(this.startPoint2D, -10, 40), ROUGH_NUMBER_ARRAY_1,
                ROUGH_NUMBER_ARRAY_2);

        //dung nham ở miệng núi lửa
        drawOutlineEllipse(new SKPoint2D(faceCenterPoint, 0, 1), (int) (startPoint2D_2.getCoordX()
                - this.startPoint2D.getCoordX()) / 2, 4, false, true, true, false);

        drawOutlineEllipse(new SKPoint2D(faceCenterPoint, 0, 2), (int) (startPoint2D_2.getCoordX()
                - this.startPoint2D.getCoordX()) / 2, 4, false, true, true, false);

        drawSegmentUnSave(new SKPoint2D(pointList1.get(0), 0, 0),
                new SKPoint2D(pointList1.get(0), 5, 0));

        /* To mau */
        setFilledColor(COLOR_2);

        Ultility.paint(changedColorOfBoard, markedChangeOfBoard,
                new SKPoint2D(this.startPoint2D, 0, 7), COLOR_3, false);

        Ultility.paint(changedColorOfBoard, markedChangeOfBoard,
                new SKPoint2D(this.startPoint2D, 20, 10), COLOR_3, false);

        Ultility.paint(changedColorOfBoard, markedChangeOfBoard,
                new SKPoint2D(this.startPoint2D, 45, 30), COLOR_3, false);
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
}
