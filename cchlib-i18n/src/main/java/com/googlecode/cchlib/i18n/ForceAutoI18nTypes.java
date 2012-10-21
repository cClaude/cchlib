package com.googlecode.cchlib.i18n;

import java.util.MissingResourceException;
import javax.swing.JTextArea;

/**
 *
 */
public class ForceAutoI18nTypes extends DefaultAutoI18nTypes 
{
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	public ForceAutoI18nTypes() 
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
        return new AbstractType<JTextArea>()
        {
        	private static final long serialVersionUID = 1L;
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
            @Override
            public Class<JTextArea> getType()
            {
            	return JTextArea.class;
            }
        };
    }

}
