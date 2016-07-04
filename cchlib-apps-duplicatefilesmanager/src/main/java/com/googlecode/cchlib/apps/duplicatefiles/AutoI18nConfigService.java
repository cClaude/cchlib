package com.googlecode.cchlib.apps.duplicatefiles;

import java.util.EnumSet;
import java.util.Set;
import com.googlecode.cchlib.i18n.AutoI18nConfig;

public class AutoI18nConfigService {

    private static volatile AutoI18nConfigService service;
    private final Object lock = new Object();
    private volatile EnumSet<AutoI18nConfig> autoI18nConfig;

    private AutoI18nConfigService()
    {
        // empty
    }

    public Set<AutoI18nConfig> getAutoI18nConfig()
    {
        if( this.autoI18nConfig == null ) {
            synchronized( this.lock ) {
                if( this.autoI18nConfig == null ) {
                    createAutoI18nConfigService();
                }
            }
        }

        return this.autoI18nConfig;
    }

    private void createAutoI18nConfigService()
    {
        this.autoI18nConfig = AutoI18nConfig.newAutoI18nConfig( AutoI18nConfig.DO_DEEP_SCAN );
    }

    public static AutoI18nConfigService getInstance()
    {
        if( service == null ) {
            synchronized( AutoI18nConfigService.class ) {
                if( service == null ) {
                    service = new AutoI18nConfigService();
                }
            }
        }

        return service;
    }
}
