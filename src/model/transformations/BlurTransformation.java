package model.transformations;


import model.IImageState;
import model.Kernel;


/**
 * Represents a transformation on all images where the image is blurred. An image can be blurred
 * by applying the Gaussian blur 3x3 matrix of calculations to a specified Pixel, and its
 * surrounding pixels.
 */
public class BlurTransformation implements ITransformation {
  private int row; // row position of target Pixel
  private int col; // col position of target Pixel


  /**
   * Constructor of a BlurTransformation object. Keep parameters empty
   * like other transformations.
   */
  public BlurTransformation() {

    // set the matrix values for the BlurTransformation Kernel
    //Blur Kernel is a 3x3 matrix with set values
    double[][] kernelValues = {
            {(0.0625), (0.125), (0.0625)},
            {(0.125), (0.25), (0.125)},
            {(0.0625), (0.125), (0.0625)}
    };

    // initialize the Kernel used in a BlurTransformation, 3x3 with above values
    //filter transformation require a kernel for calculations!
    Kernel kernel = new Kernel(3, kernelValues);
  }


  @Override
  public IImageState run(IImageState sourceImage) {

    double[][] kernelValues = {
            {(0.0625), (0.125), (0.0625)},
            {(0.125), (0.25), (0.125)},
            {(0.0625), (0.125), (0.0625)}
    };

    Kernel newKernel = new Kernel(3, kernelValues);
    IImageState processedImage = newKernel.applyKernel(sourceImage);

    return processedImage;
  }
}
