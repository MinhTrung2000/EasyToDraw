/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

/**
 *
 * @author Minh Tu
 */
public class Point2D {
    public int X;
    public int Y;
    public Point2D(int x, int y){
        this.X=x;
        this.Y=y;
    }
    public Point2D ConvertToPixelCoord (Point2D inputCoord, int OX, int OY){
        Point2D result= new Point2D(0,0);
        result.X= OX-2+inputCoord.X*7;// công thức chuyển tọa độ ( 7 = space + size);
        result.Y= OY-2-inputCoord.Y*7;
        return result;
    }
}
