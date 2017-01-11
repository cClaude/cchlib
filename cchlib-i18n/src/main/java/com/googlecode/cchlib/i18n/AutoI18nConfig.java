package com.googlecode.cchlib.i18n;

import java.awt.Window;
import java.util.EnumSet;
import java.util.Set;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JWindow;
import com.googlecode.cchlib.i18n.core.AutoI18n;

/**
 * How to select Fields:
 * <p>
 * By default, I18n process inspect all the fields
 * declared by the class or interface represented.
 * by 'objectToI18n' ({@link AutoI18n#performeI18n(Object, Class)}.
 * This includes public, protected, default (package)
 * access, and private fields, but excludes inherited
 * fields.
 *
 * @see AutoI18n
 * @see #DISABLE_PROPERTIES
 */
public enum AutoI18nConfig
{
    /**
     * Select only field objects reflecting all the
     * accessible public fields of the class or
     * interface represented by 'objectToI18n' :
     * <br>
     * {@link AutoI18n#performeI18n(Object, Class)}.
     */
    ONLY_PUBLIC,

    /**
     * Also get inspect Fields from super class.
     * <BR>
     * Recurse process into super classes since
     * super class is one of {@link Object},
     * {@link JFrame}, {@link JDialog},
     * {@link JWindow}, {@link Window}
     */
    DO_DEEP_SCAN,

    /**
     * Disable internalization process, could be also done
     * using {@link #DISABLE_PROPERTIES}.
     */
    DISABLE,

    /**
     * Add stack trace in logs
     */
    PRINT_STACKTRACE_IN_LOGS,

    /*
     * TODO : HANDLE_ONLY_FIELDS_WITH_ANNOTATION
     */
    ;

    /**
     * Configuration through system properties
     * <p>
     * System properties : {@value #DISABLE_PROPERTIES}<br>
     * If set with "true" disable automation of internalization process.
     */
    public static final String DISABLE_PROPERTIES = "com.googlecode.cchlib.i18n.AutoI18n.disabled";

    public static Set<AutoI18nConfig> newAutoI18nConfig()
    {
        return EnumSet.noneOf( AutoI18nConfig.class );
    }

    public static Set<AutoI18nConfig> newAutoI18nConfig(
        final AutoI18nConfig first,
        final AutoI18nConfig...rest
        )
    {
        return EnumSet.of( first, rest );
    }
}
