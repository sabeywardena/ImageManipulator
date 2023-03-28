package model.operations;

import model.Pixel;
import model.RGBChannel;

/**
 * Represents an abstract class that extends AEdits, used to create color transforming filters on
 * images. Utilizes an additional field "Kernel" to hold a matrix to be used for filtering.
 */
public abstract class Filter extends AEdits {

  public double[][] kernel;

  /**
   * Constructs an instance of the Filter class given a 2D matrix of pixels.
   * @param matrix the matrix of pixels to edit.
   * @throws IllegalArgumentException if matrix of pixels is null
   */
  public Filter(Pixel[][] matrix) throws IllegalArgumentException {
    super(matrix);
  }

  public Filter(Pixel[][] matrix, Pixel[][] masking) throws IllegalArgumentException {
    super(matrix, masking);
  }

  /**
   * Takes in an {@code RGBChannel} and returns a matrix of doubles, with the corresponding pixels
   * channel transformed by the kernel.
   * @return a matrix of doubles representing the new values for the channel c
   */
  @Override
  public Pixel[][] operation() {
    // new matrix of doubles with the new channel specific values per pixel
    Pixel[][] newColors = new Pixel[matrix.length][matrix[0].length];
    int center = kernel.length / 2;
    // goes through all the rows of pixels
    for (int i = 0; i < matrix.length; i++) {
      // goes through all the colums of pixels per row to adjust the pixel at [i][j]
      for (int j = 0; j < matrix[0].length; j++) {
        if (!mask && masking[i][j] != null) {
          masked(masking[i][j]);
        }
        if (mask) {
          // local variable to keep track of the sum of the KERNEL and corresponding pixels for a
          // particular pixel
          double redSum = 0;
          double greenSum = 0;
          double blueSum = 0;
          for (int kernelI = 0; kernelI < kernel.length; kernelI++) {
            for (int kernelJ = 0; kernelJ < kernel.length; kernelJ++) {
              if (((i - (center - kernelI)) >= 0) && ((i - (center - kernelI)) < matrix.length)
                      && ((j - (center - kernelJ)) >= 0)
                      && ((j - (center - kernelJ)) < matrix[0].length)) {
                redSum += (matrix[i - (center - kernelI)][j - (center - kernelJ)].getChannel(RGBChannel.RED)
                        * kernel[kernelI][kernelJ]);
                greenSum += (matrix[i - (center - kernelI)][j - (center - kernelJ)].getChannel(RGBChannel.GREEN)
                        * kernel[kernelI][kernelJ]);
                blueSum += (matrix[i - (center - kernelI)][j - (center - kernelJ)].getChannel(RGBChannel.BLUE)
                        * kernel[kernelI][kernelJ]);
              }
            }
          }
          if (redSum < 0) {
            redSum = 0;
          } else if (redSum > 255) {
            redSum = 255;
          }
          if (greenSum < 0) {
            greenSum = 0;
          } else if (greenSum > 255) {
            greenSum = 255;
          }
          if (blueSum < 0) {
            blueSum = 0;
          } else if (blueSum > 255) {
            blueSum = 255;
          }
          newColors[i][j] = new Pixel((int) redSum, (int) greenSum, (int) blueSum);
        }
        else {
          newColors[i][j] = matrix[i][j];
        }
        if (masking[i][j] != null) {
          mask = false;
        }
      }
    }
    return newColors;
  }
}
