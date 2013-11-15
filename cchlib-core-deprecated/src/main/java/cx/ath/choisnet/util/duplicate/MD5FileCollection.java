package cx.ath.choisnet.util.duplicate;

//import cx.ath.choisnet.util.checksum.MD5TreeEntry;
import java.io.File;
import java.util.Map;
import java.util.Set;

/**
 *
 */
@Deprecated
public interface MD5FileCollection
{
    Map<byte[],? extends Set<File>> getMap();
}
