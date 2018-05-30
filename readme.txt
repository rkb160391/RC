
#################################################################################################################################
Server Setup:
steps to create Roam REST(Backend) WAR:
---------------------------------------------------------------------------------------------------------------------------------
step 1- checkout the Rest Project from: http://collaborate.bt.com/svn/ngoss/trunk/Labrador/Roam_Connect/RoamRest_Refactored
step 2- import as maven project in eclipse. select the pom from above.
step 3- run as MAVEN build.
step 4- WAR will be created in target folder in the location where you checked out the project above.
URL to run in local is as http://localhost:8080/roamconnect?operatorId=123456789

#################################################################################################################################
Database Setup:
---------------------------------------------------------------------------------------------------------------------------------
step 1- checkout the Database Script file from: http://collaborate.bt.com/svn/ngoss/trunk/Labrador/Roam_Connect/DB_Script/ROAM_METADATA_V3.sql
step 2- import this script file into your database.
step 3- And start your database.


#################################################################################################################################
GUI Setup: 
Deployment Steps for GUI- RoamConnect:
You can use this given war as well as you can create a war using these steps.
---------------------------------------------------------------------------------------------------------------------------------
step 1. checkout the GUI project from: http://collaborate.bt.com/svn/ngoss/trunk/Labrador/Roam_Connect/RoamConnectUi_Refactored
step 2. npm install
step 3. ng build //dist folder will be created after successful execution of this command.(Build Creation)
step 4. Open Command prompt into dist folder.
step 5. and fire jar -cvf roamconnect.war * (It will create a war)
step 6. Now deploy this war into tomcat webapps folder and start your server.
step 7. once war is extracted to roamconnect folder kindly change into index.html file. 

  index.html-

  <link href="roamconnect/assets/css/bootstrap.min.css" rel="stylesheet">
  <link href="roamconnect/assets/css/style.css" rel="stylesheet">
                  
                 
  <script type="text/javascript" src="roamconnect/inline.bundle.js"></script>
  <script type="text/javascript" src="roamconnect/polyfills.bundle.js"></script>
  <script type="text/javascript" src="roamconnect/scripts.bundle.js"></script>
  <script type="text/javascript" src="roamconnect/styles.bundle.js"></script>
  <script type="text/javascript" src="roamconnect/vendor.bundle.js"></script>
  <script type="text/javascript" src="roamconnect/main.bundle.js"></script>

After successfully save, kindly visit the given path. Hopefully your server will be up and running too.

Application URL : http://localhost:8080/roamconnect?operatorId=123456789

your application is up and running now.
