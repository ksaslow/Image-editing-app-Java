package controller.commands;

import java.util.Objects;
import java.util.Scanner;

import controller.io.BMPImageLoader;
import controller.io.IImageLoader;
import model.IImageDataBase;
import model.IImageState;

/**
 * Represents the LoadBMPCommand command pattern to allow the user to load a PPM file as an image.
 */
public class LoadBMPCommand implements ICommand {

  public LoadBMPCommand() {
    //Constructor left empty to make command patterns and strategy patters as neat and clean
    // ... as possible.
  }

  @Override
  public void run(Scanner scanner, IImageDataBase model) {
    Objects.requireNonNull(scanner);
    model = Objects.requireNonNull(model);

    if (!scanner.hasNext()) {
      throw new IllegalStateException("Second argument must be the filepath.");
    }

    String filePath = scanner.next();

    if (!scanner.hasNext()) {
      throw new IllegalStateException("Third argument must be the filename of the loaded image.");
    }

    String fileName = scanner.next();

    IImageLoader loader = new BMPImageLoader(filePath);
    IImageState loadedImage = loader.run();
    String id;
    model.add(fileName, loadedImage);
  }
}
