package controller.commands;

import java.util.Objects;
import java.util.Scanner;

import controller.io.IImageLoader;
import controller.io.PPMImageLoader;
import model.IImageDataBase;
import model.IImageState;


/**
 * Represents the LoadPPMCommand command pattern to allow the user to load a PPM file as an image.
 */
public class LoadPPMCommand implements ICommand {

  public LoadPPMCommand() {
    // Constructor left empty to make command patterns and strategy patterns as neat and clean
    // ... as possible.
  }

  @Override
  public void run(Scanner scanner, IImageDataBase model) {
    Objects.requireNonNull(scanner);
    IImageDataBase model1 = Objects.requireNonNull(model);

    if ( !scanner.hasNext() ) {
      throw new IllegalStateException("Second argument must be the filepath.");
    }

    String filePath = scanner.next();

    if ( !scanner.hasNext() ) {
      throw new IllegalStateException("Third argument must be the filename of the loaded image.");
    }

    String fileName = scanner.next();

    IImageLoader loader = new PPMImageLoader(filePath);
    IImageState loadedImage = loader.run();
    String id;
    model.add(fileName, loadedImage);
  }
}
