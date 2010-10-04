/**
 * 
 */
package cx.ath.choisnet.i18n.builder;

import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Properties;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import org.apache.log4j.Logger;
import cx.ath.choisnet.i18n.AutoI18n;
import cx.ath.choisnet.i18n.AutoI18nBasicInterface;
import cx.ath.choisnet.i18n.AutoI18nCustomInterface;
import cx.ath.choisnet.i18n.I18nDefaultImpl;
import cx.ath.choisnet.i18n.I18nInterface;

/***
 * IMPORTANT: 
 * Default handle:
 * Result is write into your classes directory!
 * 
 * @author Claude
 *
 */
public class AutoI18nUpdateBundle 
    extends AutoI18n
        implements I18nInterface, Closeable
{
    public enum Attrib {
        ADD_ONLY_NEEDED_KEY
    }
    private static Logger slogger = Logger.getLogger(AutoI18nUpdateBundle.class);
    private Map<String,String> keysValues = new HashMap<String,String>();
    private EnumSet<Attrib>    attribs;
    private I18nDefaultImpl    i18n;
    
    /**
     * @param i18n
     */
    public AutoI18nUpdateBundle( 
            I18nInterface i18n
            )
    {
        this( i18n, null );
    }
    
    /**
     * @param i18n
     * @param firstAttribute 
     * @param restAttribute 
     */
    public AutoI18nUpdateBundle( 
            I18nInterface   i18n,
            Attrib          firstAttribute,
            Attrib...       restAttribute
            )
    {
        this(i18n,EnumSet.of( firstAttribute, restAttribute ));
    }
    
    /**
     * @param i18n
     * @param attributes 
     */
    public AutoI18nUpdateBundle( 
            I18nInterface   i18n,
            EnumSet<Attrib> attributes
            )
    {
        super();
        setI18n( this.i18n );
        
        if(attributes == null) {
            this.attribs = EnumSet.noneOf( Attrib.class );
        }
        else {
            this.attribs = EnumSet.copyOf( attributes );
        }
    }
    
    @Override
    public void setI18n( final I18nInterface i18nObject )
    {
        this.i18n = new I18nDefaultImpl() {
            
        };
        super.setI18n( this.i18n );
    }
    
    @Override // AutoI18n
    protected String getKeyValue(AbstractFieldValue abstractFieldValue)
    {
        try {
            //slogger.info( "@key :" + keyOrValue.getKey() );
            String result = super.getKeyValue( abstractFieldValue );
        
            return result;
        }
        catch( MissingResourceException logIt ) {
            slogger.warn( "Found unkwnon key :" + abstractFieldValue.getKey() + " for Field:" + abstractFieldValue.getField().getName() );
        }
        
        Object fieldValue = null;
        
        try {
            fieldValue = abstractFieldValue.getField().get( abstractFieldValue.getObjectToI18n() );
        }
        catch( IllegalArgumentException e ) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch( IllegalAccessException e ) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        boolean done = false;
        
        if( fieldValue instanceof AutoI18nBasicInterface) {
            String text = AutoI18nBasicInterface.class.cast( fieldValue ).getI18nString();
            this.keysValues.put( abstractFieldValue.getKey(), text );
            done = true;
        }
//        else if( fieldValue instanceof AutoI18nCustomInterface ) {
//            Map<String, String> map = AutoI18nCustomInterface.class.cast( keyOrValue.getValue() ).getI18n( this.i18n );
//            this.keysValues.putAll( map );
//            done = true;
//        }
        else if( fieldValue instanceof JLabel ) {
            String text = JLabel.class.cast( fieldValue ).getText();
            this.keysValues.put( abstractFieldValue.getKey(), text );
            done = true;
        }
        else if( fieldValue instanceof JMenuItem ) {
            String text = JMenuItem.class.cast( fieldValue ).getText();
            this.keysValues.put( abstractFieldValue.getKey(), text );
            done = true;
        }
        else if( fieldValue instanceof JCheckBox ) {
            String text = JCheckBox.class.cast( fieldValue ).getText();
            this.keysValues.put( abstractFieldValue.getKey(), text );
            done = true;
        }

        if( !done ) {
            this.keysValues.put( abstractFieldValue.getKey(), abstractFieldValue.getField().getName() );
        }
        
        return abstractFieldValue.getField().getName();
    }
    
    @Override // AutoI18n
    protected void setAutoI18nCustomInterface( AutoI18nCustomInterface autoI18n )
    {
        try {
            autoI18n.setI18n( this.i18n );
            
            return;
        }
        catch( MissingResourceException logIt ) {
            Map<String, String> map = autoI18n.getI18n( this.i18n );
            this.keysValues.putAll( map );

            slogger.warn( "Found unkwnon key :" + toString( map.keySet(), 10) );
        }
    }

    private String toString( Collection<?> collection, int maxLen )
    {
        StringBuilder builder = new StringBuilder();
        builder.append( "[" );
        int i = 0;
        for( Iterator<?> iterator = collection.iterator(); iterator.hasNext()
                && i < maxLen; i++ ) {
            if( i > 0 ) builder.append( ", " );
            builder.append( iterator.next() );
        }
        builder.append( "]" );
        return builder.toString();
    }

    private void loadKnowValue(Properties prop)
    {
        try {
            InputStream is = getResourceBundleInputStream();
            
            if( is == null ) {
                slogger.warn( "Can't open resource bundle for reading !" );
            }
            else {
                prop.load( is );
                is.close();
            }
        }
        catch( IOException e ) {
            slogger.warn( "Can't read resource bundle", e );
        }
        
        slogger.info( "Resource bundle entries count: " + prop.size() );        
    }

    private void storeValues(Properties prop) throws IOException
    {
        OutputStream os = getResourceBundleOutputStream(); 
        
        if( os == null ) {
            slogger.warn( "Can't open resource bundle for writing !" );
        }
        else {            
            prop.store( os, "Creat by :" + getClass().getName() );
            os.close();
        }
    }

    @Override // Closeable
    public void close() throws IOException
    {
       Properties properties = new Properties();
        
        if( !this.attribs.contains( Attrib.ADD_ONLY_NEEDED_KEY )) {
            loadKnowValue(properties);
        }
        
        slogger.info( "Entries to add count: " + keysValues.size() );

        properties.putAll( keysValues );
        
        slogger.info( "New Resource bundle entries count: " + properties.size() );
        
        storeValues(properties);
        
        properties.clear();        
        keysValues.clear(); // free some memory ;)
    }

    @Override // I18nInterface
    public void setLocale( Locale locale )
    {
        this.i18n.setLocale( locale );
    }

    @Override // I18nInterface
    public Locale getCurrentLocale()
    {
        return this.i18n.getCurrentLocale();
    }
    
    @Override // I18nInterface
    public String getString( String key )
    {
        return this.i18n.getString( key );
    }

    private URL getResourceBundleBaseNameURL()
    {
        String fixName = this.i18n.getResourceBundleBaseName()
                .replace( '.', '/' )
                + ".properties";
        
        return getClass().getClassLoader().getResource( fixName );
    }

    /**
     * Can't be override to use how own InputStream
     * @return an InputStream, may be null
     * @throws IOException
     */
    public InputStream getResourceBundleInputStream() throws IOException
    {
        URL url = getResourceBundleBaseNameURL();
        
        slogger.info( "getResourceBundleInputStream() read from: " + url );
        
        return url.openStream();
    }

    /**
     * Can't be override to use how own OutputStream
     * @return an InputStream, may be null
     * @throws IOException
     */
    public OutputStream getResourceBundleOutputStream() throws IOException
    {
        URL url = getResourceBundleBaseNameURL();
        URI uri;
        
        try {
            uri = url.toURI();
        }
        catch( URISyntaxException e ) {
            throw new IOException("URISyntaxException for:" + url, e);
        }
        
        File file = new File( uri );
        
        slogger.info( "getResourceBundleOutputStream() write to: " + file );

        return new FileOutputStream( file );
    }    
}
