package com.googlecode.cchlib.tools.autorename.sortfiles;

import java.io.FileFilter;
import java.nio.file.Path;
import java.nio.file.Paths;

public enum FileType 
{
    image_ico( Paths.get( "[image]", "ico" ), "ico", "cur", "ani" ),
    image_gif( Paths.get( "[image]", "gif" ), "gif" ),
    image_jpeg( Paths.get( "[image]", "jpeg" ),"jpeg", "jpg" ),
    image_png( Paths.get( "[image]", "png" ), "png" ),
    image_bmp( Paths.get( "[image]", "bmp" ), "bmp", "wmf" ),
    image_tga( Paths.get( "[image]", "tga" ), "tga" ),

    sound_mp3( Paths.get( "[sound]", "mp3" ), "mp3" ),
    sound_wav( Paths.get( "[sound]", "wav" ), "wav" ),
    sound_Ogg( Paths.get( "[sound]", "Ogg" ), "Ogg" ),

    movie_avi( Paths.get( "[movie]", "avi" ), "m4a", "avi", "mkv" ),
    movie_mp4( Paths.get( "[movie]", "mp4" ), "mp4" ),
    movie_flv( Paths.get( "[movie]", "flv" ), "flv" ),
    movie_wmv( Paths.get( "[movie]", "wmv" ), "wmv" ),
    movie_VOB( Paths.get( "[movie]", "vob" ), "vob" ),

    arch_zip( Paths.get( "[arch]" ), "zip", "rar", "wim", "z", "gz", "7z", "tgz" ),

    web_html( "html", "htm", "css", "js", "json", "php", "xhtml" ),

    xls( "xls" ),
    doc( "doc", "odt", "docx", "dot", "dotx" ),

    URL( "url" ),

    CHK( "CHK" ),
    ERR( "ERR" ),
    IDX( "IDX" ),
    IDY( "IDY" ),
    INS( "INS" ),
    JCL( "JCL" ),
    LBR( "LBR" ),
    OLD( "OLD" ),
    OPA( "OPA" ),
    PLS( "PLS" ),
    PSL( "PSL" ),
    PTR( "PTR" ),
    RSA( "RSA" ),
    SF( "SF" ),
    SWF( "swf" ),
    TRA( "TRA" ),
    VER( "VER" ),
    XRR( "XRR" ),
    XSG( "XSG" ),
    Xgen( "Xgen", "Skl" ),
    app( "app" ),
    bak( "bak" ),
    bat( "bat", "cmd" ),
    bin( "bin" ),
    cbl( "cbl" ),
    cfg( "cfg" ),
    chm( "chm" ),
    cls( "cls" ),
    cnt( "cnt" ),
    cpb( "cpb" ),
    cpp( "cpp", "c", "h", "rc", "dfm", "res" ),
    cpy( "cpy" ),
    crw( "crw" ),
    csv( "csv" ),
    dat( "dat" ),
    dcu( "dcu" ),
    dll( "dl_", "dll" ),
    dta( "dta" ),
    ebc( "ebc" ),
    exe( "cab", "ex_", "cat", "sys", "wiz", "vsd", "exe", "scr", "cpl" ),
    gnt( "gnt" ),
    gs( "gs" ),
    hlp( "hlp" ),
    inf( "inf" ),
    ini( "ini" ),
    jar( "jar" ),
    java( "java" ),
    ksh( "ksh" ),
    lib( "lib" ),
    lnk( "lnk" ),
    log( "log" ),
    man( "man" ),
    mdb( "mdb" ),
    mf( "mf" ),
    msi( "msi" ),
    mui( "mui" ),
    mvs( "mvs" ),
    nvi( "nvi" ),
    obj( "obj" ),
    pas( "pas" ),
    pdf( "pdf" ),
    ppt( "ppt" ),
    properties( "properties" ),
    reg( "reg" ),
    rtf( "rtf" ),
    s( "s" ),
    sav( "sav" ),
    sql( "sql" ),
    tab( "tab" ),
    ttf( "ttf", "fon" ),
    txt( "txt" ),
    vbs( "vbs" ),
    wma( "wma" ),
    xml( "xml", "dtd", "xsl" ),
    gm( "gm" ),
    db( "db" ),
    
    //NO Extension
    no_extension( Paths.get( "[no_ext]" ), new NoExtentionFileFilter() );

    private Path folder;
    //private String[] extensions;
    private FileFilter fileFilter;

    private FileType( final Path folder, final FileFilter fileFilter )
    {
        this.folder     = folder == null ? Paths.get( name() ) : folder;
        //this.extensions = new String[0];
        this.fileFilter = fileFilter;
    }
    private FileType( final Path folder, final FileFilterFactory fileFilterFactory, final String...extensions )
    {
        this.folder     = folder == null ? Paths.get( name() ) : folder;
        //this.extensions = extensions;
        this.fileFilter = fileFilterFactory.createFileFilter( extensions );
    }
    
    private FileType( final Path folder, final String...extensions )
    {
//        this.folder     = folder == null ? Paths.get( name() ) : folder;
//        this.extensions = extensions;
//        //this.fileFilter = new ExtensionsFileFilter( extensions, ExtensionsFileFilter.Param.NO_CASE );
//        this.fileFilter = FileType.getNoCaseExtensionsFileFilterFactory().createFileFilter( extensions );
        this( folder, FileType.getNoCaseExtensionsFileFilterFactory(), extensions );
    }
    
    private FileType( final String...extensions )
    {
        this( (Path)null, extensions );
    }

//    private String[] getExtensions()
//    {
//        return extensions;
//    }
    
    public FileFilter getFileFilter()
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
            sb.append( fileType.getFileFilter() );
            sb.append( ")" );
            }
        sb.append( "]" );

        return sb.toString();
    }

    private static FileFilterFactory getNoCaseExtensionsFileFilterFactory()
    {
       return new FileFilterFactory()
       {
           @Override
           public FileFilter createFileFilter( String[] extensions )
           {
               return new IgnoreCaseExtensionsFileFilter( extensions );
           }
       };
    }
}
