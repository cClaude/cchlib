/*
** -----------------------------------------------------------------------
** Nom           : org/homedns/chez/jtools/webapptools/WEBAppToolsInitializationException.java
** Description   :
** Encodage      : ANSI
**
**  1.01.001 2007.01.01 Claude CHOISNET - Version initiale
** -----------------------------------------------------------------------
**
** org.homedns.chez.jtools.webapptools.WEBAppToolsInitializationException
**
*/
package org.homedns.chez.jtools.webapptools;

import org.homedns.chez.jtools.lib.ContextInitializationException;

/**
**
** @author Claude CHOISNET
** @since   1.01.001
** @version 1.01.001
*/
public class WEBAppToolsInitializationException
    extends ContextInitializationException
{
/** serialVersionUID */
private static final long serialVersionUID = 1L;

/**
**
*/
public WEBAppToolsInitializationException( // -----------------------------
    String message,
    Throwable cause
    )
{
 super( message, cause );
}

/**
**
*/
public WEBAppToolsInitializationException( // -----------------------------
    String message
    )
{
 super( message );
}

} // class
