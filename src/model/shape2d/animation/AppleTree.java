package model.shape2d.animation;

import control.myawt.SKPoint2D;
import control.util.Ultility;
import java.awt.Color;
import java.util.ArrayList;
import model.shape2d.Shape2D;
import model.shape2d.Vector2D;

public class AppleTree extends Shape2D {

    public static final Color COLOR_1 = new Color(184, 133, 71);
    public static final Color COLOR_2 = new Color(247, 10, 10);
    public static final Color COLOR_3 = new Color(24, 203, 39);
    public static final Color COLOR_4 = new Color(102, 54, 4);

    private ArrayList<SKPoint2D> appleCenterPointArray = new ArrayList<>();

    private ArrayList<SKPoint2D> pointList = new ArrayList<>();
    private ArrayList<SKPoint2D> pointList2 = new ArrayList<>();

    private SKPoint2D trunkLeft_CenterP = new SKPoint2D();
    private SKPoint2D trunkRight_CenterP = new SKPoint2D();

    public AppleTree(boolean[][] markedChangeOfBoard, Color[][] changedColorOfBoard,
            String[][] changedCoordOfBoard, Color filledColor) {
        super(markedChangeOfBoard, changedColorOfBoard, changedCoordOfBoard, filledColor);
    }

    public void setProperty(SKPoint2D startPoint) {
        startPoint2D.setLocation(startPoint);

        pointList.clear();

        pointList.add(new SKPoint2D(this.startPoint2D, -10, -4));
        pointList.add(new SKPoint2D(pointList.get(0), 10, -6));
        pointList.add(new SKPoint2D(pointList.get(1), 7, 3));
        pointList.add(new SKPoint2D(pointList.get(2), 0, 4));
        //về trái
        pointList.add(new SKPoint2D(pointList.get(0), -9, 6));

        trunkLeft_CenterP.setLocation(pointList.get(4), 8, 19);
        trunkRight_CenterP.setLocation(pointList.get(4), 28, 19);

        pointList2.clear();

        pointList2.add(new SKPoint2D(trunkLeft_CenterP, 0, -17));
        pointList2.add(new SKPoint2D(pointList2.get(0), -7, -9));
        pointList2.add(new SKPoint2D(pointList2.get(1), 3, -3));
        pointList2.add(new SKPoint2D(pointList2.get(2), 10, 13));
        pointList2.add(new SKPoint2D(pointList2.get(3), 8, -6));
        pointList2.add(new SKPoint2D(pointList2.get(4), -4, -6));
        pointList2.add(new SKPoint2D(pointList2.get(5), 2, -2));
        pointList2.add(new SKPoint2D(pointList2.get(6), 4, 5));
        pointList2.add(new SKPoint2D(pointList2.get(7), 5, -10));
        pointList2.add(new SKPoint2D(pointList2.get(8), 3, 0));
        pointList2.add(new SKPoint2D(pointList2.get(9), -4, 17));

        appleCenterPointArray.add(new SKPoint2D(pointList2.get(3), 0, -20));
        appleCenterPointArray.add(new SKPoint2D(pointList2.get(3), -17, 0));
        appleCenterPointArray.add(new SKPoint2D(pointList2.get(3), 12, -24));
        appleCenterPointArray.add(new SKPoint2D(pointList2.get(3), 24, -6));
    }

//    public void drawAppleTree(SKPoint2D startPoint) {
    public void drawAppleTree() {
        setFilledColor(Color.BLACK);

        drawOutlineCircle(pointList.get(0), 20, 
                false, false, false, false, false, false, true, true);

        drawOutlineCircle(pointList.get(1), 20, 
                true, false, false, false, false, false, false, true);

        drawOutlineCircle(pointList.get(2), 21, 
                true, true, false, false, false, false, false, false);

        drawOutlineCircle(pointList.get(3), 20, 
                false, true, true, true, false, false, false, false);

        //về trái
        drawOutlineCircle(pointList.get(4), 12, 
                false, false, false, true, true, true, true, false);

        // vẽ thân
        drawOutlineEllipse(trunkLeft_CenterP, 3, 17, true, true, false, false);
        drawOutlineEllipse(trunkRight_CenterP, 3, 17, false, false, true, true);

        //vẽ cành 
        drawZigZag(pointList2);

        //vẽ vân gỗ
        setFilledColor(COLOR_1);

        drawSegmentUnSave(new SKPoint2D(pointList2.get(3), 3, 7),
                new SKPoint2D(pointList2.get(3), 1, 13));
        drawSegmentUnSave(new SKPoint2D(pointList2.get(3), 7, 15), 
                new SKPoint2D(pointList2.get(3), 4, 22));
        drawSegmentUnSave(new SKPoint2D(pointList2.get(3), 11, -3), 
                new SKPoint2D(pointList2.get(3), 9, 3));
        drawSegmentUnSave(new SKPoint2D(pointList2.get(3), 2, 17), 
                new SKPoint2D(pointList2.get(3), 0, 22));

        //vẽ táo 
        for (SKPoint2D centerP : appleCenterPointArray) {
            drawApple(centerP);
        }

        //giới hạn  chân để tô màu
        setFilledColor(Color.BLACK);

        drawSegmentUnSave(new SKPoint2D(pointList.get(4), 9, 36), 
                new SKPoint2D(pointList.get(4), 27, 36));

        /* To mau */
        setFilledColor(COLOR_3);

        Ultility.paint(changedColorOfBoard, markedChangeOfBoard, 
                new SKPoint2D(this.startPoint2D, -3, -3), filledColor, false);

        setFilledColor(COLOR_4);

        Ultility.paint(changedColorOfBoard, markedChangeOfBoard, 
                new SKPoint2D(this.startPoint2D, 0, 3), filledColor, false);

        // To mau cac qua tao
        setFilledColor(COLOR_2);

        for (SKPoint2D centerP : appleCenterPointArray) {
            Ultility.paint(changedColorOfBoard, markedChangeOfBoard, centerP, 
                    filledColor, false);
        }
    }

    private void drawApple(SKPoint2D centerP) {
        setFilledColor(Color.BLACK);

        drawOutlineCircle(centerP, 3, 
                true, true, true, true, true, true, true, true);
        drawSegmentUnSave(new SKPoint2D(centerP, 4, -4),
                new SKPoint2D(centerP, 2, -2));
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
