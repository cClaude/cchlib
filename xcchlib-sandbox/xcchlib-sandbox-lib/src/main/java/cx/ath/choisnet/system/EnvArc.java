/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/system/EnvArc.java
** Description   :
** Encodage      : ANSI
**
**  3.02.017 2006.06.28 Claude CHOISNET - Version initiale
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.system.EnvArc
**
*/
package cx.ath.choisnet.system;

/**
**
** @author Claude CHOISNET
** @since   3.02.017
** @version 3.02.017
*/
public interface EnvArc
{

/**
**
** @return le contenu de la variable 'varname'
**         ou null elle n'existe pas.
*/
public String getVar( String varname ) // ---------------------------------
    throws EnvArcException;

/**
**
*/
public void setVar( String varname, String value ) // ---------------------
    throws EnvArcException, UnsupportedOperationException;

} // class
