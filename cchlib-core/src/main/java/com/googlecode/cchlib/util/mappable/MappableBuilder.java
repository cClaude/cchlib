package com.googlecode.cchlib.util.mappable;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Pattern;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.NeedDoc;

/**
 * NEEDDOC
 */
@NeedDoc
public class MappableBuilder
{
    private static final Logger LOGGER = Logger.getLogger( MappableBuilder.class );

    private final Pattern               methodesNamePattern;
    private final Collection<Class<?>>  returnTypeClasses;
    private final Set<MappableItem>     mappableItemSet;
    private final String                toStringNullValue;
    private final MappableBuilderFormat mappableBuilderFormat;

    /**
     * Create a MappableBuilder using giving factory
     *
     * @param factory Factory to use for this MappableBuilder
     */
    public MappableBuilder( final MappableBuilderFactory factory )
    {
        this.toStringNullValue      = factory.getStringNullValue();
        this.mappableBuilderFormat  = new MappableBuilderFormat( factory );
        this.methodesNamePattern    = factory.getMethodesNamePattern();
        this.returnTypeClasses      = factory.getClasses();
        this.mappableItemSet        = factory.getMappableItemSet();
    }

    /**
     * Create a MappableBuilder using default factory
     * @see #createMappableBuilderFactory()
     *
     * @return a new MappableBuilderFactory
     */
    public static MappableBuilder createMappableBuilder()
    {

        return new MappableBuilder( createMappableBuilderFactory() );
    }

    /**
     * Create a default MappableBuilderFactory based
     * on {@link DefaultMappableBuilderFactory}
     * <br>
     * <br>
     * Equivalent to invoke:
     * <pre>
     *   new DefaultMappableBuilderFactory()
     *      .add( MAPPABLE_ITEM_DEFAULT_CONFIG )
     *      .add( CLASSES_STANDARDS_TYPES );
     * </pre>
     *
     * @return a new MappableBuilderFactory
     */
    public static MappableBuilderFactory createMappableBuilderFactory()
    {
        return new DefaultMappableBuilderFactory()
            .add( MappableItem.MAPPABLE_ITEM_DEFAULT_CONFIG )
            .add( MappableTypes.CLASSES_STANDARDS_TYPES )
            ;
    }

    /**
     * Build Map according to specified factory.
     * <p>
     * keys String are sorted.
     * </p>
     * @param object Object to examine.
     * @return a Map view of the {@code object}
     */
    public Map<String,String> toMap( final Object object )
    {
        final Map<String,String> map     = new HashMap<>();
        final Class<?>           clazz   = object.getClass();
        final Method[]           methods = getDeclaredMethods(clazz);

        for( final Method method : methods ) {
            if( (method.getParameterTypes().length == 0)
                && this.methodesNamePattern.matcher( method.getName() ).matches()
                ) {
                handleMethodWithValueForToMap( object, map, method );
                }
            }

        return new TreeMap<>( map );
    }

    @SuppressWarnings("squid:MethodCyclomaticComplexity")
    private void handleMethodWithValueForToMap(
        final Object             object,
        final Map<String,String> map,
        final Method             method
        )
    {
        final Class<?> returnType = method.getReturnType();
        Object   result0;

        if( isMappable( returnType ) ) {
            if( returnType.isArray() ) {
                handleMappableArrayForMap( object, map, method );
                }

            result0 = invoke(object, method, map, Mappable.class);

            if(result0 != null) {
                MappableBuilder.addRec(
                        map,
                        method.getName() + "().",
                        (Mappable) result0
                        );
            }
            return;
        }

        if( this.mappableItemSet.contains(MappableItem.DO_ITERATOR) && Iterator.class.isAssignableFrom(returnType)) {
            handleIteratorForMap( object, map, method );
            return;
        }

        if( this.mappableItemSet.contains( MappableItem.DO_ITERABLE ) &&
                Iterable.class.isAssignableFrom( returnType )
                ) {
            handleIterableForMap( object, map, method );
            return;
        }

        if( this.mappableItemSet.contains(MappableItem.DO_ENUMERATION) && Enumeration.class.isAssignableFrom(returnType)) {
            handleEnumerationForMap( object, map, method );
            return;
        }

        if( !shouldEvaluate(returnType) ) {
            return;
        }

        invokeMethodWithValueForToMap( object, map, method, returnType );
    }

    private void invokeMethodWithValueForToMap( final Object object, final Map<String, String> map, final Method method, final Class<?> returnType )
    {
        final Object methodResult = invoke(object, method, map, Object.class);

        if( returnType.isArray() ) {
            final String methodName = method.getName();

            if( methodResult == null ) {
                map.put(
                        formatMethodName( methodName ),
                        toString( methodResult )
                        );
                }
            else {
                final int len = Array.getLength( methodResult );

                for(int i = 0; i < len; i++) {
                    final Object value = Array.get( methodResult, i );

                    map.put(
                            formatArrayEntry(methodName, i, len),
                            toString(value)
                            );
                    }
                }
        }
        else {
            if( methodResult == null ) {
                map.put(
                        formatMethodName(method.getName()),
                        null
                        );
                }
            else {
                map.put(
                        formatMethodName(method.getName()),
                        methodResult.toString()
                        );
                }
        }
    }

    private void handleEnumerationForMap(
        final Object             object,
        final Map<String,String> map,
        final Method             method
        )
    {
        final Enumeration<?> enum0      = (Enumeration<?>)invoke(object, method, map, Enumeration.class);
        final String         methodName = method.getName();

        int i = 0;
        map.put(
                formatMethodName( methodName ),
                (enum0 == null) ? null : enum0.toString()
                );

        if( enum0 != null ) {
            while( enum0.hasMoreElements() ) {
                map.put(
                        formatEnumerationEntry(methodName, i++, -1),
                        toString( enum0.nextElement() )
                        );
                }
            }
    }

    private void handleIterableForMap(
        final Object             object,
        final Map<String,String> map,
        final Method             method
        )
    {
        final Iterable<?> list       = (Iterable<?>)invoke(object, method, map, Iterable.class);
        final String      methodName = method.getName();

        map.put(
                formatMethodName( methodName ),
                (list == null) ? null : list.toString()
                );

        if( list != null ) {
            final Iterator<?> iter = list.iterator();
            int               i    = 0;

            if( iter != null ) {
                while( iter.hasNext() ) {
                    map.put(
                            formatIterableEntry(methodName, i++, -1),
                            toString(iter.next())
                            );
                }
            }
        }

    }

    private void handleIteratorForMap(
        final Object             object,
        final Map<String,String> map,
        final Method             method
        )
    {
        final Iterator<?> iter = (Iterator<?>)invoke(object, method, map, Iterator.class);
        final String      name = method.getName();

        int i = 0;
        map.put(
                formatMethodName(name),
                (new StringBuilder()).append(iter).toString()
                );
        while( iter.hasNext() ) {
            map.put(
                    formatIteratorEntry(name, i++, -1),
                    toString( iter.next())
                    );
        }
    }

    private void handleMappableArrayForMap(
        final Object             object,
        final Map<String,String> map,
        final Method             method
        )
    {
        final Mappable[] result1 = (Mappable[])invoke(object, method, map, Mappable.class);

        if( result1 == null ) {
            return;
            }

        final int     len         = Array.getLength(result1);
        final String  methodName  = method.getName();

        for( int i = 0; i<len; i++) {
            final Mappable value = (Mappable)Array.get(result1, i);
            final String   name  = formatIterableEntry(methodName, i, len);

            if( value == null ) {
                map.put( name, null );
                }
            else {
                MappableBuilder.addRec( map, name, value );
                }
            }
    }

    private String[] buildStringArray(
        final String    methodeName,
        final int       index,
        final int       max
        )
    {
        return new String[] {
            methodeName,
            Integer.toString(index),
            Integer.toString(max)
            };
    }

    @NeedDoc
    protected String formatIterableEntry(
        final String  methodeName,
        final int     index,
        final int     max
        )
    {
        return this.mappableBuilderFormat.getMessageFormatIterableEntry().format(
            buildStringArray( methodeName, index, max )
            );
    }

    @NeedDoc
    protected String formatIteratorEntry( final String methodeName, final int index, final int max)
    {
        return this.mappableBuilderFormat.getMessageFormatIteratorEntry().format(
            buildStringArray( methodeName, index, max )
            );
    }

    @NeedDoc
    protected String formatEnumerationEntry( final String methodeName, final int index, final int max )
    {
        return this.mappableBuilderFormat.getMessageFormatEnumerationEntry().format(
            buildStringArray( methodeName, index, max )
            );
    }

    @NeedDoc
    protected String formatArrayEntry( final String methodeName, final int index, final int max )
    {
        return this.mappableBuilderFormat.getMessageFormatArrayEntry().format(
            buildStringArray( methodeName, index, max )
            );
    }

    @NeedDoc
    protected String formatMethodName( final String methodeName )
    {
        final String[] params = {
            methodeName
            };

        return this.mappableBuilderFormat.getMessageFormatMethodName().format(params);
    }

    /** Build methods list to handle */
    private final Method[] getDeclaredMethods( final Class<?> clazz )
    {
        if( !this.mappableItemSet.contains( MappableItem.DO_PARENT_CLASSES ) ) {
            return clazz.getDeclaredMethods();
            }

        final Set<Method> methodsSet = new HashSet<>();

        for( final Method method : clazz.getDeclaredMethods() ) {
            methodsSet.add( method );
            }

        for( Class<?> aClass = clazz.getSuperclass(); aClass != null; aClass = aClass.getSuperclass() ) {
            for( final Method method : aClass.getDeclaredMethods() ) {
                methodsSet.add( method );
                }
            }

         return methodsSet.toArray( new Method[ methodsSet.size() ] );
    }

    @NeedDoc
    protected final boolean isMappable( final Class<?> clazz )
    {
        if(!this.mappableItemSet.contains(MappableItem.DO_RECURSIVE)) {
            return false;
            }

        if(clazz.isArray()) {
            return Mappable.class.isAssignableFrom(clazz.getComponentType());
            }
        else {
            return Mappable.class.isAssignableFrom(clazz);
            }
    }

    @SuppressWarnings("squid:MethodCyclomaticComplexity")
    private final boolean shouldEvaluate( final Class<?> returnType )
    {
        final int modifier = returnType.getModifiers();

        if(Modifier.isPrivate(modifier) && !this.mappableItemSet.contains(MappableItem.TRY_PRIVATE_METHODS)) {
            return false;
            }

        if(Modifier.isProtected(modifier) && !this.mappableItemSet.contains(MappableItem.TRY_PROTECTED_METHODS)) {
            return false;
            }
        if(this.mappableItemSet.contains(MappableItem.ALL_PRIMITIVE_TYPE) && returnType.isPrimitive()) {
            return true;
            }

        if(this.mappableItemSet.contains(MappableItem.DO_ARRAYS) && returnType.isArray()) {
            return true;
            }

        for( final Class<?> c : this.returnTypeClasses ) {
            if( c.isAssignableFrom( returnType ) ) {
                return true;
                }
            }

        return false;
    }

    private String toString( final Object object )
    {
        if( object == null ) {
            return this.toStringNullValue;
            }
        else {
            return toString( this.mappableItemSet, object );
            }
    }

    private static final String toString(
        final Set<MappableItem>   mappableItemSet,
        final Object              object
        )
    {
        if( object.getClass().isArray() ) {
            return handleArrayForToString( mappableItemSet, object );
            }

        if( mappableItemSet.contains( MappableItem.DO_ITERATOR ) && (object instanceof Iterator) ) {
            return handleIteratorForToString( mappableItemSet, object );
            }

        if( mappableItemSet.contains( MappableItem.DO_ENUMERATION ) && (object instanceof java.util.Enumeration) ) {
            return handleEnumerationForToString( mappableItemSet, object );
            }

        return object.toString();
    }

    private static String handleEnumerationForToString(
        final Set<MappableItem> mappableItemSet,
        final Object            object
        )
    {
        final StringBuilder  sb     = new StringBuilder();
        final Enumeration<?> enum0 = (Enumeration<?>)object;
        boolean              first = true;

        sb.append( "Enumeration[" );

        while( enum0.hasMoreElements() ) {
            if( first ) {
                first = false;
                }
            else {
                sb.append( ',' );
                }

            sb.append(
                toString(mappableItemSet, enum0.nextElement())
                );
        }

        sb.append( ']' );

        return sb.toString();
    }

    private static String handleIteratorForToString(
        final Set<MappableItem> mappableItemSet,
        final Object            object
        )
    {
        final StringBuilder sb    = new StringBuilder();
        final Iterator<?>   iter  = (Iterator<?>)object;
        boolean             first = true;

        sb.append( "Iterator[" );

        while( iter.hasNext() ) { // $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.minimizeScopeOfLocalVariables
            if(first) {
                first = false;
                }
            else {
                sb.append(',');
                }
            sb.append(
                toString( mappableItemSet, iter.next() )
                );
            }

        sb.append( ']' );

        return sb.toString();
    }

    private static String handleArrayForToString(
        final Set<MappableItem> mappableItemSet,
        final Object            object
        )
    {
        final StringBuilder   sb      = new StringBuilder();
        final Object[]        array   = (Object[])object;
        boolean               first   = true;

        sb.append( '[' );

        for( final Object o : array ) {
            if( first ) {
                first = false;
                }
            else {
                sb.append(',');
                }
            sb.append( toString(mappableItemSet, o) );
            }

        sb.append( ']' );

        return sb.toString();
    }

    private static final void addRec(
        final Map<String,String>  hashMap,
        final String              methodName,
        final Mappable            object
        )
    {
         for( final Map.Entry<String,String> entry : object.toMap().entrySet() ) {
            hashMap.put(
                    methodName + entry.getKey(),
                    entry.getValue()
                    );
             }
    }

    @NeedDoc
    @SuppressWarnings({"squid:MethodCyclomaticComplexity"})
    protected final Object invoke(
            final Object             object,
            final Method             method,
            final Map<String,String> hashMap,
            final Class<?>           resultClass
            )
    {
        final Object result = null;

        try {
            return methodInvoke( object, method, hashMap, resultClass );
            }
        catch( final ClassCastException improbable ) {
            throw new MappableRuntimeException(
                    "method.getName() - ClassCastException: " + result,
                    improbable
                    );
            }
        catch( IllegalArgumentException | NullPointerException e ) {
            throw e;
            }
        catch( final ExceptionInInitializerError e ) {
            if( LOGGER.isDebugEnabled() ) {
                logInvokeException( method, e );
            }

            hashMap.put(
                    formatMethodName( method.getName() ),
                    "ExceptionInInitializerError: " + e.getCause()
                    );
            return null;
            }
        catch( final IllegalAccessException ignore ) {
            if( LOGGER.isDebugEnabled() ) {
                logInvokeException( method, ignore );
            }

            return null;
            }
        catch( final InvocationTargetException e ) {
            if( LOGGER.isDebugEnabled() ) {
                logInvokeException( method, e );
            }

            hashMap.put(
                    formatMethodName( method.getName() ),
                    "InvocationTargetException: " + e.getCause()
                    );
            }

        return null;
    }

    @SuppressWarnings("squid:RedundantThrowsDeclarationCheck")
    private Object methodInvoke( //
        final Object              object, //
        final Method              method, //
        final Map<String, String> hashMap,  //
        final Class<?>            resultClass //
        ) throws IllegalAccessException,
                 IllegalArgumentException,
                 InvocationTargetException
    {
        final Object result = method.invoke( object, (Object[])null );

        if( result == null ) {
            hashMap.put(
                    formatMethodName( method.getName() ),
                    this.toStringNullValue
                    );

            return null;
        }

        return resultClass.cast( result );
    }

    private void logInvokeException( final Method method, final Throwable e )
    {
        LOGGER.error( "Invoke: " + method, e );
    }
}
