package ch.heigvd.dai.models;

/** Defines a pixel with 3 color components */
public class Pixel {
  private int red;
  private int green;
  private int blue;

  public static void main(String[] args) {
    Pixel pixel = new Pixel(0, 0, 0);
    pixel.add(new Pixel(1500, 2, 1));
    pixel.div(2);
    System.out.println(pixel.getRed());
    System.out.println(pixel.getGreen());
    System.out.println(pixel.getBlue());
  }

  public Pixel(int red, int green, int blue) {
    this.red = red;
    this.green = green;
    this.blue = blue;
  }

  public int getRed() {
    return red;
  }

  public void setRed(int red) {
    this.red = red;
  }

  public int getGreen() {
    return green;
  }

  public void setGreen(int green) {
    this.green = green;
  }

  public int getBlue() {
    return blue;
  }

  public void setBlue(int blue) {
    this.blue = blue;
  }

  /**
   * Adds the color components of another pixel to the current pixel.
   *
   * @param other the pixel whose color components are to be added to the current pixel
   */
  public void add(Pixel other) {
    this.red += other.red;
    this.blue += other.blue;
    this.green += other.green;
  }

  /**
   * Divides the pixel's color components by a given value.
   *
   * @param val the value to divide the pixel's color components by
   */
  public void div(int val) {
    this.red /= val;
    this.green /= val;
    this.blue /= val;
  }
}
