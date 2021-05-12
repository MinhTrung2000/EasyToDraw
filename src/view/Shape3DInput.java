package view;

import control.SettingConstants;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import control.myawt.SKPoint3D;
import control.util.Ultility;
import javax.swing.JOptionPane;
import javax.swing.text.JTextComponent;

public class Shape3DInput extends javax.swing.JDialog implements ActionListener {

    private GenTextForCubeMode genTextEngine = new GenTextForCubeMode();
    private SKPoint3D accepctedCenterPoint = new SKPoint3D();

    public Shape3DInput(java.awt.Frame parent) {
        super(parent, true);
        initComponents();

        setModalityType(ModalityType.APPLICATION_MODAL);
        setLocationRelativeTo(parent);

        customComponents();
    }

    private void customComponents() {
        btnOptionGroup.add(btnOptionRectangular);
        btnOptionGroup.add(btnOptionCylinder);
        btnOptionGroup.add(btnOptionPyramid);
        btnOptionGroup.add(btnOptionSphere);

        // Default, Rectangular is selected
        btnOptionGroup.setSelected(btnOptionRectangular.getModel(), true);

        btnOptionRectangular.addActionListener(this);
        btnOptionCylinder.addActionListener(this);
        btnOptionPyramid.addActionListener(this);
        btnOptionSphere.addActionListener(this);

        btnRectangularModeGroup.add(rbtRectangularMode);
        btnRectangularModeGroup.add(rbtCubeMode);

        btnRectangularModeGroup.setSelected(rbtRectangularMode.getModel(), true);

        rbtRectangularMode.addActionListener(this);
        rbtCubeMode.addActionListener(this);

        btnCancel.setRequestFocusEnabled(false);
        btnCancel.addActionListener(this);
        btnOK.addActionListener(this);

        for (Component panel : panelCustom.getComponents()) {
            for (Component comp : ((JPanel) panel).getComponents()) {
                if (comp instanceof JTextComponent) {
                    comp.addFocusListener(new FocusAdapter() {
                        @Override
                        public void focusGained(FocusEvent e) {
                            ((JTextComponent) comp).setBorder(UIManager.getLookAndFeel().getDefaults().getBorder("TextField.border"));
                        }
                    });
                }
            }
        }

        textfRectangularCenterPointCoordX.setName("Input x coordinate of center point");
        textfRectangularCenterPointCoordY.setName("Input y coordinate of center point");
        textfRectangularCenterPointCoordZ.setName("Input z coordinate of center point");
        textfRectangularHeight.setName("Input rectangular height");
        textfRectangularWidth.setName("Input rectangular width");
        textfRectangularHigh.setName("Input rectangular high");

        textfCylinderCenterPointCoordX.setName("Input x coordinate of center point");
        textfCylinderCenterPointCoordY.setName("Input y coordinate of center point");
        textfCylinderCenterPointCoordZ.setName("Input z coordinate of center point");
        textfCylinderHigh.setName("Input cylinder high");
        textfCylinderRadius.setName("Input cylinder radius");
        
        textfPyramidCenterPointCoordX.setName("Input x coordinate of center point");
        textfPyramidCenterPointCoordY.setName("Input y coordinate of center point");
        textfPyramidCenterPointCoordZ.setName("Input z coordinate of center point");
        textfPyramidBottomEdge.setName("Input pyramid bottom edge");
        textfPyramidHigh.setName("Input pyramid high");
        
        textfSphereCenterPointCoordX.setName("Input x coordinate of center point");
        textfSphereCenterPointCoordY.setName("Input y coordinate of center point");
        textfSphereCenterPointCoordZ.setName("Input z coordinate of center point");
        textfSphereRadius.setName("Input sphere radius");
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        Object source = evt.getSource();

        // Do nothing if choose the current shape option.
        ButtonModel selectedObject = btnOptionGroup.getSelection();
        if (source instanceof JButton) {
            if (((JButton) source).getModel() == selectedObject) {
                return;
            }
        }

        if (source == rbtRectangularMode) {
            textfRectangularWidth.getDocument().removeDocumentListener(genTextEngine);
            setRectangularMode();
        } else if (source == rbtCubeMode) {
            textfRectangularWidth.getDocument().addDocumentListener(genTextEngine);
            setCubeMode();
        } else if (source == btnOptionRectangular) {
            btnOptionGroup.setSelected(btnOptionRectangular.getModel(), true);
            showCard(panelRectangular.getName());
        } else if (source == btnOptionCylinder) {
            btnOptionGroup.setSelected(btnOptionCylinder.getModel(), true);
            showCard(panelCylinder.getName());
        } else if (source == btnOptionPyramid) {
            btnOptionGroup.setSelected(btnOptionPyramid.getModel(), true);
            showCard(panelPyramid.getName());
        } else if (source == btnOptionSphere) {
            btnOptionGroup.setSelected(btnOptionSphere.getModel(), true);
            showCard(panelSphere.getName());
        } else if (source == btnOK) {
            if (!process()) {
                return;
            }
            dispose();
        } else if (source == btnCancel) {
            dispose();
        }
    }
//s

    /*
    WARNING: Not check for x, y is out of bound.
     */
    private boolean process() {
        if (btnOptionRectangular.isSelected()) {
            try {
                int centerPointX = Ultility.getValidInputComponent(textfRectangularCenterPointCoordX, false, null);
                int centerPointY = Ultility.getValidInputComponent(textfRectangularCenterPointCoordY, false, null);
                int centerPointZ = Ultility.getValidInputComponent(textfRectangularCenterPointCoordZ, false, null);
                int width = Ultility.getValidInputComponent(textfRectangularWidth, false, null);
                int height = Ultility.getValidInputComponent(textfRectangularHeight, false, null);
                int high = Ultility.getValidInputComponent(textfRectangularHigh, false, null);

                ((MainFrame) getParent()).getDrawingPanel().draw3DShapeRectangular(centerPointX, centerPointY, centerPointZ, width, height, high);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
                return false;
            }
        } else if (btnOptionCylinder.isSelected()) {
            try {
                int centerPointX = Ultility.getValidInputComponent(textfCylinderCenterPointCoordX, false, null);
                int centerPointY = Ultility.getValidInputComponent(textfCylinderCenterPointCoordY, false, null);
                int centerPointZ = Ultility.getValidInputComponent(textfCylinderCenterPointCoordZ, false, null);
                int radius = Ultility.getValidInputComponent(textfCylinderRadius, false, null);
                int high = Ultility.getValidInputComponent(textfCylinderHigh, false, null);

                ((MainFrame) getParent()).getDrawingPanel().draw3DShapeCylinder(centerPointX, centerPointY, centerPointZ, radius, high);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
                return false;
            }

        } else if (btnOptionPyramid.isSelected()) {
            try {
                int centerPointX = Ultility.getValidInputComponent(textfPyramidCenterPointCoordX, false, null);
                int centerPointY = Ultility.getValidInputComponent(textfPyramidCenterPointCoordY, false, null);
                int centerPointZ = Ultility.getValidInputComponent(textfPyramidCenterPointCoordZ, false, null);
                int edge = Ultility.getValidInputComponent(textfPyramidBottomEdge, false, null);
                int high = Ultility.getValidInputComponent(textfPyramidHigh, false, null);

                ((MainFrame) getParent()).getDrawingPanel().draw3DShapePyramid(centerPointX, centerPointY, centerPointZ, edge, high);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
                return false;
            }

        } else if (btnOptionSphere.isSelected()) {
            try {
                int centerPointX = Ultility.getValidInputComponent(textfSphereCenterPointCoordX, false, null);
                int centerPointY = Ultility.getValidInputComponent(textfSphereCenterPointCoordY, false, null);
                int centerPointZ = Ultility.getValidInputComponent(textfSphereCenterPointCoordZ, false, null);
                int radius = Ultility.getValidInputComponent(textfSphereRadius, false, null);

                ((MainFrame) getParent()).getDrawingPanel().draw3DShapeSphere(centerPointX, centerPointY, centerPointZ, radius);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
                return false;
            }

        }

        return true;
    }

    private void setRectangularMode() {
        textfRectangularHeight.setEnabled(true);
        textfRectangularHigh.setEnabled(true);
    }

    private void setCubeMode() {
        textfRectangularHeight.setEnabled(false);
        textfRectangularHigh.setEnabled(false);

        textfRectangularHeight.setText(textfRectangularWidth.getText());
        textfRectangularHigh.setText(textfRectangularWidth.getText());
    }

    private void showCard(String cardName) {
        CardLayout layout = ((CardLayout) panelCustom.getLayout());
        layout.show(panelCustom, cardName);
    }

    public void reset() {
        btnOptionGroup.setSelected(btnOptionRectangular.getModel(), true);
        btnRectangularModeGroup.setSelected(rbtRectangularMode.getModel(), true);
    }

    public void setSelectedShape(SettingConstants.DrawingToolMode mode) {
        switch (mode) {
            case DRAWING_3DSHAPE_RECTANGULAR: {
                btnOptionGroup.setSelected(btnOptionRectangular.getModel(), true);
                showCard(panelRectangular.getName());
                break;
            }
            case DRAWING_3DSHAPE_CYLINDER: {
                btnOptionGroup.setSelected(btnOptionCylinder.getModel(), true);
                showCard(panelCylinder.getName());
                break;
            }
            case DRAWING_3DSHAPE_PYRAMID: {
                btnOptionGroup.setSelected(btnOptionPyramid.getModel(), true);
                showCard(panelPyramid.getName());
                break;
            }
            case DRAWING_3DSHAPE_SPHERE: {
                btnOptionGroup.setSelected(btnOptionSphere.getModel(), true);
                showCard(panelSphere.getName());
                break;
            }
        }
    }

    private class GenTextForCubeMode implements DocumentListener {

        @Override
        public void insertUpdate(DocumentEvent e) {
            textfRectangularHeight.setText(textfRectangularWidth.getText());
            textfRectangularHigh.setText(textfRectangularWidth.getText());
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            textfRectangularHeight.setText(textfRectangularWidth.getText());
            textfRectangularHigh.setText(textfRectangularWidth.getText());
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            textfRectangularHeight.setText(textfRectangularWidth.getText());
            textfRectangularHigh.setText(textfRectangularWidth.getText());
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

        btnOptionGroup = new javax.swing.ButtonGroup();
        btnRectangularModeGroup = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        btnOptionRectangular = new javax.swing.JButton();
        btnOptionCylinder = new javax.swing.JButton();
        btnOptionPyramid = new javax.swing.JButton();
        btnOptionSphere = new javax.swing.JButton();
        panelCustom = new javax.swing.JPanel();
        panelRectangular = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        textfRectangularCenterPointCoordX = new javax.swing.JTextField();
        textfRectangularCenterPointCoordY = new javax.swing.JTextField();
        rbtRectangularMode = new javax.swing.JRadioButton();
        rbtCubeMode = new javax.swing.JRadioButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        textfRectangularWidth = new javax.swing.JTextField();
        textfRectangularHeight = new javax.swing.JTextField();
        textfRectangularHigh = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        textfRectangularCenterPointCoordZ = new javax.swing.JTextField();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 30), new java.awt.Dimension(0, 30), new java.awt.Dimension(32767, 30));
        panelCylinder = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        textfCylinderCenterPointCoordX = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        textfCylinderCenterPointCoordY = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        textfCylinderRadius = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        textfCylinderHigh = new javax.swing.JTextField();
        textfCylinderCenterPointCoordZ = new javax.swing.JTextField();
        jLabel34 = new javax.swing.JLabel();
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 30), new java.awt.Dimension(0, 30), new java.awt.Dimension(32767, 30));
        panelPyramid = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        textfPyramidCenterPointCoordX = new javax.swing.JTextField();
        textfPyramidCenterPointCoordY = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        textfPyramidHigh = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        textfPyramidBottomEdge = new javax.swing.JTextField();
        textfPyramidCenterPointCoordZ = new javax.swing.JTextField();
        jLabel35 = new javax.swing.JLabel();
        filler3 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 30), new java.awt.Dimension(0, 30), new java.awt.Dimension(32767, 30));
        panelSphere = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        textfSphereCenterPointCoordX = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        textfSphereCenterPointCoordY = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        textfSphereRadius = new javax.swing.JTextField();
        textfSphereCenterPointCoordZ = new javax.swing.JTextField();
        jLabel36 = new javax.swing.JLabel();
        filler4 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 30), new java.awt.Dimension(0, 30), new java.awt.Dimension(32767, 30));
        btnOK = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Shape 3D Input");
        setResizable(false);

        jLabel1.setText("Choose 3D shape:");

        btnOptionRectangular.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/rectangular24px.png"))); // NOI18N
        btnOptionRectangular.setText("Rectangular");
        btnOptionRectangular.setToolTipText("");
        btnOptionRectangular.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnOptionRectangular.setVerifyInputWhenFocusTarget(false);

        btnOptionCylinder.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/cylinder24px.png"))); // NOI18N
        btnOptionCylinder.setText("Cylinder");
        btnOptionCylinder.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnOptionCylinder.setPreferredSize(new java.awt.Dimension(129, 33));
        btnOptionCylinder.setVerifyInputWhenFocusTarget(false);

        btnOptionPyramid.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/pyramid24px.png"))); // NOI18N
        btnOptionPyramid.setText("Pyramid");
        btnOptionPyramid.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnOptionPyramid.setPreferredSize(new java.awt.Dimension(129, 33));
        btnOptionPyramid.setVerifyInputWhenFocusTarget(false);

        btnOptionSphere.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/sphere24px.png"))); // NOI18N
        btnOptionSphere.setText("Sphere");
        btnOptionSphere.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnOptionSphere.setPreferredSize(new java.awt.Dimension(129, 33));
        btnOptionSphere.setVerifyInputWhenFocusTarget(false);

        panelCustom.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelCustom.setLayout(new java.awt.CardLayout());

        panelRectangular.setName("CardRectangular"); // NOI18N

        jLabel2.setText("Center point location:");

        jLabel3.setText("X:");

        jLabel4.setText("Y:");

        rbtRectangularMode.setText("Rectangular");

        rbtCubeMode.setText("Cube");

        jLabel5.setText("Modal:");

        jLabel6.setText("Edge size:");

        jLabel7.setText("Width:");

        jLabel8.setText("Height:");

        jLabel9.setText("High:");

        jLabel10.setText("Rectangular Shape Custom:");

        jLabel33.setText("Z:");

        javax.swing.GroupLayout panelRectangularLayout = new javax.swing.GroupLayout(panelRectangular);
        panelRectangular.setLayout(panelRectangularLayout);
        panelRectangularLayout.setHorizontalGroup(
            panelRectangularLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRectangularLayout.createSequentialGroup()
                .addGroup(panelRectangularLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelRectangularLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panelRectangularLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelRectangularLayout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addGap(81, 81, 81)
                                .addGroup(panelRectangularLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING))
                                .addGap(18, 18, 18)
                                .addGroup(panelRectangularLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(textfRectangularHeight)
                                    .addComponent(textfRectangularWidth)
                                    .addComponent(textfRectangularHigh, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(panelRectangularLayout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(textfRectangularCenterPointCoordX, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(35, 35, 35)
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(textfRectangularCenterPointCoordY, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(35, 35, 35)
                                .addComponent(jLabel33)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(textfRectangularCenterPointCoordZ, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelRectangularLayout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(28, 28, 28)
                                .addComponent(rbtRectangularMode)
                                .addGap(30, 30, 30)
                                .addComponent(rbtCubeMode))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRectangularLayout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addGap(256, 256, 256))))
                    .addComponent(filler1, javax.swing.GroupLayout.PREFERRED_SIZE, 435, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(104, 104, 104))
        );
        panelRectangularLayout.setVerticalGroup(
            panelRectangularLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRectangularLayout.createSequentialGroup()
                .addComponent(filler1, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(panelRectangularLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbtRectangularMode)
                    .addComponent(rbtCubeMode)
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addGroup(panelRectangularLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelRectangularLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel33)
                        .addComponent(textfRectangularCenterPointCoordZ, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelRectangularLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(jLabel3)
                        .addComponent(jLabel4)
                        .addComponent(textfRectangularCenterPointCoordX, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(textfRectangularCenterPointCoordY, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(panelRectangularLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addGroup(panelRectangularLayout.createSequentialGroup()
                        .addGroup(panelRectangularLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(panelRectangularLayout.createSequentialGroup()
                                .addGroup(panelRectangularLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel7)
                                    .addComponent(textfRectangularWidth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(27, 27, 27)
                                .addComponent(jLabel8))
                            .addComponent(textfRectangularHeight, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(27, 27, 27)
                        .addGroup(panelRectangularLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(textfRectangularHigh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );

        panelCustom.add(panelRectangular, "CardRectangular");

        panelCylinder.setName("CardCylinder"); // NOI18N

        jLabel11.setText("Cylinder Shape Custom:");

        jLabel12.setText("Center point location:");

        jLabel13.setText("X:");

        jLabel14.setText("Y:");

        jLabel15.setText("Radius:");

        jLabel16.setText("R:");

        jLabel17.setText("High:");

        jLabel18.setText("H:");

        jLabel34.setText("Z:");

        javax.swing.GroupLayout panelCylinderLayout = new javax.swing.GroupLayout(panelCylinder);
        panelCylinder.setLayout(panelCylinderLayout);
        panelCylinderLayout.setHorizontalGroup(
            panelCylinderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCylinderLayout.createSequentialGroup()
                .addGroup(panelCylinderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelCylinderLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panelCylinderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panelCylinderLayout.createSequentialGroup()
                                .addGroup(panelCylinderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel12)
                                    .addComponent(jLabel15))
                                .addGap(18, 18, 18)
                                .addGroup(panelCylinderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panelCylinderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(panelCylinderLayout.createSequentialGroup()
                                            .addComponent(jLabel18)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(textfCylinderHigh, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(panelCylinderLayout.createSequentialGroup()
                                            .addComponent(jLabel16)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(textfCylinderRadius, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(panelCylinderLayout.createSequentialGroup()
                                        .addComponent(jLabel13)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(textfCylinderCenterPointCoordX, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(35, 35, 35)
                                        .addComponent(jLabel14)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(textfCylinderCenterPointCoordY, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(35, 35, 35)
                                        .addComponent(jLabel34)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(textfCylinderCenterPointCoordZ, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addComponent(jLabel17)))
                    .addComponent(filler2, javax.swing.GroupLayout.PREFERRED_SIZE, 435, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        panelCylinderLayout.setVerticalGroup(
            panelCylinderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCylinderLayout.createSequentialGroup()
                .addComponent(filler2, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(panelCylinderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelCylinderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel34)
                        .addComponent(textfCylinderCenterPointCoordZ, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelCylinderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel12)
                        .addComponent(jLabel13)
                        .addComponent(jLabel14)
                        .addComponent(textfCylinderCenterPointCoordX, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(textfCylinderCenterPointCoordY, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(panelCylinderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(jLabel16)
                    .addComponent(textfCylinderRadius, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelCylinderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(jLabel18)
                    .addComponent(textfCylinderHigh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(123, Short.MAX_VALUE))
        );

        panelCustom.add(panelCylinder, "CardCylinder");

        panelPyramid.setName("CardPyramid"); // NOI18N

        jLabel19.setText("Pyramid Shape Custom:");

        jLabel20.setText("Center point location:");

        jLabel21.setText("X:");

        jLabel22.setText("Y:");

        jLabel23.setText("High:");

        jLabel24.setText("H:");

        jLabel25.setText("Bottom edge:");

        jLabel26.setText("D:");

        jLabel35.setText("Z:");

        javax.swing.GroupLayout panelPyramidLayout = new javax.swing.GroupLayout(panelPyramid);
        panelPyramid.setLayout(panelPyramidLayout);
        panelPyramidLayout.setHorizontalGroup(
            panelPyramidLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPyramidLayout.createSequentialGroup()
                .addGroup(panelPyramidLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelPyramidLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panelPyramidLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel23)
                            .addGroup(panelPyramidLayout.createSequentialGroup()
                                .addGroup(panelPyramidLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel20)
                                    .addComponent(jLabel25))
                                .addGap(18, 18, 18)
                                .addGroup(panelPyramidLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panelPyramidLayout.createSequentialGroup()
                                        .addComponent(jLabel26)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(textfPyramidBottomEdge, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(panelPyramidLayout.createSequentialGroup()
                                        .addComponent(jLabel21)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(textfPyramidCenterPointCoordX, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(35, 35, 35)
                                        .addComponent(jLabel22)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(textfPyramidCenterPointCoordY, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(35, 35, 35)
                                        .addComponent(jLabel35)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(textfPyramidCenterPointCoordZ, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(panelPyramidLayout.createSequentialGroup()
                                        .addComponent(jLabel24)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(textfPyramidHigh, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                    .addComponent(filler3, javax.swing.GroupLayout.PREFERRED_SIZE, 435, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        panelPyramidLayout.setVerticalGroup(
            panelPyramidLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPyramidLayout.createSequentialGroup()
                .addComponent(filler3, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(panelPyramidLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelPyramidLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel35)
                        .addComponent(textfPyramidCenterPointCoordZ, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelPyramidLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel20)
                        .addComponent(jLabel21)
                        .addComponent(jLabel22)
                        .addComponent(textfPyramidCenterPointCoordX, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(textfPyramidCenterPointCoordY, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(panelPyramidLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25)
                    .addComponent(jLabel26)
                    .addComponent(textfPyramidBottomEdge, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelPyramidLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(jLabel24)
                    .addComponent(textfPyramidHigh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(120, Short.MAX_VALUE))
        );

        panelCustom.add(panelPyramid, "CardPyramid");

        panelSphere.setName("CardSphere"); // NOI18N

        jLabel27.setText("Sphere Shape Custom:");

        jLabel28.setText("Center point location:");

        jLabel29.setText("X:");

        jLabel30.setText("Y:");

        jLabel31.setText("Radius");

        jLabel32.setText("R:");

        jLabel36.setText("Z:");

        javax.swing.GroupLayout panelSphereLayout = new javax.swing.GroupLayout(panelSphere);
        panelSphere.setLayout(panelSphereLayout);
        panelSphereLayout.setHorizontalGroup(
            panelSphereLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSphereLayout.createSequentialGroup()
                .addGroup(panelSphereLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelSphereLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panelSphereLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panelSphereLayout.createSequentialGroup()
                                .addGroup(panelSphereLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel28)
                                    .addComponent(jLabel31))
                                .addGap(18, 18, 18)
                                .addGroup(panelSphereLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panelSphereLayout.createSequentialGroup()
                                        .addComponent(jLabel32)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(textfSphereRadius, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(panelSphereLayout.createSequentialGroup()
                                        .addComponent(jLabel29)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(textfSphereCenterPointCoordX, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(35, 35, 35)
                                        .addComponent(jLabel30)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(textfSphereCenterPointCoordY, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(35, 35, 35)
                                        .addComponent(jLabel36)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(textfSphereCenterPointCoordZ, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                    .addComponent(filler4, javax.swing.GroupLayout.PREFERRED_SIZE, 435, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        panelSphereLayout.setVerticalGroup(
            panelSphereLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSphereLayout.createSequentialGroup()
                .addComponent(filler4, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(panelSphereLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelSphereLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel36)
                        .addComponent(textfSphereCenterPointCoordZ, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelSphereLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel28)
                        .addComponent(jLabel29)
                        .addComponent(jLabel30)
                        .addComponent(textfSphereCenterPointCoordX, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(textfSphereCenterPointCoordY, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(24, 24, 24)
                .addGroup(panelSphereLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel31)
                    .addComponent(jLabel32)
                    .addComponent(textfSphereRadius, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(158, 158, 158))
        );

        panelCustom.add(panelSphere, "CardSphere");

        btnOK.setText("OK");

        btnCancel.setText("Cancel");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnOK, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnOptionCylinder, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnOptionPyramid, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnOptionSphere, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1)
                            .addComponent(btnOptionRectangular, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(panelCustom, javax.swing.GroupLayout.PREFERRED_SIZE, 438, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(btnOptionRectangular)
                        .addGap(18, 18, 18)
                        .addComponent(btnOptionCylinder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnOptionPyramid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnOptionSphere, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(panelCustom, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnOK)
                    .addComponent(btnCancel))
                .addContainerGap())
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
            java.util.logging.Logger.getLogger(Shape3DInput.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Shape3DInput dialog = new Shape3DInput(new javax.swing.JFrame());
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
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnOK;
    private javax.swing.JButton btnOptionCylinder;
    private javax.swing.ButtonGroup btnOptionGroup;
    private javax.swing.JButton btnOptionPyramid;
    private javax.swing.JButton btnOptionRectangular;
    private javax.swing.JButton btnOptionSphere;
    private javax.swing.ButtonGroup btnRectangularModeGroup;
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler filler2;
    private javax.swing.Box.Filler filler3;
    private javax.swing.Box.Filler filler4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel panelCustom;
    private javax.swing.JPanel panelCylinder;
    private javax.swing.JPanel panelPyramid;
    private javax.swing.JPanel panelRectangular;
    private javax.swing.JPanel panelSphere;
    private javax.swing.JRadioButton rbtCubeMode;
    private javax.swing.JRadioButton rbtRectangularMode;
    private javax.swing.JTextField textfCylinderCenterPointCoordX;
    private javax.swing.JTextField textfCylinderCenterPointCoordY;
    private javax.swing.JTextField textfCylinderCenterPointCoordZ;
    private javax.swing.JTextField textfCylinderHigh;
    private javax.swing.JTextField textfCylinderRadius;
    private javax.swing.JTextField textfPyramidBottomEdge;
    private javax.swing.JTextField textfPyramidCenterPointCoordX;
    private javax.swing.JTextField textfPyramidCenterPointCoordY;
    private javax.swing.JTextField textfPyramidCenterPointCoordZ;
    private javax.swing.JTextField textfPyramidHigh;
    private javax.swing.JTextField textfRectangularCenterPointCoordX;
    private javax.swing.JTextField textfRectangularCenterPointCoordY;
    private javax.swing.JTextField textfRectangularCenterPointCoordZ;
    private javax.swing.JTextField textfRectangularHeight;
    private javax.swing.JTextField textfRectangularHigh;
    private javax.swing.JTextField textfRectangularWidth;
    private javax.swing.JTextField textfSphereCenterPointCoordX;
    private javax.swing.JTextField textfSphereCenterPointCoordY;
    private javax.swing.JTextField textfSphereCenterPointCoordZ;
    private javax.swing.JTextField textfSphereRadius;
    // End of variables declaration//GEN-END:variables

}
