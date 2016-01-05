package com.googlecode.cchlib.apps.emptydirectories;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class AbstractEmptyDirectoriesLookup<FILTER> implements EmptyDirectoriesLookup<FILTER> {
    private final List<EmptyDirectoriesListener> listeners = new ArrayList<>();

    /**
     * Add listener to this object.
     *
     * @param listener A valid {@link EmptyDirectoriesListener}
     * @throws NullPointerException if listener is null.
     */
    @Override
    final
    public void addListener( final EmptyDirectoriesListener listener )
    {
        if( listener == null ) {
            throw new NullPointerException();
            }

        this.getListeners().add( listener );
    }

    /**
     * Remove listener to this object
     *
     * @param listener A {@link EmptyDirectoriesListener} object
     */
    @Override
    final
    public void removeListener( final EmptyDirectoriesListener listener )
    {
        this.getListeners().remove( listener );
    }
    protected final Collection<EmptyDirectoriesListener> getListeners()
    {
        return this.listeners;
    }
    protected final void notify( final EmptyFolder emptyFolder )
    {
        for( final EmptyDirectoriesListener l : getListeners() ) {
            l.newEntry( emptyFolder );
            }
    }
}
