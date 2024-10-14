package ch.heigvd.dai.models;

import java.io.*;

/** Supports BMP images for reading and writing. */
public class BMPImage {

  private BMPHeader header;
  private Pixel[][] image;
  private final String inputPath;
  private final String outputPath;

  /**
   * Constructs a BMPImage with the specified input and output paths.
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

      for (int h = 0; h < height; h++) { // Iterate from top to bottom
        for (int w = 0; w < width; w++) {
          int blue = bis.read();
          int green = bis.read();
          int red = bis.read();
          if (blue == -1 || green == -1 || red == -1) {
            throw new IOException("Unexpected end of pixel data");
          }
          // Store pixels from bottom to top
          image[w][height - 1 - h] = new Pixel(red, green, blue);
        }
      }

    } catch (IOException e) {
      System.err.println("[e] Error reading image: '" + e.getMessage() + "'");
    } catch (RuntimeException e) {
      System.err.println("[e] Invalid values: '" + e.getMessage() + "'");
    }
  }

  /** Writes the image to the specified output path. */
  public void writeImage() {
    try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(outputPath))) {
      int width = header.getImageWidth();
      int height = header.getImageHeight();

      header.writeHeader(bos);

      // Write pixel data
      for (int h = height - 1; h >= 0; h--) { // Iterate from bottom to top
        for (int w = 0; w < width; w++) {
          Pixel pixel = image[w][h];
          // Write bytes Blue, Green, Red
          bos.write(pixel.getBlue());
          bos.write(pixel.getGreen());
          bos.write(pixel.getRed());
        }
      }
      bos.flush(); // Ensure pixels are written

    } catch (IOException e) {
      System.err.println("[e] Error writing image: " + outputPath);
    }
  }
}
