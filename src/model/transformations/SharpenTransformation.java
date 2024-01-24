package model.transformations;

import model.IImageState;
import model.Kernel;

/**
 * Represents a transformation on all images where the image is being sharpened. An image can be
 * sharpened by applying a 5x5 Kernel of calculations to each Pixel in the image.
 */
public class SharpenTransformation implements ITransformation {
  private int row; // row position of target pixel
  private int col; // col position of target pixel

  /**
   * Constructor of a SharpenTransformation object. Keep parameters empty like
   */
  public SharpenTransformation() {

    //set the matrix values for the SharpenTransformation Kernel
    //Sharpen Kernel is a 5x5 matrix with set values:
    double[][] kernelValues = {
            {(-0.125), (-0.125), (-0.125), (-0.125), (-0.125)},
            {(-0.125), (0.25), (0.25), (0.25), (-0.125)},
            {(-0.125), (0.25), (1), (0.25), (-0.125)},
            {(-0.125), (0.25), (0.25), (0.25), (-0.125)},
            {(-0.125), (-0.125), (-0.125), (-0.125), (-0.125)}
    };

    //initialize the Kernel used in a SharpenTransformation, 5x5 with above values
    //filter transformations require a kernel for calculations
    Kernel kernel = new Kernel(5, kernelValues);
  }

  @Override
  public IImageState run(IImageState sourceImage) {

    double[][] kernelValues = {
            {(-0.125), (-0.125), (-0.125), (-0.125), (-0.125)},
            {(-0.125), (0.25), (0.25), (0.25), (-0.125)},
            {(-0.125), (0.25), (1), (0.25), (-0.125)},
            {(-0.125), (0.25), (0.25), (0.25), (-0.125)},
            {(-0.125), (-0.125), (-0.125), (-0.125), (-0.125)}
    };

    Kernel newKernel = new Kernel(5, kernelValues);
    IImageState processedImage = newKernel.applyKernel(sourceImage);

    return processedImage;
  }

}
