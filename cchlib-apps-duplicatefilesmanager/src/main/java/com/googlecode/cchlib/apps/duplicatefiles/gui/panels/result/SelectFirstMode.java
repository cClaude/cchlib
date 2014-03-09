package com.googlecode.cchlib.apps.duplicatefiles.gui.panels.result;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import com.googlecode.cchlib.apps.duplicatefiles.KeyFileState;

/**
 * TODOC
 */
public enum SelectFirstMode
{
    /**
     * get first file is set, no rule
     */
    QUICK( new QuickCompute() ),

    /**
     * File depth ascending order
     */
    FILEDEPTH_ASCENDING_ORDER( new FileDepthAscendingCompute() ),

    /**
     * File depth descending order
     */
    FILEDEPTH_DESCENDING_ORDER( new FileDepthDescendingCompute() ),
    ;

    private final ComputeForMode computeForMode;

    private SelectFirstMode( final ComputeForMode computeForMode )
    {
        this.computeForMode = computeForMode;
    }

    public Collection<KeyFileState> sort( Collection<KeyFileState> files )
    {
        return computeForMode.sort( files );
    }

    public KeyFileState getFileToDisplay( Collection<KeyFileState> files )
    {
        if( files instanceof List ) {
            return ((List<KeyFileState>)files).get( 0 );
        } else {
            return files.iterator().next();
        }
    }

    private interface ComputeForMode {
        Collection<KeyFileState> sort( Collection<KeyFileState> files );
    }

    private static final class QuickCompute implements ComputeForMode {
        @Override
        public Collection<KeyFileState> sort( Collection<KeyFileState> files )
        {
            return files;
        }
    }

    private static final class FileDepthAscendingCompute implements ComputeForMode {
        private static final Comparator<? super KeyFileState> fileDepthAscendingComparator = new FileDepthDescendingComparator();
        @Override
        public Collection<KeyFileState> sort( Collection<KeyFileState> files )
        {
            List<KeyFileState> list = new ArrayList<>( files );
            Collections.sort( list, fileDepthAscendingComparator );

            return list;
        }
    }

    private static final class FileDepthDescendingCompute implements ComputeForMode {
        private static final Comparator<? super KeyFileState> fileDepthDescendingComparator = new FileDepthAscendingComparator();
        @Override
        public Collection<KeyFileState> sort( Collection<KeyFileState> files )
        {
            List<KeyFileState> list = new ArrayList<>( files );
            Collections.sort( list, fileDepthDescendingComparator );

            return list;
        }
    }

    private static final class FileDepthDescendingComparator implements Comparator<KeyFileState> {
        @Override
        public int compare( final KeyFileState o1, final KeyFileState o2)
        {
            return o1.getDepth() - o2.getDepth();
        }
    }

    private static final class FileDepthAscendingComparator implements Comparator<KeyFileState> {
        @Override
        public int compare( final KeyFileState o1, final KeyFileState o2)
        {
            return o1.getDepth() - o2.getDepth();
        }
    }
}
