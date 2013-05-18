package com.googlecode.cchlib.i18n.builder;

import java.io.Closeable;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Locale;
import java.util.MissingResourceException;
import javax.swing.JComponent;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.i18n.AutoI18nBasicInterface;
import com.googlecode.cchlib.i18n.AutoI18nEventHandler;
import com.googlecode.cchlib.i18n.AutoI18nExceptionHandler;
import com.googlecode.cchlib.i18n.AutoI18nTypes;
import com.googlecode.cchlib.i18n.LateKey;
import com.googlecode.cchlib.i18n.annotation.I18nForce;
import com.googlecode.cchlib.i18n.annotation.I18nString;
import com.googlecode.cchlib.i18n.AutoI18n;
import com.googlecode.cchlib.i18n.hidden.AutoI18nImpl;
import com.googlecode.cchlib.i18n.logging.LogFieldFormat;
import com.googlecode.cchlib.i18n.missing.MissingForToolTipText;
import com.googlecode.cchlib.i18n.missing.MissingInfo;
import com.googlecode.cchlib.i18n.missing.MissingLateKey;
import com.googlecode.cchlib.i18n.missing.MissingMethodsResolution;
import com.googlecode.cchlib.i18n.missing.MissingSimpleKey;

/**
 * Abstract class of {@link AutoI18nImpl} that allow to build initial resource
 * file for localization.
 */
public abstract class AbstractI18nResourceAutoUpdate
    extends AutoI18nImpl
        implements Closeable
{
    private static final long serialVersionUID = 1L;

    /**
     * Attributes for {@link AbstractI18nResourceAutoUpdate}
     */
    public enum Attribute {
        /**
         * Add only key that are not already define in resource
         */
        ADD_ONLY_NEEDED_KEY
        };

    private transient static Logger logger = Logger.getLogger(AbstractI18nResourceAutoUpdate.class);
    /** @serial */
    private HashMap<String,String> keysValues = new HashMap<String,String>();
    /** @serial */
    private EnumSet<Attribute> attribs;
    /** @serial */
    private I18nAutoUpdateInterface i18nAutoUpdateInterface;

    /**
     * Create an AbstractI18nResourceAutoUpdate
     *
     * @param i18nAutoUpdateInterface
     * @param exceptionHandler
     * @param eventHandler
     * @param autoI18nAttributes
     * @param bundleAttributes
     */
    public AbstractI18nResourceAutoUpdate(
            I18nAutoUpdateInterface                             i18nAutoUpdateInterface,
            AutoI18nExceptionHandler                            exceptionHandler,
            AutoI18nEventHandler                                eventHandler,
            EnumSet<AutoI18n.Attribute>                         autoI18nAttributes,
            EnumSet<AbstractI18nResourceAutoUpdate.Attribute>   bundleAttributes
            )
    {
        this(
            i18nAutoUpdateInterface,
            null,
            null,
            exceptionHandler,
            eventHandler,
            autoI18nAttributes,
            bundleAttributes
            );
    }

    /**
     * Create an AbstractI18nResourceAutoUpdate
     *
     * @param i18nAutoUpdateInterface
     * @param autoI18nDefaultTypes
     * @param autoI18nForceTypes
     * @param exceptionHandler
     * @param eventHandler
     * @param autoI18nAttributes
     * @param bundleAttributes
     */
    public AbstractI18nResourceAutoUpdate(
            final I18nAutoUpdateInterface         i18nAutoUpdateInterface,
            final AutoI18nTypes                   autoI18nDefaultTypes,
            final AutoI18nTypes                   autoI18nForceTypes,
            final AutoI18nExceptionHandler        exceptionHandler,
            final AutoI18nEventHandler            eventHandler,
            final EnumSet<AutoI18n.Attribute>     autoI18nAttributes,
            final EnumSet<Attribute>              bundleAttributes
            )
    {
        super(  i18nAutoUpdateInterface,
                autoI18nDefaultTypes,
                autoI18nForceTypes,
                exceptionHandler, // temp value, need to make an new handler
                eventHandler,
                autoI18nAttributes
                );

        //Overwrite exception handler
        super.setAutoI18nExceptionHandler(
        new AutoI18nUpdateEvent( exceptionHandler )
        {
            private static final long serialVersionUID = 1L;
//            @Override
//            public void handleMissingResourceException(
            private void handleMissingResourceException_SimpleKey(
                    MissingResourceException    mse,
                    Field                       f,
                    String                      key
                    )
            {
                //getParentHandler().handleMissingResourceException( mse, f, key );

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
                    catch( IllegalArgumentException shouldNotOccur ) {
                        throw new RuntimeException( shouldNotOccur );
                        }
                    catch( IllegalAccessException shouldNotOccur ) {
                        throw new RuntimeException( shouldNotOccur );
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
                    catch( IllegalArgumentException shouldNotOccur ) {
                        throw new RuntimeException( shouldNotOccur );
                        }
                    catch( IllegalAccessException shouldNotOccur ) {
                        throw new RuntimeException( shouldNotOccur );
                        }
                    }
                final String msg = "<<NOT HANDLE (c1)>>";

                logger.fatal( msg, new Exception("DEBUG") );

                needProperty( getKey( f ), msg);
             }
//            @Override
//            public void handleMissingResourceException(
            private void handleMissingResourceException_LateKey(
                    MissingResourceException    mse,
                    Field                       f,
                    LateKey                     key
                    )
            {
                //getParentHandler().handleMissingResourceException( mse, f, key );

                Class<?> fclass = f.getType();

                f.setAccessible( true );

                try {
                    AutoI18nTypes types;

                    if( f.getAnnotation( I18nForce.class ) != null ) {
                        types = getAutoI18nForceTypes();
                        }
                    else {
                        types = getAutoI18nDefaultTypes();
                        }

                    for( AutoI18nTypes.Type t : types ) {
                        if( t.getType().isAssignableFrom( fclass ) ) {
                            Object fValue = f.get( getObjectToI18n() );

                            if( fValue == null ) {
                                final String msg = "Value of " + LogFieldFormat.toString( f ) + " is NULL ";

                                logger.warn( msg );
                                String v = String.format("<<%s>>", msg );

                                needProperty(key.getKey(),v);
                                }
                            else {
                                String[] values = t.getText( fValue );

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
                                }
                            return; // done
                            }
                        }
                    }
                catch( IllegalArgumentException shouldNotOccur ) {
                        throw new RuntimeException( shouldNotOccur );
                    }
                catch( IllegalAccessException shouldNotOccur ) {
                        throw new RuntimeException( shouldNotOccur );
                    }

                needProperty( getKey( f ), "<<NOT HANDLE (c2)>>");
            }
            //@Override
            //public void handleMissingResourceException(
            private void handleMissingResourceException_Methods(
                    MissingResourceException mse,
                    Field                    f,
                    String                   key,
                    Method[]                 methods
                    )
            {
                //*getParentHandler().handleMissingResourceException( mse, f, key );

                try {
                    String v = (String)methods[1].invoke( getObjectToI18n() );

                    if( v == null ) {
                        v = String.format("<<get 'null' from %s>>",methods[1]);
                        }
                    needProperty(key,v);
                    }
                catch( IllegalArgumentException e ) {
                    // Should NOT occur !
                    throw new RuntimeException( "IllegalArgumentException while invoke: " + methods[1], e );
                    }
                catch( IllegalAccessException e ) {
                    // TODO ?? better handle this exception
                    throw new RuntimeException( "IllegalAccessException while invoke: " + methods[1], e );
                    }
                catch( InvocationTargetException e ) {
                    // TODO ?? better handle this exception
                    throw new RuntimeException( "InvocationTargetException while invoke: " + methods[1], e );
                    }
            }
            private void handleMissingResourceException_MissingMethodsResolution(
                    MissingResourceException mse,
                    Field field,
                    MissingMethodsResolution mmr )
            {
                handleMissingResourceException_Methods( mse, field, mmr.getKey(), new Method[]{ mmr.getGetter(), mmr.getSetter() } );
            }
            @Override
            public void handleMissingResourceException(
                final MissingResourceException mse,
                final Field                    field,
                final MissingInfo              missingInfo
                )
            {
                getParentHandler().handleMissingResourceException( mse, field, missingInfo );

                switch( missingInfo.getType() ) {
                    case SIMPLE_KEY :
                        handleMissingResourceException_SimpleKey( mse, field, MissingSimpleKey.class.cast( missingInfo ).getKey() );
                        break;
                    case LATE_KEY :
                        handleMissingResourceException_LateKey( mse, field, MissingLateKey.class.cast( missingInfo ).getAutoI18nKey() );
                        break;
                    case METHODS_RESOLUTION :
                        handleMissingResourceException_MissingMethodsResolution( mse, field, MissingMethodsResolution.class.cast( missingInfo ) );
                        break;
                    case JCOMPONENT_TOOLTIPTEXT:
                        handleMissingResourceException_ToolTipText( mse, field, MissingForToolTipText.class.cast( missingInfo ) );
                        break;
                    }
             }
            private void handleMissingResourceException_ToolTipText(
                final MissingResourceException mse,
                final Field                    field,
                final MissingForToolTipText    missingInfo
                )
            {
                Class<?> fclass = field.getType();

                if( JComponent.class.isAssignableFrom( fclass ) ) {
                    boolean accessible = field.isAccessible();

                    if( ! accessible ) {
                        field.setAccessible( true );
                        }

                    try {
                        Object obj = field.get( getObjectToI18n() );
                        String v;

                        if( obj == null ) {
                            String msg = LogFieldFormat.toString( field ) + " value is null";
                            logger.fatal( msg );

                            v = String.format("<<%s>>", msg );
                            }
                        else {
                            JComponent c          = JComponent.class.cast( obj );
                            String    toolTipText = c.getToolTipText();

                            if( toolTipText == null ) {
                                String msg = LogFieldFormat.toString( field ) + ".getToolTipText() value is null";
                                logger.fatal( msg );

                                v = String.format("<<%s>>", msg );
                                }
                            else {
                                v = toolTipText;
                                }
                            }

                        needProperty( missingInfo.getKey(), v );
                        }
                    catch( IllegalArgumentException e ) {
                        // TODO ?? better handle this exception
                        throw new RuntimeException( e );
                        }
                    catch( IllegalAccessException e ) {
                        // TODO ?? better handle this exception
                        throw new RuntimeException( e );
                        }
                    }
                else {
                    final String msg = "ToolTipText are handle only on " + JComponent.class + " not on " + fclass;

                    logger.fatal( msg );

                    String v = String.format("<<%s>>", msg );

                    needProperty( getKey( field ), v );
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

        logger.info( "Entries to add count: " + keysValues.size() );

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

