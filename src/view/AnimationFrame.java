package view;

import control.SettingConstants;
import control.myawt.SKPoint2D;
import control.util.Ultility;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.RoundingMode;
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

    public static final int TIME_DELAY = 75;

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

        timer = new Timer(TIME_DELAY, new ActionListener() {
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

        public final Color SKY_COLOR = new Color(205, 249, 255);

        private int widthBoard;
        private int heightBoard;

        private Color[][] colorOfBoard;
        private Color[][] changedColorOfBoard;
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
        private SKPoint2D startPointCloud1 = new SKPoint2D(130, 35);
        private SKPoint2D startPointCloud2 = new SKPoint2D(140, 20);
        private SKPoint2D startPointSmoke = new SKPoint2D(startPointVolcano, 15, -22);
        private SKPoint2D startPointGround = new SKPoint2D(0, 70);
        private SKPoint2D startPointTree = new SKPoint2D(startPointGround, 180, -20);
        private SKPoint2D startPointRiver = new SKPoint2D(startPointGround, 0, 26);
        private SKPoint2D startPointFish1 = new SKPoint2D(startPointRiver, 0, 55);
        private SKPoint2D startPointFish2 = new SKPoint2D(startPointRiver, 0, 35);
        private SKPoint2D skyPaintPoint = new SKPoint2D(startPointGround, 10, -10);

        public AnimationPanel() {
        }

        public void setComponent() {
            widthBoard = this.getWidth() / SettingConstants.RECT_SIZE + 3;
            heightBoard = this.getHeight() / SettingConstants.RECT_SIZE + 3;

            this.colorOfBoard = new Color[heightBoard][widthBoard];
            this.changedColorOfBoard = new Color[heightBoard][widthBoard];
            this.markedChangeOfBoard = new boolean[heightBoard][widthBoard];

            resetSavedPropertyArray();
            resetChangedPropertyArray();

            sun = new Sun(markedChangeOfBoard, changedColorOfBoard,
                    null, Color.BLACK);
            sun.setProperty(startPointSun);

            cloud1 = new Cloud(markedChangeOfBoard, changedColorOfBoard,
                    null, Color.BLACK);
            cloud1.setProperty(startPointCloud1);

            cloud2 = new Cloud(markedChangeOfBoard, changedColorOfBoard,
                    null, Color.BLACK);
            cloud2.setProperty(startPointCloud2);

            volcano = new Volcano(markedChangeOfBoard, changedColorOfBoard,
                    null, Color.BLACK);
            volcano.setProperty(startPointVolcano, endPointVolcano);

            smoke = new Smoke(markedChangeOfBoard, changedColorOfBoard,
                    null, Color.BLACK);
            smoke.setProperty(startPointSmoke, SKY_COLOR);

            ground = new Ground(markedChangeOfBoard, changedColorOfBoard,
                    null, Color.BLACK);
            ground.setProperty(this.widthBoard, startPointGround);

            tree = new AppleTree(markedChangeOfBoard, changedColorOfBoard,
                    null, Color.BLACK);
            tree.setProperty(startPointTree);

            river = new River(markedChangeOfBoard, changedColorOfBoard,
                    null, Color.BLACK);
            river.setProperty(this.widthBoard, startPointRiver);

            fish1 = new Fish1(markedChangeOfBoard, changedColorOfBoard,
                    null, Color.BLACK);
            fish1.setPropertyFish1(this.widthBoard, startPointFish1);

            fish2 = new Fish2(markedChangeOfBoard, changedColorOfBoard,
                    null, Color.BLACK);
            fish2.setPropertyFish2(this.widthBoard, startPointFish2);
        }

        @Override
        public void paintComponent(Graphics graphic) {
            super.paintComponents(graphic);

            graphic.setColor(Color.WHITE);
            graphic.fillRect(0, 0, this.getWidth(), this.getHeight());

            for (int i = 0; i < this.heightBoard; i++) {
                for (int j = 0; j < this.widthBoard; j++) {
                    graphic.setColor(colorOfBoard[i][j]);

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

            /* SKY */
            Ultility.paint(colorOfBoard, markedChangeOfBoard,
                    skyPaintPoint, SKY_COLOR, true);

            /* VOLCANO */
            volcano.drawVolcano();
            label_StartP_Volca.setText(volcano.getStartPoint().saveCoordToString());
            label_EndP_Volca.setText(volcano.getEndPoint().saveCoordToString());

            /* SMOKE */
            Double scaleLevel = smoke.getLevelScale();
            scaleLevel = BigDecimal.valueOf(scaleLevel).setScale(3, RoundingMode.HALF_UP)
                    .doubleValue();

            smoke.drawSmoke();
            label_CenterP_Smoke.setText(smoke.getStartPoint().saveCoordToString());
            label_ScaleLevel_Smoke.setText(scaleLevel.toString());

            mergeColorValue();
            resetChangedPropertyArray();

            /* SUN */
            sun.drawSun();
            label_CenterP_Sun.setText(sun.getCenterPoint2D().saveCoordToString());
            label_Angle_Sun.setText(Integer.toString(sun.getRotatedAngle()));

            /* GROUND */
            ground.drawGround();
            label_StartP_ground.setText(ground.getStartPoint().saveCoordToString());

            /* CLOUD */
            cloud1.drawCloud();
            cloud2.drawCloud();
            label_CenterP_C1.setText(cloud1.movingCenterPoint.saveCoordToString());
            label_CenterP_C2.setText(cloud2.movingCenterPoint.saveCoordToString());

            mergeColorValue();
            resetChangedPropertyArray();

            //cây đè lên Ground + Cloud
            /* APPLE TREE */
            tree.drawAppleTree();
            label_StartP_Tree.setText(tree.getStartPoint().saveCoordToString());

            /* RIVER */
            river.drawRiver();
            label_StartP_river.setText(river.getStartPoint().saveCoordToString());

            /* FISH1, FISH2*/
            fish1.drawFish1();

            label_StartP_F1.setText(fish1.getStartPoint().saveCoordToString());
            label_BodyCenterP_F1.setText(fish1.getThanCaCenterPoint().saveCoordToString());
            label_HeadCenterP_F1.setText(fish1.getDauCaCenterPoint().saveCoordToString());
            label_TopFinStartP_F1.setText(fish1.getVayTrenStartPoint().saveCoordToString());
            label_BotFinStartP_F1.setText(fish1.getVayDuoiStartPoint().saveCoordToString());
            label_TailECenterP_F1.setText(fish1.getTailEdgeCenterPoint().saveCoordToString());

            mergeColorValue();
            resetChangedPropertyArray();

            fish2.drawFish2();

            label_StartP_F2.setText(fish2.getStartPoint().saveCoordToString());
            label_BodyCenterP_F2.setText(fish2.getThanCaCenterPoint().saveCoordToString());
            label_FoBCenterP_F2.setText(fish2.getVayTT2CenterPoint().saveCoordToString());
            label_TopFinStartP_F2.setText(fish2.getTopFinStartPoints().saveCoordToString());
            label_BotFinStartP_F2.setText(fish2.getBotFinStartPoints().saveCoordToString());
            label_TailStartP_F2.setText(fish2.getDuoiStartPoint().saveCoordToString());

            mergeColorValue();
            resetChangedPropertyArray();
            
            repaint();
        }

        /**
         * Merge new color drawn from new shape to saved color array.
         */
        private void mergeColorValue() {
            for (int i = 0; i < this.heightBoard; i++) {
                for (int j = 0; j < this.widthBoard; j++) {
                    if (markedChangeOfBoard[i][j]) {
                        colorOfBoard[i][j] = new Color(changedColorOfBoard[i][j].getRGB());
                    }
                }
            }
        }

        /**
         * We need to reset the buffer array because a new shape can override
         * the old one.
         */
        public void resetSavedPropertyArray() {
            for (int i = 0; i < this.heightBoard; i++) {
                for (int j = 0; j < this.widthBoard; j++) {
                    colorOfBoard[i][j] = SettingConstants.DEFAULT_PIXEL_COLOR;
                }
            }
        }

        public void resetChangedPropertyArray() {
            for (int i = 0; i < this.heightBoard; i++) {
                for (int j = 0; j < this.widthBoard; j++) {
                    markedChangeOfBoard[i][j] = false;
                    changedColorOfBoard[i][j] = SettingConstants.DEFAULT_PIXEL_COLOR;
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
        coordinatePanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        Fish1Title = new javax.swing.JLabel();
        SmokeTitle = new javax.swing.JLabel();
        GroundTitle = new javax.swing.JLabel();
        TreeTitle = new javax.swing.JLabel();
        Fish2Title = new javax.swing.JLabel();
        CloudTitle = new javax.swing.JLabel();
        RiverTitle = new javax.swing.JLabel();
        Fish1Panel = new javax.swing.JPanel();
        F2_StartDraw1 = new javax.swing.JLabel();
        label_StartP_F1 = new javax.swing.JLabel();
        label_BodyCenterP_F1 = new javax.swing.JLabel();
        F2_Body1 = new javax.swing.JLabel();
        label_TopFinStartP_F1 = new javax.swing.JLabel();
        F2_TopFin1 = new javax.swing.JLabel();
        F2_Head1 = new javax.swing.JLabel();
        label_HeadCenterP_F1 = new javax.swing.JLabel();
        F2_BotFin1 = new javax.swing.JLabel();
        label_BotFinStartP_F1 = new javax.swing.JLabel();
        label_TailECenterP_F1 = new javax.swing.JLabel();
        F2_TailStartP1 = new javax.swing.JLabel();
        VolcanoTitle = new javax.swing.JLabel();
        Fish2Panel = new javax.swing.JPanel();
        F2_StartDraw2 = new javax.swing.JLabel();
        label_StartP_F2 = new javax.swing.JLabel();
        label_BodyCenterP_F2 = new javax.swing.JLabel();
        F2_Body2 = new javax.swing.JLabel();
        label_TopFinStartP_F2 = new javax.swing.JLabel();
        F2_TopFin2 = new javax.swing.JLabel();
        F2_Head2 = new javax.swing.JLabel();
        label_FoBCenterP_F2 = new javax.swing.JLabel();
        F2_BotFin2 = new javax.swing.JLabel();
        label_BotFinStartP_F2 = new javax.swing.JLabel();
        label_TailStartP_F2 = new javax.swing.JLabel();
        F2_TailStartP2 = new javax.swing.JLabel();
        VolcanoPanel = new javax.swing.JPanel();
        F2_StartDraw3 = new javax.swing.JLabel();
        label_StartP_Volca = new javax.swing.JLabel();
        label_EndP_Volca = new javax.swing.JLabel();
        F2_Body3 = new javax.swing.JLabel();
        GroundPanel = new javax.swing.JPanel();
        F2_StartDraw4 = new javax.swing.JLabel();
        label_StartP_ground = new javax.swing.JLabel();
        panelFish7 = new javax.swing.JPanel();
        F2_StartDraw5 = new javax.swing.JLabel();
        label_CenterP_Smoke = new javax.swing.JLabel();
        label_ScaleLevel_Smoke = new javax.swing.JLabel();
        F2_Body4 = new javax.swing.JLabel();
        TreePanel = new javax.swing.JPanel();
        F2_StartDraw6 = new javax.swing.JLabel();
        label_StartP_Tree = new javax.swing.JLabel();
        SunPanel = new javax.swing.JPanel();
        F2_StartDraw7 = new javax.swing.JLabel();
        label_CenterP_Sun = new javax.swing.JLabel();
        label_Angle_Sun = new javax.swing.JLabel();
        F2_Body5 = new javax.swing.JLabel();
        CloudPanel = new javax.swing.JPanel();
        F2_StartDraw8 = new javax.swing.JLabel();
        label_CenterP_C1 = new javax.swing.JLabel();
        label_CenterP_C2 = new javax.swing.JLabel();
        F2_Body6 = new javax.swing.JLabel();
        RiverPanel = new javax.swing.JPanel();
        F2_StartDraw9 = new javax.swing.JLabel();
        label_StartP_river = new javax.swing.JLabel();
        SunTitle = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        animationPanel.setPreferredSize(new java.awt.Dimension(1361, 972));

        javax.swing.GroupLayout animationPanelLayout = new javax.swing.GroupLayout(animationPanel);
        animationPanel.setLayout(animationPanelLayout);
        animationPanelLayout.setHorizontalGroup(
            animationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1362, Short.MAX_VALUE)
        );
        animationPanelLayout.setVerticalGroup(
            animationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        coordinatePanel.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("DETAILS");

        Fish1Title.setFont(new java.awt.Font("Segoe UI", 2, 18)); // NOI18N
        Fish1Title.setForeground(new java.awt.Color(255, 0, 0));
        Fish1Title.setText("Fish 1");

        SmokeTitle.setFont(new java.awt.Font("Segoe UI", 2, 18)); // NOI18N
        SmokeTitle.setForeground(new java.awt.Color(255, 0, 0));
        SmokeTitle.setText("Smoke");

        GroundTitle.setFont(new java.awt.Font("Segoe UI", 2, 18)); // NOI18N
        GroundTitle.setForeground(new java.awt.Color(255, 0, 0));
        GroundTitle.setText("Ground");

        TreeTitle.setFont(new java.awt.Font("Segoe UI", 2, 18)); // NOI18N
        TreeTitle.setForeground(new java.awt.Color(255, 0, 0));
        TreeTitle.setText("Apple Tree");

        Fish2Title.setFont(new java.awt.Font("Segoe UI", 2, 18)); // NOI18N
        Fish2Title.setForeground(new java.awt.Color(255, 0, 0));
        Fish2Title.setText("Fish 2");

        CloudTitle.setFont(new java.awt.Font("Segoe UI", 2, 18)); // NOI18N
        CloudTitle.setForeground(new java.awt.Color(255, 0, 0));
        CloudTitle.setText("Cloud");

        RiverTitle.setFont(new java.awt.Font("Segoe UI", 2, 18)); // NOI18N
        RiverTitle.setForeground(new java.awt.Color(255, 0, 0));
        RiverTitle.setText("River");

        Fish1Panel.setBackground(new java.awt.Color(255, 255, 255));

        F2_StartDraw1.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        F2_StartDraw1.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        F2_StartDraw1.setText("Drawing StartP");

        label_StartP_F1.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        label_StartP_F1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label_StartP_F1.setText("aa");
        label_StartP_F1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        label_StartP_F1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        label_BodyCenterP_F1.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        label_BodyCenterP_F1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label_BodyCenterP_F1.setText("aa");
        label_BodyCenterP_F1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        label_BodyCenterP_F1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        F2_Body1.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        F2_Body1.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        F2_Body1.setText("Body CenterP");

        label_TopFinStartP_F1.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        label_TopFinStartP_F1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label_TopFinStartP_F1.setText("aa");
        label_TopFinStartP_F1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        label_TopFinStartP_F1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        F2_TopFin1.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        F2_TopFin1.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        F2_TopFin1.setText("TopFin StartP");

        F2_Head1.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        F2_Head1.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        F2_Head1.setText("Head CenterP");

        label_HeadCenterP_F1.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        label_HeadCenterP_F1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label_HeadCenterP_F1.setText("aa");
        label_HeadCenterP_F1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        label_HeadCenterP_F1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        F2_BotFin1.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        F2_BotFin1.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        F2_BotFin1.setText("BotFin StartP");

        label_BotFinStartP_F1.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        label_BotFinStartP_F1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label_BotFinStartP_F1.setText("aa");
        label_BotFinStartP_F1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        label_BotFinStartP_F1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        label_TailECenterP_F1.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        label_TailECenterP_F1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label_TailECenterP_F1.setText("aa");
        label_TailECenterP_F1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        label_TailECenterP_F1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        F2_TailStartP1.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        F2_TailStartP1.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        F2_TailStartP1.setText("TailE CenterP");

        javax.swing.GroupLayout Fish1PanelLayout = new javax.swing.GroupLayout(Fish1Panel);
        Fish1Panel.setLayout(Fish1PanelLayout);
        Fish1PanelLayout.setHorizontalGroup(
            Fish1PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Fish1PanelLayout.createSequentialGroup()
                .addGroup(Fish1PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(Fish1PanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(F2_BotFin1, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(label_BotFinStartP_F1, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Fish1PanelLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(Fish1PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Fish1PanelLayout.createSequentialGroup()
                                .addComponent(F2_Head1, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(label_HeadCenterP_F1, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Fish1PanelLayout.createSequentialGroup()
                                .addComponent(F2_StartDraw1, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(label_StartP_F1, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(Fish1PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(F2_TopFin1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(F2_Body1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(F2_TailStartP1, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addGroup(Fish1PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(Fish1PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(label_TailECenterP_F1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(label_BodyCenterP_F1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(label_TopFinStartP_F1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        Fish1PanelLayout.setVerticalGroup(
            Fish1PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Fish1PanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(Fish1PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(label_BodyCenterP_F1)
                    .addComponent(F2_Body1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label_StartP_F1)
                    .addComponent(F2_StartDraw1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(Fish1PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(Fish1PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(F2_Head1)
                        .addComponent(label_HeadCenterP_F1))
                    .addGroup(Fish1PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(F2_TopFin1)
                        .addComponent(label_TopFinStartP_F1)))
                .addGap(18, 18, 18)
                .addGroup(Fish1PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(Fish1PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(F2_BotFin1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(label_BotFinStartP_F1))
                    .addGroup(Fish1PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(label_TailECenterP_F1)
                        .addComponent(F2_TailStartP1)))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        VolcanoTitle.setFont(new java.awt.Font("Segoe UI", 2, 18)); // NOI18N
        VolcanoTitle.setForeground(new java.awt.Color(255, 0, 0));
        VolcanoTitle.setText("Volcano");

        Fish2Panel.setBackground(new java.awt.Color(255, 255, 255));

        F2_StartDraw2.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        F2_StartDraw2.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        F2_StartDraw2.setText("Drawing StartP");

        label_StartP_F2.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        label_StartP_F2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label_StartP_F2.setText("aa");
        label_StartP_F2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        label_StartP_F2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        label_BodyCenterP_F2.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        label_BodyCenterP_F2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label_BodyCenterP_F2.setText("aa");
        label_BodyCenterP_F2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        label_BodyCenterP_F2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        F2_Body2.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        F2_Body2.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        F2_Body2.setText("Body CenterP");

        label_TopFinStartP_F2.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        label_TopFinStartP_F2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label_TopFinStartP_F2.setText("aa");
        label_TopFinStartP_F2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        label_TopFinStartP_F2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        F2_TopFin2.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        F2_TopFin2.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        F2_TopFin2.setText("TopFin StartP");

        F2_Head2.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        F2_Head2.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        F2_Head2.setText("FoB CenterP");

        label_FoBCenterP_F2.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        label_FoBCenterP_F2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label_FoBCenterP_F2.setText("aa");
        label_FoBCenterP_F2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        label_FoBCenterP_F2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        F2_BotFin2.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        F2_BotFin2.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        F2_BotFin2.setText("BotFin StartP");

        label_BotFinStartP_F2.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        label_BotFinStartP_F2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label_BotFinStartP_F2.setText("aa");
        label_BotFinStartP_F2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        label_BotFinStartP_F2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        label_TailStartP_F2.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        label_TailStartP_F2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label_TailStartP_F2.setText("aa");
        label_TailStartP_F2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        label_TailStartP_F2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        F2_TailStartP2.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        F2_TailStartP2.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        F2_TailStartP2.setText("Tail StartP");

        javax.swing.GroupLayout Fish2PanelLayout = new javax.swing.GroupLayout(Fish2Panel);
        Fish2Panel.setLayout(Fish2PanelLayout);
        Fish2PanelLayout.setHorizontalGroup(
            Fish2PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Fish2PanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(Fish2PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(Fish2PanelLayout.createSequentialGroup()
                        .addComponent(F2_BotFin2, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(label_BotFinStartP_F2, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(Fish2PanelLayout.createSequentialGroup()
                        .addGroup(Fish2PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(Fish2PanelLayout.createSequentialGroup()
                                .addComponent(F2_StartDraw2, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(label_StartP_F2, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(Fish2PanelLayout.createSequentialGroup()
                                .addComponent(F2_Head2, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(label_FoBCenterP_F2, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(6, 6, 6)))
                .addGroup(Fish2PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(F2_TopFin2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(F2_Body2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(F2_TailStartP2, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addGroup(Fish2PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(label_TailStartP_F2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label_TopFinStartP_F2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label_BodyCenterP_F2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        Fish2PanelLayout.setVerticalGroup(
            Fish2PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Fish2PanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(Fish2PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(label_BodyCenterP_F2)
                    .addComponent(F2_Body2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label_StartP_F2)
                    .addComponent(F2_StartDraw2, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(Fish2PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(F2_Head2)
                    .addGroup(Fish2PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(F2_TopFin2)
                        .addComponent(label_TopFinStartP_F2)
                        .addComponent(label_FoBCenterP_F2)))
                .addGap(18, 18, 18)
                .addGroup(Fish2PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(Fish2PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(F2_BotFin2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(label_BotFinStartP_F2))
                    .addGroup(Fish2PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(label_TailStartP_F2)
                        .addComponent(F2_TailStartP2)))
                .addContainerGap(10, Short.MAX_VALUE))
        );

        VolcanoPanel.setBackground(new java.awt.Color(255, 255, 255));

        F2_StartDraw3.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        F2_StartDraw3.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        F2_StartDraw3.setText("Drawing StartP");

        label_StartP_Volca.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        label_StartP_Volca.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label_StartP_Volca.setText("aa");
        label_StartP_Volca.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        label_StartP_Volca.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        label_EndP_Volca.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        label_EndP_Volca.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label_EndP_Volca.setText("aa");
        label_EndP_Volca.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        label_EndP_Volca.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        F2_Body3.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        F2_Body3.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        F2_Body3.setText("Drawing EndP");

        javax.swing.GroupLayout VolcanoPanelLayout = new javax.swing.GroupLayout(VolcanoPanel);
        VolcanoPanel.setLayout(VolcanoPanelLayout);
        VolcanoPanelLayout.setHorizontalGroup(
            VolcanoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(VolcanoPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(F2_StartDraw3, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(label_StartP_Volca, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(F2_Body3, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(label_EndP_Volca, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        VolcanoPanelLayout.setVerticalGroup(
            VolcanoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(VolcanoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(VolcanoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(label_EndP_Volca)
                    .addComponent(F2_Body3, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label_StartP_Volca)
                    .addComponent(F2_StartDraw3, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        GroundPanel.setBackground(new java.awt.Color(255, 255, 255));

        F2_StartDraw4.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        F2_StartDraw4.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        F2_StartDraw4.setText("Drawing StartP");

        label_StartP_ground.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        label_StartP_ground.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label_StartP_ground.setText("aa");
        label_StartP_ground.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        label_StartP_ground.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout GroundPanelLayout = new javax.swing.GroupLayout(GroundPanel);
        GroundPanel.setLayout(GroundPanelLayout);
        GroundPanelLayout.setHorizontalGroup(
            GroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(GroundPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(F2_StartDraw4, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(label_StartP_ground, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        GroundPanelLayout.setVerticalGroup(
            GroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(GroundPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(GroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(F2_StartDraw4, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label_StartP_ground)))
        );

        panelFish7.setBackground(new java.awt.Color(255, 255, 255));

        F2_StartDraw5.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        F2_StartDraw5.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        F2_StartDraw5.setText("Drawing CenterP");

        label_CenterP_Smoke.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        label_CenterP_Smoke.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label_CenterP_Smoke.setText("aa");
        label_CenterP_Smoke.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        label_CenterP_Smoke.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        label_ScaleLevel_Smoke.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        label_ScaleLevel_Smoke.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label_ScaleLevel_Smoke.setText("aa");
        label_ScaleLevel_Smoke.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        label_ScaleLevel_Smoke.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        F2_Body4.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        F2_Body4.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        F2_Body4.setText("Scale Level");

        javax.swing.GroupLayout panelFish7Layout = new javax.swing.GroupLayout(panelFish7);
        panelFish7.setLayout(panelFish7Layout);
        panelFish7Layout.setHorizontalGroup(
            panelFish7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFish7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(F2_StartDraw5, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(label_CenterP_Smoke, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(F2_Body4, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(label_ScaleLevel_Smoke, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        panelFish7Layout.setVerticalGroup(
            panelFish7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFish7Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelFish7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(label_ScaleLevel_Smoke)
                    .addComponent(F2_Body4, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addGroup(panelFish7Layout.createSequentialGroup()
                .addGroup(panelFish7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(F2_StartDraw5, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label_CenterP_Smoke))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        TreePanel.setBackground(new java.awt.Color(255, 255, 255));

        F2_StartDraw6.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        F2_StartDraw6.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        F2_StartDraw6.setText("Drawing StartP");

        label_StartP_Tree.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        label_StartP_Tree.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label_StartP_Tree.setText("aa");
        label_StartP_Tree.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        label_StartP_Tree.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout TreePanelLayout = new javax.swing.GroupLayout(TreePanel);
        TreePanel.setLayout(TreePanelLayout);
        TreePanelLayout.setHorizontalGroup(
            TreePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TreePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(F2_StartDraw6, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(label_StartP_Tree, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        TreePanelLayout.setVerticalGroup(
            TreePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TreePanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(TreePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(label_StartP_Tree)
                    .addComponent(F2_StartDraw6, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(37, 37, 37))
        );

        SunPanel.setBackground(new java.awt.Color(255, 255, 255));

        F2_StartDraw7.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        F2_StartDraw7.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        F2_StartDraw7.setText("Drawing CenterP");

        label_CenterP_Sun.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        label_CenterP_Sun.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label_CenterP_Sun.setText("aa");
        label_CenterP_Sun.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        label_CenterP_Sun.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        label_Angle_Sun.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        label_Angle_Sun.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label_Angle_Sun.setText("aa");
        label_Angle_Sun.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        label_Angle_Sun.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        F2_Body5.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        F2_Body5.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        F2_Body5.setText("Rotate Angle");

        javax.swing.GroupLayout SunPanelLayout = new javax.swing.GroupLayout(SunPanel);
        SunPanel.setLayout(SunPanelLayout);
        SunPanelLayout.setHorizontalGroup(
            SunPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SunPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(F2_StartDraw7, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(label_CenterP_Sun, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(F2_Body5, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(label_Angle_Sun, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        SunPanelLayout.setVerticalGroup(
            SunPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SunPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(SunPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(label_Angle_Sun)
                    .addComponent(F2_Body5, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label_CenterP_Sun)
                    .addComponent(F2_StartDraw7, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        CloudPanel.setBackground(new java.awt.Color(255, 255, 255));

        F2_StartDraw8.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        F2_StartDraw8.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        F2_StartDraw8.setText("Drawing CP1");

        label_CenterP_C1.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        label_CenterP_C1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label_CenterP_C1.setText("aa");
        label_CenterP_C1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        label_CenterP_C1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        label_CenterP_C2.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        label_CenterP_C2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label_CenterP_C2.setText("aa");
        label_CenterP_C2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        label_CenterP_C2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        F2_Body6.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        F2_Body6.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        F2_Body6.setText("Drawing CP2");

        javax.swing.GroupLayout CloudPanelLayout = new javax.swing.GroupLayout(CloudPanel);
        CloudPanel.setLayout(CloudPanelLayout);
        CloudPanelLayout.setHorizontalGroup(
            CloudPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CloudPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(F2_StartDraw8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(label_CenterP_C1, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(F2_Body6, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(label_CenterP_C2, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        CloudPanelLayout.setVerticalGroup(
            CloudPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CloudPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(CloudPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(label_CenterP_C2)
                    .addComponent(F2_Body6, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label_CenterP_C1)
                    .addComponent(F2_StartDraw8, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        RiverPanel.setBackground(new java.awt.Color(255, 255, 255));

        F2_StartDraw9.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        F2_StartDraw9.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        F2_StartDraw9.setText("Drawing StartP");

        label_StartP_river.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        label_StartP_river.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label_StartP_river.setText("aa");
        label_StartP_river.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        label_StartP_river.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout RiverPanelLayout = new javax.swing.GroupLayout(RiverPanel);
        RiverPanel.setLayout(RiverPanelLayout);
        RiverPanelLayout.setHorizontalGroup(
            RiverPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(RiverPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(F2_StartDraw9, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(label_StartP_river, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        RiverPanelLayout.setVerticalGroup(
            RiverPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(RiverPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(RiverPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(label_StartP_river)
                    .addComponent(F2_StartDraw9, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        SunTitle.setFont(new java.awt.Font("Segoe UI", 2, 18)); // NOI18N
        SunTitle.setForeground(new java.awt.Color(255, 0, 0));
        SunTitle.setText("Sun");

        javax.swing.GroupLayout coordinatePanelLayout = new javax.swing.GroupLayout(coordinatePanel);
        coordinatePanel.setLayout(coordinatePanelLayout);
        coordinatePanelLayout.setHorizontalGroup(
            coordinatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(coordinatePanelLayout.createSequentialGroup()
                .addGroup(coordinatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(coordinatePanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(SunPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(coordinatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(coordinatePanelLayout.createSequentialGroup()
                            .addGap(14, 14, 14)
                            .addComponent(Fish1Title))
                        .addGroup(coordinatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 416, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(CloudPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, coordinatePanelLayout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addGroup(coordinatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(SunTitle)
                                    .addComponent(RiverTitle)))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, coordinatePanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(RiverPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, coordinatePanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(coordinatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(TreePanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(Fish1Panel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(panelFish7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(coordinatePanelLayout.createSequentialGroup()
                                        .addGroup(coordinatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(Fish2Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(coordinatePanelLayout.createSequentialGroup()
                                                .addGap(6, 6, 6)
                                                .addGroup(coordinatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(Fish2Title)
                                                    .addComponent(VolcanoTitle)
                                                    .addComponent(GroundTitle)
                                                    .addComponent(SmokeTitle)
                                                    .addComponent(TreeTitle)))
                                            .addGroup(coordinatePanelLayout.createSequentialGroup()
                                                .addGap(8, 8, 8)
                                                .addComponent(CloudTitle))
                                            .addGroup(coordinatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addComponent(VolcanoPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(GroundPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                        .addGap(0, 0, Short.MAX_VALUE)))))))
                .addGap(12, 12, 12))
        );
        coordinatePanelLayout.setVerticalGroup(
            coordinatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(coordinatePanelLayout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(38, 38, 38)
                .addComponent(Fish1Title)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Fish1Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Fish2Title)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Fish2Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(VolcanoTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(VolcanoPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(GroundTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(GroundPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(SmokeTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelFish7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TreeTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TreePanel, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(SunTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(SunPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(CloudTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(CloudPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(RiverTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(RiverPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(78, 78, 78))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(animationPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 1362, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(coordinatePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(coordinatePanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(animationPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 973, Short.MAX_VALUE)
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
    private javax.swing.JPanel CloudPanel;
    private javax.swing.JLabel CloudTitle;
    private javax.swing.JLabel F2_Body1;
    private javax.swing.JLabel F2_Body2;
    private javax.swing.JLabel F2_Body3;
    private javax.swing.JLabel F2_Body4;
    private javax.swing.JLabel F2_Body5;
    private javax.swing.JLabel F2_Body6;
    private javax.swing.JLabel F2_BotFin1;
    private javax.swing.JLabel F2_BotFin2;
    private javax.swing.JLabel F2_Head1;
    private javax.swing.JLabel F2_Head2;
    private javax.swing.JLabel F2_StartDraw1;
    private javax.swing.JLabel F2_StartDraw2;
    private javax.swing.JLabel F2_StartDraw3;
    private javax.swing.JLabel F2_StartDraw4;
    private javax.swing.JLabel F2_StartDraw5;
    private javax.swing.JLabel F2_StartDraw6;
    private javax.swing.JLabel F2_StartDraw7;
    private javax.swing.JLabel F2_StartDraw8;
    private javax.swing.JLabel F2_StartDraw9;
    private javax.swing.JLabel F2_TailStartP1;
    private javax.swing.JLabel F2_TailStartP2;
    private javax.swing.JLabel F2_TopFin1;
    private javax.swing.JLabel F2_TopFin2;
    private javax.swing.JPanel Fish1Panel;
    private javax.swing.JLabel Fish1Title;
    private javax.swing.JPanel Fish2Panel;
    private javax.swing.JLabel Fish2Title;
    private javax.swing.JPanel GroundPanel;
    private javax.swing.JLabel GroundTitle;
    private javax.swing.JPanel RiverPanel;
    private javax.swing.JLabel RiverTitle;
    private javax.swing.JLabel SmokeTitle;
    private javax.swing.JPanel SunPanel;
    private javax.swing.JLabel SunTitle;
    private javax.swing.JPanel TreePanel;
    private javax.swing.JLabel TreeTitle;
    private javax.swing.JPanel VolcanoPanel;
    private javax.swing.JLabel VolcanoTitle;
    private javax.swing.JPanel animationPanel;
    private javax.swing.JPanel coordinatePanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel label_Angle_Sun;
    private javax.swing.JLabel label_BodyCenterP_F1;
    private javax.swing.JLabel label_BodyCenterP_F2;
    private javax.swing.JLabel label_BotFinStartP_F1;
    private javax.swing.JLabel label_BotFinStartP_F2;
    private javax.swing.JLabel label_CenterP_C1;
    private javax.swing.JLabel label_CenterP_C2;
    private javax.swing.JLabel label_CenterP_Smoke;
    private javax.swing.JLabel label_CenterP_Sun;
    private javax.swing.JLabel label_EndP_Volca;
    private javax.swing.JLabel label_FoBCenterP_F2;
    private javax.swing.JLabel label_HeadCenterP_F1;
    private javax.swing.JLabel label_ScaleLevel_Smoke;
    private javax.swing.JLabel label_StartP_F1;
    private javax.swing.JLabel label_StartP_F2;
    private javax.swing.JLabel label_StartP_Tree;
    private javax.swing.JLabel label_StartP_Volca;
    private javax.swing.JLabel label_StartP_ground;
    private javax.swing.JLabel label_StartP_river;
    private javax.swing.JLabel label_TailECenterP_F1;
    private javax.swing.JLabel label_TailStartP_F2;
    private javax.swing.JLabel label_TopFinStartP_F1;
    private javax.swing.JLabel label_TopFinStartP_F2;
    private javax.swing.JPanel panelFish7;
    // End of variables declaration//GEN-END:variables
}
