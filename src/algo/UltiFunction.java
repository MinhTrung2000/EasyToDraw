package algo;

import java.awt.Color;
import java.awt.Graphics;

public class UltiFunction {
    public UltiFunction() {}
    
    public static void fillCells(Graphics g, int coordX, int coordY, Color color) {
        g.fillRect(coordX, coordY, coordX + 5, coordY + 5);
    }
}
