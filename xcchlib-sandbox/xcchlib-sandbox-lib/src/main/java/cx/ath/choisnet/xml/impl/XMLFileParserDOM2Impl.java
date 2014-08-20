/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/xml/impl/XMLFileParserDOM2Impl.java
** Description   :
** Encodage      : ANSI
**
**  3.01.028 2006.04.19 Claude CHOISNET - Version initiale
**                      Nom: cx.ath.choisnet.xml.impl.XMLFileParserDOM2
**  3.02.002 2006.05.30 Claude CHOISNET - Version initiale
**                      Nouveau nom: cx.ath.choisnet.xml.impl.XMLFileParserDOM2Impl
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.xml.impl.XMLFileParserDOM2Impl
**
*/
package cx.ath.choisnet.xml.impl;

import java.io.File;
import java.util.EnumSet;
import cx.ath.choisnet.xml.XMLFileParser;
import cx.ath.choisnet.xml.XMLParserErrorHandler;

/**
**
** @author Claude CHOISNET
** @since   3.01.028
** @version 3.02.002
*/
public class XMLFileParserDOM2Impl
    extends XMLParserDOM2Impl
        implements XMLFileParser
{
private final File file;

@SuppressWarnings("resource")
public XMLFileParserDOM2Impl( // ------------------------------------------
    final File                                  sourceFile,
    final EnumSet<XMLParserDOM2Impl.Attributs>  attributes,
    final XMLParserErrorHandler                 errorHandler

    )
    throws
        java.io.FileNotFoundException,
        cx.ath.choisnet.xml.XMLParserException
{
 super(
    new java.io.FileInputStream( sourceFile ),
    attributes,
    errorHandler
    );

 this.file = sourceFile;
}

/**
** Retourne l'objet File de la source.
*/
@Override
public File getFile() // --------------------------------------------------
{
 return this.file;
}

} // class



