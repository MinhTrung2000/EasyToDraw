package model.shape2d.animation;

import control.myawt.SKPoint2D;
import control.util.Ultility;
import java.awt.Color;
import java.util.ArrayList;
import model.shape2d.Shape2D;
import model.shape2d.Transform2D;
import model.shape2d.Vector2D;

public class Fish2 extends Shape2D {

    private int widthDirection = -1;

    //chiều dài và rộng thân hình
    public static final int RADIUS_BODY_F2_X = 20;
    public static final int RADIUS_BODY_F2_Y = 6;

    // khoảng cách (X) từ startPoint2D tới tâm của vây trên thân
    public static final int DISTANCE_FIN_F2_X = 13;

    // dài và rộng của vây trên thân
    public static final int RADIUS_FIN_F2_X = 4;
    public static final int RADIUS_FIN_F2_Y = 2;

    // dài rộng của vân cá trên thân
    public static final int SKIN_SHAPE_F2_X = 1;
    public static final int SKIN_SHAPE_F2_Y = 3;

    public static final int TOP_FIN_HEIGHT_2 = 4;
    public static final int TOP_FIN_WIDTH_2 = 20;

    public static final int BOTTOM_FIN_HEIGHT_2 = 3;
    public static final int BOTTOM_FIN_WIDTH_2 = 14;

    public static final int TAIL_WIDTH_2 = 10;
    public static final int TAIL_HEIGHT_2 = 8;
    public static final int LECH_TREN_2 = 4;
    public static final int LECH_DUOI_2 = 4;
    //COLOR
    public static final Color FIN_SHAPE_COLOR = new Color(210, 255, 255);
    public static final Color SKIN_COLOR = new Color(0, 152, 255);
    public static final Color FISH_MOUTH_COLOR = new Color(180, 61, 59);

    /*
        Chỉ số 1 là dịch Y, chỉ số 2 là dịch X!
     */
    public static final int[] FISH_MOUTH_ADJUSTMENT_PIXEL_1 = {1, 1};
    public static final int[] FISH_MOUTH_ADJUSTMENT_PIXEL_2 = {1, 2};
    public static final int[] FISH_MOUTH_ADJUSTMENT_PIXEL_3 = {0, 3};

    public static int MOVE_HOR_PIXEL_NUM = 3;

    public static Vector2D VECTOR_MOVE_HOR_RIGHT = new Vector2D(MOVE_HOR_PIXEL_NUM, 0);
    public static Vector2D VECTOR_MOVE_HOR_LEFT = new Vector2D(-MOVE_HOR_PIXEL_NUM, 0);

    private SKPoint2D thanCaCenterPoint = new SKPoint2D();
    private SKPoint2D vayTT2CenterPoint = new SKPoint2D();
    private SKPoint2D skinShapeCenterPoint1 = new SKPoint2D();
    private SKPoint2D skinShapeCenterPoint2 = new SKPoint2D();
    private SKPoint2D skinShapeCenterPoint3 = new SKPoint2D();
    private SKPoint2D vayTrenStartPoint = new SKPoint2D();
    private SKPoint2D vayTrenPoint2 = new SKPoint2D();
    private SKPoint2D vayTrenPoint3 = new SKPoint2D();
    private SKPoint2D vayTrenEndPoint = new SKPoint2D();
    private SKPoint2D vayDuoi2StartPoint = new SKPoint2D();
    private SKPoint2D vayDuoiPoint2 = new SKPoint2D();
    private SKPoint2D vayDuoiPoint3 = new SKPoint2D();
    private SKPoint2D vayDuoiEndPoint = new SKPoint2D();

    private SKPoint2D duoiStartPoint = new SKPoint2D();
    private SKPoint2D duoiEndPoint = new SKPoint2D();

    private SKPoint2D duoiTrenEndPoint = new SKPoint2D();
    private SKPoint2D duoiDuoiEndPoint = new SKPoint2D();

    //điểm bắt đầu (cùng tọa độ X với điểm kết thúc ở trên), đường thẳng ở trong
    private SKPoint2D duoiTrenStartPoint2 = new SKPoint2D();
    private SKPoint2D duoiDuoiStartPoint2 = new SKPoint2D();

    //tâm của 2 đường ellipse ngoài rìa đuôi cá
    private SKPoint2D edgeDuoiTrenCenterPoint = new SKPoint2D();
    private SKPoint2D edgeDuoiDuoiCenterPoint = new SKPoint2D();

    //Point to Paint
    private SKPoint2D bodyPosToPaint = new SKPoint2D();
    private SKPoint2D topFinPosToPaint = new SKPoint2D();
    private SKPoint2D botFinPosToPaint = new SKPoint2D();
    private SKPoint2D tailPosToPaint = new SKPoint2D();

    //Drawing after painting
    private SKPoint2D[] topFinStartPoints = new SKPoint2D[4];
    private SKPoint2D[] topFinEndPoints = new SKPoint2D[4];

    private SKPoint2D firstBotFinFPStartPoint = new SKPoint2D();
    private SKPoint2D firstBotFinFPEndPoint = new SKPoint2D();
    private SKPoint2D[] botFinStartPoints = new SKPoint2D[3];
    private SKPoint2D[] botFinEndPoints = new SKPoint2D[3];

    private SKPoint2D firstBotTailFPStartPoint = new SKPoint2D();
    private SKPoint2D firstBotTailFPEndPoint = new SKPoint2D();
    private SKPoint2D secondBotTailFPStartPoint = new SKPoint2D();
    private SKPoint2D secondBotTailFPEndPoint = new SKPoint2D();
    private SKPoint2D firstTopTailFPStartPoint = new SKPoint2D();
    private SKPoint2D firstTopTailFPEndPoint = new SKPoint2D();
    private SKPoint2D secondTopTailFPStartPoint = new SKPoint2D();
    private SKPoint2D secondTopTailFPEndPoint = new SKPoint2D();

    /*
        Các điểm mắt theo thứ tự sau:
        | diemMat00 | diemMat01 |  
        | diemMat10 | diemMat11 |  
     */
    private SKPoint2D diemMat00 = new SKPoint2D();
    private SKPoint2D diemMat01 = new SKPoint2D();
    private SKPoint2D diemMat10 = new SKPoint2D();
    private SKPoint2D diemMat11 = new SKPoint2D();

    /*
        Ba điểm miệng.
     */
    private SKPoint2D diemMieng1 = new SKPoint2D();
    private SKPoint2D diemMieng2 = new SKPoint2D();
    private SKPoint2D diemMieng3 = new SKPoint2D();

    private boolean animatePeriod1 = true;
    private boolean animatePeriod2 = false;

    private SKPoint2D animateStartPoint = new SKPoint2D();

    private int widthLimit = 0;

    public Fish2(boolean[][] markedChangeOfBoard, Color[][] changedColorOfBoard,
            String[][] changedCoordOfBoard, Color filledColor) {
        super(markedChangeOfBoard, changedColorOfBoard, changedCoordOfBoard,
                filledColor);
    }

    public void setPropertyFish2(int widthLimit, SKPoint2D startPoint) {
        this.widthLimit = widthLimit;

        this.startPoint2D.setLocation(startPoint);
        this.animateStartPoint.setLocation(startPoint);

        thanCaCenterPoint.setLocation(this.startPoint2D,
                RADIUS_BODY_F2_X * widthDirection, 0);

        vayTT2CenterPoint.setLocation(this.startPoint2D,
                widthDirection * DISTANCE_FIN_F2_X, 0);

        skinShapeCenterPoint1.setLocation(this.startPoint2D,
                widthDirection * (DISTANCE_FIN_F2_X + RADIUS_FIN_F2_X + 5), 0);

        vayTrenStartPoint.setLocation(this.startPoint2D,
                widthDirection * (DISTANCE_FIN_F2_X - 3), -RADIUS_BODY_F2_Y);

        vayTrenPoint2.setLocation(vayTrenStartPoint,
                LECH_TREN_2 * widthDirection, -TOP_FIN_HEIGHT_2);

        vayTrenPoint3.setLocation(vayTrenStartPoint,
                widthDirection * TOP_FIN_WIDTH_2, -TOP_FIN_HEIGHT_2);

        vayTrenEndPoint.setLocation(vayTrenStartPoint,
                widthDirection * TOP_FIN_WIDTH_2 - LECH_TREN_2 * widthDirection, 0);

        vayDuoi2StartPoint.setLocation(this.startPoint2D,
                widthDirection * (DISTANCE_FIN_F2_X - 1), RADIUS_BODY_F2_Y);

        vayDuoiPoint2.setLocation(vayDuoi2StartPoint,
                LECH_DUOI_2 * widthDirection, +BOTTOM_FIN_HEIGHT_2);

        vayDuoiPoint3.setLocation(vayDuoi2StartPoint,
                widthDirection * BOTTOM_FIN_WIDTH_2, BOTTOM_FIN_HEIGHT_2);

        vayDuoiEndPoint.setLocation(vayDuoi2StartPoint,
                widthDirection * BOTTOM_FIN_WIDTH_2 - LECH_DUOI_2 * widthDirection, 0);

        skinShapeCenterPoint2.setLocation(skinShapeCenterPoint1.getCoordX()
                + 4 * widthDirection, skinShapeCenterPoint1.getCoordY());

        skinShapeCenterPoint3.setLocation(skinShapeCenterPoint2.getCoordX()
                + 4 * widthDirection, skinShapeCenterPoint2.getCoordY());

        duoiStartPoint.setLocation(this.startPoint2D,
                widthDirection * (RADIUS_BODY_F2_X * 2 - 1), 0);

        duoiTrenEndPoint.setLocation(duoiStartPoint,
                widthDirection * TAIL_WIDTH_2, -TAIL_HEIGHT_2);

        duoiDuoiEndPoint.setLocation(duoiStartPoint,
                widthDirection * TAIL_WIDTH_2, TAIL_HEIGHT_2);

        edgeDuoiTrenCenterPoint.setLocation(duoiTrenEndPoint, 0, 2);

        duoiTrenStartPoint2.setLocation(duoiTrenEndPoint, 0, 4);

        duoiEndPoint.setLocation(duoiStartPoint, widthDirection * 4, 0);

        edgeDuoiDuoiCenterPoint.setLocation(edgeDuoiTrenCenterPoint, 0,
                TAIL_HEIGHT_2 * 2 - 4);

        duoiDuoiStartPoint2.setLocation(duoiTrenStartPoint2.getCoordX(),
                edgeDuoiTrenCenterPoint.getCoordY() + TAIL_HEIGHT_2 * 2 - 6);

        //Paint
        bodyPosToPaint.setLocation(this.startPoint2D, widthDirection * 2, 0);

        topFinPosToPaint.setLocation(this.startPoint2D,
                widthDirection * (DISTANCE_FIN_F2_X - 3 + 2), -RADIUS_BODY_F2_Y - 1);

        botFinPosToPaint.setLocation(this.startPoint2D,
                widthDirection * (DISTANCE_FIN_F2_X - 1 + 2), RADIUS_BODY_F2_Y + 1);

        tailPosToPaint.setLocation(this.startPoint2D,
                widthDirection * (RADIUS_BODY_F2_X * 2 - 1 + 2), 0);

        topFinStartPoints[0] = new SKPoint2D(this.startPoint2D,
                widthDirection * (DISTANCE_FIN_F2_X - 3 + 5), -RADIUS_BODY_F2_Y - 1);

        topFinEndPoints[0] = new SKPoint2D(topFinStartPoints[0],
                2 * widthDirection, -TOP_FIN_HEIGHT_2 + 2);

        for (int i = 1; i < 4; i++) {
            topFinStartPoints[i] = new SKPoint2D(topFinStartPoints[i - 1],
                    3 * widthDirection, 0);

            topFinEndPoints[i] = new SKPoint2D(topFinStartPoints[i],
                    2 * widthDirection, -TOP_FIN_HEIGHT_2 + 2);
        }

        botFinStartPoints[0] = new SKPoint2D(this.startPoint2D,
                widthDirection * (DISTANCE_FIN_F2_X - 1 + 3), RADIUS_BODY_F2_Y + 1);

        botFinEndPoints[0] = new SKPoint2D(botFinStartPoints[0],
                widthDirection * 1, +BOTTOM_FIN_HEIGHT_2 - 2);

        for (int i = 1; i < 3; i++) {
            botFinStartPoints[i] = new SKPoint2D(botFinStartPoints[i - 1],
                    widthDirection * 3, 0);

            botFinEndPoints[i] = new SKPoint2D(botFinStartPoints[i],
                    widthDirection * 1, BOTTOM_FIN_HEIGHT_2 - 2);
        }

        firstBotFinFPStartPoint.setLocation(this.startPoint2D,
                widthDirection * (DISTANCE_FIN_F2_X - 1 + 3), RADIUS_BODY_F2_Y + 1);

        firstBotFinFPEndPoint.setLocation(firstBotFinFPStartPoint, 1,
                BOTTOM_FIN_HEIGHT_2 - 2);

        firstBotTailFPStartPoint.setLocation(this.startPoint2D,
                widthDirection * (RADIUS_BODY_F2_X * 2 - 1 + 6), 2);

        firstBotTailFPEndPoint.setLocation(firstBotTailFPStartPoint,
                widthDirection * 1, 3);

        secondBotTailFPStartPoint.setLocation(firstBotTailFPStartPoint,
                widthDirection * 3, 2);

        secondBotTailFPEndPoint.setLocation(secondBotTailFPStartPoint,
                widthDirection * 1, 3);

        firstTopTailFPStartPoint.setLocation(this.startPoint2D,
                widthDirection * (RADIUS_BODY_F2_X * 2 - 1 + 6), -2);

        firstTopTailFPEndPoint.setLocation(firstTopTailFPStartPoint,
                widthDirection * 1, -3);

        secondTopTailFPStartPoint.setLocation(firstTopTailFPStartPoint,
                widthDirection * 3, -2);

        secondTopTailFPEndPoint.setLocation(secondTopTailFPStartPoint,
                widthDirection * 1, -3);

        // Set cac diem mat
        // Note: chỉ cần xét tọa độ cho điểm diemMat00
        diemMat00.setLocation(this.startPoint2D.getCoordX()
                + (DISTANCE_FIN_F2_X / 2) * widthDirection,
                this.startPoint2D.getCoordY() - 2);

        diemMat01.setLocation(diemMat00.getCoordX() + 1 * widthDirection,
                diemMat00.getCoordY());

        diemMat10.setLocation(diemMat00.getCoordX(), diemMat00.getCoordY() + 1);

        diemMat11.setLocation(diemMat01.getCoordX(), diemMat10.getCoordY());

        /* Vẽ các điểm ở miệng */
        diemMieng1.setLocation(this.startPoint2D.getCoordX()
                + FISH_MOUTH_ADJUSTMENT_PIXEL_1[1] * widthDirection,
                this.startPoint2D.getCoordY() + FISH_MOUTH_ADJUSTMENT_PIXEL_1[0]);

        diemMieng2.setLocation(this.startPoint2D.getCoordX()
                + FISH_MOUTH_ADJUSTMENT_PIXEL_2[1] * widthDirection,
                this.startPoint2D.getCoordY() + FISH_MOUTH_ADJUSTMENT_PIXEL_2[0]);

        diemMieng3.setLocation(this.startPoint2D.getCoordX()
                + FISH_MOUTH_ADJUSTMENT_PIXEL_3[1] * widthDirection,
                this.startPoint2D.getCoordY() + FISH_MOUTH_ADJUSTMENT_PIXEL_3[0]);

        this.centerPoint2D.midPoint(this.startPoint2D, this.firstBotTailFPStartPoint);
        //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        pointSet2D.clear();
        pointSet2D.add(this.startPoint2D);
        pointSet2D.add(this.centerPoint2D);

        pointSet2D.add(thanCaCenterPoint);
        pointSet2D.add(vayTT2CenterPoint);
        pointSet2D.add(skinShapeCenterPoint1);
        pointSet2D.add(skinShapeCenterPoint2);
        pointSet2D.add(skinShapeCenterPoint3);
        pointSet2D.add(vayTrenStartPoint);
        pointSet2D.add(vayTrenPoint2);
        pointSet2D.add(vayTrenPoint3);
        pointSet2D.add(vayTrenEndPoint);
        pointSet2D.add(vayDuoi2StartPoint);
        pointSet2D.add(vayDuoiPoint2);
        pointSet2D.add(vayDuoiPoint3);
        pointSet2D.add(vayDuoiEndPoint);
        pointSet2D.add(duoiStartPoint);
        pointSet2D.add(duoiEndPoint);
        pointSet2D.add(duoiTrenEndPoint);
        pointSet2D.add(duoiDuoiEndPoint);
        pointSet2D.add(duoiTrenStartPoint2);
        pointSet2D.add(duoiDuoiStartPoint2);
        pointSet2D.add(edgeDuoiTrenCenterPoint);
        pointSet2D.add(edgeDuoiDuoiCenterPoint);
        pointSet2D.add(bodyPosToPaint);
        pointSet2D.add(topFinPosToPaint);
        pointSet2D.add(botFinPosToPaint);
        pointSet2D.add(tailPosToPaint);

        pointSet2D.add(firstBotFinFPStartPoint);
        pointSet2D.add(firstBotFinFPStartPoint);
        pointSet2D.add(firstBotTailFPStartPoint);
        pointSet2D.add(firstBotTailFPEndPoint);
        pointSet2D.add(secondBotTailFPStartPoint);
        pointSet2D.add(secondBotTailFPEndPoint);
        pointSet2D.add(firstTopTailFPStartPoint);
        pointSet2D.add(firstTopTailFPEndPoint);
        pointSet2D.add(secondTopTailFPStartPoint);
        pointSet2D.add(secondTopTailFPEndPoint);

        pointSet2D.add(diemMat00);
        pointSet2D.add(diemMat01);
        pointSet2D.add(diemMat10);
        pointSet2D.add(diemMat11);

        pointSet2D.add(diemMieng1);
        pointSet2D.add(diemMieng2);
        pointSet2D.add(diemMieng3);
    }

    public void drawFish2() {
        //thân
        setFilledColor(Color.BLACK);

        drawOutlineEllipseUnSave(thanCaCenterPoint, RADIUS_BODY_F2_X, RADIUS_BODY_F2_Y,
                true, true, true, true);

        //vây trên thân
        setFilledColor(FIN_SHAPE_COLOR);

        if (widthDirection == 1) {
            drawOutlineEllipseUnSave(vayTT2CenterPoint, RADIUS_FIN_F2_X,
                    RADIUS_FIN_F2_Y, true, true, false, false);
        } else {
            drawOutlineEllipseUnSave(vayTT2CenterPoint, RADIUS_FIN_F2_X,
                    RADIUS_FIN_F2_Y, false, false, true, true);
        }

        // da trên thân
        if (widthDirection == 1) {
            drawOutlineEllipseUnSave(skinShapeCenterPoint1, SKIN_SHAPE_F2_X,
                    SKIN_SHAPE_F2_Y, false, false, true, true);

            drawOutlineEllipseUnSave(skinShapeCenterPoint2, SKIN_SHAPE_F2_X,
                    SKIN_SHAPE_F2_Y, false, false, true, true);

            drawOutlineEllipseUnSave(skinShapeCenterPoint3, SKIN_SHAPE_F2_X,
                    SKIN_SHAPE_F2_Y, false, false, true, true);
        } else {
            drawOutlineEllipseUnSave(skinShapeCenterPoint1, SKIN_SHAPE_F2_X,
                    SKIN_SHAPE_F2_Y, true, true, false, false);

            drawOutlineEllipseUnSave(skinShapeCenterPoint2, SKIN_SHAPE_F2_X,
                    SKIN_SHAPE_F2_Y, true, true, false, false);

            drawOutlineEllipseUnSave(skinShapeCenterPoint3, SKIN_SHAPE_F2_X,
                    SKIN_SHAPE_F2_Y, true, true, false, false);
        }

        // vây trên
        setFilledColor(Color.BLACK);
        drawSegmentUnSave(vayTrenStartPoint, vayTrenPoint2);
        drawSegmentUnSave(vayTrenPoint2, vayTrenPoint3);
        drawSegmentUnSave(vayTrenPoint3, vayTrenEndPoint);

        //vây dưới
        drawSegmentUnSave(vayDuoi2StartPoint, vayDuoiPoint2);
        drawSegmentUnSave(vayDuoiPoint2, vayDuoiPoint3);
        drawSegmentUnSave(vayDuoiPoint3, vayDuoiEndPoint);

        //đuôi
        setFilledColor(Color.BLACK);

        drawSegmentUnSave(duoiStartPoint, duoiTrenEndPoint);
        drawSegmentUnSave(duoiStartPoint, duoiDuoiEndPoint);

        if (widthDirection == 1) {
            drawOutlineEllipseUnSave(edgeDuoiTrenCenterPoint, 2, 2, true, true, false,
                    false);
        } else {
            drawOutlineEllipseUnSave(edgeDuoiTrenCenterPoint, 2, 2, false, false, true,
                    true);
        }

        drawSegmentUnSave(duoiTrenStartPoint2, duoiEndPoint);

        if (widthDirection == 1) {
            drawOutlineEllipseUnSave(edgeDuoiDuoiCenterPoint, 2, 2, true, true, false,
                    false);
        } else {
            drawOutlineEllipseUnSave(edgeDuoiDuoiCenterPoint, 2, 2, false, false, true,
                    true);
        }

        //lấy tọa độ của edge_duoiTren_CenterP -4 ở trên -2 thêm 2 cho nhanh :3
        drawSegmentUnSave(duoiDuoiStartPoint2, duoiEndPoint);

        /* TÔ MÀU */
        //tô thân
        Ultility.paint(changedColorOfBoard, markedChangeOfBoard, bodyPosToPaint,
                SKIN_COLOR, false);

        //tô vây và đuôi
        Ultility.paint(changedColorOfBoard, markedChangeOfBoard, topFinPosToPaint,
                SKIN_COLOR, false);

        Ultility.paint(changedColorOfBoard, markedChangeOfBoard, botFinPosToPaint,
                SKIN_COLOR, false);

        Ultility.paint(changedColorOfBoard, markedChangeOfBoard, tailPosToPaint,
                SKIN_COLOR, false);

        //vẽ vân ở vây và đuôi
        setFilledColor(FIN_SHAPE_COLOR);

        //vân trên
        for (int i = 0; i < 4; i++) {
            drawSegmentUnSave(topFinStartPoints[i], topFinEndPoints[i]);
        }

        // vân dưới
        for (int i = 0; i < 3; i++) {
            drawSegmentUnSave(botFinStartPoints[i], botFinEndPoints[i]);
        }

        // đuôi
        drawSegmentUnSave(firstBotTailFPStartPoint, firstBotTailFPEndPoint);

        drawSegmentUnSave(secondBotTailFPStartPoint, secondBotTailFPEndPoint);

        drawSegmentUnSave(firstTopTailFPStartPoint, firstTopTailFPEndPoint);

        drawSegmentUnSave(secondTopTailFPStartPoint, secondTopTailFPEndPoint);

        /* Vẽ mắt */
        setFilledColor(Color.WHITE);
        savePoint(diemMat01);

        setFilledColor(Color.BLACK);
        savePoint(diemMat11);
        savePoint(diemMat00);
        savePoint(diemMat10);

        /* Vẽ miệng */
        setFilledColor(FISH_MOUTH_COLOR);
        savePoint(diemMieng1);
        savePoint(diemMieng2);
        savePoint(diemMieng3);

        if (animatePeriod1) {
            move(VECTOR_MOVE_HOR_RIGHT);

            if (this.startPoint2D.getCoordX() >= this.widthLimit) {
                reflectSelf();
                animatePeriod1 = false;
                animatePeriod2 = true;
            }
        } else if (animatePeriod2) {
            move(VECTOR_MOVE_HOR_LEFT);
            
            if (this.startPoint2D.getCoordX() <= 0) {
                reflectSelf();
                animatePeriod1 = true;
                animatePeriod2 = false;
            }
        }
    }

    private void reflectSelf() {
        double[][] verSymMat = Transform2D.getVerSymMat(this.centerPoint2D.getCoordX());

        for (int i = 0; i < pointSet2D.size(); ++i) {
            Transform2D.transform(pointSet2D.get(i), verSymMat);
        }

        this.widthDirection = -this.widthDirection;
    }

    @Override
    public void drawOutline() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void saveCoordinates() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void applyMove(Vector2D vector
    ) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
