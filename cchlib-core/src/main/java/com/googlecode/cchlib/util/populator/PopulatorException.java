package com.googlecode.cchlib.util.populator;

import java.lang.reflect.Field;
import java.lang.reflect.Member;
import javax.annotation.Nullable;
import com.googlecode.cchlib.NeedDoc;

@NeedDoc
public class PopulatorException extends PopulatorRuntimeException
{
    private static final long serialVersionUID = 2L;

    private final String   causeFieldOrMethod;
    private final Class<?> causeType;

    PopulatorException(
        final String    message,
        final Member    causeFieldOrMethod,
        final Class<?>  causeType,
        final Throwable cause
        )
    {
        super( buildMessage( message, causeFieldOrMethod, causeType ), cause );

        this.causeFieldOrMethod = toString( causeFieldOrMethod );
        this.causeType          = causeType;
    }

    PopulatorException(
        final String        message,
        final FieldOrMethod causeFieldOrMethod,
        final Class<?>      causeType,
        final Throwable     cause
        )
    {
        super( buildMessage( message, causeFieldOrMethod.getMember(), causeType ), cause );

        this.causeFieldOrMethod = toString( causeFieldOrMethod );
        this.causeType          = causeType;
    }

    PopulatorException(
        final String   message,
        final Field    causeField,
        final Class<?> causeType
        )
    {
        this( message, causeField, causeType, (Throwable)null );
    }

    private static String buildMessage(
        final String   message,
        final Member   causeFieldOrMethod,
        final Class<?> causeType
        )
    {
        return message + " for " + causeFieldOrMethod + " usign type " + causeType;
    }

    private static <T> String toString( final T causeFieldOrMethod )
    {
        if( causeFieldOrMethod == null ) {
            return null;
        }

        return causeFieldOrMethod.toString();
    }

    @NeedDoc
    @Nullable
    public final String getCauseFieldOrMethod()
    {
        return this.causeFieldOrMethod;
    }

    public final Class<?> getCauseType()
    {
        return this.causeType;
    }
}
