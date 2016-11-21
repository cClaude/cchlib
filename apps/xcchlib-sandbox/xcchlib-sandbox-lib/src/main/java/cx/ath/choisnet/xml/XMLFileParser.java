/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/xml/XMLFileParser.java
** Description   :
** Encodage      : ANSI
**
**  3.01.028 2006.04.19 Claude CHOISNET - Version initiale
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.xml.XMLFileParser
**
*/
package cx.ath.choisnet.xml;

import java.io.File;

/**
**
** @author  Claude CHOISNET
** @since   3.01.028
** @version 3.01.028
*/
public interface XMLFileParser
    extends XMLParser
{

/**
** Retourne l'objet File de la source.
*/
public File getFile(); // -------------------------------------------------


} // interface
