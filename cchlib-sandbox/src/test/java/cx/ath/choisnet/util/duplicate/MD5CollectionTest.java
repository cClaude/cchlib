/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/util/duplicate/MD5CollectionTest.java
** Description   :
**
**  3.01.003 2006.03.01 Claude CHOISNET - Version initiale
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.util.duplicate.MD5CollectionTest
**
*/
package cx.ath.choisnet.util.duplicate;

import cx.ath.choisnet.io.Serialization;
import cx.ath.choisnet.util.checksum.MD5Tree;
import cx.ath.choisnet.util.duplicate.impl.MD5CollectionImpl;
import java.io.File;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
**
*/
public class MD5CollectionTest extends TestCase
{

/**
**
*/
public static Test suite() // ---------------------------------------------
{
 return new TestSuite( MD5CollectionTest.class );
}

/** */
private MyTestCase[] myTestCase;

/**
**
*/
protected void setUp() // -------------------------------------------------
{
 this.myTestCase = MD5CollectionTest.getMyTestCase();
}


/**
**
*/
private static MD5Tree getMD5Tree( final File folder ) // -----------------
{
 MD5Tree tree = new MD5Tree();

 try {
    tree.load(
        folder,
        new MD5Tree.ExceptionHandler()
            {
                public void handleIOException(
                    File                file,
                    java.io.IOException cause
                    )
                {
                    // on ignore les erreurs...
                }
            }
        );
    }
 catch( java.io.IOException ignore ) {
    assertNull( "getMD5Tree() failed : " + ignore, ignore );
    }

 return tree;
}

/**
**
protected void tearDown() // ----------------------------------------------
{
 this.trees = null;
}
*/


/**
** V�rification java.io.Serializable et Comparable<MD5Collection>
*/
public void testSerializable() // -----------------------------------------
    throws java.io.IOException, ClassNotFoundException
{
 for( MyTestCase mytest : this.myTestCase ) {
    MD5Collection   thisMD5Collection   = mytest.md5Collection;

    byte[]          serialization       = Serialization.toByteArray( thisMD5Collection, MD5Collection.class );
    MD5Collection   aMD5Collection      = Serialization.newFromByteArray( serialization, MD5Collection.class );

    assertEquals(
        "R�sultat de la s�rialisation ",
        thisMD5Collection.compareTo( aMD5Collection ),
        0
        );
    }
}

/**
** V�rification Comparable<MD5Collection>
*/
public void testCompareTo() // --------------------------------------------
{
    MD5Collection collect0  = this.myTestCase[ 0 ].md5Collection;
    MD5Collection collect1  = this.myTestCase[ 1 ].md5Collection;

    int res1 = collect0.compareTo( collect1 );
    int res2 = collect1.compareTo( collect0 );

    assertFalse( "compareTo() - diff", res1 == 0 );

    assertEquals( "compareTo() symetrie" , collect0.compareTo( collect0 ), 0 );
    assertEquals( "compareTo() cmp(this)", collect1.compareTo( collect1 ), 0 );
}

// ------------------------------------------------------------------------
// ----------------------        INIT               -----------------------
// ------------------------------------------------------------------------
public static class MyTestCase
    {
        File            folder;
        MD5Tree         md5Tree;
        MD5Collection   md5Collection;


        public MyTestCase( final String foldername )
        {
            this( new File( foldername ) ) ;
        }

        public MyTestCase( final File folder )
        {
            this.folder = folder;
        }

    };

private final static MyTestCase[] samples = {
    new MyTestCase( System.getProperty( "java.io.tmpdir" ) ),
    new MyTestCase( System.getProperty( "user.home" ) )
    };


static  {
    for( int i = 0; i<samples.length; i++ ) {
        File            folder  = MD5CollectionTest.samples[ i ].folder;
        MD5Tree         tree    = MD5CollectionTest.getMD5Tree( folder );
        MD5Collection   collec  = new MD5CollectionImpl( tree, folder );

        MD5CollectionTest.samples[ i ].md5Tree          = tree;
        MD5CollectionTest.samples[ i ].md5Collection    = collec;
        }
    };

/**
**
*/
public static MyTestCase[] getMyTestCase() // -----------------------------
{
 return MD5CollectionTest.samples;
}
// ------------------------------------------------------------------------
// ------------------------------------------------------------------------
// ------------------------------------------------------------------------

} // class

