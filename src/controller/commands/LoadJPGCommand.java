package controller.commands;

import java.util.Objects;
import java.util.Scanner;

import controller.io.IImageLoader;
import controller.io.JPGImageLoader;
import model.IImageDataBase;
import model.IImageState;


/**
 * Represents the LoadJPGCommand command pattern to allow the user to load a PPM file as an image.
 */
public class LoadJPGCommand implements ICommand {

  public LoadJPGCommand() {
    //Constructor left empty to make command patterns and strategy patterns as neat and clean
    // ... as possible.
  }

  @Override
  public void run(Scanner scanner, IImageDataBase model) {
    Objects.requireNonNull(scanner);
    model = Objects.requireNonNull(model);

    if (!scanner.hasNext()) {
      throw new IllegalStateException("Second argument must be filepath.");
    }

    String filePath = scanner.next();

    if (!scanner.hasNext()) {
      throw new IllegalStateException("Third argument must be the filename of the loaded image.");
    }

    String fileName = scanner.next();

    IImageLoader loader = new JPGImageLoader(filePath);
    IImageState loadedImage = loader.run();
    String id;
    model.add(fileName, loadedImage);
  }
}
