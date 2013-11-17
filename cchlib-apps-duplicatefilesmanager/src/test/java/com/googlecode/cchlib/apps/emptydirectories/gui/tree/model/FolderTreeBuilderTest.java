package com.googlecode.cchlib.apps.emptydirectories.gui.tree.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import com.googlecode.cchlib.apps.emptydirectories.EmptyFolder;
import com.googlecode.cchlib.apps.emptydirectories.Folders;
import com.googlecode.cchlib.lang.StringHelper;

public class FolderTreeBuilderTest
{
    private static final Logger LOGGER = Logger.getLogger( FolderTreeBuilderTest.class );
    private static final String TAB = "  ";

    @BeforeClass
    public static void setUpBeforeClass()
    {}

    @AfterClass
    public static void tearDownAfterClass()
    {}

    private List<Path> globalList = new ArrayList<>();

    @Before
    public void setUp() throws Exception
    {
        globalList.clear();
    }

    @After
    public void tearDown() throws Exception
    {}

    @Test
    public void testAdd() throws IOException
    {
        final FolderTreeModelable model = Mockito.mock( FolderTreeModelable.class );

        FolderTreeBuilder folderTreeBuilder = new FolderTreeBuilder( model  );
        Path              emptyPath1        = Files.createTempDirectory( getClass().getSimpleName() );
        EmptyFolder       emptyFolder1      = Folders.createEmptyFolder( emptyPath1 );

        LOGGER.info( "############## emptyFolder1 = " + emptyFolder1 );
        folderTreeBuilder.add( emptyFolder1 );

        Map<Path,FolderTreeNode> map = folderTreeBuilder.getRootNodesMap();

        Assert.assertEquals( 1, map.size() );
        FolderTreeNode rootFolderTreeNode =  map.values().iterator().next();
        LOGGER.info( "rootFolderTreeNode = " + rootFolderTreeNode );
        //assertEquals( 0, rootFolderTreeNode.g );
        //assertEquals( emptyPath.getNameCount() + 1, map.values().iterator().next(). );

        Path         emptyPath2   = Files.createTempDirectory( getClass().getSimpleName() );
        EmptyFolder  emptyFolder2 = Folders.createEmptyFolder( emptyPath2 );

        LOGGER.info( "############## emptyFolder2 = " + emptyFolder2 );
        folderTreeBuilder.add( emptyFolder2 );
        Assert.assertEquals( 1, map.size() );

        Path        cbEmptyPath3   = Files.createTempDirectory( getClass().getSimpleName() );
        Path        emptyPath3     = cbEmptyPath3.resolve( "empty3" );
        Files.createDirectory( emptyPath3 );
        EmptyFolder cbEmptyFolder3 = Folders.createCouldBeEmptyFolder( cbEmptyPath3 );
        EmptyFolder emptyFolder3   = Folders.createEmptyFolder( emptyPath3 );

        LOGGER.info( "############## emptyFolder3 = " + emptyFolder3 );
        folderTreeBuilder.add( emptyFolder3 );
        Assert.assertEquals( 1, map.size() );

        LOGGER.info( "############## cbEmptyFolder3 = " + cbEmptyFolder3 );
        folderTreeBuilder.add( cbEmptyFolder3 );
        Assert.assertEquals( 1, map.size() );/* */

        for( FolderTreeNode rootNode : map.values() ) {
            displayTree( rootNode, StringHelper.EMPTY );
            checkIfNoDoubleOnNode( rootNode );
            }

        LOGGER.info( "done" );
    }

    private void checkIfNoDoubleOnNode( final FolderTreeNode node )
    {
        List<Path>     list = new ArrayList<>();
        Enumeration<?> enu  = node.children();

        while( enu.hasMoreElements() ) {
            FolderTreeNode child = FolderTreeNode.class.cast( enu.nextElement() );
            Path           path  = child.getFolder().getPath();
            Path           name  = path.getFileName();

            LOGGER.info( "checkIfNoDoubleOnNode: " + node + " = " + name );
            Assert.assertFalse( list.contains( name ) );
            list.add( name );

            Assert.assertFalse( globalList.contains( path ) );
            globalList.add( path );

            checkIfNoDoubleOnNode( child );
            }
    }

    private void displayTree( final FolderTreeNode node, String prefix )
    {
        Enumeration<?> enu  = node.children();

        LOGGER.info( "T:" + prefix + node.getFolder().getPath() + " * " + node.getFolder() );

        while( enu.hasMoreElements() ) {
            FolderTreeNode child = FolderTreeNode.class.cast( enu.nextElement() );

            displayTree( child, TAB + prefix );
            }
    }

}
