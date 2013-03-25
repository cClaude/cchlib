#!/bin/bash
#
# #########################################################
#
# #########################################################
# Apache Maven 3.0.4
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
echo "mvn deploy --errors"
echo "or for no tests:"
echo "mvn deploy --errors -Dmaven.test.skip=true"
echo "------------------------------------------"
