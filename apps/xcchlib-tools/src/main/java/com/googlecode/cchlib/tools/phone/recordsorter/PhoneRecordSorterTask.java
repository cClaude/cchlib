package com.googlecode.cchlib.tools.phone.recordsorter;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.io.FileIterator;
import com.googlecode.cchlib.io.filefilter.FileFileFilter;
import com.googlecode.cchlib.swing.batchrunner.AbstractBRRunnable;
import com.googlecode.cchlib.swing.batchrunner.BRExecutionEvent;
import com.googlecode.cchlib.swing.batchrunner.BRExecutionException;
import com.googlecode.cchlib.tools.phone.recordsorter.conf.Config;
import com.googlecode.cchlib.tools.phone.recordsorter.conf.ConfigFactory;
import com.googlecode.cchlib.tools.phone.recordsorter.conf.util.ConfigDebug;
import com.googlecode.cchlib.tools.phone.recordsorter.core.DestinationFolders;

public class PhoneRecordSorterTask extends AbstractBRRunnable
{
    private static final Logger LOGGER = Logger.getLogger( PhoneRecordSorterTask.class );

    private final ConfigFactory configFactory;
    private final Pattern       filenamePattern;

    public PhoneRecordSorterTask( final ConfigFactory configFactory )
    {
        this.configFactory   = configFactory;
        this.filenamePattern = Pattern.compile( "\\d\\d\\d\\d_\\d\\d_\\d\\d_\\d\\d_\\d\\d_.*" );
    }

    @Override
    public void execute( final BRExecutionEvent event ) throws BRExecutionException
    {
      final File sourceFile      = event.getSourceFile();
      final File destinationFile = event.getDestinationFile();

      LOGGER.info( "DO execute() : " + sourceFile + " -> " + destinationFile );

      try {
          final CurrentTask task = new CurrentTask( destinationFile );

          task.sortDirectoryRec( sourceFile );
          task.saveConf();
          }
      catch( final IOException e ) {
          // TODO Auto-generated catch block
          e.printStackTrace();
          }
      LOGGER.info( "DONE execute() : " + sourceFile + " -> " + destinationFile );
    }

    @Override
    public File buildOutputFile( final File sourceFile )
    {
        return null;
    }

    private void log( final String msg )
    {
        System.out.println( msg );
    }

    public class CurrentTask
    {
        private final File               configFile;
        private final Config             config;
        private final DestinationFolders destinationFolders;

        public CurrentTask( final File destinationFolderFile) throws IOException
        {
            this.configFile     = new File( destinationFolderFile, "config.json" );
            this.config         = PhoneRecordSorterTask.this.configFactory.load( this.configFile );

            ConfigDebug.debug( this.config, System.err );

            this.destinationFolders = new DestinationFolders( this.config, destinationFolderFile );

            log( "DEST  :" + destinationFolderFile );
            log( "DEST.exists()  :" + destinationFolderFile.exists() );
        }

        public void saveConf() throws IOException
        {
            PhoneRecordSorterTask.this.configFactory.encodeToFile( this.config, this.configFile );
        }

        public void sortDirectoryRec( final File sourceFolderFile )
        {
            log( "SOURCE:" + sourceFolderFile );
            log( "SOURCE.exists():" + sourceFolderFile.exists() );

            final FileIterator iter = new FileIterator( sourceFolderFile, new FileFileFilter() );

            log( "Explore:" + sourceFolderFile );

            while( iter.hasNext() ) {
                sort( iter.next() );
                }
        }

        private void sort( final File file )
        {
            final String filename = file.getName();

            final Matcher m = PhoneRecordSorterTask.this.filenamePattern.matcher( filename );

            if( m.find() ) {
                String number = filename.substring( 17 );
                number = number.substring( 0, number.indexOf( '_' ) );

                if( number.startsWith( "+33" ) ) {
                    number = '0' + number.substring( 3 );
                } else if( number.startsWith( "33" ) ) {
                    number = '0' + number.substring( 2 );
                } else if( number.startsWith( "+41" ) ) {
                    number = "00" + number.substring( 1 );
                } else if( number.startsWith( "41" ) ) {
                    number = "00" + number;
                }

                log( "> filename " + filename + " number " + number );
                File destination = this.destinationFolders.getFolder( number );

                if( destination == null ) {
                    destination = this.destinationFolders.createFolder( number, number );
                }

                log( "> move " + number + " to " + destination );
                moveFileTo( file, destination );
                }
            else {
                log( "ERR: bad filename [" + filename + ']' );
                }
        }

        private void moveFileTo( final File source, final File destinationFolder )
        {
            assert source.isFile();
            assert destinationFolder != null;

            if( ! destinationFolder.exists() ) {
                destinationFolder.mkdir();
                }

            assert destinationFolder.exists() : destinationFolder + " does not exist";

            final File target = new File( destinationFolder, source.getName() );

            assert !target.exists() : target + " already exist";

            log( "move to\n" + source + "\n" + target );

//            try {
//                Files.move( source.toPath(), target.toPath(), StandardCopyOption.REPLACE_EXISTING );
//                }
//            catch( IOException e ) {
//                log( "Can not move " + source + " to " + destinationFolder );
//                }
        }
   }

}
