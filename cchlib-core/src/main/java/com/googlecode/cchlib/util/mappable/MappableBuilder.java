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
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
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
     *      .add( MappableItem.MAPPABLE_ITEM_DEFAULT_CONFIG )
     *      .add( MappableTypes.ALL_TYPES );
     * </pre>
     *
     * @return a new MappableBuilderFactory
     */
    public static MappableBuilderFactory createMappableBuilderFactory()
    {
        return new DefaultMappableBuilderFactory()
            .add( MappableItem.MAPPABLE_ITEM_DEFAULT_CONFIG )
            .add( MappableTypes.ALL_TYPES )
            ;
    }

    /**
     * Build Map according to specified factory.
     *
     * @param <T>
     *            Type of the {@code object} to examine.
     * @param object
     *            Object to examine.
     * @return a Map view of the {@code object}
     * @see #toMap(Object, Class)
     */
    public <T> Map<String,String> toMap( @Nonnull final T object )
    {
        @SuppressWarnings("unchecked")
        final Class<T> type = (Class<T>)object.getClass();
        return toMap( object, type );
    }

    /**
     * Build Map according to specified factory.
     * <p>
     * keys String are sorted.
     *
     * @param <T> Type of the {@code object} to examine.
     * @param object Object to examine.
     * @param type  Type to use to examine {@code object}
     * @return a Map view of the {@code object}
     * @since 4.2
     */
    public <T extends C,C> Map<String,String> toMap(
        @Nullable final T       object,
        @Nonnull final Class<C> type
        )
    {
        final Map<String,String> map     = new HashMap<>();
        final Set<Method>        methods = getMethodsToHandle( type );

        for( final Method method : methods ) {
            if( (method.getParameterTypes().length == 0)
                && this.methodesNamePattern.matcher( method.getName() ).matches()
                ) {
                toMap( object, map, method );
                }
            }

        return new TreeMap<>( map );
    }

    private <T> void toMap(
        @Nullable final T                  object,
        @Nonnull final Map<String, String> map,
        @Nonnull final Method              method
        )
    {
        if( object == null ) {
            map.put( getKey( method ), this.toStringNullValue );
        } else {
            handleMethodWithValueForToMap( object, map, method );
        }
    }

    private String getKey( final Method method )
    {
        final String[] params = {
                method.getName()
                };

            return this.mappableBuilderFormat.getMessageFormatMethodName().format( params );
    }

    @SuppressWarnings("squid:MethodCyclomaticComplexity")
    private <T> void handleMethodWithValueForToMap(
        @Nonnull final T                  object,
        @Nonnull final Map<String,String> map,
        @Nonnull final Method             method
        )
    {
        final Class<?> returnType = method.getReturnType();
        Object         result0;

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

    private void invokeMethodWithValueForToMap(
        final Object              object,
        final Map<String, String> map,
        final Method              method,
        final Class<?>            returnType
        )
    {
        final Object methodResult = invoke(object, method, map, Object.class);

        if( returnType.isArray() ) {

            if( methodResult == null ) {
                map.put(
                    getKey( method ),
                    toString( methodResult )
                    );
                }
            else {
                final String methodName = method.getName();
                final int    len        = Array.getLength( methodResult );

                for(int i = 0; i < len; i++) {
                    final Object value = Array.get( methodResult, i );

                    map.put(
                        formatArrayEntry( methodName, i, len ),
                        toString( value )
                        );
                    }
                }
        }
        else {
            if( methodResult == null ) {
                map.put(
                    getKey( method ),
                    null
                    );
                }
            else {
                map.put(
                    getKey( method ),
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
        final Enumeration<?> enumeration = (Enumeration<?>)invoke( object, method, map, Enumeration.class );

        map.put(
            getKey( method ),
            (enumeration == null) ? null : enumeration.toString()
            );

        if( enumeration != null ) {
            final String methodName = method.getName();
            int          i          = 0;

            while( enumeration.hasMoreElements() ) {
                map.put(
                    formatEnumerationEntry( methodName, i++, -1 ),
                    toString( enumeration.nextElement() )
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
        final Iterable<?> iterable = (Iterable<?>)invoke(object, method, map, Iterable.class);

        map.put(
            getKey( method ),
            (iterable == null) ? null : iterable.toString()
            );

        if( iterable != null ) {
            final String      methodName = method.getName();
            final Iterator<?> iter       = iterable.iterator();
            int               i          = 0;

            if( iter != null ) {
                while( iter.hasNext() ) {
                    map.put(
                        formatIterableEntry( methodName, i++, -1 ),
                        toString( iter.next() )
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
        final Iterator<?> iterator = (Iterator<?>)invoke(object, method, map, Iterator.class);

        map.put(
            getKey( method ),
            String.valueOf( iterator )
            );

        while( iterator.hasNext() ) {
            final String name = method.getName();
            int          i    = 0;

            map.put(
                formatIteratorEntry( name, i++, -1 ),
                toString( iterator.next() )
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

    /** Build methods list to handle */
    private final Set<Method> getMethodsToHandle( final Class<?> type )
    {
        if( !this.mappableItemSet.contains( MappableItem.DO_PARENT_CLASSES ) ) {
            return getMethods( type );
            }

        final Set<Method> methods = new HashSet<>();

        for( final Method method : getMethods( type ) ) {
            methods.add( method );
            }

        for( Class<?> supertype = type.getSuperclass(); supertype != null; supertype = supertype.getSuperclass() ) {
            methods.addAll( getMethods( supertype ) );
            }

         return methods;
    }

    private Set<Method> getMethods( final Class<?> type )
    {
        final Set<Method> methods = new HashSet<>();
        final Method[]    methodsArray;

        if( this.mappableItemSet.contains( MappableItem.TRY_PRIVATE_METHODS ) ||
            this.mappableItemSet.contains( MappableItem.TRY_PROTECTED_METHODS ) ) {
            methodsArray = type.getDeclaredMethods();
        } else {
            methodsArray = type.getMethods();
        }

        for( final Method method : methodsArray ) {
            methods.add( method );
        }

        return methods;
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
                getKey( method ),
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
                getKey( method ),
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
                getKey( method ),
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
