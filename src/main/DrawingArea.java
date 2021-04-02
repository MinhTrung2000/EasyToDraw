/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 *
 * @author Minh Tu
 */


public class DrawingArea extends JPanel {
    private  int width;
    private  int height;
   
   private int space;
   private  int size;
    private int OX;
    private int OY;
   
   
   
   // private int OX = space + width / 2 * (space+size) + (size) / 2;
   //  private int OY = space + height / 2 * (space+size) + (size) / 2;
  public DrawingArea (int width, int height, int space, int size){
      super();
      this.width=width;
      this.height=height;
      this.space=space;
      this.size=size;
      OX = this.width/2 -1;
       OY = this.height/2 +1;
  }
    @Override
    public void paintComponent (Graphics g){
        super.paintComponent(g);
        g.setColor(new Color(235,235,235));
        g.fillRect(0, 0, width, height ); // vẽ nền, chính là lưới pixel sau khi chấm các điểm pixel màu trắng lên
        g.setColor(Color.white);
        for (int i=0;i<height ; i++){
            for (int j=0;j<width;j++){
                g.fillRect(i*(space+size)+1 ,j*(space+size)+1 ,size,size); //chấm các điểm pixel màu trắng (+1 để thụt vào 1 pixel, không bị viền đen che mất)
            }
        }
        //Ve truc Y
        g.setColor(Color.red);
        g.drawLine(OX, 1, OX, height); 
        //Ve truc X
        
        g.drawLine(1, OY, width, OY);
       
        
        //g.setColor(Color.yellow);
        
        //Point2D point = new Point2D(-5,1);
        
        //point=point.ConvertToPixelCoord(point, OX, OY);
        //g.fillRect(point.X, point.Y, size, size);
        
    }
}
