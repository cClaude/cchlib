package com.googlecode.cchlib.apps.duplicatefiles.swing.services;

import com.googlecode.cchlib.apps.duplicatefiles.swing.AppToolKit;
import com.googlecode.cchlib.i18n.core.AutoI18nCore;
import com.googlecode.cchlib.i18n.core.AutoI18nCoreFactory;

public class AutoI18nCoreService
{
    private static volatile AutoI18nCoreService service;
    private final Object lock = new Object();
    private AutoI18nCore autoI18nCore;

    private AutoI18nCoreService()
    {
        // empty
    }

    private static AutoI18nCore initI18n()
    {
        final AppToolKit appToolKit = AppToolKitService.getInstance().getAppToolKit();

        return AutoI18nCoreFactory.newAutoI18nCore(
                AutoI18nConfigService.getInstance().getAutoI18nConfig(),
                appToolKit.getI18nResourceBundleName(),
                appToolKit.getValidLocale()
                );
    }

    public AutoI18nCore getAutoI18nCore()
    {
        if( this.autoI18nCore == null ) {
            synchronized( this.lock ) {
                if( this.autoI18nCore == null ) {
                    this.autoI18nCore = initI18n();
                }
            }
        }

        return this.autoI18nCore;
    }

    public static AutoI18nCoreService getInstance()
    {
        if( service == null ) {
            synchronized( AutoI18nCoreService.class ) {
                if( service == null ) {
                    service = new AutoI18nCoreService();
                }
            }
        }

        return service;
    }
}
