package control.myawt;

/**
 * Extends the View functionality by layer listener
 *
 * @author Zbynek
 *
 */
public interface ViewListenerInterface extends ViewInterface {

    /**
     * Called when layer is being changed
     *
     * @param g element that changed layer
     * @param oldLayer old layer
     * @param newLayer new layer
     */
    public void changeLayer(GeoElement g, int oldLayer, int newLayer);
}
