package control.myawt;

import control.SettingConstants;
import control.myawt.SKPoint2D;
import control.util.Ultility;

public class SKPoint3D extends SKPoint2D {

    public static final double COS_DEGREE_45 = Math.cos(Math.toRadians(45));
    public static final double SIN_DEGREE_45 = Math.sin(Math.toRadians(45));

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

    public SKPoint2D get2DRelativePosition() {
        //cabinet, f = 1/2 => y = y/2
        SKPoint2D ret = new SKPoint2D();
        int tempX = (int) (this.coordX - Math.round(this.coordY/2 * COS_DEGREE_45));
        int tempY = (int) (this.coordZ - Math.round(this.coordY/2 * SIN_DEGREE_45));
        ret.setLocation(tempX, tempY);
        ret.convertToSystemCoord();
        return ret;
    }

    public static SKPoint2D get2DRelativePosition(double x, double y, double z) {
        SKPoint2D ret = new SKPoint2D();
        int tempX = (int) (x - Math.round(y/2 * COS_DEGREE_45));
        int tempY = (int) (z - Math.round(y/2 * SIN_DEGREE_45));
        ret.setLocation(tempX, tempY);
        ret.convertToSystemCoord();
        return ret;
    }

    @Override
    public void saveCoord(String[][] coordOfBoard) {
        SKPoint2D relativePoint2D = get2DRelativePosition();
        if (Ultility.checkValidPoint(coordOfBoard, relativePoint2D.coordX, relativePoint2D.coordY)) {
            coordOfBoard[relativePoint2D.coordY][relativePoint2D.coordX] = "(" + coordX + ", " + coordY + ", " + coordZ + ")";
        }
    }

    @Override
    public String toString() {
        return "(" + coordX + ", " + coordY + ", " + coordZ + ")";
    }

    public static void main(String[] args) {
        SKPoint3D p = new SKPoint3D(25, 25, 25);
        System.out.println("to 2d:" + p.get2DRelativePosition());

//        SKPoint2D p2d = new SKPoint2D(26, 20);
//        p2d.convertToSystemCoord();
//        System.out.println(p2d.convertToVisualCoord());
    }
}
