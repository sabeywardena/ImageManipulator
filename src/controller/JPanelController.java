package controller;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import controller.file.IFileFunctions;
import controller.file.IOFunctions;
import controller.file.PPMFunctions;
import model.Histogram;
import model.RGBChannel;
import model.image.IImage;
import model.operations.*;
import view.IMEFrame;

/**
 * The {@code JPanelController} class represents the controller that utilizes a GUI to display
 * the data. Here, scripting is removed, and instead the user relies on buttons to perform
 * operations on the images.
 *
 * <p>Includes the ability to create and represent the data of an images channels in a histogram, as
 * reflected in the view.
 */
public class JPanelController implements ActionListener {
  private final HashMap<String, AEdits> knownCmds = new HashMap<>();

  HashMap<String, IImage> files = new HashMap<>();
  private int brightenFactor;
  private int sWidth;
  private int sHeight;

  IMEFrame view;
  IImage edited;

  /**
   * Constructs a JPanelController given an IMEFrame to allow for usage of a GUI.
   * @param v the GUI for our program
   * @throws IllegalArgumentException if the given view is null
   */
  public JPanelController(IMEFrame v) throws IllegalArgumentException {
    brightenFactor = 0;
    sWidth = 0;
    sHeight = 0;
    if (v == null) {
      throw new IllegalArgumentException("View object is null.");
    }
    this.view = v;
    view.setListener(this);
    edited = null;
  }

  @Override
  public void actionPerformed(ActionEvent fileCommand) {
    // TODO Auto-generated method stub
    switch (fileCommand.getActionCommand()) {
      case "Operations":
        if (fileCommand.getSource() instanceof JComboBox) {
          JComboBox<String> box = (JComboBox<String>) fileCommand.getSource();
          String i = (String) box.getSelectedItem();
          operationToView(i);
        }
        break;

      case "Open file": {
        try {
          loadToView();
          int[] r = new Histogram(edited.getPixels()).rgbData(RGBChannel.RED);
          int[] g = new Histogram(edited.getPixels()).rgbData(RGBChannel.GREEN);
          int[] b = new Histogram(edited.getPixels()).rgbData(RGBChannel.BLUE);
          int[] i = new Histogram(edited.getPixels()).intensityData();
          view.displayHistogram(r, g, b, i);
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
      }
      break;

      case "Save file": {
        saveToView();
      }
      break;
      default:
        // a valid actionCommand wasn't given
        break;
    }
  }

  private void operationToView(String i) throws NumberFormatException {
    if (i.equals("brighten") || i.equals("darken")) {
      String factor = JOptionPane.showInputDialog("Please enter an integer from 1 - 255");
      try {
        brightenFactor = Integer.parseInt(factor);
      }
      catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(view, "Not an integer.", "Error",
            JOptionPane.ERROR_MESSAGE);
      }
    }
    else if (i.equals("downscale")) {
      String w = JOptionPane.showInputDialog("Please enter an integer to be the new width.");
      String h = JOptionPane.showInputDialog("Please enter an integer to be the new height.");
      try {
        sWidth = Integer.parseInt(w);
        sHeight = Integer.parseInt(h);
      }
      catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(view, "Not an integer.", "Error",
                JOptionPane.ERROR_MESSAGE);
      }
    }
    if (edited != null) {
      setHashMap(edited);
      edited = edited.upgradeImage(knownCmds.get(i).operation());
      view.updateImage(edited.getBufferedImg());
      int[] r = new Histogram(edited.getPixels()).rgbData(RGBChannel.RED);
      int[] g = new Histogram(edited.getPixels()).rgbData(RGBChannel.GREEN);
      int[] b = new Histogram(edited.getPixels()).rgbData(RGBChannel.BLUE);
      int[] intense = new Histogram(edited.getPixels()).intensityData();
      view.displayHistogram(r, g, b, intense);
    }
    else {
      JOptionPane.showMessageDialog(view, "No image loaded.", "Error",
          JOptionPane.ERROR_MESSAGE);
    }

  }

  private void loadToView() throws IOException {
    String filePath;
    IFileFunctions fileFunc;
    final JFileChooser fChooser = new JFileChooser(".");
    FileNameExtensionFilter filter = new FileNameExtensionFilter(
        "Image Filter", "jpg", "gif", "png", "jpeg");
    fChooser.setFileFilter(filter);
    int retValue = fChooser.showOpenDialog(view);
    if (retValue == JFileChooser.APPROVE_OPTION) {
      File f = fChooser.getSelectedFile();
      filePath = f.getAbsolutePath();
      System.out.println(f.getAbsolutePath());
      System.out.println(f.getPath());
      System.out.println(f.getName().substring(0, f.getName().lastIndexOf(".")));
      view.addTextLoad(filePath);
      view.renderImage(filePath);

      if (filePath.substring(filePath.lastIndexOf(".") + 1)
          .equalsIgnoreCase("ppm")) {
        fileFunc = new PPMFunctions();
      } else {
        fileFunc = new IOFunctions();
      }
      // the reader class
      fileFunc.readImg(filePath);
      edited = fileFunc.getImg();
    }
  }

  private void saveToView() {
    IFileFunctions fileFunc;

    final JFileChooser fChooser = new JFileChooser(".");
    int retValue;
    retValue = fChooser.showSaveDialog(view);
    if (retValue == JFileChooser.APPROVE_OPTION) {
      File f = fChooser.getSelectedFile();
      String filePath = f.getAbsolutePath();
      view.addSaveLoad(filePath);

      if (filePath.substring(filePath.indexOf(".") + 1)
          .equalsIgnoreCase("ppm")) {
        fileFunc = new PPMFunctions();
      } else {
        fileFunc = new IOFunctions();
      }

      try {
        fileFunc.save(filePath, edited);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }
  }

  private void setHashMap(IImage model) {
    knownCmds.put("value component", new Value(model.getPixels()));
    knownCmds.put("horizontal flip", new Flip(model.getPixels(), true));
    knownCmds.put("vertical flip", new Flip(model.getPixels(), false));
    knownCmds.put("brighten", new ChangeBrightness(model.getPixels(), brightenFactor,
        model.getMaxVal(), true));
    knownCmds.put("darken", new ChangeBrightness(model.getPixels(), brightenFactor,
        model.getMaxVal(), false));
    knownCmds.put("luma component", new Luma(model.getPixels()));
    knownCmds.put("intensity component", new Intensity(model.getPixels()));
    knownCmds.put("red component", new RGBGreyscale(model.getPixels(), RGBChannel.RED));
    knownCmds.put("green component", new RGBGreyscale(model.getPixels(), RGBChannel.GREEN));
    knownCmds.put("blue component", new RGBGreyscale(model.getPixels(), RGBChannel.BLUE));
    knownCmds.put("blur", new Blur(model.getPixels()));
    knownCmds.put("sharpen", new Sharpen(model.getPixels()));
    knownCmds.put("greyscale", new Greyscale(model.getPixels()));
    knownCmds.put("sepia", new Sepia(model.getPixels()));
    knownCmds.put("downscale", new Downscaling(model.getPixels(), sWidth, sHeight));
  }
}

