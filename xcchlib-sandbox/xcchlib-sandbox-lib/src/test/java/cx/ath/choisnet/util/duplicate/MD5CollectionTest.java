package cx.ath.choisnet.util.duplicate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import java.io.File;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import cx.ath.choisnet.io.Serialization;
import cx.ath.choisnet.util.checksum.MD5Tree;
import cx.ath.choisnet.util.duplicate.impl.MD5CollectionImpl;

public class MD5CollectionTest {
    private final static Logger LOGGER = Logger.getLogger( MD5CollectionTest.class );
    private MyTestCase[] myTestCase;

    @Before
    public void setUp() // -------------------------------------------------
    {
        this.myTestCase = MD5CollectionTest.getMyTestCase();
    }

    private static MD5Tree getMD5Tree( final File folder ) // -----------------
    {
        final MD5Tree tree = new MD5Tree();

        try {
            tree.load( folder, new MD5Tree.ExceptionHandler() {
                @Override
                public void handleIOException( final File file, final java.io.IOException cause )
                {
                    // on ignore les erreurs...
                    LOGGER.warn( "handleIOException " + file + "  * " + cause.getMessage() );
                }
            } );
        }
        catch( final java.io.IOException ignore ) {
            assertNull( "getMD5Tree() failed : " + ignore, ignore );
        }

        return tree;
    }

    /**
     ** Verification java.io.Serializable et Comparable<MD5Collection>
     */
    @Test
    public void testSerializable() // -----------------------------------------
            throws java.io.IOException, ClassNotFoundException
    {
        for( final MyTestCase mytest : this.myTestCase ) {
            final MD5Collection thisMD5Collection = mytest.md5Collection;

            final byte[] serialization = Serialization.toByteArray( thisMD5Collection, MD5Collection.class );
            final MD5Collection aMD5Collection = Serialization.newFromByteArray( serialization, MD5Collection.class );

            assertEquals( "Resultat de la serialisation ", thisMD5Collection.compareTo( aMD5Collection ), 0 );
        }
    }

    // ------------------------------------------------------------------------
    // ---------------------- INIT -----------------------
    // ------------------------------------------------------------------------
    private static class MyTestCase {
        File          folder;
        //MD5Tree       md5Tree;
        MD5Collection md5Collection;

        public MyTestCase( final String foldername )
        {
            this( new File( foldername ) );
        }

        public MyTestCase( final File folder )
        {
            this.folder = folder;
        }

    };

    private final static MyTestCase[] samples = {//
        new MyTestCase( System.getProperty( "java.io.tmpdir" ) ),
        /* new MyTestCase( System.getProperty( "user.home" ) )*/
        };

    static {
        for( int i = 0; i < samples.length; i++ ) {
            final File folder = MD5CollectionTest.samples[ i ].folder;
            final MD5Tree tree = MD5CollectionTest.getMD5Tree( folder );
            final MD5Collection collec = new MD5CollectionImpl( tree, folder );

            //MD5CollectionTest.samples[ i ].md5Tree = tree;
            MD5CollectionTest.samples[ i ].md5Collection = collec;
        }
    };

    public static MyTestCase[] getMyTestCase() // -----------------------------
    {
        return MD5CollectionTest.samples;
    }
    // ------------------------------------------------------------------------
    // ------------------------------------------------------------------------
    // ------------------------------------------------------------------------

} // class

