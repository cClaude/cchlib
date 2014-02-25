call mvn install:install-file -DgroupId=com.ice -DartifactId=registry -Dversion=3.1.3 -Dpackaging=jar -Dfile=registry.jar

call mvn install:install-file -DgroupId=com.ice -DartifactId=ICE_JNIRegistry -Dversion=3.1.3	-Dpackaging=dll -Dfile=ICE_JNIRegistry.dll

pause
