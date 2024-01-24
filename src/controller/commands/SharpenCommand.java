package controller.commands;

import java.util.Objects;
import java.util.Scanner;

import model.IImageDataBase;
import model.IImageState;
import model.transformations.ITransformation;
import model.transformations.SharpenTransformation;

/**
 * Represents the command to sharpen an image. The user must input the commane "sharpen"
 * in order to sharpend the image.
 */
public class SharpenCommand implements ICommand {
  private IImageDataBase model;

  /**
   * Constructs a SharpenCommand object. Constructor is purposefully left empty.
   */
  public SharpenCommand() {
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

    ITransformation sharpenTransformation = new SharpenTransformation();

    IImageState sharpenedImage = sharpenTransformation.run(sourceImage);


    //3. I need to save it back in the database (model)
    model.add(destID, sharpenedImage);
  }
}
