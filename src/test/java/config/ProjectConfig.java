package config;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "system:properties",
        "classpath:config/${platform}.properties"
})
public interface ProjectConfig extends Config {

    @Key("browserWithVersion")
    @DefaultValue("chrome,113.0")
    String[] getBrowserAndVersion();

    @Key("browserSize")
    @DefaultValue("1920x1080")
    String getBrowserSize();

    @Key("remoteDriverUrl")
    String remoteDriverUrl();

    @Key("baseUrl")
    @DefaultValue("https://allure.autotests.cloud")
    String getBaseUrl();

@DefaultValue("android_browserstack")
    String platform();

    @Key("browserstack.user")
    @DefaultValue("bsuser_wTiNfW")
        String getBrowserstackUser();

    @Key("browserstack.key")
    @DefaultValue("yCm6opRKbLpF6puwsqmz")
    String getBrowserstackKey();

    @Key("deviceName")
    @DefaultValue("Google Pixel 3")
    String getDeviceName();

    @Key("platformVersion")
    @DefaultValue("9.0")
    String getPlatformVersion();

    @Key("app")
    @DefaultValue("bs://c700ce60cf13ae8ed97705a55b8e022f13c5827c")
    String getApp();

    @Key("base_url")
    @DefaultValue("https://api.browserstack.com/app-automate/sessions/%s.json")
    String getSessionsUrl();
}
