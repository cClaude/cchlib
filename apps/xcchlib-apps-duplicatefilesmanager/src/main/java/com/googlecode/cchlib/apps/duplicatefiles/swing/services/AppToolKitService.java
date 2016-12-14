package com.googlecode.cchlib.apps.duplicatefiles.swing.services;

import com.googlecode.cchlib.apps.duplicatefiles.swing.AppToolKit;
import com.googlecode.cchlib.apps.duplicatefiles.swing.gui.DuplicateFilesFrame;
import com.googlecode.cchlib.apps.duplicatefiles.swing.prefs.PreferencesControler;

public class AppToolKitService {

    private static volatile AppToolKitService service;
    private final Object lock = new Object();
    private volatile AppToolKit appToolKit;
    private PreferencesControler preferences;
    private DuplicateFilesFrame mainWindow;

    private AppToolKitService()
    {
        // empty
    }

    public AppToolKit getAppToolKit()
    {
        if( this.appToolKit == null ) {
            synchronized( this.lock ) {
                if( this.appToolKit == null ) {
                    createAppToolKit();
                }
            }
        }

        return this.appToolKit;
    }

    private void createAppToolKit()
    {
        assert this.preferences != null;
        assert this.mainWindow != null;

        final DefaultAppToolKit newObject = new DefaultAppToolKit( this.preferences );

        newObject.setMainWindow( this.mainWindow );
        this.appToolKit = newObject;
    }

    public AppToolKit createAppToolKit(
        final PreferencesControler  preferences,
        final DuplicateFilesFrame   duplicateFilesFrame
        )
    {
        assert preferences != null;
        assert duplicateFilesFrame != null;

        this.preferences = preferences;
        this.mainWindow  = duplicateFilesFrame;

        synchronized( this.lock ) {
            createAppToolKit();
        }

        return this.appToolKit;
    }

    public static AppToolKitService getInstance()
    {
        if( service == null ) {
            synchronized( AppToolKitService.class ) {
                if( service == null ) {
                    service = new AppToolKitService();
                }
            }
        }

        return service;
    }
}
