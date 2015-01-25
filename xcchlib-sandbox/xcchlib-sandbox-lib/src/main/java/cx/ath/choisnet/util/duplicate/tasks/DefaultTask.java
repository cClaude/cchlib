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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import cx.ath.choisnet.io.FileCopyException;
import cx.ath.choisnet.io.InputStreamHelper;
import cx.ath.choisnet.util.checksum.MD5TreeEntry;

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
    private boolean firstime = true;

    /**
    **
    */
    private final String actionName;

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
     catch( final IOException e ) {
        if( this.firstime ) {
            this.firstime = false;

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
     return this.actionName + ":[" + this.destinationAbsoluteFile + "]";
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
            if( this.destinationAbsoluteFile.isFile() ) {
                final boolean res =  this.destinationAbsoluteFile.delete();

                if( res ) {
                    System.out.println( "OK: actionLocalDeleteFile:" + this.destinationAbsoluteFile  );
                    }
                else {
                    throw new cx.ath.choisnet.io.DeleteFileException( this.destinationAbsoluteFile );
                    }
                }
            else {
                System.out.println( "WARN: actionLocalDeleteFile:" + this.destinationAbsoluteFile + " n'est pas un fichier" );
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
            if( ! this.destinationAbsoluteFile.isDirectory() ) {
                final boolean res = this.destinationAbsoluteFile.mkdirs();

                if( res ) {
                    System.out.println( "OK: actionLocalCreateFolder:" + this.destinationAbsoluteFile  );
                    }
                else {
                    throw new IOException( "can't create folder ["
                        + this.destinationAbsoluteFile
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
            if( this.destinationAbsoluteFile.isDirectory() ) {

                final boolean res =  this.destinationAbsoluteFile.delete();

                if( res ) {
                    System.out.println( "OK: actionLocalDeleteFolder:" + this.destinationAbsoluteFile  );
                    }
                else {
                    throw new cx.ath.choisnet.io.DeleteDirectoryException( this.destinationAbsoluteFile );
                    }
                }
            else {
                System.out.println( "WARN: actionLocalDeleteFolder:" + this.destinationAbsoluteFile + " n'est pas un dossier" );
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

        private final File sourceAbsoluteFile;

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
            return "ActionLocalCopyFile from:[" + this.sourceAbsoluteFile
                        + "] to:["
                        + this.destinationAbsoluteFile
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
            if( ! this.destinationAbsoluteFile.getParentFile().isDirectory() ) {
                //
                // Le dossier n'existe pas encore
                //
                throw new TaskRetryLaterException( "No Folder" );
                }

            System.out.println( "actionLocalCopyFile from {" + this.sourceAbsoluteFile + "}" );
            System.out.println( "                      to {" + this.destinationAbsoluteFile + "}" );

            try {
                InputStreamHelper.copy( this.sourceAbsoluteFile, this.destinationAbsoluteFile );
                }
            catch( final IOException e ) {
                throw new cx.ath.choisnet.io.FileCopyException(
                        "actionLocalCopyFile ["
                            + this.sourceAbsoluteFile + "] to ["
                            + this.destinationAbsoluteFile + "]",
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
        private final TasksFactory<?> tasksFactory;

        /** */
        private final MD5TreeEntry sourceFileDigest;

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
            try( final InputStream input = this.tasksFactory.getInputStreamFromSource( this.sourceFileDigest ) ) {
                try( final OutputStream output = new FileOutputStream( this.destinationAbsoluteFile ) ) {
                    InputStreamHelper.copy( input, output );
                    }
            }
            catch( final IOException e ) {
                throw new FileCopyException(
                        "actionCopyFileFromSource ["
                            + this.sourceFileDigest + "] to ["
                            + this.destinationAbsoluteFile + "]",
                        e
                        );
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
        private final File sourceAbsoluteFile;

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
            if( this.destinationAbsoluteFile.exists() ) {
                throw new TaskRetryLaterException( "destination file exist: " + this.destinationAbsoluteFile );
                }

            if( this.sourceAbsoluteFile.renameTo( this.destinationAbsoluteFile ) ) {
                System.out.println(
                    "OK: ActionLocalMoveFile:"
                        + this.sourceAbsoluteFile
                        + " as "
                        + this.destinationAbsoluteFile
                    );
                }
            else {
                throw new TaskRetryLaterException( "can't move: " + this.sourceAbsoluteFile );
                }
        }
    }

} // class

