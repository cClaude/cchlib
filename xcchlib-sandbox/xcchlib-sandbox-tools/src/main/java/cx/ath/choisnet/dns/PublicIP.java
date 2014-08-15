/*
** -----------------------------------------------------------------------
** Nom           : cx\ath\choisnet\dns\PublicIP.java
** Description   :
** Encodage      : ANSI
**
**  1.00 2005.02.27 Claude CHOISNET
**  1.02 2006.04.06 Claude CHOISNET
**                  Adaptation aux �volutions de
**                      cx.ath.choisnet.dns.PublicIPReader
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.dns.PublicIP
**
*/
package cx.ath.choisnet.dns;

import org.apache.log4j.Logger;

/**
**
*/
public class PublicIP
    implements PublicIPReader
{
/** serialVersionUID */
private static final long serialVersionUID = 1L;

/** Objet d'acquisition de l'IP publique */
private final PublicIPReader publicIPReader;

/** */
private Long lastChangeTimeMillis;

/**
** Objet statique pour les traitements globaux au niveau de la JVM.
*/
private static PublicIP globalPublicIP = null;

/**
**
*/
public PublicIP( // -------------------------------------------------------
    final PublicIPReader publicIPReader
    )
    throws PublicIPException
{
 this.publicIPReader        = publicIPReader;
 this.lastChangeTimeMillis  = System.currentTimeMillis();
}


/**
**
*/
public boolean hasChange() // ---------------------------------------------
    throws PublicIPException
{
 final String   currentIP;
 final boolean  hasChange;

 try {
    currentIP = this.publicIPReader.getCurrentPublicIP();
    hasChange = !currentIP.equals( this.publicIPReader.getPreviousPublicIP() );

    this.publicIPReader.storePublicIP(); // on sauvegarde � tous les coups
    }
 catch( final PublicIPException e ) {
    getLogger().warn( "hasChange{} - getCurrentPublicIP{}", e );

    return false;
    }

 if( hasChange ) {
    getLogger().info( "new IP : " + currentIP );

    this.lastChangeTimeMillis = System.currentTimeMillis();
    }

 return hasChange;
}

/**
**
*/
public long getLastChangeTimeMillis() // ----------------------------------
{
 return this.lastChangeTimeMillis.longValue();
}

/**
**
*/
@Override
public String getCurrentPublicIP() // -------------------------------------
    throws PublicIPException
{
 return this.publicIPReader.getCurrentPublicIP();
}

/**
**
*/
@Override
public String getPreviousPublicIP() // ------------------------------------
    throws PublicIPException
{
 return this.publicIPReader.getPreviousPublicIP();
}

/**
**
*/
@Override
public void storePublicIP() // --------------------------------------------
    throws PublicIPException
{
 this.publicIPReader.storePublicIP();
}


/**
**
*/
public static PublicIP getGlobalPublicIP() // -----------------------------
{
 return globalPublicIP;
}

/**
**
*/
public synchronized static void setGlobalPublicIP( // ---------------------
        final PublicIP publicIP
        )
{
 globalPublicIP = publicIP;
}

/**
**
*/
public synchronized static void setOnceGlobalPublicIP( // -----------------
        final PublicIP publicIP
        )
{
 if( globalPublicIP == null ) {
    globalPublicIP = publicIP;
    }
}

/** Gestion des traces (Serializable) */
private transient Logger transientLogger = null;

/**
** Gestion des traces (Serializable)
*/
final protected Logger getLogger() // -------------
{
 if( transientLogger == null ) {
    transientLogger = Logger.getLogger( this.getClass() );
    }

 return transientLogger;
}

} // class

