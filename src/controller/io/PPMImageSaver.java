package controller.io;

import java.io.IOException;
import java.util.Objects;
import model.IImageState;

/**
 * This represents the PPMImageSaver implementation of the IImageSaver interface as it relates
 * to PPM image types. This class saves a PPM so that the user can keep loaded and transformed
 * images in the designated directory.
 */
public class PPMImageSaver implements IImageSaver {
  private final IImageState image;
  private final Appendable output;

  /**
   * Constructs a PPMImageSaver object.
   * @param pathToSave string path to where the file should be saved.
   * @param image IImageState to be saved by command.
   * @param output Appendable type, most likely file.
   */
  public PPMImageSaver(String pathToSave, IImageState image, Appendable output) {
    String pathToSave1 = Objects.requireNonNull(pathToSave);
    this.image = Objects.requireNonNull(image);
    this.output = output;
  }

  private void write(String message) {
    try {
      this.output.append(message);
    } catch (IOException e) {
      throw new IllegalStateException("Writing failed.");
    }
  }

  @Override
  public void run() {
    write("P3\n");
    write(image.getWidth() + " " + image.getHeight() + "\n");
    write("255\n");

    //iterate over the image
    for (int row = 0; row < image.getHeight(); row++) {
      for (int col = 0; col < image.getWidth(); col++) {
        int r = image.getRedChannel(col, row);
        int g = image.getGreenChannel(col, row);
        int b = image.getBlueChannel(col, row);

        //append the RGB values to the output:
        write(r + "\n" + g + "\n" + b + "\n");
      }
    }
  }
}
