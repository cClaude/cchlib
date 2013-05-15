package com.googlecode.cchlib.i18n;

/**
 * TODOC
 *
 * @param <T> TODOC
 */
// not public
abstract class AbstractType<T> implements AutoI18nTypes.Type
{
    private static final long serialVersionUID = 1L;

    /**
     * Returns type for this object
     * @return type for this object
     */
    @Override
    public abstract Class<T> getType();

    /**
     * Cast current object to current type.
     *
     * @param toI18n Object to I18n
     * @return cast field to localize to current type
     * @see #getType()
     */
    final public T cast( Object toI18n )
    {
        return getType().cast( toI18n );
    }
}