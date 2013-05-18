package com.googlecode.cchlib.i18n;

import java.awt.Window;
import java.lang.reflect.Field;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JWindow;
import com.googlecode.cchlib.i18n.annotation.I18n;
import com.googlecode.cchlib.i18n.annotation.I18nIgnore;
import com.googlecode.cchlib.i18n.annotation.I18nString;

/**
 * AutoI18n is design to assist internalization process.
 * AutoI18n use reflection to identify {@link Field} that
 * should be localized.
 * <p>
 * You can customize AutoI18n by many ways:
 * <br/>
 * - Using annotations {@link I18n}, {@link I18nIgnore},
 * {@link I18nString};
 * <br/>
 * - Using custom process by extending {@link AutoI18nBasicInterface},
 * {@link AutoI18nCustomInterface} or {@link  I18nInterface};
 * <br/>
 * - Defines {@link AutoI18nImpl.Attribute attributes} on constructor.
 * </p>
 * <p>
 * <b>How to use:</b>
 * <pre>
 *     // on main object (JFrame)
 *     private AutoI18n autoI18n = {@code <<init_AutoI18n>>}
 *
 *     private void init()
 *     {
 *       ...
 *       // after all initialization ...
 *       // Localize current class
 *       autoI18n.performeI18n(this,this.getClass());
 *
 *       // but also you can Localize some extra fields
 *       autoI18n.performeI18n(this.myPanel,this.myPanel.getClass());
 *     }
 * </pre>
 * <b>How to initialize:</b>
 * <pre><code>
 *     AutoI18n autoI18n = DefaultI18nBundleFactory.createDefaultI18nBundle(
            locale,
            objectToI18n
            ).getAutoI18n();
 * </code></pre>
 * <b>Debugging:</b>
 * <ul>
 *  <li>See {@link #DISABLE_PROPERTIES}</li>
 * </ul>
 * </p>
 *
 */
public interface AutoI18n
{
    /**
     * How to select Fields:
     * <p>
     * By default, I18n process inspect all the fields
     * declared by the class or interface represented.
     * by 'objectToI18n' ({@link AutoI18nImpl#performeI18n(Object, Class)}.
     * This includes public, protected, default (package)
     * access, and private fields, but excludes inherited
     * fields.
     * </p>
     */
    public enum Attribute
    {
        /**
         * Select only field objects reflecting all the
         * accessible public fields of the class or
         * interface represented by 'objectToI18n'
         * ({@link AutoI18n#performeI18n(Object, Class)}.
         */
        ONLY_PUBLIC,

        /**
         * Also get inspect Fields from super class.
         * <br/>
         * Recurse process into super classes since
         * super class is one of {@link Object},
         * {@link JFrame}, {@link JDialog},
         * {@link JWindow}, {@link Window}
         */
        DO_DEEP_SCAN,

        /**
         * Internal use, see {@link AutoI18n#DISABLE_PROPERTIES}<br/>
         * Disable internalization process.
         */
        DISABLE,
    }
    
    /**
     * System properties : {@value #DISABLE_PROPERTIES}<br/>
     * If set with "true" disable
     * auto-internalization process.
     */
    public static final String DISABLE_PROPERTIES = "com.googlecode.cchlib.i18n.AutoI18n.disabled";

    /**
     * Change {@link I18nInterface} to use
     *
     * @param i18n {@link I18nInterface} to use
     */
    public void setI18n( I18nInterface i18n );

    /**
     * Change {@link AutoI18nExceptionHandler} to use
     *
     * @param handler {@link AutoI18nExceptionHandler} to use
     */
    public void setAutoI18nExceptionHandler( AutoI18nExceptionHandler handler );

    /**
     * 'key' value for RessourceBundle is build using full Field name:
     * i.e. package.path.ClassName.aFieldName
     * <br/>
     * Then try to make I18N use these rules :
     * <pre>
     *   1. Looking for @I18n, @I18nIgnore annotations
     *      1.1  If annotation I18nIgnore is define, just ignore
     *           this field
     *      1.1. If a keyName is define, use it to find value in
     *           resource bundle.
     *      1.2. If a methodName is define, use it to set
     *           value: objectToI18n.methodName(value);
     *   2  Try to customize using default rules, getting Fields
     *      values.
     *      2.1. If field value is an instance of AutoI18nBasicInterface
     *           use it to set value.
     *      2.2. If field value is an instance of AutoI18nCustomInterface
     *           use it to set value
     *      2.3. If field value is an instance of javax.swing.JLabel
     *           use {@link javax.swing.JLabel#setText(String)}
     *      2.4. If field value is an instance of javax.swing.AbstractButton
     *           use {@link javax.swing.AbstractButton#setText(String)}
     *      2.5. If field value is an instance of javax.swing.JCheckBox
     *           use {@link javax.swing.JCheckBox#setText(String)}
     * </pre>
     * @param <T> Type of object to internationalize
     * @param objectToI18n Object to I18n
     * @param clazz        Class to use for I18n
     * @see AutoI18nBasicInterface
     * @see AutoI18nBasicInterface#getI18nString()
     * @see AutoI18nCustomInterface
     * @see AutoI18nCustomInterface#getI18n(I18nInterface)
     */
    public <T> void performeI18n( T objectToI18n, Class<? extends T> clazz );

}