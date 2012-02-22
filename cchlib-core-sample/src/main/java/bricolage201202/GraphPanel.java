package bricolage201202;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Panel;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class GraphPanel
    extends Panel
        implements Runnable
{
    private static final long serialVersionUID = 1L;

    IntuiGraph graph;
    int nnodes;
    Node[] nodes = new Node[10];
    int nedges;
    Edge[] edges = new Edge[20];
    Thread relaxer;
    boolean stress;
    boolean random;
    Node pick;
    boolean pickfixed;
    Image offscreen;
    Dimension offscreensize;
    Graphics offgraphics;
    final Color fixedColor = Color.blue;
    final Color selectColor = Color.pink;
    final Color edgeColor = Color.black;
    final Color nodeColor1 = new Color(0, 186, 185);
    final Color nodeColor2 = new Color(249, 196, 106);
    final Color stressColor = Color.darkGray;
    final Color arcColor1 = Color.black;
    final Color arcColor2 = Color.pink;
    final Color arcColor3 = Color.blue;
    private MouseListener myMouseListener;
    private MouseMotionListener myMouseMotionListener;

    GraphPanel(IntuiGraph graph)
    {
        this.graph = graph;

        addMouseListener( getMouseListener() );
    }

    int findNode(String lbl) {
        for (int i = 0; i < this.nnodes; i++) {
            if (this.nodes[i].lbl.equals(lbl)) {
                return i;
            }
        }
        return addNode(lbl);
    }

    public Node getNode(String name) {
        Node node = null;
        for (int i = 0; i < this.nnodes; i++) {
            if (this.nodes[i].lbl.equals(name)) {
                node = this.nodes[i];
                break;
            }
        }
        return node;
    }

    int addNode(String lbl) {
        try {
            Node n = new Node();
            n.x = (10.0D + 380.0D * Math.random());
            n.y = (10.0D + 380.0D * Math.random());
            n.parent = this;
            n.circel = (this.nnodes * 45);
            n.jumped = false;
            if (lbl.equals("Intuitec")) {
                n.image = this.graph.newImage(lbl);
            }
            n.lbl = lbl;
            this.nodes[this.nnodes] = n;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.nnodes++;
    }

    void addEdge(String from, String to, int len) {
        // Edge e = new Edge();
        // e.from = findNode(from);
        // e.to = findNode(to);
        // e.len = len;
        Edge e = new Edge(findNode(from), findNode(to), len);
        this.edges[(this.nedges++)] = e;
    }

    @Override
    public void run()
    {
        for(;;) {
            relax();

            if ((this.random) && (Math.random() < 0.03D)) {
                Node n = this.nodes[(int) (Math.random() * this.nnodes)];

                if (!n.fixed) {
                    n.x += n.px * Math.random() - 50.0D;
                    n.y += n.py * Math.random() - 50.0D;
                    }
                }
            try {
                Thread.sleep(100L);
                }
            catch (InterruptedException e) {
                break;
            }
        }
    }

    synchronized void relax() {
        edgesRelax();
        nodesRelax1();
        nodesRelax2(getSize());
        repaint();
    }

    private void nodesRelax2(Dimension d) {
        for (int i = 0; i < this.nnodes; i++) {
            Node n = this.nodes[i];
            if (!n.fixed) {
                if (n.px > n.x)
                    n.x += Math.abs((n.px - n.x) / 12.0D * Math.random());
                else
                    n.x -= Math.abs((n.px - n.x) / 12.0D * Math.random());
                if (n.py > n.y)
                    n.y += Math.abs((n.py - n.y) / 12.0D * Math.random());
                else
                    n.y -= Math.abs((n.py - n.y) / 12.0D * Math.random());
                if (n.x < 0.0D)
                    n.x = 1.0D;
                else if (n.x > d.width) {
                    n.x = (d.width - 10);
                }
                if (n.y < 0.0D)
                    n.y = 1.0D;
                else if (n.y > d.height) {
                    n.y = (d.height - 10);
                }

            }

            n.dx /= 2.0D;
            n.dy /= 2.0D;
        }
    }

    private void nodesRelax1() {
        for (int i = 0; i < this.nnodes; i++) {
            Node n1 = this.nodes[i];
            double dx = 0.0D;
            double dy = 0.0D;
            for (int j = 0; j < this.nnodes; j++) {
                if (i == j) {
                    continue;
                }
                Node n2 = this.nodes[j];
                double vx = n1.x - n2.x;
                double vy = n1.y - n2.y;
                double len = vx * vx + vy * vy;
                if (len == 0.0D) {
                    dx += Math.random();
                    dy += Math.random();
                } else if (len < 10000.0D) {
                    dx += vx / len;
                    dy += vy / len;
                }
            }
            double dlen = dx * dx + dy * dy;
            if (dlen > 0.0D) {
                dlen = Math.sqrt(dlen) / 2.0D;
                n1.dx += dx / dlen;
                n1.dy += dy / dlen;
            }
        }
    }

    private void edgesRelax() {
        for (int i = 0; i < this.nedges; i++) {
            Edge e = this.edges[i];
            double vx = this.nodes[e.getTo()].x - this.nodes[e.getFrom()].x;
            double vy = this.nodes[e.getTo()].y - this.nodes[e.getFrom()].y;
            double len = Math.sqrt(vx * vx + vy * vy);
            double f = (this.edges[i].getLen() - len) / (len * 3.0D);
            double dx = f * vx;
            double dy = f * vy;
            this.nodes[e.getTo()].dx += dx;
            this.nodes[e.getTo()].dy += dy;
            this.nodes[e.getFrom()].dx += -dx;
            this.nodes[e.getFrom()].dy += -dy;
        }
    }

    @Override
    public void paint(Graphics g) {
        update(g);
    }

    @Override
    public synchronized void update(Graphics g) {
        Dimension d = getSize();
        if ((this.offscreen == null) || (d.width != this.offscreensize.width)
                || (d.height != this.offscreensize.height)) {
            this.offscreen = createImage(d.width, d.height);
            this.offscreensize = d;
            this.offgraphics = this.offscreen.getGraphics();
            this.offgraphics.setFont(getFont());
        }

        this.offgraphics.setColor(Color.white);
        this.offgraphics.fillRect(0, 0, d.width, d.height);
        edgeLoop(Edge.getLev());
        nodeLoop();
        g.drawImage(this.offscreen, 0, 0, null);
    }

    private void nodeLoop() {
        FontMetrics fm = this.offgraphics.getFontMetrics();
        for (int i = 0; i < this.nnodes; i++)
            if (this.nodes[i] == this.nodes[findNode("Intuitec")])
                this.offgraphics.drawImage(this.nodes[i].image,
                        (int) this.nodes[i].x, (int) this.nodes[i].y, this);
            else
                this.nodes[i].paint(this.offgraphics, fm);
    }

    private void edgeLoop(int l) {
        for (int i = 0; i < this.nedges; i++) {
            int[] xx1 = new int[l];
            int[] xx2 = new int[l];
            int[] yy1 = new int[l];
            int[] yy2 = new int[l];

            Edge e = this.edges[i];
            Node m = this.nodes[findNode("Intuitec")];
            int y2;
            int x1;
            int y1;
            int x2;
            /* int y2; */
            if (this.nodes[e.getFrom()].lbl.equals("Intuitec")) {
                /* int */x1 = (int) this.nodes[e.getFrom()].x + 19;
                /* int */y1 = (int) this.nodes[e.getFrom()].y + 48;
                /* int */x2 = (int) this.nodes[e.getTo()].x;
                y2 = (int) this.nodes[e.getTo()].y;
            } else {
                x1 = (int) this.nodes[e.getFrom()].x;
                y1 = (int) this.nodes[e.getFrom()].y;
                x2 = (int) this.nodes[e.getTo()].x;
                y2 = (int) this.nodes[e.getTo()].y;
            }
            int x0 = (int) m.x + 19;

            int y0 = (int) m.y + 48;
            for (int j = 0; j < l; j++) {
                xx1[j] = (((l - j) * x1 + j * x0) / l);
                xx2[j] = (((l - j) * x2 + j * x0) / l);

                yy1[j] = (((l - j) * y1 + j * y0) / l);

                yy2[j] = (((l - j) * y2 + j * y0) / l);
            }

            int len = (int) Math.abs(Math.sqrt((x1 - x2) * (x1 - x2)
                    + (y1 - y2) * (y1 - y2))
                    - e.getLen());
            this.offgraphics.setColor(len < 20 ? this.arcColor2
                    : len < 10 ? this.arcColor1 : this.arcColor3);
            this.offgraphics.drawLine(x1, y1, x2, y2);
            if (!this.nodes[e.getFrom()].lbl.equals("Intuitec")) {
                for (int j = 0; j < l; j++) {
                    this.offgraphics.drawLine(xx1[j], yy1[j], xx2[j], yy2[j]);
                }
            }
            if (this.stress) {
                String lbl = String.valueOf(len);
                this.offgraphics.setColor(this.stressColor);
                this.offgraphics.drawString(lbl, x1 + (x2 - x1) / 2, y1
                        + (y2 - y1) / 2);
                this.offgraphics.setColor(this.edgeColor);
            }
        }
    }

    public void setHandCursor() {
        setCursor(Cursor.getPredefinedCursor(12));
    }

    public void setDefaultCursor() {
        setCursor(Cursor.getPredefinedCursor(0));
    }

    public synchronized void start() {
        this.relaxer = new Thread(this);
        this.relaxer.start();
    }

    public synchronized void stop() {
        this.relaxer = null;
    }


    private MouseListener getMouseListener()
    {
        if( this.myMouseListener == null ) {
            this.myMouseListener = new MouseListener()
            {
                @Override
                public synchronized void mousePressed(MouseEvent e)
                {
                    int x = e.getX();
                    int y = e.getY();
                    double bestdist = 1.7976931348623157E+308D;

                    for (int i = 0; i < nnodes; i++) {
                        Node n = nodes[i];
                        n.jumped = false;
                        double dist = (n.x - x) * (n.x - x) + (n.y - y)
                                * (n.y - y);
                        if (dist < bestdist) {
                            pick = n;
                            bestdist = dist;
                            }
                        }

                    if (pick.lbl.equals("Intuitec")) {
                        return;
                        }

                    pickfixed = pick.fixed;
                    pick.fixed = true;
                    pick.x = x;
                    pick.y = y;

                    repaint();

                    addMouseMotionListener(getMouseMotionListener());
                }

                @Override
                public synchronized void mouseReleased(MouseEvent e)
                {
                    pick.x = e.getX();
                    pick.y = e.getY();
                    pick.fixed = pickfixed;

                    repaint();
                    removeMouseMotionListener(getMouseMotionListener());
                }

                @Override
                public synchronized void mouseExited(MouseEvent e)
                {
                    setDefaultCursor();
                }

                @Override
                public synchronized void mouseEntered(MouseEvent e)
                {
                    setHandCursor();
                }

                @Override
                public synchronized void mouseClicked(MouseEvent e)
                {
                    if( !pick.lbl.equals("Intuitec") ) {
                        graph.jumpTo(pick.lbl);
                        }

                    pick.jumped = true;
                    pick = null;
                }
            };
            }
        return this.myMouseListener;
    }

    private MouseMotionListener getMouseMotionListener()
    {
        if( this.myMouseMotionListener == null ) {
            this.myMouseMotionListener = new MouseMotionListener() {
                @Override
                public synchronized void mouseMoved(MouseEvent e) {}

                @Override
                public synchronized void mouseDragged(MouseEvent e)
                {
                    pick.x = e.getX();
                    pick.y = e.getY();

                    repaint();
                }
            };
            }
        return this.myMouseMotionListener;
    }
}
