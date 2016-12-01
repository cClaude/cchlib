package com.googlecode.cchlib.jdbf;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Class for reading the metadata assuming that the given
 * {@link InputStream} carries DBF data.
 */
class DBFHeader
{
    private static final byte SIG_DBASE_III = (byte)0x03;

    /* DBF structure start here */
    private byte       signature;              /* 0 */
    private byte       year;                   /* 1 */
    private byte       month;                  /* 2 */
    private byte       day;                    /* 3 */
    private int        numberOfRecords;        /* 4-7 */
    private short      headerLength;           /* 8-9 */
    private short      recordLength;           /* 10-11 */
    private short      reserv1;                /* 12-13 */
    private byte       incompleteTransaction;  /* 14 */
    private byte       encryptionFlag;         /* 15 */
    private int        freeRecordThread;       /* 16-19 */
    private int        reserv2;                /* 20-23 */
    private int        reserv3;                /* 24-27 */
    private byte       mdxFlag;                /* 28 */
    private byte       languageDriver;         /* 29 */
    private short      reserv4;                /* 30-31 */
    private DBFField[] fieldArray;             /* each 32 bytes */
    private final byte terminator1;            /* n+1 */
    @SuppressWarnings("unused") // Not use, but legal structure
    private byte[]     databaseContainer;      /* 263 bytes */
    /* DBF structure ends here */

    DBFHeader()
    {
        this.signature   = SIG_DBASE_III;
        this.terminator1 = 0x0D;
    }

    DBFField getDBFField( final int index )
    {
        return this.fieldArray[ index ];
    }

    DBFField[] getDBFFields()
    {
        return this.fieldArray;
    }

    void setDBFFields( final DBFField[] fields )
        throws DBFStructureException
    {
        if( this.fieldArray != null ) {
            throw new DBFStructureException( "Fields has already been set" );
            }

        this.fieldArray = fields;
    }

    int getDBFFieldSize()
    {
        return this.fieldArray.length;
    }

    void read( final DataInput dataInput) throws IOException
    {
        this.signature = dataInput.readByte(); /* 0 */
        this.year = dataInput.readByte();      /* 1 */
        this.month = dataInput.readByte();     /* 2 */
        this.day = dataInput.readByte();       /* 3 */
        this.numberOfRecords = Utils.readLittleEndianInt( dataInput); /* 4-7 */

        this.headerLength = Utils.readLittleEndianShort( dataInput); /* 8-9 */
        this.recordLength = Utils.readLittleEndianShort( dataInput); /* 10-11 */

        this.reserv1 = Utils.readLittleEndianShort( dataInput);      /* 12-13 */
        this.incompleteTransaction = dataInput.readByte();           /* 14 */
        this.encryptionFlag = dataInput.readByte();                  /* 15 */
        this.freeRecordThread = Utils.readLittleEndianInt( dataInput); /* 16-19 */
        this.reserv2 = dataInput.readInt();                            /* 20-23 */
        this.reserv3 = dataInput.readInt();                            /* 24-27 */
        this.mdxFlag = dataInput.readByte();                           /* 28 */
        this.languageDriver = dataInput.readByte();                    /* 29 */
        this.reserv4 = Utils.readLittleEndianShort( dataInput);        /* 30-31 */

        final List<DBFField>  fields = new ArrayList<>();
        DBFField        field    = DBFField.createField( dataInput); /* 32 each */

        while( field != null ) {
            fields.add( field );
            field = DBFField.createField( dataInput );
        }

        this.fieldArray = new DBFField[ fields.size()];

        for( int i=0; i<this.fieldArray.length; i++) {
            this.fieldArray[ i ] = fields.get( i );
            }
     }

    void write( final DataOutput dataOutput) throws IOException
    {
        dataOutput.writeByte( this.signature ); /* 0 */

        final GregorianCalendar calendar = new GregorianCalendar();
        this.year  = (byte)( calendar.get( Calendar.YEAR) - 1900 );
        this.month = (byte)( calendar.get( Calendar.MONTH ) + 1 );
        this.day   = (byte)( calendar.get( Calendar.DAY_OF_MONTH) );

        dataOutput.writeByte( this.year );  /* 1 */
        dataOutput.writeByte( this.month ); /* 2 */
        dataOutput.writeByte( this.day );   /* 3 */

        this.numberOfRecords = Utils.littleEndian( this.numberOfRecords );
        dataOutput.writeInt( this.numberOfRecords ); /* 4-7 */

        this.headerLength = findHeaderLength();
        dataOutput.writeShort( Utils.littleEndian( this.headerLength ) ); /* 8-9 */

        this.recordLength = findRecordLength();
        dataOutput.writeShort( Utils.littleEndian( this.recordLength ) ); /* 10-11 */

        dataOutput.writeShort( Utils.littleEndian( this.reserv1 ) ); /* 12-13 */
        dataOutput.writeByte( this.incompleteTransaction ); /* 14 */
        dataOutput.writeByte( this.encryptionFlag ); /* 15 */
        dataOutput.writeInt( Utils.littleEndian( this.freeRecordThread ) );/* 16-19 */
        dataOutput.writeInt( Utils.littleEndian( this.reserv2 ) ); /* 20-23 */
        dataOutput.writeInt( Utils.littleEndian( this.reserv3 ) ); /* 24-27 */

        dataOutput.writeByte( this.mdxFlag ); /* 28 */
        dataOutput.writeByte( this.languageDriver ); /* 29 */
        dataOutput.writeShort( Utils.littleEndian( this.reserv4 ) ); /* 30-31 */

        for( int i=0; i<this.fieldArray.length; i++ ) {
            this.fieldArray[ i ].write( dataOutput );
            }

        dataOutput.writeByte( this.terminator1 ); /* n+1 */
    }

    private short findHeaderLength()
    {
        return (short)(
        1+
        3+
        4+
        2+
        2+
        2+
        1+
        1+
        4+
        4+
        4+
        1+
        1+
        2+
        (32*this.fieldArray.length)+
        1
        );
    }

    private short findRecordLength()
    {
        int lRecordLength = 0;

        for( int i=0; i<this.fieldArray.length; i++) {
            lRecordLength += this.fieldArray[i].getFieldLength();
            }

        return (short)(lRecordLength + 1);
    }

    short getRecordLength()
    {
        return this.recordLength;
    }

    short getHeaderLength()
    {
        return this.headerLength;
    }

    byte getYear()
    {
        return this.year;
    }

    byte getMonth()
    {
        return this.month;
    }

    int getNumberOfRecords()
    {
        return this.numberOfRecords;
    }

    void setNumberOfRecords( final int numberOfRecords )
    {
        this.numberOfRecords = numberOfRecords;
    }

    byte getDay()
    {
        return this.day;
    }
}
