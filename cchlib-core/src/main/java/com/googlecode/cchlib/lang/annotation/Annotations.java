package com.googlecode.cchlib.lang.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import javax.annotation.Nullable;
import org.apache.log4j.Logger;

/**
 * Tools for {@link Annotation}
 *
 * @since 4.2
 */
public class Annotations
{
    private static final Logger LOGGER = Logger.getLogger( Annotations.class );

    private Annotations()
    {
        // All static
    }

    @SuppressWarnings("null")
    @Nullable
    private static <T extends Annotation> T notFound()
    {
        return null;
    }

    /**
     * Find for {@link Annotation} on current {@link Method} but also on all interfaces
     * and on all super classes (order is define by {@code order} parameter).
     * Return first corresponding {@link Annotation} for this method.
     *
     * @param <T>
     *            The annotation type
     * @param method
     *            The method
     * @param annotationType
     *            The Class object corresponding to the annotation type
     * @param annotationLookup
     *            The function able to find annotation.
     * @return this element's annotation for the specified annotation type if present
     *         on this element or it's parents, else null
     */
    @Nullable
    public static <T extends Annotation> T findAnnotation(
        final Method           method,
        final Class<T>         annotationType,
        final AnnotationLookup annotationLookup
        )
    {
        final T annotation = method.getAnnotation( annotationType );

        if( annotation != null ) {
            return annotation;
        }

        final Class<?>   declaringClass = method.getDeclaringClass();
        final String     methodName     = method.getName();
        final Class<?>[] parameterTypes = method.getParameterTypes();

        return annotationLookup.findAnnotation(
                    annotationType,
                    declaringClass,
                    methodName,
                    parameterTypes
                    );
    }

    /* private */
    static <T extends Annotation> T findAnnotationOnInterfacesFirst(
        final Class<T>   annotationType,
        final Class<?>   declaringClass,
        final String     methodName,
        final Class<?>[] parameterTypes
        )
    {
        // Find annotation on interfaces
        T annotation = findAnnotationOnInterfaces( annotationType, declaringClass, methodName, parameterTypes );

        if( annotation == null ) {
            // Find annotation on super classes
            annotation = findAnnotationOnSuperclass( annotationType, declaringClass, methodName, parameterTypes );
        }

        return annotation;
    }

    /* private */
    static <T extends Annotation> T findAnnotationOnSuperclassFirst(
        final Class<T>   annotationType,
        final Class<?>   declaringClass,
        final String     methodName,
        final Class<?>[] parameterTypes
        )
    {
        // Find annotation on super classes
        T annotation = findAnnotationOnSuperclass( annotationType, declaringClass, methodName, parameterTypes );

        if( annotation == null ) {
            // Find annotation on interfaces
            annotation = findAnnotationOnInterfaces( annotationType, declaringClass, methodName, parameterTypes );
        }

        return annotation;
    }

    // Could be public (warn, did not check current class)
    private static <T extends Annotation> T findAnnotationOnSuperclass(
        final Class<T>   annotationType,
        final Class<?>   declaringClass,
        final String     methodName,
        final Class<?>[] parameterTypes
        )
    {
        final Class<?> superclass = declaringClass.getSuperclass();

        if( superclass != null ) {
            final T annotation =  findAnnotationOnSuperclassRec( annotationType, superclass, methodName, parameterTypes );

            if( annotation != null ) {
                return annotation;
            }
        }

        return notFound();
    }

    // Could be public
    private static <T extends Annotation> T findAnnotationOnInterfaces(
        final Class<T>   annotationType,
        final Class<?>   declaringClass,
        final String     methodName,
        final Class<?>[] parameterTypes
        )
    {
        final Class<?>[] interfaces = declaringClass.getInterfaces();

        for( final Class<?> type : interfaces ) {
            final T annotation = findAnnotationOnSuperclassRec( annotationType, type, methodName, parameterTypes );

            if( annotation != null ) {
                return annotation;
            }
        }

        return notFound();
    }

    /* private */
    @SuppressWarnings("squid:RedundantThrowsDeclarationCheck")
    @Nullable
    private static <T extends Annotation> T findAnnotationOnSuperclassRec(
        final Class<T>   annotationType,
        final Class<?>   declaringClass,
        final String     methodName,
        final Class<?>[] parameterTypes
       )
    {
        final Method method = findMethod( declaringClass, methodName, parameterTypes );

        if( method != null ) {
            final T annotation = method.getAnnotation( annotationType );

            if( annotation != null ) {
                return annotation;
            }
        }

        final Class<?> superclass = declaringClass.getSuperclass();

        if( superclass != null ) {
            return findAnnotationOnSuperclassRec( annotationType, superclass, methodName, parameterTypes );
        }

        return notFound();
    }

    /* private */
    private static Method findMethod(
        final Class<?>   declaringClass,
        final String     methodName,
        final Class<?>[] parameterTypes
        )
    {
        try {
            return declaringClass.getMethod( methodName, parameterTypes );
        }
        catch( NoSuchMethodException | SecurityException cause ) {
            if( LOGGER.isDebugEnabled() ) {
                final String message = "Can not find method "
                        + methodName + '(' + Arrays.toString( parameterTypes )
                        + " ) on " + declaringClass.getName();
                if( LOGGER.isTraceEnabled() ) {
                    LOGGER.trace( message, cause );
                } else {
                    LOGGER.debug( message );
                }
            }
            return null;
        }
    }
}
