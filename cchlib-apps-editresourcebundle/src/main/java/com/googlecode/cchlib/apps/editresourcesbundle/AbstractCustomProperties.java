package com.googlecode.cchlib.apps.editresourcesbundle;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.EventListenerList;
//import org.apache.log4j.Logger;

/**
 * TODOC
 */
public abstract class AbstractCustomProperties
    implements CustomProperties
{
    //private static Logger logger = Logger.getLogger(AbstractCustomProperties.class);
    private static final long serialVersionUID = 1L;
    /** The listeners waiting for object changes. */
    private EventListenerList listenerList = new EventListenerList();
    private boolean hasChanged;

    public AbstractCustomProperties()
    {
        this.hasChanged = false;
    }

    @Override
    public boolean isEdited()
    {
        return this.hasChanged;
    }

    /**
     * TODOC
     * @param isEdited
     */
    protected void setEdited( boolean isEdited )
    {
        //logger.info( "setEdited: " + isEdited );

        if( this.hasChanged != isEdited ) {
            this.hasChanged = isEdited;

            ChangeEvent event     = new ChangeEvent( this );
            Object[]    listeners = listenerList.getListenerList();

            //logger.info( "setEdited: prepare notification: " + listeners.length );

            for( int i = listeners.length - 2; i >= 0; i -= 2 ) {
                if( listeners[i] == ChangeListener.class ) {
                    ((ChangeListener)listeners[i + 1]).stateChanged( event );
                    //logger.info( "setEdited: notification sent: " + listeners[i + 1] );
                    }
                }
            }
    }

    /**
     * Adds a {@link ChangeListener}
     *
     * @param l the {@link ChangeListener} to add
     */
    @Override
    public void addChangeListener(ChangeListener l)
    {
        //logger.info( "addChangeListener: " + l );
        listenerList.add( ChangeListener.class, l );
    }

    /**
     * Removes a {@link ChangeListener}
     *
     * @param l the {@link ChangeListener} to remove
     */
    @Override
    public void removeChangeListener(ChangeListener l)
    {
        listenerList.remove( ChangeListener.class, l );
    }
}
