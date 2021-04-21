package view;

import model.shape2d.Point2D;
import java.awt.Color;
import java.util.ArrayList;

/**
 * Application-wide constants.
 */
public interface SketchPointConstants {

    /*
    Sketch Point version.
    Don't change the format of the VERSION_STRING (or comment out).
    Because it is read by the build system and update automatically
    by building task.
    */
    
    /** Last build date */
    public static final String BUILD_DATE = "20 April 2021";
    /** Complete version string */
    public static final String VERSION_STRING = "1.0.0";
    /** Application name */
    public static final String APPLICATION_NAME = "SketchPoint";
    
    /** Update download link */
    public static final String DOWNLOAD_PACKAGE_WINDOWS = 
            "https://github.com/MinhTrung2000/SketchPoint/releases";
    
    /*
    Splash screen.
    */
    public static final int SPLASH_DIALOG_DELAY = 1000;
    
    enum CoordinateMode {
        MODE_2D,
        MODE_3D,
    }

    enum LineStyle {
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
        TOOL_COLOR_PICKER {
            public String toString(){
                return "COLOR PICKER";
            }
        },
        TOOL_COLOR_FILLER {
            public String toString(){
                return "COLOR FILLER";
            }
        },
        TOOL_CLEAR_ALL {
            public String toString(){
                return "CLEAR ALL";
            }
        },
        TOOL_ERASER {
            public String toString(){
                return "ERASER";
            }
        },
        TOOL_SELECT {
            public String toString(){
                return "SELECT";
            }
        },
        TOOL_ANIMATION {
            public String toString(){
                return "ANIMATION";
            }
        },
        DRAWING_LINE_SEGMENT {
            public String toString() {
                return "SEGMENT";
            }
        },
        DRAWING_LINE_STRAIGHT {
            public String toString() {
                return "STRAIGHT LINE";
            }
        },
        DRAWING_LINE_FREE {
            public String toString() {
                return "PENCIL";
            }
        },
        DRAWING_POLYGON_FREE {
            public String toString() {
                return "POLYGON";
            }
        },
        DRAWING_POLYGON_TRIANGLE {
            public String toString() {
                return "TRIANGLE";
            }
        },
        DRAWING_POLYGON_RECTANGLE {
            public String toString() {
                return "RECTANGLE";
            }
        },
        DRAWING_POLYGON_CIRCLE {
            public String toString() {
                return "CIRCLE";
            }
        },
        DRAWING_SHAPE_STAR {
            public String toString() {
                return "STAR";
            }
        },
        DRAWING_SHAPE_DIAMOND {
            public String toString() {
                return "DIAMOND";
            }
        },
        DRAWING_SHAPE_ARROW {
            public String toString() {
                return "ARROW";
            }
        },
        DRAWING_TRANSFORM_ROTATION {
            public String toString() {
                return "ROATATION";
            }
        },
        DRAWING_TRANSFORM_SYMMETRY {
            public String toString() {
                return "SYMMETRY";
            }
        },
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
