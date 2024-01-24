package controller.io;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;

import javax.imageio.ImageIO;

import model.IImageState;

/**
 * Represents the JPGImageSaver implementation of the IImageSaver interface as it related to
 * JPG image types. This class saves a JPG so that the user can keep loaded and transformed
 * images in the designated directory.
 */
public class JPGImageSaver implements IImageSaver {
  private final IImageState image;
  private final String pathToSave;
  private final ByteArrayOutputStream output;

  /**
   * Constructor for a JPGImageSaver object, which saves an IImageState image to a JPG
   * file.
   * @param pathToSave where to save the .jpg file.
   * @param image IImageState image to save as jpg type.
   * @param output ByteArrayOutputStream output type.
   */
  public JPGImageSaver(String pathToSave, IImageState image, ByteArrayOutputStream output) {
    this.pathToSave = Objects.requireNonNull(pathToSave);
    this.image = Objects.requireNonNull(image);
    this.output = output;
  }

  @Override
  public void run() {
    try {
      //convert IImageState to BufferedImage
      BufferedImage bufferedImage = new BufferedImage(image.getWidth(), image.getHeight(),
              BufferedImage.TYPE_INT_RGB);

      for (int row = 0; row < image.getHeight(); row++) {
        for (int col = 0; col < image.getWidth(); col++) {
          int r = image.getRedChannel(col, row);
          int g = image.getGreenChannel(col, row);
          int b = image.getBlueChannel(col, row);

          //create a Color object using the RGB values
          java.awt.Color color = new java.awt.Color(r, g, b);

          //get the RGB values from the Color object
          int rgb = color.getRGB();

          //set RGB value to BufferedImage
          bufferedImage.setRGB(col, row, rgb);
        }
      }
      output.reset(); //clear previous data in ByteArrayOutputStream
      ImageIO.write(bufferedImage, "jpg", output);

      //save bufferedImage to PNG file
      try (OutputStream fileOutputStream = new FileOutputStream(new File(pathToSave))) {
        output.writeTo(fileOutputStream);
      }
    } catch (IOException e) {
      throw new IllegalStateException("Writing failed.");
    }
  }
}
