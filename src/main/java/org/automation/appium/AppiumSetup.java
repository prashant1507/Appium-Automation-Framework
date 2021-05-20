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

@SuppressWarnings("deprecation")
@Log
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AppiumSetup {

    public static HashMap<String, Object> startAppiumServer() {
        AppiumServiceBuilder builder = new AppiumServiceBuilder();
        ObjectMapper mapper = new ObjectMapper();
        HashMap<String, Object> capabilities = null;
        try {
            FileUtils.forceMkdir(new File(GlobalVars.getLogsDir()));
            Object obj = getCapability("capabilities", CapabilityManager.getDeviceUDID());
            capabilities = mapper.readValue(String.valueOf(obj), HashMap.class);

            builder.usingAnyFreePort();
            builder.withIPAddress(PropertyUtils.get(ConfigMap.IP));
            builder.withAppiumJS(new File(PropertyUtils.get(ConfigMap.APPIUMJS)));
            builder.usingDriverExecutable(new File(PropertyUtils.get(ConfigMap.NODEJS)));
            builder.withLogFile(new File(GlobalVars.getLogsDir()
                    .concat(String.valueOf(getCapability("name", CapabilityManager.getDeviceUDID()))
                    .concat(Thread.currentThread().getName())
                    .concat(".txt"))));
            builder.withArgument(GeneralServerFlag.SESSION_OVERRIDE);
            builder.withArgument(GeneralServerFlag.LOG_LEVEL, "error");
            builder.withStartUpTimeOut(60, TimeUnit.SECONDS);

//            Map<String, String> env = new HashMap<>();
//            env.put("JAVA_HOME", "");
//            env.put("ANDROID_HOME", "");
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
            log.severe("Found 0 devices connected. Check debugging option.");
            System.exit(1);
        }

        for (JadbDevice device : devices) {
            InputStream inputStream = device.executeShell("getprop ro.build.version.release");

            String serial = device.getSerial().trim();
            String template = GlobalVars.getCapabilityDir() + "Copy_" + serial + ".json";
            String platformVersion = Stream.readAll(inputStream, StandardCharsets.UTF_8).trim();
            String appPackage = PropertyUtils.get(ConfigMap.APPPACKAGE);
            String appActivity = PropertyUtils.get(ConfigMap.APPACTIVITY);

            File capabilityFile = new File(GlobalVars.getCapabilityDir() + serial + ".json");
            GlobalVars.getConnectedDevices().put(serial, false);
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
        for (String udid : GlobalVars.getConnectedDevices().keySet()) {
            if (!GlobalVars.getConnectedDevices().get(udid)) {
                GlobalVars.getConnectedDevices().replace(udid, true);
                return udid;
            }
        }
        return null;
    }

    public static synchronized void resetDeviceStatusFlag(String udid) {
        GlobalVars.getConnectedDevices().replace(udid, false);
    }

    public static File getCapabilityFile(String udid) {
        return new File(GlobalVars.getCapabilityDir() + udid + ".json");
    }

    public static Object getCapability(String key, String udid) {
        if (CapabilityManager.getDeviceUDID() == null) {
            log.info("All devices are busy. Available device(s) 0.");
            Thread.currentThread().stop();
        }

        JSONParser jsonParser = new JSONParser();
        try {
            JSONArray jsonArray = (JSONArray) jsonParser.parse(new FileReader(GlobalVars.getResPath() + "capabilities/" + udid + ".json"));
            for (Object obj : jsonArray) {
                return ((JSONObject) obj).get(key);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}
