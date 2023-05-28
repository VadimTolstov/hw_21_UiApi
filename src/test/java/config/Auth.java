package config;

import org.aeonbits.owner.ConfigFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;

public class Auth {

    public static AuthConfig config = ConfigFactory.create(AuthConfig.class, System.getProperties());

    private static final Logger logger = LoggerFactory.getLogger(Project.class);

    static {
        validateProperty(config.userNameAllure(), "userNameAllure");
        validateProperty(config.passwordAllure(), "passwordAllure");
        logger.info(config.toString());
    }

    public static void validateProperty(String propertyValue, String propertyName) {
        assertThat(propertyValue)
                .withFailMessage("'%s' значение равно null или пусто!", propertyName)
                .isNotEmpty();
    }
}
