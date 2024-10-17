package ch.heigvd.dai.commands;

import ch.heigvd.dai.models.BMPHeader;
import ch.heigvd.dai.models.BMPImage;
import ch.heigvd.dai.models.ImageRatio;
import ch.heigvd.dai.utils.ImageRatioConverter;
import java.util.concurrent.Callable;
import picocli.CommandLine;

@CommandLine.Command(name = "crop", description = "Crop a picture to a new format")
public class Crop implements Callable<Integer> {
  @CommandLine.ParentCommand protected Root parent;

  // Additional option for this command
  @CommandLine.Option(
      names = {"-f", "--format"},
      description = "Le format de découpage sous la forme WIDTH/HEIGHT, par exemple 16/9 ou 4/5",
      required = true,
      converter = ImageRatioConverter.class)
  private ImageRatio imageRatio;

  @Override
  public Integer call() throws CloneNotSupportedException {
    System.out.println(
        "Cropping "
            + parent.getInputPath()
            + " to a new format located at "
            + parent.getOutputPath());

    int widthRatio = imageRatio.getWidthRatio();
    int heightRatio = imageRatio.getHeightRatio();

    BMPImage image = new BMPImage(parent.getInputPath(), parent.getOutputPath());
    BMPImage desiredImage;
    image.readImage();

    // Write the header
    BMPHeader originalHeader = image.getHeader();
    BMPHeader desiredHeader = originalHeader.clone();

    double originalRatio = (double) image.getWidth() / image.getHeight();
    double desiredRatio = (double) widthRatio / heightRatio;

    // Check if we need to crop in width or height
    if (desiredRatio > originalRatio) {
      // crop height
      int newHeight = (int) (image.getWidth() / desiredRatio);
      desiredHeader.setImageHeight(newHeight);
      desiredImage = new BMPImage(parent.getInputPath(), parent.getOutputPath(), desiredHeader);
      int deltaHeight = (image.getHeight() - newHeight) / 2;
      // create croped image
      for (int i = 0; i < image.getWidth(); i++) {
        for (int j = 0; j < newHeight; j++) {
          desiredImage.setPixel(i, j, image.getPixel(i, j + deltaHeight));
        }
      }
    } else {
      // crop width
      int newWidth = (int) (image.getHeight() * desiredRatio);
      System.out.println("newWidth: " + newWidth);
      desiredHeader.setImageWidth(newWidth);
      System.out.println("desiredHeader width: " + desiredHeader.getImageWidth());
      desiredImage = new BMPImage(parent.getInputPath(), parent.getOutputPath(), desiredHeader);
      int deltaWidth = (image.getWidth() - newWidth) / 2;
      System.out.println("deltaWidth: " + deltaWidth);
      for (int i = 0; i < newWidth; i++) {
        for (int j = 0; j < image.getHeight(); j++) {
          desiredImage.setPixel(i, j, image.getPixel(i + deltaWidth, j));
        }
      }
    }
    desiredImage.writeImage();

    return 0;
  }
}
