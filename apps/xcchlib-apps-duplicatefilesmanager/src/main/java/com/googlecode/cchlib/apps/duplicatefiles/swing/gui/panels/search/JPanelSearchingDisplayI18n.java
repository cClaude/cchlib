package com.googlecode.cchlib.apps.duplicatefiles.swing.gui.panels.search;

import javax.swing.JPanel;
import com.googlecode.cchlib.i18n.annotation.I18nName;
import com.googlecode.cchlib.i18n.annotation.I18nString;

@I18nName("JPanelSearching")
public class JPanelSearchingDisplayI18n extends JPanel
{
    private static final long serialVersionUID = 1L;

    @I18nString private String txtDuplicateSetsFound;
    @I18nString private String txtDuplicateFilesFound;
    @I18nString private String txtNumberOfFilesProcessed;
    @I18nString private String txtOctectsToCheck;

    @I18nString private String txtCurrentFile;
    @I18nString private String txtCurrentDir;

    protected JPanelSearchingDisplayI18n()
    {
        setTxtDuplicateSetsFound( "%,d" );
        setTxtDuplicateFilesFound( "%,d" );
        setTxtNumberOfFilesProcessed( "Number of files processed: %,d" );
        setTxtOctectsToCheck( "Octects to check: %,d" );

        setTxtCurrentFile( "Current File :" );
        setTxtCurrentDir( "Current directory :" );
    }

    protected final String getTxtDuplicateSetsFound()
    {
        return this.txtDuplicateSetsFound;
    }

    protected final String getTxtDuplicateFilesFound()
    {
        return this.txtDuplicateFilesFound;
    }

    protected final String getTxtNumberOfFilesProcessed()
    {
        return this.txtNumberOfFilesProcessed;
    }

    protected final String getTxtOctectsToCheck()
    {
        return this.txtOctectsToCheck;
    }

    protected final String getTxtCurrentFile()
    {
        return this.txtCurrentFile;
    }

    protected final String getTxtCurrentDir()
    {
        return this.txtCurrentDir;
    }

    private final void setTxtDuplicateSetsFound( final String txtDuplicateSetsFound )
    {
        this.txtDuplicateSetsFound = txtDuplicateSetsFound;
    }

    private final void setTxtDuplicateFilesFound( final String txtDuplicateFilesFound )
    {
        this.txtDuplicateFilesFound = txtDuplicateFilesFound;
    }

    private final void setTxtNumberOfFilesProcessed( final String txtNumberOfFilesProcessed )
    {
        this.txtNumberOfFilesProcessed = txtNumberOfFilesProcessed;
    }

    private final void setTxtOctectsToCheck( final String txtOctectsToCheck )
    {
        this.txtOctectsToCheck = txtOctectsToCheck;
    }

    private final void setTxtCurrentFile( final String txtCurrentFile )
    {
        this.txtCurrentFile = txtCurrentFile;
    }

    private final void setTxtCurrentDir( final String txtCurrentDir )
    {
        this.txtCurrentDir = txtCurrentDir;
    }
}
