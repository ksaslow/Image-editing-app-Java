package controller.io;

import java.util.Objects;

import controller.ImageUtil;
import model.IImageState;

/**
 * Represents the BMPImageLoader implementation of the IImageLoader interface
 * as it relates to BMP image types. This class loads a BMP image so that the user can
 * manipulate it with the existing command patterns.
 */
public class BMPImageLoader implements IImageLoader {
  private final String filePath;

  public BMPImageLoader(String filePath) {
    this.filePath = Objects.requireNonNull(filePath);
  }

  @Override
  public IImageState run() {
    //call readBMP() method from ImageUtil class
    IImageState loadedImage = ImageUtil.readBMP(this.filePath);
    return loadedImage;
  }
}
