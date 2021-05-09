package model.shape2d.animation;

import control.myawt.SKPoint2D;
import java.util.ArrayList;
import model.shape2d.Shape2D;
import model.shape2d.Vector2D;

/**
 * Lớp dùng cho vẽ hình sau các phép biến đổi (scale, rotate, symmetry, move).
 *
 * Khi phân tích một hình, ta chia hình làm các 2 phần: + Phần gồm các hình cơ
 * sở. Hình cơ sở là các hình chữ nhật, hình ellipse, hình tròn, tam giác. +
 * Phần gồm các chi tiết không phải hình cơ sở. Ta quản lý chúng bằng cách lưu
 * dữ liệu các điểm đầu mút, điểm mà theo đó vẽ các đoạn thẳng khi nối liền sẽ
 * tạo ra chi tiết tổng thể.
 *
 * Kết quả của phép biến đổi là tập hợp từng kết quả của phép biến đổi của từng
 * hình cơ bản (trong phần 1) và kết quả phép biến đổi trên các điểm đầu mút
 * (trong phần 2) để từ đó ta chỉ cần nối liền các điểm bằng thuật toán vẽ đoạn
 * thẳng.
 *
 * Như vậy, ta cần tạo 2 hàm cần thiết: hàm để vẽ lại và hàm dùng để tô lại. Hàm
 * vẽ lại đặc biệt sẽ nối các điểm đầu mút bằng thuật toán đoạn thẳng dựa vào
 * nguyên tắc được thiết lập.
 *
 * @author MinhTrung2000
 */
public abstract class ComplexShape {

    /**
     * Danh sách các điểm đầu mút của các đoạn thẳng. Không tính đến các trường
     * hợp của hình cơ sở.
     */
    private ArrayList<SKPoint2D> pulpPointList = new ArrayList<>();

    /**
     * Danh sách các hình cơ sở.
     */
    private ArrayList<Shape2D> normShapeList = new ArrayList<>();

//    private RulePulpDrawInterface ruleDraw;

//    public ComplexShape(RulePulpDrawInterface ruleDraw) {
    public ComplexShape() {
//        this.ruleDraw = ruleDraw;
    }

    public void addNormShape(Shape2D shape) {
        normShapeList.add(shape);
    }

    public void addPulpPoint(SKPoint2D point) {
        pulpPointList.add(point);
    }

    /**
     * Hàm thiết lập rule vẽ lại outline hình sau phép biến đổi.
     */
    public void reDrawOutline() {
        for (int i = 0; i < normShapeList.size(); i++) {
            normShapeList.get(i).drawOutline();
        }

        for (int i = 0; i < pulpPointList.size(); i++) {
            // XXX
        }

        ruleDrawOutline();
    }

    /**
     * Hàm thiết lập rule tô lại hình sau phép biến đổi.
     */
    public void rePaintColor() {
        for (int i = 0; i < normShapeList.size(); i++) {
            // XXX
        }

        for (int i = 0; i < pulpPointList.size(); i++) {
            // XXX
        }

    }

    public void move(Vector2D vector) {
        for (int i = 0; i < normShapeList.size(); i++) {
            normShapeList.get(i).drawVirtualMove(vector);
        }

        for (int i = 0; i < pulpPointList.size(); i++) {
            pulpPointList.get(i).move(vector);
        }
    }

    public void scale(int k) {
        for (int i = 0; i < normShapeList.size(); i++) {
            normShapeList.get(i).scale(k);
        }

        for (int i = 0; i < pulpPointList.size(); i++) {
            pulpPointList.get(i).scale(k);
        }
    }

    public void rotate(double angle) {
        for (int i = 0; i < normShapeList.size(); i++) {
            // XXX
        }

        for (int i = 0; i < pulpPointList.size(); i++) {
            pulpPointList.get(i).rotate(angle);
        }
    }

    public void symmetry() {
        for (int i = 0; i < normShapeList.size(); i++) {
            // XXX
        }

        for (int i = 0; i < pulpPointList.size(); i++) {
            // XXX
        }
    }

    /**
     * Hàm thiết lập vẽ đoạn thẳng cho các điểm đầu mút.
     */
    public abstract void ruleDrawOutline();

    /**
     * Hàm thiết lập tô lại.
     */
    public abstract void rulePaintColor();
}
