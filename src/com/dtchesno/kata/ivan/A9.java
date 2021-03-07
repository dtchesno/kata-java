package com.dtchesno.kata.ivan;

import java.awt.*;

public class A9 {

    public void edgeDetectionRightBelow(int edgeDist)
    {
        Pixel pixel = null;
        Pixel leftPixel = null;
        Pixel rightPixel = null;
        Pixel topPixel = null;
        Pixel belowPixel = null;
        Pixel[][] pixels = this.getPixels2D();
        Color rightColor = null;
        Color belowColor = null;
        int[][] color = new int[pixels.length][pixels[0].length];

        for (int row = 0; row < pixels.length; row++)
        {
            for (int col = 0; col < pixels[0].length - 1; col++)
            {
                leftPixel = pixels[row][col];
                rightPixel = pixels[row][col + 1];
                rightColor = rightPixel.getColor();
                if (leftPixel.colorDistance(rightColor) > edgeDist)
                    color[row][col] = 1;
                else
                    color[row][col] = 0;
            }
        }

        for (int row = 0; row < pixels.length - 1; row++)
        {
            for (int col = 0; col < pixels[0].length; col++)
            {
                topPixel = pixels[row][col];
                belowPixel = pixels[row + 1][col];
                belowColor = belowPixel.getColor();
                if (topPixel.colorDistance(belowColor) > edgeDist)
                    color[row][col] = 1;
                else
                    color[row][col] = 0;
            }
        }

        for (int row = 0; row < pixels.length; row++)
        {
            for (int col = 0; col < pixels[0].length; col++)
            {
                pixel = pixels[row][col];
                if (color[row][col] == 1)
                     pixel.setColor(Color.BLACK);
                else
                    pixel.setColor(Color.WHITE);
            }
        }
    }

    public static void testEdgeRightBelow()
    {
        Picture swan = new Picture("swan.jpg");
        swan.edgeDetectionRightBelow(10);
        swan.explore();
    }




    public void edgeDetectionRightBelow2(int edgeDist)
    {
        Pixel pixel = null;
        Pixel rightPixel = null;
        Pixel belowPixel = null;
        Pixel[][] pixels = this.getPixels2D();
        Color rightColor = null;
        Color belowColor = null;
        int height = pixels.length;
        int width = pixels[0].length;

        for (int row = 0; row < pixels.length - 1; row++)
        {
            for (int col = 0; col < pixels[0].length - 1; col++)
            {
                pixel = pixels[row][col];
                rightPixel = pixels[row][col + 1];
                rightColor = rightPixel.getColor();
                belowPixel = pixels[row + 1][col];
                belowColor = belowPixel.getColor();
                if (pixel.colorDistance(rightColor) > edgeDist || pixel.colorDistance(belowColor) > edgeDist)
                    pixel.setColor(Color.BLACK);
                else
                    pixel.setColor(Color.WHITE);
            }
        }


        for (int row = 0; row < pixels.length - 1; row++)
        {
            pixel = pixels[row][width - 1];
            belowPixel = pixels[row + 1][width - 1];
            if (pixel.colorDistance(belowColor) > edgeDist)
                pixel.setColor(Color.BLACK);
            else
                pixel.setColor(Color.WHITE);
        }

        for (int col = 0; col < width - 1; col++)
        {
            pixel = pixels[height - 1][col];
            rightPixel = pixels[height - 1][col + 1];
            if (pixel.colorDistance(rightPixel) > edgeDist)
                pixel.setColor(Color.BLACK);
            else
                pixel.setColor(Color.WHITE);
        }
    }

    public static void testEdgeRightBelow2()
    {
        Picture swan = new Picture("swan.jpg");
        swan.edgeDetectionRightBelow2(10);
        swan.explore();
    }
}
