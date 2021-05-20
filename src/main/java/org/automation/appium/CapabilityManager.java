package org.automation.appium;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.File;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CapabilityManager {

    private static final ThreadLocal<String> deviceUDID = new ThreadLocal<>();
    private static final ThreadLocal<File> capabilityFile = new ThreadLocal<>();

    public static String getDeviceUDID() {
        return deviceUDID.get();
    }

    public static File getCapabilityFile() {
        return capabilityFile.get();
    }

    public static void setDeviceUDID(String udid) {
        deviceUDID.set(udid);
    }

    public static void setCapabilityFile(File file) {
        capabilityFile.set(file);
    }

    public static void unloadDeviceUDID() {
        deviceUDID.remove();
    }

    public static void unloadCapabilityFile() {
        capabilityFile.remove();
    }

}
