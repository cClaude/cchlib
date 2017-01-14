package com.googlecode.cchlib.apps.duplicatefiles.swing.gui.panels.search;

import java.util.Locale;
import java.util.function.BiConsumer;

enum Pass {
    PASS1( (p,l) -> p.updateDisplayPass1( l ) ), 
    PASS2( (p,l) -> p.updateDisplayPass2( l ) ),
    ;
    private BiConsumer<JPanelSearchingParallel,Locale> displayUpdater;
    
    private Pass( BiConsumer<JPanelSearchingParallel,Locale> displayUpdater )
    {
        this.displayUpdater = displayUpdater;
    }

    public void updateDisplay( JPanelSearchingParallel jPanelSearchingParallel , Locale locale )
    {
        displayUpdater.accept( jPanelSearchingParallel, locale );
    }
}