import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

import controller.io.IImageLoader;
import controller.io.JPGImageLoader;
import controller.io.PPMImageLoader;
import model.IImageDataBase;
import model.IImageState;
import model.ImageDataBase;
import model.ImageImpl;
import model.Pixel;
import model.transformations.BlurTransformation;
import model.transformations.BrightenTransformation;
import model.transformations.GreyscaleChannelTransformation;
import model.transformations.GreyscaleLumaTransformation;
import model.transformations.GreyscaleValueTransformation;
import model.transformations.GreyscaleIntensityTransformation;
import model.transformations.SharpenTransformation;
import view.IImageTextView;
import view.TextView;


/**
 * This class represents all tests for PPM image transformations.
 * Transformations to be tested include the greyscale image transformations:
 * GreyscaleChannelTransformation, GreyscaleValueTransformation,
 * GreyscaleIntensityTransformation, and GreyscaleLumaTransformation; and all color image
 * transformations including BrightenImage, which allows the user to brighten and darken the image
 * depending on the sign of the increment value. If the increment value given is positive,
 *  * the program will brighten the image.
 */
public class TransformationsTest {
  private ImageImpl dummyImage;

  @Before
  public void setup() {
    Pixel[][] testPixels = new Pixel[][]{
            {new Pixel(255, 0, 0), new Pixel(0, 255, 0), new Pixel(0, 0, 255)},
            {new Pixel(128, 128, 128), new Pixel(64, 64, 64), new Pixel(100, 100, 100)}
    };
    dummyImage = new ImageImpl(testPixels[0].length, testPixels.length);
    for (int i = 0; i < testPixels.length; i++) {
      for (int j = 0; j < testPixels[0].length; j++) {
        Pixel pixel = testPixels[i][j];
        dummyImage.setPixel(i, j, pixel.getR(), pixel.getG(), pixel.getB());
      }
    }
  }

  @Test
  public void testDBLoadAddImage() {
    //setup a dummy PPM to test
    //MockModel = "fourxfour.ppm" image
    ImageDataBase modelDB = new ImageDataBase();
    IImageLoader imageLoader = new PPMImageLoader("test/fourxfour.ppm");
    IImageState modelImage = imageLoader.run();
    modelDB.add("fourxfour.ppm", modelImage);
    IImageTextView view = new TextView(modelImage);
    String expected =
            "Width of image: 4\n" +
                    "Height of image: 4\n" +
                    "Maximum value of a color in this file (usually 255): 255\n" +
                    "Color of pixel (0,0): 40,35,60\n" +
                    "Color of pixel (1,0): 0,0,0\n" +
                    "Color of pixel (2,0): 220,220,220\n" +
                    "Color of pixel (3,0): 70,70,70\n" +
                    "Color of pixel (0,1): 14,14,14\n" +
                    "Color of pixel (1,1): 0,0,0\n" +
                    "Color of pixel (2,1): 125,170,200\n" +
                    "Color of pixel (3,1): 45,90,90\n" +
                    "Color of pixel (0,2): 40,40,40\n" +
                    "Color of pixel (1,2): 60,20,0\n" +
                    "Color of pixel (2,2): 220,3,25\n" +
                    "Color of pixel (3,2): 75,85,2\n" +
                    "Color of pixel (0,3): 14,14,14\n" +
                    "Color of pixel (1,3): 0,0,0\n" +
                    "Color of pixel (2,3): 200,200,200\n" +
                    "Color of pixel (3,3): 90,90,90\n";
    assertEquals(expected, view.toString());
  }

  // Test the transformations in smaller "images" to make toStrings easier
  @Test
  public void testGreyscaleValueTransformation() {
    //need IImageState to run the transformation
    IImageState dummy = dummyImage;
    String expected =
            "Width of image: 3\n" +
                    "Height of image: 2\n" +
                    "Maximum value of a color in this file (usually 255): 255\n" +
                    "Color of pixel (0,0): 255,255,255\n" +
                    "Color of pixel (1,0): 255,255,255\n" +
                    "Color of pixel (2,0): 255,255,255\n" +
                    "Color of pixel (0,1): 128,128,128\n" +
                    "Color of pixel (1,1): 64,64,64\n" +
                    "Color of pixel (2,1): 100,100,100\n";

    GreyscaleValueTransformation valueTransformation = new GreyscaleValueTransformation();
    IImageState newImage = valueTransformation.run(dummy);
    IImageTextView view = new TextView(newImage);
    assertEquals(expected, view.toString());
  }

  @Test
  public void testGreyscaleIntensityTransformation() {
    IImageState dummy = dummyImage;
    String expected =
            "Width of image: 3\n" +
                    "Height of image: 2\n" +
                    "Maximum value of a color in this file (usually 255): 255\n" +
                    "Color of pixel (0,0): 85,85,85\n" +
                    "Color of pixel (1,0): 85,85,85\n" +
                    "Color of pixel (2,0): 85,85,85\n" +
                    "Color of pixel (0,1): 128,128,128\n" +
                    "Color of pixel (1,1): 64,64,64\n" +
                    "Color of pixel (2,1): 100,100,100\n";

    GreyscaleIntensityTransformation intensityTransformation =
            new GreyscaleIntensityTransformation();
    IImageState newImage = intensityTransformation.run(dummy);
    IImageTextView view = new TextView(newImage);
    assertEquals(expected, view.toString());
  }

  @Test
  public void testGreyscaleLumaTransformation() {
    IImageState dummy = dummyImage;
    String expected =
            "Width of image: 3\n" +
                    "Height of image: 2\n" +
                    "Maximum value of a color in this file (usually 255): 255\n" +
                    "Color of pixel (0,0): 54,54,54\n" +
                    "Color of pixel (1,0): 182,182,182\n" +
                    "Color of pixel (2,0): 18,18,18\n" +
                    "Color of pixel (0,1): 128,128,128\n" +
                    "Color of pixel (1,1): 64,64,64\n" +
                    "Color of pixel (2,1): 100,100,100\n";

    GreyscaleLumaTransformation lumaTransformation =
            new GreyscaleLumaTransformation();
    IImageState newImage = lumaTransformation.run(dummy);
    IImageTextView view = new TextView(newImage);
    assertEquals(expected, view.toString());
  }

  @Test
  public void testGreyscaleChannelRed() {
    IImageState dummy = dummyImage;
    String expected =
            "Width of image: 3\n" +
                    "Height of image: 2\n" +
                    "Maximum value of a color in this file (usually 255): 255\n" +
                    "Color of pixel (0,0): 255,255,255\n" +
                    "Color of pixel (1,0): 0,0,0\n" +
                    "Color of pixel (2,0): 0,0,0\n" +
                    "Color of pixel (0,1): 128,128,128\n" +
                    "Color of pixel (1,1): 64,64,64\n" +
                    "Color of pixel (2,1): 100,100,100\n";

    GreyscaleChannelTransformation channelRedTransformation =
            new GreyscaleChannelTransformation("red");
    IImageState newImage = channelRedTransformation.run(dummy);
    IImageTextView view = new TextView(newImage);
    assertEquals(expected, view.toString());
  }

  @Test
  public void testGreyscaleChannelGreen() {
    IImageState dummy = dummyImage;
    String expected =
            "Width of image: 3\n" +
                    "Height of image: 2\n" +
                    "Maximum value of a color in this file (usually 255): 255\n" +
                    "Color of pixel (0,0): 0,0,0\n" +
                    "Color of pixel (1,0): 255,255,255\n" +
                    "Color of pixel (2,0): 0,0,0\n" +
                    "Color of pixel (0,1): 128,128,128\n" +
                    "Color of pixel (1,1): 64,64,64\n" +
                    "Color of pixel (2,1): 100,100,100\n";

    GreyscaleChannelTransformation channelGreenTransformation =
            new GreyscaleChannelTransformation("green");
    IImageState newImage = channelGreenTransformation.run(dummy);
    IImageTextView view = new TextView(newImage);
    assertEquals(expected, view.toString());
  }

  @Test
  public void testGreyscaleChannelBlue() {
    IImageState dummy = dummyImage;
    String expected =
            "Width of image: 3\n" +
                    "Height of image: 2\n" +
                    "Maximum value of a color in this file (usually 255): 255\n" +
                    "Color of pixel (0,0): 0,0,0\n" +
                    "Color of pixel (1,0): 0,0,0\n" +
                    "Color of pixel (2,0): 255,255,255\n" +
                    "Color of pixel (0,1): 128,128,128\n" +
                    "Color of pixel (1,1): 64,64,64\n" +
                    "Color of pixel (2,1): 100,100,100\n";

    GreyscaleChannelTransformation channelBlueTransformation =
            new GreyscaleChannelTransformation("blue");
    IImageState newImage = channelBlueTransformation.run(dummy);
    IImageTextView view = new TextView(newImage);
    assertEquals(expected, view.toString());
  }

  // For the brighten and darken image tests, ensure that the values are clamped properly
  // between 0 and 255. If brightening the image or darkening the image takes and of the RGB values
  // below 0 or above 255, then values 0 and 255 should be used
  @Test
  public void testBrightenImageBy20() {
    IImageState dummy = dummyImage;
    int incrementValue = 20;
    String expected =
            "Width of image: 3\n" +
                    "Height of image: 2\n" +
                    "Maximum value of a color in this file (usually 255): 255\n" +
                    "Color of pixel (0,0): 255,20,20\n" +
                    "Color of pixel (1,0): 20,255,20\n" +
                    "Color of pixel (2,0): 20,20,255\n" +
                    "Color of pixel (0,1): 148,148,148\n" +
                    "Color of pixel (1,1): 84,84,84\n" +
                    "Color of pixel (2,1): 120,120,120\n";

    BrightenTransformation brightenTransformation =
            new BrightenTransformation(20);
    IImageState newImage = brightenTransformation.run(dummy);
    IImageTextView view = new TextView(newImage);
    assertEquals(expected, view.toString());
  }

  // For the brighten and darken image tests, ensure that the values are clamped properly
  // between 0 and 255. If brightening the image or darkening the image takes and of the RGB values
  // below 0 or above 255, then values 0 and 255 should be used
  @Test
  public void testDarkenTransformation() {
    IImageState dummy = dummyImage;
    int incrementValue = -20;
    String expected =
            "Width of image: 3\n" +
                    "Height of image: 2\n" +
                    "Maximum value of a color in this file (usually 255): 255\n" +
                    "Color of pixel (0,0): 235,0,0\n" +
                    "Color of pixel (1,0): 0,235,0\n" +
                    "Color of pixel (2,0): 0,0,235\n" +
                    "Color of pixel (0,1): 108,108,108\n" +
                    "Color of pixel (1,1): 44,44,44\n" +
                    "Color of pixel (2,1): 80,80,80\n";

    BrightenTransformation darkenTransformation =
            new BrightenTransformation(-20);
    IImageState newImage = darkenTransformation.run(dummy);
    IImageTextView view = new TextView(newImage);
    assertEquals(expected, view.toString());
  }

  //Test BlurTransformation
  @Test
  public void testBlurTransformation() {
    IImageState dummy = dummyImage;
    // note that toString prints Color of pixel (COL, ROW), not row, col
    String expected = "Width of image: 3\n" +
            "Height of image: 2\n" +
            "Maximum value of a color in this file (usually 255): 255\n" +
            "Color of pixel (0,0): 83,51,20\n" +
            "Color of pixel (1,0): 54,86,54\n" +
            "Color of pixel (2,0): 16,48,80\n" +
            "Color of pixel (0,1): 71,55,40\n" +
            "Color of pixel (1,1): 60,76,60\n" +
            "Color of pixel (2,1): 33,48,64\n";
    BlurTransformation blurTransformation = new BlurTransformation();
    IImageState blurredImage = blurTransformation.run(dummy);
    IImageTextView view = new TextView(blurredImage);
    assertEquals(expected, view.toString());
  }

  //Test SharpenTransformation
  @Test
  public void testSharpenTransformation() {
    IImageState dummy = dummyImage;
    String expected = "Width of image: 3\n" +
            "Height of image: 2\n" +
            "Maximum value of a color in this file (usually 255): 255\n" +
            "Color of pixel (0,0): 255,99,3\n" +
            "Color of pixel (1,0): 136,255,136\n" +
            "Color of pixel (2,0): 0,88,255\n" +
            "Color of pixel (0,1): 195,195,99\n" +
            "Color of pixel (1,1): 184,184,184\n" +
            "Color of pixel (2,1): 68,163,163\n";
    SharpenTransformation sharpenTransformation = new SharpenTransformation();
    IImageState sharpenedImage = sharpenTransformation.run(dummy);
    IImageTextView view = new TextView(sharpenedImage);

    assertEquals(expected, view.toString());
  }

  //Test BlurTransformation on LARGE image.
  @Test
  public void testBlurTransformationMedium() {
    ImageDataBase modelDB = new ImageDataBase();
    //load image
    IImageLoader loader = new PPMImageLoader("test/july-med.ppm");
    IImageState imageJuly = loader.run();
    BlurTransformation blur = new BlurTransformation();
    IImageState blurredImage = blur.run(imageJuly);
    System.out.println("Successfully blurred image.");
    modelDB.add("blurredImage", blurredImage);
    assertEquals(blurredImage, modelDB.get("blurredImage"));
  }

  @Test
  public void testBlurTransformationKoala() {
    ImageDataBase modelDB = new ImageDataBase();
    //load image
    IImageLoader loader = new PPMImageLoader("test/Koala.ppm");
    IImageState imageJuly = loader.run();
    BlurTransformation blur = new BlurTransformation();
    IImageState blurredImage = blur.run(imageJuly);
    System.out.println("Successfully blurred image.");
    modelDB.add("blurredImage", blurredImage);
    assertEquals(blurredImage, modelDB.get("blurredImage"));
  }

  @Test
  public void testBlurTransformationLarge() {
    ImageDataBase modelDB = new ImageDataBase();
    //load image
    IImageLoader loader = new PPMImageLoader("test/July4th.ppm");
    IImageState imageJuly = loader.run();
    BlurTransformation blur = new BlurTransformation();
    IImageState blurredImage = blur.run(imageJuly);
    System.out.println("Successfully blurred image.");
    modelDB.add("blurredImage", blurredImage);
    assertEquals(blurredImage, modelDB.get("blurredImage"));
  }

  //Test BlurTransformation on LARGE image.
  @Test
  public void testSharpenTransformationMedium() {
    ImageDataBase modelDB = new ImageDataBase();
    //load image
    IImageLoader loader = new PPMImageLoader("test/july-med.ppm");
    IImageState imageJuly = loader.run();
    SharpenTransformation sharpen = new SharpenTransformation();
    IImageState sharpenedImage = sharpen.run(imageJuly);
    System.out.println("Successfully sharpened image.");
    modelDB.add("sharpenedImage", sharpenedImage);
    assertEquals(sharpenedImage, modelDB.get("sharpenedImage"));
  }

  @Test
  public void testSharpenTransformationKoala() {
    ImageDataBase modelDB = new ImageDataBase();
    //load image
    IImageLoader loader = new PPMImageLoader("test/Koala.ppm");
    IImageState imageKoala = loader.run();
    SharpenTransformation sharpen = new SharpenTransformation();
    IImageState sharpenedImage = sharpen.run(imageKoala);
    System.out.println("Successfully sharpened image.");
    modelDB.add("sharpenedImage", sharpenedImage);
    assertEquals(sharpenedImage, modelDB.get("sharpenedImage"));
  }

  @Test
  public void testSharpenTransformationLarge() {
    ImageDataBase modelDB = new ImageDataBase();
    //load image
    IImageLoader loader = new PPMImageLoader("test/July4th.ppm");
    IImageState imageJuly = loader.run();
    SharpenTransformation sharpen = new SharpenTransformation();
    IImageState sharpenImage = sharpen.run(imageJuly);
    System.out.println("Successfully sharpened image.");
    modelDB.add("blurredImage", sharpenImage);
    assertEquals(sharpenImage, modelDB.get("blurredImage"));
  }

  @Test
  public void testBlurJPGImageLarge() {
    IImageDataBase modelDB = new ImageDataBase();
    IImageLoader loader = new JPGImageLoader("res/July4th.jpg");
    IImageState imageJuly = loader.run();
    BlurTransformation blur = new BlurTransformation();
    IImageState blurredImage = blur.run(imageJuly);
    System.out.println("Successfully blurred image.");
    modelDB.add("blurredImageJPG", blurredImage);
    assertEquals(blurredImage, modelDB.get("blurredImageJPG"));
  }


}



