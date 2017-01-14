package com.googlecode.cchlib.jdbf;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;

/**
 * DBFReader class can creates objects to represent DBF data.
 *
 * This Class is used to read data from a DBF file. Meta data and
 * records can be queried against this document.
 *
 * <p>DBFReader cannot write anything to a DBF file. For creating DBF
 * files use DBFWriter.
 *
 * <p>Fetching record is possible only in the forward direction
 * and cannot re-wound. In such situation, a suggested approach
 * is to reconstruct the object.
 *
 * <p>The nextRecord() method returns an array of Objects and the
 * types of these Object are as follows:
 *
 * <table summary="xBase vs Java types">
 * <tr><th>xBase Type</th><th>Java Type</th></tr>
 * <tr><td>C</td><td>String</td></tr>
 * <tr><td>N</td><td>Integer</td></tr>
 * <tr><td>F</td><td>Double</td></tr>
 * <tr><td>L</td><td>Boolean</td></tr>
 * <tr><td>D</td><td>java.util.Date</td></tr>
 * </table>
 */
@SuppressWarnings({
    "squid:S2674" //The value returned from a stream read should be checked
    })
public class DBFReader extends DBFBase
{
    private static final Logger LOGGER = Logger.getLogger( DBFReader.class );

    private static final String SOURCE_IS_NOT_OPEN = "Source is not open";

    private DataInputStream dataInputStream;
    private DBFHeader       header;

    /* Class specific variables */
    private boolean                   isClosed       = true;
    private final Map<String,Integer> fieldsNamesMap = new HashMap<>();

    /**
     * Initializes a DBFReader object.
     *
     * When this constructor returns the object will have completed reading the header (meta date) and header
     * information can be queried there on. And it will be ready to return the first row.
     *
     * @param in
     *            where the data is read from.
     * @throws DBFException
     *             if any
     */
    public DBFReader( final InputStream in ) throws DBFException
    {
        try {
            this.dataInputStream = new DataInputStream( in );
            this.isClosed        = false;
            this.header          = new DBFHeader();

            this.header.read( this.dataInputStream );

            /* it might be required to leap to the start of records at times */
            final int dataStartIndex = this.header.getHeaderLength() - (32 + (32 * this.header.getDBFFieldSize() )) - 1;

            if( dataStartIndex > 0 ) {
                final long skiped = this.dataInputStream.skip( dataStartIndex );

                if( skiped != dataStartIndex ) {
                    throw new DBFException( "Cannot not find data" );
                }
            }
        }
        catch( final IOException e ) {
            throw new DBFException( e );
        }

        refreshMapFieldIndex();
    }

    /**
     * Initializes a DBFReader object.
     *
     * @param in where the data is read from.
     * @param useFirstRecordForFieldsNames if true try to use first record to set fields names,
     * first record is no more available.
     * @throws DBFException if any
     */
    public DBFReader(
        final InputStream in,
        final boolean     useFirstRecordForFieldsNames
        )
        throws DBFException
    {
        this( in );

        if( useFirstRecordForFieldsNames ) {
            final Object[] record = privateNextRecord();

            for( int i = 0; i < record.length; i++ ) {
                final Object o = record[ i ];

                if( o instanceof String ) {
                    setName( getField( i ), (String)o );
                    }
                else {
                    throw new DBFStructureException( "Field '" + i + "' is not a String : " + o );
                    }
                }

            this.header.setNumberOfRecords( this.header.getNumberOfRecords() - 1 );

            refreshMapFieldIndex();
            }
    }

    private void setName( final DBFField field, final String name )
    {
        final String nameTrim = name.trim();

        try {
            field.setName( nameTrim );
            }
        catch( final IllegalArgumentException e ) {
            // No change for this field.
            LOGGER.warn( "DBFField:" + field + " can not set name: " + nameTrim, e );
            }
    }

    @Override
    public String toString()
    {
        final StringBuilder sb = new StringBuilder();

        sb.append( this.header.getYear() )
          .append( '/' )
          .append( this.header.getMonth() )
          .append( '/' )
          .append( this.header.getDay() )
          .append( '\n' )
          .append( "Total records: " )
          .append( this.header.getNumberOfRecords() )
          .append( "\nHEader length: " )
          .append( this.header.getHeaderLength() );

        for( int i=0; i<this.header.getDBFFieldSize(); i++) {
            sb.append( this.header.getDBFField( i ).getName() );
            sb.append( '\n' );
            }

        return sb.toString();
    }

    private void refreshMapFieldIndex()
    {
        final int indexMax = this.header.getDBFFieldSize();

        this.fieldsNamesMap.clear();

        for( int i = 0; i<indexMax; i++ ) {
            this.fieldsNamesMap.put(
                this.header.getDBFField( i ).getName(),
                Integer.valueOf( i )
                );
            }
    }


    /**
     * Returns the number of records in the DBF.
     * @return number of records in the DBF.
     */
    public int getRecordCount()
    {
        return this.header.getNumberOfRecords();
    }

    /**
     * Returns the asked field. In case of an invalid index, it returns
     * a ArrayIndexOutofboundsException.
     *
     * @param index
     *            Index of the field. Index of the first field is zero.
     * @return the asked field
     * @throws ArrayIndexOutOfBoundsException
     *             if index in not in range.
     * @throws DBFException
     *              if file is not open
     */
    public DBFField getField( final int index ) throws DBFException
    {
        if( this.isClosed ) {
            throw new DBFClosedException( SOURCE_IS_NOT_OPEN );
            }

        return this.header.getDBFField( index );
    }

    /**
     * Returns the number of field in the DBF.
     *
     * @return the number of field in the DBF.
     * @throws DBFException
     *             if file is not open
     */
    public int getFieldCount()
        throws DBFException
    {
        if( this.isClosed) {
            throw new DBFClosedException( SOURCE_IS_NOT_OPEN );
            }
        if( this.header.getDBFFields() != null) {
            return this.header.getDBFFieldSize();
            }

        return -1;
    }

    /**
     * Returns column number for giving columnName
     *
     * @param columnName
     *            Column name to retrieve
     * @return column number for giving columnName
     * @throws DBFUnknownFieldNameException
     *             if columnName is not found
     */
    public int getColumnNumber( final String columnName )
        throws DBFUnknownFieldNameException
    {
        final Integer columnNumber = this.fieldsNamesMap.get( columnName );

        if( columnNumber != null ) {
            return columnNumber.intValue();
            }
        throw new DBFUnknownFieldNameException( columnName );
    }

    /**
     * Returns the next row in the DBF stream as a {@link DBFRecord}
     *
     * @return The next row as a {@link DBFRecord}.
     * @throws DBFException if any
     */
    public DBFRecord nextRecord() throws DBFException
    {
        final Object[]      record  = privateNextRecord();
        final DBFEntry[]    entries = new DBFEntry[ record.length ];

        for( int i = 0; i<record.length; i++ ) {
            final DBFField  field = getField( i );
            final Object    value = record[ i ];

            entries[ i ] = new DBFEntryImpl( value, field );
            }

        return new DBFRecordImpl( this, entries );
    }

    /**
     * Reads the returns the next row in the DBF stream.
     *
     * @returns The next row as an Object array. Types of the elements
     *          these arrays follow the convention mentioned in
     *          the class description.
     */
    @SuppressWarnings({
        "squid:S1168", // null is EOF
        "squid:S1166" // Don't wan't to preserve EOFException
    })
    private Object[] privateNextRecord() throws DBFException
    {
        if( this.isClosed ) {
            throw new DBFClosedException( SOURCE_IS_NOT_OPEN );
            }

        final Object[] recordObjects = new Object[ this.header.getDBFFieldSize()];

        try {
            boolean isDeleted = false;

            do {
                if( isDeleted ) {
                    this.dataInputStream.skip( this.header.getRecordLength() - 1L );
                    }

                final int abyte = this.dataInputStream.readByte();

                if( abyte == END_OF_DATA ) {
                    return null;
                    }

                isDeleted = abyte == '*';
            } while( isDeleted );

            privateNextRecord( recordObjects );
        }
        catch( final EOFException e ) {
            return null;
            }
        catch( final IOException e) {
            throw new DBFException( e );
            }

        return recordObjects;
    }

    private void privateNextRecord( final Object[] recordObjects )
        throws IOException
    {
        for( int i=0; i<this.header.getDBFFieldSize(); i++) {

            switch( this.header.getDBFField( i ).getDataType()) {
                case 'C':
                    handleString( recordObjects, i );
                    break;

                case 'D':
                    handleDate( recordObjects, i );
                    break;

                case 'F':
                    handleFloat( recordObjects, i );
                    break;

                case 'N':
                    handleNumeric( recordObjects, i );
                    break;

                case 'L':
                    handleLogical( recordObjects, i );
                    break;

                case 'M':
                    // TODO Later
                    recordObjects[i] = "null";
                    break;

                default:
                    recordObjects[i] = "null";
            }
        }
    }

    private void handleLogical( final Object[] recordObjects, final int i ) throws IOException
    {
        final byte logical = this.dataInputStream.readByte();

        if( (logical == 'Y') || (logical == 'y') || (logical == 'T') || (logical == 't')) {
            recordObjects[i] = Boolean.TRUE;
        }
        else {
            recordObjects[i] = Boolean.FALSE;
        }
    }

    private void handleNumeric( final Object[] recordObjects, final int i )
        throws IOException
    {
        try {
            byte[] numeric = new byte[ this.header.getDBFField( i ).getFieldLength()];

            this.dataInputStream.read( numeric );
            numeric = Utils.trimLeftSpaces( numeric );

            if( (numeric.length > 0) && !Utils.contains( numeric, (byte)'?')) {
                recordObjects[i] = new Double( new String( numeric ) );
            }
            else {
                recordObjects[i] = null;
            }
        }
        catch( final NumberFormatException e ) {
            throw new DBFException( "Failed to parse Number: " + e.getMessage(), e );
        }
    }

    @SuppressWarnings("squid:RedundantThrowsDeclarationCheck")
    private void handleFloat( final Object[] recordObjects, final int i )
        throws IOException, DBFException
    {
        try {
            byte[] afloat = new byte[ this.header.getDBFField( i ).getFieldLength()];

            this.dataInputStream.read( afloat );
            afloat = Utils.trimLeftSpaces( afloat );
            if( (afloat.length > 0) && !Utils.contains( afloat, (byte)'?')) {

                recordObjects[i] = new Float( new String( afloat ) );
            }
            else {
                recordObjects[i] = null;
            }
        }
        catch( final NumberFormatException e ) {
            throw new DBFException( "Failed to parse Float: " + e.getMessage(), e );
        }
    }

    private void handleDate( final Object[] recordObjects, final int i ) throws IOException
    {
        final byte[] byteYear = new byte[ 4 ];

        this.dataInputStream.read( byteYear );

        final byte[] byteMonth = new byte[ 2 ];
        this.dataInputStream.read( byteMonth );

        final byte[] byteDay = new byte[ 2 ];
        this.dataInputStream.read( byteDay );

        try {
            final Calendar calendar = new GregorianCalendar(
                Integer.parseInt( new String( byteYear ) ),
                Integer.parseInt( new String( byteMonth ) ) - 1,
                Integer.parseInt( new String( byteDay ) )
                );

            recordObjects[i] = calendar.getTime();
            }
        catch( final NumberFormatException e ) { // $codepro.audit.disable logExceptions
            /* this field may be empty or may have improper value set */
            recordObjects[i] = null;
        }
    }

    private void handleString( final Object[] recordObjects, final int i )
        throws IOException
    {
        final byte[] array = new byte[ this.header.getDBFField( i ).getFieldLength()];

        this.dataInputStream.read( array );
        recordObjects[i] = new String( array, getCharacterSetName() );
    }
}

