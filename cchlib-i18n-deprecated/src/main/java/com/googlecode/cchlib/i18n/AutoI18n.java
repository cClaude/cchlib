package com.googlecode.cchlib.i18n;

import java.awt.Window;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.EnumSet;
import java.util.MissingResourceException;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JWindow;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.i18n.logging.AutoI18nLog4JExceptionHandler;

/**
 * AutoI18n is design to assist internalization process.
 * AutoI18n use reflection to identify {@link Field} that
 * should be localized.
 * <p>
 * You can customize AutoI18n by many ways:
 * <br/>
 * - Using annotations {@link I18n}, {@link I18nIgnore},
 * {@link I18nString};
 * <br/>
 * - Using custom process by extending {@link AutoI18nBasicInterface},
 * {@link AutoI18nCustomInterface} or {@link  I18nInterface};
 * <br/>
 * - Defines {@link AutoI18n.Attribute attributes} on constructor.
 * </p>
 * <p>
 * <b>How to use:</b>
 * <pre>
 *     // on main object (JFrame)
 *     private AutoI18n autoI18n = {@code <<init_AutoI18n>>}
 *
 *     private void init()
 *     {
 *       ...
 *       // after all initialization ...
 *       // Localize current class
 *       autoI18n.performeI18n(this,this.getClass());
 *
 *       // but also you can Localize some extra fields
 *       autoI18n.performeI18n(this.myPanel,this.myPanel.getClass());
 *     }
 * </pre>
 * <b>How to initialize:</b>
 * <pre>
 *     AutoI18n autoI18n = new AutoI18n(
            new {@link I18nSimpleResourceBundle}( "{@code <<MessageBundleFullName>>}" ),
            new {@link AutoI18nLog4JExceptionHandler}(),
            EnumSet.of(
                AutoI18n.Attribute.DO_DEEP_SCAN
                )
            );
 * </pre>
 * <b>Debugging:</b>
 * <ul>
 *  <li>See {@link #DISABLE_PROPERTIES}</li>
 * </ul>
 * </p>
 *
 */
public class AutoI18n implements Serializable
{
    private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger( AutoI18n.class );
	
    private transient Object      objectToI18n;
    private transient Class<?>    objectToI18nClass;
    private transient String      objectToI18nClassNamePrefix;
    /** @serial */
    private I18nInterface i18n;
    /** @serial */
    private AutoI18nEventHandler eventHandler;
    /** @serial */
    private AutoI18nExceptionHandler exceptionHandler;
    /** @serial */
    private EnumSet<Attribute> config;
    private final static Class<?>[] ignoredClasses = {
        Object.class,
        JFrame.class,
        JDialog.class,
        JWindow.class,
        Window.class,
        JPanel.class,
        };
    /**
     * System properties : {@value #DISABLE_PROPERTIES}<br/>
     * If set with "true" disable
     * auto-internalization process.
     */
    public static final String DISABLE_PROPERTIES = "com.googlecode.cchlib.i18n.AutoI18n.disabled";

    //TODO: add properties to force locale
    //public static final String LOCALE_PROPERTIES = "com.googlecode.cchlib.i18n.AutoI18n.locale";

    /** @serial */
    private AutoI18nTypes defaultTypes;
    /** @serial */
    private AutoI18nTypes forceTypes;
    
    /**
     * How to select Fields:
     * <p>
     * By default, I18n process inspect all the fields
     * declared by the class or interface represented.
     * by 'objectToI18n' ({@link AutoI18n#performeI18n(Object, Class)}.
     * This includes public, protected, default (package)
     * access, and private fields, but excludes inherited
     * fields.
     * </p>
     */
    public enum Attribute
    {
        /**
         * Select only field objects reflecting all the
         * accessible public fields of the class or
         * interface represented by 'objectToI18n'
         * ({@link AutoI18n#performeI18n(Object, Class)}.
         */
        ONLY_PUBLIC,

        /**
         * Also get inspect Fields from super class.
         * <br/>
         * Recurse process into super classes since
         * super class is one of {@link Object},
         * {@link JFrame}, {@link JDialog},
         * {@link JWindow}, {@link Window}
         */
        DO_DEEP_SCAN,

        /**
         * Internal use, see {@link AutoI18n#DISABLE_PROPERTIES}<br/>
         * Disable internalization process.
         */
        DISABLE,
    }
    
    /**
     * Create an AutoI18n object using {@link I18nInterface},
     *
     * @param i18n I18nInterface to use
     * @param exceptionHandler  AutoI18nExceptionHandler
     *                          to use handle exceptions.
     * @param eventHandler AutoI18nEventHandler to use to
     *                     handle events. (could be null).
     * @param attributes Customize Auto18n (could be null to
     * use defaults).
     */
    public AutoI18n(
            I18nInterface               i18n,
            AutoI18nExceptionHandler    exceptionHandler,
            AutoI18nEventHandler        eventHandler,
            EnumSet<Attribute>          attributes
            )
    {
    	this(i18n, null, null, exceptionHandler, eventHandler, attributes);
    }

    /**
     * Create an AutoI18n object using {@link I18nInterface},
     *
     * @param i18n I18nInterface to use
     * @param autoI18nDefaultTypes AutoI18nTypes to use (could be null
     *        then use defaults implementation : {@link I18nSimpleResourceBundle}).
     *        This parameter is for default handle process.
     * @param autoI18nForceTypes AutoI18nTypes to use (could be null
     *        then use defaults implementation : {@link I18nSimpleResourceBundle}).
     *        This parameter is force ({@link I18nForce}) handle process.
     * @param exceptionHandler  AutoI18nExceptionHandler
     *                          to use handle exceptions.
     * @param eventHandler AutoI18nEventHandler to use to
     *                     handle events. (could be null).
     * @param attributes Customize Auto18n (could be null to
     * use defaults).
     */
    protected AutoI18n(
            I18nInterface               i18n,
            AutoI18nTypes               autoI18nDefaultTypes,
            AutoI18nTypes               autoI18nForceTypes,
            AutoI18nExceptionHandler    exceptionHandler,
            AutoI18nEventHandler        eventHandler,
            EnumSet<Attribute>          attributes
            )
    {
        if( autoI18nDefaultTypes == null ) {
            this.defaultTypes = new DefaultAutoI18nTypes();
            }
        else {
            this.defaultTypes = autoI18nDefaultTypes;
            }
        if( autoI18nForceTypes == null ) {
            this.forceTypes = new ForceAutoI18nTypes();
            }
        else {
            this.forceTypes = autoI18nForceTypes;
            }
        setI18n( i18n ); // could be override
        setAutoI18nExceptionHandler( exceptionHandler ); // could be override
        this.eventHandler = eventHandler;

        if( attributes == null ) {
            this.config = EnumSet.noneOf( Attribute.class );
            }
        else {
            this.config = EnumSet.copyOf( attributes );
            }

        if( Boolean.getBoolean( DISABLE_PROPERTIES )) {
            // Ignore ALL !
            this.config.add( Attribute.DISABLE );
            }
    }

    /**
     * Change {@link I18nInterface} to use
     *
     * @param i18n {@link I18nInterface} to use
     */
    public void setI18n( final I18nInterface i18n )
    {
        if( i18n == null ) {
            throw new NullPointerException();
            }
        this.i18n = i18n;
    }

    /**
     * Change {@link AutoI18nExceptionHandler} to use
     *
     * @param handler {@link AutoI18nExceptionHandler} to use
     */
    public void setAutoI18nExceptionHandler( 
        final AutoI18nExceptionHandler handler 
        )
    {
        if( handler == null ) {
            throw new NullPointerException();
            }
        this.exceptionHandler = handler;
    }

    /**
     * 'key' value for RessourceBundle is build using full Field name:
     * i.e. package.path.ClassName.aFieldName
     * <br/>
     * Then try to make I18N use these rules :
     * <pre>
     *   1. Looking for @I18n, @I18nIgnore annotations
     *      1.1  If annotation I18nIgnore is define, just ignore
     *           this field
     *      1.1. If a keyName is define, use it to find value in
     *           resource bundle.
     *      1.2. If a methodName is define, use it to set
     *           value: objectToI18n.methodName(value);
     *   2  Try to customize using default rules, getting Fields
     *      values.
     *      2.1. If field value is an instance of AutoI18nBasicInterface
     *           use it to set value.
     *      2.2. If field value is an instance of AutoI18nCustomInterface
     *           use it to set value
     *      2.3. If field value is an instance of javax.swing.JLabel
     *           use {@link javax.swing.JLabel#setText(String)}
     *      2.4. If field value is an instance of javax.swing.AbstractButton
     *           use {@link javax.swing.AbstractButton#setText(String)}
     *      2.5. If field value is an instance of javax.swing.JCheckBox
     *           use {@link javax.swing.JCheckBox#setText(String)}
     * </pre>
     * @param <T> Type of object to internationalize
     * @param objectToI18n Object to I18n
     * @param clazz        Class to use for I18n
     * @see AutoI18nBasicInterface
     * @see AutoI18nBasicInterface#getI18nString()
     * @see AutoI18nCustomInterface
     * @see AutoI18nCustomInterface#getI18n(I18nInterface)
     */
    synchronized public <T> void performeI18n(
            final T                     objectToI18n,
            final Class<? extends T>    clazz
            )
    {
        if( this.config.contains( Attribute.DISABLE ) ) {
            // Internalization is disabled.
            return;
            }

        if( this.exceptionHandler == null ) {
            setAutoI18nExceptionHandler( new AutoI18nLog4JExceptionHandler() );
            }

        setObjectToI18n(objectToI18n,clazz);
        Class<?> currentClass = objectToI18nClass;

        while( currentClass != null ) {
            boolean stop = false;

            for(Class<?> c:ignoredClasses) {
                if( currentClass.equals( c )) {
                    stop = true;
                    break;
                    }
                }

            if( stop ) {
                break;
                }

            //
            //
            //
            Field[] fields;

            if( config.contains( Attribute.ONLY_PUBLIC ) ) {
                fields = currentClass.getFields();
                }
            else {
                fields = currentClass.getDeclaredFields();
                }

            for( Field f : fields ) {
                if( f.isSynthetic() ) {
                    continue; // ignore member that was introduced by the compiler.
                    }
                Class<?> ftype = f.getType();

                if( ftype.isAnnotation() ) {
                    if( eventHandler!=null ) {
                        eventHandler.ignoredField( f, AutoI18nEventHandler.Cause.ANNOTATION);
                        }
                    continue; // ignore annotations
                    }
                if( ftype.isPrimitive() ) {
                    if( eventHandler!=null ) {
                        eventHandler.ignoredField( f, AutoI18nEventHandler.Cause.PRIMITIVE );
                        }
                    continue; // ignore primitive (numbers)
                    }
                if( ftype.isAssignableFrom( Number.class )) {
                    if( eventHandler!=null ) {
                        eventHandler.ignoredField( f, AutoI18nEventHandler.Cause.NUMBER );
                        }
                    continue; // ignore numbers
                    }
                //TODO: ignore some Fields types like EnumSet

                I18nIgnore ignoreIt = f.getAnnotation( I18nIgnore.class );

                if( ignoreIt != null ) {
                    if( eventHandler!=null ) {
                        eventHandler.ignoredField( f, AutoI18nEventHandler.Cause.ANNOTATION_I18nIgnore_DEFINE );
                        }
                    continue;
                    }

                setValue( f );
                }
            if( config.contains( Attribute.DO_DEEP_SCAN )) {
                currentClass = currentClass.getSuperclass();
                }
            else {
                break;
                }
            }
        //?? TODO ?? eventHandle.ignoreSuperClass(?)
    }

    private <T> void setObjectToI18n(
            T                   objectToI18n,
            Class<? extends T>  clazz
            )
    {
        this.objectToI18n = objectToI18n;
        this.objectToI18nClass = clazz;
        this.objectToI18nClassNamePrefix = this.objectToI18nClass.getName() + '.';
    }

    private void setValue( Field f )
    {
        // WARNING: 'key' must be always valid
        // before resolve 'value'
        String   key     = null; // 'key' is use by catch statement !
        Method[] methods = null; // 'methods' is use by catch statement !

        try {
            I18n        annoI18n        = f.getAnnotation( I18n.class );
            
            if( annoI18n != null ) {
                // Get methods
                methods = getCustomMethodsFromCurrentObject(f, annoI18n.method() );
                
                if( methods == null ) {
                    @SuppressWarnings("deprecation")
                    Method[] methodsSuppressWarnings = getCustomMethodsFromCurrentObject(f, annoI18n.methodSuffixName() );
                    methods = methodsSuppressWarnings;
                    }

                // Get key
                key = annoI18n.id();
                
                if( key.length() == 0 ) {
                    @SuppressWarnings("deprecation")
                    String keySuppressWarnings = annoI18n.keyName();
                    key = keySuppressWarnings;
                    }
                if( key.length() == 0 ) {
                    key = getKey( f );
                    }

                if( methods == null ) {
                    setValueFromKey( f, key );
                    }
                else {
                    setValueFromAnnotation( f, key, methods );
                    }
                }
            else if( f.getType().isArray() ) {
                if( f.getAnnotation( I18nString.class ) != null ) {
                    Class<?> ac = f.getType().getComponentType();

                    if( ac.equals( String.class ) ) {
                        f.setAccessible( true );
                        Object o      = f.get( objectToI18n );
                        String prefix = getKey( f ) + '.';
                        int    len    = Array.getLength( o );

                        for( int i = 0; i < len; i++ ) {
                            key = prefix + i;
                            Array.set( o, i, this.i18n.getString( key ) );
                        }
                    }
                }
            }
            else if( f.getType().isAssignableFrom( AutoI18nCustomInterface.class ) ) {
                Object o = f.get( objectToI18n );

                setAutoI18nCustomInterface( AutoI18nCustomInterface.class.cast( o ) );
            }
            else {
                key = getKey( f );
                setValueFromKey( f, key );
            }
        }
        catch( MissingResourceException e ) {
            if( methods == null ) {
                this.exceptionHandler.handleMissingResourceException( e, f, key );
                }
            else {
                this.exceptionHandler.handleMissingResourceException( e, f, key, methods );
                }
            }
        catch( IllegalArgumentException e ) {
            this.exceptionHandler.handleIllegalArgumentException( e );
            }
        catch( IllegalAccessException e ) {
            this.exceptionHandler.handleIllegalAccessException( e );
            }
        }

    // Warning !
    // Warning !
    // Warning !
    /**
     * This method is override by ???
     * <br/>
     * Should be the only entry point to get String from resource bundle
     */
    protected void setAutoI18nCustomInterface( AutoI18nCustomInterface autoI18n )
    {
        autoI18n.setI18n( this.i18n );
    }

    // Warning !
    // Warning !
    // Warning !
    /**
     * This method is use by ???
     * @return key name for this field
     */
    protected String getKey( final Field f )
    {
        return this.objectToI18nClassNamePrefix + f.getName();
    }

    private void setValueFromKey(
            final Field   f,
            final String  k
            )
    throws  IllegalArgumentException,
            IllegalAccessException
    {
        if( String.class.isAssignableFrom( f.getType() ) ) {
            if( f.getAnnotation( I18nString.class ) != null ) {
                f.setAccessible( true );
                f.set( this.objectToI18n, i18n.getString( k ) );

                if( eventHandler!=null ) {
                    eventHandler.localizedField( f );
                    }
                }
            else {
                if( eventHandler!=null ) {
                    eventHandler.ignoredField( f, AutoI18nEventHandler.Cause.NOT_A_I18nString );
                    }
                }
            return;//done
            }
        else { // Not a String
            if( f.getAnnotation( I18nString.class ) != null ) {
                // But annotation
            	logger.warn( "Annotation: @" + I18nString.class + " found on field (Not a String)" );
//                if( eventHandler!=null ) {
//                    eventHandler.warnOnField( f, AutoI18nEventHandler.Warning.ANNOTATION_I18nString_BUT_NOT_A_String );
//                }
                }
            }
        final Class<?> fclass = f.getType();

        if( AutoI18nBasicInterface.class.isAssignableFrom( fclass ) ) {
            f.setAccessible( true );
            Object o = f.get( objectToI18n );

            AutoI18nBasicInterface.class.cast( o ).setI18nString(
                    i18n.getString( k )
                    );
            if( eventHandler!=null ) {
                eventHandler.localizedField( f );
                }
            return; //done;
            }

        Key key = new Key( k );

        try {
        	AutoI18nTypes types;
        	
			if( f.getAnnotation( I18nForce.class ) != null ) {
        		types = this.forceTypes;
        		}
        	else {
        		types = this.defaultTypes;
        		}
            for(AutoI18nTypes.Type t:types) {
                if( t.getType().isAssignableFrom( fclass ) ) {
                    f.setAccessible( true );
                    t.setText(
                            f.get( objectToI18n ),
                            key
                            );

                    if( eventHandler!=null ) {
                        eventHandler.localizedField( f );
                        }
                    return;//done
                    }
                }
            logger.warn( "Type (" + fclass + ") not handle for field: " + f );
            }
        catch( MissingResourceException e ) {
            this.exceptionHandler.handleMissingResourceException( e, f, key );
            }

        if( eventHandler!=null ) {
            eventHandler.ignoredField( f, AutoI18nEventHandler.Cause.NOT_HANDLED );
            }
    }

    /**
     * Find get/set method to retrieve or update I18n value
     * 
     * @param f_notuse
     * @param suffixName
     * @return null or 2 methods (m[0]=set, m[1]=get]
     */
    private Method[] getCustomMethodsFromCurrentObject(
        final Field     f_notuse, 
        final String    suffixName 
        )
    //private Method[] getMethods( final Field f, final I18n i18n )
    {
        //final String suffixName = i18n.methodSuffixName();

        if( suffixName.length() > 0 ) {
            Method[] methods = new Method[2];

            try {
                methods[0] = this.objectToI18nClass.getMethod( "set"+ suffixName, String.class );
                }
            catch( SecurityException e ) {
                this.exceptionHandler.handleSecurityException(e);
                }
            catch( NoSuchMethodException e ) {
                this.exceptionHandler.handleNoSuchMethodException(e);
                }
            try {
                methods[1] = this.objectToI18nClass.getMethod( "get"+ suffixName );

                checkIfReturnTypeIsString( methods[ 1 ] );
                }
            catch( SecurityException e ) {
                this.exceptionHandler.handleSecurityException(e);
                }
            catch( NoSuchMethodException e ) {
                this.exceptionHandler.handleNoSuchMethodException(e);
                }

            return methods;
            }
        return null;
    }
    
    private static void checkIfReturnTypeIsString( final Method m )
        throws NoSuchMethodException
    {
        if( ! m.getReturnType().equals( String.class ) ) {
            throw new NoSuchMethodException( 
                "Method " + m + " must return a String"
                );
            }
    }

    private void setValueFromAnnotation( Field f, String key, Method[] methods )
        throws MissingResourceException
    {
        String keyValue = this.i18n.getString( key );

        try {
            methods[0].invoke( this.objectToI18n, keyValue );

            if( eventHandler!=null ) {
                eventHandler.localizedField( f );
                }
            }
        catch( IllegalArgumentException e ) {
            this.exceptionHandler.handleIllegalArgumentException(e);
            }
        catch( IllegalAccessException e ) {
            this.exceptionHandler.handleIllegalAccessException(e);
            }
        catch( InvocationTargetException e ) {
            this.exceptionHandler.handleInvocationTargetException(e);
            }
     }

    /**
     * @return the objectToI18n
     */
    protected final Object getObjectToI18n()
    {
        return objectToI18n;
    }

    /**
     * @return the objectToI18nClass
     */
    protected Class<?> getObjectToI18nClass()
    {
        return objectToI18nClass;
    }

    /**
     * @return the AutoI18nTypes
     */
    protected AutoI18nTypes getAutoI18nDefaultTypes()
    {
        return defaultTypes;
    }

    /**
     * @return the AutoI18nTypes
     */
    protected AutoI18nTypes getAutoI18nForceTypes()
    {
        return forceTypes;
    }

    /**
     * Private class use to identify current field
     * and to resolve value.
     */
    public class Key
    {
        private String key;
        public Key( String key )
        {
            this.key = key;
        }
        public String getKey()
        {
            return key;
        }
        public String getKey(int index)
        {
            return key + '.' + index;
        }
        public String getValue()
            throws MissingResourceException
        {
            return i18n.getString( key );
        }
        public String getValue(int index)
            throws MissingResourceException
        {
            return i18n.getString( getKey(index) );
        }
    }
}
