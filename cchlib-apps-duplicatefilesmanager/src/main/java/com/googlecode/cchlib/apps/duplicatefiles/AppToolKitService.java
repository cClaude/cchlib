package com.googlecode.cchlib.apps.duplicatefiles;

import com.googlecode.cchlib.apps.duplicatefiles.gui.DuplicateFilesFrame;
import com.googlecode.cchlib.apps.duplicatefiles.prefs.Preferences;

public class AppToolKitService {

    private static AppToolKitService service;
    private final Object lock = new Object();
    private AppToolKit appToolKit;
    private Preferences preferences;
    private DuplicateFilesFrame mainWindow;

    private AppToolKitService()
    {
        // empty
    }

    public AppToolKit getAppToolKit()
    {
        if( this.appToolKit == null ) {
            synchronized( this.lock ) {
                createAppToolKit();;
            }
        }

        return this.appToolKit;
    }

    private void createAppToolKit()
    {
        assert this.preferences != null;
        assert this.mainWindow != null;

        DefaultAppToolKit newObject = new DefaultAppToolKit( this.preferences );

        newObject.setMainWindow( this.mainWindow );
        this.appToolKit = newObject;
    }

    public AppToolKit createAppToolKit(
        final Preferences         preferences,
        final DuplicateFilesFrame duplicateFilesFrame
        )
    {
        assert preferences != null;
        assert duplicateFilesFrame != null;

        this.preferences = preferences;
        this.mainWindow  = duplicateFilesFrame;

        synchronized( this.lock ) {
            createAppToolKit();;
        }

        return this.appToolKit;
    }

    public static AppToolKitService getInstance()
    {
        if( service == null ) {
            service = new AppToolKitService();
        }

        return service;
    }
}
