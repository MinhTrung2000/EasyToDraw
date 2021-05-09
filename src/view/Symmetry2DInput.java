package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import control.myawt.SKPoint2D;
import model.tuple.MyPair;
import model.tuple.MyTriple;

public class Symmetry2DInput extends javax.swing.JDialog implements ActionListener {

    private SKPoint2D acceptedPoint = new SKPoint2D();
    private MyTriple acceptedLineCoeffs = new MyTriple();

    private InputVerifier inputVerifier = new ValidInputCheck();

    public Symmetry2DInput(java.awt.Frame parent) {
        super(parent, true);
        initComponents();

        setModalityType(ModalityType.APPLICATION_MODAL);
        setLocationRelativeTo(parent);
        setTitle("Symmetry Transform Input");

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
        }
    }

    private void customComponents() {
        btnGroupOption.add(rbtnOCenterOption);
        btnGroupOption.add(rbtnOXOption);
        btnGroupOption.add(rbtnOYOption);
        btnGroupOption.add(rbtnPointOption);
        btnGroupOption.add(rbtnLineOption);

        setPointEnable(false);
        setLineEnable(false);

        rbtnOCenterOption.setSelected(true);

        btnOK.requestFocus();

        rbtnOCenterOption.addActionListener(this);
        rbtnOXOption.addActionListener(this);
        rbtnOYOption.addActionListener(this);
        rbtnPointOption.addActionListener(this);
        rbtnLineOption.addActionListener(this);

        textfPointCoordX.setInputVerifier(inputVerifier);
        textfPointCoordY.setInputVerifier(inputVerifier);
        textfLineCoeffA.setInputVerifier(inputVerifier);
        textfLineCoeffB.setInputVerifier(inputVerifier);
        textfLineCoeffC.setInputVerifier(inputVerifier);

        btnCancel.setInputVerifier(null);
        btnCancel.setRequestFocusEnabled(false);

        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        btnOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
                    ((MainFrame) getParent()).getDrawingPanel()
                            .paintViaPointSymmetry(acceptedPoint);
                } else {
                    ((MainFrame) getParent()).getDrawingPanel()
                            .paintViaLineSymmetry(acceptedLineCoeffs.getX(), 
                                    acceptedLineCoeffs.getY(), 
                                    acceptedLineCoeffs.getZ());
                }

                dispose();
            }
        });
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

    private class ValidInputCheck extends InputVerifier {

        public boolean checkCoordInBound(int coord, MyPair bound) {
            if (coord < bound.x || coord > bound.y) {
                JOptionPane.showMessageDialog(null, "Error: Coordinate out of bound!");
                return false;
            }

            return true;
        }

        @Override
        public boolean verify(JComponent input) {
            try {
                if (input == textfPointCoordX) {
                    String coordXText = textfPointCoordX.getText();

                    if (coordXText.equals("")) {
                        JOptionPane.showMessageDialog(null, "X coordinate is required!");
                        return false;
                    }

                    int coordX = Integer.parseInt(coordXText);

                    MyPair xBound = ((MainFrame) getParent()).getDrawingPanel().getXBound();

                    if (!checkCoordInBound(coordX, xBound)) {
                        return false;
                    }

                    acceptedPoint.setCoordX(coordX);
                } else if (input == textfPointCoordY) {
                    String coordYText = textfPointCoordY.getText();

                    if (coordYText.equals("")) {
                        JOptionPane.showMessageDialog(null, "Y coordinate is required!");
                        return false;
                    }

                    int coordY = Integer.parseInt(coordYText);

                    MyPair yBound = ((MainFrame) getParent()).getDrawingPanel().getYBound();

                    if (!checkCoordInBound(coordY, yBound)) {
                        return false;
                    }

                    acceptedPoint.setCoordY(coordY);
                } else if (input == textfLineCoeffA) {
                    String coeffAText = textfLineCoeffA.getText();

                    if (coeffAText.equals("")) {
                        JOptionPane.showMessageDialog(null, "Line coefficient A is required!");
                        return false;
                    }

                    int coeffA = Integer.parseInt(coeffAText);

                    acceptedLineCoeffs.setX(coeffA);
                } else if (input == textfLineCoeffB) {
                    String coeffBText = textfLineCoeffB.getText();

                    if (coeffBText.equals("")) {
                        JOptionPane.showMessageDialog(null, "Line coefficient B is required!");
                        return false;
                    }

                    int coeffB = Integer.parseInt(coeffBText);

                    acceptedLineCoeffs.setY(coeffB);
                } else if (input == textfLineCoeffC) {
                    String coeffCText = textfLineCoeffC.getText();

                    if (coeffCText.equals("")) {
                        JOptionPane.showMessageDialog(null, "Line coefficient A is required!");
                        return false;
                    }

                    int coeffC = Integer.parseInt(coeffCText);

                    acceptedLineCoeffs.setZ(coeffC);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Please enter numeric number!");
                return false;
            }

            btnOK.requestFocus();
            return true;
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

        btnGroupOption = new javax.swing.ButtonGroup();
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rbtnPointOption)
                            .addComponent(labelChooseObjectSymmetry)
                            .addComponent(rbtnOCenterOption)
                            .addComponent(rbtnOXOption)
                            .addComponent(rbtnOYOption)
                            .addComponent(rbtnLineOption))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(textfLineCoeffA, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(textfPointCoordX, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(textfPointCoordY, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(textfLineCoeffB, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(textfLineCoeffC, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnOK, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(btnCancel)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(labelChooseObjectSymmetry)
                .addGap(18, 18, 18)
                .addComponent(rbtnOCenterOption)
                .addGap(18, 18, 18)
                .addComponent(rbtnOXOption)
                .addGap(18, 18, 18)
                .addComponent(rbtnOYOption)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbtnPointOption)
                    .addComponent(textfPointCoordX, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textfPointCoordY, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbtnLineOption)
                    .addComponent(jLabel3)
                    .addComponent(textfLineCoeffA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(textfLineCoeffB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(textfLineCoeffC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnOK)
                    .addComponent(btnCancel))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Symmetry2DInput.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Symmetry2DInput.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Symmetry2DInput.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Symmetry2DInput.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
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
