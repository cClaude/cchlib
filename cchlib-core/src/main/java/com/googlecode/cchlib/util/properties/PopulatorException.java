package com.googlecode.cchlib.util.properties;

import java.lang.reflect.Field;
import com.googlecode.cchlib.NeedDoc;

@NeedDoc
public class PopulatorException extends PropertiesPopulatorException
{
    private static final long serialVersionUID = 1L;
    private final Field causeField;// NOT SERIALISABLE !
    private final Class<?> causeType;

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
