package main;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JColorChooser;
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

public class Paint extends javax.swing.JFrame {

    /**
     * Show status-bar or not.
     */
    private boolean showStatusBar_Flag;

    private Settings.DrawingToolMode savedLineMode;
    private Settings.DrawingToolMode savedPolygonMode;
    private Settings.DrawingToolMode savedShapeMode;
    private Settings.DrawingToolMode savedTransformMode;

    /**
     * Number of saved color.
     */
    private int savedColorNumber;

    public Paint() {
        customizeComponents();
        setIconFrame();
        setOptionLineStyle();
        setAllEventHandler();

        panel_DrawingArea.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent event) {
                showCursorCoordinate(event);
            }

            @Override
            public void mouseMoved(MouseEvent event) {
                showCursorCoordinate(event);
            }
        });

    }

    public void showCursorCoordinate(MouseEvent event) {
        label_CoordValue.setText(
                "X: " + (event.getX() / Settings.RECT_SIZE - COORD_X_O / Settings.RECT_SIZE) + "   "
                + "Y: " + (-(event.getY() / Settings.RECT_SIZE - COORD_Y_O / Settings.RECT_SIZE))
        );
    }

    /**
     * Method to customize the components.
     */
    private void customizeComponents() {
        initComponents();

        setDefaultVisualOption();
        setDefaultFormatOption();
        setDefaultColorOption();
        setDefaultDrawingToolOption();
    }

    private void setDefaultDrawingToolOption() {
        // Make a group of 2 button mode 2D and 3D
        buttonGroup_CoordMode = new ButtonGroup();
        buttonGroup_CoordMode.add(button_2DMode);
        buttonGroup_CoordMode.add(button_3DMode);

        buttonGroup_CoordMode.setSelected(button_2DMode.getModel(), true);

        // Create menu pop up for drawing tools.
        popMenu_Line = new JPopupMenu();
        popMenu_Polygon = new JPopupMenu();
        popMenu_Shape = new JPopupMenu();
        popMenu_Transform = new JPopupMenu();

        // Segment
        menuItem_Segment = new JMenuItem(
                DrawingToolMode.DRAWING_LINE_SEGMENT.toString(),
                new ImageIcon(getClass().getResource("/img/segment24px.png"))
        );
        popMenu_Line.add(menuItem_Segment);

        // Straight line
        menuItem_Line = new JMenuItem(
                DrawingToolMode.DRAWING_LINE_STRAIGHT.toString(),
                new ImageIcon(getClass().getResource("/img/straightLine24px.png"))
        );
        popMenu_Line.add(menuItem_Line);

        // Free drawing
        menuItem_FreeDrawing = new JMenuItem(
                DrawingToolMode.DRAWING_LINE_FREE.toString(),
                new ImageIcon(getClass().getResource("/img/freeDrawing24px.png"))
        );
        popMenu_Line.add(menuItem_FreeDrawing);

        // Free polygon
        menuItem_FreePolygon = new JMenuItem(
                DrawingToolMode.DRAWING_POLYGON_FREE.toString(),
                new ImageIcon(getClass().getResource("/img/polygon24px.png"))
        );
        popMenu_Polygon.add(menuItem_FreePolygon);

        // Triangle
        menuItem_Triangle = new JMenuItem(
                DrawingToolMode.DRAWING_POLYGON_TRIANGLE.toString(),
                new ImageIcon(getClass().getResource("/img/triangle24px.png"))
        );
        popMenu_Polygon.add(menuItem_Triangle);

        // Rectangle
        menuItem_Rectangle = new JMenuItem(
                DrawingToolMode.DRAWING_POLYGON_RECTANGLE.toString(),
                new ImageIcon(getClass().getResource("/img/rectangle24px.png"))
        );
        popMenu_Polygon.add(menuItem_Rectangle);

        // Circle
        menuItem_Circle = new JMenuItem(
                DrawingToolMode.DRAWING_POLYGON_CIRCLE.toString(),
                new ImageIcon(getClass().getResource("/img/circle24px.png"))
        );
        popMenu_Polygon.add(menuItem_Circle);

        // Star
        menuItem_Star = new JMenuItem(
                DrawingToolMode.DRAWING_SHAPE_STAR.toString(),
                new ImageIcon(getClass().getResource("/img/star24px.png"))
        );
        popMenu_Shape.add(menuItem_Star);

        // Diamond
        menuItem_Diamond = new JMenuItem(
                DrawingToolMode.DRAWING_SHAPE_DIAMOND.toString(),
                new ImageIcon(getClass().getResource("/img/diamond24px.png"))
        );
        popMenu_Shape.add(menuItem_Diamond);

        // Arrow
        menuItem_Arrow = new JMenuItem(
                DrawingToolMode.DRAWING_SHAPE_ARROW.toString(),
                new ImageIcon(getClass().getResource("/img/arrow24px.png"))
        );
        popMenu_Shape.add(menuItem_Arrow);

        // Rotation
        menuItem_Rotation = new JMenuItem(
                DrawingToolMode.DRAWING_TRANSFORM_ROTATION.toString(),
                new ImageIcon(getClass().getResource("/img/rotation24px.png"))
        );
        popMenu_Transform.add(menuItem_Rotation);

        // Symmetry
        menuItem_Symmetry = new JMenuItem(
                DrawingToolMode.DRAWING_TRANSFORM_SYMMETRY.toString(),
                new ImageIcon(getClass().getResource("/img/symmetry24px.png"))
        );
        popMenu_Transform.add(menuItem_Symmetry);

        savedLineMode = Settings.DrawingToolMode.DRAWING_LINE_SEGMENT;
        savedPolygonMode = Settings.DrawingToolMode.DRAWING_POLYGON_FREE;
        savedShapeMode = Settings.DrawingToolMode.DRAWING_SHAPE_STAR;
        savedTransformMode = Settings.DrawingToolMode.DRAWING_TRANSFORM_ROTATION;
    }

    private void setDefaultFormatOption() {
        spinner_SizeLize.setModel(new SpinnerNumberModel(
                MIN_LINE_SIZE,
                MIN_LINE_SIZE,
                MAX_LINE_SIZE,
                STEP_LINE_SIZE)
        );
    }

    private void setDefaultVisualOption() {
        checkBox_showGridlines.setSelected(Settings.DEFAULT_VISUAL_SHOW_GRID);
        checkBox_showCoordinate.setSelected(Settings.DEFAULT_VISUAL_SHOW_COORDINATE);
        checkBox_showStatusBar.setSelected(Settings.DEFAULT_VISUAL_SHOW_STATUSBAR);
        this.showStatusBar_Flag = Settings.DEFAULT_VISUAL_SHOW_STATUSBAR;
    }

    private void setDefaultColorOption() {
        savedColorNumber = 0;
        button_ColorSave_1.setBackground(Settings.DEFAULT_COLOR_SAVE_1);
        button_ColorSave_2.setBackground(Settings.DEFAULT_COLOR_SAVE_2);
        button_ColorSave_3.setBackground(Settings.DEFAULT_COLOR_SAVE_3);
        button_ColorSave_4.setBackground(Settings.DEFAULT_COLOR_SAVE_4);
        button_ColorSave_5.setBackground(Settings.DEFAULT_COLOR_SAVE_5);
        button_ColorSave_6.setBackground(Settings.DEFAULT_COLOR_SAVE_6);
        button_ColorSave_7.setBackground(Settings.DEFAULT_COLOR_SAVE_7);
        button_ColorSave_8.setBackground(Settings.DEFAULT_COLOR_SAVE_8);
    }

    /**
     * The dialog shown for temporarily change. <br>
     * Cases for applying: <br>
     * + Choose between coordinate. <br>
     * + Choose clear board button. <br>
     * + Choose create new file button.
     *
     * @return integer
     */
    public int showConfirmSaveFileDiaglog() {
        return JOptionPane.showConfirmDialog(this, "Save your current file?");
    }

    public void showStatusBar(boolean flag) {
        JOptionPane.showMessageDialog(this, "Not support yet");
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

    /**
     * User control option event handling.
     */
    private void setEventHandlingControlOption() {
        button_OpenFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                JOptionPane.showMessageDialog(null, "Not support yet!");
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
                ((Panel_DrawingArea) panel_DrawingArea).undo();
                button_Undo.setEnabled(((Panel_DrawingArea) panel_DrawingArea).ableUndo());
                button_Redo.setEnabled(((Panel_DrawingArea) panel_DrawingArea).ableRedo());
                repaint();
            }
        });

        button_Redo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                ((Panel_DrawingArea) panel_DrawingArea).redo();
                button_Undo.setEnabled(((Panel_DrawingArea) panel_DrawingArea).ableUndo());
                button_Redo.setEnabled(((Panel_DrawingArea) panel_DrawingArea).ableRedo());
                repaint();
            }
        });

        button_Helper.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                JOptionPane.showMessageDialog(null, "Not support yet!");
            }
        });
    }

    /**
     * User visual option event handling.
     */
    private void setEventHandlingVisualOption() {
        checkBox_showGridlines.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent event) {
                boolean showGrid_Flag = (event.getStateChange() == ItemEvent.SELECTED);
                ((Panel_DrawingArea) panel_DrawingArea).setShowGridLinesFlag(showGrid_Flag);
            }
        });

        checkBox_showCoordinate.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent event) {
                boolean showCoordinate_Flag = (event.getStateChange() == ItemEvent.SELECTED);
                ((Panel_DrawingArea) panel_DrawingArea).setShowCoordinateFlag(showCoordinate_Flag);
            }
        });

        checkBox_showStatusBar.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent event) {
                boolean showStatusBar_Flag = (event.getStateChange() == ItemEvent.SELECTED);
                showStatusBar(showStatusBar_Flag);
            }
        });

    }

    /**
     * User format option event handling.
     */
    private void setEventHandlingFormatOption() {
        comboBox_StyleLine.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                int selectedIndex = comboBox_StyleLine.getSelectedIndex();
                for (LineStyle ls : LineStyle.values()) {
                    if (selectedIndex == ls.ordinal()) {
                        ((Panel_DrawingArea) panel_DrawingArea).setSelectedLineStyle(ls);
                        break;
                    }
                }
            }
        });

        spinner_SizeLize.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent event) {
                int selectedLineSize = (Integer) ((JSpinner) event.getSource()).getValue();
                ((Panel_DrawingArea) panel_DrawingArea).setSelectedLineSize(selectedLineSize);
            }
        });

    }

    /**
     * Set selected tool mode from user.
     *
     * @param toolMode
     */
    private void setSelectedToolMode(Settings.DrawingToolMode toolMode) {
        Settings.DrawingToolMode selectedButtonMode
                = ((Panel_DrawingArea) panel_DrawingArea).getSelectedToolMode();

        if (selectedButtonMode == toolMode) {
            return;
        }

        ((Panel_DrawingArea) panel_DrawingArea).setSelectedButtonMode(toolMode);
    }

    /**
     * User tool option event handling.
     */
    private void setEventHandlingToolOption() {
        button_ColorPicker.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                setSelectedToolMode(Settings.DrawingToolMode.TOOL_COLOR_PICKER);
            }
        });

        button_FillColor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                setSelectedToolMode(Settings.DrawingToolMode.TOOL_COLOR_FILLER);
            }
        });

        button_ClearAll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                setSelectedToolMode(Settings.DrawingToolMode.TOOL_CLEAR_ALL);
            }
        });

        button_Eraser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                setSelectedToolMode(Settings.DrawingToolMode.TOOL_ERASER);
            }
        });

        button_Select.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                setSelectedToolMode(Settings.DrawingToolMode.TOOL_SELECT);
            }
        });

        button_Animation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                setSelectedToolMode(Settings.DrawingToolMode.TOOL_ANIMATION);
            }
        });
    }

    /**
     * User color option event handling.
     */
    private void setEventHandlingColorOption() {
        button_ColorChooser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                Color selectedColor = JColorChooser.showDialog(null, "Choose color", Color.BLACK);

                ((Panel_DrawingArea) panel_DrawingArea).setSelectedColor(selectedColor);

                // Add new saved color if it's not found in list
                if (savedColorNumber < 4) {
                    if (savedColorNumber == 0) {
                        button_ColorSave_5.setBackground(selectedColor);
                    } else if (savedColorNumber == 1) {
                        button_ColorSave_6.setBackground(selectedColor);
                    } else if (savedColorNumber == 2) {
                        button_ColorSave_7.setBackground(selectedColor);
                    } else if (savedColorNumber == 3) {
                        button_ColorSave_8.setBackground(selectedColor);
                    }
                    savedColorNumber++;
                } else {
                    button_ColorSave_5.setBackground(button_ColorSave_6.getBackground());
                    button_ColorSave_6.setBackground(button_ColorSave_7.getBackground());
                    button_ColorSave_7.setBackground(button_ColorSave_8.getBackground());
                    button_ColorSave_8.setBackground(selectedColor);
                }
            }
        });
    }

    /**
     * User drawing option event handling.
     */
    private void setEventHandlingDrawingOption() {
        /* 
        NOTE: PROCESS BUTTON MODE.
        Button coordinate mode khi được chọn sẽ xem xét các yếu tố sau:
            +   Giá trị của cờ show_Gridlines và show_Coordinate là gì?
            +   Mode hiện thời là 2D hay 3D?
        
        Chú ý: Nếu chuyển hệ tọa độ, phải:
            1.  Đặt lại mặc định cho các cờ show_Gridlines, show_Coordinate 
                theo class Settings và checkbox tương ứng.
            2.  Gọi hàm setCoordinateMode của panel_DrawingArea (xem chi tiết
                tại class Panel_DrawingArea).
         */

        button_2DMode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                Settings.CoordinateMode currentDrawingMode
                        = ((Panel_DrawingArea) panel_DrawingArea).getCoordinateMode();

                // If user click the same mode, do nothing
                if (currentDrawingMode == Settings.CoordinateMode.MODE_2D) {
                    return;
                }

                /*
                Change coordinate system to 3D mode.
                 */
                checkBox_showGridlines.setSelected(Settings.DEFAULT_VISUAL_SHOW_GRID);
                checkBox_showCoordinate.setSelected(Settings.DEFAULT_VISUAL_SHOW_COORDINATE);

                ((Panel_DrawingArea) panel_DrawingArea).setCoordinateMode(
                        Settings.CoordinateMode.MODE_2D
                );
            }
        });

        button_3DMode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                Settings.CoordinateMode currentDrawingMode
                        = ((Panel_DrawingArea) panel_DrawingArea).getCoordinateMode();

                // If user click the same mode, do nothing
                if (currentDrawingMode == Settings.CoordinateMode.MODE_3D) {
                    return;
                }

                /*
                Change coordinate system to 2D mode.
                 */
                checkBox_showGridlines.setSelected(Settings.DEFAULT_VISUAL_SHOW_GRID);
                checkBox_showCoordinate.setSelected(Settings.DEFAULT_VISUAL_SHOW_COORDINATE);

                ((Panel_DrawingArea) panel_DrawingArea).setCoordinateMode(
                        Settings.CoordinateMode.MODE_3D
                );
            }
        });

        button_Line.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent event) {
                if (SwingUtilities.isLeftMouseButton(event)) {
                    setSelectedToolMode(savedLineMode);
                } else if (SwingUtilities.isRightMouseButton(event)) {
                    Ultility.showPopMenuOfButton(button_Line, popMenu_Line);
                }
            }
        });

        button_Polygon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent event) {
                if (SwingUtilities.isLeftMouseButton(event)) {
                    setSelectedToolMode(savedPolygonMode);
                } else if (SwingUtilities.isRightMouseButton(event)) {
                    Ultility.showPopMenuOfButton(button_Polygon, popMenu_Polygon);
                }
            }
        });

        button_Shape.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent event) {
                if (SwingUtilities.isLeftMouseButton(event)) {
                    setSelectedToolMode(savedShapeMode);
                } else if (SwingUtilities.isRightMouseButton(event)) {
                    Ultility.showPopMenuOfButton(button_Shape, popMenu_Shape);
                }
            }
        });

        button_Transform.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent event) {
                if (SwingUtilities.isLeftMouseButton(event)) {
                    setSelectedToolMode(savedTransformMode);
                } else if (SwingUtilities.isRightMouseButton(event)) {
                    Ultility.showPopMenuOfButton(button_Transform, popMenu_Transform);
                }
            }
        });

        //======================================================================
        // MenuItem inside popup menu
        //======================================================================
        menuItem_Segment.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                savedLineMode = Settings.DrawingToolMode.DRAWING_LINE_SEGMENT;

                button_Line.setIcon(new ImageIcon(getClass()
                        .getResource("/img/Line_Segment.png"))
                );

                setSelectedToolMode(savedLineMode);
                button_Line.repaint();
            }
        });

        menuItem_Line.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                savedLineMode = Settings.DrawingToolMode.DRAWING_LINE_STRAIGHT;

                button_Line.setIcon(new ImageIcon(getClass()
                        .getResource("/img/Line_StraightLine.png"))
                );

                setSelectedToolMode(savedLineMode);
                button_Line.repaint();
            }
        });

        menuItem_FreeDrawing.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                savedLineMode = Settings.DrawingToolMode.DRAWING_LINE_FREE;

                button_Line.setIcon(new ImageIcon(getClass()
                        .getResource("/img/Line_FreeDrawing.png"))
                );

                setSelectedToolMode(savedLineMode);
                button_Line.repaint();
            }
        });

        menuItem_FreePolygon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                savedPolygonMode = Settings.DrawingToolMode.DRAWING_POLYGON_FREE;

                button_Polygon.setIcon(new ImageIcon(getClass()
                        .getResource("/img/Polygon_Polygon.png"))
                );

                setSelectedToolMode(savedPolygonMode);
                button_Polygon.repaint();
            }
        });

        menuItem_Triangle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                savedPolygonMode = Settings.DrawingToolMode.DRAWING_POLYGON_TRIANGLE;

                button_Polygon.setIcon(new ImageIcon(getClass()
                        .getResource("/img/Polygon_Triangle.png"))
                );

                setSelectedToolMode(savedPolygonMode);
                button_Polygon.repaint();
            }
        });

        menuItem_Rectangle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                savedPolygonMode = Settings.DrawingToolMode.DRAWING_POLYGON_RECTANGLE;

                button_Polygon.setIcon(new ImageIcon(getClass()
                        .getResource("/img/Poligon_Rectangle.png"))
                );

                setSelectedToolMode(savedPolygonMode);
                button_Polygon.repaint();
            }
        });

        menuItem_Circle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                savedPolygonMode = Settings.DrawingToolMode.DRAWING_POLYGON_CIRCLE;

                button_Polygon.setIcon(new ImageIcon(getClass()
                        .getResource("/img/Polygon_Circle.png"))
                );

                setSelectedToolMode(savedPolygonMode);
                button_Polygon.repaint();
            }
        });

        menuItem_Star.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                savedShapeMode = Settings.DrawingToolMode.DRAWING_SHAPE_STAR;

                button_Shape.setIcon(new ImageIcon(getClass()
                        .getResource("/img/Shape_Star.png"))
                );

                setSelectedToolMode(savedShapeMode);
                button_Shape.repaint();
            }
        });

        menuItem_Diamond.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                savedShapeMode = Settings.DrawingToolMode.DRAWING_SHAPE_DIAMOND;

                button_Shape.setIcon(new ImageIcon(getClass()
                        .getResource("/img/Shape_Diamond.png"))
                );

                setSelectedToolMode(savedShapeMode);
                button_Shape.repaint();
            }
        });

        menuItem_Arrow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                savedShapeMode = Settings.DrawingToolMode.DRAWING_SHAPE_ARROW;

                button_Shape.setIcon(new ImageIcon(getClass()
                        .getResource("/img/Shape_Arrow.png"))
                );

                setSelectedToolMode(savedShapeMode);
                button_Shape.repaint();
            }
        });

        menuItem_Rotation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                savedTransformMode = Settings.DrawingToolMode.DRAWING_TRANSFORM_ROTATION;

                button_Transform.setIcon(new ImageIcon(getClass()
                        .getResource("/img/Transform_Rotation.png"))
                );

                setSelectedToolMode(savedTransformMode);
                button_Transform.repaint();
            }
        });

        menuItem_Symmetry.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                savedTransformMode = Settings.DrawingToolMode.DRAWING_TRANSFORM_SYMMETRY;

                button_Transform.setIcon(new ImageIcon(getClass()
                        .getResource("/img/Transform_Symmetry.png"))
                );

                setSelectedToolMode(savedTransformMode);
                button_Transform.repaint();
            }
        });

    }

    private void setAllEventHandler() {
        setEventHandlingControlOption();
        setEventHandlingVisualOption();
        setEventHandlingFormatOption();
        setEventHandlingToolOption();
        setEventHandlingColorOption();
        setEventHandlingDrawingOption();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        buttonGroup_CoordMode = new javax.swing.ButtonGroup();
        popMenu_Line = new javax.swing.JPopupMenu();
        menuItem_Segment = new javax.swing.JMenuItem();
        menuItem_Line = new javax.swing.JMenuItem();
        menuItem_FreeDrawing = new javax.swing.JMenuItem();
        popMenu_Polygon = new javax.swing.JPopupMenu();
        menuItem_FreePolygon = new javax.swing.JMenuItem();
        menuItem_Triangle = new javax.swing.JMenuItem();
        menuItem_Rectangle = new javax.swing.JMenuItem();
        menuItem_Circle = new javax.swing.JMenuItem();
        popMenu_Shape = new javax.swing.JPopupMenu();
        menuItem_Star = new javax.swing.JMenuItem();
        menuItem_Diamond = new javax.swing.JMenuItem();
        menuItem_Arrow = new javax.swing.JMenuItem();
        popMenu_Transform = new javax.swing.JPopupMenu();
        menuItem_Rotation = new javax.swing.JMenuItem();
        menuItem_Symmetry = new javax.swing.JMenuItem();
        panel_Operation = new javax.swing.JPanel();
        button_OpenFile = new javax.swing.JButton();
        jSeparator4 = new javax.swing.JSeparator();
        button_CreateNewFile = new javax.swing.JButton();
        jSeparator5 = new javax.swing.JSeparator();
        button_SaveFile = new javax.swing.JButton();
        jSeparator6 = new javax.swing.JSeparator();
        button_Undo = new javax.swing.JButton();
        jSeparator7 = new javax.swing.JSeparator();
        button_Redo = new javax.swing.JButton();
        button_Helper = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
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
        jSeparator2 = new javax.swing.JSeparator();
        panel_Drawing = new javax.swing.JPanel();
        panel_DrawingTool = new javax.swing.JPanel();
        panel_SelectCoordinate = new javax.swing.JPanel();
        button_2DMode = new javax.swing.JRadioButton();
        button_3DMode = new javax.swing.JRadioButton();
        button_Line = new javax.swing.JButton();
        button_Polygon = new javax.swing.JButton();
        button_Shape = new javax.swing.JButton();
        button_Transform = new javax.swing.JButton();
        Seperator = new javax.swing.JSeparator();
        panel_DrawingArea = new Panel_DrawingArea();
        jSeparator3 = new javax.swing.JSeparator();
        panel_StatusBar = new javax.swing.JPanel();
        panel_CoordinateCursor = new javax.swing.JPanel();
        label_CoordIcon = new javax.swing.JLabel();
        label_CoordValue = new javax.swing.JLabel();
        jSeparator8 = new javax.swing.JSeparator();
        label_ToolTip = new javax.swing.JLabel();

        menuItem_Segment.setText("jMenuItem1");
        popMenu_Line.add(menuItem_Segment);

        menuItem_Line.setText("jMenuItem1");
        popMenu_Line.add(menuItem_Line);

        menuItem_FreeDrawing.setText("jMenuItem1");
        popMenu_Line.add(menuItem_FreeDrawing);

        menuItem_FreePolygon.setText("jMenuItem1");
        popMenu_Polygon.add(menuItem_FreePolygon);

        menuItem_Triangle.setText("jMenuItem1");
        popMenu_Polygon.add(menuItem_Triangle);

        menuItem_Rectangle.setText("jMenuItem1");
        popMenu_Polygon.add(menuItem_Rectangle);

        menuItem_Circle.setText("jMenuItem1");
        popMenu_Polygon.add(menuItem_Circle);

        menuItem_Star.setText("jMenuItem1");
        popMenu_Shape.add(menuItem_Star);

        menuItem_Diamond.setText("jMenuItem1");
        popMenu_Shape.add(menuItem_Diamond);

        menuItem_Arrow.setText("jMenuItem1");
        popMenu_Shape.add(menuItem_Arrow);

        menuItem_Rotation.setText("jMenuItem1");
        popMenu_Transform.add(menuItem_Rotation);

        menuItem_Symmetry.setText("jMenuItem1");
        popMenu_Transform.add(menuItem_Symmetry);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("SketchPoint");
        setBackground(new java.awt.Color(12, 240, 240));
        setFocusable(false);
        setResizable(false);

        button_OpenFile.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/openFile.png"))); // NOI18N
        button_OpenFile.setToolTipText("Open file");
        button_OpenFile.setContentAreaFilled(false);
        button_OpenFile.setEnabled(false);
        button_OpenFile.setFocusable(false);
        button_OpenFile.setRequestFocusEnabled(false);
        button_OpenFile.setRolloverEnabled(false);

        jSeparator4.setOrientation(javax.swing.SwingConstants.VERTICAL);

        button_CreateNewFile.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/createNew.png"))); // NOI18N
        button_CreateNewFile.setToolTipText("Create a new file");
        button_CreateNewFile.setContentAreaFilled(false);
        button_CreateNewFile.setEnabled(false);
        button_CreateNewFile.setFocusable(false);
        button_CreateNewFile.setRequestFocusEnabled(false);
        button_CreateNewFile.setRolloverEnabled(false);

        jSeparator5.setOrientation(javax.swing.SwingConstants.VERTICAL);

        button_SaveFile.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/save.png"))); // NOI18N
        button_SaveFile.setToolTipText("Save file");
        button_SaveFile.setContentAreaFilled(false);
        button_SaveFile.setEnabled(false);
        button_SaveFile.setFocusable(false);
        button_SaveFile.setRequestFocusEnabled(false);
        button_SaveFile.setRolloverEnabled(false);

        jSeparator6.setOrientation(javax.swing.SwingConstants.VERTICAL);

        button_Undo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/undo.png"))); // NOI18N
        button_Undo.setToolTipText("Undo");
        button_Undo.setContentAreaFilled(false);
        button_Undo.setDisabledIcon(null);
        button_Undo.setEnabled(false);
        button_Undo.setFocusable(false);
        button_Undo.setOpaque(false);
        button_Undo.setRequestFocusEnabled(false);
        button_Undo.setRolloverEnabled(false);
        button_Undo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        jSeparator7.setOrientation(javax.swing.SwingConstants.VERTICAL);

        button_Redo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/redo.png"))); // NOI18N
        button_Redo.setToolTipText("Redo");
        button_Redo.setBorderPainted(false);
        button_Redo.setContentAreaFilled(false);
        button_Redo.setDisabledIcon(null);
        button_Redo.setEnabled(false);
        button_Redo.setFocusable(false);
        button_Redo.setOpaque(false);
        button_Redo.setRequestFocusEnabled(false);
        button_Redo.setRolloverEnabled(false);
        button_Redo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        button_Helper.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/helper.png"))); // NOI18N
        button_Helper.setToolTipText("Helper");
        button_Helper.setBorder(null);
        button_Helper.setContentAreaFilled(false);
        button_Helper.setEnabled(false);
        button_Helper.setFocusPainted(false);
        button_Helper.setFocusable(false);
        button_Helper.setOpaque(false);
        button_Helper.setRequestFocusEnabled(false);
        button_Helper.setRolloverEnabled(false);

        javax.swing.GroupLayout panel_OperationLayout = new javax.swing.GroupLayout(panel_Operation);
        panel_Operation.setLayout(panel_OperationLayout);
        panel_OperationLayout.setHorizontalGroup(
            panel_OperationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_OperationLayout.createSequentialGroup()
                .addComponent(button_OpenFile)
                .addGap(0, 0, 0)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(button_CreateNewFile)
                .addGap(0, 0, 0)
                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(button_SaveFile)
                .addGap(0, 0, 0)
                .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(button_Undo)
                .addGap(0, 0, 0)
                .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(button_Redo)
                .addGap(961, 961, 961)
                .addComponent(button_Helper, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        panel_OperationLayout.setVerticalGroup(
            panel_OperationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator4)
            .addComponent(jSeparator5)
            .addGroup(panel_OperationLayout.createSequentialGroup()
                .addGroup(panel_OperationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(button_OpenFile, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(button_CreateNewFile, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(button_SaveFile, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(button_Undo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(button_Redo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panel_OperationLayout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addComponent(button_Helper))
                    .addComponent(jSeparator6)
                    .addComponent(jSeparator7))
                .addGap(0, 1, Short.MAX_VALUE))
        );

        panel_Control.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        panel_Control.setMinimumSize(new java.awt.Dimension(994, 182));
        panel_Control.setRequestFocusEnabled(false);

        panel_View.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Visual", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 15))); // NOI18N

        checkBox_showGridlines.setText("Show gridlines");
        checkBox_showGridlines.setToolTipText("Whether to show grid");

        checkBox_showCoordinate.setText("Show coordinate");
        checkBox_showCoordinate.setToolTipText("Whether to show coordinate");

        checkBox_showStatusBar.setText("Show status bar");
        checkBox_showStatusBar.setToolTipText("Whether to show status bar below");
        checkBox_showStatusBar.setEnabled(false);

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
                .addGap(13, 13, 13)
                .addComponent(checkBox_showGridlines)
                .addGap(18, 18, 18)
                .addComponent(checkBox_showCoordinate)
                .addGap(18, 18, 18)
                .addComponent(checkBox_showStatusBar)
                .addContainerGap())
        );

        panel_Format.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Format", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 15))); // NOI18N

        label_StyleLine.setText("Style:");

        comboBox_StyleLine.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        comboBox_StyleLine.setToolTipText("Choose line style");

        label_SizeLine.setText("Size:");

        spinner_SizeLize.setToolTipText("Choose line size");
        spinner_SizeLize.setEnabled(false);

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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(label_Pixel)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel_FormatLayout.setVerticalGroup(
            panel_FormatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_FormatLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_FormatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboBox_StyleLine, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label_StyleLine))
                .addGap(25, 25, 25)
                .addGroup(panel_FormatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(label_SizeLine)
                    .addComponent(spinner_SizeLize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label_Pixel))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panel_Tool.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tool", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 15))); // NOI18N

        button_ColorPicker.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/colorpicker.png"))); // NOI18N
        button_ColorPicker.setToolTipText("Color picker");
        button_ColorPicker.setEnabled(false);
        button_ColorPicker.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_ColorPickerActionPerformed(evt);
            }
        });

        button_FillColor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/fillColor.png"))); // NOI18N
        button_FillColor.setToolTipText("Fill");
        button_FillColor.setEnabled(false);

        button_ClearAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/clearAll.png"))); // NOI18N
        button_ClearAll.setToolTipText("Clear");
        button_ClearAll.setEnabled(false);

        button_Eraser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/eraser.png"))); // NOI18N
        button_Eraser.setToolTipText("Eraser");
        button_Eraser.setEnabled(false);

        button_Select.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/select.png"))); // NOI18N
        button_Select.setToolTipText("Select");
        button_Select.setEnabled(false);

        button_Animation.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/animation.png"))); // NOI18N
        button_Animation.setToolTipText("Animations");
        button_Animation.setEnabled(false);

        javax.swing.GroupLayout panel_ToolLayout = new javax.swing.GroupLayout(panel_Tool);
        panel_Tool.setLayout(panel_ToolLayout);
        panel_ToolLayout.setHorizontalGroup(
            panel_ToolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_ToolLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(panel_ToolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(button_ColorPicker, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(button_Eraser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_ToolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_ToolLayout.createSequentialGroup()
                        .addComponent(button_Select)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(button_Animation))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_ToolLayout.createSequentialGroup()
                        .addComponent(button_FillColor)
                        .addGap(7, 7, 7)
                        .addComponent(button_ClearAll)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel_ToolLayout.setVerticalGroup(
            panel_ToolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_ToolLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_ToolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(button_FillColor, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(button_ColorPicker, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 47, Short.MAX_VALUE)
                    .addComponent(button_ClearAll, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_ToolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(button_Eraser, javax.swing.GroupLayout.DEFAULT_SIZE, 47, Short.MAX_VALUE)
                    .addComponent(button_Select, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(button_Animation, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        panel_Color.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Color", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 15))); // NOI18N

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
                .addContainerGap()
                .addGroup(panel_ColorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(button_ColorSave_1, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(button_ColorSave_5, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panel_ColorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(button_ColorSave_2, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(button_ColorSave_6, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panel_ColorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(button_ColorSave_3, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(button_ColorSave_7, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panel_ColorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(button_ColorSave_4, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(button_ColorSave_8, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(button_ColorChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        panel_ColorLayout.setVerticalGroup(
            panel_ColorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_ColorLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_ColorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(button_ColorChooser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panel_ColorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panel_ColorLayout.createSequentialGroup()
                            .addGroup(panel_ColorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(button_ColorSave_3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(button_ColorSave_4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(panel_ColorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(button_ColorSave_7, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(button_ColorSave_8, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panel_ColorLayout.createSequentialGroup()
                            .addGroup(panel_ColorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(button_ColorSave_2, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(button_ColorSave_1, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(13, 13, 13)
                            .addGroup(panel_ColorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(button_ColorSave_5, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(button_ColorSave_6, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panel_Color, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        panel_ControlLayout.setVerticalGroup(
            panel_ControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_ControlLayout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addGroup(panel_ControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(panel_Tool, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panel_View, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panel_Format, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panel_Color, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(3, 3, 3))
        );

        panel_SelectCoordinate.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Coordinate", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 15))); // NOI18N
        panel_SelectCoordinate.setToolTipText("Choose drawing coordinate");

        button_2DMode.setText("2D");
        button_2DMode.setFocusable(false);
        button_2DMode.setRequestFocusEnabled(false);
        button_2DMode.setRolloverEnabled(false);

        button_3DMode.setText("3D");
        button_3DMode.setFocusable(false);
        button_3DMode.setRequestFocusEnabled(false);
        button_3DMode.setRolloverEnabled(false);

        javax.swing.GroupLayout panel_SelectCoordinateLayout = new javax.swing.GroupLayout(panel_SelectCoordinate);
        panel_SelectCoordinate.setLayout(panel_SelectCoordinateLayout);
        panel_SelectCoordinateLayout.setHorizontalGroup(
            panel_SelectCoordinateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_SelectCoordinateLayout.createSequentialGroup()
                .addComponent(button_2DMode)
                .addGap(18, 18, 18)
                .addComponent(button_3DMode))
        );
        panel_SelectCoordinateLayout.setVerticalGroup(
            panel_SelectCoordinateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_SelectCoordinateLayout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(panel_SelectCoordinateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(button_2DMode)
                    .addComponent(button_3DMode))
                .addContainerGap())
        );

        button_Line.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Line_Segment.png"))); // NOI18N
        button_Line.setFocusable(false);
        button_Line.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        button_Line.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        button_Polygon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Polygon_Polygon.png"))); // NOI18N
        button_Polygon.setBorder(null);
        button_Polygon.setFocusable(false);
        button_Polygon.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        button_Polygon.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        button_Shape.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Shape_Star.png"))); // NOI18N
        button_Shape.setEnabled(false);
        button_Shape.setFocusable(false);
        button_Shape.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        button_Shape.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        button_Transform.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Transform_Rotation.png"))); // NOI18N
        button_Transform.setEnabled(false);
        button_Transform.setFocusable(false);
        button_Transform.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        button_Transform.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        javax.swing.GroupLayout panel_DrawingToolLayout = new javax.swing.GroupLayout(panel_DrawingTool);
        panel_DrawingTool.setLayout(panel_DrawingToolLayout);
        panel_DrawingToolLayout.setHorizontalGroup(
            panel_DrawingToolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_DrawingToolLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_DrawingToolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(button_Transform, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(Seperator)
                    .addGroup(panel_DrawingToolLayout.createSequentialGroup()
                        .addGroup(panel_DrawingToolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel_DrawingToolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(panel_SelectCoordinate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(button_Line, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                            .addComponent(button_Shape, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(button_Polygon, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panel_DrawingToolLayout.setVerticalGroup(
            panel_DrawingToolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_DrawingToolLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(panel_SelectCoordinate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addComponent(button_Line, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(button_Polygon, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38)
                .addComponent(button_Shape, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(71, 71, 71)
                .addComponent(Seperator, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(button_Transform, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        panel_DrawingArea.setBackground(new java.awt.Color(248, 248, 248));
        panel_DrawingArea.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panel_DrawingArea.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        panel_DrawingArea.setMinimumSize(new java.awt.Dimension(1173, 656));

        javax.swing.GroupLayout panel_DrawingAreaLayout = new javax.swing.GroupLayout(panel_DrawingArea);
        panel_DrawingArea.setLayout(panel_DrawingAreaLayout);
        panel_DrawingAreaLayout.setHorizontalGroup(
            panel_DrawingAreaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1171, Short.MAX_VALUE)
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panel_DrawingArea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel_DrawingLayout.setVerticalGroup(
            panel_DrawingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_DrawingLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(panel_DrawingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panel_DrawingTool, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panel_DrawingLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(panel_DrawingArea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, 0))
        );

        label_CoordIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/moveCursor.png"))); // NOI18N
        label_CoordIcon.setToolTipText("Mouse coord");
        label_CoordIcon.setFocusable(false);
        label_CoordIcon.setInheritsPopupMenu(false);
        label_CoordIcon.setRequestFocusEnabled(false);
        label_CoordIcon.setVerifyInputWhenFocusTarget(false);

        label_CoordValue.setText("X: 100   Y: 100");

        javax.swing.GroupLayout panel_CoordinateCursorLayout = new javax.swing.GroupLayout(panel_CoordinateCursor);
        panel_CoordinateCursor.setLayout(panel_CoordinateCursorLayout);
        panel_CoordinateCursorLayout.setHorizontalGroup(
            panel_CoordinateCursorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_CoordinateCursorLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(label_CoordIcon)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(label_CoordValue)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel_CoordinateCursorLayout.setVerticalGroup(
            panel_CoordinateCursorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(label_CoordIcon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(panel_CoordinateCursorLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(label_CoordValue)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jSeparator8.setOrientation(javax.swing.SwingConstants.VERTICAL);

        label_ToolTip.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        label_ToolTip.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/tooltip.png"))); // NOI18N
        label_ToolTip.setText("Let me say something!");
        label_ToolTip.setFocusable(false);
        label_ToolTip.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        javax.swing.GroupLayout panel_StatusBarLayout = new javax.swing.GroupLayout(panel_StatusBar);
        panel_StatusBar.setLayout(panel_StatusBarLayout);
        panel_StatusBarLayout.setHorizontalGroup(
            panel_StatusBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_StatusBarLayout.createSequentialGroup()
                .addComponent(panel_CoordinateCursor, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jSeparator8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(label_ToolTip, javax.swing.GroupLayout.PREFERRED_SIZE, 1170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        panel_StatusBarLayout.setVerticalGroup(
            panel_StatusBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_CoordinateCursor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jSeparator8, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(panel_StatusBarLayout.createSequentialGroup()
                .addComponent(label_ToolTip)
                .addGap(0, 0, 0))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_StatusBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                .addComponent(panel_Operation, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(panel_Drawing, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(panel_Control, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jSeparator2)
            .addComponent(jSeparator1)
            .addComponent(jSeparator3)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panel_Operation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panel_Control, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(panel_Drawing, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(panel_StatusBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void button_ColorPickerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_ColorPickerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_button_ColorPickerActionPerformed

    private Panel_DrawingArea getDrawingPanel() {
        return (Panel_DrawingArea) panel_DrawingArea;
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
                try {
                    new Paint().setVisible(true);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JSeparator Seperator;
    private javax.swing.ButtonGroup buttonGroup_CoordMode;
    private javax.swing.JRadioButton button_2DMode;
    private javax.swing.JRadioButton button_3DMode;
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
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JLabel label_CoordIcon;
    private javax.swing.JLabel label_CoordValue;
    private javax.swing.JLabel label_Pixel;
    private javax.swing.JLabel label_SizeLine;
    private javax.swing.JLabel label_StyleLine;
    private javax.swing.JLabel label_ToolTip;
    private javax.swing.JMenuItem menuItem_Arrow;
    private javax.swing.JMenuItem menuItem_Circle;
    private javax.swing.JMenuItem menuItem_Diamond;
    private javax.swing.JMenuItem menuItem_FreeDrawing;
    private javax.swing.JMenuItem menuItem_FreePolygon;
    private javax.swing.JMenuItem menuItem_Line;
    private javax.swing.JMenuItem menuItem_Rectangle;
    private javax.swing.JMenuItem menuItem_Rotation;
    private javax.swing.JMenuItem menuItem_Segment;
    private javax.swing.JMenuItem menuItem_Star;
    private javax.swing.JMenuItem menuItem_Symmetry;
    private javax.swing.JMenuItem menuItem_Triangle;
    private javax.swing.JPanel panel_Color;
    private javax.swing.JPanel panel_Control;
    private javax.swing.JPanel panel_CoordinateCursor;
    private javax.swing.JPanel panel_Drawing;
    private javax.swing.JPanel panel_DrawingArea;
    private javax.swing.JPanel panel_DrawingTool;
    private javax.swing.JPanel panel_Format;
    private javax.swing.JPanel panel_Operation;
    private javax.swing.JPanel panel_SelectCoordinate;
    private javax.swing.JPanel panel_StatusBar;
    private javax.swing.JPanel panel_Tool;
    private javax.swing.JPanel panel_View;
    private javax.swing.JPopupMenu popMenu_Line;
    private javax.swing.JPopupMenu popMenu_Polygon;
    private javax.swing.JPopupMenu popMenu_Shape;
    private javax.swing.JPopupMenu popMenu_Transform;
    private javax.swing.JSpinner spinner_SizeLize;
    // End of variables declaration//GEN-END:variables
}
