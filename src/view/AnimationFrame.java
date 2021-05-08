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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.Timer;
import model.shape2d.animation.AppleTree;
import model.shape2d.animation.Cloud;
import model.shape2d.animation.Fish;
import model.shape2d.animation.Ground;
import model.shape2d.animation.River;
import model.shape2d.animation.Smoke;
import model.shape2d.animation.Sun;
import model.shape2d.animation.Volcano;

/**
 *
 * @author DELL
 */
public class AnimationFrame extends javax.swing.JFrame {

    private Timer timer;

    /**
     * Creates new form AnimationFrame
     */
    public AnimationFrame(java.awt.Frame parent) {
        initComponents();

        setResizable(false);
        getAnimationPanel().setComponent();
        setLocationRelativeTo(parent);
        setAlwaysOnTop(false);

        timer = new Timer(0, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getAnimationPanel().animate();
            }
        });
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
            widthBoard = this.getWidth() / SettingConstants.RECT_SIZE + 3;
            heightBoard = this.getHeight() / SettingConstants.RECT_SIZE + 3;

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
        public void paintComponent(Graphics graphic) {
            super.paintComponents(graphic);

            graphic.setColor(Color.WHITE);
            graphic.fillRect(0, 0, this.getWidth(), this.getHeight());

            for (int i = 0; i < this.heightBoard; i++) {
                for (int j = 0; j < this.widthBoard; j++) {
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
            resetSavedPropertyArray();
            resetChangedPropertyArray();

            Fish fish = new Fish(markedChangeOfBoard, changedColorOfBoard, changedCoordOfBoard, Color.BLACK);

            SKPoint2D startPoint = new SKPoint2D(50, 30);
            SKPoint2D endPoint = new SKPoint2D(70, 70);

            /* VOLCANO */
            SKPoint2D startPointVolcano = new SKPoint2D(80, 45);
            SKPoint2D endPointVolcano = new SKPoint2D(40, 105);
            SKPoint2D startPointSmoke = new SKPoint2D(startPointVolcano, 15, -25);
//            Volcano volcano = new Volcano(markedChangeOfBoard, changedColorOfBoard, changedCoordOfBoard, Color.BLACK);
//            Smoke smoke = new Smoke(markedChangeOfBoard, changedColorOfBoard, changedCoordOfBoard, Color.BLACK);
//            smoke.drawSmoke(startPointSmoke);
//            volcano.drawVolcano(startPointVolcano, endPointVolcano);
//            volcano.paintVolcano(startPointVolcano);

            /* SUN */
            SKPoint2D startPointSun = new SKPoint2D(70, 10);
            Sun sun = new Sun(markedChangeOfBoard, changedColorOfBoard, changedCoordOfBoard, Color.BLACK);
            sun.drawSun(startPointSun);
            sun.paintSun(startPointSun);

            /* GROUND */
            SKPoint2D startPointGround = new SKPoint2D(0, 56);
            Ground ground = new Ground(markedChangeOfBoard, changedColorOfBoard, changedCoordOfBoard, Color.BLACK);
            ground.drawGround(startPointGround);
            ground.paintGround(startPointGround);
            ground.drawAndPaintFlowers();

            /* APPLE TREE */
            SKPoint2D startPointTree = new SKPoint2D(startPointGround, 30, -20);
            AppleTree tree = new AppleTree(markedChangeOfBoard, changedColorOfBoard, changedCoordOfBoard, Color.BLACK);
            tree.drawAppleTree(startPointTree);
            tree.paintAppleTree(startPointTree);
            tree.paintApple();

            /* RIVER */
            SKPoint2D startPointRiver = new SKPoint2D(startPointGround, 0, 26);
            River river = new River(markedChangeOfBoard, changedColorOfBoard, changedCoordOfBoard, Color.BLACK);
            river.drawRiver(startPointRiver);
            river.paintRiver(startPointRiver);

            /* CLOUD */
            SKPoint2D startPointCloud = new SKPoint2D(70, 30);
            Cloud cloud = new Cloud(markedChangeOfBoard, changedColorOfBoard, changedCoordOfBoard, Color.BLACK);
            cloud.drawCloud(startPointCloud);
            cloud.drawCloud(new SKPoint2D(90, 20));

            /* FISH1, FISH2*/
            SKPoint2D startPointFish1 = new SKPoint2D(startPointRiver, 10, 40);
            SKPoint2D startPointFish2 = new SKPoint2D(startPointRiver, 80, 40);
            fish = new Fish(markedChangeOfBoard, changedColorOfBoard, changedCoordOfBoard, Color.BLACK);
            fish.drawFish1(startPointFish1, new SKPoint2D(0, 0));
            fish.paintFish1(startPointFish1, new SKPoint2D(0, 0));
            fish.drawFish2(startPointFish2, new SKPoint2D(0, 0));
            fish.paintFish2(startPointFish2, new SKPoint2D(0, 0));
            
            mergeColorValue();
            this.repaint();
            timer.stop();
        }

        private void mergeColorValue() {
            for (int i = 0; i < this.heightBoard; i++) {
                for (int j = 0; j < this.widthBoard; j++) {
                    if (markedChangeOfBoard[i][j] == true) {
                        colorOfBoard[i][j] = new Color(changedColorOfBoard[i][j].getRGB());
                    }
                }
            }
        }

        public void resetSavedPropertyArray() {
            for (int i = 0; i < this.heightBoard; i++) {
                for (int j = 0; j < this.widthBoard; j++) {
                    colorOfBoard[i][j] = SettingConstants.DEFAULT_PIXEL_COLOR;
                    coordOfBoard[i][j] = null;
                }
            }
        }

        public void resetChangedPropertyArray() {
            for (int i = 0; i < this.heightBoard; i++) {
                for (int j = 0; j < this.widthBoard; j++) {
                    markedChangeOfBoard[i][j] = false;
                    changedColorOfBoard[i][j] = SettingConstants.DEFAULT_PIXEL_COLOR;
                    changedCoordOfBoard[i][j] = null;
                }
            }
        }
    }

    @Override
    public void setVisible(boolean b) {
        super.setVisible(b); //To change body of generated methods, choose Tools | Templates.
        timer.start();
    }

    @Override
    public void dispose() {
        super.dispose(); //To change body of generated methods, choose Tools | Templates.
        timer.stop();
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
