package com.dtchesno.kata.ivan;

// add methods to class Picture
// add test methods to class PictureTester
public class A8 {
    public void copy(Picture fromPic, int startRow, int startCol,
                     int fromStartRow, int fromStartCol, int fromEndRow, int fromEndCol)
    {
        Pixel fromPixel = null;
        Pixel toPixel = null;
        Pixel[][] toPixels = this.getPixels2D();
        Pixel[][] fromPixels = fromPic.getPixels2D();
        for (int fromRow = fromStartRow, toRow = startRow;
             fromRow <= fromEndRow && toRow < toPixels.length;
             fromRow++, toRow++)
        {
            for (int fromCol = fromStartCol, toCol = startCol;
                 fromCol <= fromEndCol && toCol < toPixels[0].length;
                 fromCol++, toCol++)
            {
                fromPixel = fromPixels[fromRow][fromCol];
                toPixel = toPixels[toRow][toCol];
                toPixel.setColor(fromPixel.getColor());
            }
        }
    }

    public void testCopy()
    {
        Picture flower1 = new Picture("flower1.jpg");
        Picture flower2 = new Picture("flower2.jpg");

        this.copy(flower1,0,0, 0, 0, 99, 49);
        this.copy(flower2,0,50, 0, 50, 99, 99);

        this.copy(flower1,100,0, 0, 0, 49, 99);
        this.copy(flower2,150,0, 50, 0, 99, 99);
    }
}
