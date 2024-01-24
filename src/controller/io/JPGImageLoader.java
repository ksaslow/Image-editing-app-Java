package controller.io;

import java.util.Objects;

import controller.ImageUtil;
import model.IImageState;

/**
 * Represents a JPG ImageLoader object to load JPG images. Implements IImageLoader interface.
 */
public class JPGImageLoader implements IImageLoader {

  private final String filePath;

  public JPGImageLoader(String filePath) {
    this.filePath = Objects.requireNonNull(filePath);
  }

  @Override
  public IImageState run() {
    //call readPNG() method from ImageUtil class.
    IImageState loadedImage = ImageUtil.readJPG(this.filePath);
    return loadedImage;
  }
}
