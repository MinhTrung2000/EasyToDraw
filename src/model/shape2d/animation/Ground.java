/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.shape2d.animation;

import control.SettingConstants;
import control.util.Ultility;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import model.shape2d.Point2D;
import model.shape2d.Shape2D;
import model.shape2d.Vector2D;

/**
 *
 * @author Minh Tu
 */
public class Ground extends Shape2D {
     int[] roughNumberArray110 = {2, 1, 1, 1, 2, 2, 2, 2, 2, 3, 2, 1, 2, 1, 3, 2, 1, 1, 1, 1, 1, 3, 1, 2, 3, 2, 3, 1, 1, 3, 2, 3, 2, 3, 3, 2, 3, 3, 2, 3, 3, 1, 3, 3, 3, 2, 1, 3, 3, 1, 3, 1, 3, 3, 1, 3, 1, 2, 3, 2, 3, 2, 3, 2, 1, 1, 2, 3, 3, 1, 3, 3, 1, 2, 1, 1, 2, 2, 3, 3, 3, 1, 2, 2, 1, 1, 3, 2, 3, 1, 3, 1, 1, 2, 2, 1, 2, 1, 2, 2, 1, 1, 1, 1, 2, 1, 2, 2, 2, 2};
    int[] roughNumberArray110_2 = {3, 1, 2, 1, 1, 3, 3, 3, 2, 3, 1, 2, 3, 3, 1, 1, 2, 1, 2, 2, 2, 2, 3, 2, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 1, 1, 3, 1, 3, 2, 3, 3, 3, 1, 1, 3, 1, 2, 1, 1, 3, 1, 1, 1, 2, 3, 3, 3, 1, 3, 1, 1, 1, 3, 2, 1, 1, 2, 1, 1, 2, 1, 1, 2, 1, 2, 3, 1, 3, 1, 3, 1, 2, 2, 3, 2, 2, 3, 1, 2, 3, 3, 3, 3, 3, 3, 1, 1, 3, 2, 3, 1, 2, 1, 2, 3, 3, 2, 3, 2};
  //  Color groundColor = new Color (120, 80, 47);
    Color grassColor = new Color (15,242,22);
    HashMap<Point2D, Boolean> flower_CenterP = new HashMap<>();
    public Ground (boolean[][] markedChangeOfBoard, Color[][] changedColorOfBoard, String[][] changedCoordOfBoard, Color filledColor) {
        super(markedChangeOfBoard, changedColorOfBoard, changedCoordOfBoard, filledColor);
    }
    public void drawGround(Point2D startP){
//        int rY_Thickness = 15;
//        int rX_Length = 30;
        int realWidthLimit = SettingConstants.WIDTH_DRAW_AREA/SettingConstants.RECT_SIZE -1;
        this.filledColor = new Color (15,242,22);
        int flatGround1_Length = 30;
        int slopeGround1_Length = 50;
        int slopeGround2_Length = 45;
        int flatGround2_Length = 50;
        int slopeGround3_Length = realWidthLimit - (flatGround1_Length + flatGround2_Length + slopeGround1_Length + slopeGround2_Length);
        
        
        
        
        
        ArrayList<Point2D> pointList = new ArrayList<>();
        pointList.add(new Point2D (startP,0,0));
        pointList.add(new Point2D (startP,flatGround1_Length ,0));
        pointList.add(new Point2D (pointList.get(1),slopeGround1_Length,8));
        pointList.add(new Point2D (pointList.get(2),slopeGround2_Length,-15));
        pointList.add(new Point2D (pointList.get(3),flatGround2_Length, 0));
        pointList.add(new Point2D (pointList.get(4),slopeGround3_Length, 3));
        this.drawZigZagS(pointList,roughNumberArray110, roughNumberArray110_2);
        
        this.filledColor = grassColor;
        ArrayList<Point2D> pointList2 = new ArrayList<>();
        pointList2.add(startP);
        pointList2.add(new Point2D (startP,0,25));
        pointList2.add(new Point2D(pointList2.get(1),realWidthLimit,0));
        pointList2.add(new Point2D (pointList2.get(2),0,-27));
        this.drawZigZag(pointList2);
        
        
        //đặt trước vị trí vẽ hoa
        this.flower_CenterP.put(new Point2D  (startP,20,-10),true);
        this.flower_CenterP.put(new Point2D (startP,175,4),false);
        this.flower_CenterP.put(new Point2D (startP,42,10),false);
        this.flower_CenterP.put(new Point2D (startP,140,7),true);

    }
    
    
    
   
    public void paintGround(Point2D startP){
        Ultility.paint(changedColorOfBoard, markedChangeOfBoard, new Point2D(startP,5,5),grassColor);
        
       
    }
    public void drawFlower(Point2D startP, boolean right){
        this.filledColor = new Color (228,197,65);
        this.drawOutlineCircle(2, startP, true, true, true, true, true, true, true, true);
        this.filledColor = new Color (252,164,237);

        
        this.drawOutlineCircle(2, new Point2D (startP,-3,2), false, false, false, true, true, true, true, true);
        this.drawOutlineCircle(2, new Point2D (startP,3,2), true, true, true, true, true, false, false, false);
        this.drawOutlineCircle(2, new Point2D (startP,-3,-2), true, false, false, false, true, true, true, true);
        this.drawOutlineCircle(2, new Point2D (startP,3,-2), true, true, true, true, false, false, false, true);
        
       this.filledColor = new Color (59,102,107);
       if(right){
           this.drawSegment(new Point2D (startP,0,3), new Point2D (startP,2,13));

       }
       
       else {
           this.drawSegment(new Point2D (startP,0,3), new Point2D (startP,-2,13));
       }
    
  
    }
    public void drawAndPaintFlowers(){
        for(Map.Entry<Point2D, Boolean> m : flower_CenterP.entrySet())
           this.drawFlower(m.getKey(),m.getValue());
         this.paintFlower();
    }
    public void paintFlower(){
        this.filledColor = new Color (252,164,237);
          for(Map.Entry<Point2D, Boolean> centerP : flower_CenterP.entrySet()){
               Ultility.paint(changedColorOfBoard, markedChangeOfBoard, centerP.getKey(), new Color (249,255,135));
               Ultility.paint(changedColorOfBoard, markedChangeOfBoard, new Point2D(centerP.getKey(),-3,2), filledColor);
               Ultility.paint(changedColorOfBoard, markedChangeOfBoard, new Point2D(centerP.getKey(),3,2), filledColor);
               Ultility.paint(changedColorOfBoard, markedChangeOfBoard, new Point2D(centerP.getKey(),3,-2), filledColor);
               Ultility.paint(changedColorOfBoard, markedChangeOfBoard, new Point2D(centerP.getKey(),-3,-2), filledColor);
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
    public void setProperty(Point2D startPoint, Point2D endPoint) {

    }
}
