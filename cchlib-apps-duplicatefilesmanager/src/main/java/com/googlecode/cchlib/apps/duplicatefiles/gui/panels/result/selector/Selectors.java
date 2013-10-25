package com.googlecode.cchlib.apps.duplicatefiles.gui.panels.result.selector;

import com.googlecode.cchlib.apps.duplicatefiles.DFToolKit;

public enum Selectors
{
    NONE,
    SELECT_BY_REGEXP,
    SELECT_BY_SAME_FOLDER,
    ;

    public SelectorPanel newSelectorPanel(
        final DFToolKit dFToolKit, 
        final DuplicateData duplicateData
        )
    {
        switch( this ) {
            case NONE:
                return new SelectByDisabled();

            case SELECT_BY_REGEXP:
                return new SelectByRegExp( dFToolKit, duplicateData );

            case SELECT_BY_SAME_FOLDER:
                return new SelectBySameFolder( dFToolKit, duplicateData );
            }

        throw new IllegalStateException();
    }
}
