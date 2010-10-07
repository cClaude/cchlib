package cx.ath.choisnet.lang.reflect;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.text.MessageFormat;
import java.util.EnumSet;
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
 * <p style="border:groove;">
 * <b>Warning:</b>
 * Insofar the code of this class comes from decompiling
 * my own code following the loss of source code, the use
 * of this class must do so under protest until I have
 * check its stability, it could be subject to significant
 * change.
 * <br/>
 * <br/>
 * <b>Attention:</b>
 * Dans la mesure où le code de cette classe est issue de
 * la décompilation de mon propre code, suite à la perte
 * du code source, l'utilisation de cette classe doit ce
 * faire sous toute réserve tant que je n'ai pas vérifier
 * sa stabilité, elle est donc sujette à des changements 
 * importants.
 * </p>
 *
 * @author Claude CHOISNET
 *
 */
@ToDo
public class MappableHelper
{
    public enum Attributes {
        ALL_PRIMITIVE_TYPE,
        DO_RECURSIVE,
        DO_ARRAYS,
        DO_ITERATOR,
        DO_ITERABLE,
        DO_ENUMERATION,
        DO_PARENT_CLASSES,
        TRY_PRIVATE_METHODS,
        TRY_PROTECTED_METHODS
    };

    public static final EnumSet<Attributes> DEFAULT_ATTRIBUTES = EnumSet.of(
            Attributes.ALL_PRIMITIVE_TYPE,
            Attributes.DO_ARRAYS
            );
    public static final EnumSet<Attributes> SHOW_ALL = EnumSet.of(
            Attributes.ALL_PRIMITIVE_TYPE
            );

    private final Pattern methodesNamePattern;
    private final Set<Class<?>> returnTypeClasses;
    private final EnumSet<Attributes> attributesSet;
    private final String toStringNullValue;
    private final MessageFormat messageFormatIteratorEntry;
    private final MessageFormat messageFormatIterableEntry;
    private final MessageFormat messageFormatEnumerationEntry;
    private final MessageFormat messageFormatArrayEntry;
    private final MessageFormat messageFormatMethodName;

    public MappableHelper(MappableHelperFactory factory)
    {
        toStringNullValue = factory.getStringNullValue();
        messageFormatIteratorEntry = new MessageFormat(factory.getMessageFormatIteratorEntry());
        messageFormatIterableEntry = new MessageFormat(factory.getMessageFormatIterableEntry());
        messageFormatEnumerationEntry = new MessageFormat(factory.getMessageFormatEnumerationEntry());
        messageFormatArrayEntry = new MessageFormat(factory.getMessageFormatArrayEntry());
        messageFormatMethodName = new MessageFormat(factory.getMessageFormatMethodName());
        methodesNamePattern = factory.getMethodesNamePattern();
        returnTypeClasses = factory.getClasses();
        attributesSet = factory.getAttributes();
    }

//    @Deprecated
//    public MappableHelper(MappableHelperFactory factory, Pattern methodesNamePattern, Collection<Class<?>> returnTypeClasses, EnumSet<Attributes> attributesSet)
//    {
//        toStringNullValue = factory.getStringNullValue();
//        messageFormatIteratorEntry = new MessageFormat(factory.getMessageFormatIteratorEntry());
//        messageFormatIterableEntry = new MessageFormat(factory.getMessageFormatIterableEntry());
//        messageFormatEnumerationEntry = new MessageFormat(factory.getMessageFormatEnumerationEntry());
//        messageFormatArrayEntry = new MessageFormat(factory.getMessageFormatArrayEntry());
//        messageFormatMethodName = new MessageFormat(factory.getMessageFormatMethodName());
//
//        this.methodesNamePattern = methodesNamePattern;
//        this.returnTypeClasses = new HashSet<Class<?>>(returnTypeClasses);
//        this.attributesSet = attributesSet;
//    }

    public MappableHelper()
    {
        this(new MappableHelperFactory());
    }

//    @Deprecated
//    public MappableHelper(MappableHelperFactory factory, Pattern methodesNamePattern, Class<?>[] returnTypeClasses, EnumSet<Attributes> attributesSet)
//    {
//        this(factory.addClasses(returnTypeClasses).setMethodesNamePattern(methodesNamePattern).addAttributes(attributesSet));
//    }
//
//    @Deprecated
//    public MappableHelper(Pattern methodesNamePattern, Collection<Class<?>> returnTypeClasses, EnumSet<Attributes> attributesSet)
//    {
//        this((new MappableHelperFactory()).addClasses(returnTypeClasses).setMethodesNamePattern(methodesNamePattern).addAttributes(attributesSet));
//    }
//
//    @Deprecated
//    public MappableHelper(Pattern methodesNamePattern, Class<?> returnTypeClasses[], EnumSet<Attributes> attributesSet)
//    {
//        this((new MappableHelperFactory()).addClasses(returnTypeClasses).setMethodesNamePattern(methodesNamePattern).addAttributes(attributesSet));
//    }
//
//    @Deprecated
//    public MappableHelper(Pattern methodesNamePattern, Collection<Class<?>> returnTypeClasses)
//    {
//        this((new MappableHelperFactory()).addClasses(returnTypeClasses).setMethodesNamePattern(methodesNamePattern).addAttributes(DEFAULT_ATTRIBUTES));
//    }
//
//    @Deprecated
//    public MappableHelper(Pattern methodesNamePattern, Class<?> returnTypeClasses)
//    {
//        this((new MappableHelperFactory()).addClasses(returnTypeClasses).setMethodesNamePattern(methodesNamePattern).addAttributes(DEFAULT_ATTRIBUTES));
//    }

    public Map<String,String> toMap(Object object)
    {
        HashMap<String,String> hashMap = new HashMap<String,String>();
        Class<?> clazz = object.getClass();

        Method[] methods = getDeclaredMethods(clazz);

        int len$ = methods.length;

label0:
        for(int i$ = 0; i$ < len$; i$++) {
            Method method = methods[i$];
            if(method.getParameterTypes().length != 0 || !methodesNamePattern.matcher(method.getName()).matches())
            {
                continue;
            }

            Class<?> returnType = method.getReturnType();
            Object   result0;

            if(isMappable(returnType)) {
                if(returnType.isArray()) {
                    Mappable[] result1 = (Mappable[])invoke(object, method, hashMap, Mappable.class);
                    if(result1 == null) {
                        continue;
                    }
                    int len = Array.getLength(result1);

                    String methodName = method.getName();

                    int i = 0;
                    do {
                        if(i >= len) {
                            continue label0;
                        }

                        Mappable value = (Mappable)Array.get(result1, i);

                        String name = formatIterableEntry(methodName, i, len);
                        if(value == null) {
                            hashMap.put(name, null);

                        }
                        else {
                            MappableHelper.addRec(hashMap, name, value);
                        }
                        i++;
                    } while(true);
                }

                result0 = (Mappable)invoke(object, method, hashMap, Mappable.class);

                if(result0 != null) {
                    MappableHelper.addRec(
                            hashMap,
                            (new StringBuilder()).append(method.getName()).append("().").toString(),
                            ((Mappable) (result0) )
                            );
                }
                continue;
            }
            
            if(attributesSet.contains(Attributes.DO_ITERATOR) && Iterator.class.isAssignableFrom(returnType)) {
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

            if(attributesSet.contains(Attributes.DO_ITERABLE) && Iterable.class.isAssignableFrom(returnType)) {
                @SuppressWarnings("rawtypes")
                Iterable iterLst = (Iterable)invoke(object, method, hashMap, Iterable.class);
                @SuppressWarnings("rawtypes")
                Iterator iter = iterLst.iterator();

                String name = method.getName();

                int i = 0;
                hashMap.put(
                        formatMethodName(method.getName()),
                        (new StringBuilder()).append("").append(iter).toString()
                        );

                while( iter.hasNext() ) {
                    hashMap.put(
                            formatIterableEntry(name, i++, -1), 
                            toString(iter.next())
                            );
                }
                continue;

            }

            if(attributesSet.contains(Attributes.DO_ENUMERATION) && Enumeration.class.isAssignableFrom(returnType)) {
                Enumeration<?> enum0 = (Enumeration<?>)invoke(object, method, hashMap, Enumeration.class);
                String name = method.getName();

                int i = 0;
                hashMap.put(
                        formatMethodName(method.getName()),
                        (new StringBuilder()).append("").append(enum0).toString()
                        );

                for(; enum0.hasMoreElements(); hashMap.put(formatEnumerationEntry(name, i++, -1), toString(enum0.nextElement()))) { }
                continue;

            }

            if(!shouldEvaluate(returnType)) {
                continue;
            }
            Enumeration<?> enum0 = ((java.util.Enumeration<?>) (invoke(object, method, hashMap, Object.class)));

            if(enum0 == null) {
                continue;
            }
            if(returnType.isArray()) {
                int len =Array.getLength(enum0);

                String methodName = method.getName();

                for(int i = 0; i < len; i++) {
                    Object value = Array.get(enum0, i);
                    hashMap.put(
                            formatArrayEntry(methodName, i, len),
                            toString(value)
                            );

                }
            } 
            else {
                hashMap.put(
                        formatMethodName(method.getName()), 
                        enum0.toString()
                        );
            }
        }

        return new TreeMap<String,String>(hashMap);
    }

    protected String formatIterableEntry(String methodeName, int index, int max)
    {
        String params[] = {
            methodeName, Integer.toString(index), Integer.toString(max)
        };

        return messageFormatIterableEntry.format(params);
    }

    protected String formatIteratorEntry(String methodeName, int index, int max)
    {
        String[] params = {
            methodeName, 
            Integer.toString(index), 
            Integer.toString(max)
        };

        return messageFormatIteratorEntry.format(params);
    }

    protected String formatEnumerationEntry(String methodeName, int index, int max)
    {
        String[] params = {
            methodeName, 
            Integer.toString(index), 
            Integer.toString(max)
        };

        return messageFormatEnumerationEntry.format(params);

    }

    protected String formatArrayEntry(String methodeName, int index, int max)
    {
        String[] params = {
            methodeName, 
            Integer.toString(index), 
            Integer.toString(max)
        };

        return messageFormatArrayEntry.format(params);
    }

    protected String formatMethodName(String methodeName)
    {
        String[] params = {
            methodeName
        };

        return messageFormatMethodName.format(params);
    }

    private final Method[] getDeclaredMethods(Class<?> clazz)
    {
        if(!attributesSet.contains(Attributes.DO_PARENT_CLASSES)) {
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

    protected final boolean isMappable(Class<?> clazz)
    {
        if(!attributesSet.contains(Attributes.DO_RECURSIVE)) {
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

        if(Modifier.isPrivate(modifier) && !attributesSet.contains(Attributes.TRY_PRIVATE_METHODS)) {
            return false;
        }
        if(Modifier.isProtected(modifier) && !attributesSet.contains(Attributes.TRY_PROTECTED_METHODS)) {
            return false;
        }
        if(attributesSet.contains(Attributes.ALL_PRIMITIVE_TYPE) && returnType.isPrimitive()) {
            return true;
        }
        if(attributesSet.contains(Attributes.DO_ARRAYS) && returnType.isArray()) {
            return true;
        }

        for(Iterator<Class<?>> i$ = returnTypeClasses.iterator(); i$.hasNext();) {
            Class<?> c = i$.next();

            if(c.isAssignableFrom(returnType)) {
                return true;
            }
        }

        return false;
    }

    public String toString(Object object)
    {
        if(object == null) {
            return toStringNullValue;
        }
        else {
            return MappableHelper.toString(attributesSet, object);
        }
    }

    private static final void addRec(Map<String,String> hashMap, String methodName, Mappable object)
    {
        Set<Map.Entry<String,String>> set = object.toMap().entrySet();

        Map.Entry<String,String> entry;
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

    // TODO: Optimizations: remove some StringBuilder
    private static final String toString(EnumSet<Attributes> attributesSet, Object object)
    {
        if(object.getClass().isArray()) {
            StringBuilder sb = new StringBuilder();
            Object[] array = (Object[])object;
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
                sb.append( MappableHelper.toString(attributesSet, o) );
            }

            return (new StringBuilder())
                .append('[')
                .append(sb)
                .append(']')
                .toString();
        }

        if(attributesSet.contains(Attributes.DO_ITERATOR)) {
            if(object instanceof java.util.Iterator) {
                Iterator<?> iter = (Iterator<?>)object;
                StringBuilder sb = new StringBuilder();
                boolean first = true;

                for(; iter.hasNext(); sb.append(MappableHelper.toString(attributesSet, iter.next()))) {
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
        else if(attributesSet.contains(Attributes.DO_ENUMERATION) && (object instanceof java.util.Enumeration)) {
            Enumeration<?> enum0 = (Enumeration<?>)object;
            StringBuilder sb = new StringBuilder();
            boolean first = true;

            for(; enum0.hasMoreElements(); sb.append(cx.ath.choisnet.lang.reflect.MappableHelper.toString(attributesSet, enum0.nextElement()))) {
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

    protected final Object invoke(Object object, Method method, Map<String,String> hashMap, Class<?> resultClass)
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

    public static Map<String,String> toMap(MappableHelperFactory factory, Object object)
    {
        return new MappableHelper(factory).toMap(object);
    }

//    @Deprecated
//    public static Map<String,String> toMap(Object object, Pattern methodesNamePattern, Class<?> returnTypeClasses[], EnumSet<Attributes> attributesSet)
//    {
//        MappableHelper instance = new MappableHelper(new MappableHelperFactory(), methodesNamePattern, Arrays.asList(returnTypeClasses), attributesSet);
//
//        return instance.toMap(object);
//    }

    public static void toXML(Appendable out, Class<?> clazz, Map<String,String> map)
        throws java.io.IOException
    {
        if(map == null) {
            out.append((new StringBuilder()).append("<class name=\"").append(clazz.getName()).append("\" /><!-- NULL OBJECT -->\n").toString());
        }
        else if(map.size() == 0) {
            out.append((new StringBuilder()).append("<class name=\"").append(clazz.getName()).append("\" /><!-- EMPTY -->\n").toString());
        }
        else {
            out.append((new StringBuilder()).append("<class name=\"").append(clazz.getName()).append("\">\n").toString());
            String name;
            for(
                    Iterator<String> i$ = map.keySet().iterator();
                    i$.hasNext();
                    out.append((new StringBuilder()).append("  <value name=\"").append(name).append("\">").append(map.get(name)).append("</value>\n").toString())
                    ) {
                name = i$.next();
            }

            out.append("</class>\n");
        }
    }

    public static void toXML(Appendable out, Class<?> clazz, Mappable aMappableObject)
        throws java.io.IOException
    {
        MappableHelper.toXML(out, clazz, aMappableObject != null ? aMappableObject.toMap() : null);
    }

    public static void toXML(Appendable out, Mappable aMappableObject)
        throws java.io.IOException
    {
        MappableHelper.toXML(out, aMappableObject.getClass(), aMappableObject);
    }

    public static String toXML(Class<?> clazz, Mappable aMappableObject)
    {
        StringBuilder sb = new StringBuilder();

        try {
            MappableHelper.toXML( sb, clazz, aMappableObject);
        }
        catch(java.io.IOException improbable) {
            throw new RuntimeException(improbable);
        }

        return sb.toString();
    }

    public static String toXML(Mappable aMappableObject)
    {
        return MappableHelper.toXML(aMappableObject.getClass(), aMappableObject);
    }
}
