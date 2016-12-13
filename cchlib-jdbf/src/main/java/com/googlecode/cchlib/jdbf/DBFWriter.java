package com.googlecode.cchlib.jdbf;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.annotation.Nonnull;
import com.googlecode.cchlib.lang.StringHelper;

/**
 * An object of this class can create a DBF file.
 * <p>
 * Create an object, then define fields by creating DBFField objects and add
 * them to the DBFWriter object add records using the addRecord() method and
 * then call write() method.
 */
public class DBFWriter extends DBFBase
{
    /* other class variables */
    private final DBFHeader         header;
    private final List<Object[]>    records     = new ArrayList<>();
    private final Calendar          calendar    = new GregorianCalendar();

    // private boolean appendMode = false - not handled

    /**
     * Creates an empty Object.
     */
    public DBFWriter()
    {
        this.header = new DBFHeader();
    }

    protected void readHeaderFrom( final DataInput raf ) throws IOException
    {
        this.header.read( raf );
    }

    protected void writeHeaderFrom( final DataOutput raf ) throws IOException
    {
        this.header.write( raf );
    }

    protected int getHeaderNumberOfRecords()
    {
        return this.header.getNumberOfRecords();
    }

    protected void setHeaderNumberOfRecords( final int numberOfRecords )
    {
        this.header.setNumberOfRecords( numberOfRecords );
    }

    /**
     * Sets fields.
     *
     * @param fields Array of fields to set
     * @throws DBFException if an IO error occurs.
     */
    public void setFields( final DBFField[] fields ) throws DBFException
    {
        validateParams( fields );

        this.header.setDBFFields( fields );
    }

    private void validateParams( final DBFField[] fields )
        throws DBFStructureException
    {
        if( (fields == null) || (fields.length == 0) ) {
            throw new DBFStructureException( "Should have at least one field" );
            }

        for( int i = 0; i < fields.length; i++ ) {
            if( fields[ i ] == null ) {
                throw new DBFStructureException( "Field " + (i + 1)  + " is null" );
            }
        }
    }

    /**
     * Add a record.
     *
     * @param values Record to add
     * @throws DBFException if any
     */
    public void addRecord( final Object[] values ) throws DBFException
    {
        addRecordValidateParams( values );

        this.records.add( values );
    }

    protected void addRecordValidateParams( @Nonnull final Object[] values )
        throws DBFStructureException
    {
        if( this.header.getDBFFields() == null ) {
            throw new DBFStructureException( "Fields should be set before adding records" );
            }

        if( values == null ) {
            throw new DBFStructureException( "Null cannot be added as row" );
            }

        if( values.length != this.header.getDBFFieldSize() ) {
            throw new DBFStructureException( "Invalid record. Invalid number of fields in row" );
            }

        for( int i = 0; i < this.header.getDBFFieldSize(); i++ ) {
            if( values[ i ] != null ) {
                handleAddRecordForField( values, i );
            }
        }
    }

    private void handleAddRecordForField( final Object[] values, final int i )
        throws DBFStructureException
    {
        final DBFType dbfType = this.header.getDBFField( i ).getDBFType();

        try {
            dbfType.checkValue( values[ i ] );
        }
        catch( final DBFStructureException e ) {
            throw new DBFStructureException( e.getMessage() + ": " + i, e );
        }
    }

    /**
     * Writes the set data to the OutputStream.
     *
     * @param out output stream
     * @throws DBFException if any
     */
    public void write( final OutputStream out ) throws DBFException
    {
        try {
            final DataOutputStream outStream = new DataOutputStream( out );

            this.header.setNumberOfRecords( this.records.size() );
            this.header.write( outStream );

            /* Now write all the records */
            final int recCount = this.records.size();

            for( int i = 0; i < recCount; i++ ) { /* iterate through
                                                     * records */
                final Object[] values = this.records.get( i );

                writeRecord( outStream, values );
            }

            outStream.write( END_OF_DATA );
            outStream.flush();
        }
        catch( final IOException e ) {
            throw new DBFException( e );
        }
    }

    public void write() throws DBFException
    {
        this.write( null );
    }

    protected void writeRecord(
        final DataOutput dataOutput,
        final Object[]   objectArray //
        ) throws IOException
    {
        dataOutput.write( (byte)' ' );

        for( int index = 0; index < this.header.getDBFFieldSize(); index++ ) {
            handleWriteRecordForField( dataOutput, objectArray, index );
            }
    }

    private void handleWriteRecordForField(
        final DataOutput dataOutput,
        final Object[]   objectArray,
        final int        index
        ) throws IOException
    {
        switch( this.header.getDBFField( index ).getDataType() ) {
            case 'C':
                handleChars( dataOutput, objectArray, index );
                break;

            case 'D':
                handleDate( dataOutput, objectArray, index );
                break;

            case 'F':
            case 'N':
                handleFloat( dataOutput, objectArray, index );
                break;

            case 'L':
                handleBoolean( dataOutput, objectArray, index );
                break;

            case 'M':
                break;

            default:
                throw new DBFException( "Unknown field type "
                        + this.header.getDBFField( index ).getDataType() );
        }
    }

    private void handleBoolean( //
        final DataOutput dataOutput, //
        final Object[]   objectArray, //
        final int        index
        ) throws IOException
    {
        if( objectArray[ index ] != null ) {
            if( ((Boolean)objectArray[ index ]).booleanValue() ) {
                dataOutput.write( (byte)'T' );
            } else {
                dataOutput.write( (byte)'F' );
            }
        } else {
            dataOutput.write( (byte)'?' );
        }
    }

    private void handleChars( //
        final DataOutput dataOutput, //
        final Object[]   objectArray, //
        final int        index
        ) throws IOException
    {
        if( objectArray[ index ] != null ) {
            final String strValue = objectArray[ index ].toString();

            dataOutput.write( Utils.textPadding( strValue,
                    this.getCharacterSetName(),
                    this.header.getDBFField( index ).getFieldLength() ) );
        } else {
            dataOutput.write( Utils.textPadding(
                    StringHelper.EMPTY, this.getCharacterSetName(),
                    this.header.getDBFField( index ).getFieldLength() ) );
        }
    }

    private void handleFloat( //
            final DataOutput dataOutput, //
            final Object[]   objectArray, //
            final int        index
            ) throws IOException
    {
        if( objectArray[ index ] != null ) {
            dataOutput.write( Utils.doubleFormating(
                            (Double)objectArray[ index ],
                            this.getCharacterSetName(),
                            this.header.getDBFField( index )
                                    .getFieldLength(),
                            this.header.getDBFField( index )
                                    .getDecimalCount() ) );
        } else {
            dataOutput.write( Utils.textPadding( "?",
                    this.getCharacterSetName(),
                    this.header.getDBFField( index ).getFieldLength(),
                    Utils.ALIGN_RIGHT ) );
        }
    }

    private void handleDate( //
            final DataOutput dataOutput, //
            final Object[] objectArray, //
            final int j ) throws IOException
    {
        if( objectArray[ j ] != null ) {
            this.calendar.setTime( (Date)objectArray[ j ] );

            dataOutput.write( String.valueOf(
                    this.calendar.get( Calendar.YEAR ) ).getBytes() );
            dataOutput.write( Utils.textPadding( String
                    .valueOf( this.calendar.get( Calendar.MONTH ) + 1 ),
                    this.getCharacterSetName(), 2, Utils.ALIGN_RIGHT,
                    (byte)'0' ) );
            dataOutput.write( Utils.textPadding(
                    String.valueOf( this.calendar
                            .get( Calendar.DAY_OF_MONTH ) ),
                    this.getCharacterSetName(), 2, Utils.ALIGN_RIGHT,
                    (byte)'0' ) );
        } else {
            dataOutput.write( "        ".getBytes() );
        }
    }
}
