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
    private static int width = 918;
    private static int height = 718;
   
   private static int space=2;
   private static int size = 5;
   
   private int OX = width/2 -1;
  private int OY = height/2 +1;
   
   // private int OX = space + width / 2 * (space+size) + (size) / 2;
   //  private int OY = space + height / 2 * (space+size) + (size) / 2;
    @Override
    public void paintComponent (Graphics g){
        super.paintComponent(g);
        g.setColor(new Color(235,235,235));
        g.fillRect(0, 0, width, height ); // vẽ nền, chính là lưới pixel sau khi chấm các điểm pixel màu trắng lên
        g.setColor(Color.white);
        for (int i=0;i<width ; i++){
            for (int j=0;j<height;j++){
                g.fillRect(i*(space+size)+1 ,j*(space+size)+1 ,size,size); //chấm các điểm pixel màu trắng (+1 để thụt vào 1 pixel, không bị viền đen che mất)
            }
        }
        //Ve truc Y
        g.setColor(Color.red);
        g.drawLine(OX, 1, OX, height); 
        //Ve truc X
        g.drawLine(1, OY, width, OY);
    }
}
