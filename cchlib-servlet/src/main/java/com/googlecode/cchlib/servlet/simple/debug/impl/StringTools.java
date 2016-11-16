package com.googlecode.cchlib.servlet.simple.debug.impl;

import java.util.Enumeration;

//NOT public
class StringTools
{
    private static final class EnumerationString implements Enumeration<String>
    {
        private final Enumeration<?> enumerator;

        private EnumerationString( final Enumeration<?> enumerator )
        {
            this.enumerator = enumerator;
        }

        @Override
        public boolean hasMoreElements()
        {
            return this.enumerator.hasMoreElements();
        }

        @Override
        public String nextElement()
        {
            final Object o = this.enumerator.nextElement();

            if( o == null ) {
                return null;
            }
            else {
                return o.toString();
                }
        }
    }

    private StringTools() {} // All static

    static String safeToString(final Object o)
    {
        if(o != null) {
            return o.toString();
        }
        else {
            return "Object is null";
        }
    }

    static String getObjectInfo(final Object o)
    {
        if(o != null) {
            return "ClassName:" + o.getClass().getName() + "<br/>" + o.toString();
        }
        else {
            return "NULL";
        }
    }

    static Enumeration<String> toEnumerationString(
            final Enumeration<?> enumerator
            )
    {
        return new EnumerationString( enumerator );
    }

}
