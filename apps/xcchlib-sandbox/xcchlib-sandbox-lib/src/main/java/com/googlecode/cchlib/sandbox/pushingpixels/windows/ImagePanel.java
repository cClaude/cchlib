package com.googlecode.cchlib.sandbox.pushingpixels.windows;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.Window;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class ImagePanel extends JPanel
{
    private static final long serialVersionUID = 1L;

    private final class MouseHandler implements MouseListener, MouseMotionListener
    {
        private int pressX;
        private int pressY;

        @Override
        public void mousePressed(final MouseEvent e)
        {
            Point dragWindowOffset = e.getPoint();
            final Component source = (Component) e.getSource();

            final Window w = SwingUtilities.getWindowAncestor(ImagePanel.this);
            dragWindowOffset = SwingUtilities.convertPoint(source,
                    dragWindowOffset, w);

            this.pressX = dragWindowOffset.x;
            this.pressY = dragWindowOffset.y;
        }

        @Override
        public void mouseReleased(final MouseEvent e) {
            this.pressX = -1;
            this.pressY = -1;
        }

        @Override
        public void mouseMoved(final MouseEvent e) {
            // Not use
        }

        @Override
        public void mouseClicked(final MouseEvent e) {
            // Not use
        }

        @Override
        public void mouseEntered(final MouseEvent e) {
            // Not use
        }

        @Override
        public void mouseExited(final MouseEvent e) {
            // Not use
        }

        @Override
        public void mouseDragged(final MouseEvent e)
        {
            final Point loc = e.getLocationOnScreen();
            final int dx = loc.x - this.pressX;
            final int dy = loc.y - this.pressY;

            final Window w = SwingUtilities.getWindowAncestor(ImagePanel.this);
            w.setLocation(dx, dy);
        }
    }

    BufferedImage avatar;

    public ImagePanel(final BufferedImage avatar) {
        this.avatar = avatar;
        final MouseHandler mHandler = new MouseHandler();
        this.addMouseListener(mHandler);
        this.addMouseMotionListener(mHandler);
    }

    public void setAvatar(final BufferedImage avatar) {
        this.avatar = avatar;
    }

    @Override
    protected void paintComponent(final Graphics g) {
        final Graphics2D g2d = (Graphics2D) g.create();

        // code from
        // http://weblogs.java.net/blog/campbell/archive/2006/07/java_2d_tricker.html
        final int avatarWidth = this.avatar.getWidth();
        final int avatarHeight = this.avatar.getHeight();

        final GraphicsConfiguration gc = g2d.getDeviceConfiguration();
        final BufferedImage img = gc.createCompatibleImage(avatarWidth, avatarHeight,
                Transparency.TRANSLUCENT);
        final Graphics2D g2 = img.createGraphics();

        g2.setComposite(AlphaComposite.Clear);
        g2.fillRect(0, 0, avatarWidth, avatarHeight);

        g2.setComposite(AlphaComposite.Src);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.WHITE);
        g2.fillRoundRect(0, 0, avatarWidth, avatarHeight + 10, 10, 10);

        g2.setComposite(AlphaComposite.SrcAtop);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(this.avatar, 0, 0, null);
        g2.dispose();

        // at this point the 'img' contains a soft
        // clipped round rectangle with the avatar

        // do the reflection with the code from
        // http://www.jroller.com/gfx/entry/swing_glint

        final BufferedImage reflection = new BufferedImage(avatarWidth, avatarHeight,
                BufferedImage.TYPE_INT_ARGB);
        final Graphics2D reflectionGraphics = reflection.createGraphics();

        final AffineTransform tranform = AffineTransform.getScaleInstance(1.0, -1.0);
        tranform.translate(0, -avatarHeight);
        reflectionGraphics.drawImage(img, tranform, this);

        final GradientPaint painter = new GradientPaint(0.0f, 0.0f, new Color(0.0f,
                0.0f, 0.0f, 0.7f), 0.0f, avatarHeight / 2.0f, new Color(0.0f,
                0.0f, 0.0f, 1.0f));

        reflectionGraphics.setComposite(AlphaComposite.DstOut);
        reflectionGraphics.setPaint(painter);
        reflectionGraphics.fill(new Rectangle2D.Double(0, 0, avatarWidth,
                avatarHeight));

        reflectionGraphics.dispose();

        g2d.drawImage(img, 0, 0, this);
        g2d.drawImage(reflection, 0, avatarHeight, this);

        g2d.dispose();
    }
}
