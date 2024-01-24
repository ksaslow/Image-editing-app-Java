package controller.commands;

import java.util.Objects;
import java.util.Scanner;

import model.IImageDataBase;
import model.IImageState;
import model.transformations.BlurTransformation;
import model.transformations.ITransformation;

/**
 * Represents the command pattern to Blur an image. The user must input the correct command
 * "blur" in order to blur the image.
 */
public class BlurCommand implements ICommand {
  private IImageDataBase model;

  /**
   * Constructs a BlurCommand object. Constructor is purposefully left empty.
   */
  public BlurCommand() {
    // Constructor left empty to make command patterns and strategy patterns as neat and clean
    // ... as possible.
  }


  @Override
  public void run(Scanner scanner, IImageDataBase model) {
    Objects.requireNonNull(model);
    Objects.requireNonNull(scanner);


    if ( !scanner.hasNext() ) {
      throw new IllegalStateException("Second argument must be the source image id.");
    }

    String sourceImageID = scanner.next();

    if ( !scanner.hasNext() ) {
      throw new IllegalStateException("Third argument must be the new image id.");
    }

    String destID = scanner.next();

    IImageState sourceImage = model.get(sourceImageID);
    if (sourceImage == null) {
      throw new IllegalStateException("Image with specified id doesn't exist.");
    }

    ITransformation blurTransformation = new BlurTransformation();

    IImageState blurredImage = blurTransformation.run(sourceImage);


    //3. I need to save it back in the database (model)
    model.add(destID, blurredImage);

  }
}
