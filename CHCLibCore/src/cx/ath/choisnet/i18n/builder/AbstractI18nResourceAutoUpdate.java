/**
 * 
 */
package cx.ath.choisnet.i18n.builder;

import java.io.Closeable;
import java.io.IOException;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import org.apache.log4j.Logger;
import cx.ath.choisnet.i18n.AutoI18n;
import cx.ath.choisnet.i18n.AutoI18nBasicInterface;
import cx.ath.choisnet.i18n.AutoI18nExceptionHandler;

/***
 * TODO: Doc!
 * 
 * @author Claude CHOISNET
 */
public abstract class AbstractI18nResourceAutoUpdate 
    extends AutoI18n
        implements Closeable
{
    public enum Attrib {
        ADD_ONLY_NEEDED_KEY
    }
    private static Logger slogger = Logger.getLogger(AbstractI18nResourceAutoUpdate.class);
    private final Map<String,String> keysValues = new HashMap<String,String>();
    private final EnumSet<Attrib>    attribs;
    private I18nAutoUpdateInterface  i18nAutoUpdateInterface;
        
    /**
     * @param i18nAutoUpdateInterface
     * @param handler 
     * @param attributes 
     */
    public AbstractI18nResourceAutoUpdate( 
            I18nAutoUpdateInterface     i18nAutoUpdateInterface,
            AutoI18nExceptionHandler    handler,
            EnumSet<Attrib>             attributes
            )
    {
        super(i18nAutoUpdateInterface,handler);

        if(attributes == null) {
            this.attribs = EnumSet.noneOf( Attrib.class );
        }
        else {
            this.attribs = EnumSet.copyOf( attributes );
        }
    }
    
    /**
     * Set I18nAutoUpdateInterface object.
     * 
     * @param i18nAutoUpdateInterface
     */
    final //TODO: remove this
    public void setI18nAutoUpdateInterface( final I18nAutoUpdateInterface i18nAutoUpdateInterface )
    {
        this.i18nAutoUpdateInterface = i18nAutoUpdateInterface;
    
        super.setI18n( i18nAutoUpdateInterface );
    }
    
    /**
     * @return internal I18nAutoUpdateInterface
     */
    final //TODO: remove this
    public I18nAutoUpdateInterface getI18nAutoUpdateInterface()
    {
        return this.i18nAutoUpdateInterface;
    }
    
    @Override // AutoI18n
    final //TODO: remove this
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
    
//    @Override // AutoI18n
//    protected void setAutoI18nCustomInterface( AutoI18nCustomInterface autoI18n )
//    {
//        try {
//            autoI18n.setI18n( this.i18n );
//            
//            return;
//        }
//        catch( MissingResourceException logIt ) {
//            Map<String, String> map = autoI18n.getI18n( this.i18n );
//            this.keysValues.putAll( map );
//
//            slogger.warn( "Found unknown key :" + toString( map.keySet(), 10) );
//        }
//    }
//    private String toString( Collection<?> collection, int maxLen )
//    {
//        StringBuilder builder = new StringBuilder();
//        builder.append( "[" );
//        int i = 0;
//        for( Iterator<?> iterator = collection.iterator(); iterator.hasNext()
//                && i < maxLen; i++ ) {
//            if( i > 0 ) builder.append( ", " );
//            builder.append( iterator.next() );
//        }
//        builder.append( "]" );
//        return builder.toString();
//    }

    /**
     * @throws IOException 
     */
    public abstract void loadKnowValue() throws IOException;

    /**
     * 
     * @param mapOfUnknownEntries
     */
    public abstract void setUnknownMap(Map<String,String> mapOfUnknownEntries);

    /**
     * 
     * @throws IOException
     */
    public abstract void saveValues() throws IOException;
    
    @Override // Closeable
    public void close() throws IOException
    {
        if( !this.attribs.contains( Attrib.ADD_ONLY_NEEDED_KEY )) {
            loadKnowValue();
        }
        
        slogger.info( "Entries to add count: " + keysValues.size() );

        setUnknownMap(Collections.unmodifiableMap( keysValues ));
        saveValues();
        
        keysValues.clear(); // free some memory ;)
    }

    /**
     * @param locale new {@link Locale} to use
     */
    final //TODO: remove this
    public void setLocale( Locale locale )
    {
        this.i18nAutoUpdateInterface.setLocale( locale );
    }

    /**
     * @return current {@link Locale}
     */
    final //TODO: remove this
    public Locale getCurrentLocale()
    {
        return this.i18nAutoUpdateInterface.getCurrentLocale();
    }   
}
