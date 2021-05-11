package view;

import control.SettingConstants;
import control.myawt.SKPoint2D;
import control.util.Ultility;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.Timer;
import model.shape2d.animation.AppleTree;
import model.shape2d.animation.Cloud;
import model.shape2d.animation.Fish1;
import model.shape2d.animation.Fish2;
import model.shape2d.animation.Ground;
import model.shape2d.animation.River;
import model.shape2d.animation.Smoke;
import model.shape2d.animation.Sun;
import model.shape2d.animation.Volcano;

public class AnimationFrame extends javax.swing.JFrame {

    private Timer timer;
    private int timeUnit = 0;

    /**
     * Creates new form AnimationFrame
     */
    public AnimationFrame(java.awt.Frame parent) {
        initComponents();

        setResizable(false);
        getAnimationPanel().setComponent();
        setLocationRelativeTo(parent);
        setAlwaysOnTop(false);

        timer = new Timer(150, new ActionListener() {
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
        
        private int rotation = 0;
        private  int widthBoard;
        private  int heightBoard;

        private Color[][] colorOfBoard;
        private String[][] coordOfBoard;
        private Color[][] changedColorOfBoard;
        private String[][] changedCoordOfBoard;
        private boolean[][] markedChangeOfBoard;

        /* Animation objects */
        private Sun sun;
        private Cloud cloud1;
        private Cloud cloud2;
        private Volcano volcano;
        private Smoke smoke;
        private Ground ground;
        private AppleTree tree;
        private River river;
        private Fish1 fish1;
        private Fish2 fish2;

        /* Object intial position */
        private SKPoint2D startPointSun = new SKPoint2D(20, 20);
        private SKPoint2D startPointVolcano = new SKPoint2D(80, 40);
        private SKPoint2D endPointVolcano = new SKPoint2D(30, 100);
        private SKPoint2D startPointCloud1 = new SKPoint2D(90, 35);
        private SKPoint2D startPointCloud2 = new SKPoint2D(100, 20);
 
        private SKPoint2D startPointSmoke = new SKPoint2D(startPointVolcano, 15, -22);
        private SKPoint2D startPointGround = new SKPoint2D(0, 70);
        private SKPoint2D startPointTree = new SKPoint2D(startPointGround, 180, -20);
        private SKPoint2D startPointRiver = new SKPoint2D(startPointGround, 0, 26);
        private SKPoint2D startPointFish1 = new SKPoint2D(startPointRiver, 100, 35);
        private SKPoint2D startPointFish2 = new SKPoint2D(startPointRiver, 100, 57);

        public AnimationPanel() {
        }

        public void setComponent() {
            System.out.println("view.AnimationFrame.AnimationPanel.setComponent()");

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

            sun = new Sun(markedChangeOfBoard, changedColorOfBoard, changedCoordOfBoard, Color.BLACK);
            sun.setProperty(startPointSun);

            cloud1 = new Cloud(markedChangeOfBoard, changedColorOfBoard, changedCoordOfBoard, Color.BLACK);
            cloud1.setProperty(startPointCloud1);
            cloud2 = new Cloud(markedChangeOfBoard, changedColorOfBoard, changedCoordOfBoard, Color.BLACK);
            cloud2.setProperty(startPointCloud2);

            volcano = new Volcano(markedChangeOfBoard, changedColorOfBoard, changedCoordOfBoard, Color.BLACK);
            smoke = new Smoke(markedChangeOfBoard, changedColorOfBoard, changedCoordOfBoard, Color.BLACK);
            ground = new Ground(markedChangeOfBoard, changedColorOfBoard, changedCoordOfBoard, Color.BLACK);
            tree = new AppleTree(markedChangeOfBoard, changedColorOfBoard, changedCoordOfBoard, Color.BLACK);
            river = new River(markedChangeOfBoard, changedColorOfBoard, changedCoordOfBoard, Color.BLACK);

            fish1 = new Fish1(markedChangeOfBoard, changedColorOfBoard, changedCoordOfBoard, Color.BLACK);
            fish2 = new Fish2(markedChangeOfBoard, changedColorOfBoard, changedCoordOfBoard, Color.BLACK);
//            fish.setPropertyFish1(startPointFish1);
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

            /* VOLCANO */
            smoke.drawSmoke(startPointSmoke);
            volcano.drawVolcano(startPointVolcano, endPointVolcano);
            volcano.paintVolcano(startPointVolcano);

            mergeColorValue();
            resetChangedPropertyArray();
            
            /* SUN */
            sun.drawSun();
            sun.drawSunLight(rotation);
            rotation+=10;
            if(rotation>45) rotation =0;
            /* CLOUD */
            cloud1.drawCloud();
            cloud2.drawCloud();

           

            /* GROUND */
            ground.drawGround(startPointGround);
            ground.paintGround(startPointGround);
            ground.drawAndPaintFlowers();
            
            mergeColorValue();
            resetChangedPropertyArray();
            
             /* APPLE TREE */
            tree.drawAppleTree(startPointTree);
            tree.paintAppleTree(startPointTree);
            tree.paintApple();

            mergeColorValue();
            resetChangedPropertyArray();
            
            /* SKY */
            Ultility.paint(colorOfBoard, markedChangeOfBoard, new SKPoint2D(startPointGround, 10, -10), new Color(205,249,255), true);

            /* RIVER */
            river.drawRiver(startPointRiver);
            river.paintRiver(startPointRiver);

            /* FISH1, FISH2*/
            // Set property trong hàm setComponent
            fish1.setPropertyFish1(startPointFish1);
            fish1.drawFish1();
            
            fish2.setPropertyFish2(startPointFish2);
            fish2.drawFish2();
            
            this.repaint();
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
        super.setVisible(b);
        timer.start();
    }

    @Override
    public void dispose() {
        super.dispose();
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

        animationPanel.setPreferredSize(new java.awt.Dimension(1361, 972));

        javax.swing.GroupLayout animationPanelLayout = new javax.swing.GroupLayout(animationPanel);
        animationPanel.setLayout(animationPanelLayout);
        animationPanelLayout.setHorizontalGroup(
            animationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1361, Short.MAX_VALUE)
        );
        animationPanelLayout.setVerticalGroup(
            animationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 960, Short.MAX_VALUE)
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
            java.util.logging.Logger.getLogger(AnimationFrame.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AnimationFrame.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AnimationFrame.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AnimationFrame.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
