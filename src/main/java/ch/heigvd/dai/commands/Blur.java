package ch.heigvd.dai.commands;

import ch.heigvd.dai.models.BMPImage;
import ch.heigvd.dai.models.Pixel;
import java.util.concurrent.Callable;
import picocli.CommandLine;

@CommandLine.Command(name = "blur", description = "Blur a picture or part of it")
public class Blur implements Callable<Integer> {
  @CommandLine.ParentCommand protected Root parent;

  @Override
  public Integer call() {
    System.out.println(
        "Blurring " + parent.getInputPath() + " to a picture located at " + parent.getOutputPath());

    BMPImage image = new BMPImage(parent.getInputPath(), parent.getOutputPath());
    image.readImage();

    // Copy the pixels to operate safely on them in the image
    Pixel[][] imagePixels = image.getImage();
    Pixel[][] originalPixels = new Pixel[imagePixels.length][imagePixels[0].length];
    // TODO : replace above with image.getWidth when merged with Aladin's PR
    for (int i = 0; i < imagePixels.length; i++) {
      originalPixels[i] = imagePixels[i].clone();
    }

    // For each pixel, compute its neighbors average and set its value to it
    for (int y = 0; y < imagePixels.length; y++) {
      for (int x = 0; x < imagePixels[0].length; x++) {
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

    for (int i = -7; i <= 7; i++) { // TODO : Arbitrary radius value
      for (int j = -7; j <= 7; j++) {
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
