import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

import model.ImageImpl;
import model.Kernel;
import model.Pixel;
import model.IImageState;
import view.IImageTextView;
import view.TextView;

/**
 * Represents the test class for a Kernel object.
 */
public class KernelTest {
  private ImageImpl dummyImage;
  private Kernel kernel;

  @Before
  public void setup() {
    //create test image with pixel values
    Pixel[][] testPixels = new Pixel[][] {
            { new Pixel(255, 0, 0), new Pixel(0, 255, 0), new Pixel(0, 0, 255) },
            { new Pixel(128, 128, 128), new Pixel(64, 64, 64), new Pixel(100, 100, 100) }
    };
    dummyImage = new ImageImpl(testPixels[0].length, testPixels.length);
    for (int i = 0; i < testPixels.length; i++) {
      for (int j = 0; j < testPixels[0].length; j++) {
        Pixel pixel = testPixels[i][j];
        dummyImage.setPixel(j, i, pixel.getR(), pixel.getG(), pixel.getB());
      }
    }

    double[][] kernelValues = {
            {0.1, 0.2, 0.1},
            {0.2, 0.3, 0.2},
            {0.1, 0.2, 0.1}
    };
    kernel = new Kernel(3, kernelValues);
  }

  @Test
  public void testKernelApplication() {
    // test altering target pixel dummyImage[1][1]
    int x = 1; //target pixel at row 1
    int y = 1; //target pixel at col 1
    IImageState processedImage = kernel.applyKernel(dummyImage);
    int expectedRed = (int)90.3;
    int expectedGreen = (int)115.3;
    int expectedBlue = (int)90.3;

    int actualRed = processedImage.getRedChannel(x, y);
    int actualGreen = processedImage.getGreenChannel(x, y);
    int actualBlue = processedImage.getBlueChannel(x, y);

    assertEquals(expectedRed, actualRed);
    assertEquals(expectedGreen, actualGreen);
    assertEquals(expectedBlue, actualBlue);
  }

  // test that there is correct overlap and exclusion of kernel pixels
  // with kernel at very corner of image
  @Test
  public void testKernelApplicationCornerPixel() {
    int x = 0; //target pixel at corner
    int y = 0; //target pixel at corner
    IImageState processedImage = kernel.applyKernel(dummyImage);
    int expectedRed = (int)108.5;
    int expectedGreen = 83;
    int expectedBlue = 32;

    int actualRed = processedImage.getRedChannel(x, y);
    int actualGreen = processedImage.getGreenChannel(x, y);
    int actualBlue = processedImage.getBlueChannel(x, y);

    assertEquals(expectedRed, actualRed);
    assertEquals(expectedGreen, actualGreen);
    assertEquals(expectedBlue, actualBlue);
  }

  // test that the whole image is modified correctly with kernel application
  @Test
  public void testWholeImageModifiedWithKernel() {
    int width = dummyImage.getWidth();
    int height = dummyImage.getHeight();
    ImageImpl processedImage = new ImageImpl(width, height);
    IImageTextView view = new TextView(dummyImage);
    //System.out.println(view.toString());

    //apply kernel to entire image:
    // note: in ImageImpl the height and width are swapped, so y here has to represent the row
    // and x should represent the column.
    for (int y = 0; y < width; y++) {
      for (int x = 0; x < height; x++) {
        IImageState resultPixel = kernel.applyKernel(dummyImage);
        //System.out.println(resultPixel.getRedChannel(x, y));
        //System.out.println(x + " " + y);
        processedImage.setPixel(y, x,
                resultPixel.getRedChannel(y, x),
                resultPixel.getGreenChannel(y, x),
                resultPixel.getBlueChannel(y, x));
      }
    }

    // define the expected output image after applying the kernel to entire image
    Pixel[][] expectedPixels = new Pixel[][] {
            { new Pixel((int)108.5,(int)83,(int)32), new Pixel((int)86.6,(int)112.1,(int)86.6),
                new Pixel((int)26.4,(int)77.4,(int)102.9) },
            { new Pixel((int)102.2,(int)76.7,(int)51.2), new Pixel((int)90.3,(int)115.8,(int)90.3),
                new Pixel((int)42.8,(int)68.3,(int)93.8) }
    };

    //check each Pixel in processed image against expected pixel values
    IImageTextView viewProcessedImage = new TextView(processedImage);
    //print view of processedImage to screen to compare
    //System.out.println(viewProcessedImage.toString());

    for (int i = 0; i < expectedPixels.length; i++) {
      for (int j = 0; j < expectedPixels[0].length; j++) {
        Pixel expectedPixel = expectedPixels[i][j]; //iterate through all expected pixels
        int expectedR = expectedPixel.getR();
        int expectedG = expectedPixel.getG();
        int expectedB = expectedPixel.getB();

        int actualR = processedImage.getRedChannel(j, i);
        int actualG = processedImage.getGreenChannel(j, i);
        int actualB = processedImage.getBlueChannel(j, i);

        assertEquals(expectedR, actualR);
        assertEquals(expectedG, actualG);
        assertEquals(expectedB, actualB);
      }
    }
  }

}
