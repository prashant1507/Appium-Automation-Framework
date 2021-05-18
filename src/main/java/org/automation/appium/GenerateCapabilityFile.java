package org.automation.appium;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.automation.constants.GlobalVars;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class GenerateCapabilityFile {

    public static void generateCapabilityFile() {
        try {
            String adbPath;
            if (GlobalVars.getOsPlatform().startsWith("Windows")) {
                adbPath = GlobalVars.getResPath() + "adb/windows/adb.exe";
            } else {
                adbPath = GlobalVars.getResPath() + "adb/linux/adb";
                Runtime.getRuntime().exec(GlobalVars.getExecutePermission() + adbPath);
            }
            Runtime.getRuntime().exec(adbPath.concat(" start-server"));
            Thread.sleep(3000);
            AppiumServerSetup.createCapabilityFile();
            Runtime.getRuntime().exec(adbPath.concat(" kill-server"));
            Thread.sleep(3000);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
