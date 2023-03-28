package controller.file;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.imageio.ImageIO;

import controller.file.IFileFunctions;
import model.image.IImage;
import model.image.IOImage;
import model.Pixel;

/**
 * Represents the functions to be used on a file type represented in the java ImageIO class.
 * Allows for the loading and saving of images into an IImage to preform various transformations on.
 */
public class IOFunctions implements IFileFunctions {
  private IImage img;

  /**
   * The constructor of the {@code IOFunctions} class, used to read and write any image
   * files represented in the ImageIO library, initializes the IImage img field to null since
   * it is only reassigned in the readImg method.
   */
  public IOFunctions() {
    img = null;
  }

  /**
   * The convenience constructor of the {@code IOFunctions class}, for use in testing various
   * methods of the class without needing to rely on a file.
   */
  public IOFunctions(IImage img) {
    this.img = img;
  }

  @Override
  public void readImg(String filePath) throws IOException {

    File file = new File(filePath);
    BufferedImage img = ImageIO.read(file);

    Pixel[][] pixels = new Pixel[img.getHeight()][img.getWidth()];

    for (int i = 0; i < img.getHeight(); i++) {
      for (int j = 0; j < img.getWidth(); j++) {
        //Retrieving contents of a pixel
        int pixel = img.getRGB(j, i);
        //Creating a Color object from pixel value
        Color color = new Color(pixel, true);
        //Retrieving the R G B values
        int red = color.getRed();
        int green = color.getGreen();
        int blue = color.getBlue();
        pixels[i][j] = new Pixel(red, green, blue);
      }
    }
    this.img = new IOImage(img.getWidth(), img.getHeight(), pixels);
  }

  @Override
  public void save(String pathname, IImage img) throws IOException {
    ArrayList<String> imgFormats = new ArrayList<>(Arrays.asList(ImageIO.getWriterFormatNames()));
    // String type2 = pathname.split("\\.")[1];
    String type2 = pathname.substring(pathname.lastIndexOf(".") + 1);

    if (imgFormats.contains(type2)) {
      File file = new File(pathname);
      ImageIO.write(img.getBufferedImg(), type2, file);

    } else {
      throw new IllegalArgumentException("Image type not supported");
    }
  }

  @Override
  public IImage getImg() {
    return this.img;
  }
}
