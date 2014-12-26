package com.googlecode.cchlib.apps.duplicatefiles.prefs.util;

import java.awt.Dimension;

public final class Dimensions
{
    private Dimensions()
    {
        //All static
    }

    public static Dimension toDimension( final SerializableDimension serializableDimension )
    {
        final Dimension dimension = new Dimension();

        if( serializableDimension != null ) {
            dimension.setSize( serializableDimension.getWidth(), serializableDimension.getHeight() );
        }
        return dimension;
    }

    public static SerializableDimension toSerializableDimension( final Dimension dimension )
    {
        return new SerializableDimension( dimension );
    }
}
