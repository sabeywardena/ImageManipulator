package model.operations;

import model.Pixel;
import model.RGBChannel;

/**
 * Represents an operation to display the {@code Luma} of a given 2D array of Pixels. Luma is the
 * brightness in an image and is displayed as a calculation of the rgb channels, represented as 0
 * .2126r + 0.7152g + 0.0722b, with rgb representing red green and blue respectively.
 *
 * @throws IllegalArgumentException if 2D Array is null
 */
public class Luma extends AEdits {

  /**
   * Constructs an instance of {@code Luma} when given a 2D array of Pixels.
   *
   * @param pixels the array to display the luma of
   * @throws IllegalArgumentException if the given array is null
   */
  public Luma(Pixel[][] pixels) throws IllegalArgumentException {
    super(pixels);
  }

  public Luma(Pixel[][] pixels, Pixel[][] masking) throws IllegalArgumentException {
    super(pixels, masking);
  }

  /**
   * Produces a 2D array of Pixels with the rgb channels altered to reflect the Luma of the given
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
          int luma = (int) (Math.round(
                  0.2126 * (this.matrix[i][j].getChannel(RGBChannel.RED))
                          + 0.7152 * (this.matrix[i][j].getChannel(RGBChannel.GREEN))
                          + 0.0722 * (this.matrix[i][j].getChannel(RGBChannel.BLUE))));

          newPix[i][j] = new Pixel(luma);
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
