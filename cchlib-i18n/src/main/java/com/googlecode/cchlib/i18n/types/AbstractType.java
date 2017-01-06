package com.googlecode.cchlib.i18n.types;

import com.googlecode.cchlib.NeedDoc;
import com.googlecode.cchlib.i18n.AutoI18nType;

@NeedDoc
public abstract class AbstractType<T> implements AutoI18nType
{
    private static final long serialVersionUID = 1L;
    private final Class<T> type;

    public AbstractType( final Class<T> type )
    {
        this.type = type;
    }

    /**
     * Returns type for this object
     * @return type for this object
     */
    @Override
    public final Class<T> getType()
    {
        return this.type;
    }

    /**
     * Cast current object to current type.
     *
     * @param toI18n Object to I18n
     * @return cast field to localize to current type
     * @see #getType()
     */
    public final T cast( final Object toI18n )
    {
        try {
            return getType().cast( toI18n );
            }
        catch( final ClassCastException cause ) {
            final ClassCastException ex = new ClassCastException(
                    "Try to cast '" + toI18n.getClass() + "' to " + getType()
                    );

            ex.initCause( cause );

            throw ex;
            }
    }

    @Override
    public String toString()
    {
        final StringBuilder builder = new StringBuilder();
        builder.append( "AbstractType [getType()=" );
        builder.append( getType() );
        builder.append( ']' );
        return builder.toString();
    }
}
