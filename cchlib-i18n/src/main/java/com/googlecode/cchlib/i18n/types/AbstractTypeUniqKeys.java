package com.googlecode.cchlib.i18n.types;

import com.googlecode.cchlib.i18n.core.resolve.Keys;
import com.googlecode.cchlib.i18n.core.resolve.UniqKeys;

/**
 *
 */
public abstract class AbstractTypeUniqKeys<T> extends AbstractType<T>
{
    private static final long serialVersionUID = 1L;
    public AbstractTypeUniqKeys( Class<T> type )
    {
        super( type );
    }

    @Override
    public final Keys getKeys( Object toI18n, String keyBaseName )
    {
        return new UniqKeys( keyBaseName );
    }
}
