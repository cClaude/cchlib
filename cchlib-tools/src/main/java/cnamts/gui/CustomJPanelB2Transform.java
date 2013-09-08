package cnamts.gui;

import javax.swing.JPanel;

import java.awt.GridBagLayout;
import javax.swing.JTextField;
import java.awt.GridBagConstraints;
import javax.swing.JLabel;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JCheckBox;
import javax.swing.JSpinner;
import javax.swing.SpinnerListModel;
import com.googlecode.cchlib.swing.textfield.LimitedIntegerJTextField;


/**
 *
 */
@Deprecated
public class CustomJPanelB2Transform
    extends JPanel
        implements com.googlecode.cchlib.swing.batchrunner.EnableListener
{
    private static final long serialVersionUID = 1L;
    private JTextField extentionJTextField;
    private LimitedIntegerJTextField lineLengthJTextField;
    private JSpinner charReplacementJSpinner;

    /**
     *
     */
    public CustomJPanelB2Transform()
    {
        {
            GridBagLayout gridBagLayout = new GridBagLayout();
            gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0};
            gridBagLayout.rowHeights = new int[]{0, 0, 0, 0};
            gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
            gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
            setLayout(gridBagLayout);
        }
        {
            extentionJTextField = new JTextField();
            extentionJTextField.setText(".b2-128");
            GridBagConstraints gbc_extentionJTextField = new GridBagConstraints();
            gbc_extentionJTextField.gridwidth = 2;
            gbc_extentionJTextField.fill = GridBagConstraints.HORIZONTAL;
            gbc_extentionJTextField.insets = new Insets(0, 0, 5, 5);
            gbc_extentionJTextField.gridx = 1;
            gbc_extentionJTextField.gridy = 0;
            add(extentionJTextField, gbc_extentionJTextField);
            extentionJTextField.setColumns(8);

            JLabel extentionJLabel = new JLabel("Extention des nouveaux fichiers :");
            extentionJLabel.setLabelFor( extentionJTextField );
            GridBagConstraints gbc_extentionJLabel = new GridBagConstraints();
            gbc_extentionJLabel.anchor = GridBagConstraints.EAST;
            gbc_extentionJLabel.insets = new Insets(0, 0, 5, 5);
            gbc_extentionJLabel.gridx = 0;
            gbc_extentionJLabel.gridy = 0;
            add(extentionJLabel, gbc_extentionJLabel);
        }
        {
            lineLengthJTextField = createLineLengthLimitedIntegerJTextField();
            GridBagConstraints gbc_lineLengthJTextField = new GridBagConstraints();
            gbc_lineLengthJTextField.insets = new Insets(0, 0, 5, 5);
            gbc_lineLengthJTextField.fill = GridBagConstraints.HORIZONTAL;
            gbc_lineLengthJTextField.gridx = 1;
            gbc_lineLengthJTextField.gridy = 1;
            add(lineLengthJTextField, gbc_lineLengthJTextField);

            JLabel lineLengthJLabel = new JLabel("Longueur de l'enregistrement (128) :");
            lineLengthJLabel.setLabelFor( lineLengthJTextField );
            GridBagConstraints gbc_lineLengthJLabel = new GridBagConstraints();
            gbc_lineLengthJLabel.anchor = GridBagConstraints.EAST;
            gbc_lineLengthJLabel.insets = new Insets(0, 0, 5, 5);
            gbc_lineLengthJLabel.gridx = 0;
            gbc_lineLengthJLabel.gridy = 1;
            add(lineLengthJLabel, gbc_lineLengthJLabel);

            final JCheckBox lineLengthJCheckBox = new JCheckBox("Effectuer l'insertion de saut de ligne");
            lineLengthJCheckBox.setSelected(true);
            lineLengthJCheckBox.setEnabled(true);
            lineLengthJCheckBox.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    lineLengthJTextField.setEnabled(
                        lineLengthJCheckBox.isSelected()
                        );
                }});
            GridBagConstraints gbc_lineLengthJCheckBox = new GridBagConstraints();
            gbc_lineLengthJCheckBox.fill = GridBagConstraints.BOTH;
            gbc_lineLengthJCheckBox.gridwidth = 2;
            gbc_lineLengthJCheckBox.insets = new Insets(0, 0, 5, 5);
            gbc_lineLengthJCheckBox.gridx = 2;
            gbc_lineLengthJCheckBox.gridy = 1;
            add(lineLengthJCheckBox, gbc_lineLengthJCheckBox);
        }
        {
            charReplacementJSpinner = createReplacementCharJSpinner();
            GridBagConstraints gbc_charReplacementJSpinner = new GridBagConstraints();
            gbc_charReplacementJSpinner.fill = GridBagConstraints.HORIZONTAL;
            gbc_charReplacementJSpinner.insets = new Insets(0, 0, 0, 5);
            gbc_charReplacementJSpinner.gridx = 1;
            gbc_charReplacementJSpinner.gridy = 2;
            add(charReplacementJSpinner, gbc_charReplacementJSpinner);

            JLabel charReplacementJLabel = new JLabel("Caractère de remplacement :");
            GridBagConstraints gbc_charReplacementJLabel = new GridBagConstraints();
            gbc_charReplacementJLabel.anchor = GridBagConstraints.EAST;
            gbc_charReplacementJLabel.insets = new Insets(0, 0, 0, 5);
            gbc_charReplacementJLabel.gridx = 0;
            gbc_charReplacementJLabel.gridy = 2;
            add(charReplacementJLabel, gbc_charReplacementJLabel);

            final JCheckBox charReplacementJCheckBox = new JCheckBox("Effectuer le remplacement des valeurs non affichables");
            charReplacementJCheckBox.setSelected(true);
            charReplacementJCheckBox.setEnabled(true);
            charReplacementJCheckBox.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    charReplacementJSpinner.setEnabled(
                        charReplacementJCheckBox.isSelected()
                        );
                }});
            GridBagConstraints gbc_charReplacementJCheckBox = new GridBagConstraints();
            gbc_charReplacementJCheckBox.gridwidth = 2;
            gbc_charReplacementJCheckBox.gridx = 2;
            gbc_charReplacementJCheckBox.gridy = 2;
            add(charReplacementJCheckBox, gbc_charReplacementJCheckBox);
        }
        super.setEnabled(false);
    }

    @Override//EnableListener//javax.swing.JComponent
    public void setEnabled( final boolean enabled )
    {
        super.setEnabled( enabled );

        this.extentionJTextField.setEnabled( enabled );
        this.lineLengthJTextField.setEnabled( enabled );
        this.charReplacementJSpinner.setEnabled( enabled );
    }

    public Character getReplacementChar()
    {
        if( charReplacementJSpinner.isEnabled() ) {
            Object  value = charReplacementJSpinner.getValue();
            String  str   = String.class.cast( value );

            return new Character( str.charAt( 0 ) );
            }
        else {
            return null;
            }
    }

    public Integer getLineLength()
    {
        if( lineLengthJTextField.isEnabled() ) {
            return this.lineLengthJTextField.getValue();
            }
        else {
            return null;
            }
    }

    public String getFileExtension()
    {
        return extentionJTextField.getText();
    }

    /**
     * @wbp.factory
     */
    public static LimitedIntegerJTextField createLineLengthLimitedIntegerJTextField()
    {
        LimitedIntegerJTextField textField = new LimitedIntegerJTextField();
        textField.setText( "128" );
        textField.setColumns( 5 );
        return textField;
    }

    /**
     * @wbp.factory
     */
    public static JSpinner createReplacementCharJSpinner()
    {
        Object[] spinnerValues = { "#", ".", "×", "°", "­", "·", "-" };
        SpinnerListModel spinnerListModel = new SpinnerListModel( spinnerValues );

        JSpinner spinner = new JSpinner( spinnerListModel );
        return spinner;
    }

}
