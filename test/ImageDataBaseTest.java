import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;


import model.IImage;
import model.IImageState;
import model.ImageDataBase;
import model.ImageImpl;
import model.Pixel;


/**
 * Represents the tests for the ImageDataBase object within the model. The ImageDataBase is
 * a HashMap made of different IImageStates.
 */
public class ImageDataBaseTest {
  private HashMap<String, IImageState> testImagesDB;
  private IImage testImage;

  @Before
  public void setup() {
    Pixel[][] testPixels = new Pixel[][]{
            {new Pixel(255, 0, 0), new Pixel(0, 255, 0), new Pixel(0, 0, 255)},
            {new Pixel(128, 128, 128), new Pixel(64, 64, 64), new Pixel(100, 100, 100)}
    };
    // initialize the image:
    testImage = new ImageImpl(testPixels[0].length, testPixels.length);
    for (int i = 0; i < testPixels.length; i++) {
      for (int j = 0; j < testPixels[0].length; j++) {
        Pixel pixel = testPixels[i][j];
        testImage.setPixel(j, i, pixel.getR(), pixel.getG(), pixel.getB());
      }
    }
  }

  @Test
  public void testAddAndGet() {
    //make sure image was added to hashmap correctly:
    //add image to HashMap:
    ImageDataBase model = new ImageDataBase();
    model.add("testImage", testImage);
    IImageState imageFetched = model.get("testImage");
    assertEquals(testImage, imageFetched);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullArgumentExceptionsThrown() {
    String id = null;
    ImageDataBase model = new ImageDataBase();
    model.add(id, testImage);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullArgumentExceptionsThrown2() {
    String id = "testImage";
    ImageDataBase model = new ImageDataBase();
    IImageState image = null;
    model.add(id, image);
  }
}