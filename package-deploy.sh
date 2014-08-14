#!/bin/bash
#
# #########################################################
# package-deploy.sh
# #########################################################
# Apache Maven 3.0.4
# #########################################################
#

##########################################################
copyJars()
{
  echo "-- copyJars() ----------------------------------------"
  for project in ${PROJECTS}
  do
    DDIR=${PACKAGE_DIR}/${project}
    echo "Init. project [${DDIR}]"
    if [ ! -e "${DDIR}" ]; then
      mkdir -p "${DDIR}"
    elif [ ! -d "${DDIR}" ]; then
      echo "${DDIR} already exists but is not a directory" 1>&2
      exit 1
    fi
    if [ ! -e "${DDIR}" ]; then
      echo "${DDIR} not found"1>&2
      exit 1
    fi

    JARS="${DDIR}/${project}-*.jar"
    echo "Clean previous jar files: ${JARS}"
    if [ -e "${JARS}" ]; then
      rm -r "${JARS}"
    fi

    SJARS="`pwd`/${project}/target/${project}-*.jar"
    echo "Try to copy ${SJARS} to ${DDIR} (no javadoc)"
    ls ${SJARS} 2>/dev/null | grep -v "javadoc.jar" | while read jar
    do
      echo "Copy ${jar} to ${DDIR} (no javadoc)"
      cp "${jar}" "${DDIR}"
      if [ "$?" -ne "0" ]; then
        echo "***[ERROR] Copy error" 1>&2
        exit 1
      fi
    done
  done
}
##########################################################
copyJavadoc()
{
  echo "-- copyJavadoc() ----------------------------------------"
  for project in ${PROJECTS_WITH_DOC}
  do
      SDIR="./${project}/target/apidocs"
      DDIR="${PACKAGE_DIR}/${project}"

      JAVADOC="${DDIR}/apidocs"
      echo "Clean previous documentation: ${JAVADOC}"
      if [ -e "${JAVADOC}" ]; then
        chmod -R 777 "${JAVADOC}"
        rm -r "${JAVADOC}"
      fi

      echo "Prepare javadoc: [${SDIR}] -> [${DDIR}]"
      cp -r --no-preserve=all "${SDIR}" "${DDIR}"
      if [ "$?" -ne "0" ]; then
        echo "***[ERROR] Copy error" 1>&2
        exit 1
      fi

      SJARS="`pwd`/${project}/target/${project}-*-javadoc.jar"
      echo "(Javadoc jar) Try to copy ${SJARS} to ${DDIR}"
      ls ${SJARS} 2>/dev/null | while read jar
      do
        echo "(Javadoc jar) Copy ${jar} to ${DDIR}"
        cp "${jar}" "${DDIR}"
        if [ "$?" -ne "0" ]; then
          echo "***[ERROR] Copy error" 1>&2
          exit 1
        fi
      done
      
#    ls -l ${DDIR}
#    for doc in ${REMOVE_THESES_DOCS}
#    do
#      JAR="${DDIR}/${doc}-*-javadoc.jar"
# echo "xxxxx internal documentation: ${JAR}"
#        rm "${JAR}"
#      if [ -e "${JAR}" ]; then
#        echo "remove internal documentation: ${JAR}"
#        rm "${JAR}"
#      fi
#    done
  done
}
##########################################################
packageLibCore()
{
  echo "------------------------------------------ ${PROJECTS_SUB_cchlib-core}"
  DDIR="${PACKAGE_DIR}/cchlib-core"
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
}
##########################################################
# packageLibEE()
# {
#   echo "------------------------------------------ ${PROJECTS_SUB_CCHLIB_J2EE}"
#   DDIR="${PACKAGE_DIR}/cchlib-j2ee"
#   for project in ${PROJECTS_SUB_CCHLIB_J2EE}
#   do
#     SJAR="./${project}/target/*.jar"
# 
#     echo "Append sub-project ${project}"
#     echo "Copy [${SJAR}] to [${DDIR}]"
#     cp ${SJAR} ${DDIR}
#     if [ "$?" -ne "0" ]; then
#       echo "***[ERROR] Copy error" 1>&2
#       exit 1
#     fi
#   done
#   # No javadoc for these projects
#   # rm ${DDIR}/*-javadoc.jar ${DDIR}/*-shaded.jar ${DDIR}/original-*.jar
# }
##########################################################
packageApps()
{
  echo "------------------------------------------"
  DDIR="${PACKAGE_DIR}/cchlib-apps"
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
  SCRIPTSSRC="./metadata/start-apps/${project}"
  echo "Copy [${SCRIPTSSRC}/*.sh] to [${DDIR}]"
  cp ${SCRIPTSSRC}/*.sh ${DDIR}
  if [ "$?" -ne "0" ]; then
    echo "***[ERROR] Copy error" 1>&2
    exit 1
  fi
done

  # No javadoc for these projects
  rm ${DDIR}/*-javadoc.jar
  rm ${DDIR}/original-*.jar
}
##########################################################
cleanDirs()
{
  echo "-- clean dirs ----------------------------------------"
  # clean dirs
  for project in ${PROJECTS} ${PROJECTS_SUB_CCHLIB_CORE} ${PROJECTS_SUB_CCHLIB_J2EE} ${PROJECTS_APPS} cchlib-apps
  do
    DDIR="${PACKAGE_DIR}/${project}"

    echo "Clean: ${DDIR}"

    if [ -e "${DDIR}/*-shaded.jar" ]; then
      rm -f ${DDIR}/*-shaded.jar
    fi
    if [ -e "${DDIR}/original-*.jar" ]; then
      rm -f ${DDIR}/original-*.jar
    fi
  done
}
##########################################################

##########################################################
# MAIN ###################################################
##########################################################
EXEC="./package-javadoc.sh"
echo ${EXEC}
${EXEC}
if [ ! "$?" -eq "0" ];
then
  echo "[ERROR] in ${EXEC}"
  exit 1
fi

PACKAGE_DIR=./.releases
if [ ! -e "${PACKAGE_DIR}" ]; then
  mkdir -p "${PACKAGE_DIR}"
elif [ ! -d "${PACKAGE_DIR}" ]; then
  echo "${PACKAGE_DIR} already exists but is not a directory" 1>&2
  exit 1
fi

# #########################################################
PROJECTS="cchlib-apps
cchlib-core
cchlib-i18n
cchlib-io
cchlib-j2ee
cchlib-jdbf
cchlib-net
cchlib-sql
cchlib-swing
cchlib-sys
cchlib-unit
cchlib-samples
cchlib-tools
cchlib-x-googlecontact"

PROJECTS_WITH_DOC="cchlib-core
cchlib-i18n
cchlib-io
cchlib-j2ee
cchlib-jdbf
cchlib-net
cchlib-sql
cchlib-swing
cchlib-sys
cchlib-unit
cchlib-x-googlecontact"

# XXXXX-.*-javadoc.jar
REMOVE_THESES_DOCS="cchlib-apps-regexpbuilder
DuplicateFilesManager
EditResourcesBundle
cchlib-core-beta
cchlib-core-sample"

#PROJECTS_SUB_CCHLIB_CORE="cchlib-core-sample cchlib-core-beta"
PROJECTS_SUB_CCHLIB_CORE="cchlib-core-beta"

# PROJECTS_SUB_CCHLIB_J2EE="cchlib-j2ee-deprecated"

PROJECTS_APPS="cchlib-apps-duplicatefilesmanager
cchlib-apps-regexpbuilder
cchlib-apps-editresourcebundle"

echo "-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-="
copyJars
copyJavadoc
packageLibCore
# packageLibEE
packageApps
cleanDirs
echo "-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-="

echo "------------------------------------------"

echo "TODO mvn deploy --errors"
echo "in each needed sub projets"
echo "(Not done by this batch)"
echo "mvn deploy --errors"
echo "or for no tests:"
echo "mvn deploy --errors -Dmaven.test.skip=true"
echo "------------------------------------------"
##########################################################
# END
##########################################################

