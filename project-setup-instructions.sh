#!/bin/sh
#mvn archetype:generate -DarchetypeGroupId=org.apache.maven.archetypes -DarchetypeArtifactId=maven-archetype-webapp -DgroupId=au.com.numbrcrunchr -DartifactId=numbrcrunchr
mvn clean compile eclipse:clean eclipse:eclipse -Dwtpversion=2.0
