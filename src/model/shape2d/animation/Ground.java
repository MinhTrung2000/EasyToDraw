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
    public static final Color COLOR_5 = new Color(249, 255, 135);

    public static final int FLAT_GROUND_LENGTH_1 = 40;
    public static final int FLAT_GROUND_LENGTH_2 = 60;
    public static final int SLOPE_GROUND_LENGTH_1 = 55;
    public static final int SLOPE_GROUND_LENGTH_2 = 43;

    private HashMap<SKPoint2D, Boolean> flower_CenterP = new HashMap<>();

    private ArrayList<SKPoint2D> pointList = new ArrayList<>();
    private ArrayList<SKPoint2D> pointList2 = new ArrayList<>();

    private int slopeGround3_Length = 0;

    public Ground(boolean[][] markedChangeOfBoard, Color[][] changedColorOfBoard,
            String[][] changedCoordOfBoard, Color filledColor) {
        super(markedChangeOfBoard, changedColorOfBoard, changedCoordOfBoard,
                filledColor);
    }

    public void setProperty(int widthLimit, SKPoint2D startP) {
        this.startPoint2D.setLocation(startP);

        slopeGround3_Length = widthLimit -1 - (FLAT_GROUND_LENGTH_1
                + FLAT_GROUND_LENGTH_2 + SLOPE_GROUND_LENGTH_1 + SLOPE_GROUND_LENGTH_2);

        pointList.clear();

        pointList.add(new SKPoint2D(this.startPoint2D, 0, 0));
        pointList.add(new SKPoint2D(this.startPoint2D, FLAT_GROUND_LENGTH_1, 0));
        pointList.add(new SKPoint2D(pointList.get(1), SLOPE_GROUND_LENGTH_1, 8));
        pointList.add(new SKPoint2D(pointList.get(2), SLOPE_GROUND_LENGTH_2, -10));
        pointList.add(new SKPoint2D(pointList.get(3), FLAT_GROUND_LENGTH_2, 0));
        pointList.add(new SKPoint2D(pointList.get(4), slopeGround3_Length, 3));

        pointList2.clear();
        
        //viền của đất cỏ, tránh tô màu bị loang
        pointList2.add(this.startPoint2D);
        pointList2.add(new SKPoint2D(this.startPoint2D, 0, 25));
        pointList2.add(new SKPoint2D(pointList2.get(1), widthLimit - 1, 0));
        pointList2.add(new SKPoint2D(pointList2.get(2), 0, -27));

        //đặt trước vị trí vẽ hoa
        flower_CenterP.put(new SKPoint2D(this.startPoint2D, 20, -10), true);
        flower_CenterP.put(new SKPoint2D(this.startPoint2D, 100, -3), false);
        flower_CenterP.put(new SKPoint2D(this.startPoint2D, 63, 10), false);
        flower_CenterP.put(new SKPoint2D(this.startPoint2D, 17, 7), true);
    }

    public void drawGround() {
        setFilledColor(COLOR_1);

        drawZigZagS(pointList, ROUGH_NUMBER_ARRAY_1, ROUGH_NUMBER_ARRAY_2);

        setFilledColor(GRASS_COLOR);
        
        drawZigZag(pointList2);

        /* To mau dat */
        Ultility.paint(changedColorOfBoard, markedChangeOfBoard,
                new SKPoint2D(this.startPoint2D, 5, 5), GRASS_COLOR, false);
        
        /* Ve hoa*/
        drawFlower();
    }

    public void drawFlower() {
        // Ve 4 canh hoa
        for (Map.Entry<SKPoint2D, Boolean> m : flower_CenterP.entrySet()) {
            SKPoint2D point = m.getKey();

            setFilledColor(COLOR_2);
            
            drawOutlineCircle(point, 2,
                    true, true, true, true, true, true, true, true);

            setFilledColor(COLOR_3);

            drawOutlineCircle(new SKPoint2D(point, -3, 2), 2,
                    false, false, false, true, true, true, true, true);
            drawOutlineCircle(new SKPoint2D(point, 3, 2), 2,
                    true, true, true, true, true, false, false, false);
            drawOutlineCircle(new SKPoint2D(point, -3, -2), 2,
                    true, false, false, false, true, true, true, true);
            drawOutlineCircle(new SKPoint2D(point, 3, -2), 2,
                    true, true, true, true, false, false, false, true);

            boolean right = m.getValue();
            
            setFilledColor(COLOR_4);
            
            if (right) {
                drawSegmentUnSave(new SKPoint2D(point, 0, 3),
                        new SKPoint2D(point, 2, 13));
            } else {
                drawSegmentUnSave(new SKPoint2D(point, 0, 3),
                        new SKPoint2D(point, -2, 13));
            }
        }

        // To 4 canh hoa
        for (Map.Entry<SKPoint2D, Boolean> centerP : flower_CenterP.entrySet()) {
            Ultility.paint(changedColorOfBoard, markedChangeOfBoard,
                    centerP.getKey(), COLOR_5, false);
            Ultility.paint(changedColorOfBoard, markedChangeOfBoard,
                    new SKPoint2D(centerP.getKey(), -3, 2), COLOR_3, false);
            Ultility.paint(changedColorOfBoard, markedChangeOfBoard,
                    new SKPoint2D(centerP.getKey(), 3, 2), COLOR_3, false);
            Ultility.paint(changedColorOfBoard, markedChangeOfBoard,
                    new SKPoint2D(centerP.getKey(), 3, -2), COLOR_3, false);
            Ultility.paint(changedColorOfBoard, markedChangeOfBoard,
                    new SKPoint2D(centerP.getKey(), -3, -2), COLOR_3, false);
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
