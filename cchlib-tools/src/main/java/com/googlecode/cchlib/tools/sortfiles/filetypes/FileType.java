package com.googlecode.cchlib.tools.sortfiles.filetypes;

import java.nio.file.Path;
import java.nio.file.Paths;

public enum FileType
{
    image_ico ( Paths.get( "[image]", "ico"  ), new IgnoreCaseExtensionsFileFilter( "ico", "cur", "ani" ) ),
    image_gif ( Paths.get( "[image]", "gif"  ), new IgnoreCaseExtensionsFileFilter( "gif" ) ),
    image_jpeg( Paths.get( "[image]", "jpeg" ), new IgnoreCaseExtensionsFileFilter( "jpeg", "jpg" ) ),
    image_png ( Paths.get( "[image]", "png"  ), new IgnoreCaseExtensionsFileFilter( "png" ) ),
    image_bmp ( Paths.get( "[image]", "bmp"  ), new IgnoreCaseExtensionsFileFilter( "bmp", "wmf" ) ),
    image_tga ( Paths.get( "[image]", "tga"  ), new IgnoreCaseExtensionsFileFilter( "tga" ) ),
    image_tif ( Paths.get( "[image]", "tga"  ), new IgnoreCaseExtensionsFileFilter( "tif", "tiff" ) ),

    sound_mp3( Paths.get( "[sound]", "mp3" ), new IgnoreCaseExtensionsFileFilter( "mp3" ) ),
    sound_wav( Paths.get( "[sound]", "wav" ), new IgnoreCaseExtensionsFileFilter( "wav" ) ),
    sound_Ogg( Paths.get( "[sound]", "Ogg" ), new IgnoreCaseExtensionsFileFilter( "Ogg" ) ),
    sound_mod( Paths.get( "[sound]", "mod" ), new IgnoreCaseExtensionsFileFilter( "mod", "mo3" ) ),

    movie_avi( Paths.get( "[movie]", "avi" ), new IgnoreCaseExtensionsFileFilter( "m4a", "avi", "mkv" ) ),
    movie_mp4( Paths.get( "[movie]", "mp4" ), new IgnoreCaseExtensionsFileFilter( "mp4" ) ),
    movie_flv( Paths.get( "[movie]", "flv" ), new IgnoreCaseExtensionsFileFilter( "flv" ) ),
    movie_wmv( Paths.get( "[movie]", "wmv" ), new IgnoreCaseExtensionsFileFilter( "wmv" ) ),
    movie_VOB( Paths.get( "[movie]", "vob" ), new IgnoreCaseExtensionsFileFilter( "vob" ) ),
    movie_mov( Paths.get( "[movie]", "mov" ), new IgnoreCaseExtensionsFileFilter( "mov" ) ),

    arch_7z ( Paths.get( "[arch]", "7z"  ), new IgnoreCaseExtensionsFileFilter( "7z" ) ),
    arch_zip( Paths.get( "[arch]", "zip" ), new IgnoreCaseExtensionsFileFilter( "zip" ) ),
    arch_rar( Paths.get( "[arch]", "rar" ), new IgnoreCaseExtensionsFileFilter( "rar" ) ),
    arch_wim( Paths.get( "[arch]", "wim" ), new IgnoreCaseExtensionsFileFilter( "wim" ) ),
    arch_gz ( Paths.get( "[arch]", "gz"  ), new IgnoreCaseExtensionsFileFilter( "gz", "tgz", "z" ) ),

    web_html( Paths.get( "[web]", "html" ), new IgnoreCaseExtensionsFileFilter( "html", "htm", "css", "js", "json", "php", "xhtml" ) ),
    web_swf ( Paths.get( "[web]", "swf"  ), new IgnoreCaseExtensionsFileFilter( "swf" ) ),

    db_dbx ( Paths.get( "[db]", "dbx"  ), new IgnoreCaseExtensionsFileFilter( "dbx" ) ),

    doc_xls( Paths.get( "[doc]", "xls" ), new IgnoreCaseExtensionsFileFilter( "xls" ) ),
    doc_doc( Paths.get( "[doc]", "doc" ), new IgnoreCaseExtensionsFileFilter( "doc", "odt", "docx", "dot", "dotx", "dotm" ) ),
    doc_pdf( Paths.get( "[doc]", "pdf" ), new IgnoreCaseExtensionsFileFilter( "pdf" ) ),
    doc_ppt( Paths.get( "[doc]", "ppt" ), new IgnoreCaseExtensionsFileFilter( "ppt" ) ),
    doc_rtf( Paths.get( "[doc]", "rtf" ), new IgnoreCaseExtensionsFileFilter( "rtf" ) ),
    doc_txt( Paths.get( "[doc]", "txt" ), new IgnoreCaseExtensionsFileFilter( "txt" ) ),

    URL( new IgnoreCaseExtensionsFileFilter( "url" ) ),

    exe_dll ( Paths.get( "[exe]", "dll"  ), new IgnoreCaseExtensionsFileFilter( "dl_", "dll" ) ),
    exe_exe ( Paths.get( "[exe]", "exe"  ), new IgnoreCaseExtensionsFileFilter( "ex_", "exe" ) ),
    exe_misc( Paths.get( "[exe]", "misc" ), new IgnoreCaseExtensionsFileFilter( "cab", "cat", "sys", "wiz", "vsd", "scr", "cpl" ) ),

    java_jar       ( Paths.get( "[java]", "jar"        ), new IgnoreCaseExtensionsFileFilter( "jar", "war", "ear", "class" ) ),
    java_java      ( Paths.get( "[java]", "java"       ), new IgnoreCaseExtensionsFileFilter( "java" ) ),
    java_properties( Paths.get( "[java]", "properties" ), new IgnoreCaseExtensionsFileFilter( "properties" ) ),
    
    ttf( Paths.get( "[fonts]" ), new IgnoreCaseExtensionsFileFilter( "ttf", "fon" ) ),

    so ( new ExtensionsFileFilter( "so" ) ),
    CHK( new IgnoreCaseExtensionsFileFilter( "CHK" ) ),
    ERR( new IgnoreCaseExtensionsFileFilter( "ERR" ) ),
    IDX( new IgnoreCaseExtensionsFileFilter( "IDX" ) ),
    IDY( new IgnoreCaseExtensionsFileFilter( "IDY" ) ),
    INS( new IgnoreCaseExtensionsFileFilter( "INS" ) ),
    JCL( new IgnoreCaseExtensionsFileFilter( "JCL" ) ),
    LBR( new IgnoreCaseExtensionsFileFilter( "LBR" ) ),
    OLD( new IgnoreCaseExtensionsFileFilter( "OLD" ) ),
    OPA( new IgnoreCaseExtensionsFileFilter( "OPA" ) ),
    PLS( new IgnoreCaseExtensionsFileFilter( "PLS" ) ),
    PSL( new IgnoreCaseExtensionsFileFilter( "PSL" ) ),
    PTR( new IgnoreCaseExtensionsFileFilter( "PTR" ) ),
    RSA( new IgnoreCaseExtensionsFileFilter( "RSA" ) ),
    SF ( new IgnoreCaseExtensionsFileFilter( "SF" ) ),
    TRA( new IgnoreCaseExtensionsFileFilter( "TRA" ) ),
    VER( new IgnoreCaseExtensionsFileFilter( "VER" ) ),
    XRR( new IgnoreCaseExtensionsFileFilter( "XRR" ) ),
    XSG( new IgnoreCaseExtensionsFileFilter( "XSG" ) ),
    app( new IgnoreCaseExtensionsFileFilter( "app" ) ),
    bak( new IgnoreCaseExtensionsFileFilter( "bak" ) ),
    bat( new IgnoreCaseExtensionsFileFilter( "bat", "cmd" ) ),
    bin( new IgnoreCaseExtensionsFileFilter( "bin" ) ),
    cbl( new IgnoreCaseExtensionsFileFilter( "cbl" ) ),
    cfg( new IgnoreCaseExtensionsFileFilter( "cfg" ) ),
    chm( new IgnoreCaseExtensionsFileFilter( "chm" ) ),
    cls( new IgnoreCaseExtensionsFileFilter( "cls" ) ),
    cnt( new IgnoreCaseExtensionsFileFilter( "cnt" ) ),
    cpb( new IgnoreCaseExtensionsFileFilter( "cpb" ) ),
    cpp( new IgnoreCaseExtensionsFileFilter( "cpp", "c", "h", "rc", "dfm", "res" ) ),
    cpy( new IgnoreCaseExtensionsFileFilter( "cpy" ) ),
    crw( new IgnoreCaseExtensionsFileFilter( "crw" ) ),
    csv( new IgnoreCaseExtensionsFileFilter( "csv" ) ),
    dat( new IgnoreCaseExtensionsFileFilter( "dat" ) ),
    dcu( new IgnoreCaseExtensionsFileFilter( "dcu" ) ),
    dta( new IgnoreCaseExtensionsFileFilter( "dta" ) ),
    ebc( new IgnoreCaseExtensionsFileFilter( "ebc" ) ),
    gnt( new IgnoreCaseExtensionsFileFilter( "gnt" ) ),
    gs ( new IgnoreCaseExtensionsFileFilter( "gs"  ) ),
    hlp( new IgnoreCaseExtensionsFileFilter( "hlp" ) ),
    inf( new IgnoreCaseExtensionsFileFilter( "inf" ) ),
    ini( new IgnoreCaseExtensionsFileFilter( "ini" ) ),
    ksh( new IgnoreCaseExtensionsFileFilter( "ksh" ) ),
    lib( new IgnoreCaseExtensionsFileFilter( "lib" ) ),
    lnk( new IgnoreCaseExtensionsFileFilter( "lnk" ) ),
    log( new IgnoreCaseExtensionsFileFilter( "log" ) ),
    man( new IgnoreCaseExtensionsFileFilter( "man" ) ),
    mdb( new IgnoreCaseExtensionsFileFilter( "mdb" ) ),
    mf ( new IgnoreCaseExtensionsFileFilter( "mf"  ) ),
    msi( new IgnoreCaseExtensionsFileFilter( "msi" ) ),
    mui( new IgnoreCaseExtensionsFileFilter( "mui" ) ),
    mvs( new IgnoreCaseExtensionsFileFilter( "mvs" ) ),
    nvi( new IgnoreCaseExtensionsFileFilter( "nvi" ) ),
    obj( new IgnoreCaseExtensionsFileFilter( "obj" ) ),
    pas( new IgnoreCaseExtensionsFileFilter( "pas" ) ),
    reg( new IgnoreCaseExtensionsFileFilter( "reg" ) ),
    s  ( new IgnoreCaseExtensionsFileFilter( "s"   ) ),
    sav( new IgnoreCaseExtensionsFileFilter( "sav" ) ),
    sql( new IgnoreCaseExtensionsFileFilter( "sql" ) ),
    tab( new IgnoreCaseExtensionsFileFilter( "tab" ) ),
    vbs( new IgnoreCaseExtensionsFileFilter( "vbs" ) ),
    wma( new IgnoreCaseExtensionsFileFilter( "wma" ) ),
    xml( new IgnoreCaseExtensionsFileFilter( "xml", "dtd", "xsl" ) ),
    gm ( new IgnoreCaseExtensionsFileFilter( "gm" ) ),
    db ( new IgnoreCaseExtensionsFileFilter( "db" ) ),

    //NO Extension
    no_extension( Paths.get( "[no_ext]" ), new NoExtentionFileFilter() );

    private Path folder;
    //private String[] extensions;
    private XFileFilter fileFilter;

    private FileType( final Path folder, final XFileFilter fileFilter )
    {
        this.folder     = folder == null ? Paths.get( name() ) : folder;
        //this.extensions = new String[0];
        this.fileFilter = fileFilter;
        this.fileFilter.setFileType( this );
    }
    
//    private FileType( final Path folder, final XFileFilterFactory fileFilterFactory, final String extension, final String...others )
//    {
//        this.folder     = folder == null ? Paths.get( name() ) : folder;
//        //this.extensions = extensions;
//        this.fileFilter = fileFilterFactory.createFileFilter( extension, others );
//    }

//    private FileType( final Path folder, final String extension, final String...others )
//    {
//        this( folder, FileType.getNoCaseExtensionsFileFilterFactory(), extension, others );
//    }

    private FileType( final XFileFilter fileFilter )
    {
        this( (Path)null, fileFilter );
    }

//    private FileType( final String extension, final String...others  )
//    {
//        this( (Path)null, extension, others );
//    }

    public XFileFilter getFileFilter()
    {
        return fileFilter;
    }

    public Path getFolder()
    {
        return folder;
    }

    public static String toString( final FileType fileType )
    {
        StringBuilder sb = new StringBuilder();

        sb.append( "FileType[" );

        if( fileType != null ) {
            sb.append( fileType.name() );
            sb.append( "(P(" );
            sb.append( fileType.getFolder() );
            sb.append( "):" );
            //sb.append( Arrays.toString( fileType.getExtensions() ) );
            sb.append( fileType.getFileFilter().toDisplay() );
            sb.append( ')' );
            }
        sb.append( ']' );

        return sb.toString();
    }

//    private static XFileFilterFactory getNoCaseExtensionsFileFilterFactory()
//    {
//       return new XFileFilterFactory()
//       {
//           @Override
//           public XFileFilter createFileFilter( String extension,  String...others )
//           {
//               return new IgnoreCaseExtensionsFileFilter( extension, others );
//           }
//       };
//    }
}
