package ch.heigvd.dai.models;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;

/**
 * Handles reading a BMP header, extracting its data and writing to a new file.
 *
 * @author Jacobs Arthur
 * @author Iseni Aladin
 */
public class BMPHeader {
  /** Magic identifier of BMP files */
  private static final int BMP_MAGIC = 0x424D;

  /** Size of the fixed header */
  private static final int HEADER_LENGTH = 14;

  /** Index of the value "data offset" in a BMP header */
  private static final int DATA_OFFSET_INDEX = 10;

  /** The BITMAPFILEHEADER, a fixed-size header for BMP */
  private byte[] fileHeader = new byte[HEADER_LENGTH];

  /** The BITMAPINFOHEADER, a variable-size header coming after the BITMAPFILEHEADER */
  private byte[] infoHeader;

  BMPHeader() {}
  ;

  BMPHeader(byte[] fileHeader, byte[] infoHeader) {
    this.fileHeader = fileHeader;
    this.infoHeader = infoHeader;
  }

  /**
   * Read a BMP header : both the fixed-size header and variable-size header
   *
   * @param bis the buffered input stream to read from
   * @throws IOException if error while reading from stream
   */
  public void readHeader(BufferedInputStream bis) throws IOException {
    int bytesRead;

    // Read the fixed header
    bytesRead = bis.read(fileHeader, 0, HEADER_LENGTH);
    if (bytesRead != HEADER_LENGTH) {
      throw new IOException("Failed to read all the fixed header's data");
    }
    if (getMagic() != BMP_MAGIC) {
      throw new RuntimeException("The file type is not bmp, thus it is not supported");
    }

    int infoheaderLength = getDataOffset() - HEADER_LENGTH;
    infoHeader = new byte[infoheaderLength];

    // Read the rest of the metadata
    bytesRead = bis.read(infoHeader, 0, infoheaderLength);
    if (bytesRead != infoheaderLength) {
      throw new IOException("Failed to read all the variable header's data");
    }
  }

  /**
   * Read the Magic number from the header
   *
   * @return the file's magic number. Should be 0x424D as we're dealing with BMP
   */
  private int getMagic() {
    return (fileHeader[0] << 8 | fileHeader[1]);
  }

  /**
   * If we correctly read a BMP header, return the dataOffset value (the number of bytes before the
   * actual pixels are stored)
   *
   * @return pixel data offset
   */
  public int getDataOffset() {
    // Check we read a BMP before
    if (getMagic() != BMP_MAGIC) {
      throw new RuntimeException("Error, try to access dataOffset value before reading it");
    }

    return ((fileHeader[DATA_OFFSET_INDEX + 3] & 0xFF) << 24)
        | ((fileHeader[DATA_OFFSET_INDEX + 2] & 0xFF) << 16)
        | ((fileHeader[DATA_OFFSET_INDEX + 1] & 0xFF) << 8)
        | (fileHeader[DATA_OFFSET_INDEX] & 0xFF);
  }

  /**
   * Write the header to a new file
   *
   * @param bos stream to write to
   * @throws IOException if error while writing to stream
   */
  public void writeHeader(BufferedOutputStream bos) throws IOException {
    bos.write(fileHeader);
    bos.write(infoHeader);
  }

  /**
   * Read the image's height from the header
   *
   * @return image height
   */
  public int getImageHeight() {
    if (infoHeader.length < 12) {
      throw new RuntimeException("Error, header data is insufficient for reading the height.");
    }

    return ((infoHeader[8 + 3] & 0xFF) << 24)
        | ((infoHeader[8 + 2] & 0xFF) << 16)
        | ((infoHeader[8 + 1] & 0xFF) << 8)
        | (infoHeader[8] & 0xFF);
  }

  /**
   * Read the image's width from the header
   *
   * @return image width
   */
  public int getImageWidth() {
    if (fileHeader.length != 14) {
      throw new RuntimeException("Error, try to access dataOffset value before reading it");
    }

    return ((infoHeader[4 + 3] & 0xFF) << 24)
        | ((infoHeader[4 + 2] & 0xFF) << 16)
        | ((infoHeader[4 + 1] & 0xFF) << 8)
        | (infoHeader[4] & 0xFF);
  }

  /**
   * Defines image height
   *
   * @param height new height
   */
  public void setImageHeight(int height) {
    if (height <= 0) {
      throw new RuntimeException("Image height must be positive.");
    }

    infoHeader[8] = (byte) (height & 0xFF);
    infoHeader[9] = (byte) ((height >> 8) & 0xFF);
    infoHeader[10] = (byte) ((height >> 16) & 0xFF);
    infoHeader[11] = (byte) ((height >> 24) & 0xFF);
  }

  /**
   * Defines image width
   *
   * @param width new width
   */
  public void setImageWidth(int width) {
    if (width <= 0) {
      throw new RuntimeException("Image width must be positive.");
    }

    infoHeader[4] = (byte) (width & 0xFF);
    infoHeader[5] = (byte) ((width >> 8) & 0xFF);
    infoHeader[6] = (byte) ((width >> 16) & 0xFF);
    infoHeader[7] = (byte) ((width >> 24) & 0xFF);
  }

  @Override
  public BMPHeader clone() {
    byte[] clonedFileHeader = fileHeader.clone();
    byte[] clonedInfoHeader = infoHeader.clone();

    return new BMPHeader(clonedFileHeader, clonedInfoHeader);
  }
}
