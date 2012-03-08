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
${EXEC}
if [ ! "$?" -eq "0" ];
then
  echo "[ERROR] in ${EXEC}"
  exit 1
fi

echo "------------------------------------------"
echo "TODO mvn deploy --errors"
echo "in each needed sub projets"
echo "(Not done by this batch)"
echo "mvn deploy --errors --projects cchlib-core,cchlib-j2ee,cchlib-swing"
echo "no tests: -Dmaven.test.skip=true"
echo "------------------------------------------"

