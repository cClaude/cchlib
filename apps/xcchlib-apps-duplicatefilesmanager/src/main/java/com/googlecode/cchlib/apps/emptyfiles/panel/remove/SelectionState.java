package com.googlecode.cchlib.apps.emptyfiles.panel.remove;

import java.util.function.Consumer;

public enum SelectionState
{
    NONE_SELECTED(
        workingJPanel -> workingJPanel.setButtonsStateNoneSelected()
        ),
    AT_LEAST_ONE_FILE_SELECTED(
        workingJPanel -> workingJPanel.setButtonsStateAtLeastOneFileSelected()
        ),
    ALL_SELECTED(
        workingJPanel -> workingJPanel.setButtonsStateAllSelected()
        ),
    ;

    private Consumer<WorkingJPanel> consumer;

    private SelectionState( final Consumer<WorkingJPanel> consumer )
    {
        this.consumer = consumer;
    }

    // apply buttons state
    public void applyButtonsState( final WorkingJPanel workingJPanel )
    {
        this.consumer.accept( workingJPanel );
    }
}
