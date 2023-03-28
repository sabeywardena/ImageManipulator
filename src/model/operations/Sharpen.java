package model.operations;

import model.Pixel;

/**
 * The {@code Sharpen} class represents a filter to transform an image by using a traditional
 * sharpen kernel to operate on the various pixel channels in the image.
 *
 * @throws IllegalArgumentException if 2D Array is null
 */
public class Sharpen extends Filter {

  /**
   * Constructs an instance of {@code Sharpen} when given a 2D array of Pixels.
   *
   * @param pixels the array to display the sharpen filter
   * @throws IllegalArgumentException if the given array is null
   */
  public Sharpen(Pixel[][] pixels) throws IllegalArgumentException {
    super(pixels);
    this.kernel = new double[][]{{-.125, -.125, -.125, -.125, -.125},
                                 {-.125, .25, .25, .25, -.125},
                                 {-.125, .25, 1, .25, -.125},
                                 {-.125, .25, .25, .25, -.125},
                                 {-.125, -.125, -.125, -.125, -.125}};
  }

  public Sharpen(Pixel[][] pixels, Pixel[][] masking) throws IllegalArgumentException {
    super(pixels, masking);
    this.kernel = new double[][]{{-.125, -.125, -.125, -.125, -.125},
            {-.125, .25, .25, .25, -.125},
            {-.125, .25, 1, .25, -.125},
            {-.125, .25, .25, .25, -.125},
            {-.125, -.125, -.125, -.125, -.125}};
  }
}

