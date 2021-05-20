package org.automation.appium;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.automation.constants.GlobalVars;
import org.automation.utils.ShellCommandExecutor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CapabilityFileGeneration {

    public static void generateCapabilityFile() {
        try {
            String adbPath;
            if (GlobalVars.getOsPlatform().startsWith("Windows")) {
                adbPath = GlobalVars.getResPath() + "adb/windows/adb.exe";
            } else {
                adbPath = GlobalVars.getResPath() + "adb/linux/adb";
                ShellCommandExecutor.executeCommand(GlobalVars.getExecutePermission() + adbPath);
            }
            ShellCommandExecutor.executeCommand(adbPath.concat(" start-server"));
            AppiumSetup.createCapabilityFile();
            ShellCommandExecutor.executeCommand(adbPath.concat(" kill-server"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
