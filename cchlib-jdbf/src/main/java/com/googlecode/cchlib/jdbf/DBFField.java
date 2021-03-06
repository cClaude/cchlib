// $codepro.audit.disable numericLiterals
package com.googlecode.cchlib.jdbf;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import javax.annotation.Nonnull;

/**
 * DBFField represents a field specification in an dbf file.
 * <p>
 * DBFField objects are either created and added to a DBFWriter
 * object or obtained from DBFReader object through
 * {@link DBFReader#getField(int)} query.
 * </p>
 */
public class DBFField
{
    /**
     * {@value} define a {@link String}
     */
    public static final byte FIELD_TYPE_C = (byte)'C';

    /**
     * {@value} define a {@link Boolean}
     */
    public static final byte FIELD_TYPE_L = (byte)'L';

    /**
     * {@value} define a {@link Integer}
     */
    public static final byte FIELD_TYPE_N = (byte)'N';

    /**
     * {@value} define a {@link Double}
     */
    public static final byte FIELD_TYPE_F = (byte)'F';

    /**
     * {@value} define a {@link java.util.Date}
     */
    public static final byte FIELD_TYPE_D = (byte)'D';

//    /**
//     * {@value} define a {@link xxxx} NEEDDOC
//     */
    public static final byte FIELD_TYPE_M = (byte)'M';

    /* Field struct variables start here */
    private byte[] fieldName = new byte[ 11 ];/* 0-10*/
    private byte dataType;                    /* 11 */
    @SuppressWarnings("unused")
    private int reserv1;                      /* 12-15 */
    private int fieldLength;                  /* 16 */
    private byte decimalCount;                /* 17 */
    @SuppressWarnings("unused")
    private short reserv2;                    /* 18-19 */
    @SuppressWarnings("unused")
    private byte workAreaId;                  /* 20 */
    @SuppressWarnings("unused")
    private short reserv3;                    /* 21-22 */
    @SuppressWarnings("unused")
    private byte setFieldsFlag;               /* 23 */
    private final byte[] reserv4 = new byte[ 7 ]; /* 24-30 */
    @SuppressWarnings("unused")
    private byte indexFieldFlag;              /* 31 */
    /* Field struct variables end here */

    /* other class variables */
    private int nameNullIndex = 0;

    /**
     * Creates a DBFField object from the data read from the
     * given DataInputStream.
     * <BR>
     * The data in the DataInputStream object is supposed to
     * be organized correctly and the stream "pointer" is
     * supposed to be positioned properly.
     *
     * @param in DataInputStream
     * @return the created DBFField object.
     * @throws IOException If any stream reading problems occurs.
     */
    static DBFField createField( final DataInput in )
        throws IOException
    {
        final DBFField field = new DBFField();
        final byte     aByte = in.readByte(); /* 0 */

        if( aByte == (byte)0x0d ) {
            return null;
            }

        in.readFully( field.fieldName, 1, 10 ); /* 1-10 */
        field.fieldName[0] = aByte;

        for( int i=0; i<field.fieldName.length; i++) {
            if( field.fieldName[ i] == (byte)0 ) {
                field.nameNullIndex = i;
                break;
            }
        }

        field.dataType = in.readByte(); /* 11 */
        field.reserv1 = Utils.readLittleEndianInt( in); /* 12-15 */
        field.fieldLength = in.readUnsignedByte();  /* 16 */
        field.decimalCount = in.readByte(); /* 17 */
        field.reserv2 = Utils.readLittleEndianShort( in); /* 18-19 */
        field.workAreaId = in.readByte(); /* 20 */
        field.reserv2 = Utils.readLittleEndianShort( in); /* 21-22 */
        field.setFieldsFlag = in.readByte(); /* 23 */
        in.readFully( field.reserv4); /* 24-30 */
        field.indexFieldFlag = in.readByte(); /* 31 */

        return field;
    }

    /**
     * Writes the content of DBFField object into the stream as per
     * DBF format specifications.
     *
     * @param output OutputStream
     * @throws IOException if any stream related issues occur.
     * @deprecated use {@link #write(DBFField, DataOutput)} instead
     */
    @Deprecated
    void write( final DataOutput output ) throws IOException
    {
        write( this, output );
    }

    /**
     * Writes the content of DBFField object into the stream as per DBF
     * format specifications.
     *
     * @param field
     *            {@link DBFField} to write
     * @param output
     *            OutputStream
     * @throws IOException
     *             if any stream related issues occur.
     */
    static void write( final DBFField field, final DataOutput output )
        throws IOException
    {
        // Field Name
        output.write( field.fieldName);        /* 0-10 */
        output.write( new byte[ 11 - field.fieldName.length]);

        // data type
        output.writeByte( field.dataType); /* 11 */
        output.writeInt( 0x00);   /* 12-15 */
        output.writeByte( field.fieldLength); /* 16 */
        output.writeByte( field.decimalCount); /* 17 */
        output.writeShort( (short)0x00); /* 18-19 */
        output.writeByte( (byte)0x00); /* 20 */
        output.writeShort( (short)0x00); /* 21-22 */
        output.writeByte( (byte)0x00); /* 23 */
        output.write( new byte[7]); /* 24-30*/
        output.writeByte( (byte)0x00); /* 31 */
    }

    /**
     * Returns the name of the field.
     *
     * @return Name of the field as String.
    */
    public String getName()
    {
        return new String( this.fieldName, 0, this.nameNullIndex );
    }

    /**
     * Returns the data type of the field.
     *
     * @return Data type as byte.
     */
    public byte getDataType()
    {
        return this.dataType;
    }

    /**
     * Returns the data type of the field.
     *
     * @return Data type as {@link DBFType}.
     */
    @Nonnull
    public DBFType getDBFType()
    {
        return DBFType.get( this.dataType );
    }

    /**
     * Returns field length.
     *
     * @return field length as int.
     */
    public int getFieldLength()
    {
        return this.fieldLength;
    }

    /**
     * Returns the decimal part. This is applicable
     * only if the field type if of numeric in nature.
     * <p>
     * If the field is specified to hold integral values
     * the value returned by this method will be zero.
     * </p>
     * @return decimal field size as int.
    */
    public int getDecimalCount()
    {
        return this.decimalCount;
    }

    /**
     * Sets the name of the field.
     * @param name Name of the field as String.
     * @return this element for initialization chaining
     * */
    public DBFField setName( final String name )
    {
        if( name == null) {
            throw new IllegalArgumentException( "Field name cannot be null" );
            }

        if( (name.length() == 0) || (name.length() > 10)) {
            throw new IllegalArgumentException( "Field name should be of length 0-10" );
            }

        this.fieldName      = name.getBytes();
        this.nameNullIndex  = this.fieldName.length;

        return this;
    }

    /**
     * Sets the data type of the field.
     *
     * @param value Type of the field. One of the following: C, L, N, F, D, M
     * @return this element for initialization chaining
     * @throws IllegalArgumentException if value is not in specified range
     */
    @SuppressWarnings("squid:S128")
    public DBFField setDataType( final byte value )
    {
        switch( value ) {
            case FIELD_TYPE_D:
                this.fieldLength = 8; /* fall through */
            case FIELD_TYPE_C:
            case FIELD_TYPE_L:
            case FIELD_TYPE_N:
            case FIELD_TYPE_F:
            case FIELD_TYPE_M:
                this.dataType = value;
                break;

            default:
                throw new IllegalArgumentException( "Unknown data type");
        }

        return this;
    }

    /**
     * Length of the field.
     *
     * This method should be called before calling setDecimalCount().
     *
     * @param length Length of the field as integer.
     * @return this element for initialization chaining
     */
    public DBFField setFieldLength( final int length )
    {
        if( length <= 0 ) {
            throw new IllegalArgumentException( "Field length should be a positive number" );
            }
        if( this.dataType == FIELD_TYPE_D ) {
            throw new UnsupportedOperationException( "Cannot do this on a Date field" );
            }

        this.fieldLength = length;

        return this;
    }

    /**
     * Sets the decimal place size of the field.
     * Before calling this method the size of the field
     * should be set by calling setFieldLength().
     *
     * @param value Size of the decimal field.
     * @return this element for initialization chaining
     */
    public DBFField setDecimalCount( final int value )
    {
        if( value < 0 ) {
            throw new IllegalArgumentException( "Decimal length should be a positive number" );
            }
        if( value > this.fieldLength ) {
            throw new IllegalArgumentException( "Decimal length should be less than field length" );
            }

        this.decimalCount = (byte)value;

        return this;
    }

}
