package com.googlecode.cchlib.util.mappable;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 *
 */
public enum MappableTypes
{
//    /**
//     * @deprecated use ALL_TYPES instead
//     */
//    @Deprecated
//    CLASSES_STANDARDS_TYPES( Object.class ),
    BASIC_TYPES(
        Boolean.class,
        Character.class,
        Enum.class,
        Number.class,
        String.class
        ),
    BASIC_TYPES_AND_COLLECTIONS( BASIC_TYPES, Collection.class ),
//    /**
//     * @deprecated use STANDARD_TYPES instead
//     */
//    @Deprecated
//    CLASSES_SHOW_ALL(
//        BASIC_TYPES_AND_COLLECTIONS,
//        File.class,
//        URL.class,
//        URI.class
//        ),
    STANDARD_TYPES(
        BASIC_TYPES_AND_COLLECTIONS,
        File.class,
        URL.class,
        URI.class
            ),
    ALL_TYPES( Object.class ),
    ;

    private final Set<Class<?>> classes;

    private MappableTypes( final MappableTypes parent, final Class<?>...types )
    {
        final Set<Class<?>> typesList = new HashSet<>( types.length );

        if( parent != null ) {
            typesList.addAll( parent.classes );
        }

        for( final Class<?> type : types ) {
            typesList.add( type );
            }

        this.classes = Collections.unmodifiableSet( typesList );
    }

    private MappableTypes( final Class<?>...types )
    {
        this( null, types );
    }

    public Collection<Class<?>> getClasses()
    {
        return this.classes;
    }
}
