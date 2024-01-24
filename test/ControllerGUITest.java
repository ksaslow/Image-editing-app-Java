import org.junit.Test;
import org.junit.Before;

import java.awt.image.BufferedImage;

import controller.ControllerGUI;
import controller.ImageUtil;
import model.IImageDataBase;
import model.IImageState;
import model.ImageDataBase;
import view.View;


/**
 * Represents the test class for the ControllerGUI, the View-specific controller.
 * Make sure that the view's FileDialog and Canvas objects are correctly loading the
 * image into the controller so that it can be correctly converted to an IImageState object
 * and stored into the IImageDataBase model.
 */
public class ControllerGUITest {

  @Before
  public void setup() {
    //create a mock view and IImageDataBase objects
    IImageDataBase model = new ImageDataBase();
    View view = new View(model);

    ControllerGUI controller = new ControllerGUI(model, view);
  }

  @Test
  public void testHandleLoadEvent() {
    BufferedImage loadedImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
    IImageState imageState = ImageUtil.convertToIImageState(loadedImage);
  }
}
