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

    public AppleTree(boolean[][] markedChangeOfBoard, Color[][] changedColorOfBoard,
            String[][] changedCoordOfBoard, Color filledColor) {
        super(markedChangeOfBoard, changedColorOfBoard, changedCoordOfBoard, filledColor);
    }

    public void drawAppleTree(SKPoint2D startPoint) {
        startPoint2D.setLocation(startPoint);

        setFilledColor(Color.BLACK);

        pointList.clear();

        pointList.add(new SKPoint2D(this.startPoint2D, -10, -4));
        drawOutlineCircle(20, pointList.get(0), false, false, false, false, false, false, true, true);

        pointList.add(new SKPoint2D(pointList.get(0), 10, -6));
        drawOutlineCircle(20, pointList.get(1), true, false, false, false, false, false, false, true);

        pointList.add(new SKPoint2D(pointList.get(1), 7, 3));
        drawOutlineCircle(21, pointList.get(2), true, true, false, false, false, false, false, false);

        pointList.add(new SKPoint2D(pointList.get(2), 0, 4));
        drawOutlineCircle(20, pointList.get(3), false, true, true, true, false, false, false, false);

        //về trái
        pointList.add(new SKPoint2D(pointList.get(0), -9, 6));
        drawOutlineCircle(12, pointList.get(4), false, false, false, true, true, true, true, false);

        // vẽ thân
        SKPoint2D trunkLeft_CenterP = new SKPoint2D(pointList.get(4), 8, 19);
        SKPoint2D trunkRight_CenterP = new SKPoint2D(pointList.get(4), 28, 19);

        drawOutlineEllipse(3, 17, trunkLeft_CenterP, false, true, false, true);
        drawOutlineEllipse(3, 17, trunkRight_CenterP, true, false, true, false);

        //vẽ cành
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
        drawZigZag(pointList2);

        //vẽ vân gỗ
        setFilledColor(COLOR_1);

        drawSegment(new SKPoint2D(pointList2.get(3), 3, 7), new SKPoint2D(pointList2.get(3), 1, 13));
        drawSegment(new SKPoint2D(pointList2.get(3), 7, 15), new SKPoint2D(pointList2.get(3), 4, 22));
        drawSegment(new SKPoint2D(pointList2.get(3), 11, -3), new SKPoint2D(pointList2.get(3), 9, 3));
        drawSegment(new SKPoint2D(pointList2.get(3), 2, 17), new SKPoint2D(pointList2.get(3), 0, 22));

        //vẽ táo 
        appleCenterPointArray.add(new SKPoint2D(pointList2.get(3), 0, -20));
        appleCenterPointArray.add(new SKPoint2D(pointList2.get(3), -17, 0));
        appleCenterPointArray.add(new SKPoint2D(pointList2.get(3), 12, -24));
        appleCenterPointArray.add(new SKPoint2D(pointList2.get(3), 24, -6));

        for (SKPoint2D centerP : appleCenterPointArray) {
            drawApple(centerP);
        }

        //giới hạn  chân để tô màu
        setFilledColor(Color.BLACK);
        
        drawSegment(new SKPoint2D(pointList.get(4), 9, 36), new SKPoint2D(pointList.get(4), 27, 36));
    }

    public void paintAppleTree(SKPoint2D startP) {
        setFilledColor(COLOR_3);
        
        Ultility.paint(changedColorOfBoard, markedChangeOfBoard, new SKPoint2D(startP, -3, -3), filledColor, false);
       
        setFilledColor(COLOR_4);
        
        Ultility.paint(changedColorOfBoard, markedChangeOfBoard, new SKPoint2D(startP, 0, 3), filledColor, false);

    }

    private void drawApple(SKPoint2D centerP) {
        setFilledColor(Color.BLACK);
        
        drawOutlineCircle(3, centerP, true, true, true, true, true, true, true, true);
        drawSegment(new SKPoint2D(centerP, 4, -4), new SKPoint2D(centerP, 2, -2));

    }

    public void paintApple() {
        setFilledColor(COLOR_2);
        
        for (SKPoint2D centerP : appleCenterPointArray) {
            Ultility.paint(changedColorOfBoard, markedChangeOfBoard, centerP, filledColor, false);
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
