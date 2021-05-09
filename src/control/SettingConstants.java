package control;

import control.myawt.SKPoint2D;
import java.awt.Color;
import java.util.ArrayList;

public interface SettingConstants {

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
        DOT,
    }

    public enum DrawingToolMode {
        TOOL_COLOR_PICKER("") {
            public String toString() {
                return "COLOR PICKER";
            }
        },
        TOOL_COLOR_FILLER("") {
            public String toString() {
                return "COLOR FILLER";
            }
        },
        TOOL_CLEAR_ALL("") {
            public String toString() {
                return "CLEAR ALL";
            }
        },
        TOOL_ERASER("") {
            public String toString() {
                return "ERASER";
            }
        },
        TOOL_SELECT("") {
            public String toString() {
                return "SELECT";
            }
        },
        TOOL_ANIMATION("") {
            public String toString() {
                return "ANIMATION";
            }
        },
        DRAWING_LINE_SEGMENT("Press SHIFT to draw Straight Line Segment") {
            public String toString() {
                return "LINE SEGMENT";
            }
        },
        DRAWING_LINE_STRAIGHT("") {
            public String toString() {
                return "STRAIGHT LINE";
            }
        },
        DRAWING_LINE_FREE("") {
            public String toString() {
                return "PENCIL";
            }
        },
        DRAWING_POLYGON_FREE("") {
            public String toString() {
                return "POLYGON";
            }
        },
        DRAWING_POLYGON_TRIANGLE("Press SHIFT to draw Equilateral Triangle") {
            public String toString() {
                return "TRIANGLE";
            }

        },
        DRAWING_POLYGON_RECTANGLE("Press SHIFT to draw Square") {
            public String toString() {
                return "RECTANGLE";
            }

        },
        DRAWING_POLYGON_ELLIPSE("Press SHIFT to draw Ellipse") {
            public String toString() {
                return "ELLIPSE";
            }

        },
        DRAWING_SHAPE_STAR("") {
            public String toString() {
                return "STAR";
            }

        },
        DRAWING_SHAPE_DIAMOND("Press SHIFT to draw Square Diamond") {
            public String toString() {
                return "DIAMOND";
            }

        },
        DRAWING_SHAPE_ARROW("") {
            public String toString() {
                return "ARROW";
            }

        },
        DRAWING_3DSHAPE_RECTANGULAR("") {
            public String toString() {
                return "RECTANGULAR";
            }

        },
        DRAWING_3DSHAPE_CYLINDER("") {
            public String toString() {
                return "CYLINDER";
            }

        },
        DRAWING_3DSHAPE_PYRAMID("") {
            public String toString() {
                return "PYRAMID";
            }

        },
        DRAWING_3DSHAPE_SPHERE("") {
            public String toString() {
                return "SPHERE";
            }

        },
        DRAWING_TRANSFORM_ROTATION("") {
            public String toString() {
                return "ROATATION";
            }

        },
        DRAWING_TRANSFORM_SYMMETRY("") {
            public String toString() {
                return "SYMMETRY";
            }

        },
        TOOL_ERASER__FALSE("") {
            public String toString() {
                return "";
            }
        },
        
        // Adding
        
        // Perpendicular Line
        MODE_ORTHOGONAL(""),
        MODE_INTERSECT(""),
        MODE_DELETE(""),
        MODE_RELATION(""),
        MODE_MIDPOINT("");
        
        

        public final String toolTip;

        private DrawingToolMode(String toolTip) {
            this.toolTip = toolTip;
        }
    }

    // Note: Don't remove this, it is used to help anyone who 
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

    public static final int COORD_X_O = (int) (WIDTH_DRAW_AREA / 2) - 1;
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

    public static final SKPoint2D DEFAULT_UNUSED_POINT = new SKPoint2D(-1, -1);

    public static final Color DEFAULT_DRAWING_COLOR = Color.BLACK;
    public static final Color DEFAULT_FILL_COLOR = Color.BLACK;
}
