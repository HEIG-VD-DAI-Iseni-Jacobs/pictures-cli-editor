package ch.heigvd.dai.models;

import java.io.*;

/** Supports BMP images for reading and writing. */
public class BMPImage {

  private BMPHeader header;
  private Pixel[][] image;
  private final String inputPath;
  private final String outputPath;

  /** Stores any extra data after the pixel data */
  private byte[] extraData;

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

      // Read any remaining data (extra data)
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      int nextByte;
      while ((nextByte = bis.read()) != -1) {
        baos.write(nextByte);
      }
      extraData = baos.toByteArray();

    } catch (IOException e) {
      System.err.println("[e]: Error reading image: '" + e.getMessage() + "'");
    } catch (RuntimeException e) {
      System.err.println("[e]: Invalid values: '" + e.getMessage() + "'");
    }
  }

  public BMPHeader getHeader() {
    return header;
  }

  /** Writes the image to the specified output path. */
  public void writeImage() {
    try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(outputPath))) {
      int width = header.getImageWidth();
      int height = header.getImageHeight();

      // Write the header
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

      // Write any extra data after the pixel data
      if (extraData != null && extraData.length > 0) {
        bos.write(extraData);
      }

      bos.flush(); // Ensure all data is written
      System.out.println("Image written successfully to " + outputPath);

    } catch (IOException e) {
      System.err.println("Error writing image: " + outputPath);
    }
  }
}
