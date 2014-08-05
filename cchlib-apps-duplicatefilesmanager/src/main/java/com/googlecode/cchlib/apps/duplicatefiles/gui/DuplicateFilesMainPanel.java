package com.googlecode.cchlib.apps.duplicatefiles.gui;

import java.awt.CardLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.util.TooManyListenersException;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JPanel;
import com.googlecode.cchlib.apps.duplicatefiles.AppToolKit;
import com.googlecode.cchlib.apps.duplicatefiles.AppToolKitService;
import com.googlecode.cchlib.apps.duplicatefiles.gui.panels.JPanelConfig;
import com.googlecode.cchlib.apps.duplicatefiles.gui.panels.JPanelSelectFoldersOrFiles;
import com.googlecode.cchlib.apps.duplicatefiles.gui.panels.confirm.JPanelConfirm;
import com.googlecode.cchlib.apps.duplicatefiles.gui.panels.result.JPanelResult;
import com.googlecode.cchlib.apps.duplicatefiles.gui.panels.search.JPanelSearching;
import com.googlecode.cchlib.apps.duplicatefiles.gui.panels.search.JPanelSearchingParallel;
import com.googlecode.cchlib.i18n.core.AutoI18nCore;
import com.googlecode.cchlib.i18n.core.I18nAutoCoreUpdatable;

/**
 * Main frame layout for DuplicateFilesManager
 */
public class DuplicateFilesMainPanel
    extends JPanel
        implements I18nAutoCoreUpdatable
{
    private static final long serialVersionUID = 1L;

    public static final String ACTIONCMD_RESTART = "ACTIONCMD_RESTART";
    public static final String ACTIONCMD_NEXT = "ACTIONCMD_NEXT";
    public static final String ACTIONCMD_CANCEL = "ACTIONCMD_CANCEL";

    private ActionListener              mainActionListener;
    private CardLayout                  jPanelMainCardLayout;
    private JButton                     jButtonCancel;
    private JButton                     jButtonNextStep;
    private JButton                     jButtonRestart;
    private JPanel                      jPanelMain;
    private JPanelConfig                jPanel1Config;
    private JPanelConfirm               jPanel4Confirm;
    private JPanelResult                jPanel3Result;
    private JPanelSearching             jPanel2Searching;
    private JPanelSelectFoldersOrFiles  jPanel0Select;

    /**
     * Fake JPannel for builder
     */
    public DuplicateFilesMainPanel()
    {
        //Empty
    }

    /**
     * Create the frame.
     * @param dfToolKit
     * @throws TooManyListenersException
     * @throws HeadlessException
     */
    public DuplicateFilesMainPanel(
        final ActionListener mainActionListener
        )
        throws HeadlessException, TooManyListenersException
    {
        this.mainActionListener = mainActionListener;

        final GridBagLayout gbl_contentPane = new GridBagLayout();
        gbl_contentPane.columnWidths = new int[]{0, 0, 0, 0, 0};
        gbl_contentPane.rowHeights = new int[]{100, 0, 0};
        gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
        gbl_contentPane.rowWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
        this.setLayout(gbl_contentPane);

        {
            jPanelMain = new JPanel();
            final GridBagConstraints gbc_jPanelMain = new GridBagConstraints();
            gbc_jPanelMain.anchor = GridBagConstraints.WEST;
            gbc_jPanelMain.gridwidth = 4;
            gbc_jPanelMain.gridheight = 1;
            gbc_jPanelMain.insets = new Insets(0, 0, 5, 0);
            gbc_jPanelMain.fill = GridBagConstraints.BOTH;
            gbc_jPanelMain.gridx = 0;
            gbc_jPanelMain.gridy = 0;
            this.add(jPanelMain, gbc_jPanelMain);
            jPanelMainCardLayout = new CardLayout(0, 0);
            jPanelMain.setLayout( jPanelMainCardLayout );

            jPanel0Select = createJPanel0Select();
            jPanelMain.add( jPanel0Select, DuplicateFilesState.STATE_SELECT_DIRS.name() );

            jPanel1Config = createJPanel1Config();
            jPanelMain.add( jPanel1Config, DuplicateFilesState.STATE_SEARCH_CONFIG.name() );

            jPanel2Searching = createJPanel2Searching();
            jPanelMain.add( jPanel2Searching, DuplicateFilesState.STATE_SEARCHING.name() );

            jPanel3Result = createJPanel3Result();
            jPanelMain.add( jPanel3Result, DuplicateFilesState.STATE_RESULTS.name() );

            jPanel4Confirm = createJPanel4Confirm();
            jPanelMain.add( jPanel4Confirm, DuplicateFilesState.STATE_CONFIRM.name() );
        }

        jButtonRestart = new JButton("Restart");
        jButtonRestart.setActionCommand( ACTIONCMD_RESTART );
        jButtonRestart.addActionListener( getActionListener() );
        final GridBagConstraints gbc_jButtonRestart = new GridBagConstraints();
        gbc_jButtonRestart.anchor = GridBagConstraints.WEST;
        gbc_jButtonRestart.insets = new Insets(0, 0, 0, 5);
        gbc_jButtonRestart.gridx = 0;
        gbc_jButtonRestart.gridy = 1;
        this.add(jButtonRestart, gbc_jButtonRestart);

        jButtonCancel = new JButton("Cancel");
        jButtonCancel.setActionCommand( ACTIONCMD_CANCEL );
        jButtonCancel.addActionListener( getActionListener() );
        final GridBagConstraints gbc_jButtonCancel = new GridBagConstraints();
        gbc_jButtonCancel.anchor = GridBagConstraints.WEST;
        gbc_jButtonCancel.insets = new Insets(0, 0, 0, 5);
        gbc_jButtonCancel.gridx = 1;
        gbc_jButtonCancel.gridy = 1;
        this.add(jButtonCancel, gbc_jButtonCancel);

        jButtonNextStep = new JButton( "Next" );
        jButtonNextStep.setActionCommand( ACTIONCMD_NEXT );
        jButtonNextStep.addActionListener( getActionListener() );
        final GridBagConstraints gbc_jButtonNextStep = new GridBagConstraints();
        gbc_jButtonNextStep.anchor = GridBagConstraints.EAST;
        gbc_jButtonNextStep.gridx = 3;
        gbc_jButtonNextStep.gridy = 1;
        this.add(getJButtonNextStep(), gbc_jButtonNextStep);
    }

    private ActionListener getActionListener()
    {
        return this.mainActionListener;
    }

    protected JButton getJButtonNextStep()
    {
        return jButtonNextStep;
    }

    protected JButton getJButtonCancel()
    {
        return jButtonCancel;
    }

    public JPanelSelectFoldersOrFiles getJPanel0Select()
    {
        return jPanel0Select;
    }

    protected JPanelConfig getJPanel1Config()
    {
        return jPanel1Config;
    }

    protected JPanelSearching getJPanel2Searching()
    {
        return jPanel2Searching;
    }

    public JPanelResult getJPanel3Result()
    {
        return jPanel3Result;
    }

    protected JPanelConfirm getJPanel4Confirm()
    {
        return jPanel4Confirm;
    }

    protected AbstractButton getJButtonRestart()
    {
        return jButtonRestart;
    }

    protected void selectedPanel( final DuplicateFilesState state )
    {
        jPanelMainCardLayout.show(
                jPanelMain,
                //Integer.toString( state )
                state.name()
                );
    }

    public final AppToolKit getAppToolKit()
    {
        return AppToolKitService.getInstance().getAppToolKit();
    }

    /**
     * @throws TooManyListenersException
     * @throws HeadlessException
     * @wbp.factory
     */
    public JPanelSelectFoldersOrFiles createJPanel0Select()
        throws HeadlessException, TooManyListenersException
    {
        return new JPanelSelectFoldersOrFiles();
    }

    /**
     * @wbp.factory
     */
    public JPanelConfig createJPanel1Config()
    {
        return new JPanelConfig();
    }

    /**
     * @wbp.factory
     */
    public JPanelSearching createJPanel2Searching()
    {
        return new JPanelSearchingParallel();
    }

    /**
     * @wbp.factory
     */
    public JPanelResult createJPanel3Result()
    {
        return new JPanelResult();
    }

    /**
     * @wbp.factory
     */
    public JPanelConfirm createJPanel4Confirm()
    {
        return new JPanelConfirm();
    }

    @Override // I18nAutoCoreUpdatable
    public void performeI18n( final AutoI18nCore autoI18n )
    {
        autoI18n.performeI18n(this,this.getClass());
        autoI18n.performeI18n(getJPanel0Select(),getJPanel0Select().getClass());
        getJPanel1Config().performeI18n(autoI18n);
        getJPanel2Searching().performeI18n( autoI18n );
        getJPanel3Result().performeI18n(autoI18n);
        autoI18n.performeI18n(getJPanel4Confirm(),getJPanel4Confirm().getClass());
    }

    public void initFixComponents()
    {
        getJPanel0Select().initFixComponents();
        getJPanel2Searching().initFixComponents();
    }
}
