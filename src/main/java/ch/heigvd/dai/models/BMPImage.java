package ch.heigvd.dai.models;

import java.io.*;

/** We support only BMPImage as it's one of the easiest way to store pictures. */
public class BMPImage {

  private BMPHeader header;
  private byte[][] image;
  private final String inputPath;
  private final String outputPath;

  /**
   * Constructs an ImageProcessor with the specified input path, output path, and format.
   *
   * @param inputPath the path of the image file to load
   * @param outputPath the path where the processed image will be saved
   */
  public BMPImage(String inputPath, String outputPath) {
    this.inputPath = inputPath;
    this.outputPath = outputPath;
  }

  /** Reads an image from the specified file path. */
  public void readImage() {
    try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(inputPath))) {
      header = new BMPHeader();
      header.readHeader(bis);
      System.out.println(header.getDataOffset());
      System.out.println("Width: " + header.getImageWidth());
    } catch (IOException e) {
      System.err.println("[e]: Error reading image: '" + e.getMessage() + "'");
    }
  }

  /**
   * Writes the image to the specified output path in the given format.
   *
   * @throws RuntimeException if the image cannot be written in the specified format
   */
  //    public void writeImage() {
  //        try (OutputStream imageFile = new FileOutputStream(outputPath)) {
  //            if (!ImageIO.write(image, format, imageFile)) {
  //                throw new RuntimeException("Failed to write the image in the specified format: "
  // +
  //                        format);
  //            }
  //        } catch (Exception e) {
  //            System.err.println("Error writing image: " + outputPath);
  //        }
  //    }
}
