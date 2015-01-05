package com.googlecode.cchlib.util.duplicate;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Listener handler for {@link DFFPass2}
 *
 * @since 4.2
 */
public abstract class AbstractDFFPass2 implements DFFPass2 {

    private final ArrayList<DuplicateFileFinderEventListener> listeners;
    private final DFFConfig config;

    public AbstractDFFPass2( final DFFConfig config )
    {
        this.config    = config;
        this.listeners = new ArrayList<>();
    }

    protected DFFConfig getConfig()
    {
        return this.config;
    }

    @Override
    public void addEventListener( @Nonnull final DuplicateFileFinderEventListener eventListener )
    {
        if( eventListener == null ) {
            throw new NullPointerException( "DuplicateFileFinderEventListener is null" );
        }

        synchronized( this.listeners ) {
            if( ! this.listeners.contains( eventListener ) ) {
                this.listeners.add( eventListener );
            }
        }
    }

    @Override
    public void removeEventListener( @Nonnull final DuplicateFileFinderEventListener eventListener )
    {
        if( eventListener == null ) {
            throw new NullPointerException( "DuplicateFileFinderEventListener is null" );
        }

        synchronized( this.listeners ) {
            final int index = this.listeners.indexOf( eventListener );

            if( index >= 0 ) {
                this.listeners.remove( index );
            }
        }
    }

    protected void notify_analysisStart( @Nonnull final File file )
    {
        synchronized( this.listeners ) {
            for(final DuplicateFileFinderEventListener l:this.listeners) {
                l.analysisStart( file );
            }
       }
    }

    protected void notify_ioError( @Nonnull final File file, final IOException ioe )
    {
        synchronized( this.listeners ) {
            for(final DuplicateFileFinderEventListener l:this.listeners) {
                l.ioError( file, ioe );
            }
        }
    }

    protected void notify_analysisStatus( @Nonnull final File file, @Nonnegative final int length )
    {
        synchronized( this.listeners ) {
            for(final DuplicateFileFinderEventListener l:this.listeners) {
                l.analysisStatus( file, length );
            }
       }
    }

    protected void notify_analysisDone( @Nonnull final File file, @Nullable final String hashString )
    {
        synchronized( this.listeners ) {
            for(final DuplicateFileFinderEventListener l:this.listeners) {
                l.analysisDone( file, hashString );
            }
       }
    }
}
