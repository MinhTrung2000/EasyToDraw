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
        int tempX = (int) (this.coordX - Math.round(this.coordY * Math.cos(Math.toRadians(45))));
        int tempY = (int) (this.coordZ - Math.round(this.coordY * Math.sin(Math.toRadians(45))));
        ret.setLocation(tempX, tempY);
        return ret;
    }

    public static SKPoint2D get2DRelativePosition(double x, double y, double z) {
        SKPoint2D ret = new SKPoint2D();
        int tempX = (int) (x - Math.round(y * Math.cos(Math.toRadians(45))));
        int tempY = (int) (z - Math.round(y * Math.sin(Math.toRadians(45))));
        ret.setLocation(tempX, tempY);
        return ret;
    }

    @Override
    public void saveCoord(String[][] coordOfBoard) {
        SKPoint2D relativePoint2D = this.get2DRelativePosition().convertViewToMachineCoord();
        if (Ultility.checkValidPoint(coordOfBoard, relativePoint2D.coordX, relativePoint2D.coordY)) {
            int x = (int) (coordX / SettingConstants.RECT_SIZE);
            int y = (int) (coordY / SettingConstants.RECT_SIZE);
            int z = (int) (coordZ / SettingConstants.RECT_SIZE);
            coordOfBoard[relativePoint2D.coordY][relativePoint2D.coordX] = "(" + x + ", " + y + ", " + z + ")";
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
//        p2d.convertViewToMachineCoord();
//        System.out.println(p2d.convertMachineToViewCoord());
    }
}
