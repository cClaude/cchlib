package com.googlecode.cchlib.util.properties;

import java.lang.reflect.Field;

/**
 *
 */
public class PopulatorException extends RuntimeException
{
    private static final long serialVersionUID = 1L;
    private Field causeField;// NOT SERIALISABLE !
    private Class<?> causeType;

    public PopulatorException(
        final String    message,
        final Field     causeField,
        final Class<?>  causeType
        )
    {
        super( message + " for Field " + causeField.getName() + " that return a " + causeField.getName() );

        this.causeField = causeField;
        this.causeType = causeType;
    }

    public final Field getCauseField()
    {
        return causeField;
    }

    public final Class<?> getCauseType()
    {
        return causeType;
    }
}
