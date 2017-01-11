package com.googlecode.cchlib.apps.emptydirectories.gui.tree.model;

import static org.fest.assertions.api.Assertions.assertThat;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import com.googlecode.cchlib.lang.StringHelper;
import com.googlecode.cchlib.util.emptydirectories.EmptyFolder;
import com.googlecode.cchlib.util.emptydirectories.util.Folders;

public class FolderTreeBuilderTest
{
    private static final Logger LOGGER = Logger.getLogger( FolderTreeBuilderTest.class );
    private static final String TAB = "  ";

    private final List<Path> globalList = new ArrayList<>();

    @Before
    public void setUp() throws Exception
    {
        this.globalList.clear();
    }

    @Test
    public void testAdd() throws IOException
    {
        final FolderTreeModelable model = Mockito.mock( FolderTreeModelable.class );

        final FolderTreeBuilder folderTreeBuilder = new FolderTreeBuilder( model  );
        final Path              emptyPath1        = createTempDirectory( getClass().getSimpleName() );
        final EmptyFolder       emptyFolder1      = Folders.createEmptyFolder( emptyPath1 );

        LOGGER.info( "############## emptyFolder1 = " + emptyFolder1 );
        folderTreeBuilder.add( emptyFolder1 );

        final Map<Path,FolderTreeNode> map = folderTreeBuilder.getRootNodesMap();

        assertThat( map ).hasSize( 1 );

        final FolderTreeNode rootFolderTreeNode =  map.values().iterator().next();
        LOGGER.info( "rootFolderTreeNode = " + rootFolderTreeNode );
        //assertEquals( 0, rootFolderTreeNode.g );
        //assertEquals( emptyPath.getNameCount() + 1, map.values().iterator().next(). );

        final Path         emptyPath2   = createTempDirectory( getClass().getSimpleName() );
        final EmptyFolder  emptyFolder2 = Folders.createEmptyFolder( emptyPath2 );

        LOGGER.info( "############## emptyFolder2 = " + emptyFolder2 );
        folderTreeBuilder.add( emptyFolder2 );
        assertThat( map ).hasSize( 1 );

        final Path        cbEmptyPath3   = createTempDirectory( getClass().getSimpleName() );
        final Path        emptyPath3     = cbEmptyPath3.resolve( "empty3" );
        createDirectory( emptyPath3 );
        final EmptyFolder cbEmptyFolder3 = Folders.createCouldBeEmptyFolder( cbEmptyPath3 );
        final EmptyFolder emptyFolder3   = Folders.createEmptyFolder( emptyPath3 );

        LOGGER.info( "############## emptyFolder3 = " + emptyFolder3 );
        folderTreeBuilder.add( emptyFolder3 );
        assertThat( map ).hasSize( 1 );

        LOGGER.info( "############## cbEmptyFolder3 = " + cbEmptyFolder3 );
        folderTreeBuilder.add( cbEmptyFolder3 );
        assertThat( map ).hasSize( 1 );

        for( final FolderTreeNode rootNode : map.values() ) {
            displayTree( rootNode, StringHelper.EMPTY );
            checkIfNoDoubleOnNode( rootNode );
            }

        LOGGER.info( "done" );
    }

    private void createDirectory( final Path dir ) throws IOException
    {
        final Path path = Files.createDirectories( dir );
        path.toFile().deleteOnExit();
    }

    private Path createTempDirectory( final String prefix ) throws IOException
    {
        final Path path = Files.createTempDirectory( prefix );

        path.toFile().deleteOnExit();

        return path;
    }

    private void checkIfNoDoubleOnNode( final FolderTreeNode node )
    {
        final List<Path>     list = new ArrayList<>();
        final Enumeration<?> enu  = node.children();

        while( enu.hasMoreElements() ) {
            final FolderTreeNode child = FolderTreeNode.class.cast( enu.nextElement() );
            final Path           path  = child.getFolder().getPath();
            final Path           name  = path.getFileName();

            LOGGER.info( "checkIfNoDoubleOnNode: " + node + " = " + name );
            assertThat( list ).doesNotContain( name );
            list.add( name );

            assertThat( this.globalList ).doesNotContain( name );
            this.globalList.add( path );

            checkIfNoDoubleOnNode( child );
            }
    }

    private void displayTree( final FolderTreeNode node, final String prefix )
    {
        final Enumeration<?> enu  = node.children();

        LOGGER.info( "T:" + prefix + node.getFolder().getPath() + " * " + node.getFolder() );

        while( enu.hasMoreElements() ) {
            final FolderTreeNode child = FolderTreeNode.class.cast( enu.nextElement() );

            displayTree( child, TAB + prefix );
            }
    }
}
