package com.googlecode.cchlib.util.media;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 *
 */
public final class Images
{
    private Images()
    {
    }

    public static void saveTo( final ImageIcon icon, final File file, final int imageType ) throws IOException
    {
        Image img = icon.getImage();

        saveTo( img, file, imageType );
    }

    public static void saveTo( final Image img, final File file, final int imageType ) throws IOException
    {
        BufferedImage bi = new BufferedImage( img.getWidth(null), img.getHeight(null), imageType );

        Graphics2D g2 = bi.createGraphics();
        g2.drawImage(img, 0, 0, null);
        g2.dispose();
        ImageIO.write( bi, "jpg", file );
    }

}
