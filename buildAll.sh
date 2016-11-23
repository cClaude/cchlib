#!/bin/bash
#

mvn clean install

pushd apps
./buildAllApps.sh
popd
