package cx.ath.choisnet.i18n.builder;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.EnumSet;
import java.util.Properties;
import org.apache.log4j.Logger;
import cx.ath.choisnet.i18n.AutoI18n;
import cx.ath.choisnet.i18n.AutoI18nEventHandler;
import cx.ath.choisnet.i18n.AutoI18nExceptionHandler;
import cx.ath.choisnet.i18n.AutoI18nTypes;

/***
 * Abstract class of {@link AutoI18n} that allow to build initial properties
 * files for localization.
 */
public abstract class AbstractI18nPropertiesResourceAutoUpdate
    extends AbstractI18nResourceAutoUpdate
{
    private static final long serialVersionUID = 1L;
    private transient static Logger slogger = Logger.getLogger(AbstractI18nPropertiesResourceAutoUpdate.class);
    /** @serial */
    private Properties properties = new Properties();

    /**
     * Create an AbstractI18nPropertiesResourceAutoUpdate
     */
    public AbstractI18nPropertiesResourceAutoUpdate(
            I18nAutoUpdateInterface                             i18nAutoUpdateInterface,
            AutoI18nTypes                                       autoI18nTypes,
            AutoI18nExceptionHandler                            handler,
            AutoI18nEventHandler                                eventHandler,
            EnumSet<AutoI18n.Attribute>                         autoI18nAttributes,
            EnumSet<AbstractI18nResourceAutoUpdate.Attribute>   bundleAttributes
            )
    {
        super(
            i18nAutoUpdateInterface,
            autoI18nTypes,
            handler,
            eventHandler,
            autoI18nAttributes,
            bundleAttributes
            );
    }

    @Override
    protected void loadKnowValue() throws IOException
    {
        try {
            InputStream is = getResourceBundleInputStream();

            if( is == null ) {
                slogger.warn( "Can't open resource bundle for reading !" );
            }
            else {
                properties.load( is );
                is.close();
            }
        }
        catch( IOException e ) {
            slogger.warn( "Can't read resource bundle", e );
        }

        slogger.info( "Resource bundle entries count: " + properties.size() );
    }

    @Override
    public void saveValues() throws IOException
    {
        OutputStream os = getResourceBundleOutputStream();

        slogger.info( "saveValues(): know values = " + properties.size() );
        slogger.info( "saveValues(): unknow values = " + getProperties().size() );

        properties.putAll( getProperties() );

        if( os == null ) {
            slogger.warn( "Can't open resource bundle for writing !" );
        }
        else {
            properties.store( os, "Creat by :" + getClass().getName() );
            os.close();
        }
    }

    @Override // Closeable
    public void close() throws IOException
    {
        properties.clear();

        super.close();

        slogger.info( "New Resource bundle entries count: " + properties.size() );

        properties.clear(); // free some memory ;)
    }

    /**
     * InputStream to use to build Properties.
     *
     * @return an InputStream, may be null
     * @throws IOException
     */
    public abstract InputStream getResourceBundleInputStream()
        throws IOException;

    /**
     * OutputStream to use to store Properties.
     *
     * @return an InputStream, should not null
     * @throws IOException
     */
    public abstract OutputStream getResourceBundleOutputStream()
        throws IOException;
}
