package controller.commands;

import java.util.Objects;
import java.util.Scanner;

import model.IImageDataBase;
import model.IImageState;
import model.transformations.GreyscaleLumaTransformation;
import model.transformations.ITransformation;

/**
 * Represents the command pattern to calculate the Luma of an image.
 * The luma of an image is found by calculating a weighted sum of the RGB values of an image.
 * All three values, the RGB, will all be switched to the luma value with this transformation.
 */
public class GreyscaleLumaCommand implements ICommand {
  private IImageDataBase model;


  public GreyscaleLumaCommand() {
    // Constructor left empty to make command patterns and strategy patterns as neat and clean
    // ... as possible.
  }

  @Override
  public void run(Scanner scanner, IImageDataBase model) {
    Objects.requireNonNull(model);
    Objects.requireNonNull(scanner);

    if ( !scanner.hasNext() ) {
      throw new IllegalStateException("Second argument must be the image id.");
    }

    String sourceImageID = scanner.next();

    if ( !scanner.hasNext() ) {
      throw new IllegalStateException("Third argument must be the transformed image id.");
    }

    String destID = scanner.next();
    // What needs to happen here?
    // 1. I need to get the image with source ID. To do this, I need the model.

    IImageState sourceImage = model.get(sourceImageID);
    if (sourceImage == null) {
      throw new IllegalStateException("Image with specified id doesn't exist.");
    }


    ITransformation greyscaleLumaTransformation = new GreyscaleLumaTransformation();

    IImageState greyscaleLumaImage = greyscaleLumaTransformation.run(sourceImage);


    //3. I need to save it back in the database (model)
    model.add(destID, greyscaleLumaImage);
  }
}
