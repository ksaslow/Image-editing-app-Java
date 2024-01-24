package model.transformations;

import model.IImage;
import model.IImageState;
import model.ImageImpl;

/**
 * Represents a greyscale value transformation strategy pattern. If the command pattern to
 * transform an image by calculating its value component, this class will be the workhorse for
 * this transformation.
 */
public class GreyscaleValueTransformation implements ITransformation {

  public GreyscaleValueTransformation() {
    // Constructor left empty to make command patterns and strategy patterns as neat and clean
    // ... as possible.
  }

  private int maxValue(int r, int g, int b) {
    return Math.max(Math.max(r, g), b);
  }

  @Override
  public IImageState run(IImageState sourceImage) {
    IImage newImage = new ImageImpl(sourceImage.getWidth(), sourceImage.getHeight());

    for (int row = 0; row < sourceImage.getHeight(); row++) {
      for (int col = 0; col < sourceImage.getWidth(); col++) {
        int r = sourceImage.getRedChannel(col, row);
        int g = sourceImage.getGreenChannel(col, row);
        int b = sourceImage.getBlueChannel(col, row);

        int maxValue = maxValue(r, g, b);
        newImage.setPixel(col, row, maxValue, maxValue, maxValue);
      }
    }
    return newImage; //this is a full image, it has setters (IIMage, not IImageState)
  }
}
