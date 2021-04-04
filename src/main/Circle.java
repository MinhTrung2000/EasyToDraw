package main;

public class Circle extends Shape {
    
    public Circle() {}
    
    public Circle(Point2D startPoint, Point2D descPoint2D)  {
        
    }
    
    public static void draw(Point2D startPoint, Point2D descPoint, Settings.LineStyle lineStyle) {
        if (startPoint.getCoordX() == -1 || startPoint.getCoordY() == -1) 
            return;
        
        int radius = (int) (new Vector2D(startPoint, descPoint)).getLength();
        
        
    }
}
