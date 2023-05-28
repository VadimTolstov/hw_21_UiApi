package allure.config;

import org.aeonbits.owner.ConfigFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

import static allure.config.Auth.validateProperty;

public class Project {

    public static ProjectConfig config = ConfigFactory.create(ProjectConfig.class, System.getProperties());

    private static final Logger logger = LoggerFactory.getLogger(Project.class);//todo

    static {
        static {
            if ("API".equals(System.getProperty("tag"))) {
                validateProperty(config.apiBaseUrl(), "apiBaseUrl");
                validateProperty(Auth.config.apiToken(), "apiToken");
            } else {
                validateEnvDependentProperties();
            }
            logger.info(config.toString());
        }

        private static void validateEnvDependentProperties () {
            validateProperty(config.platform(), "platform");
            switch (config.platform()) {
                case "local":
                    validateProperty(Arrays.toString(config.getBrowserAndVersion()), "browserWithVersion");
                    break;
                case "remote":
                    validateProperty(config.remoteDriverUrl(), "remoteDriverUrl");
                    break;
                default:
                    throw new IllegalStateException("Неправильное значение в 'platform' " + config);
            }
        }

        public static boolean isRemoteDriver () {
            return !(config.remoteDriverUrl() == null) && !config.remoteDriverUrl().isEmpty();
        }
    }
