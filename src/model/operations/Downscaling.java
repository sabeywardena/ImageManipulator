package model.operations;

import model.Pixel;
import model.RGBChannel;

/**
 * Represents a class to downsize an image from it's original size to a smaller one.
 */
public class Downscaling extends AEdits {

    int sWidth;
    int sHeight;


    /**
     * Constructs an instance of the Filter class given a 2D matrix of pixels.
     * @param matrix the matrix of pixels to edit.
     * @throws IllegalArgumentException if matrix of pixels is null
     */
     public Downscaling(Pixel[][] matrix, int sWidth, int sHeight)throws IllegalArgumentException {
        super(matrix);
        this.sWidth = sWidth;
        this.sHeight = sHeight;
    }

    @Override
    public Pixel[][] operation() {
        Pixel[][] smaller = new Pixel[sHeight][sWidth];
        for (int i = 0; i < sWidth; i ++) {
            for (int j = 0; j < sHeight; j++) {
                double x = i / (double)sWidth * matrix[0].length;
                double y = j / (double)sHeight * matrix.length;
                int xceil = (int)Math.ceil(x);
                int yceil = (int)Math.ceil(y);
                int xfloor = (int)Math.floor(x);
                int yfloor = (int)Math.floor(y);
                Pixel[] p = {matrix[yceil][xceil], matrix[yceil][xfloor], matrix[yfloor][xceil],
                        matrix[yfloor][xfloor]};
                smaller[j][i] = avgColor(p);
            }
        }
        return smaller;
    }

    private Pixel avgColor(Pixel[] p) {
         int redSum = 0;
         int greenSum = 0;
         int blueSum = 0;
         for (Pixel a: p) {
             redSum += a.getChannel(RGBChannel.RED);
             greenSum += a.getChannel(RGBChannel.GREEN);
             blueSum += a.getChannel(RGBChannel.BLUE);
         }
         return new Pixel((int)Math.round(redSum / 4.0), (int)Math.round(greenSum / 4.0),
                 (int)Math.round(blueSum / 4.0));
    }
}
