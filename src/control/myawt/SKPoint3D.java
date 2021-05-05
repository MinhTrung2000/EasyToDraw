package control.myawt;

import control.SettingConstants;
import control.myawt.SKPoint2D;

public class SKPoint3D extends SKPoint2D {

    private double coordZ;

    public SKPoint3D() {
        super();
        coordZ = 0.0;
    }

    public SKPoint3D(double coordX, double coordY, double coordZ) {
        super(coordX, coordY);
        this.coordZ = coordZ;
    }

    public SKPoint3D(SKPoint3D other) {
        super(other.coordX, other.coordY);
        this.coordZ = other.coordZ;
    }

    public void setCoordZ(double coordZ) {
        this.coordZ = coordZ;
    }

    @Override
    public double getCoordZ() {
        return coordZ;
    }

    public void setLocation(double coordX, double coordY, double coordZ) {
        super.setLocation(coordX, coordY);
        this.coordZ = coordZ;
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
        double tmpX = (this.coordX - Math.round(this.coordY * Math.cos(Math.toRadians(45))));
        double tmpY = (this.coordZ - Math.round(this.coordY * Math.sin(Math.toRadians(45))));
        ret.setLocation(tmpX + SettingConstants.WIDTH_DRAW_AREA / 2, -tmpY + SettingConstants.HEIGHT_DRAW_AREA / 2);
        return ret;
    }

    @Override
    public void saveCoord(String[][] coordOfBoard) {
        String coordPointInformation = "(" + this.coordX + ", "
                + this.coordY + ", "
                + this.coordZ + ")";

        SKPoint2D relativePoint = get2DRelativePosition();

        coordOfBoard[(int) relativePoint.getCoordX()][(int) relativePoint.getCoordY()] = coordPointInformation;
    }
    @Override
    public String toString() {
        return "(" + coordX + ", " + coordY + ", " + coordZ + ")";
    }
}
