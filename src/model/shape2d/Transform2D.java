package model.shape2d;

import control.myawt.SKPoint2D;

public class Transform2D {

    /**
     * Matrix for point movement.
     *
     * @param x
     * @param y
     * @return
     */
    public static double[][] getMoveMat(double x, double y) {
        return new double[][]{
            {1, 0, 0},
            {0, 1, 0},
            {x, y, 1}
        };
    }

    /**
     * Matrix for point scaling.
     *
     * @param x
     * @param y
     * @return
     */
    public static double[][] getScaleMat(double x, double y) {
        return new double[][]{
            {x, 0, 0},
            {0, y, 0},
            {0, 0, 1}
        };
    }

    /**
     * Matrix for point scaling.
     *
     * @param x
     * @param y
     * @return
     */
    public static double[][] getScaleMat(double k) {
        return new double[][]{
            {k, 0, 0},
            {0, k, 0},
            {0, 0, 1}
        };
    }

    /**
     * Matrix for counter clockwise rotation by angle (degree)
     *
     * @param angle
     * @return
     */
    public static double[][] getRotateMat(double angle) {
        double theta = Math.toRadians(angle);

        double cos = Math.cos(theta);
        double sin = Math.sin(theta);
        return new double[][]{
            {cos, sin, 0},
            {-sin, cos, 0},
            {0, 0, 1}
        };
    }

    /**
     * Matrix for counter clockwise rotation from a point by angle
     *
     * @param angle
     * @return
     */
    public static double[][] getRotateFromPointMat(SKPoint2D point, double angle) {
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);

        int x = point.getCoordX();
        int y = point.getCoordY();

        return new double[][]{
            {cos, sin, 0},
            {-sin, cos, 0},
            {(-x * cos + y * sin + x), (-x * sin - y * cos + y), 1}
        };
    }

    /**
     * Matrix for symmetric from Ox axis
     *
     * @return
     */
    public static double[][] getOXSymMat() {
        return new double[][]{
            {1, 0, 0},
            {0, -1, 0},
            {0, 0, 1}
        };
    }

    /**
     * Matrix for symmetry from Oy axis
     *
     * @return
     */
    public static double[][] getOYSymMat() {
        return new double[][]{
            {-1, 0, 0},
            {0, 1, 0},
            {0, 0, 1}
        };
    }

    /**
     * Matrix for symmetry from O.
     */
    public static double[][] getOCenterSymMat() {
        return new double[][]{
            {-1, 0, 0},
            {0, -1, 0},
            {0, 0, 1}
        };
    }

    /**
     * Matrix for symmetry from a point.
     */
    public static double[][] getPointSymMat(SKPoint2D point) {
        int x = point.getCoordX();
        int y = point.getCoordY();

        return new double[][]{
            {-1, 0, 0},
            {0, -1, 0},
            {2 * x, 2 * y, 1}
        };
    }

    /**
     * Matrix for symmetry from a line ax + by = c.
     *
     * @param a
     * @param b
     * @param c
     * @return
     */
    public static double[][] getLineSymMat(double a, double b, double c) {
        if (a == 0 && b == 0) {
            return null;
        }

        if (a == 0) {
            return getHorSymMat(c / b);
        } else if (b == 0) {
            return getVerSymMat(c / a);
        }

        // Vector chi phuong cua (d): ax + by = c
        Vector2D vtcp = Vector2D.getVTCP(a, b, c);

        // Angle (CCW) with Ox from this line.
        double angleWithOx = Math.PI - vtcp.getAngleWithOx();

        double cos = Math.cos(angleWithOx);
        double sin = Math.sin(angleWithOx);

        // Toa do giao diem (d) voi Ox
        double x0 = c / a;

        double m1 = cos * cos - sin * sin;
        double m2 = 2 * cos * sin;

        return new double[][]{
            {m1, -m2, 0},
            {-m2, -m1, 0},
            {x0 * (-m1 + cos), x0 * (m2 - sin), 1}
        };
    }

    /**
     * Get vertical symmetry from line x = k
     *
     * @param k
     * @return
     */
    public static double[][] getVerSymMat(double k) {
        return new double[][]{
            {-1, 0, 0},
            {0, 1, 0},
            {2 * k, 0, 1}
        };
    }

    /**
     * Get horizontal symmetry from line y = k
     *
     * @param k
     * @return
     */
    public static double[][] getHorSymMat(double k) {
        return new double[][]{
            {1, 0, 0},
            {0, -1, 0},
            {0, 2 * k, 1}
        };
    }

    public static SKPoint2D getTransformPoint(SKPoint2D point, double[][] m) {
        double x = point.getCoordX();
        double y = point.getCoordY();

        SKPoint2D ret = new SKPoint2D();

        ret.setLocation(
                Math.round(x * m[0][0] + y * m[1][0] + 1 * m[2][0]),
                Math.round(x * m[0][1] + y * m[1][1] + 1 * m[2][1])
        );

        return ret;
    }

    public static void transform(SKPoint2D point, double[][] m) {
        double x = point.getCoordX();
        double y = point.getCoordY();

        double new_x = Math.round(x * m[0][0] + y * m[1][0] + 1 * m[2][0]);
        double new_y = Math.round(x * m[0][1] + y * m[1][1] + 1 * m[2][1]);

        point.setLocation(new_x, new_y);
    }

    public static Vector2D getTransformVector(Vector2D vec, double[][] m) {
        double x = vec.getCoordX();
        double y = vec.getCoordY();

        Vector2D ret = new Vector2D();

        ret.setCoord(
                (x * m[0][0] + y * m[1][0] + 1 * m[2][0]),
                (x * m[0][1] + y * m[1][1] + 1 * m[2][1])
        );

        return ret;
    }

    public static void transform(Vector2D vec, double[][] m) {
        double x = vec.getCoordX();
        double y = vec.getCoordY();

        double new_x = Math.round(x * m[0][0] + y * m[1][0] + 1 * m[2][0]);
        double new_y = Math.round(x * m[0][1] + y * m[1][1] + 1 * m[2][1]);

        vec.setCoord(new_x, new_y);
    }
    
    public static void main(String[] args) {
        SKPoint2D p = new SKPoint2D(2, 5);
        System.out.println(Transform2D.getTransformPoint(p, Transform2D.getVerSymMat(7)));
    }
}
