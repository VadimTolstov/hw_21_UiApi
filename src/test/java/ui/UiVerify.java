package ui;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class UiVerify {

    private final String ALLURE_URL_PROJECT = "https://allure.autotests.cloud/project/";

    private final SelenideElement
            testCaseInput = $(".TestCaseLayout__name"),
            testCaseDescriptionInput = $("[data-testid='section__description']"),
            testCaseTagInput = $("[data-testid='section__tags']"),
            testCaseCommentInput = $(".Comment__body");


    @Step("Открываем страницу проекта {projectId} с test case {testCaseId}")
    public UiVerify openPageTestCase(Long projectId, Long testCaseId) {
        Selenide.open(ALLURE_URL_PROJECT + projectId + "/test-cases/" + testCaseId);
        return this;
    }

    @Step("Проверяем имя {testCaseName} у test case")
    public UiVerify verifyNameTestCase(String testCaseName) {
        testCaseInput.shouldHave(text(testCaseName)).shouldHave(visible);
        return this;
    }

    @Step("Проверяем описание у test case")
    public UiVerify verifyDescriptionTestCase(String testCaseDescription) {
        testCaseDescriptionInput.shouldHave(text(testCaseDescription)).shouldHave(visible);
        return this;
    }

    @Step("Проверяем, tag {tagName} добавлен в test case")
    public UiVerify verifyAddendumTagTestCase(String tagName) {
        testCaseTagInput.shouldHave(text(tagName)).shouldHave(visible);
        return this;
    }

    @Step("Проверяем, коментарий {comment} добавлен к test case")
    public UiVerify verifyCommentTestCase(String comment) {
        testCaseCommentInput.shouldHave(text(comment)).shouldHave(visible);
        return this;
    }

    @Step("Проверяем, шаг {step} добавлен в test case")
    public UiVerify verifyUpdateTestCaseStepsTest(String step) {
        $x("//pre[text()='" + step + "']").shouldHave(text(step)).shouldHave(visible);
        return this;
    }
}
