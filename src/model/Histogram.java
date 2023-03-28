package model;

import model.operations.Intensity;

/**
 * The {@code Histogram} class represents a way to create a matrix of data represented in
 * integers to serve as data points on a Histogram graph. The histogram represents the volume of
 * pixels that share a specific channel value.
 */
public class Histogram {

  private Pixel[][] matrix;

  /**
   * Constructs an instance of {@code Histogram} with the given 2D matrix of pixels.
   * @param matrix the 2D matrix of Pixels to represent the pixels in an image
   * @throws IllegalArgumentException when array is null
   */
  public Histogram(Pixel[][] matrix) throws IllegalArgumentException {
    if (matrix == null) {
      throw new IllegalArgumentException("Matrix of pixels is null. (Histogram)");
    }
    this.matrix = matrix;
  }

  /**
   * Constructs an array of integers to represent the frequency of a given RGB channel value,
   * represented in the index of the local variable {@code values} for loop.
   * @param c the channel to collect the data from
   * @return an array of integers with the collected data
   */
  public int[] rgbData(RGBChannel c) {

    int[] values = new int[256];
    // where cVal represents the value of the channel
    for (int cVal = 0; cVal < 256; cVal++) {
      values[cVal] = 0;
    }

    for (int row = 0; row < matrix.length; row++) {
      for (int col = 0; col < matrix[0].length; col++) {
        values[matrix[row][col].getChannel(c)] ++;
      }
    }
    return values;
  }

  /**
   * Constructs an array of integers to represent the volume of pixels that share the same
   * intensity value.
   * @return and array of integers representing the collected intensity values.
   */
  public int[] intensityData() {

    Pixel[][] operate = new Intensity(matrix).operation();
    Histogram intense = new Histogram(operate);
    return intense.rgbData(RGBChannel.RED);
  }
}
