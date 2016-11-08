// $codepro.audit.disable
package com.googlecode.cchlib.apps.duplicatefiles.swing.gui.panels.result.selector;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.util.Set;
import java.util.regex.Pattern;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.apps.duplicatefiles.swing.AppToolKit;
import com.googlecode.cchlib.apps.duplicatefiles.swing.ConfigMode;
import com.googlecode.cchlib.apps.duplicatefiles.swing.KeyFileState;
import com.googlecode.cchlib.apps.duplicatefiles.swing.services.AppToolKitService;
import com.googlecode.cchlib.i18n.annotation.I18nName;
import com.googlecode.cchlib.i18n.annotation.I18nString;
import com.googlecode.cchlib.swing.combobox.XComboBoxPattern;

@I18nName("JPanelResult.SelectByRegExp")
public class SelectByRegExp extends SelectorPanel
{
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER  = Logger.getLogger( SelectByRegExp.class );

    @I18nString private String txtPatternSyntaxExceptionTitle;

    private final JButton jButtonRegExDelete;
    private final JButton jButtonRegExRestore;
    private final JCheckBox jCheckBoxKeepOne;
    private final XComboBoxPattern xComboBoxPatternRegEx;

    private final AppToolKit dFToolKit;
    private final DuplicateData duplicateData;

    /**
     * Create the select by regular expression panel.
     */
    public SelectByRegExp( final DuplicateData duplicateData )
    {
        beSurNonFinal();

        this.dFToolKit     = AppToolKitService.getInstance().getAppToolKit();
        this.duplicateData = duplicateData;

        final Color errorColor = Color.RED;

        {
            final GridBagLayout gridBagLayout = new GridBagLayout();
            gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0};
            gridBagLayout.rowHeights = new int[]{0, 0};
            gridBagLayout.columnWeights = new double[]{1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
            gridBagLayout.rowWeights = new double[]{0.0, Double.MIN_VALUE};
            setLayout(gridBagLayout);
        }
        {
            this.xComboBoxPatternRegEx = new XComboBoxPattern();
            this.xComboBoxPatternRegEx.setErrorBackGroundColor( errorColor );
            this.xComboBoxPatternRegEx.setModel(
                new DefaultComboBoxModel<>(new String[] { ".*\\.jpg", ".*\\.gif", ".*\\.tmp" })
                );

            final GridBagConstraints gbc_xComboBoxPatternRegEx = new GridBagConstraints();
            gbc_xComboBoxPatternRegEx.fill = GridBagConstraints.BOTH;
            gbc_xComboBoxPatternRegEx.insets = new Insets(0, 0, 0, 5);
            gbc_xComboBoxPatternRegEx.gridx = 0;
            gbc_xComboBoxPatternRegEx.gridy = 0;

            add( this.xComboBoxPatternRegEx, gbc_xComboBoxPatternRegEx );
        }
        {
            final GridBagConstraints gbc_jCheckBoxKeepOne = new GridBagConstraints();
            gbc_jCheckBoxKeepOne.fill = GridBagConstraints.BOTH;
            gbc_jCheckBoxKeepOne.insets = new Insets(0, 0, 0, 5);
            gbc_jCheckBoxKeepOne.gridx = 1;
            gbc_jCheckBoxKeepOne.gridy = 0;

            this.jCheckBoxKeepOne = new JCheckBox( "Preserve one file" );
            this.jCheckBoxKeepOne.setSelected( true );
            add( this.jCheckBoxKeepOne, gbc_jCheckBoxKeepOne );
        }
        {
            final GridBagConstraints gbc_jButtonRegExDelete = new GridBagConstraints();
            gbc_jButtonRegExDelete.fill = GridBagConstraints.BOTH;
            gbc_jButtonRegExDelete.insets = new Insets(0, 0, 0, 5);
            gbc_jButtonRegExDelete.gridx = 2;
            gbc_jButtonRegExDelete.gridy = 0;

            this.jButtonRegExDelete = new JButton( "Delete" );
            this.jButtonRegExDelete.addActionListener((final ActionEvent event) -> {
                onButtonRegExDelete();
            });
            add(this.jButtonRegExDelete, gbc_jButtonRegExDelete);
        }
        {
            final GridBagConstraints gbc_jButtonRegExRestore = new GridBagConstraints();
            gbc_jButtonRegExRestore.fill = GridBagConstraints.BOTH;
            gbc_jButtonRegExRestore.gridx = 3;
            gbc_jButtonRegExRestore.gridy = 0;

            this.jButtonRegExRestore = new JButton( "Restore" );
            this.jButtonRegExRestore.addActionListener((final ActionEvent event) -> {
                onButtonRegExRestore();
            });
            add( this.jButtonRegExRestore, gbc_jButtonRegExRestore );
        }
    }

    private void beSurNonFinal()
    {
        this.txtPatternSyntaxExceptionTitle = "Not valid Regular Expression";
    }

    private Pattern getCurrentPattern()
    {
        try {
            return this.xComboBoxPatternRegEx.getSelectedPattern();
            }
        catch( final java.util.regex.PatternSyntaxException e ) {
          JOptionPane.showMessageDialog(
                      this,
                      e.getLocalizedMessage(),
                      this.txtPatternSyntaxExceptionTitle ,
                      JOptionPane.ERROR_MESSAGE
                      );
          return null;
              }
    }

    private void onButtonRegExDelete()
    {
        final Pattern p = getCurrentPattern();

        if( p == null ) {
            return;
            }
        final boolean keepOne = this.jCheckBoxKeepOne.isSelected();

        for( final KeyFileState f : this.duplicateData.getListModelDuplicatesFiles().getAllDuplicates() ) {
            if( !f.isSelectedToDelete() ) {
                LOGGER.info( p.matcher( f.getPath() ).matches() + "=" + f.getPath() );
                if( p.matcher( f.getPath() ).matches() ) {
                    if( keepOne ) {
                        final String            k = f.getKey();
                        final Set<KeyFileState> s = this.duplicateData.getListModelDuplicatesFiles().getStateSet( k );
                        int               c = 0;

                        for(final KeyFileState fc:s) {
                            if( !fc.isSelectedToDelete() ) {
                                c++;
                                }
                            }
                        LOGGER.info( "count=" + c );
                        if( c > 1 ) {
                            f.setSelectedToDelete( true );
                            }
                        }
                    else {
                        f.setSelectedToDelete( true );
                        }
                    }
                }
            }

        this.duplicateData.updateDisplay();
    }

    private void onButtonRegExRestore()
    {
        final Pattern p = getCurrentPattern();

        if( p == null ) {
            return;
            }

        for( final KeyFileState f : this.duplicateData.getListModelDuplicatesFiles().getAllDuplicates() ) {
            if( f.isSelectedToDelete() ) {
                if( p.matcher( f.getPath() ).matches() ) {
                    f.setSelectedToDelete( false );
                    }
                }
            }

        this.duplicateData.updateDisplay();
    }

    @Override
    public void updateDisplay()
    {
        final boolean b = !( this.dFToolKit.getPreferences().getConfigMode() == ConfigMode.BEGINNER );

        this.xComboBoxPatternRegEx.setEnabled( b );
        this.jCheckBoxKeepOne.setEnabled( b );
        this.jButtonRegExDelete.setEnabled( b );
        this.jButtonRegExRestore.setEnabled( b );
    }
}
