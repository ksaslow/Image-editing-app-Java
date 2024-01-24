package view;

import java.io.IOException;

/**
 * Represents the IImageView interface, the interface that dictates the view component
 * of the MVC program.
 */
public interface IImageTextView {

  String toString();

  void renderMessage(String message) throws IOException;
}
