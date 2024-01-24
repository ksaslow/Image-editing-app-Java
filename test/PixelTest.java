import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

import model.Pixel;


/**
 * Represents the tests for the Pixel class. The Pixel is the smallest component of an image. In
 * any given image, the pixels are what is actually be manipulated by the transformation.
 */
public class PixelTest {
  private Pixel pixel;

  @Before
  public void setup() {
    pixel = new Pixel(200, 75, 100);
  }

  // Test the getter methods:
  @Test
  public void testGetR() {
    assertEquals(200, pixel.getR());
  }

  @Test
  public void testGetG() {
    assertEquals(75, pixel.getG());
  }

  @Test
  public void testGetB() {
    assertEquals(100, pixel.getB());
  }

  @Test
  public void testGetAlpha() {
    assertEquals(0, pixel.getAlpha(), 0.1);
  }

  //Test setter methods:
  @Test
  public void testSetR() {
    pixel.setR(225);
    assertEquals(225, pixel.getR());
  }

  @Test
  public void testSetG() {
    pixel.setG(90);
    assertEquals(90, pixel.getG());
  }

  @Test
  public void testSetB() {
    pixel.setB(200);
    assertEquals(200, pixel.getB());
  }

  //Test toString()
  @Test
  public void testPixelToString() {
    String expected = "200 75 100";
    assertEquals(expected, pixel.toString());
  }

  //Test execptions if maxValue 255 exceeded
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidR() {
    Pixel invalidR = new Pixel(256, 75, 100);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidG() {
    Pixel invalidR = new Pixel(200, -45, 100);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidB() {
    Pixel invalidR = new Pixel(200, 75, -1);
  }
}
