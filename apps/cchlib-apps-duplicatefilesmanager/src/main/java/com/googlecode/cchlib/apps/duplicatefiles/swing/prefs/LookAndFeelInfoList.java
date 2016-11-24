package com.googlecode.cchlib.apps.duplicatefiles.swing.prefs;

import java.util.Iterator;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import com.googlecode.cchlib.apps.duplicatefiles.swing.prefs.locale.ListContener;
import com.googlecode.cchlib.apps.duplicatefiles.swing.prefs.locale.ListInfo;
import com.googlecode.cchlib.util.iterator.AbstractIteratorWrapper;
import com.googlecode.cchlib.util.iterator.ArrayIterator;

/**
 *
 */
public class LookAndFeelInfoList
    implements ListContener<LookAndFeelInfo>
{
    /**
     *
     */
    public LookAndFeelInfoList()
    {
        // Empty
    }

    @Override
    public Iterator<ListInfo<LookAndFeelInfo>> iterator()
    {
        return new AbstractIteratorWrapper<LookAndFeelInfo,ListInfo<LookAndFeelInfo>>(
                getContentIterator()
                )
            {
                @Override
                public ListInfo<LookAndFeelInfo> wrap( final LookAndFeelInfo lafi )
                {
                    return new ListInfo<LookAndFeelInfo>()
                        {
                            @Override
                            public LookAndFeelInfo getContent()
                            {
                                return lafi;
                            }
                            @Override
                            public String toString()
                            {
                             return lafi.getName();
                            }
                        };
                }
            };
    }

    private Iterator<LookAndFeelInfo> getContentIterator()
    {
        return new ArrayIterator<>( UIManager.getInstalledLookAndFeels() );
    }

    @Override
    public Iterable<LookAndFeelInfo> getContentIterable()
    {
        return this::getContentIterator;
    }

}
