package ch.heigvd.dai.utils;

import ch.heigvd.dai.models.ImageRatio;
import picocli.CommandLine;

public class ImageRatioConverter implements CommandLine.ITypeConverter<ImageRatio> {
  @Override
  public ImageRatio convert(String value) throws Exception {
    String[] parts = value.split("/");
    if (parts.length != 2) {
      throw new CommandLine.TypeConversionException(
          "Invalid format. Use WIDTH/HEIGHT, for example 16/9.");
    }

    try {
      int width = Integer.parseInt(parts[0]);
      int height = Integer.parseInt(parts[1]);
      return new ImageRatio(width, height);
    } catch (NumberFormatException e) {
      throw new CommandLine.TypeConversionException("Values WIDTH and HEIGHT must be integers.");
    }
  }
}
