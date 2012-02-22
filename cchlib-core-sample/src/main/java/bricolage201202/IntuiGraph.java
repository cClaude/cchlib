package bricolage201202;

import java.applet.Applet;
import java.applet.AppletContext;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.net.URL;
import java.util.StringTokenizer;

public class IntuiGraph extends Applet
{
    private static final long serialVersionUID = 1L;
    GraphPanel panel;

    /*
    <applet CODE="IntuiGraph.class" width=700 height=700 BORDER=0 >Your browser does not support Java!
    <param name=edges value="Intuitec-Wer,Intuitec-Wie,Intuitec-Was,Intuitec-Tanit,Intuitec-Privat,Wer-Wie,Wie-Was,Was-Tanit,Tanit-Privat">
    <param name=center value="Intuitec">
    <param name=cpos value="600-5">
    <param name=position value="120-40,80-150,90-240,240-300,550-260">
    <param name=levers value="5">
    </applet>
     */
    @Override
    public String getParameter( String name )
    {
        String value = super.getParameter( name );

        if( value != null ) {
            return value;
            }

        if( "edges".equals( name ) ) {
            return "Intuitec-Wer,Intuitec-Wie,Intuitec-Was,Intuitec-Tanit,Intuitec-Privat,Wer-Wie,Wie-Was,Was-Tanit,Tanit-Privat";
            }
        else if( "center".equals( name ) ) {
            return "Intuitec";
            }
        else if( "position".equals( name ) ) {
            return "120-40,80-150,90-240,240-300,550-260";
            }
        else if( "levers".equals( name ) ) {
            return "5";
            }
        else if( "cpos".equals( name ) ) {
            return "600-5";
            }

        return null;
    }

    @Override
    public void init()
    {
        setLayout(new BorderLayout());
        setFont(new Font(getFont().getFamily(), 1, getFont().getSize()));
        this.panel = new GraphPanel(this);
        add("Center", this.panel);

        String edges = getParameter("edges");
        String center = getParameter("center");
        String position = getParameter("position");
        String levers = getParameter("levers");
        Edge.setLev(Integer.parseInt(levers));
        for (StringTokenizer t = new StringTokenizer(edges, ","); t
                .hasMoreTokens();) {
            String str = t.nextToken();
            int i = str.indexOf('-');
            if (i > 0) {
                int len = 50;
                int j = str.indexOf('/');
                if (j > 0) {
                    len = Integer.valueOf(str.substring(j + 1)).intValue();
                    str = str.substring(0, j);
                }
                this.panel.addEdge(str.substring(0, i), str.substring(i + 1),
                        len);
            }
        }
        Dimension d = getSize();

        if (center != null) {
            String cPos = getParameter("cpos");
            Node n = this.panel.getNode( this.panel.findNode(center) );
            if (cPos != null) {
                int I = cPos.indexOf('-');
                n.px = (n.x = new Double(cPos.substring(0, I)).doubleValue());
                n.py = (n.y = new Double(cPos.substring(I + 1)).doubleValue());
                n.fixed = (n.pfixed = /* 1 */true);
            } else {
                System.out.println("width " + d.width + "heigth " + d.height);
                n.px = (n.x = d.width / 2 - 19.5D);
                n.py = (n.y = d.height / 2 - 48.5D);
                n.fixed = /* ( */n.pfixed = /* 1) */true;
            }
        }
        for (StringTokenizer pp = new StringTokenizer(position, ","); pp
                .hasMoreTokens();) {
            String pos = pp.nextToken();
            int I = pos.indexOf('-');
            for (int i = 0; i < this.panel.getNnodes(); i++) {
                Node n = this.panel.getNode( i );
                if ((!n.lbl.equals("Intuitec")) && (!n.pfixed)) {
                    n.px = new Double(pos.substring(0, I)).doubleValue();
                    n.py = new Double(pos.substring(I + 1)).doubleValue();
                    if (d.width / 2 > n.px)
                        n.x = ((n.px - 20.0D) * Math.random());
                    else
                        n.x = ((n.px + 20.0D) * Math.random());
                    if (d.height / 2 > n.py)
                        n.y = ((n.py - 20.0D) * Math.random());
                    else
                        n.y = ((n.py + 20.0D) * Math.random());
                    n.pfixed = true;
                    break;
                }
            }
        }
        this.panel.random = true;
    }

    @Override
    public synchronized void start() {
        this.panel.start();
    }

    @Override
    public synchronized void stop() {
        this.panel.stop();
    }

    public synchronized void jumpTo(String tName)
    {
        try {
            AppletContext context = getAppletContext();

            URL intuitext = new URL(getDocumentBase(), "./" + tName
                    + "/index.html");
            context.showDocument(intuitext, tName);
            }
        catch (Exception e) {
            e.printStackTrace( System.err );
            }
    }

    public synchronized Image loadImage( final String name )
    {
        System.out.println("loading " + name);

        try {
            URL url = new URL(
                getDocumentBase(),
                "./images/" + name + ".gif"
                );
            System.out.println( "loading " + url );

            return getImage( url );
            }
        catch( Exception e ) {
            e.printStackTrace( System.err );
            }

        return null;
    }
}
