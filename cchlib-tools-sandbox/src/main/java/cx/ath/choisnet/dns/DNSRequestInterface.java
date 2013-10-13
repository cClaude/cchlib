/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/dns/DNSRequestInterface.java
** Description   :
**
** 1.00 2005.09.03 Claude CHOISNET - Version initiale
** -----------------------------------------------------------------------
**
**
** cx.ath.choisnet.dns.DNSRequestInterface
**
*/
package cx.ath.choisnet.dns;

import java.io.InputStream;

/**
**
** @author Claude CHOISNET
** @version 1.0
*/
public interface DNSRequestInterface
{

/**
**
*/
public InputStream getInputStream( String ip ) // -------------------------
    throws
        java.net.MalformedURLException,
        java.io.IOException;

/**
**
*/
public boolean updateIP( String ip ) // -----------------------------------
    throws java.io.IOException;

} // class
