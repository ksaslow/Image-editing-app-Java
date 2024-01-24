package model.transformations;

import model.IImageState;

/**
 * This represents the ITransformation interface. This is the contract for all of the transformation
 * strategy patterns in the program.
 */
public interface ITransformation {
  IImageState run(IImageState sourceImage);
}
