package com.googlecode.cchlib.apps.duplicatefiles.prefs;

import com.googlecode.cchlib.util.WrapperException;
import com.googlecode.cchlib.util.iterator.AbstractIteratorWrapper;
import com.googlecode.cchlib.util.iterator.ArrayIterator;
import java.util.Iterator;
import java.util.Locale;

/**
 *
 */
public class LocaleList implements ListContener<Locale>
{
    private final Locale[] locales = { // TODO Should be computed !
        null,
        Locale.ENGLISH,
        Locale.FRENCH
        };
    private final String txtStringDefaultLocale;

    /**
     *
     */
    public LocaleList( final String txtStringDefaultLocale )
    {
        this.txtStringDefaultLocale = txtStringDefaultLocale;
    }

    @Override
    public Iterator<ListInfo<Locale>> iterator()
    {
        final Iterator<Locale> ci = getContentIterator();

        return new AbstractIteratorWrapper<Locale,ListInfo<Locale>>( ci )
            {
                @Override
                public ListInfo<Locale> wrap( final Locale locale )
                        throws WrapperException
                {
                    return new ListInfo<Locale>()
                    {
                        @Override
                        public Locale getContent()
                        {
                            return locale;
                        }

                        @Override
                        public String toString()
                        {
                            if( locale == null ) {
                                return txtStringDefaultLocale;
                                }
                            else {
                                return locale.getDisplayLanguage();
                                }
                        }
                    };
                }
            };
    }

    private Iterator<Locale> getContentIterator()
    {
        return new ArrayIterator<>( locales );
    }

    @Override
    public Iterable<Locale> getContentIterable()
    {
        return this::getContentIterator;
    }
}
