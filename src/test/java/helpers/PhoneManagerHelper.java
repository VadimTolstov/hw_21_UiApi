package helpers;

import com.codeborne.selenide.WebDriverRunner;
import io.appium.java_client.AppiumDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;

import java.util.Collections;

import static java.time.Duration.ofMillis;
import static org.openqa.selenium.interactions.PointerInput.Kind.TOUCH;
import static org.openqa.selenium.interactions.PointerInput.MouseButton.LEFT;
import static org.openqa.selenium.interactions.PointerInput.Origin.viewport;

public class PhoneManagerHelper {

    private final static PointerInput FINGER = new PointerInput(TOUCH, "finger");

    @Step("Свайп справа налево")
    public static void swipeFromRightToLeft() {
        AppiumDriver driver = (AppiumDriver) WebDriverRunner.getWebDriver();

        Dimension dimension = driver.manage().window().getSize();
        Point start = new Point((int) (dimension.width * 0.9), (int) (dimension.height * 0.5));
        Point end = new Point((int) (dimension.width * 0.1), (int) (dimension.height * 0.5));
        doSwipe(driver, start, end, 300);  //with duration 0.3s
    }

    private static void doSwipe(AppiumDriver driver, Point start, Point end, int duration) {
        Sequence swipe = new Sequence(FINGER, 1)
                .addAction(FINGER.createPointerMove(ofMillis(0), viewport(), start.getX(), start.getY()))
                .addAction(FINGER.createPointerDown(LEFT.asArg()))
                .addAction(FINGER.createPointerMove(ofMillis(duration), viewport(), end.getX(), end.getY()))
                .addAction(FINGER.createPointerUp(LEFT.asArg()));
        driver.perform(Collections.singletonList(swipe));
    }

}
