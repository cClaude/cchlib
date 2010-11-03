/**
 * 
 */
package cx.ath.choisnet.i18n.builder;

import java.io.Closeable;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Locale;
import java.util.MissingResourceException;
import org.apache.log4j.Logger;
import cx.ath.choisnet.i18n.AutoI18n;
import cx.ath.choisnet.i18n.AutoI18nBasicInterface;
import cx.ath.choisnet.i18n.AutoI18nEventHandler;
import cx.ath.choisnet.i18n.AutoI18nExceptionHandler;
import cx.ath.choisnet.i18n.AutoI18nTypes;
import cx.ath.choisnet.i18n.I18nString;

/***
 * TODO: Doc!
 * 
 * @author Claude CHOISNET
 */
public abstract class AbstractI18nResourceAutoUpdate 
    extends AutoI18n
        implements Closeable
{
    private static final long serialVersionUID = 1L;

    /**
     * TODO: Doc !!
     * 
     * @author Claude CHOISNET
     */
    public enum Attribute {
        /**
         * TODO: Doc!
         */
        ADD_ONLY_NEEDED_KEY
    }
    private transient static Logger slogger = Logger.getLogger(AbstractI18nResourceAutoUpdate.class);
    private HashMap<String,String> keysValues = new HashMap<String,String>();
    private EnumSet<Attribute> attribs;
    private I18nAutoUpdateInterface i18nAutoUpdateInterface;

    /**
     * TODO: Doc!
     * 
     * @param i18nAutoUpdateInterface
     * @param autoI18nTypes 
     * @param exceptionHandler 
     * @param eventHandler 
     * @param autoI18nAttributes 
     * @param bundleAttributes 
     */
    public AbstractI18nResourceAutoUpdate( 
            I18nAutoUpdateInterface                             i18nAutoUpdateInterface,
            AutoI18nTypes                                       autoI18nTypes,
            AutoI18nExceptionHandler                            exceptionHandler,
            AutoI18nEventHandler                                eventHandler,
            EnumSet<AutoI18n.Attribute>                         autoI18nAttributes,
            EnumSet<AbstractI18nResourceAutoUpdate.Attribute>   bundleAttributes
            )
    {
        super(  i18nAutoUpdateInterface,
                autoI18nTypes,
                exceptionHandler, // temp value, need to make an new handler
                eventHandler,
                autoI18nAttributes
                );

        //Overwrite exception handler
        super.setAutoI18nExceptionHandler(
        new AutoI18nUpdateEvent(exceptionHandler)
        {
            private static final long serialVersionUID = 1L;
            @Override
            public void handleMissingResourceException(
                    MissingResourceException    mse, 
                    Field                       f, 
                    String                      key 
                    )
            {
                getParentHandler().handleMissingResourceException( mse, f, key );

                // I18nString annotation
                if( f.getAnnotation( I18nString.class ) != null ) {
                    f.setAccessible( true );

                    try {
                        Object o = f.get( getObjectToI18n() );
                        
                        if( o instanceof String ) {
                            needProperty(key, String.class.cast( o ) );
                            return; // done
                        }
                        else if( o instanceof String[] ) {
                            String[] values = String[].class.cast( o );
                            String   prefix = getKey( f ) + '.';
                            
                            for(int i=0;i<values.length;i++) {
                                needProperty(prefix+i,values[i]);
                            }

                            return; // done
                        }
                    }
                    catch( IllegalArgumentException e1 ) {
                        // Should NOT occur !
                        e1.printStackTrace();
                    }
                    catch( IllegalAccessException e1 ) {
                        // Should NOT occur !
                        e1.printStackTrace();
                    }
                }

                // AutoI18nBasicInterface interface
                Class<?> fclass = f.getType();

                if( fclass.isAssignableFrom( AutoI18nBasicInterface.class ) ) {
                    f.setAccessible( true );

                    try {
                        Object o = f.get( getObjectToI18n() );
                        String v = AutoI18nBasicInterface.class.cast( o ).getI18nString();

                        needProperty(key, v );
                        return; // done
                    }
                    catch( IllegalArgumentException e ) {
                        // Should NOT occur !
                        e.printStackTrace();
                    }
                    catch( IllegalAccessException e ) {
                        // Should NOT occur !
                        e.printStackTrace();
                    }
                }
                needProperty( getKey( f ), "<<NOT HANDLE (c1)>>");
            }
            @Override
            public void handleMissingResourceException(
                    MissingResourceException    mse, 
                    Field                       f, 
                    Key                         key 
                    )
            {
                getParentHandler().handleMissingResourceException( mse, f, key );

                Class<?> fclass = f.getType();

                f.setAccessible( true );

                try {
                    for(AutoI18nTypes.Type t:getAutoI18nTypes()) {
                        if( t.getType().isAssignableFrom( fclass ) ) {
                            String[] values = t.getText( f.get( getObjectToI18n() ) );

                            if( values.length == 1 ) {
                                needProperty(key.getKey(),values[0]);
                            }
                            else if( values.length > 1 ) {
                                String   prefix = getKey( f ) + '.';
                                
                                for(int i=0;i<values.length;i++) {
                                    needProperty(prefix+i,values[i]);
                                }
                            }
                            else {
                                needProperty(key.getKey(),"<<EMPTY-ARRAY-OF-STRING>>");
                            }
                            return; // done
                        }
                    }
                }
                catch( IllegalArgumentException e ) {
                    // Should NOT occur !
                    e.printStackTrace();
                }
                catch( IllegalAccessException e ) {
                    // Should NOT occur !
                    e.printStackTrace();
                }

                needProperty( getKey( f ), "<<NOT HANDLE (c2)>>");
            }
            @Override
            public void handleMissingResourceException(
                    MissingResourceException mse, 
                    Field                    f,
                    String                   key,
                    Method[]                 methods 
                    )
            {
                getParentHandler().handleMissingResourceException( mse, f, key );

                try {
                    String v = (String)methods[1].invoke( getObjectToI18n() );
                    
                    if( v == null ) {
                        v = String.format("<<get 'null' from %s>>",methods[1]);
                    }
                    needProperty(key,v);
                }
                catch( IllegalArgumentException e ) {
                    // Should NOT occur !
                    e.printStackTrace();
                }
                catch( IllegalAccessException e ) {
                    // TODO ??
                    e.printStackTrace();
                }
                catch( InvocationTargetException e ) {
                    // TODO ??
                    e.printStackTrace();
                }
            }
        });

        if(bundleAttributes == null) {
            this.attribs = EnumSet.noneOf( Attribute.class );
        }
        else {
            this.attribs = EnumSet.copyOf( bundleAttributes );
        }
    }
    
    /**
     * Set I18nAutoUpdateInterface object.
     * 
     * @param i18nAutoUpdateInterface
     */
    public void setI18nAutoUpdateInterface( final I18nAutoUpdateInterface i18nAutoUpdateInterface )
    {
        this.i18nAutoUpdateInterface = i18nAutoUpdateInterface;
    
        super.setI18n( i18nAutoUpdateInterface );
    }
    
    /**
     * @return internal I18nAutoUpdateInterface
     */
    public I18nAutoUpdateInterface getI18nAutoUpdateInterface()
    {
        return this.i18nAutoUpdateInterface;
    }
    
    protected void needProperty( String key, String value )
    {
        System.err.printf( "needProperty( \"%s\", \"%s\" )\n",key,value);
        
        if( key == null ) {
            throw new NullPointerException("'key' is null");
        }
        if( value == null ) {
            throw new NullPointerException("'value' is null");
        }
        this.keysValues.put( key, value );
    }

    /**
     * @throws IOException 
     */
    protected abstract void loadKnowValue() throws IOException;

    /**
     * Must call {@link #getProperties()}
     * 
     * @throws IOException
     */
    protected abstract void saveValues() throws IOException;
    
    @Override // Closeable
    public void close() throws IOException
    {
        if( !this.attribs.contains( Attribute.ADD_ONLY_NEEDED_KEY )) {
            loadKnowValue();
        }

        slogger.info( "Entries to add count: " + keysValues.size() );

        saveValues();

        keysValues.clear(); // free some memory ;)
    }

    /**
     * Set current Locale
     * @param locale {@link Locale} to use
     */
    public void setLocale( Locale locale )
    {
        this.i18nAutoUpdateInterface.setLocale( locale );
    }

    /**
     * Returns current Locale
     * @return current {@link Locale}
     */
    public Locale getLocale()
    {
        return this.i18nAutoUpdateInterface.getLocale();
    }

    /**
     * @return the keysValues
     */
    protected HashMap<String, String> getProperties()
    {
        return keysValues;
    }   
}
///**
//* 
//* @param mapOfUnknownEntries
//*/
//public abstract void setUnknownMap(Map<String,String> mapOfUnknownEntries);


//@Override // AutoI18n
//final //TO DO: remove this
//protected String getKeyValue(AbstractFieldValue abstractFieldValue)
//{
//  try {
//      //slogger.info( "@key :" + abstractFieldValue.getKey() );
//      String result = super.getKeyValue( abstractFieldValue );
//  
//      return result;
//  }
//  catch( MissingResourceException logIt ) {
//      slogger.warn( "Found unkwnon key :" + abstractFieldValue.getKey() + " for Field:" + abstractFieldValue.getField().getName() );
//  }
//  
//  Object fieldValue = null;
//  
//  try {
//      //fieldValue = abstractFieldValue.getField().get( abstractFieldValue.getObjectToI18n() );
//      fieldValue = abstractFieldValue.getField().get( getObjectToI18n() );
//  }
//  catch( IllegalArgumentException e ) {
//      // TO DO Auto-generated catch block
//      e.printStackTrace();
//  }
//  catch( IllegalAccessException e ) {
//      // TO DO Auto-generated catch block
//      e.printStackTrace();
//  }
//  
//  boolean done = false;
//  
//  if( fieldValue instanceof AutoI18nBasicInterface) {
//      String text = AutoI18nBasicInterface.class.cast( fieldValue ).getI18nString();
//      this.keysValues.put( abstractFieldValue.getKey(), text );
//      done = true;
//  }
////  else if( fieldValue instanceof AutoI18nCustomInterface ) {
////      Map<String, String> map = AutoI18nCustomInterface.class.cast( keyOrValue.getValue() ).getI18n( this.i18n );
////      this.keysValues.putAll( map );
////      done = true;
////  }
//  else if( fieldValue instanceof JLabel ) {
//      String text = JLabel.class.cast( fieldValue ).getText();
//      this.keysValues.put( abstractFieldValue.getKey(), text );
//      done = true;
//  }
//  else if( fieldValue instanceof JMenuItem ) {
//      String text = JMenuItem.class.cast( fieldValue ).getText();
//      this.keysValues.put( abstractFieldValue.getKey(), text );
//      done = true;
//  }
//  else if( fieldValue instanceof JCheckBox ) {
//      String text = JCheckBox.class.cast( fieldValue ).getText();
//      this.keysValues.put( abstractFieldValue.getKey(), text );
//      done = true;
//  }
//
//  if( !done ) {
//      this.keysValues.put( abstractFieldValue.getKey(), abstractFieldValue.getField().getName() );
//  }
//  
//  return abstractFieldValue.getField().getName();
//}

//@Override // AutoI18n
//protected void setAutoI18nCustomInterface( AutoI18nCustomInterface autoI18n )
//{
//  try {
//      autoI18n.setI18n( this.i18n );
//      
//      return;
//  }
//  catch( MissingResourceException logIt ) {
//      Map<String, String> map = autoI18n.getI18n( this.i18n );
//      this.keysValues.putAll( map );
//
//      slogger.warn( "Found unknown key :" + toString( map.keySet(), 10) );
//  }
//}
//private String toString( Collection<?> collection, int maxLen )
//{
//  StringBuilder builder = new StringBuilder();
//  builder.append( "[" );
//  int i = 0;
//  for( Iterator<?> iterator = collection.iterator(); iterator.hasNext()
//          && i < maxLen; i++ ) {
//      if( i > 0 ) builder.append( ", " );
//      builder.append( iterator.next() );
//  }
//  builder.append( "]" );
//  return builder.toString();
//}
