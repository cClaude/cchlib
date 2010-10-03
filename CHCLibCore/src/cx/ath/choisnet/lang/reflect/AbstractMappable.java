package cx.ath.choisnet.lang.reflect;

import java.io.File;
import java.net.URL;
import java.util.Map;
import cx.ath.choisnet.ToDo;

/**
 *
 * @author Claude CHOISNET
 *
 */
@ToDo(action=ToDo.Action.DOCUMENTATION)
public abstract class AbstractMappable
    implements Mappable
{

    public AbstractMappable()
    {

    }

    public Map<String,String> toMap()
    {
        MappableHelper mappableHelper = new MappableHelper(
                (new MappableHelperFactory())
                    .setMethodesNamePattern("(get|is).*")
                    .addClasses(new Class<?>[] { String.class, File.class, URL.class })
                    .addAttribute( MappableHelper.Attributes.ALL_PRIMITIVE_TYPE )
                    );

        return mappableHelper.toMap(this);
    }
}
