package cx.ath.choisnet.tools.duplicatefiles.gui.panel;

import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import cx.ath.choisnet.swing.XTextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public abstract class JPanelSelectFoldersOrFilesWB extends JPanel
{
    private static final long serialVersionUID = 1L;
    protected JTable jTableSelectedFoldersOrFiles;
    protected XTextField jTextFieldCurrentDir;
    protected JButton jButtonSelectDir;
    protected JButton jButtonSelectFile;
    protected JButton jButtonAddDir;
    protected JButton jButtonRemEntry;

    /**
     * Create the panel.
     */
    public JPanelSelectFoldersOrFilesWB()
    {
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{0, 0, 0};
        gridBagLayout.rowHeights = new int[]{23, 0, 0, 0};
        gridBagLayout.columnWeights = new double[]{0.0, 1.0, 0.0};
        gridBagLayout.rowWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
        setLayout(gridBagLayout);

        jButtonSelectFile = new JButton("Add File");
        jButtonSelectFile.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                jButtonSelectFileMouseMousePressed(e);
            }
        });
        GridBagConstraints gbc_jButtonSelectFile = new GridBagConstraints();
        gbc_jButtonSelectFile.fill = GridBagConstraints.HORIZONTAL;
        gbc_jButtonSelectFile.insets = new Insets(0, 0, 5, 5);
        gbc_jButtonSelectFile.anchor = GridBagConstraints.NORTH;
        gbc_jButtonSelectFile.gridx = 0;
        gbc_jButtonSelectFile.gridy = 0;
        add(jButtonSelectFile, gbc_jButtonSelectFile);

        jTextFieldCurrentDir = createXTextField();
        GridBagConstraints gbc_jTextFieldCurrentDir = new GridBagConstraints();
        gbc_jTextFieldCurrentDir.gridheight = 2;
        gbc_jTextFieldCurrentDir.insets = new Insets(0, 0, 5, 5);
        gbc_jTextFieldCurrentDir.fill = GridBagConstraints.HORIZONTAL;
        gbc_jTextFieldCurrentDir.gridx = 1;
        gbc_jTextFieldCurrentDir.gridy = 0;
        add(jTextFieldCurrentDir, gbc_jTextFieldCurrentDir);

        jButtonAddDir = new JButton("Append");
        jButtonAddDir.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                jButtonAddDirMouseMousePressed( e );
                }
        });
        GridBagConstraints gbc_jButtonAddDir = new GridBagConstraints();
        gbc_jButtonAddDir.fill = GridBagConstraints.HORIZONTAL;
        gbc_jButtonAddDir.insets = new Insets(0, 0, 5, 0);
        gbc_jButtonAddDir.gridx = 2;
        gbc_jButtonAddDir.gridy = 0;
        add(jButtonAddDir, gbc_jButtonAddDir);

        jButtonSelectDir = new JButton("Add Folder");
        jButtonSelectDir.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                jButtonSelectDirMouseMousePressed( e );
            }
        });
        GridBagConstraints gbc_jButtonSelectDir = new GridBagConstraints();
        gbc_jButtonSelectDir.fill = GridBagConstraints.HORIZONTAL;
        gbc_jButtonSelectDir.insets = new Insets(0, 0, 5, 5);
        gbc_jButtonSelectDir.gridx = 0;
        gbc_jButtonSelectDir.gridy = 1;
        add(jButtonSelectDir, gbc_jButtonSelectDir);

        jButtonRemEntry = new JButton("Remove");
        jButtonRemEntry.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                jButtonRemEntryMouseMousePressed( e );
            }
        });
        GridBagConstraints gbc_jButtonRemEntry = new GridBagConstraints();
        gbc_jButtonRemEntry.fill = GridBagConstraints.HORIZONTAL;
        gbc_jButtonRemEntry.insets = new Insets(0, 0, 5, 0);
        gbc_jButtonRemEntry.gridx = 2;
        gbc_jButtonRemEntry.gridy = 1;
        add(jButtonRemEntry, gbc_jButtonRemEntry);

        JScrollPane scrollPane = new JScrollPane();
        GridBagConstraints gbc_scrollPane = new GridBagConstraints();
        gbc_scrollPane.gridwidth = 3;
        gbc_scrollPane.fill = GridBagConstraints.BOTH;
        gbc_scrollPane.gridx = 0;
        gbc_scrollPane.gridy = 2;
        add(scrollPane, gbc_scrollPane);

        jTableSelectedFoldersOrFiles = new JTable();
        scrollPane.setViewportView(jTableSelectedFoldersOrFiles);
    }

    protected abstract void jButtonSelectFileMouseMousePressed(MouseEvent e);
    protected abstract void jButtonSelectDirMouseMousePressed(MouseEvent e);
    protected abstract void jButtonAddDirMouseMousePressed(MouseEvent e);
    protected abstract void jButtonRemEntryMouseMousePressed(MouseEvent e);

    /**
     * @wbp.factory
     */
    public static XTextField createXTextField() {
        return new XTextField();
    }
}
