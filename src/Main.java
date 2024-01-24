
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;


import controller.ControllerGUI;
import controller.ControllerImpl;
import controller.IController;
import model.IImageDataBase;
import model.ImageDataBase;
import view.View;


/**
 * This is my main driver. THe main driver created the model, creates the view,
 * passes the model and the view to the controller, then gives the controller control
 * over the game.
 */
public class Main {

  /**
   * Constructor for main driver object.
   *
   * @param args arguments passed to the main driver.
   */
  public static void main(String[] args) {
    IImageDataBase modelDB = new ImageDataBase();

    if (args.length >= 2 && args[0].equals("-file")) {
      // if -file option provided, then this is the input for the program
      String filePath = args[1];
      try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
        StringBuilder fileContents = new StringBuilder();
        String newLineCommand;
        while ((newLineCommand = br.readLine()) != null) {
          fileContents.append(newLineCommand).append("\n");
        }
        Readable readable = new StringReader(fileContents.toString());
        System.out.println(fileContents.toString());
        System.out.println("\n");
        IController controller = new ControllerImpl(readable, modelDB, System.out);
        controller.start();
      } catch (IOException e) {
        System.out.println("Error reading file.");
      }
    } if (args.length >= 1 && args[0].equals("-text")) {
      // otherwise, use the standard input with InputStreamReader
      IController controller = new ControllerImpl(
              new InputStreamReader(System.in), modelDB, System.out);
      controller.start();
    } else {
      View view = new View(modelDB);
      ControllerGUI controller = new ControllerGUI(modelDB, view);
      controller.run();
    }
  }
}
