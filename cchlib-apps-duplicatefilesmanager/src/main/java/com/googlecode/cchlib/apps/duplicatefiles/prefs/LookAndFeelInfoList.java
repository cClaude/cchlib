package com.googlecode.cchlib.apps.duplicatefiles.prefs;

import java.util.Iterator;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import com.googlecode.cchlib.util.WrappeException;
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
                        throws WrappeException
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
        return new Iterable<LookAndFeelInfo>()
            {
                @Override
                public Iterator<LookAndFeelInfo> iterator()
                {
                    return getContentIterator();
                }
            };
    }
    
}
