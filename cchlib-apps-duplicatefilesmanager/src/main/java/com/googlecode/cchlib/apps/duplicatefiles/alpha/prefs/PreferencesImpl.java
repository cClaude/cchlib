package com.googlecode.cchlib.apps.duplicatefiles.alpha.prefs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import javax.swing.UIManager;
import com.googlecode.cchlib.apps.duplicatefiles.ConfigMode;

/**
 *
 */
public class PreferencesImpl implements Preferences
{
    private static final long serialVersionUID = 1L;
    private ConfigMode userLevel;
    private String lookAndFeelName;
    private List<RootFile> rootFileList = new ArrayList<>();
    private Locale locale;

    /**
     *
     */
    public PreferencesImpl()
    {
        this.userLevel = ConfigMode.EXPERT;
        this.lookAndFeelName = UIManager.getSystemLookAndFeelClassName();

        RootFile testEntry = new DefaultRootFile(
                "C:\\",
                RootFileAction.INCLUDE_ALL_FILES
                );
        this.rootFileList.add( testEntry  );
    }

    @Override // DefaultPreferences
    public ConfigMode getUserLevel()
    {
        if( this.userLevel == null ) {
            this.userLevel = ConfigMode.BEGINNER;
            }
        return this.userLevel;
    }

    @Override // DefaultPreferences
    public String getLookAndFeelName()
    {
        if( this.lookAndFeelName == null ) {
            this.lookAndFeelName = UIManager.getCrossPlatformLookAndFeelClassName();
            }
        return this.lookAndFeelName;
    }

    @Override
    public Collection<RootFile> getCompareRootFileCollection()
    {
        return rootFileList;
    }

    @Override
    public Locale getLocale()
    {
        return this.locale;
    }
}
