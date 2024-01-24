import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.StringReader;
import java.util.HashMap;

import controller.ControllerImpl;
import controller.IController;
import controller.io.IImageLoader;
import controller.io.JPGImageLoader;
import controller.io.PNGImageLoader;
import controller.io.PPMImageLoader;
import controller.io.PPMImageSaver;
import model.IImage;
import model.IImageDataBase;
import model.IImageState;
import model.ImageDataBase;
import model.ImageImpl;
import model.Pixel;
import view.IImageTextView;
import view.TextView;

/**
 * Represents the tests for all command patterns that can be entered by the user into the
 * program to transform the images and load/save them to the database.
 */
public class CommandPatternTests {
  private HashMap<String, IImageState> testImagesDB;
  private IImageDataBase modelDB;
  private IImage testXSImage;
  private IController controller;

  @Before
  public void setup() {
    Readable in = new StringReader("loadPPM test/testXSimage3.ppm testXSImage");
    Appendable appendable = new StringBuilder();
    modelDB = new ImageDataBase();
    controller = new ControllerImpl(in, modelDB, appendable);

    //pass the existing modelDB instance to the PPMImageSaver
    Pixel[][] testPixels = new Pixel[][]{
            {new Pixel(255, 0, 0), new Pixel(0, 255, 0), new Pixel(0, 0, 255)},
            {new Pixel(128, 128, 128), new Pixel(64, 64, 64), new Pixel(100, 100, 100)}
    };
    // initialize the image:
    testXSImage = new ImageImpl(testPixels[0].length, testPixels.length);
    for (int i = 0; i < testPixels.length; i++) {
      for (int j = 0; j < testPixels[0].length; j++) {
        Pixel pixel = testPixels[i][j];
        testXSImage.setPixel(i, j, pixel.getR(), pixel.getG(), pixel.getB());
      }
    }
  }

  //save to file working correctly
  @Test
  public void testSaveToFile() {
    String pathToSave = "test/testXSImage3.ppm";
    //IImageState image = testXSImage;

    //create a StringBuilder to capture the output
    StringBuilder stringBuilder = new StringBuilder();
    File outputFile = new File(pathToSave);
    try (FileWriter fileWriter = new FileWriter(outputFile)) {
      PPMImageSaver saver = new PPMImageSaver(pathToSave, testXSImage, fileWriter);
      saver.run();
    } catch (IOException e) {
      throw new IllegalStateException("File could not be written.");
    }
    assertEquals(2, testXSImage.getHeight());
  }


  //test that loaded images saved to DB correctly
  @Test
  public void testLoadPPMCommand() {
    Readable in = new StringReader("loadPPM test/testXSimage3.ppm testXSImage");
    IImageState image = testXSImage;
    controller.start();
    //fetch the image from DB to make sure it worked
    IImageState fetchedImage = modelDB.get("testXSImage");
    assertEquals(fetchedImage.getWidth(), 3);
  }

  //test transformations work correctly
  @Test
  public void testBrightenCommand() {
    modelDB.add("testXSImage", testXSImage);
    String input = "brighten 30 testXSImage testXS-brightened-30";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    Readable readable = new InputStreamReader(in);
    ByteArrayOutputStream outStream = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(outStream);
    IImageState image = testXSImage;

    IImageTextView view = new TextView(image, out);
    System.out.println(view.toString()); //compare the RGB values to the transformed IImageState

    controller = new ControllerImpl(readable, modelDB, out);
    controller.start();

    IImageState fetchedImage = modelDB.get("testXS-brightened-30");
    IImageTextView view2 = new TextView(fetchedImage, out);
    System.out.println(view2.toString());
    assertEquals(2, fetchedImage.getHeight());
  }

  @Test
  public void testDarkenWithBrightenCommand() {
    modelDB.add("testXSImage", testXSImage);
    String input = "brighten -30 testXSImage testXS-darkened-30";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    Readable readable = new InputStreamReader(in);
    ByteArrayOutputStream outStream = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(outStream);
    IImageState image = testXSImage;

    IImageTextView view = new TextView(image, out);
    System.out.println(view.toString()); //compare the darkened RGB values to the original IImage

    controller = new ControllerImpl(readable, modelDB, out);
    controller.start();

    IImageState fetchedImage = modelDB.get("testXS-darkened-30");
    IImageTextView view2 = new TextView(fetchedImage, out);
    System.out.println(view2.toString());
    assertEquals(2, fetchedImage.getHeight());
  }


  @Test
  public void testGreyScaleChannelRedCommand() {
    modelDB.add("testXSImage", testXSImage);
    String input = "color-channel red testXSImage testXS-red-channel";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    Readable readable = new InputStreamReader(in);
    ByteArrayOutputStream outStream = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(outStream);
    IImageState image = testXSImage;

    IImageTextView view = new TextView(image, out);
    System.out.println(view.toString()); //compare the darkened RGB values to the original IImage

    controller = new ControllerImpl(readable, modelDB, out);
    controller.start();

    IImageState fetchedImage = modelDB.get("testXS-red-channel");
    IImageTextView view2 = new TextView(fetchedImage, out);
    System.out.println(view2.toString());
    assertEquals(2, fetchedImage.getHeight());
  }

  @Test
  public void testGreyScaleChannelGreenCommand() {
    modelDB.add("testXSImage", testXSImage);
    String input = "color-channel green testXSImage testXS-green-channel";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    Readable readable = new InputStreamReader(in);
    ByteArrayOutputStream outStream = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(outStream);
    IImageState image = testXSImage;

    IImageTextView view = new TextView(image, out);
    System.out.println(view.toString()); //compare the darkened RGB values to the original IImage

    controller = new ControllerImpl(readable, modelDB, out);
    controller.start();

    IImageState fetchedImage = modelDB.get("testXS-green-channel");
    IImageTextView view2 = new TextView(fetchedImage, out);
    System.out.println(view2.toString());
    assertEquals(2, fetchedImage.getHeight());
  }

  @Test
  public void testGreyScaleChannelBlueCommand() {
    modelDB.add("testXSImage", testXSImage);
    String input = "color-channel blue testXSImage testXS-blue-channel";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    Readable readable = new InputStreamReader(in);
    ByteArrayOutputStream outStream = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(outStream);
    IImageState image = testXSImage;

    IImageTextView view = new TextView(image, out);
    System.out.println(view.toString()); //compare the darkened RGB values to the original IImage

    controller = new ControllerImpl(readable, modelDB, out);
    controller.start();

    IImageState fetchedImage = modelDB.get("testXS-blue-channel");
    IImageTextView view2 = new TextView(fetchedImage, out);
    System.out.println(view2.toString());
    assertEquals(2, fetchedImage.getHeight());
  }

  @Test
  public void testGreyScaleLumaCommand() {
    modelDB.add("testXSImage", testXSImage);
    String input = "luma-component testXSImage testXS-greyscale-luma";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    Readable readable = new InputStreamReader(in);
    ByteArrayOutputStream outStream = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(outStream);
    IImageState image = testXSImage;

    IImageTextView view = new TextView(image, out);
    System.out.println(view.toString()); //compare the darkened RGB values to the original IImage

    controller = new ControllerImpl(readable, modelDB, out);
    controller.start();

    IImageState fetchedImage = modelDB.get("testXS-greyscale-luma");
    IImageTextView view2 = new TextView(fetchedImage, out);
    System.out.println(view2.toString());
    assertEquals(2, fetchedImage.getHeight());
  }

  @Test
  public void testGreyScaleIntensityCommand() {
    modelDB.add("testXSImage", testXSImage);
    String input = "intensity-component testXSImage testXS-greyscale-intensity";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    Readable readable = new InputStreamReader(in);
    ByteArrayOutputStream outStream = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(outStream);
    IImageState image = testXSImage;

    IImageTextView view = new TextView(image, out);
    System.out.println(view.toString()); //compare the darkened RGB values to the original IImage

    controller = new ControllerImpl(readable, modelDB, out);
    controller.start();

    IImageState fetchedImage = modelDB.get("testXS-greyscale-intensity");
    IImageTextView view2 = new TextView(fetchedImage, out);
    System.out.println(view2.toString());
    assertEquals(2, fetchedImage.getHeight());
  }

  @Test
  public void testGreyScaleValueCommand() {
    modelDB.add("testXSImage", testXSImage);
    String input = "value-component testXSImage testXS-greyscale-value";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    Readable readable = new InputStreamReader(in);
    ByteArrayOutputStream outStream = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(outStream);
    IImageState image = testXSImage;

    IImageTextView view = new TextView(image, out);
    System.out.println(view.toString()); //compare the darkened RGB values to the original IImage

    controller = new ControllerImpl(readable, modelDB, out);
    controller.start();

    IImageState fetchedImage = modelDB.get("testXS-greyscale-value");
    IImageTextView view2 = new TextView(fetchedImage, out);
    System.out.println(view2.toString());
    assertEquals(2, fetchedImage.getHeight());
  }

  @Test
  public void testBlurCommand() {
    modelDB.add("testXSImage", testXSImage);
    String input = "blur testXSImage testXS-blurred";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    Readable readable = new InputStreamReader(in);
    ByteArrayOutputStream outStream = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(outStream);
    IImageState image = testXSImage;

    IImageTextView view = new TextView(image, out);
    System.out.println(view.toString()); //compare the blurred RGB values to the original IImage

    controller = new ControllerImpl(readable, modelDB, out);
    controller.start();

    System.out.println(modelDB.getImages());
    IImageState fetchedImage = modelDB.get("testXS-blurred");
    IImageTextView view2 = new TextView(fetchedImage, out);
    System.out.println(view2.toString());
    assertEquals(2, fetchedImage.getHeight());

  }

  @Test
  public void testSharpenCommand() {
    modelDB.add("testXSImage", testXSImage);
    String input = "sharpen testXSImage testXS-sharpened";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    Readable readable = new InputStreamReader(in);
    ByteArrayOutputStream outStream = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(outStream);
    IImageState image = testXSImage;

    IImageTextView view = new TextView(image, out);
    System.out.println(view.toString()); //compare the blurred RGB values to the original IImage

    controller = new ControllerImpl(readable, modelDB, out);
    controller.start();

    System.out.println(modelDB.getImages());
    IImageState fetchedImage = modelDB.get("testXS-sharpened");
    IImageTextView view2 = new TextView(fetchedImage, out);
    System.out.println(view2.toString());
    assertEquals(2, fetchedImage.getHeight());
  }

  /**
   * Testing the SaveCommand. Should be able to detect extension type and initialize the correct
   * IImageSaver object correctly.
   * Test by saving an image in different file format, then loading it back into the model.
   * then create a new model image out of it. Compare this to the original model in view.
   */
  @Test
  public void testSavePPMFromGeneralSaveCommand() {
    modelDB.add("testXSImage", testXSImage);
    String input = "save test/testXSImage_test.ppm testXSImage";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    Readable readable = new InputStreamReader(in);
    ByteArrayOutputStream outStream = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(outStream);

    controller = new ControllerImpl(readable, modelDB, out);
    controller.start();

    //now try loading the saved image back into the model
    IImageLoader loader = new PPMImageLoader("test/testXSImage_test.ppm");
    IImageState imageTest = loader.run();
    assertEquals(2, imageTest.getHeight());
  }

  @Test
  public void testSavePNGFromGeneralSaveCommand() {
    IImageLoader loader = new PNGImageLoader("test/Koala.png");
    IImageState koala = loader.run();
    //it is loaded correctly!
    assertEquals(768, koala.getHeight());
    modelDB.add("koala", koala);
    String input = "save test/koala_test.png koala";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    Readable readable = new InputStreamReader(in);
    ByteArrayOutputStream outStream = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(outStream);

    //create view of original image model to compare new saved/loaded image to
    IImageTextView view = new TextView(koala);

    controller = new ControllerImpl(readable, modelDB, out);
    controller.start();

    //now try loading the saved image back into the model
    IImageLoader loaderNew = new PNGImageLoader("test/koala_test.png");
    IImageState imageTest = loaderNew.run();
    // new view made from saved/loaded image
    IImageTextView viewNew = new TextView(imageTest);
    assertEquals(view.toString(), viewNew.toString());
  }

  @Test
  public void testSaveJPGFromGeneralSaveCommand() {
    IImageLoader loader = new PNGImageLoader("test/Koala.png");
    IImageState koala = loader.run();
    //it is loaded correctly!
    assertEquals(768, koala.getHeight());
    modelDB.add("koala", koala);
    String input = "save test/koala_test.jpg koala";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    Readable readable = new InputStreamReader(in);
    ByteArrayOutputStream outStream = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(outStream);

    //create view of original image model to compare new saved/loaded image to
    IImageTextView view = new TextView(koala);

    controller = new ControllerImpl(readable, modelDB, out);
    controller.start();

    //now try loading the newly saved image back into the model
    IImageLoader loaderNew = new JPGImageLoader("test/koala_test.jpg");
    IImageState imageTest = loaderNew.run();

    // new view made from saved/loaded image
    assertEquals(768, imageTest.getHeight());
  }

  // Testing GENERAL LoadCommand pattern
  @Test
  public void testLoadPPMGeneralLoadCommand() {
    String input = "load test/fourxfour.ppm fourxfour";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    Readable readable = new InputStreamReader(in);
    ByteArrayOutputStream outStream = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(outStream);

    controller = new ControllerImpl(readable, modelDB, out);
    controller.start();

    //test that image in database correctly:
    IImageState fetchedImage = modelDB.get("fourxfour");
    assertEquals(4, fetchedImage.getHeight());
  }

  @Test
  public void testLoadPNGGeneralLoadCommand() {
    String input = "load test/koala_test.png koala-png";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    Readable readable = new InputStreamReader(in);
    ByteArrayOutputStream outStream = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(outStream);

    controller = new ControllerImpl(readable, modelDB, out);
    controller.start();

    //test that image in database correctly:
    IImageState fetchedImage = modelDB.get("koala-png");
    assertEquals(768, fetchedImage.getHeight());
  }

  @Test
  public void testLoadJPGGeneralLoadCommand() {
    String input = "load test/koala_test.jpg koala-jpg";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    Readable readable = new InputStreamReader(in);
    ByteArrayOutputStream outStream = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(outStream);

    controller = new ControllerImpl(readable, modelDB, out);
    controller.start();

    //test that image in database correctly:
    IImageState fetchedImage = modelDB.get("koala-jpg");
    assertEquals(768, fetchedImage.getHeight());
  }

  @Test
  public void testTwoCommands() {
    String input = "load test/koala_test.jpg koala-jpg\n" +
            "blur koala-jpg koala-jpg-blurred\n" +
            "save test/koala-blurred-test-test.jpg koala-jpg-blurred";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    Readable readable = new InputStreamReader(in);
    ByteArrayOutputStream outStream = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(outStream);

    controller = new ControllerImpl(readable, modelDB, out);
    controller.start();

    //test that image in database correctly:
    IImageState fetchedImage = modelDB.get("koala-jpg-blurred");
    assertEquals(768, fetchedImage.getHeight());
  }

  @Test
  public void testFromTXTScriptCommands() {
    String input = "load res/July4th.png july-png\n" +
            "blur july-png july-png-blurred\n" +
            "save res/July-blurred_test_Test.png july-png-blurred";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    Readable readable = new InputStreamReader(in);
    ByteArrayOutputStream outStream = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(outStream);

    controller = new ControllerImpl(readable, modelDB, out);
    controller.start();

    //test that image in database correctly:
    IImageState fetchedImage = modelDB.get("july-png-blurred");
    assertEquals(909, fetchedImage.getWidth());
  }


  @Test(expected = IllegalStateException.class)
  public void testErrorWritten() {
    String pathToSave = "";
    //error should be thrown, no filePath given!!

    StringBuilder stringBuilder = new StringBuilder();
    File outputFile = new File(pathToSave);
    try (FileWriter fileWriter = new FileWriter(outputFile)) {
      PPMImageSaver saver = new PPMImageSaver(pathToSave, testXSImage, fileWriter);
      saver.run();
    } catch (IOException e) {
      throw new IllegalStateException("File could not be written.");
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidChannelInCommand() {
    modelDB.add("testXSImage", testXSImage);
    String input = "color-channel pink testXSImage testXS-blue-channel";
    String expected = ("Invalid channel chosen, please choose red, "
            + "green, or blue.");
    InputStream in = new ByteArrayInputStream(input.getBytes());
    Readable readable = new InputStreamReader(in);
    ByteArrayOutputStream outStream = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(outStream);

    PrintStream originalSystemOut = System.out;
    System.setOut(out);

    IImageState image = testXSImage;

    IImageTextView view = new TextView(image, out);
    System.out.println(view.toString()); //compare the darkened RGB values to the original IImage

    controller = new ControllerImpl(readable, modelDB, out);
    controller.start();

    //Restore original System.out
    System.setOut(originalSystemOut);
    String actualOutput = outStream.toString();
    assertEquals(expected, actualOutput);
  }
}


