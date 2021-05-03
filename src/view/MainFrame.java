/**
 * SketchPoint Application.
 *
 */
package view;

import com.pump.swing.JEyeDropper;
import control.SettingConstants;
import control.util.Ultility;
import java.awt.Color;
import java.awt.Component;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JSpinner;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import static control.SettingConstants.*;
import control.io.FileHandle;
import java.awt.AWTException;
import java.awt.CardLayout;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

public class MainFrame extends javax.swing.JFrame {

    private SettingConstants.DrawingToolMode savedLineMode;
    private SettingConstants.DrawingToolMode savedPolygonMode;
    private SettingConstants.DrawingToolMode savedShapeMode;
    private SettingConstants.DrawingToolMode savedTransformMode;

    private SettingConstants.DrawingToolMode saved3DShapeMode;

    private AnimationDialog animationDialog = new AnimationDialog(this, true);
    
    /**
     * Number of saved color.
     */
    private int savedColorNumber;

    private JButton[] savedColorButtonList;

    private DrawingToolMode[] ShiftException = {
        DrawingToolMode.DRAWING_POLYGON_ELLIPSE,
        DrawingToolMode.DRAWING_LINE_SEGMENT,
        DrawingToolMode.DRAWING_POLYGON_RECTANGLE,
        DrawingToolMode.DRAWING_POLYGON_TRIANGLE,
        DrawingToolMode.DRAWING_SHAPE_DIAMOND
    };

    public MainFrame() {
        UIManager.put("PopupMenu.consumeEventOnClose", false);
        customizeComponents();
        setIconFrame();
        setOptionLineStyle();
        setAllEventHandler();
        button_Line.requestFocus();
        panel_DrawingArea.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent event) {
                showCursorCoordinate(event);
            }

            @Override
            public void mouseMoved(MouseEvent event) {
                showCursorCoordinate(event);
                boolean isInTheList = false;
                for (DrawingToolMode element : ShiftException) {
                    if (getDrawingPanel().getSelectedToolMode() == element) {
                        isInTheList = true;
                    }
                }

                if (!isInTheList) {
                    hideTooltip();
                }
            }
        });

        setSelectedToolMode(DrawingToolMode.DRAWING_LINE_SEGMENT);

        // Set frame location.
        setLocationRelativeTo(null);
    }

    public void showCursorCoordinate(MouseEvent event) {
        //Để mẫu số riêng để tọa độ trùng khớp với tọa độ put pixel vào, do đặt mẫu chung sẽ bị sai lệch kết quả trong quá trình
        //khử phần thập phân!
        label_CoordValue.setText(
                "X: " + ((event.getX() / SettingConstants.RECT_SIZE) - (COORD_X_O / SettingConstants.RECT_SIZE)) + "   "
                + "Y: " + (-((event.getY() / SettingConstants.RECT_SIZE) - (COORD_Y_O / SettingConstants.RECT_SIZE))));
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
        buttonGroup_CoordMode.add(button_2DMode);
        buttonGroup_CoordMode.add(button_3DMode);

        buttonGroup_CoordMode.setSelected(button_2DMode.getModel(), true);

        // Line modal
        initMenuItem(popMenu_Line, menuItem_Segment, DrawingToolMode.DRAWING_LINE_SEGMENT, "/img/segment24px.png");
        initMenuItem(popMenu_Line, menuItem_Line, DrawingToolMode.DRAWING_LINE_STRAIGHT, "/img/straightLine24px.png");
        initMenuItem(popMenu_Line, menuItem_FreeDrawing, DrawingToolMode.DRAWING_LINE_FREE, "/img/freeDrawing24px.png");

        // Polygon modal
        initMenuItem(popMenu_Polygon, menuItem_FreePolygon, DrawingToolMode.DRAWING_POLYGON_FREE, "/img/polygon24px.png");
        initMenuItem(popMenu_Polygon, menuItem_Triangle, DrawingToolMode.DRAWING_POLYGON_TRIANGLE, "/img/triangle24px.png");
        initMenuItem(popMenu_Polygon, menuItem_Rectangle, DrawingToolMode.DRAWING_POLYGON_RECTANGLE, "/img/rectangle24px.png");
        initMenuItem(popMenu_Polygon, menuItem_Circle, DrawingToolMode.DRAWING_POLYGON_ELLIPSE, "/img/circle24px.png");

        // Shape modal
        initMenuItem(popMenu_Shape, menuItem_Star, DrawingToolMode.DRAWING_SHAPE_STAR, "/img/star24px.png");
        initMenuItem(popMenu_Shape, menuItem_Diamond, DrawingToolMode.DRAWING_SHAPE_DIAMOND, "/img/diamond24px.png");
        initMenuItem(popMenu_Shape, menuItem_Arrow, DrawingToolMode.DRAWING_SHAPE_ARROW, "/img/arrow24px.png");

        // For 3D
//        initMenuItem(popMenu_3DShape, menuItem_3DCube, DrawingToolMode.DRAWING_3DSHAPE_CUBE, "/img/cube24px.png");
//        initMenuItem(popMenu_3DShape, menuItem_3DCylinder, DrawingToolMode.DRAWING_3DSHAPE_CYLINDER, "/img/cylinder24px.png");
//        initMenuItem(popMenu_3DShape, menuItem_3DPyramid, DrawingToolMode.DRAWING_3DSHAPE_PYRAMID, "/img/pyramid24px.png");
//        initMenuItem(popMenu_3DShape, menuItem_3DSphere, DrawingToolMode.DRAWING_3DSHAPE_SPHERE, "/img/sphere24px.png");

        // Transform modal
        initMenuItem(popMenu_Transform, menuItem_Rotation, DrawingToolMode.DRAWING_TRANSFORM_ROTATION, "/img/rotation24px.png");
        initMenuItem(popMenu_Transform, menuItem_Symmetry, DrawingToolMode.DRAWING_TRANSFORM_SYMMETRY, "/img/symmetry24px.png");

        savedLineMode = SettingConstants.DrawingToolMode.DRAWING_LINE_SEGMENT;
        savedPolygonMode = SettingConstants.DrawingToolMode.DRAWING_POLYGON_FREE;
        savedShapeMode = SettingConstants.DrawingToolMode.DRAWING_SHAPE_STAR;
        savedTransformMode = SettingConstants.DrawingToolMode.DRAWING_TRANSFORM_ROTATION;

        saved3DShapeMode = SettingConstants.DrawingToolMode.DRAWING_3DSHAPE_RECTANGULAR;

        button_Line.requestFocus();
    }

    /**
     * Set up menu item to pop up menu.
     *
     * @param popMenu
     * @param menuItem
     * @param mode
     * @param iconPath
     */
    private void initMenuItem(JPopupMenu popMenu, JMenuItem menuItem, DrawingToolMode mode, String iconPath) {
        String text = mode.toString().toLowerCase();
        text = String.valueOf(text.charAt(0)).toUpperCase() + text.substring(1, text.length());
        menuItem.setText(text);

        if (iconPath != null) {
            menuItem.setIcon(new ImageIcon(getClass().getResource(iconPath)));
        }

        popMenu.add(menuItem);
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
        checkBox_showGridlines.setSelected(SettingConstants.DEFAULT_VISUAL_SHOW_GRID);
        checkBox_showCoordinate.setSelected(SettingConstants.DEFAULT_VISUAL_SHOW_COORDINATE);
    }

    private void setDefaultColorOption() {
        button_ColorSave_8.setBackground(SettingConstants.DEFAULT_COLOR_SAVE_1);

        savedColorButtonList = new JButton[]{
            button_ColorSave_1,
            button_ColorSave_2,
            button_ColorSave_3,
            button_ColorSave_4,
            button_ColorSave_5,
            button_ColorSave_6,
            button_ColorSave_7,
            button_ColorSave_8};

        savedColorNumber = 0;

        button_ColorSave_1.setBackground(SettingConstants.DEFAULT_COLOR_SAVE_1);
        button_ColorSave_2.setBackground(SettingConstants.DEFAULT_COLOR_SAVE_2);
        button_ColorSave_3.setBackground(SettingConstants.DEFAULT_COLOR_SAVE_3);
        button_ColorSave_4.setBackground(SettingConstants.DEFAULT_COLOR_SAVE_4);
        button_ColorSave_5.setBackground(SettingConstants.DEFAULT_COLOR_SAVE_5);
        button_ColorSave_6.setBackground(SettingConstants.DEFAULT_COLOR_SAVE_6);
        button_ColorSave_7.setBackground(SettingConstants.DEFAULT_COLOR_SAVE_7);
        button_ColorSave_8.setBackground(SettingConstants.DEFAULT_COLOR_SAVE_8);
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

                JFileChooser chooser = new JFileChooser() {
                    @Override
                    protected JDialog createDialog(Component parent) throws HeadlessException {
                        JDialog dialog = super.createDialog(parent);
                        ImageIcon img = new ImageIcon(getClass().getResource("/img/openFile.png"));
                        dialog.setIconImage(img.getImage());
                        return dialog;
                    }
                };

                int i, j;
                int count = 0;

                getDrawingPanel().resetSavedPropertyArray();

                FileNameExtensionFilter filter = new FileNameExtensionFilter("PNG Images", "png");

                chooser.setFileFilter(filter);

                int reVal = chooser.showOpenDialog(null);

                if (reVal == JFileChooser.APPROVE_OPTION) {
                    getDrawingPanel().resetSavedPropertyArray();
                    getDrawingPanel().repaint();
                    String inputFileName = chooser.getSelectedFile().getAbsolutePath();

                    FileHandle.openFile(inputFileName, getDrawingPanel().getColorOfBoard(), getDrawingPanel().getCoordOfBoard());

                    repaint();
                }
            }
        });

        button_CreateNewFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                dispose();
                new MainFrame().setVisible(true);
            }
        });

        button_SaveFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                // if the board is not drawn, do no thing
                if (getDrawingPanel().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "The board is empty!", "Invalid Option", JOptionPane.INFORMATION_MESSAGE);

                    return;
                }

                JFileChooser chooser = new JFileChooser() {
                    @Override
                    protected JDialog createDialog(Component parent) throws HeadlessException {
                        JDialog dialog = super.createDialog(parent);
                        ImageIcon img = new ImageIcon(getClass().getResource("/img/save.png"));
                        dialog.setIconImage(img.getImage());
                        return dialog;
                    }
                };

                FileNameExtensionFilter filter = new FileNameExtensionFilter("PNG Images", "png");

                chooser.setFileFilter(filter);

                int reVal = chooser.showSaveDialog(null);

                if (reVal == JFileChooser.APPROVE_OPTION) {
                    String outputFileName = chooser.getSelectedFile().getAbsolutePath();

                    FileHandle.saveFile(outputFileName, getDrawingPanel().getColorOfBoard(), getDrawingPanel().getCoordOfBoard());
                }
            }
        });

        button_Undo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                ((DrawingPanel) panel_DrawingArea).undo();
                button_Undo.setEnabled(getDrawingPanel().ableUndo());
                button_Redo.setEnabled(getDrawingPanel().ableRedo());
                getDrawingPanel().repaint();
            }
        });

        button_Redo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                ((DrawingPanel) panel_DrawingArea).redo();
                button_Undo.setEnabled(getDrawingPanel().ableUndo());
                button_Redo.setEnabled(getDrawingPanel().ableRedo());
                getDrawingPanel().repaint();
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
                ((DrawingPanel) panel_DrawingArea).setShowGridLinesFlag(showGrid_Flag);
            }
        });

        checkBox_showCoordinate.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent event) {
                boolean showCoordinate_Flag = (event.getStateChange() == ItemEvent.SELECTED);
                ((DrawingPanel) panel_DrawingArea).setShowCoordinateFlag(showCoordinate_Flag);
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
                        ((DrawingPanel) panel_DrawingArea).setSelectedLineStyle(ls);
                        break;
                    }
                }
            }
        });

        spinner_SizeLize.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent event) {
                int selectedLineSize = (Integer) ((JSpinner) event.getSource()).getValue();
                ((DrawingPanel) panel_DrawingArea).setSelectedLineSize(selectedLineSize);
            }
        });

    }

    /**
     * Set selected tool mode from user.
     *
     * @param toolMode
     */
    private boolean setSelectedToolMode(SettingConstants.DrawingToolMode toolMode) {

        if (toolMode.toolTip.compareTo("") != 0) {
            showTooltip(toolMode.toolTip);
        }

        SettingConstants.DrawingToolMode selectedDrawingToolMode
                = ((DrawingPanel) panel_DrawingArea).getSelectedToolMode();

        if (selectedDrawingToolMode == toolMode) {
            return false;
        }

        return ((DrawingPanel) panel_DrawingArea).setSelectedToolMode(toolMode);
    }

    /**
     * User tool option event handling.
     */
    private void setEventHandlingToolOption() {
        button_ColorPicker.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                try {
                    JEyeDropper eyeDroper = new JEyeDropper(100);
                    eyeDroper.setVisible(true);

                    Color selectedColor = eyeDroper.getModel().getSelectedColor();

                    getDrawingPanel().setSelectedColor(selectedColor);

                    button_CurrentColor.setBackground(selectedColor);

                    button_ColorSave_8.setBackground(selectedColor);

                    button_ColorPicker.setSelected(false);

                    button_ColorPicker.repaint();
                } catch (AWTException ex) {
                    Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        button_FillColor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                setSelectedToolMode(SettingConstants.DrawingToolMode.TOOL_COLOR_FILLER);
            }
        });

        button_ClearAll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                getDrawingPanel().resetChangedPropertyArray();
                getDrawingPanel().resetSavedPropertyArray();
                getDrawingPanel().repaint();
            }
        });

        button_Eraser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                setSelectedToolMode(SettingConstants.DrawingToolMode.TOOL_ERASER);
            }
        });

        button_Select.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                setSelectedToolMode(SettingConstants.DrawingToolMode.TOOL_SELECT);

            }
        });

        button_Animation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                if (animationDialog.isShowing()) {
                    animationDialog.requestFocus();
                } else {
                    animationDialog.setVisible(true);
                }
            }
        });
        
    }

    /**
     * User color option event handling.
     */
    private void setEventHandlingColorOption() {
        //khởi tạo
        for (int j = 0; j < SettingConstants.DEFAULT_SAVED_COLOR_NUMBER; j++) {
            savedColorButtonList[j].addActionListener(new CustomSavedColorButtonEventHandling(savedColorButtonList[j]));
        }

        button_ColorChooser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                Color selectedColor = JColorChooser.showDialog(null, "Choose color", Color.BLACK);

                getDrawingPanel().setSelectedColor(selectedColor);
                button_CurrentColor.setBackground(selectedColor);
                //Check if it's already in the list
                for (int i = 0; i < DEFAULT_SAVED_COLOR_NUMBER; i++) {
                    if (savedColorButtonList[i].getBackground().equals(selectedColor)) {
                        return;
                    }
                }
                // Add new saved color if it's not found in list
                if (savedColorNumber < 4) {
                    savedColorButtonList[4 + savedColorNumber].setBackground(selectedColor);
                    savedColorNumber++;
                } else {
                    int startingColorSavedButtonIndex = SettingConstants.DEFAULT_SAVED_COLOR_NUMBER / 2;

                    for (int i = startingColorSavedButtonIndex; i < SettingConstants.DEFAULT_SAVED_COLOR_NUMBER - 1; i++) {
                        savedColorButtonList[i].setBackground(savedColorButtonList[i + 1].getBackground());
                    }
                    savedColorButtonList[SettingConstants.DEFAULT_SAVED_COLOR_NUMBER - 1].setBackground(selectedColor);
                }

                for (int i = 0; i < SettingConstants.DEFAULT_SAVED_COLOR_NUMBER; i++) {
                    savedColorButtonList[i].addActionListener(new CustomSavedColorButtonEventHandling(savedColorButtonList[i]));
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
                theo class SettingConstants và checkbox tương ứng.
            2.  Gọi hàm setCoordinateMode của panel_DrawingArea (xem chi tiết
                tại class DrawingPanel).
         */

        button_2DMode.addActionListener(new CustomChangeCoordSystemAction(CoordinateMode.MODE_2D));
        button_3DMode.addActionListener(new CustomChangeCoordSystemAction(CoordinateMode.MODE_3D));

        //======================================================================
        // Button drawing tool
        //======================================================================
        button_Line.addMouseListener(new CustomMouseAtButtonDrawingTool(savedLineMode, button_Line, popMenu_Line));

        button_Polygon.addMouseListener(new CustomMouseAtButtonDrawingTool(savedPolygonMode, button_Polygon, popMenu_Polygon));

        button_Shape.addMouseListener(new CustomMouseAtButtonDrawingTool(savedShapeMode, button_Shape, popMenu_Shape));

        button_Transform.addMouseListener(new CustomMouseAtButtonDrawingTool(savedTransformMode, button_Transform, popMenu_Transform));

//        button_3DShape.addMouseListener(new CustomMouseAtButtonDrawingTool(saved3DShapeMode, button_3DShape, popMenu_3DShape));
        //======================================================================
        // MenuItem inside popup menu
        //======================================================================
        menuItem_Segment.addActionListener(new CustomMenuItemChooseAction(
                savedLineMode,
                SettingConstants.DrawingToolMode.DRAWING_LINE_SEGMENT,
                "/img/Line_Segment.png",
                button_Line)
        );

        menuItem_Line.addActionListener(new CustomMenuItemChooseAction(
                savedLineMode,
                SettingConstants.DrawingToolMode.DRAWING_LINE_STRAIGHT,
                "/img/Line_StraightLine.png",
                button_Line)
        );

        menuItem_FreeDrawing.addActionListener(new CustomMenuItemChooseAction(
                savedLineMode,
                SettingConstants.DrawingToolMode.DRAWING_LINE_FREE,
                "/img/Line_FreeDrawing.png",
                button_Line)
        );

        //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        menuItem_FreePolygon.addActionListener(new CustomMenuItemChooseAction(
                savedPolygonMode,
                SettingConstants.DrawingToolMode.DRAWING_POLYGON_FREE,
                "/img/Polygon_Polygon.png",
                button_Polygon)
        );

        menuItem_Triangle.addActionListener(new CustomMenuItemChooseAction(
                savedPolygonMode,
                SettingConstants.DrawingToolMode.DRAWING_POLYGON_TRIANGLE,
                "/img/Polygon_Triangle.png",
                button_Polygon)
        );

        menuItem_Rectangle.addActionListener(new CustomMenuItemChooseAction(
                savedPolygonMode,
                SettingConstants.DrawingToolMode.DRAWING_POLYGON_RECTANGLE,
                "/img/Poligon_Rectangle.png",
                button_Polygon)
        );

        menuItem_Circle.addActionListener(new CustomMenuItemChooseAction(
                savedPolygonMode,
                SettingConstants.DrawingToolMode.DRAWING_POLYGON_ELLIPSE,
                "/img/Polygon_Circle.png",
                button_Polygon)
        );

        //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        menuItem_Star.addActionListener(new CustomMenuItemChooseAction(
                savedShapeMode,
                SettingConstants.DrawingToolMode.DRAWING_SHAPE_STAR,
                "/img/Shape_Star.png",
                button_Shape)
        );

        menuItem_Diamond.addActionListener(new CustomMenuItemChooseAction(
                savedShapeMode,
                SettingConstants.DrawingToolMode.DRAWING_SHAPE_DIAMOND,
                "/img/Shape_Diamond.png",
                button_Shape)
        );

        menuItem_Arrow.addActionListener(new CustomMenuItemChooseAction(
                savedShapeMode,
                SettingConstants.DrawingToolMode.DRAWING_SHAPE_ARROW,
                "/img/Shape_Arrow.png",
                button_Shape)
        );

        //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        // For 3D
//        menuItem_3DRectangular.addActionListener(new CustomMenuItemChooseAction(
//                saved3DShapeMode,
//                SettingConstants.DrawingToolMode.DRAWING_3DSHAPE_RECTANGULAR,
//                "/img/3DShape_Rectangular.png",
//                button_3DShape)
//        );
//        
//        menuItem_3DCylinder.addActionListener(new CustomMenuItemChooseAction(
//                saved3DShapeMode,
//                SettingConstants.DrawingToolMode.DRAWING_3DSHAPE_CYLINDER,
//                "/img/3DShape_Cylinder.png",
//                button_3DShape)
//        );
//        
//        menuItem_3DPyramid.addActionListener(new CustomMenuItemChooseAction(
//                saved3DShapeMode,
//                SettingConstants.DrawingToolMode.DRAWING_3DSHAPE_PYRAMID,
//                "/img/3DShape_Pyramid.png",
//                button_3DShape)
//        );
//        
//        menuItem_3DSphere.addActionListener(new CustomMenuItemChooseAction(
//                saved3DShapeMode,
//                SettingConstants.DrawingToolMode.DRAWING_3DSHAPE_SPHERE,
//                "/img/3DShape_Sphere.png",
//                button_3DShape)
//        );
        
        //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        menuItem_Rotation.addActionListener(new CustomMenuItemChooseAction(
                savedTransformMode,
                SettingConstants.DrawingToolMode.DRAWING_TRANSFORM_ROTATION,
                "/img/Transform_Rotation.png",
                button_Transform)
        );

        menuItem_Symmetry.addActionListener(new CustomMenuItemChooseAction(
                savedTransformMode,
                SettingConstants.DrawingToolMode.DRAWING_TRANSFORM_SYMMETRY,
                "/img/Transform_Symmetry.png",
                button_Transform)
        );
    }

    /**
     * Set up all event handling for frame.
     */
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
        button_CreateNewFile = new javax.swing.JButton();
        button_SaveFile = new javax.swing.JButton();
        button_Undo = new javax.swing.JButton();
        button_Redo = new javax.swing.JButton();
        jSeparator9 = new javax.swing.JSeparator();
        jSeparator10 = new javax.swing.JSeparator();
        jSeparator11 = new javax.swing.JSeparator();
        jSeparator12 = new javax.swing.JSeparator();
        jSeparator1 = new javax.swing.JSeparator();
        panel_Control = new javax.swing.JPanel();
        panel_View = new javax.swing.JPanel();
        checkBox_showGridlines = new javax.swing.JCheckBox();
        checkBox_showCoordinate = new javax.swing.JCheckBox();
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
        jPanel1 = new javax.swing.JPanel();
        button_CurrentColor = new javax.swing.JButton();
        label_CurrentColor = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        panel_Drawing = new javax.swing.JPanel();
        panel_DrawingTool = new javax.swing.JPanel();
        panel_SelectCoordinate = new javax.swing.JPanel();
        button_2DMode = new javax.swing.JRadioButton();
        button_3DMode = new javax.swing.JRadioButton();
        panelDrawingTool = new javax.swing.JPanel();
        panelDrawingTool2D = new javax.swing.JPanel();
        button_Line = new javax.swing.JButton();
        button_Polygon = new javax.swing.JButton();
        button_Shape = new javax.swing.JButton();
        Seperator = new javax.swing.JSeparator();
        button_Transform = new javax.swing.JButton();
        panelDrawingTool3D = new javax.swing.JPanel();
        button_3DShape = new javax.swing.JButton();
        panel_DrawingArea = new view.DrawingPanel();
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

        panel_Operation.setFocusable(false);

        button_OpenFile.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/openFile.png"))); // NOI18N
        button_OpenFile.setToolTipText("Open file");
        button_OpenFile.setContentAreaFilled(false);
        button_OpenFile.setFocusable(false);
        button_OpenFile.setRequestFocusEnabled(false);
        button_OpenFile.setRolloverEnabled(false);

        button_CreateNewFile.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/createNew.png"))); // NOI18N
        button_CreateNewFile.setToolTipText("Create a new file");
        button_CreateNewFile.setContentAreaFilled(false);
        button_CreateNewFile.setFocusable(false);
        button_CreateNewFile.setRequestFocusEnabled(false);
        button_CreateNewFile.setRolloverEnabled(false);

        button_SaveFile.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/save.png"))); // NOI18N
        button_SaveFile.setToolTipText("Save file");
        button_SaveFile.setContentAreaFilled(false);
        button_SaveFile.setFocusable(false);
        button_SaveFile.setRequestFocusEnabled(false);
        button_SaveFile.setRolloverEnabled(false);

        button_Undo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/undo.png"))); // NOI18N
        button_Undo.setToolTipText("Undo");
        button_Undo.setContentAreaFilled(false);
        button_Undo.setDisabledIcon(null);
        button_Undo.setEnabled(false);
        button_Undo.setFocusable(false);
        button_Undo.setRequestFocusEnabled(false);
        button_Undo.setRolloverEnabled(false);
        button_Undo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        button_Redo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/redo.png"))); // NOI18N
        button_Redo.setToolTipText("Redo");
        button_Redo.setBorderPainted(false);
        button_Redo.setContentAreaFilled(false);
        button_Redo.setDisabledIcon(null);
        button_Redo.setEnabled(false);
        button_Redo.setFocusable(false);
        button_Redo.setRequestFocusEnabled(false);
        button_Redo.setRolloverEnabled(false);
        button_Redo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        jSeparator9.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jSeparator10.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jSeparator11.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jSeparator12.setOrientation(javax.swing.SwingConstants.VERTICAL);

        javax.swing.GroupLayout panel_OperationLayout = new javax.swing.GroupLayout(panel_Operation);
        panel_Operation.setLayout(panel_OperationLayout);
        panel_OperationLayout.setHorizontalGroup(
            panel_OperationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_OperationLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(button_OpenFile, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(button_CreateNewFile, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(button_SaveFile, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jSeparator12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(button_Undo, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator11, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(button_Redo, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel_OperationLayout.setVerticalGroup(
            panel_OperationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_OperationLayout.createSequentialGroup()
                .addGroup(panel_OperationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(button_SaveFile, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 57, Short.MAX_VALUE)
                    .addComponent(button_Undo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(button_OpenFile, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(1, 1, 1))
            .addComponent(button_Redo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(button_CreateNewFile, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(panel_OperationLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_OperationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator9, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator10, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator11)
                    .addComponent(jSeparator12, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );

        panel_Control.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        panel_Control.setMinimumSize(new java.awt.Dimension(994, 182));
        panel_Control.setRequestFocusEnabled(false);

        panel_View.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Visual", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 15))); // NOI18N

        checkBox_showGridlines.setText("Show gridlines");
        checkBox_showGridlines.setToolTipText("Whether to show grid");
        checkBox_showGridlines.setFocusable(false);

        checkBox_showCoordinate.setText("Show coordinate");
        checkBox_showCoordinate.setToolTipText("Whether to show coordinate");
        checkBox_showCoordinate.setFocusable(false);

        javax.swing.GroupLayout panel_ViewLayout = new javax.swing.GroupLayout(panel_View);
        panel_View.setLayout(panel_ViewLayout);
        panel_ViewLayout.setHorizontalGroup(
            panel_ViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_ViewLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_ViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(checkBox_showGridlines)
                    .addComponent(checkBox_showCoordinate))
                .addContainerGap())
        );
        panel_ViewLayout.setVerticalGroup(
            panel_ViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_ViewLayout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addComponent(checkBox_showGridlines)
                .addGap(18, 18, 18)
                .addComponent(checkBox_showCoordinate)
                .addGap(52, 52, 52))
        );

        panel_Format.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Format", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 15))); // NOI18N

        label_StyleLine.setText("Style:");

        comboBox_StyleLine.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        comboBox_StyleLine.setToolTipText("Choose line style");
        comboBox_StyleLine.setFocusable(false);

        label_SizeLine.setText("Size:");

        spinner_SizeLize.setToolTipText("Choose line size");
        spinner_SizeLize.setEnabled(false);
        spinner_SizeLize.setFocusable(false);
        spinner_SizeLize.setRequestFocusEnabled(false);

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
        button_ColorPicker.setFocusPainted(false);

        button_FillColor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/fillColor.png"))); // NOI18N
        button_FillColor.setToolTipText("Fill");
        button_FillColor.setFocusPainted(false);

        button_ClearAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/clearAll.png"))); // NOI18N
        button_ClearAll.setToolTipText("Clear");
        button_ClearAll.setFocusPainted(false);

        button_Eraser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/eraser.png"))); // NOI18N
        button_Eraser.setToolTipText("Eraser");
        button_Eraser.setFocusPainted(false);

        button_Select.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/select.png"))); // NOI18N
        button_Select.setToolTipText("Select");
        button_Select.setEnabled(false);
        button_Select.setFocusPainted(false);

        button_Animation.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/animation.png"))); // NOI18N
        button_Animation.setToolTipText("Animations");
        button_Animation.setFocusPainted(false);

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
        button_ColorSave_1.setFocusable(false);
        button_ColorSave_1.setOpaque(true);

        button_ColorSave_2.setBackground(new java.awt.Color(0, 0, 0));
        button_ColorSave_2.setToolTipText("Recent color");
        button_ColorSave_2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        button_ColorSave_2.setContentAreaFilled(false);
        button_ColorSave_2.setFocusable(false);
        button_ColorSave_2.setOpaque(true);

        button_ColorSave_3.setBackground(new java.awt.Color(0, 0, 0));
        button_ColorSave_3.setToolTipText("Recent color");
        button_ColorSave_3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        button_ColorSave_3.setContentAreaFilled(false);
        button_ColorSave_3.setFocusable(false);
        button_ColorSave_3.setOpaque(true);

        button_ColorSave_4.setBackground(new java.awt.Color(0, 0, 0));
        button_ColorSave_4.setToolTipText("Recent color");
        button_ColorSave_4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        button_ColorSave_4.setContentAreaFilled(false);
        button_ColorSave_4.setFocusable(false);
        button_ColorSave_4.setOpaque(true);

        button_ColorSave_5.setBackground(new java.awt.Color(0, 0, 0));
        button_ColorSave_5.setToolTipText("Recent color");
        button_ColorSave_5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        button_ColorSave_5.setContentAreaFilled(false);
        button_ColorSave_5.setFocusable(false);
        button_ColorSave_5.setOpaque(true);

        button_ColorSave_6.setBackground(new java.awt.Color(0, 0, 0));
        button_ColorSave_6.setToolTipText("Recent color");
        button_ColorSave_6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        button_ColorSave_6.setContentAreaFilled(false);
        button_ColorSave_6.setFocusable(false);
        button_ColorSave_6.setOpaque(true);

        button_ColorSave_7.setBackground(new java.awt.Color(0, 0, 0));
        button_ColorSave_7.setToolTipText("Recent color");
        button_ColorSave_7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        button_ColorSave_7.setContentAreaFilled(false);
        button_ColorSave_7.setFocusable(false);
        button_ColorSave_7.setOpaque(true);

        button_ColorSave_8.setBackground(new java.awt.Color(0, 0, 0));
        button_ColorSave_8.setToolTipText("Recent color");
        button_ColorSave_8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        button_ColorSave_8.setContentAreaFilled(false);
        button_ColorSave_8.setFocusable(false);
        button_ColorSave_8.setOpaque(true);

        button_ColorChooser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/colorChooser.png"))); // NOI18N
        button_ColorChooser.setText("Edit");
        button_ColorChooser.setToolTipText("Color chooser");
        button_ColorChooser.setFocusable(false);
        button_ColorChooser.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        button_ColorChooser.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        jPanel1.setBackground(new java.awt.Color(201, 224, 247));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(98, 162, 228)));

        button_CurrentColor.setBackground(new java.awt.Color(0, 0, 0));
        button_CurrentColor.setContentAreaFilled(false);
        button_CurrentColor.setOpaque(true);

        label_CurrentColor.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label_CurrentColor.setText("Current color");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(label_CurrentColor, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 87, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(button_CurrentColor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(button_CurrentColor, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(label_CurrentColor)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panel_ColorLayout = new javax.swing.GroupLayout(panel_Color);
        panel_Color.setLayout(panel_ColorLayout);
        panel_ColorLayout.setHorizontalGroup(
            panel_ColorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_ColorLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panel_ColorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_ColorLayout.createSequentialGroup()
                        .addComponent(button_ColorSave_1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(button_ColorSave_2, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(button_ColorSave_3, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(button_ColorSave_4, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel_ColorLayout.createSequentialGroup()
                        .addComponent(button_ColorSave_5, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(button_ColorSave_6, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(button_ColorSave_7, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(button_ColorSave_8, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(button_ColorChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        panel_ColorLayout.setVerticalGroup(
            panel_ColorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_ColorLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_ColorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(button_ColorChooser, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panel_ColorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panel_ColorLayout.createSequentialGroup()
                            .addGroup(panel_ColorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(button_ColorSave_2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(button_ColorSave_4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(button_ColorSave_3, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(panel_ColorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(button_ColorSave_7, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(button_ColorSave_6, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(button_ColorSave_8, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panel_ColorLayout.createSequentialGroup()
                            .addComponent(button_ColorSave_1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(button_ColorSave_5, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
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
                .addComponent(button_3DMode)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

        panelDrawingTool.setLayout(new java.awt.CardLayout());

        panelDrawingTool2D.setName("Card2D"); // NOI18N

        button_Line.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Line_Segment.png"))); // NOI18N
        button_Line.setFocusPainted(false);
        button_Line.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        button_Line.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        button_Polygon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Polygon_Polygon.png"))); // NOI18N
        button_Polygon.setBorder(null);
        button_Polygon.setFocusPainted(false);
        button_Polygon.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        button_Polygon.setMaximumSize(new java.awt.Dimension(148, 105));
        button_Polygon.setMinimumSize(new java.awt.Dimension(148, 105));
        button_Polygon.setPreferredSize(new java.awt.Dimension(148, 105));
        button_Polygon.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        button_Shape.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Shape_Star.png"))); // NOI18N
        button_Shape.setFocusPainted(false);
        button_Shape.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        button_Shape.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        button_Transform.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Transform_Rotation.png"))); // NOI18N
        button_Transform.setFocusPainted(false);
        button_Transform.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        button_Transform.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        javax.swing.GroupLayout panelDrawingTool2DLayout = new javax.swing.GroupLayout(panelDrawingTool2D);
        panelDrawingTool2D.setLayout(panelDrawingTool2DLayout);
        panelDrawingTool2DLayout.setHorizontalGroup(
            panelDrawingTool2DLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDrawingTool2DLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(panelDrawingTool2DLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(Seperator)
                    .addComponent(button_Shape, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(button_Polygon, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(button_Line, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(button_Transform, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(0, 0, 0))
        );
        panelDrawingTool2DLayout.setVerticalGroup(
            panelDrawingTool2DLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDrawingTool2DLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(button_Line, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(button_Polygon, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(button_Shape, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 174, Short.MAX_VALUE)
                .addComponent(Seperator, javax.swing.GroupLayout.PREFERRED_SIZE, 4, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(button_Transform, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        panelDrawingTool.add(panelDrawingTool2D, "Card2D");
        panelDrawingTool2D.getAccessibleContext().setAccessibleName("");

        panelDrawingTool3D.setName("Card3D"); // NOI18N

        button_3DShape.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/3DShape_Rectangular.png"))); // NOI18N
        button_3DShape.setFocusPainted(false);
        button_3DShape.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        button_3DShape.setMaximumSize(new java.awt.Dimension(148, 116));
        button_3DShape.setMinimumSize(new java.awt.Dimension(148, 116));
        button_3DShape.setPreferredSize(new java.awt.Dimension(148, 116));
        button_3DShape.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        javax.swing.GroupLayout panelDrawingTool3DLayout = new javax.swing.GroupLayout(panelDrawingTool3D);
        panelDrawingTool3D.setLayout(panelDrawingTool3DLayout);
        panelDrawingTool3DLayout.setHorizontalGroup(
            panelDrawingTool3DLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelDrawingTool3DLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(button_3DShape, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
        panelDrawingTool3DLayout.setVerticalGroup(
            panelDrawingTool3DLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDrawingTool3DLayout.createSequentialGroup()
                .addComponent(button_3DShape, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 487, Short.MAX_VALUE))
        );

        panelDrawingTool.add(panelDrawingTool3D, "Card3D");

        javax.swing.GroupLayout panel_DrawingToolLayout = new javax.swing.GroupLayout(panel_DrawingTool);
        panel_DrawingTool.setLayout(panel_DrawingToolLayout);
        panel_DrawingToolLayout.setHorizontalGroup(
            panel_DrawingToolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_DrawingToolLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_DrawingToolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelDrawingTool, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panel_SelectCoordinate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        panel_DrawingToolLayout.setVerticalGroup(
            panel_DrawingToolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_DrawingToolLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(panel_SelectCoordinate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelDrawingTool, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addGap(0, 0, 0)
                .addComponent(panel_DrawingArea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        panel_DrawingLayout.setVerticalGroup(
            panel_DrawingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_DrawingLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(panel_DrawingArea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(panel_DrawingLayout.createSequentialGroup()
                .addComponent(panel_DrawingTool, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        label_CoordIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/moveCursor.png"))); // NOI18N
        label_CoordIcon.setToolTipText("Mouse coord");
        label_CoordIcon.setFocusable(false);
        label_CoordIcon.setInheritsPopupMenu(false);
        label_CoordIcon.setRequestFocusEnabled(false);
        label_CoordIcon.setVerifyInputWhenFocusTarget(false);

        label_CoordValue.setText("X: 100   Y: 100");
        label_CoordValue.setFocusable(false);

        javax.swing.GroupLayout panel_CoordinateCursorLayout = new javax.swing.GroupLayout(panel_CoordinateCursor);
        panel_CoordinateCursor.setLayout(panel_CoordinateCursorLayout);
        panel_CoordinateCursorLayout.setHorizontalGroup(
            panel_CoordinateCursorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_CoordinateCursorLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(label_CoordIcon)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
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
        label_ToolTip.setFocusable(false);
        label_ToolTip.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        javax.swing.GroupLayout panel_StatusBarLayout = new javax.swing.GroupLayout(panel_StatusBar);
        panel_StatusBar.setLayout(panel_StatusBarLayout);
        panel_StatusBarLayout.setHorizontalGroup(
            panel_StatusBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_StatusBarLayout.createSequentialGroup()
                .addComponent(panel_CoordinateCursor, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jSeparator8, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(label_ToolTip, javax.swing.GroupLayout.PREFERRED_SIZE, 1161, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
        );
        panel_StatusBarLayout.setVerticalGroup(
            panel_StatusBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_StatusBarLayout.createSequentialGroup()
                .addComponent(panel_CoordinateCursor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(label_ToolTip, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(panel_StatusBarLayout.createSequentialGroup()
                .addComponent(jSeparator8)
                .addGap(3, 3, 3))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator3)
            .addComponent(jSeparator1)
            .addComponent(panel_Operation, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panel_Control, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panel_Drawing, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(panel_StatusBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panel_Operation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panel_Control, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(panel_Drawing, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(panel_StatusBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public DrawingPanel getDrawingPanel() {
        return (DrawingPanel) panel_DrawingArea;
    }

    /**
     * Class handling mouse press at button drawing tool.
     */
    private class CustomMouseAtButtonDrawingTool implements MouseListener {

        private boolean mousePressed;
        private JButton button;
        private JPopupMenu popMenu;
        private SettingConstants.DrawingToolMode savedMode;

        public CustomMouseAtButtonDrawingTool(SettingConstants.DrawingToolMode savedMode, JButton button, JPopupMenu popMenu) {
            this.savedMode = savedMode;
            this.button = button;
            this.popMenu = popMenu;
            this.mousePressed = false;
        }

        @Override
        public void mouseClicked(MouseEvent event) {
            if (SwingUtilities.isLeftMouseButton(event)) {
                String type = this.savedMode.name().substring(8, 11);
                switch (type) {
                    case "POL":
                        setSelectedToolMode(savedPolygonMode);

                        break;
                    case "SHA":
                        setSelectedToolMode(savedShapeMode);

                        break;
                    case "3DS":
                        setSelectedToolMode(saved3DShapeMode);

                        break;
                    case "TRA":
                        setSelectedToolMode(savedTransformMode);

                        break;
                    case "LIN":
                        setSelectedToolMode(savedLineMode);

                        break;
                }
            } else if (SwingUtilities.isRightMouseButton(event)) {
                Ultility.showPopMenuOfButton(this.button, this.popMenu);
                hideTooltip();
                this.button.requestFocus();
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    mousePressed = true;
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }

                    if (mousePressed) {
                        Ultility.showPopMenuOfButton(button, popMenu);
                        hideTooltip();
                    } else {
                        if (button == button_Transform) {
                            promptTranformInput();
                        }
                    }
                }
            }).start();
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            mousePressed = false;
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            if (!popMenu.isShowing()) {
                showTooltip("Right click or press until menu shown up.");
            }
        }

        @Override
        public void mouseExited(MouseEvent e) {

            boolean isInTheList = false;
            for (DrawingToolMode element : ShiftException) {
                if (getDrawingPanel().getSelectedToolMode() == element) {
                    isInTheList = true;
                }
            }
            if (!isInTheList) {
                hideTooltip();
            } else {
                showTooltip(getDrawingPanel().getSelectedToolMode().toolTip);
            }
        }
    }

    /**
     * Showing tool tip at status bar.
     *
     * @param toolTipText
     */
    private void showTooltip(String toolTipText) {
        label_ToolTip.setIcon(new ImageIcon(getClass().getResource("/img/tooltip.png")));
        label_ToolTip.setText(toolTipText);
        label_ToolTip.repaint();
    }

    /**
     * Hide tool tip at status bar.
     */
    private void hideTooltip() {
        label_ToolTip.setIcon(null);
        label_ToolTip.setText("");
        label_ToolTip.repaint();
    }

    /**
     * Class is used for handling user color choosing.
     */
    private class CustomSavedColorButtonEventHandling implements ActionListener {

        JButton button;

        public CustomSavedColorButtonEventHandling(JButton button) {
            this.button = button;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            ((DrawingPanel) panel_DrawingArea).setSelectedColor(button.getBackground());
            button_CurrentColor.setBackground(button.getBackground());
        }
    }

    /**
     * Class handling click at menu item at pop-up menu.
     */
    private class CustomMenuItemChooseAction implements ActionListener {

        private SettingConstants.DrawingToolMode selectedMode;
        private String icon32pxPath;
        private JButton button;

        public CustomMenuItemChooseAction(SettingConstants.DrawingToolMode savedMode,
                SettingConstants.DrawingToolMode selectedMode, String icon32pxPath,
                JButton button) {
            this.selectedMode = selectedMode;
            this.icon32pxPath = icon32pxPath;
            this.button = button;
        }

        @Override
        public void actionPerformed(ActionEvent event) {
            String type = this.selectedMode.name().substring(8, 11);
            switch (type) {
                case "LIN":
                    savedLineMode = this.selectedMode;
                    break;
                case "POL":
                    savedPolygonMode = this.selectedMode;
                    break;
                case "SHA":
                    savedShapeMode = this.selectedMode;
                    break;
                case "3DS":
                    saved3DShapeMode = this.selectedMode;
                    break;
                case "TRA":
                    savedTransformMode = this.selectedMode;
                    break;
            }
            if (setSelectedToolMode(this.selectedMode)) {
                this.button.setIcon(new ImageIcon(getClass()
                        .getResource(this.icon32pxPath))
                );
            }

            promptTranformInput();
        }
    }

    public void promptTranformInput() {
        DrawingToolMode selectedMode = getDrawingPanel().getSelectedToolMode();

        switch (selectedMode) {
            case DRAWING_TRANSFORM_ROTATION: {
                Rotation2DInput rotationInputDialog = new Rotation2DInput(this);
                rotationInputDialog.setVisible(true);
                break;
            }
            case DRAWING_TRANSFORM_SYMMETRY: {
                JDialog symmetryInputDialog = new Symmetry2DInput(this);
                symmetryInputDialog.setVisible(true);
                break;
            }
        }
    }

    /**
     * Class handling coordinate system selection.
     */
    private class CustomChangeCoordSystemAction implements ActionListener {

        private SettingConstants.CoordinateMode selectedCoordMode;

        public CustomChangeCoordSystemAction(SettingConstants.CoordinateMode selectedCoordMode) {
            this.selectedCoordMode = selectedCoordMode;
        }

        @Override
        public void actionPerformed(ActionEvent event) {
            SettingConstants.CoordinateMode currCoordMode
                    = getDrawingPanel().getCoordinateMode();

            // If user click the same mode, do nothing
            if (currCoordMode == this.selectedCoordMode) {
                return;
            }

            /*
                Reset visual option.
             */
            checkBox_showGridlines.setSelected(SettingConstants.DEFAULT_VISUAL_SHOW_GRID);
            checkBox_showCoordinate.setSelected(SettingConstants.DEFAULT_VISUAL_SHOW_COORDINATE);

            /*
                Change coordinate system.
             */
            getDrawingPanel().setCoordinateMode(this.selectedCoordMode);

            if (button_2DMode.isSelected()) {
                showCardDrawingTool(panelDrawingTool2D.getName());
            } else if (button_3DMode.isSelected()) {
                showCardDrawingTool(panelDrawingTool3D.getName());
            }
        }
    }

    /**
     * Show panel containing drawing tool for current coordinate system.
     *
     * @param cardName
     */
    private void showCardDrawingTool(String cardName) {
        CardLayout layout = (CardLayout) (panelDrawingTool.getLayout());
        layout.show(panelDrawingTool, cardName);
        panelDrawingTool.repaint();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        try {
            // Set windows style
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    new MainFrame().setVisible(true);
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
    private javax.swing.JButton button_3DShape;
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
    private javax.swing.JButton button_CurrentColor;
    private javax.swing.JButton button_Eraser;
    private javax.swing.JButton button_FillColor;
    private javax.swing.JButton button_Line;
    private javax.swing.JButton button_OpenFile;
    private javax.swing.JButton button_Polygon;
    private javax.swing.JButton button_Redo;
    private javax.swing.JButton button_SaveFile;
    private javax.swing.JButton button_Select;
    private javax.swing.JButton button_Shape;
    private javax.swing.JButton button_Transform;
    public static javax.swing.JButton button_Undo;
    private javax.swing.JCheckBox checkBox_showCoordinate;
    private javax.swing.JCheckBox checkBox_showGridlines;
    private javax.swing.JComboBox<String> comboBox_StyleLine;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator11;
    private javax.swing.JSeparator jSeparator12;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JLabel label_CoordIcon;
    private javax.swing.JLabel label_CoordValue;
    private javax.swing.JLabel label_CurrentColor;
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
    private javax.swing.JPanel panelDrawingTool;
    private javax.swing.JPanel panelDrawingTool2D;
    private javax.swing.JPanel panelDrawingTool3D;
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
