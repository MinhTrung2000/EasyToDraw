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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JPanel;
import model.shape2d.animation.Fish;

/**
 *
 * @author DELL
 */
public class AnimationFrame extends javax.swing.JFrame {

    /**
     * Creates new form AnimationFrame
     */
    public AnimationFrame(java.awt.Frame parent) {
        initComponents();

        getAnimationPanel().setComponent();

        setLocationRelativeTo(parent);
        setAlwaysOnTop(false);
    }

    public AnimationPanel getAnimationPanel() {
        return ((AnimationPanel) this.animationPanel);
    }

    public class AnimationPanel extends JPanel {

        private int widthBoard;
        private int heightBoard;

        private Color[][] colorOfBoard;
        private String[][] coordOfBoard;
        private Color[][] changedColorOfBoard;
        private String[][] changedCoordOfBoard;
        private boolean[][] markedChangeOfBoard;

        public AnimationPanel() {
        }

        public void setComponent() {
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

        }

        @Override
        public void paintComponents(Graphics graphic) {
            super.paintComponents(graphic);

            for (int i = 0; i < heightBoard / SettingConstants.RECT_SIZE; i++) {
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

        public void animate() {
            Fish fish = new Fish(markedChangeOfBoard, changedColorOfBoard, changedCoordOfBoard, Color.BLACK);
            SKPoint2D startP = new SKPoint2D(50, 30);
            SKPoint2D endP = new SKPoint2D(70, 70);

            SKPoint2D startP_Volcano = new SKPoint2D(80, 45);
            SKPoint2D endP_Volcano = new SKPoint2D(40, 105);

            SKPoint2D startP_Cloud = new SKPoint2D(35, 30);

            SKPoint2D startP_Ground = new SKPoint2D(0, 25);
            SKPoint2D startP_Smoke = new SKPoint2D(startP_Volcano, 15, -25);
            SKPoint2D startP_Sun = new SKPoint2D(startP_Volcano, -50, -30);
            SKPoint2D startP_Tree = new SKPoint2D(startP_Volcano, -30, 20);
            SKPoint2D startP_Fish1 = new SKPoint2D(startP_Volcano, 50, 50);
            SKPoint2D startP_Fish2 = new SKPoint2D(startP_Volcano, -30, 42);

            fish = new Fish(markedChangeOfBoard, changedColorOfBoard, changedCoordOfBoard, Color.BLACK);
            fish.drawFish1(startP_Fish1, new SKPoint2D(0, 0));
            fish.drawFish2(startP_Fish2, new SKPoint2D(0, 0));
            fish.paintFish1(startP_Fish1, new SKPoint2D(0, 0));
            fish.paintFish2(startP_Fish2, new SKPoint2D(0, 0));

            mergeColorValue();
            repaint();
        }

        private void mergeColorValue() {
            for (int i = 0; i < this.getHeight() / SettingConstants.RECT_SIZE; i++) {
                for (int j = 0; j < this.getWidth() / SettingConstants.RECT_SIZE; j++) {
                    if (markedChangeOfBoard[i][j] == true) {
                        colorOfBoard[i][j] = new Color(changedColorOfBoard[i][j].getRGB());
                    }
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

        animationPanel = new AnimationPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        javax.swing.GroupLayout animationPanelLayout = new javax.swing.GroupLayout(animationPanel);
        animationPanel.setLayout(animationPanelLayout);
        animationPanelLayout.setHorizontalGroup(
            animationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1361, Short.MAX_VALUE)
        );
        animationPanelLayout.setVerticalGroup(
            animationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 826, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(animationPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(animationPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AnimationFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AnimationFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AnimationFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AnimationFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AnimationFrame(null).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel animationPanel;
    // End of variables declaration//GEN-END:variables
}
