package model.operations;

import model.Pixel;

/**
 * The {@code Sepia} class represents a filter to transform an image by using a traditional sepia
 * kernel to operate on the various pixel channels in the image.
 */
public class Sepia extends ColorTransformations {


  /**
   * Constructs an instance of {@code Sepia} when given a 2D array of Pixels.
   *
   * @param matrix the array to display the sepia filter
   * @throws IllegalArgumentException if the given array is null
   */
  public Sepia(Pixel[][] matrix) throws IllegalArgumentException {
    super(matrix);
    this.kernel = new double[][] {{0.393, 0.769, 0.189},
                                  {0.349, 0.686, 0.168},
                                  {0.272, 0.534, 0.131}};
  }

  public Sepia(Pixel[][] matrix, Pixel[][] masking) throws IllegalArgumentException {
    super(matrix, masking);
    this.kernel = new double[][] {{0.393, 0.769, 0.189},
            {0.349, 0.686, 0.168},
            {0.272, 0.534, 0.131}};
  }
}
