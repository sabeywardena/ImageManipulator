# Image Manipulator
_____________________________________________________________________________________________

IME: Image Manipulation and Enhancement
Authored by Sarah Abeywardena
Authored by Oishani Chatterji

_____________________________________________________________________________________________

IME is an Image based editing software that serves to purpose enhancing and changing photos, with
current iterations allowing for the brightening and darkening of an image, the flipping of an image,
and a greyscale visualization of the RGB channels, Luma, Value, and Intensity.

(12/13) UPDATE: For Downscaling, a new Downscaling class extending AEdits was made to perform the operation, the
setHashMap() of both controllers were updated to include the new 

(11/22) UPDATE: Created a graphical user interface for program using Java Swing which supports
previous commands like loading and saving images of different file formats, displaying the current
image being worked on with any edits made on it using the program, and displaying a histogram
showing the values of the red, blue, and green channels iun an image, along with the overall
intensity of each pixel in said image.

(11/11) UPDATE: Added implementation for kernel based filters in the Filter and ColorTransformations
 classes, in addition to added support for any image type supported by the ImageIO library. Some
 previous classes were changed to support abstraction for the new additions; namely, the
 IFileFunctions class was created to serve as an interface which is implemented by PPMFunctions and
 ImageIOFunctions, which both implement a load and save method to use on files of the corresponding
 type.

How to Run: ------------------------

Run the program and then enter commands to load, edit, and save images while the program is running

Acceptable command script: ----

Load: load image-path image-name

Save: save image-path image-name

Horizontal-Flip: horizontal-flip image-name dest-image-name

Vertical-Flip: vertical-flip image-name dest-image-name

Brighten: brighten increment image-name dest-image-name

Darken: darken increment image-name dest-image-name

Red-Component: red-component image-name dest-image-name

Green-Component: green-component image-name dest-image-name

Blue-Component: blue-component image-name dest-image-name

Luma-Component: luma-component image-name dest-image-name

Value-Component: value-component image-name dest-image-name

Intensity-Component: intensity-component image-name dest-image-name

Blur: blur image-name dest-image-name

Sepia: sepia image-name dest-image-name

Greyscale: greyscale image-name dest-image-name

Downscale: downscale image-name new-width new-height dest-image-name

Main: ------------------------------
RunIME:
A "Main" class to offer support for the view and the JPanelController, used to render the GUI.

The View: --------------------------

IMEFrame:
An extension of JFrame that allows for creation of the IME GUI, with a panel for the image,
histogram, a drop-down for the operations, and buttons to load and save an image from a file
selector.

HistogramPanel:
An extension of JPanel that allows for the creation of a histogram when given multiple matrices of
data. The histogram paints the data on to allow for a visual representation of the various channels
and intensity, color coded as red, green, and blue, with the intensity marked as a grey.
Transparency is used to be able to clearly see the various levels.

The Controller: --------------------

The Controller is used as a tool to parse information from our model directly to the file stream on
the user's computer. The controller is responsible for loading and saving the images, as well as
communicating with the user.

IMEController:
The controller class with the main method, parses from the commandline to the model to create and
work on files as dictated by the script lines.

JPanelController:
The controller class used when running the GUI, contains support with Java swing and interaction
through the view and the model.

File -----

IFileFunctions:
An interface representing the functions that can be preformed directly on the files, contains a load
 and save method to load in files and set the contents as an IImage.

 PPMFunctions:
 A class to represent the functions to be preformed on a PPM file.

 IOFunctions:
 A class to represent the functions to be preformed on a file represented in the ImageIO class.

The Model: -------------------------

The Model is used as a visual representation of the data contained in an image file.

Image -----
IImage:
An Interface used to represent an Image of any file type, that contains pixels arranged in a 2
dimensional array.

PPMImage:
A class used to represent a PPMImage file, where the information from the file is stored as integers
 and a matrix of Pixels, which each pixel representing a single pixel in an Image with rgb channels.

IOImage:
A class used to represent a file type that is contained in the ImageIOImage class, where the image
in the file is stored as integers in a matrix of Pixels.

---

Pixel:
A class used to represent a single pixel in an image. The class contains 3 fields representing the
three RGB channels, and can be used for images of anytype.

RGBChannel:
An enum used to represent the three types of channels present in a Pixel of an image. With one
channel for red, one for green, and one for blue.

Histogram:
Takes in a matrix of pixels and creates a matrix of integers to represent the intensity of a given
channel. The matrix is sorted with index 0-255, and the value at a given index represents the amount
of pixels that share that channel value (represented by the index number).

Operations: -----

The operations are used to preform various manipulations to the images loaded in by the program.

AEdits: ---------
An abstract class used to represent an edit for an image. Each child class of AEdits implements an
operation method, which is then called on the class to return a new 2D array of Pixels. An AEdit
takes in an existing matrix of pixels and uses that as the reference for the operations.

ChangeBrightness:
This class serves to represent the operation to alter the brightness of an image, by introducing a
second field to represent whether the image should be brightened (true) or darkened (false).

Flip:
Similarly to change brightness, flip also introduces a boolean in the constructor to designate
whether the image should be flipped horizontally (true) or flipped vertically (false).

Intensity:
A class used to represent the intensity of an image. Uses the default AEdits constructor.

Luma:
A class used to represent the luma of an image. Uses the default AEdits constructor.

RGBGreyscale:
A class used to represent a single channel of an image. Takes in an additional parameter to
represent the channel to use in the image manipulation.

Value:
A class used to represent the Value of an image. Uses the default AEdits constructor.

Filter:
Abstract class that extends AEdits, for use in filtering using blur and sharpen.

ColorTransformations:
Abstract class that extends AEdits, for use in filtering based on a color kernal, used here in sepia
 and greyscale.

 Sepia:
 A filter class that uses a kernel to create a sepia filter.

 Greyscale:
 A filter class that uses a kernel to create a greyscale filter.

Blur:
A filter class that uses a kernal to blur an image.

Sharpen:
A filter class that uses a kernal to sharpen an image.

The PPM Image: ---------------------
The "default ppm.ppm" image included in the Res folder was a file called "sample_640x426.ppm" 
downloaded from "https://filesamples.com/formats/ppm#google_vignette". The FileSamples site allows
people to download file samples for free to use for testing purposes. The file downloaded was in P6
format rather than P3, so we converted it to P3

The JPG Image: ---------------------
The "flowers.jpg" image included in the res folder is a personal image Sarah Abeywardena has taken
from her iphone of a flower stand in Vienna, Austria. It is my personal image and I consent for
rights to use it in this project.

"orange_pixel.jpg" -----------------
The "orange_pixel.jpg" image is my personal image and I consent to use it in this project.
