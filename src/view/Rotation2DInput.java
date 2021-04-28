/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import control.SettingConstants;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import model.shape2d.Point2D;
import model.tuple.MyPair;

/**
 *
 * @author DELL
 */
public class Rotation2DInput extends javax.swing.JDialog {

    private InputVerifier inputVerifier;

    Point2D acceptCenterPoint = new Point2D();
    double acceptAngle;
//    boolean inputValidFlag = false;

    /**
     * Creates new form Rotation2DInput
     */
    public Rotation2DInput(java.awt.Frame parent) {
        super(parent, true);
        initComponents();

        inputVerifier = new ValidInputCheck();

        textfCenterPointCoordX.setInputVerifier(inputVerifier);
        textfCenterPointCoordY.setInputVerifier(inputVerifier);
        textfAngle.setInputVerifier(inputVerifier);
        
        btnCancel.setInputVerifier(null);
        btnCancel.setRequestFocusEnabled(false);
        
        setModalityType(ModalityType.APPLICATION_MODAL);
        setLocationRelativeTo(parent);
        setTitle("Rotation Transform Input");
        
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        
        btnOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ((MainFrame) getParent()).getDrawingPanel().paintRotation(acceptCenterPoint, acceptAngle);
                dispose();
            }
        });
        
    }

    public Point2D getAcceptCenterPoint() {
        return acceptCenterPoint;
    }

    public double getAcceptAngle() {
        return acceptAngle;
    }

//    public boolean isInputValidFlag() {
//        return inputValidFlag;
//    }

    private class ValidInputCheck extends InputVerifier {

        public boolean checkCoordInBound(int coord, MyPair bound) {
            if (coord < bound.x || coord > bound.y) {
                JOptionPane.showMessageDialog(null, "Error: Coordination out of bound!");
                return false;
            }

            return true;
        }

        @Override
        public boolean verify(JComponent input) {
            try {
                if (input == textfCenterPointCoordX) {
                    String coordXText = textfCenterPointCoordX.getText();

                    if (coordXText.equals("")) {
                        JOptionPane.showMessageDialog(null, "X coordination is required!");
                        return false;
                    }

                    int coordX = Integer.parseInt(coordXText);

                    MyPair xBound = ((MainFrame) getParent()).getDrawingPanel().getXBound();

                    if (!checkCoordInBound(coordX, xBound)) {
                        return false;
                    }

                    // Change visual coord to real machine coord
                    coordX += (int) (SettingConstants.COORD_X_O / SettingConstants.RECT_SIZE);
                    
                    acceptCenterPoint.setCoordX(coordX);
                } else if (input == textfCenterPointCoordY) {
                    String coordYText = textfCenterPointCoordY.getText();

                    if (coordYText.equals("")) {
                        JOptionPane.showMessageDialog(null, "Y coordination is required!");
                        return false;
                    }

                    int coordY = Integer.parseInt(coordYText);

                    MyPair yBound = ((MainFrame) getParent()).getDrawingPanel().getYBound();

                    if (!checkCoordInBound(coordY, yBound)) {
                        return false;
                    }

                    // Change visual coord to real machine coord
                    coordY = (int) (SettingConstants.COORD_Y_O / SettingConstants.RECT_SIZE) - coordY;
                    
                    acceptCenterPoint.setCoordY(coordY);
                } else if (input == textfAngle) {
                    String angText = textfAngle.getText();

                    if (angText.equals("")) {
                        JOptionPane.showMessageDialog(null, "Value of angle of rotation is required!");
                        return false;
                    }

                    int angle = Integer.parseInt(angText);

                    if (angle < 0 || angle > 360) {
                        JOptionPane.showMessageDialog(null, "Error: Value of angle is not in bound!");
                        return false;
                    }

                    acceptAngle = Math.toRadians(angle);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Please enter numeric number!");
                return false;
            }

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

        labelCenterpointRotationINput = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        textfCenterPointCoordX = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        textfCenterPointCoordY = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        textfAngle = new javax.swing.JTextField();
        btnOK = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        labelCenterpointRotationINput.setText("Center-point coordinate:");

        jLabel2.setText("X:");

        jLabel3.setText("Y:");

        jLabel4.setText("Angle (decimal value 0..360):");

        btnOK.setText("OK");

        btnCancel.setText("Cancel");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(labelCenterpointRotationINput)
                            .addGap(27, 27, 27)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addComponent(jLabel2)
                                    .addGap(18, 18, 18)
                                    .addComponent(textfCenterPointCoordX, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addComponent(jLabel3)
                                    .addGap(18, 18, 18)
                                    .addComponent(textfCenterPointCoordY, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel4)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(textfAngle, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(198, 198, 198)
                        .addComponent(btnOK, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(textfCenterPointCoordX, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(textfCenterPointCoordY, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(labelCenterpointRotationINput))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(textfAngle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel4)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancel)
                    .addComponent(btnOK))
                .addContainerGap())
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
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Rotation2DInput.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Rotation2DInput.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Rotation2DInput.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Rotation2DInput.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Rotation2DInput dialog = new Rotation2DInput(new javax.swing.JFrame());
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
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel labelCenterpointRotationINput;
    private javax.swing.JTextField textfAngle;
    private javax.swing.JTextField textfCenterPointCoordX;
    private javax.swing.JTextField textfCenterPointCoordY;
    // End of variables declaration//GEN-END:variables
}
