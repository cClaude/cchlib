#!/bin/bash
#
# #########################################################
# package-javadoc.sh
# #########################################################
#
# #########################################################
#
MVN="${MAVEN_HOME}/bin/mvn"
#MVN_XPARAM=
MVN_XPARAM=-T 1.5C

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

gnome-open http://google.com/

##########################################################
#
# Perform a full clean first
#
mvnClean()
{
  echo "-- mvnClean() ----------------------------------------"
  MVN_PARAM="${MVN_XPARAM} clean --offline"
  echo ${MVN} ${MVN_PARAM}
  ###
  ${MVN} ${MVN_PARAM}
  MVN_EXIT="$?"
  
  echo "RC=${MVN_EXIT} for ${MVN} ${MVN_PARAM}"
  if [ ! "${MVN_EXIT}" -eq "0" ];
  then
    echo "[ERROR] in ${MVN} ${MVN_PARAM}"
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
  MVN_PARAM="${MVN_XPARAM} compile --errors --fail-fast -Dmaven.test.skip=true"
  echo "${MVN} ${MVN_PARAM}"
  ${MVN} ${MVN_PARAM} >"${LOGS_COMPIL}"
  MVN_EXIT="$?"

  cat "${LOGS_COMPIL}"
  echo "RC=${MVN_EXIT} for ${MVN} ${MVN_PARAM}"
  if [ ! "${MVN_EXIT}" -eq "0" ];
  then
    echo "[ERROR] in ${MVN} ${MVN_PARAM}"
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
  MVN_PARAM="${MVN_XPARAM} install package --errors --fail-fast"
  echo "${MVN} ${MVN_PARAM}"
  ${MVN} ${MVN_PARAM}
  MVN_EXIT="$?"
  
  echo "RC=${MVN_EXIT} for ${MVN} ${MVN_PARAM}"
  if [ ! "${MVN_EXIT}" -eq "0" ];
  then
    echo "[ERROR] in ${MVN} ${MVN_PARAM}"
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
#  MVN_PARAM="${MVN_XPARAM} javadoc:jar --errors -Dmaven.test.skip=true" 
  MVN_PARAM="${MVN_XPARAM} javadoc:jar -Dmaven.test.skip=true" 
  echo ${MVN} ${MVN_PARAM}
  ${MVN} ${MVN_PARAM} >"${LOGS_JAVADOC}"
  MVN_EXIT="$?"

  cat "${LOGS_JAVADOC}"
  echo "RC=${MVN_EXIT} for ${MVN} ${MVN_PARAM}"

  cat "${LOGS_JAVADOC}" | grep -F "[ERROR] >"${LOGS_JAVADOC_WARNING}"

#
#  cat "${LOGS_JAVADOC}" \
#  | grep -F "[ERROR] \
#  | grep -v -F "@wbp.factory is an unknown tag." \
#  | grep -v -F "@wbp.factory.parameter.source is an unknown tag." \
#  [ERROR]" >"${LOGS_TMP}"
#  | grep -v -F "[ERROR] Error fetching link:" 
#
#  cat "${LOGS_TMP}" \
#  | grep ": warning - @return tag has no arguments." >"${LOGS_JAVADOC_WARNING}.returntag"
#
#  cat "${LOGS_TMP}" \
#  | grep ": warning: no description for @param" >"${LOGS_JAVADOC_WARNING}.noDesc@param"
#
#  cat "${LOGS_TMP}" \
#  | grep -v ": warning - @return tag has no arguments." \
#  | grep -v ": warning: no description for @param" \
#  >"${LOGS_JAVADOC_WARNING}.others"
#

  if [ ! "${MVN_EXIT}" -eq "0" ];
  then
    echo "[ERROR] in ${MVN} ${MVN_PARAM}"
    exit 1
  fi
}
##########################################################
mvnExtra()
{
  echo "-- mvnExtra($1) ----------------------------------------"
  cd ${CCHLIB_HOME}/$1
  # install ??? TODO fix this if possible
  MVN_PARAM="${MVN_XPARAM} $2 --errors --fail-fast"
  echo "${MVN} ${MVN_PARAM}"
  ${MVN} ${MVN_PARAM}
  MVN_EXIT="$?"
  cd ${CCHLIB_HOME}
  
  echo "RC=${MVN_EXIT} for ${MVN} ${MVN_PARAM}"
  if [ ! "${MVN_EXIT}" -eq "0" ];
  then
    echo "[ERROR] in ${MVN} ${MVN_PARAM}"
    exit 1
  end if
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
java -version

CCHLIB_HOME=$PWD

mvnClean
mvnExtra xcchlib-core-sample clean
mvnExtra xcchlib-sample clean
mvnExtra xcchlib-sandbox clean
mvnExtra xcchlib-tools clean

mvnCompile
mvnPackage
mvnJavadoc

mvnExtra xcchlib-core-sample test
mvnExtra xcchlib-sample test
mvnExtra xcchlib-sandbox test
mvnExtra xcchlib-tools test

echo "------------------------------------------"
echo "---            BUILD DONE             ----"
echo "------------------------------------------"

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

