package model.operations;

import model.Pixel;
import model.RGBChannel;

/**
 * ChangeBrightness is a class that, with a matrix of pixels
 * from an image, either brightens or darkens them, by a given
 * positive constant, a factor. For any pixel where darkening by the
 * factor would cause any channel to drop below zero, the pixel is
 * instead darkened by the value making the smallest channel exactly
 * zero. Similarly, for any pixel where brightening by the factor would
 * bring any channel above the given max value, the pixel's channels would
 * instead all be incremented by the value bringing the largest
 * channel to exactly the max.
 *
 * @author Oishani Chatterji
 * @author Sarah Abeywardena
 */

public class ChangeBrightness extends AEdits {

  private int factor;

  private boolean brightened;

  /**
   * Takes in the original pixels in an image before being edited,
   * the max value for channels in pixels in the image, which channels
   * in pixels in the image shouldn't be brightened beyond, factor
   * by which the pixels are either brightened or darkened, and a
   * boolean expressing whether the image is brightened or darkened.
   *
   * @param matrix     the original pixels in an image
   * @param factor     the factor by which an image's pixels are either
   *                   brightened or darkened
   * @param max        the max value beyond which channels in pixels in the
   *                   image cannot be brightened
   * @param brightened whether the image is being brightened or darkened
   * @throws IllegalArgumentException because the factor should be positive
   */
  public ChangeBrightness(Pixel[][] matrix, int factor, int max, boolean brightened)
      throws IllegalArgumentException {
    super(matrix, max);
    if (factor < 0) {
      throw new IllegalArgumentException("factor should be a positive constant");
    }
    this.factor = factor;
    this.brightened = brightened;
  }

  public ChangeBrightness(Pixel[][] matrix, int factor, int max, boolean brightened, Pixel[][] masking)
          throws IllegalArgumentException {
    super(matrix, masking);
    this.maxValue = max;
    if (factor < 0) {
      throw new IllegalArgumentException("factor should be a positive constant");
    }
    this.factor = factor;
    this.brightened = brightened;
  }

  /**
   * takes in a pixel and produces new pixel brightened or darkened as specified by the instance of
   * this class calling the method. If brightening would bring any of the channel's above the
   * maxValue for this class, the constant the channels are changed by is changed to one that
   * makes the largest channel equal to exactly the max. Similarly, if darkening would bring any
   * of the channel's below zero, the channels are instead changed by a constant bringing the
   * smallest channel to exactly zero.
   *
   * @param p the original pixel to be brightened or darkened
   * @return a new pixel with the original pixel brightened or darkened as specified
   */
  public Pixel pixelBrightness(Pixel p) {
    int f;
    if (this.brightened) {
      if ((p.getChannel(RGBChannel.RED) + factor)
          <= maxValue && (p.getChannel(RGBChannel.GREEN) + factor)
          <= maxValue && (p.getChannel(RGBChannel.BLUE) + factor)
          <= maxValue) {
        f = factor;
      } else {
        f = maxValue - Math.max(p.getChannel(RGBChannel.RED),
            Math.max(p.getChannel(RGBChannel.BLUE), p.getChannel(RGBChannel.GREEN)));
      }
    } else {
      if ((p.getChannel(RGBChannel.RED) - factor) >= 0
          && (p.getChannel(RGBChannel.GREEN) - factor) >= 0
          && (p.getChannel(RGBChannel.BLUE) - factor) >= 0) {
        f = -1 * factor;
      } else {
        f = -1 * Math.min(p.getChannel(RGBChannel.GREEN), (Math.min(p.getChannel(RGBChannel.BLUE),
            p.getChannel(RGBChannel.RED))));
      }
    }
    return new Pixel(p.getChannel(RGBChannel.RED) + f, p.getChannel(RGBChannel.GREEN) + f,
        p.getChannel(RGBChannel.BLUE) + f);
  }

  /**
   * Creates a matrix of pixels with the original pixel's channels brightened or darkened
   * as specified by this instance, changing who much a certain pixel's channels are incremented
   * or decremented based on whether they exceed the maxValue for the instance of this class, or
   * drop below zero.
   *
   * @return a new matrix with the original matrix's (which remains unchanged) pixels brightened or
   *         darkened as specified, and within valid limits
   */
  @Override
  public Pixel[][] operation() {
    Pixel[][] adjusted = new Pixel[matrix.length][matrix[0].length];
    for (int i = 0; i < matrix.length; i++) {
      for (int j = 0; j < matrix[0].length; j++) {
        if (!mask && masking[i][j] != null) {
          masked(masking[i][j]);
        }
        if (mask) {
          adjusted[i][j] = pixelBrightness(matrix[i][j]);
        }
        else {
          adjusted[i][j] = matrix[i][j];
        }
        if (masking[i][j] != null) {
          mask = false;
        }
      }
    }
    return adjusted;
  }
}

