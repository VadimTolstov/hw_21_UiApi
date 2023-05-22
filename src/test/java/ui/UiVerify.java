package ui;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class UiVerify {

    @Step("Открываем страницу с test case")
    public UiVerify openPageTestCase(Long projectId, Long testCaseId) {
        Selenide.open("https://allure.autotests.cloud/project/" + projectId + "/test-cases/" + testCaseId);
        return this;
    }

    @Step("Проверяем имя test case")
    public  UiVerify verifyNameTestCase(String testCaseName) {
        $(".TestCaseLayout__name").shouldHave(text(testCaseName)).shouldHave(visible);
        return this;
    }
}
