package controller.io;

/**
 * Represents the IImageSaver interface. Right now, the interface is implemented by the
 * PPMImageSaver class, but the interface is open to being implemented by other image type
 * Savers in future assignments.
 */
public interface IImageSaver {
  //make interface because in the first assignment, we are only saving PPM files.
  // but this allows us to save in different formats

  void run();
}
