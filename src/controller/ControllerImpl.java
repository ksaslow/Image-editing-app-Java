package controller;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

import controller.commands.BlurCommand;
import controller.commands.BrightenCommand;
import controller.commands.GreyscaleChannelCommand;
import controller.commands.GreyscaleIntensityCommand;
import controller.commands.GreyscaleLumaCommand;
import controller.commands.GreyscaleValueCommand;
import controller.commands.ICommand;
import controller.commands.LoadCommand;

import controller.commands.LoadPPMCommand;
import controller.commands.SaveCommand;
import controller.commands.SavePPMCommand;
import controller.commands.SharpenCommand;
import model.IImageDataBase;


/**
 * Represents the implementation of the controller in the MVC model.
 */
public class ControllerImpl implements IController {
  private final Readable input;
  private final IImageDataBase model;
  private final Appendable appendable; //just use appendable for now so that we can TEST it!

  private final Map<String, ICommand> commandMap;

  /**
   * Constructs the ControllerImpl object to run the program. The controller takes a command,
   * the model to be used, and an appendable.
   * @param input readable as command input.
   * @param model database that holds the images.
   * @param appendable to store and represent the output.
   */
  public ControllerImpl(Readable input, IImageDataBase model, Appendable appendable) {
    this.input = Objects.requireNonNull(input);
    this.model = Objects.requireNonNull(model);
    this.appendable = Objects.requireNonNull(appendable);

    this.commandMap = new HashMap<String, ICommand>();
    this.commandMap.put("load", new LoadCommand());
    this.commandMap.put("brighten", new BrightenCommand());
    this.commandMap.put("savePPM", new SavePPMCommand());
    this.commandMap.put("save", new SaveCommand());
    this.commandMap.put("loadPPM", new LoadPPMCommand());
    this.commandMap.put("luma-component", new GreyscaleLumaCommand());
    this.commandMap.put("intensity-component", new GreyscaleIntensityCommand());
    this.commandMap.put("value-component", new GreyscaleValueCommand());
    this.commandMap.put("color-channel", new GreyscaleChannelCommand());
    this.commandMap.put("blur", new BlurCommand());
    this.commandMap.put("sharpen", new SharpenCommand());
  }

  private void write(String message) {
    try {
      this.appendable.append(message);
    } catch (IOException e) {
      throw new IllegalStateException("Writing to the appendable failed.");
    }
  }

  @Override
  public void start() {
    Scanner scanner = new Scanner(this.input);

    while (scanner.hasNext()) {
      String command = scanner.next();
      //This is where the COMMAND PATTERN starts!

      ICommand commandToRun = this.commandMap.getOrDefault(command, null);
      if (commandToRun == null) {
        write("Invalid command.");
        continue;
      }
      try {
        commandToRun.run(scanner, this.model);
      } catch (IllegalStateException e) {
        write(e.getMessage());
      }
    }
  }
}
