package com.googlecode.cchlib.net.dhcp.server0.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import com.googlecode.cchlib.net.dhcp.server0.DHCPServer;

public class DHCPServerFrame extends JFrame
{
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTable table;
    private JScrollPane scrollPane;
    private DHCPTableModel model;

    /**
     * Launch the application.
     */
    public static void main( String[] args )
    {
        EventQueue.invokeLater( new Runnable() {
            @Override
            public void run()
            {
                try {
                    DHCPServerFrame frame = new DHCPServerFrame();
                    frame.setVisible( true );

                    DHCPServer server = new DHCPServer();
                    server.startDeamon( frame.getModel() );
                    }
                catch( Exception e ) {
                    e.printStackTrace();
                    }
            }
        } );
    }

    /**
     * Create the frame.
     */
    public DHCPServerFrame()
    {
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        setBounds( 100, 100, 450, 300 );
        this.contentPane = new JPanel();
        this.contentPane.setBorder( new EmptyBorder( 5, 5, 5, 5 ) );
        this.contentPane.setLayout( new BorderLayout( 0, 0 ) );
        setContentPane( this.contentPane );

        this.scrollPane = new JScrollPane();
        this.contentPane.add(this.scrollPane, BorderLayout.NORTH);

        this.table = new JTable();
        this.scrollPane.setViewportView( this.table );

        this.model = new DHCPTableModel();
        this.table.setModel( model );
    }

    protected DHCPTableModel getModel()
    {
        return model;
    }
}
