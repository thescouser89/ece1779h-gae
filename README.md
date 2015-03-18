## ECE1779H Assignment 2 - GAE

### To build.

1. Install maven
2. Git clone the project
3. `cd` to the project
4. Run: `mvn clean install`
5. To test locally: `mvn appengine:devserver`, then open your web browser at <http://localhost:8080>
6. To deploy: `mvn appengine:update`
7. Your app should be available at: <http://app-id.appspot.com>

## What needs to be changed?

In the file `src/main/webapp/WEB-INF/appengine-web.xml`, edit the application
name to have the app-id you have created.
