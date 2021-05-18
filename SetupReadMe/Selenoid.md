<h2><i>Selenoid</i></h2>
<h4>This documents is about how to set up selenoid using docker images</h4><br>

1. Create a file in config/browser.json with below sample data. Using vnc images, so we can view the execution in vnc viewer.
    ```
   {
         "firefox": {
            "default": "latest",
            "versions": {
               "latest": {
               "image": "selenoid/vnc_firefox",
               "port": "4444",
               "path": "/wd/hub",
               "tmpfs": {
                  "/tmp": "size=512m"
               }
            }
         }
      },
         "chrome": {
            "default": "latest",
            "versions": {
               "latest": {
               "image": "selenoid/vnc_chrome",
               "port": "4444",
               "tmpfs": {
               "/tmp": "size=512m"
               }
            }
         }
      }
   }
    ```
2. Run compose file - [docker-compose-selenoid.yml](../src/test/resources/docker/Selenoid/docker-compose-selenoid.yml)  
   OR<br/>do below steps from 3 to 4 -------
3. Pull below images:
   1. To run test cases: docker pull aerokube/selenoid
   2. To view the execution: docker pull aerokube/selenoid-ui
   3. To record execution: docker pull selenoid/video-recorder
   4. docker pull selenoid/vnc_chrome
   5. docker pull selenoid/vnc_firefox

4. Start aerokube/selenoid
   ```
   docker run -d --name selenoid -p 4444:4444 -v /var/run/docker.sock:/var/run/docker.sock -v PATH_TO_CONFIG_FOLDER:/etc/selenoid/:ro -v PATH_TO_VIDEO_FOLDER:/opt/selenoid/video/ -e OVERRIDE_VIDEO_OUTPUT_DIR=PATH_TO_VIDEO_FOLDER aerokube/selenoid:latest
   ```
   PATH_TO_CONFIG_FOLDER - where browsers.json is placed   
   PATH_TO_VIDEO_FOLDER - where u want to mount the video folder of docker container   
   <b>Note:</b> By default selenoid provides 5 parallel session. To change this add `--limit=10` at the end of above command.

5. Start aerokube/selenoid-ui to view execution (optional)
   ```
   docker run --rm -d --name selenoid-ui --link selenoid -p 8090:8080 aerokube/selenoid-ui --selenoid-uri=http://selenoid:4444
   ```

6. That's it. Run the test execution.