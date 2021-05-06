package control.myawt;

import control.SettingConstants;
import control.myawt.SKPoint2D;
import control.util.Ultility;

public class SKPoint3D extends SKPoint2D {

    private int coordZ;

    public SKPoint3D() {
        super();
        coordZ = 0;
    }

    public SKPoint3D(double coordX, double coordY, double coordZ) {
        super(coordX, coordY);
        this.coordZ = (int) coordZ;
    }

    public SKPoint3D(SKPoint3D other) {
        super(other.coordX, other.coordY);
        this.coordZ = other.coordZ;
    }

    public void setCoordZ(int coordZ) {
        this.coordZ = coordZ;
    }

    @Override
    public int getCoordZ() {
        return coordZ;
    }

    public void setLocation(double coordX, double coordY, double coordZ) {
        super.setLocation(coordX, coordY);
        this.coordZ = (int) coordZ;
    }

    public void setLocation(SKPoint3D other) {
        super.setLocation(other.coordX, other.coordY);
        this.coordZ = other.coordZ;
    }

    /**
     * Note: Implement this code!
     *
     * @return SKPoint2D
     */
    public SKPoint2D get2DRelativePosition() {
        SKPoint2D ret = new SKPoint2D();
        int tmpX = (int) (this.coordX - Math.round(this.coordY * Math.cos(Math.toRadians(45))));
        int tmpY = (int) (this.coordZ - Math.round(this.coordY * Math.sin(Math.toRadians(45))));
        ret.setLocation(tmpX, tmpY);
        ret.convertViewToMachineCoord();
        return ret;
    }

    @Override
    public String toString() {
        return "(" + coordX + ", " + coordY + ", " + coordZ + ")";
    }
}
