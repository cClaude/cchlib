package com.googlecode.cchlib.apps.duplicatefiles.swing.prefs.locale;

import java.util.Iterator;
import java.util.Locale;
import com.googlecode.cchlib.util.iterator.AbstractIteratorWrapper;
import com.googlecode.cchlib.util.iterator.ArrayIterator;

public class LocaleList implements Iterable<ListInfo<Locale>>
{
    private final class LocalesListInfo extends AbstractIteratorWrapper<Locale, ListInfo<Locale>>
    {
        private LocalesListInfo( final Iterator<Locale> sourceIterator )
        {
            super( sourceIterator );
        }

        @Override
        public ListInfo<Locale> wrap( final Locale locale )
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
                        return LocaleList.this.txtStringDefaultLocale;
                        }
                    else {
                        return locale.getDisplayLanguage();
                        }
                }
            };
        }
    }

    private final Locale[] locales = { // TODO Should be computed !
        null,
        Locale.ENGLISH,
        Locale.FRENCH
        };
    private final String txtStringDefaultLocale;

    public LocaleList( final String txtStringDefaultLocale )
    {
        this.txtStringDefaultLocale = txtStringDefaultLocale;
    }

    @Override
    public Iterator<ListInfo<Locale>> iterator()
    {
        return new LocalesListInfo( getContentIterator() );
    }

    private Iterator<Locale> getContentIterator()
    {
        return new ArrayIterator<>( this.locales );
    }
}
