package com.googlecode.cchlib.swing.textfield;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.List;
import javax.swing.JLabel;
import com.googlecode.cchlib.swing.combobox.AutoCompleteComboBox;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AutoCompleteTextFieldTestJPanel extends JPanel
{
    private static final long serialVersionUID = 1L;
    //private JTextField textField;
    private AutoCompleteTextField textField;
    //private JComboBox<String> comboBox;
    private AutoCompleteComboBox comboBox;
    private JList<String> list;
    private JCheckBox chckbxCaseSensitive;
    private JCheckBox chckbxStrict;
    private JButton btnApply;
    private Params params;

    /*
    private ListModel<String> listModel = new AbstractListModel<String>()
    {
        private static final long serialVersionUID = 1L;

        @Override
        public int getSize()
        {
            return params.getDataList().size();
        }

        @Override
        public String getElementAt( int index )
        {
            return params.getDataList().get( index );
        }
    };
   */
    private DefaultListModel<String> listModel;

    public interface Params
    {
        List<String> getDataList();
    }
    /**
     * Create the panel.
     * @param valuesList
     */
    public AutoCompleteTextFieldTestJPanel( final Params params )
    {
        this.params    = params;
        this.listModel = new DefaultListModel<String>();

        for( String str : params.getDataList() ) {
            this.listModel.addElement( str );
            }

        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{60, 0, 0};
        gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0};
        gridBagLayout.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
        setLayout(gridBagLayout);

        {
            //textField = new JTextField();
            textField = new AutoCompleteTextField( params.getDataList() );
            GridBagConstraints gbc_textField = new GridBagConstraints();
            gbc_textField.insets = new Insets(0, 0, 5, 5);
            gbc_textField.fill = GridBagConstraints.BOTH;
            gbc_textField.gridx = 0;
            gbc_textField.gridy = 0;
            add(textField, gbc_textField);
            //textField.setColumns(10);
        }
        {
            chckbxCaseSensitive = new JCheckBox("Case Sensitive");
            GridBagConstraints gbc_chckbxCaseSensitive = new GridBagConstraints();
            gbc_chckbxCaseSensitive.fill = GridBagConstraints.HORIZONTAL;
            gbc_chckbxCaseSensitive.insets = new Insets(0, 0, 5, 0);
            gbc_chckbxCaseSensitive.gridx = 1;
            gbc_chckbxCaseSensitive.gridy = 0;
            add(chckbxCaseSensitive, gbc_chckbxCaseSensitive);
        }
        {
            //comboBox = new JComboBox<String>();
            comboBox = new AutoCompleteComboBox( params.getDataList() );
            GridBagConstraints gbc_comboBox = new GridBagConstraints();
            gbc_comboBox.insets = new Insets(0, 0, 5, 5);
            gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
            gbc_comboBox.gridx = 0;
            gbc_comboBox.gridy = 1;
            add(comboBox, gbc_comboBox);
        }
        {
            chckbxStrict = new JCheckBox("Strict");
            GridBagConstraints gbc_chckbxStrict = new GridBagConstraints();
            gbc_chckbxStrict.fill = GridBagConstraints.HORIZONTAL;
            gbc_chckbxStrict.insets = new Insets(0, 0, 5, 0);
            gbc_chckbxStrict.gridx = 1;
            gbc_chckbxStrict.gridy = 1;
            add(chckbxStrict, gbc_chckbxStrict);
        }
        {
            JLabel lblTestForAutocompletjtextfield = new JLabel("Test for AutoCompletJTextField");
            GridBagConstraints gbc_lblTestForAutocompletjtextfield = new GridBagConstraints();
            gbc_lblTestForAutocompletjtextfield.fill = GridBagConstraints.HORIZONTAL;
            gbc_lblTestForAutocompletjtextfield.insets = new Insets(0, 0, 5, 5);
            gbc_lblTestForAutocompletjtextfield.gridx = 0;
            gbc_lblTestForAutocompletjtextfield.gridy = 2;
            add(lblTestForAutocompletjtextfield, gbc_lblTestForAutocompletjtextfield);
        }
        {
            list = new JList<String>();
            list.setModel( listModel );
            GridBagConstraints gbc_list = new GridBagConstraints();
            gbc_list.insets = new Insets(0, 0, 5, 0);
            gbc_list.fill = GridBagConstraints.BOTH;
            gbc_list.gridx = 1;
            gbc_list.gridy = 2;
            add(list, gbc_list);
        }
        {
            btnApply = new JButton("Apply");
            btnApply.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    onApply();
                }
            });
            GridBagConstraints gbc_btnApply = new GridBagConstraints();
            gbc_btnApply.gridx = 1;
            gbc_btnApply.gridy = 3;
            add(btnApply, gbc_btnApply);
        }
    }

    protected void onApply()
    {
        boolean isCaseSensitive = chckbxCaseSensitive.isSelected();

        textField.setCaseSensitive( isCaseSensitive  );
        comboBox.setCaseSensitive( isCaseSensitive );

        boolean isStrict = chckbxStrict.isSelected();

        textField.setStrict( isStrict );
        comboBox.setStrict( isStrict );

        List<String> dataList = params.getDataList();

        textField.setDataList( dataList );
        comboBox.setDataList( dataList );
    }
}

