package tests.steam;

import config.AuthConfig;
import config.ProjectConfig;
import helpers.Android;
import io.appium.java_client.AppiumBy;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import tests.TestBase;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.sleep;
import static io.qameta.allure.Allure.step;

public class SearchTests extends TestBase {

    static AuthConfig config = ConfigFactory.create(AuthConfig.class, System.getProperties());
    @Test
    @Android
    @Order(1)
    @DisplayName("Авторизация в приложении")
    void openApp() {
        step("Авторизация", () -> {
            $$(AppiumBy.className("android.widget.EditText")).get(0).click();
         //   sleep(1000);
            $$(AppiumBy.className("android.widget.EditText")).get(0).sendKeys(config.steamName());
          //  sleep(1000);
            $$(AppiumBy.className("android.widget.EditText")).get(1).click();
          //  sleep(1000);
            $$(AppiumBy.className("android.widget.EditText")).get(1).sendKeys(config.steamPassword());
           // sleep(1000);
            $$(AppiumBy.className("android.widget.TextView")).get(1).click();
           // sleep(15000);
            $$(AppiumBy.className("android.view.ViewGroup")).get(3).shouldHave(visible);
        });
    }

    @Test
    @Android
    @Disabled
    @DisplayName("Successful search")
    void elvisPresleyTest() {
        step("Type search", () -> {
            $(AppiumBy.accessibilityId("Search Wikipedia")).click();
            $(AppiumBy.id("org.wikipedia.alpha:id/search_src_text")).sendKeys("Elvis Presley");
        });
        step("Verify content found", () ->
                $$(AppiumBy.id("org.wikipedia.alpha:id/page_list_item_container"))
                        .shouldHave(sizeGreaterThan(0)));
    }
}