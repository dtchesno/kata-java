package com.dtchesno.kata.ivan;

// add methods to class Picture
// add test methods to class PictureTester
public class A7 {

    public void mirrorSnowman()
    {
        Pixel topPixel = null;
        Pixel bottomPixel = null;
        int count = 0;
        Pixel[][] pixels = this.getPixels2D();

        int mirrorPoint = 190;
        for (int row = 158; row < 190; row++)
        {
            for (int col = 106; col < 170; col++)
            {
                topPixel = pixels[row][col];
                bottomPixel = pixels[mirrorPoint + (mirrorPoint - row)][col];
                bottomPixel.setColor(topPixel.getColor());

                count++;
            }
        }

        mirrorPoint = 196;
        for (int row = 170; row < 196; row++)
        {
            for (int col = 239; col < 293; col++)
            {
                topPixel = pixels[row][col];
                bottomPixel = pixels[mirrorPoint + (mirrorPoint - row)][col];
                bottomPixel.setColor(topPixel.getColor());

                count++;
            }
        }

        System.out.println("total " + count);;
    }

    public static void testMirrowSnowman()
    {
        Picture snowman = new Picture("snowman.jpg");
        snowman.explore();
        snowman.mirrorSnowman();
        snowman.explore();
    }


    public void mirrorGull()
    {
        int mirrorPoint = 350;
        Pixel leftPixel = null;
        Pixel rightPixel = null;
        int count = 0;
        Pixel[][] pixels = this.getPixels2D();

        for (int row = 196; row < 320; row++)
        {
            for (int col = 230; col < 345; col++)
            {
                leftPixel = pixels[row][col];
                rightPixel = pixels[row][mirrorPoint + (mirrorPoint - col)];
                rightPixel.setColor(leftPixel.getColor());

                count++;
            }
        }

        System.out.println("total " + count);;
    }

    public static void testMirrowGull()
    {
        Picture seagull = new Picture("seagull.jpg");
        seagull.explore();
        seagull.mirrorGull();
        seagull.explore();
    }
}
