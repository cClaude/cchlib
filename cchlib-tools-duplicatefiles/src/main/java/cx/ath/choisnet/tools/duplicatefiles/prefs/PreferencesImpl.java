package cx.ath.choisnet.tools.duplicatefiles.prefs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

import javax.swing.UIManager;
import cx.ath.choisnet.tools.duplicatefiles.ConfigMode;

/**
 *
 */
public class PreferencesImpl implements Preferences
{
    private static final long serialVersionUID = 1L;
    private ConfigMode userLevel;
    private String lookAndFeelName;
    private ArrayList<RootFile> rootFileList = new ArrayList<>();
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

    @Override // Preferences
    public ConfigMode getUserLevel()
    {
        if( this.userLevel == null ) {
            this.userLevel = ConfigMode.BEGINNER;
            }
        return this.userLevel;
    }

    @Override // Preferences
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
