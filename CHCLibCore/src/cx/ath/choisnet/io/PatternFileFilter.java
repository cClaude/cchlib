package cx.ath.choisnet.io;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.util.regex.Pattern;

/**
 *
 * @author Claude CHOISNET
 *
 */
public class PatternFileFilter
    implements FileFilter, FilenameFilter, java.io.Serializable
{
    private static final long serialVersionUID = 1L;
    /** @serial */
    private Pattern pattern;

    public PatternFileFilter(Pattern pattern)
    {
        this.pattern = pattern;
    }

    public PatternFileFilter(String regex)
    {
        this( Pattern.compile(regex) );
    }

    public boolean accept(File file)
    {
        return pattern.matcher(file.getPath()).matches();
    }

    public boolean accept(File dir, String name)
    {
        return pattern.matcher(name).matches();
    }
}
