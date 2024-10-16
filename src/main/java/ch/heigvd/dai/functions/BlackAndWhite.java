package ch.heigvd.dai.functions;

import ch.heigvd.dai.models.BMPImage;
import ch.heigvd.dai.models.Pixel;
import java.awt.*;

// @CommandLine.Command(name = "grey", description = "Modify a picture to black and white")
public class BlackAndWhite {
  //    @CommandLine.ParentCommand protected Root parent;

  // Grey formula -> https://chatgpt.com/share/670f8a88-59c4-800a-bda0-a135bed2a099
  public void toGrey(BMPImage bmpImage) {
    //        BMPImage bmpImage = new BMPImage(parent.getSrc, parent.getSink);

    Pixel[][] pixels = bmpImage.getImage();
    int red;
    int green;
    int blue;
    int grey;
    for (int i = 0; i < pixels.length; ++i) {
      for (int j = 0; j < pixels[0].length; ++j) {
        red = pixels[i][j].getRed();
        green = pixels[i][j].getGreen();
        blue = pixels[i][j].getBlue();
        grey = (int) (0.299 * red + 0.587 * green + 0.114 * blue);
        pixels[i][j].setRed(grey);
        pixels[i][j].setGreen(grey);
        pixels[i][j].setBlue(grey);
      }
    }
  }
}
