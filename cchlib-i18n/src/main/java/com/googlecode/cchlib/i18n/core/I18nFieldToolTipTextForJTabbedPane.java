package com.googlecode.cchlib.i18n.core;

import java.lang.reflect.Field;
import javax.swing.JTabbedPane;
import com.googlecode.cchlib.i18n.I18nInterface;
import com.googlecode.cchlib.i18n.I18nSyntaxeException;
import com.googlecode.cchlib.i18n.core.resolve.GetFieldException;
import com.googlecode.cchlib.i18n.core.resolve.I18nKeyFactory;
import com.googlecode.cchlib.i18n.core.resolve.I18nResolvedFieldGetter;
import com.googlecode.cchlib.i18n.core.resolve.I18nResolvedFieldSetter;
import com.googlecode.cchlib.i18n.core.resolve.I18nResolver;
import com.googlecode.cchlib.i18n.core.resolve.IndexKeys;
import com.googlecode.cchlib.i18n.core.resolve.IndexValues;
import com.googlecode.cchlib.i18n.core.resolve.KeyException;
import com.googlecode.cchlib.i18n.core.resolve.Keys;
import com.googlecode.cchlib.i18n.core.resolve.SetFieldException;

//NOT public
final class I18nFieldToolTipTextForJTabbedPane
    extends AbstractI18nField
        implements I18nField
{
    private static final long serialVersionUID = 1L;

    public I18nFieldToolTipTextForJTabbedPane(
        final I18nDelegator  i18nDelegator,
        final I18nKeyFactory i18nKeyFactory,
        final Field  field,
        final String keyIdValue
        ) throws I18nSyntaxeException
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
        return FieldType.JCOMPONENT_MULTI_TOOLTIPTEXT;
    }

    @Override
    public <T> I18nResolver createI18nResolver( final T objectToI18n, final I18nInterface i18nInterface_ )
    {
        return new I18nResolver() {
            @Override
            public Keys getKeys()
            {
                try {
                    final JTabbedPane c = getComponent( objectToI18n );

                    return new IndexKeys( getKeyBase(), c.getTabCount() );
                    }
                catch( final IllegalArgumentException e ) {
                    throw new KeyException( "objectToI18n is: " + objectToI18n, e );
                    }
                catch( final IllegalAccessException e ) {
                    throw new KeyException( e );
                    }
            }

            @Override
            public I18nResolvedFieldGetter getI18nResolvedFieldGetter()
            {
                return keys -> {
                    try {
                        final JTabbedPane c = getComponent( objectToI18n );

                        assert keys.size() == c.getTabCount();

                        final String[] tips = new String[ c.getTabCount() ];

                        for( int i = 0; i<tips.length; i++ ) {
                            tips[ i ] = c.getToolTipTextAt( i );
                            }

//if( objectToI18n != null ) {throw new NullPointerException();}

                        return new IndexValues( tips );
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

                    try {
                        final JTabbedPane c = getComponent( objectToI18n );

                        assert keys.size() == c.getTabCount();

                        for( int i = 0; i<c.getTabCount(); i++ ) {
                            c.setToolTipTextAt( i, values.get( i ) );
                            }
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

    private final <T> JTabbedPane getComponent( final T objectToI18n )
        throws IllegalArgumentException, IllegalAccessException
    {
        final Field f = getField();
        f.setAccessible( true ); // FIXME: try to restore ! (need to handle concurrent access) !
        final Object      v = f.get( objectToI18n );

        return (JTabbedPane)v;
    }
}
