package com.googlecode.cchlib.apps.duplicatefiles.swing;

import java.util.Collection;
import java.util.regex.Pattern;

public interface FileFilterBuilder
{
    Collection<String> getNamePart();
    Pattern getRegExp();
}
