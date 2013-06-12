package com.googlecode.cchlib.i18n;

import java.awt.Window;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JWindow;
import com.googlecode.cchlib.i18n.core.AutoI18nCore;

/**
 * How to select Fields:
 * <p>
 * By default, I18n process inspect all the fields
 * declared by the class or interface represented.
 * by 'objectToI18n' ({@link AutoI18nCore#performeI18n(Object, Class)}.
 * This includes public, protected, default (package)
 * access, and private fields, but excludes inherited
 * fields.
 * </p>
 */
public enum AutoI18nConfig
{
    /**
     * Select only field objects reflecting all the
     * accessible public fields of the class or
     * interface represented by 'objectToI18n'
     * ({@link AutoI18nCore#performeI18n(Object, Class)}.
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
     * Disable internalization process, could be also done
     * using {@link AutoI18n#DISABLE_PROPERTIES}.
     */
    DISABLE, 
    
    /**
     * TODOC
     */
    PRINT_STACKTRACE_IN_LOGS,
    
    /*
     * TODO : HANDLE_ONLY_FIELDS_WITH_ANNOTATION
     */
}