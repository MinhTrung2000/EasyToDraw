package control.myawt;

import java.awt.Shape;
import java.util.ArrayList;

/**
 * A GeneralPath implementation that does clipping of line segments at the
 * screen in double coordinates. This is important to avoid rendering problems
 * that occur with GeneralPath when coordinates are larger than Float.MAX_VALUE.
 *
 * @author Markus Hohenwarter
 * @version October 2009
 */
public class GeneralPathClipped implements SKShapeInterface {

    private static final double MAX_COORD_VALUE = 10000;

    private final ArrayList<MyPoint> pathPoints;

    private final SKGeneralPathInterface generalPath;

    protected EuclidianViewInterface view;

    private double largestCoord;
    private int lineThinkness;

    private boolean isPolygon = true;

    private boolean needClosePath;

    private SKRectangleInterface oldBounds;
    private SKRectangleInterface bounds;

    // First control point
    private double controlPoint1X = Double.NaN;
    private double controlPoint1Y = Double.NaN;

    // Second control point
    private double controlPoint2X = Double.NaN;
    private double controlPoint2Y = Double.NaN;

    private final ClipAlgoSutherlandHodogman clipAlgoSutherlandHodogman;

    public GeneralPathClipped(EuclidianViewInterface view) {
        this.view = view;
        this.pathPoints = new ArrayList<>();
        this.generalPath = AwtFactory.getPrototype().newGeneralPath();
        this.clipAlgoSutherlandHodogman = new ClipAlgoSutherlandHodogman();
    }

    public MyPoint firstPoint() {
        if (pathPoints.isEmpty()) {
            return null;
        }
        return pathPoints.get(0);
    }

    /**
     * Clears all points and resets internal variables
     */
    public void reset() {
        pathPoints.clear();
        generalPath.reset();
        oldBounds = bounds;
        bounds = null;
        largestCoord = 0;
        needClosePath = false;
    }

    public void closePath() {
        needClosePath = true;
    }

    // XXX
    public SKGeneralPathInterface getGeneralPath() {
        if (pathPoints.isEmpty()) {
            return generalPath;
        }

        generalPath.reset();

        if (largestCoord < MAX_COORD_VALUE || !isPolygon) {
            addSimpleSegments();
        } else {
            addSegmentsWithSutherladHoloman();
        }

        // Free memory
        pathPoints.clear();

        return generalPath;
    }

    private void addSimpleSegments() {
        for (int i = 0; i < pathPoints.size(); i++) {
            MyPoint currPoint = pathPoints.get(i);

            if (currPoint != null) {
//                addToGeneralPath(currPoint, currPoint.getPointDrawingType());
            } else {

            }
        }

        if (needClosePath) {
            generalPath.closePath();
        }
    }

    private void addSegmentsWithSutherladHoloman() {
        int padding = this.lineThinkness + 5;
        double[][] clipPoints = {
            {-padding, -padding},
            {-padding, view.getHeight() + padding},
            {view.getWidth() + padding, view.getHeight() + padding},
            {view.getWidth(), -padding},};

        if (needClosePath) {
//            pathPoints.get(0).setLineTo(true);
        }

        ArrayList<MyPoint> result = clipAlgoSutherlandHodogman.process(pathPoints, clipPoints);

        for (MyPoint curP : result) {
//            addToGeneralPath(curP, curP.getPointDrawingType());
        }

        if (result.size() > 0 && needClosePath) {
            generalPath.closePath();
        }
    }

    @Override
    public SKRectangleInterface getBounds() {
        return null;
    }

    @Override
    public boolean contains(int x, int y) {
        return false;
    }

    @Override
    public boolean containts(double x, double y) {
        return false;
    }

    @Override
    public boolean contains(SKRectangleInterface rect) {
        return false;
    }

    public boolean intersects(int x, int y, int width, int height) {
        return false;
    }

    @Override
    public boolean intersects(double x, double y, double width, double height) {
        return false;
    }

    @Override
    public boolean intersects(SKRectangleInterface rect) {
        return false;
    }

    @Override
    public SKPathIteratorInterface getPathIterator(SKAffineTranformInterface at) {
        return null;
    }

    @Override
    public Shape getAwtShape() {
        return null;
    }

    @Override
    public boolean contains(SKPoint2D point) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
