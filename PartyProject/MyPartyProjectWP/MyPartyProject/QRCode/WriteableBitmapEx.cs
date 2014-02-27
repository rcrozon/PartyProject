using System;
using System.Net;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Documents;
using System.Windows.Ink;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Animation;
using System.Windows.Shapes;
using System.Windows.Media.Imaging;

namespace MyPartyProject.QRCode
{
    static public class WriteableBitmapEx
    {

        private const int SizeOfArgb = 4;

        public static void Clear(this WriteableBitmap bmp, Color color)
        {
            // Add one to use mul and cheap bit shift for multiplicaltion
            var a = color.A + 1;
            var col = (color.A << 24)
                     | ((byte)((color.R * a) >> 8) << 16)
                     | ((byte)((color.G * a) >> 8) << 8)
                     | ((byte)((color.B * a) >> 8));
            var pixels = bmp.Pixels;
            var w = bmp.PixelWidth;
            var h = bmp.PixelHeight;
            var len = w * SizeOfArgb;

            // Fill first line
            for (var x = 0; x < w; x++)
            {
                pixels[x] = col;
            }

            // Copy first line
            var blockHeight = 1;
            var y = 1;
            while (y < h)
            {
                Buffer.BlockCopy(pixels, 0, pixels, y * len, blockHeight * len);
                y += blockHeight;
                blockHeight = Math.Min(2 * blockHeight, h - y);
            }
        }


        public static void FillRectangle(this WriteableBitmap bmp, int x1, int y1, int x2, int y2, int color)
        {
            // Use refs for faster access (really important!) speeds up a lot!
            int w = bmp.PixelWidth;
            int h = bmp.PixelHeight;
            int[] pixels = bmp.Pixels;

            // Check boundaries
            if (x1 < 0) { x1 = 0; }
            if (y1 < 0) { y1 = 0; }
            if (x2 < 0) { x2 = 0; }
            if (y2 < 0) { y2 = 0; }
            if (x1 >= w) { x1 = w - 1; }
            if (y1 >= h) { y1 = h - 1; }
            if (x2 >= w) { x2 = w - 1; }
            if (y2 >= h) { y2 = h - 1; }


            // Fill first line
            int startY = y1 * w;
            int startYPlusX1 = startY + x1;
            int endOffset = startY + x2;
            for (int x = startYPlusX1; x < endOffset; x++)
            {
                pixels[x] = color;
            }

            // Copy first line
            int len = (x2 - x1) * SizeOfArgb;
            int srcOffsetBytes = startYPlusX1 * SizeOfArgb;
            int offset2 = y2 * w + x1;
            for (int y = startYPlusX1 + w; y < offset2; y += w)
            {
                Buffer.BlockCopy(pixels, srcOffsetBytes, pixels, y * SizeOfArgb, len);
            }
        }
    }
}
