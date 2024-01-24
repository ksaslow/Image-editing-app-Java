import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

import model.IImage;
import model.ImageImpl;
import model.Pixel;


/**
 * Represents the tests for ImageImpl, the implementation of an Image object. An Image is a
 * 2-Dimensional array of Pixel objects. An image has a width (how many pixels wide), and a
 * height (how many pixels long).
 */
public class ImageImplTest {
  private IImage testImage;

  @Before
  public void setup() {
    Pixel[][] testPixels = new Pixel[][] {
            { new Pixel(255, 0, 0), new Pixel(0, 255, 0), new Pixel(0, 0, 255) },
            { new Pixel(128, 128, 128), new Pixel(64, 64, 64), new Pixel(100, 100, 100) }
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

  //Test exception being thrown for setPixel()
  @Test(expected = IllegalArgumentException.class)
  public void testSetPixelNegative() {
    testImage.setPixel(4, 4, -1, 100, 255);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetPixelOverMax() {
    testImage.setPixel(4, 4, 100, 400, 255);
  }



  @Test
  public void testGetHeight() {
    assertEquals(2, testImage.getHeight());
  }

  @Test
  public void testGetWidth() {
    assertEquals(3, testImage.getWidth());
  }

  // Test getting the channels
  @Test
  public void testGetRedChannel() {
    assertEquals(255, testImage.getRedChannel(0, 0));
    assertEquals(0, testImage.getRedChannel(1, 0));
    assertEquals(0, testImage.getRedChannel(2, 0));
    assertEquals(128, testImage.getRedChannel(0, 1));
    assertEquals(64, testImage.getRedChannel(1, 1));
    assertEquals(64, testImage.getRedChannel(1, 1));
  }

  @Test
  public void testGetGreenChannel() {
    assertEquals(0, testImage.getGreenChannel(0, 0));
    assertEquals(255, testImage.getGreenChannel(1, 0));
    assertEquals(0, testImage.getGreenChannel(2, 0));
    assertEquals(255, testImage.getGreenChannel(1, 0));
    assertEquals(64, testImage.getGreenChannel(1, 1));
    assertEquals(100, testImage.getGreenChannel(2, 1));
  }

  @Test
  public void testGetBlueChannel() {
    assertEquals(0, testImage.getBlueChannel(0, 0));
    assertEquals(128, testImage.getBlueChannel(0, 1));
    assertEquals(255, testImage.getBlueChannel(2, 0));
    assertEquals(0, testImage.getBlueChannel(1, 0));
    assertEquals(64, testImage.getBlueChannel(1, 1));
    assertEquals(100, testImage.getBlueChannel(2, 1));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testXOutOfBoundsGetRed() {
    testImage.getRedChannel(5, 0); //matrix 2x3
  }

  @Test(expected = IllegalArgumentException.class)
  public void testYOutOfBoundsGetGreen() {
    testImage.getGreenChannel(0, -5); //matrix 2x3
  }

  @Test(expected = IllegalArgumentException.class)
  public void testXYOutOfBoundsGetBlue() {
    testImage.getGreenChannel(9, 7); //matrix 2x3
  }

  @Test(expected = IllegalArgumentException.class)
  public void testXOutOfBoundsSetPixel() {
    testImage.setPixel(4,3,20, 20, 20);
  }
}

