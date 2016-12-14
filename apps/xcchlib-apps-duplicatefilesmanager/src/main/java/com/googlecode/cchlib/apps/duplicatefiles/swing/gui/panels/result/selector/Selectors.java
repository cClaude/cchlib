package com.googlecode.cchlib.apps.duplicatefiles.swing.gui.panels.result.selector;

public enum Selectors
{
    NONE,
    SELECT_BY_REGEXP,
    SELECT_BY_SAME_FOLDER,
    ;

    public SelectorPanel newSelectorPanel(
        final DuplicateData duplicateData
        )
    {
        switch( this ) {
            case NONE:
                return new SelectByDisabled();

            case SELECT_BY_REGEXP:
                return new SelectByRegExp( duplicateData );

            case SELECT_BY_SAME_FOLDER:
                return new SelectBySameFolder( duplicateData );
            }

        throw new IllegalStateException();
    }
}
