package com.googlecode.cchlib.i18n.core.internal;

import java.lang.reflect.Field;
import javax.swing.JTabbedPane;
import com.googlecode.cchlib.i18n.I18nSyntaxException;
import com.googlecode.cchlib.i18n.api.I18nResource;
import com.googlecode.cchlib.i18n.core.I18nField;
import com.googlecode.cchlib.i18n.core.I18nFieldType;
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
import com.googlecode.cchlib.i18n.core.resolve.Values;
import com.googlecode.cchlib.lang.reflect.AccessibleRestorer;

//NOT public
final class I18nFieldToolTipTextForJTabbedPane
    extends AbstractI18nField
        implements I18nField
{
    private final class TollTipI18nResolver<T> implements I18nResolver
    {
        private final T objectToI18n;

        private TollTipI18nResolver( final T objectToI18n )
        {
            this.objectToI18n = objectToI18n;
        }

        @Override
        public Keys getKeys()
        {
            try {
                final JTabbedPane c = getComponent( this.objectToI18n );

                return new IndexKeys( getKeyBase(), c.getTabCount() );
                }
            catch( final IllegalArgumentException e ) {
                throw new KeyException( getMessage( this.objectToI18n ), e );
                }
            catch( final IllegalAccessException e ) {
                throw new KeyException( e );
                }
        }

        private String getMessage( final T objectToI18n  )
        {
            return "objectToI18n is: " + objectToI18n;
        }

        @Override
        public I18nResolvedFieldGetter getI18nResolvedFieldGetter()
        {
            return this::getI18nResolvedFieldGetter;
        }

        @SuppressWarnings("squid:S3346") // assert usage
        private Values getI18nResolvedFieldGetter( final Keys keys ) throws GetFieldException
        {
            try {
                final JTabbedPane c = getComponent( this.objectToI18n );

                assert keys.size() == c.getTabCount();

                final String[] tips = new String[ c.getTabCount() ];

                for( int i = 0; i<tips.length; i++ ) {
                    tips[ i ] = c.getToolTipTextAt( i );
                    }

                return new IndexValues( tips );
                }
            catch( final IllegalArgumentException e ) {
                throw new GetFieldException( getMessage( this.objectToI18n ), e );
                }
            catch( final IllegalAccessException e ) {
                throw new GetFieldException( e );
                }
        }

        @Override
        public I18nResolvedFieldSetter getI18nResolvedFieldSetter()
        {
            return this::getI18nResolvedFieldSetter;
        }

        @SuppressWarnings("squid:S3346") // assert usage
        private void getI18nResolvedFieldSetter( final Keys keys, final Values values )
            throws SetFieldException
        {
            assert keys.size() == values.size();

            try {
                final JTabbedPane c = getComponent( this.objectToI18n );

                assert keys.size() == c.getTabCount();

                for( int i = 0; i<c.getTabCount(); i++ ) {
                    c.setToolTipTextAt( i, values.get( i ) );
                    }
                }
            catch( final IllegalArgumentException e ) {
                throw new SetFieldException( getMessage( this.objectToI18n ), e );
                }
            catch( final IllegalAccessException e ) {
                throw new SetFieldException( e );
                }
        }

        @SuppressWarnings("squid:RedundantThrowsDeclarationCheck")
        private final JTabbedPane getComponent( final T objectToI18n )
            throws IllegalArgumentException, IllegalAccessException
        {
            final Field              field      = getField();
            final AccessibleRestorer accessible = new AccessibleRestorer( field );

            try {
                final Object value = field.get( objectToI18n );

                return (JTabbedPane)value;
            } finally {
                accessible.restore();
            }
        }
    }

    private static final long serialVersionUID = 1L;

    public I18nFieldToolTipTextForJTabbedPane(
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
    public I18nFieldType getFieldType()
    {
        return I18nFieldType.JCOMPONENT_MULTI_TOOLTIPTEXT;
    }

    @Override
    public <T> I18nResolver createI18nResolver(
            final T            objectToI18n,
            final I18nResource i18nResource // TODO investigate why not use ?
            )
    {
        return new TollTipI18nResolver<T>( objectToI18n );
    }
}
