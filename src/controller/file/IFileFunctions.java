package controller.file;

import java.io.IOException;

import model.image.IImage;

/**
 * The IFileFunctions interface represents a series of methods used to load and save any given
 * file type.
 */
public interface IFileFunctions {

  /**
   * Reads in a file and creates the corresponding IImage depending on the file type.
   * @param filePath represents the path of the file to parse information from
   */
  public void readImg(String filePath) throws IOException;

  /**
   * Writes a file based on the given path to write to and the IImage with the contents to put in
   * the image.
   * @param pathName the path to save the IImage too
   * @param img the IImage containing the information for the file
   * @throws IOException if the path is incorrect or doesn't exist
   */
  public void save(String pathName, IImage img) throws IOException;


  /**
   * Retrieves the contents of the image as an IImage.
   * @return an IImage with the contents of the image.
   */
  public IImage getImg() throws IllegalArgumentException;

}
