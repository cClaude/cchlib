package com.googlecode.cchlib.apps.editresourcesbundle.load;

import java.util.function.Consumer;
import com.googlecode.cchlib.util.EnumHelper;

//NOT public
@SuppressWarnings("squid:S00115") // Naming conventions
enum LoadDialogAction
{
    ACTIONCMD_OK_BUTTON( d -> d.actionOkButton() ),
    ACTIONCMD_CANCEL_BUTTON( d -> d.actionCancelButton() ),
    ACTIONCMD_SELECT_PREFIX( d -> d.actionSelectPrefix() ),

    ACTION_FT_Properties( d -> d.actionFtProperties() ),
    ACTION_FT_FormattedProperties( d -> d.actionFtFormattedproperties() ),
    ACTION_FT_ini( d -> d.actionFtIni() ),
    ACTION_Change_isUseLeftHasDefault( d -> d.actionChangeIsuselefthasdefault() ),
    ACTION_Change_ShowLineNumbers( d -> d.actionChangeShowlinenumbers() ),
    ACTION_Change_CUT_LINE_AFTER_HTML_BR( d -> d.actionChangeCutLineAfterHtmlBr() ),
    ACTION_Change_CUT_LINE_AFTER_HTML_END_P( d -> d.actionChangeCutLineAfterHtmlEndP() ),
    ACTION_Change_CUT_LINE_AFTER_NEW_LINE( d -> d.actionChangeCutLineAfterNewLine() ),
    ACTION_Change_CUT_LINE_BEFORE_HTML_BEGIN_P( d -> d.actionChangeCutLineBeforeHtmlBeginP() ),
    ACTION_Change_CUT_LINE_BEFORE_HTML_BR( d -> d.actionChangeCutLineBeforeHtmlBr() ),
    ;

    private Consumer<LoadDialog> performer;

    private LoadDialogAction( final Consumer<LoadDialog> performer )
    {
        this.performer = performer;
    }

    public String getActionCommand( final int index )
    {
        if( this == ACTIONCMD_SELECT_PREFIX ) {
            return name() + index;
            }

        throw new IllegalStateException();
    }

    public Integer getIndex( final String value )
    {
        return EnumHelper.getSuffixInteger( this, value );
    }

    public void perform( final LoadDialog loadDialog )
    {
        this.performer.accept( loadDialog );
    }
}
