package com.googlecode.cchlib.i18n.types;

import com.googlecode.cchlib.NeedDoc;
import com.googlecode.cchlib.i18n.AutoI18nType;

/**
 * TODOC
 *
 * @param <T> TODOC
 */
@NeedDoc
public abstract class AbstractType<T> implements AutoI18nType
{
    private static final long serialVersionUID = 1L;
    private final Class<T> type;

    public AbstractType( Class<T> type )
    {
        this.type = type;
    }
    
    /**
     * Returns type for this object
     * @return type for this object
     */
    @Override
    final public Class<T> getType()
    {
        return type;
    }

    /**
     * Cast current object to current type.
     *
     * @param toI18n Object to I18n
     * @return cast field to localize to current type
     * @see #getType()
     */
    public final T cast( Object toI18n )
    {
        try {
            return getType().cast( toI18n );
            }
        catch( ClassCastException e ) {
            throw new ClassCastException( "Try to cast '" + toI18n.getClass() + "' to " + getType() );
            }
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append( "AbstractType [getType()=" );
        builder.append( getType() );
        builder.append( ']' );
        return builder.toString();
    }
}