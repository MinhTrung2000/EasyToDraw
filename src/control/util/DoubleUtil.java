package control.util;

public class DoubleUtil {
    public static boolean isEqual(double x, double y, double eps) {
        if (x == y) {
            return true;
        }
        return ((x - y) < eps) && ((x - y) > -eps);
    }
    
    public static boolean isFinite(double d) {
        return !Double.isNaN(d) && !Double.isInfinite(d);
    }
    
}
