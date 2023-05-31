package config;

import org.aeonbits.owner.ConfigFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class Project {

    public static ProjectConfig config = ConfigFactory.create(ProjectConfig.class, System.getProperties());

    private static final Logger logger = LoggerFactory.getLogger(Project.class);//todo

    static {
        validateEnvDependentProperties();
        logger.info(config.toString());
    }

    private static void validateEnvDependentProperties() {
        Auth.validateProperty(config.platform(), "platform");
        switch (config.platform()) {
            case "local":
                Auth.validateProperty(Arrays.toString(config.browserAndVersion()), "browserWithVersion");
                break;
            case "remote":
                Auth.validateProperty(config.remoteDriverUrl(), "remoteDriverUrl");
                break;
            case "android_browserstack":
                Auth.validateProperty(config.browserstackUser(), "browserstack.user");
                Auth.validateProperty(config.browserstackKey(), "browserstack.key");
                break;
            case "android_emulator":
                Auth.validateProperty(config.remoteDriverUrl(), "remoteDriver");
                Auth.validateProperty(config.deviceName(), "deviceName");
                Auth.validateProperty(config.platformVersion(), "platformVersion");
                break;
            default:
                throw new IllegalStateException("Неправильное значение в 'platform' " + config);
        }
    }

    public static boolean isRemoteDriver() {
        return !(config.remoteDriverUrl() == null) && !config.remoteDriverUrl().isEmpty();
    }
}
