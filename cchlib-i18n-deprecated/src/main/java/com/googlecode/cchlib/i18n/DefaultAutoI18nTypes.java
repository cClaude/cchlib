package com.googlecode.cchlib.i18n;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.MissingResourceException;
import javax.swing.AbstractButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;
import javax.swing.border.TitledBorder;

/**
 *
 *
 */
public class DefaultAutoI18nTypes implements AutoI18nTypes
{
    private static final long serialVersionUID = 1L;
    /** @serial */
    private ArrayList<AutoI18nTypes.Type> types;

    /**
     *
     */
    public static abstract class AbstractType<T> implements AutoI18nTypes.Type
    {
        private static final long serialVersionUID = 1L;

        /**
         * Returns type for this object
         * @return type for this object
         */
        @Override
        public abstract Class<T> getType();

        /**
         * Cast current object to current type.
         *
         * @param toI18n Object to I18n
         * @return cast field to localize to current type
         * @see #getType()
         */
        final public T cast( Object toI18n )
        {
            return getType().cast( toI18n );
        }
    }

    /**
     * Build {@link DefaultAutoI18nTypes} with the
     * default list of supported classes.
     */
    public DefaultAutoI18nTypes()
    {
        this.types = new ArrayList<AutoI18nTypes.Type>();

        final Method[] methods = getClass().getMethods();

        for( Method m:methods ) {
            if( AutoI18nTypes.Type.class.isAssignableFrom( m.getReturnType() ) ) {
                if( m.getParameterTypes().length == 0 ) {
                    try {
                        AutoI18nTypes.Type t = AutoI18nTypes.Type.class.cast( m.invoke( DefaultAutoI18nTypes.this ) );

                        types.add( t );
                        }
                    catch( Exception shouldNotOccur ) {
                        throw new RuntimeException( shouldNotOccur );
                        }
                    }
                }
            }
    }

    @Override // AutoI18nTypes
    public Iterator<AutoI18nTypes.Type> iterator()
    {
        return getAutoI18nTypes().iterator();
    }

    /**
     * Returns collection of AutoI18nTypes.Type supported
     * by this AutoI18nTypes
     *
     * @return collection of AutoI18nTypes.Type supported
     * by this AutoI18nTypes
     */
    public Collection<AutoI18nTypes.Type> getAutoI18nTypes()
    {
        return types;
    }

    /**
     * Handle I18h for {@link JLabel}
     *
     * @return an {@link AbstractType} to handle {@link JLabel}
     */
    public AbstractType<JLabel> getJLabel()
    {
        return new AbstractType<JLabel>()
        {
            private static final long serialVersionUID = 1L;
            @Override
            public Class<JLabel> getType()
            {
                return JLabel.class;
            }
            @Override
            public void setText( Object toI18n, AutoI18n.Key k)
                throws MissingResourceException
            {
                cast( toI18n ).setText( k.getValue() );
            }
            @Override
            public String[] getText( Object toI18n )
            {
                String[] r = { cast( toI18n ).getText() };
                return r;
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
        return new AbstractType<AbstractButton>()
        {
            private static final long serialVersionUID = 1L;
            @Override
            public Class<AbstractButton> getType()
            {
                return AbstractButton.class;
            }
            @Override
            public void setText( Object toI18n, AutoI18n.Key k)
                throws MissingResourceException
            {
                cast( toI18n ).setText( k.getValue() );
            }
            @Override
            public String[] getText( Object toI18n )
            {
                return new String[] { cast( toI18n ).getText() };
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
        return new AbstractType<JCheckBox>()
        {
            private static final long serialVersionUID = 1L;
            @Override
            public Class<JCheckBox> getType()
            {
                return JCheckBox.class;
            }
            @Override
            public void setText( Object toI18n, AutoI18n.Key k)
                throws MissingResourceException
            {
                cast( toI18n ).setText(k.getValue());
            }
            @Override
            public String[] getText( Object toI18n )
            {
                return new String[] { cast( toI18n ).getText() };
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
        return new AbstractType<JTabbedPane>()
        {
            private static final long serialVersionUID = 1L;
            @Override
            public Class<JTabbedPane> getType()
            {
                return JTabbedPane.class;
            }
            @Override
            public void setText( Object toI18n, AutoI18n.Key k)
                throws MissingResourceException
            {
                JTabbedPane  o      = cast( toI18n );
                final int    len    = o.getTabCount();

                for(int i = 0;i<len;i++) {
                    //FIX ME ! ONLY FIRST VALUE COLLECTED HERE?
                    o.setTitleAt( i, k.getValue( i ) );
                    }
            }
            @Override
            public String[] getText( Object toI18n )
            {
                JTabbedPane o   = cast( toI18n );
                final int   len = o.getTabCount();
                String[]    r   = new String[len];

                for(int i = 0;i<len;i++) {
                    r[i ] = o.getTitleAt( i );
                    }
                return r;
            }
        };
    }

    //
    /**
     * Handle I18h for {@link TitledBorder}
     *
     * @return an {@link AbstractType} to handle {@link TitledBorder}
     */
    public AbstractType<TitledBorder> getTitledBorder()
    {
        return new AbstractType<TitledBorder>()
        {
            private static final long serialVersionUID = 1L;
            @Override
            public Class<TitledBorder> getType()
            {
                return TitledBorder.class;
            }
            @Override
            public void setText( Object toI18n, AutoI18n.Key k ) throws MissingResourceException
            {
                cast( toI18n ).setTitle( k.getValue() );
            }
            @Override
            public String[] getText(Object toI18n)
            {
                return new String[] { cast( toI18n ).getTitle() };
            }
        };
    }

}
