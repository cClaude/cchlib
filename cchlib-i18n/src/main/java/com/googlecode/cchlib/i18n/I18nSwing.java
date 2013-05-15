package com.googlecode.cchlib.i18n;

import javax.swing.JTextArea;

/**
 * 
 *
 */
// NOT public 
class I18nSwing 
{
    public I18nSwing()
    {
        // Empty
    }
    
    /**
     * Get {@link JTextArea} text for I18n
     * 
     * @param jTextArea {@link JTextArea} component
     * @return text of JTextArea
     * @see I18nForce
     */
    public String getJTextArea( final JTextArea jTextArea )
    {
        return jTextArea.getText();
    }
    
    /**
     * Set {@link JTextArea} text for I18n
     * 
     * @param jTextArea  {@link JTextArea} component
     * @param str        text of JTextArea
     */
    public void setJTextArea(
        final JTextArea jTextArea, 
        final String    str 
        )
    {
        jTextArea.setText( str );
    }
}
