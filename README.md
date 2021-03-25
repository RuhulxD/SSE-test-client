# SSE-test-client
As all the browser has a limitation of creating SSE connections, a simple test client created in java to create desired number of SSE connection to server.


## Required:
* JDK11
* gradle

## Compile:

     gradle clean build

## Run:
##### Note: No additional parameter required right now

     java -jar build/libs/SSE-test-client-0.0.1-SNAPSHOT.jar

## EndPoints:

1. ### Request: SSE end point
    * ### Method
      GET
    * ### path
      /public/ping/subscribe
   ### Response
    * Media type: `Content-Type: text/event-stream;charset=UTF-8`

            {"id":"e26e1c66-98a9-4cc7-8d36-347ace6ce810","epoch":1616665061,"time":"15:37:41","event":"PING","data":" id->1 total consumer->:1"}
            {"id":"1f902d1e-4b19-46b6-ab54-0b3d840437e5","epoch":1616665055,"time":"15:37:35","event":"PING","data":" id->1 total consumer->:1"}
   
2. ### You can also load test the restapi by the provided jmeter configuration file in resource folder.
   * download jmeter: https://jmeter.apache.org/download_jmeter.cgi
   * Open jmeter
   * open configuration: jmeter>file>open
   * load the `/resources/rest-file-read-jmeter.jmx` file.
   * click on run.
   
   
