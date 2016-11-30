package com.googlecode.cchlib.util;

import java.util.Enumeration;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 *
 */
public class NeverFailResourceBundle extends ResourceBundle
{
    /**
     *  @see ResourceBundle
     */
    protected NeverFailResourceBundle( final ResourceBundle parentResourceBundle )
    {
        setParent( parentResourceBundle );
    }

    @Override
    protected Object handleGetObject( final String key )
    {
        try {
            final Object value = super.parent.getObject( key );

            if( value != null ) {
                return value;
                }
            }
        catch( final MissingResourceException ignore ) {
            // Ignore
        }

        System.err.println( key + "=" );

        return "##" + key + "##";
    }

    @Override
    public Enumeration<String> getKeys()
    {
        return super.parent.getKeys();
    }

}
