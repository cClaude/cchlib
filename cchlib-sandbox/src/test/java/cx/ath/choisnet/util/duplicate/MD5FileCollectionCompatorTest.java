/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/util/duplicate/MD5FileCollectionCompatorTest.java
** Description   :
**
**  3.01.003 2006.03.01 Claude CHOISNET - Version initiale
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.util.duplicate.MD5FileCollectionCompatorTest
**
*/
package cx.ath.choisnet.util.duplicate;

import cx.ath.choisnet.io.Serialization;
import cx.ath.choisnet.util.checksum.MD5TreeEntry;
import cx.ath.choisnet.util.checksum.MD5Tree;
import cx.ath.choisnet.util.duplicate.impl.MD5CollectionFactory;
import cx.ath.choisnet.util.duplicate.tasks.Task;
import cx.ath.choisnet.util.duplicate.tasks.TasksFactory;
import java.io.File;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
**
*/
public class MD5FileCollectionCompatorTest extends TestCase
{
/**
**
*/
public static Test suite() // ---------------------------------------------
{
 return new TestSuite( MD5FileCollectionCompatorTest.class );
}


/** */
private MD5CollectionTest.MyTestCase[] myTestCase;

/**
**
*/
protected void setUp() // -------------------------------------------------
{
 this.myTestCase = MD5CollectionTest.getMyTestCase();
}

/**
** Vérification  getMD5CollectionCompatorResult()
*/
public void testCompatorResult() // ---------------------------------------
    throws java.io.IOException, ClassNotFoundException
{
/*
 final MD5FileCollectionCompatorTest md5cmp
        = new MD5FileCollectionCompatorTest(
                this.myTestCase[ 0 ].md5Collection,
                this.myTestCase[ 1 ].md5Collection,
                getTasksFactory()
                );

 int taskListSize = md5cmp.getTasksList().size();

 assertEquals( "Tâches attendues " + taskListSize, taskListSize, 0 );
*/
}

/**
** Vérification  getMD5CollectionCompatorResult()
*/
public void testCompatorResultNoChange() // ---------------------------------------
    throws java.io.IOException, ClassNotFoundException
{
/*
 final MD5FileCollectionCompatorTest md5cmp
        = new MD5FileCollectionCompatorTest(
                this.myTestCase[ 0 ].md5Collection,
                this.myTestCase[ 0 ].md5Collection,
                getTasksFactory()
                );

 int taskListSize = md5cmp.getTasksList().size();

 assertEquals( "pas de tâches attendues " + taskListSize, taskListSize, 0 );
 */
}

/**
**
*/
public final static TasksFactory getTasksFactory() // ---------------------
{
 return new TasksFactory<File>()
    {

    public File getLocalTmpName( final MD5TreeEntry md5 )
    {
        return null;
    }

    public java.io.InputStream getInputStreamFromSource( final MD5TreeEntry md5 )
    {
        return null;
    }

    public Task buildActionLocalCreateFolder( final File f )
    {
        return null;
    }

    public Task buildActionLocalDeleteFolder( final File f )
    {
        return null;
    }

    public Task buildActionLocalDeleteFile( final File f )
    {
        return null;
    }

    public Task buildActionLocalCopyFile( final MD5TreeEntry f1, final File f2 )
    {
        return null;
    }

    public Task buildActionLocalCopyFile( final File f1, final File f2 )
    {
        return null;
    }

    public Task buildActionCopyFileFromSource( final MD5TreeEntry f1 )
    {
        return null;
    }

    public Task buildActionLocalMoveFile( final File f1, final File f2 )
    {
        return null;
    }

    };
}


} // class


