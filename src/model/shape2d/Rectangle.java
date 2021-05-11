package model.shape2d;

import control.SettingConstants;
import control.myawt.SKPoint2D;
import control.myawt.SKPoint3D;
import java.awt.Color;

public class Rectangle extends Shape2D {

    public enum Modal {
        RECTANGLE,
        SQUARE
    }

    private SKPoint2D leftTopPoint = new SKPoint2D();
    private SKPoint2D rightTopPoint = new SKPoint2D();
    private SKPoint2D leftBottomPoint = new SKPoint2D();
    private SKPoint2D rightBottomPoint = new SKPoint2D();

    public Rectangle(boolean[][] markedChangeOfBoard, Color[][] changedColorOfBoard, String[][] changedCoordOfBoard, Color filledColor) {
        super(markedChangeOfBoard, changedColorOfBoard, changedCoordOfBoard, filledColor);
    }

    public void setProperty(SKPoint2D startPoint, SKPoint2D endPoint, Modal modal) {
        centerPoint2D.setMidLocation(startPoint, endPoint);

        if (modal == Modal.RECTANGLE) {
            leftTopPoint.setLocation(startPoint);
            rightBottomPoint.setLocation(endPoint);

            leftBottomPoint.setLocation(leftTopPoint.getCoordX(), rightBottomPoint.getCoordY());
            rightTopPoint.setLocation(rightBottomPoint.getCoordX(), leftTopPoint.getCoordY());

        } else {
            int width = (int) (endPoint.getCoordX() - startPoint.getCoordX());
            int height = (int) (endPoint.getCoordY() - startPoint.getCoordY());
            int widthDirection = this.getWidthDirection(width);
            int heightDirection = this.getHeightDirection(height);
            int preferedLength = this.getPreferredLength(width, height);
            if (preferedLength > 0) {
                leftTopPoint.setLocation(startPoint);
                leftBottomPoint.setLocation(startPoint.getCoordX(), startPoint.getCoordY() + heightDirection * preferedLength);
                rightTopPoint.setLocation(startPoint.getCoordX() + widthDirection * preferedLength, startPoint.getCoordY());
                rightBottomPoint.setLocation(startPoint.getCoordX() + widthDirection * preferedLength, startPoint.getCoordY() + heightDirection * preferedLength);

            }
        }
    }

    @Override
    public void setProperty(SKPoint2D startPoint, SKPoint2D endPoint) {
        setProperty(startPoint, endPoint, Modal.RECTANGLE);
    }

    @Override
    public void saveCoordinates() {
        leftTopPoint.createRotate(centerPoint2D, rotatedAngle).saveCoord(this.changedCoordOfBoard);
        rightTopPoint.createRotate(centerPoint2D, rotatedAngle).saveCoord(this.changedCoordOfBoard);
        leftBottomPoint.createRotate(centerPoint2D, rotatedAngle).saveCoord(this.changedCoordOfBoard);
        rightBottomPoint.createRotate(centerPoint2D, rotatedAngle).saveCoord(this.changedCoordOfBoard);
    }

    @Override
    public void applyMove(Vector2D vector) {
        leftTopPoint.rotate(rotatedAngle).move(vector);
        rightTopPoint.rotate(rotatedAngle).move(vector);
        leftBottomPoint.rotate(rotatedAngle).move(vector);
        rightBottomPoint.rotate(rotatedAngle).move(vector);

        centerPoint2D.move(vector);
    }

    @Override
    public void drawOutline() {
        SKPoint2D tempLeftTopPoint = leftTopPoint.createRotate(centerPoint2D, this.rotatedAngle);
        SKPoint2D tempRightTopPoint = rightTopPoint.createRotate(centerPoint2D, this.rotatedAngle);
        SKPoint2D tempLeftBottomPoint = leftBottomPoint.createRotate(centerPoint2D, this.rotatedAngle);
        SKPoint2D tempRightBottomPoint = rightBottomPoint.createRotate(centerPoint2D, this.rotatedAngle);

        drawSegment(tempLeftTopPoint, tempRightTopPoint, lineStyle);
        drawSegment(tempRightTopPoint, tempRightBottomPoint, lineStyle);
        drawSegment(tempRightBottomPoint, tempLeftBottomPoint, lineStyle);
        drawSegment(tempLeftBottomPoint, tempLeftTopPoint, lineStyle);
    }

    public static void setFourPointSymmetricFromCenter(SKPoint2D centerPoint,
            double width, double height, SKPoint3D ULPoint, SKPoint3D URPoint,
            SKPoint3D LLPoint, SKPoint3D LRPoint) {
        int cx = centerPoint.getCoordX();
        int cy = centerPoint.getCoordY();
        int cz = centerPoint.getCoordZ();
        double half_w = width / 2 * SettingConstants.RECT_SIZE;
        double half_h = height / 2 * SettingConstants.RECT_SIZE;

        ULPoint.setLocation(cx - half_w, cy - half_h, cz);
        URPoint.setLocation(cx + half_w, cy - half_h, cz);
        LLPoint.setLocation(cx - half_w, cy + half_h, cz);
        LRPoint.setLocation(cx + half_w, cy + half_h, cz);
    }

    public static void setFourPointSymmetricFromCenter(double center_x,
            double center_y, double center_z, double width, double high,
            SKPoint3D ULPoint, SKPoint3D URPoint, SKPoint3D LRPoint,
            SKPoint3D LLPoint) {
        double half_w = width / 2;
        double half_h = high / 2;

        // Set location in visual system coordinate mode.
        ULPoint.setLocation(center_x - half_w, center_y, center_z + half_h);
        URPoint.setLocation(center_x + half_w, center_y, center_z + half_h);
        LLPoint.setLocation(center_x - half_w, center_y, center_z - half_h);
        LRPoint.setLocation(center_x + half_w, center_y, center_z - half_h);
    }

    @Override
    public void createRotateInstance(SKPoint2D centerPoint, double angle) {
        if (pointSet.isEmpty()) {
            return;
        }

        double totalAngle = rotatedAngle + angle;

        SKPoint2D newLeftTopPoint = leftTopPoint.createRotate(centerPoint, totalAngle);
        SKPoint2D newLeftBottomPoint = leftBottomPoint.createRotate(centerPoint, totalAngle);
        SKPoint2D newRightTopPoint = rightTopPoint.createRotate(centerPoint, totalAngle);
        SKPoint2D newRightBottomPoint = rightBottomPoint.createRotate(centerPoint, totalAngle);

        newLeftTopPoint.saveCoord(changedCoordOfBoard);
        newLeftBottomPoint.saveCoord(changedCoordOfBoard);
        newRightTopPoint.saveCoord(changedCoordOfBoard);
        newRightBottomPoint.saveCoord(changedCoordOfBoard);

        drawSegment(newLeftTopPoint, newRightTopPoint);
        drawSegment(newRightTopPoint, newRightBottomPoint);
        drawSegment(newRightBottomPoint, newLeftBottomPoint);
        drawSegment(newLeftBottomPoint, newLeftTopPoint);
    }

    @Override
    public void createOCenterSymInstance() {
        super.createOCenterSymInstance();
        
        SKPoint2D newLeftTopPoint = leftTopPoint.createOCenterSym();
        SKPoint2D newLeftBottomPoint = leftBottomPoint.createOCenterSym();
        SKPoint2D newRightTopPoint = rightTopPoint.createOCenterSym();
        SKPoint2D newRightBottomPoint = rightBottomPoint.createOCenterSym();

        newLeftTopPoint.saveCoord(changedCoordOfBoard);
        newLeftBottomPoint.saveCoord(changedCoordOfBoard);
        newRightTopPoint.saveCoord(changedCoordOfBoard);
        newRightBottomPoint.saveCoord(changedCoordOfBoard);
    }

    @Override
    public void createOXSymInstance() {
        super.createOXSymInstance();
        
        SKPoint2D newLeftTopPoint = leftTopPoint.createOXSym();
        SKPoint2D newLeftBottomPoint = leftBottomPoint.createOXSym();
        SKPoint2D newRightTopPoint = rightTopPoint.createOXSym();
        SKPoint2D newRightBottomPoint = rightBottomPoint.createOXSym();

        newLeftTopPoint.saveCoord(changedCoordOfBoard);
        newLeftBottomPoint.saveCoord(changedCoordOfBoard);
        newRightTopPoint.saveCoord(changedCoordOfBoard);
        newRightBottomPoint.saveCoord(changedCoordOfBoard);
    }

    @Override
    public void createOYSymInstance() {
        super.createOYSymInstance();
        
        SKPoint2D newLeftTopPoint = leftTopPoint.createOYSym();
        SKPoint2D newLeftBottomPoint = leftBottomPoint.createOYSym();
        SKPoint2D newRightTopPoint = rightTopPoint.createOYSym();
        SKPoint2D newRightBottomPoint = rightBottomPoint.createOYSym();

        newLeftTopPoint.saveCoord(changedCoordOfBoard);
        newLeftBottomPoint.saveCoord(changedCoordOfBoard);
        newRightTopPoint.saveCoord(changedCoordOfBoard);
        newRightBottomPoint.saveCoord(changedCoordOfBoard);
    }

    @Override
    public void createPointSymInstance(SKPoint2D basePoint) {
        super.createPointSymInstance(basePoint);
        
        SKPoint2D newLeftTopPoint = leftTopPoint.createPointSym(basePoint);
        SKPoint2D newLeftBottomPoint = leftBottomPoint.createPointSym(basePoint);
        SKPoint2D newRightTopPoint = rightTopPoint.createPointSym(basePoint);
        SKPoint2D newRightBottomPoint = rightBottomPoint.createPointSym(basePoint);

        newLeftTopPoint.saveCoord(changedCoordOfBoard);
        newLeftBottomPoint.saveCoord(changedCoordOfBoard);
        newRightTopPoint.saveCoord(changedCoordOfBoard);
        newRightBottomPoint.saveCoord(changedCoordOfBoard);
    }

    @Override
    public void createLineSymInstance(double a, double b, double c) {
        super.createLineSymInstance(a, b, c);
        
        SKPoint2D newLeftTopPoint = leftTopPoint.createLineSym(a, b, c);
        SKPoint2D newLeftBottomPoint = leftBottomPoint.createLineSym(a, b, c);
        SKPoint2D newRightTopPoint = rightTopPoint.createLineSym(a, b, c);
        SKPoint2D newRightBottomPoint = rightBottomPoint.createLineSym(a, b, c);

        newLeftTopPoint.saveCoord(changedCoordOfBoard);
        newLeftBottomPoint.saveCoord(changedCoordOfBoard);
        newRightTopPoint.saveCoord(changedCoordOfBoard);
        newRightBottomPoint.saveCoord(changedCoordOfBoard);
    }

}
