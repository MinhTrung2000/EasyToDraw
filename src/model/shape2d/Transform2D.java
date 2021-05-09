package model.shape2d;

import control.myawt.SKPoint2D;

public class Transform2D {
    
    public static double[][] getMoveMat(double x, double y) {
        return new double[][]{
            {1, 0, 0},
            {0, 1, 0},
            {x, y, 1}
        };
    }

    public static double[][] getScaleMat(double x, double y) {
        return new double[][]{
            {x, 0, 0},
            {0, y, 0},
            {0, 0, 1}
        };
    }

    public static double[][] getRotateMat(double angle) {
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);
        return new double[][]{
            {cos, sin, 0},
            {-sin, cos, 0},
            {0, 0, 1}
        };
    }

    public static double[][] getSymOXMat() {
        return new double[][]{
            {1, 0, 0},
            {0, -1, 0},
            {0, 0, 1}
        };
    }

    public static double[][] getSymOYMat() {
        return new double[][]{
            {-1, 0, 0},
            {0, 1, 0},
            {0, 0, 1}
        };
    }

    public static double[][] getSymOCenterMat() {
        return new double[][]{
            {-1, 0, 0},
            {0, -1, 0},
            {0, 0, 1}
        };
    }
    
    public static SKPoint2D transform(SKPoint2D point, double[][] m) {
        double x = point.getCoordX();
        double y = point.getCoordY();
        
        SKPoint2D ret = new SKPoint2D();
        ret.setLocation(
                (int) (x * m[0][0] + y * m[1][0] + 1 * m[2][0]),
                (int) (x * m[0][1] + y * m[1][1] + 1 * m[2][1])
        );
        
        return ret;
    }

    public static Vector2D transform(Vector2D vec, double[][] m) {
        double x = vec.getCoordX();
        double y = vec.getCoordY();
        
        Vector2D ret = new Vector2D();
        
        ret.setCoord(
                (x * m[0][0] + y * m[1][0] + 1 * m[2][0]),
                (x * m[0][1] + y * m[1][1] + 1 * m[2][1])
        );
        
        return ret;
    }
}
