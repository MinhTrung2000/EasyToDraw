package control.myawt;

import java.awt.Color;
import java.util.List;

public interface GeoElementInterface {

    void update();

    void setObjColor(Color color);

    void setVisible(boolean visible);

    boolean isVisible();

    boolean isInfinite();

    void remove();

    GeoElementInterface copy();

    boolean isIndependent();

//    AlgoElement getParentAlgorithm();

    boolean isDefined();

    void setUndefined();

    void setLineType(int type);

    boolean isPointOnPath();

    boolean isPointInRegion();

    double distance(SKPoint2D point);

    /**
     * Update this geo and all its descendants.
     */
    void updateCascade();

    /**
     * Update and repaint.
     */
    void updateRepaint();

    int getLineType();

    int getLineThickness();

    /**
     * Update value and basic properties from other geo. Implemented in each
     * subclass.
     *
     * @param other
     */
    void set(GeoElement other);

    /**
     * @return whether this is a point (ND)
     */
    boolean isGeoPoint();

    /**
     *
     * @return IDs of views that contain this geo
     */
    List<Integer> getViewSet();

    /**
     *
     * @return whether this is a segment
     */
    boolean isGeoSegment();

    /**
     *
     * @return whether this is a polygon
     */
    boolean isGeoPolygon();

    /**
     *
     * @return whether this can be drawn in 2D
     */
    boolean isDrawable();

    boolean isParentOf(final GeoElement geo);

    /**
     * Remove this geo and all its dependents.
     */
    void doRemove();

    /**
     *
     * @return whether this object is parent of other geos.
     */
    boolean hasChildren();

    /**
     *
     * @param geo
     * @return whether the elements are equal in geometric sense (for congruency
     * use isCongruent)
     */
    boolean isEqual(GeoElement geo);
    
    Kernel getKernel();
    
    /**
     * 
     * @return algorithm responsible for drawing this
     */
//    AlgoElement getDrawAlgorithm();
    
    
}
