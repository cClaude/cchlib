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

##########################################################
#
# Perform a full clean first
#
mvnClean()
{
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
}
##########################################################

##########################################################
#
# Compile all projets (skip tests)
#
mvnCompile()
{
  echo "------------------------------------------"
  MVNPARAM="compile --errors --fail-fast -Dmaven.test.skip=true"
  echo "${MVN} ${MVNPARAM}"
  ${MVN} ${MVNPARAM} >"${LOGS_COMPIL}"
  MVN_EXIT="$?"

  cat "${LOGS_COMPIL}"
  echo "RC=${MVN_EXIT} for ${MVN} ${MVNPARAM}"
  if [ ! "${MVN_EXIT}" -eq "0" ];
  then
    echo "[ERROR] in ${MVN} ${MVNPARAM}"
    exit 1
  fi

  cat "${LOGS_COMPIL}" | grep -F "[WARNING]
  [ERROR]" >"${LOGS_COMPIL_WARNING}"
}
##########################################################

##########################################################
#
# Package projets
#
mvnPackage()
{
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
}
##########################################################

##########################################################
#
# Build javadocs
#
mvnJavadoc()
{
  echo "------------------------------------------"
  MVNPARAM=" javadoc:jar --errors -Dmaven.test.skip=true"
  echo ${MVN} ${MVNPARAM}
  ###
  ${MVN} ${MVNPARAM} >"${LOGS_JAVADOC}"
  MVN_EXIT="$?"

  cat "${LOGS_JAVADOC}"
  echo "RC=${MVN_EXIT} for ${MVN} ${MVNPARAM}"
  if [ ! "$?" -eq "0" ];
  then
    echo "[ERROR] in ${MVN} ${MVNPARAM}"
    exit 1
  fi
}
##########################################################

##########################################################
#
# main
#
##########################################################
mvnClean
mvnCompile
mvnPackage
mvnJavadoc

echo "------------------------------------------"
echo "---            BUILD DONE             ----"
echo "------------------------------------------"

cat "${LOGS_JAVADOC}" | sort | uniq \
| grep -v -F "[ERROR] Error fetching link:" \
| grep -v -F "@wbp.factory is an unknown tag." \
| grep -v -F "@wbp.factory.parameter.source is an unknown tag." \
| grep -F "[WARNING]
[ERROR]" >"${LOGS_TMP}"

cat "${LOGS_TMP}" \
| grep ": warning - @return tag has no arguments." >"${LOGS_JAVADOC_WARNING}.returntag"

cat "${LOGS_TMP}" \
| grep -v ": warning - @return tag has no arguments." >"${LOGS_JAVADOC_WARNING}.others"

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
PROJECTS="cchlib-apps
cchlib-core
cchlib-core-deprecated
cchlib-i18n
cchlib-j2ee
cchlib-j2ee-deprecated
cchlib-jdbf
cchlib-net
cchlib-sql
cchlib-swing
cchlib-swing-deprecated
cchlib-sys
cchlib-tools"

PROJECTS_WITH_DOC="cchlib-core
cchlib-core-deprecated
cchlib-i18n
cchlib-j2ee
cchlib-jdbf
cchlib-net
cchlib-sql
cchlib-swing
cchlib-swing-deprecated
cchlib-sys"

PROJECTS_SUB_CCHLIB_CORE="cchlib-core-sample cchlib-core-beta"

PROJECTS_SUB_CCHLIB_J2EE="cchlib-j2ee-deprecated"

PROJECTS_APPS="cchlib-apps-duplicatefilesmanager
cchlib-apps-regexpbuilder
cchlib-apps-editresourcebundle"

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
