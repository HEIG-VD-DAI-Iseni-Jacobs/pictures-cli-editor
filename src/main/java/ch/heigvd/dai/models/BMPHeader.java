package ch.heigvd.dai.models;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class BMPHeader {
    /** Size of the fixed header */
    public static final int HEADER_LENGTH = 14;
    /** Index of the value "data offset" in a BMP header */
    public static final int DATA_OFFSET_INDEX = 5;

    /** The BITMAPFILEHEADER, a fixed-size header for BMP */
    private byte[] fileHeader = new byte[14];
    /** The BITMAPINFOHEADER, a variable-size header coming after the BITMAPFILEHEADER */
    private byte[] infoHeader;

    /**
     * Read a BMP header : both the fixed header and
     * @param inputPath the path of the image file to load
     */
    public void readHeader(String inputPath) {
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(inputPath))) {
            byte b;

            // Read the fixed header
            int i = 0;
            bis.read(fileHeader, 0, 14);
            while ((b = (byte) bis.read()) != -1 && i < HEADER_LENGTH) {
                fileHeader[i++] = b;
            }

            // Read the rest of the metadata
            int dataOffset = getDataOffset();
            while ((b = (byte) bis.read()) != -1 && i < dataOffset) {
                infoHeader.add(b);
            }
        } catch (IOException e) {
            System.err.println("[e]: Error reading image: '" + e.getMessage() + "'");
        }
    }

    public void writeHeader(String outputPath) {
        // TODO : write fixed header and variable header
    }

    private int getDataOffset() {
        if (fileHeader.length != 14) {
            System.err.println("[e]: Error, try to access dataOffset value before reading it");
            return -1;
        }

        return fileHeader[DATA_OFFSET_INDEX];
    }
}
