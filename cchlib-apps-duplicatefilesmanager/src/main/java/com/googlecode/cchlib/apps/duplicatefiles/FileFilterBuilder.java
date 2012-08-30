package com.googlecode.cchlib.apps.duplicatefiles;

import java.util.Collection;
import java.util.regex.Pattern;

public interface FileFilterBuilder
{
    public Collection<String> getNamePart();
    public Pattern getRegExp();
}