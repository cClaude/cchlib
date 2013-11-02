// $codepro.audit.disable com.instantiations.assist.eclipse.analysis.pathManipulation, reusableImmutables
package com.googlecode.cchlib.jdbf;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import org.junit.Test;

public class JavaDBFTest
{
    private void print( String s )
    {
        System.out.print( s);
    }

    private void println( String s )
    {
        System.out.println( s );
    }

    private File getTempDirFile()
    {
        String tmpdir = System.getProperty( "java.io.tmpdir" );

        return new File( tmpdir );
    }

    private File getTempFile( String filename )
    {
        return new File( getTempDirFile(), filename );
    }

    @Test
    public void test1() throws Exception
    {
        print( "Creating an empty DBFWriter object... ");
        DBFWriter writer = new DBFWriter();
        println( "OK: " + writer );
    }

    @Test
    public void test2() throws Exception
    {
        print( "Creating an empty DBFField object... ");
        DBFField field = new DBFField();
        println( "OK: " + field );
    }

    @Test
    public void test3() throws Exception
    {
        print( "Writing a sample DBF file ... ");
        DBFField field = new DBFField();
        field.setName( "F1");
        field.setDataType( DBFField.FIELD_TYPE_N);
        DBFWriter writer = new DBFWriter();
        writer.setFields( new DBFField[] { field});
        writer.addRecord( new Object[] {new Double( 3)});
        //FileOutputStream fos = new FileOutputStream( "/tmp/121212.dbf");
        FileOutputStream fos = new FileOutputStream( getTempFile( "121212.dbf" ) );
        writer.write( fos);
        fos.flush();
        fos.close();
        println( "OK.");
    }

    @Test
    public void test4() throws Exception
    {
        print( "Reading the written file ..." );
        //FileInputStream fis = new FileInputStream( "/tmp/121212.dbf");
        FileInputStream fis = new FileInputStream( getTempFile( "121212.dbf" ) );
        DBFReader reader = new DBFReader( fis );
        print( "\tRecord count=" + reader.getRecordCount());
        fis.close();
        println( " OK.");
    }

    @Test
    public void checkDataType_N() throws Exception
    {
        //FileOutputStream fos = new FileOutputStream( "/tmp/121212.dbf");
        FileOutputStream fos = new FileOutputStream( getTempFile( "121212.dbf" ) );
        DBFWriter writer = new DBFWriter();
        DBFField field = new DBFField();
        field.setName( "F1");
        field.setDataType( DBFField.FIELD_TYPE_N);
        field.setFieldLength( 15);
        field.setDecimalCount( 0);

        writer.setFields( new DBFField[] { field});
        Double value = new Double( 123456789012345L);
        writer.addRecord( new Object[] { value});
        print( " written=" + value);
        writer.write( fos);
        fos.close();

        //FileInputStream fis = new FileInputStream( "/tmp/121212.dbf");
        FileInputStream fis = new FileInputStream( getTempFile( "121212.dbf" ) );
        DBFReader reader = new DBFReader( fis);

        //Object[] values = reader.nextRecord();
        DBFRecord   record = reader.nextRecord();
        //DBFEntry    entry   = record.getDBFEntries()[ 0 ];
        DBFEntry    entry   = record.getDBFEntry( 0 );
        Object      v       = entry.getValue();

        //print( " read=" + (Double)values[0]);
        print( " read=" + (Double)v);
        //println( " written == read (" + (((Double)values[0]).equals( value)) + ")");
        println( " written == read (" + (((Double)v).equals(value)) + ")");
        fis.close();
    }

    @Test
    public void checkRAFwriting() throws Exception
    {
        print( "Writing in RAF mode ... ");
        //File file = new File( "/tmp/raf-1212.dbf");
        File file = new File( getTempDirFile(), "raf-1212.dbf" );

        if( file.exists()) {
            file.delete();
        }
        DBFWriter writer = new DBFWriter( file);

        DBFField []fields = new DBFField[2];

        fields[0] = new DBFField();
        fields[0].setName( "F1");
        fields[0].setDataType( DBFField.FIELD_TYPE_C);
        fields[0].setFieldLength( 10);

        fields[1] = new DBFField();
        fields[1].setName( "F2");
        fields[1].setDataType( DBFField.FIELD_TYPE_N);
        fields[1].setFieldLength( 2);

        writer.setFields( fields);

        Object []record = new Object[2];
        record[0] = "Red";
        record[1] = new Double( 10);

        writer.addRecord( record);

        record = new Object[2];
        record[0] = "Blue";
        record[1] = new Double( 20);

        writer.addRecord( record);

        writer.write();
        println( "done.");

        print( "Appending to this file");

        writer = new DBFWriter( file);

        record = new Object[2];
        record[0] = "Green";
        record[1] = new Double( 33);

        writer.addRecord( record);

        record = new Object[2];
        record[0] = "Yellow";
        record[1] = new Double( 44);

        writer.addRecord( record);

        writer.write();
        println( "done.");
    }
}
