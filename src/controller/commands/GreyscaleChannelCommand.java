package controller.commands;

import java.util.Objects;
import java.util.Scanner;

import model.IImageDataBase;
import model.IImageState;
import model.transformations.GreyscaleChannelTransformation;
import model.transformations.ITransformation;

/**
 * Represents the command pattern to select the RBG channel of an image, a greyscale transformation.
 * The user must input the correct command as a string, "red", "green", or "blue".
 * Depending on which channel is selected by the user, the image will be converted to greyscale and
 * all RGB values of the Pixel object will be the R, G or B value accordingly.
 */
public class GreyscaleChannelCommand implements ICommand {
  private IImageDataBase model;

  public GreyscaleChannelCommand() {
    // Constructor left empty to make command patterns and strategy patterns as neat and clean
    // ... as possible.
  }

  @Override
  public void run(Scanner scanner, IImageDataBase model) {
    Objects.requireNonNull(model);
    Objects.requireNonNull(scanner);

    //GreyscaleChannelCommand requires "red" "green" or "blue"
    if ( !scanner.hasNext() ) {
      throw new IllegalStateException("Second argument must be the channel.");
    }
    String channel = scanner.next();

    if ( !scanner.hasNext() ) {
      throw new IllegalStateException("Third argument must be the image id.");
    }

    String sourceImageID = scanner.next();

    if ( !scanner.hasNext() ) {
      throw new IllegalStateException("Fourth argument must be the image id.");
    }

    String destID = scanner.next();

    IImageState sourceImage = model.get(sourceImageID);
    if (sourceImage == null) {
      throw new IllegalStateException("Image with specified id doesn't exist.");
    }

    ITransformation greyscaleChannelTransformation = new GreyscaleChannelTransformation(channel);
    IImageState greyscaleChannelImage = greyscaleChannelTransformation.run(sourceImage);


    //3. I need to save it back in the database (model)
    model.add(destID, greyscaleChannelImage);
  }

}

