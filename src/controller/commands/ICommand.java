package controller.commands;

import java.util.Scanner;

import model.IImageDataBase;

/**
 * Represents the ICommand interface, an interface implemented by all Command Pattern classes.
 * This allows the user to simply enter a command into the program and the image will be
 * transformed accordingly.
 */
public interface ICommand {
  void run(Scanner scanner, IImageDataBase model);
}
