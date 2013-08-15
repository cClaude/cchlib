package alpha.bricolage201202;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class GraphPanel
    extends GraphPanelHideEdges //GraphPanelHideNodes //Panel
        implements Runnable
{
    private static final long serialVersionUID = 1L;
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

    Thread relaxer;
    boolean stress;
    boolean random;
    Node pick;
    boolean pickfixed;
    Image offscreen;
    Dimension offscreensize;
    Graphics offgraphics;

    public GraphPanel(IntuiGraph applet)
    {
        super( applet );
        //this.graph = graph;

        addMouseListener( getMouseListener() );
    }


    @Override
    public void run()
    {
        for(;;) {
            relax();

            if ((this.random) && (Math.random() < 0.03D)) {
                Node n = getNode(
                    (int) (Math.random() * this.getNodeListSize())
                    );

                if (!n.is_fixed()) {
                    n.set_x(n.get_x() + (n.get_px() * Math.random() - 50.0D));
                    n.set_y(n.get_y() + (n.get_py() * Math.random() - 50.0D));
                    }
                }
            try {
                Thread.sleep(100L);
                }
            catch (InterruptedException e) { // $codepro.audit.disable logExceptions
                break;
            }
        }
    }

    synchronized void relax()
    {
        edgesRelax();
        nodesRelax1();
        nodesRelax2(getSize());
        repaint();
    }

    private void nodesRelax2(Dimension d)
    {
//        for (int i = 0; i < this.getNnodes(); i++) {
//            Node n = getNode( i );
        for( Node n : getNodes() ) {
            if (!n.is_fixed()) {
                if (n.get_px() > n.get_x()) {
                    n.set_x(n.get_x() + Math.abs((n.get_px() - n.get_x()) / 12.0D * Math.random()));
                    }
                else {
                    n.set_x(n.get_x() - Math.abs((n.get_px() - n.get_x()) / 12.0D * Math.random()));
                    }

                if (n.get_py() > n.get_y()) {
                    n.set_y(n.get_y() + Math.abs((n.get_py() - n.get_y()) / 12.0D * Math.random()));
                    }
                else {
                    n.set_y(n.get_y() - Math.abs((n.get_py() - n.get_y()) / 12.0D * Math.random()));
                    }

                if (n.get_x() < 0.0D) {
                    n.set_x(1.0D);
                    }
                else if (n.get_x() > d.width) {
                    n.set_x((d.width - 10));
                    }

                if (n.get_y() < 0.0D) {
                    n.set_y(1.0D);
                    }
                else if (n.get_y() > d.height) {
                    n.set_y((d.height - 10));
                    }
                }

            n.set_dx(n.get_dx() / 2.0D);
            n.set_dy(n.get_dy() / 2.0D);
            }
    }

    private void nodesRelax1()
    {
//        for (int i = 0; i < this.getNnodes(); i++) {
//            Node n1 = getNode( i );
        for( Node n1 : this.getNodes() ) {
            double dx = 0.0D;
            double dy = 0.0D;

//            for (int j = 0; j < this.getNnodes(); j++) {
//            Node n2 = getNode( j );
            for( Node n2 : this.getNodes() ) {
                //if (i == j) {
                if( n1 == n2 ) {
                    continue;
                    }
                double vx = n1.get_x() - n2.get_x();
                double vy = n1.get_y() - n2.get_y();
                double len = vx * vx + vy * vy;

                if( Double.compare( len, 0.0D ) == 0 ) {
                    dx += Math.random();
                    dy += Math.random();
                    }
                else if (len < 10000.0D) {
                    dx += vx / len;
                    dy += vy / len;
                }
            }
            double dlen = dx * dx + dy * dy;
            if (dlen > 0.0D) {
                dlen = Math.sqrt(dlen) / 2.0D;
                n1.set_dx(n1.get_dx() + dx / dlen);
                n1.set_dy(n1.get_dy() + dy / dlen);
            }
        }
    }

    private void edgesRelax()
    {
//        for (int i = 0; i < this.getNedges(); i++) {
//            Edge e = this.getEdge( i );
        for( Edge e : this.getEdges() ) {
            double vx = getNode( e.getTo() ).get_x() - getNode( e.getFrom() ).get_x();
            double vy = getNode( e.getTo() ).get_y() - getNode( e.getFrom() ).get_y();
            double len = Math.sqrt(vx * vx + vy * vy);
//            double f = (this.getEdge( i ).getLen() - len) / (len * 3.0D);
            double f = (e.getLen() - len) / (len * 3.0D);
            double dx = f * vx;
            double dy = f * vy;

            getNode( e.getTo() ).set_dx(getNode( e.getTo() ).get_dx() + dx);
            getNode( e.getTo() ).set_dy(getNode( e.getTo() ).get_dy() + dy);
            getNode( e.getFrom() ).set_dx(getNode( e.getFrom() ).get_dx() + -dx);
            getNode( e.getFrom() ).set_dy(getNode( e.getFrom() ).get_dy() + -dy);
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
        nodesPaint( this.offgraphics );
        g.drawImage(this.offscreen, 0, 0, null);
    }

    private void edgeLoop(int l)
    {
        //for (int i = 0; i < this.getNedges(); i++) {
        for( Edge e : this.getEdges() ) {
            int[] xx1 = new int[l];
            int[] xx2 = new int[l];
            int[] yy1 = new int[l];
            int[] yy2 = new int[l];

            //Edge e = this.getEdge( i );
            Node m = getNode( findNode("Intuitec") );
            int y2;
            int x1;
            int y1;
            int x2;

            if( getNode( e.getFrom() ).get_lbl().equals("Intuitec") ) {
                x1 = (int)getNode( e.getFrom() ).get_x() + 19;
                y1 = (int)getNode( e.getFrom() ).get_y() + 48;
                x2 = (int)getNode( e.getTo() ).get_x();
                y2 = (int)getNode( e.getTo() ).get_y();
            } else {
                x1 = (int)getNode( e.getFrom() ).get_x();
                y1 = (int)getNode( e.getFrom() ).get_y();
                x2 = (int)getNode( e.getTo() ).get_x();
                y2 = (int)getNode( e.getTo() ).get_y();
            }
            int x0 = (int) m.get_x() + 19;

            int y0 = (int) m.get_y() + 48;
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

            if (! getNode( e.getFrom() ).get_lbl().equals("Intuitec")) {
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

    public synchronized void start()
    {
        this.relaxer = new Thread(this);
        this.relaxer.start();
    }

    public synchronized void stop()
    {
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

//                    for (int i = 0; i < getNnodes(); i++) {
//                        Node n = getNode( i );
                    for( Node n : getNodes() ) {
                        n.set_jumped(false);
                        double dist = (n.get_x() - x) * (n.get_x() - x) + (n.get_y() - y)
                                * (n.get_y() - y);
                        if (dist < bestdist) {
                            pick = n;
                            bestdist = dist;
                            }
                        }

                    if (pick.get_lbl().equals("Intuitec")) {
                        return;
                        }

                    pickfixed = pick.is_fixed();
                    pick.set_fixed(true);
                    pick.set_x(x);
                    pick.set_y(y);

                    repaint();

                    addMouseMotionListener(getMouseMotionListener());
                }

                @Override
                public synchronized void mouseReleased(MouseEvent e)
                {
                    pick.set_x(e.getX());
                    pick.set_y(e.getY());
                    pick.set_fixed(pickfixed);

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
                    if( !pick.get_lbl().equals("Intuitec") ) {
                        jumpTo(pick.get_lbl());
                        }

                    pick.set_jumped(true);
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
                    pick.set_x(e.getX());
                    pick.set_y(e.getY());

                    repaint();
                }
            };
            }
        return this.myMouseMotionListener;
    }
}
