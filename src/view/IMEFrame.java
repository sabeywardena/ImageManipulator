package view;

import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.ImageIcon;
import javax.swing.BoxLayout;
import javax.swing.BorderFactory;


/**
 * An extension of the Java Swing {@code JFrame} class, {@code IMEFrame} represents the methods
 * and tools used to construct the GUI for IME. Utilizes several Swing components to create a
 * user-friendly UI that allows someone unfamiliar with the code to easily manipulate an image.
 */
public class IMEFrame extends JFrame {
  private JPanel mainPanel;
  private JLabel fileOpenDisplay;
  private JLabel fileSaveDisplay;
  private JPanel histogramPanel;
  private JPanel dialogBoxesPanel;
  private JButton fileOpenButton;
  private JButton fileSaveButton;
  private JComboBox<String> combobox;
  private JLabel imageLabel;

  /**
   * Constructs an instance of {@code IMEFrame} with a window to view the loaded image,
   * histogram, and buttons for loading, saving, and a drop-down for choosing an operation to
   * apply to the image.
   */
  public IMEFrame() {
    super();
    setUp();
    setDialogBox();
    viewLoad();
    setDropDown();
    viewSave();
  }


  /**
   * Renders a histogram of the RGB channel values and intensity beneath the image. 
   * @param red the array of values to represent the red channel
   * @param green the array of values to represent the green channel
   * @param blue the array of values to represent the blue channel
   * @param i the array of values to represent the intensity
   */
  public void displayHistogram(int[] red, int[] green, int[] blue, int[] i) {
    histogramPanel.removeAll();
    HistogramPanel h = new HistogramPanel();
    h.setPreferredSize(new Dimension(750, 450));
    h.showHistogram(red, green, blue, i);
    histogramPanel.add(h);
  }

  /**
   * Renders the text of the FilePath of an image when loaded in by pressing the load button.
   * @param f the file path as a string
   */
  public void addTextLoad(String f) {
    fileOpenDisplay.setText(f);
  }

  /**
   * Renders the text of the FilePath of an image when typed to save by pressing the save button.
   * @param f the file path as a string
   */
  public void addSaveLoad(String f) {
    fileSaveDisplay.setText(f);
  }

  /**
   * Sets the action listener to the given listener to control the action events for this
   * instance of {@code IMEFrame}.
   * @param listener the listener to assign to this code
   */
  public void setListener(ActionListener listener) {
    fileOpenButton.addActionListener(listener);
    fileSaveButton.addActionListener(listener);
    combobox.addActionListener(listener);
  }

  /**
   * Renders an image to the GUI.
   * @param pathName the name of the image to load in from a file
   */
  public void renderImage(String pathName) {
    imageLabel.setIcon(new ImageIcon(pathName));
  }

  /**
   * Updates the loaded image to a given buffered image.
   * @param img the buffered image to replace the currently loaded image
   * @throws IllegalArgumentException if the buffered image is not loaded properly
   */
  public void updateImage(BufferedImage img) throws IllegalArgumentException {
    imageLabel.setIcon(new ImageIcon(img));

  }

  private void setUp() {
    setTitle("Image Manipulation and Enhancement");
    setSize(1500, 900);

    // actual field
    mainPanel = new JPanel();
    dialogBoxesPanel = new JPanel();
    //show an image with a scrollbar
    JPanel imagePanel = new JPanel();
    imageLabel = new JLabel();
    JScrollPane imageDisplay = new JScrollPane(imageLabel);
    dialogBoxesPanel = new JPanel();



    // for the histogram
    histogramPanel = new JPanel();

    //for elements to be arranged vertically within this panel
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
    //scroll bars around this main panel (actual field)
    JScrollPane mainScrollPane = new JScrollPane(mainPanel);
    add(mainScrollPane);

    //a border around the panel with a caption
    imagePanel.setBorder(BorderFactory.createTitledBorder("Loaded Image"));
    imagePanel.setPreferredSize(new Dimension(750, 450));
    imagePanel.setMaximumSize(new Dimension(750, 450));
    imageDisplay.setPreferredSize(new Dimension(500, 400));
    mainPanel.add(imagePanel);
    imagePanel.add(imageDisplay);

    histogramPanel.setBorder(BorderFactory.createTitledBorder("Histogram"));
    histogramPanel.setPreferredSize(new Dimension(750, 450));
    histogramPanel.setMaximumSize(new Dimension(750, 450));

    mainPanel.add(histogramPanel);

  }

  private void setDialogBox() {
    //dialog boxes
    dialogBoxesPanel.setBorder(BorderFactory.createTitledBorder("Load/Save"));
    dialogBoxesPanel.setLayout(new BoxLayout(dialogBoxesPanel, BoxLayout.PAGE_AXIS));
    mainPanel.add(dialogBoxesPanel);
  }

  private void viewLoad() {
    JPanel fileOpenPanel = new JPanel();
    fileOpenPanel.setLayout(new FlowLayout());
    dialogBoxesPanel.add(fileOpenPanel);
    fileOpenButton = new JButton("Open a file");
    fileOpenButton.setActionCommand("Open file");
    //fileOpenButton.addActionListener(this);
    fileOpenPanel.add(fileOpenButton);
    fileOpenDisplay = new JLabel("File path will appear here");
    fileOpenPanel.add(fileOpenDisplay);
  }

  private void viewSave() {
    JPanel fileSavePanel = new JPanel();
    fileSavePanel.setLayout(new FlowLayout());
    dialogBoxesPanel.add(fileSavePanel);
    fileSaveButton = new JButton("Save a file");
    fileSaveButton.setActionCommand("Save file");
    fileSavePanel.add(fileSaveButton);
    fileSaveDisplay = new JLabel("File path will appear here");
    fileSavePanel.add(fileSaveDisplay);
  }

  private void setDropDown() {
    // combo
    JPanel comboboxPanel = new JPanel();
    comboboxPanel.setBorder(BorderFactory.createTitledBorder("Operations"));
    comboboxPanel.setLayout(new BoxLayout(comboboxPanel, BoxLayout.PAGE_AXIS));
    mainPanel.add(comboboxPanel);

    JLabel comboboxDisplay = new JLabel("Choose an operation:");
    comboboxPanel.add(comboboxDisplay);
    String[] options = {"", "value component", "horizontal flip", "vertical flip", "brighten",
                        "darken", "luma component", "intensity component", "red component",
                        "green component", "blue component", "blur", "sharpen", "greyscale",
                        "sepia", "downscale"};
    combobox = new JComboBox<String>();

    //the event listener when an option is selected
    combobox.setActionCommand("Operations");
    for (int i = 0; i < options.length; i++) {
      combobox.addItem(options[i]);
    }
    comboboxPanel.add(combobox);
  }
}
