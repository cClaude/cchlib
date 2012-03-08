#!/bin/bash
#
# #########################################################
#
# #########################################################
# #########################################################
#
MVN="${MAVEN_HOME}/bin/mvn"

LOGSDIR="./logs"
LOGS_TMP="${LOGSDIR}/.mvn.logs"

LOGS_COMPIL="${LOGSDIR}/.mvn.logs.compilation"
LOGS_COMPIL_WARNING="${LOGSDIR}/.mvn.logs.compilation.WARNING"

LOGS_JAVADOC="${LOGSDIR}/.mvn.logs.javadoc"
LOGS_JAVADOC_WARNING="${LOGSDIR}/.mvn.logs.javadoc.WARNING"

if [ ! -e "${LOGSDIR}" ]; then
  mkdir "${LOGSDIR}"
fi

#
# Perform a full clean first
#
echo "------------------------------------------"
MVNPARAM="clean --offline"
echo ${MVN} ${MVNPARAM}
###
${MVN} ${MVNPARAM}
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
echo "${MVN} ${MVNPARAM}"
###
${MVN} ${MVNPARAM} | tee "${LOGS_COMPIL}"
if [ ! "$?" -eq "0" ];
then
  echo "[ERROR] in ${MVN} ${MVNPARAM}"
  exit 1
fi

cat "${LOGS_COMPIL}" | grep -F "[WARNING]
[ERROR]" >"${LOGS_COMPIL_WARNING}"

#
# Package projets
#
echo "------------------------------------------"
# install ??? TODO fix this if possible
MVNPARAM="install package --errors --fail-fast"
echo "${MVN} ${MVNPARAM}"
###
${MVN} ${MVNPARAM}
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
MVNPARAM=" javadoc:jar --errors -Dmaven.test.skip=true"
echo ${MVN} ${MVNPARAM}
###
${MVN} ${MVNPARAM} | tee "${LOGS_JAVADOC}"
if [ ! "$?" -eq "0" ];
then
  echo "[ERROR] in ${MVN} ${MVNPARAM}"
  exit 1
fi

echo "------------------------------------------"
echo "---            BUILD DONE             ----"
echo "------------------------------------------"

cat "${LOGS_JAVADOC}" | sort | uniq | grep -v -F "[ERROR] Error fetching link:" | grep -F "[WARNING]
[ERROR]" >"${LOGS_TMP}"

cat "${LOGS_TMP}" | grep ": warning - @return tag has no arguments." >"${LOGS_JAVADOC_WARNING}.returntag"
cat "${LOGS_TMP}" | grep -v ": warning - @return tag has no arguments." >"${LOGS_JAVADOC_WARNING}.others"

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
PROJECTS="cchlib-core
cchlib-j2ee
cchlib-jdbf
cchlib-sys
cchlib-swing
cchlib-apps
cchlib-tools"

PROJECTS_WITH_DOC="cchlib-core
cchlib-j2ee
cchlib-jdbf
cchlib-sys
cchlib-swing"

PROJECTS_SUB_CCHLIB_CORE="cchlib-core-deprecated
cchlib-core-sample"

PROJECTS_SUB_CCHLIB_J2EE="cchlib-j2ee-deprecated"

PROJECTS_APPS="cchlib-apps-regexpbuilder
cchlib-apps-editresourcebundle
cchlib-tools-duplicatefiles"

echo "------------------------------------------"
for project in ${PROJECTS}
do
  DIR=./releases/${project}
  echo "Init. project [${DIR}]"
  if [ ! -e "${DIR}" ]; then
    mkdir -p "${DIR}"
  elif [ ! -d "${DIR}" ]; then
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
    if [ "$?" -ne "0" ]; then
      echo "***[ERROR] Copy error" 1>&2
      exit 1
    fi
  done
done

echo "------------------------------------------"
for project in ${PROJECTS_WITH_DOC}
do
  SDIR="./${project}/target/apidocs"
  DDIR="./releases/${project}"
  echo "Prepare javadoc: [${SDIR}] -> [${DDIR}]"
  cp -r --no-preserve=all "${SDIR}" "${DDIR}"
  if [ "$?" -ne "0" ]; then
    echo "***[ERROR] Copy error" 1>&2
    exit 1
  fi
done

echo "------------------------------------------ ${PROJECTS_SUB_cchlib-core}"
DDIR="./releases/cchlib-core"
for project in ${PROJECTS_SUB_CCHLIB_CORE}
do
  SJAR="./${project}/target/*.jar"

  echo "Append sub-project ${project}"
  echo "Copy [${SJAR}] to [${DDIR}]"
  cp ${SJAR} ${DDIR}
  if [ "$?" -ne "0" ]; then
    echo "***[ERROR] Copy error" 1>&2
    exit 1
  fi
done
# No javadoc for these projects
# rm ${DDIR}/*-javadoc.jar ${DDIR}/*-shaded.jar ${DDIR}/original-*.jar

echo "------------------------------------------"
DDIR="./releases/cchlib-j2ee"
for project in ${PROJECTS_SUB_CCHLIB_J2EE}
do
  SJAR="./${project}/target/*.jar"

  echo "Append sub-project ${project}"
  echo "Copy [${SJAR}] to [${DDIR}]"
  cp ${SJAR} ${DDIR}
  if [ "$?" -ne "0" ]; then
    echo "***[ERROR] Copy error" 1>&2
    exit 1
  fi
done
# No javadoc for these projects
# rm ${DDIR}/*-javadoc.jar ${DDIR}/*-shaded.jar ${DDIR}/original-*.jar


echo "------------------------------------------"
DDIR="./releases/cchlib-apps"
for project in ${PROJECTS_APPS}
do
  SJAR="./${project}/target/*.jar"

  echo "Prepare project ${project}"
  echo "Copy [${SJAR}] to [${DDIR}]"
  cp ${SJAR} ${DDIR}
  if [ "$?" -ne "0" ]; then
    echo "***[ERROR] Copy error" 1>&2
    exit 1
  fi
done
# No javadoc for these projects
# rm ${DDIR}/*-javadoc.jar ${DDIR}/*-shaded.jar ${DDIR}/original-*.jar

echo "------------------------------------------"
# clean dirs
for project in ${PROJECTS} ${PROJECTS_SUB_CCHLIB_CORE} ${PROJECTS_SUB_CCHLIB_J2EE} ${PROJECTS_APPS}
do
  DDIR="./releases/${project}"
  rm -r ${DDIR}/*-javadoc.jar ${DDIR}/*-shaded.jar ${DDIR}/original-*.jar 2>/dev/null
done

echo "------------------------------------------"
exit 0
