#!/bin/sh
mvn clean compile jxr:jxr findbugs:findbugs pmd:pmd cobertura:cobertura site tomcat:stop tomcat:undeploy tomcat:deploy tomcat:start