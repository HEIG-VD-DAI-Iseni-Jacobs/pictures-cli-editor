package ch.heigvd.dai.models;

/** Used to manipulate image ratios like 9/16 */
public class ImageRatio {
  private final int widthRatio;
  private final int heightRatio;

  public ImageRatio(int widthRatio, int heightRatio) {
    if (widthRatio <= 0 || heightRatio <= 0) {
      throw new IllegalArgumentException("Ratio values must be positive.");
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
