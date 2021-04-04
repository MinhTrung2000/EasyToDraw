package main;

import com.sun.corba.se.impl.util.Utility;
import java.awt.Button;
import java.awt.Color;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.util.Pair;
import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JSpinner;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import static main.Settings.*;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Paint extends javax.swing.JFrame {

    // Set 2D as default coordinated system.
    private CoordinateMode coordinateMode = CoordinateMode.MODE_2D;

    // Visual option
    private boolean showGrid_Flag = true;
    private boolean showCoordinate_Flag = false;
    private boolean showStatusBar_Flag = true;

    // Selected objects.
    private Button selectedButton = null;
    private Color selectedColor = Color.BLACK;
    private LineStyle selectedLineStyle = LineStyle.DEFAULT;
    private Integer selectedLineSize = 1;

    // Animation playing flag
    private boolean animationPlaying_Flag = false;

    // Mouse coordinate
    private Point2D previousCoordMouse;
    private Point2D currentCoordMouse;

    private ButtonGroup coordModeButtonGroup = new ButtonGroup();

    private JPopupMenu popMenu_Line = new JPopupMenu();
    private JPopupMenu popMenu_Polygon = new JPopupMenu();
    private JPopupMenu popMenu_Shape = new JPopupMenu();
    private JPopupMenu popMenu_Transform = new JPopupMenu();

    public Paint() {
        customizeComponents();
        setIconFrame();
        setOptionLineStyle();
        setEventHandler();
    }

    /**
     * Method to customize the components.
     */
    private void customizeComponents() {
        initComponents();
        Spinner_SizeLize.setModel(new SpinnerNumberModel(
                MIN_LINE_SIZE,
                MIN_LINE_SIZE,
                MAX_LINE_SIZE,
                STEP_LINE_SIZE)
        );

        // Make a group of 2 button mode 2D and 3D
        coordModeButtonGroup.add(Button_2DMode);
        coordModeButtonGroup.add(Button_3DMode);

        // Create menu pop up for drawing tools.
        popMenu_Line.add(new JMenuItem("Free line"));
        popMenu_Line.add(new JMenuItem("Straight line"));
        
        popMenu_Polygon.add(new JMenuItem("Free polygon"));
        popMenu_Polygon.add(new JMenuItem("Triangle"));
        popMenu_Polygon.add(new JMenuItem("Rectangle"));
        popMenu_Polygon.add(new JMenuItem("Circle"));
        
        popMenu_Shape.add(new JMenuItem("Arrow"));
        popMenu_Shape.add(new JMenuItem("Start"));
        popMenu_Shape.add(new JMenuItem("Heart"));
        
        popMenu_Transform.add(new JMenuItem("Rotation"));
        popMenu_Transform.add(new JMenuItem("Symmetry"));
    }

    /**
     * Set icon for paint frame.
     */
    private void setIconFrame() {
        ImageIcon img = new ImageIcon(getClass().getResource("/img/paintIcon.png"));
        this.setIconImage(img.getImage());
    }

    /**
     * Set options for line mode.
     */
    private void setOptionLineStyle() {
        for (LineStyle lm : LineStyle.values()) {
            comboBox_StyleLine.addItem(lm.toString());
        }
    }

    private void setEventHandler() {
        //======================================================================
        // User control option
        //======================================================================        
        Button_OpenFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                JOptionPane.showMessageDialog(null, "Not support yet!");

                /*
                JFileChooser fileChooser = new JFileChooser();
                FileNameExtensionFilter fileNameFilter = new FileNameExtensionFilter(
                        "PNG Images",
                        "png"
                );
                int returnedValue = fileChooser.showOpenDialog(null);

                if (returnedValue == JFileChooser.APPROVE_OPTION) {
                    //System.out.println("You chose to open this file: " +
                    //chooser.getSelectedFile().getName());
                    try {
                        Board.applyImageInput();
                        
                        // Create a buffer image.
                        BufferedImage myNewPNGFile = ImageIO.read(
                                new File(fileChooser
                                        .getSelectedFile()
                                        .getAbsolutePath()
                                )
                        );
                        
                        
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
                 */
            }
        });

        Button_CreateNewFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                JOptionPane.showMessageDialog(null, "Not support yet!");
            }
        });

        Button_SaveFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                JOptionPane.showMessageDialog(null, "Not support yet!");
            }
        });

        Button_Undo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                JOptionPane.showMessageDialog(null, "Not support yet!");
            }
        });

        Button_Redo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                JOptionPane.showMessageDialog(null, "Not support yet!");
            }
        });

        Button_Helper.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                JOptionPane.showMessageDialog(null, "Not support yet!");
            }
        });

        //======================================================================
        // User visual option
        //======================================================================
        checkBox_showGridlines.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent event) {
                showGrid_Flag = (event.getStateChange() == ItemEvent.SELECTED);
            }
        });

        checkBox_showCoordinate.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent event) {
                showCoordinate_Flag = (event.getStateChange() == ItemEvent.SELECTED);
            }
        });

        checkBox_showStatusBar.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent event) {
                showStatusBar_Flag = (event.getStateChange() == ItemEvent.SELECTED);
            }
        });

        //======================================================================
        // User format option
        //======================================================================
        comboBox_StyleLine.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                int selectedIndex = comboBox_StyleLine.getSelectedIndex();
                for (LineStyle ls : LineStyle.values()) {
                    if (selectedIndex == ls.ordinal()) {
                        selectedLineStyle = ls;
                        break;
                    }
                }
            }
        });

        Spinner_SizeLize.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent event) {
                selectedLineSize = (Integer) ((JSpinner) event.getSource()).getValue();
            }
        });

        //======================================================================
        // User tool option
        //====================================================================== 
        Button_ColorPicker.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                JOptionPane.showMessageDialog(null, "Not support yet!");
            }
        });

        Button_FillColor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                JOptionPane.showMessageDialog(null, "Not support yet!");
            }
        });

        Button_ClearAll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                JOptionPane.showMessageDialog(null, "Not support yet!");
            }
        });

        Button_Eraser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                JOptionPane.showMessageDialog(null, "Not support yet!");
            }
        });

        Button_Select.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                JOptionPane.showMessageDialog(null, "Not support yet!");
            }
        });

        Button_Animation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                JOptionPane.showMessageDialog(null, "Not support yet!");
            }
        });

        //======================================================================
        // User color option
        //====================================================================== 
        Button_ColorChooser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                JOptionPane.showMessageDialog(null, "Not support yet!");
            }
        });

        //======================================================================
        // User drawing option
        //====================================================================== 
        Button_2DMode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                JOptionPane.showMessageDialog(null, "Not support yet!");
            }
        });

        Button_3DMode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                JOptionPane.showMessageDialog(null, "Not support yet!");
            }
        });

        button_Line.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent event) {
                if (SwingUtilities.isLeftMouseButton(event)) {
                    
                } else if (SwingUtilities.isRightMouseButton(event)) {
                    Ultility.showPopMenuOfButton(button_Line, popMenu_Line);
                }
            }
        });
        
        

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Panel_Operation = new javax.swing.JPanel();
        Button_OpenFile = new javax.swing.JButton();
        Button_CreateNewFile = new javax.swing.JButton();
        Button_SaveFile = new javax.swing.JButton();
        Button_Undo = new javax.swing.JButton();
        Button_Redo = new javax.swing.JButton();
        Button_Helper = new javax.swing.JButton();
        Panel_Control = new javax.swing.JPanel();
        Panel_View = new javax.swing.JPanel();
        checkBox_showGridlines = new javax.swing.JCheckBox();
        checkBox_showCoordinate = new javax.swing.JCheckBox();
        checkBox_showStatusBar = new javax.swing.JCheckBox();
        Panel_Format = new javax.swing.JPanel();
        Label_StyleLine = new javax.swing.JLabel();
        comboBox_StyleLine = new javax.swing.JComboBox<>();
        Label_SizeLine = new javax.swing.JLabel();
        Spinner_SizeLize = new javax.swing.JSpinner();
        Label_Pixel = new javax.swing.JLabel();
        Panel_Tool = new javax.swing.JPanel();
        Button_ColorPicker = new javax.swing.JButton();
        Button_FillColor = new javax.swing.JButton();
        Button_ClearAll = new javax.swing.JButton();
        Button_Eraser = new javax.swing.JButton();
        Button_Select = new javax.swing.JButton();
        Button_Animation = new javax.swing.JButton();
        Panel_Color = new javax.swing.JPanel();
        Button_ColorSave_1 = new javax.swing.JButton();
        Button_ColorSave_2 = new javax.swing.JButton();
        Button_ColorSave_3 = new javax.swing.JButton();
        Button_ColorSave_4 = new javax.swing.JButton();
        Button_ColorSave_5 = new javax.swing.JButton();
        Button_ColorSave_6 = new javax.swing.JButton();
        Button_ColorSave_7 = new javax.swing.JButton();
        Button_ColorSave_8 = new javax.swing.JButton();
        Button_ColorChooser = new javax.swing.JButton();
        Panel_Drawing = new javax.swing.JPanel();
        Panel_DrawingTool = new javax.swing.JPanel();
        Panel_SelectCoordinate = new javax.swing.JPanel();
        Button_2DMode = new javax.swing.JRadioButton();
        Button_3DMode = new javax.swing.JRadioButton();
        button_Line = new javax.swing.JButton();
        Button_Transform = new javax.swing.JButton();
        Button_Shape = new javax.swing.JButton();
        Button_Polygon = new javax.swing.JButton();
        Panel_DrawingArea = new javax.swing.JPanel();
        Panel_StatusBar = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("SketchPoint");
        setBackground(new java.awt.Color(12, 240, 240));
        setResizable(false);

        Button_OpenFile.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/openFile.png"))); // NOI18N
        Button_OpenFile.setToolTipText("Open file");

        Button_CreateNewFile.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/createNew.png"))); // NOI18N
        Button_CreateNewFile.setToolTipText("Create a new file");

        Button_SaveFile.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/save.png"))); // NOI18N
        Button_SaveFile.setToolTipText("Save file");

        Button_Undo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/undo.png"))); // NOI18N
        Button_Undo.setToolTipText("Undo");
        Button_Undo.setDisabledIcon(null);
        Button_Undo.setOpaque(false);
        Button_Undo.setRolloverEnabled(false);
        Button_Undo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        Button_Redo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/redo.png"))); // NOI18N
        Button_Redo.setToolTipText("Redo");
        Button_Redo.setBorderPainted(false);
        Button_Redo.setDisabledIcon(null);
        Button_Redo.setOpaque(false);
        Button_Redo.setRolloverEnabled(false);
        Button_Redo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        Button_Helper.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/helper.png"))); // NOI18N
        Button_Helper.setToolTipText("Helper");

        javax.swing.GroupLayout Panel_OperationLayout = new javax.swing.GroupLayout(Panel_Operation);
        Panel_Operation.setLayout(Panel_OperationLayout);
        Panel_OperationLayout.setHorizontalGroup(
            Panel_OperationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_OperationLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(Button_OpenFile)
                .addGap(0, 0, 0)
                .addComponent(Button_CreateNewFile)
                .addGap(0, 0, 0)
                .addComponent(Button_SaveFile)
                .addGap(0, 0, 0)
                .addComponent(Button_Undo)
                .addGap(0, 0, 0)
                .addComponent(Button_Redo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 840, Short.MAX_VALUE)
                .addComponent(Button_Helper, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        Panel_OperationLayout.setVerticalGroup(
            Panel_OperationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_OperationLayout.createSequentialGroup()
                .addGroup(Panel_OperationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Button_OpenFile)
                    .addComponent(Button_CreateNewFile)
                    .addComponent(Button_Undo)
                    .addComponent(Button_Redo)
                    .addComponent(Button_SaveFile))
                .addGap(1, 1, 1))
            .addComponent(Button_Helper, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        Panel_Control.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        Panel_Control.setMinimumSize(new java.awt.Dimension(994, 182));

        Panel_View.setBorder(javax.swing.BorderFactory.createTitledBorder("Visual"));

        checkBox_showGridlines.setText("Show gridlines");
        checkBox_showGridlines.setToolTipText("Whether to show grid");

        checkBox_showCoordinate.setText("Show coordinate");
        checkBox_showCoordinate.setToolTipText("Whether to show coordinate");

        checkBox_showStatusBar.setText("Show status bar");
        checkBox_showStatusBar.setToolTipText("Whether to show status bar below");

        javax.swing.GroupLayout Panel_ViewLayout = new javax.swing.GroupLayout(Panel_View);
        Panel_View.setLayout(Panel_ViewLayout);
        Panel_ViewLayout.setHorizontalGroup(
            Panel_ViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_ViewLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(Panel_ViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(checkBox_showGridlines)
                    .addComponent(checkBox_showCoordinate)
                    .addComponent(checkBox_showStatusBar))
                .addContainerGap())
        );
        Panel_ViewLayout.setVerticalGroup(
            Panel_ViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_ViewLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(checkBox_showGridlines)
                .addGap(18, 18, 18)
                .addComponent(checkBox_showCoordinate)
                .addGap(18, 18, 18)
                .addComponent(checkBox_showStatusBar)
                .addContainerGap())
        );

        Panel_Format.setBorder(javax.swing.BorderFactory.createTitledBorder("Format"));

        Label_StyleLine.setText("Style:");

        comboBox_StyleLine.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        comboBox_StyleLine.setToolTipText("Choose line style");

        Label_SizeLine.setText("Size:");

        Spinner_SizeLize.setToolTipText("Choose line size");

        Label_Pixel.setText("px");

        javax.swing.GroupLayout Panel_FormatLayout = new javax.swing.GroupLayout(Panel_Format);
        Panel_Format.setLayout(Panel_FormatLayout);
        Panel_FormatLayout.setHorizontalGroup(
            Panel_FormatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_FormatLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(Panel_FormatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(Panel_FormatLayout.createSequentialGroup()
                        .addComponent(Label_StyleLine)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(comboBox_StyleLine, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(Panel_FormatLayout.createSequentialGroup()
                        .addComponent(Label_SizeLine)
                        .addGap(18, 18, 18)
                        .addComponent(Spinner_SizeLize, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(Label_Pixel)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        Panel_FormatLayout.setVerticalGroup(
            Panel_FormatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_FormatLayout.createSequentialGroup()
                .addGroup(Panel_FormatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(Panel_FormatLayout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(Label_StyleLine))
                    .addGroup(Panel_FormatLayout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(comboBox_StyleLine, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(32, 32, 32)
                .addGroup(Panel_FormatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Label_SizeLine)
                    .addComponent(Spinner_SizeLize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Label_Pixel))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        Panel_Tool.setBorder(javax.swing.BorderFactory.createTitledBorder("Tool"));

        Button_ColorPicker.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/colorpicker.png"))); // NOI18N
        Button_ColorPicker.setToolTipText("Color picker");

        Button_FillColor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/fillColor.png"))); // NOI18N
        Button_FillColor.setToolTipText("Fill");

        Button_ClearAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/clearAll.png"))); // NOI18N
        Button_ClearAll.setToolTipText("Clear");

        Button_Eraser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/eraser.png"))); // NOI18N
        Button_Eraser.setToolTipText("Eraser");

        Button_Select.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/select.png"))); // NOI18N
        Button_Select.setToolTipText("Select");

        Button_Animation.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/animation.jpg"))); // NOI18N
        Button_Animation.setToolTipText("Animations");

        javax.swing.GroupLayout Panel_ToolLayout = new javax.swing.GroupLayout(Panel_Tool);
        Panel_Tool.setLayout(Panel_ToolLayout);
        Panel_ToolLayout.setHorizontalGroup(
            Panel_ToolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_ToolLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(Panel_ToolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(Panel_ToolLayout.createSequentialGroup()
                        .addComponent(Button_ColorPicker)
                        .addGap(7, 7, 7)
                        .addComponent(Button_FillColor)
                        .addGap(7, 7, 7)
                        .addComponent(Button_ClearAll))
                    .addGroup(Panel_ToolLayout.createSequentialGroup()
                        .addComponent(Button_Eraser)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Button_Select)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Button_Animation)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        Panel_ToolLayout.setVerticalGroup(
            Panel_ToolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_ToolLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(Panel_ToolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Button_ColorPicker)
                    .addComponent(Button_FillColor)
                    .addComponent(Button_ClearAll))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(Panel_ToolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Button_Animation)
                    .addComponent(Button_Select)
                    .addComponent(Button_Eraser))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        Panel_Color.setBorder(javax.swing.BorderFactory.createTitledBorder("Color"));

        Button_ColorSave_1.setBackground(new java.awt.Color(0, 0, 0));
        Button_ColorSave_1.setToolTipText("Recent color");
        Button_ColorSave_1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        Button_ColorSave_1.setContentAreaFilled(false);
        Button_ColorSave_1.setOpaque(true);

        Button_ColorSave_2.setBackground(new java.awt.Color(0, 0, 0));
        Button_ColorSave_2.setToolTipText("Recent color");
        Button_ColorSave_2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        Button_ColorSave_2.setContentAreaFilled(false);
        Button_ColorSave_2.setOpaque(true);

        Button_ColorSave_3.setBackground(new java.awt.Color(0, 0, 0));
        Button_ColorSave_3.setToolTipText("Recent color");
        Button_ColorSave_3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        Button_ColorSave_3.setContentAreaFilled(false);
        Button_ColorSave_3.setOpaque(true);

        Button_ColorSave_4.setBackground(new java.awt.Color(0, 0, 0));
        Button_ColorSave_4.setToolTipText("Recent color");
        Button_ColorSave_4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        Button_ColorSave_4.setContentAreaFilled(false);
        Button_ColorSave_4.setOpaque(true);

        Button_ColorSave_5.setBackground(new java.awt.Color(0, 0, 0));
        Button_ColorSave_5.setToolTipText("Recent color");
        Button_ColorSave_5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        Button_ColorSave_5.setContentAreaFilled(false);
        Button_ColorSave_5.setOpaque(true);

        Button_ColorSave_6.setBackground(new java.awt.Color(0, 0, 0));
        Button_ColorSave_6.setToolTipText("Recent color");
        Button_ColorSave_6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        Button_ColorSave_6.setContentAreaFilled(false);
        Button_ColorSave_6.setOpaque(true);

        Button_ColorSave_7.setBackground(new java.awt.Color(0, 0, 0));
        Button_ColorSave_7.setToolTipText("Recent color");
        Button_ColorSave_7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        Button_ColorSave_7.setContentAreaFilled(false);
        Button_ColorSave_7.setOpaque(true);

        Button_ColorSave_8.setBackground(new java.awt.Color(0, 0, 0));
        Button_ColorSave_8.setToolTipText("Recent color");
        Button_ColorSave_8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        Button_ColorSave_8.setContentAreaFilled(false);
        Button_ColorSave_8.setOpaque(true);

        Button_ColorChooser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/colorChooser.png"))); // NOI18N
        Button_ColorChooser.setText("Edit");
        Button_ColorChooser.setToolTipText("Color chooser");
        Button_ColorChooser.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Button_ColorChooser.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        javax.swing.GroupLayout Panel_ColorLayout = new javax.swing.GroupLayout(Panel_Color);
        Panel_Color.setLayout(Panel_ColorLayout);
        Panel_ColorLayout.setHorizontalGroup(
            Panel_ColorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_ColorLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(Panel_ColorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(Panel_ColorLayout.createSequentialGroup()
                        .addComponent(Button_ColorSave_1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Button_ColorSave_2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Button_ColorSave_3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Button_ColorSave_4, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(Panel_ColorLayout.createSequentialGroup()
                        .addComponent(Button_ColorSave_5, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Button_ColorSave_6, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Button_ColorSave_7, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Button_ColorSave_8, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Button_ColorChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        Panel_ColorLayout.setVerticalGroup(
            Panel_ColorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Panel_ColorLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(Panel_ColorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(Button_ColorChooser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(Panel_ColorLayout.createSequentialGroup()
                        .addGroup(Panel_ColorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Panel_ColorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(Button_ColorSave_1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(Button_ColorSave_2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(Button_ColorSave_3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(Button_ColorSave_4, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(8, 8, 8)
                        .addGroup(Panel_ColorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Button_ColorSave_8, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Button_ColorSave_6, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Button_ColorSave_5, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Button_ColorSave_7, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(78, 78, 78))
        );

        javax.swing.GroupLayout Panel_ControlLayout = new javax.swing.GroupLayout(Panel_Control);
        Panel_Control.setLayout(Panel_ControlLayout);
        Panel_ControlLayout.setHorizontalGroup(
            Panel_ControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_ControlLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Panel_View, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Panel_Format, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Panel_Tool, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(Panel_Color, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        Panel_ControlLayout.setVerticalGroup(
            Panel_ControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_ControlLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(Panel_ControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(Panel_Format, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Panel_Tool, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Panel_View, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Panel_Color, javax.swing.GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        Panel_SelectCoordinate.setBorder(javax.swing.BorderFactory.createTitledBorder("Coordinate"));
        Panel_SelectCoordinate.setToolTipText("Choose drawing coordinate");

        Button_2DMode.setText("2D");

        Button_3DMode.setText("3D");

        javax.swing.GroupLayout Panel_SelectCoordinateLayout = new javax.swing.GroupLayout(Panel_SelectCoordinate);
        Panel_SelectCoordinate.setLayout(Panel_SelectCoordinateLayout);
        Panel_SelectCoordinateLayout.setHorizontalGroup(
            Panel_SelectCoordinateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_SelectCoordinateLayout.createSequentialGroup()
                .addComponent(Button_2DMode)
                .addGap(18, 18, 18)
                .addComponent(Button_3DMode))
        );
        Panel_SelectCoordinateLayout.setVerticalGroup(
            Panel_SelectCoordinateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_SelectCoordinateLayout.createSequentialGroup()
                .addContainerGap(13, Short.MAX_VALUE)
                .addGroup(Panel_SelectCoordinateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Button_2DMode)
                    .addComponent(Button_3DMode))
                .addContainerGap())
        );

        button_Line.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/straightLine.png"))); // NOI18N
        button_Line.setText("Line");
        button_Line.setFocusable(false);
        button_Line.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        button_Line.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        Button_Transform.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/transform.png"))); // NOI18N
        Button_Transform.setText("Transform");
        Button_Transform.setFocusable(false);
        Button_Transform.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Button_Transform.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        Button_Shape.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/star.png"))); // NOI18N
        Button_Shape.setText("Shape");
        Button_Shape.setFocusable(false);
        Button_Shape.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Button_Shape.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        Button_Polygon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/polygon.png"))); // NOI18N
        Button_Polygon.setText("Polygon");
        Button_Polygon.setFocusable(false);
        Button_Polygon.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Button_Polygon.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        javax.swing.GroupLayout Panel_DrawingToolLayout = new javax.swing.GroupLayout(Panel_DrawingTool);
        Panel_DrawingTool.setLayout(Panel_DrawingToolLayout);
        Panel_DrawingToolLayout.setHorizontalGroup(
            Panel_DrawingToolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_DrawingToolLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(Panel_DrawingToolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(Button_Shape, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Panel_SelectCoordinate, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(button_Line, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Button_Polygon, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Button_Transform, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        Panel_DrawingToolLayout.setVerticalGroup(
            Panel_DrawingToolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_DrawingToolLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(Panel_SelectCoordinate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(button_Line, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Button_Polygon, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Button_Shape, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Button_Transform, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        Panel_DrawingArea.setBackground(new java.awt.Color(248, 248, 248));
        Panel_DrawingArea.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        Panel_DrawingArea.setMinimumSize(new java.awt.Dimension(1055, 656));
        Panel_DrawingArea.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                Panel_DrawingAreaMouseMoved(evt);
            }
        });

        javax.swing.GroupLayout Panel_DrawingAreaLayout = new javax.swing.GroupLayout(Panel_DrawingArea);
        Panel_DrawingArea.setLayout(Panel_DrawingAreaLayout);
        Panel_DrawingAreaLayout.setHorizontalGroup(
            Panel_DrawingAreaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1053, Short.MAX_VALUE)
        );
        Panel_DrawingAreaLayout.setVerticalGroup(
            Panel_DrawingAreaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 654, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout Panel_DrawingLayout = new javax.swing.GroupLayout(Panel_Drawing);
        Panel_Drawing.setLayout(Panel_DrawingLayout);
        Panel_DrawingLayout.setHorizontalGroup(
            Panel_DrawingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_DrawingLayout.createSequentialGroup()
                .addComponent(Panel_DrawingTool, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(Panel_DrawingArea, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        Panel_DrawingLayout.setVerticalGroup(
            Panel_DrawingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Panel_DrawingTool, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(Panel_DrawingArea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/moveCursor.png"))); // NOI18N
        jLabel1.setToolTipText("Mouse coord");
        jLabel1.setFocusable(false);
        jLabel1.setInheritsPopupMenu(false);
        jLabel1.setRequestFocusEnabled(false);
        jLabel1.setVerifyInputWhenFocusTarget(false);

        javax.swing.GroupLayout Panel_StatusBarLayout = new javax.swing.GroupLayout(Panel_StatusBar);
        Panel_StatusBar.setLayout(Panel_StatusBarLayout);
        Panel_StatusBarLayout.setHorizontalGroup(
            Panel_StatusBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_StatusBarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        Panel_StatusBarLayout.setVerticalGroup(
            Panel_StatusBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Panel_Operation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(Panel_Control, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(Panel_Drawing, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(Panel_StatusBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(Panel_Operation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(Panel_Control, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Panel_Drawing, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Panel_StatusBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void Panel_DrawingAreaMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Panel_DrawingAreaMouseMoved
        // TODO add your handling code here:

//        Label_XCoord.setText("X: " + ((evt.getX() - (width / 2 - 1)) / (space + size)));
//        Label_YCoord.setText("Y: " + (-(evt.getY() - (height / 2 + 1)) / (space + size)));
    }//GEN-LAST:event_Panel_DrawingAreaMouseMoved

    // Button event handling
    public void actionPerformed(ActionEvent actionEvent) {
        Object affectedComponent = actionEvent.getSource();

        if (!animationPlaying_Flag) {
            // If animation is not played, get affected component and continue.

        } else {
            // Otherwise, force to select animation button to turn off.

        }
    }

    // Mouse event handling
    public class Click implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent event) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void mousePressed(MouseEvent event) {
//            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            if (SwingUtilities.isLeftMouseButton(event)) {

            }
        }

        @Override
        public void mouseReleased(MouseEvent event) {
//            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            if (SwingUtilities.isLeftMouseButton(event)) {

            }
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void mouseExited(MouseEvent e) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

    }

// Move event handling
    public class Move implements MouseMotionListener {

        public void showCoordInformation() {
//            xCoord2D.setText("X: " + (mouseEvent.getX() / rectSize - OX / rectSize));
//            yCoord2D.setText("Y: " + (-(mouseEvent.getY() / rectSize - OY / rectSize)));
        }

        @Override
        public void mouseDragged(MouseEvent event) {
//            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            if (SwingUtilities.isLeftMouseButton(event)) {
                showCoordInformation();
            }
        }

        @Override
        public void mouseMoved(MouseEvent event) {
            showCoordInformation();
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

    }

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
            // Set windows style
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Paint.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Paint.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Paint.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Paint.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Paint().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton Button_2DMode;
    private javax.swing.JRadioButton Button_3DMode;
    private javax.swing.JButton Button_Animation;
    private javax.swing.JButton Button_ClearAll;
    private javax.swing.JButton Button_ColorChooser;
    private javax.swing.JButton Button_ColorPicker;
    private javax.swing.JButton Button_ColorSave_1;
    private javax.swing.JButton Button_ColorSave_2;
    private javax.swing.JButton Button_ColorSave_3;
    private javax.swing.JButton Button_ColorSave_4;
    private javax.swing.JButton Button_ColorSave_5;
    private javax.swing.JButton Button_ColorSave_6;
    private javax.swing.JButton Button_ColorSave_7;
    private javax.swing.JButton Button_ColorSave_8;
    private javax.swing.JButton Button_CreateNewFile;
    private javax.swing.JButton Button_Eraser;
    private javax.swing.JButton Button_FillColor;
    private javax.swing.JButton Button_Helper;
    private javax.swing.JButton Button_OpenFile;
    private javax.swing.JButton Button_Polygon;
    private javax.swing.JButton Button_Redo;
    private javax.swing.JButton Button_SaveFile;
    private javax.swing.JButton Button_Select;
    private javax.swing.JButton Button_Shape;
    private javax.swing.JButton Button_Transform;
    private javax.swing.JButton Button_Undo;
    private javax.swing.JLabel Label_Pixel;
    private javax.swing.JLabel Label_SizeLine;
    private javax.swing.JLabel Label_StyleLine;
    private javax.swing.JPanel Panel_Color;
    private javax.swing.JPanel Panel_Control;
    private javax.swing.JPanel Panel_Drawing;
    private javax.swing.JPanel Panel_DrawingArea;
    private javax.swing.JPanel Panel_DrawingTool;
    private javax.swing.JPanel Panel_Format;
    private javax.swing.JPanel Panel_Operation;
    private javax.swing.JPanel Panel_SelectCoordinate;
    private javax.swing.JPanel Panel_StatusBar;
    private javax.swing.JPanel Panel_Tool;
    private javax.swing.JPanel Panel_View;
    private javax.swing.JSpinner Spinner_SizeLize;
    private javax.swing.JButton button_Line;
    private javax.swing.JCheckBox checkBox_showCoordinate;
    private javax.swing.JCheckBox checkBox_showGridlines;
    private javax.swing.JCheckBox checkBox_showStatusBar;
    private javax.swing.JComboBox<String> comboBox_StyleLine;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}
