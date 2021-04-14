package main;

import model.shape2d.Point2D;
import java.awt.Color;
import java.util.ArrayList;

/**
 * This class contains all settings for this project.
 */
public class Settings {

    public enum CoordinateMode {
        MODE_2D,
        MODE_3D,
    }

    public enum LineStyle {
        DEFAULT {
            public String toString() {
                return "────────────";
            }
        },
        DASH {
            public String toString() {
//                return "---------------------";
                return "─── ─── ─── ──";
            }
        },
        DASH_DOT {
            public String toString() {
                return "── ∙ ── ∙ ── ∙ ──";
            }
        },
        DASH_DOT_DOT {
            public String toString() {
                return "── ∙ ∙ ── ∙ ∙ ── ∙ ∙";
            }
        },
        ARROW {
            public String toString() {
                return "───────────→";
            }
        },
    }

    public enum DrawingToolMode {
        TOOL_COLOR_PICKER,
        TOOL_COLOR_FILLER,
        TOOL_CLEAR_ALL,
        TOOL_ERASER,
        TOOL_SELECT,
        TOOL_ANIMATION,
        DRAWING_LINE_SEGMENT {
            public String toString() {
                return "Segment";
            }
        },
        DRAWING_LINE_STRAIGHT {
            public String toString() {
                return "Straight line";
            }
        },
        DRAWING_LINE_FREE {
            public String toString() {
                return "Pencil";
            }
        },
        DRAWING_POLYGON_FREE {
            public String toString() {
                return "Polygon";
            }
        },
        DRAWING_POLYGON_TRIANGLE {
            public String toString() {
                return "Triangle";
            }
        },
        DRAWING_POLYGON_RECTANGLE {
            public String toString() {
                return "Rectangle";
            }
        },
        DRAWING_POLYGON_CIRCLE {
            public String toString() {
                return "Circle";
            }
        },
        DRAWING_SHAPE_STAR {
            public String toString() {
                return "Star";
            }
        },
        DRAWING_SHAPE_DIAMOND {
            public String toString() {
                return "Diamond";
            }
        },
        DRAWING_SHAPE_ARROW {
            public String toString() {
                return "Arrow";
            }
        },
        DRAWING_TRANSFORM_ROTATION {
            public String toString() {
                return "Rotation";
            }
        },
        DRAWING_TRANSFORM_SYMMETRY {
            public String toString() {
                return "Symmetry";
            }
        },
    }

    public Settings() {
    }

    // Note: Don't remove this, I want to have something to help anyone who 
    // read this code can understand the relation between visual pixel and 
    // machine pixel.
    public static final int PIXEL_UNIT = 1;

    // The size of drawing area (fixed version).
    // TODO: remove if find out the way to make drawing area resizeable.
    public static final int WIDTH_DRAW_AREA = 1173;
    public static final int HEIGHT_DRAW_AREA = 656;

    // Khoảng cách 2 pixel.
    public static final int SPACE = 1 * PIXEL_UNIT;
    // Kích thước 1 pixel.
    public static final int SIZE = 5 * PIXEL_UNIT;

    public static final int RECT_SIZE = SPACE + SIZE;
    
    // Line size option
    public static final int MIN_LINE_SIZE = 1;
    public static final int MAX_LINE_SIZE = 5;
    public static final int STEP_LINE_SIZE = 1;

    /**
     * X coordinate part of O point.
     */
    public static final int COORD_X_O = (int) (WIDTH_DRAW_AREA / 2) - 1;

    /**
     * Y coordinate part of O point.
     */
    public static final int COORD_Y_O = (int) (HEIGHT_DRAW_AREA / 2) - 1;

    public static final int DEFAULT_LINE_SIZE = 1;

    public static final boolean DEFAULT_VISUAL_SHOW_GRID = true;
    public static final boolean DEFAULT_VISUAL_SHOW_COORDINATE = true;

    
    public static final int DEFAULT_SAVED_COLOR_NUMBER = 8;
    
    public static final Color DEFAULT_COLOR_SAVE_1 = new Color(0, 0, 0);
    public static final Color DEFAULT_COLOR_SAVE_2 = new Color(255, 255, 255);
    public static final Color DEFAULT_COLOR_SAVE_3 = new Color(127, 127, 127);
    public static final Color DEFAULT_COLOR_SAVE_4 = new Color(195, 195, 195);
    public static final Color DEFAULT_COLOR_SAVE_5 = new Color(240, 240, 240);
    public static final Color DEFAULT_COLOR_SAVE_6 = new Color(240, 240, 240);
    public static final Color DEFAULT_COLOR_SAVE_7 = new Color(240, 240, 240);
    public static final Color DEFAULT_COLOR_SAVE_8 = new Color(240, 240, 240);

    public static final Color DEFAULT_PIXEL_COLOR = Color.WHITE;
    public static final Color DEFAULT_EMPTY_BACKGROUND_COLOR = Color.WHITE;
    public static final Color DEFAULT_GRID_BACKGROUND_COLOR = new Color(235, 235, 235);
    public static final Color DEFAULT_COORDINATE_AXIS_COLOR = new Color(128, 128, 128);
    public static final Color DEFAULT_COORDINATE_POINT_COLOR = Color.RED;

    public static final Point2D DEFAULT_UNUSED_POINT = new Point2D(-1, -1);

    public static final Color DEFAULT_DRAWING_COLOR = Color.BLACK;
    public static final Color DEFAULT_FILL_COLOR = Color.BLACK;
}
