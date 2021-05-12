package model.shape2d.animation;

import control.SettingConstants;
import control.util.Ultility;
import java.awt.Color;
import java.util.ArrayList;
import control.myawt.SKPoint2D;
import model.shape2d.Shape2D;
import model.shape2d.Vector2D;

public class River extends Shape2D {

    public static final int[] ROUGH_NUMBER_ARRAY_1 = {1, 2, 2, 2, 2, 2, 2, 1, 1, 1, 2, 2, 2, 2, 2, 1, 2, 1, 1, 1, 2, 1, 2, 1, 1, 2, 1, 1, 2, 1, 1, 2, 1, 2, 1, 2, 2, 2, 2, 2, 1, 2, 2, 2, 1, 2, 1, 2, 2, 2, 1, 1, 2, 2, 1, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 1, 2, 2, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 2, 1, 2, 2, 2, 1, 1, 1, 1, 1, 2, 1, 2, 2, 1, 2, 2, 1, 1, 2, 1, 1, 2, 2, 2, 2, 2, 2, 2};
    public static final int[] ROUGH_NUMBER_ARRAY_2 = {2, 1, 1, 2, 1, 2, 1, 2, 1, 2, 2, 1, 1, 2, 1, 2, 2, 2, 2, 1, 2, 1, 1, 1, 2, 2, 1, 1, 2, 2, 2, 2, 2, 1, 1, 1, 2, 2, 1, 2, 2, 1, 2, 1, 1, 1, 2, 2, 1, 1, 2, 2, 1, 1, 2, 2, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 2, 1, 1, 2, 1, 2, 1, 1, 2, 1, 1, 2, 2, 1, 2, 1, 2, 1, 1, 1, 1, 2, 2, 1, 1, 2, 1, 1, 1, 2, 1, 2, 2, 2, 1, 1, 1, 2, 1, 1, 2, 1, 1, 2};

    public static final int HEIGHT_1 = 22;
    public static final int HEIGHT_2 = HEIGHT_1 + 45;

    public static final Color SURFACE_COLOR = new Color(151, 251, 251);
    public static final Color BOTTOM_COLOR = new Color(120, 250, 250);

    public static final Color COLOR_1 = new Color(101, 202, 225);
    public static final Color COLOR_2 = new Color(137, 225, 255);
    
    private ArrayList<SKPoint2D> pointList = new ArrayList<>();

    public River(boolean[][] markedChangeOfBoard, Color[][] changedColorOfBoard, String[][] changedCoordOfBoard, Color filledColor) {
        super(markedChangeOfBoard, changedColorOfBoard, changedCoordOfBoard, filledColor);
    }

    public void drawRiver(SKPoint2D startP) {
        int realWidthLimit = 1361 / SettingConstants.RECT_SIZE + 3 - 1;

        //phân viền mặt nước
        setFilledColor(COLOR_1);
        drawSegment(startP, new SKPoint2D(startP, realWidthLimit, 0));
        drawSegment(new SKPoint2D(startP, 0, 1), new SKPoint2D(startP, realWidthLimit, 1));

        setFilledColor(SURFACE_COLOR);
        drawSegment(startP, new SKPoint2D(startP, 0, HEIGHT_1));
        drawSegment(new SKPoint2D(startP, realWidthLimit, 0), new SKPoint2D(startP, realWidthLimit, HEIGHT_1));

        // vẽ gợn sóng trên mặt nước
        pointList.clear();
        
        pointList.add(new SKPoint2D(startP, 0, HEIGHT_1));
        pointList.add(new SKPoint2D(pointList.get(0), 40, -6));
        pointList.add(new SKPoint2D(pointList.get(1), 70, 8));
        pointList.add(new SKPoint2D(pointList.get(2), 53, -5));
        pointList.add(new SKPoint2D(startP, realWidthLimit, HEIGHT_1));

        setFilledColor(COLOR_2);
        drawZigZagS(pointList, ROUGH_NUMBER_ARRAY_1, ROUGH_NUMBER_ARRAY_2);

        int length = 27;
        int posX = 23;
        setLineStyle(SettingConstants.LineStyle.DASH);

        drawOutlineEllipse(length / 2 + 1, 3, new SKPoint2D(startP, posX, 7), true, true, false, false);
        drawOutlineEllipse(length / 2 + 1, 5, new SKPoint2D(startP, posX + length, 7), false, false, true, true);

        posX = 80;
        length = 20;
        drawOutlineEllipse(length / 2 + 1, 2, new SKPoint2D(startP, posX, 12), true, true, false, false);
        drawOutlineEllipse(length / 2 + 1, 4, new SKPoint2D(startP, posX + length, 12), false, false, true, true);

        posX = 140;
        length = 22;
        drawOutlineEllipse(length / 2 + 1, 2, new SKPoint2D(startP, posX, 9), true, true, false, false);
        drawOutlineEllipse(length / 2 + 1, 4, new SKPoint2D(startP, posX + length, 9), false, false, true, true);

        //phân viền dưới nước
        setLineStyle(DEFAULT_LINE_STYLE);

        setFilledColor(BOTTOM_COLOR);
        drawSegment(new SKPoint2D(startP, 0, HEIGHT_1), new SKPoint2D(startP, 0, HEIGHT_2));
        drawSegment(new SKPoint2D(startP, realWidthLimit, HEIGHT_1), new SKPoint2D(startP, realWidthLimit, HEIGHT_2));

        drawSegment(new SKPoint2D(startP, 0, HEIGHT_2), new SKPoint2D(startP, realWidthLimit, HEIGHT_2));

    }

    public void paintRiver(SKPoint2D startP) {
        setFilledColor(SURFACE_COLOR);
        Ultility.paint(changedColorOfBoard, markedChangeOfBoard, new SKPoint2D(startP, 4, 4), filledColor, false);

        setFilledColor(BOTTOM_COLOR);
        Ultility.paint(changedColorOfBoard, markedChangeOfBoard, new SKPoint2D(startP, 4, HEIGHT_1 + 4), filledColor, false);
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
