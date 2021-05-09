package control.myawt;

import java.util.ArrayList;

/**
 * Provides methods for computation
 *
 */
public class Kernel {

    /**
     * standard precision
     */
    public final static double STANDARD_PRECISION = 1E-8;
    /**
     * square root of standard precision
     */
    public final static double STANDARD_PRECISION_SQRT = 1E-4;
    /**
     * square of standard precision
     */
    public final static double STANDARD_PRECISION_SQUARE = 1E-16;
    /**
     * cube of standard precision
     */
    public final static double STANDARD_PRECISION_CUBE = 1E-24;

    /**
     * minimum precision
     */
    public final static double MIN_PRECISION = 1E-5;
    /**
     * 1 / (min precision)
     */
    public final static double INV_MIN_PRECISION = 1E5;

    /**
     * maximum reasonable precision
     */
    public final static double MAX_PRECISION = 1E-12;

    /**
     * maximum axes can zoom to
     */
    private final static double AXES_PRECISION = 1E-14;
    /**
     * Angle unit: radians
     */
    final public static int ANGLE_RADIANT = 1;
    /**
     * Angle unit: degrees
     */
    final public static int ANGLE_DEGREE = 2;

    /**
     * Angle unit is degree default
     */
    private int angleUnit = ANGLE_DEGREE;

    // Views may register to be informed about
    // changes to the Kernel
    // (add, remove, update)
    protected ArrayList<ViewInterface> views = new ArrayList<>();

    private boolean addingPolygon = false;
}
