package model;

/**
 * Represents the smallest part of the model, the pixel. All images are made up of pixels and each
 * pixel has a red value, a green value, and a blue value. Though outside the scope of this
 * assignment, I have initialized the Pixel objects with value Alpha as well, as it will be
 * useful for the next homework assignment.
 */
public interface IPixelState {
  public int getR();

  public int getG();

  public int getB();

  public double getAlpha();

}
