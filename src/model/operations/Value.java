package model.operations;

import model.Pixel;
import model.RGBChannel;

/**
 * Represents an operation to display the {@code Value} of a given 2D array of Pixels. Value is a
 * representation of the brightness of an image, with each pixel's RGB channels represented as
 * the brightest channel value.
 *
 * @throws IllegalArgumentException if 2D Array is null
 */
public class Value extends AEdits {

  /**
   * Constructs an instance of {@code Value} when given a 2D array of Pixels.
   *
   * @param pixels the array to display the value of
   * @throws IllegalArgumentException if the given array is null
   */
  public Value(Pixel[][] pixels) throws IllegalArgumentException {
    super(pixels);
  }

  public Value(Pixel[][] pixels, Pixel[][] masking) throws IllegalArgumentException {
    super(pixels, masking);
  }

  /**
   * Produces a 2D array of Pixels with the rgb channels altered to reflect the Value of the given
   * matrix in the constructor.
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
          int maxVal = Math.max(this.matrix[i][j].getChannel(RGBChannel.RED),
                  Math.max(this.matrix[i][j].getChannel(RGBChannel.GREEN),
                          this.matrix[i][j].getChannel(RGBChannel.BLUE)));

          newPix[i][j] = new Pixel(maxVal);
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
