#!/bin/sh

mvn clean package -Dmaven.test.skip=true

sleep 2

cd /Users/omar/software/resume-extractor-web/

bin/shutdown.sh

cd webapps/

rm -r ROOT

rm ROOT.war

cp /Users/omar/workspace/engine/parent/resume-extractor-web/target/*.war .

mv *.war ROOT.war

cd /Users/omar/software/resume-extractor-web/

rm -rf work/Catalina/

bin/startup.sh
