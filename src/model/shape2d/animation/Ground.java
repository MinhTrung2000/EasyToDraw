package model.shape2d.animation;

import control.SettingConstants;
import control.util.Ultility;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import control.myawt.SKPoint2D;
import model.shape2d.Shape2D;
import model.shape2d.Vector2D;

public class Ground extends Shape2D {

    public static final int[] ROUGH_NUMBER_ARRAY_1 = {2, 1, 1, 1, 2, 2, 2, 2, 2, 3, 2, 1, 2, 1, 3, 2, 1, 1, 1, 1, 1, 3, 1, 2, 3, 2, 3, 1, 1, 3, 2, 3, 2, 3, 3, 2, 3, 3, 2, 3, 3, 1, 3, 3, 3, 2, 1, 3, 3, 1, 3, 1, 3, 3, 1, 3, 1, 2, 3, 2, 3, 2, 3, 2, 1, 1, 2, 3, 3, 1, 3, 3, 1, 2, 1, 1, 2, 2, 3, 3, 3, 1, 2, 2, 1, 1, 3, 2, 3, 1, 3, 1, 1, 2, 2, 1, 2, 1, 2, 2, 1, 1, 1, 1, 2, 1, 2, 2, 2, 2};
    public static final int[] ROUGH_NUMBER_ARRAY_2 = {3, 1, 2, 1, 1, 3, 3, 3, 2, 3, 1, 2, 3, 3, 1, 1, 2, 1, 2, 2, 2, 2, 3, 2, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 1, 1, 3, 1, 3, 2, 3, 3, 3, 1, 1, 3, 1, 2, 1, 1, 3, 1, 1, 1, 2, 3, 3, 3, 1, 3, 1, 1, 1, 3, 2, 1, 1, 2, 1, 1, 2, 1, 1, 2, 1, 2, 3, 1, 3, 1, 3, 1, 2, 2, 3, 2, 2, 3, 1, 2, 3, 3, 3, 3, 3, 3, 1, 1, 3, 2, 3, 1, 2, 1, 2, 3, 3, 2, 3, 2};

    public static final Color GRASS_COLOR = new Color(15, 242, 22);
    public static final Color COLOR_1 = new Color(15, 242, 22);
    public static final Color COLOR_2 = new Color(228, 197, 65);
    public static final Color COLOR_3 = new Color(252, 164, 237);
    public static final Color COLOR_4 = new Color(59, 102, 107);

    private HashMap<SKPoint2D, Boolean> flower_CenterP = new HashMap<>();
    private ArrayList<SKPoint2D> pointList = new ArrayList<>();
    private ArrayList<SKPoint2D> pointList2 = new ArrayList<>();

    public Ground(boolean[][] markedChangeOfBoard, Color[][] changedColorOfBoard, String[][] changedCoordOfBoard, Color filledColor) {
        super(markedChangeOfBoard, changedColorOfBoard, changedCoordOfBoard, filledColor);
    }

    public void drawGround(SKPoint2D startP) {
        int realWidthLimit = 1361 / SettingConstants.RECT_SIZE + 3 - 1;

        setFilledColor(COLOR_1);

        int flatGround1_Length = 40;
        int slopeGround1_Length = 55;
        int slopeGround2_Length = 43;
        int flatGround2_Length = 60;

        int slopeGround3_Length = realWidthLimit - (flatGround1_Length + flatGround2_Length + slopeGround1_Length + slopeGround2_Length);

        pointList.clear();

        pointList.add(new SKPoint2D(startP, 0, 0));
        pointList.add(new SKPoint2D(startP, flatGround1_Length, 0));
        pointList.add(new SKPoint2D(pointList.get(1), slopeGround1_Length, 8));
        pointList.add(new SKPoint2D(pointList.get(2), slopeGround2_Length, -10));
        pointList.add(new SKPoint2D(pointList.get(3), flatGround2_Length, 0));
        pointList.add(new SKPoint2D(pointList.get(4), slopeGround3_Length, 3));

        drawZigZagS(pointList, ROUGH_NUMBER_ARRAY_1, ROUGH_NUMBER_ARRAY_2);

        setFilledColor(GRASS_COLOR);

        pointList2.clear();

        pointList2.add(startP);
        pointList2.add(new SKPoint2D(startP, 0, 25));
        pointList2.add(new SKPoint2D(pointList2.get(1), realWidthLimit, 0));
        pointList2.add(new SKPoint2D(pointList2.get(2), 0, -27));

        drawZigZag(pointList2);

        //đặt trước vị trí vẽ hoa
        this.flower_CenterP.put(new SKPoint2D(startP, 20, -10), true);
        this.flower_CenterP.put(new SKPoint2D(startP, 100, -3), false);
        this.flower_CenterP.put(new SKPoint2D(startP, 63, 10), false);
        this.flower_CenterP.put(new SKPoint2D(startP, 17, 7), true);

    }

    public void paintGround(SKPoint2D startP) {
        Ultility.paint(changedColorOfBoard, markedChangeOfBoard, new SKPoint2D(startP, 5, 5), GRASS_COLOR, false);
    }

    public void drawFlower(SKPoint2D startP, boolean right) {
        setFilledColor(COLOR_2);
        drawOutlineCircle(2, startP, true, true, true, true, true, true, true, true);

        setFilledColor(COLOR_3);

        drawOutlineCircle(2, new SKPoint2D(startP, -3, 2), false, false, false, true, true, true, true, true);
        drawOutlineCircle(2, new SKPoint2D(startP, 3, 2), true, true, true, true, true, false, false, false);
        drawOutlineCircle(2, new SKPoint2D(startP, -3, -2), true, false, false, false, true, true, true, true);
        drawOutlineCircle(2, new SKPoint2D(startP, 3, -2), true, true, true, true, false, false, false, true);

        setFilledColor(COLOR_4);

        if (right) {
            drawSegment(new SKPoint2D(startP, 0, 3), new SKPoint2D(startP, 2, 13));

        } else {
            drawSegment(new SKPoint2D(startP, 0, 3), new SKPoint2D(startP, -2, 13));
        }

    }

    public void drawAndPaintFlowers() {
        for (Map.Entry<SKPoint2D, Boolean> m : flower_CenterP.entrySet()) {
            this.drawFlower(m.getKey(), m.getValue());
        }

        paintFlower();
    }

    public void paintFlower() {
        setFilledColor(COLOR_3);

        for (Map.Entry<SKPoint2D, Boolean> centerP : flower_CenterP.entrySet()) {
            Ultility.paint(changedColorOfBoard, markedChangeOfBoard, centerP.getKey(), new Color(249, 255, 135), false);
            Ultility.paint(changedColorOfBoard, markedChangeOfBoard, new SKPoint2D(centerP.getKey(), -3, 2), filledColor, false);
            Ultility.paint(changedColorOfBoard, markedChangeOfBoard, new SKPoint2D(centerP.getKey(), 3, 2), filledColor, false);
            Ultility.paint(changedColorOfBoard, markedChangeOfBoard, new SKPoint2D(centerP.getKey(), 3, -2), filledColor, false);
            Ultility.paint(changedColorOfBoard, markedChangeOfBoard, new SKPoint2D(centerP.getKey(), -3, -2), filledColor, false);
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
