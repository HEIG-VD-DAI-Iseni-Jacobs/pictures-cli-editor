package ch.heigvd.dai;

import ch.heigvd.dai.functions.BlackAndWhite;
import ch.heigvd.dai.models.BMPImage;

public class Main {
  public static void main(String[] args) {
    BMPImage a = new BMPImage(args[0], args[1]);
    a.readImage();
    BlackAndWhite blackAndWhite = new BlackAndWhite();
    blackAndWhite.toGrey(a);
    a.writeImage();
  }
}
