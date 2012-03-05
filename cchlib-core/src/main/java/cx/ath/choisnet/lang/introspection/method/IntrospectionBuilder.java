/************************************************************************************
 *                                                                                  *
 *                                                                                  *
 ************************************************************************************/
package cx.ath.choisnet.lang.introspection.method;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.EnumSet;
import java.util.Map;
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
    private final static Logger sLog = Logger.getLogger(IntrospectionBuilder.class);

    /** Getter Methods list */
    private Map<String,Method> getterMethodsMap = new TreeMap<String,Method>();

    /** Setter Methods list */
    private Map<String,Method> setterMethodsMap = new TreeMap<String,Method>();

    /**
     *
     * @param inpectClass
     * @param attribSet
     * @see IVIgnore
     *
     *  TIPS: Use EnumSet.of(Introspection.Attrib.ONLY_PUBLIC, Introspection.Attrib.NO_DEPRECATED) for parameter attribSet
     */
    public IntrospectionBuilder(
            Class<O>                        inpectClass,
            EnumSet<Introspection.Attrib>   attribSet
            )
    {
        if( attribSet == null ) {
            attribSet = EnumSet.noneOf( Introspection.Attrib.class );
        }

        // Construit la liste des observateurs
        final Method[] methods = inpectClass.getMethods();

        for( Method m : methods ) {
            boolean getThis = true;

            if( attribSet.contains( Introspection.Attrib.ONLY_PUBLIC ) ) {
                getThis = Modifier.isPublic( m.getModifiers() );
            }
            if( getThis ) {
                if( attribSet.contains( Introspection.Attrib.NO_DEPRECATED ) ) {
                    getThis = ! m.isAnnotationPresent( Deprecated.class );
                }
            }
            if( getThis ) {
                getThis = ! m.isAnnotationPresent( IVIgnore.class );
            }

            if( getThis ) {
                if( m.getParameterTypes().length == 0 ) {
                    final String methodName = m.getName();

                    //TODO: check if return something !

                    if( methodName.equals( "getClass" ) ) {
                        // ignore privateSLog.trace( "Ignored Method: " + m );
                        }
                    else if( methodName.startsWith( "is" ) ) {
                        addGetter( methodName.substring( 2 ), m );
                        }
                    else if( methodName.startsWith( "get" ) ) {
                        addGetter( methodName.substring( 3 ), m );
                        }
                    /*else if( sLog.isDebugEnabled() ) {
                        sLog.debug( "* (0)Ignore this Method: " + m );
                    } */
                    }
                else if( m.getParameterTypes().length == 1 ) {
                    final String methodName = m.getName();

                    if( methodName.startsWith( "is" ) ) {
                        // for some set method witch have name like isSomeThing
                        addSetter( methodName.substring( 2 ), m );
                    } else if( methodName.startsWith( "set" ) ) {
                        addSetter( methodName.substring( 3 ), m );
                    } /*else if( sLog.isDebugEnabled() ) {
                        sLog.debug( "* (1)Ignore this Method: " + m );
                    }*/
                } /*else if( sLog.isDebugEnabled() ) {
                    sLog.debug( "* (>1)Ignore this Method: " + m );
                }*/
            } // if( getThis )
            else if( sLog.isDebugEnabled() ) {
                sLog.debug( "* (out of scope) Ignore this Method: " + m );
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
        Method previous = methodsMap.put( beanName, method );

        // Verify than method does not already exist in Map !
        if( previous != null ) {
             sLog.error( "*** " + beanName + " already in Map: " + previous.getName() + " - " + method.getName() );
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

