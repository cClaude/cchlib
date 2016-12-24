package com.googlecode.cchlib.tools.downloader;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GenericDownloaderUIPanelEntry extends JPanel
{
    private static final long serialVersionUID = 1L;

    private final JLabel            jLabelDescription;
    private final JLabel            jLabelSelectedDescription;
    private final JComboBox<String> jComboBox;
    private final ArrayList<Item>   itemList = new ArrayList<>();

    public interface Item extends Serializable
    {
        String getJComboBoxText();
        String getSelectedDescription();
    }

    @SuppressWarnings("squid:S00117")
    public GenericDownloaderUIPanelEntry( final ActionListener actionListener )
    {
        final GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{32, 32, 32, 0};
        gridBagLayout.rowHeights = new int[]{14, 0};
        gridBagLayout.columnWeights = new double[]{1.0, 1.0, 2.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{0.0, Double.MIN_VALUE};
        setLayout(gridBagLayout);

        this.jLabelDescription = new JLabel();
        final GridBagConstraints gbc_jLabelDescription = new GridBagConstraints();
        gbc_jLabelDescription.fill = GridBagConstraints.HORIZONTAL;
        gbc_jLabelDescription.insets = new Insets(0, 0, 0, 5);
        gbc_jLabelDescription.anchor = GridBagConstraints.NORTH;
        gbc_jLabelDescription.gridx = 0;
        gbc_jLabelDescription.gridy = 0;
        add(this.jLabelDescription, gbc_jLabelDescription);

        this.jComboBox = new JComboBox<>();
        this.jComboBox.addActionListener(e -> {
            final int index = GenericDownloaderUIPanelEntry.this.jComboBox.getSelectedIndex();
            updateLabel( index );
        });
        this.jComboBox.addActionListener( actionListener );
        final GridBagConstraints gbc_jComboBox = new GridBagConstraints();
        gbc_jComboBox.insets = new Insets(0, 0, 0, 5);
        gbc_jComboBox.fill = GridBagConstraints.HORIZONTAL;
        gbc_jComboBox.gridx = 1;
        gbc_jComboBox.gridy = 0;
        add(this.jComboBox, gbc_jComboBox);

        this.jLabelSelectedDescription = new JLabel();
        final GridBagConstraints gbc_jLabelSelectedDescription = new GridBagConstraints();
        gbc_jLabelSelectedDescription.fill = GridBagConstraints.HORIZONTAL;
        gbc_jLabelSelectedDescription.gridx = 2;
        gbc_jLabelSelectedDescription.gridy = 0;
        add(this.jLabelSelectedDescription, gbc_jLabelSelectedDescription);
    }

    private void updateLabel( final int index )
    {
        if( index < this.itemList.size() ) {
            this.jLabelSelectedDescription.setText( this.itemList.get( index ).getSelectedDescription() );
            }
    }

    public void setDescription( final String description )
    {
        this.jLabelDescription.setText( description );
    }

    public void setJComboBoxEntry( final List<Item> itemList )
    {
        this.jComboBox.removeAllItems();
        this.itemList.clear();

        for( final Item item : itemList ) {
            this.itemList.add( item );
            this.jComboBox.addItem( item.getJComboBoxText() );
            }

        updateLabel( 0 );
    }

    public void setEnabledAllComponents( final boolean enabled )
    {
        this.jComboBox.setEnabled( enabled );
    }

    public Item getSelectedItem()
    {
        return this.itemList.get( this.jComboBox.getSelectedIndex() );
    }
}
