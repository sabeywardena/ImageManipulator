package model.operations;


import model.Pixel;

/**
 * AEdits is the abstract base class for various edits
 * done to the pixels in an image, and producing a new
 * matrix of pixels.
 *
 * @author Oishani Chatterji
 * @author Sarah Abeywardena
 */
public abstract class AEdits {
  Pixel[][] matrix;
  int maxValue;

  boolean mask;

  Pixel[][] masking;

  /**
   * Takes in the original pixels in an image before being edited
   * used in all child classes except brighten.
   *
   * @param matrix the original pixels in an image
   * @throws IllegalArgumentException if 2D Array is null
   */
  public AEdits(Pixel[][] matrix) throws IllegalArgumentException {
    this(matrix, 255);
  }

  /**
   * Takes in both the original pixels in an image before being edited,
   * and also the max value for channels in pixels in the image
   * Used only in brighten, where channels are incremented, but
   * shouldn't be brightened beyond the max value.
   *
   * @param matrix the original pixels in an image
   * @param max    the max value for channels in pixels in the image
   * @throws IllegalArgumentException if 2D Array is null
   */
  AEdits(Pixel[][] matrix, int max) throws IllegalArgumentException {
    if (matrix == null) {
      throw new IllegalArgumentException("Provided 2D Array is null.");
    }
    this.matrix = matrix;
    maxValue = max;
    mask = true;
    masking = new Pixel[matrix.length][matrix[0].length];
  }

  AEdits(Pixel[][] matrix, Pixel[][] masking) throws IllegalArgumentException{
    this(matrix);
    mask = false;
    if (masking == null) {
      throw new IllegalArgumentException("Provided 2D Array is null.");
    }
    this.masking = masking;
  }

  protected void masked(Pixel p){
    mask = (p.equals(new Pixel(0)));
  }

  /**
   * Based on the class, performs an image editing operation with the matrix of pixels
   * creating a new matrix of pixels with the modifications while leaving the original
   * matrix unchanged.
   *
   * @return a matrix of pixels with the child class's corresponding operation done
   */
  public abstract Pixel[][] operation();

}
