#!/bin/bash
#
set +ex

mvn clean install | tee .logs/mvn-install.log

cat .logs/mvn-install.log | grep "warning: no description for" | sort | uniq > .logs/mvn-install-warn-no-desc.log
cat .logs/mvn-install.log | grep "warning: no @param for" | sort | uniq > .logs/mvn-install-warn-no-param.log
cat .logs/mvn-install.log | grep "warning: no @return" | sort | uniq > .logs/mvn-install-warn-no-return.log
cat .logs/mvn-install.log | grep "error: unknown tag:" | sort | uniq > .logs/mvn-install-error-unknown-tag.log

cat .logs/mvn-install.log | grep -v "warning: no description for" \
  | grep -v "warning: no @param for" \
  | grep -v "warning: no @return" \
  | grep -v "error: unknown tag:" \
  > .logs/mvn-install-others.log

pushd apps
./buildAllApps.sh | tee ../.logs/buildAllApps.log
popd
