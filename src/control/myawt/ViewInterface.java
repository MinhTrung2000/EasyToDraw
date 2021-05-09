package control.myawt;

/**
 * Interface for views. A view registered to the kernel gets informed if
 * elements were added, removed or changed.
 */
public interface ViewInterface {

    void add(GeoElement geo);

    void remove(GeoElement geo);

    void update(GeoElement geo);

    /**
     * Repaints all objects.
     */
    void repaintView();

    boolean suggestRepaint();

    void reset();

    void clearView();

    /**
     * Notify this view about changed mode
     *
     * @param mode
     * @param m
     */
    void setMode(int mode, ModeTrigger m);

    /**
     * Get unique ID of this view.
     *
     * @return
     */
    int getViewID();
    
    boolean hasFocus();
    
    void startBatchUpdate();
    
    void endBatchUpdate();
    
    void updatePreviewFromInputBar(GeoElement[] geos);
}
