package com.googlecode.cchlib.apps.duplicatefiles.swing.gui.panels.filtersconfig;

import com.googlecode.cchlib.i18n.annotation.I18nString;

@SuppressWarnings("squid:MaximumInheritanceDepth")
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
    }

    protected String getjPanelIncFilesFilterTitle()
    {
        return this.jPanelIncFilesFilterTitle;
    }

    private void setjPanelIncFilesFilterTitle( final String jPanelIncFilesFilterTitle )
    {
        this.jPanelIncFilesFilterTitle = jPanelIncFilesFilterTitle;
    }

    protected String getjPanelIncFilesFilterRegExp()
    {
        return this.jPanelIncFilesFilterRegExp;
    }

    private void setjPanelIncFilesFilterRegExp( final String jPanelIncFilesFilterRegExp )
    {
        this.jPanelIncFilesFilterRegExp = jPanelIncFilesFilterRegExp;
    }

    protected String getjPanelExcFilesFilterTitle()
    {
        return this.jPanelExcFilesFilterTitle;
    }

    private void setjPanelExcFilesFilterTitle( final String jPanelExcFilesFilterTitle )
    {
        this.jPanelExcFilesFilterTitle = jPanelExcFilesFilterTitle;
    }

    protected String getjPanelExcFilesFilterRegExp()
    {
        return this.jPanelExcFilesFilterRegExp;
    }

    private void setjPanelExcFilesFilterRegExp( final String jPanelExcFilesFilterRegExp )
    {
        this.jPanelExcFilesFilterRegExp = jPanelExcFilesFilterRegExp;
    }

    protected String getjPanelIncDirsFilterTitle()
    {
        return this.jPanelIncDirsFilterTitle;
    }

    private void setjPanelIncDirsFilterTitle( final String jPanelIncDirsFilterTitle )
    {
        this.jPanelIncDirsFilterTitle = jPanelIncDirsFilterTitle;
    }

    protected String getjPanelIncDirsFilterRegExp()
    {
        return this.jPanelIncDirsFilterRegExp;
    }

    private void setjPanelIncDirsFilterRegExp( final String jPanelIncDirsFilterRegExp )
    {
        this.jPanelIncDirsFilterRegExp = jPanelIncDirsFilterRegExp;
    }

    protected String getjPanelExcDirsFilterTitle()
    {
        return this.jPanelExcDirsFilterTitle;
    }

    private void setjPanelExcDirsFilterTitle( final String jPanelExcDirsFilterTitle )
    {
        this.jPanelExcDirsFilterTitle = jPanelExcDirsFilterTitle;
    }

    protected String getjPanelExcDirsFilterRegExp()
    {
        return this.jPanelExcDirsFilterRegExp;
    }

    private void setjPanelExcDirsFilterRegExp( final String jPanelExcDirsFilterRegExp )
    {
        this.jPanelExcDirsFilterRegExp = jPanelExcDirsFilterRegExp;
    }
}
