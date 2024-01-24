package view;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;


/**
 * Represents a Canvas object, and acts as the canvas onto which an image is rendered in the GUI.
 * The canvas object will be wrapped inside the main panel of the GUI Frame, so that the current
 * image to be edited is rendered to the center of the frame.
 */
public class Canvas extends JPanel implements Scrollable {
  private BufferedImage image;

  /**
   * Constructor of a canvas object. Based on my preferences, the background color and preferred
   * dimensions of the Canvas object are set upon initialization. These could easily be modified
   * depending on how the class is to be implemented.
   */
  public Canvas() {
    setBackground(new Color(224, 242, 241));
    Dimension canvasSize = new Dimension(1000, 600);
    setPreferredSize(canvasSize);
  }

  /**
   * Sets the desired BufferedImage image object to the canvas. This allows the image to be
   * transformed within the program and then directly updated and rendered to the screen.
   * @param image image currently being manipulated by the program.
   */
  public void setImage(BufferedImage image) {
    this.image = image;
    this.repaint(); //this will trigger a call to repaint
  }

  /**
   * Allows the buffered image to be cast to the Canvas object. Using the Graphics2D Class in Java
   * Swing, this renders the BufferedImage to the screen (or "paints the canvas").
   * @param g the <code>Graphics</code> object to protect
   */
  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    if (this.image != null) {
      g.drawImage(this.image, 0, 0 , null);
    }

  }

  /**
   * Allows the GUI to have scroller bars on either side so that an image can be larger than the frame
   * and the user still has a way to see the entire image. This method sets the desired size of the
   * scrollable frame.
   */
  @Override
  public Dimension getPreferredScrollableViewportSize() {
    if (image != null) {
      return new Dimension(image.getWidth(), image.getHeight()); //set preferred size of the viewport
    } else {
      return super.getPreferredSize();
    }
  }

  /**
   * Determines how quickly the scroller bars scroll through an image rendered on the canvas in
   * terms of number of pixels. I set it to ten pixels at a time for this program, but this could
   * be changed depending on how the Class is to be implemented.
   * @param visibleRect The view area visible within the viewport
   * @param orientation Either SwingConstants.VERTICAL or SwingConstants.HORIZONTAL.
   * @param direction Less than zero to scroll up/left, greater than zero for down/right.
   * @return int number of pixels to be scrolled through at a time.
   */
  @Override
  public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
   // How many pixels to scroll in one step
    return 10;
  }

  /**
   * Determines how many pixels are to be scrolled through in one block.
   * @param visibleRect The view area visible within the viewport
   * @param orientation Either SwingConstants.VERTICAL or SwingConstants.HORIZONTAL.
   * @param direction Less than zero to scroll up/left, greater than zero for down/right.
   * @return int number of pixels to be scrolled through in one block.
   */
  @Override
  public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
    // set block increment for scrolling (how many pixels to scroll in one block)
    return 100;
  }

  /**
   * From interface Scrollable, return true if a viewport should always force the width of this
   * Scrollable to match the width of the viewport.
   * @return false.
   */
  @Override
  public boolean getScrollableTracksViewportWidth() {
    return false;
  }


  /**
   * From interface Scrollable, return true if a viewport should always force the height of this
   * Scrollable to match the height of the viewport.
   * @return false.
   */
  @Override
  public boolean getScrollableTracksViewportHeight() {
    return false;
  }
}
