package tests.steam;

import com.codeborne.selenide.Selenide;
import config.AuthConfig;
import helpers.Android;
import helpers.PhoneManagerHelper;
import io.appium.java_client.AppiumBy;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import tests.TestBase;

import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.step;

public class MobilSteamTests extends TestBase {

    static AuthConfig config = ConfigFactory.create(AuthConfig.class, System.getProperties());

    @Test
    @Android
    @Order(1) // ToDo Это зло! НЕ ДЕЛАЙ ТАК НИКОГДА!
    @DisplayName("Авторизация в приложении")
    void openApp() {
        step("Авторизация", () -> {
            $$(AppiumBy.className("android.widget.EditText")).get(0).click();
            $$(AppiumBy.className("android.widget.EditText")).get(0).sendKeys(config.steamName());
            $$(AppiumBy.className("android.widget.EditText")).get(1).click();
            $$(AppiumBy.className("android.widget.EditText")).get(1).sendKeys(config.steamPassword());
            $(AppiumBy.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup[2]/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[1]/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.widget.ScrollView/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[4]")).click();

            // Падало на этом локаторе
//            $(AppiumBy.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup[2]/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[1]/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[1]/android.widget.TextView")).shouldHave(visible).shouldHave(text("WELCOME TO"));

            // Заменить sleep() на проверку, что перед нами нужная форма (UI)
            Selenide.sleep(5_000);

            PhoneManagerHelper.swipeFromRightToLeft();
            PhoneManagerHelper.swipeFromRightToLeft();
            PhoneManagerHelper.swipeFromRightToLeft();
            back();

            Selenide.sleep(5000);
        });
    }

//    @Test
//    @Android
//    @Disabled
//    @Order(2)
//    @DisplayName("Кнопка поиска игр")
//    void searchGames() {
//        step("Поиск игры", () -> {
//            $$(AppiumBy.className("android.widget.EditText")).get(0).click();
//            $$(AppiumBy.className("android.widget.EditText")).get(0).sendKeys(config.steamName());
//            $$(AppiumBy.className("android.widget.EditText")).get(1).click();
//            $$(AppiumBy.className("android.widget.EditText")).get(1).sendKeys(config.steamPassword());
//            $(AppiumBy.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup[2]/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[1]/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.widget.ScrollView/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[4]")).click();
//            $(AppiumBy.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup[2]/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[1]/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[1]/android.widget.TextView")).shouldHave(visible).shouldHave(text("WELCOME TO"));
//
//
//            swipeUpQuick(100);
//            swipeUpQuick(100);
//
//
//            sleep(3000);
//            $$(AppiumBy.className("android.view.ViewGroup")).get(1).click();
//            sleep(5000);
//            $(AppiumBy.className("android.widget.EditText")).sendKeys("Cuphead" + "\n");
//            sleep(5000);
//            $(AppiumBy.xpath("//android.view.View[@content-desc=\"blank\"]/android.widget.Image")).click();
//            sleep(5000);
//            $$(AppiumBy.className("android.view.View")).get(1).shouldHave(text("Cuphead - The Delicious Last Course"));
//        });
//    }
//
//    @Test
//    @Android
//    @Disabled
//    @DisplayName("Successful search")
//    void elvisPresleyTest() {
//        step("Type search", () -> {
//            $(AppiumBy.accessibilityId("Search Wikipedia")).click();
//            $(AppiumBy.id("org.wikipedia.alpha:id/search_src_text")).sendKeys("Elvis Presley");
//        });
//        step("Verify content found", () ->
//                $$(AppiumBy.id("org.wikipedia.alpha:id/page_list_item_container"))
//                        .shouldHave(sizeGreaterThan(0)));
//    }


}