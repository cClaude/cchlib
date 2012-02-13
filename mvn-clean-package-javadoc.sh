#!/bin/bash
#
# #########################################################
#
# #########################################################
# Apache Maven 2.2.1 (r801777; 2009-08-06 21:16:01+0200)
# #########################################################
#
MVN="${MAVEN_HOME}/bin/mvn"
LOGSDIR=logs
LOGS=${LOGSDIR}/.logs.javadoc
LOGSWARN=${LOGSDIR}/.logs.javadoc.WARNING

#
# Perform a full clean first
#
echo "------------------------------------------"
MVNPARAM="clean --offline"
echo ${MVN} ${MVNPARAM}
### ${MVN} ${MVNPARAM}
if [ ! "$?" -eq "0" ];
then
  echo "[ERROR] in ${MVN} ${MVNPARAM}"
  exit 1
fi

#
# Compile all projets (skip tests)
#
echo "------------------------------------------"
MVNPARAM="compile --errors --fail-fast -Dmaven.test.skip=true"
echo ${MVN} ${MVNPARAM}
### ${MVN} ${MVNPARAM}
if [ ! "$?" -eq "0" ];
then
  echo "[ERROR] in ${MVN} ${MVNPARAM}"
  exit 1
fi

#
# Package projets
#
echo "------------------------------------------"
# install ??? TODO fix this if possible
MVNPARAM="install package --errors --fail-fast"
echo ${MVN} ${MVNPARAM}
### ${MVN} ${MVNPARAM}
if [ ! "$?" -eq "0" ];
then
  echo "[ERROR] in ${MVN} ${MVNPARAM}"
  exit 1
end if
fi

#
# Build javadocs
#
echo "------------------------------------------"
if [ ! -e "${LOGSDIR}" ]; then
  mkdir "${LOGSDIR}"
fi
MVNPARAM=" javadoc:jar --errors -Dmaven.test.skip=true"
echo ${MVN} ${MVNPARAM}
### ${MVN} ${MVNPARAM} | tee ${LOGS}
if [ ! "$?" -eq "0" ];
then
  echo "[ERROR] in ${MVN} ${MVNPARAM}"
  exit 1
fi

echo "------------------------------------------"
echo "---            BUILD DONE             ----"
echo "------------------------------------------"

cat "${LOGS}" | grep -F "[WARNING]
[ERROR]" | grep -v -F "[ERROR] Error fetching link:">"${LOGSWARN}"

#
# javadoc:jar
#    creates an archive file of the generated Javadocs. It
#    is used during the release process to create the Javadoc
#    artifact for the project's release. This artifact is
#    uploaded to the remote repository along with the project's
#    compiled binary and source archive.
#
# javadoc:test-jar
#    creates an archive file of the generated Test Javadocs.
#
# javadoc:aggregate-jar
#    creates an archive file of the generated Javadocs for an
#    aggregator project.
#
# javadoc:fix
#    is an interactive goal which fixes the Javadoc documentation
#    and tags for the Java files.
#
PROJECTS="cchlib-core cchlib-j2ee cchlib-jdbf cchlib-sys cchlib-apps cchlib-tools"
PROJECTS_WITH_DOC="cchlib-core cchlib-j2ee cchlib-jdbf cchlib-sys"

PROJECTS_APPS="cchlib-apps-cchlib-apps-regexpbuilder cchlib-tools-duplicatefiles cchlib-apps-regexpbuilder"

echo "------------------------------------------"
for project in ${PROJECTS}
do
  DIR=./releases/${project}
  echo "Init. project [${DIR}]"
  if [ ! -e "${DIR}" ]; then
    mkdir -p "${DIR}"
  elif [[ ! -d "${DIR}" ]]; then
    echo "${DIR} already exists but is not a directory" 1>&2
    exit 1
  fi
  if [ ! -e "${DIR}" ]; then
    echo "${DIR} not found"1>&2
    exit 1
  fi

  JAVADOC="${DIR}/apidocs"
  echo "Clean previous documentation: ${JAVADOC}"
  if [ -e "${JAVADOC}" ]; then
    rm -r "${JAVADOC}"
  fi

  JARS="${DIR}/${project}-*.jar"
  echo "Clean previous jar files: ${JARS}"
  if [ -e "${JARS}" ]; then
    rm -r "${JARS}"
  fi

  SJARS="`pwd`/${project}/target/${project}-*.jar"
  echo "Try to copy ${SJARS} to ${DIR}"
  ls ${SJARS} 2>/dev/null | while read jar
  do
    echo "Copy ${jar}"
    cp "${jar}" "${DIR}"
  done
done

echo "------------------------------------------"
for project in ${PROJECTS_WITH_DOC}
do
  SDIR="./${project}/target/apidocs"
  DDIR="./releases/${project}"
  echo "Prepare javadoc: [${SDIR}] -> [${DDIR}]"
  cp -r "${SDIR}" "${DDIR}"
done

echo "------------------------------------------"
for project in ${PROJECTS_APPS}
do
  SJAR="./${project}/target/*.jar"
  DDIR="./releases/cchlib-apps"

  echo "Prepare project ${project}"
  echo "Copy [${SJAR}] to [${DDIR}]"
  cp ${SJAR} ${DDIR}
done


#echo "------------------------------------------"
#for project in ${PROJECTS_TOOLS}
#do
#  SJAR="./${project}/target/*.jar"
#  DDIR="./releases/cchlib-tools"
#
#  echo "Prepare project ${project}"
#  echo "Copy [${SJAR}] to [${DDIR}]"
#  cp ${SJAR} ${DDIR}
#done

echo "------------------------------------------"
exit 0

# @Echo ON
# Set MODULE_WITH_DOC=cchlib-core;cchlib-j2ee;cchlib-jdbf;cchlib-sys
# Set MODULE_WITH_SUBPROJECT=cchlib-core;cchlib-j2ee;cchlib-tools;cchlib-apps


#********* @Echo # Move content of subproject into project
#********* IF NOT EXIST ".\releases\cchlib-apps" Md ."\releases\cchlib-apps"
#********* For /D %%P IN ( %MODULE_WITH_SUBPROJECT% ) DO For /D %%F IN ( "%%P-*" ) DO Echo ************* %%F -- %%P ***************
#********* For /D %%P IN ( %MODULE_WITH_SUBPROJECT% ) DO For /D %%F IN ( "%%P-*" ) DO Del ".\releases\%%F\*-javadoc.jar"
#********* For /D %%P IN ( %MODULE_WITH_SUBPROJECT% ) DO For /D %%F IN ( "%%P-*" ) DO Move ".\releases\%%F\*.jar .\releases\%%P\"
#********* For /D %%P IN ( %MODULE_WITH_SUBPROJECT% ) DO For /D %%F IN ( "%%P-*" ) DO RmDir ".\releases\%%F"

# @Echo # Prepare directories for new documentations
# For /D %%F IN ( %MODULE_WITH_DOC% ) DO IF NOT EXIST ".\releases\%%F\apidocs" Md ".\releases\%%F\apidocs"

# @Echo # Copy new documentations
# For /D %%F IN ( %MODULE_WITH_DOC% ) DO XCopy ".\%%F\target\apidocs" ".\releases\%%F\apidocs\" /E /Y /Q

# @Echo # Remove javadoc for cchlib-tools {no doc here}
# Del /Q ".\releases\cchlib-tools\cchlib-tools-*-javadoc.jar"

# @Echo # Remove javadoc for cchlib-apps {no doc here}
# Del /Q ".\releases\cchlib-tools\cchlib-apps-*-javadoc.jar"

# @Echo # Remove shaded jar file in tools {fix}
# Del /Q ".\releases\cchlib-tools\cchlib-tools-*-shaded.jar"
