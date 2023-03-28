package model.operations;

import model.Pixel;

/**
 * Flip is a class that is used to flip the pixels in an image
 * horizontally or vertically, as specified, and provide a new matrix
 * with the pixels appropriately flipped.
 *
 * @author Oishani Chatterji
 * @author Sarah Abeywardena
 */

public class Flip extends AEdits {

  private Boolean flipHorizontal;

  /**
   * Takes in the original pixels in an image before being edited,
   * and uses the super constructor, but additionally takes in
   * whether the given pixels should be flipped horizontally
   * or vertically, to be used in the operation method.
   *
   * @param matrix     the original pixels in an image
   * @param horizontal keeps track of whether the image is being flipped
   *                   horizontally or vertically. Is <code>true</code>
   *                   if the image is being flipped horizontally,
   *                   <code>false</code> if being flipped vertically
   * @throws IllegalArgumentException if 2D Array is null
   */
  public Flip(Pixel[][] matrix, Boolean horizontal) throws IllegalArgumentException {
    super(matrix);
    flipHorizontal = horizontal;
  }

  /**
   * Creates a new array of pixels with the pixels from the matrix given
   * to this object, placed based on the flip orientation.
   *
   * @return a new array of pixels after being flipped in the appropriate
   *         orientation
   */
  public Pixel[][] operation() {
    Pixel[][] flipped = new Pixel[matrix.length][matrix[0].length];
    int height = matrix.length;
    int width = matrix[0].length;

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        if (flipHorizontal) {
          flipped[i][j] = matrix[i][(width - 1) - j];
        } else {
          flipped[i][j] = matrix[(height - 1) - i][j];
        }
      }
    }
    return flipped;
  }
}
