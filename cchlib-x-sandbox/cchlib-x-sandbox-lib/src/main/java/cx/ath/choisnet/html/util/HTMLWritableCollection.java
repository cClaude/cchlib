/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/html/util/HTMLWritableCollection.java
** Description   :
**
**  3.02.031 2006.07.24 Claude CHOISNET - Version initial
**                      Nom: cx.ath.choisnet.html.HTMLWritableCollection
**  3.02.037 2006.08.07 Claude CHOISNET
**                      Nouveau nom: cx.ath.choisnet.html.util.HTMLWritableCollection
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.html.util.HTMLWritableCollection
**
*/
package cx.ath.choisnet.html.util;

import java.util.LinkedList;
import java.util.List;

/**
** <p>
** Impl�mentation d'un conteneur {@link HTMLWritable}, pouvant recevoir d'autre
** objects {@link HTMLWritable}
** </p>
**
** @author Claude CHOISNET
** @since   3.02.031
** @version 3.02.037
*/
public class HTMLWritableCollection
    implements HTMLWritable
{
/** */
private final List<HTMLWritable> htmlCollection;

/**
**
*/
public HTMLWritableCollection() // ----------------------------------------
{
 this.htmlCollection = new LinkedList<HTMLWritable>();
}

/**
**
*/
public HTMLWritableCollection add( final HTMLWritable entry ) // ----------
{
 this.htmlCollection.add( entry );

 return this;
}

/**
**
*/
public HTMLWritableCollection add( final String htmlStr ) // --------------
{
 this.htmlCollection.add( new HTMLString( htmlStr ) );

 return this;
}

/**
**
*/
@Override
public void write( final HTMLDocumentWriter out ) // ----------------------
    throws HTMLDocumentException
{
 for( HTMLWritable entry : this.htmlCollection ) {
    entry.write( out );

    try {
        out.append( '\n' );
        }
    catch( java.io.IOException e ) {
        throw new HTMLDocumentException( e );
        }
    }
}

} // class

