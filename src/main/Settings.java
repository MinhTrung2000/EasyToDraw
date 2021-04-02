package main;

/**
 * This class contains all settings for this project.
 */
public class Settings {
    public Settings() {}
    
    // Note: Don't remove this, I want to have something to help anyone who 
    // read this code can understand the relation between visual pixel and 
    // machine pixel.
    public static final int PIXEL_UNIT = 1;
    
    // The size of drawing area.
    public static final int WIDTH_DRAW_AREA = 918;
    public static final int HEIGHT_DRAW_AREA = 718;
    
    // Khoảng cách 2 pixel.
    public static final int SPACE = 2 * PIXEL_UNIT;
    // Kích thước 1 pixel.
    public static final int SIZE = 5 * PIXEL_UNIT;

    // Tọa độ x của tâm O.
    public static final int COORD_X_O = WIDTH_DRAW_AREA / 2 - 1;
    // Tọa độ y của tâm O.
    public static final int COORD_Y_O = HEIGHT_DRAW_AREA / 2 + 1;
    // Tọa độ z của tâm O trong 3D.
    // private final int COORD_Z_O = ?
}
