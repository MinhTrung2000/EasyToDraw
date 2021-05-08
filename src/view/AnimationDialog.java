/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import control.SettingConstants;
import control.myawt.SKPoint2D;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.UIManager;
import model.shape2d.animation.Fish;

/**
 *
 * @author DELL
 */
public class AnimationDialog extends javax.swing.JDialog {

    private final int widthBoard;
    private final int heightBoard;

    public Color[][] colorOfBoard;
    private String[][] coordOfBoard;
    private Color[][] changedColorOfBoard;
    private String[][] changedCoordOfBoard;
    private boolean[][] markedChangeOfBoard;

    /**
     * Creates new form AnimationDialog
     */
    public AnimationDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        widthBoard = this.getWidth();
        heightBoard = this.getHeight();
        this.colorOfBoard = new Color[heightBoard][widthBoard];
        this.coordOfBoard = new String[heightBoard][widthBoard];
        this.changedColorOfBoard = new Color[heightBoard][widthBoard];
        this.changedCoordOfBoard = new String[heightBoard][widthBoard];
        this.markedChangeOfBoard = new boolean[heightBoard][widthBoard];

        for (int i = 0; i < this.heightBoard; i++) {
            for (int j = 0; j < this.widthBoard; j++) {
                colorOfBoard[i][j] = SettingConstants.DEFAULT_PIXEL_COLOR;
                coordOfBoard[i][j] = null;
            }
        }

        for (int i = 0; i < this.heightBoard; i++) {
            for (int j = 0; j < this.widthBoard; j++) {
                markedChangeOfBoard[i][j] = false;
                changedColorOfBoard[i][j] = SettingConstants.DEFAULT_PIXEL_COLOR;
                changedCoordOfBoard[i][j] = null;
            }
        }

        setModalityType(ModalityType.APPLICATION_MODAL);
        setLocationRelativeTo(parent);
        setAlwaysOnTop(false);
    }

    public void animate() {
        System.out.println("view.AnimationDialog.animate()");
        Fish fish = new Fish(markedChangeOfBoard, changedColorOfBoard, changedCoordOfBoard, Color.BLACK);
        SKPoint2D startP = new SKPoint2D(50, 30);
        SKPoint2D endP = new SKPoint2D(70, 70);

//        fish.paintFish1(startP, endP);
//        fish.paintFish2(new SKPoint2D(30, 65), new SKPoint2D(110, 110));
//
        SKPoint2D startP_Volcano = new SKPoint2D(80, 45);
        SKPoint2D endP_Volcano = new SKPoint2D(40, 105);

        SKPoint2D startP_Cloud = new SKPoint2D(35, 30);

        SKPoint2D startP_Ground = new SKPoint2D(0, 25);
        SKPoint2D startP_Smoke = new SKPoint2D(startP_Volcano, 15, -25);
        SKPoint2D startP_Sun = new SKPoint2D(startP_Volcano, -50, -30);
        SKPoint2D startP_Tree = new SKPoint2D(startP_Volcano, -30, 20);
        SKPoint2D startP_Fish1 = new SKPoint2D(startP_Volcano, 50, 50);
        SKPoint2D startP_Fish2 = new SKPoint2D(startP_Volcano, -30, 42);
//                         
//        AppleTree tree = new AppleTree(markedChangeOfBoard, changedColorOfBoard, changedCoordOfBoard, selectedColor);
//        tree.drawAppleTree(startP_Tree);
//        tree.paintAppleTree(startP_Tree);
//        tree.paintApple();

//        Sun sun = new Sun(markedChangeOfBoard, changedColorOfBoard, changedCoordOfBoard, selectedColor);
//
//        sun.drawSun(startP_Sun);
//        copyColorValue(colorOfBoard, changedColorOfBoard, true);
//        sun.paintSun(startP_Sun);
//        Ground ground = new Ground(markedChangeOfBoard, changedColorOfBoard, changedCoordOfBoard, selectedColor);
//        ground.drawGround(startP_Ground);
//        ground.paintGround(startP_Ground);
//
//        ground.drawAndPaintFlowers();
//        River river = new River(markedChangeOfBoard, changedColorOfBoard, changedCoordOfBoard, selectedColor);
//        river.drawRiver(new SKPoint2D(startP_Ground, 0, 26));
//        river.paintRiver(new SKPoint2D(startP_Ground, 0, 26));
//
//        Cloud cloud = new Cloud(markedChangeOfBoard, changedColorOfBoard, changedCoordOfBoard, selectedColor);
//        cloud.drawCloud(startP_Cloud);
        fish = new Fish(markedChangeOfBoard, changedColorOfBoard, changedCoordOfBoard, Color.BLACK);
        fish.drawFish1(startP_Fish1, new SKPoint2D(0, 0));
        fish.drawFish2(startP_Fish2, new SKPoint2D(0, 0));
        fish.paintFish1(startP_Fish1, new SKPoint2D(0, 0));
        fish.paintFish2(startP_Fish2, new SKPoint2D(0, 0));

//                        Volcano volcano = new Volcano(markedChangeOfBoard, changedColorOfBoard, changedCoordOfBoard, selectedColor);
//                        Smoke smoke = new Smoke(markedChangeOfBoard, changedColorOfBoard, changedCoordOfBoard, selectedColor);
//                        smoke.drawSmoke(startP_Smoke);
//                        volcano.drawVolcano(startP_Volcano, endP_Volcano);
//                        draw xong paint luôn, vì lúc này mảng tạm đã có dữ liệu (khác với vẽ chuột, lúc đó ko có dữ liệu, phải copyCoordValue)
//                        volcano.paintVolcano(startP_Volcano);
        mergeColorValue();
        repaint();
    }

    @Override
    public void paintComponents(Graphics graphic) {
        super.paintComponents(graphic);
        
        for (int i = 0; i < this.heightBoard / SettingConstants.RECT_SIZE; i++) {
            for (int j = 0; j < this.heightBoard / SettingConstants.RECT_SIZE; j++) {
                if (markedChangeOfBoard[i][j] == true) {
                    graphic.setColor(changedColorOfBoard[i][j]);
                } else {
                    graphic.setColor(colorOfBoard[i][j]);
                }

                graphic.fillRect(j * SettingConstants.RECT_SIZE + 1,
                        i * SettingConstants.RECT_SIZE + 1,
                        SettingConstants.SIZE,
                        SettingConstants.SIZE
                );
            }
        }
    }

    private void mergeColorValue() {
        System.out.println("view.AnimationDialog.mergeColorValue()");
        for (int i = 0; i < this.getHeight() / SettingConstants.RECT_SIZE; i++) {
            for (int j = 0; j < this.getWidth() / SettingConstants.RECT_SIZE; j++) {
                if (markedChangeOfBoard[i][j] == true) {
                    colorOfBoard[i][j] = new Color(changedColorOfBoard[i][j].getRGB());
                }
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelAnimation = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        panelAnimation.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout panelAnimationLayout = new javax.swing.GroupLayout(panelAnimation);
        panelAnimation.setLayout(panelAnimationLayout);
        panelAnimationLayout.setHorizontalGroup(
            panelAnimationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1492, Short.MAX_VALUE)
        );
        panelAnimationLayout.setVerticalGroup(
            panelAnimationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 804, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelAnimation, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelAnimation, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AnimationDialog.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                AnimationDialog dialog = new AnimationDialog(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel panelAnimation;
    // End of variables declaration//GEN-END:variables
}
