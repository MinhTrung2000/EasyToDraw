package algo;

/**
 * Bresenham algorithm for drawing ...
 * 
 */
public class LineDrawing {
    public LineDrawing() {}
    
//    public static void createLine (Grid tv, int x1, int y1, int x2, int y2) {
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
//                tv.fillCell(x, y);
//                if (balance >= 0) {
//                    y += incy;
//                    balance -= dx;
//                }
//                balance += dy;
//                x += incx;
//            }
//            tv.fillCell(x, y);
//        } else {
//            dx <<= 1;
//            balance = dx - dy;
//            dy <<= 1;
//
//            while (y != y2) {
//                tv.fillCell(x, y);
//                if (balance >= 0) {
//                    x += incx;
//                    balance -= dy;
//                }
//                balance += dx;
//                y += incy;
//            }
//            tv.fillCell(x, y);
//        }
//        return;
//    }
//    
}
