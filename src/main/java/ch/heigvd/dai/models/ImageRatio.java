package ch.heigvd.dai.models;

public class ImageRatio {
  private int widthRatio;
  private int heightRatio;

  public ImageRatio(int widthRatio, int heightRatio) {
    if (widthRatio <= 0 || heightRatio <= 0) {
      throw new IllegalArgumentException("Les valeurs de ratio doivent être positives.");
    }
    this.widthRatio = widthRatio;
    this.heightRatio = heightRatio;
  }

  public int getWidthRatio() {
    return widthRatio;
  }

  public int getHeightRatio() {
    return heightRatio;
  }

  @Override
  public String toString() {
    return widthRatio + "/" + heightRatio;
  }
}
