package cx.ath.choisnet.lang.reflect;

import java.io.File;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URI;
import java.net.URL;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Pattern;
import cx.ath.choisnet.ToDo;

/**
 * TODOC
 */
@ToDo
public class MappableBuilder
{
    /**
     * TODOC
     */
    public static final MappableItem[] MAPPABLE_ITEM_DEFAULT_CONFIG = {
        MappableItem.ALL_PRIMITIVE_TYPE,
        MappableItem.DO_ARRAYS
        };

    /**
     * TODOC
     */
    public static final MappableItem[] MAPPABLE_ITEM_SHOW_ALL = {
            MappableItem.ALL_PRIMITIVE_TYPE
            };

    /**
     * TODOC
     */
    public static final Class<?>[] CLASSES_SHOW_ALL = {
        Object.class
        };

    /**
     *  TODOC
     */
    public static final Class<?>[] CLASSES_STANDARDS_TYPES = {
        Boolean.class, Character.class, Enum.class, Number.class, String.class,
        File.class, URL.class, URI.class,
        Collection.class
        };

    private final Pattern methodesNamePattern;
    private final Set<Class<?>> returnTypeClasses;
    private final Set<MappableItem> mappableItemSet;
    private final String toStringNullValue;
    private final MessageFormat messageFormatIteratorEntry;
    private final MessageFormat messageFormatIterableEntry;
    private final MessageFormat messageFormatEnumerationEntry;
    private final MessageFormat messageFormatArrayEntry;
    private final MessageFormat messageFormatMethodName;

    /**
     * Create a MappableBuilder using giving factory
     *
     * @param factory Factory to use for this MappableBuilder
     */
    public MappableBuilder( final MappableBuilderFactory factory )
    {
        this.toStringNullValue              = factory.getStringNullValue();
        this.messageFormatIteratorEntry     = new MessageFormat(factory.getMessageFormatIteratorEntry());
        this.messageFormatIterableEntry     = new MessageFormat(factory.getMessageFormatIterableEntry());
        this.messageFormatEnumerationEntry  = new MessageFormat(factory.getMessageFormatEnumerationEntry());
        this.messageFormatArrayEntry        = new MessageFormat(factory.getMessageFormatArrayEntry());
        this.messageFormatMethodName        = new MessageFormat(factory.getMessageFormatMethodName());
        this.methodesNamePattern            = factory.getMethodesNamePattern();
        this.returnTypeClasses              = factory.getClasses();
        this.mappableItemSet                = factory.getMappableItemSet();
    }

    /**
     * Create a MappableBuilder using default factory
     * @see #createMappableBuilderFactory()
     */
    public static MappableBuilder createMappableBuilder()
    {

        return new MappableBuilder( createMappableBuilderFactory() );
    }

    /**
     * Create a default MappableBuilderFactory based
     * on {@link DefaultMappableBuilderFactory}
     * <p>
     * TODO: doc! describe default initialization
     * </p>
     */
    public static MappableBuilderFactory createMappableBuilderFactory()
    {
        return new DefaultMappableBuilderFactory()
            .add( MAPPABLE_ITEM_DEFAULT_CONFIG )
            .add( CLASSES_STANDARDS_TYPES )
            ;
    }

    /**
     * Build Map according to specified factory.
     * <p>
     * keys String are sorted.
     * </p>
     */
    public Map<String,String> toMap(final Object object)
    {
        final HashMap<String,String>    hashMap = new HashMap<String,String>();
        final Class<?>                  clazz   = object.getClass();
        final Method[]                  methods = getDeclaredMethods(clazz);

        for( Method method : methods ) {
            if(
                    method.getParameterTypes().length != 0
                    || !methodesNamePattern.matcher(
                            method.getName()
                            ).matches()
                    ) {
                continue;
            }

            Class<?> returnType = method.getReturnType();
            Object   result0;

            if( isMappable( returnType ) ) {
                if( returnType.isArray() ) {
                    Mappable[] result1 = (Mappable[])invoke(object, method, hashMap, Mappable.class);

                    if(result1 == null) {
                        continue;
                    }

                    int     len         = Array.getLength(result1);
                    String  methodName  = method.getName();
                    int     i           = 0;

                    do {
                        if(i >= len) {
                            continue;// label0;
                        }

                        Mappable value = (Mappable)Array.get(result1, i);
                        String   name  = formatIterableEntry(methodName, i, len);

                        if(value == null) {
                            hashMap.put(name, null);

                        }
                        else {
                            MappableBuilder.addRec(hashMap, name, value);
                        }
                        i++;
                    } while(true);
                }

                result0 = (Mappable)invoke(object, method, hashMap, Mappable.class);

                if(result0 != null) {
                    MappableBuilder.addRec(
                            hashMap,
                            (new StringBuilder()).append(method.getName()).append("().").toString(),
                            ((Mappable) (result0) )
                            );
                }
                continue;
            }

            if(mappableItemSet.contains(MappableItem.DO_ITERATOR) && Iterator.class.isAssignableFrom(returnType)) {
                Iterator<?> iter = (Iterator<?>)invoke(object, method, hashMap, Iterator.class);
                String name = method.getName();

                int i = 0;
                hashMap.put(
                        formatMethodName(name),
                        (new StringBuilder()).append("").append(iter).toString()
                        );
                while( iter.hasNext() ) {
                    hashMap.put(
                            formatIteratorEntry(name, i++, -1),
                            toString( iter.next())
                            );
                }
                continue;
            }

            if( mappableItemSet.contains( MappableItem.DO_ITERABLE ) &&
                    Iterable.class.isAssignableFrom( returnType )
                    ) {
                Iterable<?> iterLst     = (Iterable<?>)invoke(object, method, hashMap, Iterable.class);
                Iterator<?> iter        = iterLst.iterator();
                String      methodName  = method.getName();

                int i = 0;
                hashMap.put(
                        formatMethodName( methodName ),
                        iter == null ? null : iter.toString()
                        );

                while( iter.hasNext() ) {
                    hashMap.put(
                            formatIterableEntry(methodName, i++, -1),
                            toString(iter.next())
                            );
                }
                continue;
            }

            if( mappableItemSet.contains(MappableItem.DO_ENUMERATION) && Enumeration.class.isAssignableFrom(returnType)) {
                Enumeration<?>  enum0       = (Enumeration<?>)invoke(object, method, hashMap, Enumeration.class);
                String          methodName  = method.getName();

                int i = 0;
                hashMap.put(
                        formatMethodName( methodName ),
                        enum0 == null ? null : enum0.toString()
                        );

                if( enum0 != null ) {
                    while( enum0.hasMoreElements() ) {
                        hashMap.put(
                                formatEnumerationEntry(methodName, i++, -1),
                                toString( enum0.nextElement() )
                                );
                    }
                }
                continue;
            }

            if( !shouldEvaluate(returnType) ) {
                continue;
            }

//            System.err.println( "m:" + method );
            Object methodResult = invoke(object, method, hashMap, Object.class);
//            Enumeration<?> enum0 = (Enumeration<?>)invoke(object, method, hashMap, Object.class);
//
//            if(enum0 == null) {
//                continue;
//            }

            if( returnType.isArray() ) {
                final String methodName = method.getName();

                if( methodResult == null ) {
                    hashMap.put(
                            formatMethodName( methodName ),
                            toString( methodResult )
                            );
                    }
                else {
                    int len = Array.getLength( methodResult );

                    for(int i = 0; i < len; i++) {
                        Object value = Array.get( methodResult, i );

                        hashMap.put(
                                formatArrayEntry(methodName, i, len),
                                toString(value)
                                );
                        }
                    }
            }
            else {
                if( methodResult == null ) {
                    hashMap.put(
                            formatMethodName(method.getName()),
                            null
                            );
                    }
                else {
                    hashMap.put(
                            formatMethodName(method.getName()),
                            methodResult.toString()
                            );
                    }
            }
        }

        return new TreeMap<String,String>(hashMap);
    }

    /**
     * TODOC
     *
     * @param methodeName
     * @param index
     * @param max
     * @return TODO: doc
     */
    protected String formatIterableEntry(
            String  methodeName,
            int     index,
            int     max
            )
    {
        String params[] = {
            methodeName, Integer.toString(index), Integer.toString(max)
        };

        return messageFormatIterableEntry.format(params);
    }

    /**
     * TODOC
     *
     * @param methodeName
     * @param index
     * @param max
     * @return TODO: doc
     */
    protected String formatIteratorEntry(String methodeName, int index, int max)
    {
        String[] params = {
            methodeName,
            Integer.toString(index),
            Integer.toString(max)
        };

        return messageFormatIteratorEntry.format(params);
    }

    /**
     * TODOC
     *
     * @param methodeName
     * @param index
     * @param max
     * @return TODO: doc
     */
    protected String formatEnumerationEntry(String methodeName, int index, int max)
    {
        final String[] params = {
            methodeName,
            Integer.toString(index),
            Integer.toString(max)
            };

        return messageFormatEnumerationEntry.format(params);
    }

    /**
     * TODOC
     *
     * @param methodeName
     * @param index
     * @param max
     * @return TODO: doc
     */
    protected String formatArrayEntry(String methodeName, int index, int max)
    {
        final String[] params = {
            methodeName,
            Integer.toString(index),
            Integer.toString(max)
            };

        return messageFormatArrayEntry.format(params);
    }

    /**
     * TODOC
     *
     * @param methodeName
     * @return TODO: doc
     */
    protected String formatMethodName(String methodeName)
    {
        final String[] params = {
            methodeName
            };

        return messageFormatMethodName.format(params);
    }

    private final Method[] getDeclaredMethods(Class<?> clazz)
    {
        if(!mappableItemSet.contains(MappableItem.DO_PARENT_CLASSES)) {
            return clazz.getDeclaredMethods();
            }

        Set<Method> methodsSet = new HashSet<Method>();
        Method arr0$[] = clazz.getDeclaredMethods();
        int len0$ = arr0$.length;

        for(int i$ = 0; i$ < len0$; i$++) {
            Method m = arr0$[i$];
            methodsSet.add(m);
            }

        for(Class<?> c = clazz.getSuperclass(); c != null; c = c.getSuperclass()) {
            Method arr1$[] = c.getDeclaredMethods();
            int len1$ = arr1$.length;
            for(int i$ = 0; i$ < len1$; i$++) {
                Method m = arr1$[i$];
                methodsSet.add(m);
                }
            }

        Method[] methods = new Method[methodsSet.size()];
        int i = 0;

        for(Iterator<Method> i$ = methodsSet.iterator(); i$.hasNext();) {
            Method m = i$.next();
            methods[i++] = m;
            }

        return methods;
    }

    /**
     * TODOC
     *
     * @param clazz
     * @return TODO: doc
     */
    protected final boolean isMappable(Class<?> clazz)
    {
        if(!mappableItemSet.contains(MappableItem.DO_RECURSIVE)) {
            return false;
            }

        if(clazz.isArray()) {
            return Mappable.class.isAssignableFrom(clazz.getComponentType());
            }
        else {
            return Mappable.class.isAssignableFrom(clazz);
            }
    }

    private final boolean shouldEvaluate(Class<?> returnType)
    {
        int modifier = returnType.getModifiers();

        if(Modifier.isPrivate(modifier) && !mappableItemSet.contains(MappableItem.TRY_PRIVATE_METHODS)) {
            return false;
            }

        if(Modifier.isProtected(modifier) && !mappableItemSet.contains(MappableItem.TRY_PROTECTED_METHODS)) {
            return false;
            }
        if(mappableItemSet.contains(MappableItem.ALL_PRIMITIVE_TYPE) && returnType.isPrimitive()) {
            return true;
            }

        if(mappableItemSet.contains(MappableItem.DO_ARRAYS) && returnType.isArray()) {
            return true;
            }

        for(Iterator<Class<?>> i$ = returnTypeClasses.iterator(); i$.hasNext();) {
            Class<?> c = i$.next();

            if( c.isAssignableFrom( returnType ) ) {
                return true;
                }
            }

        return false;
    }

    private String toString(final Object object)
    {
        if( object == null ) {
            return toStringNullValue;
            }
        else {
            return toString( mappableItemSet, object );
            }
    }

    // TODO: Optimizations: remove some StringBuilder
    private static final String toString(
            Set<MappableItem>   mappableItemSet,
            Object              object
            )
    {
        if(object.getClass().isArray()) {
            StringBuilder   sb      = new StringBuilder();
            Object[]        array   = (Object[])object;
            boolean first = true;
            Object[] arr$ = array;
            int len$ = arr$.length;

            for(int i$ = 0; i$ < len$; i$++) {
                Object o = arr$[i$];

                if(first) {
                    first = false;
                }
                else {
                    sb.append(',');
                }
                sb.append( toString(mappableItemSet, o) );
            }

            return (new StringBuilder())
                .append('[')
                .append(sb)
                .append(']')
                .toString();
        }

        if(mappableItemSet.contains(MappableItem.DO_ITERATOR)) {
            if(object instanceof java.util.Iterator) {
                Iterator<?> iter = (Iterator<?>)object;
                StringBuilder sb = new StringBuilder();
                boolean first = true;

                for(; iter.hasNext(); sb.append(toString(mappableItemSet, iter.next()))) {
                    if(first) {
                        first = false;
                    }
                    else {
                        sb.append(',');
                    }
                }

                return (new StringBuilder())
                    .append("Iterator[")
                    .append(sb)
                    .append(']')
                    .toString();
            }
        }
        else if(mappableItemSet.contains(MappableItem.DO_ENUMERATION) && (object instanceof java.util.Enumeration)) {
            Enumeration<?> enum0 = (Enumeration<?>)object;
            StringBuilder sb = new StringBuilder();
            boolean first = true;

            for(; enum0.hasMoreElements(); sb.append( toString(mappableItemSet, enum0.nextElement()))) {
                if(first) {
                    first = false;
                }
                else {
                    sb.append(',');
                }
            }

            return (new StringBuilder())
                .append("Enumeration[")
                .append(sb.toString())
                .append(']')
                .toString();
        }

        return object.toString();
    }

    private static final void addRec(
            Map<String,String>  hashMap,
            String              methodName,
            Mappable            object
            )
    {
        Set<Map.Entry<String,String>>   set = object.toMap().entrySet();
        Map.Entry<String,String>        entry;

        for(
                Iterator<Map.Entry<String,String>> i$ = set.iterator();
                i$.hasNext();
                hashMap.put(
                        (new StringBuilder()).append(methodName).append(entry.getKey()).toString(),
                        entry.getValue()
                        )
                     ) {
            entry = i$.next();
        }
    }

    /**
     * TODOC
     *
     * @param object
     * @param method
     * @param hashMap
     * @param resultClass
     * @return TODO: doc
     */
    protected final Object invoke(
            final Object object,
            final Method method,
            final Map<String,String> hashMap,
            final Class<?> resultClass
            )
    {
        Object result = null;

        try {
            result = method.invoke( object, (Object[])null );

            if( result == null ) {
                hashMap.put(
                        formatMethodName( method.getName() ),
                        toStringNullValue
                        );

                return null;
            }

            return resultClass.cast( result );
        }
        catch( ClassCastException improbable ) {
            throw new RuntimeException( (new StringBuilder())
                    .append( "method.getName() - ClassCastException: " )
                    .append( result )
                    .toString(),
                    improbable
                    );
        }
        catch( IllegalArgumentException e ) {
            throw e;
        }
        catch( NullPointerException e ) {
            throw e;
        }
        catch( ExceptionInInitializerError e ) {
            hashMap.put(
                    formatMethodName( method.getName() ),
                    (new StringBuilder())
                            .append( "ExceptionInInitializerError: " )
                            .append( e.getCause() )
                            .toString()
                            );
            return null;
        }
        catch( IllegalAccessException ignore ) {
            return null;
        }
        catch( InvocationTargetException e ) {
            hashMap.put(
                    formatMethodName( method.getName() ),
                    (new StringBuilder())
                            .append( "InvocationTargetException: " )
                            .append( e.getCause() )
                            .toString()
                            );
        }

        return null;
    }
}
