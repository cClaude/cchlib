package com.googlecode.cchlib.lang.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import javax.annotation.Nullable;
import org.apache.log4j.Logger;

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
     * and then on all super classes in that order. Return first corresponding
     * {@link Annotation} for this method.
     *
     * @param <T>
     *            The annotation type
     * @param method
     *            The method
     * @param annotationClass
     *            the Class object corresponding to the annotation type
     * @return this element's annotation for the specified annotation type if present
     *         on this element or it's parents, else null
     */
    @Nullable
    public static <T extends Annotation> T getAnnotation(
        final Method   method,
        final Class<T> annotationClass
        )
    {
        T annotation = method.getAnnotation( annotationClass );

        if( annotation != null ) {
            return annotation;
        }

        final Class<?>   declaringClass = method.getDeclaringClass();
        final String     methodName     = method.getName();
        final Class<?>[] parameterTypes = method.getParameterTypes();
        final Class<?>[] interfaces     = declaringClass.getInterfaces();

        // Find annotation on interfaces
        for( final Class<?> type : interfaces ) {
            annotation = findAnnotation( annotationClass, type, methodName, parameterTypes );

            if( annotation != null ) {
                return annotation;
            }
        }

        // Find annotation on super classes
        final Class<?> superclass = declaringClass.getSuperclass();

        if( superclass != null ) {
            return findAnnotation( annotationClass, superclass, methodName, parameterTypes );
        }

        return notFound();
    }

    /**
     *
     * @param <T>
     *            The annotation type
     * @param annotationClass
     *            the Class object corresponding to the annotation type
     * @param declaringClass The method declaring class
     * @param methodName The method name
     * @param parameterTypes  the list of parameters
     * @return this element's annotation for the specified annotation type if present
     *         on this element or it's parents classes, else null
     */
    @SuppressWarnings("squid:RedundantThrowsDeclarationCheck")
    @Nullable
    private static <T extends Annotation> T findAnnotation(
        final Class<T>   annotationClass,
        final Class<?>   declaringClass,
        final String     methodName,
        final Class<?>[] parameterTypes
       )
    {
        final Method method = findMethod( declaringClass, methodName, parameterTypes );

        if( method != null ) {
            final T annotation = method.getAnnotation( annotationClass );

            if( annotation != null ) {
                return annotation;
            }
        }

        final Class<?> superclass = declaringClass.getSuperclass();

        if( superclass != null ) {
            return findAnnotation( annotationClass, superclass, methodName, parameterTypes );
        }

        return notFound();
    }

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
