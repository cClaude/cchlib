package com.googlecode.cchlib.i18n.core;

import javax.swing.JEditorPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import com.googlecode.cchlib.i18n.core.resolve.IndexValues;
import com.googlecode.cchlib.i18n.core.resolve.Values;
import com.googlecode.cchlib.i18n.types.AbstractType;
import com.googlecode.cchlib.i18n.types.AbstractTypeUniqKeys;

/**
 * Default implementation for field with {@link com.googlecode.cchlib.i18n.annotation.I18n} annotation
 */
/*not public*/ class AllAutoI18nTypes extends DefaultAutoI18nTypes
{
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    public AllAutoI18nTypes()
    {
        super();
    }

    /**
     * Handle I18h for {@link JTextArea}
     *
     * @return an {@link AbstractType} to handle {@link JTextArea}
     */
    public AbstractType<JTextArea> getJTextArea()
    {
        return new AbstractTypeUniqKeys<JTextArea>( JTextArea.class )
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
     * Handle I18h for {@link JTextField}
     *
     * @return an {@link AbstractType} to handle {@link JTextField}
     */
    public AbstractType<JTextField> getJTextField()
    {
        return new AbstractTypeUniqKeys<JTextField>( JTextField.class )
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
     * Handle I18h for {@link JEditorPane}
     *
     * @return an {@link AbstractType} to handle {@link JEditorPane}
     */
    public AbstractType<JEditorPane> getJEditorPane()
    {
        return new AbstractTypeUniqKeys<JEditorPane>( JEditorPane.class )
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
}
