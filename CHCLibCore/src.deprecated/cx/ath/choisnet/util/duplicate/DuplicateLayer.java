package cx.ath.choisnet.util.duplicate;

import cx.ath.choisnet.util.CollectionFilter;
import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

/**
 *
 * @author Claude CHOISNET
 *
 */
@Deprecated
public class DuplicateLayer implements java.io.Serializable
{
    enum Params {
        SELECT_DO_NOT_SELECT_ALL_OCCURENCES
    };
    private static final long serialVersionUID = 1L;
    /** @serial */
    private Collection<Collection<File>>    duplicatesFiles;
    /** @serial */
    private List<File>                      filesToKeep;
    /** @serial */
    private List<Pattern>                   filesToKeepRegExp;

    public DuplicateLayer()
    {
        duplicatesFiles     = new ArrayList<Collection<File>>();
        filesToKeep         = new ArrayList<File>();
        filesToKeepRegExp   = new ArrayList<Pattern>();
    }

//    public DuplicateLayer(MD5FileCollection absoluteMD5FileCollection)
//    {
//        this();
//        addMD5Collection(absoluteMD5FileCollection);
//    }

    public Collection<Collection<File>> getDuplicateFilesList()
    {
        return Collections.unmodifiableCollection(duplicatesFiles);
    }

    public DuplicateLayer filesToIgnore(FileFilter fileFilter)
    {
        Iterator<Collection<File>> iter1 = duplicatesFiles.iterator();

        do {
            if(!iter1.hasNext()) {
                break;
            }

            Collection<File>    collection  = iter1.next();
            Iterator<File>      iter2       = collection.iterator();

            do {
                if(!iter2.hasNext()) {
                    break;
                }

                if( fileFilter.accept( iter2.next() ) ) {
                    iter2.remove();
                }
            } while(true);

            if(collection.size() < 2) {
                iter1.remove();
            }
        } while(true);

        return this;
    }

    public DuplicateLayer addFileToKeep( File file )
    {
        filesToKeep.add(file);

        return this;
    }

    public DuplicateLayer addFileToKeep( String regexp )
    {
        filesToKeepRegExp.add( Pattern.compile( regexp ) );
        return this;
    }

    public boolean isDeletable(File file)
    {
        for(File f:filesToKeep) {
            
            if(f.equals(file)) {
                return false;
            }
        }
        
        String path = file.getPath();

        for(Pattern p:filesToKeepRegExp) {
            if(p.matcher(path).matches()) {
                return false;
            }
        }
        return true;
    }

    public Collection<File> select( 
            FileFilter      selectFileFilter, 
            EnumSet<Params> params
            )
    {
        List<File>  result              = new ArrayList<File>();
        List<File>  currentSelectList   = new ArrayList<File>();
        boolean     selectAllAllowed    = !params.contains(Params.SELECT_DO_NOT_SELECT_ALL_OCCURENCES);

        Iterator<Collection<File>> iter = duplicatesFiles.iterator();

        do {
            if(!iter.hasNext()) {
                break;
            }

            Collection<File> filesList = iter.next();
            Iterator<File> i1$ = filesList.iterator();

            do {
                if(!i1$.hasNext()) {
                    break;
                }

                File f = i1$.next();
                
                if(selectFileFilter.accept(f) && isDeletable(f)) {
                    currentSelectList.add(f);
                }
                
            } while(true);

            int size = currentSelectList.size();

            if(size > 0) {
                if(selectAllAllowed || size < filesList.size()) {
                    result.addAll(currentSelectList);
                }
                currentSelectList.clear();
            }
        } while(true);

        return result;
    }

    public Collection<File> select(CollectionFilter<File> collectionFileFilter)
    {
        Collection<File> result = new ArrayList<File>();

        for(Collection<File> files:getDuplicateFilesList() ) {
            result.addAll( 
                    collectionFileFilter.apply( files ) 
                    );
        }
        return result;
    }

    public DuplicateLayer updateList()
    {
        Iterator<Collection<File>> iter1 = duplicatesFiles.iterator();

        do {
            if(!iter1.hasNext()) {
                break;
            }

            Collection<File> collection = iter1.next();
            Iterator<File> iter2 = collection.iterator();

            do {
                if(!iter2.hasNext()) {
                    break;
                }

                File f = iter2.next();
                if(!f.exists()) {
                    iter2.remove();
                }
            } while(true);

            if(collection.size() < 2) {
                iter1.remove();
            }
        } while(true);

        return this;
    }

//    private static final IteratorFilter<Set<File>> getDupFiles(MD5FileCollection absoluteMD5FileCollection)
//    {
//        SortedMap<MD5TreeEntry,Set<File>> map = new TreeMap<MD5TreeEntry,Set<File>>(absoluteMD5FileCollection.getEntryFiles());
//
//        Iterator<Set<File>> iter = new SortedMapIterator<MD5TreeEntry,Set<File>>( map );
//
//        return new IteratorFilter<Set<File>>(
//                iter,
//                new Selectable<Set<File>>() {
//                    public boolean isSelected(Set<File> object)
//                    {
//                        return object.size() > 1;
//                    }
//                });
//    }

//    private final void addMD5Collection(MD5FileCollection absoluteMD5FileCollection)
//    {
//
//        Collection<File> item;
//
//        for(
//                Iterator<Set<File>> i$ = DuplicateLayer.getDupFiles(
//                                                    absoluteMD5FileCollection
//                                                    ).iterator();
//                i$.hasNext();
//                duplicatesFiles.add( item )
//                )
//
//        {
//            item = i$.next();
//        }
//    }
}
