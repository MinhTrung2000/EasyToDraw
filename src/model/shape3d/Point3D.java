package model.shape3d;

import model.shape2d.Point2D;

public class Point3D extends Point2D {

    private int coordZ;

    public Point3D() {
        super();
        coordZ = 0;
    }

    public Point3D(int coordX, int coordY, int coordZ) {
        super(coordX, coordY);
        this.coordZ = coordZ;
    }

    public Point3D(Point3D other) {
        super(other.coordX, other.coordY);
        this.coordZ = other.coordZ;
    }

    public void setCoord(int coordX, int coordY, int coordZ) {
        this.coordX = coordX;
        this.coordY = coordY;
        this.coordZ = coordZ;
    }

    /**
     * Note: Implement this code!
     * @return Point2D
     */
    public Point2D get2DRelativePosition() {
        Point2D relativePoint = new Point2D();

        //??
        return relativePoint;
    }

    @Override
    public void saveCoord(String[][] coordOfBoard) {
        String coordPointInformation = "(" + this.coordX + ", "
                + this.coordY + ", "
                + this.coordZ + ")";
        
        Point2D relativePoint = get2DRelativePosition();
        
        coordOfBoard[relativePoint.getCoordX()][relativePoint.getCoordY()] = coordPointInformation;
    }
}
