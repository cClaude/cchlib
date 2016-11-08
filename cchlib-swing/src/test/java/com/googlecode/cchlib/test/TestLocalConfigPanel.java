package com.googlecode.cchlib.test;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.AbstractCollection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListModel;
import org.apache.log4j.Logger;

/**
 *
 */
public class TestLocalConfigPanel
    extends JPanel
        implements ActionListener
{
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger( TestLocalConfigPanel.class );

    private final WindowListener windowListener;
    private final TestConfigurationHelper testLocal;
    private final TestConfigurationHelper.Config config;

    private static final String ACTION_QUIT = "ACTION_QUIT";
    private static final String ACTION_SAVE = "ACTION_SAVE";

    private final DefaultListModel<String> model_existingMACAddr = new DefaultListModel<String>();
    private JTextField textField_;

    public TestLocalConfigPanel( final WindowListener windowListener )
    {
        this.windowListener = windowListener;
        this.testLocal      = new TestConfigurationHelper();
        try {
            this.testLocal.load();
            }
        catch( final FileNotFoundException e ) { // $codepro.audit.disable logExceptions
            LOGGER.warn( "No config to load : " + e.getMessage() );
            }
        catch( final IOException e ) {
            LOGGER.error( "Can not load config", e );
            }
        this.config = testLocal.getConfig();

        {
            final GridBagLayout gridBagLayout = new GridBagLayout();
            gridBagLayout.columnWidths = new int[]{25, 0, 25, 0};
            gridBagLayout.rowHeights = new int[]{0, 0, 0, 0};
            gridBagLayout.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
            gridBagLayout.rowWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
            setLayout(gridBagLayout);
        }
        {
            textField_ = new JTextField();
            final GridBagConstraints gbc_textField_ = new GridBagConstraints();
            gbc_textField_.insets = new Insets(0, 0, 5, 5);
            gbc_textField_.fill = GridBagConstraints.HORIZONTAL;
            gbc_textField_.gridx = 1;
            gbc_textField_.gridy = 0;
            add(textField_, gbc_textField_);
            textField_.setColumns(10);
        }
        {
            final JList<String> jList_existingMACAddr = new JList<String>();
            jList_existingMACAddr.setModel( model_existingMACAddr );
            final GridBagConstraints gbc_jList_existingMACAddr = new GridBagConstraints();
            gbc_jList_existingMACAddr.gridwidth = 3;
            gbc_jList_existingMACAddr.insets = new Insets(0, 0, 5, 0);
            gbc_jList_existingMACAddr.fill = GridBagConstraints.BOTH;
            gbc_jList_existingMACAddr.gridx = 0;
            gbc_jList_existingMACAddr.gridy = 1;
            add(jList_existingMACAddr, gbc_jList_existingMACAddr);
        }
        {
            final JButton btnSave = new JButton("Save");
            btnSave.setActionCommand( ACTION_SAVE );
            btnSave.addActionListener( this );
            final GridBagConstraints gbc_btnSave = new GridBagConstraints();
            gbc_btnSave.insets = new Insets(0, 0, 0, 5);
            gbc_btnSave.gridx = 0;
            gbc_btnSave.gridy = 2;
            add(btnSave, gbc_btnSave);
        }
        {
            final JButton btnQuit = new JButton("Quit");
            btnQuit.setActionCommand( ACTION_QUIT );
            btnQuit.addActionListener( this );
            final GridBagConstraints gbc_btnQuit = new GridBagConstraints();
            gbc_btnQuit.gridx = 2;
            gbc_btnQuit.gridy = 2;
            add(btnQuit, gbc_btnQuit);
        }

        for( final String macAddress : config.getExistingMACAddressCollection() ) {
            model_existingMACAddr.addElement( macAddress );
            }
//        final String    macAddress  = "00-21-70-F3-9F-8B";
//        model_existingMACAddr.addElement( macAddress );
    }

    @Override
    public void actionPerformed( final ActionEvent event )
    {
        final String cmd = event.getActionCommand();

        if( ACTION_QUIT.equals( cmd ) ) {
            this.windowListener.windowClosed( null );
            }
        else if( ACTION_SAVE.equals( cmd ) ) {
            final TestConfigurationHelper.Config config = this.testLocal.getConfig();

            config.setExistingMACAddressCollection(
                new ListModelCollection<String>( model_existingMACAddr )
                );

            //TODO something to do here ! but I don't remember what

            try {
                this.testLocal.save();

                LOGGER.info( "Config saved" );
                }
            catch( final IOException e ) {
                LOGGER.error( "Can not save config", e );
                }
            }
    }

    static class ListModelCollection<E> extends AbstractCollection<E>
    {
        private final ListModel<E> model;

        public ListModelCollection( final ListModel<E> model )
        {
            this.model = model;
        }

        @Override
        public int size()
        {
            return this.model.getSize();
        }

        @Override
        public Iterator<E> iterator()
        {
            // TODO: check concurrent modification final int size = size();

            return new Iterator<E>()
            {
                int i = 0;

                @Override
                public boolean hasNext()
                {
                    return i < size();
                }
                @Override
                public E next()
                {
                    try {
                        return model.getElementAt( i++ );
                        }
                    catch( final Exception e ) {
                        throw new NoSuchElementException( e.getMessage() );
                        }
                }
                @Override
                public void remove()
                {
                    throw new UnsupportedOperationException();
                }
            };
        }
    }
}
