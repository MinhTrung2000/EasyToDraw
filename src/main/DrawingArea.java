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

    private int width;
    private int height;

    private int space;
    private int size;

    private int OX;
    private int OY;

    // private int OX = space + width / 2 * (space+size) + (size) / 2;
    //  private int OY = space + height / 2 * (space+size) + (size) / 2;
    public DrawingArea(int width, int height, int space, int size) {
        super();
        this.width = width;
        this.height = height;
        this.space = space;
        this.size = size;
        OX = width / 2 - 1;
        OY = height / 2 + 1;
    }

    public void createGridLayout(Graphics g) {
        g.setColor(new Color(235, 235, 235));
        g.fillRect(0, 0, width, height); // vẽ nền, chính là lưới pixel sau khi chấm các điểm pixel màu trắng lên
        g.setColor(Color.white);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                g.fillRect(i * (space + size) + 1, j * (space + size) + 1, size, size); //chấm các điểm pixel màu trắng (+1 để thụt vào 1 pixel, không bị viền đen che mất)
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        createGridLayout(g);
        createCoordinateAxis(g);

        //g.setColor(Color.yellow);

        //Point2D point = new Point2D(-5,1);
        //point=point.ConvertToPixelCoord(point, OX, OY);
        //g.fillRect(point.X, point.Y, size, size);
        
        createLine(g, 0, 0, 20, 20);
    }
    
    private int getNewX(int x) {
        return (65 + x) * (this.space + this.size) + 1;
    }
    
    private int getNewY(int y) {
        return (51 - y) * (this.space + this.size) + 1;
    }
    
    public void createCoordinateAxis(Graphics g) {
        //Ve truc Y
        g.setColor(Color.red);
        g.drawLine(OX, 1, OX, height);
        //Ve truc X
        g.drawLine(1, OY, width, OY);
    }
    
    public void putPixel(Graphics g, int x, int y) {
        g.fillRect(getNewX(x), getNewY(y), this.size, this.size);
    }
    
    public void createLine(Graphics g, int x1, int y1, int x2, int y2) {
        int x, y;
        int dx, dy;
        int incx, incy;
        int balance;

        if (x2 >= x1) {
            dx = x2 - x1;
            incx = 1;
        } else {
            dx = x1 - x2;
            incx = -1;
        }

        if (y2 >= y1) {
            dy = y2 - y1;
            incy = 1;
        } else {
            dy = y1 - y2;
            incy = -1;
        }

        x = x1;
        y = y1;

        if (dx >= dy) {
            dy <<= 1;
            balance = dy - dx;
            dx <<= 1;

            while (x != x2) {
                putPixel(g, x, y);
                if (balance >= 0) {
                    y += incy;
                    balance -= dx;
                }
                balance += dy;
                x += incx;
            }
            putPixel(g,x, y);
        } else {
            dx <<= 1;
            balance = dx - dy;
            dy <<= 1;

            while (y != y2) {
                putPixel(g, x, y);
                if (balance >= 0) {
                    x += incx;
                    balance -= dy;
                }
                balance += dx;
                y += incy;
            }
            putPixel(g, x, y);
        }
        return;
    }
}
