package main;

import java.awt.Graphics;
import static main.Settings.*;

public class Line2D {
    
    private Point2D startPoint;
    private Point2D descPoint;
    
    private LineStyle style = LineStyle.DEFAULT;
    
    public Line2D() {}
    
    public Line2D(Point2D startPoint, Point2D descPoint) {
        this.startPoint = startPoint;
        this.descPoint = descPoint;
    }
    
    public Line2D rotate(Point2D basePoint, double angle) {
        return new Line2D();
    }
    
    public void drawByMidPointMethod(Point2D startPoint, Point2D descPoint, LineStyle lineStyle) {
        
    }
    
//    public void draw(Graphics graphic, int x1, int y1, int x2, int y2) {
//    public void draw(Graphics graphic, int x1, int y1, int x2, int y2) {
//        int x, y;
//        int dx, dy;
//        int incx, incy;
//        int balance;
//
//        if (x2 >= x1) {
//            dx = x2 - x1;
//            incx = 1;
//        } else {
//            dx = x1 - x2;
//            incx = -1;
//        }
//
//        if (y2 >= y1) {
//            dy = y2 - y1;
//            incy = 1;
//        } else {
//            dy = y1 - y2;
//            incy = -1;
//        }
//
//        x = x1;
//        y = y1;
//
//        if (dx >= dy) {
//            dy <<= 1;
//            balance = dy - dx;
//            dx <<= 1;
//
//            while (x != x2) {
//                putPixel(graphic, x, y);
//                if (balance >= 0) {
//                    y += incy;
//                    balance -= dx;
//                }
//                balance += dy;
//                x += incx;
//            }
//            putPixel(graphic, x, y);
//        } else {
//            dx <<= 1;
//            balance = dx - dy;
//            dy <<= 1;
//
//            while (y != y2) {
//                putPixel(graphic, x, y);
//                if (balance >= 0) {
//                    x += incx;
//                    balance -= dy;
//                }
//                balance += dx;
//                y += incy;
//            }
//            putPixel(graphic, x, y);
//        }
//        return;
//    }
//    
}
