package model;

/**
 * Represents an IImage object. And IImage object is the image object that can be manipuated
 * by the program with the setPixel method.
 */
public interface IImage extends IImageState {


  void setPixel(int x, int y, int r, int g, int b);
}
