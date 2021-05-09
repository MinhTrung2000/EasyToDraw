package control.myawt;

import java.awt.Component;
import java.awt.event.ComponentListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelListener;

public interface EuclidianControllerListener extends MouseListener,
        MouseMotionListener, MouseWheelListener, ComponentListener {

    /**
     * Add listeners to the panel
     *
     * @param evjpanel panel
     */
    public void addListenersTo(Component evjpanel);
}
