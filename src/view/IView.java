package view;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

/**
 * Interface for the GUI-specific View in this program. Lays out the contract that the View Class
 * and the MockView abide by. This GUI-specific view interface determine the functionality of which
 * transformations will be reflected in the view and available to the user.
 */
public interface IView {

  void setImageToCanvas(BufferedImage image);
  BufferedImage getCurrentImage();
  String getCurrentFilename();
  String getCurrentFilePath();
  String getTextFromView();
  void displayMessage(String text);
  void addViewListener(ViewListener listener);
  void actionPerformed (ActionEvent e);
  void setVisible(boolean b); // use this for ControllerGUI run method
}
