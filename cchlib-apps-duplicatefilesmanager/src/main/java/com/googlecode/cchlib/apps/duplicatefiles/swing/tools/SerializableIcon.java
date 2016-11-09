package com.googlecode.cchlib.apps.duplicatefiles.swing.tools;

import java.io.Serializable;
import javax.swing.Icon;

/**
 * Allow to serialize icon objects
 */
public interface SerializableIcon extends Serializable {
    /** @return icon for this object */
    public Icon getIcon();
}
