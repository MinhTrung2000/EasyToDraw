package main;

import java.awt.Color;
import java.util.ArrayList;

/**
 *
 */
public class Shape {

    private static final double DEFAULT_ANGLE = 0.0;
    private static final Color DEFAULT_COLOR = Color.WHITE;
    private static final Settings.LineStyle DEFAULT_LINE_STYLE = Settings.LineStyle.DEFAULT;
    //??
    private boolean[][] nextDrawing;
    private Color[][] nextPoint;

    // List to store points of shape in order.
    private ArrayList<Point2D> list_Points;
    private ArrayList<Color> list_Colors;

    // The angle of this shape after rotation.
    private double rotatedAngle;

    // Line style of shape
    private Settings.LineStyle lineStyle;

    // Color choosed for shape.
    private Color colorShape;

    public Shape() {
        nextDrawing = null;
        nextPoint = null;
        list_Points = new ArrayList<>();
        list_Colors = new ArrayList<>();
        rotatedAngle = DEFAULT_ANGLE;
        lineStyle = DEFAULT_LINE_STYLE;
        this.colorShape = DEFAULT_COLOR;
    }

    public Shape(boolean[][] nextDrawing, Color[][] nextPoint, Color choosedColor) {
        this.nextDrawing = nextDrawing;
        this.nextPoint = nextPoint;
        list_Points = new ArrayList<>();
        list_Colors = new ArrayList<>();
        rotatedAngle = DEFAULT_ANGLE;
        lineStyle = DEFAULT_LINE_STYLE;
        this.colorShape = choosedColor;
    }

    // Animation Purpose
    /*
    public void drawZigzag(ArrayList<Point2D> points) {
        for (int i = 0; i < points.size(); i++) {

        }
    }
     */
    
    // ??
    private void drawPoint(int coordX, int coordY) {
        nextDrawing[coordX][coordY] = true;
        nextPoint[coordX][coordY] = colorShape;
    }
}
