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

## Google App Engine App Configuration
To be able to deploy this app, you will need to edit some changes to your GAE
app configuration.

1. Enable the Google+ API and the Google Calendar API (Click on APIs on your 
   project page in GAE website)
   
2. In the same page, click on "Credentials". Click on "Create a new Client ID",
   then select "Web Application". Note: Change your origin and callback links
   so that they are the same as your application URL.
   
3. Download the JSON file for the new client id, rename it to 
   `client_secrets.json` and put it inside `src/main/resources`. You should be
   good to go now!


