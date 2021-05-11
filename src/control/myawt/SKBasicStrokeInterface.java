package control.myawt;

public interface SKBasicStrokeInterface {

    int CAP_BUTT = 0; // Java & GWT
    int CAP_ROUND = 1; // Java & GWT
    int CAP_SQUARE = 2; // Java & GWT
    int JOIN_MITER = 0; // Java
    int JOIN_ROUND = 1; // Java
    int JOIN_BEVEL = 2; // Java

    SKShapeInterface createStrokedShape(SKShapeInterface shape, int capacity);

    int getEndCap();

    double getMiterLimit();

    int getLineJoin();

    double getLineWidth();

    float[] getDashArray();

}
