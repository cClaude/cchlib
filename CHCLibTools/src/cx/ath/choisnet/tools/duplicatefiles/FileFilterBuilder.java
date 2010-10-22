package cx.ath.choisnet.tools.duplicatefiles;

import java.util.Collection;
import java.util.regex.Pattern;

public interface FileFilterBuilder 
{
    public Collection<String> getNamePart();
    public Pattern getRegExp();
    public boolean getIgnoreHidden();
}
