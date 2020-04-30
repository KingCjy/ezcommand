#!/bin/bash

mvn clean install -Dmaven.test.skip=true -pl ezcommand-core -am
mvn clean package -Dmaven.test.skip=true -pl ezcommand-minecraft -am

mv /Users/kingcjy/git/ezcommand/ezcommand-minecraft/target/ezcommand-minecraft-1.0.jar ~/server/plugins
