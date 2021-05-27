package model.shape2d.animation;

import control.myawt.SKPoint2D;
import control.util.Ultility;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.shape2d.Segment2D;
import model.shape2d.Shape2D;
import model.shape2d.Transform2D;
import model.shape2d.Vector2D;

public class Fish1 extends Shape2D {

    private int widthDirection = -1;

    /**
     * Chiều dài đuôi
     */
    public static final int TAIL_WIDTH_1 = 10;

    /**
     * Chiều rộng đuôi tính từ truc doi xung
     */
    public static final int TAIL_HEIGHT_1 = 6;

    public static final int BOTTOM_FIN_WIDTH_1 = 10;

    public static final int BOTTOM_FIN_HEIGHT_1 = 2;

    public static final int TOP_FIN_WIDTH_1 = 7;

    public static final int TOP_FIN_HEIGHT_1 = 2;

    /**
     * Bán kính lớn ellipse thân cá
     */
    public static final int RADIUS_BODY_F1_X = 15;

    /**
     * Bán kính nhỏ ellipse thân cá
     */
    public static final int RADIUS_BODY_F1_Y = 7;

    /**
     * Bán kính lớn ellipse thân cá
     */
    public static final int RADIUS_HEAD_F1_X = 5;

    /**
     * chiều rộng phần đầu
     */
    public static final int RADIUS_HEAD_F1_Y = 5;

    public static final int MOVE_UP_PIXEL_NUM = 5;

    /*
        Danh sách màu.
     */
    public static final Color MAU_MIENG_CA = new Color(180, 61, 59);
    public static final Color MAU_DA_CA = new Color(242, 236, 171);
    public static final Color MAU_VAN_DUOI_CA = new Color(254, 147, 31);
    public static final Color COLOR_0 = new Color(243, 240, 161);
    public static final Color COLOR_1 = new Color(202, 192, 50);
    public static final Color COLOR_2 = new Color(254, 241, 2);

    /*
        Chỉ số 1 là dịch Y, chỉ số 2 là dịch X!
     */
    public static final int[] FISH_MOUTH_ADJUSTMENT_PIXEL_1 = {1, 1};
    public static final int[] FISH_MOUTH_ADJUSTMENT_PIXEL_2 = {1, 2};
    public static final int[] FISH_MOUTH_ADJUSTMENT_PIXEL_3 = {0, 3};

    /**
     * Độ lệch tạo thành hình bình hành của vây cá
     */
    public static final int LECH_TREN_1 = 3;
    public static final int LECH_DUOI_1 = 3;

    public static final int MOVE_HOR_PIXEL_NUM = 4;
    public static final int MOVE_VER_PIXEL_NUM = 2;

    public static final Vector2D VECTOR_MOVE_HOR_RIGHT = new Vector2D(MOVE_HOR_PIXEL_NUM, 0);
    public static final Vector2D VECTOR_MOVE_HOR_LEFT = new Vector2D(-MOVE_HOR_PIXEL_NUM, 0);
    public static final Vector2D VECTOR_MOVE_VER_UP = new Vector2D(0, MOVE_VER_PIXEL_NUM);
    public static final Vector2D VECTOR_MOVE_VER_DOWN = new Vector2D(0, -MOVE_VER_PIXEL_NUM);

    private SKPoint2D thanCaCenterPoint = new SKPoint2D();
    public  SKPoint2D getThanCaCenterPoint(){
        return this.thanCaCenterPoint;
    }
    private SKPoint2D dauCaCenterPoint = new SKPoint2D();
    public  SKPoint2D getDauCaCenterPoint(){
        return this.dauCaCenterPoint;
    }
    private SKPoint2D duoiStartPoint = new SKPoint2D();
    private SKPoint2D duoiTrenEndPoint = new SKPoint2D();
    private SKPoint2D duoiDuoiEndPoint = new SKPoint2D();
    
    private SKPoint2D tailEdgeCenterPoint = new SKPoint2D();
    public  SKPoint2D getTailEdgeCenterPoint(){
        return this.tailEdgeCenterPoint;
    }

    private SKPoint2D vayTrenStartPoint = new SKPoint2D();
    public  SKPoint2D getVayTrenStartPoint(){
        return this.vayTrenStartPoint;
    }
    private SKPoint2D vayTrenEndPoint = new SKPoint2D();
    private SKPoint2D vayTrenPoint2 = new SKPoint2D();
    private SKPoint2D vayTrenPoint3 = new SKPoint2D();

    private SKPoint2D vayDuoiStartPoint = new SKPoint2D();
    public  SKPoint2D getVayDuoiStartPoint(){
        return this.vayDuoiStartPoint;
    }
    private SKPoint2D vayDuoiPoint2 = new SKPoint2D();
    private SKPoint2D vayDuoiPoint3 = new SKPoint2D();
    private SKPoint2D vayDuoiEndPoint = new SKPoint2D();

    private SKPoint2D skinShapeStartPoint = new SKPoint2D();

    private SKPoint2D headPosToPaint = new SKPoint2D();
    private SKPoint2D bodyPosToPaint1 = new SKPoint2D();
    private SKPoint2D bodyPosToPaint2 = new SKPoint2D();
    private SKPoint2D topFinPosToPaint = new SKPoint2D();
    private SKPoint2D botFinPosToPaint = new SKPoint2D();
    private SKPoint2D tailPosToPaint = new SKPoint2D();

    private SKPoint2D tailFinStartPoint1 = new SKPoint2D();
    private SKPoint2D tailFinStartPoint2 = new SKPoint2D();

    /*
        Các điểm mắt theo thứ tự sau:
        | diemMat00 | diemMat01 |  
        | diemMat10 | diemMat11 |  
        
        Note: widthDirection = -1 thì 2 cột tự đổi chỗ cho nhau
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

    private SKPoint2D fixedAnimateStartPoint = new SKPoint2D();

    private int widthLimit = 0;

    private boolean animatePeriod1 = true;
    private boolean animatePeriod2 = false;
    private boolean animatePeriod3 = false;
    private boolean animatePeriod4 = false;
    private boolean animatePeriod5 = false;

    public Fish1(boolean[][] markedChangeOfBoard, Color[][] changedColorOfBoard,
            String[][] changedCoordOfBoard, Color filledColor) {
        super(markedChangeOfBoard, changedColorOfBoard, changedCoordOfBoard,
                filledColor);
    }

    public void setPropertyFish1(int widthLimit, SKPoint2D startPoint) {
        this.widthLimit = widthLimit;

        this.startPoint2D.setLocation(startPoint);
        this.fixedAnimateStartPoint.setLocation(startPoint);

        setPointLocation();

        pointSet2D.clear();

        // start point is used for anchoring shape
        pointSet2D.add(this.startPoint2D);

        pointSet2D.add(this.centerPoint2D);

        pointSet2D.add(thanCaCenterPoint);
        pointSet2D.add(dauCaCenterPoint);
        pointSet2D.add(duoiStartPoint);
        pointSet2D.add(duoiTrenEndPoint);
        pointSet2D.add(duoiDuoiEndPoint);
        pointSet2D.add(tailEdgeCenterPoint);
        pointSet2D.add(vayTrenStartPoint);
        pointSet2D.add(vayTrenEndPoint);
        pointSet2D.add(vayTrenPoint2);
        pointSet2D.add(vayTrenPoint3);
        pointSet2D.add(vayDuoiStartPoint);
        pointSet2D.add(vayDuoiPoint2);
        pointSet2D.add(vayDuoiPoint3);
        pointSet2D.add(vayDuoiEndPoint);
        pointSet2D.add(skinShapeStartPoint);
        pointSet2D.add(headPosToPaint);
        pointSet2D.add(bodyPosToPaint1);
        pointSet2D.add(bodyPosToPaint2);
        pointSet2D.add(topFinPosToPaint);
        pointSet2D.add(botFinPosToPaint);
        pointSet2D.add(tailPosToPaint);
        pointSet2D.add(tailFinStartPoint1);
        pointSet2D.add(tailFinStartPoint2);

        pointSet2D.add(diemMat00);
        pointSet2D.add(diemMat01);
        pointSet2D.add(diemMat10);
        pointSet2D.add(diemMat11);

        pointSet2D.add(diemMieng1);
        pointSet2D.add(diemMieng2);
        pointSet2D.add(diemMieng3);
    }

    private void setPointLocation() {
        thanCaCenterPoint.setLocation(this.startPoint2D,
                RADIUS_BODY_F1_X * widthDirection, 0);

        dauCaCenterPoint.setLocation(this.startPoint2D,
                RADIUS_HEAD_F1_X * widthDirection, 0);

        duoiStartPoint.setLocation(this.startPoint2D,
                RADIUS_BODY_F1_X * widthDirection * 2, -1);

        duoiTrenEndPoint.setLocation(duoiStartPoint,
                TAIL_WIDTH_1 * widthDirection, -TAIL_HEIGHT_1);

        duoiDuoiEndPoint.setLocation(duoiStartPoint,
                TAIL_WIDTH_1 * widthDirection, TAIL_HEIGHT_1);

        tailEdgeCenterPoint.setLocation(duoiStartPoint,
                TAIL_WIDTH_1 * widthDirection, 0);

        //set điểm bắt đầu là góc dưới, bên trái hình bình hành
        vayTrenStartPoint.setLocation(this.startPoint2D,
                widthDirection * (RADIUS_HEAD_F1_X * 2 + 2),
                -RADIUS_BODY_F1_Y);
        //góc trên bên trái
        vayTrenPoint2.setLocation(vayTrenStartPoint, LECH_TREN_1 * widthDirection,
                -TOP_FIN_HEIGHT_1);

        //góc trên bên phải
        vayTrenPoint3.setLocation(vayTrenStartPoint, TOP_FIN_WIDTH_1 * widthDirection,
                -TOP_FIN_HEIGHT_1);

        //góc dưới bên phải
        vayTrenEndPoint.setLocation(vayTrenStartPoint,
                widthDirection * (TOP_FIN_WIDTH_1 - LECH_TREN_1),
                0);
        //---------------
        //góc trên bên trái
        vayDuoiStartPoint.setLocation(this.startPoint2D,
                widthDirection * (RADIUS_HEAD_F1_X * 2 + 2),
                RADIUS_BODY_F1_Y);

        vayDuoiPoint2.setLocation(vayDuoiStartPoint, LECH_DUOI_1 * widthDirection,
                BOTTOM_FIN_HEIGHT_1);

        //góc dưới bên phải
        vayDuoiPoint3.setLocation(vayDuoiStartPoint, BOTTOM_FIN_WIDTH_1 * widthDirection,
                BOTTOM_FIN_HEIGHT_1);

        //góc trên bên phải
        vayDuoiEndPoint.setLocation(vayDuoiStartPoint,
                widthDirection * (BOTTOM_FIN_WIDTH_1 - LECH_DUOI_1), 0);

        skinShapeStartPoint.setLocation(this.startPoint2D,
                widthDirection * (RADIUS_HEAD_F1_X * 2 + 3), 0);

        // Set các điểm để paint
        headPosToPaint.setLocation(this.startPoint2D, 1 * widthDirection, 0);

        bodyPosToPaint1.setLocation(this.startPoint2D,
                widthDirection * (RADIUS_HEAD_F1_X + 2), -RADIUS_HEAD_F1_Y);

        bodyPosToPaint2.setLocation(this.startPoint2D,
                widthDirection * (RADIUS_HEAD_F1_X + 2), RADIUS_HEAD_F1_Y);

        topFinPosToPaint.setLocation(this.startPoint2D,
                widthDirection * (RADIUS_HEAD_F1_X * 2 + 5),
                -RADIUS_BODY_F1_Y - 1);

        botFinPosToPaint.setLocation(this.startPoint2D,
                widthDirection * (RADIUS_HEAD_F1_X * 2 + 5),
                RADIUS_BODY_F1_Y + 1);

        tailPosToPaint.setLocation(this.startPoint2D,
                widthDirection * (RADIUS_BODY_F1_X * 2 + 1), -1);

        // Note: chỉ cần xét tọa độ cho điểm diemMat00
        diemMat00.setLocation(this.startPoint2D.getCoordX()
                + RADIUS_HEAD_F1_X * widthDirection, this.startPoint2D.getCoordY() - 2);

        diemMat01.setLocation(diemMat00.getCoordX()
                + 1 * widthDirection, diemMat00.getCoordY());

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

        // diem ve van o duoi
        tailFinStartPoint1.setLocation(tailEdgeCenterPoint, -2 * widthDirection, 0);
        tailFinStartPoint2.setLocation(tailEdgeCenterPoint, -4 * widthDirection, 0);

        this.centerPoint2D.setMidLocation(this.startPoint2D, this.tailEdgeCenterPoint);
    }

    public void drawFish1() {
        /* Vẽ thân hình ellipse */
        setFilledColor(Color.BLACK);

        drawOutlineEllipseUnSave(thanCaCenterPoint, RADIUS_BODY_F1_X, RADIUS_BODY_F1_Y,
                true, true, true, true);

        /* Vẽ đầu hình ellipses */
        if (widthDirection == 1) {
            drawOutlineEllipseUnSave(dauCaCenterPoint, RADIUS_HEAD_F1_X, RADIUS_HEAD_F1_Y,
                    true, true, false, false);
        } else {
            drawOutlineEllipseUnSave(dauCaCenterPoint, RADIUS_HEAD_F1_X, RADIUS_HEAD_F1_Y,
                    false, false, true, true);
        }

        /* Vẽ các điểm ở miệng */
        setFilledColor(MAU_MIENG_CA);
        savePoint(diemMieng1);
        savePoint(diemMieng2);
        savePoint(diemMieng3);

        /* Vẽ đuôi là một hình quạt */
        setFilledColor(Color.BLACK);

        /* Vẽ viền là hình ellipse nằm dọc */
        if (widthDirection == 1) {
            drawOutlineEllipseUnSave(tailEdgeCenterPoint, 4, TAIL_HEIGHT_1,
                    true, true, false, false);
        } else {
            drawOutlineEllipseUnSave(tailEdgeCenterPoint, 4, TAIL_HEIGHT_1,
                    false, false, true, true);
        }

        drawSegmentUnSave(duoiStartPoint, duoiTrenEndPoint);
        drawSegmentUnSave(duoiStartPoint, duoiDuoiEndPoint);

        /*vẽ vây trên*/
        drawSegmentUnSave(vayTrenStartPoint, vayTrenPoint2);
        drawSegmentUnSave(vayTrenPoint2, vayTrenPoint3);
        drawSegmentUnSave(vayTrenPoint3, vayTrenEndPoint);

        /*vẽ vây dưới*/
        drawSegmentUnSave(vayDuoiStartPoint, vayDuoiPoint2);
        drawSegmentUnSave(vayDuoiPoint2, vayDuoiPoint3);
        drawSegmentUnSave(vayDuoiPoint3, vayDuoiEndPoint);

        /* vẽ da */
        setFilledColor(MAU_DA_CA);

        /* Điểm bắt đầu ra khỏi phần ellipse ở đầu cá, có Y trùng với tâm đầu cá */
        drawFishSkin1(skinShapeStartPoint, 0, 0);
        drawFishSkin1(skinShapeStartPoint, 3 * widthDirection, 3);
        drawFishSkin1(skinShapeStartPoint, -1 * widthDirection, -4);
        drawFishSkin1(skinShapeStartPoint, 4 * widthDirection, -3);
        drawFishSkin1(skinShapeStartPoint, 8 * widthDirection, -2);
        drawFishSkin1(skinShapeStartPoint, 6 * widthDirection, 1);
        drawFishSkin1(skinShapeStartPoint, 11 * widthDirection, 0);

        /* Vẽ mắt */
        setFilledColor(Color.WHITE);
        savePoint(diemMat01);
        setFilledColor(Color.BLACK);
        savePoint(diemMat11);
        savePoint(diemMat00);
        savePoint(diemMat10);

        /* Tô màu */
        Ultility.paint(changedColorOfBoard, markedChangeOfBoard,
                headPosToPaint, COLOR_0, false);
        Ultility.paint(changedColorOfBoard, markedChangeOfBoard,
                bodyPosToPaint1, COLOR_1, false);
        Ultility.paint(changedColorOfBoard, markedChangeOfBoard,
                bodyPosToPaint2, COLOR_1, false);
        Ultility.paint(changedColorOfBoard, markedChangeOfBoard,
                topFinPosToPaint, COLOR_2, false);
        Ultility.paint(changedColorOfBoard, markedChangeOfBoard,
                botFinPosToPaint, COLOR_2, false);
        Ultility.paint(changedColorOfBoard, markedChangeOfBoard,
                tailPosToPaint, COLOR_2, false);

        /* Vẽ đường vân ở đuôi */
        setFilledColor(MAU_VAN_DUOI_CA);

        if (widthDirection == 1) {
            drawOutlineEllipseUnSave(tailFinStartPoint1, 3, TAIL_HEIGHT_1 - 3,
                    true, true, false, false);
        } else {
            drawOutlineEllipseUnSave(tailFinStartPoint1, 3, TAIL_HEIGHT_1 - 3,
                    false, false, true, true);
        }

        if (widthDirection == 1) {
            drawOutlineEllipseUnSave(tailFinStartPoint2, 3, TAIL_HEIGHT_1 - 4,
                    true, true, false, false);
        } else {
            drawOutlineEllipseUnSave(tailFinStartPoint2, 3, TAIL_HEIGHT_1 - 4,
                    false, false, true, true);
        }

        if (animatePeriod1) {
            move(VECTOR_MOVE_HOR_RIGHT);

            if (this.startPoint2D.getCoordX() >= (widthLimit * 3 / 4)) {
                reflectSelf();
                animatePeriod1 = false;
                animatePeriod2 = true;
            }
        } else if (animatePeriod2) {
            move(VECTOR_MOVE_VER_UP);

            if (this.fixedAnimateStartPoint.getCoordY() - this.startPoint2D.getCoordY()
                    >= MOVE_UP_PIXEL_NUM) {
                animatePeriod2 = false;
                animatePeriod3 = true;
            }
        } else if (animatePeriod3) {
            move(VECTOR_MOVE_HOR_LEFT);

            if (this.startPoint2D.getCoordX() <= widthLimit / 4) {
                reflectSelf();

                animatePeriod3 = false;
                animatePeriod4 = true;
            }
        } else if (animatePeriod4) {
            move(VECTOR_MOVE_VER_UP);

            if (this.fixedAnimateStartPoint.getCoordY() - this.startPoint2D.getCoordY()
                    >= 2 * MOVE_UP_PIXEL_NUM) {
                animatePeriod4 = false;
                animatePeriod5 = true;
            }
        } else if (animatePeriod5) {
            move(VECTOR_MOVE_HOR_RIGHT);
            movePaintPosToTailDirection();
            if (this.startPoint2D.getCoordX() >= widthLimit + 100) {
                this.startPoint2D.setLocation(this.fixedAnimateStartPoint);
                setPointLocation();

                animatePeriod1 = true;
                animatePeriod5 = false;
            }
        }
    }

    /**
     * Vẽ vây
     *
     * @param startPoint
     * @param changedPosX
     * @param changedPosY
     */
    private void drawFishSkin1(SKPoint2D startPoint, int changedPosX, int changedPosY) {
        SKPoint2D newStartPoint = new SKPoint2D(startPoint, changedPosX, changedPosY);

        SKPoint2D endPoint1 = new SKPoint2D(
                newStartPoint.getCoordX() - 1 * widthDirection,
                newStartPoint.getCoordY() - 1 * widthDirection
        );

        SKPoint2D endPoint2 = new SKPoint2D(
                newStartPoint.getCoordX() - 1 * widthDirection,
                newStartPoint.getCoordY() + 1 * widthDirection
        );

        drawSegmentUnSave(newStartPoint, endPoint1);
        drawSegmentUnSave(newStartPoint, endPoint2);
    }

    private void reflectSelf() {
        double[][] verSymMat = Transform2D.getVerSymMat(this.centerPoint2D.getCoordX());

        for (int i = 0; i < pointSet2D.size(); ++i) {
            Transform2D.transform(pointSet2D.get(i), verSymMat);
        }

        this.widthDirection = -this.widthDirection;
    }

    private void movePaintPosToTailDirection() {
        headPosToPaint.setLocation(this.startPoint2D, (1 + RADIUS_HEAD_F1_X * 2 - 2) * widthDirection, 0);

        bodyPosToPaint1.setLocation(this.startPoint2D,
                widthDirection * (RADIUS_BODY_F1_X * 2 - 1), 0);

        bodyPosToPaint2.setLocation(this.startPoint2D,
                widthDirection * (RADIUS_BODY_F1_X * 2 - 1), 0);

        topFinPosToPaint.setLocation(this.startPoint2D,
                widthDirection * (RADIUS_HEAD_F1_X * 2 + 5 + 1),
                -RADIUS_BODY_F1_Y - 1);

        botFinPosToPaint.setLocation(this.startPoint2D,
                widthDirection * (RADIUS_HEAD_F1_X * 2 + 5 + 4),
                RADIUS_BODY_F1_Y + 1);

        tailPosToPaint.setLocation(this.startPoint2D,
                widthDirection * (RADIUS_BODY_F1_X * 2 + TAIL_WIDTH_1 + 2), -1);
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
    public void applyMove(Vector2D vector) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
