/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/util/duplicate/tasks/DefaultTask.java
** Description   :
** Encodage      : ANSI
**
**  3.01.008 2006.03.07 Claude CHOISNET - Version initiale
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.util.duplicate.tasks.DefaultTask
**
*/
package cx.ath.choisnet.util.duplicate.tasks;

import cx.ath.choisnet.io.InputStreamHelper;
import cx.ath.choisnet.util.checksum.MD5TreeEntry;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
** <p>
** </p>
**
** <p>
** </p>
**
** @author Claude CHOISNET
** @since   3.01.008
** @version 3.01.008
**
*/
public abstract class DefaultTask
    implements Task
{
    private static final long serialVersionUID = 1L;

    /**
    ** Fichier local sur lequel sera effectue le traitement.
    */
    protected File destinationAbsoluteFile;

    /**
    ** Fichier local sur lequel sera effectue le traitement.
    */
    private Boolean firstime = Boolean.TRUE;

    /**
    **
    */
    private String actionName;

    /**
    **
    */
    protected DefaultTask( // -------------------------------------------------
        final String    actionName,
        final File      destinationAbsoluteFile
        )
    {
     this.actionName                = actionName;
     this.destinationAbsoluteFile   = destinationAbsoluteFile;
    }

    /**
    ** Gestion du traitement des nouvelles tentatives.
    */
    @Override
    public void doJob() // ----------------------------------------------------
        throws
            TaskFailException,
            TaskRetryLaterException
    {
     try {
        doIOJob();
        }
     catch( IOException e ) {
        if( firstime ) {
            firstime = Boolean.FALSE;

            throw new TaskRetryLaterException( e );
            }
        else {
            throw new TaskFailException( e );
            }
        }
    }

    /**
    **
    */
    @Override
    public String toString() // -----------------------------------------------
    {
     return this.actionName + ":[" + destinationAbsoluteFile + "]";
    }

    /**
    ** Action d'IO à traiter
    */
    abstract public void doIOJob() // -----------------------------------------
        throws
            IOException,
            TaskFailException,
            TaskRetryLaterException;

    /**
    ** <p>
    ** Action par défaut pour la suppression d'un fichier.
    ** </p>
    */
    public static class ActionLocalDeleteFile extends DefaultTask
    {
        /** serialVersionUID */
        private static final long serialVersionUID = 1L;

        /**
        ** <p>
        ** Action par défaut pour la suppression d'un fichier.
        ** </p>
        **
        ** @param destinationAbsoluteFile object File e supprimer
        */
        public ActionLocalDeleteFile( final File destinationAbsoluteFile )
        {
            super( "ActionLocalDeleteFile", destinationAbsoluteFile );
        }

        /**
        ** Cette methode verifie que l'objet File donne est bien un
        ** fichier, avant d'effacer le fichier, dans le cas contraire
        ** il ne fait rien.
        **
        ** @throws cx.ath.choisnet.io.DeleteFileException si la suppression n'a pas pu etre faite.
        */
        @Override
        public void doIOJob() throws cx.ath.choisnet.io.DeleteFileException
        {
            if( destinationAbsoluteFile.isFile() ) {
                final boolean res =  destinationAbsoluteFile.delete();

                if( res ) {
                    System.out.println( "OK: actionLocalDeleteFile:" + destinationAbsoluteFile  );
                    }
                else {
                    throw new cx.ath.choisnet.io.DeleteFileException( destinationAbsoluteFile );
                    }
                }
            else {
                System.out.println( "WARN: actionLocalDeleteFile:" + destinationAbsoluteFile + " n'est pas un fichier" );
                }
        }
    }

    /**
    **
    */
    public static class ActionLocalCreateFolder extends DefaultTask
    {
        /** serialVersionUID */
        private static final long serialVersionUID = 1L;

        /**
        **
        */
        public ActionLocalCreateFolder( final File destinationAbsoluteFile )
        {
            super( "ActionLocalCreateFolder", destinationAbsoluteFile );
        }

        /**
        **
        */
        @Override
        public void doIOJob() throws IOException
        {
            if( ! destinationAbsoluteFile.isDirectory() ) {
                final boolean res = destinationAbsoluteFile.mkdirs();

                if( res ) {
                    System.out.println( "OK: actionLocalCreateFolder:" + destinationAbsoluteFile  );
                    }
                else {
                    throw new IOException( "can't create folder ["
                        + destinationAbsoluteFile
                        + "]"
                        );
                    }
                }
            // else { on fait rien il existe deje }
        }
    }

    /**
    **
    */
    public static class ActionLocalDeleteFolder extends DefaultTask
    {
        /** serialVersionUID */
        private static final long serialVersionUID = 1L;

        /**
        **
        */
        public ActionLocalDeleteFolder( final File destinationAbsoluteFile )
        {
            super( "ActionLocalDeleteFolder", destinationAbsoluteFile );
        }

        /**
        **
        */
        @Override
        public void doIOJob() throws cx.ath.choisnet.io.DeleteDirectoryException
        {
            if( destinationAbsoluteFile.isDirectory() ) {

                final boolean res =  destinationAbsoluteFile.delete();

                if( res ) {
                    System.out.println( "OK: actionLocalDeleteFolder:" + destinationAbsoluteFile  );
                    }
                else {
                    throw new cx.ath.choisnet.io.DeleteDirectoryException( destinationAbsoluteFile );
                    }
                }
            else {
                System.out.println( "WARN: actionLocalDeleteFolder:" + destinationAbsoluteFile + " n'est pas un dossier" );
                }
        }
    }

    /**
    **
    */
    public static class ActionLocalCopyFile extends DefaultTask
    {
        /** serialVersionUID */
        private static final long serialVersionUID = 1L;

        private File sourceAbsoluteFile;

        /**
        **
        */
        public ActionLocalCopyFile(
                    final File  sourceAbsoluteFile,
                    final File  destinationAbsoluteFile
                    )
        {
            super( "ActionLocalCopyFile", destinationAbsoluteFile );

            this.sourceAbsoluteFile = sourceAbsoluteFile;
        }

        /**
        **
        */
        @Override
        public String toString() // -----------------------------------------------
        {
            return "ActionLocalCopyFile from:[" + sourceAbsoluteFile
                        + "] to:["
                        + destinationAbsoluteFile
                        + "]";
        }

        /**
        **
        */
        @Override
        public void doIOJob()
            throws
                TaskRetryLaterException,
                cx.ath.choisnet.io.FileCopyException
        {
            if( ! destinationAbsoluteFile.getParentFile().isDirectory() ) {
                //
                // Le dossier n'existe pas encore
                //
                throw new TaskRetryLaterException( "No Folder" );
                }

            System.out.println( "actionLocalCopyFile from {" + sourceAbsoluteFile + "}" );
            System.out.println( "                      to {" + destinationAbsoluteFile + "}" );

            try {
                InputStreamHelper.copy( sourceAbsoluteFile, destinationAbsoluteFile );
                }
            catch( IOException e ) {
                throw new cx.ath.choisnet.io.FileCopyException(
                        "actionLocalCopyFile ["
                            + sourceAbsoluteFile + "] to ["
                            + destinationAbsoluteFile + "]",
                        e
                        );
                }
        }
    }

    /**
    **
    */
    public static class ActionCopyFileFromSource extends DefaultTask
    {
        /** serialVersionUID */
        private static final long serialVersionUID = 1L;

        /** */
        private TasksFactory<?> tasksFactory;

        /** */
        private MD5TreeEntry sourceFileDigest;

        /**
        **
        */
        public ActionCopyFileFromSource(
            final TasksFactory<?> tasksFactory,
            final MD5TreeEntry    sourceFileDigest,
            final File            destinationAbsoluteFile
            )
        {
            super( "ActionCopyFileFromSource", destinationAbsoluteFile );

            this.tasksFactory       = tasksFactory;
            this.sourceFileDigest   = sourceFileDigest;
        }

        /**
        **
        */
        @Override
        public void doIOJob() throws IOException
        {
            final InputStream   input   = this.tasksFactory.getInputStreamFromSource( sourceFileDigest );
            final OutputStream  output  = new FileOutputStream( this.destinationAbsoluteFile );

            try {
                InputStreamHelper.copy( input, output );
                }
            catch( IOException e ) {
                throw new cx.ath.choisnet.io.FileCopyException(
                        "actionCopyFileFromSource ["
                            + sourceFileDigest + "] to ["
                            + destinationAbsoluteFile + "]",
                        e
                        );
                }
            finally {
                output.close();
                input.close();
                }
        }
    }

    /**
    **
    */
    public static class ActionLocalMoveFile extends DefaultTask
    {
        /** serialVersionUID */
        private static final long serialVersionUID = 1L;

        /** */
        private File sourceAbsoluteFile;

        /**
        **
        */
        public ActionLocalMoveFile(
                    final File  sourceAbsoluteFile,
                    final File  destinationAbsoluteFile
                    )
        {
            super( "ActionLocalMoveFile", destinationAbsoluteFile );

            this.sourceAbsoluteFile = sourceAbsoluteFile;
        }

        /**
        **
        */
        @Override
        public void doIOJob() throws TaskRetryLaterException
        {
            if( destinationAbsoluteFile.exists() ) {
                throw new TaskRetryLaterException( "destination file exist: " + destinationAbsoluteFile );
                }

            if( sourceAbsoluteFile.renameTo( destinationAbsoluteFile ) ) {
                System.out.println(
                    "OK: ActionLocalMoveFile:"
                        + sourceAbsoluteFile
                        + " as "
                        + destinationAbsoluteFile
                    );
                }
            else {
                throw new TaskRetryLaterException( "can't move: " + sourceAbsoluteFile );
                }
        }
    }

} // class

