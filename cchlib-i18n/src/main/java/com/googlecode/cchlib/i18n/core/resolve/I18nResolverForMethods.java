package com.googlecode.cchlib.i18n.core.resolve;
//
//import java.lang.reflect.InvocationTargetException;
//import java.lang.reflect.Method;
//import com.googlecode.cchlib.i18n.core.I18nField;
//
//public class I18nResolverForMethods<T> implements I18nResolver
//{
//    private final I18nField i18nField;
//    private final T objectToI18n;
//
//    public I18nResolverForMethods( I18nField i18nField, T objectToI18n )
//    {
//        this.i18nField    = i18nField;
//        this.objectToI18n = objectToI18n;
//    }
//
//    @Override
//    public Keys getKeys()
//    {
//        return new UniqKeys( this.i18nField.getKeyBase() );
//    }
//
//    @Override
//    public I18nResolvedFieldGetter getI18nResolvedFieldGetter()
//    {
//        return null;
////        return new I18nResolvedFieldGetter() {
////            @Override
////            public Values getValues( Keys keys ) throws GetFieldException
////            {
////                try {
////                    Method getter = I18nResolverForMethods.this.i18nField.getMethods().getInvokeMethod();
////                    Object r      = getter.invoke( objectToI18n, new Object[0] );
////
////                    return new IndexValues( (String)r );
////                    }
////                catch( SecurityException e ) {
////                    throw new GetFieldException( e );
////                    }
////                catch( NoSuchMethodException e ) {
////                    throw new GetFieldException( e );
////                    }
////                catch( IllegalArgumentException e ) {
////                    throw new GetFieldException( e );
////                    }
////                catch( IllegalAccessException e ) {
////                    throw new GetFieldException( e );
////                    }
////                catch( InvocationTargetException e ) {
////                    throw new GetFieldException( e );
////                    }
////            }
////        };
//    }
//
//    @Override
//    public I18nResolvedFieldSetter getI18nResolvedFieldSetter()
//    {
//        return null;
////        return new I18nResolvedFieldSetter(){
////
////            @Override
////            public void setValues( Keys keys, Values values )
////                    throws SetFieldException
////            {
////                assert values.size() == 1;
////
////                try {
////                    Method setter = I18nResolverForMethods.this.i18nField.getMethods().getSetter();
////                    setter.invoke( objectToI18n, new Object[] { values.getFirstValue() } );
////                    }
////                catch( SecurityException e ) {
////                    throw new SetFieldException( e );
////                    }
////                catch( NoSuchMethodException e ) {
////                    throw new SetFieldException( e );
////                    }
////                catch( IllegalArgumentException e ) {
////                    throw new SetFieldException( e );
////                    }
////                catch( IllegalAccessException e ) {
////                    throw new SetFieldException( e );
////                    }
////                catch( InvocationTargetException e ) {
////                    throw new SetFieldException( e );
////                    }
////            }};
//    }
//}