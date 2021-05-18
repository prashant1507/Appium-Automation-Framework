package org.automation.appium;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ServiceManager {
    private static final ThreadLocal<AppiumDriverLocalService> service = new ThreadLocal<>();

    public static AppiumDriverLocalService getService() {
        return service.get();
    }

    public static void setService(AppiumDriverLocalService ser) {
        service.set(ser);
    }

    public static void unloadService() {
        service.remove();
    }

}
