package com.googlecode.cchlib.util.emptydirectories.lookup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import com.googlecode.cchlib.util.emptydirectories.EmptyDirectoriesListener;
import com.googlecode.cchlib.util.emptydirectories.EmptyDirectoriesLookup;
import com.googlecode.cchlib.util.emptydirectories.EmptyFolder;

/**
 * Abstract implementation of {@link EmptyDirectoriesLookup}
 *
 * @param <FILTER> see {@link EmptyDirectoriesLookup} parameter
 */
public abstract class AbstractEmptyDirectoriesLookup<FILTER>
    implements EmptyDirectoriesLookup<FILTER>
{
    private final List<EmptyDirectoriesListener> listeners = new ArrayList<>();

    /**
     * Add listener to this object.
     *
     * @param listener A valid {@link EmptyDirectoriesListener}
     * @throws NullPointerException if listener is null.
     */
    @Override
    public final void addListener( final EmptyDirectoriesListener listener )
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
    public final void removeListener( final EmptyDirectoriesListener listener )
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
