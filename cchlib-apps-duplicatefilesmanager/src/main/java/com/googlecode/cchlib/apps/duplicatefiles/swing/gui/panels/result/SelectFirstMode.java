package com.googlecode.cchlib.apps.duplicatefiles.swing.gui.panels.result;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import com.googlecode.cchlib.apps.duplicatefiles.swing.KeyFileState;

public enum SelectFirstMode
{
    /**
     * get first file is set, no rule
     */
    QUICK( files -> files ),

    /**
     * File depth ascending order
     */
    FILEDEPTH_ASCENDING_ORDER( files -> {
        final List<KeyFileState> list = new ArrayList<>( files );

        Collections.sort( list, ( o1, o2 ) -> o1.getDepth() - o2.getDepth() );

        return list;
    } ),

    /**
     * File depth descending order
     */
    FILEDEPTH_DESCENDING_ORDER( files -> {
        final List<KeyFileState> list = new ArrayList<>( files );

        Collections.sort( list, ( o1, o2 ) -> o2.getDepth() - o1.getDepth() );

        return list;
    }),
    ;

    private final ComputeForMode computeForMode;

    private SelectFirstMode( final ComputeForMode computeForMode )
    {
        this.computeForMode = computeForMode;
    }

    public Collection<KeyFileState> sort( final Collection<KeyFileState> files )
    {
        return this.computeForMode.sort( files );
    }

    public KeyFileState getFileToDisplay( //
            final Collection<KeyFileState> files //
            ) throws SelectException
    {
        if( files instanceof List ) {
            return ((List<KeyFileState>)files).get( 0 );
        } else {
            try {
                return files.iterator().next();
            }
            catch( final NoSuchElementException e ) {
                // File has been removed
                throw new SelectException( e );
            }
        }
    }

    @FunctionalInterface
    private static interface ComputeForMode {
        Collection<KeyFileState> sort( Collection<KeyFileState> files );
    }
}
