package com.googlecode.cchlib.jdbf;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;

/**
 * Default implementation of {@link DBFEntry}
 */
final class DBFEntryImpl implements DBFEntry
{
    private final Object   value;
    private final DBFField field;

    DBFEntryImpl( Object value, DBFField field )
    {
        this.value = value;
        this.field = field;
    }

    @Override
    public DBFField getDBFField()
    {
        return field;
    }

    @Override
    public Object getValue()
    {
        return value;
    }

    private static <T extends Number> Number getNumber(
        final Object    value,
        final Class<T>  clazz
        )
        throws DBFEntryException
    {
        if( value == null ) {
            return 0;
            }

        try {
            return Number.class.cast( value );
            }
        catch( ClassCastException ignore ) {
            String s = value.toString().trim();

            if( s.length() == 0 ) {
                return 0;
                }

            try {
                Constructor<T> m = clazz.getConstructor( String.class );
                return m.newInstance( value.toString().trim() );
                }
            catch( NoSuchMethodException e1 ) {
                throw new DBFEntryException( "Internal error", e1 );
                }
            catch( IllegalArgumentException e1 ) {
                throw new DBFEntryException( "Internal error", e1 );
                }
            catch( InstantiationException e1 ) {
                throw new DBFEntryException( "Internal error", e1 );
                }
            catch( IllegalAccessException e1 ) {
                throw new DBFEntryException( "Internal error", e1 );
                }
            catch( InvocationTargetException e1 ) {
                Throwable cause = e1.getCause();

                if( cause instanceof NumberFormatException ) {
                    throw new DBFEntryException( "Bad value", cause );
                    }

                throw new DBFEntryException( "Internal error", e1 );
                }
            }
    }

    @Override
    public int getInt() throws DBFEntryException
    {
        return getNumber( value, Integer.class ).intValue();
    }

    @Override
    public String getString() throws DBFEntryException
    {
        try {
            return String.class.cast( value );
            }
        catch( ClassCastException ignore ) {
            return value.toString();
            }
    }

    @Override
    public boolean getBoolean() throws DBFEntryException
    {
        try {
            return Boolean.class.cast( value );
            }
        catch( ClassCastException ignore ) {
            return Boolean.parseBoolean( value.toString().trim() );
            }
    }

    @Override
    public double getDouble() throws DBFEntryException
    {
        return getNumber( value, Double.class ).doubleValue();
    }

    @Override
    public Date getDate() throws DBFEntryException
    {
        return Date.class.cast( value );
        // TODO improve, try to do a date with value
    }
}
