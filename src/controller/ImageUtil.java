package controller;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.FileInputStream;

import javax.imageio.ImageIO;

import model.IImage;
import model.IImageState;
import model.ImageImpl;


/**
 * This class contains utility methods to read a PPM image from file and simply print its contents.
 * Feel free to change this method as required.
 */
public class ImageUtil {

  /**
   * Read an image file in the PPM format and print the colors.
   * @param filename the path of the file.
   */
  public static IImageState readPPM(String filename) {
    Scanner sc;

    try {
      sc = new Scanner(new FileInputStream(filename));
    } catch (FileNotFoundException e) {
      System.out.println("File " + filename + " not found!");
      return null;
    }
    StringBuilder builder = new StringBuilder();
    //read the file line by line, and populate a string. This will throw away any comment lines
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0) != '#') {
        builder.append(s + System.lineSeparator());
      }
    }

    //now set up the scanner to read from the string we just built
    sc = new Scanner(builder.toString());

    String token;

    token = sc.next();
    if (!token.equals("P3")) {
      System.out.println("Invalid PPM file: plain RAW file should begin with P3");
    }
    int width = sc.nextInt();
    System.out.println("Width of image: " + width);
    int height = sc.nextInt();
    System.out.println("Height of image: " + height);
    int maxValue = sc.nextInt();
    System.out.println("Maximum value of a color in this file (usually 255): " + maxValue);

    IImage loadedImage = new ImageImpl(width, height);

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int r = sc.nextInt();
        int g = sc.nextInt();
        int b = sc.nextInt();
        loadedImage.setPixel(j, i, r, g, b);
        //System.out.println("Color of pixel ("+j+","+i+"): "+ r+","+g+","+b);
      }
    }
    return loadedImage;
  }

  /**
   * Read an image file in the JPG format and print the colors.
   * @param filename the path of the file.
   */
  public static IImageState readJPG(String filename) {
    try {
      BufferedImage bufferedImage = ImageIO.read(new File(filename));
      return convertToIImageState(bufferedImage);
    } catch (IOException e) {
      System.out.println("Failed to read JPG image: " + e.getMessage());
      return null;
    }
  }

  /**
   * Read an image file in the PNG format and print the colors.
   * @param filename the path of the file.
   */
  public static IImageState readPNG(String filename) {
    try {
      BufferedImage bufferedImage = ImageIO.read(new File(filename));
      return convertToIImageState(bufferedImage);
    } catch (IOException e) {
      System.out.println("Failed to read PNG image: " + e.getMessage());
      return null;
    }
  }

  /**
   * Read an image file in the BMP format and print the colors.
   * @param filename the path of the file.
   */
  public static IImageState readBMP(String filename) {
    try {
      BufferedImage bufferedImage = ImageIO.read(new File(filename));
      return convertToIImageState(bufferedImage);
    } catch (IOException e) {
      System.out.println("Failed to read BMP image: " + e.getMessage());
      return null;
    }
  }

  /**
   * Converts a BufferedImage into an IImageState object for compatibility with this program.
   * A BufferedImage stores the red, green and blue values as one value, rgb, but IImageState
   * stores the red, green and blue values separately, so this helper method is needed to extract
   * the individual red/green/blue values from the BufferedImage.
   *
   * @param bufferedImage image to extract red/green/blue values.
   * @return image in the IImageState format for compatibility with the program.
   */
  public static IImageState convertToIImageState(BufferedImage bufferedImage) {
    if (bufferedImage == null) {
      return null;
    }

    int width = bufferedImage.getWidth();
    int height = bufferedImage.getHeight();
    IImage loadedImage = new ImageImpl(width, height);

    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        int rgb = bufferedImage.getRGB(x, y); //get first inner loop, then outter loop. width 1st
        Color color = new Color(rgb);
        //need to isolate the red, green and blue values
        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();

        //set the pixel values
        loadedImage.setPixel(x, y, r, g, b); //set width first, then height
      }
    }
    return loadedImage;
  }

  /**
   * Converts an IImageState image to a BufferedImage.
   * @param image IImageState to be converted to a BufferedImage type.
   * @return bufferedImage a BufferedImage.
   */
  public static BufferedImage convertToBufferedImage(IImageState image) {
    int width = image.getWidth();
    int height = image.getHeight();
    BufferedImage buffImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        int r = image.getRedChannel(x, y);
        int g = image.getGreenChannel(x, y);
        int b = image.getBlueChannel(x, y);

        int rgb = (r << 16) | (g << 8) | b; //combine r g and b into RGB
        buffImage.setRGB(x, y, rgb);
      }
    }
    return buffImage;
  }

  /**
   * Demo main driver for ImageUtil program.
   *
   * @param args command line arguments.
   */
  public static void main(String[] args) {
    String filename;

    if (args.length > 0) {
      filename = args[0];
    } else {
      filename = "res/July4th.jpg";
    }
    ImageUtil.readPNG(filename);
  }
}