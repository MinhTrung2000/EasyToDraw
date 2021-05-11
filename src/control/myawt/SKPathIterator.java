package control.myawt;

import java.awt.geom.PathIterator;

public class SKPathIterator implements SKPathIteratorInterface {
    
    PathIterator impl;

    public SKPathIterator(PathIterator impl) {
        this.impl = impl;
    }

    @Override
    public int getWindingRule() {
        return impl.getWindingRule();
    }

    @Override
    public boolean isDone() {
        return impl.isDone();
    }

    @Override
    public void next() {
        impl.next();
    }

    @Override
    public int currentSegment(double[] coords) {
        return impl.currentSegment(coords);
    }

}
