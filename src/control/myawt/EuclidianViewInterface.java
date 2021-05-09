package control.myawt;

import java.util.ArrayList;

public interface EuclidianViewInterface extends ViewListenerInterface {

    /**
     * @param algo algorithm
     * @return free input points of given algorithm
     */
//    ArrayList<SKPointInterface> getFreeInputPoints(AlgoElement algo);

    /**
     * @param geoElement element
     * @return true if the element can be moved freely in this view
     */
    boolean isMoveable(GeoElement goeElement);

    /**
     * @return width in pixels of physical view. use getMaxXScreen() -
     * getMinXScreen() if you need the width for exporting
     */
    int getWidth();

    /**
     * @return height in pixels of physical view. use getMaxYScreen() -
     * getMinYScreen() if you need the width for exporting
     */
    int getHeight();

    /**
     * convert screen coordinate x to real world coordinate x
     *
     * @param x screen coord
     * @return real world coord
     */
    public double toRealWorldCoordX(double x);

    /**
     * convert screen coordinate x to real world coordinate x
     *
     * @param y screen coord
     * @return real world coord
     */
    public double toRealWorldCoordY(double y);
    
    
}
