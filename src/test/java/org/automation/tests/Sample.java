package org.automation.tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import lombok.extern.java.Log;
import org.apache.commons.io.FileUtils;
import org.aspectj.util.FileUtil;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import se.vidstige.jadb.JadbConnection;
import se.vidstige.jadb.JadbDevice;
import se.vidstige.jadb.JadbException;
import se.vidstige.jadb.Stream;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;

@Log
public class Sample {

    static HashMap<String, Boolean> connectedDevices = new HashMap<>();
    static ThreadLocal<String> deviceUDID = new ThreadLocal<>();
    static ThreadLocal<File> capabilityFile = new ThreadLocal<>();
    static AppiumDriverLocalService service;

    @BeforeSuite
    public void beforeSuite() throws IOException, InterruptedException, JadbException {
//        Runtime.getRuntime().exec("src/test/resources/adb/windows/adb.exe start-server");
//        Thread.sleep(3000);
//        createCapabilityFile();
//        Runtime.getRuntime().exec("src/test/resources/adb/windows/adb.exe kill-server");
//        Thread.sleep(3000);
    }

    @BeforeTest
    public void beforeTest() {
//        String udid = getAvailableDeviceUDID();
//        File file = getCapabilityFile(udid);
//        deviceUDID.set(udid);
//        capabilityFile.set(file);
    }

    @BeforeMethod
    public void beforeMethod() throws IOException, ParseException {
        String port = String.valueOf(getCapability("port"));

        Object obj = getCapability("capabilities");
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap deviceCaps = objectMapper.readValue(String.valueOf(obj), HashMap.class);

        AppiumServiceBuilder builder = new AppiumServiceBuilder();
        builder.usingPort(Integer.parseInt(port));
        builder.withIPAddress("127.0.0.1");
        builder.withAppiumJS(new File("C:/Users/Shibu/AppData/Roaming/npm/node_modules/appium/build/lib/appium.js"));
        builder.usingDriverExecutable(new File("C:/Program Files/nodejs/node.exe"));
        // builder.withLogFile(new File("C:/Users/Shibu/Downloads/appium.log"));
        // builder.usingAnyFreePort();

        service = AppiumDriverLocalService.buildService(builder);
        service.start();
        URL url = new URL(service.getUrl().toString());
        AppiumDriver<MobileElement> driver = new AppiumDriver<MobileElement>(url, new DesiredCapabilities(deviceCaps));
    }

    @AfterMethod
    public void afterMethod() {
        service.stop();
    }


    public static void createCapabilityFile() throws IOException, JadbException {
        int port = 4723;
        String capFileDir = "src/test/resources/capabilities/";
        FileUtils.deleteDirectory(new File(capFileDir));

        JadbConnection jadb = new JadbConnection();
        List<JadbDevice> devices = jadb.getDevices();

        if (devices.isEmpty()) {
            log.severe("Found 0 devices connected.");
            System.exit(1);
        }

        for(JadbDevice device: devices) {
            String serial = device.getSerial().trim();
            connectedDevices.put(serial, false);

            InputStream inputStream = device.executeShell("getprop ro.build.version.release");

            String platformVersion = Stream.readAll(inputStream, StandardCharsets.UTF_8).trim();
            String appPackage = "com.android.chrome";
            String appActivity = "org.chromium.chrome.browser.ChromeTabbedActivity";

            FileUtils.forceMkdir(new File(capFileDir));
            FileUtil.copyFile(new File("src/test/resources/capabilityTemplate.json"), new File(capFileDir + "Copy_" + serial + ".json"));

            String content = FileUtils.readFileToString(new File(capFileDir + "Copy_" + serial + ".json"));
            content = content.replace("\"name\": \"\",", "\"name\": \"" + serial + "\",");
            content = content.replace("\"udid\": \"\",", "\"udid\": \"" + serial + "\",");
            content = content.replace("\"platformVersion\": \"\",", "\"platformVersion\": \"" + platformVersion + "\",");
            content = content.replace("\"appPackage\": \"\",", "\"appPackage\": \"" + appPackage + "\",");
            content = content.replace("\"appActivity\": \"\"", "\"appActivity\": \"" + appActivity + "\"");
            content = content.replace("\"port\": \"\"", "\"port\": \"" + port + "\"");

            File capFile = new File(capFileDir + serial + ".json");
            FileUtils.writeStringToFile(capFile, content, StandardCharsets.UTF_8);
            FileUtils.forceDelete(new File(capFileDir + "Copy_" + serial + ".json"));
            port++;
        }

    }

    public static synchronized String getAvailableDeviceUDID() {
        for(String udid: connectedDevices.keySet()) {
            if (!connectedDevices.get(udid)) {
                connectedDevices.replace(udid, true);
                return udid;
            }
        }
        return null;
    }

    public static File getCapabilityFile(String udid) {
        return new File("src/test/resources/capabilities/" + udid + ".json");
    }

    public static Object getCapability(String name) throws IOException, ParseException {
        if (deviceUDID.get() == null) {
            throw new SkipException("All devices are busy. Available device(s) 0.");
        }

        JSONParser jsonParser = new JSONParser();
        JSONArray jsonArray = (JSONArray) jsonParser.parse(new FileReader(capabilityFile.get()));

        for(Object obj: jsonArray) {
            return ((JSONObject) obj).get(name);
        }
        return null;
    }
}
