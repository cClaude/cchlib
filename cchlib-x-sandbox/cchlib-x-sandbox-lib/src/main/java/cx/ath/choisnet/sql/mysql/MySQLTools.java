/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/sql/mysql/MySQLTools.java
** Description   :
** Encodage      : ANSI
**
**  1.00.___ 2003.01.13 C.CHOISNET - Version initiale
**                      Nom de la classe
**                          cam.amt.techno.drs.drsbasique.connection.sql.MySQLTools
**  1.49.023 2004.06.09 Claude CHOISNET
**                      Changement de package :
**                          cam.amt.techno.drs.sql.MySQLTools
**  1.52.016 2004.12.07 Claude CHOISNET
**                      Correction d'un bug sur la méthode
**                          parseFieldValue()
**  3.01.026 2006.04.19 Claude CHOISNET
**                      Utilisation de StringBuilder en lieu et place de
**                      StringBuffer
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.sql.mysql.MySQLTools
**
*/
package cx.ath.choisnet.sql.mysql;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;

/**
**
** @author Claude CHOISNET
** @version 1.00
*/
public class MySQLTools
{

/** */
protected static Map<String,String> replacePhrases = new HashMap<String,String>();

/**
** Initialisations
*/
static {
    replacePhrases.put( "'", "\\'" );
    replacePhrases.put( "\\", "\\\\" );
    };

/**
**
*/
public static String parseFieldValue( // ----------------------------------
    final String    fieldValue,
    final int       maxLength
    )
{
 final int len = fieldValue.length();

 if( len > maxLength ) {
    return parseFieldValue( fieldValue.substring( 0, maxLength - 1 ) );
    }
 else {
    return parseFieldValue( fieldValue );
    }
}

/**
**
*/
public static String parseFieldValue( final String input ) // -------------
{
 //
 // http://access1.sun.com/FAQSets/javaprogfaq.html#6
 //
 final Iterator entryIt = replacePhrases.entrySet().iterator();

 //Each problem character is used as a delimiter
 //If the problem character is present in input string>
 //then the string will be tokenized.

 StringBuilder output = null;

 while( entryIt.hasNext() ) {
    Map.Entry       entry   = (Map.Entry) entryIt.next();
    StringTokenizer s       = new StringTokenizer(input, (String)entry.getKey());

    //If string tokenized, take the token before the problem
    //character, concat with the replacement character and then
    //concat the next token.

    if( s.countTokens() > 1 ) {
        output = new StringBuilder( s.nextToken() );

        while( s.hasMoreTokens() ) {
            // output += (String)entry.getValue() + s.nextToken();
            output.append( ((String)entry.getValue()) + s.nextToken() );
            }
        }
    }

 if( output == null ) {
    return input;
    }

 return output.toString();
}

} // class
