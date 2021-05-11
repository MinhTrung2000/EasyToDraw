package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import control.myawt.SKPoint2D;
import control.util.Ultility;
import java.awt.Component;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.text.JTextComponent;
import model.tuple.MyPair;
import model.tuple.MyTriple;

public class Symmetry2DInput extends javax.swing.JDialog implements ActionListener {

    private SKPoint2D acceptCenterPoint = new SKPoint2D();
    private MyTriple acceptedLineCoeffs = new MyTriple();

    public Symmetry2DInput(java.awt.Frame parent) {
        super(parent, true);
        initComponents();

        setModalityType(ModalityType.APPLICATION_MODAL);
        setLocationRelativeTo(parent);
        setTitle("Symmetry Transform Input");
        setResizable(false);

        customComponents();
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        setPointEnable(false);
        setLineEnable(false);

        Object source = evt.getSource();

        if (source == rbtnOCenterOption || source == rbtnOXOption || source == rbtnOYOption) {
            btnOK.requestFocus();
        } else if (source == rbtnPointOption) {
            setPointEnable(true);
        } else if (source == rbtnLineOption) {
            setLineEnable(true);
        } else if (source == btnCancel) {
            dispose();
        } else if (source == btnOK) {
            if (!process()) {
                return;
            }
            dispose();
        }
    }

    private void customComponents() {
        btnGroupOption.add(rbtnOCenterOption);
        btnGroupOption.add(rbtnOXOption);
        btnGroupOption.add(rbtnOYOption);
        btnGroupOption.add(rbtnPointOption);
        btnGroupOption.add(rbtnLineOption);

        btnGroupOption.setSelected(rbtnOCenterOption.getModel(), true);

        setPointEnable(false);
        setLineEnable(false);

        rbtnOCenterOption.addActionListener(this);
        rbtnOXOption.addActionListener(this);
        rbtnOYOption.addActionListener(this);
        rbtnPointOption.addActionListener(this);
        rbtnLineOption.addActionListener(this);

        btnCancel.setRequestFocusEnabled(false);

        btnCancel.addActionListener(this);
        btnOK.addActionListener(this);

        textfPointCoordX.setName("Input point coordinate x");
        textfPointCoordY.setName("Input point coordinate y");
        textfLineCoeffA.setName("Input line coefficient A");
        textfLineCoeffB.setName("Input line coefficient B");
        textfLineCoeffC.setName("Input line coefficient C");

        for (Component comp : pnInner.getComponents()) {
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

    private void setPointEnable(boolean flag) {
        textfPointCoordX.setEnabled(flag);
        textfPointCoordY.setEnabled(flag);
    }

    private void setLineEnable(boolean flag) {
        textfLineCoeffA.setEnabled(flag);
        textfLineCoeffB.setEnabled(flag);
        textfLineCoeffC.setEnabled(flag);
    }

    private boolean process() {
        if (rbtnOCenterOption.isSelected()) {
            ((MainFrame) getParent()).getDrawingPanel()
                    .paintOCenterSymmetry();
        } else if (rbtnOXOption.isSelected()) {
            ((MainFrame) getParent()).getDrawingPanel()
                    .paintOXSymmetry();
        } else if (rbtnOYOption.isSelected()) {
            ((MainFrame) getParent()).getDrawingPanel()
                    .paintOYSymmetry();
        } else if (rbtnPointOption.isSelected()) {
            try {
                MyPair xBound = ((MainFrame) getParent()).getDrawingPanel()
                        .getXBound();
                acceptCenterPoint.setCoordX(Ultility.getValidInputComponent(
                        textfPointCoordX, false, xBound));

                MyPair yBound = ((MainFrame) getParent()).getDrawingPanel()
                        .getYBound();

                acceptCenterPoint.setCoordY(Ultility.getValidInputComponent(
                        textfPointCoordY, false, yBound));

                ((MainFrame) getParent()).getDrawingPanel()
                        .paintViaPointSymmetry(acceptCenterPoint);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
                return false;
            }
        } else {
            try {
                acceptedLineCoeffs.setX(Ultility.getValidInputComponent(textfLineCoeffA, false, null));
                acceptedLineCoeffs.setY(Ultility.getValidInputComponent(textfLineCoeffB, false, null));
                acceptedLineCoeffs.setZ(Ultility.getValidInputComponent(textfLineCoeffC, false, null));

                ((MainFrame) getParent()).getDrawingPanel()
                        .paintViaLineSymmetry(acceptedLineCoeffs.getX(),
                                acceptedLineCoeffs.getY(),
                                acceptedLineCoeffs.getZ());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
                return false;
            }
        }

        return true;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnGroupOption = new javax.swing.ButtonGroup();
        pnInner = new javax.swing.JPanel();
        labelChooseObjectSymmetry = new javax.swing.JLabel();
        rbtnOCenterOption = new javax.swing.JRadioButton();
        rbtnOXOption = new javax.swing.JRadioButton();
        rbtnOYOption = new javax.swing.JRadioButton();
        rbtnPointOption = new javax.swing.JRadioButton();
        rbtnLineOption = new javax.swing.JRadioButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        textfPointCoordX = new javax.swing.JTextField();
        textfPointCoordY = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        textfLineCoeffA = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        textfLineCoeffB = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        textfLineCoeffC = new javax.swing.JTextField();
        btnOK = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        labelChooseObjectSymmetry.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        labelChooseObjectSymmetry.setText("Choose reflection object:");

        rbtnOCenterOption.setText("O(0, 0)");

        rbtnOXOption.setText("Ox axis");

        rbtnOYOption.setText("Oy axis");

        rbtnPointOption.setText("Point");

        rbtnLineOption.setText("Line Ax + By = C");

        jLabel1.setText("X:");

        jLabel2.setText("Y:");

        jLabel3.setText("A:");

        jLabel4.setText("B:");

        jLabel5.setText("C:");

        btnOK.setText("Ok");

        btnCancel.setText("Cancel");

        javax.swing.GroupLayout pnInnerLayout = new javax.swing.GroupLayout(pnInner);
        pnInner.setLayout(pnInnerLayout);
        pnInnerLayout.setHorizontalGroup(
            pnInnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnInnerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnInnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelChooseObjectSymmetry)
                    .addComponent(rbtnOCenterOption)
                    .addComponent(rbtnOXOption)
                    .addComponent(rbtnOYOption)
                    .addGroup(pnInnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(pnInnerLayout.createSequentialGroup()
                            .addComponent(btnOK, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btnCancel))
                        .addGroup(pnInnerLayout.createSequentialGroup()
                            .addGroup(pnInnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(rbtnPointOption)
                                .addComponent(rbtnLineOption))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(pnInnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(pnInnerLayout.createSequentialGroup()
                                    .addComponent(jLabel3)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(textfLineCoeffA, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(pnInnerLayout.createSequentialGroup()
                                    .addComponent(jLabel1)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(textfPointCoordX, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGap(18, 18, 18)
                            .addGroup(pnInnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(pnInnerLayout.createSequentialGroup()
                                    .addComponent(jLabel2)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(textfPointCoordY, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(pnInnerLayout.createSequentialGroup()
                                    .addComponent(jLabel4)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(textfLineCoeffB, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(jLabel5)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(textfLineCoeffC, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGap(6, 6, 6))))
                .addContainerGap())
        );
        pnInnerLayout.setVerticalGroup(
            pnInnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnInnerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelChooseObjectSymmetry)
                .addGap(18, 18, 18)
                .addComponent(rbtnOCenterOption)
                .addGap(18, 18, 18)
                .addComponent(rbtnOXOption)
                .addGap(18, 18, 18)
                .addComponent(rbtnOYOption)
                .addGap(18, 18, 18)
                .addGroup(pnInnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbtnPointOption)
                    .addGroup(pnInnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(textfPointCoordX, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(textfPointCoordY, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel1)
                        .addComponent(jLabel2)))
                .addGap(18, 18, 18)
                .addGroup(pnInnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbtnLineOption)
                    .addGroup(pnInnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(textfLineCoeffA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel4)
                        .addComponent(textfLineCoeffB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel5)
                        .addComponent(textfLineCoeffC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(pnInnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnOK)
                    .addComponent(btnCancel))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnInner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnInner, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
            java.util.logging.Logger.getLogger(Symmetry2DInput.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Symmetry2DInput dialog = new Symmetry2DInput(new javax.swing.JFrame());
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
    private javax.swing.ButtonGroup btnGroupOption;
    private javax.swing.JButton btnOK;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel labelChooseObjectSymmetry;
    private javax.swing.JPanel pnInner;
    private javax.swing.JRadioButton rbtnLineOption;
    private javax.swing.JRadioButton rbtnOCenterOption;
    private javax.swing.JRadioButton rbtnOXOption;
    private javax.swing.JRadioButton rbtnOYOption;
    private javax.swing.JRadioButton rbtnPointOption;
    private javax.swing.JTextField textfLineCoeffA;
    private javax.swing.JTextField textfLineCoeffB;
    private javax.swing.JTextField textfLineCoeffC;
    private javax.swing.JTextField textfPointCoordX;
    private javax.swing.JTextField textfPointCoordY;
    // End of variables declaration//GEN-END:variables
}
