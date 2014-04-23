package com.googlecode.cchlib.i18n.unit.util;

import java.util.Locale;
import java.util.ResourceBundle;
<<<<<<< HEAD:cchlib-i18n-test/src/test/java/com/googlecode/cchlib/i18n/unit/util/XI18nResourceBundleName.java
=======

>>>>>>> cchlib-pre4-1-8:cchlib-i18n/src/test/java/com/googlecode/cchlib/i18n/unit/utils/XI18nResourceBundleName.java
import com.googlecode.cchlib.i18n.resources.DefaultI18nResourceBundleName;
import com.googlecode.cchlib.i18n.resources.I18nResourceBundleName;
import com.googlecode.cchlib.i18n.resources.I18nSimpleResourceBundle;

public class XI18nResourceBundleName
    extends DefaultI18nResourceBundleName
        implements I18nResourceBundleName
{
    private static final long serialVersionUID = 1L;

<<<<<<< HEAD:cchlib-i18n-test/src/test/java/com/googlecode/cchlib/i18n/unit/util/XI18nResourceBundleName.java
    private Package packageMessageBundleBase;
    private String messageBundleBaseName;
=======
    private final Package packageMessageBundleBase;
    private final String messageBundleBaseName;
>>>>>>> cchlib-pre4-1-8:cchlib-i18n/src/test/java/com/googlecode/cchlib/i18n/unit/utils/XI18nResourceBundleName.java

    public XI18nResourceBundleName(
        final Package packageMessageBundleBase,
        final String  messageBundleBaseName
        )
    {
        super( packageMessageBundleBase, messageBundleBaseName );

        this.packageMessageBundleBase = packageMessageBundleBase;
        this.messageBundleBaseName = messageBundleBaseName;
    }

    public XI18nResourceBundleName(
        final Class<?> packageMessageBundleBase,
        final String   messageBundleBaseName
        )
    {
        this( packageMessageBundleBase.getPackage(), messageBundleBaseName );
    }

    public XI18nResourceBundleName( final Class<?> clazz )
    {
        this( clazz.getPackage(), clazz.getSimpleName() );
    }

    public XI18nResourceBundleName( final Package packageMessageBundleBase )
    {
        this( packageMessageBundleBase, DefaultI18nResourceBundleName.DEFAULT_MESSAGE_BUNDLE_BASENAME );
    }

    public Package getPackageMessageBundleBase()
    {
        return packageMessageBundleBase;
    }

    public String getMessageBundleBaseName()
    {
        return messageBundleBaseName;
    }

<<<<<<< HEAD:cchlib-i18n-test/src/test/java/com/googlecode/cchlib/i18n/unit/util/XI18nResourceBundleName.java
    private I18nSimpleResourceBundle createI18nSimpleResourceBundle( final Locale locale )
=======
    private I18nSimpleResourceBundle createI18nSimpleResourceBundle(final Locale locale)
>>>>>>> cchlib-pre4-1-8:cchlib-i18n/src/test/java/com/googlecode/cchlib/i18n/unit/utils/XI18nResourceBundleName.java
    {
        return new I18nSimpleResourceBundle( locale, this );
    }

<<<<<<< HEAD:cchlib-i18n-test/src/test/java/com/googlecode/cchlib/i18n/unit/util/XI18nResourceBundleName.java
    public ResourceBundle createResourceBundle( final Locale locale )
=======
    public ResourceBundle createResourceBundle(final Locale locale)
>>>>>>> cchlib-pre4-1-8:cchlib-i18n/src/test/java/com/googlecode/cchlib/i18n/unit/utils/XI18nResourceBundleName.java
    {
        return createI18nSimpleResourceBundle( locale ).getResourceBundle();
    }
}
