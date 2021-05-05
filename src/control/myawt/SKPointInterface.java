package control.myawt;

public interface SKPointInterface extends Translateable {

    /**
     * cannot move
     */
    public static int MOVE_MODE_NONE = 0; // for intersection points and fixed
    // points
    /**
     * can move in xy directions
     */
    public static int MOVE_MODE_XY = 1;
    /**
     * can move in z direction
     */
    public static int MOVE_MODE_Z = 2;
    /**
     * use tool default: XY for move, Z for others
     */
    public static int MOVE_MODE_TOOL_DEFAULT = 3;
    /**
     * can move in xyz directions
     */
    public static int MOVE_MODE_XYZ = 4;
}
