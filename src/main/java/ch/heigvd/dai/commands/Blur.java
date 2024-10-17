package ch.heigvd.dai.commands;

import ch.heigvd.dai.models.BMPImage;
import picocli.CommandLine;

import java.util.concurrent.Callable;

@CommandLine.Command(name = "blur", description = "Blur a picture or part of it")
public class Blur implements Callable<Integer> {
  @CommandLine.ParentCommand
  protected Root parent;

  @Override
  public Integer call() {
    System.out.println(
        "Blurring "
            + parent.getInputPath()
            + " to a picture located at "
            + parent.getOutputPath());

    BMPImage image = new BMPImage(parent.getInputPath(), parent.getOutputPath());
    image.readImage();
    // TODO

    return 0;
  }
}
