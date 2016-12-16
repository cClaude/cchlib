package com.googlecode.cchlib.jdbf.userpackage;

import static org.fest.assertions.api.Assertions.assertThat;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.junit.Test;
import com.googlecode.cchlib.jdbf.DBFEntry;
import com.googlecode.cchlib.jdbf.DBFException;
import com.googlecode.cchlib.jdbf.DBFField;
import com.googlecode.cchlib.jdbf.DBFFileWriter;
import com.googlecode.cchlib.jdbf.DBFReader;
import com.googlecode.cchlib.jdbf.DBFRecord;
import com.googlecode.cchlib.jdbf.DBFType;
import com.googlecode.cchlib.jdbf.DBFWriter;

public class DBFTest
{
    private static void print( final String s )
    {
        System.out.print( s );
    }

    private static void println( final String s )
    {
        System.out.println( s );
    }

    private static File createTempFile() throws IOException
    {
        final File file = File.createTempFile( "DBFTest", ".dbf" );
        file.deleteOnExit();
        return file;
    }

    private static DBFWriter createDBFWriter(
        final String fieldName,
        final double value
        ) throws DBFException
    {
        //final DBFField field = createDBFField( fieldName, DBFField.FIELD_TYPE_N ) -
        final DBFField field = DBFType.NUMERICAL.newDBFField( fieldName );

        final DBFWriter writer = new DBFWriter();

        writer.setFields( new DBFField[] { field               } );
        writer.addRecord( new Object[]   { new Double( value ) } );

        return writer;
    }

    private static File createDBFFile() throws IOException
    {
        final File file = createTempFile();

        final DBFWriter writer = createDBFWriter( "F1", 3D );

        try( final OutputStream out = new FileOutputStream( file ) ) {
            writer.write( out );
            out.flush();
        }

        return file;
    }

    @Test
    public void test1() throws Exception
    {
        print( "Creating an empty DBFWriter object... ");
        final DBFWriter writer = new DBFWriter();
        println( "OK: " + writer );
    }

    @Test
    public void test2() throws Exception
    {
        print( "Creating an empty DBFField object... ");
        final DBFField field = new DBFField();
        println( "OK: " + field );
    }

    @Test
    public void test3() throws Exception
    {
        print( "Writing a sample DBF file ... ");


        final File      file   = createTempFile();
        final DBFWriter writer = createDBFWriter( "F1", 3D );

        try( final OutputStream out = new FileOutputStream( file ) ) {
            writer.write( out );
            out.flush();
        }

        println( "OK.");
    }

    @Test
    public void test4() throws Exception
    {
        print( "Reading the written file ..." );

        final File file = createDBFFile();

        try( final InputStream in = new FileInputStream( file ) ) {
            final DBFReader reader = new DBFReader( in );
            print( "\tRecord count=" + reader.getRecordCount() );
        }

        println( " OK.");
    }

    @Test
    public void checkDataType_N() throws Exception
    {
        final long expectedLong = 123456789012345L;
        final File file         = createTempFile();

        try( final OutputStream out = new FileOutputStream( file ) ) {
            final DBFWriter writer = new DBFWriter();
            final DBFField  field  = new DBFField()
                .setName( "F1" )
                .setDataType( DBFField.FIELD_TYPE_N )
                .setFieldLength( 15 )
                .setDecimalCount( 0 );

            writer.setFields( new DBFField[] { field } );

            final Double value = new Double( expectedLong );

            writer.addRecord( new Object[] { value } );

            print( " written=" + value );
            writer.write( out );
        }

        try( final InputStream in = new FileInputStream( file ) ) {
            final DBFReader reader = new DBFReader( in );

            final DBFRecord   record = reader.nextRecord();
            final DBFEntry    entry  = record.getDBFEntry( 0 );
            final Object      value  = entry.getValue();

            print( " read=" + value );

            assertThat( value ).isInstanceOf( Double.class );
            assertThat( (Double)value ).isEqualTo( expectedLong );
        }
    }

    @Test
    public void checkRAFwriting() throws Exception
    {
        print( "Writing in RAF mode ... ");

        final File file = createTempFile();

        assertThat( file ).isFile();
        assertThat( file.length() ).isEqualTo( 0L );

        try( final DBFFileWriter writer1 = new DBFFileWriter( file ) ) {
            writer1.setFields(
                new DBFField[] {
                    DBFType.CHARACTERS
                        .newDBFField( "F1" )
                        .setFieldLength( 10 ),
                    DBFType.NUMERICAL
                        .newDBFField("F2" )
                        .setFieldLength( 2 )
                } );

            writer1.addRecord( new Object[] { "Red",  new Double( 10 ) } );
            writer1.addRecord( new Object[] { "Blue", new Double( 20 ) } );
            writer1.write();
        }
        println( "done.");

        print( "Appending to this file");
        try( final DBFFileWriter writer2 = new DBFFileWriter( file ) ) {
            writer2.addRecord( new Object[] {"Green" , new Double( 33 )} );
            writer2.addRecord( new Object[] {"Yellow", new Double( 44 )} );
            writer2.write();
        }

        println( "done.");
    }
}
