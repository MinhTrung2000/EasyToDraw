/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.shape2d.animation;

import control.util.Ultility;
import java.awt.Color;
import control.myawt.SKPoint2D;
import model.shape2d.Shape2D;
import model.shape2d.Vector2D;

/**
 *
 * @author Minh Tu
 */
public class Fish extends Shape2D {

    SKPoint2D savedPoint = new SKPoint2D();
    //-------------Fish 1---------------
    int tailWidth1 = 10;
    int tailHeight1 = 6;

    int botFinWidth1 = 10;
    int botFinHeight1 = 2;

    int topFinWidth1 = 7;
    int topFinHeight1 = 2;

    //chiều dài và rộng thân hình
    int radius_BodyF1X = 15;
    int radius_BodyF1Y = 7;
    //chiều dài và rộng phần đầu
    int radius_HeadF1X = 5;
    int radius_HeadF1Y = 5;

    //-------------Fish 2---------------
    //chiều dài và rộng thân hình
    int radius_BodyF2X = 20;
    int radius_BodyF2Y = 6;
    //khoảng cách (X) từ startPoint tới tâm của vây trên thân
    int distance_FinF2X = 13;

    // dài và rộng của vây trên thân
    int radius_FinF2X = 4;
    int radius_FinF2Y = 2;

    // dài rộng của vân cá trên thân
    int skinShape_F2X = 1;
    int skinShape_F2Y = 3;

    int topFinHeight2 = 4;
    int topFinWidth2 = 20;

    int botFinHeight2 = 3;
    int botFinWidth2 = 14;

    int tailWidth2 = 10;
    int tailHeight2 = 8;

    public Fish(boolean[][] markedChangeOfBoard, Color[][] changedColorOfBoard, String[][] changedCoordOfBoard, Color filledColor) {
        super(markedChangeOfBoard, changedColorOfBoard, changedCoordOfBoard, filledColor);
    }

    public void drawFish1(SKPoint2D startPoint, SKPoint2D endPoint) {

        /*------vẽ thân-------*/
        SKPoint2D than_CenterP = new SKPoint2D(startPoint, radius_BodyF1X, 0);
        this.drawOutlineEllipse(radius_BodyF1X, radius_BodyF1Y, than_CenterP, true, true, true, true);

        markedChangeOfBoard[(int) startPoint.getCoordY()][(int) startPoint.getCoordX() - 1] = true;
        changedColorOfBoard[(int) startPoint.getCoordY()][(int) startPoint.getCoordX() - 1] = new Color(0, 0, 0);

        //vẽ đầu
        SKPoint2D dau_CenterP = new SKPoint2D(startPoint, radius_HeadF1X, 0);
        this.drawOutlineEllipse(radius_HeadF1X, radius_HeadF1Y, dau_CenterP, false, true, false, true);

        //miệng 
        markedChangeOfBoard[(int) startPoint.getCoordY() + 1][(int) startPoint.getCoordX() + 1] = true;
        changedColorOfBoard[(int) startPoint.getCoordY() + 1][(int) startPoint.getCoordX() + 1] = new Color(180, 61, 59);

        markedChangeOfBoard[(int) startPoint.getCoordY() + 1][(int) startPoint.getCoordX() + 2] = true;
        changedColorOfBoard[(int) startPoint.getCoordY() + 1][(int) startPoint.getCoordX() + 2] = new Color(180, 61, 59);

        markedChangeOfBoard[(int) startPoint.getCoordY()][(int) startPoint.getCoordX() + 3] = true;
        changedColorOfBoard[(int) startPoint.getCoordY()][(int) startPoint.getCoordX() + 3] = new Color(180, 61, 59);
        /*-------vẽ đuôi-----*/
        this.filledColor = new Color(0, 0, 0);

        SKPoint2D duoi_StartP = new SKPoint2D(startPoint, radius_BodyF1X * 2, -1);

        SKPoint2D duoiTren_EndP = new SKPoint2D(duoi_StartP, tailWidth1, -tailHeight1);
        SKPoint2D duoiDuoi_EndP = new SKPoint2D(duoi_StartP, tailWidth1, tailHeight1);
        SKPoint2D edge_CenterP = new SKPoint2D(duoi_StartP, tailWidth1, 0);
        drawOutlineEllipse(4, tailHeight1, edge_CenterP, false, true, false, true);
        drawSegment(duoi_StartP, duoiTren_EndP);
        drawSegment(duoi_StartP, duoiDuoi_EndP);
        this.savedPoint.setLocation(edge_CenterP);

        /*vẽ vây trên*/
        this.filledColor = new Color(0, 0, 0);
        int lech = 3;
        SKPoint2D vayTren_StartP = new SKPoint2D(startPoint, radius_HeadF1X * 2 + 2, -radius_BodyF1Y);
        SKPoint2D vayTren_P2 = new SKPoint2D(vayTren_StartP, lech, -topFinHeight1);
        SKPoint2D vayTren_P3 = new SKPoint2D(vayTren_StartP, topFinWidth1, -topFinHeight1);
        SKPoint2D vayTren_EndP = new SKPoint2D(vayTren_StartP, topFinWidth1 - lech, 0);

        drawSegment(vayTren_StartP, vayTren_P2);
        drawSegment(vayTren_P2, vayTren_P3);
        drawSegment(vayTren_P3, vayTren_EndP);

        /*vẽ vây dưới*/
        lech = 3;
        SKPoint2D vayDuoi_StartP = new SKPoint2D(startPoint, radius_HeadF1X * 2 + 2, radius_BodyF1Y);
        SKPoint2D vayDuoi_P2 = new SKPoint2D(vayDuoi_StartP, lech, botFinHeight1);//
        SKPoint2D vayDuoi_P3 = new SKPoint2D(vayDuoi_StartP, botFinWidth1, botFinHeight1);
        SKPoint2D vayDuoi_EndP = new SKPoint2D(vayDuoi_StartP, botFinWidth1 - lech, 0);

        drawSegment(vayDuoi_StartP, vayDuoi_P2);
        drawSegment(vayDuoi_P2, vayDuoi_P3);
        drawSegment(vayDuoi_P3, vayDuoi_EndP);

        /* vẽ da*/
        this.filledColor = new Color(242, 236, 171);
        SKPoint2D skinShape_Start = new SKPoint2D(startPoint, radius_HeadF1X * 2 + 3, 0);
        drawFishSkin1(skinShape_Start, 0, 0);
        drawFishSkin1(skinShape_Start, 3, 3);
        drawFishSkin1(skinShape_Start, -1, -4);
        drawFishSkin1(skinShape_Start, 4, -3);
        drawFishSkin1(skinShape_Start, 8, -2);
        drawFishSkin1(skinShape_Start, 6, 1);
        drawFishSkin1(skinShape_Start, 11, 0);

    }

    private void drawFishSkin1(SKPoint2D startPoint, int changedPosX, int changedPosY) {

        SKPoint2D newStartPoint = new SKPoint2D(startPoint, changedPosX, changedPosY);

        SKPoint2D endPoint1 = new SKPoint2D(newStartPoint.getCoordX() - 1, newStartPoint.getCoordY() - 1);
        SKPoint2D endPoint2 = new SKPoint2D(newStartPoint.getCoordX() - 1, newStartPoint.getCoordY() + 1);

        drawSegment(newStartPoint, endPoint1);
        drawSegment(newStartPoint, endPoint2);
    }

    public void paintFish1(SKPoint2D startPoint, SKPoint2D endPoint) {

        SKPoint2D headPosToPaint = new SKPoint2D(startPoint, 2, 0);
        Ultility.paint(changedColorOfBoard, markedChangeOfBoard, headPosToPaint, new Color(243, 240, 161));
        SKPoint2D bodyPosToPaint = new SKPoint2D(startPoint, radius_HeadF1X * 2 + 1, 0);
        Ultility.paint(changedColorOfBoard, markedChangeOfBoard, bodyPosToPaint, new Color(202, 192, 50));
        SKPoint2D topFinPosToPaint = new SKPoint2D(startPoint, radius_HeadF1X * 2 + 5, -radius_BodyF1Y - 1);
        Ultility.paint(changedColorOfBoard, markedChangeOfBoard, topFinPosToPaint, new Color(254, 241, 2));
        SKPoint2D botFinPosToPaint = new SKPoint2D(startPoint, radius_HeadF1X * 2 + 5, radius_BodyF1Y + 1);//
        Ultility.paint(changedColorOfBoard, markedChangeOfBoard, botFinPosToPaint, new Color(254, 241, 2));
        SKPoint2D tailPosToPaint = new SKPoint2D(startPoint, radius_BodyF1X * 2 + 1, -1);
        Ultility.paint(changedColorOfBoard, markedChangeOfBoard, tailPosToPaint, new Color(254, 241, 2));

        //   mắt
        for (int i = 0; i <= 1; i++) {
            for (int j = 0; j <= 1; j++) {
                if (i == 0 && j == 1) {
                    this.filledColor = new Color(255, 255, 255);
                } else {
                    this.filledColor = new Color(0, 0, 0);
                }
                markedChangeOfBoard[(int) startPoint.getCoordY() - 2 + i][(int) startPoint.getCoordX() + radius_HeadF1X + j] = true;
                changedColorOfBoard[(int) startPoint.getCoordY() - 2 + i][(int) startPoint.getCoordX() + radius_HeadF1X + j] = filledColor;
            }
        }
        this.filledColor = new Color(254, 147, 31);

        // vẽ vân ở trong
        savedPoint.setLocation(savedPoint.getCoordX() - 2, savedPoint.getCoordY());
        drawOutlineEllipse(3, tailHeight1 - 3, savedPoint, false, true, false, true);
        savedPoint.setLocation(savedPoint.getCoordX() - 4, savedPoint.getCoordY());
        drawOutlineEllipse(3, tailHeight1 - 4, savedPoint, false, true, false, true);

        this.filledColor = new Color(0, 0, 0);

    }

    public void drawFish2(SKPoint2D startPoint, SKPoint2D endPoint) {

        //đầu
        this.filledColor = new Color(0, 0, 0);
        SKPoint2D than_CenterP = new SKPoint2D(startPoint, radius_BodyF2X, 0);
        this.drawOutlineEllipse(radius_BodyF2X, radius_BodyF2Y, than_CenterP, true, true, true, true);

        //vây trên thân
        this.filledColor = new Color(183, 250, 255);
        SKPoint2D vayTT_CenterP = new SKPoint2D(startPoint, distance_FinF2X, 0);
        this.drawOutlineEllipse(radius_FinF2X, radius_FinF2Y, vayTT_CenterP, false, true, false, true);

        // da trên thân
        SKPoint2D skinShape_CenterP = new SKPoint2D(startPoint, distance_FinF2X + radius_FinF2X + 5, 0);
        this.drawOutlineEllipse(skinShape_F2X, skinShape_F2Y, skinShape_CenterP, true, false, true, false);
        skinShape_CenterP.setLocation(skinShape_CenterP.getCoordX() + 4, skinShape_CenterP.getCoordY());
        this.drawOutlineEllipse(skinShape_F2X, skinShape_F2Y, skinShape_CenterP, true, false, true, false);
        skinShape_CenterP.setLocation(skinShape_CenterP.getCoordX() + 4, skinShape_CenterP.getCoordY());
        this.drawOutlineEllipse(skinShape_F2X, skinShape_F2Y, skinShape_CenterP, true, false, true, false);

        // vây trên
        this.filledColor = new Color(0, 0, 0);
        int lech = 4;
        SKPoint2D vayTren_StartP = new SKPoint2D(startPoint, distance_FinF2X - 3, -radius_BodyF2Y);
        SKPoint2D vayTren_P2 = new SKPoint2D(vayTren_StartP, lech, -topFinHeight2);
        SKPoint2D vayTren_P3 = new SKPoint2D(vayTren_StartP, topFinWidth2, -topFinHeight2);
        SKPoint2D vayTren_EndP = new SKPoint2D(vayTren_StartP, topFinWidth2 - lech, 0);

        drawSegment(vayTren_StartP, vayTren_P2);
        drawSegment(vayTren_P2, vayTren_P3);
        drawSegment(vayTren_P3, vayTren_EndP);

        //vây dưới
        SKPoint2D vayDuoi_StartP = new SKPoint2D(startPoint, distance_FinF2X - 1, radius_BodyF2Y);
        SKPoint2D vayDuoi_P2 = new SKPoint2D(vayDuoi_StartP, lech, +botFinHeight2);
        SKPoint2D vayDuoi_P3 = new SKPoint2D(vayDuoi_StartP, botFinWidth2, botFinHeight2);
        SKPoint2D vayDuoi_EndP = new SKPoint2D(vayDuoi_StartP, botFinWidth2 - lech, 0);

        drawSegment(vayDuoi_StartP, vayDuoi_P2);
        drawSegment(vayDuoi_P2, vayDuoi_P3);
        drawSegment(vayDuoi_P3, vayDuoi_EndP);

        //đuôi
        this.filledColor = new Color(0, 0, 0);

        SKPoint2D duoi_StartP = new SKPoint2D(startPoint, radius_BodyF2X * 2 - 1, 0);

        SKPoint2D duoiTren_EndP1 = new SKPoint2D(duoi_StartP, tailWidth2, -tailHeight2);
        SKPoint2D duoiDuoi_EndP = new SKPoint2D(duoi_StartP, tailWidth2, tailHeight2);
        drawSegment(duoi_StartP, duoiTren_EndP1);
        drawSegment(duoi_StartP, duoiDuoi_EndP);

        SKPoint2D edge_duoiTren_CenterP = new SKPoint2D(duoiTren_EndP1, 0, 2);
        this.drawOutlineEllipse(2, 2, edge_duoiTren_CenterP, false, true, false, true);

        SKPoint2D duoiTren_StartP2 = new SKPoint2D(duoiTren_EndP1, 0, 4);
        SKPoint2D duoi_EndP = new SKPoint2D(duoi_StartP, 4, 0);
        drawSegment(duoiTren_StartP2, duoi_EndP);

        SKPoint2D edge_duoiDuoi_CenterP = new SKPoint2D(edge_duoiTren_CenterP, 0, tailHeight2 * 2 - 4);
        this.drawOutlineEllipse(2, 2, edge_duoiDuoi_CenterP, false, true, false, true);
        //lấy tọa độ của edge_duoiTren_CenterP -4 ở trên -2 thêm 2 cho nhanh :3
        SKPoint2D duoiDuoi_StartP2 = new SKPoint2D(duoiTren_StartP2.getCoordX(), edge_duoiTren_CenterP.getCoordY() + tailHeight2 * 2 - 6);
        drawSegment(duoiDuoi_StartP2, duoi_EndP);

    }

    public void paintFish2(SKPoint2D startPoint, SKPoint2D endPoint) {
        //tô thân
        SKPoint2D bodyPosToPaint = new SKPoint2D(startPoint, 2, 0);
        Ultility.paint(changedColorOfBoard, markedChangeOfBoard, bodyPosToPaint, new Color(0, 182, 232));
        //tô vây và đuôi
        //lấy start của top Fin thêm 1 vài đơn vị để dịch vào trong, tương tự với những cái còn lại
        SKPoint2D topFinPosToPaint = new SKPoint2D(startPoint, distance_FinF2X - 3 + 2, -radius_BodyF2Y - 1);
        Ultility.paint(changedColorOfBoard, markedChangeOfBoard, topFinPosToPaint, new Color(0, 182, 232));
        SKPoint2D botFinPosToPaint = new SKPoint2D(startPoint, distance_FinF2X - 1 + 2, radius_BodyF2Y + 1);
        Ultility.paint(changedColorOfBoard, markedChangeOfBoard, botFinPosToPaint, new Color(0, 182, 232));

        SKPoint2D tailPosToPaint = new SKPoint2D(startPoint, radius_BodyF2X * 2 - 1 + 2, 0);
        Ultility.paint(changedColorOfBoard, markedChangeOfBoard, tailPosToPaint, new Color(0, 182, 232));

        //vẽ vân ở vây và đuôi
        this.filledColor = new Color(183, 250, 255);
        //vân trên
        SKPoint2D first_TopFinFP_StartP = new SKPoint2D(startPoint, distance_FinF2X - 3 + 5, -radius_BodyF2Y - 1);
        SKPoint2D first_TopFinFP_EndP = new SKPoint2D(first_TopFinFP_StartP, 2, -topFinHeight2 + 2);
        drawSegment(first_TopFinFP_StartP, first_TopFinFP_EndP);
        for (int i = 0; i < 3; i++) {
            first_TopFinFP_StartP = new SKPoint2D(first_TopFinFP_StartP, 3, 0);
            first_TopFinFP_EndP = new SKPoint2D(first_TopFinFP_StartP, 2, -topFinHeight2 + 2);
            drawSegment(first_TopFinFP_StartP, first_TopFinFP_EndP);
        }
        // vân dưới
        SKPoint2D first_botFinFP_StartP = new SKPoint2D(startPoint, distance_FinF2X - 1 + 3, radius_BodyF2Y + 1);
        SKPoint2D first_botFinFP_EndP = new SKPoint2D(first_botFinFP_StartP, 1, +botFinHeight2 - 2);
        drawSegment(first_botFinFP_StartP, first_botFinFP_EndP);
        for (int i = 0; i < 2; i++) {
            first_botFinFP_StartP = new SKPoint2D(first_botFinFP_StartP, 3, 0);
            first_botFinFP_EndP = new SKPoint2D(first_botFinFP_StartP, 1, +botFinHeight2 - 2);
            drawSegment(first_botFinFP_StartP, first_botFinFP_EndP);
        }

        // đuôi
        SKPoint2D first_botTailFP_StartP = new SKPoint2D(startPoint, radius_BodyF2X * 2 - 1 + 6, 2);
        SKPoint2D first_botTailFP_EndP = new SKPoint2D(first_botTailFP_StartP, 1, 3);
        drawSegment(first_botTailFP_StartP, first_botTailFP_EndP);

        SKPoint2D second_botTailFP_StartP = new SKPoint2D(first_botTailFP_StartP, 3, 2);
        SKPoint2D second_botTailFP_EndP = new SKPoint2D(second_botTailFP_StartP, 1, 3);
        drawSegment(second_botTailFP_StartP, second_botTailFP_EndP);

        SKPoint2D first_topTailFP_StartP = new SKPoint2D(startPoint, radius_BodyF2X * 2 - 1 + 6, -2);
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
                markedChangeOfBoard[(int) startPoint.getCoordY() - 2 + i][(int) startPoint.getCoordX() + distance_FinF2X / 2 + j] = true;
                changedColorOfBoard[(int) startPoint.getCoordY() - 2 + i][(int) startPoint.getCoordX() + distance_FinF2X / 2 + j] = filledColor;
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
