package controller.commands;

import java.util.Objects;
import java.util.Scanner;
import model.IImageDataBase;
import model.IImageState;
import model.transformations.GreyscaleIntensityTransformation;
import model.transformations.ITransformation;

/**
 * Represents the command pattern to calculate the Intensity of an image.
 * The intensity of an image is found by selecting the maximum of the RGB values of a pixel.
 * All three values, the RGB, will all be switched to the average value with this transformation.
 */
public class GreyscaleIntensityCommand implements ICommand {
  private IImageDataBase model;

  public GreyscaleIntensityCommand() {
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


    ITransformation greyscaleIntensityTransformation = new GreyscaleIntensityTransformation();

    IImageState greyscaleIntensityImage = greyscaleIntensityTransformation.run(sourceImage);


    //3. I need to save it back in the database (model)
    model.add(destID, greyscaleIntensityImage);
  }
}
