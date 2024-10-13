package ch.heigvd.dai.models;

import java.io.*;

/** We support only BMPImage as it's one of the easiest way to store pictures. */
public class BMPImage {

  private BMPHeader header;
  private Pixel[][] image;
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

  /** Reads an image from the specified input path. */
  public void readImage() {
    try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(inputPath))) {
      header = new BMPHeader();
      header.readHeader(bis);
      int width = header.getImageWidth();
      int height = header.getImageHeight();

      image = new Pixel[width][height];
      int red;
      int green;
      int blue;
      for (int w = 0; w < width; w++) {
        for (int h = 0; h < height; h++) {
          red = bis.read();
          green = bis.read();
          blue = bis.read();
          if (blue == -1 || green == -1 || red == -1) {
            throw new IOException("Unexpected end of pixel data");
          }
          image[w][h] = new Pixel(red, green, blue);
        }
      }
    } catch (IOException e) {
      System.err.println("[e]: Error reading image: '" + e.getMessage() + "'");
    } catch (RuntimeException e) {
      System.err.println("[e]: Invalid values: '" + e.getMessage() + "'");
    }
  }

  public BMPHeader getHeader() {
    return header;
  }

  // TODO : write to a new image
  /** Writes the image to the specified output path. */
  //    public void writeImage() {
  //        try (OutputStream imageFile = new FileOutputStream(outputPath)) {
  // TODO
  //        } catch (Exception e) {
  //            System.err.println("Error writing image: " + outputPath);
  //        }
  //    }
}
