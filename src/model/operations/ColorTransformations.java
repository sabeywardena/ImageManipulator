package model.operations;

import model.Pixel;
import model.RGBChannel;

/**
 * Represents an abstract class that extends AEdits, used to create color transforming filters on
 * images. Utilizes an additional field "Kernel" to hold a matrix to be used for filtering.
 */
public abstract class ColorTransformations extends AEdits {

  double[][] kernel;

  public ColorTransformations(Pixel[][] matrix) throws IllegalArgumentException {
    super(matrix);
  }

  public ColorTransformations(Pixel[][] matrix, Pixel[][] masking) throws IllegalArgumentException {
    super(matrix, masking);
  }

  @Override
  public Pixel[][] operation() {
    Pixel[][] newPixels = new Pixel[matrix.length][matrix[0].length];
    for (int i = 0; i < matrix.length; i++) {
      for (int j = 0; j < matrix[0].length; j++) {
        if (!mask && masking[i][j] != null) {
          masked(masking[i][j]);
        }
        if (mask) {
          Pixel pix = matrix[i][j];
          double newRed = (pix.getChannel(RGBChannel.RED) * kernel[0][0])
                  + (pix.getChannel(RGBChannel.GREEN) * kernel[0][1])
                  + (pix.getChannel(RGBChannel.BLUE) * kernel[0][2]);

          double newGreen = (pix.getChannel(RGBChannel.RED) * kernel[1][0])
                  + (pix.getChannel(RGBChannel.GREEN) * kernel[1][1])
                  + (pix.getChannel(RGBChannel.BLUE) * kernel[1][2]);

          double newBlue = (pix.getChannel(RGBChannel.RED) * kernel[1][0])
                  + (pix.getChannel(RGBChannel.GREEN) * kernel[1][1])
                  + (pix.getChannel(RGBChannel.BLUE) * kernel[1][2]);

          newPixels[i][j] = new Pixel(fixRange(newRed), fixRange(newGreen), fixRange(newBlue));
        }
        else {
          newPixels[i][j] = matrix[i][j];
        }
        if (masking[i][j] != null) {
          mask = false;
        }
      }
    }
    return newPixels;
  }

  private int fixRange(double c) {
    if (c < 0) {
      return 0;
    } else if (c > 255) {
      return 255;
    } else {
      return (int) c;
    }
  }
}
