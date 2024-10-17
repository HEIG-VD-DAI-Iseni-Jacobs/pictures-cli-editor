package ch.heigvd.dai.commands;

import ch.heigvd.dai.models.BMPImage;
import picocli.CommandLine;

import java.util.concurrent.Callable;

@CommandLine.Command(name = "grey", description = "Convert a picture to black and white")
public class Grey implements Callable<Integer> {
  @CommandLine.ParentCommand
  protected Root parent;

  @Override
  public Integer call() {

    System.out.println(
        "Converting "
            + parent.getInputPath()
            + " to a black and white picture located at "
            + parent.getOutputPath());

    BMPImage image = new BMPImage(parent.getInputPath(), parent.getOutputPath());
    image.readImage();
    // TODO
    
    return 0;
  }
}
