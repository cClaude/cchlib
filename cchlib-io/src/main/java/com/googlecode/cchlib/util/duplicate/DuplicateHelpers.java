package com.googlecode.cchlib.util.duplicate;

import java.io.File;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public final class DuplicateHelpers {
    private DuplicateHelpers() {
        // All static
    }

    public static <KEY> int removeNonDuplicate( final Map<KEY, Set<File>> mapSet )
    {
        int                 count = 0;
        final Iterator<Set<File>> iter  = mapSet.values().iterator();

        while(iter.hasNext()) {
            final Set<File> s = iter.next();

            if( s.size() < 2 ) {
                count += s.size();
                iter.remove();
            }
        }

        return count;
    }

}
