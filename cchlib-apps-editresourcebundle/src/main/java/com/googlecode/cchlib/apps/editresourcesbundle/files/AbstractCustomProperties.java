package com.googlecode.cchlib.apps.editresourcesbundle.files;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.EventListenerList;

/**
 * TODOC
 */
public abstract class AbstractCustomProperties
    implements CustomProperties
{
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
    protected void setEdited( final boolean isEdited )
    {
        if( this.hasChanged != isEdited ) {
            this.hasChanged = isEdited;

            final ChangeEvent event     = new ChangeEvent( this );
            final Object[]    listeners = listenerList.getListenerList();


            for( int i = listeners.length - 2; i >= 0; i -= 2 ) { // $codepro.audit.disable numericLiterals
                if( listeners[i] == ChangeListener.class ) { // $codepro.audit.disable useEquals
                    ((ChangeListener)listeners[i + 1]).stateChanged( event );
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
    public void addChangeListener( final ChangeListener l )
    {
        listenerList.add( ChangeListener.class, l );
    }

    /**
     * Removes a {@link ChangeListener}
     *
     * @param l the {@link ChangeListener} to remove
     */
    @Override
    public void removeChangeListener( final ChangeListener l )
    {
        listenerList.remove( ChangeListener.class, l );
    }
}
