package controller.io;

import java.util.Objects;
import controller.ImageUtil;
import model.IImageState;

/**
 * This represents the PPMImageLoader implementation of the IImageLoader interface as it relates
 * to PPM image types. This class loads a PPM so that the user can manipulate it with the
 * existing command patterns.
 */
public class PPMImageLoader implements IImageLoader {
  private final String filePath;


  public PPMImageLoader(String filePath) {
    this.filePath = Objects.requireNonNull(filePath);
  }


  @Override
  public IImageState run() {
    IImageState loadedImage = ImageUtil.readPPM(this.filePath);
    return loadedImage;
  }
}
