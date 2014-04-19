package com.googlecode.cchlib.apps.duplicatefiles;

import com.googlecode.cchlib.apps.duplicatefiles.prefs.Preferences;
import com.googlecode.cchlib.i18n.AutoI18nConfig;
import com.googlecode.cchlib.i18n.resources.I18nResourceBundleName;
import com.googlecode.cchlib.swing.filechooser.JFileChooserInitializer;
import java.awt.Component;
import java.awt.Frame;
import java.awt.Window;
import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import javax.swing.JFileChooser;

/**
 * Fake class for tests
 */
public class FakeAppToolKit implements AppToolKit
{
    private static final long serialVersionUID = 1L;
    private JFileChooserInitializer jFileChooserInitializer;
    private final DefaultAppToolKit delegator;

    public FakeAppToolKit()
    {
        delegator = new DefaultAppToolKit(null);
    }

    @Override
    public void initJFileChooser()
    {
        Window win = getMainFrame();
        getJFileChooserInitializer( win , win );
    }

    @Override
    public JFileChooserInitializer getJFileChooserInitializer(
        final Window parentWindow,
        final Component refComponent
        )
    {
        if( jFileChooserInitializer == null ) {
            jFileChooserInitializer = new JFileChooserInitializer();
            }
        return jFileChooserInitializer;
    }

    @Override
    public JFileChooser getJFileChooser(
        final Window parentWindow,
        final Component refComponent
        )
    {
        return getJFileChooserInitializer( parentWindow, refComponent ).getJFileChooser();
    }

    @Override
    public void beep()
    {
        delegator.beep();
    }

    @Override
    public void openDesktop( File file )
    {
        // fake
        throw new UnsupportedOperationException();
    }

    @Override
    public Locale getValidLocale()
    {
        // fake
        return Locale.ENGLISH;
    }

    @Override
    public void sleep( long ms )
    {
        delegator.sleep( ms );
    }

    @Override
    public Preferences getPreferences()
    {
        // fake (no pref here)
        return null;
    }

    @Override
    public Frame getMainFrame()
    {
        return null; // fake
    }

    @Override
    public void setEnabledJButtonCancel( boolean b )
    {
        // fake
    }

    @Override
    public boolean isEnabledJButtonCancel()
    {
        return false; // fake
    }

    @Override
    public void initComponentsJPanelConfirm()
    {
        // fake
    }

    @Override
    public Resources getResources()
    {
        return delegator.getResources();
    }

    @Override
    public List<File> getRootDirectoriesList()
    {
        return Collections.emptyList();
    }

    @Override
    public I18nResourceBundleName getI18nResourceBundleName()
    {
        return delegator.getI18nResourceBundleName();
    }

    @Override
    public Set<AutoI18nConfig> getAutoI18nConfig()
    {
        return delegator.getAutoI18nConfig();
    }
}