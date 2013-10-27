/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/html/HTMLHeader.java
** Description   :
**
**  1.00.____ 2000.09.29 Claude CHOISNET - Version initiale
**                      Nom: cx.ath.choisnet.html.document.Header
**  1.50.___ 2005.05.19 Claude CHOISNET
**                      Adaptation � l'interface HTMLWritable
**  3.02.031 2006.07.24 Claude CHOISNET
**                      Nouveau nom: cx.ath.choisnet.html.HTMLHeader
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.html.HTMLHeader
**
*/
package cx.ath.choisnet.html;

import java.util.List;
import java.util.LinkedList;
import cx.ath.choisnet.html.util.HTMLDocumentWriter;
import cx.ath.choisnet.html.util.HTMLDocumentException;
import cx.ath.choisnet.html.util.HTMLWritable;
import cx.ath.choisnet.html.util.HTMLString;

/**
** <p>
**
** </p>
**
** @author Claude CHOISNET
** @version 3.02.031
*/
public class HTMLHeader
    implements HTMLWritable
{
/** */
private String title;

/** */
private boolean noCache = false;

/** */
// protected String extraDatas;

/** */
private List<HTMLLink> htmlLinks;

/** */
private List<HTMLString> htmlStrings;


/**
** Cr�ation d'une en-t�te de page HTML
*/
public HTMLHeader() // ----------------------------------------------------
{
 super();

 this.htmlLinks     = null;
 this.htmlStrings   = null;
}

/**
**
public HTMLHeader getThis() // --------------------------------------------
{
 return this;
}
*/

/**
** D�finition d'un titre
*/
public HTMLHeader setTitle( final String title ) // -----------------------
{
 this.title = title;

 return this;
}

/**
**
*/
public HTMLHeader setNoCache( final boolean noCache ) // ------------------
{
 this.noCache = noCache;

 return this;
}

/**
**
*/
public HTMLHeader add( final HTMLLink link ) // ---------------------------
{
 if( this.htmlLinks == null ) {
    this.htmlLinks = new LinkedList<HTMLLink>();
    }

 this.htmlLinks.add( link );

 return this;

}

/**
**
*/
public HTMLHeader add( final HTMLString htmlStr ) // ----------------------
{
 if( this.htmlStrings == null ) {
    this.htmlStrings = new LinkedList<HTMLString>();
    }

 this.htmlStrings.add( htmlStr );

 return this;
}

/**
**
*/
public HTMLHeader add( final String htmlStr ) // --------------------------
{
 return add( new HTMLString( htmlStr ) );
}

/**
**
*/
@Override
public void write( final HTMLDocumentWriter out ) // ----------------------
    throws HTMLDocumentException
{
 try {
    out.append( "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\"\n" );
    out.append( "   \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">\n" );

    out.append( "<head>\n" );
    out.append( "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\" />\n" );
 // out.append( "<meta name='author' content='******' />\n" );

    if( this.noCache ) {
        out.append( "<meta http-equiv=\"Cache-Control\" content=\"no-store\" />\n" );
        out.append( "<meta http-equiv=\"pragma\" content=\"no-cache\" />\n" );
        out.append( "<meta http-equiv=\"Expires\" content=\"Fri, Jun 12 1981 08:20:00 GMT\" />\n" );
        }

    if( title != null ) {
        out.append( "<title>" + title + "</title>\n" );
        }

    if( this.htmlLinks != null ) {
        for( HTMLLink link : this.htmlLinks ) {
            link.write( out );
            }
        }

    out.append( "</head>\n" );
    }
 catch( java.io.IOException e ) {
    throw new HTMLDocumentException( e );
    }
}

} //class

/*
 <link rel="stylesheet" type="text/css" media="screen" href="/common/css/screen.css" />
 <link rel="stylesheet" type="text/css" media="projection" href="/common/css/screen.css" />
 <link rel="stylesheet" type="text/css" media="print" href="/common/css/print.css" />
<link rel="stylesheet" type="text/css" href="/doc/docs.css" />
*/
