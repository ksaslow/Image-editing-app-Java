package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Objects;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import controller.ImageUtil;
import controller.io.PPMImageSaver;
import model.IImageDataBase;
import model.IImageState;
import static javax.swing.SwingConstants.HORIZONTAL;

/**
 * Represents the View Class of this MVC model, specifically the GUI-specific view, as opposed to
 * the text-based view implemented in the last two assignments. This View Class implements the
 * functionality of the IView interface, as well as the ActionListener and the KeyListener
 * interfaces, in order to make the most of the Java Swing JButton functionality, and allow each
 * transformation in the model to have a corresponding button for the user to select in order to
 * transform the image in the desired way.
 */
public class View extends JFrame implements ActionListener, IView {

  private final JTextField enterText;
  private final JLabel showText; //use this text field to display error messages to the screen
  private final IImageDataBase model;
  private final Canvas canvas;
  private BufferedImage currentImage; //communicate between view/canvas and Controller
  private String currentFilename; //communicate between view/canvas and Controller
  private String currentFilePath;
  private final List<ViewListener> listenersToNotify;

  /**
   * Constructor of a View object. Requires an IImageDataBase model so that the images being
   * transformed can be loaded into and saved in the database of images. A View object also has a
   * list of subscribers, or listeners, who listen to each event that happens in the View. If a
   * certain button is clicked, an Event is triggered, and all Listeners will be notified that that
   * event happened, and they will be able to properly handle that event.
   * @param model database of images in the program.
   */
  public View(IImageDataBase model) {
    this.listenersToNotify = new ArrayList<>();
    setSize(1000, 600);
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    this.model = Objects.requireNonNull(model);
    setLayout(new BorderLayout());
    this.canvas = new Canvas();
    this.showText = new JLabel("Image Editor");


    // wrap the canvas with a JScrollPane
    JScrollPane scrollPane = new JScrollPane(this.canvas);
    scrollPane.setPreferredSize(new Dimension(1000, 600));
    scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

    // add all buttons:
    JButton saveDataButton = new JButton("Save Image");
    saveDataButton.setBackground(Color.WHITE);
    saveDataButton.setFont(new Font("Arial", Font.BOLD, 15));
    saveDataButton.setForeground(Color.BLACK);
    JButton loadDataButton = new JButton("Load Image");
    loadDataButton.setBackground(Color.WHITE);
    loadDataButton.setFont(new Font("Arial", Font.BOLD, 15));
    loadDataButton.setForeground(Color.BLACK);

    // customize toolbar
    JToolBar toolbar = new JToolBar(HORIZONTAL);
    toolbar.setLayout(new BoxLayout(toolbar, BoxLayout.Y_AXIS));
    Dimension toolbarDimension = new Dimension(300, 600);
    toolbar.setPreferredSize(toolbarDimension);

    JButton blurButton = new JButton("Blur");
    JButton sharpenButton = new JButton("Sharpen");
    JButton valueButton = new JButton("Value");
    JButton intensityButton = new JButton("Intensity");
    JButton lumaButton = new JButton("Luma");
    JButton redChannelButton = new JButton("Red Channel");
    JButton greenChannelButton = new JButton("Green Channel");
    JButton blueChannelButton = new JButton("Blue Channel");
    JButton brightenButton = new JButton("Brighten");
    this.enterText = new JTextField(10); // text field for user to type brighten increment

    //create panel for IO commands
    JPanel filePanel = new JPanel();
    filePanel.setLayout(new GridLayout(0,1));
    filePanel.add(loadDataButton);
    filePanel.add(saveDataButton);

    TitledBorder fileBorder = BorderFactory.createTitledBorder("Input/Output");
    filePanel.setBorder(fileBorder);
    filePanel.setBackground(new Color(232, 234, 246));

    //create panel for Greyscale Commands:
    JPanel greyscalePanel = new JPanel();
    greyscalePanel.setLayout(new GridLayout(6,0));
    greyscalePanel.add(valueButton);
    greyscalePanel.add(intensityButton);
    greyscalePanel.add(lumaButton);
    greyscalePanel.add(redChannelButton);
    greyscalePanel.add(greenChannelButton);
    greyscalePanel.add(blueChannelButton);

    TitledBorder greyscaleBorder = BorderFactory.createTitledBorder("Greyscale Transformations");
    greyscalePanel.setBorder(greyscaleBorder);
    greyscalePanel.setBackground(new Color(232, 234, 246));

    greyscalePanel.setSize(new Dimension(300, 300));

    //create panel for Color image transformations:
    JPanel filterPanel = new JPanel();
    filterPanel.setLayout(new GridLayout(0,1));
    filterPanel.setBackground(new Color(232, 234, 246));

    //create nested panel for brighten transformation: brighten button and text field
    JPanel brightenPanel = new JPanel();
    brightenPanel.setLayout(new GridLayout(0, 1));
    JLabel brightenLabel = new JLabel("Brighten by:");
    brightenPanel.add(blurButton);
    brightenPanel.add(sharpenButton);
    brightenPanel.add(brightenButton);
    brightenPanel.add(brightenLabel);
    brightenPanel.add(enterText);

    filterPanel.add(brightenPanel);

    TitledBorder filterBorder = BorderFactory.createTitledBorder("Filter Transformations");
    filterPanel.setBorder(filterBorder);

    // add nestedPanels to toolbar Panel

    toolbar.add(showText);
    toolbar.add(filePanel);
    toolbar.add(greyscalePanel);
    toolbar.add(filterPanel);

    // add all components to main Frame
    add(scrollPane, BorderLayout.CENTER);
    //add(showText, BorderLayout.NORTH);
    add(toolbar, BorderLayout.WEST);

    // set all action commands:
    saveDataButton.setActionCommand("save");
    loadDataButton.setActionCommand("load");
    blurButton.setActionCommand("blur");
    sharpenButton.setActionCommand("sharpen");
    valueButton.setActionCommand("value");
    intensityButton.setActionCommand("intensity");
    lumaButton.setActionCommand("luma");
    redChannelButton.setActionCommand("red");
    greenChannelButton.setActionCommand("green");
    blueChannelButton.setActionCommand("blue");
    brightenButton.setActionCommand("brighten");

    // add all action listeners:
    blurButton.addActionListener(this);
    sharpenButton.addActionListener(this);
    saveDataButton.addActionListener(this);
    loadDataButton.addActionListener(this);
    valueButton.addActionListener(this);
    intensityButton.addActionListener(this);
    lumaButton.addActionListener(this);
    redChannelButton.addActionListener(this);
    greenChannelButton.addActionListener(this);
    blueChannelButton.addActionListener(this);
    brightenButton.addActionListener(this);
    this.setFocusable(true);
  }


  /**
   * Helper method to render the image to the Canvas object and project it within the GUI.
   * @param image to be rendered to the GUI.
   */
  public void setImageToCanvas(BufferedImage image) {
    this.canvas.setImage(image);
    this.canvas.setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
    // store the currently displayed BufferedImage
    currentImage = image;
  }


  /**
   * Method for testing that allows the Imgae to be loaded directly via a String filepath
   * instead of a FileDialog. This will allow the MockView to be used to test the wiring
   * between the View and Controller.
   */
  public void loadImageFromFilepath(String filepath) {
    File imageFile = new File(filepath);
    try {
      BufferedImage image = ImageIO.read(imageFile);
      setImageToCanvas(image);
      currentFilename = imageFile.getName();
      currentFilePath = filepath;
      currentImage = image;
    } catch (IOException e) {
      System.out.println("Error loading the image.");
    }
  }


  /**
   * Helper function for emitLoadEvent(), which opens up a FileDialog for the user to select an
   * image to load into the program.
   */
  private void openImageFileDialog() {
    FileDialog fileDialog = new FileDialog(this, "Open Image", FileDialog.LOAD);
    fileDialog.setFile("*.jpg;*.png;*.ppm"); //Filter the allowed image formats
    fileDialog.setVisible(true);

    String directory = fileDialog.getDirectory();
    String filename = fileDialog.getFile();

    if (filename != null) {
      if (filename != null) {
        String imagePath = directory + filename;
        loadImageFromFilepath(imagePath);
      }
      String imagePath = directory + filename;
      File imageFile = new File(imagePath);
      currentFilename = filename;
      currentFilePath = imagePath;

      if (this.getCurrentFilename().toLowerCase().endsWith(".ppm")) {
        IImageState imageState = ImageUtil.readPPM(this.getCurrentFilePath());
        BufferedImage bufferedImage = ImageUtil.convertToBufferedImage(imageState);
        setImageToCanvas(bufferedImage);
        // pass the filename to the listeners


        // set the currentImage field after the image has been loaded
        currentImage = bufferedImage;
      } else {
        try {
          BufferedImage image = ImageIO.read(imageFile);
          setImageToCanvas(image);
          //canvas.requestFocus();

          // pass the filename to the listeners
          currentFilename = imagePath;

          // set the currentImage field after the image has been loaded
          currentImage = image;

        } catch (IOException e) {
          System.out.println("Error loading the image.");
        }
      }
    }
  }


  /**
   * Helper function for emitSaveEvent(), which opens up a FileDialog for the user to save the image
   * that was transformed by the program. The user types the desired filepath directly into the
   * Dialog window and selects the image extension (limited to .ppm, .jpg, and .png) to save the
   * image under.
   */
  private void saveImageFileDialog() {
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setDialogTitle("Save Image");

    //set file filters to restrict allowed image formats for saving
    fileChooser.setFileFilter(
            new FileNameExtensionFilter("JPEG Image (*.jpg)", "jpg"));
    fileChooser.setFileFilter(
            new FileNameExtensionFilter("PNG Image (*.png)", "png"));
    fileChooser.setFileFilter(
            new FileNameExtensionFilter("PPM Image (*.ppm)", "ppm"));

    int userChoice = fileChooser.showSaveDialog(this);

    if (userChoice == JFileChooser.APPROVE_OPTION) {
      File selectedFile = fileChooser.getSelectedFile();
      String selectedPath = selectedFile.getAbsolutePath();

      try {
        //save the current image to selected file path
        if (selectedPath.toLowerCase().endsWith(".ppm")) {
          IImageState saveImage = ImageUtil.convertToIImageState(currentImage);

          PPMImageSaver ppmImageSaver = new PPMImageSaver(selectedPath, saveImage, new FileWriter(selectedFile));
          ppmImageSaver.run();
        } else {
          ImageIO.write(currentImage, "png", selectedFile); // TO CHANGE THIS TO JPG OR PNG WITH IF LOGIC!!
        }
      } catch (IOException e) {
        System.out.println("Error saving the image,");
      }
    }
  }

  /**
   * Helper method to communicate the current image in the GUI between the view and the controller.
   * This captures the image state that is currently in the GUI and allows it to be communicated to
   * the controller.
   * @return current image being manipulated in the GUI.
   */
  public BufferedImage getCurrentImage() {
    // image stored by canvas!
    return currentImage;
  }

  /**
   * Helper method to communicate the current filename of the image in the GUI between the view and
   * the controller. This captures the filename of the image that is initially loaded into the GUI
   * and allows it to be communicated to the controller.
   * @return filename of the image initially loaded into the program.
   */
  public String getCurrentFilename() {
    return currentFilename;
  }

  /**
   * Helper method to communicate the current file path of the image in the GUI between the view and
   * the controller. This captures the file path of the image that is initially loaded into the GUI
   * and allows it to be communicated to the controller.
   * @return file path of the image initially loaded into the program.
   */
  public String getCurrentFilePath() {
    return currentFilePath;
  }


  /**
   * This captures the text entered into a textfield in the view and allows it to be heard by the
   * controller. This is especially useful for the controller for operations like the
   * BrightenTransformation, where it requires an increment by which the image should be brightened
   * or darkened. The user enters this increment into the text field in the GUI, and the Controller
   * is able to ensure that the parameters required by the model are met. This means that if the
   * user types in text but no number, an error message is displayed to the view so that the user
   * knows to enter an integer value.
   * @return text entered into the GUI's text field.
   */
  public String getTextFromView() {
    return this.enterText.getText();
  }

  /**
   * This displays a message to the GUI to let the user know how to best interact with the program.
   * The view displays a message when things go right, to let the user know what was done, as well
   * as when things go wrong and the user must behave differently in order to interact with the
   * program.
   */
  public void displayMessage(String text) {
    this.showText.setText(text);
  }

  /**
   * Creates a list of subscribers, or listeners, to the View. This allows the ControllerGUI to
   * listen to all events that happen in the view in order to respond and engage accordingly.
   * @param listener to be added to subscribers list.
   */
  public void addViewListener(ViewListener listener) {
    this.listenersToNotify.add(listener);
  }

  /**
   * When the user selects the "Load" button, a load event is triggered, which tells the listeners
   * that this event happened and they can act accordingly. When a load event is emitted, a file
   * dialog is opened and the user can select the file that should be loaded into the program. The
   * listeners hear this and in turn, call their handle events.
   */
  private void emitLoadEvent() {
    for (ViewListener listener : listenersToNotify) {
      this.openImageFileDialog();
      listener.handleLoadEvent(currentFilePath);
    }
  }

  /**
   * When the user selects the "Save" button, a save event is triggered, which tells the listeners
   * that this event happened and they can act accordingly. When a save event is emitted, a file
   * dialog is opened and the user can select the filepath and extension type
   * so that an image can be saved to the harddrive. Listeners hear this and in turn,
   * call their handle events.
   */
  private void emitSaveEvent() {
    for ( ViewListener listener : listenersToNotify ) {
      this.saveImageFileDialog();
      listener.handleSaveEvent();
    }
  }

  /**
   * When the user selects the "Blur" button, a blur event is triggered, which tells the listeners
   * that this event happened and they can act accordingly. When a blur event is emitted, the
   * controller will call the BlurTransformation in the model and the image will be transformed
   * and manipulated accordingly.
   */
  private void emitBlurEvent() {
    for ( ViewListener listener : listenersToNotify ) {
      listener.handleBlurEvent();
    }
  }


  /**
   * When the user selects the "Sharpen" button, a sharpen event is triggered, which tells the listeners
   * that this event happened and they can act accordingly. When a sharpen event is emitted, the
   * controller will call the SharpenTransformation in the model and the image will be transformed
   * and manipulated accordingly.
   */
  private void emitSharpenEvent() {
    for ( ViewListener listener : listenersToNotify ) {
      listener.handleSharpenEvent();
    }
  }


  /**
   * When the user selects the "Value Component" button, a value event is triggered, which tells the
   * listeners that this event happened and they can act accordingly. When a value event is emitted, the
   * controller will call the GreyscaleValueTransformation in the model and the image will be
   * transformed and manipulated accordingly.
   */
  private void emitValueEvent() {
    for ( ViewListener listener : listenersToNotify ) {
      listener.handleValueEvent();
    }
  }


  /**
   * When the user selects the "Intensity Component" button, an intensity event is triggered, which tells the
   * listeners that this event happened and they can act accordingly. When an intensity event is emitted, the
   * controller will call the GreyscaleIntensityTransformation in the model and the image will be
   * transformed and manipulated accordingly.
   */
  private void emitIntensityEvent() {
    for ( ViewListener listener : listenersToNotify ) {
      listener.handleIntensityEvent();
    }
  }


  /**
   * When the user selects the "Luma Component" button, a value event is triggered, which tells the
   * listeners that this event happened and they can act accordingly. When a luma event is emitted, the
   * controller will call the GreyscaleLumaTransformation in the model and the image will be
   * transformed and manipulated accordingly.
   */
  private void emitLumaEvent() {
    for ( ViewListener listener : listenersToNotify ) {
      listener.handleLumaEvent();
    }
  }


  /**
   * When the user selects the "Red Channel" button, a red channel event is triggered, which tells the
   * listeners that this event happened and they can act accordingly. When a red channel event is
   * emitted, the controller will call the GreyscaleChannelTransformation(red) in the model and the
   * image will be transformed and manipulated accordingly.
   */
  private void emitRedChannelEvent() {
    for ( ViewListener listener : listenersToNotify ) {
      listener.handleRedChannelEvent();
    }
  }


  /**
   * When the user selects the "Green Channel" button, a green channel event is triggered, which tells the
   * listeners that this event happened and they can act accordingly. When a green channel event is
   * emitted, the controller will call the GreyscaleChannelTransformation(green) in the model and the
   * image will be transformed and manipulated accordingly.
   */
  private void emitGreenChannelEvent() {
    for ( ViewListener listener : listenersToNotify ) {
      listener.handleGreenChannelEvent();
    }
  }


  /**
   * When the user selects the "Blue Channel" button, a blue channel event is triggered, which tells the
   * listeners that this event happened and they can act accordingly. When a blue channel event is
   * emitted, the controller will call the GreyscaleChannelTransformation(blue) in the model and the
   * image will be transformed and manipulated accordingly.
   */
  private void emitBlueChannelEvent() {
    for ( ViewListener listener : listenersToNotify ) {
      listener.handleBlueChannelEvent();
    }
  }


  /**
   * When the user selects the "Brighten" button and enters a number into the text field,
   * a brighten event is triggered, which tells the
   * listeners that this event happened and they can act accordingly. When a brighten event is
   * emitted, the controller will call the BrightenTransformation(increment) in the model, and will get
   * the specified increment from the view, and the image will be transformed and manipulated
   * accordingly.
   */
  private void emitBrightenEvent() {
    for ( ViewListener listener : listenersToNotify ) {
      listener.handleBrightenEvent();
    }
  }


  /**
   * Determines with event was performed by the user. This will allow the view to distinguish
   * between the diffrent buttons in the frame, and which one was pressed by the user. Depending on
   * the action performed, the appropriare emit_Event() is called so that the listeners of the View
   * can respond to the different events that are performed.
   * @param e the event to be processed.
   */
  @Override
  public void actionPerformed (ActionEvent e) {
    switch (e.getActionCommand()) {
      case "load":
        emitLoadEvent();
        break;
      case "save":
        emitSaveEvent();
        break;
      case "blur":
        emitBlurEvent();
        break;
      case "sharpen":
        emitSharpenEvent();
        break;
      case "value":
        emitValueEvent();
        break;
      case "intensity":
        emitIntensityEvent();
        break;
      case "luma":
        emitLumaEvent();
        break;
      case "red":
        emitRedChannelEvent();
        break;
      case "green":
        emitGreenChannelEvent();
        break;
      case "blue":
        emitBlueChannelEvent();
        break;
      case "brighten":
        emitBrightenEvent();
        break;
      default:
        throw new IllegalStateException(e.getActionCommand());
    }
  }
}

