package controller.commands;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;
import model.IImageState;
import controller.io.IImageSaver;
import controller.io.PPMImageSaver;
import model.IImageDataBase;

/**
 * Represents a SavePPMCommmand command object. This allows the user to directly tell the program
 * to save a specific image from the image database to a file.
 */
public class SavePPMCommand implements ICommand {

  private IImageState image;

  public SavePPMCommand() {
    // Constructor left empty to make command patterns and strategy patterns as neat and clean
    // ... as possible.
  }

  @Override
  public void run(Scanner scanner, IImageDataBase model) {
    Objects.requireNonNull(scanner);
    IImageDataBase model1 = Objects.requireNonNull(model);
    //this.image = Objects.requireNonNull(image);

    if ( !scanner.hasNext() ) {
      throw new IllegalStateException("Second argument must be destination filepath.");
    }

    String destFilePath = scanner.next();

    if ( !scanner.hasNext() ) {
      throw new IllegalStateException("Third argument must be new filename.");
    }

    String newFileName = scanner.next();

    //fetch image from DB to save it as file
    IImageState image = model.get(newFileName);
    //save image to file
    File output = new File(destFilePath);
    try (FileWriter fileWriter = new FileWriter(output)) {
      IImageSaver saver = new PPMImageSaver(destFilePath, image, fileWriter);
      saver.run();
    } catch (IOException e) {
      throw new IllegalStateException("Failed to write image to file");
    }
  }
}

