package model.shape2d.animation;

import control.myawt.SKPoint2D;
import control.util.Ultility;
import java.awt.Color;
import java.util.ArrayList;
import model.shape2d.Shape2D;
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
    public static final Color FIN_SHAPE_COLOR = new Color(183, 250, 255);
    public static final Color SKIN_COLOR = new Color(0, 182, 232);
    public static final Color FISH_MOUTH_COLOR = new Color(180, 61, 59);

    private SKPoint2D startPointFish2 = new SKPoint2D();
    private SKPoint2D thanCa2_CenterP = new SKPoint2D();
    private SKPoint2D vayTT2_CenterP = new SKPoint2D();
    private SKPoint2D skinShape2_CenterP1 = new SKPoint2D();
    private SKPoint2D skinShape2_CenterP2 = new SKPoint2D();
    private SKPoint2D skinShape2_CenterP3 = new SKPoint2D();
    private SKPoint2D vayTren2_StartP = new SKPoint2D();
    private SKPoint2D vayTren2_P2 = new SKPoint2D();
    private SKPoint2D vayTren2_P3 = new SKPoint2D();
    private SKPoint2D vayTren2_EndP = new SKPoint2D();
    private SKPoint2D vayDuoi2_StartP = new SKPoint2D();
    private SKPoint2D vayDuoi2_P2 = new SKPoint2D();
    private SKPoint2D vayDuoi2_P3 = new SKPoint2D();
    private SKPoint2D vayDuoi2_EndP = new SKPoint2D();

    private SKPoint2D duoi2_StartP = new SKPoint2D();
    private SKPoint2D duoi2_EndP = new SKPoint2D();

    private SKPoint2D duoiTren2_EndP = new SKPoint2D();
    private SKPoint2D duoiDuoi2_EndP = new SKPoint2D();

    //điểm bắt đầu (cùng tọa độ X với điểm kết thúc ở trên), đường thẳng ở trong
    private SKPoint2D duoiTren2_StartP2 = new SKPoint2D();
    private SKPoint2D duoiDuoi2_StartP2 = new SKPoint2D();

    //tâm của 2 đường ellipse ngoài rìa đuôi cá
    private SKPoint2D edge_duoiTren2_CenterP = new SKPoint2D();
    private SKPoint2D edge_duoiDuoi2_CenterP = new SKPoint2D();

    //Point to Paint
    private SKPoint2D bodyPosToPaint = new SKPoint2D();
    private SKPoint2D topFinPosToPaint = new SKPoint2D();
    private SKPoint2D botFinPosToPaint = new SKPoint2D();
    private SKPoint2D tailPosToPaint = new SKPoint2D();

    //Drawing after painting
    private SKPoint2D[] TopFin_StartP = new SKPoint2D[4];
    private SKPoint2D[] TopFin_EndP = new SKPoint2D[4];

    private SKPoint2D first_botFinFP_StartP = new SKPoint2D();
    private SKPoint2D first_botFinFP_EndP = new SKPoint2D();
    private SKPoint2D[] BotFin_StartP = new SKPoint2D[3];
    private SKPoint2D[] BotFin_EndP = new SKPoint2D[3];

    private SKPoint2D first_botTailFP_StartP = new SKPoint2D();
    private SKPoint2D first_botTailFP_EndP = new SKPoint2D();
    private SKPoint2D second_botTailFP_StartP = new SKPoint2D();
    private SKPoint2D second_botTailFP_EndP = new SKPoint2D();
    private SKPoint2D first_topTailFP_StartP = new SKPoint2D();
    private SKPoint2D first_topTailFP_EndP = new SKPoint2D();
    private SKPoint2D second_topTailFP_StartP = new SKPoint2D();
    private SKPoint2D second_topTailFP_EndP = new SKPoint2D();

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

    /*
        Chỉ số 1 là dịch Y, chỉ số 2 là dịch X!
     */
    public static final int[] FISH_MOUTH_ADJUSTMENT_PIXEL_1 = {1, 1};
    public static final int[] FISH_MOUTH_ADJUSTMENT_PIXEL_2 = {1, 2};
    public static final int[] FISH_MOUTH_ADJUSTMENT_PIXEL_3 = {0, 3};

    public Fish2(boolean[][] markedChangeOfBoard, Color[][] changedColorOfBoard, String[][] changedCoordOfBoard, Color filledColor) {
        super(markedChangeOfBoard, changedColorOfBoard, changedCoordOfBoard, filledColor);
    }

    public void setPropertyFish2(SKPoint2D startPoint) {
        this.startPointFish2 = startPoint;

        thanCa2_CenterP.setLocation(startPointFish2, RADIUS_BODY_F2_X * widthDirection, 0);
        vayTT2_CenterP.setLocation(startPointFish2, widthDirection * DISTANCE_FIN_F2_X, 0);
        skinShape2_CenterP1.setLocation(startPointFish2, widthDirection * (DISTANCE_FIN_F2_X + RADIUS_FIN_F2_X + 5), 0);

        vayTren2_StartP.setLocation(startPointFish2, widthDirection * (DISTANCE_FIN_F2_X - 3), -RADIUS_BODY_F2_Y);
        vayTren2_P2.setLocation(vayTren2_StartP, LECH_TREN_2 * widthDirection, -TOP_FIN_HEIGHT_2);
        vayTren2_P3.setLocation(vayTren2_StartP, widthDirection * TOP_FIN_WIDTH_2, -TOP_FIN_HEIGHT_2);
        vayTren2_EndP.setLocation(vayTren2_StartP, widthDirection * TOP_FIN_WIDTH_2 - LECH_TREN_2 * widthDirection, 0);

        vayDuoi2_StartP.setLocation(startPointFish2, widthDirection * (DISTANCE_FIN_F2_X - 1), RADIUS_BODY_F2_Y);
        vayDuoi2_P2.setLocation(vayDuoi2_StartP, LECH_DUOI_2 * widthDirection, +BOTTOM_FIN_HEIGHT_2);
        vayDuoi2_P3.setLocation(vayDuoi2_StartP, widthDirection * BOTTOM_FIN_WIDTH_2, BOTTOM_FIN_HEIGHT_2);
        vayDuoi2_EndP.setLocation(vayDuoi2_StartP, widthDirection * BOTTOM_FIN_WIDTH_2 - LECH_DUOI_2 * widthDirection, 0);

        skinShape2_CenterP2.setLocation(skinShape2_CenterP1.getCoordX() + 4 * widthDirection, skinShape2_CenterP1.getCoordY());
        skinShape2_CenterP3.setLocation(skinShape2_CenterP2.getCoordX() + 4 * widthDirection, skinShape2_CenterP2.getCoordY());

        duoi2_StartP.setLocation(startPointFish2, widthDirection * (RADIUS_BODY_F2_X * 2 - 1), 0);
        duoiTren2_EndP.setLocation(duoi2_StartP, widthDirection * TAIL_WIDTH_2, -TAIL_HEIGHT_2);
        duoiDuoi2_EndP.setLocation(duoi2_StartP, widthDirection * TAIL_WIDTH_2, TAIL_HEIGHT_2);

        edge_duoiTren2_CenterP.setLocation(duoiTren2_EndP, 0, 2);

        duoiTren2_StartP2.setLocation(duoiTren2_EndP, 0, 4);
        duoi2_EndP.setLocation(duoi2_StartP, widthDirection * 4, 0);

        edge_duoiDuoi2_CenterP.setLocation(edge_duoiTren2_CenterP, 0, TAIL_HEIGHT_2 * 2 - 4);
        duoiDuoi2_StartP2.setLocation(duoiTren2_StartP2.getCoordX(), edge_duoiTren2_CenterP.getCoordY() + TAIL_HEIGHT_2 * 2 - 6);

        //Paint
        bodyPosToPaint.setLocation(startPointFish2, widthDirection * 2, 0);

        topFinPosToPaint.setLocation(startPointFish2, widthDirection * (DISTANCE_FIN_F2_X - 3 + 2), -RADIUS_BODY_F2_Y - 1);
        botFinPosToPaint.setLocation(startPointFish2, widthDirection * (DISTANCE_FIN_F2_X - 1 + 2), RADIUS_BODY_F2_Y + 1);
        tailPosToPaint.setLocation(startPointFish2, widthDirection * (RADIUS_BODY_F2_X * 2 - 1 + 2), 0);

        TopFin_StartP[0] = new SKPoint2D(startPointFish2, widthDirection * (DISTANCE_FIN_F2_X - 3 + 5), -RADIUS_BODY_F2_Y - 1);
        TopFin_EndP[0] = new SKPoint2D(TopFin_StartP[0], 2 * widthDirection, -TOP_FIN_HEIGHT_2 + 2);
        for (int i = 1; i < 4; i++) {
            TopFin_StartP[i] = new SKPoint2D(TopFin_StartP[i - 1], 3 * widthDirection, 0);
            TopFin_EndP[i] = new SKPoint2D(TopFin_StartP[i], 2 * widthDirection, -TOP_FIN_HEIGHT_2 + 2);
        }

        BotFin_StartP[0] = new SKPoint2D(startPointFish2, widthDirection * (DISTANCE_FIN_F2_X - 1 + 3), RADIUS_BODY_F2_Y + 1);
        BotFin_EndP[0] = new SKPoint2D(BotFin_StartP[0], widthDirection * 1, +BOTTOM_FIN_HEIGHT_2 - 2);
        for (int i = 1; i < 3; i++) {
            BotFin_StartP[i] = new SKPoint2D(BotFin_StartP[i - 1], widthDirection * 3, 0);
            BotFin_EndP[i] = new SKPoint2D(BotFin_StartP[i], widthDirection * 1, BOTTOM_FIN_HEIGHT_2 - 2);
            drawSegment(first_botFinFP_StartP, first_botFinFP_EndP);
        }

        first_botFinFP_StartP.setLocation(startPointFish2, widthDirection * (DISTANCE_FIN_F2_X - 1 + 3), RADIUS_BODY_F2_Y + 1);
        first_botFinFP_EndP.setLocation(first_botFinFP_StartP, 1, +BOTTOM_FIN_HEIGHT_2 - 2);
        first_botTailFP_StartP.setLocation(startPointFish2, widthDirection * (RADIUS_BODY_F2_X * 2 - 1 + 6), 2);
        first_botTailFP_EndP.setLocation(first_botTailFP_StartP, widthDirection * 1, 3);
        second_botTailFP_StartP.setLocation(first_botTailFP_StartP, widthDirection * 3, 2);
        second_botTailFP_EndP.setLocation(second_botTailFP_StartP, widthDirection * 1, 3);

        first_topTailFP_StartP.setLocation(startPointFish2, widthDirection * (RADIUS_BODY_F2_X * 2 - 1 + 6), -2);
        first_topTailFP_EndP.setLocation(first_topTailFP_StartP, widthDirection * 1, -3);
        second_topTailFP_StartP.setLocation(first_topTailFP_StartP, widthDirection * 3, -2);
        second_topTailFP_EndP.setLocation(second_topTailFP_StartP, widthDirection * 1, -3);

        // Set cac diem mat
        // Note: chỉ cần xét tọa độ cho điểm diemMat00
        diemMat00.setLocation(startPointFish2.getCoordX() + (DISTANCE_FIN_F2_X / 2) * widthDirection, startPointFish2.getCoordY() - 2);
        diemMat01.setLocation(diemMat00.getCoordX() + 1 * widthDirection, diemMat00.getCoordY());
        diemMat10.setLocation(diemMat00.getCoordX(), diemMat00.getCoordY() + 1);
        diemMat11.setLocation(diemMat01.getCoordX(), diemMat10.getCoordY());

        /* Vẽ các điểm ở miệng */
        diemMieng1.setLocation(startPointFish2.getCoordX() + FISH_MOUTH_ADJUSTMENT_PIXEL_1[1] * widthDirection, startPointFish2.getCoordY() + FISH_MOUTH_ADJUSTMENT_PIXEL_1[0]);
        diemMieng2.setLocation(startPointFish2.getCoordX() + FISH_MOUTH_ADJUSTMENT_PIXEL_2[1] * widthDirection, startPointFish2.getCoordY() + FISH_MOUTH_ADJUSTMENT_PIXEL_2[0]);
        diemMieng3.setLocation(startPointFish2.getCoordX() + FISH_MOUTH_ADJUSTMENT_PIXEL_3[1] * widthDirection, startPointFish2.getCoordY() + FISH_MOUTH_ADJUSTMENT_PIXEL_3[0]);
    }

    public void drawFish2() {

        //thân
        this.filledColor = Color.BLACK;
        this.drawOutlineEllipse(RADIUS_BODY_F2_X, RADIUS_BODY_F2_Y, thanCa2_CenterP, true, true, true, true);

        //vây trên thân
        this.filledColor = FIN_SHAPE_COLOR;
        if (widthDirection == 1) {
            this.drawOutlineEllipse(RADIUS_FIN_F2_X, RADIUS_FIN_F2_Y, vayTT2_CenterP, false, true, false, true);
        } else {
            this.drawOutlineEllipse(RADIUS_FIN_F2_X, RADIUS_FIN_F2_Y, vayTT2_CenterP, !false, !true, !false, !true);
        }

        // da trên thân
        if (widthDirection == 1) {
            this.drawOutlineEllipse(SKIN_SHAPE_F2_X, SKIN_SHAPE_F2_Y, skinShape2_CenterP1, true, false, true, false);
            this.drawOutlineEllipse(SKIN_SHAPE_F2_X, SKIN_SHAPE_F2_Y, skinShape2_CenterP2, true, false, true, false);
            this.drawOutlineEllipse(SKIN_SHAPE_F2_X, SKIN_SHAPE_F2_Y, skinShape2_CenterP3, true, false, true, false);
        } else {
            this.drawOutlineEllipse(SKIN_SHAPE_F2_X, SKIN_SHAPE_F2_Y, skinShape2_CenterP1, !true, !false, !true, !false);
            this.drawOutlineEllipse(SKIN_SHAPE_F2_X, SKIN_SHAPE_F2_Y, skinShape2_CenterP2, !true, !false, !true, !false);
            this.drawOutlineEllipse(SKIN_SHAPE_F2_X, SKIN_SHAPE_F2_Y, skinShape2_CenterP3, !true, !false, !true, !false);
        }

        // vây trên
        this.filledColor = Color.BLACK;
        drawSegment(vayTren2_StartP, vayTren2_P2);
        drawSegment(vayTren2_P2, vayTren2_P3);
        drawSegment(vayTren2_P3, vayTren2_EndP);

        //vây dưới
        drawSegment(vayDuoi2_StartP, vayDuoi2_P2);
        drawSegment(vayDuoi2_P2, vayDuoi2_P3);
        drawSegment(vayDuoi2_P3, vayDuoi2_EndP);

        //đuôi
        this.filledColor = Color.BLACK;
        drawSegment(duoi2_StartP, duoiTren2_EndP);
        drawSegment(duoi2_StartP, duoiDuoi2_EndP);
        if (widthDirection == 1) {
            this.drawOutlineEllipse(2, 2, edge_duoiTren2_CenterP, false, true, false, true);
        } else {
            this.drawOutlineEllipse(2, 2, edge_duoiTren2_CenterP, !false, !true, !false, !true);
        }

        drawSegment(duoiTren2_StartP2, duoi2_EndP);
        if (widthDirection == 1) {
            this.drawOutlineEllipse(2, 2, edge_duoiDuoi2_CenterP, false, true, false, true);
        } else {
            this.drawOutlineEllipse(2, 2, edge_duoiDuoi2_CenterP, !false, !true, !false, !true);
        }
        //lấy tọa độ của edge_duoiTren_CenterP -4 ở trên -2 thêm 2 cho nhanh :3

        drawSegment(duoiDuoi2_StartP2, duoi2_EndP);

        /* TÔ MÀU */
        
        //tô thân
        Ultility.paint(changedColorOfBoard, markedChangeOfBoard, bodyPosToPaint, SKIN_COLOR, false);

        //tô vây và đuôi
        Ultility.paint(changedColorOfBoard, markedChangeOfBoard, topFinPosToPaint, SKIN_COLOR, false);
        Ultility.paint(changedColorOfBoard, markedChangeOfBoard, botFinPosToPaint, SKIN_COLOR, false);
        Ultility.paint(changedColorOfBoard, markedChangeOfBoard, tailPosToPaint, SKIN_COLOR, false);

        //vẽ vân ở vây và đuôi
        this.filledColor = FIN_SHAPE_COLOR;

        //vân trên
        for (int i = 0; i < 4; i++) {
            drawSegment(TopFin_StartP[i], TopFin_EndP[i]);
        }

        // vân dưới
        for (int i = 0; i < 3; i++) {
            drawSegment(BotFin_StartP[i], BotFin_EndP[i]);
        }

        // đuôi
        drawSegment(first_botTailFP_StartP, first_botTailFP_EndP);

        drawSegment(second_botTailFP_StartP, second_botTailFP_EndP);

        drawSegment(first_topTailFP_StartP, first_topTailFP_EndP);

        drawSegment(second_topTailFP_StartP, second_topTailFP_EndP);

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
    public void applyMove(Vector2D vector
    ) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
