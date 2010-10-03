package cx.ath.choisnet.tools.i18n;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.ActionListener;
import java.io.Serializable;

/**
 * TODO: doc!
 * 
 * @author Claude CHOISNET
 */
public class JPopupMenuAssistant implements Serializable
{
    private static final long serialVersionUID = 1L;
    private final JPopupMenu    jPopupMenu;
    private final MouseListener jPopupMenuListener;

    public interface PopupMenuActionListener
    {
        public void actionPerformed( ActionEvent event, JMenuItem jMenuItem );
    }
    
    public JPopupMenuAssistant()
    {
        this.jPopupMenu         = new JPopupMenu();
        this.jPopupMenuListener = new JPopupMenuListener();
    }

    public JPopupMenuAssistant(JPopupMenu jPopupMenu)
    {
        this.jPopupMenu = jPopupMenu;
        this.jPopupMenuListener = new JPopupMenuListener();
    }
    
    /**
     * @return internal JPopupMenu object
     */
    public JPopupMenu getJPopupMenu()
    {
        return this.jPopupMenu;
    }

    /**
     * @return popup menu listener
     */
    public MouseListener getListener()
    {
        return this.jPopupMenuListener;
    }

    /**
     * Install popup menu in a JTable
     * 
     * @param jTable
     */
    public void installListener( JTable jTable )
    {
        jTable.addMouseListener( jPopupMenuListener );
        jTable.getTableHeader().addMouseListener( jPopupMenuListener );
    }

    //private
    class JPopupMenuListener implements MouseListener 
    {
        @Override
        public void mousePressed( MouseEvent e )
        {
            showPopup( e );
        }
        @Override
        public void mouseReleased( MouseEvent e )
        {
            showPopup( e );
        }
        private void showPopup( MouseEvent e )
        {
            if( e.isPopupTrigger() ) {
                jPopupMenu.show( e.getComponent(), e.getX(), e.getY() );
            }
        }
        @Override
        public void mouseClicked( MouseEvent e ) {}
        @Override
        public void mouseEntered( MouseEvent e ) {}
        @Override
        public void mouseExited( MouseEvent e ) {}
    }
        
    /**
     * TODO: doc
     * 
     * @author Claude CHOISNET
     */
    public static class ActionListenerAdapter implements ActionListener 
    {
        private final JPopupMenuAssistant.PopupMenuActionListener popupMenuActionListener;

        /**
         * 
         * @param adapter
         */
        public ActionListenerAdapter( JPopupMenuAssistant.PopupMenuActionListener adapter )
        {
            this.popupMenuActionListener = adapter;
        }

        @Override
        public void actionPerformed( ActionEvent event )
        {
            Object source = event.getSource();
            
            if( source instanceof JMenuItem ) {
                popupMenuActionListener.actionPerformed( 
                        event, 
                        JMenuItem.class.cast( source ) 
                        );
            }
        }
    }
}