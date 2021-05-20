package control.io;

import control.SettingConstants;
import static control.SettingConstants.RECT_SIZE;
import control.util.Ultility;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import control.myawt.SKPoint2D;
import view.DrawingPanel;
import view.MainFrame;

/**
 * Class handling file io.
 *
 * @author DELL
 */
public class FileController {

    public static void saveFile(String outputFileName, Color[][] boardColor, String[][] boardCoord) {
        int boardHeight = boardColor.length;
        int boardWidth = boardColor[0].length;

        BufferedImage bufferedImage = new BufferedImage(boardWidth*5, boardHeight*5, BufferedImage.TYPE_INT_RGB);

        for (int i = 0; i < bufferedImage.getWidth(); i++) {
            for (int j = 0; j < bufferedImage.getHeight(); j++) {
                for (int tempI = 0; tempI <5; tempI++) {
                    for (int tempJ =0; tempJ <5; tempJ++) {
                        if (Ultility.checkValidPoint(boardColor, j, i )) {
                            bufferedImage.setRGB(
                                    j *5 + tempJ,
                                    i *5 + tempI,
                                    boardColor[i][j].getRGB()
                            );
                        }
                    }
                }
            }
        }

        // Append file extension
        if (!outputFileName.toLowerCase().endsWith(".png")) {
            outputFileName += ".png";
        }

        try {
            FileWriter fw = new FileWriter(outputFileName.substring(0, outputFileName.length() - 4) + ".dat");

            for (int i = 0; i < boardHeight; i++) {
                for (int j = 0; j < boardWidth; j++) {
                    if (boardCoord[i][j] != null) {
                        String[] k = boardCoord[i][j].split(", ");

                        String temp1 = k[0].substring(1, k[0].length());

                        String temp2 = k[1].substring(0, k[1].length() - 1);

                        fw.write(temp1 + " " + temp2 + " ");
                    }
                }
            }

            fw.close();
        } catch (IOException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            File newFile = new File(outputFileName);
            ImageIO.write(bufferedImage, "PNG", newFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void openFile(String inputFileName, Color[][] boardColor, String[][] boardCoord) {
        int boardHeight = boardColor.length;
        int boardWidth = boardColor[0].length;

        try {
            BufferedImage myFile = ImageIO.read(new File(inputFileName));

            if (myFile.getHeight() == boardHeight*5 && myFile.getWidth() == boardWidth*5) {

                for (int i = 0; i < boardWidth; i++) {
                    for (int j = 0; j < boardHeight; j++) {
                        if (Ultility.checkValidPoint(boardColor, i,j)) {
                            Color c = new Color(myFile.getRGB(i*5 ,j*5 ), true);

                            boardColor[j][i] = c;
                        }
                    }
                }
              
                

                String filenameSavedCoor = inputFileName.substring(0, inputFileName.length() - 4) + ".dat";

                FileReader fr = new FileReader(filenameSavedCoor);

                String text = "";

                int k = fr.read();

                while (k != -1) {
                    text += (char) k;
                    k = fr.read();
                }

                fr.close();

                String[] text2 = text.trim().split(" ");

                int[] tempCoor = new int[text2.length];

                for (int i = 0; i < text2.length; i++) {
                    tempCoor[i] = Integer.parseInt(text2[i]);
                }

                for (k = 0; k < tempCoor.length; k += 2) {
                    SKPoint2D point = new SKPoint2D(
                            tempCoor[k] + (SettingConstants.COORD_X_O / SettingConstants.RECT_SIZE),
                            -tempCoor[k + 1] + (SettingConstants.COORD_Y_O / SettingConstants.RECT_SIZE)
                    );

                    point.saveCoord(boardCoord);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Wrong Image");
            }
        } catch (IOException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}