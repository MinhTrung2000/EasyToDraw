/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gridAndMouse;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author minhphuc
 */
public class Grid extends JPanel {

    private List<Point> fillCells;
    private static final int X_DAU = 5;
    private static final int Y_DAU = 5;
    private static final int WIDTH = 500;
    private static final int HEIGHT = 600;

    public Grid() {
        // 25 khoang trong
        fillCells = new ArrayList<>(25);
    }

    @Override
    protected void paintComponent(Graphics g) {
        final int WITDH_PIXEL = 5;
        super.paintComponent(g);
        for (Point fillCell : fillCells) {
            int cellX = fillCell.x * 5; // vi he toa do co cho la do rong pixel 5*5
            int cellY = fillCell.y * 5;
            g.setColor(Color.black);
            g.fillRect(cellX, cellY, WITDH_PIXEL, WITDH_PIXEL);
        }
        // tao bang  co kich thuoc la 500*600
        g.setColor(Color.red); // set duong vien la mau den
        //g.drawRect(X_DAU, Y_DAU, WIDTH, HEIGHT);
        // ve cac duong luoi cho khung
            // ve duong thang dung
        for (int i = 5; i <= WIDTH; i += 5) {
            g.drawLine(i, 5, i, HEIGHT);
        }
            // ve duong ngang
        for (int i = 5; i <= HEIGHT; i += 5) {
            g.drawLine(5, i, WIDTH, i);
        }
    }
    
    public void fillCell(int x, int y) {
            fillCells.add(new Point(x, y));
            repaint();
    }
    
    public static void createLine (Grid tv, int x1, int y1, int x2, int y2) {
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
                tv.fillCell(x, y);
                if (balance >= 0) {
                    y += incy;
                    balance -= dx;
                }
                balance += dy;
                x += incx;
            }
            tv.fillCell(x, y);
        } else {
            dx <<= 1;
            balance = dx - dy;
            dy <<= 1;

            while (y != y2) {
                tv.fillCell(x, y);
                if (balance >= 0) {
                    x += incx;
                    balance -= dy;
                }
                balance += dx;
                y += incy;
            }
            tv.fillCell(x, y);
        }
        return;
    }
    
    public static void main(String[] a) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                }

                Grid tv = new Grid();
                tv.setBackground(Color.white);
                JFrame window = new JFrame();
                window.setSize(WIDTH + 10, HEIGHT + 10);
                window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                window.add(tv);
                window.setVisible(true);
                //nhap pixel thuong qua ham fill cell se tu dong tang kich co pixel 
                createLine(tv, 5, 4, 15, 4);
                // 5 4 15 4
                createLine(tv, 2, 3, 16, 16); // duong nay bi loi
                //createLine(tv, 100, 3, 51, 52);
                
                // test ve hinh chu nhat
                //createLine(tv, 1, 1, 1, 8);
                //createLine(tv, 1, 1, 5, 1);
                //createLine(tv, 5, 8, 5, 1);
                //createLine(tv, 5, 8, 1, 8);
            }
        });
    }
}
