package cx.ath.choisnet.tools.i18n;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import java.awt.event.ActionEvent;
import java.io.Serializable;

public class CompareRessourceBundlePopupMenu
    implements JPopupMenuAssistant.PopupMenuActionListener,
               Serializable
{
    private static final long serialVersionUID = 1L;
    private static String     INSERT_CMD       = "Insert Rows";
    private static String     DELETE_CMD       = "Delete Rows";

    public CompareRessourceBundlePopupMenu( JPopupMenuAssistant assistant )
    {
        JPopupMenu jPopupMenu = assistant.getJPopupMenu();

        JMenuItem menuItem = new JMenuItem( INSERT_CMD );
        menuItem.addActionListener( new JPopupMenuAssistant.ActionListenerAdapter( this ) );
        jPopupMenu.add( menuItem );
        menuItem = new JMenuItem( DELETE_CMD );
        menuItem.addActionListener( new JPopupMenuAssistant.ActionListenerAdapter( this ) );
        jPopupMenu.add( menuItem );
    }

    public void insertColumn( ActionEvent e )
    {
        JOptionPane.showMessageDialog( null, "Insert Column here." );
        // insert new column here
    }

    public void deleteColumn( ActionEvent e )
    {
        JOptionPane.showMessageDialog( null, "Delete Column here." );
        // delete column here
    }

    @Override
    public void actionPerformed( ActionEvent event, JMenuItem jMenuItem )
    {
        if( jMenuItem.getText() == "Insert Rows" ) {
            insertColumn( event );
        } else if( jMenuItem.getText() == "Delete Rows" ) {
            deleteColumn( event );
        }
    }
}