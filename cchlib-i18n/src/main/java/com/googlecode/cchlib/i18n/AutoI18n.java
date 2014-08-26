package com.googlecode.cchlib.i18n; // $codepro.audit.disable packageNamingConvention, com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.useInterfacesOnlyToDefineTypes

/**
 * Configuration through system properties
 */
public interface AutoI18n
{
    /**
     * System properties : {@value #DISABLE_PROPERTIES}<BR>
     * If set with "true" disable automation of internalization process.
     */
    public static final String DISABLE_PROPERTIES = "com.googlecode.cchlib.i18n.AutoI18n.disabled";
}
