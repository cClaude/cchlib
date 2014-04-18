// $codepro.audit.disable numericLiterals
package cx.ath.choisnet.lang.introspection.method;

import com.googlecode.cchlib.util.EnumHelper;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import org.apache.log4j.Logger;

/**
 *
 * @author CC
 * @param <O> Object to inspect
 */
public class IntrospectionBuilder<O>
{
    /** Some logs */
    private static final Logger LOGGER = Logger.getLogger(IntrospectionBuilder.class);

    /** Getter Methods list */
    private final Map<String,Method> getterMethodsMap = new TreeMap<>();

    /** Setter Methods list */
    private final Map<String,Method> setterMethodsMap = new TreeMap<>();

    /**
     *
     * @param inpectClass Class of object to analyze
     * @param parameters  Parameters
     * @see IVIgnore
     *
     *  TIPS: Use EnumSet.of(Introspection.Attrib.ONLY_PUBLIC, Introspection.Attrib.NO_DEPRECATED) for parameter attribSet
     */
    public IntrospectionBuilder(
        final Class<O>                     inpectClass,
        final Set<IntrospectionParameters> parameters
        )
    {
        final EnumSet<IntrospectionParameters> safeParameters = getSafeParameters( parameters );

        buildGetterSetterList( inpectClass, safeParameters );
    }

    private void buildGetterSetterList(
        final Class<O>                         inpectClass,
        final EnumSet<IntrospectionParameters> safeParameters
        )
    {
        // Construit la liste des observateurs
        final Method[] methods = inpectClass.getMethods();

        for( final Method method : methods ) {
            addGetterOrSetter( safeParameters, method );
        }
    }

    private void addGetterOrSetter(
            final EnumSet<IntrospectionParameters> safeParameters,
            final Method method )
    {
        boolean getThis = true;

        if( safeParameters.contains( IntrospectionParameters.ONLY_PUBLIC ) ) {
            getThis = Modifier.isPublic( method.getModifiers() );
            }
        if( getThis ) {
            if( safeParameters.contains( IntrospectionParameters.NO_DEPRECATED ) ) {
                getThis = ! method.isAnnotationPresent( Deprecated.class );
                }
            }
        if( getThis ) {
            getThis = ! method.isAnnotationPresent( IVIgnore.class );
            }

        if( getThis ) {
            if( method.getParameterTypes().length == 0 ) {
                final String methodName = method.getName();

                //TODO: check if return something !

                if( methodName.equals( "getClass" ) ) {
                    // ignore privateSLog.trace( "Ignored Method: " + m );
                    }
                else if( methodName.startsWith( "is" ) ) {
                    addGetter( methodName.substring( 2 ), method );
                    }
                else if( methodName.startsWith( "get" ) ) {
                    addGetter( methodName.substring( 3 ), method );
                    }
                }
            else if( method.getParameterTypes().length == 1 ) {
                final String methodName = method.getName();

                if( methodName.startsWith( "is" ) ) {
                    // for some set method witch have name like isSomeThing
                    addSetter( methodName.substring( 2 ), method );
                } else if( methodName.startsWith( "set" ) ) {
                    addSetter( methodName.substring( 3 ), method );
                }
            }
        } // if( getThis )
        else if( LOGGER.isDebugEnabled() ) {
            LOGGER.debug( "* (out of scope) Ignore this Method: " + method );
        }
    }

    protected static EnumSet<IntrospectionParameters> getSafeParameters(
        final Set<IntrospectionParameters> parameters
        )
    {
        return EnumHelper.getSafeEnumSet( parameters, IntrospectionParameters.class );
    }

    private final void addGetter( final String beanName, final Method method )
    {
        addMethod( this.getterMethodsMap, beanName, method );
    }

    private void addSetter( final String beanName, final Method method )
    {
        addMethod( this.setterMethodsMap, beanName, method );
    }

    private void addMethod(
        final Map<String,Method>    methodsMap,
        final String                beanName,
        final Method                method
        )
    {
        Method previous = methodsMap.put( beanName, method );

        // Verify than method does not already exist in Map !
        if( previous != null ) {
             LOGGER.error( "*** " + beanName + " already in Map: " + previous.getName() + " - " + method.getName() );
            }
        else {
            // All ok !
        }
    }

    /**
     * @return the getterMethodsMap
     */
    public Map<String, Method> getGetterMethodsMap()
    {
        return getterMethodsMap;
    }

    /**
     * @return the setterMethodsMap
     */
    public Map<String, Method> getSetterMethodsMap()
    {
        return setterMethodsMap;
    }

}

