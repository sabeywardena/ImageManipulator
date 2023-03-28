package model.operations;

import model.Pixel;
import model.RGBChannel;

/**
 * Represents an operation to display an individual RGB channel in a 2D array of Pixels.
 *
 * @throws IllegalArgumentException if 2D Array is null
 */
public class RGBGreyscale extends AEdits {
  RGBChannel c;

  /**
   * Constructs an instance of {@code RGBGreyscale} when given a 2D array of Pixels.
   *
   * @param pixels the array to display the channel value of
   * @throws IllegalArgumentException if the given array is null
   */
  public RGBGreyscale(Pixel[][] pixels, RGBChannel c) throws IllegalArgumentException {
    super(pixels);
    this.c = c;
  }

  public RGBGreyscale(Pixel[][] pixels, RGBChannel c, Pixel[][] masking) throws IllegalArgumentException {
    super(pixels, masking);
    this.c = c;
  }

  /**
   * Produces a 2D array of Pixels with the rgb channels altered to reflect the color of the
   * channel indicated in the constructor.
   *
   * @return a 2D array of Pixels
   */
  @Override
  public Pixel[][] operation() {
    Pixel[][] newPix = new Pixel[this.matrix.length][this.matrix[0].length];

    for (int i = 0; i < this.matrix.length; i++) {
      for (int j = 0; j < this.matrix[0].length; j++) {
        if (!mask && masking[i][j] != null) {
          masked(masking[i][j]);
        }
        if (mask) {
          int channelVal = this.matrix[i][j].getChannel(c);
          newPix[i][j] = new Pixel(channelVal);
        }
        else {
          newPix[i][j] = matrix[i][j];
        }
        if (masking[i][j] != null) {
          mask = false;
        }
      }
    }
    return newPix;
  }
}
