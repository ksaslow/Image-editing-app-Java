package controller.io;

import java.util.Objects;

import controller.ImageUtil;
import model.IImageState;

/**
 * Represents the PNGImageLoader implementation of the IImageLoader interface
 * as it relates to PNG image types. This class loads a PNG image so that the user
 * can manipulate it with the existing command patterns.
 */
public class PNGImageLoader implements IImageLoader {
  private final String filePath;

  public PNGImageLoader(String filePath) {
    this.filePath = Objects.requireNonNull(filePath);
  }

  @Override
  public IImageState run() {
    //call readPNG() method from ImageUtil class.
    IImageState loadedImage = ImageUtil.readPNG(this.filePath);
    return loadedImage;
  }
}


