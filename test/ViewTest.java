import org.junit.Test;

import java.awt.image.BufferedImage;

import controller.ControllerGUI;
import controller.IControllerGUI;
import model.IImageDataBase;
import model.ImageDataBase;
import view.TextView;

import static org.junit.Assert.assertEquals;



/**
 * Represents the test class for the View portion of my image editing program.
 * The tests will utilize a MockView in order to text that the view and controller
 * are properly wired and that the view is displaying the correct image.
 */
public class ViewTest {
  public BufferedImage imageToTest;
  private StringBuilder log;
  private TextView textView; //use for toString() method

  @Test
  public void testMockView() {

    StringBuilder log = new StringBuilder();
    MockView viewTest = new MockView(log);
    IImageDataBase model = new ImageDataBase();


    IControllerGUI controller = new ControllerGUI(model, viewTest);
    String filepath = "test/fourxfour.ppm";
    String expectedImageString = "";
    controller.handleLoadEvent(filepath); //use very small image to test

    // Retrieve the current image from the MockView
    BufferedImage currentImage = viewTest.getCurrentImage();

    // Assert if the currentImage is not null or any other specific checks you want to make.
    assertEquals(expectedImageString, log.toString().trim());
  }
}