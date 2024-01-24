import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;

import controller.ControllerImpl;
import controller.IController;
import model.IImage;
import model.IImageDataBase;
import model.IImageState;
import model.ImageDataBase;
import model.ImageImpl;
import model.Pixel;
import view.IImageTextView;
import view.TextView;

import static org.junit.Assert.assertEquals;

/**
 * Represents the test class of the ControllerImpl class.
 */
public class ControllerImplTest {

  //Test IOException with FAKE APPENDABLE to make sure IOExceptions are thrown.
  @Test(expected = IOException.class)
  public void testIOException() throws IOException {
    IImageState model = new ImageImpl(5, 6);
    IImageTextView view = new TextView(model, new FakeAppendable());
    view.renderMessage("This should fail!");
  }

  //Test message written if command invalid
  @Test
  public void testBrightenCommand() {
    IImage testXSImage;
    Pixel[][] testPixels = new Pixel[][]{
            {new Pixel(255, 0, 0), new Pixel(0, 255, 0), new Pixel(0, 0, 255)},
            {new Pixel(128, 128, 128), new Pixel(64, 64, 64), new Pixel(100, 100, 100)}
    };
    // initialize the image:
    testXSImage = new ImageImpl(testPixels[0].length, testPixels.length);
    for (int i = 0; i < testPixels.length; i++) {
      for (int j = 0; j < testPixels[0].length; j++) {
        Pixel pixel = testPixels[i][j];
        testXSImage.setPixel(j, i, pixel.getR(), pixel.getG(), pixel.getB());
      }
    }

    IImageDataBase modelDB = new ImageDataBase();
    modelDB.add("testXSImage", testXSImage);
    //no command to run! Should write "Invalid command." to run dialogue
    String input = " 30 testXSImage testXS-brightened-30";

    String expectedOutput = ("Invalid command.Invalid command.Invalid command.");
    InputStream in = new ByteArrayInputStream(input.getBytes());
    Readable readable = new InputStreamReader(in);
    ByteArrayOutputStream outStream = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(outStream);

    //Redirect System.out to the ByteArrayOutputStream
    PrintStream originalSystemOut = System.out;
    System.setOut(out);

    IImageState image = testXSImage;

    IImageTextView view = new TextView(image, out);
    IController controller = new ControllerImpl(readable, modelDB, out);
    controller.start();

    //Restore original System.out
    System.setOut(originalSystemOut);
    //Retrieve the captured output from ByteArrayOutputStream
    String actualOutput = outStream.toString();
    assertEquals(expectedOutput, actualOutput);
  }
}

