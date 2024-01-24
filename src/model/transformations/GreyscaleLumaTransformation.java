package model.transformations;

import model.IImage;
import model.IImageState;
import model.ImageImpl;

/**
 * Represents a greyscale luma transformation strategy pattern. If the command pattern to
 * transform an image by calculating its luma component, this class will be the workhorse for
 * this transformation.
 */
public class GreyscaleLumaTransformation implements ITransformation {

  public GreyscaleLumaTransformation() {
    // Constructor left empty to make command patterns and strategy patterns as neat and clean
    // ... as possible.
  }

  private int luma(int r, int g, int b) {
    // cast type double to type int for Pixel
    return (int) (0.2126 * r + 0.7152 * g + 0.0722 * b);
  }

  @Override
  public IImageState run(IImageState sourceImage) {
    IImage newImage = new ImageImpl(sourceImage.getWidth(), sourceImage.getHeight());

    for (int row = 0; row < sourceImage.getHeight(); row++) {
      for (int col = 0; col < sourceImage.getWidth(); col++) {
        int r = sourceImage.getRedChannel(col, row);
        int g = sourceImage.getGreenChannel(col, row);
        int b = sourceImage.getBlueChannel(col, row);

        int luma = luma(r, g, b);
        newImage.setPixel(col, row, luma, luma, luma);
      }
    }
    return newImage; //this is a full image, it has setters (IIMage, not IImageState)
  }
}
