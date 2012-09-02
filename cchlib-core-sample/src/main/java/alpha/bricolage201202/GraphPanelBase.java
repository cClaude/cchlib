package alpha.bricolage201202;

import java.awt.Image;
import java.awt.Panel;

public abstract class GraphPanelBase extends Panel
{
    private static final long serialVersionUID = 1L;
    private IntuiGraph applet;

    public GraphPanelBase( IntuiGraph applet )
    {
        super();
        
        this.applet = applet;
    }

    protected final void jumpTo( final String label )
    {
        applet.jumpTo( label );
    }

	protected Image loadImage( final String label ) 
	{
        return applet.loadImage( label );
	}

}
