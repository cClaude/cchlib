package cx.ath.choisnet.i18n;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import javax.swing.AbstractButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;

/**
 * TODO: Doc!
 *
 * @author Claude CHOISNET
 */
public class DefaultAutoI18nTypes implements AutoI18nTypes
{
    private static final long serialVersionUID = 1L;
    private ArrayList<AutoI18nTypes.Type> types;

    /**
     * TODO: Doc!
     *
     * @author Claude CHOISNET
     * @param <T>
     */
    public abstract class AbstractType<T> implements AutoI18nTypes.Type
    {
        private static final long serialVersionUID = 1L;

        /**
         * TODO: Doc!
         *
         * @return type for this object
         */
        public abstract Class<T> getType();

        /**
         * TODO: Doc!
         *
         * @param toI18n
         * @return cast field to localize to current type
         */
        public T cast( Object toI18n )
        {
            return getType().cast( toI18n );
        }
    }

    /**
     * TODO: Doc!
     */
    public DefaultAutoI18nTypes()
    {
        types = new ArrayList<AutoI18nTypes.Type>();

        Method[] methods = getClass().getMethods();

        for(Method m:methods) {
            if( AutoI18nTypes.Type.class.isAssignableFrom( m.getReturnType() ) ) {
                if( m.getParameterTypes().length == 0 ) {
                    try {
                        AutoI18nTypes.Type t = AutoI18nTypes.Type.class.cast( m.invoke( this ) );

                        types.add( t );
                    }
                    catch( Exception e ) {
                        // TODO Should not occur !
                        e.printStackTrace();
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
                throws java.util.MissingResourceException
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
                throws java.util.MissingResourceException
            {
                cast( toI18n ).setText(k.getValue());
            }
            @Override
            public String[] getText( Object toI18n )
            {
                String[] r = { cast( toI18n ).getText() };
                return r;
            }
        };
    }

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
                throws java.util.MissingResourceException
            {
                cast( toI18n ).setText(k.getValue());
            }
            @Override
            public String[] getText( Object toI18n )
            {
                String[] r = { cast( toI18n ).getText() };
                return r;
            }
        };
    }

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
                throws java.util.MissingResourceException
            {
                JTabbedPane  o      = cast( toI18n );
                final int    len    = o.getTabCount();

                for(int i = 0;i<len;i++) {
                    //ONLY FIST VALUE COLLECTED HERE?
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
}
