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
  private final byte[] fileHeader = new byte[HEADER_LENGTH];

  /** The BITMAPINFOHEADER, a variable-size header coming after the BITMAPFILEHEADER */
  private byte[] infoHeader;

  /**
   * Read a BMP header : both the fixed-size header and variable-size header
   *
   * @param bis the buffered input stream to read from
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

  private int getMagic() {
    return (fileHeader[0] << 8 | fileHeader[1]);
  }

  /**
   * If we correctly read a BMP header, return the dataOffset value (the number of bytes before the actual pixels
   * are stored)
   *
   * @return pixel offset
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

  public void writeHeader(BufferedOutputStream bos) throws IOException {
    bos.write(fileHeader);
    bos.write(infoHeader);
  }

  public int getImageWidth() {
    if (fileHeader.length != 14) {
      System.err.println("[e]: Error, try to access dataOffset value before reading it");
      return -1;
    }

    return ((infoHeader[4 + 3] & 0xFF) << 24)
        | ((infoHeader[4 + 2] & 0xFF) << 16)
        | ((infoHeader[4 + 1] & 0xFF) << 8)
        | (infoHeader[4] & 0xFF);
  }
}
