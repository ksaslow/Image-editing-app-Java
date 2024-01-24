package model.transformations;

import model.IImage;
import model.IImageState;
import model.ImageImpl;

/**
 * This class represents a transformation on colored images. Colored images can be either
 * brightened or darkened, which is either adding or subtracting a specified increment to each
 * of the three (RGB) pixel values. The int brightenValue passed as an argument can thus be either
 * positive to brighten the image, or negative to darken the image.
 */
public class BrightenTransformation implements ITransformation {
  private final int brightenValue;

  public BrightenTransformation(int brightenValue) {
    this.brightenValue = brightenValue;
  }

  private int clamp(int value) {
    if (value < 0) {
      return 0;
    }
    if (value > 255) {
      return 255;
    }
    return value;
  }

  @Override
  public IImageState run(IImageState sourceImage) {
    // need to use IImage, not IImageState because IImageState doesnt have setter methods!
    // BUT the return type of the function can still be IImageState
    IImage newImage = new ImageImpl(sourceImage.getWidth(), sourceImage.getHeight());

    for (int row = 0; row < sourceImage.getHeight(); row++) {
      for (int col = 0; col < sourceImage.getWidth(); col++) {
        int newR = clamp(sourceImage.getRedChannel(col, row) + brightenValue);
        int newG = clamp(sourceImage.getGreenChannel(col, row) + brightenValue);
        int newB = clamp(sourceImage.getBlueChannel(col, row) + brightenValue);
        newImage.setPixel(col, row, newR, newG, newB);
      }
    }
    return newImage; //this is a full image, it has setters (IIMage, not IImageState)
  }
}
