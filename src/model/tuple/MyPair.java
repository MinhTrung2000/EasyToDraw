package model.tuple;

/**
 * Pair of two integer x, y for point.
 *
 * @author DELL
 */
public class MyPair {

    /**
     * y-coordinate *
     */
    public int y;
    /**
     * x-coordinate *
     */
    public int x;

    /**
     * Point (0, 0)
     */
    public MyPair() {
        x = 0;
        y = 0;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }

    /**
     * @param x x-coord
     * @param y y-coord
     */
    public MyPair(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Set x and y at the same time
     *
     * @param x x-coord
     * @param y y-coord
     */
    public void setLocation(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Take both coords from a point
     *
     * @param p point
     */
    public void setLocation(MyPair p) {
        this.x = p.x;
        this.y = p.y;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public int getZ() {
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof MyPair)) {
            return false;
        }   
        
        return ((MyPair) o).x == x 
                && ((MyPair) o).y == y
                && ((MyPair) o).getZ() == getZ();
    }

    @Override
    public int hashCode() {
        return (x << 16) ^ y;
    }

    public double distance(MyPair d) {
        return Math.sqrt((x - d.x) * (x - d.x) + (y - d.y) * (y - d.y));
    }

    public double distance(double dx, double dy) {
        return (int) Math.sqrt((x - dx) * (x - dx) + (y - dy) * (y - dy));
    }

    @Override
    public String toString() {
        return x + " : " + y;
    }
}
