package model.operations;

import model.Pixel;

/**
 * The {@code Greyscale} class represents a filter to transform an image by using a traditional
 * luma kernel to operate on the various pixel channels in the image.
 */
public class Greyscale extends ColorTransformations {

  /**
   * Constructs an instance of {@code Greyscale} when given a 2D array of Pixels.
   *
   * @param matrix the array to display the greyscale filter
   * @throws IllegalArgumentException if the given array is null
   */
  public Greyscale(Pixel[][] matrix) throws IllegalArgumentException {
    super(matrix);
    this.kernel = new double[][]{{0.2126, 0.7152, 0.0722},
                                 {0.2126, 0.7152, 0.0722},
                                 {0.2126, 0.7152, 0.0722}};
  }

  public Greyscale(Pixel[][] matrix, Pixel[][] masking) throws IllegalArgumentException {
    super(matrix, masking);
    this.kernel = new double[][]{{0.2126, 0.7152, 0.0722},
            {0.2126, 0.7152, 0.0722},
            {0.2126, 0.7152, 0.0722}};
  }
}
