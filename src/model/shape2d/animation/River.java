package model.shape2d.animation;

import control.SettingConstants;
import control.util.Ultility;
import java.awt.Color;
import java.util.ArrayList;
import control.myawt.SKPoint2D;
import model.shape2d.Shape2D;
import model.shape2d.Vector2D;

public class River extends Shape2D {

    public static final int[] ROUGH_NUMBER_ARRAY_1 = {1, 2, 2, 2, 2, 2, 2, 1, 1, 1, 2, 2, 2, 2, 2, 1, 2, 1, 1, 1, 2, 1, 2, 1, 1, 2, 1, 1, 2, 1, 1, 2, 1, 2, 1, 2, 2, 2, 2, 2, 1, 2, 2, 2, 1, 2, 1, 2, 2, 2, 1, 1, 2, 2, 1, 2, 2, 2, 1, 2, 2, 2, 1, 1, 1, 1, 2, 2, 1, 2, 2, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 2, 1, 2, 2, 2, 1, 1, 1, 1, 1, 2, 1, 2, 2, 1, 2, 2, 1, 1, 2, 2, 1, 2, 2, 2, 2, 2, 2, 2};
    public static final int[] ROUGH_NUMBER_ARRAY_2 = {2, 1, 1, 2, 1, 2, 1, 2, 1, 2, 2, 1, 1, 2, 1, 2, 2, 2, 2, 1, 2, 1, 1, 1, 2, 2, 1, 1, 2, 2, 2, 2, 2, 1, 1, 1, 2, 2, 1, 2, 2, 1, 2, 1, 1, 1, 2, 2, 1, 1, 2, 2, 1, 1, 2, 2, 2, 1, 2, 2, 1, 2, 1, 1, 1, 1, 2, 1, 1, 2, 1, 2, 1, 1, 2, 1, 1, 2, 2, 1, 2, 1, 2, 1, 1, 1, 1, 2, 2, 1, 1, 2, 1, 1, 1, 2, 1, 2, 2, 2, 1, 2, 1, 2, 1, 2, 2, 1, 2, 2};

    public static final int HEIGHT_1 = 22;
    public static final int HEIGHT_2 = HEIGHT_1 + 45;

    public static final Color SURFACE_COLOR = new Color(151, 251, 251);
 //   public static final Color SURFACE_COLOR = new Color(0, 0, 0);
    public static final Color BOTTOM_COLOR = new Color(120, 250, 250);

    public static final Color COLOR_1 = new Color(101, 202, 225);
    public static final Color COLOR_2 = new Color(137, 225, 255);

    private int widthLimit = 0;

    private ArrayList<SKPoint2D> pointList = new ArrayList<>();

    public River(boolean[][] markedChangeOfBoard, Color[][] changedColorOfBoard,
            String[][] changedCoordOfBoard, Color filledColor) {
        super(markedChangeOfBoard, changedColorOfBoard, changedCoordOfBoard,
                filledColor);
    }

    public void setProperty(int widthLimit, SKPoint2D startP) {
        this.widthLimit = widthLimit - 1;
        this.startPoint2D.setLocation(startP);
        System.out.println(startPoint2D.getCoordX() + " " + startPoint2D.getCoordY());
        pointList.clear();

        pointList.add(new SKPoint2D(startP, 0, HEIGHT_1));
        pointList.add(new SKPoint2D(pointList.get(0), 40, -6));
        pointList.add(new SKPoint2D(pointList.get(1), 70, 8));
        pointList.add(new SKPoint2D(pointList.get(2), 53, -5));
        pointList.add(new SKPoint2D(startP, this.widthLimit, HEIGHT_1));
    }

    public void drawRiver() {
        //phân viền mặt nước
        setFilledColor(COLOR_1);
        
        drawSegmentUnSave(this.startPoint2D, new SKPoint2D(this.startPoint2D, 
                this.widthLimit, 0));
        drawSegmentUnSave(new SKPoint2D(this.startPoint2D, 0, 1), 
                new SKPoint2D(this.startPoint2D, this.widthLimit, 1));

        setFilledColor(SURFACE_COLOR);
        
        drawSegmentUnSave(new SKPoint2D(this.startPoint2D,0,2), new SKPoint2D(this.startPoint2D, 0, 
                HEIGHT_1));
        
        drawSegmentUnSave(new SKPoint2D(this.startPoint2D, this.widthLimit, 2), 
                new SKPoint2D(this.startPoint2D, this.widthLimit, HEIGHT_1));

        // vẽ gợn sóng trên mặt nước
        setFilledColor(COLOR_2);
        
        drawZigZagS(pointList, ROUGH_NUMBER_ARRAY_1, ROUGH_NUMBER_ARRAY_2);

        int length = 27;
        int posX = 23;
        
        setLineStyle(SettingConstants.LineStyle.DASH);

        drawOutlineEllipse(new SKPoint2D(this.startPoint2D, 
                posX, 7), length / 2 + 1, 3, false, true, true, false);
        
        drawOutlineEllipse(new SKPoint2D(this.startPoint2D, 
                posX + length, 7), length / 2 + 1, 5, true, false, false, true);

        length = 20;
        posX = 80;
        
        drawOutlineEllipse(new SKPoint2D(this.startPoint2D, 
                posX, 12), length / 2 + 1, 2, false, true, true, false);
        
        drawOutlineEllipse(new SKPoint2D(this.startPoint2D, 
                posX + length, 12), length / 2 + 1, 4, true, false, false, true);

        length = 22;
        posX = 140;
        
        drawOutlineEllipse(new SKPoint2D(this.startPoint2D, 
                posX, 9), length / 2 + 1, 2, false, true, true, false);
        
        drawOutlineEllipse(new SKPoint2D(this.startPoint2D, 
                posX + length, 9), length / 2 + 1, 4, true, false, false, true);

        //phân viền dưới nước
        setLineStyle(DEFAULT_LINE_STYLE);

        setFilledColor(BOTTOM_COLOR);
        
        drawSegmentUnSave(new SKPoint2D(this.startPoint2D, 0, HEIGHT_1), 
                new SKPoint2D(this.startPoint2D, 0, HEIGHT_2));
        
        drawSegmentUnSave(new SKPoint2D(this.startPoint2D, this.widthLimit, HEIGHT_1), 
                new SKPoint2D(this.startPoint2D, this.widthLimit, HEIGHT_2));
        
        drawSegmentUnSave(new SKPoint2D(this.startPoint2D, 0, HEIGHT_2), 
                new SKPoint2D(this.startPoint2D, this.widthLimit, HEIGHT_2));

        setFilledColor(SURFACE_COLOR);
        Ultility.paint(changedColorOfBoard, markedChangeOfBoard,
                new SKPoint2D(this.startPoint2D, 4, 4), filledColor, false);

        setFilledColor(BOTTOM_COLOR);
        Ultility.paint(changedColorOfBoard, markedChangeOfBoard, 
                new SKPoint2D(this.startPoint2D, 4, HEIGHT_1 + 4), filledColor, 
                false);
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
