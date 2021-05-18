package org.automation.appium;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;
import org.apache.commons.io.FileUtils;
import org.automation.constants.GlobalVars;
import org.automation.enums.ConfigMap;
import org.automation.utils.FileSystemHandler;
import org.automation.utils.PropertyUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.SkipException;
import se.vidstige.jadb.JadbConnection;
import se.vidstige.jadb.JadbDevice;
import se.vidstige.jadb.JadbException;
import se.vidstige.jadb.Stream;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Log
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AppiumServerSetup {

    static HashMap<String, Boolean> connectedDevices = new HashMap<>();

    public static HashMap<String, Object> setUpServer() {
        AppiumServiceBuilder builder = new AppiumServiceBuilder();
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<String, Object> capabilities = null;
        try {
            FileUtils.forceMkdir(new File(GlobalVars.getLogsDir()));
            Object obj = getCapability("capabilities");
            capabilities = objectMapper.readValue(String.valueOf(obj), HashMap.class);

            builder.usingAnyFreePort();
            builder.withIPAddress(PropertyUtils.get(ConfigMap.IP));
            builder.withAppiumJS(new File(PropertyUtils.get(ConfigMap.APPIUMJS)));
            builder.usingDriverExecutable(new File(PropertyUtils.get(ConfigMap.NODEJS)));
            builder.withLogFile(new File(GlobalVars.getLogsDir()
                    .concat(String.valueOf(getCapability("name")))
                    .concat(Thread.currentThread().getName())
                    .concat(".txt")));
            builder.withArgument(GeneralServerFlag.SESSION_OVERRIDE);
            builder.withArgument(GeneralServerFlag.LOG_LEVEL, "error");
            builder.withStartUpTimeOut(60, TimeUnit.SECONDS);

//            Map<String, String> env = new HashMap<>();
//            env.put("JAVA_HOME", "C:/Program Files/Java/jdk1.8.0_281");
//            env.put("ANDROID_HOME", "C:/Users/Shibu/AppData/Local/Android/Sdk");
//            builder.withEnvironment(env);

            AppiumDriverLocalService service = AppiumDriverLocalService.buildService(builder);
            service.start();
            ServiceManager.setService(service);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        return capabilities;
    }

    public static void createCapabilityFile() throws IOException, JadbException {

        FileSystemHandler.deleteDir(GlobalVars.getCapabilityDir());
        FileSystemHandler.deleteDir(GlobalVars.getLogsDir());

        JadbConnection jadb = new JadbConnection();
        List<JadbDevice> devices = jadb.getDevices();

        if (devices.isEmpty()) {
            log.severe("Found 0 devices connected.");
            System.exit(1);
        }

        for (JadbDevice device : devices) {
            String serial = device.getSerial().trim();
            String appPackage = PropertyUtils.get(ConfigMap.APPPACKAGE);
            String appActivity = PropertyUtils.get(ConfigMap.APPACTIVITY);

            String template = GlobalVars.getCapabilityDir() + "Copy_" + serial + ".json";
            File capabilityFile = new File(GlobalVars.getCapabilityDir() + serial + ".json");
            connectedDevices.put(serial, false);

            InputStream inputStream = device.executeShell("getprop ro.build.version.release");

            String platformVersion = Stream.readAll(inputStream, StandardCharsets.UTF_8).trim();

            FileSystemHandler.createDir(GlobalVars.getCapabilityDir());
            FileSystemHandler.copyFile(GlobalVars.getCapabilityTemplate(), template);

            String content = FileUtils.readFileToString(new File(template));
            content = content.replace("\"name\": \"\",", "\"name\": \"" + serial + "\",");
            content = content.replace("\"udid\": \"\",", "\"udid\": \"" + serial + "\",");
            content = content.replace("\"platformVersion\": \"\",", "\"platformVersion\": \"" + platformVersion + "\",");
            content = content.replace("\"appPackage\": \"\",", "\"appPackage\": \"" + appPackage + "\",");
            content = content.replace("\"appActivity\": \"\"", "\"appActivity\": \"" + appActivity + "\"");

            FileUtils.writeStringToFile(capabilityFile, content, StandardCharsets.UTF_8);
            FileUtils.forceDelete(new File(template));
        }

    }

    public static synchronized String getAvailableDeviceUDID() {
        for (String udid : connectedDevices.keySet()) {
            if (!connectedDevices.get(udid)) {
                connectedDevices.replace(udid, true);
                return udid;
            }
        }
        return null;
    }

    public static File getCapabilityFile(String udid) {
        return new File(GlobalVars.getCapabilityDir() + udid + ".json");
    }

    public static Object getCapability(String name) {
        if (CapabilityManager.getDeviceUDID() == null) {
            throw new SkipException("All devices are busy. Available device(s) 0.");
        }

        JSONParser jsonParser = new JSONParser();
        try {
            JSONArray jsonArray = (JSONArray) jsonParser.parse(new FileReader(CapabilityManager.getCapabilityFile()));
            for (Object obj : jsonArray) {
                return ((JSONObject) obj).get(name);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}
