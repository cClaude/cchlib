#!/bin/bash
#
# #########################################################
#
# #########################################################
# Apache Maven 2.2.1 (r801777; 2009-08-06 21:16:01+0200)
# #########################################################
#
EXEC="./mvn-clean-package-javadoc.sh"
echo ${EXEC}
#${EXEC}
if [ ! "$?" -eq "0" ];
then
  echo "[ERROR] in ${EXEC}"
  exit 1
fi

echo "------------------------------------------"
LST="cchlib-core,cchlib-core-deprecated,\
cchlib-i18n,\
cchlib-j2ee,cchlib-j2ee-deprecated,\
cchlib-jdbf,\
cchlib-net,\
cchlib-sql,\
cchlib-swing,cchlib-swing-deprecated,\
cchlib-sys"

echo "TODO mvn deploy --errors"
echo "in each needed sub projets"
echo "(Not done by this batch)"
echo "mvn deploy --errors --projects ${LST}"
echo "or for no tests:"
echo "mvn deploy --errors -Dmaven.test.skip=true --projects ${LST}"
echo "------------------------------------------"
