## **_Setup Docker Appium_**
1. Create a virtual box  
   ```
   docker-machine create --driver virtualbox appium-test-machine
   ```
2. Once virtual box is ready stop it
   ```
   docker-machine stop appium-test-machine
   ```
3. Enable USB in created docker-machine
   ```
   vboxmanager modifyvm appium-test-machine --usb on --usbehci on
   ```
   * Install extension pack if error `Implementation of USB2.0 controller not found` is displayed.
4. Start docker appium machine
   ```
   docker-machine create --driver virtualbox appium-test-machine
   ```
5. Kill base machine adb `adb kill-server`
6. SSH to docker-machine
   ```
   docker-machine ssh appium-test-machine
   ```
7. In docker-machine connect the device
   ```
   docker exec -it container-appium adb device
   ```
8. Allow USB Debugging in device.
9. Run appium docker in docker-machine
   ```
   docker run --privileged -d -p 4723:4723 -v /dev/bus/usb:/dev/bus/usb --name container-appium appium/appium
   ```
## Register in Selenium Grid
Appium-Docker-Android can be connected with selenium grid by passing following parameters:
   ```
     CONNECT_TO_GRID=true
     APPIUM_HOST=<ip_address_of_appium_server>
     APPIUM_PORT=<port_of_appium_server>
     SELENIUM_HOST=<ip_address_of_selenium_hub>
     SELENIUM_PORT=<port_of_selenium_hub>
      
     docker run --privileged -d -p 4723:4723 -e CONNECT_TO_GRID=true -e APPIUM_HOST="127.0.0.1" -e APPIUM_PORT=4723 -e SELENIUM_HOST="172.17.0.1" -e SELENIUM_PORT=4444 -v /dev/bus/usb:/dev/bus/usb --name container-appium appium/appium
   ```
## Connect device via WiFi
1. Set the target device to listen for a TCP/IP connection `adb tcpip 5555`
2. Connect device using adb `adb connect device_ip`

## Docker Simulator
1. Your machine need to support virtualization. To check it:
   ```
   $sudo apt install cpu-checker
   $kvm-ok
   ```
2. Run Docker-Android
   - For ***Linux OS***, please use image name that contains "x86"
      ```bash
      docker run --privileged -d -p 6080:6080 -p 5554:5554 -p 5555:5555 -e DEVICE="Samsung Galaxy S6" --name android-container budtmo/docker-android-x86-8.1
      ```
     - Other device list [here](https://github.com/budtmo/docker-android/blob/master/README.md)
   - For ***OSX*** and ***Windows OS***, please use Virtual Machine that support Virtualization with Ubuntu OS
3. Verify the ip address of docker host.
   - For OSX, you can find out by using following command:
     ```bash
     docker-machine ip default
     ```
   - For different OS, localhost should work.

4. Open ***http://docker-host-ip-address:6080*** from web browser. Note: Adding ```?view_only=true``` will give user only view only permission.
5. Control real device connected to host:
   ```bash
      docker run --privileged -d -p 6080:6080 -p 5554:5554 -p 5555:5555 --name android-container budtmo/docker-android-real-device
   ```
### Other commands:
- Copy .apk to container: `docker cp path/to/apk container-appium:/opt`

[Reference Conference Video](https://www.youtube.com/watch?v=jE4oSetvzfQ) [Docker appium, SMS simulator]