package alpha.bricolage201202;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.Serializable;
import java.util.Random;
import com.googlecode.cchlib.lang.Threads;

@SuppressWarnings("squid:MaximumInheritanceDepth")
public class GraphPanel extends GraphPanelHideEdges implements Runnable
{
    private final class MyMouseMotionListener implements MouseMotionListener, Serializable
    {
        private static final long serialVersionUID = 1L;

        @Override
        public synchronized void mouseMoved( final MouseEvent event )
        {
            // Not use
        }

        @Override
        public synchronized void mouseDragged( final MouseEvent event )
        {
            GraphPanel.this.pick.setNodeX( event.getX() );
            GraphPanel.this.pick.setNodeY( event.getY() );

            repaint();
        }
    }

    private final class MyMouseListener implements MouseListener, Serializable
    {
        private static final long serialVersionUID = 1L;
        private MyMouseMotionListener myMouseMotionListener;

        @Override
        public synchronized void mousePressed(final MouseEvent e)
        {
            final int x = e.getX();
            final int y = e.getY();
            double bestdist = 1.7976931348623157E+308D;

            for( final Node n : getNodes() ) {
                n.setNodeJumped(false);
                final double dist = ((n.getNodeX() - x) * (n.getNodeX() - x)) + ((n.getNodeY() - y)
                        * (n.getNodeY() - y));
                if (dist < bestdist) {
                    GraphPanel.this.pick = n;
                    bestdist = dist;
                    }
                }

            if( IntuiGraph.INTUITEC.equals( GraphPanel.this.pick.getNodeLabel() ) ) {
                return;
                }

            GraphPanel.this.pickfixed = GraphPanel.this.pick.isNodeFixed();
            GraphPanel.this.pick.setNodeFixed(true);
            GraphPanel.this.pick.setNodeX(x);
            GraphPanel.this.pick.setNodeY(y);

            repaint();

            addMouseMotionListener( getMouseMotionListener() );
        }

        @Override
        public synchronized void mouseReleased(final MouseEvent e)
        {
            GraphPanel.this.pick.setNodeX(e.getX());
            GraphPanel.this.pick.setNodeY(e.getY());
            GraphPanel.this.pick.setNodeFixed(GraphPanel.this.pickfixed);

            repaint();
            removeMouseMotionListener(getMouseMotionListener());
        }

        @Override
        public synchronized void mouseExited(final MouseEvent e)
        {
            setDefaultCursor();
        }

        @Override
        public synchronized void mouseEntered(final MouseEvent e)
        {
            setHandCursor();
        }

        @Override
        public synchronized void mouseClicked(final MouseEvent e)
        {
            if( !IntuiGraph.INTUITEC.equals( GraphPanel.this.pick.getNodeLabel() ) ) {
                jumpTo(GraphPanel.this.pick.getNodeLabel());
                }

            GraphPanel.this.pick.setNodeJumped(true);
            GraphPanel.this.pick = null;
        }

        private MouseMotionListener getMouseMotionListener()
        {
            if( this.myMouseMotionListener == null ) {
                this.myMouseMotionListener = new MyMouseMotionListener();
                }
            return this.myMouseMotionListener;
        }
    }

    private static final long serialVersionUID = 1L;

    //private static final Color fixedColor = Color.blue;
    //private static final Color selectColor = Color.pink;
    //private static final Color nodeColor1 = new Color(0, 186, 185);
    //private static final Color nodeColor2 = new Color(249, 196, 106);
    private static final Color edgeColor = Color.black;
    private static final Color stressColor = Color.darkGray;
    private static final Color arcColor1 = Color.black;
    private static final Color arcColor2 = Color.pink;
    private static final Color arcColor3 = Color.blue;

    private MyMouseListener myMouseListener;

    private Thread relaxer;
    private boolean stress;
    boolean random;
    private Node pick;
    private boolean pickfixed;
    private Image offscreen;
    private Dimension offscreensize;
    private Graphics offgraphics;

    private Random randomSeed;

    public GraphPanel(final IntuiGraph applet)
    {
        super( applet );

        addMouseListener( getMouseListener() );
    }

    @Override
    public void run()
    {
        for(;;) {
            relax();

            if ((this.random) && (random() < 0.03D)) {
                final Node n = getNode(
                    (int) (random() * this.getNodeListSize())
                    );

                if (!n.isNodeFixed()) {
                    n.setNodeX(n.getNodeX() + ((n.getNodePX() * random()) - 50.0D));
                    n.setNodeY(n.getNodeY() + ((n.getNodePY() * random()) - 50.0D));
                    }
                }

            if( Threads.sleepAndNotify( 100L ) ) {
                break;
            }
        }
    }

    public double random()
    {
        if( this.randomSeed == null ) {
            this.randomSeed = new Random();
        }
        return this.randomSeed.nextDouble();
    }

    synchronized void relax()
    {
        edgesRelax();
        nodesRelax1();
        nodesRelax2(getSize());
        repaint();
    }

    private void nodesRelax2(final Dimension d)
    {
        for( final Node n : getNodes() ) {
            if (!n.isNodeFixed()) {
                if (n.getNodePX() > n.getNodeX()) {
                    n.setNodeX(n.getNodeX() + Math.abs(((n.getNodePX() - n.getNodeX()) / 12.0D) * random()));
                    }
                else {
                    n.setNodeX(n.getNodeX() - Math.abs(((n.getNodePX() - n.getNodeX()) / 12.0D) * random()));
                    }

                if (n.getNodePY() > n.getNodeY()) {
                    n.setNodeY(n.getNodeY() + Math.abs(((n.getNodePY() - n.getNodeY()) / 12.0D) * random()));
                    }
                else {
                    n.setNodeY(n.getNodeY() - Math.abs(((n.getNodePY() - n.getNodeY()) / 12.0D) * random()));
                    }

                if (n.getNodeX() < 0.0D) {
                    n.setNodeX(1.0D);
                    }
                else if (n.getNodeX() > d.width) {
                    n.setNodeX( d.width - 10D );
                    }

                if (n.getNodeY() < 0.0D) {
                    n.setNodeY(1.0D);
                    }
                else if (n.getNodeY() > d.height) {
                    n.setNodeY( d.height - 10D );
                    }
                }

            n.setNodeDX(n.getNodeDX() / 2.0D);
            n.setNodeDY(n.getNodeDY() / 2.0D);
            }
    }

    private void nodesRelax1()
    {
        for( final Node n1 : this.getNodes() ) {
            double dx = 0.0D;
            double dy = 0.0D;

            for( final Node n2 : this.getNodes() ) {
                if( n1 == n2 ) {
                    continue;
                    }
                final double vx = n1.getNodeX() - n2.getNodeX();
                final double vy = n1.getNodeY() - n2.getNodeY();
                final double len = (vx * vx) + (vy * vy);

                if( Double.compare( len, 0.0D ) == 0 ) {
                    dx += random();
                    dy += random();
                    }
                else if (len < 10000.0D) {
                    dx += vx / len;
                    dy += vy / len;
                }
            }
            double dlen = (dx * dx) + (dy * dy);
            if (dlen > 0.0D) {
                dlen = Math.sqrt(dlen) / 2.0D;
                n1.setNodeDX(n1.getNodeDX() + (dx / dlen));
                n1.setNodeDY(n1.getNodeDY() + (dy / dlen));
            }
        }
    }

    private void edgesRelax()
    {
        for( final Edge e : this.getEdges() ) {
            final double vx = getNode( e.getTo() ).getNodeX() - getNode( e.getFrom() ).getNodeX();
            final double vy = getNode( e.getTo() ).getNodeY() - getNode( e.getFrom() ).getNodeY();
            final double len = Math.sqrt((vx * vx) + (vy * vy));
            final double f = (e.getLen() - len) / (len * 3.0D);
            final double dx = f * vx;
            final double dy = f * vy;

            getNode( e.getTo() ).setNodeDX(getNode( e.getTo() ).getNodeDX() + dx);
            getNode( e.getTo() ).setNodeDY(getNode( e.getTo() ).getNodeDY() + dy);
            getNode( e.getFrom() ).setNodeDX(getNode( e.getFrom() ).getNodeDX() + -dx);
            getNode( e.getFrom() ).setNodeDY(getNode( e.getFrom() ).getNodeDY() + -dy);
        }
    }

    @Override
    public void paint( final Graphics g )
    {
        update( g );
    }

    @Override
    public synchronized void update( final Graphics g )
    {
        final Dimension d = getSize();

        if ((this.offscreen == null) || (d.width != this.offscreensize.width)
                || (d.height != this.offscreensize.height)) {
            this.offscreen = createImage(d.width, d.height);
            this.offscreensize = d;
            this.offgraphics = this.offscreen.getGraphics();
            this.offgraphics.setFont(getFont());
        }

        this.offgraphics.setColor(Color.white);
        this.offgraphics.fillRect(0, 0, d.width, d.height);
        edgeLoop( Edge.getGlobalLevers() );
        nodesPaint( this.offgraphics );
        g.drawImage(this.offscreen, 0, 0, null);
    }

    private void edgeLoop(final int l)
    {
        for( final Edge e : this.getEdges() ) {
            final int[] xx1 = new int[l];
            final int[] xx2 = new int[l];
            final int[] yy1 = new int[l];
            final int[] yy2 = new int[l];

            final Node m = getNode( findNode(IntuiGraph.INTUITEC) );
            int y2;
            int x1;
            int y1;
            int x2;

            if( getNode( e.getFrom() ).getNodeLabel().equals(IntuiGraph.INTUITEC) ) {
                x1 = (int)getNode( e.getFrom() ).getNodeX() + 19;
                y1 = (int)getNode( e.getFrom() ).getNodeY() + 48;
                x2 = (int)getNode( e.getTo() ).getNodeX();
                y2 = (int)getNode( e.getTo() ).getNodeY();
            } else {
                x1 = (int)getNode( e.getFrom() ).getNodeX();
                y1 = (int)getNode( e.getFrom() ).getNodeY();
                x2 = (int)getNode( e.getTo() ).getNodeX();
                y2 = (int)getNode( e.getTo() ).getNodeY();
            }
            final int x0 = (int) m.getNodeX() + 19;

            final int y0 = (int) m.getNodeY() + 48;

            for (int j = 0; j < l; j++) {
                xx1[j] = (((l - j) * x1) + (j * x0)) / l;
                xx2[j] = (((l - j) * x2) + (j * x0)) / l;

                yy1[j] = (((l - j) * y1) + (j * y0)) / l;

                yy2[j] = (((l - j) * y2) + (j * y0)) / l;
            }

            final int len = (int) Math.abs(
                    Math.sqrt( ((x1 - x2) * (x1 - x2) )
                    + ((double)(y1 - y2) * (y1 - y2)))
                    - e.getLen()
                    );
            this.offgraphics.setColor(len < 20 ? GraphPanel.arcColor2
                    : len < 10 ? GraphPanel.arcColor1 : GraphPanel.arcColor3);
            this.offgraphics.drawLine(x1, y1, x2, y2);

            if (! getNode( e.getFrom() ).getNodeLabel().equals(IntuiGraph.INTUITEC)) {
                for (int j = 0; j < l; j++) {
                    this.offgraphics.drawLine(
                            xx1[j],
                            yy1[j],
                            xx2[j],
                            yy2[j]
                            );
                }
            }

            if (this.stress) {
                final String lbl = String.valueOf(len);
                this.offgraphics.setColor(GraphPanel.stressColor);
                this.offgraphics.drawString(lbl, x1 + ((x2 - x1) / 2), y1
                        + ((y2 - y1) / 2));
                this.offgraphics.setColor(GraphPanel.edgeColor);
            }
        }
    }

    public void setHandCursor()
    {
        setCursor( Cursor.getPredefinedCursor( 12 ) );
    }

    public void setDefaultCursor()
    {
        setCursor( Cursor.getPredefinedCursor( 0 ) );
    }

    public synchronized void startRelaxer()
    {
        this.relaxer = new Thread(this);
        this.relaxer.start();
    }

    public synchronized void stopRelaxer()
    {
        this.relaxer = null;
    }

    private MouseListener getMouseListener()
    {
        if( this.myMouseListener == null ) {
            this.myMouseListener = new MyMouseListener();
            }
        return this.myMouseListener;
    }
}
