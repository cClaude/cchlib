package com.googlecode.cchlib.apps.duplicatefiles.swing.prefs;

import java.util.Iterator;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import com.googlecode.cchlib.apps.duplicatefiles.swing.prefs.locale.ListInfo;
import com.googlecode.cchlib.util.iterator.AbstractIteratorWrapper;
import com.googlecode.cchlib.util.iterator.ArrayIterator;

public class LookAndFeelInfoList
    implements Iterable<ListInfo<LookAndFeelInfo>>
{
    private static final class LookAndFeelInfoListInfoIterator
        extends AbstractIteratorWrapper<LookAndFeelInfo, ListInfo<LookAndFeelInfo>>
    {
        private LookAndFeelInfoListInfoIterator(
            final Iterator<LookAndFeelInfo> sourceIterator
            )
        {
            super( sourceIterator );
        }

        @Override
        public ListInfo<LookAndFeelInfo> wrap( final LookAndFeelInfo lafi )
        {
            return new LookAndFeelInfoListInfo( lafi );
        }
    }

    private static final class LookAndFeelInfoListInfo
        implements ListInfo<LookAndFeelInfo>
    {
        private final LookAndFeelInfo lafi;

        private LookAndFeelInfoListInfo( final LookAndFeelInfo lafi )
        {
            this.lafi = lafi;
        }

        @Override
        public LookAndFeelInfo getContent()
        {
            return this.lafi;
        }

        @Override
        public String toString()
        {
         return this.lafi.getName();
        }
    }

    public LookAndFeelInfoList()
    {
        // Empty
    }

    @Override
    public Iterator<ListInfo<LookAndFeelInfo>> iterator()
    {
        return new LookAndFeelInfoListInfoIterator( getContentIterator() );
    }

    private Iterator<LookAndFeelInfo> getContentIterator()
    {
        return new ArrayIterator<>( UIManager.getInstalledLookAndFeels() );
    }
}
