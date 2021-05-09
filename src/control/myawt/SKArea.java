package control.myawt;

public interface SKArea extends SKShapeInterface {
    void subtract(SKArea shape);
    
    void intersect(SKArea shape);
    
    void exclusiveOR(SKArea shape);
    
    void add(SKArea shape);
    
    boolean isEmpty();
}
