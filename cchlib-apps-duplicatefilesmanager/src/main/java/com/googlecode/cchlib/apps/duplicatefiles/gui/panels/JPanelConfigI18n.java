package com.googlecode.cchlib.apps.duplicatefiles.gui.panels;

import com.googlecode.cchlib.apps.duplicatefiles.gui.panels.config.JPanelConfigWB;
import com.googlecode.cchlib.i18n.annotation.I18nString;

public abstract class JPanelConfigI18n extends JPanelConfigWB
{
    private static final long serialVersionUID = 1L;

    @I18nString private String jPanelExcDirsFilterRegExp;
    @I18nString private String jPanelExcDirsFilterTitle;

    @I18nString private String jPanelExcFilesFilterRegExp;
    @I18nString private String jPanelExcFilesFilterTitle;

    @I18nString private String jPanelIncDirsFilterRegExp;
    @I18nString private String jPanelIncDirsFilterTitle;

    @I18nString private String jPanelIncFilesFilterRegExp;
    @I18nString private String jPanelIncFilesFilterTitle;

    @I18nString private String txtDisableDirsFilters;
    @I18nString private String txtDisableFilesFilters;

    @I18nString private String txtExcludeDirsFilters;
    @I18nString private String txtExcludeFilesFilters;

    @I18nString private String txtIncludeDirsFilters;
    @I18nString private String txtIncludeFilesFilters;

    protected JPanelConfigI18n()
    {
        this.setjPanelExcDirsFilterRegExp( "jPanelExcDirsFilterRegExp" );
        this.setjPanelExcDirsFilterTitle( "jPanelExcDirsFilterTitle" );
        this.setjPanelExcFilesFilterRegExp( "jPanelExcFilesFilterRegExp" );
        this.setjPanelExcFilesFilterTitle( "jPanelExcFilesFilterTitle" );
        this.setjPanelIncDirsFilterRegExp( "jPanelIncDirsFilterRegExp" );
        this.setjPanelIncDirsFilterTitle( "jPanelIncDirsFilterTitle" );
        this.setjPanelIncFilesFilterRegExp( "jPanelIncFilesFilterRegExp" );
        this.setjPanelIncFilesFilterTitle( "jPanelIncFilesFilterTitle" );

        this.setTxtDisableDirsFilters( "Disable dirs filters" );
        this.setTxtDisableFilesFilters( "Disable files filters" );
        this.setTxtExcludeDirsFilters( "Exclude filters" );
        this.setTxtExcludeFilesFilters( "Exclude filters" );
        this.setTxtIncludeDirsFilters( "Include filters" );
        this.setTxtIncludeFilesFilters( "Include filters" );
    }

    protected String getjPanelIncFilesFilterTitle()
    {
        return jPanelIncFilesFilterTitle;
    }

    private void setjPanelIncFilesFilterTitle( final String jPanelIncFilesFilterTitle )
    {
        this.jPanelIncFilesFilterTitle = jPanelIncFilesFilterTitle;
    }

    protected String getjPanelIncFilesFilterRegExp()
    {
        return jPanelIncFilesFilterRegExp;
    }

    private void setjPanelIncFilesFilterRegExp( final String jPanelIncFilesFilterRegExp )
    {
        this.jPanelIncFilesFilterRegExp = jPanelIncFilesFilterRegExp;
    }

    protected String getjPanelExcFilesFilterTitle()
    {
        return jPanelExcFilesFilterTitle;
    }

    private void setjPanelExcFilesFilterTitle( final String jPanelExcFilesFilterTitle )
    {
        this.jPanelExcFilesFilterTitle = jPanelExcFilesFilterTitle;
    }

    protected String getjPanelExcFilesFilterRegExp()
    {
        return jPanelExcFilesFilterRegExp;
    }

    private void setjPanelExcFilesFilterRegExp( final String jPanelExcFilesFilterRegExp )
    {
        this.jPanelExcFilesFilterRegExp = jPanelExcFilesFilterRegExp;
    }

    protected String getjPanelIncDirsFilterTitle()
    {
        return jPanelIncDirsFilterTitle;
    }

    private void setjPanelIncDirsFilterTitle( final String jPanelIncDirsFilterTitle )
    {
        this.jPanelIncDirsFilterTitle = jPanelIncDirsFilterTitle;
    }

    protected String getjPanelIncDirsFilterRegExp()
    {
        return jPanelIncDirsFilterRegExp;
    }

    private void setjPanelIncDirsFilterRegExp( final String jPanelIncDirsFilterRegExp )
    {
        this.jPanelIncDirsFilterRegExp = jPanelIncDirsFilterRegExp;
    }

    protected String getjPanelExcDirsFilterTitle()
    {
        return jPanelExcDirsFilterTitle;
    }

    private void setjPanelExcDirsFilterTitle( final String jPanelExcDirsFilterTitle )
    {
        this.jPanelExcDirsFilterTitle = jPanelExcDirsFilterTitle;
    }

    protected String getjPanelExcDirsFilterRegExp()
    {
        return jPanelExcDirsFilterRegExp;
    }

    private void setjPanelExcDirsFilterRegExp( final String jPanelExcDirsFilterRegExp )
    {
        this.jPanelExcDirsFilterRegExp = jPanelExcDirsFilterRegExp;
    }

    protected String getTxtDisableFilesFilters()
    {
        return txtDisableFilesFilters;
    }

    private void setTxtDisableFilesFilters( final String txtDisableFilesFilters )
    {
        this.txtDisableFilesFilters = txtDisableFilesFilters;
    }

    protected String getTxtDisableDirsFilters()
    {
        return txtDisableDirsFilters;
    }

    private void setTxtDisableDirsFilters( final String txtDisableDirsFilters )
    {
        this.txtDisableDirsFilters = txtDisableDirsFilters;
    }

    protected String getTxtIncludeFilesFilters()
    {
        return txtIncludeFilesFilters;
    }

    private void setTxtIncludeFilesFilters( final String txtIncludeFilesFilters )
    {
        this.txtIncludeFilesFilters = txtIncludeFilesFilters;
    }

    protected String getTxtExcludeFilesFilters()
    {
        return txtExcludeFilesFilters;
    }

    private void setTxtExcludeFilesFilters( final String txtExcludeFilesFilters )
    {
        this.txtExcludeFilesFilters = txtExcludeFilesFilters;
    }

    protected String getTxtExcludeDirsFilters()
    {
        return txtExcludeDirsFilters;
    }

    private void setTxtExcludeDirsFilters( final String txtExcludeDirsFilters )
    {
        this.txtExcludeDirsFilters = txtExcludeDirsFilters;
    }

    protected String getTxtIncludeDirsFilters()
    {
        return txtIncludeDirsFilters;
    }

    private void setTxtIncludeDirsFilters( final String txtIncludeDirsFilters )
    {
        this.txtIncludeDirsFilters = txtIncludeDirsFilters;
    }
}
