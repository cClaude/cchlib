package alpha.bricolage201202;

import java.awt.Color;
import java.awt.Component;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;

public class Node extends Component
{
    private static final long serialVersionUID = 1L;

    private final Color nodeColor1 = new Color(0, 186, 185);
    private final Color nodeColor2 = new Color(249, 196, 106);

    private int nodeCircle;
    private double nodeX;
    private double nodeY;
    private double nodeDX;
    private double nodeDY;
    private double nodePX;
    private double nodePY;
    private boolean nodeFixed;
    private boolean nodePFixed;
    private boolean nodeJumped;
    private String nodeAction;
    private String nodeLabel;
    private Image nodeImage;

    public Node(
        final int    circel,
        final String label,
        final Image  image
        )
    {
        this.nodeX = 10.0D + (380.0D * random());
        this.nodeY = 10.0D + (380.0D * random());

        this.nodeCircle = circel;
        this.nodeJumped = false;
        this.nodeImage  = image;
        this.nodeLabel  = label;
    }


    private double random()
    {
        return Math.random();
    }

    public synchronized void paint(final Graphics g, final FontMetrics fm)
    {
        final Node n = this;
        final int x = (int) n.nodeX;
        final int y = (int) n.nodeY;

        final int w = fm.stringWidth(n.nodeLabel) + 18;
        final int h = fm.getHeight();

        setSize(w, h);
        n.nodeCircle += 10;

        if (n.nodeCircle > 360) {
            n.nodeCircle = 0;
            }

        if (n.nodeJumped) {
            g.setColor(this.nodeColor2);
            g.fillArc(x - (w / 2) - 4, y - (h / 2) - 4, w + 4, h + 14, 0, 360);
            g.setColor(this.nodeColor1);
            g.fillArc(x - (w / 2) - 4, y - (h / 2) - 4, w + 4, h + 14, n.nodeCircle, 30);
            }
        else {
            g.setColor(this.nodeColor1);
            g.fillArc(x - (w / 2) - 4, y - (h / 2) - 4, w + 4, h + 14, 0, 360);
            g.setColor(this.nodeColor2);
            g.fillArc(x - (w / 2) - 4, y - (h / 2) - 4, w + 4, h + 14, n.nodeCircle, 30);
        }
        g.setColor(Color.blue);

        g.setColor(Color.black);

        g.setColor(Color.blue);
        g.drawString(n.nodeLabel, x - ((w - 11) / 2), (y - (h / 2)) + fm.getAscent() + 4);
    }

    public String getNodeAction() {
        return this.nodeAction;
    }

    public void setNodeAction( final String action )
    {
        this.nodeAction = action;
    }

    public int getNodeCircle() {
        return this.nodeCircle;
    }

    public void setNodeCircle(final int circle) {
        this.nodeCircle = circle;
    }

    public double getNodeDX() {
        return this.nodeDX;
    }

    public void setNodeDX(final double dx) {
        this.nodeDX = dx;
    }

    public double getNodeDY() {
        return this.nodeDY;
    }

    public void setNodeDY(final double dy) {
        this.nodeDY = dy;
    }

    public double getNodeX() {
        return this.nodeX;
    }

    public double setNodeX(final double x) {
        this.nodeX = x;
        return x;
    }

    public double getNodeY() {
        return this.nodeY;
    }

    public double setNodeY(final double y) {
        this.nodeY = y;
        return y;
    }

    public boolean isNodeFixed() {
        return this.nodeFixed;
    }

    public void setNodeFixed(final boolean fixed) {
        this.nodeFixed = fixed;
    }

    public Image getNodeImage() {
        return this.nodeImage;
    }

    public void setNodeImage(final Image image) {
        this.nodeImage = image;
    }

    public boolean isNodeJumped() {
        return this.nodeJumped;
    }

    public void setNodeJumped(final boolean jumped) {
        this.nodeJumped = jumped;
    }

    public String getNodeLabel() {
        return this.nodeLabel;
    }

    public void setNodeLabel(final String label) {
        this.nodeLabel = label;
    }

    public boolean isNodePFixed() {
        return this.nodePFixed;
    }

    public boolean setNodePFixed(final boolean pfixed) {
        this.nodePFixed = pfixed;
        return pfixed;
    }

    public double getNodePX() {
        return this.nodePX;
    }

    public void setNodePX(final double px) {
        this.nodePX = px;
    }

    public double getNodePY() {
        return this.nodePY;
    }

    public void setNodePY(final double py) {
        this.nodePY = py;
    }
}
