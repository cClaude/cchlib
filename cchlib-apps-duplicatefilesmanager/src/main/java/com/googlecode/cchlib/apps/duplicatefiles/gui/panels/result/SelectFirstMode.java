package com.googlecode.cchlib.apps.duplicatefiles.gui.panels.result;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import com.googlecode.cchlib.apps.duplicatefiles.KeyFileState;

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
        return computeForMode.sort( files );
    }

    public KeyFileState getFileToDisplay( final Collection<KeyFileState> files )
    {
        if( files instanceof List ) {
            return ((List<KeyFileState>)files).get( 0 );
        } else {
            return files.iterator().next();
        }
    }

    @FunctionalInterface
    private static interface ComputeForMode {
        Collection<KeyFileState> sort( Collection<KeyFileState> files );
    }
}
