package controller.io;

import model.IImageState;

/**
 * Represents the IImageLoader interface. Right now, the interface is implemented by the
 * PPMImageLoader class, but the interface is open to being implemented by other image type
 * Loaders in future assigments.
 */
public interface IImageLoader {
  //the loader interface will give back an image

  IImageState run();
}
