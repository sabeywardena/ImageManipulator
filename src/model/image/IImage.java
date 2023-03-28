package model.image;

import java.awt.image.BufferedImage;

import model.Pixel;

/**
 * An IImage represents an image of any file type. An image must contain a 2D array of Pixels,
 * which represent the Pixels on the screen.
 */
public interface IImage {

  /**
   * Creates a new {@code IImage} with the given 2D Array of Pixels.
   *
   * @param matrix a 2D array of Pixels to represent the pixels that make up an Image
   * @return a new {@code IImage} with its Pixels set to the provided matrix
   * @throws IllegalArgumentException if the given array is null
   */
  IImage upgradeImage(Pixel[][] matrix);

  /**
   * Retrieves the 2D Array of Pixels from this {@code IImage} to represent the images Pixels.
   *
   * @return a 2D Array of Pixels from this {@code @IImage}
   */
  Pixel[][] getPixels();

  /**
   * Retrieves the max value of an image that the rgb channels can be.
   *
   * @return an integer representing the max value
   */
  int getMaxVal();

  /**
   * Retrieves the width of the image in pixels.
   * @return the width of the image in pixels
   */
  int getWidth();

  /**
   * Retrieves the height of the image in pixels.
   * @return the height of the image in pixels
   */
  int getHeight();

  /**
   * Creates a buffered image from the data in the IImage.
   * @return the IImage as a Buffered Image
   * @throws IllegalArgumentException if the BufferedImage would be null
   */
  public BufferedImage getBufferedImg() throws IllegalArgumentException;
}
