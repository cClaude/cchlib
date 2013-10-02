#!/bin/bash
#
# #########################################################
# package-javadoc.sh
# #########################################################
# #########################################################
#
MVN="${MAVEN_HOME}/bin/mvn"

LOGSDIR="${PWD}/.logs"
LOGS_TMP="${LOGSDIR}/.mvn.logs"

LOGS_COMPIL="${LOGSDIR}/.mvn.logs.compilation"
LOGS_COMPIL_WARNING="${LOGSDIR}/.mvn.logs.compilation.WARNING"

LOGS_JAVADOC="${LOGSDIR}/.mvn.logs.javadoc"
LOGS_JAVADOC_WARNING="${LOGSDIR}/.mvn.logs.javadoc.WARNING"

if [ -e "${HOME}/.cchlib_conf.sh" ]; then
  . ${HOME}/.cchlib_conf.sh
fi

set | grep MAVEN

if [ ! -e "${LOGSDIR}" ]; then
  mkdir "${LOGSDIR}"
fi

##########################################################
#
# Perform a full clean first
#
mvnClean()
{
  echo "-- mvnClean() ----------------------------------------"
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
  echo "-- mvnCompile() ----------------------------------------"
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
  echo "-- mvnPackage() ----------------------------------------"
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
  echo "-- mvnJavadoc() ----------------------------------------"
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

echo "-- done --"
exit 0

