package cx.ath.choisnet.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.Reader;
import java.io.Writer;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import com.googlecode.cchlib.Const;
import com.googlecode.cchlib.io.IOHelper;

public class FormattedPropertiesTest
{
    private final static String REF = "tstref.properties";
    private Properties refPropertiesStream;
    private Properties refPropertiesReader;

    @Before
    @Test
    public void setUp() throws IOException
    {
        refPropertiesStream = getProperties(
                getClass().getResourceAsStream( REF )
                );
        refPropertiesReader = getProperties(
                new InputStreamReader(
                    getClass().getResourceAsStream( REF ),
                    "ISO-8859-1"
                    )
                );
//        File f = storeOutputStream(refPropertiesReader);
//        System.out.println("ref="+f);//TO DO
//        final String key = "multi.lines.message";
//        System.out.println( key + "=" +
//                refPropertiesReader.getProperty( key )
//                );

        // Something loaded ?
        Assert.assertTrue("Should not empty",0 != refPropertiesStream.size() );
        Assert.assertTrue("Should not empty",0 != refPropertiesReader.size() );

        // Verify standard Properties
        // give same result using Steam and Reader
        // for giving file
        compare(
                refPropertiesStream,
                refPropertiesReader
                );
    }

    @Test
    @Ignore//FIXME
    public void test_Reader_load_save() throws IOException
    {
        File                copy = getCopy();
        FormattedProperties prop = getFormattedProperties(
                new FileReader(copy)
                );

        System.out.println("->Reader");
        compare(
                refPropertiesStream,
                prop
                );

        File tmpWriterFile = storeWriter(prop);
        System.out.printf(
                "Reader->store(Writer)(%d):%s\n",
                    tmpWriterFile.length(),
                    tmpWriterFile
                    );
        compare(
                refPropertiesStream,
                tmpWriterFile
                );
        compare( // No Change ?
                refPropertiesStream,
                prop
                );

        File tmpStreamFile = storeOutputStream(prop);
        System.out.printf(
                "Reader->store(Stream)(%d):%s\n",
                    tmpStreamFile.length(),
                    tmpStreamFile
                    );
        compare(
                refPropertiesStream,
                tmpStreamFile
                );
        compare( // No Change ?
                refPropertiesStream,
                prop
                );

        delete(copy);
        delete(tmpWriterFile);
        delete(tmpStreamFile);
    }

    @Test
    @Ignore//FIXME
    public void test_Stream_load_save()
        throws IOException
    {
        File                copy = getCopy();
        FormattedProperties prop = getFormattedProperties(
                new FileInputStream(copy)
                );
        System.out.println("->Stream");
        compare(
                refPropertiesStream,
                prop
                );

        File tmpStreamFile = storeOutputStream(prop);
        compare(
                refPropertiesStream,
                tmpStreamFile
                );
        System.out.printf(
                "Stream->store(Stream)(%d):%s\n",
                    tmpStreamFile.length(),
                    tmpStreamFile
                    );

        File tmpWriterFile = storeWriter(prop);
        compare(
                refPropertiesStream,
                tmpWriterFile
                );
        System.out.printf(
                "Stream->store(Writer)(%d):%s\n",
                    tmpWriterFile.length(),
                    tmpWriterFile
                    );

        delete(copy);
        delete(tmpWriterFile);
        delete(tmpStreamFile);
    }

    @Test
    @Ignore//FIXME
    public void test_clear() throws FileNotFoundException, IOException
    {
        File                copy = getCopy();
        FormattedProperties prop = getFormattedProperties(
                new FileInputStream(copy)
                );
        prop.clear();
        Assert.assertEquals("must be empty",0,prop.size());

        delete(copy);
    }

    @Test
    @Ignore//FIXME
    public void test_add() throws IOException
    {
        File                copy = getCopy();
        FormattedProperties prop = getFormattedProperties(
                new FileInputStream(copy)
                );
        prop.addBlankLine();
        prop.addCommentLine( Const.EMPTY_STRING );
        prop.addCommentLine( "# New entries" );
        final String addKey   = "new.key.put";
        final String addValue = "new value";
        prop.put(addKey,"tmp value");
        prop.put(addKey,addValue);

        Map<String,String> map = new HashMap<String,String>();

        for(int i=0;i<5;i++) {
            map.put( "map.key." + i, "value-" + i );
        }
        prop.putAll( map );

        Assert.assertEquals(
                "Can't find add value",
                addValue,
                prop.getProperty(addKey)
                );

        for(Map.Entry<String,String> entry:map.entrySet()) {
            Assert.assertEquals(
                    "Can't find add value",
                    entry.getValue(),
                    prop.getProperty(
                            entry.getKey()
                            )
                        );
        }

        File tmpWriterFile = storeWriter(prop);
        compare(
                prop,
                tmpWriterFile
                );

        //store(prop,System.out);
        delete(copy);
        delete(tmpWriterFile);
    }

    @Test
    @Ignore//FIXME
    public void test_getLines() throws FileNotFoundException, IOException
    {
        File                copy = getCopy();
        FormattedProperties prop = getFormattedProperties(
                new FileInputStream(copy)
                );
        List<FormattedProperties.Line> lines = prop.getLines();
        int         i  = 1;
        PrintStream ps = System.out;

        for(FormattedProperties.Line line:lines) {
            if( line.isComment() ) {
                ps.printf( "%d - %s\n", i, line.getComment() );
            }
            else {
                ps.printf(
                        "%d - %s=%s\n",
                        i,
                        line.getKey(),
                        prop.getProperty(
                                line.getKey()
                                )
                        );
            }
            i++;
        }

        delete(copy);
    }

    @Test
    @Ignore//FIXME
    public void test_equal() throws FileNotFoundException, IOException
    {
        File                copy  = getCopy();
        FormattedProperties prop1 = getFormattedProperties(
                new FileInputStream(copy)
                );
        FormattedProperties prop2 = getFormattedProperties(
                new FileInputStream(copy)
                );

        boolean r = prop1.equals(prop1);
        Assert.assertTrue("Must be equals (same object)",r);

        r = prop1.equals(prop2);
        Assert.assertTrue("Must be equals",r);

        r = prop2.equals(prop1);
        Assert.assertTrue("Must be equals",r);

        r = prop1.equals(getNull());
        Assert.assertFalse("Must be not equals",r);

        final String key   = "test.key";
        final String value = "a value";

        prop1.put(key,value);
        r = prop1.equals(prop2);
        Assert.assertFalse("Must be not equals",r);
        Assert.assertEquals(
                "Value not found",
                value,
                prop1.getProperty( key )
                );

        prop2.put(key,value);
        r = prop1.equals(prop2);
        Assert.assertTrue("Must be equals",r);

        final String comment = "# a comment";

        prop2.addCommentLine( comment );
        r = prop1.equals(prop2);
        Assert.assertFalse("Must be not equals",r);

        prop1.addCommentLine( comment );
        r = prop1.equals(prop2);
        Assert.assertTrue("Must be equals",r);

        prop1.put(key,value+value);
        r = prop1.equals(prop2);
        Assert.assertFalse("Must be not equals",r);

        prop1.remove( key );
        r = prop1.equals(prop2);
        Assert.assertFalse("Must be not equals",r);

        prop2.remove( key );
        r = prop1.equals(prop2);
        Assert.assertTrue("Must be equals",r);

        delete(copy);
    }

    @Test
    @Ignore//FIXME
    public void test_clone() throws FileNotFoundException, IOException
    {
        File                copy = getCopy();
        FormattedProperties prop = getFormattedProperties(
                new FileInputStream(copy)
                );

        FormattedProperties clone = (FormattedProperties)prop.clone();

        List<FormattedProperties.Line> lines  = prop.getLines();
        List<FormattedProperties.Line> clines = clone.getLines();
        final int linesSize  = lines.size();
        final int clinesSize = clines.size();
        final int size = Math.max(linesSize,clinesSize);
        FormattedProperties.Line l;
        FormattedProperties.Line cl;

        for(int i=0; i<size;i++) {
            l = cl = null;

            if( i < linesSize ) {
                l = lines.get(  i );
            }
            if( i < clinesSize ) {
                cl = clines.get(  i );
            }
            //System.out.printf("%d - 1>%s\n",i, l );
            //System.out.printf("%d - 2>%s\n",i, cl );

            Assert.assertEquals("Lines a diff !",l,cl);
        }

        Assert.assertEquals("Must be same size (keys)",prop.size(),clone.size());
        Assert.assertEquals("Must be same size (lines)",prop.getLines().size(),clone.getLines().size());

        boolean r = prop.equals( clone );
        Assert.assertTrue("Must be equals",r);

        delete(copy);
    }

    @Test
    @Ignore//FIXME
    public void test_store_plusplus() throws FileNotFoundException, IOException
    {
        File                copy = getCopy();
        File                file = getTmpFile("formatall");
        FormattedProperties prop = getFormattedProperties(
                new FileInputStream(copy)
                );
        Writer out = new FileWriter( file );
        prop.store(
                out,
                EnumSet.allOf( FormattedProperties.Store.class )
                );
        out.close();
        compare(prop,file);

        //keepFile(file);
        file.delete();
        copy.delete();
    }
    // ---------------------------------------------------
    // ---------------------------------------------------
    public void compare(
            Properties propRef,
            Properties prop
            )
    {
        // Verify standard Properties
        // give same result using Steam and Reader
        // for giving file
        Assert.assertEquals(
                "bad size()",
                propRef .size(),
                prop.size()
                );

        Set<String> namesRef    = propRef.stringPropertyNames();
        Set<String> names       = prop.stringPropertyNames();

        Assert.assertTrue( "missing name", namesRef.containsAll( names ));
        Assert.assertTrue( "missing name", names.containsAll( namesRef ));

        for(String key:namesRef) {
            String vRef = propRef.getProperty( key );
            String v    = prop.getProperty( key );

            Assert.assertEquals("REFs: bad value",vRef, v );
        }
    }

    public void compare(
            Properties  propRef,
            File        propFile
            )
        throws FileNotFoundException, IOException
    {
        Properties propS = getProperties(new FileInputStream(propFile));
        compare(propRef,propS);
        propS.clear();
        propS = null;

        Properties propR = getProperties(new FileReader(propFile));
        compare(propRef,propR);
        propR.clear();
        propR = null;

        FormattedProperties propS2 = getFormattedProperties(new FileInputStream(propFile));
        compare(propRef,propS2);
        propS2.clear();
        propS2 = null;

        FormattedProperties propR2 = getFormattedProperties(new FileReader(propFile));
        compare(propRef,propR2);
        propR2.clear();
        propR2 = null;
    }

    public File getCopy() throws IOException
    {
        return getCopy(
                getClass().getResourceAsStream( REF )
                );
    }

    public File getTmpFile(String tag) throws IOException
    {
        File f = File.createTempFile( getClass().getSimpleName(), tag );
        //f.deleteOnExit();

        return f;
    }

    public File getCopy(InputStream is) throws IOException
    {
        File            f       = getTmpFile("copy");
        OutputStream    output  = new FileOutputStream( f );
        IOHelper.copy( is, output );
        is.close();
        output.close();

        return f;
    }

    public Properties getProperties(InputStream is) throws IOException
    {
        Properties prop = new Properties();
        prop.load( is );
        is.close();

        return prop;
    }

    public Properties getProperties(Reader r) throws IOException
    {
        Properties prop = new Properties();
        prop.load( r );
        r.close();

        return prop;
    }

    public FormattedProperties getFormattedProperties(InputStream is) throws IOException
    {
        FormattedProperties prop = new FormattedProperties();
        prop.load( is );
        is.close();

        return prop;
    }

    public FormattedProperties getFormattedProperties(Reader r) throws IOException
    {
        FormattedProperties prop = new FormattedProperties();
        prop.load( r );
        r.close();

        return prop;
    }

    public void store(Properties prop, Writer w) throws IOException
    {
        prop.store( w, "comments" );
        w.close();
    }

    public File storeWriter(Properties prop) throws IOException
    {
        File        tmpFile = getTmpFile("writer");
        store(prop, new FileWriter(tmpFile));

        return tmpFile;
    }

    public void store(Properties prop, OutputStream os) throws IOException
    {
        prop.store( os, "comments" );
        os.close();
    }

    public File storeOutputStream(Properties prop) throws FileNotFoundException, IOException
    {
        File        tmpFile = getTmpFile("OuputStream");
        store(prop, new FileOutputStream(tmpFile));

        return tmpFile;
    }

    public void delete(File f)
    {
        boolean isDeleted = f.delete();

        Assert.assertTrue("Can't delete:" + f,isDeleted);
    }

    private Object getNull()
    {// Just to remove warning
        return null;
    }

    protected void keepFile(File f)
    {// Just for debugging!
        File n = new File(
                f.getParent(),
                f.getName() + ".keep"
                );
        f.renameTo( n );
    }
}
