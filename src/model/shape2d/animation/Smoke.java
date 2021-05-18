package model.shape2d.animation;

import java.awt.Color;
import java.util.ArrayList;
import control.myawt.SKPoint2D;
import control.util.Ultility;
import model.shape2d.Shape2D;
import model.shape2d.Transform2D;
import model.shape2d.Vector2D;

public class Smoke extends Shape2D {

    public static final double LEVEL_SCALE_START = 0.5;
    
    private ArrayList<SKPoint2D> pointList_Inside = new ArrayList<>();
    private ArrayList<SKPoint2D> pointList_Border = new ArrayList<>();

    private double levelScale = LEVEL_SCALE_START;

    private Color skyColor = Color.WHITE;

    public Smoke(boolean[][] markedChangeOfBoard, Color[][] changedColorOfBoard,
            String[][] changedCoordOfBoard, Color filledColor) {
        super(markedChangeOfBoard, changedColorOfBoard, changedCoordOfBoard,
                filledColor);
    }

    public void setProperty(SKPoint2D startPoint, Color skyColor) {
        //startPoint là tâm của khói
        this.startPoint2D.setLocation(startPoint);
        this.skyColor = skyColor;

        pointList_Inside.clear();

        //khói ở trong
        pointList_Inside.add(new SKPoint2D(this.startPoint2D, -6, 2));
        pointList_Inside.add(new SKPoint2D(this.startPoint2D, -6 - 2, 2 + 3));
        pointList_Inside.add(new SKPoint2D(this.startPoint2D, 7, 8));
        pointList_Inside.add(new SKPoint2D(this.startPoint2D, 4, -4));
        pointList_Inside.add(new SKPoint2D(this.startPoint2D, 4 + 6, -4));
        pointList_Inside.add(new SKPoint2D(this.startPoint2D, 0, 6));

        pointList_Border.clear();

        pointList_Border.add(new SKPoint2D(this.startPoint2D, -10, -1));
        pointList_Border.add(new SKPoint2D(startPoint2D, -6, -4));
        pointList_Border.add(new SKPoint2D(startPoint2D, -3, -6));
        pointList_Border.add(new SKPoint2D(startPoint2D, 3, -10));
        pointList_Border.add(new SKPoint2D(startPoint2D, 8, -9));
        pointList_Border.add(new SKPoint2D(startPoint2D, 13, -9));
        pointList_Border.add(new SKPoint2D(startPoint2D, 16, -4));
        pointList_Border.add(new SKPoint2D(startPoint2D, 17, 4));

        //quay về đầu, làm bên trái
        //-6 2
        //-3 6
        pointList_Border.add(new SKPoint2D(startPoint2D, -12, 5));

        pointList_Border.add(new SKPoint2D(startPoint2D, -8, 9));

        //quay lại bên phải làm cái cuối cùng trước khi vẽ ellipse
        pointList_Border.add(new SKPoint2D(startPoint2D, 14, 8));
    }

    public void drawSmoke() {

        setFilledColor(Color.BLACK);

        SKPoint2D pointI0 = pointList_Inside.get(0).createScale(levelScale, levelScale);
        SKPoint2D pointI1 = pointList_Inside.get(1).createScale(levelScale, levelScale);
        SKPoint2D pointI2 = pointList_Inside.get(2).createScale(levelScale, levelScale);
        SKPoint2D pointI3 = pointList_Inside.get(3).createScale(levelScale, levelScale);
        SKPoint2D pointI4 = pointList_Inside.get(4).createScale(levelScale, levelScale);
        SKPoint2D pointI5 = pointList_Inside.get(5).createScale(levelScale, levelScale);

        //khói ở trong
        drawOutlineCircle(pointI0, 2 * levelScale, false, false, false, false,
                false, true, true, true);

        drawOutlineCircle(pointI1, 2 * levelScale, false, false, false, false,
                false, false, true, true);

        drawOutlineCircle(pointI2, 4 * levelScale, true, true, true, false,
                false, false, false, false);

        //vòng tròn liên tiếp
        drawOutlineCircle(pointI3, 4 * levelScale, true, true, false, false,
                false, false, false, true);

        drawOutlineCircle(pointI4, 2 * levelScale, true, true, true, false,
                false, false, false, true);

        drawOutlineCircle(pointI5, 4 * levelScale, false, false, false, false,
                true, true, false, false);

        SKPoint2D pointB0 = pointList_Border.get(0).createScale(levelScale, levelScale);
        SKPoint2D pointB1 = pointList_Border.get(1).createScale(levelScale, levelScale);
        SKPoint2D pointB2 = pointList_Border.get(2).createScale(levelScale, levelScale);
        SKPoint2D pointB3 = pointList_Border.get(3).createScale(levelScale, levelScale);
        SKPoint2D pointB4 = pointList_Border.get(4).createScale(levelScale, levelScale);
        SKPoint2D pointB5 = pointList_Border.get(5).createScale(levelScale, levelScale);
        SKPoint2D pointB6 = pointList_Border.get(6).createScale(levelScale, levelScale);
        SKPoint2D pointB7 = pointList_Border.get(7).createScale(levelScale, levelScale);
        SKPoint2D pointB8 = pointList_Border.get(8).createScale(levelScale, levelScale);
        SKPoint2D pointB9 = pointList_Border.get(9).createScale(levelScale, levelScale);
        SKPoint2D pointB10 = pointList_Border.get(10).createScale(levelScale, levelScale);

        //viền ngoài, theo chiều kim đồng hồ lồng nhau
        drawOutlineCircle(pointB0, 7 * levelScale, false, false, false, false,
                false, false, true, true);

        drawOutlineCircle(pointB1, 5 * levelScale, false, false, false, false,
                false, false, true, true);

        drawOutlineCircle(pointB2, 7 * levelScale, true, false, false, false,
                false, false, true, true);

        drawOutlineCircle(pointB3, 5 * levelScale, true, false, false, false,
                false, false, false, true);

        drawOutlineCircle(pointB4, 7 * levelScale, true, false, false, false,
                false, false, false, true);

        drawOutlineCircle(pointB5, 7 * levelScale, true, true, true, false,
                false, false, false, false);

        drawOutlineCircle(pointB6, 5 * levelScale, false, true, true, true,
                false, false, false, false);

        drawOutlineCircle(pointB7, 5 * levelScale, true, true, true, false,
                false, false, false, false);

        drawOutlineCircle(pointB8, 7 * levelScale, false, false, false, false,
                true, true, true, true);

        drawOutlineCircle(pointB9, 7 * levelScale, false, false, false, false,
                true, true, false, false);

        //quay lại bên phải làm cái cuối cùng trước khi vẽ ellipse
        drawOutlineCircle(pointB10, 6 * levelScale, false, true, true, true,
                false, false, false, false);

        drawOutlineEllipse(new SKPoint2D(pointList_Border.get(10), 2, 12), 6 * levelScale, 10 * levelScale,
                false, false, false, true);
        drawOutlineEllipse(new SKPoint2D(pointList_Border.get(10), -25, 12), 6 * levelScale, 10 * levelScale,
                true, false, false, false);

        if (levelScale >= 1.3) {
            levelScale = LEVEL_SCALE_START;
        } else {
            levelScale += 0.08;
        }

    }

    @Override
    public void applyMove(Vector2D vector) {

    }

    @Override
    public void saveCoordinates() {

    }

    @Override
    public void drawOutline() {

    }

    @Override
    public void setProperty(SKPoint2D startPoint, SKPoint2D endPoint) {

    }
}
