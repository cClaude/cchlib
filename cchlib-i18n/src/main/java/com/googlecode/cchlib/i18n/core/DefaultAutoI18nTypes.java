package com.googlecode.cchlib.i18n.core;

import javax.swing.AbstractButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;
import javax.swing.border.TitledBorder;
import com.googlecode.cchlib.i18n.AutoI18nBasicInterface;
import com.googlecode.cchlib.i18n.core.resolve.IndexKeys;
import com.googlecode.cchlib.i18n.core.resolve.IndexValues;
import com.googlecode.cchlib.i18n.core.resolve.Keys;
import com.googlecode.cchlib.i18n.core.resolve.Values;
import com.googlecode.cchlib.i18n.types.AbstractType;
import com.googlecode.cchlib.i18n.types.AbstractTypeUniqKeys;

/**
 * TODOC
 *
 */
/*not public*/ class DefaultAutoI18nTypes 
    extends AbstractAutoI18nTypes
{
    private static final long serialVersionUID = 1L;

    /**
     * Build {@link DefaultAutoI18nTypes} with the
     * default list of supported classes.
     * <p>private constructor</p>
     */
    public DefaultAutoI18nTypes()
    {
        super();
    }

    public AbstractType<AutoI18nBasicInterface> getAutoI18nBasicInterface()
    {
        return new AbstractTypeUniqKeys<AutoI18nBasicInterface>( AutoI18nBasicInterface.class ) {
            private static final long serialVersionUID = 1L;
            @Override
            public void setText( Object toI18n, Values values )
            {
                assert values.size() == 1;

                cast( toI18n ).setI18nString( values.get( 0 ) );
            }
            @Override
            public Values getText( Object toI18n )
            {
                return new IndexValues( cast( toI18n ).getI18nString() );
            }
        };
    }

    /**
     * Handle I18h for {@link JLabel}
     *
     * @return an {@link AbstractType} to handle {@link JLabel}
     */
    public AbstractType<JLabel> getJLabel()
    {
        return new AbstractTypeUniqKeys<JLabel>(JLabel.class)
        {
            private static final long serialVersionUID = 1L;
            @Override
            public void setText( Object toI18n, Values values )
            {
                cast( toI18n ).setText( values.get( 0 ) );
            }
            @Override
            public Values getText( Object toI18n )
            {
                return new IndexValues( cast( toI18n ).getText() );
            }
        };
    }

    /**
     * Handle I18h for {@link AbstractButton}
     *
     * @return an {@link AbstractType} to handle {@link AbstractButton}
     */
    public AbstractType<AbstractButton> getAbstractButton()
    {
        return new AbstractTypeUniqKeys<AbstractButton>(AbstractButton.class)
        {
            private static final long serialVersionUID = 1L;
            @Override
            public void setText( Object toI18n, Values values )
            {
                assert values.size() == 1;

                cast( toI18n ).setText( values.get( 0 ) );
            }
            @Override
            public Values getText( Object toI18n )
            {
                return new IndexValues( cast( toI18n ).getText() );
            }
        };
    }

    /**
     * Handle I18h for {@link JCheckBox}
     *
     * @return an {@link AbstractType} to handle {@link JCheckBox}
     */
    public AbstractType<JCheckBox> getJCheckBox()
    {
        return new AbstractTypeUniqKeys<JCheckBox>(JCheckBox.class)
        {
            private static final long serialVersionUID = 1L;
            @Override
            public void setText( Object toI18n, Values values )
            {
                assert values.size() == 1;

                cast( toI18n ).setText( values.get( 0 ) );
            }
            @Override
            public Values getText( Object toI18n )
            {
                return new IndexValues( cast( toI18n ).getText() );
            }
        };
    }

    /**
     * Handle I18h for {@link JTabbedPane}
     *
     * @return an {@link AbstractType} to handle {@link JTabbedPane}
     */
    public AbstractType<JTabbedPane> getJTabbedPane()
    {
        return new AbstractType<JTabbedPane>(JTabbedPane.class)
        {
            private static final long serialVersionUID = 1L;
            @Override
            public Keys getKeys( Object toI18n, String keyBaseName )
            {
                JTabbedPane  o      = cast( toI18n );
                final int    len    = o.getTabCount();

                return new IndexKeys( keyBaseName, len );
            }
            @Override
            public void setText( Object toI18n, Values values )
            {
                JTabbedPane  o      = cast( toI18n );
                final int    len    = o.getTabCount();
                int           index = 0;

                for( String value : values ) {
                    o.setTitleAt( index++, value );

                    if( index >= len ) {
                        // TODO: log something !
                        break;
                        }
                    }
            }
            @Override
            public Values getText( Object toI18n )
            {
                JTabbedPane    o      = cast( toI18n );
                final int      len    = o.getTabCount();
                final String[] values = new String[ len ];

                for( int i = 0; i<len; i++ ) {
                    values[ i ] = o.getTitleAt( i );
                    }

                return new IndexValues( values );
            }
        };
    }

    /**
     * Handle I18h for {@link TitledBorder}
     *
     * @return an {@link AbstractType} to handle {@link TitledBorder}
     */
    public AbstractType<TitledBorder> getTitledBorder()
    {
        return new AbstractTypeUniqKeys<TitledBorder>(TitledBorder.class)
        {
            private static final long serialVersionUID = 1L;
            @Override
            public void setText( Object toI18n, Values values )
            {
                assert values.size() == 1;

                cast( toI18n ).setTitle( values.get( 0 ) );
            }
            @Override
            public Values getText( Object toI18n )
            {
                return new IndexValues( cast( toI18n ).getTitle() );
            }
        };
    }
}
