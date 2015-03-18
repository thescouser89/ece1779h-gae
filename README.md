ECE1779H Assignment 2 -- Google App Engine
=============================

## To build.

1. Install maven
2. Git clone the project
3. `cd` to the project
4. Run: `mvn clean install`
5. To test locally: `mvn appengine:devserver`
6. To deploy: `mvn appengine:update`

## What needs to be changed?

In the file `src/main/webapp/WEB-INF/appengine-web.xml`, edit the application
name to have the app-id you have created. You could also setup your GAE project
so that it has the same app-id as this one: `cloud-assignment-2`
