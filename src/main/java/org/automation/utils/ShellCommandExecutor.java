package org.automation.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;
import org.automation.constants.GlobalVars;

@Log
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ShellCommandExecutor {

    public static void executeCommand(String command) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder();
            if (GlobalVars.getOsPlatform().startsWith("Windows")) {
                processBuilder.command("cmd.exe", "/c", command);
            } else {
                processBuilder.command("sh", "-c", command);
            }
            Process process = processBuilder.start();
            if (process.waitFor() != 0) {
                log.severe(String.format("Failed to execute [ %s ]. Failed to create capability file(s).", command));
                System.exit(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
