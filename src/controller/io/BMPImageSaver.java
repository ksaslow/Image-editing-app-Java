package controller.io;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

import javax.imageio.ImageIO;

import model.IImageState;

/**
 * Represents the BMPImageSaver implementation of the IImageSaver interface as it relates
 * to BMP image types. This class saves a BMP so that the user can keep loaded and transformed
 * images in the designated directory.
 */
public class BMPImageSaver implements IImageSaver {
  private final IImageState image;
  private final ByteArrayOutputStream output;
  private final String pathToSave;

  /**
   * Constructor for a BMPImageSaver object, which saves an IImageState image
   * to a BMP file.
   * @param pathToSave where to save the .bmp file.
   * @param image IImageState image to save as a bmp type.
   * @param output ByteArrayOutputStream output type.
   */
  public BMPImageSaver(String pathToSave, IImageState image, ByteArrayOutputStream output) {
    this.pathToSave = Objects.requireNonNull(pathToSave);
    this.image = Objects.requireNonNull(image);
    this.output = output;
  }

  private void write(String message) {
    try {
      this.output.write(message.getBytes());
    } catch (IOException e) {
      throw new IllegalStateException("Writing failed.");
    }
  }


  @Override
  public void run() {
    try {
      //convert IImageState to BufferedImage
      BufferedImage bufferedImage = new BufferedImage(image.getWidth(), image.getHeight(),
              BufferedImage.TYPE_INT_RGB);

      for (int row = 0; row < image.getHeight(); row++) {
        for (int col = 0; col < image.getWidth(); col++) {
          int r = image.getRedChannel(row, col);
          int g = image.getGreenChannel(row, col);
          int b = image.getBlueChannel(row, col);

          //create a Color object using the RGB values
          java.awt.Color color = new java.awt.Color(r, g, b);

          //get the RGB values from the Color object
          int rgb = color.getRGB();

          //set RGB value to BufferedImage
          bufferedImage.setRGB(col, row, rgb);
        }

        //save bufferedImage to BMP file
        ImageIO.write(bufferedImage, "bmp", output);

        try (FileOutputStream fileOutputStream = new FileOutputStream(new File(pathToSave))) {
          output.writeTo(fileOutputStream);
        }
      }
    } catch (IOException e) {
      throw new IllegalStateException("Writing failed.");
    }
  }
}
