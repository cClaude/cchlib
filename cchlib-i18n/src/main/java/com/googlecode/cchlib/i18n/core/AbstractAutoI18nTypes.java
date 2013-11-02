package com.googlecode.cchlib.i18n.core;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import com.googlecode.cchlib.i18n.AutoI18nType;
import com.googlecode.cchlib.i18n.AutoI18nTypeLookup;

/* not public */ abstract class AbstractAutoI18nTypes implements AutoI18nTypeLookup, Iterable<AutoI18nType>
{
    private static final long serialVersionUID = 1L;
    /** @serial */
    private ArrayList<AutoI18nType> types;

    public AbstractAutoI18nTypes()
    {
        this.types = new ArrayList<AutoI18nType>();

        final Method[] methods = getClass().getMethods();

        for( Method method : methods ) {
            if( AutoI18nType.class.isAssignableFrom( method.getReturnType() ) ) {
                if( method.getParameterTypes().length == 0 ) {
                    addType( method );
                    }
                }
            }
    }

    private void addType( final Method method )
    {
        try {
            AutoI18nType type = AutoI18nType.class.cast( method.invoke( AbstractAutoI18nTypes.this ) );

            types.add( type );
            }
        catch( Exception shouldNotOccur ) {
            throw new RuntimeException( shouldNotOccur );
            }
    }

    /**
     * Returns collection of AutoI18nTypes.Type supported
     * by this AutoI18nTypes
     *
     * @return collection of AutoI18nTypes.Type supported
     * by this AutoI18nTypes
     */
    public Collection<AutoI18nType> getAutoI18nTypes()
    {
        return Collections.unmodifiableCollection( types );
    }

    @Override
    public Iterator<AutoI18nType> iterator()
    {
        return getAutoI18nTypes().iterator();
    }

    @Override
    public AutoI18nType lookup( Field field )
    {
        for( AutoI18nType type : this ) {
            if( type.getType().isAssignableFrom( field.getType() ) ) {
                return type;
                }
            }
        return null;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append( getClass() );
        builder.append( " [types=" );
        builder.append( types );
        builder.append( ']' );
        return builder.toString();
    }
}
