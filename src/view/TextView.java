package view;

import java.io.IOException;
import model.IImageState;

/**
 * This represents the PPMTextView object, which implements the IImageView interface. The
 * PPMTextView class is the PPM-specific implementer of the view. In the future, the program
 * will also be able to represent other image formats.
 */
public class TextView implements IImageTextView {
  private final IImageState modelImageState;
  private final Appendable out;


  public TextView(IImageState modelImageState) throws IllegalArgumentException {
    this(modelImageState, System.out);
  }

  /**
   * Constructs the PPMTextView object.
   * @param modelImageState model for the view to take in and represent.
   * @param out appendable to be used by the object to represent the model.
   * @throws IllegalArgumentException if any constructor arguments are null.
   */
  public TextView(IImageState modelImageState, Appendable out) throws IllegalArgumentException {
    if (out == null || modelImageState == null) {
      throw new IllegalArgumentException("Constructor arguments cannot be null.");
    }
    this.modelImageState = modelImageState;
    this.out = out;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    int width = modelImageState.getWidth();
    int height = modelImageState.getHeight();

    sb.append("Width of image: ").append(width).append(System.lineSeparator());
    sb.append("Height of image: ").append(height).append(System.lineSeparator());
    sb.append("Maximum value of a color in this file (usually 255): ").append(
            255).append(System.lineSeparator());

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int r = modelImageState.getRedChannel(i, j);
        int g = modelImageState.getGreenChannel(i, j);
        int b = modelImageState.getBlueChannel(i, j);
        sb.append("Color of pixel (").append(j).append(",").append(i).append("): ").append(
                r).append(",").append(g).append(",").append(b).append(System.lineSeparator());
      }
    }
    return sb.toString();
  }

  @Override
  public void renderMessage(String message) throws IOException {
    this.out.append(message);
  }
}
