package model.shape3d;

import control.SettingConstants;
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
     *
     * @return Point2D
     */
    public Point2D get2DRelativePosition() {
        Point2D ret = new Point2D();
        int tmpX = (int) (this.coordX - Math.round(this.coordY * Math.cos(Math.toRadians(45))));
        int tmpY = (int) (this.coordZ - Math.round(this.coordY * Math.sin(Math.toRadians(45))));
        ret.setCoord(tmpX + SettingConstants.WIDTH_DRAW_AREA / 2, -tmpY + SettingConstants.HEIGHT_DRAW_AREA / 2);
        return ret;
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
