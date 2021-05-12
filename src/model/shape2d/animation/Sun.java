/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.shape2d.animation;

import control.util.Ultility;
import java.awt.Color;
import java.util.ArrayList;
import control.myawt.SKPoint2D;
import model.shape2d.Segment2D;
import model.shape2d.Shape2D;
import model.shape2d.Vector2D;

public class Sun extends Shape2D {

    public static final int SUNLINE_NUM = 8;

    public static final Color COLOR_0 = new Color(245, 120, 60);
    public static final Color COLOR_1 = new Color(255, 255, 125);
    public static final Color COLOR_2 = new Color(255, 255, 0);

    private ArrayList<SKPoint2D> pointList = new ArrayList<>();

    private SKPoint2D traveringPoint_StartP = new SKPoint2D();
    private SKPoint2D traveringPoint_EndP = new SKPoint2D();

    private SKPoint2D[] sunLine_StartP = new SKPoint2D[SUNLINE_NUM];
    private SKPoint2D[] sunLine_EndP = new SKPoint2D[SUNLINE_NUM];
    private Segment2D[] sunLine2 = new Segment2D[SUNLINE_NUM];

    /**
     * Use for change sunny rotation mechanism.
     */
    private boolean flip = false;

    public Sun(boolean[][] markedChangeOfBoard, Color[][] changedColorOfBoard,
            String[][] changedCoordOfBoard, Color filledColor) {
        super(markedChangeOfBoard, changedColorOfBoard, changedCoordOfBoard,
                filledColor);

        for (int i = 0; i < SUNLINE_NUM; i++) {
            sunLine2[i] = new Segment2D(markedChangeOfBoard, changedColorOfBoard,
                    changedCoordOfBoard, filledColor);
        }
    }

    public void setProperty(SKPoint2D centerPoint) {
        this.centerPoint2D = centerPoint;

        pointList.add(new SKPoint2D(this.centerPoint2D, 2, 6));
        pointList.add(new SKPoint2D(this.centerPoint2D, 2, 5));
        pointList.add(new SKPoint2D(this.centerPoint2D, 3, 5));
        pointList.add(new SKPoint2D(this.centerPoint2D, 3, 1));

        pointList.add(new SKPoint2D(this.centerPoint2D, 5, 1));
        pointList.add(new SKPoint2D(this.centerPoint2D, 5, -4));

        pointList.add(new SKPoint2D(this.centerPoint2D, 4, -4));
        pointList.add(new SKPoint2D(this.centerPoint2D, 4, -5));
        pointList.add(new SKPoint2D(this.centerPoint2D, 2, -5));
        pointList.add(new SKPoint2D(this.centerPoint2D, 2, -6));
        pointList.add(new SKPoint2D(this.centerPoint2D, -2, -6));
        pointList.add(new SKPoint2D(this.centerPoint2D, -2, -3));
        pointList.add(new SKPoint2D(this.centerPoint2D, -5, -3));
        pointList.add(new SKPoint2D(this.centerPoint2D, -5, 4));
        pointList.add(new SKPoint2D(this.centerPoint2D, -3, 4));
        pointList.add(new SKPoint2D(this.centerPoint2D, -3, 5));

        for (int i = 0; i < 8; i++) {
            sunLine2[i].setFilledColor(COLOR_0);
        }

        traveringPoint_StartP.setLocation(this.centerPoint2D, 0, -12);
        traveringPoint_EndP.setLocation(this.centerPoint2D, 0, -18);

    }

    public void drawSun() {
        setFilledColor(COLOR_0);

        drawOutlineCircle(7, this.centerPoint2D, true, true, true, true, true, true, true, true);
        paintSun();
    }

    public void paintSun() {
        setFilledColor(COLOR_1);

        // tô nền => tạo viền => tô lại màu trong viền
        Ultility.paint(changedColorOfBoard, markedChangeOfBoard, this.centerPoint2D, filledColor, false);

        setFilledColor(COLOR_2);

        drawZigZag(pointList);

        Ultility.paint(changedColorOfBoard, markedChangeOfBoard, this.centerPoint2D, filledColor, false);
    }

    public void drawSunLight(int rotation) {

        setFilledColor(Color.BLACK);
        
        for (int i = 0; i < 8; i++) {
            sunLine_StartP[i] = traveringPoint_StartP.createRotate(this.centerPoint2D, Math.toRadians(i * 45 + rotation));
            sunLine_EndP[i] = traveringPoint_EndP.createRotate(this.centerPoint2D, Math.toRadians(i * 45 + rotation));
            sunLine2[i].setProperty(sunLine_StartP[i].createRotate(this.centerPoint2D, Math.toRadians(rotation)),
                    sunLine_EndP[i].createRotate(this.centerPoint2D, Math.toRadians(rotation)));
            sunLine2[i].drawOutline();

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
