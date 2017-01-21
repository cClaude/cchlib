package com.googlecode.cchlib.util.properties;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import javax.annotation.Nullable;
import com.googlecode.cchlib.NeedDoc;

@NeedDoc
public class PopulatorException extends PropertiesPopulatorRuntimeException
{
    private static final long serialVersionUID = 2L;

    private final String   causeFieldOrMethod;
    private final Class<?> causeType;

    private PopulatorException(
        final String    message,
        final Object    causeFieldOrMethod,
        final Class<?>  causeType,
        final Throwable cause
        )
    {
        super( buildMessage( message, causeFieldOrMethod, causeType ), cause );

        this.causeFieldOrMethod = buildCauseFieldOrMethod( causeFieldOrMethod );
        this.causeType          = causeType;
    }

    public PopulatorException(
        final String        message,
        final FieldOrMethod causeFieldOrMethod,
        final Class<?>      causeType,
        final Throwable     cause
        )
    {
        this( message, (Object)causeFieldOrMethod, causeType, cause );
    }

    public PopulatorException(
        final String    message,
        final Method    causeMethod,
        final Class<?>  causeType,
        final Throwable cause
        )
    {
        this( message, (Object)causeMethod, causeType, cause );
    }

    public PopulatorException(
        final String    message,
        final Field     causeField,
        final Class<?>  causeType,
        final Throwable cause
        )
    {
        this( message, (Object)causeField, causeType, cause );
    }

    public PopulatorException(
        final String   message,
        final Field    causeField,
        final Class<?> causeType
        )
    {
        this( message, (Object)causeField, causeType, null );
    }

    private static String buildMessage(
        final String   message,
        final Object   causeFieldOrMethod,
        final Class<?> causeType
        )
    {
        return message + " for " + causeFieldOrMethod + " usign type " + causeType;
    }

    @NeedDoc
    @Nullable
    public final String getCauseFieldOrMethod()
    {
        return this.causeFieldOrMethod;
    }

    private static String buildCauseFieldOrMethod( final Object causeFieldOrMethod )
    {
        if( causeFieldOrMethod == null ) {
            return null;
        }

        if( causeFieldOrMethod instanceof FieldOrMethod ) {
            return ((FieldOrMethod)causeFieldOrMethod).getFieldOrMethod().toString();
        } else {
            return causeFieldOrMethod.toString();
        }
    }

    public final Class<?> getCauseType()
    {
        return this.causeType;
    }
}
