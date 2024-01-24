package model;

/**
 * This represents the implementation of an IImage. This class allows the user to get the
 * information held within the Pixel object.
 */
public class ImageImpl implements IImage {
  private final int width;
  private final int height;
  private final IPixel[][] data;


  /**
   * Constructs an ImageImpl object.
   * @param width how many columns of Pixels an IImage has.
   * @param height how many rows of Pixels an IImage has.
   */
  public ImageImpl(int width, int height) {
    this.width = width;
    this.height = height;
    // [rows] is number of pixels high (height)
    // [cols] is number of pixels wide (width)
    this.data = new IPixel[height][width]; //initialize data array
  }


  @Override
  public int getHeight() {
    return this.height;
  }

  @Override
  public int getWidth() {
    return this.width;
  }


  @Override
  public int getRedChannel(int x, int y) {
    if (x < 0 || x >= this.width || y < 0 || y >= this.height) {
      throw new IllegalArgumentException("x or y outside of bounds.");
    }
    return this.data[y][x].getR();
  }

  @Override
  public int getGreenChannel(int x, int y) {
    if (x < 0 || x >= this.width || y < 0 || y >= this.height) {
      throw new IllegalArgumentException("x or y outside of bounds.");
    }
    return this.data[y][x].getG();
  }

  @Override
  public int getBlueChannel(int x, int y) {
    if (x < 0 ||  x >= this.width || y < 0 || y >= this.height) {
      throw new IllegalArgumentException("x or y outside of bounds.");
    }
    return this.data[y][x].getB();
  }

  // this is a much better design! DECOUPLES! that way is Pixel ever changes, it doesnt break code
  @Override
  public void setPixel(int x, int y, int r, int g, int b) {
    if (x < 0 || x >= this.width || y < 0 || y >= this.height) { // keep exception here
      throw new IllegalArgumentException("x or y outside of bounds."); // AND clamp it outside
    }
    this.data[y][x] = new Pixel(r,g,b); // always needs to be [y][x]
  }
}
