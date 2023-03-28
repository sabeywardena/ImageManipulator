package controller.file;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;

import controller.file.IFileFunctions;
import model.image.IImage;
import model.image.PPMImage;
import model.Pixel;
import model.RGBChannel;

/**
 * Represents a class to both:
 * Load in a PPM image file when given its file path, and create a corresponding model
 * representing the data in the image;
 * And to write to a PPM image file when given a path for it to be stored, and a model with the
 * data for the image.
 */
public class PPMFunctions implements IFileFunctions {
  private IImage img;

  /**
   * The constructor of the {@code PPMFunctions} class, used to read and write PPM files and
   * initializes the IImage img field to null since it is only reassigned in the readImg method.
   */
  public PPMFunctions() {
    img = null;
  }

  /**
   * The convenience constructor of the PPMFunctions class, for use in testing various methods of
   * the class without needing to rely on a file.
   */
  public PPMFunctions(IImage img) {
    this.img = img;
  }

  @Override
  public void readImg(String filePath) throws IOException {
    Scanner sc;

    try {
      sc = new Scanner(new FileInputStream(filePath));
    }
    catch (FileNotFoundException e) {
      throw new IOException("File not found.");
    }

    StringBuilder builder = new StringBuilder();
    //read the file line by line, and populate a string. This will throw away any comment lines
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (!s.isEmpty()) {
        if (s.charAt(0) != '#') {
          builder.append(s + System.lineSeparator());
        }
      }
    }

    //now set up the scanner to read from the string we just built
    sc = new Scanner(builder.toString());
    String token;
    token = sc.next();
    if (!token.equals("P3")) {
      throw new IllegalArgumentException("Invalid PPM type. PPM raw types should begin with "
          + "token: P3.");
    }

    int width = sc.nextInt();
    int height = sc.nextInt();
    int maxValue = sc.nextInt();

    Pixel[][] pixels = new Pixel[height][width];
    for (int i = 0;i < height;i++) {
      for (int j = 0;j < width;j++) {
        int r = sc.nextInt();
        int g = sc.nextInt();
        int b = sc.nextInt();
        if (r > maxValue || g > maxValue || b > maxValue) {
          throw new IllegalArgumentException("Channel values exceed max PPM channel value.");
        }
        pixels[i][j] = new Pixel(r,g,b);
      }
    }
    img = new PPMImage(width, height, maxValue, pixels);
  }

  @Override
  public void save(String pathName, IImage img) throws IOException {
    StringBuilder sb = new StringBuilder();
    FileWriter writer = new FileWriter(pathName);
    sb.append("P3");
    sb.append((System.lineSeparator()));
    sb.append(img.getWidth());
    sb.append(" ");
    sb.append(img.getHeight());
    sb.append((System.lineSeparator()));
    sb.append(img.getMaxVal());
    sb.append(System.lineSeparator());

    Pixel[][] pix = img.getPixels();

    for (Pixel[] pixels : pix) {
      for (Pixel p : pixels) {
        sb.append(p.getChannel(RGBChannel.RED));
        sb.append(" ");
        sb.append((p.getChannel(RGBChannel.GREEN)));
        sb.append(" ");
        sb.append((p.getChannel(RGBChannel.BLUE)));
        sb.append(" ");
      }
      sb.append((System.lineSeparator()));
    }
    writer.write(sb.toString());
    writer.close();
  }

  @Override
  public IImage getImg() throws IllegalArgumentException {
    if (img == null) {
      throw new IllegalArgumentException("file not yet read");
    }
    return this.img;
  }
}
