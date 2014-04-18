package com.googlecode.cchlib.util.mappable;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 *
 */
public enum MappableTypes
{
    CLASSES_STANDARDS_TYPES(Object.class),
    CLASSES_SHOW_ALL(
            Boolean.class,
            Character.class,
            Enum.class,
            Number.class,
            String.class,
            File.class,
            URL.class,
            URI.class,
            Collection.class);

    private final List<Class<?>> classes;

    private MappableTypes( final Class<?>...types )
    {
        final List<Class<?>> typesList = new ArrayList<>( types.length );

        for( final Class<?> type : types ) {
            typesList.add( type );
            }

        this.classes = Collections.unmodifiableList( typesList );
    }

    public Collection<Class<?>> getClasses()
    {
        return classes;
    }
}
