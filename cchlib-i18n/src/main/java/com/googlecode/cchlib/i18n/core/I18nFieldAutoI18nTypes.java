package com.googlecode.cchlib.i18n.core;

import java.lang.reflect.Field;
import com.googlecode.cchlib.i18n.AutoI18nType;
import com.googlecode.cchlib.i18n.I18nInterface;
import com.googlecode.cchlib.i18n.core.resolve.GetFieldException;
import com.googlecode.cchlib.i18n.core.resolve.I18nKeyFactory;
import com.googlecode.cchlib.i18n.core.resolve.I18nResolvedFieldGetter;
import com.googlecode.cchlib.i18n.core.resolve.I18nResolvedFieldSetter;
import com.googlecode.cchlib.i18n.core.resolve.I18nResolver;
import com.googlecode.cchlib.i18n.core.resolve.Keys;
import com.googlecode.cchlib.i18n.core.resolve.MissingKeyException;
import com.googlecode.cchlib.i18n.core.resolve.SetFieldException;
import com.googlecode.cchlib.i18n.core.resolve.Values;

final /* not public */ class I18nFieldAutoI18nTypes extends AbstractI18nField
{
    private static final long serialVersionUID = 1L;

    public I18nFieldAutoI18nTypes(
            final I18nDelegator     i18nDelegator,
            final I18nKeyFactory    i18nKeyFactory,
            final Field             field,
            final String            keyIdValue,
            final AutoI18nType      autoI18nType
            )
    {
        super( i18nDelegator, i18nKeyFactory, field, keyIdValue, null, autoI18nType );
        
        assert autoI18nType != null : "Parameter autoI18nTypes must not be null";
   }

    /*@Override
    public <T> String[] getDefaultValues( T objectToI18n ) throws I18nFieldDefaultValueException
    {
        try {
            Field  f           = getField();
            Object fieldObject = f.get( objectToI18n );
            
            //return getI18nDelegator().getAutoI18nTypes().getText( f, fieldObject );
            return getAutoI18nTypes().getText( fieldObject );
            }
        catch( IllegalArgumentException e ) {
            throw new I18nFieldDefaultValueException( e );
            }
        catch( IllegalAccessException e ) {
            throw new I18nFieldDefaultValueException( e );
            }
    }*/

    @Override
    public FieldType getFieldType()
    {
        return FieldType.SIMPLE_KEY;
    }

    @Override
    public <T> I18nResolver createI18nResolver( final T objectToI18n, I18nInterface i18nInterface )
    {
        return new I18nResolver() {
            @Override
            public Keys getKeys() throws MissingKeyException
            {
                try {
                   Field  f = getField();
                   f.setAccessible( true ); // FIXME: try to restore
                   Object fieldObject = f.get( objectToI18n );
                   
                   return getAutoI18nTypes().getKeys( fieldObject, getKeyBase() );
                    }
                catch( IllegalArgumentException e ) {
                    throw new MissingKeyException( e );
                     }
                catch( IllegalAccessException e ) {
                    throw new MissingKeyException( e );
                     }
           }

            @Override
            public I18nResolvedFieldGetter getI18nResolvedFieldGetter()
            {
                return new I18nResolvedFieldGetter() {
                    @Override
                    public Values getValues( Keys keys ) throws GetFieldException
                    {
                        try {
                            Field  f = getField();
                            f.setAccessible( true ); // FIXME: try to restore
                             Object fieldObject = f.get( objectToI18n );
                            
//                            String[] value = getAutoI18nTypes().getText( fieldObject );
//                            
//                            return new IndexValues( value );
                            return getAutoI18nTypes().getText( fieldObject );
                            }
                        catch( IllegalArgumentException e ) {
                            throw new GetFieldException( e );
                            }
                        catch( IllegalAccessException e ) {
                            throw new GetFieldException( e );
                            }
                    }
                };
            }

            @Override
            public I18nResolvedFieldSetter getI18nResolvedFieldSetter()
            {
                return new I18nResolvedFieldSetter() {
                    @Override
                    public void setValues( Keys keys, Values values )
                            throws SetFieldException
                    {
                        try {
                            Field  f = getField();
                            f.setAccessible( true ); // FIXME: try to restore
                            Object fieldObject = f.get( objectToI18n );

                            getAutoI18nTypes().setText( fieldObject, values );
                            }
                        catch( IllegalArgumentException e ) {
                            throw new SetFieldException( e );
                            }
                        catch( IllegalAccessException e ) {
                            throw new SetFieldException( e );
                            }
                    }
                };
            }
        };
    }
}
