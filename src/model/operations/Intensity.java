package model.operations;

import model.Pixel;
import model.RGBChannel;

/**
 * Represents an operation to display the {@code Intensity} of a given 2D array of Pixels. Intensity
 * is the overall average of the three rgb channels in a Pixel.
 *
 * @throws IllegalArgumentException if 2D Array is null
 */
public class Intensity extends AEdits {

  /**
   * Constructs an instance of {@code Intensity} when given a 2D array of Pixels.
   *
   * @param pixels the array to display the intensity of
   * @throws IllegalArgumentException if the given array is null
   */
  public Intensity(Pixel[][] pixels) throws IllegalArgumentException {
    super(pixels);
  }

  public Intensity(Pixel[][] pixels, Pixel[][] masking) throws IllegalArgumentException {
    super(pixels, masking);
  }

  /**
   * Represents an operation to display the {@code Intensity} of a given 2D array of Pixels.
   * Intensity is the overall average of the three rgb channels in a Pixel.
   *
   * @throws IllegalArgumentException if 2D Array is null
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
          int averageVal = (this.matrix[i][j].getChannel(RGBChannel.RED)
                  + this.matrix[i][j].getChannel(RGBChannel.GREEN)
                  + this.matrix[i][j].getChannel(RGBChannel.BLUE)) / 3;
          newPix[i][j] = new Pixel(averageVal);
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
