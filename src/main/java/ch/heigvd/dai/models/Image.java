package ch.heigvd.dai.models;

import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;

public class Image {
  private BufferedImage image;
  private final String format;
  private final String input_path;
  private final String output_path;

  /**
   * Constructs an ImageProcessor with the specified input path, output path, and format.
   *
   * @param path the path of the image file to load
   * @param outputPath the path where the processed image will be saved
   * @param format the format to save the image (e.g., "png", "jpg")
   */
  public Image(String path, String outputPath, String format) {
    this.input_path = path;
    this.output_path = outputPath;
    this.format = format;
  }

  /**
   * Reads an image from the specified file path.
   *
   * @throws RuntimeException if the image cannot be read or is not found
   */
  public void readImage() {
    try (InputStream imageFile = new FileInputStream(input_path)) {
      this.image = ImageIO.read(imageFile);
      if (this.image == null) {
        throw new RuntimeException("Image not found");
      }
    } catch (IOException e) {
      throw new RuntimeException("Error reading image: " + input_path, e);
    }
  }

  /**
   * Writes the image to the specified output path in the given format.
   *
   * @throws RuntimeException if the image cannot be written in the specified format
   */
  public void writeImage() {
    try (OutputStream imageFile = new FileOutputStream(output_path)) {
      if (!ImageIO.write(image, format, imageFile)) {
        throw new RuntimeException("Failed to write the image in the specified format: " + format);
      }
    } catch (Exception e) {
      System.err.println("Error writing image: " + output_path);
    }
  }
}
