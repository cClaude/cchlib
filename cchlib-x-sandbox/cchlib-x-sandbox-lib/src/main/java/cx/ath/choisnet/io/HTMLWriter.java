/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/io/HTMLWriter.java
** Description   :
** Encodage      : ANSI
**
**  1.30.___ 2005.05.11 Claude CHOISNET
**  1.31.___ 2005.05.11 Claude CHOISNET
**                      Ajout de write( Throwable );
**  1.50.___ 2005.05.20 Claude CHOISNET
**                      Prise en compte (approximative) de la tabulation
**  1.51.___ 2005.05.20 Claude CHOISNET
**                      Correction de l'encodage pour le \n et \r
**  2.01.009 2005.05.20 Claude CHOISNET
**                      Utilisation de StringBuilder en lieu et place de
**                      StringBuffer
**  3.02.003 2005.05.20 Claude CHOISNET
**                      Ajout de: rawWrite(String)
**                      Deprecated de: writeHTML(String)
**                      Ajout d'une synchronisation sur la m�thode
**                      write(byte[],int,int)
**  3.02.009 2006.06.12 Claude CHOISNET
**                      Documentation
**  3.02.025 2006.07.18 Claude CHOISNET
**                      Les m�thodes ne produisent plus de java.io.IOException,
**                      mais une HTMLWriterException. Objectif facilit�
**                      la source de l'exception.
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.io.HTMLWriter
**
*/
package cx.ath.choisnet.io;

import java.io.Writer;

/**
** <p>
** Flux destin� a �tre utiliser pour contruire une page HTML.
** </p>
**
**
** @author Claude CHOISNET
** @since   1.30
** @version 3.02.025
*/
final public class HTMLWriter
    extends Writer
        implements
            Appendable,
            java.io.Flushable,
            java.io.Closeable
{
/**
**
*/
private final Writer writer;

/**
**
*/
private final StringBuilder sbuffer;

/**
** Ecrit le flux en transformant les caract�res sp�ciaux en leur �quivalent
** HTML
*/
public HTMLWriter( // -----------------------------------------------------
    final Writer writer
    )
{
 this.writer    = writer;
 this.sbuffer   = new StringBuilder();
}

/**
** Vide les caches {@link #flush()} et ferme le flux (y compris le flux
** p�re)
**
** @see #flush()
** @see Writer#close()
*/
@Override
public void close() // ----------------------------------------------------
    throws HTMLWriterException // java.io.IOException
{
 try {
    this.flush();
    this.writer.close();
    }
 catch( java.io.IOException e ) {
    throw new HTMLWriterException( e );
    }
}

/**
** @see Writer#flush()
*/
@Override
public void flush() // ----------------------------------------------------
    throws HTMLWriterException // java.io.IOException
{
 try {
    this.writer.flush();
    }
 catch( java.io.IOException e ) {
    throw new HTMLWriterException( e );
    }
}

/**
** @deprecated use {@link #rawWrite(String)}
*/
@Deprecated
public void writeHTML( final String htmlWellFormatString ) // -------------
    throws java.io.IOException
{

 this.writer.write( htmlWellFormatString );
}

/**
**
** @since 3.02.003
*/
public void rawWrite( final String htmlWellFormatString ) // --------------
    throws HTMLWriterException // java.io.IOException
{
 try {
    this.writer.write( htmlWellFormatString );
    }
 catch( java.io.IOException e ) {
    throw new HTMLWriterException( e );
    }
}

/**
** @see Writer#write(char[] cbuf, int off, int len)
*/
@Override
public void write( final char[] cbuf, final int off, final int len ) // ---
    throws HTMLWriterException // java.io.IOException
{
 synchronized( super.lock ) {
    sbuffer.setLength( 0 );

    for( int i = off; i<len; i++ ) {
        switch( cbuf[ i ] ) {
            case 0x20 : // Space
                sbuffer.append( "&nbsp;" );
                break;

            case '\r' :
                sbuffer.append( "<!-- \\r -->\r" );
                break;

            case '\n' :
                sbuffer.append( "<br /><!-- \\n -->\n" );
                break;

            case '\t' : // Tabulation !
                sbuffer.append( "&nbsp;&nbsp;&nbsp;&nbsp;" );
                break;

            case '>' :
                sbuffer.append( "&gt;" );
                break;

            case '<' :
                sbuffer.append( "&lt;" );
                break;

            case '"' :
                sbuffer.append( "&quot;" );
                break;

            case '&' :
                sbuffer.append( "&amp;" );
                break;

            default :
                sbuffer.append( cbuf[ i ] );
                break;
            }
        }

    // this.writer.write( sbuffer.toString() );
    rawWrite( sbuffer.toString() );
    }
}

/**
**
**
** @see Writer#write(char[] cbuf, int off, int len)
**
** @since 3.02.025
*/
@Override
public void write( final char[] cbuf ) // ---------------------------------
    throws HTMLWriterException
{
 write( cbuf, 0, cbuf.length );
}

/**
**
**
** @see Writer#write(char[] cbuf, int off, int len)
**
** @since 3.02.025
*/
@Override
public void write( final String str ) // ----------------------------------
    throws HTMLWriterException
{
 write( str.toCharArray() );
}

/**
**
**
** @see Writer#write(char[] cbuf, int off, int len)
**
** @since 3.02.025
*/
@Override
public void write( final String str, final int off, final int len ) // ----
    throws HTMLWriterException
{
 write( str.toCharArray(), off, len );
}

/**
**
**
** @see Writer#write(char[] cbuf, int off, int len)
**
** @since 1.31
*/
public void write( final Throwable throwable ) // -------------------------
    throws HTMLWriterException //java.io.IOException
{
 final java.io.StringWriter sw = new java.io.StringWriter();

 throwable.printStackTrace( new java.io.PrintWriter( sw ) );

 write( sw.toString() );
}

} // class
