package model.image;

import java.awt.image.BufferedImage;

import model.Pixel;
import model.RGBChannel;

/**
 * The {@code IOImage} class represents the data of an Image that can be represented in a
 * file type supported by Java's ImageIO library. Contains fields to support the width, height,
 * max value, and a matrix of pixels.
 */
public class IOImage implements IImage {
  private final int width;
  private final int height;
  private final int maxVal = 255;
  private final Pixel[][] pixels;

  /**
   * Constructs an instance of an {@code IOImage} to represent the data in an image
   * represented by the ImageIO class.
   * @param width the width of the image as an int
   * @param height the height of the image as an int
   * @param pixels a 2D array of pixels representing the pixels in the image
   * @throws IllegalArgumentException if any parameters are null
   */
  public IOImage(int width, int height, Pixel[][] pixels)
      throws IllegalArgumentException {
    if (width < 0 || height < 0 || (pixels == null)) {
      throw new IllegalArgumentException("One or more parameters are invalid.");
    }
    this.width = width;
    this.height = height;
    this.pixels = pixels;
  }

  @Override
  public IImage upgradeImage(Pixel[][] matrix) {
    return new IOImage(this.width, this.height, matrix);
  }

  @Override
  public Pixel[][] getPixels() {
    return this.pixels;
  }

  @Override
  public int getMaxVal() {
    return this.maxVal;
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
    System.out.println(pixels);
    try {
      outputImage = new BufferedImage(this.pixels[0].length,
          this.pixels.length,
          BufferedImage.TYPE_INT_RGB);
    } catch (Exception e) {
      System.out.println(e);
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
}
