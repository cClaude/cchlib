/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/net/ProxySelectorInterface.java
** Description   :
** Encodage      : ANSI
**
**  2.02.039 2006.01.04 Claude CHOISNET - Version initiale
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.net.ProxySelectorInterface
**
**
*/
package cx.ath.choisnet.net;

import java.net.Proxy;
import java.net.URL;

/**
**
**
** @author Claude CHOISNET
** @version 1.0
** @since 1.00
*/
public interface ProxySelectorInterface
{

/**
**
*/
public Proxy select( URL anUrl ); // --------------------------------------

} // class
