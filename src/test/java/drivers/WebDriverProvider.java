package drivers;

import config.Auth;
import config.Project;
import com.codeborne.selenide.Configuration;
import io.restassured.RestAssured;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.Map;

import static config.Project.isRemoteDriver;

public class WebDriverProvider {

    public static void configure() {
        Configuration.baseUrl = Project.config.getBaseUrl();
        Configuration.browserSize = Project.config.getBrowserSize();
        String[] browserWithVersion = Project.config.getBrowserAndVersion();
        Configuration.browser = browserWithVersion[0];
        Configuration.browserVersion = browserWithVersion[1];
        Configuration.pageLoadStrategy = "eager";
        RestAssured.baseURI = "https://allure.autotests.cloud";//todo

        if (isRemoteDriver()) {
            Configuration.remote = String.format("https://%s:%s@%s/wd/hub",
                    Auth.config.userNameSelenoid(),
                    Auth.config.passwordSelenoid(),
                    Project.config.remoteDriverUrl());
        }

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("selenoid:options", Map.<String, Object>of(
                "enableVNC", true,
                "enableVideo", true
        ));

        Configuration.browserCapabilities = capabilities;
    }
}
