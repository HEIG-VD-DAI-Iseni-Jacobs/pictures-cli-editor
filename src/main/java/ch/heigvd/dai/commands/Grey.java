package ch.heigvd.dai.commands;

import ch.heigvd.dai.models.BMPImage;
import ch.heigvd.dai.models.Pixel;
import java.util.concurrent.Callable;
import picocli.CommandLine;

@CommandLine.Command(name = "grey", description = "Convert a picture to black and white")
public class Grey implements Callable<Integer> {
  @CommandLine.ParentCommand protected Root parent;

  @Override
  public Integer call() {

    System.out.println(
        "Converting "
            + parent.getInputPath()
            + " to a black and white picture located at "
            + parent.getOutputPath());

    BMPImage image = new BMPImage(parent.getInputPath(), parent.getOutputPath());
    image.readImage();
    Pixel[][] pixels = image.getImage();
    /**
     * Turns each pixel to grey by applying the same coefficient on each color. Formula:
     * https://chatgpt.com/share/670f8a88-59c4-800a-bda0-a135bed2a099
     */
    int grey;
    for (Pixel[] pixel_line : pixels) {
      for (Pixel pixel : pixel_line) {
        grey = (int) (0.299 * pixel.getRed() + 0.587 * pixel.getGreen() + 0.114 * pixel.getBlue());
        pixel.setRed(grey);
        pixel.setGreen(grey);
        pixel.setBlue(grey);
      }
    }
    image.writeImage();

    return 0;
  }
}
