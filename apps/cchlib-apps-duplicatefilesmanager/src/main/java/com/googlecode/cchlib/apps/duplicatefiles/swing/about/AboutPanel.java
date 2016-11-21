// $codepro.audit.disable largeNumberOfFields, numericLiterals
package com.googlecode.cchlib.apps.duplicatefiles.swing.about;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.apps.duplicatefiles.swing.tools.Resources;
import com.googlecode.cchlib.i18n.annotation.I18n;
import com.googlecode.cchlib.i18n.annotation.I18nIgnore;
import com.googlecode.cchlib.i18n.annotation.I18nName;

@I18nName("AboutPanel")
@SuppressWarnings({"squid:S00117","squid:S1199","squid:S00116"})
public final class AboutPanel extends JPanel
{
    private static final long serialVersionUID = 1L;
    private static final String DESCRIPTION = "About brief (dev text)";

    private boolean click;
    private final Resources resources;

    @I18nIgnore private final JLabel jLabel_authorName;
    private final JLabel jLabel_author;    // I18n
    private final JLabel jLabel_copyRight; // I18n
    private final JLabel jLabel_version;   // I18n
    private final JButton jButton_Ok;

    @I18n private final JTextArea jTextArea;
    private final TitledBorder titleBorder;

    /**
     * Creates new form AboutPanel
     */
    @SuppressWarnings("squid:S1199")
    public AboutPanel(
        final Resources        resources,
        final AboutPanelAction action
        )
    {
        this.resources = resources;

        final GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{20, 20, 20, 20, 0};
        gridBagLayout.rowHeights = new int[]{22, 22, 22, 100, 0, 0};
        gridBagLayout.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
        setLayout(gridBagLayout);

        {
            this.jLabel_authorName = new JLabel();
            this.jLabel_authorName.setForeground(new Color(0, 0, 255));
            this.jLabel_authorName.setText( resources.getAuthorName() );
            this.jLabel_authorName.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked( final MouseEvent evt ) {
                    authorMouseClicked(evt);
                }
                @Override
                public void mouseEntered( final MouseEvent evt ) {
                    authorMouseEntered(evt);
                }
                @Override
                public void mouseExited( final MouseEvent evt ) {
                    authorMouseExited(evt);
                }
            });

            final GridBagConstraints gbc_jLabel_authorName = new GridBagConstraints();
            gbc_jLabel_authorName.fill = GridBagConstraints.BOTH;
            gbc_jLabel_authorName.insets = new Insets(0, 0, 5, 5);
            gbc_jLabel_authorName.gridx = 2;
            gbc_jLabel_authorName.gridy = 1;
            add(this.jLabel_authorName, gbc_jLabel_authorName);
        }
        {
            final JLabel jLabel_logo = new javax.swing.JLabel();
            jLabel_logo.setIcon( resources.getLogoIcon() ); // NOI18N
            final GridBagConstraints gbc_jLabel_logo = new GridBagConstraints();
            gbc_jLabel_logo.gridwidth = 4;
            gbc_jLabel_logo.fill = GridBagConstraints.BOTH;
            gbc_jLabel_logo.insets = new Insets(0, 0, 5, 0);
            gbc_jLabel_logo.gridx = 0;
            gbc_jLabel_logo.gridy = 0;
            add(jLabel_logo, gbc_jLabel_logo);
        }
        {
            final JLabel jLabel_AppIcon = new JLabel();
            jLabel_AppIcon.setIcon( resources.getAddIcon() ); //  FIXME NOI18N
            final GridBagConstraints gbc_jLabel_AppIcon = new GridBagConstraints();
            gbc_jLabel_AppIcon.gridheight = 2;
            gbc_jLabel_AppIcon.fill = GridBagConstraints.BOTH;
            gbc_jLabel_AppIcon.insets = new Insets(0, 0, 5, 5);
            gbc_jLabel_AppIcon.gridx = 0;
            gbc_jLabel_AppIcon.gridy = 1;
            add(jLabel_AppIcon, gbc_jLabel_AppIcon);
        }
        {
            this.jLabel_author = new JLabel();
            this.jLabel_author.setHorizontalAlignment(SwingConstants.RIGHT);
            this.jLabel_author.setText( "Author :" );
            final GridBagConstraints gbc_jLabel_author = new GridBagConstraints();
            gbc_jLabel_author.fill = GridBagConstraints.BOTH;
            gbc_jLabel_author.insets = new Insets(0, 0, 5, 5);
            gbc_jLabel_author.gridx = 1;
            gbc_jLabel_author.gridy = 1;
            add(this.jLabel_author, gbc_jLabel_author);
        }
        {
            this.jLabel_copyRight = new JLabel();
            this.jLabel_copyRight.setText( "copyRight" ); // NOI18N
            final GridBagConstraints gbc_jLabel_copyRight = new GridBagConstraints();
            gbc_jLabel_copyRight.fill = GridBagConstraints.BOTH;
            gbc_jLabel_copyRight.insets = new Insets(0, 0, 5, 0);
            gbc_jLabel_copyRight.gridx = 3;
            gbc_jLabel_copyRight.gridy = 1;
            add(this.jLabel_copyRight, gbc_jLabel_copyRight);
        }
        {
            this.jLabel_version = new JLabel("Version :");
            this.jLabel_version.setHorizontalAlignment(SwingConstants.RIGHT);
            final GridBagConstraints gbc_jLabel_version = new GridBagConstraints();
            gbc_jLabel_version.fill = GridBagConstraints.BOTH;
            gbc_jLabel_version.insets = new Insets(0, 0, 5, 5);
            gbc_jLabel_version.gridx = 1;
            gbc_jLabel_version.gridy = 2;
            add(this.jLabel_version, gbc_jLabel_version);
        }
        {
            final JLabel jLabel_versionValue = new JLabel();
            jLabel_versionValue.setText( resources.getAboutVersion() ); // NOI18N
            final GridBagConstraints gbc_jLabel_versionValue = new GridBagConstraints();
            gbc_jLabel_versionValue.fill = GridBagConstraints.BOTH;
            gbc_jLabel_versionValue.insets = new Insets(0, 0, 5, 5);
            gbc_jLabel_versionValue.gridx = 2;
            gbc_jLabel_versionValue.gridy = 2;
            add(jLabel_versionValue, gbc_jLabel_versionValue);
        }
        {
            final JLabel jLabel_completeDate = new JLabel();
            jLabel_completeDate.setText( resources.getAboutVersionDate() ); // NOI18N
            final GridBagConstraints gbc_jLabel_completeDate = new GridBagConstraints();
            gbc_jLabel_completeDate.fill = GridBagConstraints.BOTH;
            gbc_jLabel_completeDate.insets = new Insets(0, 0, 5, 0);
            gbc_jLabel_completeDate.gridx = 3;
            gbc_jLabel_completeDate.gridy = 2;
            add(jLabel_completeDate, gbc_jLabel_completeDate);
        }
        {
            this.jTextArea = new JTextArea();
            this.jTextArea.setBackground(UIManager.getColor("Button.background"));
            this.jTextArea.setFont(UIManager.getFont("Label.font"));
            this.jTextArea.setEnabled(false);
            this.jTextArea.setEditable(false);
            this.jTextArea.setColumns(20);
            this.jTextArea.setLineWrap(true);
            this.jTextArea.setRows(5);
            this.jTextArea.setText( DESCRIPTION );

            final JScrollPane jScrollPane = new JScrollPane();
            this.titleBorder = BorderFactory.createTitledBorder( "Brief" );
            jScrollPane.setBorder( this.titleBorder );
            jScrollPane.setViewportView( this.jTextArea );

            final GridBagConstraints gbc_jScrollPane = new GridBagConstraints();
            gbc_jScrollPane.insets = new Insets(0, 0, 5, 0);
            gbc_jScrollPane.fill = GridBagConstraints.BOTH;
            gbc_jScrollPane.gridwidth = 4;
            gbc_jScrollPane.gridx = 0;
            gbc_jScrollPane.gridy = 3;
            add(jScrollPane, gbc_jScrollPane);
        }
        {
            this.jButton_Ok = new JButton("OK");
            this.jButton_Ok.addActionListener(
                    (final ActionEvent event) -> action.buttonOKClicked()
                    );
            final GridBagConstraints gbc_jButton_Ok = new GridBagConstraints();
            gbc_jButton_Ok.gridwidth = 4;
            gbc_jButton_Ok.gridx = 0;
            gbc_jButton_Ok.gridy = 4;
            add(this.jButton_Ok, gbc_jButton_Ok);
        }
    }

    private void authorMouseEntered( final MouseEvent evt )
    {
        this.jLabel_authorName.setCursor( Cursor.getPredefinedCursor(Cursor.HAND_CURSOR) );
        this.jLabel_authorName.setForeground(Color.RED);
    }

    private void authorMouseExited( final MouseEvent evt )
    {
        this.jLabel_authorName.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

        if( this.click ) {
            this.jLabel_authorName.setForeground(new Color(128,0,128));
            }
        else {
            this.jLabel_authorName.setForeground(Color.BLUE);
            }
    }

    private void authorMouseClicked( final MouseEvent evt )
    {
        this.click = true;

        if( Desktop.isDesktopSupported() ) {
            try {
                Desktop.getDesktop().browse( this.resources.getSiteURI() );
                }
            catch( final IOException e ) {
                Logger.getLogger( getClass() ).warn( "Error while opening: " + this.resources.getSiteURI(), e );
                }
            }
    }
}
