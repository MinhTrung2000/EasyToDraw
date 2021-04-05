package main;

import java.awt.Color;
import java.awt.Point;

public class Arrow2D extends Shape {

    private Point2D A;
    private Point2D B;
    private Point2D C;
    private Point2D D;
    private Point2D Left;
    private Point2D Right;
    private Point2D Top;
    private Point2D st, en;

    public Arrow2D(boolean[][] nextDrawing, Color[][] nextPoint, Color color) {
        super(nextDrawing, nextPoint, Color.yellow);
        A = new Point2D();
        B = new Point2D();
        C = new Point2D();
        D = new Point2D();
        st = new Point2D();
        en = new Point2D();
        Left = new Point2D();
        Right = new Point2D();
        Top = new Point2D();
    }

    public void set(Point2D midPoint, int rand) {
        st.setCoord(midPoint.coordX - rand, midPoint.coordY - rand * 3);
        en.setCoord(midPoint.coordX + rand, midPoint.coordY + rand * 3);
        A.setCoord(st.coordX, st.coordY);              // (start) A-----------------B
        B.setCoord(en.coordX, st.coordY);              //         |                 |
        C.setCoord(en.coordX, en.coordY);              //         |                 |
        D.setCoord(st.coordX, en.coordY);              //         D-----------------C  (end)
        System.out.println(en.coordX + "-" + en.coordY);
        Left.setCoord(st.coordX - (en.coordX - st.coordX) / 2, st.coordY);
        Right.setCoord(en.coordX + (en.coordX - st.coordX) / 2, st.coordY);
        Top.setCoord(midPoint.coordX, midPoint.coordY - rand * 6);
    }

    public void draw() {
        super.drawSegment(Left, Right, this.lineStyle);
        super.drawSegment(Left, Top, this.lineStyle);
        super.drawSegment(Right, Top, this.lineStyle);
        super.drawSegment(A, B, this.lineStyle);
        super.drawSegment(B, C, this.lineStyle);
        super.drawSegment(C, D, this.lineStyle);
        super.drawSegment(D, A, this.lineStyle);

        for (int i = st.coordX + 1; i < en.coordX; i++) {
            for (int j = st.coordY + 1; j < en.coordY; j++) {
                if (Ultility.checkValidPoint(colorPointsOfShape, i, j)) {
                    markedPointsOfShape[i][j] = true;
                    colorPointsOfShape[i][j] = filledColor;
                }
            }
        }
        Ultility.paint(colorPointsOfShape, markedPointsOfShape, new Point2D(Top.coordX, Top.coordY + 1), filledColor);
    }
}
