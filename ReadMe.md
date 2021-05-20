# _Appium Automation Framework_

This is a java appium framework which is using testng, maven, extent report, appium, java, etc to achieve different tasks.

[Project's Java Docs](java-doc)   
[Sample Test Report](framework-generate-sample-report/ExecutionReport.html)   
[Sample Test Video](framework-generate-sample-report)   
[Execution Settings](src/test/resources/config/Config.properties)

### _Project Features_
- Appium server will start and stop automatically with different ports.
- Each device's capability file will be created automatically.
- Supports parallel testing (for parallel="tests" in [testng.xml](testng.xml)).   
- Multiple devices can be attached to machine.
- Generates report after each run for all test cases with screenshots.
- Record video for failed test cases.
- Option to send report to email (Gmail / Outlook).
- Option to run tests on local or remote.
- Read data from Properties file, Excel, JSON files.
- Real time reporting using ELK.
- Option to run on docker grid, local, zalenium or selenoid.
- On-demand browser instance creation using [Dynamic Selenium Grid 4](https://github.com/SeleniumHQ/docker-selenium).
- On-demand browser instance creation using [Zalenium](https://opensource.zalando.com/zalenium/).
- Run using [JenkinsFile](src/test/resources/Jenkins/Jenkinsfile).

### _Docker Compose File_
- [Elastic Search Kibana](src/test/resources/docker/docker-compose-elastic-search-kibana.yml)
- [Jenkins](src/test/resources/docker/docker-compose-jenkins.yml)
- [Selenium Grid 4](src/test/resources/docker/docker-compose-selenium-grid.yml)
- [Selenoid](src/test/resources/docker/Selenoid/docker-compose-selenoid.yml)
- [Zalenium](src/test/resources/docker/docker-compose-zalenium.yml)
- [Dynamic Selenium Grid 4](src/test/resources/docker/Dynamic%20Selenium%20Grid/docker-compose-dynamic-grid.yml)

### _Project Components_
Below are the component details of the framework:

- #### Device interaction using
    [Appium](https://mvnrepository.com/artifact/io.appium/java-client)

- #### Test Data
    Supports to read and maintain data from multiple file types like:
    -  Properties File
    -  Excel File with the help of [ApachePOI Jars](https://mvnrepository.com/artifact/org.apache.poi/poi/)
    -  JSON File

-   #### Test Scripts
    [TestNg](https://mvnrepository.com/artifact/org.testng/testng/) is used as a test framework in order to generate test script taking into account   the `Page Object Model` design pattern.

-   #### Build Tool
    Using `maven`, project dependencies are managed. Test can be run using `pom.xml` and `testng.xml`.

-   #### Reporting
    Generates html report automatically by using [Extent Report 5](https://www.extentreports.com/docs/versions/5/java/index.html).
    By attaching screenshots and execution videos of the failed test cases.<br/>
    However, user can set the framework to take screenshots and videos of passed or/and skipped test cases. All reports are generated in `reports-test-output` folder. Report automatically opens in default browser.     
    **Note:** Exception logs and fail reasons are added to the report as well. All images are in Base64 and videos in mp4.

-   #### Utilities
    Holds common methods to re-use in order to achieve maximum re-usability.

-   #### Test Runner
    -   Tests can be executed in parallel and in cross browsers by using:
        - [pom.xml](pom.xml)
        - [testng.xml](testng.xml)
        - Jenkins
    -   Tests can be executed using:
        - real devices
        - remote devices
        - emulators
    
-   #### Other Components
    - [JavaMail API](https://mvnrepository.com/artifact/com.sun.mail/javax.mail) and [JavaBeans(TM) Activation Framework](https://mvnrepository.com/artifact/javax.activation/activation) is used to send the test report automatically on email using gmail or outlook. However, user can still decide if report has to be send or not.<br/>
      **Note:** Framework allows passwords in `Base64Encode` only.
    - All framework settings are done in properties file, making it easy for a non-technical user.
    - User can opt to run the test with their web drivers placed in drivers folder or with WebDriverManager.


### Getting Started:
1. Set all the properties/setting in [properties file](src/test/resources/config/Config.properties).
2. Run pom.xml or testng.xml file.

### Settings for Properties file
[Path to properties file](src/test/resources/config/Config.properties)

-   `url` of the test environment.
-   `environment` where the test has to be performed.
-   `testername` in order to keep track.
-   `ip` ip where appium is set up.
-   `appiumjs` path to main.js (e.g., %APPDATA%/npm/node_modules/appium/build/lib/main.js)
-   `nodejs` path to node executable (e.g., C:/Program Files/nodejs/node.exe)
-   `apppackage` app package name.
-	`appactivity` app activity name.
-	`appname` app name.
-	`runmode` decides whether to run test cases on local, grid (/ docker-grid), zalenium or selenoid. Accepts yes or no.
     -  if `runmode` is yes then user has to provide the remote url in `remoteurl`.
-   `useelk` yes if elk is deployed
    -   `elksuiteurl` if yes provide the url for kibana
-   To delete old report data:
    ```
    deleteoldreports - to elect if tester wants to delete old reports. Accepts yes or no.
    numberofdays - if yes than how old the files should be. Value in number of days.
    ```   
-   `overridereports` to elect if tester want all reports to be merged in one i.e., current reports plus old ones or create new report for each test suite run. Accepts yes or no.
-   Screenshots:
    ```
    passedscreenshot - to take screenshot of passed test cases. Accepts yes or no.
    ```
-   `retryfailedtestcases`to re-run fail test cases. Accepts yes or no. Not recommended setting to set it as yes.
-   Email details to send report:
    ```
    sendmailafterexecution - to send report. Accepts yes or no.
    sendmailusing - to choose from gmail or outlook.
    emailid - sender's email id.
    emailpassword - sender's password in Base64Encode only.
    receiversemailid - receiver's email id.
    ```   
    **Note**: In order to use gmail then enable [Allow less secure apps](https://myaccount.google.com/lesssecureapps?pli=1&rapt=AEjHL4M-tPfEQqsOBVtOWL9wQTcuoh6uNQC7kNHqA1IgKKctttT5U20uTAcW3mpM7VQfCoTrdrwVnEpKLMfkCsRkGPUziWpq5A)
-   Setup real time report using ELK:   
    ```
    useelk - to enable using real time reporting using ELK. Accepts yes or no.
    elksuiteurl - url of the elastic search data add.
    ```
    **Note:** To use ELK, set the schema to have below keys:
    -   TestName
    -   Status
    -   ExecutionTime
        
        **Note:** More fields can be added and changes should be done accordingly in ELKUtils.java

### How to set up:
- [Selenoid](SetupReadMe/Selenoid.md)  
- [Docker Selenium Grid](SetupReadMe/Docker-Selenium-Grid.md)   
- [Dynamic Selenium Grid](SetupReadMe/Dynamic-Selenium-Grid.md)

### Notes:
1.	For Jenkins to support extent reporting (or other CSS, etc. components ) run below in scripts `https://jenkisURL/script`
      `System.setProperty("hudson.model.DirectoryBrowserSupport.CSP","")`	
2. Uses [lombok](https://www.baeldung.com/lombok-ide).

### References:
- [Zalenium](https://opensource.zalando.com/zalenium/)
- [Selenium Grid 4](https://github.com/SeleniumHQ/docker-selenium)
- [Selenoid](https://github.com/aerokube/selenoid)