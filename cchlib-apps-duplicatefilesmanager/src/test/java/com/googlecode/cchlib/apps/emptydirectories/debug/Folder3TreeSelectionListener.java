package com.googlecode.cchlib.apps.emptydirectories.debug;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import org.apache.log4j.Logger;

public class Folder3TreeSelectionListener implements TreeSelectionListener
{
    private static final Logger LOGGER = Logger.getLogger( Folder3TreeSelectionListener.class );
    private final Folder3TreeImpl tree;

    public Folder3TreeSelectionListener( final Folder3TreeImpl tree )
    {
        this.tree = tree;
    }

    @Override
    public void valueChanged( final TreeSelectionEvent event )
    {
        Folder3TreeNode node = (Folder3TreeNode)
                tree.getLastSelectedPathComponent();

        if( node == null ) {
            return; //nothing is selected
            }

        if( LOGGER.isDebugEnabled() ) {
            if( node.isSelected() ) {
                LOGGER.debug( "selected node : " + node );
                }
            else {
                LOGGER.debug( "unselected node : " + node );
                }
            }
        node.setSelected( !node.isSelected() );
        LOGGER.debug( "node selection change : " + node );

    }

}
