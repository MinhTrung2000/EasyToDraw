package model.shape2d;

import control.myawt.SKPoint2D;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import static control.SettingConstants.*;

public class Segment2D extends Shape2D {

    public enum Modal {
        LINE_45_90_DEGREE,
        STRAIGHT_LINE
    }

    public enum UnchangedPoint {
        HEAD_POINT,
        FEET_POINT
    }

    public Segment2D(boolean[][] markedChangeOfBoard, Color[][] changedColorOfBoard, String[][] changedCoordOfBoard, Color filledColor) {
        super(markedChangeOfBoard, changedColorOfBoard, changedCoordOfBoard, filledColor);
    }

    public void setProperty(SKPoint2D startPoint, SKPoint2D endPoint, Modal modal) {
        if (modal == Modal.STRAIGHT_LINE) {
            this.startPoint = startPoint;
            this.endPoint = endPoint;
        } else {
            int width = (int) (endPoint.getCoordX() - startPoint.getCoordX());
            int height = (int) (endPoint.getCoordY() - startPoint.getCoordY());
            
            int widthValue = Math.abs(width);
            int heightValue = Math.abs(height);

            int heightDirection = this.getHeightDirection(height);
            int widthDirection = this.getWidthDirection(width);
            int preferedLength = this.getPreferredLength(width, height);


            this.startPoint = startPoint;

            double ratio = (double) widthValue / (double) heightValue;
            if (ratio <= 0.3) {
                this.endPoint.setLocation(this.startPoint.getCoordX(), this.startPoint.getCoordY() + heightDirection * preferedLength);
            } else if (ratio > 0.3 && ratio <= 1.5) {
                this.endPoint.setLocation(this.startPoint.getCoordX() + widthDirection * preferedLength, this.startPoint.getCoordY() + heightDirection * preferedLength);
            } else {
                this.endPoint.setLocation(this.startPoint.getCoordX() + widthDirection * preferedLength, this.startPoint.getCoordY());
            }

        }

        this.centerPoint.setLocation(
                (startPoint.getCoordX() + endPoint.getCoordX()) / 2,
                (startPoint.getCoordY() + endPoint.getCoordY()) / 2
        );
    }

    @Override
    public void setProperty(SKPoint2D startPoint, SKPoint2D endPoint) {
        setProperty(startPoint, endPoint, Modal.STRAIGHT_LINE);
    }

    @Override
    public void drawOutline() {
        SKPoint2D tempStartPoint = startPoint.createRotationPoint(centerPoint, rotatedAngle);
        SKPoint2D tempEndPoint = endPoint.createRotationPoint(centerPoint, rotatedAngle);

        drawSegment(tempStartPoint, tempEndPoint, lineStyle);
    }

    /**
     * Move the line and change in place.
     *
     * @param vector
     */
    @Override
    public void applyMove(Vector2D vector) {
        startPoint.move(vector);
        endPoint.move(vector);
        centerPoint.move(vector);
    }

    @Override
    public void saveCoordinates() {
        this.startPoint.saveCoord(changedCoordOfBoard);
        this.endPoint.saveCoord(changedCoordOfBoard);
    }

    public SKPoint2D intersect(Segment2D other) {
        Vector2D vec_a_b = new Vector2D(this.startPoint, this.endPoint);
        Vector2D vec_c_d = new Vector2D(other.startPoint, other.endPoint);
        Vector2D vec_c_a = new Vector2D(other.startPoint, this.endPoint);

        Vector2D r = Vector2D.getVectorOfLinearEquationRepr(vec_a_b, vec_c_d, vec_c_a);

        // Two segments are not collided if the root is not in range 0..1
        if (Double.compare(r.getCoordX(), 0.0) > 0 && Double.compare(r.getCoordX(), 1.0) < 0
                && Double.compare(r.getCoordY(), 0.0) > 0 && Double.compare(r.getCoordY(), 1.0) < 0) {
            return null;
        }

        SKPoint2D result = new SKPoint2D(this.startPoint);
        result.move(vec_a_b.scale(r.getCoordX()));

        return result;
    }
}
