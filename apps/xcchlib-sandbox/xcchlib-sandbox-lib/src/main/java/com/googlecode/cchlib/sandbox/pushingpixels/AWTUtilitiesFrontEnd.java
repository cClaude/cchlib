package com.googlecode.cchlib.sandbox.pushingpixels;

import java.awt.GraphicsConfiguration;
import java.awt.Shape;
import java.awt.Window;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Deprecated
public class AWTUtilitiesFrontEnd
{
    public enum Translucency {
        PERPIXEL_TRANSPARENT,
        TRANSLUCENT,
        PERPIXEL_TRANSLUCENT;

        private Enum<?> targetValue;

        private Translucency()
        {
            final Enum<?>[] targetValues = (Enum<?>[])getType().getEnumConstants();

            for( final Enum<?> targetValue : targetValues ) {
                if( targetValue.name() == this.name() ) {
                    this.targetValue = targetValue;
                    break;
                }

            assert this.targetValue != null;
            }
        }

        public Class<?> getType()
        {
            return getAWTUtilitiesTranslucencyClass();
        }

        public Enum<?> getValue()
        {
            return targetValue;
        }
    }

    private static final String COM_SUN_AWT_AWT_UTILITIES = "com.sun.awt.AWTUtilities";
    private static final String COM_SUN_AWT_AWT_UTILITIES_TRANSLUCENCY = "com.sun.awt.AWTUtilities.Translucency";

    private static Class<?> classOfAWTUtilities;
    private static Class<?> classOfAWTUtilitiesTranslucenc;
        private static Class<?> getAWTUtilitiesClass()
        {
            if( classOfAWTUtilities == null ) {
                getClass( COM_SUN_AWT_AWT_UTILITIES );
            }
            return classOfAWTUtilities;
        }

        private static Class<?> getAWTUtilitiesTranslucencyClass()
        {
            if( classOfAWTUtilitiesTranslucenc == null ) {
                getClass( COM_SUN_AWT_AWT_UTILITIES_TRANSLUCENCY );
            }
            return classOfAWTUtilitiesTranslucenc;
        }

    private static void getClass( final String className )
    {
        try {
            classOfAWTUtilities = Class.forName( className );
        }
        catch( final ClassNotFoundException e ) {
            throw new RuntimeException( className, e );
        }
    }

    private static Object invokeAWTUtilitiesClass(
            final String     methodName,
            final Class<?>[] parameterTypes,
            final Object[]   parameterValues
            )
    {
        final Class<?> clazz = getAWTUtilitiesClass();
        try {
            final Method method = clazz.getMethod( methodName, parameterTypes );
            try {
                return method.invoke( null, parameterValues );
            }
            catch( IllegalAccessException | IllegalArgumentException | InvocationTargetException e ) {
                throw new RuntimeException( "invoke on " + method, e );
            }
        }
        catch( NoSuchMethodException | SecurityException e ) {
            throw new RuntimeException( "methodName " + methodName, e );
        }
    }

    public static boolean isTranslucencySupported( final Translucency translucency )
    {
        //return com.sun.awt.AWTUtilities.isTranslucencySupported( convertTranslucency( translucency ) );
        final Boolean result = (Boolean)invokeAWTUtilitiesClass(
                "isTranslucencySupported",
                new Class<?>[]{translucency.getType()},
                new Object[]{translucency.getValue()}
                );
        return result.booleanValue();
    }

//    private static com.sun.awt.AWTUtilities.Translucency convertTranslucency(
//        final Translucency translucency
//        )
//    {
//        switch( translucency ) {
//            case PERPIXEL_TRANSPARENT:
//                return com.sun.awt.AWTUtilities.Translucency.PERPIXEL_TRANSPARENT;
//
//            case TRANSLUCENT:
//                return com.sun.awt.AWTUtilities.Translucency.TRANSLUCENT;
//
//            case PERPIXEL_TRANSLUCENT:
//                return com.sun.awt.AWTUtilities.Translucency.PERPIXEL_TRANSLUCENT;
//            }
//        return null;
//    }

    public static boolean isTranslucencyCapable( final GraphicsConfiguration graphicsConfiguration )
    {
        //return com.sun.awt.AWTUtilities.isTranslucencyCapable( graphicsConfiguration );
        final Boolean result = (Boolean)invokeAWTUtilitiesClass(
                "isTranslucencyCapable",
                new Class<?>[]{GraphicsConfiguration.class},
                new Object[]{graphicsConfiguration}
                );
        return result.booleanValue();
    }

    public static void setWindowShape( final Window window, final Shape shape )
    {
        //com.sun.awt.AWTUtilities.setWindowShape( window, shape );
        invokeAWTUtilitiesClass(
                "setWindowShape",
                new Class<?>[]{Window.class,Shape.class},
                new Object[]{window,shape}
                );
     }

    public static void setWindowOpaque( final Window window, final boolean b )
    {
        //com.sun.awt.AWTUtilities.setWindowOpaque( window, b );
        invokeAWTUtilitiesClass(
                "setWindowOpaque",
                new Class<?>[]{Window.class,boolean.class},
                new Object[]{window,Boolean.valueOf( b ) }
                );
    }

    public static void setWindowOpacity( final Window window, final float f )
    {
        //com.sun.awt.AWTUtilities.setWindowOpacity( window, f );
        invokeAWTUtilitiesClass(
                "setWindowOpacity",
                new Class<?>[]{Window.class,float.class},
                new Object[]{window,Float.valueOf( f )}
                );
    }

}
