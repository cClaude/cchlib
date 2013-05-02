package com.googlecode.cchlib.jdbf;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

/**
 * DBFReader class can creates objects to represent DBF data.
 *
 * This Class is used to read data from a DBF file. Meta data and
 * records can be queried against this document.
 * <p>
 * DBFReader cannot write anything to a DBF file. For creating DBF
 * files use DBFWriter.
 * <p>
 * Fetching record is possible only in the forward direction
 * and cannot re-wound. In such situation, a suggested approach
 * is to reconstruct the object.
 * <p>
 * The nextRecord() method returns an array of Objects and the
 * types of these Object are as follows:
 * <table>
 * <tr><th>xBase Type</th><th>Java Type</th></tr>
 * <tr><td>C</td><td>String</td></tr>
 * <tr><td>N</td><td>Integer</td></tr>
 * <tr><td>F</td><td>Double</td></tr>
 * <tr><td>L</td><td>Boolean</td></tr>
 * <tr><td>D</td><td>java.util.Date</td></tr>
 * </table>
 */
public class DBFReader extends DBFBase
{
    private DataInputStream dataInputStream;
    private DBFHeader       header;

    /* Class specific variables */
    private boolean isClosed = true;
    private Map<String,Integer> fieldsNamesMap = new HashMap<String,Integer>();

    /**
     * Initializes a DBFReader object.
     *
     * When this constructor returns the object
     * will have completed reading the header (meta date) and
     * header information can be queried there on. And it will
     * be ready to return the first row.
     *
     * @param in where the data is read from.
     * @throws DBFException if any
     */
    public DBFReader( final InputStream in ) throws DBFException
    {
        try {
            this.dataInputStream    = new DataInputStream( in );
            this.isClosed           = false;
            this.header             = new DBFHeader();

            this.header.read( this.dataInputStream );

            /* it might be required to leap to the start of records at times */
            int t_dataStartIndex = this.header.headerLength - ( 32 + (32*this.header.fieldArray.length)) - 1;

            if( t_dataStartIndex > 0) {
                dataInputStream.skip( t_dataStartIndex);
                }
            }
        catch( IOException e ) {
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
        final InputStream   in,
        final boolean       useFirstRecordForFieldsNames
        )
        throws DBFException
    {
        this( in );

        if( useFirstRecordForFieldsNames ) {
            final Object[] record = private_nextRecord();

            for( int i = 0; i < record.length; i++ ) {
                Object o = record[ i ];

                if( o instanceof String ) {
                    DBFField f = getField( i );

                    try {
                        f.setName( String.class.cast( o ).trim() );
                        }
                    catch( IllegalArgumentException e ) { // $codepro.audit.disable emptyCatchClause, logExceptions
                        // No change for this field.
                        }
                    }
                else {
                    throw new DBFStructureException( "Field '" + i + "' is not a String : " + o );
                    }
                }
            --(this.header.numberOfRecords);
            refreshMapFieldIndex();
            }
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();

        sb.append( this.header.year )
          .append( '/' )
          .append( this.header.month )
          .append( '/' )
          .append( this.header.day )
          .append( '\n' )
          .append( "Total records: " )
          .append( this.header.numberOfRecords )
          .append( "\nHEader length: " )
          .append( this.header.headerLength );

        for( int i=0; i<this.header.fieldArray.length; i++) {
            sb.append( this.header.fieldArray[i].getName() );
            sb.append( '\n' );
            }

        return sb.toString();
    }

    private void refreshMapFieldIndex()
    {
        final int indexMax = this.header.fieldArray.length;

        this.fieldsNamesMap.clear();

        for( int i = 0; i<indexMax; i++ ) {
            this.fieldsNamesMap.put(
                this.header.fieldArray[ i ].getName(),
                i
                );
            }
    }


    /**
     * Returns the number of records in the DBF.
     * @return number of records in the DBF.
     */
    public int getRecordCount()
    {
        return this.header.numberOfRecords;
    }

    /**
     * Returns the asked Field. In case of an invalid index,
     * it returns a ArrayIndexOutofboundsException.
     *
     * @param index Index of the field. Index of the first field is zero.
     * @throws ArrayIndexOutofboundsException if index in not in range.
    */
    public DBFField getField( int index )
        throws DBFException
    {
        if( isClosed ) {
            throw new DBFClosedException( "Source is not open" );
            }

        return this.header.fieldArray[ index ];
    }

    /**
     * Returns the number of field in the DBF.
     */
    public int getFieldCount()
        throws DBFException
    {
        if( isClosed) {
            throw new DBFClosedException( "Source is not open" );
            }
        if( this.header.fieldArray != null) {
            return this.header.fieldArray.length;
            }

        return -1;
    }

    /**
     * Returns column number for giving columnName
     * @param columnName Column name to retrieve
     * @return column number for giving columnName
     * @throws DBFUnknownFieldNameException if columnName is not found
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
     */
    public DBFRecord nextRecord() throws DBFException
    {
        final Object[]      record  = private_nextRecord();
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
     * these arrays follow the convention mentioned in the class description.
     */
    private Object[] private_nextRecord()
        throws DBFException
    {
        if( isClosed) {
            throw new DBFClosedException( "Source is not open" );
            }

        Object recordObjects[] = new Object[ this.header.fieldArray.length];

        try {
            boolean isDeleted = false;

            do {
                if( isDeleted) {
                    dataInputStream.skip( this.header.recordLength-1);
                    }

                int t_byte = dataInputStream.readByte();
                if( t_byte == END_OF_DATA) {
                    return null;
                    }

                isDeleted = (  t_byte == '*');
            } while( isDeleted);

            for( int i=0; i<this.header.fieldArray.length; i++) {

                switch( this.header.fieldArray[i].getDataType()) {
                    case 'C':

                        byte b_array[] = new byte[ this.header.fieldArray[i].getFieldLength()];
                        dataInputStream.read( b_array);
                        recordObjects[i] = new String( b_array, characterSetName);
                        break;

                    case 'D':
                        byte t_byte_year[] = new byte[ 4];
                        dataInputStream.read( t_byte_year);

                        byte t_byte_month[] = new byte[ 2];
                        dataInputStream.read( t_byte_month);

                        byte t_byte_day[] = new byte[ 2];
                        dataInputStream.read( t_byte_day);

                        try {
                            Calendar calendar = new GregorianCalendar(
                                Integer.parseInt( new String( t_byte_year) ),
                                Integer.parseInt( new String( t_byte_month) ) - 1,
                                Integer.parseInt( new String( t_byte_day) )
                            	);

                            recordObjects[i] = calendar.getTime();
                        	}
                        catch( NumberFormatException e ) { // $codepro.audit.disable logExceptions
                            /* this field may be empty or may have improper value set */
                            recordObjects[i] = null;
                        }
                        break;

                    case 'F':
                        try {
                            byte t_float[] = new byte[ this.header.fieldArray[i].getFieldLength()];
                            dataInputStream.read( t_float);
                            t_float = Utils.trimLeftSpaces( t_float);
                            if( t_float.length > 0 && !Utils.contains( t_float, (byte)'?')) {

                                recordObjects[i] = new Float( new String( t_float));
                            }
                            else {
                                recordObjects[i] = null;
                            }
                        }
                        catch( NumberFormatException e) {
                            throw new DBFException( "Failed to parse Float: " + e.getMessage(), e );
                        }
                        break;

                    case 'N':
                        try {

                            byte t_numeric[] = new byte[ this.header.fieldArray[i].getFieldLength()];
                            dataInputStream.read( t_numeric);
                            t_numeric = Utils.trimLeftSpaces( t_numeric);

                            if( t_numeric.length > 0 && !Utils.contains( t_numeric, (byte)'?')) {
                                recordObjects[i] = new Double( new String( t_numeric));
                            }
                            else {
                                recordObjects[i] = null;
                            }
                        }
                        catch( NumberFormatException e ) {
                            throw new DBFException( "Failed to parse Number: " + e.getMessage(), e );
                        }
                        break;

                    case 'L':
                        byte t_logical = dataInputStream.readByte();
                        if( t_logical == 'Y' || t_logical == 't' || t_logical == 'T' || t_logical == 't') {
                            recordObjects[i] = Boolean.TRUE;
                        }
                        else {
                            recordObjects[i] = Boolean.FALSE;
                        }
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
        catch( EOFException e) { // $codepro.audit.disable logExceptions
            return null;
            }
        catch( IOException e) {
            throw new DBFException( e );
            }

        return recordObjects;
    }
}

