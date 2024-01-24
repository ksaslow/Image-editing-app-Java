package model;

/**
 * Represents a Pixel object that implements the IPixel interface but can be ammended with the
 * setPixel method.
 */
public class Pixel implements IPixel {
  private int r;
  private int g;
  private int b;
  private int alpha;

  /**
   * Constructs a Pixel object.
   * @param r red value of a Pixel.
   * @param g green value of a Pixel.
   * @param b blue value of a Pixel.
   */
  public Pixel(int r, int g, int b) {
    if (r < 0 || r > 255 || g < 0 || g > 255 || b < 0 || b > 255) {
      throw new IllegalArgumentException("Pixel values out of bounds.");
    }
    this.r = r;
    this.g = g;
    this.b = b;
    this.alpha = 0;

  }

  @Override
  public int getR() {
    return r;
  }

  @Override
  public int getG() {
    return g;
  }

  @Override
  public int getB() {
    return b;
  }

  @Override
  public double getAlpha() {
    return alpha;
  }


  @Override
  public void setR(int val) {
    if (val < 0 || val > 255) {
      throw new IllegalArgumentException("Channel value is invalid.");
    }

    this.r = val;
  }

  @Override
  public void setG(int val) {
    if (val < 0 || val > 255) {
      throw new IllegalArgumentException("Channel value is invalid.");
    }

    this.g = val;
  }


  @Override
  public void setB(int val) {
    if (val < 0 || val > 255) {
      throw new IllegalArgumentException("Channel value is invalid.");
    }

    this.b = val;
  }

  @Override
  public String toString() {
    return this.r + " " + this.g + " " + this.b;
  }


}
