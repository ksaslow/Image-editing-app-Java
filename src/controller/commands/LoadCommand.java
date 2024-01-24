package controller.commands;

import java.util.Objects;
import java.util.Scanner;

import controller.io.IImageLoader;
import controller.io.JPGImageLoader;
import controller.io.PNGImageLoader;
import controller.io.PPMImageLoader;
import model.IImageDataBase;
import model.IImageState;

/**
 * Represents a load command object that is image format agnostic. This allows the user to tell the
 * program to load an image without needing to specify the format type by running a
 * specific loadPPM/loadPNG/loadJPG command. This command will take the extension of the
 * specified destinationPath (e.g. .ppm, .png, or .jpg) and will trigger the appropriate
 * IImageLoader accordingly.
 */
public class LoadCommand implements ICommand {
  private IImageState image;

  /**
   * Constructor of LoadCommand object. Method body intentionally left blank.
   */
  public LoadCommand() {
    // Constructor left empty to make command patterns and strategy patterns as neat and clean
    // ... as possible.
  }


  @Override
  public void run(Scanner scanner, IImageDataBase model) {
    Objects.requireNonNull(scanner);
    IImageDataBase model1 = Objects.requireNonNull(model);

    if (!scanner.hasNext()) {
      throw new IllegalStateException("Second argument must be the filepath.");
    }

    String filePath = scanner.next();

    if (!scanner.hasNext()) {
      throw new IllegalStateException("Third argument must be the filename of the loaded image.");
    }

    String fileName = scanner.next();


    IImageLoader loader = getImageLoader(filePath, image);
    IImageState loadedImage = loader.run();
    String id;
    model.add(fileName, loadedImage);
  }

  private String getFileExtension(String filePath) {
    if (filePath.contains("jpg")) {
      return "jpg";
    } else if (filePath.contains("png")) {
      return "png";
    } else if (filePath.contains("ppm")) {
      return "ppm";
    }
    return "";
  }

  private IImageLoader getImageLoader(String filePath, IImageState image) {
    String fileExt = getFileExtension(filePath);
    switch (fileExt) {
      case "ppm":
        return new PPMImageLoader(filePath);
      case "png":
        return new PNGImageLoader(filePath);
      case "jpg":
        return new JPGImageLoader(filePath);
      default:
        throw new IllegalArgumentException("Unsupported file type.");
    }
  }
}
