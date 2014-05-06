package com.googlecode.cchlib.util.properties;

import com.googlecode.cchlib.NeedDoc;

@NeedDoc
public class PopulatorException extends PropertiesPopulatorException
{
    private static final long serialVersionUID = 2L;
    private final Object causeFieldOrMethod;// NOT SERIALISABLE !
    private final Class<?> causeType;

    public PopulatorException(
        final String    message,
        final Object    causeFieldOrMethod,
        final Class<?>  causeType
        )
    {
        super( message + " for " + causeFieldOrMethod + " that return: " + causeType );

        this.causeFieldOrMethod = causeFieldOrMethod;
        this.causeType          = causeType;
    }

    public final Object getCauseFieldOrMethod()
    {
        return causeFieldOrMethod;
    }

    public final Class<?> getCauseType()
    {
        return causeType;
    }
}
