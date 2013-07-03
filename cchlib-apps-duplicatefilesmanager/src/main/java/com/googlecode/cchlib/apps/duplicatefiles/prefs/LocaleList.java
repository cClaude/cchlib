package com.googlecode.cchlib.apps.duplicatefiles.prefs;

import java.util.Iterator;
import java.util.Locale;
import com.googlecode.cchlib.util.WrapperException;
import com.googlecode.cchlib.util.iterator.AbstractIteratorWrapper;
import com.googlecode.cchlib.util.iterator.ArrayIterator;

/**
 *
 */
public class LocaleList implements ListContener<Locale>
{
    private Locale[] locales = {
        null,
        Locale.ENGLISH,
        Locale.FRENCH
        };
    private String txtStringDefaultLocale;

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
        Iterator<Locale> ci = getContentIterator();

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
        return new ArrayIterator<Locale>( locales );
    }

    @Override
    public Iterable<Locale> getContentIterable()
    {
        return new Iterable<Locale>()
            {
                @Override
                public Iterator<Locale> iterator()
                {
                    return getContentIterator();
                }
            };
    }
}
