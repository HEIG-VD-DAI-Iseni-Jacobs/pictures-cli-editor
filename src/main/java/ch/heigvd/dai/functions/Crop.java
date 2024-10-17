package ch.heigvd.dai.functions;

import ch.heigvd.dai.models.BMPImage;
import ch.heigvd.dai.models.BMPHeader;

public class Crop {
    public void cropBmpImage(int witdh, int height, BMPImage imageSrc) throws CloneNotSupportedException {
        // Create a new BMPImage
        BMPImage cropedImage; // new image avec les paths retournés dans command
        // Write the header
        BMPHeader headerSrc = imageSrc.getHeader();
        BMPHeader headerDst = (BMPHeader) headerSrc.clone();
        // Write the croped image
        int newValue;
        // Check if we need to crop in width or height
        if (witdh / height > imageSrc.getWidth() / imageSrc.getHeight()) {
            // crop height
            newValue = imageSrc.getHeight() * witdh / height;
            // calculate deltaHeight
            int deltaHeight = (imageSrc.getHeight() - newValue)/2;
            //create croped image
            for (int i = 0; i < witdh; i++) {
                for (int j = 0; j < height; j++) {

                }
            }
        } else {
            // crop width
            newValue = imageSrc.getWidth() * height / witdh;
        }
    }

    public void cropImage(int witdh, int height, BMPImage imageSrc) {
        for (int i = 0; i < witdh; i++) {
            for (int j = 0; j < height; j++) {

            }
        }
    }
}
