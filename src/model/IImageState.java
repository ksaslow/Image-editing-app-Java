package model;

/**
 * Interface IImageState is a READ-ONLY interface. It doesnt have the mutability functionality
 * that IImage has (e.g. setter methods). This helps with information hiding.
 */
public interface IImageState {

  int getHeight();

  int getWidth();

  int getRedChannel(int x, int y);

  int getGreenChannel(int x, int y);

  int getBlueChannel(int x, int y);




}
