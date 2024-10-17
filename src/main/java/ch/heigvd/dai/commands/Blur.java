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

  // Optional parameters for defining a rectangle to blur
  @CommandLine.Option(
      names = {"--x1"},
      description = "The x and y coordinates of the two corners of the")
  private Integer x1;

  @CommandLine.Option(
      names = {"--x2"},
      description = "rectangle. The (0,0) point is located at the top")
  private Integer x2;

  @CommandLine.Option(
      names = {"--y1"},
      description = "left of the picture. You must provide all or")
  private Integer y1;

  @CommandLine.Option(
      names = {"--y2"},
      description = "none of these 4 options. Happy blurring!")
  private Integer y2;

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
    BMPImage image = new BMPImage(parent.getInputPath(), parent.getOutputPath());
    image.readImage();

    int imageWidth = image.getWidth();
    int imageHeight = image.getHeight();

    // Validate rectangle parameters
    if ((x1 != null || y1 != null || x2 != null || y2 != null)
        && (x1 == null || y1 == null || x2 == null || y2 == null)) {
      throw new CommandLine.ParameterException(
          spec.commandLine(),
          "All rectangle parameters (x1, y1, x2, y2) must be provided together");
    }

    // Ensure the coordinates form a valid rectangle and are within image bounds
    if (x1 != null) {
      if (x1 >= x2 || y1 >= y2) {
        throw new CommandLine.ParameterException(
            spec.commandLine(),
            "Invalid rectangle coordinates: x1 must be less than x2 and y1 must be less than y2");
      }
      if (x1 < 0 || x2 > imageWidth || y1 < 0 || y2 > imageHeight) {
        throw new CommandLine.ParameterException(
            spec.commandLine(), "Rectangle coordinates must be within the image bounds");
      }
    }

    System.out.println(
        "Blurring " + parent.getInputPath() + " to a picture located at " + parent.getOutputPath());

    // Copy the pixels to operate safely on them in the image
    Pixel[][] imagePixels = image.getImage();
    Pixel[][] originalPixels = new Pixel[imageWidth][imageHeight];
    for (int i = 0; i < imageWidth; i++) {
      originalPixels[i] = imagePixels[i].clone();
    }

    // Determine the area to blur
    int startX = (x1 != null) ? x1 : 0;
    int startY = (y1 != null) ? y1 : 0;
    int endX = (x2 != null) ? x2 : imageWidth;
    int endY = (y2 != null) ? y2 : imageHeight;

    // For each pixel in the specified rectangle, compute its neighbors average and set its value to
    // it
    for (int x = startX; x < endX; x++) {
      for (int y = startY; y < endY; y++) {
        imagePixels[x][y] = computeAverageColor(originalPixels, x, y);
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

        if ((xoff >= 0 && xoff < originalPixels.length)
            && (yoff >= 0 && yoff < originalPixels[0].length)) {
          sum.add(originalPixels[xoff][yoff]); // Add all surrounding neighbors
          counter++;
        }
      }
    }

    return sum.div(counter);
  }
}
