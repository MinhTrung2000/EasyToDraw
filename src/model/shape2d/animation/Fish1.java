package model.shape2d.animation;

import control.myawt.SKPoint2D;
import control.util.Ultility;
import java.awt.Color;
import model.shape2d.Shape2D;
import model.shape2d.Vector2D;

public class Fish1 extends Shape2D {

    /* FISH 1 */
    private int widthDirection = 1;

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
    private SKPoint2D savedPoint = new SKPoint2D();

    private SKPoint2D startPointFish1 = new SKPoint2D();
    private SKPoint2D startPointFish2 = new SKPoint2D();

    private SKPoint2D thanCa1_centerP = new SKPoint2D();
    private SKPoint2D dauCa1_CenterP = new SKPoint2D();

    /*
        Danh sách màu.
     */
    public static final Color MAU_MIENG_CA = new Color(180, 61, 59);
    public static final Color MAU_DA_CA = new Color(242, 236, 171);
    public static final Color MAU_VAN_DUOI_CA = new Color(254, 147, 31);

    /*
        Chỉ số 1 là dịch Y, chỉ số 2 là dịch X!
     */
    public static final int[] FISH_MOUTH_ADJUSTMENT_PIXEL_1 = {1, 1};
    public static final int[] FISH_MOUTH_ADJUSTMENT_PIXEL_2 = {1, 2};
    public static final int[] FISH_MOUTH_ADJUSTMENT_PIXEL_3 = {0, 3};

    private SKPoint2D duoi1_StartP = new SKPoint2D();
    private SKPoint2D duoiTren1_EndP = new SKPoint2D();
    private SKPoint2D duoiDuoi1_EndP = new SKPoint2D();
    private SKPoint2D tailEdge1_CenterP = new SKPoint2D();

    /**
     * Độ lệch tạo thành hình bình hành của vây cá
     */
    public static final int LECH_TREN_1 = 3;
    public static final int LECH_DUOI_1 = 3;

    private SKPoint2D vayTren1_StartP = new SKPoint2D();
    private SKPoint2D vayTren1_P2 = new SKPoint2D();
    private SKPoint2D vayTren1_P3 = new SKPoint2D();
    private SKPoint2D vayTren1_EndP = new SKPoint2D();

    private SKPoint2D vayDuoi1_StartP = new SKPoint2D();
    private SKPoint2D vayDuoi1_P2 = new SKPoint2D();
    private SKPoint2D vayDuoi1_P3 = new SKPoint2D();
    private SKPoint2D vayDuoi1_EndP = new SKPoint2D();

    private SKPoint2D skinShape1_Start = new SKPoint2D();

    private SKPoint2D headPosToPaint = new SKPoint2D();
    private SKPoint2D bodyPosToPaint = new SKPoint2D();
    private SKPoint2D topFinPosToPaint = new SKPoint2D();
    private SKPoint2D botFinPosToPaint = new SKPoint2D();
    private SKPoint2D tailPosToPaint = new SKPoint2D();

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

    public Fish1(boolean[][] markedChangeOfBoard, Color[][] changedColorOfBoard, String[][] changedCoordOfBoard, Color filledColor) {
        super(markedChangeOfBoard, changedColorOfBoard, changedCoordOfBoard, filledColor);
    }

    public void setPropertyFish1(SKPoint2D startPoint1) {
        this.startPointFish1 = startPoint1;

        thanCa1_centerP.setLocation(startPointFish1, RADIUS_BODY_F1_X * widthDirection, 0);
        dauCa1_CenterP.setLocation(startPointFish1, RADIUS_HEAD_F1_X * widthDirection, 0);
        duoi1_StartP.setLocation(startPointFish1, RADIUS_BODY_F1_X * widthDirection * 2, -1);
        duoiTren1_EndP.setLocation(duoi1_StartP, TAIL_WIDTH_1 * widthDirection, -TAIL_HEIGHT_1);
        duoiDuoi1_EndP.setLocation(duoi1_StartP, TAIL_WIDTH_1 * widthDirection, TAIL_HEIGHT_1);
        tailEdge1_CenterP.setLocation(duoi1_StartP, TAIL_WIDTH_1 * widthDirection, 0);

        savedPoint.setLocation(tailEdge1_CenterP);

        //điểm bắt đầu là góc dưới, bên trái hình bình hành
        vayTren1_StartP.setLocation(startPointFish1,
                RADIUS_HEAD_F1_X * widthDirection * 2 + 2 * widthDirection,
                -RADIUS_BODY_F1_Y);
        //góc trên bên trái
        vayTren1_P2.setLocation(vayTren1_StartP, LECH_TREN_1 * widthDirection,
                -TOP_FIN_HEIGHT_1);
        //góc trên bên phải
        vayTren1_P3.setLocation(vayTren1_StartP, TOP_FIN_WIDTH_1 * widthDirection,
                -TOP_FIN_HEIGHT_1);
        //góc dưới bên phải
        vayTren1_EndP.setLocation(vayTren1_StartP,
                TOP_FIN_WIDTH_1 * widthDirection - LECH_TREN_1 * widthDirection,
                0);

        //góc trên bên trái
        vayDuoi1_StartP.setLocation(startPointFish1, RADIUS_HEAD_F1_X * widthDirection * 2 + 2 * widthDirection, RADIUS_BODY_F1_Y);
        //góc dưới bên trái
        vayDuoi1_P2.setLocation(vayDuoi1_StartP, LECH_DUOI_1 * widthDirection, BOTTOM_FIN_HEIGHT_1);
        //góc dưới bên phải
        vayDuoi1_P3.setLocation(vayDuoi1_StartP, BOTTOM_FIN_WIDTH_1 * widthDirection, BOTTOM_FIN_HEIGHT_1);
        //góc trên bên phải
        vayDuoi1_EndP.setLocation(vayDuoi1_StartP, BOTTOM_FIN_WIDTH_1 * widthDirection - LECH_DUOI_1 * widthDirection, 0);

        skinShape1_Start.setLocation(startPointFish1, RADIUS_HEAD_F1_X * widthDirection * 2 + 3 * widthDirection, 0);

        // Set các điểm để paint
        headPosToPaint.setLocation(startPointFish1, 2 * widthDirection, 0);
        bodyPosToPaint.setLocation(startPointFish1, RADIUS_HEAD_F1_X * widthDirection * 2 + 1 * widthDirection, 0);
        topFinPosToPaint.setLocation(startPointFish1, RADIUS_HEAD_F1_X * widthDirection * 2 + 5 * widthDirection, -RADIUS_BODY_F1_Y - 1);
        botFinPosToPaint.setLocation(startPointFish1, RADIUS_HEAD_F1_X * widthDirection * 2 + 5 * widthDirection, RADIUS_BODY_F1_Y + 1);
        tailPosToPaint.setLocation(startPointFish1, RADIUS_BODY_F1_X * widthDirection * 2 + 1 * widthDirection, -1);

        // Set cac diem mat
        // Note: chỉ cần xét tọa độ cho điểm diemMat00
        diemMat00.setLocation(startPointFish1.getCoordX() + RADIUS_HEAD_F1_X * widthDirection, startPointFish1.getCoordY() - 2);
        diemMat01.setLocation(diemMat00.getCoordX() + 1 * widthDirection, diemMat00.getCoordY());
        diemMat10.setLocation(diemMat00.getCoordX(), diemMat00.getCoordY() + 1);
        diemMat11.setLocation(diemMat01.getCoordX(), diemMat10.getCoordY());
    }

    public void drawFish1() {
        setFilledColor(Color.BLACK);

        /* Vẽ thân hình ellipse */
        this.drawOutlineEllipse(RADIUS_BODY_F1_X, RADIUS_BODY_F1_Y, thanCa1_centerP, true, true, true, true);

        /* Vẽ đầu hình ellipse */
        if (widthDirection == 1) {
            this.drawOutlineEllipse(RADIUS_HEAD_F1_X, RADIUS_HEAD_F1_Y, dauCa1_CenterP, false, true, false, true);
        } else {
            this.drawOutlineEllipse(RADIUS_HEAD_F1_X, RADIUS_HEAD_F1_Y, dauCa1_CenterP, true, false, true, false);
        }

        /* Vẽ các điểm ở miệng */
        diemMieng1.setLocation(startPointFish1.getCoordX() + FISH_MOUTH_ADJUSTMENT_PIXEL_1[1] * widthDirection, startPointFish1.getCoordY() + FISH_MOUTH_ADJUSTMENT_PIXEL_1[0]);
        diemMieng2.setLocation(startPointFish1.getCoordX() + FISH_MOUTH_ADJUSTMENT_PIXEL_2[1] * widthDirection, startPointFish1.getCoordY() + FISH_MOUTH_ADJUSTMENT_PIXEL_2[0]);
        diemMieng3.setLocation(startPointFish1.getCoordX() + FISH_MOUTH_ADJUSTMENT_PIXEL_3[1] * widthDirection, startPointFish1.getCoordY() + FISH_MOUTH_ADJUSTMENT_PIXEL_3[0]);

        setFilledColor(MAU_MIENG_CA);
        savePoint(diemMieng1);
        savePoint(diemMieng2);
        savePoint(diemMieng3);

        /* Vẽ đuôi là một hình quạt */
        setFilledColor(Color.BLACK);

        /* Vẽ viền là hình ellipse nằm dọc */
        if (widthDirection == 1) {
            drawOutlineEllipse(4, TAIL_HEIGHT_1, tailEdge1_CenterP, false, true, false, true);
        } else {
            drawOutlineEllipse(4, TAIL_HEIGHT_1, tailEdge1_CenterP, true, false, true, false);
        }

        /* Vẽ 2 đường thẳng nối từ duoi_StartP tới hình ellipse nằm dọc ở trên */
        drawSegment(duoi1_StartP, duoiTren1_EndP);
        drawSegment(duoi1_StartP, duoiDuoi1_EndP);

        /*vẽ vây trên*/
        drawSegment(vayTren1_StartP, vayTren1_P2);
        drawSegment(vayTren1_P2, vayTren1_P3);
        drawSegment(vayTren1_P3, vayTren1_EndP);

        /*vẽ vây dưới*/
        drawSegment(vayDuoi1_StartP, vayDuoi1_P2);
        drawSegment(vayDuoi1_P2, vayDuoi1_P3);
        drawSegment(vayDuoi1_P3, vayDuoi1_EndP);

        /* vẽ da */
        setFilledColor(MAU_DA_CA);

        /* Điểm bắt đầu ra khỏi phần ellipse ở đầu cá, có Y trùng với tâm đầu cá */
        drawFishSkin1(skinShape1_Start, 0, 0);
        drawFishSkin1(skinShape1_Start, 3 * widthDirection, 3);
        drawFishSkin1(skinShape1_Start, -1 * widthDirection, -4);
        drawFishSkin1(skinShape1_Start, 4 * widthDirection, -3);
        drawFishSkin1(skinShape1_Start, 8 * widthDirection, -2);
        drawFishSkin1(skinShape1_Start, 6 * widthDirection, 1);
        drawFishSkin1(skinShape1_Start, 11 * widthDirection, 0);

        /* Vẽ mắt */
        setFilledColor(Color.WHITE);
        savePoint(diemMat01);
        setFilledColor(Color.BLACK);
        savePoint(diemMat11);
        savePoint(diemMat00);
        savePoint(diemMat10);

        /* Tô màu */
        Ultility.paint(changedColorOfBoard, markedChangeOfBoard, headPosToPaint, new Color(243, 240, 161), false);
        Ultility.paint(changedColorOfBoard, markedChangeOfBoard, bodyPosToPaint, new Color(202, 192, 50), false);
        Ultility.paint(changedColorOfBoard, markedChangeOfBoard, topFinPosToPaint, new Color(254, 241, 2), false);
        Ultility.paint(changedColorOfBoard, markedChangeOfBoard, botFinPosToPaint, new Color(254, 241, 2), false);
        Ultility.paint(changedColorOfBoard, markedChangeOfBoard, tailPosToPaint, new Color(254, 241, 2), false);

        setFilledColor(MAU_VAN_DUOI_CA);

        /* Vẽ đường vân ở đuôi */
        savedPoint.setLocation(savedPoint, -2 * widthDirection, 0);
        if (widthDirection == 1) {
            drawOutlineEllipse(3, TAIL_HEIGHT_1 - 3, savedPoint, false, true, false, true);
        } else {
            drawOutlineEllipse(3, TAIL_HEIGHT_1 - 3, savedPoint, true, false, true, false);
        }

        savedPoint.setLocation(savedPoint, -4 * widthDirection, 0);
        if (widthDirection == 1) {
            drawOutlineEllipse(3, TAIL_HEIGHT_1 - 4, savedPoint, false, true, false, true);
        } else {
            drawOutlineEllipse(3, TAIL_HEIGHT_1 - 4, savedPoint, true, false, true, false);
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

        drawSegment(newStartPoint, endPoint1);
        drawSegment(newStartPoint, endPoint2);
    }

    @Override
    public void setProperty(SKPoint2D startPoint, SKPoint2D endPoint) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
