package ui;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class UiVerify {

    @Step("Открываем страницу проекта {projectId} с test case {testCaseId}")
    public UiVerify openPageTestCase(Long projectId, Long testCaseId) {
        Selenide.open("https://allure.autotests.cloud/project/" + projectId + "/test-cases/" + testCaseId);
        return this;
    }

    @Step("Проверяем имя {testCaseName} у test case")
    public  UiVerify verifyNameTestCase(String testCaseName) {
        $(".TestCaseLayout__name").shouldHave(text(testCaseName)).shouldHave(visible);
        return this;
    }

    @Step("Проверяем описание у test case")
    public  UiVerify verifyDescriptionTestCase(String testCaseDescription){
        $("[data-testid='section__description']").shouldHave(text(testCaseDescription)).shouldHave(visible);
        return this;
    }

    @Step("Проверяем, tag {tagName} добавлен в test case")
    public UiVerify verifyAddendumTagTestCase(String tagName){
        $("[data-testid='section__tags']").shouldHave(text(tagName)).shouldHave(visible);
    return this;
    }

    @Step("Проверяем, коментарий {comment} добавлен к test case")
    public UiVerify verifyCommentTestCase(String comment){
        $(".Comment__body").shouldHave(text(comment)).shouldHave(visible);
        return this;
    }

    @Step("Проверяем, шаг {step} добавлен в test case")
    public UiVerify verifyUpdateTestCaseStepsTest(String step){
        $x("//pre[text()='" + step + "']").shouldHave(text(step)).shouldHave(visible);
        return this;
    }
}
