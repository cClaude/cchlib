#!/bin/bash
#

mvnPackageJar()
{
  echo "-- mvnPackageJar($1) --"
  pushd "$1"
  mvn clean package
  MVN_EXIT="$?"
  popd
  if [ ! "${MVN_EXIT}" -eq "0" ];
  then
    echo "[ERROR] in ${MVN} ${MVN_PARAM}"
    exit 1
  fi
}

mvnInstallJar()
{
  echo "-- mvnInstallJar($1) --"
  pushd "$1"
  mvn clean install
  MVN_EXIT="$?"
  popd
  if [ ! "${MVN_EXIT}" -eq "0" ];
  then
    echo "[ERROR] in ${MVN} ${MVN_PARAM}"
    exit 1
  fi
}

saveJars()
{
  echo "-- saveJars($1) --"
  pwd
  cp ./$1/target/*.jar "${JARS_DIRECTORY}"
}

set +xe
JARS_DIRECTORY=.releases

[ -d "${JARS_DIRECTORY}" ] || mkdir "${JARS_DIRECTORY}"

mvnPackageJar cchlib-apps-duplicatefilesmanager/
mvnPackageJar cchlib-apps-editresourcebundle/
mvnPackageJar cchlib-apps-regexpbuilder/

mvnInstallJar cchlib-x-googlecontact/

mvnPackageJar xcchlib-core-sample/
mvnPackageJar xcchlib-samples/
mvnPackageJar xcchlib-sandbox/
mvnPackageJar xcchlib-tools/

saveJars cchlib-apps-duplicatefilesmanager
saveJars cchlib-apps-editresourcebundle
