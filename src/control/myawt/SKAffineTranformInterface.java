package control.myawt;

public interface SKAffineTranformInterface {
    void setTransform(SKAffineTranformInterface at);
    
    void setTransform(double m00, double m10, double m01, double m11, double m02, double m12);
    
    double getScaleX();
    
    double getScaleY();
    
    double getShearX();
    
    double getShearY();
    
    SKShapeInterface createTranformedShape(SKShapeInterface shape);
    
    SKPoint2D transform(SKPoint2D point1, SKPoint2D point2);
    
    void tranform(double[] labelCoords1, int i, double[] labelCoords2, int j, int k);
    
    SKAffineTranformInterface createInverse() throws Exception;
    
    void scale(double xScale, double d);
    
    void setToScale(double sx, double sy);
    
    void translate(double x, double y);
    
    double getTranslateX();
    
    double getTranslateY();
    
    void setToTranslation(double tx, double ty);
    
    void rotate(double angle);
    
    void setToRotation(double angle);
    
    void setToRotaion(double angle, double x, double y);

    boolean isIdentity();
    
    void getMatrix(double[] m);
}
