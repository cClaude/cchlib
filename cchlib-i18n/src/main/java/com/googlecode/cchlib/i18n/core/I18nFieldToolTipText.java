package com.googlecode.cchlib.i18n.core;

import java.lang.reflect.Field;
import javax.swing.JComponent;
import com.googlecode.cchlib.i18n.I18nInterface;
import com.googlecode.cchlib.i18n.I18nSyntaxException;
import com.googlecode.cchlib.i18n.core.resolve.GetFieldException;
import com.googlecode.cchlib.i18n.core.resolve.I18nKeyFactory;
import com.googlecode.cchlib.i18n.core.resolve.I18nResolvedFieldGetter;
import com.googlecode.cchlib.i18n.core.resolve.I18nResolvedFieldSetter;
import com.googlecode.cchlib.i18n.core.resolve.I18nResolver;
import com.googlecode.cchlib.i18n.core.resolve.IndexValues;
import com.googlecode.cchlib.i18n.core.resolve.Keys;
import com.googlecode.cchlib.i18n.core.resolve.SetFieldException;
import com.googlecode.cchlib.i18n.core.resolve.UniqKeys;

//NOT public
final class I18nFieldToolTipText extends AbstractI18nField
{
    private static final long serialVersionUID = 1L;

    public I18nFieldToolTipText(
        final I18nDelegator  i18nDelegator,
        final I18nKeyFactory i18nKeyFactory,
        final Field          field,
        final String         keyIdValue
        ) throws I18nSyntaxException
    {
        super( i18nDelegator, i18nKeyFactory, field, keyIdValue, null, null );
    }

    @Override
    public String getKeyBase()
    {
        return super.getKeyBase() + ".ToolTipText";
    }

    @Override
    public FieldType getFieldType()
    {
        return FieldType.JCOMPONENT_TOOLTIPTEXT;
    }

    @Override
    public <T> I18nResolver createI18nResolver( final T objectToI18n, final I18nInterface i18nInterface )
    {
        return new I18nResolver() {
            @Override
            public Keys getKeys()
            {
                return new UniqKeys( getKeyBase() );
            }

            @Override
            public I18nResolvedFieldGetter getI18nResolvedFieldGetter()
            {
                return keys -> {
                    assert keys.size() == 1;

                    try {
                        final JComponent c = getComponent( objectToI18n );

                        return new IndexValues( c.getToolTipText() );
                        }
                    catch( final IllegalArgumentException e ) {
                        throw new GetFieldException( "objectToI18n is: " + objectToI18n, e );
                        }
                    catch( final IllegalAccessException e ) {
                        throw new GetFieldException( e );
                        }
                };
            }

            @Override
            public I18nResolvedFieldSetter getI18nResolvedFieldSetter()
            {
                return ( keys, values ) -> {
                    assert keys.size() == values.size();
                    assert keys.size() == 1;

                    try {
                        final JComponent c = getComponent( objectToI18n );

                        c.setToolTipText( values.get( 0 ) );
                        }
                    catch( final IllegalArgumentException e ) {
                        throw new SetFieldException( "objectToI18n is: " + objectToI18n, e );
                        }
                    catch( final IllegalAccessException e ) {
                        throw new SetFieldException( e );
                        }
                };
            }
        };
    }

    private <T> JComponent getComponent( final T objectToI18n )
        throws IllegalArgumentException, IllegalAccessException
    {
        final Field f = getField();
        f.setAccessible( true ); // FIXME: try to restore ! (need to handle concurrent access) !
        final Object     v = f.get( objectToI18n );

        return (JComponent)v;
    }
}
