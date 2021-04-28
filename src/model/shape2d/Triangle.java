package model.shape2d;

import java.awt.Color;

public class Triangle extends Shape2D {

    private Point2D pointA;
    private Point2D pointB;
    private Point2D pointC;

    public enum Modal {
        COMMON_TRIANGLE,
        EQUILATERAL_TRIANGLE,
    }

    public enum UnchangedPoint {
        HEAD_POINT,
        FEET_POINT
    }

    public Triangle(boolean[][] markedChangeOfBoard, Color[][] changedColorOfBoard, String[][] changedCoordOfBoard, Color filledColor) {
        super(markedChangeOfBoard, changedColorOfBoard, changedCoordOfBoard, filledColor);
        pointA = new Point2D(-1, -1);
        pointB = new Point2D(-1, -1);
        pointC = new Point2D(-1, -1);
    }

    /**
     * Set 3 points of triangle by using startPoint and endPoint The type of
     * triangle is either common or equilateral depending on Modal
     *
     * @param startPoint
     * @param endPoint
     */
    public void setProperty(Point2D startPoint, Point2D endPoint, Modal modal) {
        int width = endPoint.getCoordX() - startPoint.getCoordX();
        int height = endPoint.getCoordY() - startPoint.getCoordY();

        int widthDirection;//true = dương, false = âm

        if (width < 0) {
            widthDirection = -1;
        } else {
            widthDirection = 1;
        }

        Point2D feetPoint = new Point2D();
        Point2D headPoint = new Point2D();
        UnchangedPoint unchangedPoint = UnchangedPoint.HEAD_POINT;

        if (width > 0 && height > 0 || width < 0 && height > 0 || width == 0 || height == 0) {//chéo xuống, từ trái qua hoặc chéo xuống từ phải qua
            feetPoint.setCoord(endPoint);                  //trường hợp width hoặc height =0 thì để chỗ nào cũng dc
            headPoint.setCoord(startPoint);                // nó sẽ vẽ một đoạn thẳng nằm ngang
            unchangedPoint = UnchangedPoint.HEAD_POINT;
        } else if (width < 0 && height < 0 || width > 0 && height < 0) {//chéo lên, từ phải qua hoặc... chéo lên, từ trái qua
            feetPoint.setCoord(startPoint);
            headPoint.setCoord(endPoint);
            unchangedPoint = UnchangedPoint.FEET_POINT;
        }

        if (modal == Modal.COMMON_TRIANGLE) {
            pointA.setCoord((feetPoint.coordX + headPoint.coordX) / 2, headPoint.coordY);
            pointB.setCoord(headPoint.getCoordX(), feetPoint.getCoordY());
            pointC.setCoord(feetPoint);

        } else {

            int widthValue = Math.abs(width);
            int heightValue = Math.abs(height);

            int preferedLength;
            if (widthValue >= heightValue) {
                preferedLength = heightValue;
            } else {
                preferedLength = widthValue;
            }
            if (preferedLength > 0) {
                if (unchangedPoint == UnchangedPoint.HEAD_POINT) {
                    pointA.setCoord(headPoint.getCoordX() + widthDirection * preferedLength / 2, headPoint.getCoordY());
                    pointB.setCoord(headPoint.getCoordX(), headPoint.getCoordY() + preferedLength);
                    pointC.setCoord(headPoint.getCoordX() + widthDirection * preferedLength + (-widthDirection), headPoint.getCoordY() + preferedLength);
                } else {
                    pointA.setCoord(feetPoint.getCoordX() + widthDirection * preferedLength / 2, feetPoint.getCoordY() - preferedLength);
                    pointB.setCoord(feetPoint.getCoordX(), feetPoint.getCoordY());
                    pointC.setCoord(feetPoint.getCoordX() + widthDirection * preferedLength + (-widthDirection), feetPoint.getCoordY());
                }
                //  +widthDirection*preferedLength = set điểm quan tâm đến hướng theo phương ngang (dựa vào hướng kéo chuột từ trái hay từ phải qua)
                //  +preferedLength = chỉ quan tâm đến giá trị cộng vào, hướng luôn theo phương dọc, do vẽ tam giác mặc định luôn hướng từ trên xuống
                //  +(-widthDirection) dịch 1 pixel có hướng theo phương ngang để căn chỉnh lại hình
            }
        }

        centerPoint.setCoord(
                pointA.coordX,
                pointC.coordY + (int) ((2.0 / 3.0) * Math.abs(pointC.coordY - pointA.coordY))
        );
    }

    @Override
    public void setProperty(Point2D startPoint, Point2D endPoint) {
        setProperty(startPoint, endPoint, Modal.COMMON_TRIANGLE);
    }

    @Override
    public void saveCoordinates() {
        pointA.saveCoord(this.changedCoordOfBoard);
        pointB.saveCoord(this.changedCoordOfBoard);
        pointC.saveCoord(this.changedCoordOfBoard);
    }

    /**
     * Rotate a copy of this shape by an angle.
     *
     * @param angle
     */
    @Override
    public void drawVirtualRotation(double angle) {
        double totalAngle = this.rotatedAngle + angle;

        Point2D tempPointA = pointA.createRotationPoint(centerPoint, totalAngle);
        Point2D tempPointB = pointB.createRotationPoint(centerPoint, totalAngle);
        Point2D tempPointC = pointC.createRotationPoint(centerPoint, totalAngle);

        drawSegment(tempPointA, tempPointB);
        drawSegment(tempPointB, tempPointC);
        drawSegment(tempPointC, tempPointA);
    }

    /**
     * Drawing in board.
     */
    @Override
    public void drawOutline() {
        Point2D tempPointA = pointA.createRotationPoint(centerPoint, this.rotatedAngle);
        Point2D tempPointB = pointB.createRotationPoint(centerPoint, this.rotatedAngle);
        Point2D tempPointC = pointC.createRotationPoint(centerPoint, this.rotatedAngle);

        drawSegment(tempPointA, tempPointB);
        drawSegment(tempPointB, tempPointC);
        drawSegment(tempPointC, tempPointA);
    }

    /**
     * Drawing
     *
     * @param vector
     */
    @Override
    public void drawVirtualMove(Vector2D vector) {
        Point2D tempPointA = pointA.createRotationPoint(centerPoint, this.rotatedAngle).move(vector);
        Point2D tempPointB = pointB.createRotationPoint(centerPoint, this.rotatedAngle).move(vector);
        Point2D tempPointC = pointC.createRotationPoint(centerPoint, this.rotatedAngle).move(vector);

        drawSegment(tempPointA, tempPointB);
        drawSegment(tempPointB, tempPointC);
        drawSegment(tempPointC, tempPointA);
    }

    @Override
    public void applyMove(Vector2D vector) {
        pointA.rotate(centerPoint, this.rotatedAngle).move(vector);
        pointB.rotate(centerPoint, this.rotatedAngle).move(vector);
        pointC.rotate(centerPoint, this.rotatedAngle).move(vector);

        centerPoint.move(vector);
    }

    @Override
    public void drawOYSymmetry() {
        Point2D pointASymmetry = pointA.createRotationPoint(centerPoint, rotatedAngle).symOy();
        Point2D pointBSymmetry = pointB.createRotationPoint(centerPoint, rotatedAngle).symOy();
        Point2D pointCSymmetry = pointC.createRotationPoint(centerPoint, rotatedAngle).symOy();

        drawSegment(pointASymmetry, pointBSymmetry);
        drawSegment(pointBSymmetry, pointCSymmetry);
        drawSegment(pointCSymmetry, pointASymmetry);
    }

    @Override
    public void drawOXSymmetry() {
        Point2D pointASymmetry = pointA.createRotationPoint(centerPoint, rotatedAngle).symOx();
        Point2D pointBSymmetry = pointB.createRotationPoint(centerPoint, rotatedAngle).symOx();
        Point2D pointCSymmetry = pointC.createRotationPoint(centerPoint, rotatedAngle).symOx();

        drawSegment(pointASymmetry, pointBSymmetry);
        drawSegment(pointBSymmetry, pointCSymmetry);
        drawSegment(pointCSymmetry, pointASymmetry);
    }

    @Override
    public void drawPointSymmetry(Point2D basePoint) {
        Point2D pointASymmetry = pointA.createRotationPoint(centerPoint, rotatedAngle).symPoint(basePoint);
        Point2D pointBSymmetry = pointB.createRotationPoint(centerPoint, rotatedAngle).symPoint(basePoint);
        Point2D pointCSymmetry = pointC.createRotationPoint(centerPoint, rotatedAngle).symPoint(basePoint);

        drawSegment(pointASymmetry, pointBSymmetry);
        drawSegment(pointBSymmetry, pointCSymmetry);
        drawSegment(pointCSymmetry, pointASymmetry);
    }

    @Override
    public void drawLineSymmetry(double a, double b, double c) {
        Point2D pointASymmetry = pointA.createRotationPoint(centerPoint, rotatedAngle).symLine(a, b, c);
        Point2D pointBSymmetry = pointB.createRotationPoint(centerPoint, rotatedAngle).symLine(a, b, c);
        Point2D pointCSymmetry = pointC.createRotationPoint(centerPoint, rotatedAngle).symLine(a, b, c);

        drawSegment(pointASymmetry, pointBSymmetry);
        drawSegment(pointBSymmetry, pointCSymmetry);
        drawSegment(pointCSymmetry, pointASymmetry);
    }
}
