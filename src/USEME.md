## Homework 10 - Image Editor Program, part III
#### Author: Kate Saslow
#### Semester: Summer 2023

***Introduction***

The culmination of this 3-week assignment is an image editor that uses a graphical user interface
(GUI) to render the image directly to the screen in a pop-up window that appears when the program
is run. The program allows the user to load an image into the program via FileDialog, and manipulate
that image with all transformations laid out in the model created in the last 2 assignments. Each 
transformation supported by the model in this program has its own button in the interface. Whatever
transformation button the user selects is called by the controller and then the manipulated image
is rendered directly in the GUI by the view. 

In order to run the program, run the Main() Class. This will open the GUI directly and an image
can be directly chosen and loaded within the frame opened. So long as no command line arguments
are input, the program will run with the GUI as the default option. If you wish to use a script 
text file, then you need to input `-file your_script_text_here.txt`. Otherwise, if you wish to just
interact with the program using Assignment 8's Run Dialog commands one at a time, then the command
line argument be simply `-text` and the program will run with the interactive IntelliJ run window. 

The source image that I used in my examples is called "July4th.png", and is my own photo. The 
original image, as well as the 3 screenshots provided, are all stored in the `src/` directory
so that I can use the screenshots in this file. 

This is the USEME file that explains how to interact with the program. My application
has buttons in the GUI which support the follopwing transformations:

``Brighten`` button: which carries out the ``BrightenTransformation``

``Red Channel`` button: which carries out the ``GreyscaleChannelTransformation``

``Green Channel`` button: which carries out the ``GreyscaleChannelTransformation``

``Blue Channel`` button: which carries out the ``GreyscaleChannelTransformation``

``Intensity Component`` button: which carries out the ``GreyscaleIntensityTransformation``

``Luma Component`` button: which carries out the ``GreyscaleLumaTransformation``

``Value Component`` button: which carries out the ``GreyscaleValueTransformation``

``Blur`` button: which carries out the ``BlurTransformation``

``Sharpen`` button: which carries out the ``SharpenTransformation``

``Load`` button: which runs the `JPGImageLoader` `PNGImageLoader` or `PPMImageLoader` accordingly.

``Save`` button: which runs the `JPGImageSaver` `PNGImageSaver` or `PPMImageSaver` accordingly. 

***Running the program***

In order to run the GUI version of this program, simply run the ``Main()`` class with no command line
arguments. This will 
start the program and automatically open the GUI where an image can be loaded and transformed. 

Once an image is selected and loaded into the program, the image can be edited by using the buttons 
in the toolbar along the left side of the window. The user can select as many buttons as many times
as preferred. The updated image post-transformation will automatically be rendered to the view so 
that the user can see what was done. Additionally, to track the transformations made by the user, 
there is a field at the top of the toolbar that displays a message telling the user which 
transformation was selected last. If there are any errors (e.g. if the user selects the `Brighten`
button without first entering an integer value in the text field), the appropriate error message will
also be displayed at the top of the toolbar. This allows a user with no information about the backend 
details of my program to be able to interact with the image editor. 

There are 3 ways a user can interact with this image editing program: from the Run dialog in IntelliJ 
directly (command line argument `-text`), from the terminal by entering a script in the form of 
a .txt file (command line argument `-file your_script_here.txt`), and directly with the 
GUI if running the Main() driving class with no additional command line arguments. 

**Results**

This is what my GUI looks like before an image is loaded into it:

![GUI before image loaded]

I have a toolbar of all transformations on the left side of the GUI frame, and I have a
canvas with scrollbars to move depending on the size of the loaded image. The bar
along the top that says "Image Editor" will display messages to the user to help with
usability. It allows the user to see any errors, as well as the last transformation
carried out.

![GUI after image loaded]

After an image is loaded into the GUI, you can see that - depending on the size of the
image - the canvas should be adjusted accordingly. The image I chose to work with
(July4th.png) is rather large, so it is bigger than the canvas I set for my GUI.

![GUI after image sharpened]

After an image is transformed (in the above example, the image has been repeatedly
sharpened to get that effect), you see that the image is rendered directly to the GUI and
the transformed image shows up immediately after the button was clicked. The user must not refresh
the screen or anything like that. The image is transformed and rendered in the same canvas,
simply replacing the image from before it.

For more details about interacting with and using the application, please refer
to the `USEME.md` file.

***Instructions from the last assignment:***
(Below are instructions for how to interact with the program via command line, as required in the 
USEME.md file from Assignment 9.)

In order to use the ``Main()`` driver from the Run dialog window and enter the .txt script as a 
custom run configuration, the directory path `res/` needs to be added to the commands. That is 
bceause the Main() file is in the `src/` directory. The corresponding commands are written in the 
txt file `ImageEditorScript.txt`. Unlike running the program from the JAR file, the Main() file is 
in a different folder, so all commands require the `res/` directory path specification. 

In order to use the `JAR` file and run the text file script directly from the command line (for 
example like such: `java -jar HW09_new.jar -file ImageEditorScript_for_JAR.txt`), the correct 
script to use is the file `ImageEditorScript_for_jar.txt` as used in the example above. This is 
because the images are in the same directory as the JAR file, so no path specification is needed.


* In order to start using the program, choose a file to load and run this command:

``load Lake.ext sampleName`` (and replace the ".ext" with the desired extension type)

This will load the file at the given path, and add it to the database under the name "fourxfour".
The load command can also be used with JPG files, and PNG files, simply include the extension type
in the file path and the image will be loaded accordingly.


* To transform this image via the JAR file, run any of the below commands:

    * Greyscale CHANNEL Command: to change all pixel values to a specific channel (R, G or B)

      ``color-channel red sampleName sampleName_red_channel``

      ``color-channel green sampleName sampleName_green_channel``

      ``color-channel blue sampleName sampleName_green_channel``

    * Greyscale VALUE Command: to change all pixel values to value calculation

      ``value-component sampleName sampleName_value_component``

    * Greyscale INTENSITY Command: to change all pixel values to intensity calculation

      ``intensity-component sampleName sampleName_intensity_component``

    * Greyscale LUMA Command: to change all pixel calues to the luma calculation

      ``luma-component sampleName sampleName_luma_component``

    * BRIGHTEN Command: to brighten OR darken an image. If positive increment entered, brighten.

      ``brighten 50 sampleName sampleName_brighten_50``

      ``brighten -50 sampleName sampleName_darken_50``

    * BLUR Command: to blur an image

      ``blur sampleName sampleName_blurred``

    * SHARPEN Command: to sharpen an image

      ``sharpen sampleName sampleName_sharpened``

    * SAVE Command: in order to save any image to a file, run this command:

      ``save sampleName_new.ext sampleName_newName`` (and replace ".ext" with the desired
      extension type.)


[GUI before image loaded]: GUI_beforeImageLoadedsmall.png
[GUI after image loaded]: GUI_imageLoadedsmall.png
[GUI after image sharpened]: GUI_imageSharpenedsmall.png