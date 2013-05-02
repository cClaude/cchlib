package com.googlecode.cchlib.apps.duplicatefiles.common;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.apps.duplicatefiles.Resources;
import com.googlecode.cchlib.apps.duplicatefiles.ResourcesLoader;
import com.googlecode.cchlib.i18n.I18nForce;
import com.googlecode.cchlib.i18n.I18nIgnore;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.border.TitledBorder;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.UIManager;

/**
 *
 */
public abstract class AboutPanel extends JPanel 
{
    private static final long serialVersionUID = 1L;
    private static final String DESCRIPTION = "About brief (dev text)";
    
    private boolean click;
    private Resources resources;

    @I18nIgnore private JLabel jLabel_authorName;
    private JLabel jLabel_author;    // I18n
    private JLabel jLabel_copyRight; // I18n
    private JLabel jLabel_version;   // I18n
    private JButton jButton_Ok;
    
    @I18nForce private JTextArea jTextArea;
	private TitledBorder titleBorder;

    
    /** 
     * Creates new form AboutPanel for windows builder ONLY.
     */
    @Deprecated // for windows builder ONLY.
    public AboutPanel() 
    {
        this( ResourcesLoader.getResources() );
    }

    
    /** 
     * Creates new form AboutPanel 
     */
    public AboutPanel( final Resources resources ) 
    {
        this.resources = resources;
        
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{20, 20, 20, 20, 0};
        gridBagLayout.rowHeights = new int[]{22, 22, 22, 100, 0, 0};
        gridBagLayout.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
        setLayout(gridBagLayout);
        
        {
            jLabel_authorName = new JLabel();
            jLabel_authorName.setForeground(new Color(0, 0, 255));
            jLabel_authorName.setText( resources.getAuthorName() );
            jLabel_authorName.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked( MouseEvent evt ) {
                    authorMouseClicked(evt);
                }
                @Override
                public void mouseEntered( MouseEvent evt ) {
                    authorMouseEntered(evt);
                }
                @Override
                public void mouseExited( MouseEvent evt ) {
                    authorMouseExited(evt);
                }
            });

            GridBagConstraints gbc_jLabel_authorName = new GridBagConstraints();
            gbc_jLabel_authorName.fill = GridBagConstraints.BOTH;
            gbc_jLabel_authorName.insets = new Insets(0, 0, 5, 5);
            gbc_jLabel_authorName.gridx = 2;
            gbc_jLabel_authorName.gridy = 1;
            add(jLabel_authorName, gbc_jLabel_authorName);
        }
        {
            JLabel jLabel_logo = new javax.swing.JLabel();
            jLabel_logo.setIcon( resources.getLogoIcon() ); // NOI18N
            GridBagConstraints gbc_jLabel_logo = new GridBagConstraints();
            gbc_jLabel_logo.gridwidth = 4;
            gbc_jLabel_logo.fill = GridBagConstraints.BOTH;
            gbc_jLabel_logo.insets = new Insets(0, 0, 5, 0);
            gbc_jLabel_logo.gridx = 0;
            gbc_jLabel_logo.gridy = 0;
            add(jLabel_logo, gbc_jLabel_logo);
        }
        {
            JLabel jLabel_AppIcon = new JLabel();
            jLabel_AppIcon.setIcon( resources.getAddIcon() ); // NOI18N FIXME
            GridBagConstraints gbc_jLabel_AppIcon = new GridBagConstraints();
            gbc_jLabel_AppIcon.gridheight = 2;
            gbc_jLabel_AppIcon.fill = GridBagConstraints.BOTH;
            gbc_jLabel_AppIcon.insets = new Insets(0, 0, 5, 5);
            gbc_jLabel_AppIcon.gridx = 0;
            gbc_jLabel_AppIcon.gridy = 1;
            add(jLabel_AppIcon, gbc_jLabel_AppIcon);
        }
        {
            jLabel_author = new JLabel();
            jLabel_author.setHorizontalAlignment(SwingConstants.RIGHT);
            jLabel_author.setText( "Author :" ); 
            GridBagConstraints gbc_jLabel_author = new GridBagConstraints();
            gbc_jLabel_author.fill = GridBagConstraints.BOTH;
            gbc_jLabel_author.insets = new Insets(0, 0, 5, 5);
            gbc_jLabel_author.gridx = 1;
            gbc_jLabel_author.gridy = 1;
            add(jLabel_author, gbc_jLabel_author);
        }
        {
            jLabel_copyRight = new JLabel();
            jLabel_copyRight.setText( "copyRight" ); // NOI18N
            GridBagConstraints gbc_jLabel_copyRight = new GridBagConstraints();
            gbc_jLabel_copyRight.fill = GridBagConstraints.BOTH;
            gbc_jLabel_copyRight.insets = new Insets(0, 0, 5, 0);
            gbc_jLabel_copyRight.gridx = 3;
            gbc_jLabel_copyRight.gridy = 1;
            add(jLabel_copyRight, gbc_jLabel_copyRight);
        }
        {
            jLabel_version = new JLabel("Version :");
            jLabel_version.setHorizontalAlignment(SwingConstants.RIGHT);
            GridBagConstraints gbc_jLabel_version = new GridBagConstraints();
            gbc_jLabel_version.fill = GridBagConstraints.BOTH;
            gbc_jLabel_version.insets = new Insets(0, 0, 5, 5);
            gbc_jLabel_version.gridx = 1;
            gbc_jLabel_version.gridy = 2;
            add(jLabel_version, gbc_jLabel_version);
        }
        {
            JLabel jLabel_versionValue = new JLabel();
            jLabel_versionValue.setText( resources.getAboutVersion() ); // NOI18N
            GridBagConstraints gbc_jLabel_versionValue = new GridBagConstraints();
            gbc_jLabel_versionValue.fill = GridBagConstraints.BOTH;
            gbc_jLabel_versionValue.insets = new Insets(0, 0, 5, 5);
            gbc_jLabel_versionValue.gridx = 2;
            gbc_jLabel_versionValue.gridy = 2;
            add(jLabel_versionValue, gbc_jLabel_versionValue);
        }
        {
            JLabel jLabel_completeDate = new JLabel();
            jLabel_completeDate.setText( resources.getAboutVersionDate() ); // NOI18N
            GridBagConstraints gbc_jLabel_completeDate = new GridBagConstraints();
            gbc_jLabel_completeDate.fill = GridBagConstraints.BOTH;
            gbc_jLabel_completeDate.insets = new Insets(0, 0, 5, 0);
            gbc_jLabel_completeDate.gridx = 3;
            gbc_jLabel_completeDate.gridy = 2;
            add(jLabel_completeDate, gbc_jLabel_completeDate);
        }
        {
            jTextArea = new JTextArea();
            jTextArea.setBackground(UIManager.getColor("Button.background"));
            jTextArea.setFont(UIManager.getFont("Label.font"));
            jTextArea.setEnabled(false);
            jTextArea.setEditable(false);
            jTextArea.setColumns(20);
            jTextArea.setLineWrap(true);
            jTextArea.setRows(5);
            jTextArea.setText( DESCRIPTION );
            
            JScrollPane jScrollPane = new JScrollPane();
            titleBorder = BorderFactory.createTitledBorder( "Brief" );
            jScrollPane.setBorder( titleBorder );
            jScrollPane.setViewportView( jTextArea );

            GridBagConstraints gbc_jScrollPane = new GridBagConstraints();
            gbc_jScrollPane.insets = new Insets(0, 0, 5, 0);
            gbc_jScrollPane.fill = GridBagConstraints.BOTH;
            gbc_jScrollPane.gridwidth = 4;
            gbc_jScrollPane.gridx = 0;
            gbc_jScrollPane.gridy = 3;
            add(jScrollPane, gbc_jScrollPane);
        }
        {
            jButton_Ok = new JButton("OK");
            jButton_Ok.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent event) 
                {
                    buttonOKClicked();
                }
            });
            GridBagConstraints gbc_jButton_Ok = new GridBagConstraints();
            gbc_jButton_Ok.gridwidth = 4;
            gbc_jButton_Ok.gridx = 0;
            gbc_jButton_Ok.gridy = 4;
            add(jButton_Ok, gbc_jButton_Ok);
        }
    }
    
    protected abstract void buttonOKClicked();

    private void authorMouseEntered( final MouseEvent evt )
    {
        jLabel_authorName.setCursor( Cursor.getPredefinedCursor(Cursor.HAND_CURSOR) );
        jLabel_authorName.setForeground(Color.RED);
    }

    private void authorMouseExited( final MouseEvent evt )
    {
        jLabel_authorName.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        
        if( click ) {
            jLabel_authorName.setForeground(new Color(128,0,128));
            } 
        else {
            jLabel_authorName.setForeground(Color.BLUE);
            }
    }
    
    private void authorMouseClicked( final MouseEvent evt ) 
    {
        click = true;
        
        if( Desktop.isDesktopSupported() ) {
            try {
                Desktop.getDesktop().browse( resources.getSiteURI() );
                }
            catch( IOException e ) {
                Logger.getLogger( getClass() ).warn( "Error while opening: " + resources.getSiteURI(), e ); 
                }
            }
    }
}