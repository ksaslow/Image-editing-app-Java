## Homework 10 - Image Editor Program, part III
#### Author: Kate Saslow
#### Semester: Summer 2023

***Introduction***

This program is a basic image editor. It follows an MVC design, meaning it has a model, a view and
a controller. 

The image being edited is an image that I own. It (in its original size) is an image of the lake
where I spent a month this June/July. I had to scale it down to ~300x300 pixels so that it would
fit in a zip file of max 5MB for the submission. So it doesn't look like much. 

The image used in the image editor is also one that I own. It is a picture of the July4th party my 
family had and the food set out on the table.

*** Preview of App ***

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

***File Structure***

**Model**

The model includes the most fundamental part of an image, a Pixel.

*Model: the pixel*

The ``IPixelState`` interface. This interface is a read-only interface that allows the program
to get the R G or B values from a pixel. 

The ``IPixel`` interface is the mutable interface, that allows the program to use the setter methods
to set the R G or B values in a pixel. 

The ``Pixel`` Class represents the implementation of the ``IPixel`` interface. A Pixel object
is what makes up an image; namely, a 2-Dimensional matrix of Pixel objects make up an image. 

*Model: the image*

The ``IImageState`` interface is a read-only interface that allows the program to get image-level
data, such as the height (how many pixels long), the width (how many pixels wide) and for any given
``Pixel`` within the image, it can get the red, green, or blue channel value.

The ``IImage`` interface sets the contract for mutating an image, by defining a ``setPixel()`` 
method, that allows the program to change and set the R, G, and B values at any given pixel
within an image.

The ``ImageImpl`` Class represents an IImage or IImageState object and is the implementing 
class for the two image interfaces. An Image is made up of a 2-Dimensional ``Pixel`` objects, 
and its characteristics include its width, height, and its unique matrix of pixels.

*Model: the image database*

The ``IImageDataBase`` interface is the contract for the ImageDataBase portion of the model.
The program allows the user to load, transform, and save images to an image database. The model uses
a HashMap and stores the ``IImageState`` objects with a unique key that the user sets. In this 
program, the ``IImageState`` objects can be added and fetched using the methods.

The ``ImageDataBase`` Class is the implementation of the IImageDataBase model. This class allows the
user to add and get images from the database by defining and implementing the methods.

*Model: the kernel*

The ``Kernel`` class in my model is what allows for the ``BlurTransformation`` and 
``SharpenTransformation`` to be possible. The Kernel Class takes a 2Dimensional array of doubles, 
and is initiated with a size. The square Kernel must have an odd number of dimensions. The two 
specific Kernel objects initiated for the blur and sharpen transformations are 3x3 and 5x5 kernels
respectively. The Kernel class does, however, accept custom kernels for future transformations.

*Model: transformations*

The ``ITransformation`` interface is the contract applied to each of the transformation classes. 
The transformation classes are the *strategy patterns* that the program uses to determine which
of the editing strategies to follow, or which transformation to apply to the image. 

The different transformation patterns that this program currently offers include:

``BrightenTransformation``: which either brightens or darkens and image, depending on if the 
increment given by the user is positive or negative.

``GreyscaleChannelTransformation``: which converts the image to a greyscale image by selecting 
either the red, green, or blue channel of the pixel accordingly, and applying that specific value
to all values of that pixel.

``GreyscaleIntensityTransformation``: which converts the image a greyscale image by selecting
the average value of the R, G and B values, and applying that average value to all values of that
pixel.

``GreyscaleLumaTransformation``: which converts the image a greyscale image by calculating a 
weighted sum of the R, G and B values, and applying that weighted sum to all values of that
pixel.

``GreyscaleValueTransformation``: which converts the image to a greyscale image by selecting
the maximum value of the R, G and B values, and applying that max value to all values of that
pixel.

``BlurTransformation``: which processes the image by applying a Gaussian blur kernel to each pixel, 
and modifying the value of each of the color channels depending on a weighted sum of the target 
pixel and the neighboring pixels. The ultimate effect is a blurred image.

``SharpenTransformation``: which processes the image by applying a sharpening kernel to each pixel, 
and modifying the value of each of the color channels depending on a weighted sum of the target
pixel and the neighboring pixels. The ultimate effect is a sharpened images.

The model was completely unchanged during my Assignment 10 work with the GUI. 

**View**

The view of the program was what was heavily modified in Assignment 10. In my previous 2 assingments, 
the view was essentially only a text-based view for a String representation of the pixels of an
image. In this homework assignment, however, I added a graphical user interface (GUI) to render the 
image in an image editor window. 

*View: IImageView:

``IImageTextView`` interface is the contract that will dictate how an image object will be rendered 
to a text-based view for the user.

``TextView`` Class implements the IImageTextView interface. There is a ``toString()`` method that 
prints the RGB values of each pixel in the image. There is also a ``renderMessage()`` method that 
allows messages to be rendered to the user, this also includes error messages. 

Although not in the view package, the transformed image objects are currently being transformed
and saved, and ultimately written as .ppm files which allow the images to be viewed after 
transforming them -- either as text files or images.

`IView` interface is the contract what will dictate how an image will be rendered to a GUI view for
the user to see. 

`View` Class implements the IView interface. This is the implementation of the GUI. Using Java
Swing, I create a new frame to render the image and show all transformations in the form of buttons. 
The user can interact with the image editor via the GUI because of the JButtons. In my last
assignment I used command patterns to transform an image, whereas this time I am using buttons in my
GUI to call the transformations in my model. 

`ViewListener` interface is where all method that are subscribed to by the controller are laid out.
The GUI-specific controller in this iteration of the assignment is the only defined "listener" to my
view. The GUI-controller "listens" to the events that happen in the view (e.g. what buttons are 
pressed by the user) and reacts accordingly. 

`Canvas` Class is the object that actually renders the image. A Canvas object extends JPanel, so it 
inherits all functionality of JPanel, but is not directly using a JPanel object as the canvas. This 
allows flexibility in the future, in case the specifics of the program are ever changed, and a new 
"canvas" type object can be switched out


**Controller**

The controller of the program allows the user to interact with the program. The controller is given
a model, and is responsible for all input from the user, and ouput that transforms and returns a
changed image. 

My controller was not modified from the last assignment, it was only extended. I added a new 
interface and class to implement a GUI-specific controller. My last two assignments utilized only
the IController and ControllerImpl interface and class respectively, and my program for the final
assignment utilizied the IControllerGUI interface and the ControllerGUI class to allow my program to
use a GUI as the image editor, rather than a text-based script or text user input. 

*Controller: input/output*

``IImageLoader`` interface is the contract applied to all ImageLoader classes. Currently this is 
only implemented with .ppm files. 

``IImageSaver`` interface is the contract applied to all ImageSaver classes. Currently only .ppm
files are being saved and manipulated in this program. 

``PPMImageLoader`` Class implements the IImageLoader interface as it pertains to loading .ppm files
to the program in order to manipulate them.

``PPMImageSaver`` Class implements the IImageSaver interface as it pertains to saving a .ppm file
type.

``JPGImageLoader`` Class implements the IImageLoader interface as it pertains to loading .jpg files
to the program in order to manipulate them.

``JPGImageSaver`` Class implements the IImageSaver interface as it pertains to saving a .jpg file
type.

``PNGImageLoader`` Class implements the IImageLoader interface as it pertains to loading .png files
to the program in order to manipulate them.

``JPGImageSaver`` Class implements the IImageSaver interface as it pertains to saving a .png file
type.

*Controller: the controller*

``IController`` interface allows the program to start and run. 

``ControllerImpl`` Class implements the IController interface and is the workhorse of the 
program. This class contains the ``Map<>`` of command patterns that allows the user to simply enter
text-based commands in order to interact with the program.

`IControllerGUI` interface allows the program to start and run a GUI so that the images can be 
rendered to the screen rather than relying on text-based representations. 

`ControllerGUI` class implements the IControllerGUI interface and is the workhorse of the GUI-based
interaction of this program. This class contains all the handle_Event() methods, which call the 
appropriate transformations from the model depending on which button was clicked in the view. If the 
user selects the "Blur" button in the view, the controller handles this event by calling the 
BlurTransformation() class from the model, which blurs the image. The blurred image is then immediately
rendered to the view for the user to see.

*Controller: command patterns*

The controller in this program uses command patterns to transform the images loaded into it. The 
different command patterns are laid out in the ``ControllerImpl`` class.

``ICommand`` interface allows the specific command read by the Scanner to be run on the specific
database initiated.

For a full overview of which commands are supported by this application, please refer
to the `USEME.md` file.

**Design Changes**

I didn't make any modifications of my model from the last assignment. Both my view and controller 
were extended (**not** modified), in that they were both given GUI functionality. The model remained
completely untouched from Assignment 9 to Assignment 10. I added a new interface and implementing 
class to my controller: IControllerGUI and ControllerGUI. I added a new method to my `ImageUtil`
class that allows the program to convert BufferedImages to IImageState objects and convert 
IImageState objects back to BufferedImages. This allows all images transformed by the program (in
the IImageState state) to be rendered by the GUI as BufferedImages. To my view, I added the
interface IView and ViewListener, and the class View. 

Once again, no files from the last homework assignment were changed, except for my `ImageUtil` 
class, where I had to add the new image-object conversion methods to maximize my program's
functionality. 
