package cx.ath.choisnet.util.duplicate;

//import cx.ath.choisnet.util.checksum.MD5TreeEntry;
import java.io.File;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Claude CHOISNET
 *
 */
public interface MD5FileCollection
//    extends java.io.Serializable
{
//    public abstract Set<File> getFolderFiles();

    public abstract Map<byte[],? extends Set<File>> getMap();

//    public abstract Set<File> getEntryFiles(MD5TreeEntry md5treeentry);

//    public abstract int getEntryCount();
}
