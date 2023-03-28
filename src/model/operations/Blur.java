package model.operations;

import model.Pixel;

/**
 * The {@code Blur} class represents a filter to transform an image by using a traditional
 * blur kernel to operate on the various pixel channels in the image.
 *
 * @throws IllegalArgumentException if 2D Array is null
 */
public class Blur extends Filter {

  /**
   * Constructs an instance of {@code Blur} when given a 2D array of Pixels.
   *
   * @param pixels the array to display the blur filter
   * @throws IllegalArgumentException if the given array is null
   */
  public Blur(Pixel[][] pixels) throws IllegalArgumentException {
    super(pixels);
    this.kernel = new double[][]{{.0625, .125, .0625},
                                 {.125, .25, .125},
                                 {.0625, .125, .0625}};
  }

  public Blur(Pixel[][] pixels, Pixel[][] masking) throws IllegalArgumentException {
    super(pixels, masking);
    this.kernel = new double[][]{{.0625, .125, .0625},
            {.125, .25, .125},
            {.0625, .125, .0625}};
  }
}