package cx.ath.choisnet.lang.introspection.method;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.util.EnumHelper;

/**
 *
 * @param <O> Object to inspect
 */
public class IntrospectionBuilder<O>
{
    private static final Logger LOGGER = Logger.getLogger(IntrospectionBuilder.class);

    /** Getter Methods list */
    private final Map<String,Method> getterMethodsMap = new TreeMap<>();

    /** Setter Methods list */
    private final Map<String,Method> setterMethodsMap = new TreeMap<>();

    /**
     * TIPS: Use EnumSet.of( {@link IntrospectionParameters#ONLY_PUBLIC},
     * {@link IntrospectionParameters#NO_DEPRECATED} ) for {@code parameters}
     *
     * @param inpectClass Class of object to analyze
     * @param parameters  Parameters
     * @see IVIgnore
     */
    public IntrospectionBuilder(
        final Class<O>                     inpectClass,
        final Set<IntrospectionParameters> parameters
        )
    {
        final EnumSet<IntrospectionParameters> safeParameters =
                EnumHelper.safeCopyOf( parameters, IntrospectionParameters.class );

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

    @SuppressWarnings("squid:S1066") // Collapsible "if" statements
    private void addGetterOrSetter(
        final Set<IntrospectionParameters> parameters,
        final Method                       method
        )
    {
        boolean tryToHandle = true;

        if( parameters.contains( IntrospectionParameters.ONLY_PUBLIC ) ) {
            tryToHandle = Modifier.isPublic( method.getModifiers() );
            }

        if( tryToHandle ) {
            if( parameters.contains( IntrospectionParameters.NO_DEPRECATED ) ) {
                tryToHandle = ! method.isAnnotationPresent( Deprecated.class );
                }
            }

        if( tryToHandle ) {
            tryToHandle = ! method.isAnnotationPresent( IVIgnore.class );
            }

        if( tryToHandle ) {
            if( method.getParameterTypes().length == 0 ) {
                handleGetter( method );
                }
            else if( method.getParameterTypes().length == 1 ) {
                handleSetter( method );
            }
        }
        else /* getThis == false */ if( LOGGER.isDebugEnabled() ) {
            LOGGER.debug( "* (out of scope) Ignore this Method: " + method );
        }
    }

    private void handleSetter( final Method method )
    {
        final String methodName = method.getName();

        if( methodName.startsWith( "is" ) ) {
            // for some set method witch have name like isSomeThing
            addSetter( methodName.substring( 2 ), method );
        } else if( methodName.startsWith( "set" ) ) {
            addSetter( methodName.substring( 3 ), method );
        }
    }

    private void handleGetter( final Method method )
    {
        final Class<?> returnType = method.getReturnType();

        if( (returnType != Void.class) && (returnType != void.class) ) {
            final String methodName = method.getName();

            if( ! "getClass".equals( methodName ) ) {
                if( methodName.startsWith( "is" ) ) {
                    addGetter( methodName.substring( 2 ), method );
                } else if( methodName.startsWith( "get" ) ) {
                    addGetter( methodName.substring( 3 ), method );
                }
            }
        }
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
        final Method previous = methodsMap.put( beanName, method );

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
        return this.getterMethodsMap;
    }

    /**
     * @return the setterMethodsMap
     */
    public Map<String, Method> getSetterMethodsMap()
    {
        return this.setterMethodsMap;
    }

}

