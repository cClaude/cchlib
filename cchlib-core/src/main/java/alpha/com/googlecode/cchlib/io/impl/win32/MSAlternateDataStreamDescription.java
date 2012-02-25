package alpha.com.googlecode.cchlib.io.impl.win32;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

/**
 * Provide some tool to access to AlternateDataStream
 * for NTFS (Alpha)
 * <br/>
 * This class describe default NTFS ADS.
 *
 * @author Claude CHOISNET
 */
public class MSAlternateDataStreamDescription 
{
    private static final List<MSAlternateDataStreamDescriptionEntry> list 
              = new ArrayList<MSAlternateDataStreamDescriptionEntry>();
    
    static {
        list.add( new MSAlternateDataStreamDescriptionEntry(
                "Master file table",
                "$Mft",
                0,
                "Contains one base file record for each file and folder on an NTFS volume. If the allocation information for a file or folder is too large to fit within a single record, other file records are allocated as well."
                ) );
        list.add( new MSAlternateDataStreamDescriptionEntry(
                "Master file table mirror",
                "$MftMirr",
                1,
                "Guarantees access to the MFT in case of a single-sector failure. It is a duplicate image of the first four records of the MFT."
                ));
        list.add( new MSAlternateDataStreamDescriptionEntry(
                "Log file",
                "$LogFile",
                2,
                "Contains a list of transaction steps used for NTFS recoverability. The log file is used by Windows XP Professional to restore consistency to NTFS after a system failure. The size of the log file depends on the size of the volume, but you can increase the size of the log file by using the Chkdsk command. For more information about the log file, see \"NTFS Recoverability\" earlier in this chapter. For more information about Chkdsk, see \"Troubleshooting Disks and File Systems\" in this book."
                ));
        list.add( new MSAlternateDataStreamDescriptionEntry(
                "Volume",
                "$Volume",
                3,
                "Contains information about the volume, such as the volume label and the volume version."
                ));
        list.add( new MSAlternateDataStreamDescriptionEntry(
                "Attribute definitions",
                "$AttrDef",
                4,
                "Lists attribute names, numbers, and descriptions."
                ));
        list.add( new MSAlternateDataStreamDescriptionEntry(
                "Root file name index",
                ".",
                5,
                "The root folder."
                ));
        list.add( new MSAlternateDataStreamDescriptionEntry(
                "Cluster bitmap",
                "$Bitmap",
                6,
                "Represents the volume by showing free and unused clusters."
                ));
        list.add( new MSAlternateDataStreamDescriptionEntry(
                "Boot sector",
                "$Boot",
                7,
                "Includes the BPB used to mount the volume and additional bootstrap loader code used if the volume is bootable."
                ));
        list.add( new MSAlternateDataStreamDescriptionEntry(
                "Bad cluster file",
                "$BadClus",
                8,
                "Contains bad clusters for a volume."
                ));
        list.add( new MSAlternateDataStreamDescriptionEntry(
                "Security file",
                "$Secure",
                9,
                "Contains unique security descriptors for all files within a volume."
                ));
        list.add( new MSAlternateDataStreamDescriptionEntry(
                "Upcase table",
                "$Upcase",
                10,
                "Converts lowercase characters to matching Unicode uppercase characters."
                ));
        list.add( new MSAlternateDataStreamDescriptionEntry(
                "NTFS extension file",
                "$Extend",
                11,
                "Used for various optional extensions such as quotas, reparse point data, and object identifiers."
                ));
    }
    
    private MSAlternateDataStreamDescription(){}
    
    public static List<MSAlternateDataStreamDescriptionEntry> getEntries()
    {
        return Collections.unmodifiableList( list );
    }
}    
