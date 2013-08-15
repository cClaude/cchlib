package com.googlecode.cchlib.swing.batchrunner.ihm;

import com.googlecode.cchlib.swing.filechooser.WaitingJFileChooserInitializer;


/**
 * Text for localization of {@link BRPanel}
 *
 * @since 1.4.8
 */
public interface BRPanelLocaleResources
{
    /**
     * @return localized text for Add source files button
     */
    public String getTextAddSourceFile();
    /**
     * @return localized text for set destination folder button
     */
    public String getTextSetDestinationFolder();
    /**
     * @return localized text for clear source files button
     */
    public String getTextClearSourceFileList();
    /**
     * @return localized text for start batch button
     */
    public String getTextDoAction();
    /**
     * @return localized text for dialog title of {@link WaitingJFileChooserInitializer}.
     */
    public String getTextJFileChooserInitializerTitle();
    /**
     * @return localized text for dialog text of {@link WaitingJFileChooserInitializer}.
     */
    public String getTextJFileChooserInitializerMessage();
    /**
     * @return localized text for message when try to start batch,
     *         but there is no source file define
     */
    public String getTextNoSourceFile();
    /**
     * @return localized text for message when try to start batch,
     *         but there is no destination folder set
     */
    public String getTextNoDestinationFolder();
    /**
     * @return localized text for message when batch is running
     */
    public String getTextWorkingOn_FMT();
    /**
     * @return localized text for Exception dialog when an unexpected
     *         exception occur.
     */
    public String getTextUnexpectedExceptionTitle();
    
    
    public String getTextExitRequestTitle();
    public String getTextExitRequestMessage();
    public String getTextExitRequestYes();
    public String getTextExitRequestNo();
}
