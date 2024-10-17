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

    for (int y = 0; y < imagePixels.length; y++) {
      for (int x = 0; x < imagePixels[0].length; x++) {
        computeAverageColor(imagePixels[y][x], originalPixels, x, y);
      }
    }

    image.writeImage();

    return 0;
  }

  private void computeAverageColor(Pixel pixel, Pixel[][] originalPixels, int x, int y) {
    int counter = 0;
    int xoff, yoff;

    for (int i = -7; i <= 7; i++) { // TODO : Arbitrary radius value
      for (int j = -7; j <= 7; j++) {
        xoff = x + i;
        yoff = y + j;

        if ((xoff >= 0 && xoff < originalPixels[0].length && xoff != x)
            && (yoff >= 0 && yoff < originalPixels.length && yoff != y)) {
          pixel.add(originalPixels[yoff][xoff]); // Add all surrounding neighbors
          counter++;
        }
      }
    }

    pixel.div(counter);
  }
}
