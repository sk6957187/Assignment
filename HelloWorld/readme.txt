registered app for normal run -->
    goto run->edit configuration -> set build and run

read through .yml file --->
    Edit configuration config parameter
    server meta-data/app_env_config.yml

To build project(jar file build) -->
    mvn clean install

To run build jar files -->
    Go to current directory and run following command
    java -jar HelloWorld-1.0.0-SNAPSHOT.jar server meta-data/app_env_config.yml

Render .ftl -->
    create separate AppView class and import dropwizard-views-freemarker
