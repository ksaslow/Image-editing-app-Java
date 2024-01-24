package model.transformations;

import model.IImage;
import model.IImageState;
import model.ImageImpl;

/**
 * Represents a greyscale intensity transformation strategy pattern. If the command pattern to
 * transform an image by calculating its intensity component, this class will be the workhorse for
 * this transformation.
 */
public class GreyscaleIntensityTransformation implements ITransformation {

  public GreyscaleIntensityTransformation() {
    // Constructor left empty to make command patterns and strategy patterns as neat and clean
    // ... as possible.
  }

  private int intensity(int r, int g, int b) {
    // intensity is the average of the RGB values
    // cast type double to type int for Pixel
    return (int) (r + g + b) / 3;
  }

  @Override
  public IImageState run(IImageState sourceImage) {
    IImage newImage = new ImageImpl(sourceImage.getWidth(), sourceImage.getHeight());

    for (int row = 0; row < sourceImage.getHeight(); row++) {
      for (int col = 0; col < sourceImage.getWidth(); col++) {
        int r = sourceImage.getRedChannel(col, row);
        int g = sourceImage.getGreenChannel(col, row);
        int b = sourceImage.getBlueChannel(col, row);

        int intensity = intensity(r, g, b);
        newImage.setPixel(col, row, intensity, intensity, intensity);
      }
    }
    return newImage; //this is a full image, it has setters (IIMage, not IImageState)
  }
}
