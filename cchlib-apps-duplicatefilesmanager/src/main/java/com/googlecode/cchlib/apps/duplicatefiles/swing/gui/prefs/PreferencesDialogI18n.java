package com.googlecode.cchlib.apps.duplicatefiles.swing.gui.prefs;

import javax.swing.JDialog;
import com.googlecode.cchlib.apps.duplicatefiles.swing.prefs.PreferencesControler;
import com.googlecode.cchlib.apps.duplicatefiles.swing.services.AppToolKitService;
import com.googlecode.cchlib.i18n.annotation.I18nString;

@SuppressWarnings("squid:MaximumInheritanceDepth")
public class PreferencesDialogI18n extends JDialog
{
    private static final long serialVersionUID = 1L;

    private final PreferencesControler preferencesControler;

    @I18nString private String txtPreferencesDialogMessageExceptionDialogTitle;
    @I18nString private String txtStringDefaultLocale;
    @I18nString private String txtJLabelDefaultMessageDigestBufferSize;
    @I18nString private String txtJLabelDefaultDeleteDelais;
    @I18nString private String txtJLabelDefaultDeleteSleepDisplayMaxEntries;
    @I18nString private String txtJPanelTitle;

    protected PreferencesDialogI18n()
    {
        this.preferencesControler = AppToolKitService.getInstance().getAppToolKit().getPreferences();

        setTxtPreferencesDialogMessageExceptionDialogTitle( "Can not save configuration" );
        setTxtStringDefaultLocale( "default system" );
        setTxtJLabelDefaultMessageDigestBufferSize( "Default: %d bytes" );
        setTxtJLabelDefaultDeleteDelais( "Default: %d ms" );
        setTxtJLabelDefaultDeleteSleepDisplayMaxEntries( "Default: %d" );
        setTxtJPanelTitle( "Default configuration" );
    }

    public PreferencesControler getPreferencesControler()
    {
        return this.preferencesControler;
    }

    protected final String getTxtPreferencesDialogMessageExceptionDialogTitle()
    {
        return this.txtPreferencesDialogMessageExceptionDialogTitle;
    }

    protected final String getTxtStringDefaultLocale()
    {
        return this.txtStringDefaultLocale;
    }

    protected final String getTxtJLabelDefaultMessageDigestBufferSize()
    {
        return this.txtJLabelDefaultMessageDigestBufferSize;
    }

    protected final String getTxtJLabelDefaultDeleteDelais()
    {
        return this.txtJLabelDefaultDeleteDelais;
    }

    protected final String getTxtJLabelDefaultDeleteSleepDisplayMaxEntries()
    {
        return this.txtJLabelDefaultDeleteSleepDisplayMaxEntries;
    }

    protected final String getTxtJPanelTitle()
    {
        return this.txtJPanelTitle;
    }

    private final void setTxtPreferencesDialogMessageExceptionDialogTitle( final String txtPreferencesDialogMessageExceptionDialogTitle )
    {
        this.txtPreferencesDialogMessageExceptionDialogTitle = txtPreferencesDialogMessageExceptionDialogTitle;
    }

    private final void setTxtStringDefaultLocale( final String txtStringDefaultLocale )
    {
        this.txtStringDefaultLocale = txtStringDefaultLocale;
    }

    private final void setTxtJLabelDefaultMessageDigestBufferSize( final String txtJLabelDefaultMessageDigestBufferSize )
    {
        this.txtJLabelDefaultMessageDigestBufferSize = txtJLabelDefaultMessageDigestBufferSize;
    }

    private final void setTxtJLabelDefaultDeleteDelais( final String txtJLabelDefaultDeleteDelais )
    {
        this.txtJLabelDefaultDeleteDelais = txtJLabelDefaultDeleteDelais;
    }

    private final void setTxtJLabelDefaultDeleteSleepDisplayMaxEntries( final String txtJLabelDefaultDeleteSleepDisplayMaxEntries )
    {
        this.txtJLabelDefaultDeleteSleepDisplayMaxEntries = txtJLabelDefaultDeleteSleepDisplayMaxEntries;
    }

    private final void setTxtJPanelTitle( final String txtJPanelTitle )
    {
        this.txtJPanelTitle = txtJPanelTitle;
    }
}
