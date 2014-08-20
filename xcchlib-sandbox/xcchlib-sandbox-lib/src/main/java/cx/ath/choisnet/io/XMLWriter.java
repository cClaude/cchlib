/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/io/XMLWriter.java
** Description   :
** Encodage      : ANSI
**
**  3.02.003 2005.05.20 Claude CHOISNET - Version initiale
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.io.XMLWriter
**
*/
package cx.ath.choisnet.io;

import java.io.Writer;

/**
** <p>
** Classe permettant de contruire des flux XML
** Ecrit le flux en transformant les caracteres speciaux en leur equivalent
** HTML
** </p>
**
**
** @author Claude CHOISNET
** @since   3.02.003
** @version 3.02.003
*/
final public class XMLWriter
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
** Creation d'un nouvel objet {@link XMLWriter}
*/
public XMLWriter( // ------------------------------------------------------
    final Writer writer
    )
{
 this.writer    = writer;
 this.sbuffer   = new StringBuilder();
}

/**
** @see Writer#close()
*/
@Override
public void close() // ----------------------------------------------------
    throws java.io.IOException
{
 this.writer.close();
}

/**
** @see Writer#flush()
*/
@Override
public void flush() // ----------------------------------------------------
    throws java.io.IOException
{
 this.writer.flush();
}

/**
**
** @see Writer#write(char[] cbuf, int off, int len)
*/
@Override
public void write( final char[] cbuf, final int off, final int len ) // ---
    throws java.io.IOException
{
 synchronized( super.lock ) {
    sbuffer.setLength( 0 );

    for( int i = off; i<len; i++ ) {
        switch( cbuf[ i ] ) {
            case '>' :
                sbuffer.append( "&gt;" );
                break;

            case '<' :
                sbuffer.append( "&lt;" );
                break;

            case '&' :
                sbuffer.append( "&amp;" );
                break;

            default :
                sbuffer.append( cbuf[ i ] );
                break;
            }
        }

    this.writer.write( sbuffer.toString() );
    }
}

/**
** Permet d'inclure la trace d'une exception dans un flux XML
*/
public void write( final Throwable throwable ) // -------------------------
    throws java.io.IOException
{
 final java.io.StringWriter sw = new java.io.StringWriter();

 throwable.printStackTrace( new java.io.PrintWriter( sw ) );

 this.write( sw.toString() );
}

/**
** Ecrit vers le flux parent sans aucune modification
**
** @see Writer#write(String)
*/
public void rawWrite( final String str ) // -------------------------------
    throws java.io.IOException
{
 this.writer.write( str );
}

/**
** Ecrit vers le flux parent sans aucune modification
**
** @see Writer#write(char[])
*/
public void rawWrite( final char[] cbuf ) // ------------------------------
    throws java.io.IOException
{
 this.writer.write( cbuf );
}

/**
** Ecrit vers le flux parent sans aucune modification
**
** @see Writer#write(char[] cbuf, int off, int len)
*/
public void rawWrite( // --------------------------------------------------
    final char[]    cbuf,
    final int       off,
    final int       len
    )
    throws java.io.IOException
{
 this.writer.write( cbuf, off, len );
}

} // class
