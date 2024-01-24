package model.transformations;

import model.IImage;
import model.IImageState;
import model.ImageImpl;


/**
 * Represents a greyscale channel transformation strategy pattern. If the command pattern to
 * transform an image by its greyscale channel component, this class will be the workhorse for
 * this transformation.
 */
public class GreyscaleChannelTransformation implements ITransformation {
  private final String channelChosen;

  public GreyscaleChannelTransformation(String channel) {
    this.channelChosen = channel;
  }

  private int channelChosen(int r, int g, int b) {
    if (this.channelChosen.equals("red")) {
      return r;
    }
    if (this.channelChosen.equals("green")) {
      return g;
    }
    if (this.channelChosen.equals("blue")) {
      return b;
    } else {
      throw new IllegalArgumentException("Invalid channel chosen, please choose red, "
              + "green, or blue.");
    }
  }

  @Override
  public IImageState run(IImageState sourceImage) {
    IImage newImage = new ImageImpl(sourceImage.getWidth(), sourceImage.getHeight());

    for (int row = 0; row < sourceImage.getHeight(); row++) {
      for (int col = 0; col < sourceImage.getWidth(); col++) {
        int r = sourceImage.getRedChannel(col, row);
        int g = sourceImage.getGreenChannel(col, row);
        int b = sourceImage.getBlueChannel(col, row);

        int chosenValue = channelChosen(r, g, b);
        newImage.setPixel(col, row, chosenValue, chosenValue, chosenValue);
      }
    }
    return newImage; //this is a full image, it has setters (IIMage, not IImageState)
  }
}
