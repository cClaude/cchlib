package com.googlecode.cchlib.lang;

import java.io.File;
import java.io.IOException;
import javax.tools.DiagnosticCollector;
import javax.tools.DiagnosticListener;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

/**
 * TODO move this into lib
 *
 */
public class CompilerHelper
{
    private CompilerHelper()
    {
        // Static
    }

    public static void compile(
            final Iterable<File>                             files,
            final DiagnosticListener<? super JavaFileObject> diagnosticListener
            ) throws IOException
    {

        final JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

        try( StandardJavaFileManager fileManager = compiler.getStandardFileManager( diagnosticListener, null, null ) ) {
            final Iterable<? extends JavaFileObject> compilationUnit =
                    fileManager.getJavaFileObjectsFromFiles( files );

            compiler.getTask(
                    null,
                    fileManager,
                    diagnosticListener,
                    null,
                    null,
                    compilationUnit
                    ).call();
        }
    }

    public static DiagnosticCollector<JavaFileObject> compile( final Iterable<File> files ) throws IOException
    {
        final DiagnosticCollector<JavaFileObject> diag = new DiagnosticCollector<JavaFileObject>();

        compile( files, diag );

        return diag;
    }
}
