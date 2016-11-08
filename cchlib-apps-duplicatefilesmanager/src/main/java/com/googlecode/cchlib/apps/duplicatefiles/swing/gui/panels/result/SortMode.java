package com.googlecode.cchlib.apps.duplicatefiles.swing.gui.panels.result;

import java.util.Comparator;
import com.googlecode.cchlib.apps.duplicatefiles.swing.KeyFiles;

/**
 *
 */
public enum SortMode implements Comparator<KeyFiles>
{

    /** Sort using name of first file in set */
    FIRST_FILENAME(new FilenameComparator()),
    /** Sort using path of first file in set */
    FIRST_FILEPATH(new PathComparator()),
    /** Sort using depth of first file in set */
    FIRST_FILEDEPTH(new DepthComparator()),
    /** Sort using files size */
    FILESIZE(new SizeComparator()),
    /** Sort using number of duplicate in set */
    NUMBER_OF_DUPLICATE(new NumberOfDuplicateComparator()),
    ;

    private final  Comparator<KeyFiles> comparator;
    private  SortMode( final Comparator<KeyFiles> comparator )
    {
        this.comparator = comparator;
    }

    @Override
    public int compare( final KeyFiles o1, final KeyFiles o2 )
    {
        return this.comparator.compare( o1, o2 );
    }

    private static final class FilenameComparator implements Comparator<KeyFiles> {
        @Override
        public int compare( final KeyFiles o1, final KeyFiles o2 )
        {
            return o1.toString().compareTo( o2.toString() );
        }
    }

    private static final class DepthComparator implements Comparator<KeyFiles> {
        @Override
        public int compare( final KeyFiles o1, final KeyFiles o2 )
        {
            return o1.getDepth() - o2.getDepth();
        }
    }

    private static final class NumberOfDuplicateComparator implements Comparator<KeyFiles> {
        @Override
        public int compare( final KeyFiles o1, final KeyFiles o2 )
        {
            return o1.getFiles().size() - o2.getFiles().size();
        }
    }

    private static final class PathComparator implements Comparator<KeyFiles> {
        @Override
        public int compare( final KeyFiles o1, final KeyFiles o2 )
        {
            return o1.getFirstFileInSet().getPath().compareTo(
                    o2.getFirstFileInSet().getPath()
                    );
        }
    }

    private static final class SizeComparator implements Comparator<KeyFiles> {
        @Override
        public int compare( final KeyFiles o1, final KeyFiles o2 )
        {
            return (int)(
                o1.getFirstFileInSet().getLength() -
                    o2.getFirstFileInSet().getLength()
                    );
        }
    }
}
