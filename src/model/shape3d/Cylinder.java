package model.shape3d;

import control.myawt.SKPoint2D;
import control.myawt.SKPoint3D;
import control.util.Ultility;
import java.awt.Color;
import java.util.ArrayList;
import model.shape2d.Ellipse;
import model.shape2d.Vector2D;

public class Cylinder extends Shape3D {

    private double radius = 0.0;
    private double high = 0.0;

    private SKPoint3D topCenter;
    private SKPoint3D botCenter;
    private SKPoint2D topLeft, topRight, botLeft, botRight;
    public static final double COS_DEGREE_45 = Math.cos(Math.toRadians(45));
    public static final double SIN_DEGREE_45 = Math.sin(Math.toRadians(45));

    public Cylinder(boolean[][] markedChangeOfBoard, Color[][] changedColorOfBoard, String[][] changedCoordOfBoard, Color filledColor) {
        super(markedChangeOfBoard, changedColorOfBoard, changedCoordOfBoard, filledColor);
    }

    public void setProperty(double center_x, double center_y, double center_z,
            double radius, double high) {
        this.centerPoint3D.setLocation(center_x, center_y, center_z);
        this.radius = radius;
        this.high = high;

        topCenter = new SKPoint3D(centerPoint3D.getCoordX(), centerPoint3D.getCoordY(),
                centerPoint3D.getCoordZ() + this.high / 2);
        botCenter = new SKPoint3D(centerPoint3D.getCoordX(), centerPoint3D.getCoordY(),
                centerPoint3D.getCoordZ() - this.high / 2);

        SKPoint2D topCenter2D = topCenter.get2DRelativePosition();
        SKPoint2D botCenter2D = botCenter.get2DRelativePosition();

        topLeft = new SKPoint2D(topCenter2D, -(int) this.radius, 0);
        topRight = new SKPoint2D(topCenter2D, (int) this.radius, 0);

        botLeft = new SKPoint2D(botCenter2D, -(int) this.radius, 0);
        botRight = new SKPoint2D(botCenter2D, (int) this.radius, 0);

    }

    private void customSaveCoord(SKPoint2D pos, SKPoint3D coord, String[][] coordOfBoard) {
        if (Ultility.checkValidPoint(coordOfBoard, pos.getCoordX(), pos.getCoordY())) {
            coordOfBoard[pos.getCoordY()][pos.getCoordX()] = "(" + coord.getCoordX() + ", " + coord.getCoordY() + ", " + coord.getCoordZ() + ")";
        }
    }

    @Override
    public void drawOutline() {
        // super.drawOutline();
        SKPoint3D leftTopPoint = new SKPoint3D(this.centerPoint3D.getCoordX() - radius, this.centerPoint3D.getCoordY(),
                this.centerPoint3D.getCoordZ() + this.high / 2);
        SKPoint3D leftBottomPoint = new SKPoint3D(this.centerPoint3D.getCoordX() - radius, this.centerPoint3D.getCoordY(),
                this.centerPoint3D.getCoordZ() - this.high / 2);
        SKPoint3D rightTopPoint = new SKPoint3D(this.centerPoint3D.getCoordX() + radius, this.centerPoint3D.getCoordY(),
                this.centerPoint3D.getCoordZ() + this.high / 2);
        SKPoint3D rightBottomPoint = new SKPoint3D(this.centerPoint3D.getCoordX() + radius, this.centerPoint3D.getCoordY(),
                this.centerPoint3D.getCoordZ() - this.high / 2);
        customSaveCoord(topLeft, leftTopPoint, changedCoordOfBoard);
        customSaveCoord(botLeft, leftBottomPoint, changedCoordOfBoard);
        customSaveCoord(topRight, rightTopPoint, changedCoordOfBoard);
        customSaveCoord(botRight, rightBottomPoint, changedCoordOfBoard);

        drawOutlineEllipseUnSave(topCenter.get2DRelativePosition(), radius, radius / 2 * COS_DEGREE_45, true, true, true, true);
        drawOutlineEllipseUnSaveS(botCenter.get2DRelativePosition(), radius, radius / 2 * COS_DEGREE_45, true, true, true, true);
        drawSegmentUnSave(topLeft, botLeft);
        drawSegmentUnSave(topRight, botRight);
    }

    @Override
    public void applyMove(Vector2D vector) {
    }

    @Override
    public void saveCoordinates() {
        super.saveCoordinates();
        topCenter.saveCoord(changedCoordOfBoard);
        botCenter.saveCoord(changedCoordOfBoard);
        savePoint(topCenter.get2DRelativePosition());
        savePoint(botCenter.get2DRelativePosition());

    }

}
