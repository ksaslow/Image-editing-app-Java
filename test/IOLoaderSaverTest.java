import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;


import javax.imageio.ImageIO;

import controller.io.BMPImageLoader;
import controller.io.BMPImageSaver;
import controller.io.JPGImageLoader;
import controller.io.JPGImageSaver;
import controller.io.PNGImageLoader;
import controller.io.PNGImageSaver;
import controller.io.PPMImageLoader;
import controller.io.PPMImageSaver;
import model.IImage;
import model.IImageDataBase;
import model.IImageState;
import model.ImageDataBase;
import model.ImageImpl;
import model.Pixel;
import model.transformations.BrightenTransformation;
import model.transformations.ITransformation;


/**
 * Represents the tests for the IO package's PPMImageLoader and PPMImageSaver classes. The image
 * simply takes a .ppm filepath from the user and loads the image. The PPMImage saver will save an
 * image as a new .ppm file, and add it to the database
 */
public class IOLoaderSaverTest {
  private HashMap<String, IImageState> testImagesDB;
  private IImage testImageXS;

  @Before
  public void setup() {
    Pixel[][] testPixels = new Pixel[][]{
            {new Pixel(255, 0, 0), new Pixel(0, 255, 0), new Pixel(0, 0, 255)},
            {new Pixel(128, 128, 128), new Pixel(64, 64, 64), new Pixel(100, 100, 100)}
    };
    // initialize the image:
    testImageXS = new ImageImpl(testPixels[0].length, testPixels.length);
    for (int i = 0; i < testPixels.length; i++) {
      for (int j = 0; j < testPixels[0].length; j++) {
        Pixel pixel = testPixels[i][j];
        testImageXS.setPixel(i, j, pixel.getR(), pixel.getG(), pixel.getB());
      }
    }
  }


  @Test
  public void testLoad() {
    PPMImageLoader loader = new PPMImageLoader("test/fourxfour.ppm");
    IImageState loadedImage = loader.run();
    assertEquals(4, loadedImage.getHeight());
  }

  @Test
  public void testLoadLargerImage() {
    PPMImageLoader loader = new PPMImageLoader("res/Lake.ppm");
    IImageState loadedImage = loader.run();
    assertEquals(481, loadedImage.getHeight());
    assertEquals(360, loadedImage.getWidth());
  }

  //Test saving the IImages with PPMImageSaver
  @Test
  public void testSaveToString() {
    String pathToSave = "test/testXSImage.ppm";
    IImageState image = testImageXS;

    //create a StringBuilder to capture the output
    StringBuilder stringBuilder = new StringBuilder();

    PPMImageSaver saver = new PPMImageSaver(pathToSave, testImageXS, stringBuilder);
    saver.run();
    String expected = "P3\n"
            + "3 2\n" //first width, then height.
            + "255\n"
            + "255\n"
            + "0\n"
            + "0\n"
            + "0\n"
            + "255\n"
            + "0\n"
            + "0\n"
            + "0\n"
            + "255\n"
            + "128\n"
            + "128\n"
            + "128\n"
            + "64\n"
            + "64\n"
            + "64\n"
            + "100\n"
            + "100\n"
            + "100\n";
    assertEquals(expected, stringBuilder.toString());
  }

  @Test
  public void testSaveToFile() {
    String pathToSave = "test/testXSImage.ppm";
    IImageState image = testImageXS;

    //create a StringBuilder to capture the output
    StringBuilder stringBuilder = new StringBuilder();
    File outputFile = new File("test/testXSImage.ppm");
    try (FileWriter fileWriter = new FileWriter(outputFile)) {
      PPMImageSaver saver = new PPMImageSaver(pathToSave, testImageXS, fileWriter);
      saver.run();
    } catch (IOException e) {
      throw new IllegalStateException("File could not be written.");
    }
    assertEquals(2, testImageXS.getHeight());
  }

  @Test
  public void testLoadTransformSaveToFileAddToDB() {
    PPMImageLoader loader = new PPMImageLoader("test/fourxfour.ppm");
    //load image from file
    IImageState loadedImage = loader.run();
    IImageDataBase modelDB = new ImageDataBase();
    //add original image to database
    modelDB.add("fourxfour", loadedImage);
    //set transformation
    ITransformation brightenTransformation = new BrightenTransformation(30);
    // transform the image
    IImageState brightenedImage = brightenTransformation.run(loadedImage);
    File transformedImageFile = new File("test/brightened_fourxfour.ppm");
    try (FileWriter fileWriter = new FileWriter(transformedImageFile)) {
      PPMImageSaver saver = new PPMImageSaver("test/brightened_fourxfour.ppm",
              brightenedImage, fileWriter);
      saver.run();
    } catch (IOException e) {
      throw new IllegalStateException("File could not be written.");
    }
    modelDB.add("brightened_fourxfour", brightenedImage);
    System.out.println(modelDB.toString());

    //test that the fetched images are the same
    IImageState fetchedOriginalImage = modelDB.get("fourxfour");
    IImageState fetchedBrightenedImage = modelDB.get("brightened_fourxfour");
    assertEquals(fetchedOriginalImage, loadedImage);
    assertEquals(fetchedBrightenedImage, brightenedImage);
  }

  // test PNGImageLoader class:
  @Test
  public void testLoadPNG() {
    PNGImageLoader loader = new PNGImageLoader("res/July4th.png");
    IImageState loadedImage = loader.run();
    assertEquals(1212, loadedImage.getHeight());
    assertEquals(909, loadedImage.getWidth());
  }

  @Test
  public void testLoadKoalaPng() {
    PNGImageLoader loader = new PNGImageLoader("test/Koala.png");
    IImageState loadedImage = loader.run();
    assertEquals(768, loadedImage.getHeight());
    assertEquals(1024, loadedImage.getWidth());
  }

  // test JPGImageLoader class:
  @Test
  public void testLoadJPG() {
    JPGImageLoader loader = new JPGImageLoader("res/July4th.jpg");
    IImageState loadedImage = loader.run();
    assertEquals(1212, loadedImage.getHeight());
    assertEquals(909, loadedImage.getWidth());
  }

  // test that exceptions thrown correctly:
  @Test
  public void testInvalidFilePathJPG() {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(outputStream);

    //Redirect System.out to the ByteArrayOutputStream
    PrintStream originalSystemOut = System.out;
    System.setOut(out);

    JPGImageLoader loader = new JPGImageLoader("July4th.jpg"); //incomplete filePath
    IImageState loadedImage = loader.run();
    String expectedErrorMessage = "Failed to read JPG image: Can't read input file!"
            + System.lineSeparator();

    //Restore original System.out
    System.setOut(originalSystemOut);
    String actualOutput = outputStream.toString();
    assertEquals(expectedErrorMessage, actualOutput);
  }

  @Test
  public void testInvalidFilePathPNG() {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(outputStream);

    //Redirect System.out to the ByteArrayOutputStream
    PrintStream originalSystemOut = System.out;
    System.setOut(out);

    PNGImageLoader loader = new PNGImageLoader("July4th.png"); //incomplete filePath
    IImageState loadedImage = loader.run();
    String expectedErrorMessage = "Failed to read PNG image: Can't read input file!"
            + System.lineSeparator();

    //Restore original System.out
    System.setOut(originalSystemOut);
    String actualOutput = outputStream.toString();
    assertEquals(expectedErrorMessage, actualOutput);
  }

  @Test
  public void testInvalidBMP() {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(outputStream);

    //Redirect System.out to the ByteArrayOutputStream
    PrintStream originalSystemOut = System.out;
    System.setOut(out);

    BMPImageLoader loader = new BMPImageLoader("July4th.beep"); //invalid file extension
    IImageState loadedImage = loader.run();
    String expectedErrorMessage = "Failed to read BMP image: Can't read input file!"
            + System.lineSeparator();

    //Restore original System.out
    System.setOut(originalSystemOut);
    String actualOutput = outputStream.toString();
    assertEquals(expectedErrorMessage, actualOutput);
  }

  //testing save to PNG file
  @Test
  public void testSaveToPNGFile() {
    String pathToSave = "test/testXSImage_new.png";
    IImageState image = testImageXS;

    //create a ByteArrayOutputStream to capture the output
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    PNGImageSaver saver = new PNGImageSaver(pathToSave, testImageXS, outputStream);

    //run the PNGImageSaver
    saver.run();

    //get the byte array from ByteArrayOutputStream
    byte[] byteArray = outputStream.toByteArray();


    try {
      ByteArrayInputStream inputStream = new ByteArrayInputStream(byteArray);
      BufferedImage savedImage = ImageIO.read(inputStream);

      //now check properties of savedImage to compare it with original image
      assertEquals(2, savedImage.getHeight());

    } catch (IOException e) {
      throw new IllegalStateException("File could not be written.");
    }
  }

  //testing save to JPG file
  @Test
  public void testSaveToJPGFile() {
    String pathToSave = "test/testXSImage.jpg";
    IImageState image = testImageXS;

    //create a ByteArrayOutputStream to capture the output
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    JPGImageSaver saver = new JPGImageSaver(pathToSave, testImageXS, outputStream);

    //run the PNGImageSaver
    saver.run();

    //get the byte array from ByteArrayOutputStream
    byte[] byteArray = outputStream.toByteArray();


    try {
      ByteArrayInputStream inputStream = new ByteArrayInputStream(byteArray);
      BufferedImage savedImage = ImageIO.read(inputStream);

      //now check properties of savedImage to compare it with original image
      assertEquals(2, savedImage.getHeight());

    } catch (IOException e) {
      throw new IllegalStateException("File could not be written.");
    }
  }

  //test saving BMP file
  @Test
  public void testSaveToBMPFile() {
    String pathToSave = "test/testXSImage.bmp";
    IImageState image = testImageXS;

    //create a ByteArrayOutputStream to capture the output
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    BMPImageSaver saver = new BMPImageSaver(pathToSave, testImageXS, outputStream);

    //run the PNGImageSaver
    saver.run();

    //get the byte array from ByteArrayOutputStream
    byte[] byteArray = outputStream.toByteArray();

    try {
      ByteArrayInputStream inputStream = new ByteArrayInputStream(byteArray);
      BufferedImage savedImage = ImageIO.read(inputStream);

      //now check properties of savedImage to compare it with original image
      assertEquals(2, savedImage.getHeight());

    } catch (IOException e) {
      throw new IllegalStateException("File could not be written.");
    }
  }

  //test that the right exceptions are thrown if the JPG/PNG/BMP file could not be written
  @Test(expected = IllegalStateException.class)
  public void testFailedToWriteToPNG() {
    String pathToSave = ""; // invalid file name!!!

    //create a ByteArrayOutputStream to capture the output
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    PNGImageSaver saver = new PNGImageSaver(pathToSave, testImageXS, outputStream);
    //run the PNGImageSaver
    saver.run(); // error thrown here, IImageSaver can't run without filename!
  }

  @Test(expected = IllegalStateException.class)
  public void testFailedBMPWrongExtension() {
    String pathToSave = "test/"; //incomplete file type, image should be thrown

    //create a ByteArrayOutputStream to capture the output
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    BMPImageSaver saver = new BMPImageSaver(pathToSave, testImageXS, outputStream);

    //run the PNGImageSaver
    saver.run(); // error thrown here, IImageSaver can't run without filename!
  }
}


