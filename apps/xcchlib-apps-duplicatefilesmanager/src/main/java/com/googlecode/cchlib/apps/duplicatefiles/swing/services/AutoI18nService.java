package com.googlecode.cchlib.apps.duplicatefiles.swing.services;

import com.googlecode.cchlib.apps.duplicatefiles.swing.AppToolKit;
import com.googlecode.cchlib.i18n.core.AutoI18n;
import com.googlecode.cchlib.i18n.core.AutoI18nFactory;

public class AutoI18nService
{
    private static volatile AutoI18nService service;
    private final Object lock = new Object();
    private AutoI18n autoI18n;

    private AutoI18nService()
    {
        // empty
    }

    private static AutoI18n initI18n()
    {
        final AppToolKit appToolKit = AppToolKitService.getInstance().getAppToolKit();

        return AutoI18nFactory.newAutoI18n(
                AutoI18nConfigService.getInstance().getAutoI18nConfig(),
                appToolKit.getI18nResource()
                );
    }

    public AutoI18n getAutoI18n()
    {
        if( this.autoI18n == null ) {
            synchronized( this.lock ) {
                if( this.autoI18n == null ) {
                    this.autoI18n = initI18n();
                }
            }
        }

        return this.autoI18n;
    }

    public static AutoI18nService getInstance()
    {
        if( service == null ) {
            synchronized( AutoI18nService.class ) {
                if( service == null ) {
                    service = new AutoI18nService();
                }
            }
        }

        return service;
    }
}
