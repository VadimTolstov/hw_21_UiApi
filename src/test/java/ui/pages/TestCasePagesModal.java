package ui.pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;


import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class TestCasePagesModal {

    private final String ALLURE_URL_PROJECT = "https://allure.autotests.cloud/project/";

    private final SelenideElement
            testCaseInput = $(".TestCaseLayout__name"),
            testCaseDescriptionInput = $("[data-testid='section__description']"),
            testCaseTagInput = $("[data-testid='section__tags']"),
            testCaseCommentInput = $(".Comment__body");

    @Step("Открываем страницу проекта {projectId} с test case {testCaseId}")
    public TestCasePagesModal openPageTestCase(Long projectId, Long testCaseId) {
        Selenide.open(ALLURE_URL_PROJECT + projectId + "/test-cases/" + testCaseId);
        return this;
    }

    @Step("Открываем страницу с test cases")
    public TestCasePagesModal openTestCasesPages() {
        $("[name='test-cases-icon']").click();
        return this;
    }
    @Step("Создаем test case {name}")
    public TestCasePagesModal createTestCase(String name) {
        $("[data-testid='input__create_test_case']").setValue(name).pressEnter();
        return this;
    }

    @Step("Открываем test case {}")
    public TestCasePagesModal openTestCase(String nameTestCase) {
        $(byText(nameTestCase)).click();
        return this;
    }

    @Step("Проверяем имя {testCaseName} у test case")
    public TestCasePagesModal verifyNameTestCase(String testCaseName) {
        testCaseInput.shouldHave(text(testCaseName)).shouldHave(visible);
        return this;
    }

    @Step("Проверяем описание у test case")
    public TestCasePagesModal verifyDescriptionTestCase(String testCaseDescription) {
        testCaseDescriptionInput.shouldHave(text(testCaseDescription)).shouldHave(visible);
        return this;
    }

    @Step("Проверяем, что tag {tagName} добавлен в test case")
    public TestCasePagesModal verifyAddendumTagTestCase(String tagName) {
        testCaseTagInput.shouldHave(text(tagName)).shouldHave(visible);
        return this;
    }

    @Step("Проверяем, что коментарий {comment} добавлен к test case")
    public TestCasePagesModal verifyCommentTestCase(String comment) {
        testCaseCommentInput.shouldHave(text(comment)).shouldHave(visible);
        return this;
    }

    @Step("Проверяем, что шаг {step} добавлен в test case")
    public TestCasePagesModal verifyStepsTestCase(String step) {
        $x("//pre[text()='" + step + "']").shouldHave(text(step)).shouldHave(visible);
        return this;
    }

    @Step("Проверяем, что test case {nameTestCase} создался")
    public TestCasePagesModal verifyTestCaseName(String nameTestCase) {
        $(byText(nameTestCase)).click();
        testCaseInput.shouldHave(visible).shouldHave(text(nameTestCase));
        return this;
    }

}