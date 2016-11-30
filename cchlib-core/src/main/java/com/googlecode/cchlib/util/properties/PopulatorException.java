package com.googlecode.cchlib.util.properties;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import com.googlecode.cchlib.NeedDoc;

@NeedDoc
public class PopulatorException extends PropertiesPopulatorRuntimeException
{
    private static final long serialVersionUID = 2L;
    private final Object causeFieldOrMethod;// NOT SERIALISABLE !
    private final Class<?> causeType;

    private PopulatorException( //
        final String        message, //
        final Object        causeFieldOrMethod, //
        final Class<?>      causeType, //
        final Throwable     cause //
        )
    {
        super( getMessage( message, causeFieldOrMethod, causeType, cause ), cause );

        this.causeFieldOrMethod = causeFieldOrMethod;
        this.causeType          = causeType;
    }

    public PopulatorException( //
        final String        message, //
        final FieldOrMethod causeFieldOrMethod, //
        final Class<?>      causeType, //
        final Throwable     cause //
        )
    {
        this( message, (Object)causeFieldOrMethod, causeType, cause );
    }

    public PopulatorException( //
        final String    message, //
        final Method    causeMethod, //
        final Class<?>  causeType, //
        final Throwable cause //
        )
    {
        this( message, (Object)causeMethod, causeType, cause );
    }
    
    public PopulatorException( //
        final String    message, //
        final Field     causeField, //
        final Class<?>  causeType, //
        final Throwable cause //
        )
    {
        this( message, (Object)causeField, causeType, cause );
    }
    
    public PopulatorException( final String message, final Field causeField, final Class<?> causeType )
    {
        this( message, (Object)causeField, causeType, null );
    }

    private static String getMessage( //
        final String    message, //
        final Object    causeFieldOrMethod, //
        final Class<?>  causeType, //
        final Throwable cause //
        )
    {
        return message + " for " + causeFieldOrMethod + " usign type " + causeType;
    }

	public final Object getCauseFieldOrMethod()
    {
       if( this.causeFieldOrMethod instanceof FieldOrMethod ) {
           return ((FieldOrMethod)this.causeFieldOrMethod).getFieldOrMethod();
       } else {
           return this.causeFieldOrMethod;
       }
    }

    public final Class<?> getCauseType()
    {
        return this.causeType;
    }
}
