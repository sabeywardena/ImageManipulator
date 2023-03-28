package controller;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Scanner;

import controller.file.IFileFunctions;
import controller.file.IOFunctions;
import controller.file.PPMFunctions;
import model.image.IImage;
import model.operations.*;
import model.RGBChannel;

/**
 * IMEController is the main Controller class for this program.
 * It uses a Scanner, and is responsible for  Input and Output
 * and communicating with the View.
 */
public class IMEController  {
  private Scanner s;
  private final HashMap<String, AEdits> knownCmds = new HashMap<>();

  private final HashMap<String, AEdits> maskingCommands = new HashMap<>();
  HashMap<String, IImage> files = new HashMap<>();
  private int brightenFactor;
  private int sWidth;
  private int sHeight;

  public IMEController(Readable r) {
    this.s = new Scanner(r);
    brightenFactor = 0;
    sWidth = 0;
    sHeight = 0;
  }

  private void setHashMap(IImage model) {
    knownCmds.put("value-component", new Value(model.getPixels()));
    knownCmds.put("horizontal-flip", new Flip(model.getPixels(), true));
    knownCmds.put("vertical-flip", new Flip(model.getPixels(), false));
    knownCmds.put("brighten", new ChangeBrightness(model.getPixels(), brightenFactor,
        model.getMaxVal(), true));
    knownCmds.put("darken", new ChangeBrightness(model.getPixels(), brightenFactor,
        model.getMaxVal(), false));
    knownCmds.put("luma-component", new Luma(model.getPixels()));
    knownCmds.put("intensity-component", new Intensity(model.getPixels()));
    knownCmds.put("red-component", new RGBGreyscale(model.getPixels(), RGBChannel.RED));
    knownCmds.put("green-component", new RGBGreyscale(model.getPixels(), RGBChannel.GREEN));
    knownCmds.put("blue-component", new RGBGreyscale(model.getPixels(), RGBChannel.BLUE));
    knownCmds.put("blur", new Blur(model.getPixels()));
    knownCmds.put("sharpen", new Sharpen(model.getPixels()));
    knownCmds.put("greyscale", new Greyscale(model.getPixels()));
    knownCmds.put("sepia", new Sepia(model.getPixels()));
    knownCmds.put("downscale", new Downscaling(model.getPixels(), sWidth, sHeight));
  }

  private void setMasking(IImage model, IImage masking) {
    maskingCommands.put("value-component", new Value(model.getPixels(), masking.getPixels()));
    maskingCommands.put("brighten", new ChangeBrightness(model.getPixels(), brightenFactor,
            model.getMaxVal(), true, masking.getPixels()));
    maskingCommands.put("darken", new ChangeBrightness(model.getPixels(), brightenFactor,
            model.getMaxVal(), false, masking.getPixels()));
    maskingCommands.put("luma-component", new Luma(model.getPixels(), masking.getPixels()));
    maskingCommands.put("intensity-component", new Intensity(model.getPixels(), masking.getPixels()));
    maskingCommands.put("red-component", new RGBGreyscale(model.getPixels(), RGBChannel.RED, masking.getPixels()));
    maskingCommands.put("green-component", new RGBGreyscale(model.getPixels(), RGBChannel.GREEN, masking.getPixels()));
    maskingCommands.put("blue-component", new RGBGreyscale(model.getPixels(), RGBChannel.BLUE, masking.getPixels()));
    maskingCommands.put("blur", new Blur(model.getPixels(), masking.getPixels()));
    maskingCommands.put("sharpen", new Sharpen(model.getPixels(), masking.getPixels()));
    maskingCommands.put("greyscale", new Greyscale(model.getPixels(), masking.getPixels()));
    maskingCommands.put("sepia", new Sepia(model.getPixels(), masking.getPixels()));
  }


  /**
   * Processes various text commands entered while the program is running.
   * @throws IOException if an invalid filepath is given
   */
  public void runCommand() throws IOException {
    // the image being read, saved, or edited
    IImage model;
    // the file reader, either for ppms or images of different formats (jpg, bmp, png, etc.)
    IFileFunctions fileFunc;

    while (s.hasNextLine()) {
      String in = s.next();

      if (in.equalsIgnoreCase("load")) {
        String filepath = s.next();
        if (filepath.substring(filepath.indexOf(".") + 1).equalsIgnoreCase("ppm")) {
          fileFunc = new PPMFunctions();
        }

        else {
          fileFunc = new IOFunctions();
        }
        // the reader class
        fileFunc.readImg(filepath);
        model = fileFunc.getImg();
        String test1 = s.next();
        files.put(test1, model);
      }

      else if (in.equalsIgnoreCase("save")) {
        String filepath = s.next();
        if (filepath.substring(filepath.indexOf(".") + 1).equalsIgnoreCase("ppm")) {
          fileFunc = new PPMFunctions();
        }
        else {
          fileFunc = new IOFunctions();
        }
        String modelKey = s.next().toLowerCase();
        if (files.containsKey(modelKey)) {
          try {
            fileFunc.save(filepath, files.get(modelKey));
          }
          catch (IOException e) {
            throw new RuntimeException(e);
          }
        }
      }

      else {
        if (s.hasNextInt()) {
          if (in.equals("downscale")) {
            sWidth = s.nextInt();
            if (s.hasNextInt()) {
              sHeight = s.nextInt();
            }
          }
          else {
            brightenFactor = s.nextInt();
          }
        }

        String modelKey = s.next().toLowerCase();
        if (files.containsKey(modelKey)) {
          model = files.get(modelKey);
          setHashMap(model);
          String test = s.next();

          if (s.hasNext()) {
            String filepath = s.next();
            if (filepath.substring(filepath.indexOf(".") + 1).equalsIgnoreCase("ppm")) {
              fileFunc = new PPMFunctions();
            } else {
              fileFunc = new IOFunctions();
            }
            // the reader class
            fileFunc.readImg(filepath);
            IImage masking = fileFunc.getImg();
            setMasking(model, masking);
            files.put(test, model.upgradeImage(maskingCommands.get(in).operation()));
          }
          else {
            files.put(test, model.upgradeImage(knownCmds.get(in).operation()));
          }
        }
        else {
          System.out.println("not a valid text command for this program");
        }
      }
    }
  }

  /**
   * the main method for the program.
   * @param args it's the arguments for the main method.
   */

  /**public static void main(String[] args) {
    Readable r = new InputStreamReader(System.in);
    IMEController c = new IMEController(r);

    try {
      c.runCommand();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
*/
}
