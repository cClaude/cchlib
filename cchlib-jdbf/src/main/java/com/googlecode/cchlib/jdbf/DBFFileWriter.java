package com.googlecode.cchlib.jdbf;

import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;

public class DBFFileWriter extends DBFWriter implements Closeable
{
    private RandomAccessFile    raf         = null;  /* Open and append records to an existing DBF */
    private int           recordCount = 0;

    /**
     * Creates a {@link DBFFileWriter} which can append to records to an
     * existing DBF file.
     *
     * @param dbfFile
     *            The file passed in should be a valid DBF file.
     * @throws DBFException
     *             if the passed in file does exist but not a valid DBF file, or
     *             if an IO error occurs.
     */
    public DBFFileWriter( final File dbfFile ) throws DBFException
    {
        try {
            this.raf = new RandomAccessFile( dbfFile, "rw" );

            /* before proceeding check whether the passed in File object is an
             * empty/non-existent file or not. */
            if( !dbfFile.exists() || (dbfFile.length() == 0) ) {
                return;
                }

            //this.header.read( this.raf );
            readHeaderFrom( this.raf );

            /* position file pointer at the end of the raf */
            this.raf.seek( this.raf.length() - 1 /* to ignore the END_OF_DATA
                                                  * byte at EoF */);
            }
        catch( final FileNotFoundException e ) {
            throw new DBFException( "Specified file is not found. "
                    + e.getMessage(), e );
            }
        catch( final IOException e ) {
            throw new DBFException( e.getMessage() + " while reading header", e );
            }

        //this.recordCount = this.header.getNumberOfRecords();
        this.recordCount = getHeaderNumberOfRecords();
    }

    /**
     * Sets fields.
     *
     * @param fields Array of fields to set
     * @throws DBFException if an IO error occurs.
     */
    @Override
    public void setFields( final DBFField[] fields ) throws DBFException
    {
        super.setFields( fields );

        try {
            if( (this.raf != null) && (this.raf.length() == 0) ) {
                /*this is a new/non-existent file. So write header before proceeding */
                //this.header.write( this.raf );
                writeHeaderFrom( this.raf );
                }
            }
        catch( final IOException e ) {
            throw new DBFException( "Error accesing file", e );
            }
    }

    /**
     * Add a record.
     *
     * @param values
     * @throws DBFException
     */
    @Override
    public void addRecord( final Object[] values ) throws DBFException
    {
        addRecordValidateParams( values );

//        if( this.raf == null ) {
//            this.records.add( values );
//        } else {
            try {
                writeRecord( this.raf, values );
                this.recordCount++;
            }
            catch( final IOException e ) {
                throw new DBFException( "Error occured while writing record. "
                        + e.getMessage(), e );
            }
//        }
    }

    /**
     * Writes the set data to the OutputStream.
     *
     * @param out output stream
     * @throws DBFException if any
     */
    @Override
    public void write( final OutputStream out ) throws DBFException
    {
        try {
            /* everything is written already. just update the header for
             * record count and the END_OF_DATA mark */
            //this.header.setNumberOfRecords( this.recordCount );
            setHeaderNumberOfRecords( this.recordCount );
            this.raf.seek( 0 );
            //this.header.write( this.raf );
            writeHeaderFrom( this.raf );
            this.raf.seek( this.raf.length() );
            this.raf.writeByte( END_OF_DATA );
            this.raf.close();

        }
        catch( final IOException e ) {
            throw new DBFException( e );
        }
    }

    @Override
    public void close() throws IOException
    {
        this.raf.close();
    }
}
