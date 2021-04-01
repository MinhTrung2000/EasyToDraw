/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gridAndMouse;

/**
 *
 * @author minhphuc
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;

import java.awt.Container;
import java.awt.BorderLayout;
import java.awt.Graphics;

public class Painter extends JFrame implements MouseMotionListener{
    private int x = -10, y =-10;
    
    public Painter() { 
        this.setTitle("Painter");
        this.setSize(800, 600);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        // set up the layout
        JLabel instructions = new JLabel("Drag the mouse to draw", JLabel.RIGHT);
        Container c = this.getContentPane();
        c.setLayout(new BorderLayout());
        c.add(instructions, BorderLayout.SOUTH);
        // configure mouse 
        c.addMouseMotionListener(this);
        setVisible(true);
    }
    
    @Override
    public void paint(Graphics g) {
        g.fillOval(x, y, 5, 5);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        this.x = e.getX(); this.y = e.getY();
        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public static void main(String[] args) {
        Painter p = new Painter();
    }
}
