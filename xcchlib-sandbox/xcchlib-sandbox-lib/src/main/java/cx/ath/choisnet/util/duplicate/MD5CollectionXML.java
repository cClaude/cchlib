/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/util/duplicate/MD5CollectionXML.java
** Description   :
** Encodage      : ANSI
**
**  3.01.042 2006.05.24 Claude CHOISNET
**                      Reprise de la classe:
**                          cx.ath.choisnet.util.checksum.MD5CollectionXML
**                      sous le nom:
**                          cx.ath.choisnet.util.duplicate.MD5CollectionXML
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.util.duplicate.MD5CollectionXML
**
*/
package cx.ath.choisnet.util.duplicate;

import cx.ath.choisnet.util.checksum.MD5TreeEntry;
import cx.ath.choisnet.xml.XMLParser;
import java.util.SortedSet;
import java.util.TreeSet;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
** <p>
** Classe permettant de constuire des flux XML � partir d'un objet
** {@link MD5Collection} ou  d'un objet {@link MD5FileCollection}
** et r�ciproquement.
** </p>
**
** @author Claude CHOISNET
** @since   3.01.042
** @version 3.01.042
**
** @see cx.ath.choisnet.util.checksum.MD5Tree
** @see DuplicateFileLayer
** @see DuplicateLayer
*/
public class MD5CollectionXML
    extends cx.ath.choisnet.util.duplicate.impl.AbstractMD5Collection
{
/** serialVersionUID */
private static final long serialVersionUID = 1L;

/**
** Nom de l'�l�ment racine du document XML
*/
public final static String ROOT_ELEMENT_NAME
                            = MD5CollectionXML.class.getName();

/**
** Construction d'un MD5CollectionXML vide
*/
public MD5CollectionXML( // -----------------------------------------------
    final Document document
    )
    throws MD5CollectionXMLException
{
 super();

 final Element rootElement = document.getDocumentElement();

 if( ! rootElement.getTagName().equals( ROOT_ELEMENT_NAME ) ) {
    //
    // Prob. d'initialisation...
    //
    throw new MD5CollectionXMLException( "�l�ment inattendu '" + rootElement.getTagName() + "'" );
    }

 loadFolders( rootElement.getElementsByTagName( "folders" ) );
 loadFiles( rootElement.getElementsByTagName( "files" ) );
}

/**
** Construction d'un MD5CollectionXML vide
*/
public MD5CollectionXML( // -----------------------------------------------
    final XMLParser xmlParser
    )
    throws MD5CollectionXMLException
{
 this( xmlParser.getDocument() );
}

/**
** Sauvegarde l'objet dans un flux sous forme de XML.
**
** @param output Objet {@link Appendable} destin� � recevoir le flux XML.
**
** @throws java.io.IOException en cas de probl�me lors de l'ajout dans le flux.
**
** @see #toXML(MD5Collection,Appendable)
*/
public void toXML( final Appendable output ) // ---------------------------
    throws java.io.IOException
{
 toXML( this, output );
}

/**
**
**
**
*/
private void loadFolders( final NodeList foldersNodeList ) // -------------
    throws MD5CollectionXMLException
{
 final int len = foldersNodeList.getLength();

 if( len > 1 ) {
    throw new MD5CollectionXMLException( "to much folders Elements:" + len );
    }
 else if( len == 1 ) {
    final NodeList  nodeList= ((Element)foldersNodeList.item( 0 )).getElementsByTagName( "folder" );
    final int       max     = nodeList.getLength();

    for( int i = 0; i < max; i++ ) {
        final Node node = nodeList.item( i );

        if( node instanceof Element ) {
            // System.out.println( "node: " + node + " - " + node.getTextContent() );
            // this.dbFoldersFilenames.add( new File( node.getTextContent() ) );
            super.dbFoldersFilenames.add( node.getTextContent() );
            }
        }
    }
}

/**
**
**
**
*/
private void loadFiles( final NodeList filesNodeList ) // -----------------
    throws MD5CollectionXMLException
{
 final int len = filesNodeList.getLength();

 if( len > 1 ) {
    throw new MD5CollectionXMLException( "to much files Elements:" + len );
    }
 else if( len == 1 ) {
    final NodeList  nodeList= ((Element)filesNodeList.item( 0 )).getElementsByTagName( "entry" );
    final int       max     = nodeList.getLength();

    for( int i = 0; i < max; i++ ) {
        final Node node = nodeList.item( i );

        if( node instanceof Element ) {
            final Element       element = ((Element)node);
            final MD5TreeEntry  md5     = MD5TreeEntry.newInstance( element.getAttribute( "id" ) );

            super.dbMessageDigestFilenames.put(
                    md5,
                    buildFilesSet( element.getElementsByTagName( "file" ) )
                    );
            }
        }
    }
}

/**
**
**
**
*/
private static SortedSet<String> buildFilesSet( // ------------------------
    final NodeList nodeList
    )
{
 final int                  max     = nodeList.getLength();
 final SortedSet<String>    list    = new TreeSet<String>();

 for( int i = 0; i < max; i++ ) {
    final Node node = nodeList.item( i );

    if( node instanceof Element ) {
        // list.add( new File( node.getTextContent() ) );
        list.add( node.getTextContent() );
        }
    }

 return list;
}

/**
** Transformation d'un objet {@link MD5Collection} en un flux XML.
**
** @param aMD5Collection    Objet {@link MD5Collection} � transformer en XML.
** @param output            Objet {@link Appendable} destin� � recevoir le flux XML.
**
** @throws java.io.IOException en cas de probl�me lors de l'ajout dans le flux.
*/
public static void toXML( // ----------------------------------------------
    final MD5Collection aMD5Collection,
    final Appendable    output
    )
    throws java.io.IOException
{
 MD5CollectionHelper.toXML( ROOT_ELEMENT_NAME, aMD5Collection, output );
}

/**
** Transformation d'un objet {@link MD5Collection} en un flux XML.
**
** @param aMD5FileCollection    Objet {@link MD5Collection} � transformer en XML.
** @param output                Objet {@link Appendable} destin� � recevoir le flux XML.
**
** @throws java.io.IOException en cas de probl�me lors de l'ajout dans le flux.
*/
public static void toXML( // ----------------------------------------------
    final MD5FileCollection aMD5FileCollection,
    final Appendable        output
    )
    throws java.io.IOException
{
 MD5CollectionHelper.toXML( ROOT_ELEMENT_NAME, aMD5FileCollection, output );
}

/**
** java cx.ath.choisnet.util.checksum.MD5CollectionXML file.xml
public static void main( String[] args ) // -------------------------------
    throws Exception
{
 File                   file    = new File( args[ 0 ] );
 java.io.StringWriter   sw      = new java.io.StringWriter();
 MD5CollectionXML instance
        = new MD5CollectionXML(
                new cx.ath.choisnet.xml.XMLParserDOM2(
                    new java.io.FileInputStream( file ),
                    false,  //  boolean validation,
                    false,  //  boolean ignoreWhitespace,
                    false,  //  boolean ignoreComments,
                    false,  //  boolean putCDATAIntoText,
                    false,  // boolean createEntityRefs,
                    new cx.ath.choisnet.xml.XMLParserErrorHandler(
                        new java.io.PrintWriter( sw )
                        )
                    )
                );

 System.out.println( "ERROR" );
 System.out.print( sw.toString() );
 System.out.println( "-----" );
 System.out.println( "INSTANCE" );
 System.out.print( MD5CollectionXML.toXML( instance ) );
 System.out.println( "-----" );
}
*/

} // class
