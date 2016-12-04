package com.googlecode.cchlib.util;

import static org.fest.assertions.api.Assertions.assertThat;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import com.googlecode.cchlib.io.IOHelper;
import com.googlecode.cchlib.lang.StringHelper;
import cx.ath.choisnet.util.FormattedProperties;
import cx.ath.choisnet.util.FormattedPropertiesLine;

@SuppressWarnings("resource")
public class FormattedPropertiesTest
{
    private static final String ISO_8859_1 = "ISO-8859-1";
    private static final String REF        = "FormattedPropertiesTest.properties";

    /**
     * Test content of one {@link Properties} with content of a {@link File}.
     * Verify that the file give same result than the standard {@link Properties}
     * using both {@link InputStream} an {@link Reader}, also verify
      *that the file give same result than the standard {@link FormattedProperties}
     * using both {@link InputStream} an {@link Reader}
     *
     * @param referenceProperties actual Properties
     * @param expectedProperties expected Properties
     */
    private static void compare( //
        final Properties    referenceProperties,
        final File          actualdFile //
        ) throws FileNotFoundException, IOException
    {
       { // Compare using an InputStream
            final Properties actualPropertiesUsingAnInputStream = getProperties( getStreamForProperties( actualdFile ) );

            compare( referenceProperties, actualPropertiesUsingAnInputStream );
            actualPropertiesUsingAnInputStream.clear();
        }
        { // Compare using an Reader
            final Properties actualPropertiesUsingAReader = getProperties( getReaderForProperties( actualdFile ) );
            compare( referenceProperties, actualPropertiesUsingAReader );
            actualPropertiesUsingAReader.clear();
        }
        { // Compare using an InputStream
            final FormattedProperties actualFormattedPropertiesUsingAnInputStream = getFormattedProperties( getStreamForProperties( actualdFile ) );
            compare( referenceProperties, actualFormattedPropertiesUsingAnInputStream );
            actualFormattedPropertiesUsingAnInputStream.clear();
        }
        { // Compare using an Reader
            final FormattedProperties actualFormattedPropertiesUsingAReader = getFormattedProperties( getReaderForProperties( actualdFile ) );
            compare( referenceProperties, actualFormattedPropertiesUsingAReader );
            actualFormattedPropertiesUsingAReader.clear();
        }
    }

    /**
     * Test content of two {@link Properties}. Verify standard Properties
     * have same content.
     *
     * @param referenceProperties expected Properties
     * @param actualProperties actual Properties
     */
    private static void compare( //
            final Properties referenceProperties, //
            final Properties actualProperties //
            )
    {
        final Set<String> referenceNames = referenceProperties.stringPropertyNames();
        final Set<String> actualNames    = actualProperties.stringPropertyNames();

        for( final String name : referenceNames ) {
            Assert.assertTrue( "Missing name entry : " + name, actualNames.contains( name ) );
        }
        for( final String name : actualNames ) {
            Assert.assertTrue( "Unexpected name entry : " + name, referenceNames.contains( name ) );
        }

        Assert.assertTrue( "missing name", referenceNames.containsAll( actualNames ) );
        Assert.assertTrue( "missing name", actualNames.containsAll( referenceNames ) );

        for( final String key : referenceNames ) {
            final String expecting = referenceProperties.getProperty( key );
            final String actual    = actualProperties.getProperty( key );

            assertThat( actual ).isEqualTo( expecting );
        }

        final int actual   = actualProperties.size();
        final int expected = referenceProperties.size();

        assertThat( actual  ).as( "bad size()" ).isEqualTo( expected );
    }

    private static void delete( final File file )
    {
        final boolean isDeleted = file.delete();

        Assert.assertTrue( "Can't delete:" + file, isDeleted );
    }

    /** get a file */
    private static File getCopy() throws IOException
    {
        return getCopy( FormattedPropertiesTest.class.getResourceAsStream( REF ) );
    }

    private static File getCopy( final InputStream is ) throws IOException
    {
        final File         file   = getTmpFile( "copy" );
        final OutputStream output = new FileOutputStream( file );

        IOHelper.copy( is, output );
        is.close();
        output.close();

        return file;
    }

    private static FormattedProperties getFormattedProperties( final InputStream is ) throws IOException
    {
        final FormattedProperties prop = new FormattedProperties();
        prop.load( is );
        is.close();

        return prop;
    }

    private static FormattedProperties getFormattedProperties( final Reader r ) throws IOException
    {
        final FormattedProperties prop = new FormattedProperties();
        prop.load( r );
        r.close();

        return prop;
    }

    private static Object getNull()
    {// Just to remove warning
        return null;
    }

    private static Properties getProperties( final InputStream is ) throws IOException
    {
        final Properties prop = new Properties();
        prop.load( is );
        is.close();

        return prop;
    }

    private static Properties getProperties( final Reader r ) throws IOException
    {
        final Properties prop = new Properties();
        prop.load( r );
        r.close();

        return prop;
    }

    private static Reader getReaderForProperties() //
            throws UnsupportedEncodingException
    {
        return new InputStreamReader( //
                FormattedPropertiesTest.class.getResourceAsStream( REF ), //
                ISO_8859_1 //
                );
    }

    private static Reader getReaderForProperties( final File file ) //
            throws UnsupportedEncodingException, FileNotFoundException
    {
        return new InputStreamReader( //
                new java.io.FileInputStream( file ), //
                ISO_8859_1 //
                );
    }

    private static InputStream getStreamForProperties()
    {
        return FormattedPropertiesTest.class.getResourceAsStream( REF );
    }

    private static InputStream getStreamForProperties( final File file ) //
            throws FileNotFoundException
    {
        return new java.io.FileInputStream( file );
    }

    private static Writer getWriterForProperties( final File file ) //
            throws UnsupportedEncodingException, FileNotFoundException
    {
        return new OutputStreamWriter( //
                new java.io.FileOutputStream( file ), //
                ISO_8859_1 //
                );
    }

    private static File getTmpFile( final String tag ) throws IOException
    {
        final File file = File.createTempFile( FormattedPropertiesTest.class.getSimpleName(), tag ); // $codepro.audit.disable

        // delete temporary file on JVM exit
        file.deleteOnExit();

        return file;
    }

    static void keepFile( final File file )
    {// Just for debugging!
        final File n = new File( file.getParent(), file.getName() + ".keep" );

        file.renameTo( n );
    }

    private static void store( final Properties prop, final OutputStream os ) throws IOException
    {
        prop.store( os, "comments" );
        os.close();
    }

    private static void store( final Properties prop, final Writer writer ) throws IOException
    {
        prop.store( writer, "comments" );
        writer.close();
    }

    private static File storeOutputStream( final Properties prop ) throws FileNotFoundException, IOException
    {
        final File tmpFile = getTmpFile( "OuputStream" );
        store( prop, new FileOutputStream( tmpFile ) );

        return tmpFile;
    }

    private static File storeWriter( final Properties prop ) throws IOException
    {
        final File tmpFile = getTmpFile( "writer" );
        store( prop, getWriterForProperties( tmpFile ) );

        return tmpFile;
    }

    // ---------------------------------------------------
    // ---------------------------------------------------

    private Properties  refPropertiesReader;
    private Properties  refPropertiesStream;

    @Before
    @Test
    public void setUp() throws IOException
    {
        this.refPropertiesStream = getProperties( getStreamForProperties() );
        this.refPropertiesReader = getProperties( getReaderForProperties() );

        // Something loaded ?
        Assert.assertTrue( "Should not empty", this.refPropertiesStream.size() != 0 );
        Assert.assertTrue( "Should not empty", this.refPropertiesReader.size() != 0 );

        // Verify standard Properties
        // give same result using Steam and Reader
        // for giving file
        compare( this.refPropertiesStream, this.refPropertiesReader );
    }

    // ---------------------------------------------------
    // ---------------------------------------------------

    @Test
    public void test_add() throws IOException
    {
        final File                copy = getCopy();
        final FormattedProperties prop = getFormattedProperties( getStreamForProperties( copy ) );

        prop.addBlankLine();
        prop.addCommentLine( StringHelper.EMPTY );
        prop.addCommentLine( "# New entries" );

        final String addKey = "new.key.put";
        final String addValue = "new value";

        prop.put( addKey, "tmp value" );
        prop.put( addKey, addValue );

        final Map<String, String> map = new HashMap<String, String>();

        for( int i = 0; i < 5; i++ ) {
            map.put( "map.key." + i, "value-" + i );
        }
        prop.putAll( map );

        Assert.assertEquals( "Can't find add value", addValue, prop.getProperty( addKey ) );

        for( final Map.Entry<String, String> entry : map.entrySet() ) {
            Assert.assertEquals( "Can't find add value", entry.getValue(), prop.getProperty( entry.getKey() ) );
        }

        {
            final File tmpWriterFile = storeWriter( prop );
            //java.lang.AssertionError: bad size() expected:<268> but was:<262>
            compare( prop, tmpWriterFile );
            delete( tmpWriterFile );
        }
        {
            final File tmpStreamFile = storeOutputStream( prop );
            //java.lang.AssertionError: bad size() expected:<268> but was:<262>
            compare( prop, tmpStreamFile );
            delete( tmpStreamFile );
        }

        store(prop,System.out);
        delete( copy );
    }

    @Test
    public void test_clear() throws FileNotFoundException, IOException
    {
        final File copy = getCopy();
        final FormattedProperties prop = getFormattedProperties( getStreamForProperties( copy ) );

        prop.clear();
        Assert.assertEquals( "must be empty", 0, prop.size() );

        delete( copy );
    }

    @Test
    public void test_clone() throws FileNotFoundException, IOException
    {
        final File                copy = getCopy();
        final FormattedProperties prop = getFormattedProperties( getStreamForProperties( copy ) );
        final FormattedProperties clone = (FormattedProperties)prop.clone();

        final List<FormattedPropertiesLine> lines  = prop.getLines();
        final List<FormattedPropertiesLine> clines = clone.getLines();

        final int linesSize  = lines.size();
        final int clinesSize = clines.size();
        final int size       = Math.max( linesSize, clinesSize );

        FormattedPropertiesLine l;
        FormattedPropertiesLine cl;

        for( int i = 0; i < size; i++ ) {
            l = cl = null;

            if( i < linesSize ) {
                l = lines.get( i );
            }
            if( i < clinesSize ) {
                cl = clines.get( i );
            }

            Assert.assertEquals( "Lines a diff !", l, cl );
        }

        Assert.assertEquals( "Must be same size (keys)", prop.size(), clone.size() );
        Assert.assertEquals( "Must be same size (lines)", prop.getLines().size(), clone.getLines().size() );

        final boolean r = prop.equals( clone );
        Assert.assertTrue( "Must be equals", r );

        delete( copy );
    }

    @Test
    public void test_equals() throws FileNotFoundException, IOException
    {
        final File                copy  = getCopy();
        final FormattedProperties prop1 = getFormattedProperties( getStreamForProperties( copy ) );
        final FormattedProperties prop2 = getFormattedProperties( getStreamForProperties( copy ) );

        boolean r = prop1.equals( prop1 );
        Assert.assertTrue( "Must be equals (same object)", r );

        r = prop1.equals( prop2 );
        Assert.assertTrue( "Must be equals", r );

        r = prop2.equals( prop1 );
        Assert.assertTrue( "Must be equals", r );

        r = prop1.equals( getNull() );
        Assert.assertFalse( "Must be not equals", r );

        final String key = "test.key";
        final String value = "a value";

        prop1.put( key, value );
        r = prop1.equals( prop2 );
        Assert.assertFalse( "Must be not equals", r );
        Assert.assertEquals( "Value not found", value, prop1.getProperty( key ) );

        prop2.put( key, value );
        r = prop1.equals( prop2 );
        Assert.assertTrue( "Must be equals", r );

        final String comment = "# a comment";

        prop2.addCommentLine( comment );
        r = prop1.equals( prop2 );
        Assert.assertFalse( "Must be not equals", r );

        prop1.addCommentLine( comment );
        r = prop1.equals( prop2 );
        Assert.assertTrue( "Must be equals", r );

        prop1.put( key, value + value );
        r = prop1.equals( prop2 );
        Assert.assertFalse( "Must be not equals", r );

        prop1.remove( key );
        r = prop1.equals( prop2 );
        Assert.assertFalse( "Must be not equals", r );

        prop2.remove( key );
        r = prop1.equals( prop2 );
        Assert.assertTrue( "Must be equals", r );

        delete( copy );
    }

    @SuppressWarnings({ "boxing" })
    @Test
    public void test_getLines() throws FileNotFoundException, IOException
    {
        final File                          copy  = getCopy();
        final FormattedProperties           prop  = getFormattedProperties( getStreamForProperties( copy ) );
        final List<FormattedPropertiesLine> lines = prop.getLines();
        final PrintStream                   ps    = System.out;
        int i = 1;

        for( final FormattedPropertiesLine line : lines ) {
            if( line.isComment() ) {
                ps.printf( "%d - %s\n", i, line.getContent() );
            } else {
                final String key = line.getContent();

                ps.printf( "%d - %s=%s\n", i, key, prop.getProperty( key ) );
            }
            i++;
        }

        delete( copy );
    }

    @Test
    public void test_Reader_load_save() throws IOException
    {
        final File                copy = getCopy();
        final FormattedProperties prop = getFormattedProperties( getReaderForProperties( copy ) );

        System.out.println( "->Reader" );
        compare( this.refPropertiesStream, prop );

        final File tmpWriterFile = storeWriter( prop );
        System.out.printf( "Reader->store(Writer)(%d):%s\n", Long.valueOf( tmpWriterFile.length() ), tmpWriterFile );
        compare( this.refPropertiesStream, tmpWriterFile );
        compare( // No Change ?
                this.refPropertiesStream, prop );

        final File tmpStreamFile = storeOutputStream( prop );
        System.out.printf( "Reader->store(Stream)(%d):%s\n", Long.valueOf( tmpStreamFile.length() ), tmpStreamFile );
        compare( this.refPropertiesStream, tmpStreamFile );
        compare( // No Change ?
                this.refPropertiesStream, prop );

        delete( copy );
        delete( tmpWriterFile );
        delete( tmpStreamFile );
    }

    @Test
    public void test_store_plusplus() throws FileNotFoundException, IOException
    {
        final File copy = getCopy();
        final File file = getTmpFile( "formatall" );

        final FormattedProperties prop = getFormattedProperties( getStreamForProperties( copy ) );
        final Writer              out  = getWriterForProperties( file );
        prop.store( out, EnumSet.allOf( FormattedProperties.Store.class ) );
        out.close();
        compare( prop, file );

        // keepFile(file);
        file.delete();
        copy.delete();
    }

    @SuppressWarnings({ "boxing" })
    @Test
    public void test_Stream_load_save() throws IOException
    {
        final File                copy = getCopy();
        final FormattedProperties prop = getFormattedProperties( getStreamForProperties( copy ) );
        System.out.println( "->Stream" );
        compare( this.refPropertiesStream, prop );

        final File tmpStreamFile = storeOutputStream( prop );
        compare( this.refPropertiesStream, tmpStreamFile );
        System.out.printf( "Stream->store(Stream)(%d):%s\n", tmpStreamFile.length(), tmpStreamFile );

        final File tmpWriterFile = storeWriter( prop );
        compare( this.refPropertiesStream, tmpWriterFile );
        System.out.printf( "Stream->store(Writer)(%d):%s\n", tmpWriterFile.length(), tmpWriterFile );

        delete( copy );
        delete( tmpWriterFile );
        delete( tmpStreamFile );
    }
}
