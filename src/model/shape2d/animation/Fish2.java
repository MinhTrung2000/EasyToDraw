package model.shape2d.animation;

import control.myawt.SKPoint2D;
import control.util.Ultility;
import java.awt.Color;
import model.shape2d.Shape2D;
import model.shape2d.Vector2D;

public class Fish2 extends Shape2D {

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

    public Fish2(boolean[][] markedChangeOfBoard, Color[][] changedColorOfBoard, String[][] changedCoordOfBoard, Color filledColor) {
        super(markedChangeOfBoard, changedColorOfBoard, changedCoordOfBoard, filledColor);
    }

    public void drawFish2(SKPoint2D startPoint, SKPoint2D endPoint) {

        //đầu
        this.filledColor = new Color(0, 0, 0);
        SKPoint2D than_CenterP = new SKPoint2D(startPoint, RADIUS_BODY_F2_X, 0);
        this.drawOutlineEllipse(RADIUS_BODY_F2_X, RADIUS_BODY_F2_Y, than_CenterP, true, true, true, true);

        //vây trên thân
        this.filledColor = new Color(183, 250, 255);
        SKPoint2D vayTT_CenterP = new SKPoint2D(startPoint, DISTANCE_FIN_F2_X, 0);
        this.drawOutlineEllipse(RADIUS_FIN_F2_X, RADIUS_FIN_F2_Y, vayTT_CenterP, false, true, false, true);

        // da trên thân
        SKPoint2D skinShape_CenterP = new SKPoint2D(startPoint, DISTANCE_FIN_F2_X + RADIUS_FIN_F2_X + 5, 0);
        this.drawOutlineEllipse(SKIN_SHAPE_F2_X, SKIN_SHAPE_F2_Y, skinShape_CenterP, true, false, true, false);
        skinShape_CenterP.setLocation(skinShape_CenterP.getCoordX() + 4, skinShape_CenterP.getCoordY());
        this.drawOutlineEllipse(SKIN_SHAPE_F2_X, SKIN_SHAPE_F2_Y, skinShape_CenterP, true, false, true, false);
        skinShape_CenterP.setLocation(skinShape_CenterP.getCoordX() + 4, skinShape_CenterP.getCoordY());
        this.drawOutlineEllipse(SKIN_SHAPE_F2_X, SKIN_SHAPE_F2_Y, skinShape_CenterP, true, false, true, false);

        // vây trên
        this.filledColor = new Color(0, 0, 0);
        int lech = 4;
        SKPoint2D vayTren_StartP = new SKPoint2D(startPoint, DISTANCE_FIN_F2_X - 3, -RADIUS_BODY_F2_Y);
        SKPoint2D vayTren_P2 = new SKPoint2D(vayTren_StartP, lech, -TOP_FIN_HEIGHT_2);
        SKPoint2D vayTren_P3 = new SKPoint2D(vayTren_StartP, TOP_FIN_WIDTH_2, -TOP_FIN_HEIGHT_2);
        SKPoint2D vayTren_EndP = new SKPoint2D(vayTren_StartP, TOP_FIN_WIDTH_2 - lech, 0);

        drawSegment(vayTren_StartP, vayTren_P2);
        drawSegment(vayTren_P2, vayTren_P3);
        drawSegment(vayTren_P3, vayTren_EndP);

        //vây dưới
        SKPoint2D vayDuoi_StartP = new SKPoint2D(startPoint, DISTANCE_FIN_F2_X - 1, RADIUS_BODY_F2_Y);
        SKPoint2D vayDuoi_P2 = new SKPoint2D(vayDuoi_StartP, lech, +BOTTOM_FIN_HEIGHT_2);
        SKPoint2D vayDuoi_P3 = new SKPoint2D(vayDuoi_StartP, BOTTOM_FIN_WIDTH_2, BOTTOM_FIN_HEIGHT_2);
        SKPoint2D vayDuoi_EndP = new SKPoint2D(vayDuoi_StartP, BOTTOM_FIN_WIDTH_2 - lech, 0);

        drawSegment(vayDuoi_StartP, vayDuoi_P2);
        drawSegment(vayDuoi_P2, vayDuoi_P3);
        drawSegment(vayDuoi_P3, vayDuoi_EndP);

        //đuôi
        this.filledColor = new Color(0, 0, 0);

        SKPoint2D duoi_StartP = new SKPoint2D(startPoint, RADIUS_BODY_F2_X * 2 - 1, 0);

        SKPoint2D duoiTren_EndP1 = new SKPoint2D(duoi_StartP, TAIL_WIDTH_2, -TAIL_HEIGHT_2);
        SKPoint2D duoiDuoi_EndP = new SKPoint2D(duoi_StartP, TAIL_WIDTH_2, TAIL_HEIGHT_2);
        drawSegment(duoi_StartP, duoiTren_EndP1);
        drawSegment(duoi_StartP, duoiDuoi_EndP);

        SKPoint2D edge_duoiTren_CenterP = new SKPoint2D(duoiTren_EndP1, 0, 2);
        this.drawOutlineEllipse(2, 2, edge_duoiTren_CenterP, false, true, false, true);

        SKPoint2D duoiTren_StartP2 = new SKPoint2D(duoiTren_EndP1, 0, 4);
        SKPoint2D duoi_EndP = new SKPoint2D(duoi_StartP, 4, 0);
        drawSegment(duoiTren_StartP2, duoi_EndP);

        SKPoint2D edge_duoiDuoi_CenterP = new SKPoint2D(edge_duoiTren_CenterP, 0, TAIL_HEIGHT_2 * 2 - 4);
        this.drawOutlineEllipse(2, 2, edge_duoiDuoi_CenterP, false, true, false, true);
        //lấy tọa độ của edge_duoiTren_CenterP -4 ở trên -2 thêm 2 cho nhanh :3
        SKPoint2D duoiDuoi_StartP2 = new SKPoint2D(duoiTren_StartP2.getCoordX(), edge_duoiTren_CenterP.getCoordY() + TAIL_HEIGHT_2 * 2 - 6);
        drawSegment(duoiDuoi_StartP2, duoi_EndP);

    }

    public void paintFish2(SKPoint2D startPoint, SKPoint2D endPoint) {
        //tô thân
        SKPoint2D bodyPosToPaint = new SKPoint2D(startPoint, 2, 0);
        Ultility.paint(changedColorOfBoard, markedChangeOfBoard, bodyPosToPaint, new Color(0, 182, 232));
        //tô vây và đuôi
        //lấy start của top Fin thêm 1 vài đơn vị để dịch vào trong, tương tự với những cái còn lại
        SKPoint2D topFinPosToPaint = new SKPoint2D(startPoint, DISTANCE_FIN_F2_X - 3 + 2, -RADIUS_BODY_F2_Y - 1);
        Ultility.paint(changedColorOfBoard, markedChangeOfBoard, topFinPosToPaint, new Color(0, 182, 232));
        SKPoint2D botFinPosToPaint = new SKPoint2D(startPoint, DISTANCE_FIN_F2_X - 1 + 2, RADIUS_BODY_F2_Y + 1);
        Ultility.paint(changedColorOfBoard, markedChangeOfBoard, botFinPosToPaint, new Color(0, 182, 232));

        SKPoint2D tailPosToPaint = new SKPoint2D(startPoint, RADIUS_BODY_F2_X * 2 - 1 + 2, 0);
        Ultility.paint(changedColorOfBoard, markedChangeOfBoard, tailPosToPaint, new Color(0, 182, 232));

        //vẽ vân ở vây và đuôi
        this.filledColor = new Color(183, 250, 255);
        //vân trên
        SKPoint2D first_TopFinFP_StartP = new SKPoint2D(startPoint, DISTANCE_FIN_F2_X - 3 + 5, -RADIUS_BODY_F2_Y - 1);
        SKPoint2D first_TopFinFP_EndP = new SKPoint2D(first_TopFinFP_StartP, 2, -TOP_FIN_HEIGHT_2 + 2);
        drawSegment(first_TopFinFP_StartP, first_TopFinFP_EndP);
        for (int i = 0; i < 3; i++) {
            first_TopFinFP_StartP = new SKPoint2D(first_TopFinFP_StartP, 3, 0);
            first_TopFinFP_EndP = new SKPoint2D(first_TopFinFP_StartP, 2, -TOP_FIN_HEIGHT_2 + 2);
            drawSegment(first_TopFinFP_StartP, first_TopFinFP_EndP);
        }
        // vân dưới
        SKPoint2D first_botFinFP_StartP = new SKPoint2D(startPoint, DISTANCE_FIN_F2_X - 1 + 3, RADIUS_BODY_F2_Y + 1);
        SKPoint2D first_botFinFP_EndP = new SKPoint2D(first_botFinFP_StartP, 1, +BOTTOM_FIN_HEIGHT_2 - 2);
        drawSegment(first_botFinFP_StartP, first_botFinFP_EndP);
        for (int i = 0; i < 2; i++) {
            first_botFinFP_StartP = new SKPoint2D(first_botFinFP_StartP, 3, 0);
            first_botFinFP_EndP = new SKPoint2D(first_botFinFP_StartP, 1, +BOTTOM_FIN_HEIGHT_2 - 2);
            drawSegment(first_botFinFP_StartP, first_botFinFP_EndP);
        }

        // đuôi
        SKPoint2D first_botTailFP_StartP = new SKPoint2D(startPoint, RADIUS_BODY_F2_X * 2 - 1 + 6, 2);
        SKPoint2D first_botTailFP_EndP = new SKPoint2D(first_botTailFP_StartP, 1, 3);
        drawSegment(first_botTailFP_StartP, first_botTailFP_EndP);

        SKPoint2D second_botTailFP_StartP = new SKPoint2D(first_botTailFP_StartP, 3, 2);
        SKPoint2D second_botTailFP_EndP = new SKPoint2D(second_botTailFP_StartP, 1, 3);
        drawSegment(second_botTailFP_StartP, second_botTailFP_EndP);

        SKPoint2D first_topTailFP_StartP = new SKPoint2D(startPoint, RADIUS_BODY_F2_X * 2 - 1 + 6, -2);
        SKPoint2D first_topTailFP_EndP = new SKPoint2D(first_topTailFP_StartP, 1, -3);
        drawSegment(first_topTailFP_StartP, first_topTailFP_EndP);

        SKPoint2D second_topTailFP_StartP = new SKPoint2D(first_topTailFP_StartP, 3, -2);
        SKPoint2D second_topTailFP_EndP = new SKPoint2D(second_topTailFP_StartP, 1, -3);
        drawSegment(second_topTailFP_StartP, second_topTailFP_EndP);

        //mắt
        for (int i = 0; i <= 1; i++) {
            for (int j = 0; j <= 1; j++) {
                if (i == 0 && j == 1) {
                    this.filledColor = new Color(255, 255, 255);
                } else {
                    this.filledColor = new Color(0, 0, 0);
                }
                markedChangeOfBoard[(int) startPoint.getCoordY() - 2 + i][(int) startPoint.getCoordX() + DISTANCE_FIN_F2_X / 2 + j] = true;
                changedColorOfBoard[(int) startPoint.getCoordY() - 2 + i][(int) startPoint.getCoordX() + DISTANCE_FIN_F2_X / 2 + j] = filledColor;
            }
        }

        //miệng
        markedChangeOfBoard[(int) startPoint.getCoordY() + 1][(int) startPoint.getCoordX() + 1] = true;
        changedColorOfBoard[(int) startPoint.getCoordY() + 1][(int) startPoint.getCoordX() + 1] = new Color(180, 61, 59);

        markedChangeOfBoard[(int) startPoint.getCoordY() + 1][(int) startPoint.getCoordX() + 2] = true;
        changedColorOfBoard[(int) startPoint.getCoordY() + 1][(int) startPoint.getCoordX() + 2] = new Color(180, 61, 59);

        markedChangeOfBoard[(int) startPoint.getCoordY()][(int) startPoint.getCoordX() + 3] = true;
        changedColorOfBoard[(int) startPoint.getCoordY()][(int) startPoint.getCoordX() + 3] = new Color(180, 61, 59);

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
