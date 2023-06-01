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
    String[] browserAndVersion();

    @Key("browserSize")
    @DefaultValue("1920x1080")
    String browserSize();

    @Key("remoteDriverUrl")
    String remoteDriverUrl();

    @Key("baseUrl")
    @DefaultValue("https://allure.autotests.cloud")
    String baseUrl();


    String platform();
}
