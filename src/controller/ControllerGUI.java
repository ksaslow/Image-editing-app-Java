package controller;

import java.awt.image.BufferedImage;
import java.util.Objects;

import model.IImageDataBase;
import model.IImageState;
import model.transformations.BlurTransformation;
import model.transformations.BrightenTransformation;
import model.transformations.GreyscaleChannelTransformation;
import model.transformations.GreyscaleIntensityTransformation;
import model.transformations.GreyscaleLumaTransformation;
import model.transformations.GreyscaleValueTransformation;
import model.transformations.SharpenTransformation;
import view.IView;
import view.ViewListener;

/**
 * Represents the GUI-specific controller in this application, the ControllerGUI Class. The
 * ControllerGUI implements the IControllerGUI interface, as well as the ViewListener interface.
 * This allows the controller to listen to the view and be notified when an event happens in the view
 * that requires a response or reaction from the controller.
 */
public class ControllerGUI implements IControllerGUI, ViewListener {
  private final IImageDataBase model;
  private final IView view;

  /**
   * Constructor for a ControllerGUI object. ControllerGUI requires the IImageDataBase model, and
   * the GUI-specific IView object.
   * @param model database of images manipulated by the controller.
   * @param view GUI interface to allow user to see the images being transformed.
   */
  public ControllerGUI(IImageDataBase model, IView view) {
    this.model = Objects.requireNonNull(model);
    this.view = Objects.requireNonNull(view);
    this.view.addViewListener(this); //subscribe to the view's events
  }


  /**
   * Gives control to the controller and runs the program.
   */
  @Override
  public void run() {
    view.setVisible(true);
  }

  /**
   * Represents the Controller's response to a Load event in the view. Once the image is selected in
   * the File Dialog in the view, this file path is communicated to the controller so that it knows
   * which image to manipulate and transform.
   * @param filepath current file loaded into the view to be transformed by the program.
   */
  @Override
  public void handleLoadEvent(String filepath) {

    // give the view a method that returns the BufferedImage of the currently loaded image
    // then modify the handleSaveEvent() to retrieve the BufferedImage from the view,
    // create an IImageState object from it, and then add it to the IImageDataBase model

    System.out.println(filepath);
    view.displayMessage("Image loaded successfully!");
    BufferedImage loadedImage = view.getCurrentImage();
    String filename = view.getCurrentFilename();

    if (loadedImage == null) {
      view.displayMessage("Loaded image is null");
      return;
    }

    IImageState imageState = ImageUtil.convertToIImageState(loadedImage);
    model.add(filename, imageState);
  }


/**
 * Represents the Controller's response to a Blur event in the view. When a blur event is emitted by
 * the view, it triggers the controller's response to a blur event, which is to call a new
 * BlurTransformation object to blur the image.
 */
  @Override
  public void handleBlurEvent() {
    BlurTransformation blurTransformation = new BlurTransformation();
    String destID = "blurredImage";

    BufferedImage loadedImage = view.getCurrentImage();
    IImageState imageState = ImageUtil.convertToIImageState(loadedImage);
    IImageState blurredImage = blurTransformation.run(imageState);
    model.add(destID, blurredImage);

    BufferedImage blurredImageReload = ImageUtil.convertToBufferedImage(blurredImage);
    view.setImageToCanvas(blurredImageReload);
    view.displayMessage("Image blurred.");
  }

  /**
   * Represents the Controller's response to a Sharpen event in the view. When a sharpen event is emitted by
   * the view, it triggers the controller's response to a sharpen event, which is to call a new
   * SharpenTransformation object to sharpen the image.
   */
  @Override
  public void handleSharpenEvent() {
    SharpenTransformation sharpenTransformation = new SharpenTransformation();
    String destID = "sharpenedImage";

    BufferedImage loadedImage = view.getCurrentImage();
    IImageState imageState = ImageUtil.convertToIImageState(loadedImage);
    IImageState sharpenedImage = sharpenTransformation.run(imageState);
    model.add(destID, sharpenedImage);

    BufferedImage sharpenedImageReload = ImageUtil.convertToBufferedImage(sharpenedImage);
    view.setImageToCanvas(sharpenedImageReload);
    view.displayMessage("Image sharpened.");
  }

  /**
   * Represents the Controller's response to a Value Component event in the view. When a value
   * component event is emitted by the view, it triggers the controller's response to a value
   * component event, which is to call a new GreyscaleValueTransformation object to
   * transform the image into a greyscale image based on the value component.
   */
  @Override
  public void handleValueEvent() {
    GreyscaleValueTransformation valueTransformation = new GreyscaleValueTransformation();
    String destID = "GreyscaleValueComponentImage";

    BufferedImage loadedImage = view.getCurrentImage();
    IImageState imageState = ImageUtil.convertToIImageState(loadedImage);
    IImageState greyscaleImageValue = valueTransformation.run(imageState);
    model.add(destID, greyscaleImageValue);

    BufferedImage greyscaleValueImageReload = ImageUtil.convertToBufferedImage(greyscaleImageValue);
    view.setImageToCanvas(greyscaleValueImageReload);
    view.displayMessage("Value component shown.");
  }

  /**
   * Represents the Controller's response to a Intensity Component event in the view. When an intensity
   * component event is emitted by the view, it triggers the controller's response to an intensity
   * component event, which is to call a new GreyscaleIntensityTransformation object to
   * transform the image into a greyscale image based on the intensity component.
   */
  @Override
  public void handleIntensityEvent() {
    GreyscaleIntensityTransformation intensityTransform = new GreyscaleIntensityTransformation();
    String destID = "GreyscaleIntensityComponentImage";

    BufferedImage loadedImage = view.getCurrentImage();
    IImageState imageState = ImageUtil.convertToIImageState(loadedImage);
    IImageState greyscaleImageIntensity = intensityTransform.run(imageState);
    model.add(destID, greyscaleImageIntensity);

    BufferedImage greyscaleIntensityImageReload =
            ImageUtil.convertToBufferedImage(greyscaleImageIntensity);
    view.setImageToCanvas(greyscaleIntensityImageReload);
    view.displayMessage("Intensity component shown.");
  }

  /**
   * Represents the Controller's response to a Luma Component event in the view. When a luma
   * component event is emitted by the view, it triggers the controller's response to a luma
   * component event, which is to call a new GreyscaleLumaTransformation object to
   * transform the image into a greyscale image based on the luma component.
   */
  @Override
  public void handleLumaEvent() {
    GreyscaleLumaTransformation lumaTransformation = new GreyscaleLumaTransformation();
    String destID = "LumaIntensityComponentImage";

    BufferedImage loadedImage = view.getCurrentImage();
    IImageState imageState = ImageUtil.convertToIImageState(loadedImage);
    IImageState greyscaleImageLuma = lumaTransformation.run(imageState);
    model.add(destID, greyscaleImageLuma);

    BufferedImage greyscaleLumaImageReload = ImageUtil.convertToBufferedImage(greyscaleImageLuma);
    view.setImageToCanvas(greyscaleLumaImageReload);
    view.displayMessage("Luma value shown.");
  }


  /**
   * Represents the Controller's response to a Red Channel event in the view. When a red channel
   * event is emitted by the view, it triggers the controller's response to a red channel
   * event, which is to call a new GreyscaleChannelTransformation(red) object to
   * transform the image into a greyscale image based on the red channel.
   */
  @Override
  public void handleRedChannelEvent() {
    GreyscaleChannelTransformation channelTransformation = new GreyscaleChannelTransformation("red");
    String destID = "RedChannelImage";

    BufferedImage loadedImage = view.getCurrentImage();
    IImageState imageState = ImageUtil.convertToIImageState(loadedImage);
    IImageState greyscaleRed = channelTransformation.run(imageState);
    model.add(destID, greyscaleRed);

    BufferedImage greyscaleRedReload = ImageUtil.convertToBufferedImage(greyscaleRed);
    view.setImageToCanvas(greyscaleRedReload);
    view.displayMessage("Red channel shown.");
  }


  /**
   * Represents the Controller's response to a Green Channel event in the view. When a green channel
   * event is emitted by the view, it triggers the controller's response to a green channel
   * event, which is to call a new GreyscaleChannelTransformation(green) object to
   * transform the image into a greyscale image based on the green channel.
   */
  @Override
  public void handleGreenChannelEvent() {
    GreyscaleChannelTransformation channelTransformation = new GreyscaleChannelTransformation("green");
    String destID = "GreenChannelImage";

    BufferedImage loadedImage = view.getCurrentImage();
    IImageState imageState = ImageUtil.convertToIImageState(loadedImage);
    IImageState greyscaleGreen = channelTransformation.run(imageState);
    model.add(destID, greyscaleGreen);

    BufferedImage greyscaleGreenReload = ImageUtil.convertToBufferedImage(greyscaleGreen);
    view.setImageToCanvas(greyscaleGreenReload);
    view.displayMessage("Green value shown.");
  }


  /**
   * Represents the Controller's response to a Blue Channel event in the view. When a blue channel
   * event is emitted by the view, it triggers the controller's response to a blue channel
   * event, which is to call a new GreyscaleChannelTransformation(blue) object to
   * transform the image into a greyscale image based on the blue channel.
   */
  @Override
  public void handleBlueChannelEvent() {
    GreyscaleChannelTransformation channelTransformation = new GreyscaleChannelTransformation("blue");
    String destID = "BlueChannelImage";

    BufferedImage loadedImage = view.getCurrentImage();
    IImageState imageState = ImageUtil.convertToIImageState(loadedImage);
    IImageState greyscaleBlue = channelTransformation.run(imageState);
    model.add(destID, greyscaleBlue);

    BufferedImage greyscaleBlueReload = ImageUtil.convertToBufferedImage(greyscaleBlue);
    view.setImageToCanvas(greyscaleBlueReload);
    view.displayMessage("Blue channel shown.");
  }


  /**
   * Represents the Controller's response to a Brighten event in the view. When a brighten
   * event is emitted by the view, it triggers the controller's response to a brighten
   * event, which is to call a new BrightenTransformation object to brighten the image. The
   * controller also listens to information from view that captures user input to determine the
   * increment by which the image should be brightened or darkened.
   */
  @Override
  public void handleBrightenEvent() {

    try{
      int increment = Integer.parseInt(view.getTextFromView());
      //set default value 50 to make sure the button works
      BrightenTransformation brightenTransformation = new BrightenTransformation(increment);
      String destID = "BrightenedImage";

      BufferedImage loadedImage = view.getCurrentImage();
      IImageState imageState = ImageUtil.convertToIImageState(loadedImage);
      IImageState brightenedImage = brightenTransformation.run(imageState);
      model.add(destID, brightenedImage);

      BufferedImage brightenedReload = ImageUtil.convertToBufferedImage(brightenedImage);
      view.setImageToCanvas(brightenedReload);
      if (increment < 0) {
        view.displayMessage("Image darkened.");
      } else {
        view.displayMessage("Image brightened.");
      }

    } catch (NumberFormatException e) {
      view.displayMessage("Please enter a number.");
      System.out.println("Invalid integer value in the text field!\n" +
              "Please enter a number!");
    }
  }

  /**
   * Represents the Controller's response to a Save event in the view. When a Save event is emitted
   * by the view, the FileDialog opens back up and prompts the user to save the image under a
   * desired filepath and extension type. The view then communicates this information to the
   * controller so that the controller is able to save the image accordingly.
   */
  @Override
  public void handleSaveEvent() {
    // essentially, when the save button in the view is clicked, the view emits a
    // save event, and since the ControllerGUI is listening, the handleSaveEvent() method
    // is invoked. So here, the current BufferedImage from the view's canvas should be converted
    // to an IImageState object and saved into the model database.
    // NOTE: use conversion method in ImageUtil

    BufferedImage currentCanvasImage = view.getCurrentImage();
    //convert to IImageState
    IImageState imageState = ImageUtil.convertToIImageState(currentCanvasImage);

    String fileName = view.getCurrentFilename();
  }

}
