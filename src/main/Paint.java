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
import javax.swing.Icon;
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
    private CoordinateMode coordinateMode;
    
    // Visual option
    private boolean showGrid_Flag;
    private boolean showCoordinate_Flag;
    private boolean showStatusBar_Flag;

    // Selected objects.
    private Button selectedButton;
    private Color selectedColor;
    private LineStyle selectedLineStyle;
    private Integer selectedLineSize = 1;

    // Animation playing flag
    private boolean animationPlaying_Flag;

    // Mouse coordinate
    private Point2D previousCoordMouse;
    private Point2D currentCoordMouse;

    private ButtonGroup coordModeButtonGroup;

    private JPopupMenu popMenu_Line;
    private JPopupMenu popMenu_Polygon;
    private JPopupMenu popMenu_Shape;
    private JPopupMenu popMenu_Transform;

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
        spinner_SizeLize.setModel(new SpinnerNumberModel(
                MIN_LINE_SIZE,
                MIN_LINE_SIZE,
                MAX_LINE_SIZE,
                STEP_LINE_SIZE)
        );

        // Make a group of 2 button mode 2D and 3D
        coordModeButtonGroup = new ButtonGroup();
        coordModeButtonGroup.add(Button_2DMode);
        coordModeButtonGroup.add(Button_3DMode);

        // Create menu pop up for drawing tools.
        popMenu_Line = new JPopupMenu();
        popMenu_Polygon = new JPopupMenu();
        popMenu_Shape = new JPopupMenu();
        popMenu_Transform = new JPopupMenu();

        popMenu_Line.add(new JMenuItem(
                LineDrawingToolMode.SEGMENT.toString(),
                new ImageIcon(getClass().getResource("/img/segment24px.png")))
        );
        popMenu_Line.add(new JMenuItem(
                LineDrawingToolMode.STRAIGHT_LINE.toString(),
                new ImageIcon(getClass().getResource("/img/straightLine24px.png")))
        );
        popMenu_Line.add(new JMenuItem(
                LineDrawingToolMode.FREE_LINE.toString(),
                new ImageIcon(getClass().getResource("/img/freeDrawing24px.png")))
        );

        popMenu_Polygon.add(new JMenuItem(
                PolygonDrawingToolMode.FREE_POLYGON.toString(),
                new ImageIcon(getClass().getResource("/img/polygon24px.png")))
        );
        popMenu_Polygon.add(new JMenuItem(
                PolygonDrawingToolMode.TRIANGLE.toString(),
                new ImageIcon(getClass().getResource("/img/triangle24px.png")))
        );
        popMenu_Polygon.add(new JMenuItem(
                PolygonDrawingToolMode.RECTANGLE.toString(),
                new ImageIcon(getClass().getResource("/img/rectangle24px.png")))
        );
        popMenu_Polygon.add(new JMenuItem(
                PolygonDrawingToolMode.CIRCLE.toString(),
                new ImageIcon(getClass().getResource("/img/circle24px.png")))
        );

        popMenu_Shape.add(new JMenuItem(
                ShapeDrawingToolMode.ARROW.toString(),
                new ImageIcon(getClass().getResource("/img/arrow24px.png")))
        );
        popMenu_Shape.add(new JMenuItem(
                ShapeDrawingToolMode.START.toString(),
                new ImageIcon(getClass().getResource("/img/star24px.png")))
        );
        popMenu_Shape.add(new JMenuItem(
                ShapeDrawingToolMode.DIAMOND.toString(),
                new ImageIcon(getClass().getResource("/img/diamond24px.png")))
        );

        popMenu_Transform.add(new JMenuItem(
                TransformDrawingToolMode.ROTATION.toString(),
                new ImageIcon(getClass().getResource("/img/rotation24px.png")))
        );
        popMenu_Transform.add(new JMenuItem(
                TransformDrawingToolMode.SYMMETRY.toString(),
                new ImageIcon(getClass().getResource("/img/symmetry24px.png")))
        );

        // Set default values
        coordinateMode = CoordinateMode.MODE_2D;

        showGrid_Flag = true;
        showCoordinate_Flag = false;
        showStatusBar_Flag = true;

        selectedButton = null;
        selectedColor = Color.BLACK;
        selectedLineStyle = LineStyle.DEFAULT;
        selectedLineSize = 1;
        animationPlaying_Flag = false;
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
        button_OpenFile.addActionListener(new ActionListener() {
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

        button_CreateNewFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                JOptionPane.showMessageDialog(null, "Not support yet!");
            }
        });

        button_SaveFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                JOptionPane.showMessageDialog(null, "Not support yet!");
            }
        });

        button_Undo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                JOptionPane.showMessageDialog(null, "Not support yet!");
            }
        });

        button_Redo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                JOptionPane.showMessageDialog(null, "Not support yet!");
            }
        });

        button_Helper.addActionListener(new ActionListener() {
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

        spinner_SizeLize.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent event) {
                selectedLineSize = (Integer) ((JSpinner) event.getSource()).getValue();
            }
        });

        //======================================================================
        // User tool option
        //====================================================================== 
        button_ColorPicker.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                JOptionPane.showMessageDialog(null, "Not support yet!");
            }
        });

        button_FillColor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                JOptionPane.showMessageDialog(null, "Not support yet!");
            }
        });

        button_ClearAll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                JOptionPane.showMessageDialog(null, "Not support yet!");
            }
        });

        button_Eraser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                JOptionPane.showMessageDialog(null, "Not support yet!");
            }
        });

        button_Select.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                JOptionPane.showMessageDialog(null, "Not support yet!");
            }
        });

        button_Animation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                JOptionPane.showMessageDialog(null, "Not support yet!");
            }
        });

        //======================================================================
        // User color option
        //====================================================================== 
        button_ColorChooser.addActionListener(new ActionListener() {
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

        button_Polygon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent event) {
                if (SwingUtilities.isLeftMouseButton(event)) {

                } else if (SwingUtilities.isRightMouseButton(event)) {
                    Ultility.showPopMenuOfButton(button_Polygon, popMenu_Polygon);
                }
            }
        });

        button_Shape.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent event) {
                if (SwingUtilities.isLeftMouseButton(event)) {

                } else if (SwingUtilities.isRightMouseButton(event)) {
                    Ultility.showPopMenuOfButton(button_Shape, popMenu_Shape);
                }
            }
        });

        button_Transform.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent event) {
                if (SwingUtilities.isLeftMouseButton(event)) {

                } else if (SwingUtilities.isRightMouseButton(event)) {
                    Ultility.showPopMenuOfButton(button_Transform, popMenu_Transform);
                }
            }
        });

        //======================================================================
        //======================================================================
        panel_DrawingArea.addMouseMotionListener(new CustomMouseMotionHandling());
        panel_DrawingArea.addMouseListener(new CustomMouseClickHandling());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel_Operation = new javax.swing.JPanel();
        button_OpenFile = new javax.swing.JButton();
        button_CreateNewFile = new javax.swing.JButton();
        button_SaveFile = new javax.swing.JButton();
        button_Undo = new javax.swing.JButton();
        button_Redo = new javax.swing.JButton();
        button_Helper = new javax.swing.JButton();
        panel_Control = new javax.swing.JPanel();
        panel_View = new javax.swing.JPanel();
        checkBox_showGridlines = new javax.swing.JCheckBox();
        checkBox_showCoordinate = new javax.swing.JCheckBox();
        checkBox_showStatusBar = new javax.swing.JCheckBox();
        panel_Format = new javax.swing.JPanel();
        label_StyleLine = new javax.swing.JLabel();
        comboBox_StyleLine = new javax.swing.JComboBox<>();
        label_SizeLine = new javax.swing.JLabel();
        spinner_SizeLize = new javax.swing.JSpinner();
        label_Pixel = new javax.swing.JLabel();
        panel_Tool = new javax.swing.JPanel();
        button_ColorPicker = new javax.swing.JButton();
        button_FillColor = new javax.swing.JButton();
        button_ClearAll = new javax.swing.JButton();
        button_Eraser = new javax.swing.JButton();
        button_Select = new javax.swing.JButton();
        button_Animation = new javax.swing.JButton();
        panel_Color = new javax.swing.JPanel();
        button_ColorSave_1 = new javax.swing.JButton();
        button_ColorSave_2 = new javax.swing.JButton();
        button_ColorSave_3 = new javax.swing.JButton();
        button_ColorSave_4 = new javax.swing.JButton();
        button_ColorSave_5 = new javax.swing.JButton();
        button_ColorSave_6 = new javax.swing.JButton();
        button_ColorSave_7 = new javax.swing.JButton();
        button_ColorSave_8 = new javax.swing.JButton();
        button_ColorChooser = new javax.swing.JButton();
        panel_Drawing = new javax.swing.JPanel();
        panel_DrawingTool = new javax.swing.JPanel();
        panel_SelectCoordinate = new javax.swing.JPanel();
        Button_2DMode = new javax.swing.JRadioButton();
        Button_3DMode = new javax.swing.JRadioButton();
        button_Line = new javax.swing.JButton();
        button_Transform = new javax.swing.JButton();
        button_Shape = new javax.swing.JButton();
        button_Polygon = new javax.swing.JButton();
        panel_DrawingArea = new javax.swing.JPanel();
        panel_StatusBar = new javax.swing.JPanel();
        label_CoordIcon = new javax.swing.JLabel();
        label_CoordXValue = new javax.swing.JLabel();
        label_CoordYValue = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("SketchPoint");
        setBackground(new java.awt.Color(12, 240, 240));
        setResizable(false);

        button_OpenFile.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/openFile.png"))); // NOI18N
        button_OpenFile.setToolTipText("Open file");

        button_CreateNewFile.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/createNew.png"))); // NOI18N
        button_CreateNewFile.setToolTipText("Create a new file");

        button_SaveFile.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/save.png"))); // NOI18N
        button_SaveFile.setToolTipText("Save file");

        button_Undo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/undo.png"))); // NOI18N
        button_Undo.setToolTipText("Undo");
        button_Undo.setDisabledIcon(null);
        button_Undo.setOpaque(false);
        button_Undo.setRolloverEnabled(false);
        button_Undo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        button_Redo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/redo.png"))); // NOI18N
        button_Redo.setToolTipText("Redo");
        button_Redo.setBorderPainted(false);
        button_Redo.setDisabledIcon(null);
        button_Redo.setOpaque(false);
        button_Redo.setRolloverEnabled(false);
        button_Redo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        button_Helper.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/helper.png"))); // NOI18N
        button_Helper.setToolTipText("Helper");

        javax.swing.GroupLayout panel_OperationLayout = new javax.swing.GroupLayout(panel_Operation);
        panel_Operation.setLayout(panel_OperationLayout);
        panel_OperationLayout.setHorizontalGroup(
            panel_OperationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_OperationLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(button_OpenFile)
                .addGap(0, 0, 0)
                .addComponent(button_CreateNewFile)
                .addGap(0, 0, 0)
                .addComponent(button_SaveFile)
                .addGap(0, 0, 0)
                .addComponent(button_Undo)
                .addGap(0, 0, 0)
                .addComponent(button_Redo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 840, Short.MAX_VALUE)
                .addComponent(button_Helper, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        panel_OperationLayout.setVerticalGroup(
            panel_OperationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_OperationLayout.createSequentialGroup()
                .addGroup(panel_OperationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(button_OpenFile)
                    .addComponent(button_CreateNewFile)
                    .addComponent(button_Undo)
                    .addComponent(button_Redo)
                    .addComponent(button_SaveFile))
                .addGap(1, 1, 1))
            .addComponent(button_Helper, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        panel_Control.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panel_Control.setMinimumSize(new java.awt.Dimension(994, 182));

        panel_View.setBorder(javax.swing.BorderFactory.createTitledBorder("Visual"));

        checkBox_showGridlines.setText("Show gridlines");
        checkBox_showGridlines.setToolTipText("Whether to show grid");

        checkBox_showCoordinate.setText("Show coordinate");
        checkBox_showCoordinate.setToolTipText("Whether to show coordinate");

        checkBox_showStatusBar.setText("Show status bar");
        checkBox_showStatusBar.setToolTipText("Whether to show status bar below");

        javax.swing.GroupLayout panel_ViewLayout = new javax.swing.GroupLayout(panel_View);
        panel_View.setLayout(panel_ViewLayout);
        panel_ViewLayout.setHorizontalGroup(
            panel_ViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_ViewLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_ViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(checkBox_showGridlines)
                    .addComponent(checkBox_showCoordinate)
                    .addComponent(checkBox_showStatusBar))
                .addContainerGap())
        );
        panel_ViewLayout.setVerticalGroup(
            panel_ViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_ViewLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(checkBox_showGridlines)
                .addGap(18, 18, 18)
                .addComponent(checkBox_showCoordinate)
                .addGap(18, 18, 18)
                .addComponent(checkBox_showStatusBar)
                .addContainerGap())
        );

        panel_Format.setBorder(javax.swing.BorderFactory.createTitledBorder("Format"));

        label_StyleLine.setText("Style:");

        comboBox_StyleLine.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        comboBox_StyleLine.setToolTipText("Choose line style");

        label_SizeLine.setText("Size:");

        spinner_SizeLize.setToolTipText("Choose line size");

        label_Pixel.setText("px");

        javax.swing.GroupLayout panel_FormatLayout = new javax.swing.GroupLayout(panel_Format);
        panel_Format.setLayout(panel_FormatLayout);
        panel_FormatLayout.setHorizontalGroup(
            panel_FormatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_FormatLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_FormatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_FormatLayout.createSequentialGroup()
                        .addComponent(label_StyleLine)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(comboBox_StyleLine, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel_FormatLayout.createSequentialGroup()
                        .addComponent(label_SizeLine)
                        .addGap(18, 18, 18)
                        .addComponent(spinner_SizeLize, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(label_Pixel)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel_FormatLayout.setVerticalGroup(
            panel_FormatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_FormatLayout.createSequentialGroup()
                .addGroup(panel_FormatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_FormatLayout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(label_StyleLine))
                    .addGroup(panel_FormatLayout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(comboBox_StyleLine, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(32, 32, 32)
                .addGroup(panel_FormatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(label_SizeLine)
                    .addComponent(spinner_SizeLize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label_Pixel))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panel_Tool.setBorder(javax.swing.BorderFactory.createTitledBorder("Tool"));

        button_ColorPicker.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/colorpicker.png"))); // NOI18N
        button_ColorPicker.setToolTipText("Color picker");

        button_FillColor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/fillColor.png"))); // NOI18N
        button_FillColor.setToolTipText("Fill");

        button_ClearAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/clearAll.png"))); // NOI18N
        button_ClearAll.setToolTipText("Clear");

        button_Eraser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/eraser.png"))); // NOI18N
        button_Eraser.setToolTipText("Eraser");

        button_Select.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/select.png"))); // NOI18N
        button_Select.setToolTipText("Select");

        button_Animation.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/animation.jpg"))); // NOI18N
        button_Animation.setToolTipText("Animations");

        javax.swing.GroupLayout panel_ToolLayout = new javax.swing.GroupLayout(panel_Tool);
        panel_Tool.setLayout(panel_ToolLayout);
        panel_ToolLayout.setHorizontalGroup(
            panel_ToolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_ToolLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(panel_ToolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_ToolLayout.createSequentialGroup()
                        .addComponent(button_ColorPicker)
                        .addGap(7, 7, 7)
                        .addComponent(button_FillColor)
                        .addGap(7, 7, 7)
                        .addComponent(button_ClearAll))
                    .addGroup(panel_ToolLayout.createSequentialGroup()
                        .addComponent(button_Eraser)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(button_Select)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(button_Animation)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel_ToolLayout.setVerticalGroup(
            panel_ToolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_ToolLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_ToolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(button_ColorPicker)
                    .addComponent(button_FillColor)
                    .addComponent(button_ClearAll))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_ToolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(button_Animation)
                    .addComponent(button_Select)
                    .addComponent(button_Eraser))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panel_Color.setBorder(javax.swing.BorderFactory.createTitledBorder("Color"));

        button_ColorSave_1.setBackground(new java.awt.Color(0, 0, 0));
        button_ColorSave_1.setToolTipText("Recent color");
        button_ColorSave_1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        button_ColorSave_1.setContentAreaFilled(false);
        button_ColorSave_1.setOpaque(true);

        button_ColorSave_2.setBackground(new java.awt.Color(0, 0, 0));
        button_ColorSave_2.setToolTipText("Recent color");
        button_ColorSave_2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        button_ColorSave_2.setContentAreaFilled(false);
        button_ColorSave_2.setOpaque(true);

        button_ColorSave_3.setBackground(new java.awt.Color(0, 0, 0));
        button_ColorSave_3.setToolTipText("Recent color");
        button_ColorSave_3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        button_ColorSave_3.setContentAreaFilled(false);
        button_ColorSave_3.setOpaque(true);

        button_ColorSave_4.setBackground(new java.awt.Color(0, 0, 0));
        button_ColorSave_4.setToolTipText("Recent color");
        button_ColorSave_4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        button_ColorSave_4.setContentAreaFilled(false);
        button_ColorSave_4.setOpaque(true);

        button_ColorSave_5.setBackground(new java.awt.Color(0, 0, 0));
        button_ColorSave_5.setToolTipText("Recent color");
        button_ColorSave_5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        button_ColorSave_5.setContentAreaFilled(false);
        button_ColorSave_5.setOpaque(true);

        button_ColorSave_6.setBackground(new java.awt.Color(0, 0, 0));
        button_ColorSave_6.setToolTipText("Recent color");
        button_ColorSave_6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        button_ColorSave_6.setContentAreaFilled(false);
        button_ColorSave_6.setOpaque(true);

        button_ColorSave_7.setBackground(new java.awt.Color(0, 0, 0));
        button_ColorSave_7.setToolTipText("Recent color");
        button_ColorSave_7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        button_ColorSave_7.setContentAreaFilled(false);
        button_ColorSave_7.setOpaque(true);

        button_ColorSave_8.setBackground(new java.awt.Color(0, 0, 0));
        button_ColorSave_8.setToolTipText("Recent color");
        button_ColorSave_8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        button_ColorSave_8.setContentAreaFilled(false);
        button_ColorSave_8.setOpaque(true);

        button_ColorChooser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/colorChooser.png"))); // NOI18N
        button_ColorChooser.setText("Edit");
        button_ColorChooser.setToolTipText("Color chooser");
        button_ColorChooser.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        button_ColorChooser.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        javax.swing.GroupLayout panel_ColorLayout = new javax.swing.GroupLayout(panel_Color);
        panel_Color.setLayout(panel_ColorLayout);
        panel_ColorLayout.setHorizontalGroup(
            panel_ColorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_ColorLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panel_ColorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_ColorLayout.createSequentialGroup()
                        .addComponent(button_ColorSave_1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(button_ColorSave_2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(button_ColorSave_3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(button_ColorSave_4, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel_ColorLayout.createSequentialGroup()
                        .addComponent(button_ColorSave_5, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(button_ColorSave_6, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(button_ColorSave_7, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(button_ColorSave_8, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(button_ColorChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        panel_ColorLayout.setVerticalGroup(
            panel_ColorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_ColorLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panel_ColorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(button_ColorChooser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panel_ColorLayout.createSequentialGroup()
                        .addGroup(panel_ColorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_ColorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(button_ColorSave_1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(button_ColorSave_2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(button_ColorSave_3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(button_ColorSave_4, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(8, 8, 8)
                        .addGroup(panel_ColorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(button_ColorSave_8, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(button_ColorSave_6, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(button_ColorSave_5, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(button_ColorSave_7, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(78, 78, 78))
        );

        javax.swing.GroupLayout panel_ControlLayout = new javax.swing.GroupLayout(panel_Control);
        panel_Control.setLayout(panel_ControlLayout);
        panel_ControlLayout.setHorizontalGroup(
            panel_ControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_ControlLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panel_View, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panel_Format, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panel_Tool, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(panel_Color, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel_ControlLayout.setVerticalGroup(
            panel_ControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_ControlLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_ControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(panel_Format, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panel_Tool, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panel_View, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panel_Color, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panel_SelectCoordinate.setBorder(javax.swing.BorderFactory.createTitledBorder("Coordinate"));
        panel_SelectCoordinate.setToolTipText("Choose drawing coordinate");

        Button_2DMode.setText("2D");

        Button_3DMode.setText("3D");

        javax.swing.GroupLayout panel_SelectCoordinateLayout = new javax.swing.GroupLayout(panel_SelectCoordinate);
        panel_SelectCoordinate.setLayout(panel_SelectCoordinateLayout);
        panel_SelectCoordinateLayout.setHorizontalGroup(
            panel_SelectCoordinateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_SelectCoordinateLayout.createSequentialGroup()
                .addComponent(Button_2DMode)
                .addGap(18, 18, 18)
                .addComponent(Button_3DMode))
        );
        panel_SelectCoordinateLayout.setVerticalGroup(
            panel_SelectCoordinateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_SelectCoordinateLayout.createSequentialGroup()
                .addContainerGap(13, Short.MAX_VALUE)
                .addGroup(panel_SelectCoordinateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Button_2DMode)
                    .addComponent(Button_3DMode))
                .addContainerGap())
        );

        button_Line.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/straightLine32px.png"))); // NOI18N
        button_Line.setText("Line");
        button_Line.setFocusable(false);
        button_Line.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        button_Line.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        button_Transform.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/rotation32px.png"))); // NOI18N
        button_Transform.setText("Transform");
        button_Transform.setFocusable(false);
        button_Transform.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        button_Transform.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        button_Shape.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/star32px.png"))); // NOI18N
        button_Shape.setText("Shape");
        button_Shape.setFocusable(false);
        button_Shape.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        button_Shape.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        button_Polygon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/polygon32px.png"))); // NOI18N
        button_Polygon.setText("Polygon");
        button_Polygon.setFocusable(false);
        button_Polygon.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        button_Polygon.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        javax.swing.GroupLayout panel_DrawingToolLayout = new javax.swing.GroupLayout(panel_DrawingTool);
        panel_DrawingTool.setLayout(panel_DrawingToolLayout);
        panel_DrawingToolLayout.setHorizontalGroup(
            panel_DrawingToolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_DrawingToolLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_DrawingToolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(button_Shape, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panel_SelectCoordinate, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(button_Line, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(button_Polygon, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(button_Transform, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        panel_DrawingToolLayout.setVerticalGroup(
            panel_DrawingToolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_DrawingToolLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(panel_SelectCoordinate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(button_Line, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(button_Polygon, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(button_Shape, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(button_Transform, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panel_DrawingArea.setBackground(new java.awt.Color(248, 248, 248));
        panel_DrawingArea.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panel_DrawingArea.setMinimumSize(new java.awt.Dimension(1055, 656));
        panel_DrawingArea.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                panel_DrawingAreaMouseMoved(evt);
            }
        });

        javax.swing.GroupLayout panel_DrawingAreaLayout = new javax.swing.GroupLayout(panel_DrawingArea);
        panel_DrawingArea.setLayout(panel_DrawingAreaLayout);
        panel_DrawingAreaLayout.setHorizontalGroup(
            panel_DrawingAreaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1053, Short.MAX_VALUE)
        );
        panel_DrawingAreaLayout.setVerticalGroup(
            panel_DrawingAreaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 654, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panel_DrawingLayout = new javax.swing.GroupLayout(panel_Drawing);
        panel_Drawing.setLayout(panel_DrawingLayout);
        panel_DrawingLayout.setHorizontalGroup(
            panel_DrawingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_DrawingLayout.createSequentialGroup()
                .addComponent(panel_DrawingTool, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(panel_DrawingArea, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel_DrawingLayout.setVerticalGroup(
            panel_DrawingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_DrawingTool, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(panel_DrawingArea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        label_CoordIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/moveCursor.png"))); // NOI18N
        label_CoordIcon.setToolTipText("Mouse coord");
        label_CoordIcon.setFocusable(false);
        label_CoordIcon.setInheritsPopupMenu(false);
        label_CoordIcon.setRequestFocusEnabled(false);
        label_CoordIcon.setVerifyInputWhenFocusTarget(false);

        label_CoordXValue.setText("100");

        label_CoordYValue.setText("100");

        javax.swing.GroupLayout panel_StatusBarLayout = new javax.swing.GroupLayout(panel_StatusBar);
        panel_StatusBar.setLayout(panel_StatusBarLayout);
        panel_StatusBarLayout.setHorizontalGroup(
            panel_StatusBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_StatusBarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(label_CoordIcon)
                .addGap(18, 18, 18)
                .addComponent(label_CoordXValue)
                .addGap(30, 30, 30)
                .addComponent(label_CoordYValue)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel_StatusBarLayout.setVerticalGroup(
            panel_StatusBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(label_CoordIcon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_StatusBarLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panel_StatusBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(label_CoordXValue)
                    .addComponent(label_CoordYValue))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_Operation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(panel_Control, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panel_Drawing, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(panel_StatusBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panel_Operation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(panel_Control, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panel_Drawing, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panel_StatusBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void panel_DrawingAreaMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panel_DrawingAreaMouseMoved
        // TODO add your handling code here:

//        Label_XCoord.setText("X: " + ((evt.getX() - (width / 2 - 1)) / (space + size)));
//        Label_YCoord.setText("Y: " + (-(evt.getY() - (height / 2 + 1)) / (space + size)));
    }//GEN-LAST:event_panel_DrawingAreaMouseMoved

    // Button event handling
    public void actionPerformed(ActionEvent actionEvent) {
        Object affectedComponent = actionEvent.getSource();

        if (!animationPlaying_Flag) {
            // If animation is not played, get affected component and continue.

        } else {
            // Otherwise, force to select animation button to turn off.

        }
    }

    public class CustomMouseClickHandling implements MouseListener {

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

    public class CustomMouseMotionHandling implements MouseMotionListener {

        public void showCoordInformation(MouseEvent event) {
            label_CoordXValue.setText(
                    "X: " + (event.getX() / (SIZE + SPACE) - COORD_X_O / (SIZE + SPACE))
            );
            label_CoordYValue.setText(
                    "Y: " + (-(event.getY() / (SIZE + SPACE) - COORD_Y_O / (SIZE + SPACE)))
            );
        }

        @Override
        public void mouseDragged(MouseEvent event) {
            if (SwingUtilities.isLeftMouseButton(event)) {
                showCoordInformation(event);
            }
        }

        @Override
        public void mouseMoved(MouseEvent event) {
            showCoordInformation(event);
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
    private javax.swing.JButton button_Animation;
    private javax.swing.JButton button_ClearAll;
    private javax.swing.JButton button_ColorChooser;
    private javax.swing.JButton button_ColorPicker;
    private javax.swing.JButton button_ColorSave_1;
    private javax.swing.JButton button_ColorSave_2;
    private javax.swing.JButton button_ColorSave_3;
    private javax.swing.JButton button_ColorSave_4;
    private javax.swing.JButton button_ColorSave_5;
    private javax.swing.JButton button_ColorSave_6;
    private javax.swing.JButton button_ColorSave_7;
    private javax.swing.JButton button_ColorSave_8;
    private javax.swing.JButton button_CreateNewFile;
    private javax.swing.JButton button_Eraser;
    private javax.swing.JButton button_FillColor;
    private javax.swing.JButton button_Helper;
    private javax.swing.JButton button_Line;
    private javax.swing.JButton button_OpenFile;
    private javax.swing.JButton button_Polygon;
    private javax.swing.JButton button_Redo;
    private javax.swing.JButton button_SaveFile;
    private javax.swing.JButton button_Select;
    private javax.swing.JButton button_Shape;
    private javax.swing.JButton button_Transform;
    private javax.swing.JButton button_Undo;
    private javax.swing.JCheckBox checkBox_showCoordinate;
    private javax.swing.JCheckBox checkBox_showGridlines;
    private javax.swing.JCheckBox checkBox_showStatusBar;
    private javax.swing.JComboBox<String> comboBox_StyleLine;
    private javax.swing.JLabel label_CoordIcon;
    private javax.swing.JLabel label_CoordXValue;
    private javax.swing.JLabel label_CoordYValue;
    private javax.swing.JLabel label_Pixel;
    private javax.swing.JLabel label_SizeLine;
    private javax.swing.JLabel label_StyleLine;
    private javax.swing.JPanel panel_Color;
    private javax.swing.JPanel panel_Control;
    private javax.swing.JPanel panel_Drawing;
    private javax.swing.JPanel panel_DrawingArea;
    private javax.swing.JPanel panel_DrawingTool;
    private javax.swing.JPanel panel_Format;
    private javax.swing.JPanel panel_Operation;
    private javax.swing.JPanel panel_SelectCoordinate;
    private javax.swing.JPanel panel_StatusBar;
    private javax.swing.JPanel panel_Tool;
    private javax.swing.JPanel panel_View;
    private javax.swing.JSpinner spinner_SizeLize;
    // End of variables declaration//GEN-END:variables
}
