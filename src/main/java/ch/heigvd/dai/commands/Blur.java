package ch.heigvd.dai.commands;

import ch.heigvd.dai.models.BMPImage;
import ch.heigvd.dai.models.Pixel;
import java.util.concurrent.Callable;
import picocli.CommandLine;

@CommandLine.Command(name = "blur", description = "Blur a picture or part of it")
public class Blur implements Callable<Integer> {
  @CommandLine.ParentCommand protected Root parent;

  @CommandLine.Spec private CommandLine.Model.CommandSpec spec;

  // Private field to store the blur radius
  private int radius;

  /** Sets the radius of the blur effect. Default is 5 */
  @CommandLine.Option(
      names = {"-r", "--radius"},
      description = "The radius of the blur, from 1 to 10 (Default: 5)",
      defaultValue = "5")
  public void setRadius(int radius) {
    // Validate that the radius is within the acceptable range
    if (radius < 1 || radius > 10) {
      throw new CommandLine.ParameterException(
          spec.commandLine(), "Radius must be between 1 and 10");
    }
    this.radius = radius;
  }

  @Override
  public Integer call() {
    System.out.println(
        "Blurring " + parent.getInputPath() + " to a picture located at " + parent.getOutputPath());

    BMPImage image = new BMPImage(parent.getInputPath(), parent.getOutputPath());
    image.readImage();

    // Copy the pixels to operate safely on them in the image
    Pixel[][] imagePixels = image.getImage();
    Pixel[][] originalPixels = new Pixel[image.getWidth()][image.getHeight()];
    for (int i = 0; i < image.getWidth(); i++) {
      originalPixels[i] = imagePixels[i].clone();
    }

    // For each pixel, compute its neighbors average and set its value to it
    for (int y = 0; y < image.getWidth(); y++) {
      for (int x = 0; x < image.getHeight(); x++) {
        imagePixels[y][x] = computeAverageColor(originalPixels, x, y);
      }
    }

    image.writeImage();

    return 0;
  }

  /**
   * Computes the average color of the neighbors of a pixel given a radius.
   *
   * @param originalPixels the original pixels of the image
   * @param x the x coordinate of the pixel
   * @param y the y coordinate of the pixel
   */
  private Pixel computeAverageColor(Pixel[][] originalPixels, int x, int y) {
    int counter = 0;
    int xoff, yoff;

    Pixel sum = new Pixel(0, 0, 0);

    for (int i = -radius; i <= radius; i++) {
      for (int j = -radius; j <= radius; j++) {
        xoff = x + i;
        yoff = y + j;

        if ((xoff >= 0 && xoff < originalPixels[0].length)
            && (yoff >= 0 && yoff < originalPixels.length)) {
          sum.add(originalPixels[yoff][xoff]); // Add all surrounding neighbors
          counter++;
        }
      }
    }

    return sum.div(counter);
  }
}
