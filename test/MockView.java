import java.awt.*;
import java.awt.event.ActionEvent;

import java.awt.image.BufferedImage;
import view.IView;
import view.ViewListener;

/**
 * Represents a MockView. Like the view used in this iteration of the program, the MockView inherits
 * from the IView interface. This allows the MockView to be used for testing. More specifically,
 * this will allow my program to test that the view and controller are wired correctly. By using
 * the MockView to test the wiring, I can be sure that the controller is rendering and manipulating
 * the image that the user and the view intend.
 */
public class MockView implements IView {
  public BufferedImage imageToTest;
  private StringBuilder log;
  private String selectedFile;
  private BufferedImage currentImage;

  public MockView(StringBuilder log) {
    this.log = log;
  }

  public String getLog() {
    return log.toString();
  }

  /**
   * Represents the rendering of an image in my MockView. The point is to use
   * the MockView to test that the view and the controller are correctly wired,
   * meaning that the correct image is communicated with the controller and
   * rendered to the View's canvas object.
   * @param image image to render to the screen.
   */
  public void setImageToCanvas(BufferedImage image) {
    this.imageToTest = image;
    //create a string from the buffered image in pixels!
    //StringBuilder imageString = new StringBuilder();
    for (int x = 0; x < this.imageToTest.getWidth(); x++) {
      for (int y = 0; y < this.imageToTest.getHeight(); y++) {
        Color color = new Color(this.imageToTest.getRGB(x, y));
        log.append(color.getRed() + " " + color.getGreen() + " " + color.getBlue() + " ");
      }
    }
  }

  public BufferedImage getCurrentImage() {
    return this.currentImage;
  }

  public void openImageFileDialog() {
    //
  }


  public void saveImageFileDialog() {
    //
  }


  public String getCurrentFilename() {
    return null;
  }


  public String getCurrentFilePath() {
    return null;
  }


  public void addViewListener(ViewListener listener) {
    //
  }


  public void actionPerformed(ActionEvent e) {
    //
  }


  @Override
  public void setVisible(boolean b) {
    //
  }

  public String getTextFromView() {
    return null;
  }

  @Override
  public void displayMessage(String text) {
    //
  }
}
