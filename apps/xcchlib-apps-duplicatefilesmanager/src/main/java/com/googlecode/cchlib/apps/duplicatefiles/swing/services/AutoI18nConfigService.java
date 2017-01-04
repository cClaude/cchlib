package com.googlecode.cchlib.apps.duplicatefiles.swing.services;

import java.util.Set;
import com.googlecode.cchlib.i18n.AutoI18nConfig;
import com.googlecode.cchlib.i18n.core.AutoI18nConfigSet;

/**
 * NEEDDOC
 *
 * TODO : NEED more tests, DO NOT USE
 */
public class AutoI18nConfigService
{
    private static volatile AutoI18nConfigService service;
    private final Object lock = new Object();
    private volatile AutoI18nConfigSet autoI18nConfig;

    private AutoI18nConfigService()
    {
        // empty
    }

    public AutoI18nConfigSet getAutoI18nConfigSet()
    {
        if( this.autoI18nConfig == null ) {
            synchronized( this.lock ) {
                if( this.autoI18nConfig == null ) {
                    this.autoI18nConfig = newAutoI18nConfigService();
                }
            }
        }

        return this.autoI18nConfig;
    }

    public Set<AutoI18nConfig> getAutoI18nConfig()
    {
        return getAutoI18nConfigSet().getSafeConfig();
    }

    private AutoI18nConfigSet newAutoI18nConfigService()
    {
        return new AutoI18nConfigSet(
            AutoI18nConfig.newAutoI18nConfig( AutoI18nConfig.DO_DEEP_SCAN )
            );
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
