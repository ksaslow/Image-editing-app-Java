package controller.commands;

import java.util.Objects;
import java.util.Scanner;

import model.IImageDataBase;
import model.IImageState;
import model.transformations.BrightenTransformation;
import model.transformations.ITransformation;

/**
 * Represents the command pattern to brighten an image. The user must input the correct command
 * "brighten" as well as an integer increment to edit the image by. If the given increment is
 * positive, the image is brightened. If the increment given is negative, the image is darkened.
 */
public class BrightenCommand implements ICommand {
  private IImageDataBase model;

  /**
   * Contstructs a BrightenCommand object. Constructor is purposefully left empty.
   */
  public BrightenCommand() {
    // Constructor left empty to make command patterns and strategy patterns as neat and clean
    // ... as possible.
  }

  @Override
  public void run(Scanner scanner, IImageDataBase model) {
    Objects.requireNonNull(model);
    Objects.requireNonNull(scanner);

    if ( !scanner.hasNextInt() ) {
      throw new IllegalStateException("Second argument must be an int.");
    }
    int value = scanner.nextInt();

    if ( !scanner.hasNext() ) {
      throw new IllegalStateException("Third argument must be the image id.");
    }

    String sourceImageID = scanner.next();

    if ( !scanner.hasNext() ) {
      throw new IllegalStateException("Fourth argument must be the image id.");
    }

    String destID = scanner.next();
    // What needs to happen here?
    // 1. I need to get the image with source ID. To do this, I need the model.

    IImageState sourceImage = model.get(sourceImageID);
    if (sourceImage == null) {
      throw new IllegalStateException("Image with specified id doesn't exist.");
    }


    //2. How do I do the brightening??! This is a strategy:
    ITransformation brightenTransformation = new BrightenTransformation(value);
    // the return type of BrightenTransformation.run is IImageState, but it actually
    // runs ON an IImage. IImageState is a LOWER interface, doesn't have setter methods.
    // This = information HIDING! Limit the freedom you give to the client.
    IImageState brightenedImage = brightenTransformation.run(sourceImage);


    //3. I need to save it back in the database (model)
    model.add(destID, brightenedImage);

  }

}
