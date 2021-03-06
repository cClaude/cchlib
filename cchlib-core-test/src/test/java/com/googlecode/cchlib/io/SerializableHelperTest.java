package com.googlecode.cchlib.io;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * The class {@link SerializableHelperTest} contains tests for the class
 * {@link SerializableHelper}.
 *
 * @version $Revision: 1.0 $
 */
public class SerializableHelperTest
{
    private static final Logger LOGGER = Logger.getLogger( SerializableHelperTest.class );

    /*
     * Run the Serializable clone(T,Class<? extends T>) method test.
     * @throws ClassNotFoundException
     * @throws IOException
     */
    @Test
    public void testClone_String() throws IOException, ClassNotFoundException
    {
        final String            value = "123456789";
        final Class<? extends String> clazz = value.getClass();


        final String result = SerializableHelper.clone( value, clazz );

        // add additional test code here
        assertThat( result ).isNotNull().isEqualTo( value ).isNotSameAs( value );
    }

// Should not occur
//    /**
//     * Run the Serializable clone(T,Class<? extends T>) method test.
//      */
//    @Test(expected = java.io.IOException.class)
//    public void testClone_2()
//        throws Exception
//    {
//        Class<? extends Serializable> clazz = Serializable.class;
//
//        Serializable result = SerializableHelper.clone(Level.OFF, clazz);
//
//        // add additional test code here
//        assertNotNull(result);
//    }

 // Should not occur
//    /**
//     * Run the Serializable clone(T,Class<? extends T>) method test.
//     */
//    @Test(expected = java.lang.ClassNotFoundException.class)
//    public void testClone_4()
//        throws Exception
//    {
//        Class<? extends Serializable> clazz = Serializable.class;
//
//        Serializable result = SerializableHelper.clone(Level.OFF, clazz);
//
//        // add additional test code here
//        assertNotNull(result);
//    }


    private File newFile( final String tag ) throws IOException
    {
        final File file = File.createTempFile( this.getClass().getSimpleName(), tag ); // $codepro.audit.disable

        // delete temporary file on JVM exit
        file.deleteOnExit();

        return file;
    }

    /**
     * Run the Serializable loadObject(File,Class<? extends T>) method test.
     */
    @Test(expected = java.io.IOException.class)
    public void testLoadObject_fileDoesNotExists()
        throws Exception
    {
        final File aFile = new File("this_file_should_not_exists");
        final Class<? extends Serializable> clazz = Serializable.class;

        final Serializable result = SerializableHelper.loadObject(aFile, clazz);

        // add additional test code here
        assertNotNull(result);
    }

//    /**
//     * Run the Serializable loadObject(File,Class<? extends T>) method test.
//     */
//    @Test(expected = java.io.IOException.class)
//    public void testLoadObject_3()
//        throws Exception
//    {
//        final File aFile = new File("");
//        final Class<? extends Serializable> clazz = Serializable.class;
//
//        final Serializable result = SerializableHelper.loadObject(aFile, clazz);
//
//        // add additional test code here
//        assertNotNull(result);
//    }

//    /**
//     * Run the Serializable loadObject(File,Class<? extends T>) method test.
//     */
//    @Test(expected = java.lang.ClassNotFoundException.class)
//    public void testLoadObject_4()
//        throws Exception
//    {
//        final File aFile = new File("");
//        final Class<? extends Serializable> clazz = Serializable.class;
//
//        final Serializable result = SerializableHelper.loadObject(aFile, clazz);
//
//        // add additional test code here
//        assertNotNull(result);
//    }


    /**
     * Run the byte[] toByteArray(null) method test.
     */
    @Test
    public void testToByteArray_null()
        throws Exception
    {
            final byte[] result = SerializableHelper.toByteArray(null);

            LOGGER.info( "result not null = " + (result!=null) );

            // add additional test code here
            assertNotNull(result);
    }

    /**
     * Run the byte[] toByteArray(T) method test.
     */
    @Test
    public void testToByteArray_1()
        throws Exception
    {
        final byte[] result = SerializableHelper.toByteArray(Level.OFF);

        // add additional test code here
        assertNotNull(result);
        assertEquals(59, result.length);
        assertEquals((byte) -84, result[0]);
        assertEquals((byte) -19, result[1]);
        assertEquals((byte) 0, result[2]);
        assertEquals((byte) 5, result[3]);
        assertEquals((byte) 115, result[4]);
        assertEquals((byte) 114, result[5]);
        assertEquals((byte) 0, result[6]);
        assertEquals((byte) 22, result[7]);
        assertEquals((byte) 111, result[8]);
        assertEquals((byte) 114, result[9]);
        assertEquals((byte) 103, result[10]);
        assertEquals((byte) 46, result[11]);
        assertEquals((byte) 97, result[12]);
        assertEquals((byte) 112, result[13]);
        assertEquals((byte) 97, result[14]);
        assertEquals((byte) 99, result[15]);
        assertEquals((byte) 104, result[16]);
        assertEquals((byte) 101, result[17]);
        assertEquals((byte) 46, result[18]);
        assertEquals((byte) 108, result[19]);
        assertEquals((byte) 111, result[20]);
        assertEquals((byte) 103, result[21]);
        assertEquals((byte) 52, result[22]);
        assertEquals((byte) 106, result[23]);
        assertEquals((byte) 46, result[24]);
        assertEquals((byte) 76, result[25]);
        assertEquals((byte) 101, result[26]);
        assertEquals((byte) 118, result[27]);
        assertEquals((byte) 101, result[28]);
        assertEquals((byte) 108, result[29]);
        assertEquals((byte) 48, result[30]);
        assertEquals((byte) 115, result[31]);
        assertEquals((byte) 7, result[32]);
        assertEquals((byte) 31, result[33]);
        assertEquals((byte) 31, result[34]);
        assertEquals((byte) 2, result[35]);
        assertEquals((byte) -60, result[36]);
        assertEquals((byte) 54, result[37]);
        assertEquals((byte) 3, result[38]);
        assertEquals((byte) 0, result[39]);
        assertEquals((byte) 0, result[40]);
        assertEquals((byte) 120, result[41]);
        assertEquals((byte) 112, result[42]);
        assertEquals((byte) 119, result[43]);
        assertEquals((byte) 13, result[44]);
        assertEquals(Byte.MAX_VALUE, result[45]);
        assertEquals((byte) -1, result[46]);
        assertEquals((byte) -1, result[47]);
        assertEquals((byte) -1, result[48]);
        assertEquals((byte) 0, result[49]);
        assertEquals((byte) 0, result[50]);
        assertEquals((byte) 0, result[51]);
        assertEquals((byte) 0, result[52]);
        assertEquals((byte) 0, result[53]);
        assertEquals((byte) 3, result[54]);
        assertEquals((byte) 79, result[55]);
        assertEquals((byte) 70, result[56]);
        assertEquals((byte) 70, result[57]);
        assertEquals((byte) 120, result[58]);
    }

    /**
     * Run the byte[] toByteArray(T) method test.
     */
    @Test
    public void testToByteArray_2()
        throws Exception
    {
        final byte[] result = SerializableHelper.toByteArray(Level.OFF);

        // add additional test code here
        assertNotNull(result);
        assertEquals(59, result.length);
        assertEquals((byte) -84, result[0]);
        assertEquals((byte) -19, result[1]);
        assertEquals((byte) 0, result[2]);
        assertEquals((byte) 5, result[3]);
        assertEquals((byte) 115, result[4]);
        assertEquals((byte) 114, result[5]);
        assertEquals((byte) 0, result[6]);
        assertEquals((byte) 22, result[7]);
        assertEquals((byte) 111, result[8]);
        assertEquals((byte) 114, result[9]);
        assertEquals((byte) 103, result[10]);
        assertEquals((byte) 46, result[11]);
        assertEquals((byte) 97, result[12]);
        assertEquals((byte) 112, result[13]);
        assertEquals((byte) 97, result[14]);
        assertEquals((byte) 99, result[15]);
        assertEquals((byte) 104, result[16]);
        assertEquals((byte) 101, result[17]);
        assertEquals((byte) 46, result[18]);
        assertEquals((byte) 108, result[19]);
        assertEquals((byte) 111, result[20]);
        assertEquals((byte) 103, result[21]);
        assertEquals((byte) 52, result[22]);
        assertEquals((byte) 106, result[23]);
        assertEquals((byte) 46, result[24]);
        assertEquals((byte) 76, result[25]);
        assertEquals((byte) 101, result[26]);
        assertEquals((byte) 118, result[27]);
        assertEquals((byte) 101, result[28]);
        assertEquals((byte) 108, result[29]);
        assertEquals((byte) 48, result[30]);
        assertEquals((byte) 115, result[31]);
        assertEquals((byte) 7, result[32]);
        assertEquals((byte) 31, result[33]);
        assertEquals((byte) 31, result[34]);
        assertEquals((byte) 2, result[35]);
        assertEquals((byte) -60, result[36]);
        assertEquals((byte) 54, result[37]);
        assertEquals((byte) 3, result[38]);
        assertEquals((byte) 0, result[39]);
        assertEquals((byte) 0, result[40]);
        assertEquals((byte) 120, result[41]);
        assertEquals((byte) 112, result[42]);
        assertEquals((byte) 119, result[43]);
        assertEquals((byte) 13, result[44]);
        assertEquals(Byte.MAX_VALUE, result[45]);
        assertEquals((byte) -1, result[46]);
        assertEquals((byte) -1, result[47]);
        assertEquals((byte) -1, result[48]);
        assertEquals((byte) 0, result[49]);
        assertEquals((byte) 0, result[50]);
        assertEquals((byte) 0, result[51]);
        assertEquals((byte) 0, result[52]);
        assertEquals((byte) 0, result[53]);
        assertEquals((byte) 3, result[54]);
        assertEquals((byte) 79, result[55]);
        assertEquals((byte) 70, result[56]);
        assertEquals((byte) 70, result[57]);
        assertEquals((byte) 120, result[58]);
    }

    /**
     * Run the byte[] toByteArray(T) method test.
     */
    @Test
    public void testToByteArray_3()
        throws Exception
    {

        final byte[] result = SerializableHelper.toByteArray(Level.OFF);

        // add additional test code here
        assertNotNull(result);
        assertEquals(59, result.length);
        assertEquals((byte) -84, result[0]);
        assertEquals((byte) -19, result[1]);
        assertEquals((byte) 0, result[2]);
        assertEquals((byte) 5, result[3]);
        assertEquals((byte) 115, result[4]);
        assertEquals((byte) 114, result[5]);
        assertEquals((byte) 0, result[6]);
        assertEquals((byte) 22, result[7]);
        assertEquals((byte) 111, result[8]);
        assertEquals((byte) 114, result[9]);
        assertEquals((byte) 103, result[10]);
        assertEquals((byte) 46, result[11]);
        assertEquals((byte) 97, result[12]);
        assertEquals((byte) 112, result[13]);
        assertEquals((byte) 97, result[14]);
        assertEquals((byte) 99, result[15]);
        assertEquals((byte) 104, result[16]);
        assertEquals((byte) 101, result[17]);
        assertEquals((byte) 46, result[18]);
        assertEquals((byte) 108, result[19]);
        assertEquals((byte) 111, result[20]);
        assertEquals((byte) 103, result[21]);
        assertEquals((byte) 52, result[22]);
        assertEquals((byte) 106, result[23]);
        assertEquals((byte) 46, result[24]);
        assertEquals((byte) 76, result[25]);
        assertEquals((byte) 101, result[26]);
        assertEquals((byte) 118, result[27]);
        assertEquals((byte) 101, result[28]);
        assertEquals((byte) 108, result[29]);
        assertEquals((byte) 48, result[30]);
        assertEquals((byte) 115, result[31]);
        assertEquals((byte) 7, result[32]);
        assertEquals((byte) 31, result[33]);
        assertEquals((byte) 31, result[34]);
        assertEquals((byte) 2, result[35]);
        assertEquals((byte) -60, result[36]);
        assertEquals((byte) 54, result[37]);
        assertEquals((byte) 3, result[38]);
        assertEquals((byte) 0, result[39]);
        assertEquals((byte) 0, result[40]);
        assertEquals((byte) 120, result[41]);
        assertEquals((byte) 112, result[42]);
        assertEquals((byte) 119, result[43]);
        assertEquals((byte) 13, result[44]);
        assertEquals(Byte.MAX_VALUE, result[45]);
        assertEquals((byte) -1, result[46]);
        assertEquals((byte) -1, result[47]);
        assertEquals((byte) -1, result[48]);
        assertEquals((byte) 0, result[49]);
        assertEquals((byte) 0, result[50]);
        assertEquals((byte) 0, result[51]);
        assertEquals((byte) 0, result[52]);
        assertEquals((byte) 0, result[53]);
        assertEquals((byte) 3, result[54]);
        assertEquals((byte) 79, result[55]);
        assertEquals((byte) 70, result[56]);
        assertEquals((byte) 70, result[57]);
        assertEquals((byte) 120, result[58]);
    }

    /**
     * Run the byte[] toByteArray(T) method test.
     */
    @Test
    public void testToByteArray_4()
        throws Exception
    {

        final byte[] result = SerializableHelper.toByteArray(Level.OFF);

        // add additional test code here
        assertNotNull(result);
        assertEquals(59, result.length);
        assertEquals((byte) -84, result[0]);
        assertEquals((byte) -19, result[1]);
        assertEquals((byte) 0, result[2]);
        assertEquals((byte) 5, result[3]);
        assertEquals((byte) 115, result[4]);
        assertEquals((byte) 114, result[5]);
        assertEquals((byte) 0, result[6]);
        assertEquals((byte) 22, result[7]);
        assertEquals((byte) 111, result[8]);
        assertEquals((byte) 114, result[9]);
        assertEquals((byte) 103, result[10]);
        assertEquals((byte) 46, result[11]);
        assertEquals((byte) 97, result[12]);
        assertEquals((byte) 112, result[13]);
        assertEquals((byte) 97, result[14]);
        assertEquals((byte) 99, result[15]);
        assertEquals((byte) 104, result[16]);
        assertEquals((byte) 101, result[17]);
        assertEquals((byte) 46, result[18]);
        assertEquals((byte) 108, result[19]);
        assertEquals((byte) 111, result[20]);
        assertEquals((byte) 103, result[21]);
        assertEquals((byte) 52, result[22]);
        assertEquals((byte) 106, result[23]);
        assertEquals((byte) 46, result[24]);
        assertEquals((byte) 76, result[25]);
        assertEquals((byte) 101, result[26]);
        assertEquals((byte) 118, result[27]);
        assertEquals((byte) 101, result[28]);
        assertEquals((byte) 108, result[29]);
        assertEquals((byte) 48, result[30]);
        assertEquals((byte) 115, result[31]);
        assertEquals((byte) 7, result[32]);
        assertEquals((byte) 31, result[33]);
        assertEquals((byte) 31, result[34]);
        assertEquals((byte) 2, result[35]);
        assertEquals((byte) -60, result[36]);
        assertEquals((byte) 54, result[37]);
        assertEquals((byte) 3, result[38]);
        assertEquals((byte) 0, result[39]);
        assertEquals((byte) 0, result[40]);
        assertEquals((byte) 120, result[41]);
        assertEquals((byte) 112, result[42]);
        assertEquals((byte) 119, result[43]);
        assertEquals((byte) 13, result[44]);
        assertEquals(Byte.MAX_VALUE, result[45]);
        assertEquals((byte) -1, result[46]);
        assertEquals((byte) -1, result[47]);
        assertEquals((byte) -1, result[48]);
        assertEquals((byte) 0, result[49]);
        assertEquals((byte) 0, result[50]);
        assertEquals((byte) 0, result[51]);
        assertEquals((byte) 0, result[52]);
        assertEquals((byte) 0, result[53]);
        assertEquals((byte) 3, result[54]);
        assertEquals((byte) 79, result[55]);
        assertEquals((byte) 70, result[56]);
        assertEquals((byte) 70, result[57]);
        assertEquals((byte) 120, result[58]);
    }

    /**
     * Run the byte[] toByteArray(T) method test.
     */
    @Test
    public void testToByteArray_5()
        throws Exception
    {

        final byte[] result = SerializableHelper.toByteArray(Level.OFF);

        // add additional test code here
        assertNotNull(result);
        assertEquals(59, result.length);
        assertEquals((byte) -84, result[0]);
        assertEquals((byte) -19, result[1]);
        assertEquals((byte) 0, result[2]);
        assertEquals((byte) 5, result[3]);
        assertEquals((byte) 115, result[4]);
        assertEquals((byte) 114, result[5]);
        assertEquals((byte) 0, result[6]);
        assertEquals((byte) 22, result[7]);
        assertEquals((byte) 111, result[8]);
        assertEquals((byte) 114, result[9]);
        assertEquals((byte) 103, result[10]);
        assertEquals((byte) 46, result[11]);
        assertEquals((byte) 97, result[12]);
        assertEquals((byte) 112, result[13]);
        assertEquals((byte) 97, result[14]);
        assertEquals((byte) 99, result[15]);
        assertEquals((byte) 104, result[16]);
        assertEquals((byte) 101, result[17]);
        assertEquals((byte) 46, result[18]);
        assertEquals((byte) 108, result[19]);
        assertEquals((byte) 111, result[20]);
        assertEquals((byte) 103, result[21]);
        assertEquals((byte) 52, result[22]);
        assertEquals((byte) 106, result[23]);
        assertEquals((byte) 46, result[24]);
        assertEquals((byte) 76, result[25]);
        assertEquals((byte) 101, result[26]);
        assertEquals((byte) 118, result[27]);
        assertEquals((byte) 101, result[28]);
        assertEquals((byte) 108, result[29]);
        assertEquals((byte) 48, result[30]);
        assertEquals((byte) 115, result[31]);
        assertEquals((byte) 7, result[32]);
        assertEquals((byte) 31, result[33]);
        assertEquals((byte) 31, result[34]);
        assertEquals((byte) 2, result[35]);
        assertEquals((byte) -60, result[36]);
        assertEquals((byte) 54, result[37]);
        assertEquals((byte) 3, result[38]);
        assertEquals((byte) 0, result[39]);
        assertEquals((byte) 0, result[40]);
        assertEquals((byte) 120, result[41]);
        assertEquals((byte) 112, result[42]);
        assertEquals((byte) 119, result[43]);
        assertEquals((byte) 13, result[44]);
        assertEquals(Byte.MAX_VALUE, result[45]);
        assertEquals((byte) -1, result[46]);
        assertEquals((byte) -1, result[47]);
        assertEquals((byte) -1, result[48]);
        assertEquals((byte) 0, result[49]);
        assertEquals((byte) 0, result[50]);
        assertEquals((byte) 0, result[51]);
        assertEquals((byte) 0, result[52]);
        assertEquals((byte) 0, result[53]);
        assertEquals((byte) 3, result[54]);
        assertEquals((byte) 79, result[55]);
        assertEquals((byte) 70, result[56]);
        assertEquals((byte) 70, result[57]);
        assertEquals((byte) 120, result[58]);
    }

    /**
     * Run the void toFile(T,File) method test.
     */
    @Ignore
    @Test
    public void testToFile_1()
        throws Exception
    {
        final File aFile = new File("");

        SerializableHelper.toFile(Level.OFF, aFile);

        // add additional test code here
        // An unexpected exception was thrown in user code while executing this test:
        //    java.lang.SecurityException: Cannot write to files while generating test cases
        //       at com.instantiations.assist.eclipse.junit.CodeProJUnitSecurityManager.checkWrite(CodeProJUnitSecurityManager.java:76)
        //       at java.io.FileOutputStream.<init>(FileOutputStream.java:203)
        //       at java.io.FileOutputStream.<init>(FileOutputStream.java:165)
        //       at com.googlecode.cchlib.io.SerializableHelper.toFile(SerializableHelper.java:146)
    }

    /**
     * Run the void toFile(T,File) method test.
     */
    @Ignore
    @Test
    public void testToFile_2()
        throws Exception
    {
        final File aFile = new File("");

        SerializableHelper.toFile(Level.OFF, aFile);

        // add additional test code here
        // An unexpected exception was thrown in user code while executing this test:
        //    java.lang.SecurityException: Cannot write to files while generating test cases
        //       at com.instantiations.assist.eclipse.junit.CodeProJUnitSecurityManager.checkWrite(CodeProJUnitSecurityManager.java:76)
        //       at java.io.FileOutputStream.<init>(FileOutputStream.java:203)
        //       at java.io.FileOutputStream.<init>(FileOutputStream.java:165)
        //       at com.googlecode.cchlib.io.SerializableHelper.toFile(SerializableHelper.java:146)
    }

    /**
     * Run the void toFile(T,File) method test.
     */
    @Ignore
    @Test
    public void testToFile_3()
        throws Exception
    {
        final File aFile = new File("");

        SerializableHelper.toFile(Level.OFF, aFile);

        // add additional test code here
        // An unexpected exception was thrown in user code while executing this test:
        //    java.lang.SecurityException: Cannot write to files while generating test cases
        //       at com.instantiations.assist.eclipse.junit.CodeProJUnitSecurityManager.checkWrite(CodeProJUnitSecurityManager.java:76)
        //       at java.io.FileOutputStream.<init>(FileOutputStream.java:203)
        //       at java.io.FileOutputStream.<init>(FileOutputStream.java:165)
        //       at com.googlecode.cchlib.io.SerializableHelper.toFile(SerializableHelper.java:146)
    }

    /**
     * Run the void toFile(T,File) method test.
     */
    @Ignore
    @Test
    public void testToFile_4()
        throws Exception
    {
        final File aFile = new File("");

        SerializableHelper.toFile(Level.OFF, aFile);

        // add additional test code here
        // An unexpected exception was thrown in user code while executing this test:
        //    java.lang.SecurityException: Cannot write to files while generating test cases
        //       at com.instantiations.assist.eclipse.junit.CodeProJUnitSecurityManager.checkWrite(CodeProJUnitSecurityManager.java:76)
        //       at java.io.FileOutputStream.<init>(FileOutputStream.java:203)
        //       at java.io.FileOutputStream.<init>(FileOutputStream.java:165)
        //       at com.googlecode.cchlib.io.SerializableHelper.toFile(SerializableHelper.java:146)
    }

    /**
     * Run the void toFile(T,File) method test.
     */
    @Ignore
    @Test
    public void testToFile_5()
        throws Exception
    {
        final File aFile = new File("");

        SerializableHelper.toFile(Level.OFF, aFile);

        // add additional test code here
        // An unexpected exception was thrown in user code while executing this test:
        //    java.lang.SecurityException: Cannot write to files while generating test cases
        //       at com.instantiations.assist.eclipse.junit.CodeProJUnitSecurityManager.checkWrite(CodeProJUnitSecurityManager.java:76)
        //       at java.io.FileOutputStream.<init>(FileOutputStream.java:203)
        //       at java.io.FileOutputStream.<init>(FileOutputStream.java:165)
        //       at com.googlecode.cchlib.io.SerializableHelper.toFile(SerializableHelper.java:146)
    }

    /**
     * Run the void toFile(T,File) method test.
     */
    @Ignore
    @Test
    public void testToFile_6()
        throws Exception
    {
        final File aFile = new File("");

        SerializableHelper.toFile(Level.OFF, aFile);

        // add additional test code here
        // An unexpected exception was thrown in user code while executing this test:
        //    java.lang.SecurityException: Cannot write to files while generating test cases
        //       at com.instantiations.assist.eclipse.junit.CodeProJUnitSecurityManager.checkWrite(CodeProJUnitSecurityManager.java:76)
        //       at java.io.FileOutputStream.<init>(FileOutputStream.java:203)
        //       at java.io.FileOutputStream.<init>(FileOutputStream.java:165)
        //       at com.googlecode.cchlib.io.SerializableHelper.toFile(SerializableHelper.java:146)
    }

    /**
     * Run the Serializable toObject(byte[],Class<? extends T>) method test.
     */
    @Ignore
    @Test
    public void testToObject_1()
        throws Exception
    {
        final byte[] aSerializedObject = new byte[] {};
        final Class<? extends Serializable> clazz = Serializable.class;

        final Serializable result = SerializableHelper.toObject(aSerializedObject, clazz);

        // add additional test code here
        assertNotNull(result);
    }

    /**
     * Run the Serializable toObject(byte[],Class<? extends T>) method test.
     */
    @Test(expected = java.io.IOException.class)
    public void testToObject_2()
        throws Exception
    {
        final byte[] aSerializedObject = new byte[] {};
        final Class<? extends Serializable> clazz = Serializable.class;

        final Serializable result = SerializableHelper.toObject(aSerializedObject, clazz);

        // add additional test code here
        assertNotNull(result);
    }

    /**
     * Run the Serializable toObject(byte[],Class<? extends T>) method test.
     */
    @Test(expected = java.io.IOException.class)
    public void testToObject_3()
        throws Exception
    {
        final byte[] aSerializedObject = new byte[] {};
        final Class<? extends Serializable> clazz = Serializable.class;

        final Serializable result = SerializableHelper.toObject(aSerializedObject, clazz);

        // add additional test code here
        assertNotNull(result);
    }

    /**
     * Run the Serializable toObject(byte[],Class<? extends T>) method test.
     */
    @Test(expected = java.io.IOException.class)
    public void testToObject_4()
        throws Exception
    {
        final byte[] aSerializedObject = new byte[] {};
        final Class<? extends Serializable> clazz = Serializable.class;

        final Serializable result = SerializableHelper.toObject(aSerializedObject, clazz);

        // add additional test code here
        assertNotNull(result);
    }

    /**
     * Run the Serializable toObject(byte[],Class<? extends T>) method test.
     */
    @Ignore
    @Test(expected = java.lang.ClassNotFoundException.class)
    public void testToObject_5()
        throws Exception
    {
        final byte[] aSerializedObject = new byte[] {};
        final Class<? extends Serializable> clazz = Serializable.class;

        final Serializable result = SerializableHelper.toObject(aSerializedObject, clazz);

        // add additional test code here
        assertNotNull(result);
    }

    /**
     * Perform pre-test initialization.
     */
    @Before
    public void setUp()
        throws Exception
    {
        // add additional set up code here
    }

    /**
     * Perform post-test clean-up.
     */
    @After
    public void tearDown()
        throws Exception
    {
        // Add additional tear down code here
    }

    /**
     * Launch the test.
      */
    public static void main(final String[] args)
    {
        new org.junit.runner.JUnitCore().run(SerializableHelperTest.class);
    }
}
