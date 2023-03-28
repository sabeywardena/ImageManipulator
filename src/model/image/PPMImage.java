package model.image;

import java.awt.image.BufferedImage;

import model.Pixel;
import model.RGBChannel;

/**
 * A class used to represent a PPM image file.
 *
 * <p></p>
 * A {@code PPMImage} is a type of file encoded in human-readable text, with a header
 * representing
 * the file's identifier, with its identifying file number, width, height, and then the maximum
 * value of the rgb channels of each individual Pixel in the files image.
 */
public class PPMImage implements IImage {

  private static final String MAGIC_NUMBER = "P3";
  private final int width;
  private final int height;
  private final int maxVal;
  private final Pixel[][] pixels;

  /**
   * The default constructor of the {@code PPMImage} class, used to construct a standard {@code
   * PPMImage} assuming none of the parameters are null or invalid.
   *
   * @param width  the width of the {@code PPMImage}
   * @param height the height of the {@code PPMImage}
   * @param maxVal the maximum value any rgb channel can be in a single pixel
   * @param pixels the matrix of pixels representing of the {@code PPMImage}
   * @throws IllegalArgumentException if any parameters are null
   * @throws IllegalArgumentException if any primitive types are less than 0
   * @throws IllegalArgumentException if the max value is less than 0 or greater than 255
   */
  public PPMImage(int width, int height, int maxVal, Pixel[][] pixels)
      throws IllegalArgumentException {
    if (width < 0 || height < 0 || maxVal < 0 || maxVal > 255 || (pixels == null)) {
      throw new IllegalArgumentException("One or more parameters are invalid.");
    }
    this.width = width;
    this.height = height;
    this.maxVal = maxVal;
    this.pixels = pixels;
  }

  @Override
  public PPMImage upgradeImage(Pixel[][] matrix) throws IllegalArgumentException {
    if (matrix == null) {
      throw new IllegalArgumentException("Given matrix of pixels is null.");
    }
    return new PPMImage(this.width, this.height, this.maxVal, matrix);
  }

  @Override
  public Pixel[][] getPixels() {
    return this.pixels;
  }

  @Override
  public int getWidth() {
    return this.width;
  }

  @Override
  public int getHeight() {
    return this.height;
  }

  @Override
  public BufferedImage getBufferedImg() throws IllegalArgumentException {
    BufferedImage outputImage;
    try {
      outputImage = new BufferedImage(this.pixels[0].length,
          this.pixels.length,
          BufferedImage.TYPE_INT_RGB);
    } catch (Exception e) {
      throw new IllegalArgumentException("Empty Image");
    }
    for (int i = 0; i < this.pixels.length; i++) {
      for (int j = 0; j < this.pixels[0].length; j++) {
        // Get RGB values
        int red = this.pixels[i][j].getChannel(RGBChannel.RED);
        int green = this.pixels[i][j].getChannel(RGBChannel.GREEN);
        int blue = this.pixels[i][j].getChannel(RGBChannel.BLUE);
        int rgb = red;
        rgb = (rgb << 8) + green;
        rgb = (rgb << 8) + blue;
        outputImage.setRGB(j, i, rgb);
      }
    }
    return outputImage;
  }

  @Override
  public int getMaxVal() {
    return this.maxVal;
  }
}
