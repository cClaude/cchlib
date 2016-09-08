package com.googlecode.cchlib.apps.duplicatefiles.gui.panels.result;

import java.util.Set;

//not public
class Enums
{
    private Enums()
    {
        // All static
    }

    static <T extends Enum<T>> String toString( final Set<T> set )
    {
        final StringBuilder sb    = new StringBuilder();
        boolean             first = true;

        for( final T v : set ) {
            if( first ) {
                first = false;
                }
            else {
                sb.append( ',' );
                }

            sb.append( v.name() );
            }

        return sb.toString();
    }
}
