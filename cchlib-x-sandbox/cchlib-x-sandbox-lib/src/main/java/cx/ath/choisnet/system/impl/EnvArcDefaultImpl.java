/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/system/impl/EnvArcDefaultImpl.java
** Description   :
** Encodage      : ANSI
**
**  3.02.017 2006.06.28 Claude CHOISNET - Version initiale
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.system.impl.EnvArcDefaultImpl
**
*/
package cx.ath.choisnet.system.impl;

import cx.ath.choisnet.system.EnvArc;
import java.util.Map;

/**
**
** @author Claude CHOISNET
** @since   3.02.017
** @version 3.02.017
*/
public class EnvArcDefaultImpl
    implements EnvArc
{
/** */
private Map<String,String> env;

/**
**
*/
public EnvArcDefaultImpl() // ---------------------------------------------
{
 this.env = System.getenv();
}

/**
**
*/
public void setVar( final String varname, final String value ) // ---------
    throws
        cx.ath.choisnet.system.EnvArcException,
        UnsupportedOperationException
{
 throw new UnsupportedOperationException();
}

/**
**
*/
public String getVar( final String varname ) // ---------------------------
    throws cx.ath.choisnet.system.EnvArcException
{
 return this.env.get( varname );
}

} // class
