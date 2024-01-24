package controller.commands;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

import controller.io.IImageSaver;
import controller.io.JPGImageSaver;
import controller.io.PNGImageSaver;
import controller.io.PPMImageSaver;
import model.IImageDataBase;
import model.IImageState;

/**
 * Represents a save command object that is image format agnostic. This allows the user to tell the
 * program to save an image without needing to specify the format type by running a
 * specific savePPM/savePNG/saveJPG command. This command will take the extension of the
 * specified destinationPath (e.g. .ppm, .png, or .jpg) and will trigger the appropriate
 * IImageSaver accordingly.
 */
public class SaveCommand implements ICommand {
  private IImageState image;


  /**
   * Constructor of SaveCommand object. Method body intentionally left blank.
   */
  public SaveCommand() {
    // Constructor left empty to make command patterns and strategy patterns as neat and clean
    // ... as possible.
  }

  @Override
  public void run(Scanner scanner, IImageDataBase model) {
    Objects.requireNonNull(scanner);
    IImageDataBase model1 = Objects.requireNonNull(model);

    if ( !scanner.hasNext() ) {
      throw new IllegalStateException("Second argument must be destination filepath.");
    }

    String destFilePath = scanner.next();

    if ( !scanner.hasNext() ) {
      throw new IllegalStateException("Third argument must be the image ID.");
    }

    String imageID = scanner.next();

    // Fetch image from DB to save it as a file
    IImageState image = model.get(imageID);

    // save image to file using correct IImageSaver:
    IImageSaver saver = getImageSaver(destFilePath, image);

    if (saver instanceof PPMImageSaver) {
      //save image to file
      File output = new File(destFilePath);
      try (FileWriter fileWriter = new FileWriter(output)) {
        saver = new PPMImageSaver(destFilePath, image, fileWriter);
        saver.run();
      } catch (IOException e) {
        throw new IllegalStateException("Failed to write image to file");
      }
    } else {
      // for PNG and JPG ImageSavers, run them normally
      saver.run();
    }
  }

  //Helper method to determine the file type to be used and which IImageSaver object to run
  private String getFileExtension(String destFilePath) {
    if (destFilePath.contains("jpg")) {
      return "jpg";
    } else if (destFilePath.contains("png")) {
      return "png";
    } else if (destFilePath.contains("ppm")) {
      return "ppm";
    }
    return "";
  }

  private IImageSaver getImageSaver(String destFilePath, IImageState image) {
    String fileExt = getFileExtension(destFilePath);
    switch (fileExt) {
      case "ppm":
        File output = new File(destFilePath);
        try (FileWriter fileWriter = new FileWriter(output)) {
          return new PPMImageSaver(destFilePath, image, fileWriter);
        } catch (IOException e) {
          throw new IllegalStateException("Failed to write image to file");
        }
      case "png":
        ByteArrayOutputStream pngOutput = new ByteArrayOutputStream();
        return new PNGImageSaver(destFilePath, image, pngOutput);
      case "jpg":
        ByteArrayOutputStream jpgOutput = new ByteArrayOutputStream();
        return new JPGImageSaver(destFilePath, image, jpgOutput);
      default:
        throw new IllegalArgumentException("Unsupported file type.");
    }
  }
}
