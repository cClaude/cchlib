package com.googlecode.cchlib.apps.duplicatefiles.swing.gui.panels.search;


public abstract class JPanelSearching extends JPanelSearchingFilters implements Cancelable { // NOSONAR
    enum Pass {
        PASS1, PASS2,
    }

    private static final long serialVersionUID = 1L;

    protected JPanelSearching( final int nThreads )
    {
        super( nThreads );
    }

    public abstract void startScan( ScanParams scanParams );

    public void clear()
    {
        super.clearErrors();
        super.clearCurrentFiles();
        super.clearCurrentFileLabels();
    }
}
