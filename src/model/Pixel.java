package model;

/**
 * Represents a Pixel in an image that contains a typical RGB coloring system.
 */
public class Pixel {
  private int red;
  private int green;
  private int blue;

  /**
   * Constructs an image of a {@code Pixel} with a given red, green, and blue channel.
   *
   * @param r the red component of the {@code Pixel}
   * @param g the green component of the {@code Pixel}
   * @param b the blue component of the {@code Pixel}
   * @throws IllegalArgumentException if any of the channel values are out of the range 0-255
   */
  public Pixel(int r, int g, int b) throws IllegalArgumentException {
    if (r > 255 || r < 0 || g > 255 || g < 0 || b > 255 || b < 0) {
      throw new IllegalArgumentException("Channel value is out of bounds. Range for an RGB value "
          + "is between 0 and 255, including both 0 and 255.");
    }
    this.red = r;
    this.green = g;
    this.blue = b;
  }

  /**
   * Constructs an image of a {@code Pixel} with a given number to set each channel too.
   *
   * @param num represents the number to set the channel to
   * @throws IllegalArgumentException if any of the channel values are out of the range 0-255
   */
  public Pixel(int num) throws IllegalArgumentException {
    this(num, num, num);
  }

  /**
   * Gets the value of the given channel based on the {@code RGBChannel} taken in.
   *
   * @param channel represents the channel to return its value of
   * @return the value of the indicated channel
   * @throws IllegalArgumentException if the given parameter is not a valid channel
   */
  public int getChannel(RGBChannel channel) throws IllegalArgumentException {
    switch (channel) {
      case RED:
        return this.red;
      case GREEN:
        return this.green;
      case BLUE:
        return this.blue;
      default:
        throw new IllegalArgumentException("Not a valid RGBChannel");
    }
  }

  @Override
  public boolean equals(Object other) throws IllegalArgumentException {
    if (!(other instanceof Pixel)) {
      throw new IllegalArgumentException("Not of type Pixel.");
    }

    Pixel that = (Pixel) other;
    return (this.red == that.red && this.green == that.green && this.blue == that.blue);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result
        + this.red * 9875 + this.green * 3475 + this.blue * 3;
    return result;
  }
}

