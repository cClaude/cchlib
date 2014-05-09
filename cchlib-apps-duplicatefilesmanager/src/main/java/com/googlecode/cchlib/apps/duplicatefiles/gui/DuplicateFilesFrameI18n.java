package com.googlecode.cchlib.apps.duplicatefiles.gui;

import javax.swing.JFrame;
import com.googlecode.cchlib.i18n.annotation.I18nString;

public class DuplicateFilesFrameI18n extends JFrame
{
    private static final long serialVersionUID = 2L;

    @I18nString private String txtContinue;
    @I18nString private String txtRestart;
    @I18nString private String txtRemove;
    @I18nString private String txtDeleteNow;
    @I18nString private String txtBack;
    @I18nString private String txtCancel;
    @I18nString private String txtClearSelection;

    public DuplicateFilesFrameI18n()
    {
        this.setTxtContinue( "Continue" );
        this.setTxtRestart( "Restart" );
        this.setTxtRemove( "Remove" );
        this.setTxtDeleteNow( "Delete now" );
        this.setTxtBack( "Back" );
        this.setTxtCancel( "Cancel" );
        this.setTxtClearSelection( "Clear selection" );
    }

    String getTxtContinue()
    {
        return txtContinue;
    }

    private void setTxtContinue( final String txtContinue )
    {
        this.txtContinue = txtContinue;
    }

    String getTxtRestart()
    {
        return txtRestart;
    }

    private void setTxtRestart( final String txtRestart )
    {
        this.txtRestart = txtRestart;
    }

    String getTxtCancel()
    {
        return txtCancel;
    }

    private void setTxtCancel( final String txtCancel )
    {
        this.txtCancel = txtCancel;
    }

    String getTxtRemove()
    {
        return txtRemove;
    }

    private void setTxtRemove( final String txtRemove )
    {
        this.txtRemove = txtRemove;
    }

    String getTxtClearSelection()
    {
        return txtClearSelection;
    }

    private void setTxtClearSelection( final String txtClearSelection )
    {
        this.txtClearSelection = txtClearSelection;
    }

    String getTxtBack()
    {
        return txtBack;
    }

    private void setTxtBack( final String txtBack )
    {
        this.txtBack = txtBack;
    }

    String getTxtDeleteNow()
    {
        return txtDeleteNow;
    }

    private void setTxtDeleteNow( final String txtDeleteNow )
    {
        this.txtDeleteNow = txtDeleteNow;
    }
}
