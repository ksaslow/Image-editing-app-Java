package model;

/**
 * Represents a Kernel for image filtering and transformations. A kernel is a 2D matrix
 * that can be applied to the pixels of an image to transform it in different ways.
 * The values within the pixel determine the effect the transformation has.
 */
public class Kernel {
  private final int size; //size of kernel, 3x3, 5x5, etc.
  private double[][] kernelValues = new double[0][]; //2D array to store the kernel's values
  private int x; // row of target pixel
  private int y; // col of target pixel

  /**
   * Kernel object Constructor.
   * @param size size of the square matrix.
   * @param kernelValues values to be held within the matrix for transformations.
   * @throws IllegalArgumentException if the dimension of the kernel is not odd/square.
   */
  public Kernel(int size, double[][] kernelValues) throws IllegalArgumentException {
    if (size < 0 || size % 2 != 1) {
      throw new IllegalArgumentException("Kernel must be a square with odd dimensions!");
    }

    if (kernelValues.length != size && kernelValues[0].length != size) {
      throw new IllegalArgumentException("Kernel must be a square!");
    }

    this.size = size;
    this.kernelValues = kernelValues;
    this.x = x;
    this.y = y;
  }

  //Should these be PRIVATE or is it okay that they're public?!
  //Getters for size and values
  private int getSize() {
    return size;
  }

  public double[][] getValues() {
    return kernelValues;
  }

  private int clamp(int value) {
    if (value < 0) {
      return 0;
    }
    if (value > 255) {
      return 255;
    }
    return value;
  }

  /**
   * Method to apply the kernel matrix to an image in order to transform it.
   * @param inputImage IImageState object to be transformed by kernel.
   * @return new IImageState object that is processed accordingly.
   */
  public IImageState applyKernel(IImageState inputImage) {
    int kernelSize = this.size;
    int radius = kernelSize / 2;
    int width = inputImage.getWidth();
    int height = inputImage.getHeight();

    // Pre-calculate kernel values outside the pixel loop
    double[][] kernelVals = new double[kernelSize][kernelSize];
    for (int ky = 0; ky < kernelSize; ky++) {
      for (int kx = 0; kx < kernelSize; kx++) {
        kernelVals[ky][kx] = kernelValues[ky][kx];
      }
    }

    ImageImpl processedImage = new ImageImpl(width, height);

    // Define the valid range for clamping
    int minClamp = 0;
    int maxClamp = 255;

    // Loop over each pixel in the input image

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        double sumRed = 0;
        double sumGreen = 0;
        double sumBlue = 0;

        // Apply the Kernel to the target pixel and its neighbors
        for (int ky = 0; ky < kernelSize; ky++) {
          for (int kx = 0; kx < kernelSize; kx++) {
            int pixelY = i + ky - radius;
            int pixelX = j + kx - radius;

            if (pixelX >= 0 && pixelX < width && pixelY >= 0 && pixelY < height) {
              int r = inputImage.getRedChannel(pixelX, pixelY);
              int g = inputImage.getGreenChannel(pixelX, pixelY);
              int b = inputImage.getBlueChannel(pixelX, pixelY);

              double kernelVal = kernelVals[ky][kx];
              sumRed += kernelVal * r;
              sumGreen += kernelVal * g;
              sumBlue += kernelVal * b;
            }
          }
        }

        // Combine color components and set new pixel in the processedImage
        int newR = (int) Math.max(minClamp, Math.min(maxClamp, sumRed));
        int newG = (int) Math.max(minClamp, Math.min(maxClamp, sumGreen));
        int newB = (int) Math.max(minClamp, Math.min(maxClamp, sumBlue));
        processedImage.setPixel(j, i, newR, newG, newB);
      }
    }
    return processedImage;
  }

}
