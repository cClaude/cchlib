package cx.ath.choisnet.dns;

import org.apache.log4j.Logger;

/**
 * @since 1.02
 */
public class PublicIP implements PublicIPReader
{
    private static final long    serialVersionUID = 1L;

    /** Gestion des traces (Serializable) */
    private transient Logger transientLogger = null;

    /** Objet d'acquisition de l'IP publique */
    private final PublicIPReader publicIPReader;
    private long                 lastChangeTimeMillis;

    /**
     * Objet statique pour les traitements globaux au niveau de la JVM.
     */
    private static PublicIP      globalPublicIP   = null;

    public PublicIP( final PublicIPReader publicIPReader ) throws PublicIPException
    {
        this.publicIPReader = publicIPReader;
        this.lastChangeTimeMillis = System.currentTimeMillis();
    }

    public boolean hasChange() throws PublicIPException
    {
        final String  currentIP;
        final boolean hasChange;

        try {
            currentIP = this.publicIPReader.getCurrentPublicIP();
            hasChange = !currentIP.equals( this.publicIPReader.getPreviousPublicIP() );

            this.publicIPReader.storePublicIP(); // on sauvegarde e tous les coups
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

    public long getLastChangeTimeMillis()
    {
        return this.lastChangeTimeMillis;
    }

    @Override
    public String getCurrentPublicIP() throws PublicIPException
    {
        return this.publicIPReader.getCurrentPublicIP();
    }

    @Override
    public String getPreviousPublicIP() throws PublicIPException
    {
        return this.publicIPReader.getPreviousPublicIP();
    }

    @Override
    public void storePublicIP() throws PublicIPException
    {
        this.publicIPReader.storePublicIP();
    }

    public static synchronized PublicIP getGlobalPublicIP()
    {
        return globalPublicIP;
    }

    public static synchronized void setGlobalPublicIP( final PublicIP publicIP )
    {
        globalPublicIP = publicIP;
    }

    public static synchronized void setOnceGlobalPublicIP(
        final PublicIP publicIP
        )
    {
        if( globalPublicIP == null ) {
            globalPublicIP = publicIP;
        }
    }

    protected final  Logger getLogger()
    {
        if( this.transientLogger == null ) {
            this.transientLogger = Logger.getLogger( this.getClass() );
        }

        return this.transientLogger;
    }
}
