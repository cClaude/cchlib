package com.googlecode.cchlib.apps.duplicatefiles.gui.panels.search.files;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Xxxxx {

    private JFrame frame;
    private JTable table;
    private JScrollPane scrollPane;

    /**
     * Launch the application.
     */
    public static void main( final String[] args )
    {
        EventQueue.invokeLater( ( ) -> {
            try {
                final Xxxxx window = new Xxxxx();
                window.frame.setVisible( true );
            }
            catch( final Exception e ) {
                e.printStackTrace();
            }
        } );
    }

    /**
     * Create the application.
     * @wbp.parser.entryPoint
     */
    public Xxxxx()
    {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize()
    {
        this.frame = new JFrame();
        this.frame.setBounds( 100, 100, 450, 300 );
        this.frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

        this.table = new JTable();
        this.table.setModel(new DefaultTableModel(
            new Object[][] {
                {"1", "dfdsd sd fsdfsdfsdfsdfsdf"},
                {"2", "dfdsd sd fsdfdsd sd fsdfsdfsdfsdfsdfdfdsd sd fsdfsdfsdfsdfsdf"},
                {"3", null},
            },
            new String[] {
                "#", "dfdsd sd fsdfsdfsdfsdfsdf"
            }
        ) {
            private static final long serialVersionUID = 1L;
            Class<?>[] columnTypes = new Class[] {
                String.class, String.class
            };
            @Override
            public Class<?> getColumnClass(final int columnIndex) {
                return columnTypes[columnIndex];
            }
        });
        this.table.getColumnModel().getColumn(0).setResizable(false);
        this.table.getColumnModel().getColumn(0).setPreferredWidth(19);
        this.table.getColumnModel().getColumn(1).setPreferredWidth(200);
        this.table.getColumnModel().getColumn(1).setMinWidth(600);
        //this.frame.getContentPane().add(this.table, BorderLayout.SOUTH);

        this.scrollPane = new JScrollPane();
        this.frame.getContentPane().add(this.scrollPane, BorderLayout.CENTER);

        this.scrollPane.setViewportView( table );
    }

}
