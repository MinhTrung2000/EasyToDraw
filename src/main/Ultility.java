package main;

import java.awt.Color;
import java.awt.Graphics;
import javafx.util.Pair;
import javax.swing.JButton;
import javax.swing.JPopupMenu;

public class Ultility {

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
    
}
