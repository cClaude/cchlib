package alpha.com.googlecode.cchlib.io.filetype;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.stream.ImageInputStream;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import com.googlecode.cchlib.lang.StringHelper;

public class MetadataApp
{
    private static final int MAX_FILES = 50;

    private void processAllFilesIn( final String directoryWithPicturesName )
    {
        final File   dirFile = new File( directoryWithPicturesName );
        final File[] files   = dirFile.listFiles();

        if( files != null ) {
            for( int i = 0; i < Math.min( files.length, MAX_FILES ); i++ ) {
                final File file = files[ i ];

                println( "<!-- File: " + file + " -->" );
                readAndDisplayMetadata( file );
                println( StringHelper.EMPTY );
            }
        } else {
            println( "Not a valid directory: \"" + directoryWithPicturesName + '"' );
        }
    }

    private void readAndDisplayMetadata( final File file )
    {
        try( ImageInputStream iis = ImageIO.createImageInputStream( file ) ) {
            final Iterator<ImageReader> readers = ImageIO.getImageReaders( iis );

            while( readers.hasNext() ) {
                // pick the first available ImageReader
                final ImageReader reader = readers.next();

                // attach source to the reader
                reader.setInput( iis, true );

                // read metadata of first image
                IIOMetadata metadata;

                metadata = getImageMetadata( reader );

                if( metadata != null ) {
                    final String[] names = metadata.getMetadataFormatNames();

                    displayMetadata( metadata, names );
                }
            }
        }
        catch( final IOException e ) {
            printStackTrace( e );
            return;
        }
    }

    private IIOMetadata getImageMetadata( final ImageReader reader )
    {
        try {
            return reader.getImageMetadata( 0 );
        }
        catch( final IOException e ) {
            printStackTrace( e );

            return null;
        }
    }

    private void displayMetadata( final IIOMetadata metadata, final String[] names )
    {
        final int length = names.length;

        for( int i = 0; i < length; i++ ) {
            println( "<!-- Format name: " + names[ i ] + " -->" );
            displayMetadata( metadata.getAsTree( names[ i ] ) );
        }
    }

    private void displayMetadata( final Node root )
    {
        displayMetadata( root, 0 );
    }

    private void displayMetadata( final Node node, final int level )
    {
        // print open tag of element
        indent( level );
        print( "<" + node.getNodeName() );
        final NamedNodeMap map = node.getAttributes();

        if( map != null ) {
            // print attribute values
            final int length = map.getLength();

            for( int i = 0; i < length; i++ ) {
                final Node attr = map.item( i );
                print( " " + attr.getNodeName() + "=\"" + attr.getNodeValue() + "\"" );
            }
        }

        Node child = node.getFirstChild();
        if( child == null ) {
            // no children, so close element and return
            println( "/>" );
            return;
        }

        // children, so close current tag
        println( ">" );
        while( child != null ) {
            // print children recursively
            displayMetadata( child, level + 1 );
            child = child.getNextSibling();
        }

        // print close tag of element
        indent( level );
        println( "</" + node.getNodeName() + ">" );
    }

    private void indent( final int level )
    {
        for( int i = 0; i < level; i++ ) {
            print( "    " );
        }
    }

    @SuppressWarnings("squid:S106")
    private void println( final String str )
    {
        System.out.println( str );
    }

    @SuppressWarnings("squid:S106")
    private void print( final String str )
    {
        System.out.print( str );
    }

    @SuppressWarnings("squid:S1148")
    private void printStackTrace( final IOException cause )
    {
        cause.printStackTrace();
    }

    public static void main( final String[] args )
    {
        final String   directoryWithPicturesName = "X:/My Skype Pictures"; //args[ 0 ];
        final MetadataApp meta                      = new MetadataApp();

        meta.processAllFilesIn( directoryWithPicturesName );
    }
}
