/*
** -----------------------------------------------------------------------
** Nom           : org/homedns/chez/jtools/lib/ContextInitializationException.java
** Description   :
** Encodage      : ANSI
**
**  1.01.001 2007.01.01 Claude CHOISNET - Version initiale
** -----------------------------------------------------------------------
**
** org.homedns.chez.jtools.lib.ContextInitializationException
**
*/
package org.homedns.chez.jtools.lib;


/**
**
** @author Claude CHOISNET
** @since   1.01.001
** @version 1.01.001
*/
public class ContextInitializationException
    extends Exception
{

/** serialVersionUID */
private static final long serialVersionUID = 1L;

/**
**
*/
public ContextInitializationException( // ---------------------------------
    String      name,
    Throwable   cause
    )
{
 super( name, cause );
}

/**
**
*/
public ContextInitializationException( // ---------------------------------
    String      name
    )
{
 super( name );
}

}

