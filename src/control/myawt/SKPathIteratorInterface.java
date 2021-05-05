package control.myawt;

public interface SKPathIteratorInterface {

    int WIND_EVEN_ODD = 0;
    int WIND_NON_ZERO = 1;

    int SEG_MOVETO = 0;
    int SEG_LINETO = 1;
    int SEG_QUADTO = 2;
    int SEG_CUBICTO = 3;
    int SEG_CLOSE = 4;
    
    int getWindingRule();
    
    boolean isDone();
    
    void next();
    
    int currentSegment(double[] coords);
}