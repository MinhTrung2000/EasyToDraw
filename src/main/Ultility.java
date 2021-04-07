package main;

import model.shape2d.Point2D;
import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.Queue;
import javafx.util.Pair;
import javax.swing.JButton;
import javax.swing.JPopupMenu;

public class Ultility {
    
    public static int D_X[] = {-1, 0, 0, 1};
    public static int D_Y[] = {0, -1, 1, 0};

    public Ultility() {
    }

    public static void fillCells(Graphics g, int coordX, int coordY, Color color) {
        g.fillRect(coordX, coordY, coordX + 5, coordY + 5);
    }

    public static void showPopMenuOfButton(JButton button, JPopupMenu popMenu) {
        assert (button != null && popMenu != null);
        popMenu.show(button,
                (int) button.getAlignmentX() + button.getWidth(),
                (int) button.getAlignmentY()
        );
    }

    public static void assertSameSizeOfArray(Object[][] array_from, Object[][] array_to) {
        int rowNum_from = array_from.length;
        int colNum_from = array_from[0].length;
        int rowNum_to = array_to.length;
        int colNum_to = array_to[0].length;

        assert (rowNum_from == rowNum_to && colNum_from == colNum_to);
    }

    /**
     * Check if point is in bound of array.
     *
     * @param array
     * @param x
     * @param y
     * @return
     */
    public static boolean checkValidPoint(Object[][] array, int x, int y) {
        if (array.length == 0) {
            return false;
        }

        int height = array.length;
        int width = array[0].length;

        return (0 <= x && x < width && 0 <= y && y < height);
    }

    /**
     * Check if pixel having coordinate x can be put or not.
     *
     * @param coordX
     * @param lineStyle
     * @return
     */
    public static boolean checkPixelPut(int coordX, Settings.LineStyle lineStyle) {
        switch (lineStyle) {
            case DEFAULT:
                return true;
            case DASH: {
                return (coordX % 5 != 0);
            }
            case DASH_DOT: {
                return (coordX % 6 != 4 && coordX % 6 != 0);
            }
            case DASH_DOT_DOT: {
                return (coordX % 8 != 4 && coordX % 8 != 6 && coordX % 8 != 0);
            }
            case ARROW: {
                return true;
            }
        }
        return false;
    }

    /**
     * Paint the neighbors of current point via BFS algorithm.
     * @param colorOfBoard
     * @param markedArray
     * @param point
     * @param choosedColor 
     */
    public static void paint(Color[][] colorOfBoard, boolean[][] markedArray, Point2D point, Color choosedColor) {
        int coordX = point.getCoordX();
        int coordY = point.getCoordY();

        Queue<Pair<Integer, Integer>> queue = new LinkedList<>();

        if (!checkValidPoint(colorOfBoard, coordX, coordY)) {
            return;
        }

        Color oldColor = colorOfBoard[coordX][coordY];

        if (!oldColor.equals(choosedColor)) {
            markedArray[coordX][coordY] = true;
            queue.add(new Pair<>(coordX, coordY));
            
            while (queue.size() > 0) {
                Pair<Integer, Integer> frontQueue = queue.poll();
                
                for (int i = 0; i < 4; i++) {
                    int newCoordX = frontQueue.getKey() + D_X[i];
                    int newCoordY = frontQueue.getValue() + D_Y[i];
                    
                    if (checkValidPoint(colorOfBoard, newCoordX, newCoordY) &&
                            colorOfBoard[newCoordX][newCoordY].equals(oldColor)) {
                        markedArray[newCoordX][newCoordY] = true;
                        colorOfBoard[newCoordX][newCoordY] = choosedColor;
                        queue.add(new Pair<>(newCoordX, newCoordY));
                    }
                }
            }
        }
    }
}
