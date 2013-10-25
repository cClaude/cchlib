/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/io/EmptyWriter.java
** Description   :
** Encodage      : ANSI
**
**  1.00.001 2004.06.09 Claude CHOISNET
**  2.01.024 2006.04.14 Claude CHOISNET
**                      Ajout de la gestion des erreurs en cas d'utilisation
**                      flux
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.io.EmptyWriter
**
*/
package cx.ath.choisnet.io;

/**
** <P>
** Construction d'un {@link java.io.Writer} absorbant.  (cas limite: trou noir)
** </P>
**
** @author Claude CHOISNET
** @since  2.01.024
*/
public class EmptyWriter
    extends java.io.Writer

{
private boolean open;

/**
**
*/
public EmptyWriter() // ---------------------------------------------------
{
 this.open = true;
}

/**
** @see java.io.Writer#close()
*/
public void close() // ----------------------------------------------------
    throws java.io.IOException
{
 if( ! this.open ) {
    throw new java.io.IOException( "aleary close" );
    }

 this.open = false;
}

/**
** @see java.io.Writer#flush()
*/
public void flush() // ----------------------------------------------------
    throws java.io.IOException
{
 if( ! this.open ) {
    throw new java.io.IOException( "is close" );
    }
}

/**
** @see java.io.Writer#write(char[],int,int)
*/
public void write( char[] cbuf, int off, int len ) // ---------------------
    throws java.io.IOException
{
 if( ! this.open ) {
    throw new java.io.IOException( "is close" );
    }
}

} // class
