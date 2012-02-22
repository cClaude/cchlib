package bricolage201202;

import java.awt.Color;
import java.awt.Component;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;

public class Node extends Component
{
    private static final long serialVersionUID = 1L;

    int circel;
    double x;
    double y;
    double dx;
    double dy;
    double px;
    double py;
    boolean fixed;
    boolean pfixed;
    boolean jumped;
    String action;
    GraphPanel parent;
    String lbl;
    Image image;
    final Color nodeColor1 = new Color(0, 186, 185);
    final Color nodeColor2 = new Color(249, 196, 106);

    public synchronized void paint(Graphics g, FontMetrics fm) {
        Node n = this;
        int x = (int) n.x;
        int y = (int) n.y;

        int w = fm.stringWidth(n.lbl) + 18;
        int h = fm.getHeight();
        setSize(w, h);
        n.circel += 10;
        if (n.circel > 360)
            n.circel = 0;
        if (n.jumped) {
            g.setColor(this.nodeColor2);
            g.fillArc(x - w / 2 - 4, y - h / 2 - 4, w + 4, h + 14, 0, 360);
            g.setColor(this.nodeColor1);
            g.fillArc(x - w / 2 - 4, y - h / 2 - 4, w + 4, h + 14, n.circel, 30);
        } else {
            g.setColor(this.nodeColor1);
            g.fillArc(x - w / 2 - 4, y - h / 2 - 4, w + 4, h + 14, 0, 360);
            g.setColor(this.nodeColor2);
            g.fillArc(x - w / 2 - 4, y - h / 2 - 4, w + 4, h + 14, n.circel, 30);
        }
        g.setColor(Color.blue);

        g.setColor(Color.black);

        g.setColor(Color.blue);
        g.drawString(n.lbl, x - (w - 11) / 2, y - h / 2 + fm.getAscent() + 4);
    }
}
