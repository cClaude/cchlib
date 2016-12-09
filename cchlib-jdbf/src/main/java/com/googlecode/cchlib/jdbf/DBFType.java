package com.googlecode.cchlib.jdbf;

import java.util.Date;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public enum DBFType {
    /**
     * Define a {@link String}
     */
    CHARACTERS(DBFField.FIELD_TYPE_C,String.class),

    /**
     * Define a {@link Boolean}
     */
    LOGICAL(DBFField.FIELD_TYPE_L,Boolean.class),

    /**
     * Define a {@link Double}
     */
    NUMERICAL(DBFField.FIELD_TYPE_N,Double.class), // FIXME is this really a Double ?

    /**
     * Define a {@link Double}
     */
    FLOAT(DBFField.FIELD_TYPE_F,Double.class),

    /**
     * Define a {@link Date}
     */
    DATE(DBFField.FIELD_TYPE_D,Date.class),

    /**
     * Need to be implemented
     */
    M(DBFField.FIELD_TYPE_M,null),
    ;

    private byte     dbfType;
    private Class<?> javaType;

    private DBFType( final byte dbfType, final Class<?> javaType )
    {
        this.dbfType  = dbfType;
        this.javaType = javaType;
    }

    public byte getType()
    {
        return this.dbfType;
    }

    public DBFField newDBFField( final String name )
    {
        final DBFField field = new DBFField();

        field.setName( name );
        field.setDataType( this.dbfType );;

        return field;
    }

    public void checkValue( @Nonnull final Object value )
            throws DBFStructureException
    {
        if( ! this.javaType.isAssignableFrom( value.getClass() ) ) {
            throw new DBFStructureException(
                "Invalid value for field [Type " + this + "] value=[" + value + "]"
                );
        }
    }

    @Nullable
    public static DBFType find( final byte type )
    {
        for( final DBFType item : values() ) {
            if( item.dbfType == type ) {
                return item;
            }
        }
        return null;
    }

    @Nonnull
    public static DBFType get( final byte type )
    {
        final DBFType item = find( type );

        if( item != null ) {
            return item;
        }

        throw new IllegalArgumentException();
    }
}
