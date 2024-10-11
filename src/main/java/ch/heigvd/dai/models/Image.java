package ch.heigvd.dai.models;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

public class Image {
    private BufferedImage image;
    private String format;
    private String input_path;
    private String output_path;

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
